package rhsso.authenticator.challenge;

import org.junit.jupiter.api.BeforeAll;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.junit.jupiter.api.Test;


import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.UserModel;
import rhsso.authenticator.SMSConstants;
import sun.rmi.runtime.Log;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import java.util.Date;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;




public class AuthCodeChallengeTest {
    private  static AuthenticationFlowContext context;
    private  static LoginFormsProvider form;
    private UserModel user;

    @BeforeAll
    public static void setup(){
        form = createLoginFormsProvider();
        context = createAuthenticationContext();
        when(context.form()).thenReturn(form);
        when(context.form().setError(SMSConstants.EXPIRED_VERIFICATION_CODE_EXPIRED_MESSAGE)).thenReturn(form);
        when(context.form().setError(SMSConstants.INVALID_VERIFICATION_CODE_MESSAGE)).thenReturn(form);


    }

    private static AuthenticationFlowContext createAuthenticationContext() {
        AuthenticationFlowContext mock = mock(AuthenticationFlowContext.class);


        return mock;
    }
    private static LoginFormsProvider createLoginFormsProvider() {
        LoginFormsProvider mock = mock(LoginFormsProvider.class);

        return mock;
    }
    private void setMockGetUser(){
        user = createUserModel();
        when(context.getUser()).thenReturn(user);
    }
    private  UserModel createUserModel(){
        UserModel mockUser = mock(UserModel.class);
        return mockUser;

    }

    @Test
    public void instance(){
        AuthCodeChallenge codeChallenge = new AuthCodeChallenge(context);
        assertNotNull(codeChallenge);
    }

    @Test
    public void isNotValidChallenge(){
        AuthCodeChallenge codeChallenge = new AuthCodeChallenge(context);

        final MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add(SMSConstants.SMSCodeInput, "123456");

        AuthCodeChallenge.CODESTATUS status = codeChallenge.isValid(formData);
        assertEquals(status, AuthCodeChallenge.CODESTATUS.InValid,"User entered code is missing" );

    }

    @Test
    public void isValidChallenge(){
        AuthCodeChallenge codeChallenge = new AuthCodeChallenge(context);
        final MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add(SMSConstants.SMSCodeInput, codeChallenge.getVerificationCode());
        AuthCodeChallenge.CODESTATUS status = codeChallenge.isValid(formData);
        assertEquals(status, AuthCodeChallenge.CODESTATUS.Valid,"User entered code matches" );
    }

    @Test
    public void codeExpiredChallenge(){
        String userInputCode ="1234";
        AuthCodeChallenge codeChallenge = new AuthCodeChallenge(context,new Date().getTime() - 10000,userInputCode);
        final MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.add(SMSConstants.SMSCodeInput, userInputCode);
        AuthCodeChallenge.CODESTATUS status = codeChallenge.isValid(formData);
        assertEquals(status, AuthCodeChallenge.CODESTATUS.Expired,"User entered code matches" );

    }

    @Test
    public void getExpiredErrorChallenge(){
        String userInputCode ="1234";
        AuthCodeChallenge codeChallenge = new AuthCodeChallenge(context,new Date().getTime(),userInputCode);

        Response challenge = codeChallenge.getExpiredCodeChallenge();

        assertEquals(codeChallenge.getErrorChallenge(AuthCodeChallenge.CODESTATUS.Expired), challenge, "should get expired challenge");

    }

    @Test
    public void getInvalidCodeErrorChallenge(){

        AuthCodeChallenge codeChallenge = new AuthCodeChallenge(context,new Date().getTime(),"12121");

        Response challenge = codeChallenge.getInvalidCodeChallenge();

        assertEquals(codeChallenge.getErrorChallenge(AuthCodeChallenge.CODESTATUS.InValid), challenge, "should get invalid code challenge");

    }

    @Test
    public void saveChallenge(){
        AuthCodeChallenge codeChallenge = new AuthCodeChallenge(context,new Date().getTime(),"fsfs");
        setMockGetUser();
        codeChallenge.saveChallenge();
        assertNotNull(codeChallenge, "should not generate any exception");

    }




}
