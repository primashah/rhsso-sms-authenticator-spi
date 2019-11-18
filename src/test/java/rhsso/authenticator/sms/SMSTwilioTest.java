package rhsso.authenticator.sms;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SMSTwilioTest {

    @Test
    void instance(){
        SMSTwilio twilio = new SMSTwilio();
        assertNotNull(twilio);
    }
    @Test
    void sendSMS(){
        SMSTwilio twilio = new SMSTwilio();
        boolean isSuccess = twilio.setAccountSID("ACd3d6efe9bb7e4475ac869092ab76158c")
                .setAccountToken("3d77f0a325b5d92993dd7e5d5f90bde7")
                .setFromPhoneNumber("14159657276")
                .setToMobileNumber("353876988528")
               .sendSMS("Testing Auth SPI");

        assertTrue(isSuccess,"We should get a text message ");


    }
    @Test
    void sendSMSFail(){
        SMSTwilio twilio = new SMSTwilio();
        boolean isSuccess = twilio.setAccountSID("ACd3d6efe9bb7e4475ac869092ab76158c")
                .setAccountToken("3d77f0a325b5d92993dd7e5d5f90bde7")
                .setFromPhoneNumber("14159657276")
                .setToMobileNumber("121212")
                .sendSMS("Testing Auth SPI");

        assertFalse(isSuccess,"We should not get a text message ");


    }
}
