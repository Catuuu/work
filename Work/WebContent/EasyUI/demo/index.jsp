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
    <meta name="viewport" content="width=device-width" />
    <title>ERP商品管理</title>
    <style>
            li{
                line-height: 26px;
                font-size: 14px;
                color: #48576a;
                padding: 0 10px;
                cursor: pointer;
                position: relative;
                transition: border-color .3s,background-color .3s,color .3s;
                box-sizing: border-box;
                white-space: nowrap;
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
                height: 25px;
                float: left;
            }
            .fitem label
            {
                display: inline-block;
                width: 100px;
            }

            .mytitle {
                font-weight:lighter;
                font-weight: 700;
            }
            #bd2{
                padding-top:10%;
                padding-left:30%;
            }
            #bd1{
                padding-top:10%;
                padding-left:30%;
            }
            .datagrid-row {
                height: 42px;
                text-align:center;
            }

            .datagrid-header-row {
                height: 42px;
                font-weight:700;
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
        var apiPath = 'http://114.55.97.90:8018/';
        //var apiPath = 'http://192.168.1.56/';
        var selectedMaterial = [];          //单个商品对应的原料
        var allMaterial = [];   //所有商品的原料
        $(function () {
            $('#List').datagrid({
                url: '/MsGoods/GetErpGoodsLists',
                toolbar: '#selectForm',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'good_id',
                idField: 'good_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: '门店品牌明细管理',
                nowrap: "true",
                queryParams:{
                    status:$("#status").val()
                },
                onClickRow: function (rowIndex,rowData) {
                    erpgoods(rowData.good_id,rowData.good_name);
                },
                columns:[[
                    {field:'good_id',title:'ID',width:'5%',align: 'center'},
                    {field:'good_name',title:'商品名称',width:'35%',align: 'center',sortable:true},
                    {field:'class_id',title:'商品类别',width:'20%',sortable:true,align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${classtypelist}">
                        if(row.class_id=="${item.class_id}"){
                            return "${item.class_name}";
                        }
                        </c:forEach>
                    }},
                    {field:'good_num',title:'商品编码',width:'10%',align: 'center',sortable:true},
                    {field:'status',title:'上/下架',width:'10%',align: 'center',sortable:true,formatter:function(value,row){
                        if (value == 1) {
                            return '<span onclick="toggle('+row.good_id+',2)" style="color:green;cursor:pointer">是</span>';
                        } else {
                            return '<span onclick="toggle('+row.good_id+',2)" style="color:red;cursor:pointer">否</span>';
                        }
                    }
                    },
                    {field:'isback',title:'备餐',width:'10%',align: 'center',formatter:function(value,row){
                        if (value == 1) {
                            return '<span onclick="toggle('+row.good_id+',1)" style="color:green;cursor:pointer">是</span>';
                        } else {
                            return '<span onclick="toggle('+row.good_id+',1)" style="color:red;cursor:pointer">否</span>';
                        }
                    }},
                    {field:'counts',title:'材料数量',width:'10%',align: 'center',sortable:true,formatter:function(value,row){
                        elestoresBindQuery(row.good_id);
                        return '<span name="'+row.good_id+'"></span>';
                    }}
                 ]]
            });
            //查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("List");
            });

            $("#refreshBtn").click(function () {
                $(':input','#selectForm').not(':button,:submit,:reset') .val('');
                $(':input','#selectForm').not(':button,:submit,:reset') .removeAttr('checked');
                $("#selectForm").serializeSelectJson("List");
            });

            //查询
            $("#findBtn2").click(function () {
                $("#selectForm2").serializeSelectJson("List3");
            });

            $("#refreshBtn2").click(function () {
                $(':input','#selectForm2').not(':button,:submit,:reset') .val('');
                $(':input','#selectForm2').not(':button,:submit,:reset') .removeAttr('checked');
                $("#selectForm2").serializeSelectJson("List3");
            });

            $("#addgood").click(function () {
                $("#dlg2").dialog("open").dialog('setTitle', '添加ERP菜品信息');
                $(':input','#fm2').not(':button,:submit,:reset') .val('');
                $(':input','#fm2').not(':button,:submit,:reset') .removeAttr('checked');
                $("#opt").val("add");
            });
            $("#editgood").click(function () {
                var row = $('#List').datagrid('getSelected');
                if(row==""||row==undefined||row==null){
                    $.messager.alert("提示信息", "请先选中一条记录");
                    return false;
                }
                $("#dlg2").dialog("open").dialog('setTitle', '修改ERP菜品信息');
                $("#fm2").form("load", row);
                sfbc("a",row.isback);
                sfbc("b",row.status);
                //根据用户id获取name
                if(row.draftsman!=null&&row.draftsman!=""&&row.draftsman!=undefined){
                    $.ajax({
                        url: "/MsGoods/getUserName",
                        data: {id:row.draftsman},
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {
                                $("#draftsman2").val(result.message);
                            }
                        }
                    });
                }
                if(row.auditor!=null&&row.auditor!=""&&row.auditor!=undefined){
                    $.ajax({
                        url: "/MsGoods/getUserName",
                        data: {id:row.auditor},
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {
                                $("#auditor2").val(result.message);
                            }
                        }
                    });
                }
                if(row.approver!=null&&row.approver!=""&&row.approver!=undefined){
                    $.ajax({
                        url: "/MsGoods/getUserName",
                        data: {id:row.approver},
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {
                                $("#approver2").val(result.message);
                            }
                        }
                    });
                }

                $("#opt").val("edit");
            });

            $("#class_id").combobox({

                onChange: function (n, o) {
                    $("#selectForm").serializeSelectJson("List");

                }
            });
            $("#status").combobox({

                onChange: function (n, o) {
                    $("#selectForm").serializeSelectJson("List");

                }
            });

            //是否备餐复选框单选
