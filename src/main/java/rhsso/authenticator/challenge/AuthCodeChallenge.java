package rhsso.authenticator.challenge;


import com.google.common.flogger.FluentLogger;
import org.keycloak.authentication.AuthenticationFlowContext;
import rhsso.authenticator.SMSConstants;


import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class AuthCodeChallenge {
    private static final int CODE_LENGTH = 4;
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    public enum CODESTATUS {
        Expired,
        Valid,
        InValid
    }

    private String verificationCode;
    private Long verificationExpiryDate;

    private AuthenticationFlowContext authContext;

    public AuthCodeChallenge(){}
    public AuthCodeChallenge(AuthenticationFlowContext context){
        this.authContext = context;
        this.verificationCode = this.generateCode();
        this.verificationExpiryDate = this.generateExpiryDate();
    }
    public AuthCodeChallenge(AuthenticationFlowContext context, Long expiryTime, String code){
        this.authContext = context;
        this.verificationCode = code;
        this.verificationExpiryDate = expiryTime;
    }
    private String generateCode(){

        double maxValue = Math.pow(10.0, CODE_LENGTH);
        Random r = new Random();
        long code = (long) (r.nextFloat() * maxValue);

        logger.atInfo().log("Verification Code :" + String.valueOf(code));
        return String.valueOf(code);
    }

    private Long generateExpiryDate(){
        return new Date().getTime() + (10000 * 1000);
    }

    public String getVerificationCode(){
        return this.verificationCode;
    }
    public Long getVerificationExpiryDate(){
        return this.verificationExpiryDate;
    }

    public Response getChallenge(){
        Response challenge = this.authContext.form()
                .createForm(SMSConstants.SMSCodeInputForm);
        return challenge;
    }


    public AuthCodeChallenge saveChallenge(){

        this.authContext.getUser().setAttribute(SMSConstants.ATTR_VERIFICATION_EXPIRATION_DATE, Arrays.asList(String.valueOf(this.verificationExpiryDate)));
        this.authContext.getUser().setAttribute(SMSConstants.ATTR_VERIFICATION_CODE, Arrays.asList(this.verificationCode));
        return this;
    }
    public AuthCodeChallenge clearChallenge(){
        this.authContext.getUser().removeAttribute(SMSConstants.ATTR_VERIFICATION_CODE);
        this.authContext.getUser().removeAttribute(SMSConstants.ATTR_VERIFICATION_EXPIRATION_DATE);
        return this;
    }
    protected Response getExpiredCodeChallenge(){
        Response challenge = this.authContext.form()
                .setError(SMSConstants.EXPIRED_VERIFICATION_CODE_EXPIRED_MESSAGE)
                .createForm(SMSConstants.SMSCodeInputForm);

        return challenge;
    }
    protected Response getInvalidCodeChallenge(){
        Response challenge = this.authContext.form()
                .setError(SMSConstants.INVALID_VERIFICATION_CODE_MESSAGE)
                .createForm(SMSConstants.SMSCodeInputForm);
        return challenge;
    }

    public Response getErrorChallenge(CODESTATUS status){
        return CODESTATUS.InValid == status ?  getInvalidCodeChallenge() :  getExpiredCodeChallenge();
    }

    private boolean isExpired(){
        long now = new Date().getTime();

        return this.verificationExpiryDate != null && Long.valueOf(this.verificationExpiryDate) < now;
    }
    private boolean isValidCode(String userEnteredCode){
        return this.verificationCode.isEmpty() ? false: this.verificationCode.equals(userEnteredCode);

    }


    public CODESTATUS isValid(MultivaluedMap<String, String> formData){


        String userEnteredCode = formData.getFirst(SMSConstants.SMSCodeInput);

        if(userEnteredCode == null || userEnteredCode.isEmpty()){
            return CODESTATUS.InValid;
        }
        if(this.isExpired()){
            return CODESTATUS.Expired;
        }
        if(!this.isValidCode(userEnteredCode)){
            return CODESTATUS.InValid;
        }
        return CODESTATUS.Valid;
    }

}
