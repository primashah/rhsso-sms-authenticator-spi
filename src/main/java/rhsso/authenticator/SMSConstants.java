package rhsso.authenticator;

public class SMSConstants {
    public static final String ATTR_MOBILE_NUMBER = "mobile_number";
    public static final String ATTR_SMS_OPT_IN = "sms_opt_in";
    public static final String ATTR_SMS_OPT_IN_LATER = "sms_opt_in_later";
    public static final String SMSCodeInputForm = "sms-code-input.ftl";
    public static final String SMSCodeInput = "sms_code";
    public static final String ATTR_VERIFICATION_CODE = "verification_code";
    public static final String ATTR_VERIFICATION_EXPIRATION_DATE = "verification_expiry_date";
    public static final String EXPIRED_VERIFICATION_CODE_EXPIRED_MESSAGE ="verificationCodeExpiredMessage";
    public static final String INVALID_VERIFICATION_CODE_MESSAGE ="verificationInvalidCodeMessage";
    public static final String SMS_NOT_SEND_MESSAGE ="SMSNotSend";
    public static final String MOBILE_NUMBER_INVALID_MESSAGE ="MobileNumberInValid";

    public static final String CONFIG_TWILIO_ACCOUNT_ID = "twilio_account_id";
    public static final String CONFIG_TWILIO_ACCOUNT_TOKEN = "twilio_account_token";
    public static final String CONFIG_TWILIO_FROM_PHONE = "twilio_from_phone";

    public static final String SMSOptInForm = "sms-opt-in-input.ftl";



}
