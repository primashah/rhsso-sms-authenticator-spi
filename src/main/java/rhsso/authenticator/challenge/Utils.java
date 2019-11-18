package rhsso.authenticator.challenge;

import org.keycloak.models.UserModel;
import rhsso.authenticator.SMSConstants;

public class Utils {

    public static String getUserVerificationCode(UserModel user){
        return user.getAttribute(SMSConstants.ATTR_VERIFICATION_CODE).isEmpty()? null:  user.getAttribute(SMSConstants.ATTR_VERIFICATION_CODE).get(0);
    }
    public static Long getUserVerificationExpiryDate(UserModel user){
        return user.getAttribute(SMSConstants.ATTR_VERIFICATION_EXPIRATION_DATE).isEmpty()? null:  Long.valueOf(user.getAttribute(SMSConstants.ATTR_VERIFICATION_EXPIRATION_DATE).get(0));
    }
    public static String getUserMobileNumber(UserModel user){
        return !user.getAttribute(SMSConstants.ATTR_MOBILE_NUMBER).isEmpty() ? user.getAttribute(SMSConstants.ATTR_MOBILE_NUMBER).get(0): null;
    }

    public static Boolean getUserOptIn(UserModel user){
        return getBooleanAttribute(user, SMSConstants.ATTR_SMS_OPT_IN);
    }

    public static Boolean getUserOptInLater(UserModel user){
        return getBooleanAttribute(user, SMSConstants.ATTR_SMS_OPT_IN_LATER);
    }

    private static Boolean getBooleanAttribute(UserModel user,String attributeName){
        return user.getAttribute(attributeName).isEmpty() ? false : Boolean.parseBoolean(user.getAttribute(attributeName).get(0));
    }
}
