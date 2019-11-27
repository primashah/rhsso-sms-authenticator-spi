<#import "template.ftl" as layout>

<@layout.registrationLayout; section>
<#if section = "title">
${msg("loginTitle",realm.name)}
<#elseif section = "header">
${msg("loginTitleHtml",realm.name)}
<#elseif section = "form">
<form id="kc-totp-login-form" class="form-signin" action="${url.loginAction}" method="post">
<img class="mb-4" src="http://www.animalhealthsurveillance.agriculture.gov.ie/media/animalhealthsurveillance/styleassets/images/DAFMLogo2018.png" width="300" height="100">
 <h1 class="h3 mb-3 font-weight-normal" style="text-align: center">2FA with SMS</h1>
    <div class="form-label-group">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" style="position:relative !important;border:0px !important">
            <input id="opt_sms_hidden" name="opt_sms_hidden" type="hidden" />
            <label for="mobile_number" class="control-label"   >
                Would you like to protect login with SMS? </label>
            <input id="mobile_number" name="mobile_number" type="text" class="form-control"  placeholder="Please enter your mobile number" />
        </div>
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" >
            <input style="background: none!important;border: none;padding: 0!important; /*input has OS specific font-family*/color: #069;text-decoration: underline; cursor: pointer;"  id="sms_opt_in_later" type="submit" name="sms_opt_in_later" value="Or would you like to opt in for  SMS later?"/>
        </div>
    </div>

    <div class="form-group">
        <div id="kc-form-options" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        </div>

        <div id="kc-form-buttons" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <div class="button float-right">
                <input class="btn btn-lg btn-primary" name="login" id="kc-login" type="submit" value="Validate"/>

            </div>
        </div>
    </div>
</form>
</#if>

</@layout.registrationLayout>
