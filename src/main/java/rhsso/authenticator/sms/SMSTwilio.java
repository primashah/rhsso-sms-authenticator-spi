package rhsso.authenticator.sms;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import com.twilio.type.PhoneNumber;

public class SMSTwilio {
    private PhoneNumber fromPhoneNumber;
    private PhoneNumber toMobileNumber;
    private String accountSID;
    private String accountToken;

       public SMSTwilio setAccountSID(String value){
           this.accountSID = value;
           return this;
       }
    public SMSTwilio setAccountToken(String value){
        this.accountToken= value;
        return this;
    }

    public SMSTwilio setFromPhoneNumber(String value){
        this.fromPhoneNumber= new PhoneNumber(value);
        return this;
    }

    public SMSTwilio setToMobileNumber(String value){
        this.toMobileNumber = new PhoneNumber(value);
        return this;
    }


    public boolean sendSMS(String smsMessage) {
        try {

            Twilio.init(this.accountSID, this.accountToken);
            Message message = Message.creator(this.toMobileNumber, this.fromPhoneNumber, smsMessage)
                    .create();

            return message.getSid() != null;
        } catch (Exception e) {
            return false;

        }
    }
}
