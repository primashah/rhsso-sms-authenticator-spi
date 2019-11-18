package rhsso.authenticator.challenge;

import org.junit.jupiter.api.BeforeAll;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.junit.jupiter.api.Test;


import org.keycloak.models.UserModel;
import rhsso.authenticator.SMSConstants;
import rhsso.authenticator.models.MobileNumber;

import java.util.*;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;

public class SMSOptInChallengeTest {

    private  static AuthenticationFlowContext context;
    private  UserModel user;
    @BeforeAll
    public static void setup(){
        context = createAuthenticationContext();
    }



    @Test
    public void userHasOptIn(){
        String mobileNumber = "12222";
        setMockGetUser();
        setMockUserAttributeData(SMSConstants.ATTR_SMS_OPT_IN,"true");
        setMockUserAttributeData(SMSConstants.ATTR_MOBILE_NUMBER,mobileNumber);
        setMockUserAttributeData(SMSConstants.ATTR_SMS_OPT_IN_LATER,null);

        SMSOptInChallenge smsOptInChallenge = new SMSOptInChallenge();
        smsOptInChallenge.setContext(context);
        smsOptInChallenge.setHasOptIn(Utils.getUserOptIn(user));
        smsOptInChallenge.setMobileNumber(new MobileNumber(Utils.getUserMobileNumber(user)));
        smsOptInChallenge.setHasOptInLater(Utils.getUserOptInLater(user));

        assertTrue(smsOptInChallenge.isOptInConfigured(), "User should be configured with SMS Opt in");
       assertNotNull(smsOptInChallenge.getMobileNumber(), "User mobile number should be set");
        assertTrue(smsOptInChallenge.getHasOptInLater() == false, "User opt in later should be false");

    }

    @Test
    public void userHasOptInLater(){
        String mobileNumber = "12222";
        setMockGetUser();
        setMockUserAttributeData(SMSConstants.ATTR_SMS_OPT_IN,"false");
        setMockUserAttributeData(SMSConstants.ATTR_MOBILE_NUMBER,null);
        setMockUserAttributeData(SMSConstants.ATTR_SMS_OPT_IN_LATER,"true");

        SMSOptInChallenge smsOptInChallenge = new SMSOptInChallenge();
        smsOptInChallenge.setHasOptIn(Utils.getUserOptIn(user));
        smsOptInChallenge.setMobileNumber(new MobileNumber(Utils.getUserMobileNumber(user)));
        smsOptInChallenge.setHasOptInLater(Utils.getUserOptInLater(user));

        assertFalse(smsOptInChallenge.isOptInConfigured(), "User should not be configured with SMS Opt in");
        assertNotNull(smsOptInChallenge.getMobileNumber(), "User mobile number should not be set");
        assertTrue(smsOptInChallenge.getHasOptInLater() == true, "User opt in later should be true");

    }

    @Test
    public void instance(){
        setMockGetUser();
        List<String> mockUserData = setMockUserAttributeData(SMSConstants.ATTR_SMS_OPT_IN,"false");

        when(user.getAttribute(SMSConstants.ATTR_SMS_OPT_IN)).thenReturn(mockUserData);
        mockUserData = setMockUserAttributeData(SMSConstants.ATTR_SMS_OPT_IN_LATER,"false");
        when(user.getAttribute(SMSConstants.ATTR_SMS_OPT_IN_LATER)).thenReturn(mockUserData);

        SMSOptInChallenge smsOptInChallenge = new SMSOptInChallenge();
        assertNotNull(smsOptInChallenge);
    }



    private  UserModel createUserModel(){
        UserModel mockUser = mock(UserModel.class);
        return mockUser;

    }
    private static AuthenticationFlowContext createAuthenticationContext() {
        AuthenticationFlowContext mock = mock(AuthenticationFlowContext.class);
        return mock;
    }

    private List<String> setMockUserAttributeData(String attributeName,String valueToMock){
        List<String> listAttrs = new ArrayList<String>();
        listAttrs.add(valueToMock);
        when(user.getAttribute(attributeName)).thenReturn(listAttrs);
        return listAttrs;
    }

    private void setMockGetUser(){
        user = createUserModel();
        when(context.getUser()).thenReturn(user);
    }

}
