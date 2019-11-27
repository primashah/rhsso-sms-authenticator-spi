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
            <label for="sms_code" class="form-control">Enter verification code</label>
            <input id="sms_code" name="sms_code" type="text" class="form-control" placeholder="Enter code you receive through sms" />
    </div>
        <div id="kc-form-buttons" class=" class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        <div class="button float-right">
           <button class="btn btn-lg btn-primary" type="submit" name="login" id="kc-login"  >Login</button>
           <button class="btn btn-lg btn-default" type="submit" name="cancel" id="kc-cancel"  >Cancel</button>
        </div>
        </div>
</form>

</#if>
</@layout.registrationLayout>