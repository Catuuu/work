<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%


    Exception exception = (Exception)request.getAttribute("exception");
    String exceptionClassName = exception.getClass().getName();
    request.setAttribute("exceptionClassName",exceptionClassName);
    String accept = request.getHeader("Accept");

    if(accept.startsWith("application/json")){
        String message = exception.getMessage().replaceAll("\"","\\\\\"");
        String exceptionName = exceptionClassName.replaceAll("\"","\\\\\"");
        out.println("{\"exceptionMessage\":\""+message+"\",\"exceptionName\":\""+exceptionName+"\"}");
        return;
    }
%>
<html>
<head>
    <%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
    <title>异常处理页面</title>
</head>
<body style="margin:0px" >

<c:choose>
    <c:when test="${exceptionClassName =='com.framework.exception.NotAccessException'}">
        <script type="text/javascript">
            $(function(){
                $.closeWin(top.openWindow);
                top.$.messager.alert('信息提示', '${exception.message}!', 'warning');
            })
        </script>
    </c:when>
    <c:when test="${exceptionClassName =='com.framework.exception.NotLoginException'}">
        <script type="text/javascript">
            $(function(){
                $.closeWin(top.openWindow);
                top.$.messager.alert('登录超时', '${exception.message}', 'warning', function () {
                    window.top.location.href = "/login/userLogin";
                });
            })
        </script>
    </c:when>
    <c:otherwise>
        <style type="text/css">
            .exceptionTitle {
                font-size: 16px;
                color: red;
                font-weight: bold;
            }

            .exceptionContent {
                font-size: 16px;
                color: red;
            }
            .u {
                padding: 10px;
                display: inline-block;
                zoom: 1;
                *display: inline; /* IE < 8: fake inline-block */
                vertical-align: top;
                width: 90%;
            }
            p {
                font-size:12px;
                line-height: 1.1;
                margin-bottom: 1.5em;
                margin-top: 1em;
            }
        </style>
        <script type="text/javascript">
            var showException = function() {
                if ($('#exception').css("visibility") == 'visible') {
                    $('#exception').css("visibility", "hidden");
                } else {
                    $('#exception').css("visibility", "visible");
                }
            }
        </script>
        <div class="u">
            <div style="width: 97%">
                <img width="300" height="208" src="/static/images/error_info.png" alt=""/>
            </div>
            <div style="width: 97%">
                <p>系统环境出现问题，或是文件残缺导致该模块无法使用。<br>请联系系统管理员！</p>
                <%--<a href="javascript:showException()">显示详细信息</a>
                <div id="exception" style="padding:10px;visibility:hidden;">
                    <font class="exceptionTitle">异常类:</font>
                    <font class="exceptionContent">${exceptionClassName}</font>
                    <br>
                    <font class="exceptionTitle">异常消息:</font>
                    <font class="exceptionContent">${exception.message}</font>
                </div>--%>
            </div>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>