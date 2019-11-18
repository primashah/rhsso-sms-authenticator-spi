package rhsso.authenticator.models;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


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
        assertFalse(number.isValid(), "Mobile number should  be in correct format");

    }
}
