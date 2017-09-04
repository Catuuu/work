<%--
  订单列表
  User: chenbin
  Date: 12-12-26
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width" />
    <title>密码修改</title>
    <style>
        .bodymain {
            background:#fff;
            padding:50px;
        }
        .datagrid-row {
            height: 42px;
            text-align: center;
        }

        .datagrid-header-row {
            height: 42px;
            font-weight: 700;
        }

        #fm {
            margin: 0;

        }

        .ftitle {
            font-size: 14px;
            font-weight: bold;
            padding: 5px 0;
            margin-bottom: 10px;

        }

        .fitem {

            padding-bottom: 15px;
            padding-top: 15px;
        }

        .fitem label {
            display: inline-block;
            width: 100px;
        }

        .mytitle {
            font-weight: lighter;
        }

        #dlg-buttons{
            padding-left:120px;
        }
    </style>
    <script type="text/javascript">
        $.extend($.fn.validatebox.defaults.rules, {
            /*必须和某个字段相等*/
            equalTo: { validator: function (value, param) { return $(param[0]).val() == value; }, message: '两次输入密码不一致!' }
        });

        $(function () {
            $("#fm").form("clear");
        })

        //修改密码
        function savepassword() {
            $.ajax({
                url: "/ClientSet/savePassword",
                data: $("#fm").serialize(),
                type: 'POST',
                beforeSend: function () {
                    return $("#fm").form("validate");
                },
                success: function (result) {
                    if (result.status == "1") {
                        $("#fm").form("clear");
                        $.messager.alert("提示信息", "修改成功");

                    }
                    else {
                        $.messager.alert("提示信息", result.message);
                    }
                }
            });
        }
    </script>

</head>
<body>
    <div class="bodymain">
        <form id="fm" method="post">
            <div style="display:none">
                <input id="oldpass123" name="oldpass123" type="password" value="" />
            </div>
            <div class="fitem">

                <label class="mytitle">原始密码</label>
                <input id="oldpass" name="oldpass" class="easyui-validatebox" required="true" type="password" value=""/>
            </div>
            <div class="fitem">
                <label class="mytitle">新密码</label>
                <input id="password" name="password" class="easyui-validatebox" required="true" type="password" validType="length[4,10]" invalidMessage="密码长度为4至10位"/>
            </div>
            <div class="fitem">
                <label class="mytitle">确认密码</label>
                <input name="repassword" id="repassword" class="easyui-validatebox" required="true" type="password" validType="equalTo['password']" invalidMessage="两次输入密码不匹配"/>
            </div>

        </form>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="savepassword()" iconcls="icon-save">确认修改</a>
        </div>
        </div>
    </div>
</body>
</html>
