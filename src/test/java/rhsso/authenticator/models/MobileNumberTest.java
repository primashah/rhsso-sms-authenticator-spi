package rhsso.authenticator.models;

import org.junit.jupiter.api.Test;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class MobileNumberTest {

    @Test
    public void instance(){
        MobileNumber number = new MobileNumber("12345");
        assertNotNull(number, "Mobile number instance should not be null");
    }

    @Test
    public void nullMobileNumber(){
        MobileNumber number = new MobileNumber(null);
        assertFalse(number.isValid(), "Mobile number should not be null");

    }

    @Test
    public void emptyMobileNumber(){
        MobileNumber number = new MobileNumber("");
        assertFalse(number.isValid(), "Mobile number should not be empty");

    }

    @Test
    public void notValidMobileNumber(){
        MobileNumber number = new MobileNumber("123456");
       assertFalse(number.isValid(), "Mobile number should not be in correct format");

    }

    @Test
    public void validMobileNumber(){
        MobileNumber number = new MobileNumber("353876988528");
        assertTrue(number.isValid(), "Mobile number should  be in correct format");

    }

    @Test
    public void sampleMobileTest(){
        String regex = "^((\\+|00)(\\d{1,3})[\\s-]?)?(\\d{9})$";
        String str = "00353876988528";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        if (m.matches())
        {

        }
    }
}
