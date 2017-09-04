<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../inc/tld.jsp" %>
<%
    String accept = request.getHeader("Accept");
    if(accept.startsWith("application/json")){
        out.println("{\"exceptionMessage\":\"请求路径无法找到！\",\"exceptionName\":\"ErrorCode404\"}");
    }else{
%>
<style type="text/css">
    p {
        font-size:14px;
        line-height: 25px;
        margin-bottom: 1.5em;
        margin-top: 1em;
    }
</style>
<div style="width: 400px;margin: 120px auto;">
<image src="/static/images/error404.jpg"></image>
<p>请求路径无法找到，或是文件残缺导致该模块无法使用。<br>请联系系统管理员！</p>
</div>
<%}%>