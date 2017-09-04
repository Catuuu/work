<%
    if (request.getProtocol().compareTo("HTTP/1.0") == 0)
        response.setHeader("Pragma", "No-cache");
    if (request.getProtocol().compareTo("HTTP/1.1") == 0)
        response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