//            var lastDom = null;
//            $("input[type='checkbox']").bind("click", function() {
//                if (lastDom && lastDom.attr("name") != $(this).attr("name")) {
//                    lastDom.attr("checked", false);
//
//                }
//                lastDom = $(this);
//            })
//            $(':checkbox[name=isback]').each(function(){
//                $(this).click(function(){
//                    if($(this).attr('checked')){
//                        $(':checkbox[name=isback]').removeAttr('checked');
//                        $(this).attr('checked','checked');
//                    }
//                });
//            });
            greatGrid();
        });
        function greatGrid3(id) {
            //所有原料列表
            $('#List3').datagrid({
                url: apiPath + 'Handle/SalesHandler.ashx?m=GetMaterials',
                methord: 'post',
                sortName: 'id',
                idField: 'mid',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                nowrap: "true",
                checkOnSelect:true,
                pagination:true,
                toolbar: '#selectForm2',
                onClickRow: function (rowIndex,rowData) {
                    addList3();
                },
//                toolbar: [
//                    {
//                        text: '保存',
//                        iconCls: 'icon-add',
//                        handler: function () {
//                            addList3();
//                        }
//                    }
//                ],
//                frozenColumns:[[
//                    {field:'ck',checkbox:true}
//                ]],
                columns: [[
                    { field: 'mid', title: '商品原料编号', width: '25%', align: 'center' },
                    { field: 'cname', title: '原料类别', width: '25%', align: 'center' },
                    { field: 'mname', title: '商品名称', width: '25%', align: 'center' },
                    { field: 'unit', title: '规格', width: '25%', align: 'center'}
                ]]
            });
            $('#List3').datagrid('load', {cid:id  });
        }

        //单个商品原料列表
        function greatGrid(data) {
            //editIndex = undefined;
            var lastIndex;
            $('#List2').datagrid({
                //url: apiPath + 'Handle/SalesHandler.ashx?m=GetMaterials',
                data:data,
                methord: 'post',
                sortName: 'id',
                idField: 'mid',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: 'erp商品原料',
                nowrap: "true",
                pagination: false,
                toolbar: [
                    {
                        text: '添加',
                        iconCls: 'icon-edit',
                        handler: function () {
                            addList2();
                        }
                    },
                    {
                        text: '提交',
                        iconCls: 'icon-ok',
                        handler: function () {
                            editList2();
                        }
                    }
                ],
                columns: [[
                    { field: 'mid', title: '商品原料编号', width: '20%', align: 'center' },
                    { field: 'mname', title: '商品名称', width: '20%', align: 'center' },
                    { field: 'count', title: '数量', width: '20%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                                precision: 2
                            }
                        }
                    },
                    { field: 'unit', title: '规格', width: '20%', align: 'center'},
                    { field: 'cz', title: '删除', width: '20%', align: 'center' ,formatter:function (val, row) {
                        return '<span onclick=deleList2() style="color: red;cursor:pointer" class="easyui-linkbutton" >删除</span>';
                    }}
                ]],
//                onClickCell:function (rowIndex, field, value) {
//                    beginEditing(rowIndex, field, value);
//                },
                onClickCell: function(rowIndex, field, value){
                    if (lastIndex != rowIndex){
                        if (field != "count"){
                            return;
                        }
                        $('#List2').datagrid('endEdit', lastIndex);
                        $('#List2').datagrid('beginEdit', rowIndex);
//                        if((value.indexOf(".00"))>0){
//                            value=value.substr(0,value.length-3);
//                        }
                    }
                    lastIndex = rowIndex;
                }
            });
        }

        //
        function updateCount(value) {
            value = value.trim();
            if(isNaN(value)){
                $.messager.alert("提示信息", "只能输入数字");
                return value;
            }else if(value.trim()==""||value==undefined){
                $.messager.alert("提示信息", "不能为空哦");
                return value;
            }else{
                alert(value);
            }
//            var mates=[{mid:mid},{count:count}];
//            $.ajax({
//                url: apiPath + 'Handle/SalesHandler.ashx?m=GetMaterials',
//                data: {fid:goodid;fname:name;mates:mates},
//                type: 'POST',
//                success: function (result) {
//                    if(result==1){
//                        erpgoods(id,name);
//                    }
//
//                }
//            });
        }
        function deleerp() {
            alert("ddd");
        }

        function erpgoods(id,cname) {
            $("#list_id").val(id);
            $("#list_name").val(cname);
            $.ajax({
                url: apiPath + 'Handle/SalesHandler.ashx?m=GetFoodMate',
                data: {fids:id},
                type: 'POST',
                success: function (result) {
                    var data = JSON.parse(result);
                    selectedMaterial = data.data[0].mates;
                    //$('#List2').datagrid('loadData', selectedMaterial);
                    greatGrid(selectedMaterial);

//                    $("#yltitle").attr("data-options", "title:546");
                    $("#yltitle").text(cname);
                       // document.getElementById ("yltitle").setAttribute("title",name);
//                        $("#List2").html("");
//                        $("#List2").append("<tr><th>编号</th><th>名称</th><th>规格</th><th>操作</th></tr>")
//                        for (var i = 0; i < selectedMaterial.length; i++) {
//                            $("#List2").append('<tr><td>'+selectedMaterial[i].mid+'</td> <td>'+selectedMaterial[i].mname+'</td>' +
//                                '<td><input name="count" onChange="updateCount(value)" style="width: 40px;color: green" value='+selectedMaterial[i].count+'>'+selectedMaterial[i].unit+'</td>' +
//                                '<td><a href="javascript:void(0)" class="easyui-linkbutton " iconCls="icon-add" style="text-decoration:none;color: red" onclick="deleerp()">删除</a></td></tr>');
//                        }
                }
            });

        }

        //是否上下架
        function toggle(good_id,opt) {
            if(opt==1){
                opt="bc";
            }else{
                opt="sxj";
            }
            $.ajax({
                url: "/MsGoods/updateErp",
                type: 'POST',
                data: { good_id: good_id,opt: opt},
                success: function (result) {
                    if (result.status == "1") {
                        $("#List").datagrid("reload");
                        $('#List').datagrid('selectRecord',good_id);
                    }
                    else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        //查询材料总数
        function elestoresBindQuery(id) {
            $.ajax({
                url: apiPath + 'Handle/SalesHandler.ashx?m=GetFoodMate',
                data: {fids:id},
                type: 'POST',
                //async:false,
                success: function (result) {
                    var data = JSON.parse(result);
                    selectedMaterial = data.data[0].mates;
                    var length = selectedMaterial.length;
                    $("span[name='" + id + "']").text(length);
                }
            });
        }

        function deleList2() {
            var row = $('#List2').datagrid('getSelected');
            if (row){
                var index = $('#List2').datagrid('getRowIndex', row);
                $('#List2').datagrid('deleteRow', index);
            }
        }
        function editList2() {
            $('#List2').datagrid('acceptChanges');
            var data = $('#List2').datagrid('getData');
            var fid = $("#list_id").val();
            var fname = $("#list_name").val();
            if(fid==null||fid==undefined||fid==""){
                $.messager.alert("提示信息", "请先选择商品");
                return false;
            }
            var rows;
            if(data.total!=0&&data!=""&&data!=undefined){
                rows= JSON.stringify(data.rows);
            }
            var datas = {fid:fid,fname:fname,mates:rows};
            $.messager.confirm('提示信息', '您确定要保存吗？', function (r) {
                if (r) {
                    $.ajax({
                        url: apiPath + 'Handle/SalesHandler.ashx?m=FoodSync',
                        data: datas,
                        type: 'POST',
                        success: function (result) {
                            result = JSON.parse(result);
                            var statu = result.result;
                            if (statu == 1) {
                                $.messager.alert("提示信息", "同步成功");
                                erpgoods(fid, fname);
                            }
                        }
                    });
                }
            });
//            data["fid"] =fid;
//            data["fname"] = fname;
//            $.messager.confirm('提示信息', '您确定要保存吗？', function (r) {
//                if (r) {
//                    $.ajax({
//                        url:"/MsGoods/saveErp",
//                        data:data,
//                        type: 'POST',
//                        success: function (result) {
//                            var statu = result.status;
//                            var jsondata = JSON.parse(result.message);
//                            var obj = jsondata.mates;
//                            var jsonmate = JSON.stringify(obj);
//                            var data = {fid:jsondata.fid,fname:jsondata.fname,mates:jsonmate};
//                            if(statu==1){
//                                $.ajax({
//                                    url: apiPath + 'Handle/SalesHandler.ashx?m=FoodSync',
//                                    data:data,
//                                    type: 'POST',
//                                    success: function (result) {
//                                        result = JSON.parse(result);
//                                        var statu = result.result;
//                                        if(statu==1){
//                                            $.messager.alert("提示信息", "同步成功");
//                                            erpgoods(fid,fname);
//                                        }
//                                    }
//                                });
//                            }
//                        }
//                    });
//                }
//            });
        }
        function addList2() {
            var fid = $("#list_id").val();
            var fname = $("#list_name").val();
            if(fid==null||fid==undefined||fid==""){
                $.messager.alert("提示信息", "请先选择商品");
                return false;
            }
            $.ajax({
                url: apiPath + 'Handle/SalesHandler.ashx?m=GetMaterCate',
                data:null,
                type: 'POST',
                success: function (result) {
                    result = JSON.parse(result);
                    var objs = result.data;
                    $("#ylList").html("");
                    $("#ylList").append(' <a href="###" onclick=tzbg("")> <li class="mdss" name="all">所有原料</li></a>');
                    for (var i = 0; i < objs.length; i++){
                        var name = objs[i].name;
                        var id = objs[i].id;
                        $("#ylList").append(' <a href="###" onclick=tzbg("'+id+'") > <li class="mdss" name='+id+'>'+name+'</li></a>');
//                        $("li[class='mdss']").click(function(){
//                            $('#List3').datagrid({url: apiPath + 'Handle/SalesHandler.ashx?m=GetMaterCate&cid='+id});
//                        });
                    }

                }
            });
            greatGrid3('');
            $("#dlg").dialog("open").dialog('setTitle', '原料列表');
        }

        function tzbg(id) {
            $("#cid").val(id);
            $("#ylList li").each(function () {
                $(this).css("backgroundColor", "");
            });
            $("a [name='all']").css("backgroundColor", "");
            $("a [name='"+id+"']").css("backgroundColor", "#C0C0C0");
            if(id==null||id==""){
                $("a [name='all']").css("backgroundColor", "#C0C0C0");
            }
            greatGrid3(id);
//                $.ajax({
//                    url: apiPath + 'Handle/SalesHandler.ashx?m=GetMaterials',
//                    data:{cid:id},
//                    type: 'POST',
//                    success: function (result) {
//                        result = JSON.parse(result);
//                        var objs = result.data;
//                        //console.log(objs);
//                        greatGrid3(objs);
////                        $('#List3').datagrid('loadData', objs);
//                    }
//                });


            //$('#List3').datagrid({url: apiPath + 'Handle/SalesHandler.ashx?m=GetMaterCate&cid='+id});
        }
        //添加原料
        function addList3() {
//            var rows = $('#List3').datagrid('getSelections');
            //var rows = $('#List3').datagrid('getChecked');
//            for(var i=0;i<rows.length;i++){
//                var mid = rows[i].mid;
//                var mname = rows[i].mname;
//                var unit = rows[i].unit;
//                $('#List2').datagrid('endEdit', lastIndex);
//                $('#List2').datagrid('appendRow',{
//                    mid:mid,
//                    mname:mname,
//                    unit:unit,
//                });
//                var lastIndex = $('#List2').datagrid('getRows').length-1;
//                $('#List2').datagrid('selectRow', lastIndex);
//                $('#List2').datagrid('beginEdit', lastIndex);
//            }
            var row = $('#List3').datagrid('getSelected');
            var mid = row.mid;
            var mname = row.mname;
            var unit = row.unit;
            var data = $('#List2').datagrid('getData');
            var rows = data.rows;
            var isok = "no";
            if(rows!=null&&rows.length>0){
                for(var i=0;i<rows.length;i++){
                    if(mid==rows[i].mid){
                        isok ="ok";
                    }
                }
            }
            if(isok=="ok"){
                $.messager.alert("提示信息", "商品已经有该原料了！");
                return false;
            }
            $('#List2').datagrid('endEdit', lastIndex);
            $('#List2').datagrid('appendRow',{
                mid:mid,
                mname:mname,
                unit:unit,
            });
            var lastIndex = $('#List2').datagrid('getRows').length-1;
            $('#List2').datagrid('selectRow', lastIndex);
        }

        function saveprint(){
            $.ajax({
                url: "/MsGoods/saveMsGoods",
                data: $("#fm2").serialize(),
                type: 'POST',
                beforeSend: function () {
                    return $("#fm2").form("validate");
                },
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.alert("提示信息", "操作成功");
                        $("#dlg2").dialog("close");
                        $("#List").datagrid("reload");
                    }
                    else{
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }
        function getUsers(status) {
            $('body').openWin({
                url:'/MsGoods/userIndex?status='+status ,
                width:'70%',
                height:'75%',
                title:'员工选择'
            });
        }
        function fuzhi(id,name,status) {
            if(status==1){
                //$("#draftsman2").textbox('setValue', name);
                $("#draftsman2").val(name);
                //$('#draftsman').textbox('setValue', id);
                $("#draftsman").val(id);
            }else if(status==2){
//                $("#auditor2").textbox('setValue', name);
//                $('#auditor').textbox('setValue', id);
                $("#auditor2").val(name);
                $("#auditor").val(id);
            }else if(status==3){
//                $("#approver2").textbox('setValue', name);
//                $('#approver').textbox('setValue', id);
                $("#approver2").val(name);
                $("#approver").val(id);
            }

        }
        function sfbc(stasu,isok) {
                if(stasu=='a'){
                    if(isok=='1'){
                        $("#isback").val('1');
                        $('input:radio[name=isback]')[0].checked = true;
                    }else{
                        $("#isback").val('0');
                        $('input:radio[name=isback]')[1].checked = true;
                    }
                }else{
                    if(isok=='1'){
                        $("#status2").val('1');
                        $('input:radio[name=status]')[0].checked = true;
                    }else{
                        $("#status2").val('2');
                        $('input:radio[name=status]')[1].checked = true;
                    }
                }

        }

    </script>
</head>
<body>
<div style="height:25px; background-color:#ffffff">
    <input type="hidden" id="list_id"/>
    <input type="hidden" id="list_name"/>
    <h2 style="background-color:#ffffff;border-width:0 0 1px 0;font-size: 12px;font-weight: bold;padding-top: 5px;margin-left: 5px;" >ERP商品管理</h2>
</div>
<div class="easyui-layout" style="width:100%;height:100%;">
    <div id="selectForm" name="selectForm" style="height: 35px">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
           <!-- <div style="float: left;padding: 5px">
                <a href="javascript:void(0)" onclick="add()" class="easyui-linkbutton " iconCls="icon-add">添加</a>
                <a href="javascript:void(0)" onclick="icon-edit()" class="easyui-linkbutton " iconCls="icon-edit">修改</a>
            </div>-->
            <div style="float: left;padding-left: 5px">
                <label class="mytitle">商品类别:</label>
                <select class="easyui-combobox easyui-validatebox" id="class_id" name="class_id" style="width:150px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <option value="" selected="true">全部</option>
                    <c:forEach var="item" items="${classtypelist}">
                        <option value="${item.class_id}">${item.class_name}</option>
                    </c:forEach>
                </select>
                <label class="mytitle" style="padding-left: 15px">是否上架:</label>
                <select class="easyui-combobox easyui-validatebox" id="status" name="status" style="width:100px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <option value="">全部</option>
                    <option value="1" selected="true">是</option>
                    <option value="2">否</option>
                </select>
                <input class="easyui-textbox" name="good_name" id="good_name" style="width:200px"
                       data-options="label:'商品名称：',prompt:'商品名称模糊查询'">
                <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
                <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
                <a href="###" id="addgood" class="easyui-linkbutton" iconCls="icon-add">新增</a>
                <a href="###" id="editgood" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
            </div>
        </div>
    </div>
    <!--原料分类查询-->
    <div id="selectForm2" name="selectForm" style="height: 35px">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
            <div style="float: left;padding-left: 5px">
                <input class="easyui-textbox" name="key" id="key" style="width:250px"
                       data-options="label:'原料名称：',prompt:'原料名称模糊查询'">
                <input type="hidden" name="cid" id="cid">
                <a href="###" id="findBtn2" class="easyui-linkbutton " iconCls="icon-search">查询</a>
                <a href="###" id="refreshBtn2" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
            </div>
        </div>
    </div>

    <div data-options="region:'south',split:true" style="height:25px;"></div>
    <div data-options="region:'west',split:true"  style="width:50%;">
        <table id="List"></table>
    </div>
    <div data-options="region:'center',iconCls:'icon-tip'" style="height:100%;width:50%;">
        <div style=" height:26px;margin-left: 10px;margin-top: 5px;color: green;text-align: center;font-size:15pt"  id="yltitle">未选择商品</div>
        <div style="height:95%">
            <table id="List2" class="easyui-datagrid " data-options="singleSelect:true,collapsible:true" style="width: 100%">
            </table>
        </div>
        <!--<div style="height:50%">
        <div style="float: left; width:10%">

        </div>
        <div style="float: left;width:90%">
            <table id="List3">
            </table>
        </div>-->

    </div>

    </div>
    <!-- ==============新增窗口============ -->
    <div id="win"></div>

    <!--   ============================== 所以原料界面div、表单===============================     -->
    <div id="dlg" class="easyui-dialog" style="width: 60%; height: 80%; padding: 10px 20px;"
         closed="true">
        <div class="easyui-layout" style="width:100%;height:100%;">
        <div data-options="region:'north',split:false" style="height:0px">
            <!--<h2 style="margin-left: 10px;margin-top: 5px;">
                门店品牌明细管理</h2>-->
        </div>
        <div data-options="region:'west',split:true" style="width:20%;" id="ylList">
        </div>
        <div data-options="region:'center',title:'原料列表'">
            <table id="List3">
            </table>
        </div>
    </div>
    </div>


<!--   ============================== 新增erp div、表单===============================     -->
<div id="dlg2" class="easyui-dialog" style="width: 60%; height: 80%; padding: 10px 20px;"
     closed="true" buttons="#dlg-buttons2">

    <form id="fm2" method="post">
        <div class="fitem">
            <label class="mytitle">商品种类</label>
            <select class="easyui-combobox easyui-validatebox" name="class_id" required="true" style="width: 160px;">
                <option value="" selected="true">请选择</option>
                <c:forEach var="item" items="${classtypelist}">
                    <option value="${item.class_id}">${item.class_name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="fitem">
            <label class="mytitle">商品名称</label>
            <input name="good_name" class="easyui-validatebox" required="true"/>
        </div>
        <div class="fitem">
            <label class="mytitle">销售价格</label>
            <input name="good_price" class="easyui-numberbox" precision=2/>
        </div>
        <div class="fitem">
            <label class="mytitle">出餐时间(分钟)</label>
            <input name="out_time" class="easyui-numberbox"/>
        </div>
        <div class="fitem">
            <table>
                <tr>
                    <th><label class="mytitle">是否备餐</label></th>
                    <td style="line-height: 20px;padding-left: 0px;text-align: center;"><input name="isback" type="radio" value="1" onclick=sfbc('a','1') /><span onclick=sfbc('a','1') style="cursor:pointer;">是</span></td>
                    <td style="line-height: 20px;padding-left: 40px;text-align: center;"><input name="isback" type="radio" value="0" onclick=sfbc('a','0') /><span onclick=sfbc('a','0') style="cursor:pointer;">否</span></td>
                </tr>
            </table>
            <!--<select class="easyui-combobox easyui-validatebox"  name="isback" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'" required="true">
                <option value="" selected="true">请选择</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>-->
        </div>
        <div class="fitem">
            <table>
                <tr>
                    <th><label class="mytitle">是否上架</label></th>
                    <td style="line-height: 20px;padding-left: 0px;text-align: center;"><input name="status" type="radio" value="1" onclick=sfbc('b','1') /><span onclick=sfbc('b','1') style="cursor:pointer;">是</span></td>
                    <td style="line-height: 20px;padding-left: 40px;text-align: center;"><input name="status" type="radio" value="2"  onclick=sfbc('b','0') /><span onclick=sfbc('b','0') style="cursor:pointer;">否</span></td>
                </tr>
            </table>

            <!--<select class="easyui-combobox easyui-validatebox" name="status" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'" required="true">
                <option value="" selected="true">请选择</option>
                <option value="1">是</option>
                <option value="2">否</option>
            </select>-->
        </div>
        <div class="fitem">
            <label class="mytitle">起草人</label>
            <input name="draftsman2" class="easyui-validatebox" onfocus="getUsers('1')" id="draftsman2"/>
        </div>
        <div class="fitem">
            <label class="mytitle">审核人</label>
            <input name="auditor2" class="easyui-validatebox" onfocus="getUsers('2')" id="auditor2"/>
        </div>
        <div class="fitem">
            <label class="mytitle">批准人</label>
            <input name="approver2" class="easyui-validatebox" onfocus="getUsers('3')" id="approver2"/>
        </div>
        <div class="fitem">
            <label class="mytitle">成品重量(克)</label>
            <input name="finished_weight" class="easyui-numberbox"/>
        </div>
        <div class="fitem" style="width: 100%;height: 70px;">
            <label class="mytitle">商品关键字</label>
            <textarea cols=140 rows=5 name=good_key class="textbox"></textarea>
        </div>
        <div class="fitem" style="width: 100%;height: 70px;">
            <label class="mytitle">操作要领</label>
            <textarea cols=140 rows=5 name=gist class="textbox"></textarea>
        </div>
        <div class="fitem" style="width: 100%;height: 70px;">
            <label class="mytitle">制作流程</label>
            <textarea cols=140 rows=5 name=process class="textbox"></textarea>
        </div>
        <input type="hidden" name="status" id="status2" value="1"/>
        <input type="hidden" name="isback" id="isback" value="1"/>
        <input type="hidden" name="draftsman" id="draftsman" value="1"/>
        <input type="hidden" name="auditor" id="auditor" value="1"/>
        <input type="hidden" name="approver" id="approver" value="1"/>
        <input type="hidden" name="good_id" id="good_id" value="1"/>
        <input type="hidden" name="opt" id="opt" value=""/>
    </form>

</div>
<div id="dlg-buttons2">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg2').dialog('close')"
       iconcls="icon-cancel">取消</a>
</div>
</div>
</body>
</html>
