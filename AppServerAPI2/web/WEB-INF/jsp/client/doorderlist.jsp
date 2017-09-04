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
    <title>订单处理</title>
    <link rel="stylesheet" href="/static/color.css">
</head>
<body>
<div class="bodymain">
    <div class="easyui-panel" style="padding:18px 15px;background:#fff;">

        <a href="###" class="orderstatus c1">申请取消订单<span class="badge pull-right">2</span></a>
        <a href="###" class="orderstatus c2">申请退款订单<span class="badge pull-right">3</span></a>
        <a href="###" class="orderstatus c8">用户催单<span class="badge pull-right">4</span></a>
        <a href="###" class="orderstatus c7">预定单提醒<span class="badge pull-right">4</span></a>
        <a href="###" class="orderstatus c5">配置异常订单<span class="badge pull-right">4</span></a>
        <a href="###" class="orderstatus c6">打包异常订单<span class="badge pull-right">4</span></a>

    </div>

    <div style="padding:5px;background:#fff;position:absolute;top:60px;left:0px;right:0px;bottom:0px;">
        <table id="dg" title="订单信息" class="easyui-datagrid" style="width:100%;height:100%"
               url="get_users.php"
               toolbar="#toolbar" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
            <tr>
                <th field="firstname" width="50">订单编号</th>
                <th field="lastname" width="50">收货人</th>
                <th field="phone" width="50">下单日期</th>
                <th field="email" width="50">订单内容</th>
                <th field="email1" width="50">订单金额</th>
                <th field="email2" width="50">配送员</th>
                <th field="email3" width="50">订单状态</th>
                <th field="email4" width="50">操作</th>
            </tr>
            </thead>
        </table>
    </div>

</div>
</body>
</html>
