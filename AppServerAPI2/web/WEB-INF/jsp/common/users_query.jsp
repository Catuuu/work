<%--
  users表查询
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
    <title>员工查询</title>
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
            font-weight: 700;
        }
        input{
            height:20px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $('#List').datagrid({
                toolbar: '#selectForm',
                url: '/MsGoods/getUserList',
                //width: $(window).width(),
                methord: 'post',
                //height: $(window).height(),
                sortName: 'id',
                //sortOrder: 'desc',
                idField: 'Id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
               // title: '打包客户端配置管理',
                nowrap: "true",
                onClickRow: function (rowIndex,rowData) {
                    getUser(rowData.id,rowData.user_nicename);
                },
                columns: [[
                    { field: 'id', title: '员工id', width: '8%', align: 'center' },
                    { field: 'user_login', title: '用户名', width: '8%', align: 'center' },
                    { field: 'user_nicename', title: '用户昵称', width: '8%', align: 'center' },
                    { field: 'sex', title: '用户性别', width: '8%', align: 'center',formatter:function (value,row){
                            var time = row.login_time;
                            if(value==1){
                                return "男";
                            }else {
                                return "女";
                            }
                    }},
                    { field: 'birthday', title: '生日', width: '8%', align: 'center' },
                    { field: 'last_login_ip', title: '最后登录ip', width: '8%', align: 'center' },
                    { field: 'last_login_time', title: '最后登录时间', width: '13%', align: 'center' },
                    { field: 'create_time', title: '注册时间', width: '12%', align: 'center' },
                    { field: 'user_status', title: '用户状态', width: '8%', align: 'center', formatter:function (value,row){
                    console.log(row);
                        var time = row.login_time;
                        if(value==0){
                            return "禁用";
                        }else {
                            return "正常";
                        }
                    }},
                    { field: 'mobile', title: '手机号', width: '8%', align: 'center' },
                    { field: 'callid', title: '来电号码', width: '8%', align: 'center' }
                    <%--{ field: 'stores_id', title: '所属商铺', width: '8%', align: 'center',formatter:function (row,value) {--%>
                        <%--<c:forEach var="item" items="${storeslist}">--%>
                        <%--if (row.stores_id == "${item.stores_id}") {--%>
                            <%--return "${item.name}";--%>
                        <%--}--%>
                        <%--</c:forEach>--%>
                    <%--} }--%>
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

        function getUser(id,name) {
            var status = ${requestScope.status};
            $.messager.confirm('提示信息', '是否选择该用户？', function (r) {
                if (r) {
                    window.parent.fuzhi(id,name,status);
                    $.closeWin();
                }
            });

        }


    </script>

</head>
<body>
<div class="bodymain">
    <div id="selectForm" name="selectForm" style="height: 40px">
    <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
        <div>
            <label class="mytitle">所属商铺:</label>
            <select class="easyui-combobox easyui-validatebox" id="stores_id" name="stores_id" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'" required="true">
                <option value="" selected="true">全部</option>
                <c:forEach var="item" items="${storeslist}">
                    <option value="${item.stores_id}">${item.name}</option>
                </c:forEach>
            </select>
            <input class="easyui-textbox" name="user_login" style="width:250px"
                   data-options="label:'登陆账号：',prompt:'账号模糊查询'">
            <input class="easyui-textbox" name="user_nicename" style="width:250px"
                   data-options="label:'用户昵称：',prompt:'昵称模糊查询'">
            <label class="mytitle">性别:</label>
            <select class="easyui-combobox easyui-validatebox" id="sex" name="sex" style="width:80px;"
                    data-options="editable:false,panelHeight:'auto'" required="true">
                <option value="" selected="true">全部</option>
                <option value="1">男</option>
                <option value="2">女</option>
            </select>
            <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
            <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
        </div>
    </div>
    </div>
    <div style="height: 100%;width: 100%">
        <table id="List" style="width:100%;height:100%" toolbar="#selectForm"></table>
    </div>
</div>
</body>
</html>
