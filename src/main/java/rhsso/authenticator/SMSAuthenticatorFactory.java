package rhsso.authenticator;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.authentication.ConfigurableAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import com.google.common.flogger.FluentLogger;

import java.util.ArrayList;
import java.util.List;

public class SMSAuthenticatorFactory implements AuthenticatorFactory, ConfigurableAuthenticatorFactory {

    public static final String PROVIDER_ID = "sms-authenticator";
    private static final SMSAuthenticator SINGLETON = new SMSAuthenticator();
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return SINGLETON;
    }

    private static AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };
    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        logger.atInfo().log("SMSAuthenticator: isUserSetupAllowed ");

        return true;
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    private static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static {
        ProviderConfigProperty property;
        property = new ProviderConfigProperty();
        property.setName(SMSConstants.CONFIG_TWILIO_ACCOUNT_ID);
        property.setLabel("Account Id");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Twilio's accountId");
        configProperties.add(property);

        property = new ProviderConfigProperty();
        property.setName(SMSConstants.CONFIG_TWILIO_ACCOUNT_TOKEN);
        property.setLabel("Account Token");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Twilio's account token");
        configProperties.add(property);

        property = new ProviderConfigProperty();
        property.setName(SMSConstants.CONFIG_TWILIO_FROM_PHONE);
        property.setLabel("From Phone Number");
        property.setType(ProviderConfigProperty.STRING_TYPE);
        property.setHelpText("Twilio's from phone number through which message will be sent");
        configProperties.add(property);
    }


    @Override
    public String getHelpText() {
        return "SMS-Twillio";
    }

    @Override
    public String getDisplayType() {
        return "SMS Twilio";
    }

    @Override
    public String getReferenceCategory() {
        return "SMS Twilio";
    }

    @Override
    public void init(Config.Scope config) {

    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {

    }

    @Override
    public void close() {

    }


}