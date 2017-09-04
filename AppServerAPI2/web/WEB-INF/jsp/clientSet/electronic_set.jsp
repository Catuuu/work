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
    <title>电子秤设置</title>
    <style>
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
            border-bottom: 1px solid #ccc;
        }

        .fitem {
            border-bottom: 1px dashed #9f9f9f;
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
    </style>
    <script type="text/javascript">
        $(function () {
            $('#List').datagrid({
                url: '/ClientSet/GetElectronicScaleList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),

                sortName: 'es_id',
                sortOrder: 'desc',
                idField: 'es_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                title: '电子秤设置',
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
                        handler: function () { edit(); }
                    },
                    {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: function () { destroy(); }
                    }
                ],
                columns: [[
                    { field: 'es_name', title: '电子秤名称', width: 200, align: 'left' },
                    { field: 'es_ip', title: '电子秤IP', width: 120, align: 'center' },
                    {
                        field: 'es_type_id', title: '电子秤类型', width: 100, align: 'center', formatter: function (val, row) {
                        if (val == 1) {
                            return '<span style="color:#345645">中餐电子秤</span>';
                        } else {
                            return '<span style="color:#985498">西餐电子秤</span>';
                        }

                    }
                    },
                    { field: 'special_info1', title: '特殊信息1（厨师）', width: 120, align: 'center' },
                    { field: 'special_info2', title: '特殊信息2（配菜组）', width: 150, align: 'center' },
                    { field: 'special_info3', title: '特殊信息3（洗菜组）', width: 150, align: 'center' },
                    { field: 'es_remark', title: '备注', width: 250, align: 'left', nowrap: "true" },
                    {
                        field: 'status', title: '状态', width: 60, align: 'center', formatter: function (val, row) {
                        if (val == 1) {
                            return '<span onclick="toggle(' + row.es_id + ')" style="cursor:pointer">启用</span>';
                        } else {
                            return '<span onclick="toggle(' + row.es_id + ')" style="color:#9f9f9f;cursor:pointer">禁用</span>';
                        }
                    }
                    }
                ]]
            });



        });

        //添加电子秤
        function add() {
            //$("#fm").form("clear");

            $("#dlg").dialog("open").dialog('setTitle', '电子秤添加');
            $("[name='stores_id']").val("0");
            $("[name='es_name']").val("");
            $("[name='es_ip']").val("");
            $("[name='special_info1']").val("");
            $("[name='special_info2']").val("");
            $("[name='special_info3']").val("");
            $("#es_remark").textbox("setValue", "");
            $("input[type='radio'][name='es_type_id'][value='2']").attr("checked", "checked");

            $("#opt").val("add");
        }


        function edit() {
            var row = $("#List").datagrid("getSelected");
            if (row) {
                $("#dlg").dialog("open").dialog('setTitle', '修改电子秤');
                $("#fm").form("load", row);
                $("#opt").val("mod");
            }
        }


        //保存扫描枪信息
        function save() {
            $.ajax({
                url: "/ClientSet/saveElectronicScale",
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

        //删除扫描枪
        function destroy() {
            var row = $('#List').datagrid('getSelected');
            if (row) {
                $.messager.confirm('Confirm', '你确认删除此打印机吗?', function (r) {
                    if (r) {
                        $.ajax({
                            url: "/ClientSet/destroyElectronicScale",
                            data: { es_id: row.es_id },
                            type: 'POST',
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
        }

        //启用、禁用打印机
        function toggle(es_id) {
            $.ajax({
                url: "/ClientSet/electronicScaleToggle",
                data: { es_id: es_id },
                type: 'POST',
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
    <div id="dlg" class="easyui-dialog" style="width: 550px; height: 550px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem">
                <label class="mytitle">电子秤名称</label>
                <input name="es_name" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">电子秤IP</label>
                <input name="es_ip" class="easyui-validatebox" required="true" />
            </div>

            <div class="fitem">
                <label class="mytitle">扫描枪类型</label>
                <label><input type="radio" name="es_type_id" value="1">&nbsp;中餐电子秤</label>
                <label><input type="radio" name="es_type_id" value="2">&nbsp;西餐电子秤</label>
            </div>
            <div class="fitem">
                <label class="mytitle">特殊信息1(厨师)</label>
                <input name="special_info1" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">特殊信息2(配菜组)</label>
                <input name="special_info2" class="easyui-validatebox" required="true" />

            </div>
            <div class="fitem">
                <label class="mytitle">特殊信息3(洗菜组)</label>
                <input name="special_info3" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem printcf">
                <label class="mytitle">备注</label>
                <input class="easyui-textbox" data-options="multiline:true" value="123" style="width:375px;height:100px" id="es_remark" name="es_remark">
            </div>

            <input type="hidden" name="status" id="status" value="1" />
            <input type="hidden" name="stores_id" id="stores_id" value="0" />
            <input type="hidden" name="opt" id="opt" value="" />
            <input type="hidden" name="es_id" id="es_id" value="0" />
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
