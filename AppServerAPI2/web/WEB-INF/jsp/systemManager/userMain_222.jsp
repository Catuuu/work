<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<%--*菜单初始化--%>
<script type="text/javascript" src='/static/common/sysMenus.js'></script>
<html>
<style type="text/css">
    <!--

    -->
</style>
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${systemName}</title>
    <script type="text/javascript">
        $(function () {
            /*$('#mainMenus').rsLoadMenus({
                mSecond: $('#navMenus'),
                mData:${showMenu}});*/

            $('#navMenus').rsLoadMenus({mData:${showMenu}});

            /*重新登录*/
            $('#loginOut').click(function () {
                $.messager.confirm('系统提示', '您确定要重新登录吗?', function (r) {
                    if (r) {
                        location.href = '/login/userLoginOut';
                    }
                });
            });

            /*退出登录*/
            $('#exitOut').click(function () {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function (r) {
                    if (r) {
                        $.postJSON("/login/userExitOut", null, function (jsonMessage) {
                            window.open('', '_self').close();
                        })
                    }
                });
            })
        });
    </script>

</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<noscript>
    <div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
        <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！'/>
    </div>
</noscript>

<%--头部内容区--%>
<div id="northDiv" region="north" split="false" style="height:80px;border-width:0px;">
    <div id="sysResources" style="float:right;width:340px;margin-top: 20px;margin-right:20px;background-color: #00ee00;">
        <a href="#" id="editpass" class="easyui-linkbutton" data-options="iconCls:'icon-editpass'">修改密码</a>
        <a href="#" id="loginOut" class="easyui-linkbutton" data-options="iconCls:'icon-loginOut'">重新登录</a>
        <a href="#" id="exitOut" class="easyui-linkbutton" data-options="iconCls:'icon-exitOut'">安全退出</a>
        <a href="#" id="helpDoc" class="easyui-linkbutton" data-options="iconCls:'icon-help'">帮助手册</a>
    </div>
    <div id="mainMenus" style="float:left;margin-left: 10px;margin-top: 50px;background-color: #00ee00;">
        <span style="width:180px;text-align: left;height: 24px;padding: 5px;">当前登录用户：${loginUser.user_nicename}</span>
    </div>
</div>

<%--尾部内容区--%>
<%--<div region="south" split="false" style="height:30px;">
    <div class="footer">技术支持：武汉至上一家科技有限公司 联系电话：027-6900080</div>
</div>--%>
<%--右边内容区--%>
<%--<div region="east" split="false" title="East" style="width:180px;"></div>--%>

<%--右边内容区--%>
<div id="westDiv" region="west" split="true" title="导航菜单" style="width:180px;" iconCls="icon icon-24">
    <div id="navMenus" class="easyui-accordion" fit="true" border="false"></div>
</div>

<%--中间内容区--%>
<div id="centerDiv" region="center" split="true">
    <div id="tabs" class="easyui-tabs" fit="true" border="false">
        <div title="首页" iconCls="icon icon-194">
            <%--<iframe id="mapiframe" name="mapiframe" frameborder="0" src="welcome.admin"
                    style="width: 100%;height: 100%"></iframe>--%>
        </div>
    </div>
</div>
<div id="_window_div" style="display: none">
    <%--打开Window放置的位置--%>
</div>
</body>
</html>