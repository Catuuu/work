<%--
  门店品牌明细管理
  User: zhuzhenghua
  Date: 17-05-28
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<script type="text/javascript" src="/static/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="/static/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="/static/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="/static/lib/base64.js"></script>
<script type="text/javascript" src="/static/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="/static/lib/upload.js"></script>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width initial-scale=1, maximum-scale=1, user-scalable=no" />
    <title>平台商品管理</title>
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
        $(function () {
            $('#List').datagrid({
                url: '/GoodsInfo/GetGoodsInfoLists',
                toolbar: '#selectForm',
                //width: $(window).width(),
                methord: 'post',
               // height: $(window).height(),
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
                    greatGrid(rowData.good_id,rowData.good_name);
                    $("#yltitle").text(rowData.good_name);
                    $("#list_id").val(rowData.good_id);
                    $("#list_name").val(rowData.good_name);
                },
                columns:[[
                    {field:'good_id',title:'ID',width:'5%',align: 'center'},
                    {field:'image_hash',title:'ID',width:'5%',align: 'center',hidden:true},
                    {field:'good_name',title:'商品名称',width:'25%',align: 'center',sortable:true},
                    {field:'class_id',title:'商品类别',width:'12%',sortable:true,align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${classlist}">
                        if(row.class_id=="${item.class_id}"){
                            return "${item.class_name}";
                        }
                        </c:forEach>
                    }},
                    {field:'brand_id',title:'品牌',width:'8%',sortable:true,align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${brandlist}">
                        if(row.brand_id=="${item.brand_id}"){
                            return "${item.brand_name}";
                        }
                        </c:forEach>
                    }},
                    {field:'market_price',title:'商品价格',width:'10%',align: 'center',sortable:true},
                    {field:'box_price',title:'餐盒费',width:'8%',align: 'center',sortable:true},
                    {field:'status',title:'状态',width:'7%',align: 'center',sortable:true,formatter:function(value,row){
                        if (value == 1) {
//                            return '<span onclick="toggle('+row.good_id+',0)" style="color:green;cursor:pointer">上架</span>';
                            return'<select style="cursor:pointer;color:green" onchange="toggle('+row.good_id+',0)"><option value="1" selected="true">上架</option> <option value="0">下架</option></select>';
                        } else if(value == 0){
                            return '<select style="cursor:pointer;color:red" onchange="toggle('+row.good_id+',1)"><option value="1" >上架</option> <option value="0" selected="true">下架</option></select>';
                        }
//                        else if(value == 2){
//                            return '<span onclick="toggle('+row.good_id+',0)" style="color:red;cursor:pointer">删除</span>';
//                        }
                    }
                    },
//                    {field:'vip_discounts',title:'折扣',width:'10%',align: 'center',formatter:function(value,row){
//                        if (value == 1) {
//                            return '<span onclick="toggle2('+row.good_id+',0)" style="color:green;cursor:pointer">参加折扣</span>';
//                        } else {
//                            return '<span onclick="toggle2('+row.good_id+',1)" style="color:red;cursor:pointer">不参加</span>';
//                        }
//                    } },
                    {field:'add_date',title:'添加时间',width:'15%',align: 'center',sortable:true},
                    {field:'good_count',title:'菜品数量',width:'10%',align: 'center',sortable:true}
                 ]]
            });

            $('#List3').datagrid({
                url: '/MsGoods/GetErpGoodsLists',
                toolbar: '#selectForm2',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'good_id',
                idField: 'good_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                pageSize: 10,
                //title: '门店品牌明细管理',
                nowrap: "true",
                queryParams:{
                    status:$("#status2").val()
                },
                onClickRow: function (rowIndex,rowData) {
                    addList2();
                },
                columns:[[
                    {field:'good_id',title:'ID',width:'5%',align: 'center'},
                    {field:'good_name',title:'菜品名称',width:'25%',align: 'center',sortable:true},
                    {field:'class_id',title:'菜品类别',width:'20%',sortable:true,align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${classtypelist}">
                        if(row.class_id=="${item.class_id}"){
                            return "${item.class_name}";
                        }
                        </c:forEach>
                    }},
                    {field:'good_num',title:'菜品编码',width:'20%',align: 'center',sortable:true},
                    {field:'status',title:'上/下架',width:'15%',align: 'center',sortable:true,formatter:function(value,row){
                        if (value == 1) {
                            return '<span  style="color:green">是</span>';
                        } else {
                            return '<span  style="color:red">否</span>';
                        }
                    }
                    },
                    {field:'isback',title:'备餐',width:'15%',align: 'center',formatter:function(value,row){
                        if (value == 1) {
                            return '<span style="color:green">是</span>';
                        } else {
                            return '<span  style="color:red">否</span>';
                        }
                    } }
                ]]
            });
