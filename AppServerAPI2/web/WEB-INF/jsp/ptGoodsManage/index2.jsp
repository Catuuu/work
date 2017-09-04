
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
        var indexdd ;
        var stores_add=${stores_id} ;
        $(function () {
            $('#btn').linkbutton('disable');
            $("#bc").on("click", function() {
                saveprint();
            });
            $('#tabs').tabs({
                onSelect: function(title){
                    if(title=="单个添加"){
                        $("#bc").unbind('click');
                        $("#bc").on("click", function() {
                            saveprint();
                        });

                    }else if(title=="批量添加"){
                        getGoodList();
                        $("#bc").unbind('click');
                        $("#bc").on("click", function() {
                            saveprint2();
                        });
                    }
                }
            })

            $('#goodTabs').tabs({
                onSelect: function(title) {
                    if (title == "平台") {
                        $("#selectForm").serializeSelectJson("List");
                    } else if (title == "饿了么") {
                        var stores_id = $('#stores_idc').val();
                        var brand_id = $('#brand_idc').val();
                        var stores_name = $('#stores_namec').val();
                        var stores_class_id = $('#stores_class_idc').val();
                        if (!validateStr(stores_class_id)) {
                            $.messager.show({
                                title: '提示信息',
                                msg: '请先选择类别',
                                showType: 'show'
                            });
                            return false;
                        }
                        greatEGrid(stores_class_id, stores_name);

                        } else {

                        }
                    }
            })

            $('#List').datagrid({
                url: '/PtGoodsManage/getGoodsList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'a.sgl_id',
                sortOrder: 'desc',
                idField: 'sgl_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: '门店品牌明细管理',
                nowrap: "true",
                pageSize: 15,
                selectOnCheck: true,
                checkOnSelect: true,
                singleSelect:false,
                toolbar:'#selectForm',
//                queryParams:{
//                    brand_id:brand_id,
//                    stores_id:stores_id,
//                    class_id:class_id,
//                    good_id:good_id
//                },
//                onClickCell: function(rowIndex, field, value){
//                    if (lastIndex != rowIndex){
//                        $('#List').datagrid('endEdit', lastIndex);
//                        $('#List').datagrid('beginEdit', rowIndex);
//                    }
//                    lastIndex = rowIndex;
//                },
                onDblClickRow: function (rowIndex, rowData) {
                    edit(rowData.sgl_id,rowData.bd_id,rowData.st_id);
                },
                columns: [[
//                    { field: 'sgl_id', title: 'ID', width: '10%', align: 'center',checkbox:true,hidden:false},
                    { field: 'image_hash', title: 'ih', width: '10%', align: 'center',hidden:true},
                    { field: 'good_pic', title: '美团图片url', width: '10%', align: 'center',hidden:true},
                    { field: 'stores_brand_id', title: 'sb', width: '10%', align: 'center',hidden:true},
//                    { field: 'elem_restaurant_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'good_info', title: 'gi', width: '10%', align: 'center',hidden:true},
                    { field: 'bd_id', title: 'bd', width: '10%', align: 'center',hidden:true},
                    { field: 'food_category_id', title: 'fc', width: '10%', align: 'center',hidden:true},
                    { field: 'meituan_cat_id', title: 'mt', width: '10%', align: 'center',hidden:true},
                    { field: 'st_id', title: 'st', width: '10%', align: 'center',checkbox:true},
                    { field: 'sgl_id', title: 'ID', width: '10%', align: 'center'},
                    { field: 'good_name', title: '商品名称', width: '15%', align: 'center' },
                    { field: 'name', title: '店铺', width: '15%', align: 'center' },
                    { field: 'brand_name', title: '品牌', width: '15%', align: 'center' },
                    { field: 'class_name', title: '类别', width: '10%', align: 'center' },
//                    { field: 'elem_count', title: '饿了么', width: '7%', align: 'center'},
//                    { field: 'mt_count', title: '美团', width: '5%', align: 'center'},
                    { field: 'wx_count', title: '微信', width: '5%', align: 'center'},
                    { field: 'food_id', title: '饿了么', width: '7%', align: 'center',formatter:function(value,row){
                        if(!validateStr(value)){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=elemeGoodsAll('+row.sgl_id+','+row.food_category_id+',"1") style="text-decoration:none;color: red">绑定</a>'
                        }else if(row.type==2){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=elemeGoodsAll('+row.sgl_id+','+row.food_category_id+',"2") style="text-decoration:none;color: green" >更新</a>';
                        }else{
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=elemeGoodsAll('+row.sgl_id+','+row.food_category_id+',"3") style="text-decoration:none;color: green" >解绑</a>'
                        }
                    }},
                    { field: 'mt_isband', title: '美团', width: '5%', align: 'center',formatter:function(value,row){
                        if(!validateStr(value)){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=mtGoodsAll('+row.sgl_id+',"'+row.meituan_cat_id+'","1") style="text-decoration:none;color: red">绑定</a>'
                        }else if(row.type==2){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=mtGoodsAll('+row.sgl_id+',"'+row.meituan_cat_id+'","2") style="text-decoration:none;color: green" >更新</a>';
                        }else{
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=mtGoodsAll('+row.sgl_id+',"'+row.meituan_cat_id+'","3",'+row.stores_brand_id+') style="text-decoration:none;color: green" >解绑</a>'
                        }
                    }},
                    { field: 'all_count', title: '总数', width: '5%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                            }
                        }
                    },
                    { field: 'market_price', title: '价格', width: '5%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                                precision: 2
                            }
                        }
                    },
                    { field: 'cz', title: '操作', width: '5%', align: 'center',
                        formatter:function(value,row){
                            return'<a style="color:red;" href="###" onclick=deleteGood('+row.sgl_id+')>删除</a>'
                        }
                    }
                ]]

            });

            //规格表
            var lastIndex;
            $('#standard_list').datagrid({
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
                    $('#standard_list').datagrid('endEdit', lastIndex);
                    $('#standard_list').datagrid('beginEdit', rowIndex);
                    lastIndex = rowIndex;
                },
                onClickRow:function(rowIndex){
                    $('#standard_list').datagrid('acceptChanges');
                    if (rowIndex==0){
                        $('#erpGood_list').datagrid('loadData', datajson.a)
                    }else if(rowIndex==1){
                        $('#erpGood_list').datagrid('loadData', datajson.b)
                    }else if(rowIndex==2){
                        $('#erpGood_list').datagrid('loadData', datajson.c);
                    }else if(rowIndex==3){
                        $('#erpGood_list').datagrid('loadData', datajson.d)
                    }else if(rowIndex==4){
                        $('#erpGood_list').datagrid('loadData', datajson.e)
                    }else if(rowIndex==5){
                        $('#erpGood_list').datagrid('loadData', datajson.f)
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
                            if (value==1){
                                return'<a style="color:red;" href="###" onclick=deleteStandard()>删除</a>'
                            }

                        }
                    }
                ]]
            });


            //rep菜品表
            var lastIndex;
            $('#erpGood_list').datagrid({
                methord: 'post',
                sortName: 'g.good_id',
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
                        return '<span onclick=deleList2(indexdd) style="color: red;cursor:pointer" class="easyui-linkbutton" >删除</span>';
                    }}
                ]],
                onClickCell: function(rowIndex, field, value){
                    $('#erpGood_list').datagrid('acceptChanges');
                    var data = $('#erpGood_list').datagrid('getData').rows;
                    if (indexdd==0){
                        datajson.a=data;
                    }else if(indexdd==1){
                        datajson.a=data;

                    }else if(indexdd==2){
                        datajson.c=data;

                    }else if(indexdd==3){
                        datajson.d=data;

                    }else if(indexdd==4){
                        datajson.e=data;
                    }else if(indexdd==5){
                        datajson.f=data;
                    }
                    if (lastIndex != rowIndex){
                        if (field != "good_count"){
                            return;
                        }
                        $('#erpGood_list').datagrid('beginEdit', rowIndex);
                        $('#erpGood_list').datagrid('endEdit', lastIndex);

                    }
                    lastIndex = rowIndex;
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
                        url:'/PtGoodsManage/getStoresClass?brand_id='+n+'&stores_id='+stores_add,
                        panelWidth:'200px',
                        panelHeight:'300px',
                        valueField:'id',
                        textField:'text'
                    });
                }
            });

