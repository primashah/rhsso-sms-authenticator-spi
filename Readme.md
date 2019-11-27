# rhsso-push-notification-authenticator-spi

This authentication SPI allows to implement 2factor authentication by sending verification code as SMS using Twilio on  mobile device

## Pre-requiste

* RHSSO instance
* mvn 3.6.2+
* java jdk 1.8
* Twilio configuration - AccountID, Token and FromPhoneNumber
	
	* Provide  Twilio configuration as part of RHSSO SPI configuration


## Build 

	mvn clean install

## Deploy on RHSSO instance running on openshift:

    Login to cluster:

        oc login  <<cluster url >> -u <<username>> -p <<password>>
    
    Get the SSO POD

      oc get pods -n <<sso-project=namespace>>

    Add the jar to the rhsso server:
        $ oc cp target/rhsso-sms1-authenticator-jar-with-dependencies.jar <<sso-pod-name>>:/opt/eap/standalone/deployments

    Add two templates to the rhsso server:
        $ cp themes/rhsso/login <<sso-pod-name>>:/opt/eap/themes/rhsso/login


## Hot Deploy on RHSSO running locally:
	
	Add the jar to the rhsso server:
		mvn clean install wildfly:deploy
		
	Add two templates to the rhsso server:
        $ cp themes/push-notification-2fa _RHSSO_HOME_/themes/

## Configure RHSSO to use SMS as Two Factor Authentication.
 
1. Login to RHSSO Admin Console
2. Select or Create a new Realm (eg. SMS 2FA).
3. Under the selected realm > Authentication > Flows:
    * Copy ‘Browser’ Flow and set name as ‘SMS Browser Flow’.
    * Under the new flow, set the auth execution
          Actions > Add execution > Select ‘SMS Twilio’
    * Under the new flow, set ‘SMS Twilio’ as Required.
    * Set Configuration for the SMS Browser flow by clicking on “Actions > Config” dropdown.

4. Under the selected realm > Authentication > Bindings, select ‘SMS Browser Flow’ as the browser flow for the realm

5. Under the selected realm > Themes, set login theme as dafm-theme or the theme
              where you copied the above templates.

6. Create a client for the mobile/web application.