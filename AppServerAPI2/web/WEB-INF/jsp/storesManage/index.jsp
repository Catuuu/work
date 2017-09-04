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
            width: 50%;
            float: left;

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
//                toolbar: '#selectForm',
                url: '/StoresManage/getStoresList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'a.stores_id',
                //sortOrder: 'desc',
                idField: 'stores_brand_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                title: '店铺管理',
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
                    { field: 'stores_brand_id', title: 'ID', width: '40%', align: 'center',hidden:true },
                    { field: 'stores_id', title: '商品ID', width: '40%', align: 'center',hidden:true },
                    { field: 'stores_name', title: '商铺名称', width: '10%', align: 'center'},
                    { field: 'brand_name', title: '品牌名称', width: '10%', align: 'center',sortable:true},
                    { field: 'intro', title: '店铺简介', width: '10%', align: 'center' },
                    { field: 'linkman', title: '店长电话', width: '10%', align: 'center' },
                    { field: 'start_time', title: '营业开始时间', width: '10%', align: 'center'},
                    { field: 'end_time', title: '营业结束时间', width: '10%', align: 'center'},
                    { field: 'address', title: '店铺地址', width: '10%', align: 'center'},
                    { field: 'elem_restaurant_id', title: '饿了么绑定', width: '10%', align: 'center'},
                    { field: 'meituan_restaurant_id', title: '美团绑定', width: '10%', align: 'center'},
                    { field: '_parentId', width: '10%', align: 'center'}
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
            //var login_time = $("input[name='login_time']").val();
            var opt = $("input[name='opt']").val();
            $.ajax({
                url: "/Stores/saveStoresLogin",
                data: {id:id,stores_id:stores_id,username:username,pass:pass,opt:opt},
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


        //省市县三级联动
        $("#province").combobox({
            onChange: function (n, o) {
                $.ajax({
                    url: "/Stores/getCityStores",
                    data: {city: n},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            var objs = result.obj;
                            $("#shop").html("");
                            for (var i = 0; i < objs.length; i++){
                                var name = objs[i].name;
                                var id = objs[i].stores_id;
                                var country = objs[i].country;
                                $("#shop").append(' <a href="###" onclick=tzbg('+id+','+country+') > <li class="mdss" name='+id+'>'+name+'</li></a>');
                            }
                        } else {

                        }
                    }
                });

            }
        });




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
            <label class="mytitle">商铺:</label>
            <select class="easyui-combobox easyui-validatebox" id="stores_id" name="stores_id" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'" required="true">
                <option value="" selected="true">请选择</option>
                <c:forEach var="item" items="${storeslist}">
                    <option value="${item.stores_id}">${item.name}</option>
                </c:forEach>
            </select>
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

    <!--   ============================== 新增erp div、表单===============================     -->
    <div id="dlg" class="easyui-dialog" style="width: 60%; height: 80%; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
                <div class="fitem">
                    <label class="mytitle">店铺名称</label>
                    <input name="name" class="easyui-validatebox easyui-textbox"  required="true"  style="width:180px"/>
                </div>
                <div class="fitem">
                    <label class="mytitle">店长电话</label>
                    <input name="linkman" class="easyui-validatebox easyui-textbox"  required="true"  style="width:150px"/>
                </div>

                <div class="fitem">
                    <label class="mytitle">营业开始时间</label>
                    <input name="start_time" class="easyui-datetimebox" name="birthday" data-options="required:true,showSeconds:false"/>
                </div>
                <div class="fitem">
                    <label class="mytitle">营业结束时间</label>
                    <input name="end_time" class="easyui-datetimebox" name="birthday" data-options="required:true,showSeconds:false"/>
                </div>

                <div class="fitem">
                    <label class="mytitle">省</label>
                    <input id="province" class="easyui-combobox" name="province"
                           data-options="valueField:'id',textField:'text',url:'/StoresManage/getProvince'">
                </div>
                <div class="fitem">
                    <label class="mytitle">市</label>
                    <input id="city" class="easyui-combobox" name="city"
                           data-options="valueField:'id',textField:'text',url:'/StoresManage/getCity'">
                </div>
                <div class="fitem">
                    <label class="mytitle">区</label>
                    <input id="country" class="easyui-combobox" name="country"
                           data-options="valueField:'id',textField:'text',url:'/StoresManage/getCity'">
                </div>

                <div class="fitem">
                    <table>
                        <tr>
                            <th><label class="mytitle">状态</label></th>
                            <td style="line-height: 20px;padding-left: 0px;text-align: center;"><input name="status" type="radio" value="0" onclick=sfbc('b','0') /><span onclick=sfbc('b','0') style="cursor:pointer;">下架</span></td>
                            <td style="line-height: 20px;padding-left: 40px;text-align: center;"><input name="status" type="radio" checked="true" value="1" onclick=sfbc('b','1') /><span onclick=sfbc('b','1') style="cursor:pointer;">上架</span></td>
                        </tr>
                    </table>

                </div>
                <%--<div class="fitem" style="width: 100%;height: 70px;">--%>
                <%--<label class="mytitle">商品详情</label>--%>
                <%--<textarea cols=140 rows=5 name=introduce class="textbox"></textarea>--%>
                <%--</div>--%>
                <div class="fitem" style="width: 100%;height: 70px;">
                    <label class="mytitle">店铺简介</label>
                    <textarea cols=140 rows=5 name=good_info class="textbox"></textarea>
                </div>
                <div style="width: 50%;float: left">
                    <label class="mytitle">美团商品图标</label>
                    <img src="/static/images/uploadbg.png" height="200" width="300" alt="暂无图片" id="mt" onclick="getPic('1')"/>
                </div>
                <div style="width: 50%;float: left">
                    <label class="mytitle">饿了么商品图标</label>
                    <img src="/static/images/uploadbg.png" height="200" width="300" alt="暂无图片" id="ele" onclick="getPic('2')"/>
                </div>
                <%--<input type="hidden" name="vip_discounts" id="vip_discounts" value="0"/>--%>
                <input type="hidden" name="status" id="status3" value="1"/>
                <input type="hidden" name="image_hash"  value=""/>
                <input type="hidden" name="good_pic" id="good_pic" value="1"/>
                <input type="hidden" name="egood_pic" id="egood_pic" value="1"/>
                <input type="hidden" name="good_id" id="good_id" value="1"/>

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
