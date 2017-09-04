<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<title>OSS web直传</title>
	<link rel="stylesheet" type="text/css" href="style.css"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
</head>
<body>
<style>
	.datagrid-row {
		height: 42px;
		text-align:center;
	}

	.datagrid-header-row {
		height: 42px;
		font-weight:700;
	}
	#fm
	{
		margin: 0;
	}
	.ftitle
	{
		font-size: 14px;
		font-weight: bold;
		padding: 5px 0;
		margin-bottom: 10px;
		border-bottom: 1px solid #ccc;
	}
	.fitem
	{

		border-bottom:1px dashed #9f9f9f;
		padding-bottom:15px;
		padding-top:15px;

	}
	.fitem label
	{
		display: inline-block;
		width: 150px;
	}

	.mytitle {
		font-weight:bolder;
	}
	input{
		height:20px;
	}
</style>
<script type="text/javascript">
    var status =${requestScope.status};
    //图片预览
    function previewFile() {
        var preview = document.querySelector('img');
        var file  = document.querySelector('input[type=file]').files[0];
        var reader = new FileReader();
        reader.onloadend = function () {
            preview.src = reader.result;
        }
        if (file) {
            reader.readAsDataURL(file);
        } else {
            preview.src = "";
        }
    }
</script>

<div class="bodymain">
	<div>
		<div id="ossfile">你的浏览器不支持flash,Silverlight或者HTML5！</div>
		<br/>
		<div id="container" style="padding: 40px;padding-left: 100px">
			<a id="selectfiles" href="javascript:void(0);" class='btn'>选择文件</a>
			<a id="postfiles" href="javascript:void(0);" class='btn' style="padding-left: 30px">开始上传</a>
		</div>

		<%--<div style="padding-left: 20px;color: green">美团图片尺寸为600*450         饿了么图片尺寸为640*640</div>--%>
		<pre id="console"></pre>
		<%--<input type="hidden" id="class_pic" name="class_pic">--%>
		<%--<input type="hidden" id="opt" name="opt" value="update">--%>
		<%--<input type="hidden" id="class_id" name="class_id">--%>
	</div>
</div>

</body>
<script type="text/javascript" src="/static/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="/static/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="/static/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="/static/lib/base64.js"></script>
<script type="text/javascript" src="/static/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="/static/lib/upload2.js"></script>
</html>
