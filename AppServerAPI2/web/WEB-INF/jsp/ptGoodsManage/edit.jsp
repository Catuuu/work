
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
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>平台商品管理</title>
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
        .fitem
        {

            border-bottom:1px dashed #9f9f9f;
            padding-bottom:15px;
            padding-top:15px;
            width: 50%;
            height: 15px;
            float: left;
        }

        .ftitle {
            font-size: 14px;
            font-weight: bold;
            padding: 5px 0;
            margin-bottom: 10px;
            border-bottom: 1px solid #ccc;
        }

        .fitem label {
            display: inline-block;
            width: 100px;
        }

        .mytitle {
            font-weight: lighter;
            margin-left: 10px;
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
        img {
            border-style: outset;
            border-color: rgb(204, 204, 255);
            border-width: 4px;
            margin-top: 4px;
            margin-bottom: 3px;
        }
    </style>
    <script type="text/javascript">
        var datajson={"a":[],"b":[],"c":[],"d":[],"e":[],"f":[]};
        var standard_id ;
        var lastIndexs;
        $(function () {
            $('#brand_id').combotree({
                url:'/PtGoodsManage/getBrandCombobox',
                panelWidth:'200px',
                panelHeight:'300px',
                valueField:'id',
                textField:'text'
            });

            $("#brand_id").combobox({
                onChange: function (n, o) {
                    $('#stores_class_id').combotree({
                        url:'/PtGoodsManage/getStoresClass?brand_id='+n+'&stores_id='+${stores_id},
                        panelWidth:'200px',
                        panelHeight:'300px',
                        valueField:'id',
                        textField:'text'
                    });
                }
            });
            $("#class_id2").combobox({

                onChange: function (n, o) {
                    $("#selectForm2").serializeSelectJson("list_erp");
                }
            });
            $("#status2").combobox({
                onChange: function (n, o) {
                    $("#selectForm2").serializeSelectJson("list_erp");
                }
            });

            //查询
            $("#findBtn2").click(function () {
                $("#selectForm2").serializeSelectJson("list_erp");
            });

            $("#refreshBtn2").click(function () {
                $(':input','#selectForm2').not(':button,:submit,:reset') .val('');
                $(':input','#selectForm2').not(':button,:submit,:reset') .removeAttr('checked');
                $("#selectForm2").serializeSelectJson("list_erp");
            });

            //规格表

            $('#standard_list').datagrid({
                url: '/PtGoodsManage/getStandardList?sgl_id='+${sgl_id},
//                toolbar: '#selectForm',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'standard_id',
                idField: 'standard_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: '门店品牌明细管理',
                nowrap: "true",
                pagination:false,
                onDblClickRow: function (rowIndex, rowData) {
//                    if (lastIndex != rowIndex){
                    $('#standard_list').datagrid('endEdit', lastIndexs);
                    $('#standard_list').datagrid('beginEdit', rowIndex);
//                    }
                    lastIndexs = rowIndex;
                },
                onClickRow:function(rowIndex,rowData){
                    $('#standard_list').datagrid('endEdit', lastIndexs);
                    if(validateStr(rowData.standard_id)){
                        greatGrid(rowData.standard_id);
                    }
                    standard_id = rowData.standard_id;
                    var row = $('#standard_list').datagrid('getChanges');
                    if(row!=null||row.length>0){
                        editStandard(row);
                        $('#standard_list').datagrid('acceptChanges');
                    }
                },
                onLoadSuccess:function () {
                    var length = $('#standard_list').datagrid('getRows').length;
                    $('#standard_list').datagrid('hideColumn', 'standard_name');
                    if(length>1){
                        $('#standard_list').datagrid('showColumn', 'standard_name');
                    }
                },
                toolbar: '#tb',
                columns:[[
                    {field:'standard_id',title:'ID',width:'5%',align: 'center',hidden:true},
                    {field:'standard_name',title:'名称',width:'20%',align: 'center',sortable:true,
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'text'
                        }
                    },
                    {field:'count',title:'份数',width:'15%',align: 'center',sortable:true,
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                            }
                        }
                    },
                    { field: 'current_price', title: '价格', width: '15%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer;text-align: center'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                                precision: 2
                            }
                        }
                    },
                    { field: 'box_price', title: '餐盒费', width: '15%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer;text-align: center'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                                precision: 2
                            }
                        }
                    },
                    { field: 'box_count', title: '餐盒数量', width: '15%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                            }
                        }
                    },
                    { field: 'cz', title: '操作', width: '15%', align: 'center',
                        formatter:function(value,row){
                                return'<a style="color:red;" href="###" onclick=deleteStandard('+row.standard_id+')>删除</a>'
                        }
                    }
                ]]
            });

            $('#brand_id').combotree("setValue",${brand_id});
            $('#stores_class_id').combotree("setValue",${csg.stores_class_id});
            $('#market_price').textbox("setValue",${csg.market_price});
            $('#good_name').textbox("setValue",'${csg.good_name}');
            <%--alert(${csg.good_name});--%>
            $('#good_info').val('${csg.good_info}');
            sfbc('b',${csg.status});

            if(${empty csg.good_pic}){
                $("#mt").attr("src","/static/images/uploadbg.png");
            }else{
                $("#mt").attr("src","http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+'${csg.good_pic}');
                $("#good_pic").val('${csg.good_pic}');
            }
            if(${empty csg.egood_pic}){
                $("#ele").attr("src","/static/images/uploadbg.png");
            }else{
                $("#ele").attr("src","http://caidashi.oss-cn-hangzhou.aliyuncs.com/"+'${csg.egood_pic}');
                $("#egood_pic").val('${csg.egood_pic}');
            }



        });


        function greatGrid(standard_id) {
            //rep菜品表
            var lastIndex;
            $('#erpGood_list').datagrid({
                url:'/PtGoodsManage/getStandardGoods?standard_id='+standard_id,
                methord: 'post',
                sortName: 'a.good_id',
                idField: 'good_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: 'erp商品原料',
                nowrap: "true",
                pagination: false,
                toolbar: '#tb2',
                columns: [[
                    { field: 'erp_good_id', title: 'ID', width: '15%', align: 'center',hidden:true },
                    {field:'good_id',title:'ID',width:'5%',align: 'center',hidden:true},
                    { field: 'class_id', title: '菜品类别', width: '25%', align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${classtypelist}">
                        if(row.class_id=="${item.class_id}"){
                            return "${item.class_name}";
                        }
                        </c:forEach>
                    } },
                    { field: 'good_name', title: '菜品名称', width: '40%', align: 'center' },
                    { field: 'good_count', title: '数量', width: '15%', align: 'center',formatter:function(value,row){
                        if (value==null||value==""||value==undefined){
                            return '1';
                        }else{
                            return value;
                        }
                    },
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0
                            }
                        }
                    },
                    { field: 'cz', title: '删除', width: '15%', align: 'center' ,formatter:function (val, row,rowIndex) {
                        return '<span onclick=deleErp('+row.erp_good_id+') style="color: red;cursor:pointer" class="easyui-linkbutton" >删除</span>';
                    }}
                ]],
                onClickCell: function(rowIndex, field, value){
                    if (lastIndex != rowIndex){
                        if (field != "good_count"){
                            return;
                        }
                        $('#erpGood_list').datagrid('beginEdit', rowIndex);
                        $('#erpGood_list').datagrid('endEdit', lastIndex);

                    }
                    lastIndex = rowIndex;
                },
                onClickRow:function(rowIndex,rowData){
                    var row = $('#erpGood_list').datagrid('getChanges');
                    if(row!=null&&row.length>0){
                        editErp(row[0].erp_good_id,row[0].good_count)
                        $('#erpGood_list').datagrid('acceptChanges');
                    }
                },
            });
        }

        function addErpList() {
            var row = $('#standard_list').datagrid('getSelected');
            var index=0;
            if (row!=null&&row!=""&&row!=undefined) {
                index= $('#standard_list').datagrid('getRowIndex', row);
            }else{
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择一种规格',
                    showType:'show'
                });
                return false;
            }

            $("#dlg").dialog("open").dialog('setTitle', '添加Erp菜品信息');
            greatGread(index)
        }

        function greatGread(index) {
            //erp菜品总表
            $('#list_erp').datagrid({
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
        }
        function sfbc(stasu,isok) {
            if(stasu=='a'){
                $("#status").val(isok);
                if(isok=='0'){
                    $('input:radio[name=vip_discounts]')[0].checked = true;
                }else{
                    $('input:radio[name=vip_discounts]')[1].checked = true;
                }
            }else{
                $("#status").val(isok);
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
                width:'35%',
                height:'30%',
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

        //添加原料
        function addList2() {
            if(standard_id==null||standard_id==""||standard_id==undefined){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择规格',
                    showType:'show'
                });
                return false;
            }
            var row = $('#list_erp').datagrid('getSelected');
            var data = $('#erpGood_list').datagrid('getData').rows;
            for (var i =0;i<data.length;i++){
                if(row.good_id==data[i].good_id){
                    $.messager.show({
                        title:'提示信息',
                        msg:'已经存在改菜品了',
                        showType:'show'
                    });
                    return false;
                }
            }
            $.ajax({
                url: "/PtGoodsManage/saveStandardGood",
                data: {good_id:row.good_id,good_count:1,standard_id:standard_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.alert("提示信息", "操作成功");
                        $("#erpGood_list").datagrid("reload");
                    }
                    else{
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });

        }

        function editErp(erp_good_id,good_count) {
            if(!validateStr(erp_good_id)||!validateStr(good_count)){
                return false;
            }
            $.ajax({
                url: "/PtGoodsManage/editErp",
                data: {erp_good_id:erp_good_id,good_count:good_count},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                    }
                    else{
                    }
                }
            });
        }

        function deleErp(erp_good_id) {
            if (validateStr(erp_good_id)){
                $.ajax({
                    url: "/PtGoodsManage/deleErp",
                    data: {erp_good_id:erp_good_id},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $("#erpGood_list").datagrid("reload");
                        }
                        else{
                        }
                    }
                });

            }
        }

        function editStandard(row) {
            var opt="";
            if(validateStr(row)){
                if(validateStr(row[0].standard_id)){
                    opt = "edit";
                }else{
                    opt = "add";
                }
                var datas = JSON.stringify(row);
                $.ajax({
                    url: "/PtGoodsManage/editStandard",
                    data: {sgl_id:${sgl_id},standard:datas,opt:opt},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $("#standard_list").datagrid("reload");
                        }
                        else{
                        }
                    }
                });
            }
        }

        function addStandard_list() {
            var length = $('#standard_list').datagrid('getRows').length;
            $('#standard_list').datagrid('hideColumn', 'standard_name');
            if(length>0){
                $('#standard_list').datagrid('showColumn', 'standard_name');
            }
            $('#standard_list').datagrid('endEdit', lastIndexs);
            $('#standard_list').datagrid('appendRow',{
                standard_name:'',
                count:'',
                current_price:'',
                box_price:'1',
                box_count:'1',
            });
            lastIndexs = $('#standard_list').datagrid('getRows').length-1;
            $('#standard_list').datagrid('selectRow', lastIndexs);
            $('#standard_list').datagrid('beginEdit', lastIndexs);
        }

        //规格删除
        function deleteStandard(standard_id) {
            if(validateStr(standard_id)){
                $.ajax({
                    url: "/PtGoodsManage/deleteStandard",
                    data: {standard_id:standard_id},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $("#standard_list").datagrid("reload");
                        }
                        else{
                            $.messager.show({
                                title:'提示信息',
                                msg:'操作失败',
                                showType:'show'
                            });
                        }
                    }
                });
            }
        }

        //保存
        function saveprint() {
            var node = $('#stores_class_id').combotree('tree').tree('getSelected');
            var stores_id = node.stores_id;
            var stores_brand_id = node.stores_brand_id
            var brand_id = $("#brand_id").val();
            var stores_class_id = $("#stores_class_id").val();
            var good_name = $("#good_name").val();
            var market_price = 0;
            var good_info = $("#good_info").val();
            var egood_pic = $("#egood_pic").val();
            var good_pic = $("#good_pic").val();
            var opt = $("#opt").val();
            var status =$("#status").val();
            if(!validateStr(stores_class_id)&&!validateStr(good_name)){
                $.messager.alert("提示信息", "相关信息不能为空");
                return false;
            }
            //结束规格表编辑
            $('#standard_list').datagrid('endEdit', lastIndexs);
            var row = $('#standard_list').datagrid('getChanges');
            if(row!=null||row.length>0){
                editStandard(row);
                $('#standard_list').datagrid('acceptChanges');
            }

            $.ajax({
                url: "/PtGoodsManage/updateGood",
                data: {stores_class_id:stores_class_id,market_price:market_price,good_name:good_name,
                    good_info:good_info,opt:opt,egood_pic:egood_pic,good_pic:good_pic,status:status,brand_id:brand_id,
                    stores_id:stores_id,stores_brand_id:stores_brand_id,sgl_id:${sgl_id}},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        window.parent.reloadGread();
                        $.closeWin();
                    }else if(result.status == "2"){
                        $.messager.alert("提示信息", "名称不能重复");
                    }
                    else{
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }



        //非空验证
        function validateStr(text) {
            var isok = true;
            if(text==null||text==""||text==undefined){
                isok = false;
            }
            return isok ;
        }


    </script>
</head>
<body>
<!--   ============================== 新增erp div、表单===============================     -->
<form id="fm2" method="post">
    <div class="fitem">
        <label class="mytitle">品牌</label>
        <input id="brand_id" name="brand_id"  class="easyui-textbox easyui-validatebox" style="width:160px;" >
    </div>
    <div class="fitem">
        <label class="mytitle">商品种类</label>
        <input id="stores_class_id" name="stores_class_id"  class="easyui-textbox easyui-validatebox" style="width:160px;" >
        </select>
    </div>
    <div class="fitem">
        <label class="mytitle">商品名称</label>
        <input name="good_name" class="easyui-validatebox easyui-textbox" id="good_name" required="true" style="width:280px"/>
        <%--<span id="check1" style="color: red"></span>--%>
    </div>
    <div class="fitem">
        <table style="text-align:left;height: 100%">
            <tr>
                <th><label class="mytitle">状态</label></th>
                <td style="line-height: 20px;padding-left: 0px;text-align: center;"><input name="status" type="radio" value="2" onclick=sfbc('b','2') /><span onclick=sfbc('b','2') style="cursor:pointer;">下架</span></td>
                <td style="line-height: 20px;padding-left: 40px;text-align: center;"><input name="status" type="radio" checked="true" value="1" onclick=sfbc('b','1') /><span onclick=sfbc('b','1') style="cursor:pointer;">上架</span></td>
            </tr>
        </table>
    </div>
    <%--<div class="fitem">--%>
        <%--<label class="mytitle">商品价格</label>--%>
        <%--<input name="market_price" id="market_price" class="easyui-numberbox" precision=2/>--%>
    <%--</div>--%>
    <div class="fitem" style="width: 100%;height: 70px;">
        <label class="mytitle">商品描述</label>
        <textarea cols=100 rows=5 name=good_info id="good_info" class="textbox"></textarea>
    </div>
    <%--<div class="fitem" style="width: 100%;height: 70px;">--%>
    <%--<label class="mytitle">商品详情</label>--%>
    <%--<textarea cols=140 rows=5 name=introduce class="textbox"></textarea>--%>
    <%--</div>--%>
    <div style="width: 50%;float: left">
        <label class="mytitle">美团商品图标</label>
        <img src="/static/images/uploadbg.png" height="80" width="150" alt="暂无图片" id="mt" onclick="getPic('1')"/>
    </div>
    <div style="width: 50%;float: left">
        <label class="mytitle">饿了么商品图标</label>
        <img src="/static/images/uploadbg.png" height="80" width="80" alt="暂无图片" id="ele" onclick="getPic('2')"/>
    </div>
    <div class="easyui-layout" data-options="region:'center',title:'',iconCls:'icon-ok'" style="overflow: hidden;border:0;width: 100%;height: 300px">
        <div data-options="region:'west',title:'',split:true" style="width:65%;">
            <table id="standard_list"></table>
        </div>
        <div data-options="region:'east',title:'',split:true" style="width:35%;">
            <table id="erpGood_list"></table>
        </div>
    </div>
    <%--<div style="width:55%;height: 300px;float: left">--%>

    <%--</div>--%>
    <%--<div style="width:40%;height: 300px;float: left">--%>

    <%--</div>--%>
    <input type="hidden" name="status" id="status" value="1"/>
    <input type="hidden" name="image_hash"  value=""/>
    <input type="hidden" name="good_pic" id="good_pic" value="1"/>
    <input type="hidden" name="egood_pic" id="egood_pic" value="1"/>
    <input type="hidden" name="good_id" id="good_id" value="1"/>
    <input type="hidden" name="opt" id="opt" value=""/>
</form>
<div style="width: 100%">
    <div id="dlg-buttons2" style="width: 15%;height: 20px; line-height: 30px;margin-left: 850px;margin-top: 30px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$.closeWin();"
           iconcls="icon-cancel" style="margin-left: 10px">取消</a>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width: 80%; height: 90%; padding: 10px 20px;"
     closed="true">
    <table id="list_erp"></table>
    <div id="selectForm2" name="selectForm2" style="height: 35px">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
            <div style="float: left;padding-left: 5px">
                <label class="mytitle">菜品类别:</label>
                <select class="easyui-combobox easyui-validatebox" id="class_id2" name="class_id" style="width:100px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <option value="" selected="true">全部</option>
                    <c:forEach var="item" items="${classtypelist}">
                        <option value="${item.class_id}">${item.class_name}</option>
                    </c:forEach>
                </select>
                <label class="mytitle" style="padding-left: 15px">是否上架:</label>
                <select class="easyui-combobox easyui-validatebox" id="status2" name="status" style="width:80px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <option value="" >全部</option>
                    <option value="1" selected="true">是</option>
                    <option value="2">否</option>
                </select>
                <input class="easyui-textbox" name="good_name" id="good_name2" style="width:150px"
                       data-options="label:'菜品名称：',prompt:'菜品名称模糊查询'">
                <a href="###" id="findBtn2" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
                <a href="###" id="refreshBtn2" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
            </div>
        </div>
    </div>

    <div id="tb" style="height: 25px">
        <span style="font-size: 12px;font-weight: bold;line-height: 15px;color: #777;line-height: 16px;float: left;margin-top: 5px" >规格</span>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right" onclick="addStandard_list()">添加</a>
    </div>

    <div id="tb2" style="height: 25px">
        <span style="font-size: 12px;font-weight: bold;line-height: 15px;color: #777;line-height: 16px;float: left;margin-top: 5px" >ERP菜品库</span>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right" onclick="addErpList()">添加</a>
    </div>
</div>



</body>
</html>