//查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("List");
            });

            $("#refreshBtn").click(function () {
                $(':input','#selectForm').not(':button,:submit,:reset') .val('');
                //$(':input','#selectForm').not(':button,:submit,:reset') .removeAttr('checked');
                $('#status').combobox('setValue','1');
                $("#class_id").get(0).options[0].selected = true;
                //$('#class_id').combobox('setValue',"");
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
                $("#dlg2").dialog("open").dialog('setTitle', '添加平台商品信息');
                $(':input','#fm2').not(':button,:submit,:reset') .val('');
                $('input:radio[name=status]')[1].checked = true;
                $("#mt").attr("src","/static/images/uploadbg.png");
                $("#ele").attr("src","/static/images/uploadbg.png");
                $("#opt").val("add");
            });
            $("#editgood").click(function () {
                var row = $('#List').datagrid('getSelected');
                if(row==""||row==undefined||row==null){
                    $.messager.alert("提示信息", "请先选中一条记录");
                    return false;
                }
                $("#dlg2").dialog("open").dialog('setTitle', '修改平台商品信息');
                $("#fm2").form("load", row);
                if(row.good_pic==''||row.good_pic==null||row.good_pic==undefined){
                    $("#mt").attr("src","/static/images/uploadbg.png");
                    $("#ele").attr("src","/static/images/uploadbg.png");
                }else{
                    $("#mt").attr("src","http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+row.good_pic);
                    $("#ele").attr("src","http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+row.egood_pic);
                }
                $("#opt").val("edit");
                sfbc('b',row.status);
//                sfbc('a',row.vip_discounts);
                //根据用户id获取name
            });

            $("#goodclass").click(function () {
                $('body').openWin({
                    url:'/GoodsInfo/classIndex',
                    width:'50%',
                    height:'65%',
                    title:'类别管理'
                });
            });

//            $("#class_id").combobox({
//
//                onChange: function (n, o) {
//                    $("#selectForm").serializeSelectJson("List");
//                }
//            });
            $("#status").combobox({
                onChange: function (n, o) {
                    $("#selectForm").serializeSelectJson("List");
                }
            });
            $("#class_id2").combobox({

                onChange: function (n, o) {
                    $("#selectForm2").serializeSelectJson("List3");
                }
            });
            $("#status2").combobox({
                onChange: function (n, o) {
                    $("#selectForm2").serializeSelectJson("List3");
                }
            });
            greatGrid('','');
        });

        //单个商品原料列表
        function greatGrid(id,name) {
            var lastIndex;
            $('#List2').datagrid({
                url:'/GoodsInfo/GetGoodsInfoErp?good_id='+id,
                methord: 'post',
                sortName: 'g.good_id',
                idField: 'good_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: 'erp商品原料',
                nowrap: "true",
                pagination: false,
                toolbar: [
                    {
                        text: '提交',
                        iconCls: 'icon-ok',
                        handler: function () {
                            editList2();
                        }
                    }
                ],
                columns: [[
                    { field: 'erp_good_id', title: 'ID', width: '15%', align: 'center',hidden:true },
                    { field: 'good_id', title: '菜品ID', width: '15%', align: 'center' },
                    { field: 'class_id', title: '菜品类别', width: '15%', align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${classtypelist}">
                        if(row.class_id=="${item.class_id}"){
                            return "${item.class_name}";
                        }
                        </c:forEach>
                    } },
                    { field: 'good_name', title: '菜品名称', width: '30%', align: 'center' },
                    { field: 'good_count', title: '数量', width: '25%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0
                            }
                        }
                    },
                    { field: 'cz', title: '删除', width: '15%', align: 'center' ,formatter:function (val, row) {
                        return '<span onclick=deleList2() style="color: red;cursor:pointer" class="easyui-linkbutton" >删除</span>';
                    }}
                ]],
                onClickCell: function(rowIndex, field, value){
                    if (lastIndex != rowIndex){
                        if (field != "good_count"){
                            return;
                        }
                        $('#List2').datagrid('endEdit', lastIndex);
                        $('#List2').datagrid('beginEdit', rowIndex);
                    }
                    lastIndex = rowIndex;
                }
            });
        }

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
        }


        //是否上下架
        function toggle(good_id,status) {
            $.ajax({
                url: "/GoodsInfo/updateGoodsInfo",
                type: 'POST',
                data: { good_id: good_id,status: status},
                success: function (result) {
                    if (result.status == "1") {
                        $("#List").datagrid("reload");
                        $('#List').datagrid('selectRecord',good_id);
                    }else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        //是否参加折扣
        function toggle2(good_id,vip_discounts) {
            $.ajax({
                url: "/GoodsInfo/updateGoodsInfo2",
                type: 'POST',
                data: { good_id: good_id,vip_discounts: vip_discounts},
                success: function (result) {
                    if (result.status == "1") {
                        $("#List").datagrid("reload");
                        $('#List').datagrid('selectRecord',good_id);
                    }else {
                        $.messager.alert("提示信息", "操作失败");
                    }
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
            $.messager.confirm('提示信息', '您确定要保存吗？', function (r) {
                if(r){
                    $.ajax({
                        url: '/GoodsInfo/saveErp?good_id='+fid,
                        data:data,
                        type: 'POST',
                        success: function (result) {
                            if(result.status=="1"){
                                $.messager.show({title:'提示信息', msg:'操作成功',showType:'show'});
                                greatGrid(fid,fname);
                                $("#List").datagrid("reload");
                                $('#List').datagrid('selectRecord',fid);
                            }else{
                                $.messager.alert("提示信息", "操作失败");
                            }
                        }
                    });
                }
            });
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
        }
        //添加原料
        function addList2() {
            var fid = $("#list_id").val();
            var fname = $("#list_name").val();
            if(fid==null||fid==undefined||fid==""){
                $.messager.show({title:'提示信息', msg:'请先选择商品',showType:'show'});
                return false;
            }
            var row = $('#List3').datagrid('getSelected');
            var good_id = row.good_id;
            var good_name = row.good_name;
            var unit = row.unit;
            var data = $('#List2').datagrid('getData');
            var rows = data.rows;
            var isok = "no";
            if(rows!=null&&rows.length>0){
                for(var i=0;i<rows.length;i++){
                    if(good_id==rows[i].good_id){
                        isok ="ok";
                    }
                }
            }
            if(isok=="ok"){
                $.messager.show({title:'提示信息', msg:'商品已经有该菜品了',showType:'show'});
                return false;
            }
            $('#List2').datagrid('endEdit', lastIndex);
            $('#List2').datagrid('appendRow',{
                good_id:good_id,
                good_name:good_name,
                class_id:row.class_id,
                erp_good_id:1,
                good_count:1,
            });
            var lastIndex = $('#List2').datagrid('getRows').length-1;
            $('#List2').datagrid('selectRow', lastIndex);
        }

        function saveprint(){
           var tus =  $("#status3").val();
           if(tus==null||tus==undefined||tus==""){
               tus='1';
           }
           var vipzk = $("#vip_discounts").val();
            var good_name = $("#check_name1").val();
            var class_id = $("#classSelect").combotree("getValue")
            var opt = $("#opt").val();
            if(opt=="edit"){
                if(good_name==null||good_name==""){
                    $.messager.show({
                        title:'提示信息',
                        msg:'商品名称不能为空',
                        showType:'show'
                    });
                    return false;
                }
                $.ajax({
                    url: "/GoodsInfo/saveGoodsInfo?status="+tus+"&vip_discounts=0",
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
            }else{
                $.ajax({
                    url: "/GoodsInfo/checkName",
                    data: {name:good_name,class_id:class_id,status:1},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $.messager.show({
                                title:'提示信息',
                                msg:'商品名称不能重复',
                                showType:'show'
                            });
                            $("#check_name1").textbox("setValue", "");
                        } else{
                            if(good_name==null||good_name==""){
                                $.messager.show({
                                    title:'提示信息',
                                    msg:'商品名称不能为空',
                                    showType:'show'
                                });
                                return false;
                            }
                            $.ajax({
                                url: "/GoodsInfo/saveGoodsInfo?status="+tus+"&vip_discounts=0",
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
                    }
                });
            }



        }
        function sfbc(stasu,isok) {
                if(stasu=='a'){
                    $("#vip_discounts").val(isok);
                    if(isok=='0'){
                        $('input:radio[name=vip_discounts]')[0].checked = true;
                    }else{
                        $('input:radio[name=vip_discounts]')[1].checked = true;
                    }
                }else{
                    $("#status3").val(isok);
                    if(isok=='0'){
                        $('input:radio[name=status]')[0].checked = true;
                    }else if(isok=='1'){
                        $('input:radio[name=status]')[1].checked = true;
                    }else{
                        $('input:radio[name=status]')[2].checked = true;
                    }
                }

        }

        function getPic(status) {
            $('body').openWin({
                url:'/GoodsInfo/getPicAddress?status='+status ,
                width:'20%',
                height:'22%',
                title:'图片上传'
            });
        }
        function getAddress(address,status){
            if(status=='1'){
                //document.querySelector('img').src="http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+row.class_pic;
                $("#mt").attr("src","http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+address);
                $("#good_pic").val(address);
            }else{
                $("#ele").attr("src","http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+address);
                $("#egood_pic").val(address);
            }
        }

        function greatGread() {
            $('#List_goods').datagrid({
                url: '/PtGoodsManage/getGoodsList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'stores_class_id',
                sortOrder: 'desc',
                idField: 'stores_class_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: '门店品牌明细管理',
                nowrap: "true",
                pageSize: 30,
                selectOnCheck: true,
                checkOnSelect: true,
                singleSelect:false,
                toolbar: '#selectForm',
                pagination:true,
                columns: [[
                    { field: 'elem_restaurant_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'stores_brand_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'listorder', title: 'ID',  align: 'center',hidden:true},
                    { field: 'type', title: 'ID',  align: 'center',hidden:true},
                    { field: 'stores_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'stores_class_id', title: 'ID', width: '10%', align: 'center',checkbox:true},
                    { field: 'class_name', title: '类别名称', width: '15%', align: 'center'},
                    { field: 'stores_name', title: '店铺', width: '20%', align: 'center'},
                    { field: 'bd_id', title: '所属品牌', width: '10%', align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${brandlist}">
                        if(row.bd_id=="${item.brand_id}"){
                            return "${item.brand_name}";
                        }
                        </c:forEach>
                    }  },
                    { field: 'class_desc', title: '类别描述', width: '20%', align: 'center'},
                ]]

            });
        }

    </script>
</head>
<body>
<div style="height:25px; background-color:#ffffff">
    <input type="hidden" id="list_id"/>
    <input type="hidden" id="list_name"/>
    <h2 style="background-color:#ffffff;color:#777;border-width:0 0 1px 0;font-size: 12px;font-weight: bold;padding-top: 5px" >平台商品管理</h2>
</div>
<div class="easyui-layout" style="width:100%;height:100%;">
    <div id="selectForm" name="selectForm" style="height: 35px">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
            <div style="float: left;padding-left: 5px">
                <label class="mytitle">商品类别:</label>
                <%--<select class="easyui-combobox easyui-validatebox" id="class_id" name="class_id" style="width:120px;"--%>
                        <%--data-options="editable:false,panelHeight:'auto'" required="true">--%>
                    <%--<option value="" selected="true">全部</option>--%>
                    <%--<c:forEach var="item" items="${classlist}">--%>
                        <%--<option value="${item.class_id}">${item.class_name}</option>--%>
                    <%--</c:forEach>--%>
                <%--</select>--%>
                <select name="class_select" id="tt" panelWidth="300px" panelHeight="300px" class="easyui-combotree" style="width:170px;"
                        data-options="
                        url:'/GoodsInfo/GetBrandTree',
                        onClick: function(node){
                                    var rows = node.children;
                                    $('#s_brand_id').val('');
                                    $('#s_class_id').val('');
                                    if( rows != undefined && rows.length>0 ){
                                        var id = (node.id);
                                        id=id.substr(0,id.length-1);
                                        $('#s_brand_id').val(id);
                                    }else{
                                        var parent = $('#tt').combotree('tree').tree('getParent', node.target);
                                         var id = (parent.id);
                                        id=id.substr(0,id.length-1);
                                        $('#s_brand_id').val(id);
                                        $('#s_class_id').val(node.id);
                                    }
		                            $('#selectForm').serializeSelectJson('List');
	                            }
                ">
                </select>
                <label class="mytitle" style="padding-left: 15px">是否上架:</label>
                <select class="easyui-combobox easyui-validatebox" id="status" name="status" style="width:50px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <option value="" >全部</option>
                    <option value="1" selected="true">是</option>
                    <option value="0">否</option>
                </select>
                <input name="brand_id" id="s_brand_id" type="hidden">
                <input name="class_id" id="s_class_id" type="hidden">
                <input class="easyui-textbox" name="good_name" id="good_name" style="width:160px"
                       data-options="label:'商品名称：',prompt:'商品名称模糊查询'">
                <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
                <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
                <a href="###" id="addgood" class="easyui-linkbutton" iconCls="icon-add">新增</a>
                <a href="###" id="editgood" class="easyui-linkbutton" iconCls="icon-edit">修改</a>
                <a href="###" id="goodclass" class="easyui-linkbutton" iconCls="icon-edit">类别管理</a>
            </div>
        </div>
    </div>
    <!--原料分类查询-->
    <div id="selectForm2" name="selectForm2" style="height: 35px">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
            <div style="float: left;padding-left: 5px">
                <label class="mytitle">菜品类别:</label>
                <select class="easyui-combobox easyui-validatebox" id="class_id2" name="class_id" style="width:150px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <option value="" selected="true">全部</option>
                    <c:forEach var="item" items="${classtypelist}">
                        <option value="${item.class_id}">${item.class_name}</option>
                    </c:forEach>
                </select>
                <label class="mytitle" style="padding-left: 15px">是否上架:</label>
                <select class="easyui-combobox easyui-validatebox" id="status2" name="status" style="width:100px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <option value="" >全部</option>
                    <option value="1" selected="true">是</option>
                    <option value="2">否</option>
                </select>
                <input class="easyui-textbox" name="good_name" id="good_name2" style="width:200px"
                       data-options="label:'菜品名称：',prompt:'菜品名称模糊查询'">
                <a href="###" id="findBtn2" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
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
        <div style="height:30%">
            <table id="List2" class="easyui-datagrid " data-options="singleSelect:true,collapsible:true" style="width: 100%">
            </table>
        </div>
        <div style="height:65%">
            <table id="List3" class="easyui-datagrid " data-options="singleSelect:true,collapsible:true" style="width: 100%">
            </table>
        </div>
    </div>

    </div>
    <!-- ==============新增窗口============ -->
    <div id="win"></div>



<!--   ============================== 新增erp div、表单===============================     -->
<div id="dlg2" class="easyui-dialog" style="width: 60%; height: 80%; padding: 10px 20px;"
     closed="true" buttons="#dlg-buttons2">

    <form id="fm2" method="post">
        <div class="fitem">
            <label class="mytitle">商品种类</label>
            <%--<select class="easyui-combobox easyui-validatebox" name="class_id" required="true" style="width: 160px;">--%>
                <%--<option value="" selected="true">请选择</option>--%>
                <%--<c:forEach var="item" items="${classlist}">--%>
                    <%--<option value="${item.class_id}">${item.class_name}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
            <select name="class_id" id="classSelect" panelWidth="300px" panelHeight="300px" class="easyui-combotree" style="width:170px;"
                    data-options="
                        url:'/GoodsInfo/GetBrandTree',
                         onClick: function(node){
		                            var rows = node.children;
		                            if(rows.length>0){
		                                $('#classSelect').combotree('clear');
		                            }
	                            }
                ">
            </select>
        </div>
        <div class="fitem">
            <label class="mytitle">商品名称</label>
            <input name="good_name" class="easyui-validatebox easyui-textbox" id="check_name1" required="true"  style="width:280px"/>
            <span id="check1" style="color: red"></span>
        </div>
        <div class="fitem">
            <label class="mytitle">商品价格</label>
            <input name="market_price" class="easyui-numberbox" precision=2/>
        </div>
        <div class="fitem">
            <label class="mytitle">餐盒费</label>
            <input name="box_price" class="easyui-numberbox" precision=2/>
        </div>
        <%--<div class="fitem">--%>
            <%--<table>--%>
                <%--<tr>--%>
                    <%--<th><label class="mytitle">会员是否折扣</label></th>--%>
                    <%--<td style="line-height: 20px;padding-left: 0px;text-align: center;"><input name="vip_discounts" type="radio" value="0" onclick=sfbc('a','0') /><span onclick=sfbc('a','0') style="cursor:pointer;">不参加</span></td>--%>
                    <%--<td style="line-height: 20px;padding-left: 40px;text-align: center;"><input name="vip_discounts" type="radio" value="1" onclick=sfbc('a','1') /><span onclick=sfbc('a','1') style="cursor:pointer;">参加</span></td>--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</div>--%>
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
            <label class="mytitle">商品描述</label>
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
