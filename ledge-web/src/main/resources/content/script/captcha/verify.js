function initCaptchaVerify(fieldName,url)
{
  $("#"+ fieldName).click(function(){ captchaVerify(url); } );
}

function captchaVerify(url, form, msg)
{
  var challenge = $("#recaptcha_challenge_field").val();
  var response = $("#recaptcha_response_field").val();
  jQuery.get(url, {'recaptcha_challenge_field' : challenge, 'recaptcha_response_field' : response } , function(data){ captchaCallback(data, form, msg); }, "json");
}

function captchaCallback(data, form, msg)
{
  if(data.result)
  {
    form.submit();
  }else{
    alert(msg);
  }
}