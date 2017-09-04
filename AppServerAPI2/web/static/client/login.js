if (window != top) {
    top.location.href = location.href;
}

$(function () {
    var objChkValue = getCookieValue("objChk");
       
    if (objChkValue == "true") {
        $("#chkRememberPass").attr("checked", true);
        var userNameValue = getCookieValue("uname");
        userNameValue = unescape(userNameValue);
        var userPassValue = getCookieValue("pass");

        $("#uname").val(userNameValue);
        $("#pass").val(userPassValue);
    } else {
        $("#chkRememberPass").attr("checked", false);
        $("#uname").val("");
        $("#pass").val("");
    }


    $('#login').find('input,select').keyup(function (event) {
        if (event.keyCode === 13) {
            $("#btnlogin").click();
        }
    });

    $("#btnlogin").click(function () {
        $(this).attr("disabled", true).html("登录中...");
        $("#btnlogin").html();
        var uname = $("#uname").val();
        var pass = $("#pass").val();
        var pdata = { "username": uname, "pass": pass, "ValidateCode": $("#ValidateCodeValue").val() }

        $.ajax({
            type: "POST",
            data: pdata,
            url: "/login/LoginVerify",
            dataType: 'json',
            success: function (data) {
                $("#btnlogin").attr("disabled", false).html("登&nbsp;&nbsp;&nbsp;&nbsp;录");

                if (data.status == "1") {
                    var objChk = document.getElementById("chkRememberPass");
                  
                    if (objChk.checked) {
                        //添加cookie
                        addCookie("uname", uname, 7, "/");
                        addCookie("pass", pass, 7, "/");
                        addCookie("objChk", "true", 7, "/");
                    } else {
                        deleteCookie("uname", "/");
                        deleteCookie("pass", "/");
                        deleteCookie("objChk", "/");
                    }

                    $(".tips_error").html("登录成功！");
                    window.location.href = "/";
                } else {
                    $("#ValidateCode").click();
                    $(".tips_error").html(data.message);
                }
            },
            error: function (data) {
                $(".tips_error").html("网络请求异常，登陆失败！");
                $("#btnlogin").attr("disabled", false).html("登&nbsp;&nbsp;&nbsp;&nbsp;录");
            }
        });
    });
  

    $("#ValidateCode").click(function () {
        var newSrc = "/Code/index?t=" + (new Date()).getTime();
        this.src=newSrc;
        return false;
    });

    $("#changecode").click(function () {
        $("#ValidateCode").click();
    });
});