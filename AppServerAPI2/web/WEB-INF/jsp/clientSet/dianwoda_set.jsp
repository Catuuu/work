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
    <meta name="viewport" content="width=device-width"/>
    <title>骑手设置</title>
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
            //查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("List");
            });

            $('#List').datagrid({
                toolbar: '#selectForm',
                url: '/ClientSet/GetDianwodaList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                onDblClickRow: function (rowIndex, rowData) {
                    var rorowDataw = $("#List").datagrid("getSelected");
                    if (rowData) {
                        $("#dlg").dialog("open").dialog('setTitle', '修改骑手信息');
                        $("#fm").form("load", rowData);
                        $("#opt").val("mod");
                    }
                },
                sortName: 'opt_time',
                sortOrder: 'desc',
                idField: 'id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                title: '点我达过滤手机号码设置',
                nowrap: "true",
                columns: [[
                    { field: 'storesName', title: '店铺名称', width: 200, align: 'left' },
                    {field: 'name', title: '骑手姓名', width: 120, align: 'left'},
                    {field: 'phone', title: '电话', width: 120, align: 'left'},
                    {field: 'opt_time', title: '创建时间', width: 200, align: 'center'},
                    {field: 'opt', title: '操作',align: 'center',width: 120, formatter: function (val, row) {
                            var str = '<div><a class="cancelsend-btn" style="cursor: pointer" onclick="destroy(\'' + row.id + '\')" >删除</a></div>';
                        return str;
                        }
                    }
                ]]
            });


        });

        //添加
        function add() {
            //$("#fm").form("clear");

            $("#dlg").dialog("open").dialog('setTitle', '添加骑手信息');
            $("[name='id']").val("0");
            $("[name='name']").val("");
            $("[name='phone']").val("");
            $("#opt").val("add");
        }


        function edit() {
            var row = $("#List").datagrid("getSelected");
            if (row) {
                $("#dlg").dialog("open").dialog('setTitle', '修改骑手信息');
                $("#fm").form("load", row);
                $("#opt").val("mod");
            }
        }


        //保存骑手信息
        function save() {
            $.ajax({
                url: "/ClientSet/saveDianwoda",
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
                    } else if (result.status == "2") {
                        $.messager.alert("提示信息", "操作失败，此手机号已添加");
                    }
                    else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        //删除扫描枪
        function destroy(id) {
            $.messager.confirm('Confirm', '你确认删除此骑手吗吗?', function (r) {
                if (r) {
                    $.ajax({
                        url: "/ClientSet/destroyDianwoda",
                        data: {id: id},
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
    </script>

</head>
<body>
<div class="bodymain">
    <div id="selectForm" name="selectForm">
        <input class="easyui-textbox" name="keywords" style="width:280px"
               data-options="label:'关键词：',prompt:'输入流水号、姓名、电话、地址...'">

        <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
        <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>

        <a href="###" id="addBtn" class="easyui-linkbutton" iconCls="icon-add" onclick="add()">添加骑手</a>

    </div>
</div>


<div style="padding:5px;background:#fff;position:absolute;top:0px;left:0px;right:0px;bottom:0px;">
    <table id="List" style="width:100%;height:100%"></table>
</div>

<div id="dlg" class="easyui-dialog" style="width: 450px; height: 350px; padding: 10px 20px;"
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
            <label class="mytitle">骑手名称</label>
            <input name="name" class="easyui-validatebox" validType="maxLength['18']" required="true"/>
        </div>
        <div class="fitem">
            <label class="mytitle">骑手电话</label>
            <input name="phone" class="easyui-validatebox" validType="mobile" required="true"/>
        </div>


        <input type="hidden" name="id" id="id" value="0"/>
        <input type="hidden" name="opt" id="opt" value=""/>
    </form>
</div>

<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()" iconcls="icon-save">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')"
       iconcls="icon-cancel">取消</a>
</div>

</div>
</body>
</html>
