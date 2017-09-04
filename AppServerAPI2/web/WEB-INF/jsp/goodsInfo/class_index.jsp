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
    $(function () {

        $('#List').datagrid({
            toolbar: '#selectForm',
            url: '/GoodsInfo/GetGoodsClassLists',
            //width: $(window).width(),
            methord: 'post',
            //height: $(window).height(),

            sortName: 'listorder',
            //sortOrder: 'desc',
            idField: 'class_id',
            striped: true, //奇偶行是否区分
            singleSelect: true,//单选模式
            rownumbers: true,//行号
            pageSize: 10,
            nowrap: "true",
//                toolbar: [
//                    {
//                        text: '添加',
//                        iconCls: 'icon-add',
//                        handler: function () { add(); }
//                    },
//                    {
//                        text: '修改',
//                        iconCls: 'icon-edit',
//                        handler: function () { editprint(); }
//                    }
//                ],
            columns: [[
                { field: 'class_id', title: 'ID', width: '7%', align: 'center'},
                { field: 'class_name', title: '商品种类名称', width: '25%', align: 'center' },
                { field: 'brand_id', title: '品牌', width: '10%',sortable: true, align: 'center',formatter:function(value,row){
                    <c:forEach var="item" items="${brandlist}">
                    if(row.brand_id=="${item.brand_id}"){
                        return "${item.brand_name}";
                    }
                    </c:forEach>
                }  },
                { field: 'listorder', title: '排序', width: '8%', align: 'center' },
                { field: 'class_desc', title: '类别描述', width: '50%', align: 'center'}
            ]]

        });

        //查询
        $("#findBtn").click(function () {
            $("#selectForm").serializeSelectJson("List");
        });

        $("#refreshBtn").click(function () {
            window.location.reload();
        });

        $("#s_brand_id").combobox({
            onChange: function (n, o) {
                $("#selectForm").serializeSelectJson("List");
            }
        });


    });

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

    //添加打印机
    function add() {
        $("#dlg").dialog("open").dialog('setTitle', '添加平台商品类别');
        $(':input','#fm').not(':button,:submit,:reset') .val('');
        $(':input','#fm').not(':button,:submit,:reset') .removeAttr('checked');
        $('input[name=opt]').val("add");
        // $(".printcf").hide();
    }


    function editprint() {
        var row = $("#List").datagrid("getSelected");
        if(row==null){
            $.messager.alert("提示信息", "请选择一条记录");
        }
        if (row) {
            $("#dlg").dialog("open").dialog('setTitle', '修改平台商品类别');
            $("#fm").form("load", row);
            //document.querySelector('img').src="http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+row.class_pic;
            //$("img").attr("src","http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+row.class_pic);
            $('input[name=opt]').val("mod");
            $("#class_id").val(row.class_id);
        }
    }


    //保存品牌
    function saveprint() {
        var class_name = $("#check_name1").val();
        if(class_name.length>10){
            $.messager.show({
                title:'提示信息',
                msg:'分类名称不能超过10个字！',
                showType:'show'
            });
            return false;
		}

        $.ajax({
            url: "/GoodsInfo/saveGoodsClass",
            type: 'POST',
            data: $("#fm").serialize(),
            beforeSend: function () {
                return $("#fm").form("validate");
            },
            success: function (result) {
                if (result.status == "1") {
                    $.messager.alert("提示信息", "操作成功");
                    $("#dlg").dialog("close");
                    $("#List").datagrid("reload");
                }
                else {
                    $.messager.alert("提示信息", "操作失败");
                }
            }
        });
    }

    function checkName(status){
        var name = $("#check_name1").val();
        var brand_id = $("#brand_id").val();
        if(brand_id==null||brand_id==''||brand_id==undefined){
            $.messager.alert("提示信息", "请先选择品牌");
            $('input[name=class_name]').val("");
            return false;
		}
        $.ajax({
            url: "/GoodsInfo/checkName",
            data: {name:name,status:status,brand_id:brand_id},
            type: 'POST',
            success: function (result) {
                if (result.status == "1") {
                    $("#check1").text("商品种类名称不能重复！");
                    $('input[name=class_name]').val("");
                }else{
                    $("#check1").text("");
                }

            }
        });
    }

</script>

<div class="bodymain">
	<div id="selectForm" name="selectForm" style="height: 35px">
		<div class="easyui-panel" style="padding-top:2px;padding-left:5px;padding-bottom: 3px;background:#fff;height:100%;">
			<div style="float: left;padding: 5px">
				<a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton " iconCls="icon-add">添加</a>
				<a href="javascript:void(0)" onclick="editprint()" class="easyui-linkbutton " iconCls="icon-edit">修改</a>
			</div>
			<div style="float: left;padding-left: 100px">
				<label class="mytitle">品牌:</label>
            <select class="easyui-combobox easyui-validatebox" id="s_brand_id" name="brand_id" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'" required="true">
                <option value="" selected="true">全部</option>
                <c:forEach var="item" items="${brandlist}">
                    <option value="${item.brand_id}">${item.brand_name}</option>
                </c:forEach>
            </select>
				<input class="easyui-textbox" name="class_name" style="width:180px"
					   data-options="label:'类型名称：',prompt:'类型名称模糊查询'">
				<a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
				<a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
			</div>
		</div>
	</div>
	<div style="height: 100%;width: 100%">
		<table id="List" style="width:100%;height:100%" toolbar="#selectForm"></table>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 520px; height: 350px; padding: 10px 20px;"
		 closed="true" buttons="#dlg-buttons">

		<form id="fm" method="post">
			<div class="fitem">
			<label class="mytitle">品牌</label>
			<select class="easyui-combobox easyui-validatebox" id="brand_id" name="brand_id" style="width:160px;"
					data-options="editable:false,panelHeight:'auto'" required="true">
				<c:forEach var="item" items="${brandlist}">
					<option value="${item.brand_id}">${item.brand_name}</option>
				</c:forEach>
			</select>
			</div>
			<div class="fitem">
				<label  class="mytitle">商品种类名称:</label>
				<input name="class_name" class="easyui-validatebox" required="true" id="check_name1" onblur="checkName('2')"/>
				<span id="check1" style="color: red"></span>
			</div>
			<div class="fitem">
				<label class="mytitle">类别排序:</label>
				<input name="listorder" class="easyui-numberbox" required="true"  />
			</div>
			<div class="fitem">
				<label class="mytitle">商品详情</label>
				<textarea cols=40 rows=5 name=class_desc class="textbox"></textarea>
			</div>
			<input name="class_id" type="hidden"  value="1"/>
			<input name="opt" type="hidden" value="" />
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>

</div>

</body>
<script type="text/javascript" src="/static/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="/static/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="/static/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="/static/lib/base64.js"></script>
<script type="text/javascript" src="/static/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="/static/lib/upload.js"></script>
</html>