//            $('#stores_brand_idc').combotree({
//                url:'/PtGoodsManage/getStoresCombobox',
//                panelWidth:'200px',
//                panelHeight:'300px',
//                valueField:'id',
//                textField:'text'
//            });
//
//            $("#stores_brand_idc").combotree({
//
//                onChange: function (n, o) {
//                    $("#selectForm").serializeSelectJson("List");
//
//                }
//            });

            //查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("List");
            });

            $("#refreshBtn").click(function () {
                $(':input','#selectForm').not(':button,:submit,:reset') .val('');
                $(':input','#selectForm').not(':button,:submit,:reset') .removeAttr('checked');
                $("#selectForm").serializeSelectJson("List");
            });



            //批量模块
            $('#stores_brand_id').combotree({
                url:'/PtGoodsManage/getStoresCombobox',
                panelWidth:'200px',
                panelHeight:'300px',
                valueField:'id',
                textField:'text'
            });

            $('#stores_brand_id3').combotree({
                url:'/PtGoodsManage/getStoresCombobox',
                panelWidth:'200px',
                panelHeight:'300px',
                valueField:'id',
                textField:'text'
            });

            $("#stores_brand_id").combotree({

                onChange: function (n, o) {
                    $("#selectForm3").serializeSelectJson("List_goods");

                }
            });

            $("#stores_brand_id3").combotree({
                onChange: function (n, o) {
                    getGoodList(n);
                }
            });
            //查询
            $("#findBtn3").click(function () {
                $("#selectForm3").serializeSelectJson("List_goods");
            });

            $("#refreshBtn3").click(function () {
                $(':input','#selectForm3').not(':button,:submit,:reset') .val('');
                $(':input','#selectForm3').not(':button,:submit,:reset') .removeAttr('checked');
                $("#selectForm3").serializeSelectJson("List_goods");
            });






        });

        //规格删除
        function deleteStandard() {
            var row = $('#standard_list').datagrid('getSelected');
            if (row){
                var index = $('#standard_list').datagrid('getRowIndex', row);
                $('#standard_list').datagrid('deleteRow', index);
                if (index==0){
                    datajson.a=datajson.b;
                    datajson.b=datajson.c;
                    datajson.c=datajson.d;
                    datajson.d=datajson.e;
                    datajson.e=datajson.f;
                    datajson.f=[];
                    $('#erpGood_list').datagrid('loadData', datajson.a)

                }else if(index==1){
                    datajson.b=datajson.c;
                    datajson.c=datajson.d;
                    datajson.d=datajson.e;
                    datajson.e=datajson.f;
                    datajson.f=[];
                    $('#erpGood_list').datagrid('loadData', datajson.b)

                }else if(index==2){
                    datajson.c=datajson.d;
                    datajson.d=datajson.e;
                    datajson.e=datajson.f;
                    datajson.f=[];
                    $('#erpGood_list').datagrid('loadData', datajson.c)

                }else if(index==3){
                    datajson.d=datajson.e;
                    datajson.e=datajson.f;
                    datajson.f=[];
                    $('#erpGood_list').datagrid('loadData', datajson.d)

                }else if(index==4){
                    datajson.e=datajson.f;
                    datajson.f=[];
                    $('#erpGood_list').datagrid('loadData', datajson.e)
                }else if(index==5){
                    datajson.f=[];
                    $('#erpGood_list').datagrid('loadData', datajson.f)
                }
            }
        }



        //分类管理width: 60%; height: 80%;
        function queryGoods(){
            $('body').openWin({
                url:'/PtGoodsManage/classIndex2' ,
                width:'80%',
                height:'80%',
                title:'分类管理'
            });
        }

