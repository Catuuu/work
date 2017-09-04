<%--
  用户登录界面
  User: Administrator
  Date: 12-12-26
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<script type="text/javascript" src="/static/common/print.js"></script>
<html>
<head>

    <title>${systemName}</title>
    <link rel="stylesheet" type="text/css" href="/static/client/login.css"/>
    <script src="/static/client/login.js"></script>
    <script type="text/javascript">
        $(function () {
            switch ('${status}') {
                case '2':
                    $.messager.alert("提示", "用户不存在~~", "info");
                    break;
                case '6':
                    $.messager.alert("提示", "用户未启用~~", "info");
                    break;
                case '7':
                    $.messager.alert("提示", "密码错误~~", "info");
                    break;
                case '8':
                    $.messager.alert("提示", "用户没有使用权限~~", "info");
                    break;
                case '9':
                    $.messager.alert("提示", "无法登陆，请联系系统管理员将此客户端加入系统中~~", "info");
            }

            $("#userLoginBtn").click(function () {
                if ($("#userLoginForm").form('validate')) {
                   // $("#user_pass").val(hex_md5($("#su_pass").val()));
                    $("#user_pass").val($("#su_pass").val());
                    $("#userLoginForm").submit();
                }
            });

            $(document).bind('keydown', function (evt) {
                if (evt.keyCode == 13) {
                    $("#userLoginBtn").click();
                }
            });
        });

    </script>
</head>
<body id="login">
<div id="main">
    <div class="center">
        <div class="center_left">
            <img class="logo" src="/static/images/logo.png" alt="">
        </div>
        <div class="center_right">

            <h2>菜大师门店管理系统</h2>
            <div class="line"></div>
            <div class="right_form">
                <div class="form form_user">
                    <label><span>用户名:</span><input type="text" class="input_line" id="uname"></label>
                </div>
                <div class="form form_password">
                    <label><span>密&nbsp;&nbsp;&nbsp;码:</span><input type="password" class="input_line" input id="pass" ></label>
                </div>
                <div class="form form_usercode clear">
                    <label>
                        <span>验证码: </span><input type="text" class="input" maxlength="4" id="ValidateCodeValue" >
                        <div class="code">
                            <img id="ValidateCode" style="width:100px;height:30px;cursor:pointer" src="/Code/index" title="看不清，点击换一张" />
                        </div>
                    </label>
                    <a href="javascript:;" id="changecode">看不清楚?换一张</a>
                </div>
                <div class="tips_error">

                </div>
                <div class="form form_agree">
                    <label><input type="checkbox" id="chkRememberPass">记住密码</label>
                    <button id="btnlogin" >登&nbsp;&nbsp;&nbsp;&nbsp;录</button>
                </div>
            </div>

        </div>
    </div>
</div>
</body>
</html>
