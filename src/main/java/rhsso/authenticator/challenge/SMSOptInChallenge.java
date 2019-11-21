package rhsso.authenticator.challenge;

import org.keycloak.authentication.AuthenticationFlowContext;
import rhsso.authenticator.SMSConstants;
import rhsso.authenticator.models.MobileNumber;


import javax.ws.rs.core.Response;

import java.util.Arrays;

public class SMSOptInChallenge {
    public static final String SMSOptInForm = "sms-opt-in-input.ftl";


    private Boolean hasOptIn;
    private Boolean hasOptInLater;
    private MobileNumber mobileNumber;
    private AuthenticationFlowContext authContext;


    public SMSOptInChallenge(){}


    public SMSOptInChallenge setContext(AuthenticationFlowContext context){
        this.authContext = context;
        return this;
    }

    public SMSOptInChallenge setHasOptIn(Boolean value){
        this.hasOptIn = value;
        return this;
    }
    public SMSOptInChallenge setHasOptInLater(Boolean value){
        this.hasOptInLater = value;
        return this;
    }
    public SMSOptInChallenge setMobileNumber(MobileNumber value){
        this.mobileNumber = value;
        return this;
    }
    public Boolean getHasOptIn(){
        return  this.hasOptIn;
    }

    public Boolean getHasOptInLater(){
        return  this.hasOptInLater;
    }

    public MobileNumber getMobileNumber(){

        return this.mobileNumber;
    }

    public boolean isOptInConfigured(){
        return this.hasOptIn;
    }


    public Response getChallenge(){
        Response challenge = this.authContext.form()
                .createForm(SMSOptInForm);
        return challenge;
    }
    public Response getErrorChallenge(String errorMessage){
        Response challenge = this.authContext.form()
                .setError(errorMessage)
                .createForm(SMSOptInForm);
        return challenge;
    }
    public void saveChallenge(){
        if(this.mobileNumber != null) {
            this.authContext.getUser().setAttribute(SMSConstants.ATTR_MOBILE_NUMBER, Arrays.asList(this.mobileNumber.get()));
        }
        if(this.hasOptInLater != null) {
            this.authContext.getUser().setAttribute(SMSConstants.ATTR_SMS_OPT_IN_LATER, Arrays.asList(this.hasOptInLater.toString()));
        }
        if(this.hasOptIn != null) {
            this.authContext.getUser().setAttribute(SMSConstants.ATTR_SMS_OPT_IN, Arrays.asList(this.hasOptIn.toString()));
        }
    }

    public boolean isValid(){

        if(this.mobileNumber != null) {
            return this.mobileNumber.isValid();
        }
        return false;
    }

}