//        function queryGoods(){
//            $('body').openWin({
//                url:'/PtGoodsManage/text' ,
//                width:'80%',
//                height:'80%',
//                title:'测试'
//            });
//        }

        //修改
        function edit(sgl_id,brand_id,stores_id){
            $('body').openWin({
                url:'/PtGoodsManage/edit?sgl_id='+sgl_id+'&brand_id='+brand_id+'&stores_id='+stores_id ,
                width:'60%',
                height:'75%',
                title:'修改'
            });
        }

        function addGoods() {
            if(!validateStr(stores_add)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择店铺',
                    showType:'show'
                });
                return false;
            }
            $('#standard_list').datagrid('loadData', []);
            $('#erpGood_list').datagrid('loadData', []);
            datajson={"a":[],"b":[],"c":[],"d":[],"e":[],"f":[]};
            $("#dlg2").dialog("open").dialog('setTitle', '添加平台商品信息');
            $(':input','#fm2').not(':button,:submit,:reset') .val('');
            $('input:radio[name=status]')[1].checked = true;
            $("#mt").attr("src","/static/images/uploadbg.png");
            $("#ele").attr("src","/static/images/uploadbg.png");
            $("#status").val("1");
            $("#opt").val("add");
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

        function sfbc(stasu,isok) {
            if(stasu=='a'){
                $("#status").val(isok);
                if(isok=='2'){
                    $('input:radio[name=vip_discounts]')[0].checked = true;
                }else{
                    $('input:radio[name=vip_discounts]')[1].checked = true;
                }
            }else{
                $("#status").val(isok);
                if(isok=='2'){
                    $('input:radio[name=status]')[0].checked = true;
                }else if(isok=='1'){
                    $('input:radio[name=status]')[1].checked = true;
                }else{
                    $('input:radio[name=status]')[2].checked = true;
                }
            }

        }
        
        function addStandard_list() {
            var length = $('#standard_list').datagrid('getRows').length;
            $('#standard_list').datagrid('acceptChanges');
            $('#standard_list').datagrid('endEdit', lastIndex);
            $('#standard_list').datagrid('appendRow',{
//                standard_name:'',
                count:'1',
                current_price:'',
                box_price:'2',
                box_count:'1',
                cz:'1'
            });
            $('#standard_list').datagrid('hideColumn', 'standard_name');
            if(length>0){
                $('#standard_list').datagrid('showColumn', 'standard_name');
            }
            var lastIndex = $('#standard_list').datagrid('getRows').length-1;
            $('#standard_list').datagrid('selectRow', lastIndex);
            $('#standard_list').datagrid('beginEdit', lastIndex);
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


        //添加原料
        function addList2(index) {
            var row = $('#list_erp').datagrid('getSelected');
            var row2 ={};
            if (index==0){
                if (datajson.a!=null&&datajson.a.length>0){
                    for (var i =0;i<datajson.a.length;i++){
                        if(row.good_id==datajson.a[i].good_id){
                            $.messager.show({
                                title:'提示信息',
                                msg:'已经存在改菜品了',
                                showType:'show'
                            });
                            return false;
                        }
                    }
                }
                row2 = {"good_id":row.good_id,"good_count":1,"class_id":row.class_id,"good_name":row.good_name};
                datajson.a.push(row2);
                $('#erpGood_list').datagrid('loadData', datajson.a);
            }else if(index==1){
                if (datajson.b!=null&&datajson.b.length>0){
                    for (var i =0;i<datajson.b.length;i++){
                        if(row.good_id==datajson.b[i].good_id){
                            $.messager.show({
                                title:'提示信息',
                                msg:'已经存在改菜品了',
                                showType:'show'
                            });
                            return false;
                        }
                    }
                }
                row2 = {"good_id":row.good_id,"good_count":1,"class_id":row.class_id,"good_name":row.good_name};
                datajson.b.push(row2);
                $('#erpGood_list').datagrid('loadData', datajson.b)
            }else if(index==2){
                if (datajson.c!=null&&datajson.c.length>0){
                    for (var i =0;i<datajson.c.length;i++){
                        if(row.good_id==datajson.c[i].good_id){
                            $.messager.show({
                                title:'提示信息',
                                msg:'已经存在改菜品了',
                                showType:'show'
                            });
                            return false;
                        }
                    }
                }
                row2 = {"good_id":row.good_id,"good_count":1,"class_id":row.class_id,"good_name":row.good_name};
                datajson.c.push(row2);
                $('#erpGood_list').datagrid('loadData', datajson.c);
            }else if(index==3){
                if (datajson.d!=null&&datajson.d.length>0){
                    for (var i =0;i<datajson.d.length;i++){
                        if(row.good_id==datajson.d[i].good_id){
                            $.messager.show({
                                title:'提示信息',
                                msg:'已经存在改菜品了',
                                showType:'show'
                            });
                            return false;
                        }
                    }
                }
                row2 = {"good_id":row.good_id,"good_count":1,"class_id":row.class_id,"good_name":row.good_name};
                datajson.d.push(row2);
                $('#erpGood_list').datagrid('loadData', datajson.d)
            }else if(index==4){
                if (datajson.e!=null&&datajson.e.length>0){
                    for (var i =0;i<datajson.e.length;i++){
                        if(row.good_id==datajson.e[i].good_id){
                            $.messager.show({
                                title:'提示信息',
                                msg:'已经存在改菜品了',
                                showType:'show'
                            });
                            return false;
                        }
                    }
                }
                row2 = {"good_id":row.good_id,"good_count":1,"class_id":row.class_id,"good_name":row.good_name};
                datajson.e.push(row2);
                $('#erpGood_list').datagrid('loadData', datajson.e)
            }else if(index==5){
                if (datajson.f!=null&&datajson.f.length>0){
                    for (var i =0;i<datajson.f.length;i++){
                        if(row.good_id==datajson.f[i].good_id){
                            $.messager.show({
                                title:'提示信息',
                                msg:'已经存在改菜品了',
                                showType:'show'
                            });
                            return false;
                        }
                    }
                }
                row2 = {"good_id":row.good_id,"good_count":1,"class_id":row.class_id,"good_name":row.good_name};
                datajson.f.push(row2);
                $('#erpGood_list').datagrid('loadData', datajson.f)
            }

//            var good_id = row.good_id;
//            var good_name = row.good_name;
//            var unit = row.unit;
//            var data = $('#List2').datagrid('getData');
//            var rows = data.rows;
//            var isok = "no";
//            if(rows!=null&&rows.length>0){
//                for(var i=0;i<rows.length;i++){
//                    if(good_id==rows[i].good_id){
//                        isok ="ok";
//                    }
//                }
//            }
//            if(isok=="ok"){
//                $.messager.show({title:'提示信息', msg:'商品已经有该菜品了',showType:'show'});
//                return false;
//            }
//            $('#List2').datagrid('endEdit', lastIndex);
//            $('#List2').datagrid('appendRow',{
//                good_id:good_id,
//                good_name:good_name,
//                class_id:row.class_id,
//                erp_good_id:1,
//                good_count:1,
//            });
//            var lastIndex = $('#List2').datagrid('getRows').length-1;
//            $('#List2').datagrid('selectRow', lastIndex);
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
                    indexdd = index;
                    addList2(index);
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


        function deleList2(i) {
            var row = $('#erpGood_list').datagrid('getSelected');
            if (row!=null&&row!=""&&row!=undefined){
                var index = $('#erpGood_list').datagrid('getRowIndex', row);
                $('#erpGood_list').datagrid('deleteRow', index);
                var data = $('#erpGood_list').datagrid('getData').rows;
                if (i==0){
                    datajson.a=data;
                }else if(i==1){
                    datajson.b=data;

                }else if(i==2){
                    datajson.c=data;

                }else if(i==3){
                    datajson.d=data;

                }else if(i==4){
                    datajson.e=data;
                }else if(i==5){
                    datajson.f=data;
                }
            }
        }

        //保存
        function saveprint() {
            $('#standard_list').datagrid('acceptChanges');
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
            var rows = $('#standard_list').datagrid('getData').rows;
            var data1 = JSON.stringify(rows);
            var erpdata = JSON.stringify(datajson);
            if(!validateStr(stores_class_id)){
                $.messager.alert("提示信息", "请选择类别");
                return false;
            }
            if(!validateStr(good_name)){
                $.messager.alert("提示信息", "请填写商品名称");
                return false;
            }
            $.ajax({
                url: "/PtGoodsManage/saveGood",
                data: {stores_class_id:stores_class_id,market_price:market_price,good_name:good_name,
                    good_info:good_info,opt:opt,egood_pic:egood_pic,good_pic:good_pic,status:status,brand_id:brand_id,
                    stores_id:stores_id,stores_brand_id:stores_brand_id, standardList:data1,erpList:erpdata},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.show({
                            title:'提示信息',
                            msg:'操作成功',
                            showType:'show'
                        });
                        $('#dlg2').dialog('close');
                        $("#List").datagrid("reload");
                    }else if(result.status == "2"){
                        $.messager.alert("提示信息", "名称不能重复");
                    }
                    else{
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        function deleteGood(sgl_id) {
            if(validateStr(sgl_id)){
                $.messager.confirm('系统提示', '确定删除吗?', function(r){
                    if (r){
                        $.ajax({
                            url: "/PtGoodsManage/deleteGood",
                            data: {sgl_id:sgl_id},
                            type: 'POST',
                            success: function (result) {
                                if (result.status == "1") {
                                    $.messager.show({
                                        title:'提示信息',
                                        msg:'操作成功',
                                        showType:'show'
                                    });
                                    $("#List").datagrid("reload");
                                }
                                else{
                                    $.messager.alert("提示信息", "操作失败");
                                }
                            }
                        });
                    }
                });
            }
        }

        //子页面刷新
        function reloadGread() {
            $.messager.show({
                title:'提示信息',
                msg:'操作成功',
                showType:'show'
            });
            $("#List").datagrid("reload");
        }

        //非空验证
        function validateStr(text) {
            var isok = true;
            if(text==null||text==""||text==undefined){
                isok = false;
            }
            return isok ;
        }


//        <!--******************************************************************************************************
//                **********************************************************************************************************
//                ************************************************批量添加Grid*****************************************************
//                **************************************************************************************************************
//                ************************************************************************************************************
//                ***********************************************************************************************************-->

        function getGoodList(stores_brand_id) {
            $('#List_goods').datagrid({
                url: '/PtGoodsManage/getGoodsList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'a.sgl_id',
                sortOrder: 'desc',
                idField: 'sgl_id',
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                nowrap: "true",
                pageSize: 30,
                toolbar:'#selectForm3',
                pagination:true,
                selectOnCheck: true,
                checkOnSelect: true,
                singleSelect:false,
                onDblClickRow: function (rowIndex, rowData) {
                },
                columns: [[
                    { field: 'sgl_id', title: 'ID', width: '10%', align: 'center',checkbox:true},
                    { field: 'image_hash', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'good_pic', title: '美团图片url', width: '10%', align: 'center',hidden:true},
                    { field: 'stores_brand_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'good_info', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'food_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'brand_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'food_category_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'meituan_cat_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'good_name', title: '商品名称', width: '20%', align: 'center' },
                    { field: 'name', title: '店铺', width: '20%', align: 'center' },
                    { field: 'brand_name', title: '品牌', width: '10%', align: 'center' },
                    { field: 'class_name', title: '类别', width: '20%', align: 'center' },
                    { field: 'bd_id', title: '添加类别', width: '10%', align: 'center',formatter:function(value,row){
                        if(validateStr(stores_brand_id)){
//                            return '<input  name="stores_class_id"  class="easyui-combobox" style="width:20px;" data-options="
//                                valueField: 'id',
//                                textField:'text',
//                                url: '/PtGoodsManage/getClassCombobox?stores_brand_id='+stores_brand_id,
//                            ">';
                            getClass_name(stores_brand_id,row.class_name,row.sgl_id);
                            return '<span id="'+row.sgl_id+'"></span>';

                        }
                    }  },
                    { field: 'market_price', title: '价格', width: '10%', align: 'center'},
                ]]

            });
        }

        function getClass_name(stores_brand_id,class_name,sgl_id) {
            $.ajax({
                url: "/PtGoodsManage/getClassName",
                data: {stores_brand_id:stores_brand_id,class_name:class_name},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                         $("#"+sgl_id).text(result.obj.class_name);
                    }
                }
            });
        }

//        function getSelect() {
//            return "<input  name='stores_class_id'  class='easyui-combobox' style='width:20px;' data-options="
//                                valueField: "id",
//                                textField:'text',
//                                url:"/PtGoodsManage/getClassCombobox?stores_brand_id="+stores_brand_id,
//                            ">";
//        }

        function saveprint2() {
            var rows = $('#List_goods').datagrid('getChecked');
            if(!validateStr(rows)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择一条记录',
                    showType:'show'
                });
                return false;
            }
//            var stores_brand_id2 = $("#stores_brand_id").val();
//            if(!validateStr(stores_brand_id2)){
//                $.messager.show({
//                    title:'提示信息',
//                    msg:'请先选择店铺',
//                    showType:'show'
//                });
//                return false;
//            }
            var stores_brand_id = $("#stores_brand_id3").val();
            if(!validateStr(stores_brand_id)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择要添加的店铺',
                    showType:'show'
                });
                return false;
            }
            start();
            var datas = JSON.stringify(rows);
            $.ajax({
                url: "/PtGoodsManage/saveGoodList",
                data: {jsondata:datas,stores_brand_id:stores_brand_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.progress('close');
                        $.messager.alert("提示信息", "操作成功");
                        $("#dlg2").dialog("close");
                        $("#List").datagrid("reload");

                    }
                    else{
                        $.messager.progress('close');
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        function refreshList() {
            $('#selectForm').serializeSelectJson('List');
        }

        function start(){
            var win = $.messager.progress({
                title:'请稍等',
                msg:'保存中...'
            });
        };



        //饿了么反向绑定表格
        function greatEGrid(stores_class_id,stores_name) {
            $('#eList').datagrid({
                url: '/PtGoodsManage/getElemeGoods?stores_class_id='+stores_class_id,
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'a.sgl_id',
                sortOrder: 'desc',
                idField: 'sgl_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: '门店品牌明细管理',
                nowrap: "true",
                pageSize: 15,
                pagination: false,
//            onDblClickRow: function (rowIndex, rowData) {
//                edit(rowData.sgl_id,rowData.bd_id,rowData.st_id);
//            },
                columns: [[
                    { field: 'id', title: 'ID', width: '10%', align: 'center'},
                    { field: 'name', title: '商品名称', width: '15%', align: 'center' },
                    { field: 'names', title: '店铺', width: '15%', align: 'center',formatter:function(value,row){
                            return stores_name;
                    } },
                    { field: 'description', title: '饿了么绑定状态', width: '20%', align: 'center',formatter:function(value,row){
//                        elemeGoodQuery(stores_id,brand_id,stores_name);
                        return '<span id="'+row.id+'" name="dept" ></sapn>'
                    } }

                ]]

            });
        }

        function elemeGoodsAll(sgl_id,food_category_id,type) {
            if (!validateStr(food_category_id)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先绑定类别',
                    showType:'show'
                });
                return false;
            }
            $.ajax({
                url: "/PtGoodsManage/elemeGoodsAll",
                data: {sgl_id:sgl_id,food_category_id:food_category_id,type:type},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        refreshList();
                        $.messager.show({
                            title:'提示信息',
                            msg:'操作成功',
                            showType:'show'
                        });
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }



        function mtGoodsAll(sgl_id,meituan_cat_id,type,stores_brand_id) {
            if (!validateStr(meituan_cat_id)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先绑定类别',
                    showType:'show'
                });
                return false;
            }
            $.ajax({
                url: "/PtGoodsManage/mtGoodsAll",
                data: {sgl_id:sgl_id,category_name:meituan_cat_id,type:type,stores_brand_id:stores_brand_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        refreshList();
                        $.messager.show({
                            title:'提示信息',
                            msg:'操作成功',
                            showType:'show'
                        });
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }
        
        function mtPlBind() {
            var rows = $('#List').datagrid('getChecked');
            if(!validateStr(rows)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择一条记录',
                    showType:'show'
                });
                return false;
            }
            start();
            var datas=[];
            if (rows!=null&&rows.length>0){
                for (var i = 0;i<rows.length;i++){
                    var data = {"sgl_id":rows[i].sgl_id,"class_name":rows[i].class_name,"stores_brand_id":rows[i].stores_brand_id};
                    datas.push(data);
                }

            }
            var allData = JSON.stringify(datas);
            $.ajax({
                url: "/PtGoodsManage/mtPlBind",
                data: {allData:allData},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.progress('close');
                        refreshList();
                        $.messager.show({
                            title:'提示信息',
                            msg:'操作成功',
                            showType:'show'
                        });
                    } else {
                        $.messager.progress('close');
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
        门店商品管理</h2>
</div>
<div class="easyui-layout" style="width:100%;height:100%;">
    <!--  =========================所有商铺=====================   -->
    <div data-options="region:'west',title:'',split:true" style="width:15%;height: 100%" id="chuFang">
        <div style="height: 30px;border-style: solid;border-width: 0 0 1px;border-color: #ddd;margin-bottom: 0px">
            <div style="float: right;margin-right: 0px;height: 20px;margin-top: -10px;width: 50%">
                <a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"  onclick="queryGoods()" id="bd2">分类管理</a>
            </div>
        </div>
        <ul id="tt2" class="easyui-tree" data-options="
                        url: '/PtGoodsManage/getClassTree?type=1',
                        method: 'post',
                        animate: false,
                        onClick: function(node){
                            var isok =node.isok;
                           if(isok=='0'){
                                $('#tt2').tree('collapseAll');
                           }
                           $('#tt2').tree('expandAll', node.target);
                            var rows = node.children;
                            var parent = $('#tt2').tree('getParent', node.target);
                            if(isok=='0'){
                                $('#stores_idc').val(node.id);
                                stores_add = node.id;
                                $('#stores_class_idc').val('');
                                 $('#brand_idc').val('');
                                 $('#btn').linkbutton('enable');
                                 $('#btn2').linkbutton('enable');
                                refreshList();
                            }else if(isok =='1'){
                                 $('#stores_class_idc').val('');
                                 $('#brand_idc').val(node.id);
                                $('#stores_idc').val(parent.id);
                                $('#stores_namec').val(parent.text);
                                stores_add = parent.id
                                $('#btn').linkbutton('enable')
                                $('#btn2').linkbutton('enable');
                                refreshList();
                            }else if(isok =='1a'){
                                $('#stores_class_idc').val('');
                                 $('#brand_idc').val(node.id);
                                $('#stores_idc').val(${stores_id});

                                <%--stores_add = ${stores_id};--%>
                                $('#btn').linkbutton('enable');
                                $('#btn2').linkbutton('enable');
                                refreshList();
                            }else {
                                $('#brand_idc').val('');
                                $('#stores_brand_idc').val('');
                                $('#stores_class_idc').val(node.id);
                                 refreshList();
                            }


                        }

                    "></ul>
    </div>
    <%--<div data-options="region:'center',title:'',iconCls:'icon-ok'" style="width: 90%;height: 100%;">--%>
        <%----%>
    <%--</div>--%>
    <div class="easyui-layout" data-options="region:'center',title:'',iconCls:'icon-ok'" style="overflow: hidden;border:0;height: 95%">
        <div data-options="region:'north'" style="height: 30px"  id="selectForm">
            <%--<input class="easyui-textbox" name="good_name" id="good_namec" style="width:250px"--%>
                   <%--data-options="label:'商品名称：',prompt:'商品名称模糊查询'">--%>
            <%--<label  class="mytitle">门店:</label>--%>
            <input id="stores_idc" name="stores_id"   type="hidden" value="">
            <input id="stores_class_idc" name="stores_class_id" type="hidden"  value="">
                <input id="brand_idc" name="brand_id" type="hidden"  value="">
                <input id="stores_namec" name="stores_namec" type="hidden"  value="">
            <%--<a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>--%>
            <%--<a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>--%>
            <a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right;margin-right: 20px;margin-top: 5px;height: 20px" onclick="addGoods()" id="btn">添加</a>
                <a href="###" class="easyui-linkbutton" data-options="iconCls:'icon icon-6',plain:true" style="float: right;margin-right: 20px;margin-top: 5px;height: 20px" onclick="mtPlBind()" id="btnMt">美团批量绑定(更新)</a>
                <%--<a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right;margin-right: 20px;margin-top: 5px;height: 20px" onclick="elemBindGoods()" id="btnE">饿了么批量绑定</a>--%>
        </div>
        <div class="easyui-tabs" style="width:100%;height: 97%;" id="goodTabs">
            <div title="平台" style="display:none;">
                <table id="List"></table>
            </div>
            <div title="饿了么" >
                <table id="eList"></table>
            </div>
            <div title="美团 " >
                <table id="mtList"></table>
            </div>
        </div>
    </div>


</div>

<div id="tb" style="height: 25px">
    <span style="font-size: 12px;font-weight: bold;line-height: 15px;color: #777;line-height: 16px;float: left;margin-top: 5px" >规格</span>
    <a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right" onclick="addStandard_list()">添加</a>
</div>

<div id="tb2" style="height: 25px">
    <span style="font-size: 12px;font-weight: bold;line-height: 15px;color: #777;line-height: 16px;float: left;margin-top: 5px" >ERP菜品库</span>
    <a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right" onclick="addErpList()">添加</a>
</div>


<!--   ============================== 新增erp div、表单===============================     -->
<div id="dlg2" class="easyui-dialog" style="width: 60%; height: 80%; padding: 10px 20px;"
     closed="true" buttons="#dlg-buttons2">
    <div class="easyui-tabs" style="width:100%;height: 100%;border:0" id="tabs">
        <div title="单个添加">
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
                    <input name="good_name" class="easyui-validatebox easyui-textbox" id="good_name" required="true"  style="width:280px"/>
                    <span id="check1" style="color: red"></span>
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
        </div>
        <div title="批量添加" style="display:none;">
            <table id="List_goods"></table>

        </div>
    </div>

</div>
<div id="dlg-buttons2">
    <a href="javascript:void(0)" class="easyui-linkbutton"  iconcls="icon-save"  id="bc">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg2').dialog('close')"
       iconcls="icon-cancel">取消</a>
</div>
</div>


<div id="dlg" class="easyui-dialog" style="width: 40%; height: 60%; padding: 10px 20px;"
     closed="true">
    <table id="list_erp"></table>
</div>

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

<div id="selectForm3">
    <div class="easyui-panel" style="background:#fff;height:100%;">
        <div style="float: left;padding-left: 5px">
            <input class="easyui-textbox" name="good_name" id="good_name3" style="width:250px"
                   data-options="label:'商品名称：',prompt:'商品名称模糊查询'">
            <label  class="mytitle">门店:</label>
            <input id="stores_brand_id" name="stores_brand_id"  class="easyui-textbox easyui-validatebox" style="width:160px;" value="">
            <a href="###" id="findBtn3" class="easyui-linkbutton " iconCls="icon-search">查询</a>
            <a href="###" id="refreshBtn3" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
            <label  class="mytitle">添加到:</label>
            <input id="stores_brand_id3" name="stores_brand_id3"  class="easyui-textbox easyui-validatebox" style="width:160px;" >
        </div>
    </div>
</div>

</body>
</html>
