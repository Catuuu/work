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
    <title>品牌管理</title>
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
                url: '/Stores/GetStoresList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),

                sortName: 'brand_id',
                //sortOrder: 'desc',
                idField: 'Id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                title: '品牌管理',
                nowrap: "true",
                toolbar: [
                    {
                        text: '添加',
                        iconCls: 'icon-add',
                        handler: function () { add(); }
                    },
                    {
                        text: '修改',
                        iconCls: 'icon-edit',
                        handler: function () { editprint(); }
                    }/*,
                    {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: function () { destroyprint(); }
                    }*/
                ],
                columns: [[
                    { field: 'brand_name', title: '品牌名称', width: 200, align: 'left' },
                    { field: 'brand_comment', title: '品牌备注', width: 200, align: 'left' },
                    { field: 'brand_fromno_start', title: '品牌起始流水号', width: 120, align: 'center' },
                    { field: 'weixin_appid', title: '微信公众号 appID', width: 120, align: 'center' },
                    { field: 'weixin_appsecret', title: '微信公众号 appsecret', width: 150, align: 'center'},
                    { field: 'weixin_token', title: '微信公众号 token', width: 150, align: 'center'},
                    { field: 'weixin_mchid', title: '(微支付)商户号MCHID', width: 150, align: 'center'},
                    { field: 'weixin_key', title: '(微支付)商户支付密钥KEY', width: 150, align: 'center'}
                ]]
            });

            /*$("input[type='radio'][name='print_type']").click(function () {
                var print_type = $(this).val();
                if (print_type == 2) {
                    $(".printcf").hide();
                } else {
                    $(".printcf").show();
                }
            });*/

        });

        //添加打印机
        function add() {
            //$("#fm").form("clear");

            $("#dlg").dialog("open").dialog('setTitle', '添加品牌');
            $("[name='brand_name']").val("");
            $("[name='brand_comment']").val("");
            $("[name='brand_fromno_start']").val("");
            $("[name='weixin_appid']").val("");
            $("[name='weixin_appsecret']").val("");
            $("[name='weixin_token']").val("");
            $("[name='weixin_mchid']").val("");
            $("[name='weixin_key']").val("");


            /*$("input[type='radio'][name='print_type'][value='2']").attr("checked", "checked");
            $("input[type='radio'][name='print_model'][value='1']").attr("checked", "checked");
            $("input[type='radio'][name='isdefault'][value='0']").attr("checked", "checked");*/

            $("#opt").val("add");
           // $(".printcf").hide();
        }


        function editprint() {
            var row = $("#List").datagrid("getSelected");
            if(row==null){
                $.messager.alert("提示信息", "请选择一条记录");
            }
            if (row) {
                $("#dlg").dialog("open").dialog('setTitle', '修改品牌信息');
                $("#fm").form("load", row);
                $("#opt").val("mod");
                /*if (row.print_type == 2) {
                    $(".printcf").hide();
                } else {
                    $(".printcf").show();
                }*/
            }
        }


        //保存打印机信息
        function saveprint() {
            var num = $("input[name='brand_fromno_start']").val();
            if(isNaN(num)){
                $.messager.alert("提示信息", "品牌起始流水号只能输入数字");
            }else{
                $.ajax({
                    url: "/Stores/saveStores",
                    data: $("#fm").serialize(),
                    type: 'POST',
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

        }

        //删除打印机
       /* function destroyprint() {
            var row = $('#List').datagrid('getSelected');
            if (row) {
                $.messager.confirm('Confirm', '你确认删除此打印机吗?', function (r) {
                    if (r) {
                        $.ajax({
                            url: "/ClientSet/destroyprint",
                            type: 'POST',
                            data: { print_id: row.print_id },
                            success: function (result) {
                                if (result.status == "1") {
                                    $.messager.alert("提示信息", "删除成功");
                                    $("#List").datagrid("reload");
                                }
                                else {
                                    $.messager.alert("提示信息", "删除失败");
                                }
                            }
                        });
                    }
                });
            }
        }*/

        //启用、禁用打印机
        function toggle(print_id) {
            $.ajax({
                url: "/ClientSet/toggle",
                type: 'POST',
                data: { print_id: print_id },
                success: function (result) {
                    if (result.status == "1") {
                        $("#List").datagrid("reload");
                    }
                    else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

    </script>

</head>
<body>
<div class="bodymain">
    <table id="List"></table>
    <div id="dlg" class="easyui-dialog" style="width: 550px; height: 530px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem">
                <label class="mytitle">品牌名称:</label>
                <input name="brand_name" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label  class="mytitle">品牌备注:</label>
                <input name="brand_comment" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">品牌起始流水号:</label>
                <input name="brand_fromno_start"class="easyui-validatebox"  required="true" id="fromno" />
            </div>
            <div class="fitem">
                <label class="mytitle">微信公众号 appID:</label>
                <input name="weixin_appid" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">微信公众号 appsecret:</label>
                <input name="weixin_appsecret" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">微信公众号 Token:</label>
                <input name="weixin_token" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">(微支付)商户号MCHID:</label>
                <input name="weixin_mchid" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">(微支付)商户支付密钥KEY:</label>
                <input name="weixin_key" class="easyui-validatebox" required="true" />
            </div>

            <input type="hidden" name="status" id="status" value="1"/>
            <input type="hidden" name="opt" id="opt" value="" />
            <input type="hidden" name="brand_id" id="brand_id" value="0"/>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
