<#import "template.ftl" as layout>

<@layout.registrationLayout; section>
<#if section = "title">
${msg("loginTitle",realm.name)}
<#elseif section = "header">
${msg("loginTitleHtml",realm.name)}
<#elseif section = "form">

<form id="kc-totp-login-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
    <div class="${properties.kcFormGroupClass!}">

        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-8" style="position:relative !important;border:0px !important">

            <input id="opt_sms_hidden" name="opt_sms_hidden" type="hidden" />
            <label for="mobile_number" class="${properties.kcLabelClass!}"   >
                Would you like to protect login with SMS? </label>
            <input id="mobile_number" name="mobile_number" type="text" class="${properties.kcInputClass!}"  placeholder="Please enter your mobile number" />


        </div>

        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-8" >


            <input style="background: none!important;border: none;padding: 0!important; /*input has OS specific font-family*/color: #069;text-decoration: underline; cursor: pointer;"  id="sms_opt_in_later" type="submit" name="sms_opt_in_later" value="Or would you like to opt in for  SMS later?"/>


        </div>

    </div>

    <div class="${properties.kcFormGroupClass!}">

        <div id="kc-form-options" class="col-xs-12 col-sm-12 col-md-4 col-lg-8">
        <div id="kc-form-buttons" class="submit">
            <div class="${properties.kcFormButtonsWrapperClass!}">
                <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" name="login" id="kc-login" type="submit" value="Validate"/>

            </div>
        </div>
        </div>
    </div>
</form>
</#if>

</@layout.registrationLayout>
