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
    <title>打包客户端配置管理</title>
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
                url: '/Stores/stores_login_grid',
                //width: $(window).width(),
                methord: 'post',
                //height: $(window).height(),

                sortName: 'id',
                //sortOrder: 'desc',
                idField: 'Id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                title: '打包客户端配置管理',
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
                    { field: 'stores_id', title: '商铺名称', width: '20%', align: 'left',formatter:function (value,row) {

                        <c:choose>
                        <c:when test="${fn:length(cdsshops)==1}">
                        return "${cdsshops[0].storesName}";
                        </c:when>
                        <c:otherwise>
                        <c:forEach var="item" items="${cdsshops}">
                        if(row.stores_id=="${item.storesId}"){
                            return "${item.storesName}";
                        }
                        </c:forEach>
                        </c:otherwise>
                        </c:choose>
                    } },
                    { field: 'username', title: '登陆账号', width: '20%', align: 'left' },
                    { field: 'version_no', title: '版本号', width: '20%', align: 'center' },
                    { field: 'login_time', title: '登陆时间', width: '20%', align: 'center'},
                    { field: 'print_format_id', title: '商品打印格式', width: '20%', align: 'center',formatter:function (value,row) {
                        if(value=="1"){
                            return "菜品汇总";
                        }else{
                            return "原菜品";
                        }
                    } }
                ]]
            });

            //查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("List");
            });

            $("#refreshBtn").click(function () {
                window.location.reload();
            });

        });



        //添加打印机
        function add() {
            //$("#fm").form("clear");
            //$("[name='login_time']").datebox('setValue', formatterDate(new Date()));
            $("#dlg").dialog("open").dialog('setTitle', '添加打包客户端配置');
            $("form [name='username']").val("");
            $("form [name='stores_id']").val("");
            $("[name='pass']").val("");
            //$("[name='version_no']").val("");
            $("[name='login_time']").val("");
            $("#opt").val("add");
           // $(".printcf").hide();
        }


        function editprint() {
            var row = $("#List").datagrid("getSelected");
            if(row==null){
                $.messager.alert("提示信息", "请选择一条记录");
            }
            if (row) {
                $("#dlg").dialog("open").dialog('setTitle', '修改打包客户端配置');
                $("#fm").form("load", row);
                $("[name='pass']").val("");
                $("#opt").val("mod");
            }
        }


        //保存打印机信息
        function saveprint() {
            //var version_no = $("input[name='version_no']").val();
            var id = $("input[name='id']").val();
            var username = $("form [name='username']").val();
            var pass = $("input[name='pass']").val();
            var stores_id = $("form [name='stores_id']").val();
            if(!stores_id)stores_id = '${cdsshops[0].storesId}'
            //var login_time = $("input[name='login_time']").val();
            var opt = $("input[name='opt']").val();
            var print_format_id = $("#print_format_id").val();
            $.ajax({
                url: "/Stores/saveStoresLogin",
                data: {id:id,stores_id:stores_id,username:username,pass:pass,opt:opt,print_format_id:print_format_id},
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




    </script>

</head>
<body>
<div class="bodymain">
    <div id="selectForm" name="selectForm" style="height: 5%">
    <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
        <div style="float: left;padding: 5px">
            <a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton " iconCls="icon-add">添加</a>
            <a href="javascript:void(0)" onclick="editprint()" class="easyui-linkbutton " iconCls="icon-edit">修改</a>
        </div>
        <div style="float: left;padding-left: 500px">
            <input class="easyui-textbox" name="username" style="width:180px"
                   data-options="label:'登陆账号：',prompt:'账号模糊查询'">
            <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
            <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
        </div>
    </div>
    </div>
    <div style="height: 100%;width: 100%">
        <table id="List" style="width:100%;height:100%" toolbar="#selectForm"></table>
    </div>
    <div id="dlg" class="easyui-dialog" style="width: 550px; height: 300px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem">
                <label class="mytitle">商铺</label>
                <select class="easyui-combobox easyui-validatebox"  name="stores_id" style="width:160px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <c:choose>
                        <c:when test="${fn:length(cdsshops)==1}">
                            <option value="${cdsshops[0].storesId}">${cdsshops[0].storesName}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="" selected="true">请选择</option>
                            <c:forEach items="${cdsshops}" var="item">
                                <option value="${item.storesId}">${item.storesName}</option>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div class="fitem">
                <label  class="mytitle">登陆账号:</label>
                <input name="username" class="easyui-validatebox" required="true" />
            </div>
            <div class="fitem">
                <label class="mytitle">登陆密码:</label>
                <input name="pass"class="easyui-validatebox"  required="true" />
            </div>
            <div class="fitem">
                <label  class="mytitle">打印格式:</label>
                <select class="easyui-combobox" id="print_format_id" name="print_format_id" style="width: 100px"
                        data-options="editable:false,panelHeight:'auto'">
                        <option value="1" selected="selected">菜品汇总</option>
                        <option value="2" >原菜品</option>
                </select>
            </div>
            <!--<div class="fitem">
                <label class="mytitle">版本号:</label>
                <input name="version_no" class="easyui-validatebox" />
            </div>
            <div class="fitem">
                <label class="mytitle">登陆时间:</label>
                <input name="login_time" class="easyui-datebox"  />
            </div>-->
            <input type="hidden" name="opt" id="opt" value="" />
            <input type="hidden" name="id" id="id" value="0"/>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
