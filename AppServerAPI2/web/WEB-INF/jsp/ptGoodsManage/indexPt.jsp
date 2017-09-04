
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
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
        var stores_id;
        var brand_id;
        var class_ids;
        $(function () {
            $('#btn').linkbutton('disable');
            var status = ${status};
             stores_id=${stores_id};
             brand_id =${brand_id};
             if(status=='1'){
                 $('#layoutAll').layout('remove', 'west');
             }else{
                 getStoresGoodsClass(brand_id,stores_id);
                 if(stores_id!=null&&stores_id!=""&&stores_id!=undefined){
                     $('#btn').linkbutton('enable');
                 }
             }
             var class_id = ${class_id};
             var good_id = ${good_id};
            var lastIndex;
            $('#List').datagrid({
                url: '/PtGoodsManage/GetStoresGoodsList',
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
                toolbar:'#selectForm',
                queryParams:{
                    brand_id:brand_id,
                    stores_id:stores_id,
                    class_id:class_id,
                    good_id:good_id
                },
                onClickCell: function(rowIndex, field, value){
                    if (lastIndex != rowIndex){
                        $('#List').datagrid('endEdit', lastIndex);
                        $('#List').datagrid('beginEdit', rowIndex);
                    }
                    lastIndex = rowIndex;
                },
                onAfterEdit:function (rowIndex, rowData, changes) {
                    if(changes.all_count==null||changes.all_count==""||changes.all_count=="undefined"){
                        return false;
                    }
                    //根据总数分配饿了么、美团、微信数量
                    var ecount = rowData.elem_count;
                    var mcount = rowData.mt_count;
                    var wxcount = rowData.wx_count;
                    if(changes.all_count!=null&&changes.all_count!=""&&changes.all_count!="undefined"){
                        if(changes.all_count>0){
                            var all_count =changes.all_count ;
                            wxcount = Math.floor(all_count/10);
                            all_count = all_count - wxcount;
                            if(all_count%2){
                                //不能被整除
                                ecount = Math.floor(all_count/2);//向下取整
                                mcount = Math.ceil(all_count/2) //向上取整
                            }else{
                                //能被整除
                                ecount = all_count/2;
                                mcount = ecount;
                            }
                        }else if(changes.all_count==0){
                            mcount=0;
                            ecount=0;
                            wxcount=0;
                        }
                        $('#List').datagrid('updateRow',{
                            index: rowIndex,
                            row: {
                                elem_count: ecount,
                                mt_count: mcount,
                                wx_count:wxcount
                            }
                        });
                    }
                    $.ajax({
                        url: "/PtGoodsManage/updateStoresGoods",
                        data: {sgl_id: rowData.sgl_id,
                                elem_count:ecount,
                                mt_count:mcount,
                                wx_count:wxcount,
                                all_count:rowData.all_count,
                                market_price:changes.market_price,
                                box_price:changes.box_price,
                                box_count:changes.box_count,
                                unit:rowData.unit,
                                name:rowData.good_name,
                                good_info:rowData.good_info,
                                image_hash:rowData.image_hash,
                                stores_brand_id:rowData.stores_brand_id,
                                class_name :rowData.class_name
                                },
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {

                            }
                        }
                    });

                },
                columns: [[
                    { field: 'sgl_id', title: 'ID', width: '10%', align: 'center'},
                    { field: 'image_hash', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'good_pic', title: '美团图片url', width: '10%', align: 'center',hidden:true},
                    { field: 'stores_brand_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'good_info', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'food_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'food_category_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'meituan_cat_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    { field: 'good_name', title: '商品名称', width: '15%', align: 'center' },
                    { field: 'class_name', title: '类别', width: '10%', align: 'center' },
                    { field: 'elem_count', title: '饿了么', width: '7%', align: 'center'},
                    { field: 'mt_count', title: '美团', width: '5%', align: 'center'},
                    { field: 'wx_count', title: '微信', width: '5%', align: 'center'},
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
                    { field: 'unit', title: '单位', width: '5%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'text'
                        }
                    },
                    { field: 'box_price', title: '餐盒价格', width: '7%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                                precision: 2
                            }
                        }
                    },
                    { field: 'box_count', title: '餐盒数量', width: '5%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
//                                precision: 2
                            }
                        }
                    },
//                    { field: 'status', title: '售卖状态', width: '5%', align: 'center',formatter:function(value,row){
//                        if(value=="1"){
//                            return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=sellStatus('+row.sgl_id+',"2","'+row.good_name+'","'+row.class_name+'",0) style="text-decoration:none;color: green" >售卖中</a>'
//                        }else{
//                            return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=sellStatus('+row.sgl_id+',"1","'+row.good_name+'","'+row.class_name+'",'+row.elem_count+') style="text-decoration:none;color: red" >暂停售卖</a>'
//                        }
//                    }},
                    { field: 'status2', title: '饿了么绑定', width: '8%', align: 'center',formatter:function(value,row){
                        if(row.food_category_id==''||row.food_category_id==null||row.food_category_id=="undefined"){
                            return'<span style="text-decoration:none;color: green">请先绑定类别</span>';
                        }else if(validation(row.food_id)){
                            return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=elemRemoveGoods('+row.food_id+') style="text-decoration:none;color: green" >删除</a>';
                        }else{
                            return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=elemBandGoods("'+row.food_category_id+'","'+row.good_name+'",'+row.market_price+',"'+row.good_info+'",'+row.elem_count+','+row.sgl_id+',"'+row.image_hash+'") style="text-decoration:none;color: red" >添加</a>';
                        }
                    } },
                    { field: 'mt_isband', title: '美团绑定', width: '7%', align: 'center',formatter:function(value,row){
                        if(row.meituan_cat_id==''||row.meituan_cat_id==null||row.meituan_cat_id=="undefined"){
                            return'<span style="text-decoration:none;color: green">请先绑定类别</span>';
                        }else{
                            if(value==null||value==""||value==undefined){
                                return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=mtBand('+row.sgl_id+',"'+row.good_name+'","'+row.good_info+'","'+row.good_pic+'","'+row.class_name+'") style="text-decoration:none;color: red" >添加</a>'
                            }else{
                                return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=mtDeleteBand('+row.sgl_id+') style="text-decoration:none;color: green" >删除</a>'

                            }
                        }

                    }},
                    { field: 'detete', title: '操作', width: '5%', align: 'center',formatter:function(value,row){
                        if(validation(row.food_id)||row.mt_isband==1){
                            return '<span style="color: green">请先在三方平台上删除</span>';
                        }else{
                            return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=deleteGoods('+row.sgl_id+') style="text-decoration:none;color: red" >删除</a>';
                        }
                    } }
                ]]

            });

        


            //查询
            $("#findBtn").click(function () {
                var brand_id= $('#s_brand_id').val();
                var stores_id = $('#stores_id').val();
                var good_ids = $("#good_ids").val();
                var class_id = $('#s_class_id').combobox('getValue');
                var status = $("#status").val();
                var good_name = $("#good_name").val();
                var g = $('#cc').combogrid('grid');
                g.datagrid("reload",{
                    brand_id:brand_id,
                    stores_id:stores_id,
                    good_ids:good_ids,
                    class_id:class_id,
                    status:status,
                    good_name:good_name
                });
            });

            $("#refreshBtn").click(function () {
                $('#status').combobox('setValue','');
                $('#s_class_id').combobox('setValue',"");
                $('#good_name').textbox('setValue',"");
                var brand_id= $('#s_brand_id').val();
                var stores_id = $('#stores_id').val();
                var good_ids = $("#good_ids").val();
                var g = $('#cc').combogrid('grid');
                g.datagrid("reload",{
                    brand_id:brand_id,
                    stores_id:stores_id,
                    good_ids:good_ids,
                });
            });

            $("#status").combobox({
                onChange: function (n, o) {
                    var brand_id= $('#s_brand_id').val();
                    var stores_id = $('#stores_id').val();
                    var good_ids = $("#good_ids").val();
                    var class_id = $('#s_class_id').combobox('getValue');
                    var good_name = $("#good_name").val();
                    var g = $('#cc').combogrid('grid');
                    g.datagrid("reload",{
                        brand_id:brand_id,
                        stores_id:stores_id,
                        good_ids:good_ids,
                        status:n,
                        class_id:class_id,
                        good_name:good_name
                    });
                }
            });

            $("#s_class_id").combobox({
                onChange: function (n, o) {
                    var brand_id= $('#s_brand_id').val();
                    var stores_id = $('#stores_id').val();
                    var good_ids = $("#good_ids").val();
                    var status = $("#status").val();
                    var good_name = $("#good_name").val();
                    var g = $('#cc').combogrid('grid');
                    g.datagrid("reload",{
                        brand_id:brand_id,
                        stores_id:stores_id,
                        good_ids:good_ids,
                        status:status,
                        good_name:good_name,
                        class_id:n
                    });
                }
            });

        });

        //删除商品
        function deleteGoods(sgl_id) {
            $.ajax({
                url: "/PtGoodsManage/deleteGoods",
                data: {sgl_id: sgl_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.show({
                            title:'提示信息',
                            msg:'删除成功',
                            showType:'show'
                        });
                        if(stores_id!=""&&stores_id!=null&&stores_id!=undefined){
                            getGood_ids(stores_id,brand_id);
                        }
                        $("#List").datagrid("reload");
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }
        
        function validation(str) {
            if(str==''||str==null||str=="undefined"){
                return false;
            }else{
                return true;
            }
        }


        
        //修改售卖状态
        function sellStatus(sgl_id,status,good_name,class_name,sock) {
            $.ajax({
                url: "/PtGoodsManage/sellStatus",
                data: {sgl_id: sgl_id,status:status,good_name:good_name,class_name:class_name,sock:sock},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $("#List").datagrid("reload");
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        //分类管理
        function queryGoods(){
            $('body').openWin({
                url:'/PtGoodsManage/queryGoods' ,
                width:'80%',
                height:'80%',
                title:'分类管理'
            });
        }

        function addGoods(){
           var brand_id= $('#s_brand_id').val();
           var stores_id = $('#stores_id').val();
           var good_ids = $("#good_ids").val();
           greatCombogrid(brand_id,stores_id,good_ids);
            $('#List2').datagrid('loadData',{total:0,rows:[]});//清空表格
            $('#cc').combogrid('clear');//清除下拉列表数据
           $("#dlg").dialog("open").dialog('setTitle', '添加');
        }

        //根据门店id获取已有的商品id
        function getGood_ids(stores_id,brand_id) {
            $.ajax({
                url: "/PtGoodsManage/getGood_ids",
                data: {stores_id: stores_id,brand_id:brand_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                         var good_ids = result.message;
                         $("#good_ids").val(good_ids);

                    } else {
                    }
                }
            });
        }
        
        function greatCombogrid(brand_id,stores_id,good_ids) {
            //商品下拉列表
            $('#cc').combogrid({
                panelWidth: '50%',
                panelHeight:'60%',
                idField: 'good_id',
                textField: 'good_name',
                sortName: 'good_id',
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                url: '/GoodsInfo/GetGoodsInfoLists',
                method: 'post',
                toolbar: '#selectForm2',
                pagination:true,
                multiple:true,
                queryParams:{
                    brand_id:brand_id,
                    stores_id:stores_id,
                    class_ids:class_ids,
                    good_ids:good_ids
//                    class_id:class_id,
//                    status:status,
//                    good_name:good_name
                },
                onClickRow: function (rowIndex,rowData) {
                    greatGrid2();
                    addRows(rowData);
                },
                columns: [[
                    {field:'good_id',title:'ID',width:'5%',align: 'center'},
                    {field:'good_name',title:'商品名称',width:'35%',align: 'center',sortable:true},
                    {field:'class_id',title:'商品类别',width:'15%',sortable:true,align: 'center',formatter:function(value,row){
                        <c:forEach var='item' items='${classlist}'>
                        if(row.class_id=='${item.class_id}'){
                            return '${item.class_name}';
                        }
                        </c:forEach>
                    }},
                    {field:'brand_id',title:'品牌',width:'10%',sortable:true,align: 'center',formatter:function(value,row){
                        <c:forEach var='item' items='${brandlist}'>
                        if(row.brand_id=='${item.brand_id}'){
                            return '${item.brand_name}';
                        }
                        </c:forEach>
                    }},
                    {field:'market_price',title:'商品价格',width:'15%',align: 'center',sortable:true},
                    {field:'box_price',title:'餐盒费',width:'10%',align: 'center',sortable:true},
                    {field:'status',title:'状态',width:'10%',align: 'center',sortable:true,formatter:function(value,row){
                        if (value == 1) {
                            return '<span  style="color:green">上架</span>';
                        }else{
                            return '<span  style="color:red">下架</span>';
                        }
                    }

                    }
                ]]
            });
        }
        
        function getCombobox(brand_id) {
            $('#s_class_id').combobox({
                url:"/PtGoodsManage/getCombobox?brand_id="+brand_id,
                valueField:'id',
                textField:'text'
            });
        }

        function greatGrid2() {
            var lastIndex;
            $('#List2').datagrid({
                datagrid:null,
                width: '100%',
                height:'450px',
                sortName: 'g.good_id',
                idField: 'good_id',
                singleSelect: true,//单选模式
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                //title: 'erp商品原料',
                nowrap: "true",
                pagination: false,
                onClickCell: function(rowIndex, field, value){
                    if (lastIndex != rowIndex){
                        $('#List2').datagrid('endEdit', lastIndex);
                        $('#List2').datagrid('beginEdit', rowIndex);
                    }
                    lastIndex = rowIndex;
                },
                onAfterEdit: function (rowIndex, rowData, changes) {
                    if(changes.all_count!=null&&changes.all_count!=""&&changes.all_count!="undefined"){
                        var ecount = 0;
                        var mcount = 0;
                        var wxcount = 0;
                        if(changes.all_count>0){
                            var all_count =changes.all_count ;
                            wxcount = Math.floor(all_count/10);
                            all_count = all_count-wxcount;
                            if(all_count%2){
                                //不能被整除
                                ecount = Math.floor(all_count/2);
                                mcount = Math.ceil(all_count/2)
                            }else{
                                //能被整除
                                ecount = all_count/2;
                                mcount = ecount;
                            }
                        }else if(changes.all_count==0){
                            ecount = 0;
                            mcount = 0;
                            wxcount = 0;
                        }

                        $('#List2').datagrid('updateRow',{
                            index: rowIndex,
                            row: {
                                elem_count: ecount,
                                mt_count: mcount,
                                wx_count: wxcount
                            }
                        });
                    }
                },
                columns: [[
                    { field: 'good_id', title: '商品ID', width: '40%', align: 'center',hidden:true },
                    { field: 'stores_brand_id', title: '', width: '40%', align: 'center',hidden:true },
                    { field: 'brand_id', title: '', width: '40%', align: 'center',hidden:true },
                    { field: 'stores_id', title: '', width: '40%', align: 'center',hidden:true },
                    {field:'good_name',title:'商品名称',width:'25%',align: 'center',sortable:true},
                    {field:'class_id',title:'商品类别',width:'15%',sortable:true,align: 'center',formatter:function(value,row){
                        <c:forEach var='item' items='${classlist}'>
                        if(row.class_id=='${item.class_id}'){
                            return '${item.class_name}';
                        }
                        </c:forEach>
                    }},
                    { field: 'elem_count', title: '美团', width: '5%', align: 'center'},
                    { field: 'mt_count', title: '饿了么', width: '5%', align: 'center'},
                    { field: 'wx_count', title: '微信', width: '5%', align: 'center'},
                    { field: 'all_count', title: '总数', width: '10%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
//                                precision: 2
                            }
                        }
                    },
                    { field: 'market_price', title: '价格', width: '10%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                                precision: 2
                            }
                        }
                    },
                    { field: 'unit', title: '单位', width: '10%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'text'
                        }
                    },
                    { field: 'box_price', title: '餐盒价格', width: '10%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                                precision: 2
                            }
                        }
                    },
                    { field: 'box_count', title: '餐盒数量', width: '10%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
//                                precision: 2
                            }
                        }
                    }
                ]]
            });
        }

        function addRows(rowData) {
            var rowIndex;
            var data = $('#List2').datagrid('getData');
            var rows = data.rows;
            var stores_brand_id = $("#stores_brand_id").val();
            var isok = "no";
            if(rows!=null&&rows.length>0){
                for(var i=0;i<rows.length;i++){
                    if(rowData.good_id==rows[i].good_id){
                        rowIndex =  $('#List2').datagrid('getRowIndex', rows[i]);
                        isok ="ok";
                    }
                }
            }
            if(isok=="ok"){
                $('#List2').datagrid('deleteRow',rowIndex);
                return false;
            }
            var brand_id= $('#s_brand_id').val();
            var stores_id = $('#stores_id').val();
            var stores_brand_id = $('#stores_brand_id').val();
            $('#List2').datagrid('endEdit', lastIndex);
            $('#List2').datagrid('appendRow',{
                good_id:rowData.good_id,
                brand_id:brand_id,
                stores_id:stores_id,
                stores_brand_id:stores_brand_id,
                good_name:rowData.good_name,
                class_id:rowData.class_id,
                elem_count:4950,
                mt_count:4950,
                wx_count:100,
                all_count:10000,
                unit:'份',
                box_price:rowData.box_price,
                box_count:1,
                market_price:rowData.market_price
            });
            var lastIndex = $('#List2').datagrid('getRows').length-1;
            $('#List2').datagrid('selectRow', lastIndex);
        }
        function saveprint() {
            $('#List2').datagrid('acceptChanges');
            var data = $('#List2').datagrid('getData');
            var rows = data.rows;
            var datas = JSON.stringify(rows);
            var brand_id= $('#s_brand_id').val();
            var stores_id = $('#stores_id').val();
            $.ajax({
                url: "/PtGoodsManage/saveGoods",
                data: {jsondata:datas},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        getGood_ids(stores_id,brand_id);//刷新下拉grid
                        $('#cc').combogrid('clear');
                        $.messager.alert("提示信息", "操作成功");
                        $("#dlg").dialog("close");
//                        $("#List").datagrid("reload",{
//                            brand_id:brand_id,
//                            stores_id:stores_id,
//                        });

                        $('#selectForm').serializeSelectJson('List');
                    }
                    else{
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }


        function getStoresGoodsClass(brand_id,stores_id) {
            $.ajax({
                url: "/PtGoodsManage/getStoresGoodsClass",
                data: {stores_id: stores_id,brand_id:brand_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        getGood_ids(stores_id,brand_id);
                        getCombobox(brand_id);
                        var objs = result.obj;
                        $("#storesGoodClass").html("");
                        $("#storesGoodClass").append(' <a href="###" onclick=goodClass("")> <li class="mdss" name="all" style="background-color:#C0C0C0 ">全部</li></a>');
                        $('#brand_id2').val(brand_id);
                        $('#stores_id2').val(stores_id);
                        $('#s_brand_id').val(brand_id);
                        $('#stores_id').val(stores_id);
                        $('#s_stores_name').textbox('setValue',${stores_name});
                        $('#s_brand_name').textbox('setValue',${brand_name});
                        if(objs!=null&&objs.length>0){
                            $('#meituan_restaurant_id').val(objs[0].meituan_restaurant_id);
                            $('#elem_restaurant_id').val(objs[0].elem_restaurant_id);
                            $('#stores_brand_id').val(objs[0].stores_brand_id);
                            class_ids="and c.class_id in ("
                            for (var i = 0; i < objs.length; i++){
                                var name = objs[i].class_name;
                                var id = objs[i].class_id;
                                class_ids = class_ids + id +','    //拼接商品类别id
                                if(i==objs.length-1){
                                    class_ids=class_ids.substring(0,(class_ids.length-1));
                                    class_ids=class_ids+')';
                                }
                                $("#storesGoodClass").append(' <a href="###" onclick=goodClass("'+id+'") > <li class="mdss" name='+id+'>'+name+'</li></a>');
                            }
                        }

                    } else if(result.status == "2") {
                        var objs = result.obj;
                        $('#brand_id2').val(brand_id);
                        $("#storesGoodClass").html("");
                        $("#storesGoodClass").append(' <a href="###" onclick=goodClass("")> <li class="mdss" name="all" style="background-color:#C0C0C0 ">全部</li></a>');
                        for (var i = 0; i < objs.length; i++){
                            var name = objs[i].class_name;
                            var id = objs[i].class_id;
                            $("#storesGoodClass").append(' <a href="###" onclick=goodClass("'+id+'") > <li class="mdss" name='+id+'>'+name+'</li></a>');
                        }
                    }else if(result.status == "3"){
                        var objs = result.obj;
                        $("#storesGoodClass").html("");
                        $("#storesGoodClass").append(' <a href="###" onclick=goodClass("")> <li class="mdss" name="all" style="background-color:#C0C0C0 ">全部</li></a>');
                        for (var i = 0; i < objs.length; i++){
                            var name = objs[i].class_name;
                            var id = objs[i].class_id;
                            $("#storesGoodClass").append(' <a href="###" onclick=goodClass("'+id+'") > <li class="mdss" name='+id+'>'+name+'</li></a>');
                        }
                    }
                }
            });
        }

        function goodClass(id) {
            $("#storesGoodClass li").each(function () {
                $(this).css("backgroundColor", "");
            });
            $("a [name='all']").css("backgroundColor", "");
            $("a [name='"+id+"']").css("backgroundColor", "#C0C0C0");
            if(id==null||id==""){
                $("a [name='all']").css("backgroundColor", "#C0C0C0");
            }
            $("#class_id").val(id);
            $('#selectForm').serializeSelectJson('List');

        }
        
        function elemRemoveGoods(food_id) {
            $.ajax({
                url: "/PtGoodsManage/eleDeleteFood",
                data: {food_id:food_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
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

        function elemBandGoods(food_category_id,name,price,description,stock,sgl_id,image_hash) {
            if(!validation(image_hash)){
                image_hash='';
            }
            $.ajax({
                url: "/PtGoodsManage/elemBandGood",
                data: {food_category_id: food_category_id,name:name,price:price,description:description,stock:stock,sgl_id:sgl_id,image_hash:image_hash},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
                        $.messager.show({
                            title:'提示信息',
                            msg:'绑定成功',
                            showType:'show'
                        });
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        function mtBand(sgl_id,good_name,good_info,good_pic,class_name) {
            if(!validation(good_pic)){
                good_pic='';
            }
            $.ajax({
                url: "/PtGoodsManage/mtBandGood",
                data: {sgl_id:sgl_id,category_name:class_name,good_name:good_name,good_info:good_info,good_pic:good_pic},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
                        $.messager.show({
                            title:'提示信息',
                            msg:'绑定成功',
                            showType:'show'
                        });
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        function mtDeleteBand(sgl_id) {
            $.ajax({
                url: "/PtGoodsManage/mtDeleteGood",
                data: {sgl_id:sgl_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
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



    </script>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;overflow:hidden;" id="layoutAll">


    <div data-options="region:'west',split:true,title:'',iconCls:'icon-ok'" style="width:200px;"  id="storesGoodClass">

    </div>
    <div class="easyui-layout" data-options="region:'center',title:'',iconCls:'icon-ok'" style="overflow: hidden;">
        <div data-options="region:'north'" style="height: 0px"  id="selectForm">

                <input type="hidden" id="brand_id2" name="brand_id">
                <input type="hidden" id="stores_id2" name="stores_id">
                <input type="hidden" id="class_id" name="class_id">
                <input type="hidden" id="good_id" name="good_id">
                <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">平台</a>--%>
                <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">饿了么</a>--%>
                <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">美团</a>--%>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" style="float: right" onclick="queryGoods()">分类管理</a>
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right" onclick="addGoods()" id="btn">添加</a>


        </div>
        <table id="List"></table>
        <input type="hidden" id="meituan_restaurant_id" name="meituan_restaurant_id">
        <input type="hidden" id="elem_restaurant_id" name="elem_restaurant_id">
    </div>



    <!--   ============================== 新增界面div、表单===============================     -->
    <div id="dlg" class="easyui-dialog" style="width: 60%; height: 600px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem" style="float: left;width: 50%">
                <label class="mytitle">品牌</label>
                <input name="brand_name" id="s_brand_name" class="easyui-validatebox easyui-textbox" required="true" readonly="readonly"/>
            </div>
            <div class="fitem" style="float: left;width: 50%">
                <label class="mytitle">门店</label>
                <input name="stores_name" id="s_stores_name" class="easyui-validatebox easyui-textbox" required="true" readonly="readonly"/>
            </div>
            <div class="fitem">
                <label class="mytitle" style="padding-top: 15px">选择商品</label>
                <select class="easyui-combogrid" id="cc" style="width:450px;padding-top: 15px">
                </select>
            </div>
                <table id="List2" class="easyui-datagrid " data-options="singleSelect:true,collapsible:true" style="width: 100%;height: 600px;">
                </table>
                <input name="good_ids" id="good_ids" class="easyui-validatebox" required="true" type="hidden"/>
                <input name="brand_id" id="s_brand_id" class="easyui-validatebox" required="true" type="hidden"/>
                <input name="stores_id" id="stores_id" class="easyui-validatebox" required="true" type="hidden"/>
                <input type="hidden" name="stores_brand_id" id="stores_brand_id" value=""/>
                <input type="hidden" name="opt" id="opt" value=""/>
        </form>


        <div id="selectForm2" name="selectForm2" style="height: 35px">
            <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
                <div style="float: left;padding-left: 5px">
                    <label class="mytitle">商品类别:</label>
                    <input id="s_class_id" name="class_id" value="" style="width:120px;">
                    <label class="mytitle" style="padding-left: 15px">是否上架:</label>
                    <select class="easyui-combobox easyui-validatebox" id="status" name="status" style="width:50px;"
                            data-options="editable:false,panelHeight:'auto'" required="true">
                        <option value="" >全部</option>
                        <option value="1" selected="true">是</option>
                        <option value="0">否</option>
                    </select>
                    <input class="easyui-textbox" name="good_name" id="good_name" style="width:160px"
                           data-options="label:'商品名称：',prompt:'商品名称模糊查询'">
                    <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
                    <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
                </div>
            </div>
        </div>

    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>


</div>
</body>
</html>
