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
    <title>打印机设置</title>
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
            width: 100px;
        }

        .mytitle {
            font-weight:lighter;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $('#List').datagrid({
                url: '/ClientSet/GetPrintList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),

                sortName: 'print_id',
                sortOrder: 'desc',
                idField: 'Id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                title: '打印机设置',
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
                    },
                    {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: function () { destroyprint(); }
                    }
                ],
                columns: [[
                    { field: 'name', title: '店铺名称', width: 200, align: 'left' },
                    { field: 'brand_name', title: '品牌名称', width: 200, align: 'left' },
                    { field: 'print_remark', title: '打印机名称', width: 200, align: 'left' },
                    { field: 'print_name', title: '打印机终端号(名称)', width: 120, align: 'center' },
                    { field: 'print_address', title: '终端秘钥(IP)', width: 120, align: 'center' },
                    {
                        field: 'print_model', title: '打印机类型', width: 100, align: 'center', formatter: function (val, row) {
                            if (val == 1) {
                                return '<span style="color:#345645">云打印机</span>';
                            } else {
                                return '<span style="color:#985498">网络打印机</span>';
                            }
                        }
                    },
                    {
                        field: 'status', title: '状态', width: 60, align: 'center', formatter: function (val, row) {
                        if (val == 1) {
                            return '<span onclick="toggle('+row.print_id+')" style="cursor:pointer">启用</span>';
                        } else {
                            return '<span onclick="toggle('+row.print_id+')" style="color:#9f9f9f;cursor:pointer">禁用</span>';
                        }
                    }
                    }
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

            $("#dlg").dialog("open").dialog('setTitle', '打印机添加');
            $("[name='print_remark']").val("");
            $("[name='print_name']").val("");
            $("[name='print_address']").val("");
            $("#print_key").textbox("setValue", "");

            /*$("input[type='radio'][name='print_type'][value='2']").attr("checked", "checked");
            $("input[type='radio'][name='print_model'][value='1']").attr("checked", "checked");
            $("input[type='radio'][name='isdefault'][value='0']").attr("checked", "checked");*/

            $("#opt").val("add");
           // $(".printcf").hide();
        }


        function editprint() {
            var row = $("#List").datagrid("getSelected");
            if (row) {
                $("#dlg").dialog("open").dialog('setTitle', '修改打印机');
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
            $.ajax({
                url: "/ClientSet/savePrint",
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

        //删除打印机
        function destroyprint() {
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
        }

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
    <div id="dlg" class="easyui-dialog" style="width: 550px; height: 470px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem" >
                <label class="mytitle">品牌</label>
                <select class="easyui-combobox" id="brand_id" name="brand_id" style="width:160px;"
                        data-options="editable:false,panelHeight:'auto'">
                    <c:forEach var="item" items="${brandlist}">
                        <option value="${item.brand_id}">${item.brand_name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="fitem" >
                <label class="mytitle">店铺</label>
                <select class="easyui-combobox" id="stores_id" name="stores_id" style="width:160px;"
                        data-options="editable:false,panelHeight:'auto'">
                    <c:choose>
                        <c:when test="${fn:length(cdsshops)==1}">
                            <option value="${cdsshops[0].storesId}">${cdsshops[0].storesName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="" selected="true">请选择</option>
                            <c:forEach items="${cdsshops}" var="item">
                                <option value="${item.stores_id}">${item.name}</option>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>

            <div class="fitem">
                <label class="mytitle">打印机名称</label>
                <input name="print_remark" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label  class="mytitle">终端号(名称)</label>
                <input name="print_name" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">终端秘钥(IP)</label>
                <input name="print_address" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">打印机类型</label>
                <label><input type="radio" name="print_model" value="1" >&nbsp;云打印机</label>
                <label><input type="radio" name="print_model" value="2">&nbsp;网络打印机</label>
            </div>



            <input type="hidden" name="status" id="status" value="1"/>
            <input type="hidden" name="opt" id="opt" value="" />
            <input type="hidden" name="print_id" id="print_id" value="0"/>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
