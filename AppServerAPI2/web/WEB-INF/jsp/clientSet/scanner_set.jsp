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
    <title>扫描枪设置</title>
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
                url: '/ClientSet/GetScannerList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'cs_id',
                sortOrder: 'desc',
                idField: 'cs_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                title: '扫描枪设置',
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
                        handler: function () { editScanner(); }
                    },
                    {
                        text: '删除',
                        iconCls: 'icon-remove',
                        handler: function () { destroyScanner(); }
                    }
                ],
                columns: [[
                    { field: 'name', title: '店铺名称', width: 200, align: 'left' },
                    { field: 'sc_name', title: '扫描枪名称', width: 200, align: 'left' },
                    { field: 'cs_num', title: '打印枪编号', width: 120, align: 'center' },
                    {
                        field: 'cs_type', title: '打印枪类型', width: 100, align: 'center', formatter: function (val, row) {
                        if (val == 1) {
                            return '<span style="color:#345645">打包</span>';
                        } else {
                            return '<span style="color:#985498">出餐</span>';
                        }

                    }
                    },

                    { field: 'cs_remark', title: '备注', width: 250, align: 'left', nowrap: "true" },
                    {
                        field: 'status', title: '状态', width: 60, align: 'center', formatter: function (val, row) {
                        if (val == 1) {
                            return '<span onclick="toggle(' + row.cs_id + ')" style="cursor:pointer">启用</span>';
                        } else {
                            return '<span onclick="toggle(' + row.cs_id + ')" style="color:#9f9f9f;cursor:pointer">禁用</span>';
                        }
                    }
                    }
                ]]
            });



        });

        //添加扫描枪
        function add() {
            //$("#fm").form("clear");

            $("#dlg").dialog("open").dialog('setTitle', '扫描枪添加');
            $("[name='stores_id']").val("0");
            $("[name='sc_name']").val("");
            $("[name='cs_num']").val("");
            $("#cs_remark").textbox("setValue", "");
            $("input[type='radio'][name='cs_type'][value='2']").attr("checked", "checked");

            $("#opt").val("add");
        }


        function editScanner() {
            var row = $("#List").datagrid("getSelected");
            if (row) {
                $("#dlg").dialog("open").dialog('setTitle', '修改扫描枪');
                $("#fm").form("load", row);
                $("#opt").val("mod");
            }
        }


        //保存扫描枪信息
        function saveScenner() {
            $.ajax({
                url: "/ClientSet/saveScenner",
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
        function destroyScanner() {
            var row = $('#List').datagrid('getSelected');
            if (row) {
                $.messager.confirm('Confirm', '你确认删除此打印机吗?', function (r) {
                    if (r) {
                        $.ajax({
                            url: "/ClientSet/destroyScanner",
                            type: 'POST',
                            data: { cs_id: row.cs_id },
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
        function toggle(cs_id) {
            $.ajax({
                url: "/ClientSet/scnnerToggle",
                type: 'POST',
                data: { cs_id: cs_id },
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
                <label class="mytitle">扫描枪名称</label>
                <input name="sc_name" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">打印枪编号</label>
                <input name="cs_num" class="easyui-validatebox" required="true" />
            </div>

            <div class="fitem">
                <label class="mytitle">扫描枪类型</label>
                <label><input type="radio" name="cs_type" value="2">&nbsp;出餐</label>
                <label><input type="radio" name="cs_type" value="1">&nbsp;打包</label>
            </div>

            <div class="fitem printcf">
                <label class="mytitle">备注</label>
                <input class="easyui-textbox" data-options="multiline:true" value="123" style="width:385px;height:100px" id="cs_remark" name="cs_remark">
            </div>

            <input type="hidden" name="status" id="status" value="1" />
            <input type="hidden" name="opt" id="opt" value="" />
            <input type="hidden" name="cs_id" id="cs_id" value="0" />
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveScenner()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
