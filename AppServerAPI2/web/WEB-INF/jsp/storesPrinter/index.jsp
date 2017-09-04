<%--
  ERP商品管理
  User: zhuzhenghua
  Date: 17-05-28
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>厨房接单机管理</title>
    <style>
        li {
            line-height: 26px;
            font-size: 14px;
            color: #48576a;
            padding: 0 10px;
            cursor: pointer;
            position: relative;
            transition: border-color .3s, background-color .3s, color .3s;
            box-sizing: border-box;
            white-space: nowrap;
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
            width: 50%;
            height: 25px;
            float: left;
        }

        .fitem label {
            display: inline-block;
            width: 100px;
        }

        #fm div {
            width: 30%;
            float: left;
            margin-top: 10px;
        }

        li span {
            font-size: large;
            margin-left: 200px;
            line-height: 50px;
        }

        .mytitle {
            font-weight: lighter;
            font-weight: 700;
        }

        #bd2 {
            padding-top: 10%;
            padding-left: 30%;
        }

        #bd1 {
            padding-top: 10%;
            padding-left: 30%;
        }

        .datagrid-row {
            height: 42px;
            text-align: center;
        }

        .datagrid-header-row {
            height: 42px;
            font-weight: 700;
        }

        .panel-title {
            font-size: 12px;
            font-weight: bold;
            color: #777;
            height: 16px;
            line-height: 16px;
        }
    </style>
    <script type="text/javascript">

        function add() {
            $("#dlg2").dialog("open").dialog('setTitle', '添加厨房打印机');
            $("#m_stores_id").val(stores_id);
            $("#m_cm_name").textbox('setValue', '');
            $("#m_ct_user").textbox('setValue', '');
            $("#m_ct_password").textbox('setValue', '');
            $("#m_wash_group").textbox('setValue', '');
            $("#m_jardiniere_group").textbox('setValue', '');
            $("#m_cook_group").textbox('setValue', '');
            $("#m_ct_num").textbox('setValue', '');
            $("#m_cm_id").val('');
            $("#opt").val("add");
        }



        var stores_id;
        $(function () {
            $('#pbList').datagrid({
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'es_id',
                //sortOrder: 'desc',
                idField: 'es_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                pageSize: 10,
//                toolbar: '#selectForm2',
                pagination: false,
                nowrap: "true",
                columns: [[
                    {field: 'es_id', title: '平板ID', width: '10%', align: 'center', hidden: true},
                    {field: 'ct_name', title: '平板名称', width: '10%', align: 'center', sortable: true},
                    {field: 'ct_user', title: '账户', width: '30%', align: 'center'},
                    {field: 'ct_password', title: '密码', width: '30%', align: 'center'},
                    {
                        field: 'status', title: '状态', width: '10%', align: 'center', formatter: function (value, row) {
                        if (value == "1") {
                            return '<select style="cursor:pointer;color:green" onchange="setStatus(' + row.es_id + ',2)"><option value="1" selected="true">启用</option> <option value="2">禁用</option></select>'
                        } else {
                            return '<select style="cursor:pointer;color:red" onchange="setStatus(' + row.es_id + ',1)"><option value="1" >启用</option> <option value="2" selected="true">禁用</option></select>'
                        }
                    }
                    },
                ]]
            });

            $("#printAdd").click(function () {
                add();
            });

            $('#List').datagrid({
//                toolbar: '#selectForm',
//                url: '/StoresPrinter/getChuFanMeal?stores_id='+stores_id,
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'cm_id',
                //sortOrder: 'desc',
                idField: 'cm_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                title: '',
                pageSize: 10,
                pagination: false,
                nowrap: "true",
                onDblClickRow:function (rowIndex,rowData) {
                    $("#dlg2").dialog("open").dialog('setTitle', '修改打包客户端配置');
                    $("#fm2").form("load", rowData);
                    $("#opt").val("mod");
                },
                onClickCell:function(rowIndex, field, value){
                    if(field!='status'){
                        $('#List').datagrid("selectRow",rowIndex);
                        var rowData = $('#List').datagrid("getSelected");
                        $("#stores_id").val(rowData.stores_id);
                        $("#cm_id").val(rowData.cm_id);
                        $('#List2').datagrid({url: '/StoresPrinter/getChuFanGoods?stores_id=' + rowData.stores_id + '&cm_id=' + rowData.cm_id});
                    }
                },
                columns: [[
                    {field: 'cm_id', title: '接单机ID', width: '10%', align: 'center', hidden: true},
                    {field: 'stores_id', title: '接单机ID', width: '5%', align: 'center', hidden: true},
                    {field: 'cm_name', title: '接单机名称', width: '30%', align: 'center'},
                    {field: 'ct_user', title: '接单机账户', width: '20%', align: 'center', sortable: true},
                    {field: 'wash_group', title: '洗菜组', width: '10%', align: 'center'},
                    {field: 'jardiniere_group', title: '配菜组', width: '10%', align: 'center'},
                    {field: 'cook_group', title: '厨师', width: '5%', align: 'center'},
                    {
                        field: 'status', title: '状态', width: '10%', align: 'center', formatter: function (value, row) {
                        if (value == "1") {
                            return '<select style="cursor:pointer;color:green" onchange="toggle(' + row.cm_id + ',2)"><option value="1" selected="true">启用</option> <option value="2">禁用</option></select>'
                        } else {
                            return '<select style="cursor:pointer;color:red" onchange="toggle(' + row.cm_id + ',1)"><option value="1" >启用</option> <option value="2" selected="true">禁用</option></select>'
                        }
                    }
                    },
                    {
                        field: 'cz',
                        title: '操作',
                        width: '10%',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<span onclick=addGood(' + row.cm_id + ') style="color: green;cursor:pointer"  >添加菜品</span>'
                        }
                    }
                ]]
            });


            $('#List2').datagrid({
//                toolbar: '#selectForm',
//                url: '/StoresPrinter/getChuFanGoods?stores_id='+stores_id,
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'cmg_id',
                //sortOrder: 'desc',
                idField: 'cmg_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                pageSize: 10,
                toolbar: '#selectForm2',
//                pagination:false,
                nowrap: "true",
                columns: [[
                    {field: 'cmg_id', title: '接单机ID', width: '10%', align: 'center', hidden: true},
                    {field: 'good_id', title: '菜品Id', width: '10%', align: 'center'},
                    {field: 'good_num', title: '菜品编号', width: '10%', align: 'center'},
                    {field: 'good_name', title: '菜品名称', width: '30%', align: 'center', sortable: true},
                    {field: 'good_key', title: '菜品简介', width: '30%', align: 'center'},
                    {field: 'cm_name', title: '所属接单机', width: '10%', align: 'center'},
                    {
                        field: 'cz',
                        title: '操作',
                        width: '10%',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<span onclick=deleteGood(' + row.cmg_id + ') style="color: red;cursor:pointer"  >删除</span>'
                        }
                    }
                ]]
            });


            $('#List3').datagrid({
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'good_id',
                idField: 'good_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                pageSize: 10,
                toolbar: '#selectForm3',
//                pagination:false,
                nowrap: "true",
                onClickRow: function (rowIndex, rowData) {
                    var cm_id = $("#cm_id3").val();
                    $.ajax({
                        url: "/StoresPrinter/addErpGood",
                        type: 'POST',
                        data: {good_id: rowData.good_id, cm_id: cm_id},
                        success: function (result) {
                            if (result.status == "1") {
                                $.messager.show({
                                    title: '提示信息',
                                    msg: '操作成功',
                                    showType: 'show'
                                });
                                $('#List3').datagrid({url: '/StoresPrinter/getErpGoods?stores_id=' + stores_id});
                                $('#List2').datagrid({url: '/StoresPrinter/getChuFanGoods?stores_id=' + stores_id + '&cm_id=' + cm_id});
                            } else {
                                $.messager.alert("提示信息", "操作失败");
                            }
                        }
                    });
                },
                columns: [[
                    {field: 'good_id', title: '菜品Id', width: '20%', align: 'center'},
                    {field: 'good_num', title: '菜品编号', width: '20%', align: 'center'},
                    {field: 'good_name', title: '菜品名称', width: '30%', align: 'center', sortable: true},
                    {field: 'good_key', title: '菜品简介', width: '30%', align: 'center'}
                ]]
            });

            $("#findBtn").click(function () {
                $("#selectForm2").serializeSelectJson("List2");
            });

            $("#refreshBtn").click(function () {
                $("#good_num").textbox('setValue', '');
                $("#good_name").textbox('setValue', '');
                $("#selectForm2").serializeSelectJson("List2");
            });

            $("#findBtn3").click(function () {
                $("#selectForm3").serializeSelectJson("List3");
            });

            $("#refreshBtn3").click(function () {
                $("#good_num3").textbox('setValue', '');
                $("#good_name3").textbox('setValue', '');
                $("#selectForm3").serializeSelectJson("List3");
            });




        });


        function getPrint(id, name) {
            stores_id = id;
            $("#s_stores_id").val(id);
            $("#es_id").val('');
            $("#ct_name").textbox('setValue', '');
            $("#ct_user").textbox('setValue', '');
            $("#print_ip").textbox('setValue', '');
            $("#ct_password").textbox('setValue', '');
            $.ajax({
                url: "/StoresPrinter/getChuFanTask",
                type: 'POST',
                data: {stores_id: stores_id},
                success: function (result) {
                    if (result.status == "1") {
                        var obj = result.obj;
                        $("#es_id").val(obj.es_id);
//                        $("#ct_name").val(obj.ct_name);
                        $("#ct_name").textbox('setValue', obj.ct_name);
                        $("#ct_user").textbox('setValue', obj.ct_user);
                        $("#print_ip").textbox('setValue', obj.print_ip);
                        $("#ct_password").textbox('setValue', obj.ct_password);
                        $("#status").combobox('setValue', obj.status);
                    }
                }
            });
            $("#cf_name").text(name);
            $("#chuFang li").each(function () {
                $(this).css("backgroundColor", "");
            });
            $("a [name='" + stores_id + "']").css("backgroundColor", "#C0C0C0");

            $('#List').datagrid({url: '/StoresPrinter/getChuFanMeal?stores_id=' + id});
            $('#List2').datagrid({url: '/StoresPrinter/getChuFanGoods?stores_id=' + id});
        }

        function deleteGood(cmg_id) {
            alert(cmg_id);
        }


        function setStatus(es_id, status) {
            $.ajax({
                url: "/StoresPrinter/setStatus",
                type: 'POST',
                data: {es_id: es_id, status: status},
                success: function (result) {
                    if (result.status == "1") {
                        $('#pbList').datagrid({url: '/StoresPrinter/getChuFanTask?stores_id=' + stores_id});
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }


        function toggle(cm_id, status) {
            $.ajax({
                url: "/StoresPrinter/toggle",
                type: 'POST',
                data: {cm_id: cm_id, status: status},
                success: function (result) {
                    if (result.status == "1") {
                        $('#List').datagrid({url: '/StoresPrinter/getChuFanMeal?stores_id=' + stores_id});
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        function deleteGood(cmg_id) {
            $.ajax({
                url: "/StoresPrinter/deleteGood",
                type: 'POST',
                data: {cmg_id: cmg_id},
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.show({
                            title: '提示信息',
                            msg: '操作成功',
                            showType: 'show'
                        });
                        $("#selectForm2").serializeSelectJson("List2");
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        function addGood(cm_id) {
            $("#stores_id3").val(stores_id);
            $("#cm_id3").val(cm_id);
            $('#List3').datagrid({url: '/StoresPrinter/getErpGoods?stores_id=' + stores_id});
            $("#dlg").dialog("open").dialog('setTitle', '添加菜品');
        }

        function addTask() {
            var stores_id = $("#s_stores_id").val();
            if (stores_id == null || stores_id == "" || stores_id == undefined) {
                $.messager.show({
                    title: '提示信息',
                    msg: '请先选择厨房',
                    showType: 'show'
                });
                return false;
            }
            $.ajax({
                url: "/StoresPrinter/addTask",
                data: $("#fm").serialize(),
                type: 'POST',
                beforeSend: function () {
                    return $("#fm").form("validate");
                },
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.show({
                            title: '提示信息',
                            msg: '操作成功',
                            showType: 'show'
                        });
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        function addMeal() {
            var stores_id = $("#m_stores_id").val();
            if (stores_id == null || stores_id == "" || stores_id == undefined) {
                $.messager.show({
                    title: '提示信息',
                    msg: '请先选择厨房',
                    showType: 'show'
                });
                return false;
            }
            $.ajax({
                url: "/StoresPrinter/addMeal",
                data: $("#fm2").serialize(),
                type: 'POST',
                beforeSend: function () {
                    return $("#fm2").form("validate");
                },
                success: function (result) {
                    if (result.status == "1") {
                        $('#dlg2').dialog('close');
                        $.messager.show({
                            title: '提示信息',
                            msg: '操作成功',
                            showType: 'show'
                        });
                        $('#List').datagrid({url: '/StoresPrinter/getChuFanMeal?stores_id=' + stores_id});
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }
    </script>
</head>
<body>
<div style="height:25px; background-color:#ffffff">
    <h2 style="background-color:#ffffff;border-width:0 0 1px 0;font-size: 12px;font-weight: bold;padding-top: 5px;margin-left: 5px;">
        厨房打印机管理</h2>
</div>
<div class="easyui-layout" style="width:100%;height:100%;">
    <!--  =========================所有商铺=====================   -->
    <div data-options="region:'west',title:'厨房',split:true" style="width:10%;height: 100%" id="chuFang">
        <c:forEach var="item" items="${storeslist}">
            <a href="javascript:void(0)" onclick="getPrint(${item.stores_id},'${item.name}')">
                <li class="mdss" name="${item.stores_id}">${item.name}</li>
            </a>
        </c:forEach>
    </div>
    <div data-options="region:'center',title:'',iconCls:'icon-ok'" style="width: 90%;height: 100%">
        <div data-options="region:'north',split:true" style="height:9%;width: 100%;padding: 10px;">
            <%--<table id="pbList"></table>--%>
            <form id="fm" method="post">
                <input id="es_id" name="es_id" type="hidden">
                <input id="s_stores_id" name="stores_id" type="hidden">
                <div>
                    <label class="mytitle">所属厨房:</label>
                    <span id="cf_name"></span>
                </div>
                <div>
                    <label class="mytitle">账户:</label>
                    <input id="ct_user" name="ct_user" class="easyui-validatebox easyui-textbox" required="true"
                           style="width:150px"/>
                </div>
                <div>
                    <label class="mytitle">状态:</label>
                    <select style="cursor:pointer;color:green;width: 150px;height: 28px;margin-left: 10px" name="status">
                        <option value="1">启用</option>
                        <option value="2">禁用</option>
                    </select>
                </div>
                <div>
                    <label class="mytitle">平板名称:</label>
                    <input id="ct_name" name="ct_name" class="easyui-validatebox easyui-textbox" required="true"
                           style="width:150px"/>
                </div>
                <div>
                    <label class="mytitle">密码:</label>
                    <input type="password" name="ct_password" id="ct_password" class="easyui-validatebox easyui-textbox" required="true"
                           style="width:150px"/>
                </div>
                <div>
                    <label class="mytitle">ip地址:</label>
                    <input name="print_ip" id="print_ip" class="easyui-textbox" style="width:150px"/>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="###" id="bc" class="easyui-linkbutton " iconCls="icon-save" onclick=addTask()>保存</a>
                </div>

            </form>

        </div>
        <div data-options="region:'center',title:'',split:true" style="height:25%;width: 100%;border-top:1px solid #c3c3c3;">
            <div style="height: 40px;border-bottom: 1px solid #c3c3c3">
                <div style="float: left;font-weight: 700;margin: 10px 5px 0px 10px;">厨房任务打印机</div>
                <div style="float: right;margin: 8px 10px 0px 10px;">
                    <table cellspacing="0" cellpadding="0">
                        <tbody>
                        <tr>
                            <td><a href="javascript:;" class="l-btn l-btn-small l-btn-plain" group="" id="printAdd"><span
                                    class="l-btn-left l-btn-icon-left"><span class="l-btn-text">添加</span><span
                                    class="l-btn-icon icon-add">&nbsp;</span></span></a></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <table id="List"></table>
        </div>
        <div data-options="region:'south',title:'',split:true" style="height:62%;width: 100%">
            <table id="List2"></table>
        </div>
        <%--<div data-options="region:'east',title:'所选接单菜品',split:true" style="height: 90%;width:30%"></div>--%>
    </div>
</div>
<!-- ==============查询窗口1============ -->
<div id="selectForm2" name="selectForm2" style="height: 35px">
    <input id="stores_id" name="stores_id" type="hidden">
    <input id="cm_id" name="cm_id" type="hidden">
    <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
        <div style="float: left;padding-left: 5px">
            <label class="mytitle">商品编号:</label>
            <input id="good_num" name="good_num" class="easyui-textbox" style="width:120px;">
            <input class="easyui-textbox" name="good_name" id="good_name" style="width:200px"
                   data-options="label:'商品名称：',prompt:'商品名称模糊查询'">
            <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
            <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
        </div>
    </div>
</div>

<!-- ==============添加窗口============ -->
<div id="dlg2" class="easyui-dialog" style="width: 50%; height: 40%; padding: 10px 20px;" closed="true"
     buttons="#dlg-buttons">
    <form id="fm2" method="post">
        <input id="m_stores_id" name="stores_id" type="hidden">
        <input id="m_cm_id" name="cm_id" type="hidden">
        <input name="opt" type="hidden" id="opt">
        <div class="fitem">
            <label class="mytitle">打印机名称:</label>
            <input id="m_cm_name" name="cm_name" class="easyui-validatebox easyui-textbox" required="true"
                   style="width:150px"/>
        </div>
        <div class="fitem">
            <label class="mytitle">帐户(IP地址):</label>
            <input id="m_ct_user" name="ct_user" class=" easyui-textbox" style="width:150px"/>
        </div>
        <%--<div class="fitem">--%>
            <%--<label class="mytitle">密码:</label>--%>
            <%--<input id="m_ct_password" name="ct_password" class="easyui-validatebox easyui-textbox" required="true"--%>
                   <%--style="width:150px"/>--%>
        <%--</div>--%>
        <div class="fitem">
            <label class="mytitle">编号:</label>
            <input id="m_ct_num" name="ct_num" class=" easyui-textbox" style="width:150px"/>
        </div>
        <div class="fitem">
            <label class="mytitle">洗菜组:</label>
            <input name="wash_group" class=" easyui-textbox" id="m_wash_group" style="width:150px"/>
        </div>
        <div class="fitem">
            <label class="mytitle">配菜师:</label>
            <input id="m_jardiniere_group" name="jardiniere_group" class=" easyui-textbox" style="width:150px"/>
        </div>
        <div class="fitem">
            <label class="mytitle">厨师:</label>
            <input id="m_cook_group" name="cook_group" class="easyui-textbox" style="width:150px"/>
        </div>
        <div class="fitem">
            <label class="mytitle">状态:</label>
            <select style="cursor:pointer;color:green;width: 150px;height: 25px"
                    onchange="setStatus('+row.es_id+',value)" id="m_status" name="status">
                <option value="1">启用</option>
                <option value="2">禁用</option>
            </select>
        </div>
    </form>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addMeal()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg2').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>
</div>
<!--   ==============================所有菜品列表===============================     -->
<div id="dlg" class="easyui-dialog" style="width: 60%; height: 70%; padding: 10px 20px;" closed="true">
    <table id="List3"></table>
    <!-- ==============查询窗口2============ -->
    <div id="selectForm3" name="selectForm2" style="height: 35px">
        <input id="stores_id3" name="stores_id" type="hidden">
        <input id="cm_id3" name="cm_id3" type="hidden">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
            <div style="float: left;padding-left: 5px">
                <label class="mytitle">商品编号:</label>
                <input id="good_num3" name="good_num" class="easyui-textbox" style="width:120px;">
                <input class="easyui-textbox" name="good_name" id="good_name3" style="width:200px"
                       data-options="label:'商品名称：',prompt:'商品名称模糊查询'">
                <a href="###" id="findBtn3" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
                <a href="###" id="refreshBtn3" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
