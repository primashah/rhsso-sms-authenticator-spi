package rhsso.authenticator;

import com.twilio.Twilio;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import rhsso.authenticator.challenge.AuthCodeChallenge;
import rhsso.authenticator.challenge.SMSOptInChallenge;
import rhsso.authenticator.challenge.Utils;
import rhsso.authenticator.models.MobileNumber;
import rhsso.authenticator.sms.SMSTwilio;


import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

public class SMSAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        SMSOptInChallenge smsOptIn = new SMSOptInChallenge();

        smsOptIn.setContext(context)
                .setHasOptIn(Utils.getUserOptIn(context.getUser()))
                .setHasOptInLater(Utils.getUserOptInLater(context.getUser()))
                .setMobileNumber(new MobileNumber(Utils.getUserMobileNumber(context.getUser())));

        Response challenge = smsOptIn.isOptInConfigured() ? sendSMSAndGetChallenge(context,smsOptIn) : smsOptIn.getChallenge();
        context.challenge(challenge);
    }
    private Response sendSMSAndGetChallenge(AuthenticationFlowContext context,SMSOptInChallenge smsOptIn ){
        AuthCodeChallenge authCodeChallenge = new AuthCodeChallenge(context);
        if(sendSMS(authCodeChallenge.getVerificationCode(),Utils.getUserMobileNumber(context.getUser()), context.getAuthenticatorConfig())){
            authCodeChallenge.saveChallenge();
            return authCodeChallenge.getChallenge();
        }else{
            // SMS Not Send Successfully, please try another number.
            return authCodeChallenge.getSMSErrorChallenge(SMSConstants.SMS_NOT_SEND_MESSAGE);
        }
    }



    private Boolean sendSMS(String verificationCode,String toMobileNumber,AuthenticatorConfigModel config){
        SMSTwilio smsTwilio = new SMSTwilio();
        return smsTwilio.setAccountSID(config.getConfig().get(SMSConstants.CONFIG_TWILIO_ACCOUNT_ID))
                .setAccountToken(config.getConfig().get(SMSConstants.CONFIG_TWILIO_ACCOUNT_TOKEN))
                .setFromPhoneNumber(config.getConfig().get(SMSConstants.CONFIG_TWILIO_FROM_PHONE))
                .setToMobileNumber(toMobileNumber)
                .sendSMS(verificationCode);
    }

    private void processAuthCodeInAction(AuthenticationFlowContext context,  MultivaluedMap<String, String> formData){
        AuthCodeChallenge authCodeChallenge = new AuthCodeChallenge(context, Utils.getUserVerificationExpiryDate(context.getUser()), Utils.getUserVerificationCode(context.getUser()));
        AuthCodeChallenge.CODESTATUS status = authCodeChallenge.isValid(formData);

        if(status == AuthCodeChallenge.CODESTATUS.InValid || AuthCodeChallenge.CODESTATUS.Expired == status){
            context.challenge(authCodeChallenge.getErrorChallenge(status));
            return;
        }
        authCodeChallenge.clearChallenge();
        context.success();
    }

    private void setAuthCodeChallenge(AuthenticationFlowContext context, SMSOptInChallenge smsOptIn){
        AuthCodeChallenge authCodeChallenge = new AuthCodeChallenge(context);

        if(sendSMS(authCodeChallenge.getVerificationCode(),smsOptIn.getMobileNumber().get(),context.getAuthenticatorConfig())){
            smsOptIn.saveChallenge();
            authCodeChallenge.saveChallenge();
            context.challenge(authCodeChallenge.getChallenge());
        }else{
            // SMS Not Send Successfully, please try another number.
            context.challenge(smsOptIn.getErrorChallenge(SMSConstants.SMS_NOT_SEND_MESSAGE));
        }
    }
    private void processSMSOptInAction(AuthenticationFlowContext context, MultivaluedMap<String, String> formData){
        SMSOptInChallenge smsOptIn = new SMSOptInChallenge();

        if (formData.containsKey(SMSConstants.ATTR_SMS_OPT_IN_LATER)) {
            smsOptIn.setContext(context)
                    .setHasOptIn(false)
                    .setHasOptInLater(true)
                    .saveChallenge();
            context.success();
            return;
        }
        smsOptIn
                .setContext(context)
                .setHasOptIn(true)
                .setHasOptInLater(false)
                .setMobileNumber(new MobileNumber(formData.getFirst(SMSConstants.ATTR_MOBILE_NUMBER)));

        if(smsOptIn.isValid()){
            setAuthCodeChallenge(context, smsOptIn);
        }
        else{
            // mobile number not valid
            context.challenge(smsOptIn.getErrorChallenge(SMSConstants.MOBILE_NUMBER_INVALID_MESSAGE));
        }
    }
    @Override
    public void action(AuthenticationFlowContext context) {

        MultivaluedMap<String, String> formData = context.getHttpRequest().getDecodedFormParameters();
        if (formData.containsKey("cancel")) {
            context.resetFlow();
            return;
        }
        if(formData.containsKey("opt_sms_hidden")){
            this.processSMSOptInAction(context,formData);
        }else {
           this.processAuthCodeInAction(context,formData);

        }
    }


    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
         return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {

    }
}