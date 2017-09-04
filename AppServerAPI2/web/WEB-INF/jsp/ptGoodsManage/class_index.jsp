
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title></title>
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

        $(function () {
            var eStatus='0';//门店饿了么是否绑定0未绑定、1已绑定
            var mStatus='0';//门店美团是否绑定0未绑定、1已绑定
            var lastIndex;
            $('#List').datagrid({
                url: '/PtGoodsManage/GetGoodsClassList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'a.stores_class_id',
                sortOrder: 'desc',
                idField: 'stores_class_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: '门店品牌明细管理',
                nowrap: "true",
//                pageSize: 15,
                toolbar:'#selectForm',
                pagination:true,
                columns: [[
                    { field: 'meituan_restaurant_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'elem_restaurant_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'stores_brand_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'class_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'listorder', title: 'ID',  align: 'center',hidden:true},
                    { field: 'type', title: 'ID',  align: 'center',hidden:true},
                    { field: 'stores_class_id', title: 'ID', width: '10%', align: 'center'},
                    { field: 'name', title: '店铺', width: '20%', align: 'center'},
                    { field: 'class_name', title: '类别名称', width: '15%', align: 'center'},
                    { field: 'brand_id', title: '所属品牌', width: '10%', align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${brandlist}">
                        if(row.brand_id=="${item.brand_id}"){
                            return "${item.brand_name}";
                        }
                        </c:forEach>
                    }  },
                    { field: 'food_category_id', title: '饿了么', width: '15%', align: 'center',formatter:function(value,row){
                        storesBindQuery(row.stores_brand_id,row.stores_class_id);
                        if(value==""||value==undefined){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" name="' + row.stores_class_id + '" onclick=bind('+row.stores_class_id+','+row.elem_restaurant_id+','+row.listorder+',"'+row.class_name+'","'+row.name+'",'+row.stores_brand_id+') style="text-decoration:none;color: red">绑定</a>'
                        }else if(row.type==0){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=bindUpdate('+row.food_category_id+',"'+row.class_name+'",'+row.elem_restaurant_id+',"'+row.name+'",'+row.stores_brand_id+') style="text-decoration:none;color: green" >更新</a>';
                        }else{
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=bindDelete('+row.stores_class_id+','+row.food_category_id+','+row.elem_restaurant_id+',"'+row.name+'",'+row.stores_brand_id+') style="text-decoration:none;color: green" >解绑</a>'
                        }
                    }},
                    { field: 'meituan_cat_id', title: '美团', width: '15%', align: 'center',formatter:function(value,row){
                        var id = row.stores_brand_id;
                        storesBindQuery(row.stores_brand_id,row.stores_class_id);
                        if(value==""||value==undefined){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" class="' + row.stores_class_id + '" onclick=mbind('+row.stores_class_id+','+row.listorder+',"'+row.class_name+'",'+row.stores_brand_id+',"'+row.name+'") style="text-decoration:none;color: red" >绑定</a>';
                        }else if(row.class_name!=value){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" onclick=mbindUpdate('+row.stores_class_id+','+row.stores_brand_id+',"'+row.class_name+'","'+value+'",'+row.listorder+',"'+row.name+'") style="text-decoration:none;color: green" >更新</a>';
                        }else{
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=mtClassRemoveBind('+row.stores_class_id+','+row.stores_brand_id+',"'+row.class_name+'","'+row.name+'") style="text-decoration:none;color: green" >解绑</a>';
                        }
                    }},
                    { field: 'ttttt', title: '操作', width: '15%', align: 'center',formatter:function(value,row){
                        var elme = row.food_category_id;
                        var mt = row.meituan_cat_id;
                        if(elme!=""&&elme!=null&&elme!=undefined){
                            return '请先解绑饿了么';
                        }else if(mt!=""&&mt!=null&&mt!=undefined){
                            return '请先解绑美团';
                        }else{
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" onclick=deleteClass('+row.stores_brand_id+','+row.elem_restaurant_id+',"'+row.name+'",'+row.stores_class_id+') style="text-decoration:none;color: red">删除</a>';
                        }
                    }  }
                ]]

            });


            $('#btn').linkbutton('disable');
//            $('#List').datagrid('hideColumn','food_category_id');
//            $('#List').datagrid('hideColumn','meituan_cat_id')
        });


        function greatGrid(brand_id,class_name,class_ids) {
            $('#lb_id').combogrid({
                panelWidth: '60%',
                panelHeight:'55%',
                idField: 'class_id',
                textField: 'class_name',
                sortName: 'class_id',
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                url: '/PtGoodsManage/GetClassList',
                method: 'post',
//                toolbar: '#selectForm2',
                pagination:true,
                multiple:true,
                queryParams:{
                    brand_id:brand_id,
                    class_name:class_name,
                    ids:class_ids
                },
                onClickRow: function (rowIndex,rowData) {
                    greatGrid2
                    queryBD(rowData);
                },
                columns: [[
                    {field:'class_id',title:'ID',width:'15%',align: 'center'},
                    {field:'class_name',title:'商品名称',width:'50%',align: 'center',sortable:true},
                    {field:'brand_id',title:'品牌',width:'35%',sortable:true,align: 'center',formatter:function(value,row){
                        <c:forEach var='item' items='${brandlist}'>
                        if(row.brand_id=='${item.brand_id}'){
                            return '${item.brand_name}';
                        }
                        </c:forEach>
                    }}
//                    {field:'market_price',title:'商品价格',width:'10%',align: 'center',sortable:true},
//                    {field:'box_price',title:'餐盒费',width:'8%',align: 'center',sortable:true},
//                    {field:'status',title:'状态',width:'7%',align: 'center',sortable:true,formatter:function(value,row){
//                        if (value == 1) {
//                            return '<span  style="color:green">上架</span>';
//                        }else{
//                            return '<span  style="color:red">下架</span>';
//                        }
//                    }
//
//                    }
                ]]
            });
        }

        //查询
        $("#findBtn").click(function () {
            var brand_id = $("#brand_id").val();
            var class_name = $("#class_name").val();
            greatGrid(brand_id,class_name);
        });

        $("#refreshBtn").click(function () {
            var brand_id = $("#brand_id").val();
            $('#class_name').textbox('setValue',"");
            $("#ld_id").combogrid("reload");
//                $('#ld_id').combogrid('load', {brand_id:brand_id});
        });



        function greatGrid2() {

            $('#List2').datagrid({
//                url:'/GoodsInfo/GetGoodsInfoErp?good_id='+id,
                datagrid:null,
                width: '100%',
                height:'100%',
                sortName: 'g.good_id',
                idField: 'good_id',
                singleSelect: true,//单选模式
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                //title: 'erp商品原料',
                nowrap: "true",
                pagination: false,
                columns: [[
                    { field: 'class_id', title: '类别id', width: '40%', align: 'center',hidden:true },
                    { field: 'stores_brand_id', title: '类别id', width: '40%', align: 'center',hidden:true },
                    { field: 'name', title: '类别名称', width: '40%', align: 'center' },
                    { field: 'elem', title: '饿了么绑定状态', width: '30%', align: 'center' },
                    { field: 'mt', title: '美团绑定状态', width: '30%', align: 'center' },
                ]]
            });
        }


        
        function queryBD(rowData) {
            var rowIndex;
            var data = $('#List2').datagrid('getData');
            var rows = data.rows;
            var stores_brand_id = $("#stores_brand_id").val();
            var isok = "no";
            if(rows!=null&&rows.length>0){
                for(var i=0;i<rows.length;i++){
                    if(rowData.class_id==rows[i].class_id){
                        rowIndex =  $('#List2').datagrid('getRowIndex', rows[i]);
                        isok ="ok";
                    }
                }
            }
            if(isok=="ok"){
                $('#List2').datagrid('deleteRow',rowIndex);
//                $.messager.show({title:'提示信息', msg:'商品已经有该菜品了',showType:'show'});
                return false;
            }
            $('#List2').datagrid('endEdit', lastIndex);
            $('#List2').datagrid('appendRow',{
                class_id:rowData.class_id,
                stores_brand_id:stores_brand_id,
                name:rowData.class_name,
                elem:'未绑定',
                mt:'未绑定',
            });
            var lastIndex = $('#List2').datagrid('getRows').length-1;
            $('#List2').datagrid('selectRow', lastIndex);
        }



        function getClass_ids() {
            var class_ids='and class_id not in (';
            var datas = $("#List").datagrid('getData').rows;
            if(datas!=""&&datas!=undefined&&datas.length>0){
                for (var i=0;i<datas.length;i++){
                    class_ids = class_ids+datas[i].class_id+',';
                    if(i==datas.length-1){
                        class_ids=class_ids.substring(0,(class_ids.length-1));
                        class_ids=class_ids+')';
                    }
                }
            }
            return class_ids;
        }



        function storesBindQuery(stores_brand_id,stores_class_id) {
            //查询饿了么绑定信息
            $.ajax({
                url: "/Stores/elestoresBindQuery",
                data: {stores_brand_id: stores_brand_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
//                        $('#List').datagrid('showColumn','food_category_id');
                    } else {
                        $("a[name='" + stores_class_id + "']").text('请先绑定门店');
                        $("a[name='" + stores_class_id + "']").css('color', 'red');
//                        $('#List').datagrid('hideColumn','food_category_id');
                    }
                }
            });

            //查询美团绑定信息
            $.ajax({
                url: "/Stores/meituanstoresBindQuery",
                data: {stores_brand_id: stores_brand_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
//                        $('#List').datagrid('showColumn','meituan_cat_id')
                    } else {
                        $("a[class='" + stores_class_id + "']").text('请先绑定门店');
                        $("a[class='" + stores_class_id + "']").css('color', 'red');
//                        $('#List').datagrid('hideColumn','meituan_cat_id')
                    }
                }
            });



        }

        //添加品牌明细
        function add() {
            var brand_id = $('#brand_id').val();
            var class_ids = getClass_ids();
            greatGrid(brand_id,'',class_ids);
            $('#lb_id').combogrid('clear');//清除下拉列表数据
            var stores_brand_id = $("#stores_brand_id").val();

            if(stores_id==''||stores_id==undefined||stores_id=='1'){
                $.messager.show({title:'提示信息', msg:'请先选择门店',showType:'show'});
                return false;
            }
            $("#dlg").dialog("open").dialog('setTitle', '添加');
            greatGrid2();
            $('#List2').datagrid('loadData',{total:0,rows:[]});//清空表格

//                $("select").val("");

            $("#opt").val("add");
            // $(".printcf").hide();
        }


        function saveprint(){
//            var grid=$("#lb_id").combogrid("grid");//获取表格对象
//            var rows = $("#lb_id").combogrid('getValues');
//            var row = grid.datagrid('getSelected');
            var data = $('#List2').datagrid('getData');
            var vipzk = $("#vip_discounts").val();
            $.ajax({
                url: "/PtGoodsManage/saveStoresClass",
                data: data,
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.alert("提示信息", "操作成功");
                        $("#dlg").dialog("close");
                        $("#List").datagrid("reload");
                    }
                    else{
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }



        //修改门店品牌明细
        function editprint() {
            var row = $("#List").datagrid("getSelected");
            if (row == null) {
                $.messager.alert("提示信息", "请选择一条记录");
            }
            // alert(row.elem_restaurant_id);
            if (row) {
                $("#dlg").dialog("open").dialog('setTitle', '修改门店品牌明细');
                $("[name='brand_id']").val("");
                $("[name='stores_id']").val("");
                $("[name='service_phone']").val("");
                $("[name='elem_restaurant_id']").val("");
                $("[name='elem_url']").val("");
                $("[name='meituan_restaurant_id']").val("");
                $("[name='meituan_url']").val("");
                $("[name='baidu_restaurant_id']").val("");
                $("[name='baidu_url']").val("");
                $("[name='stores_brand_id']").val("");
                $("#fm").form("load", row);
                $("#opt").val("mod");
            }
        }


        //饿了么绑定
        function bind(stores_class_id,elem_restaurant_id,listorder,class_name,name,stores_brand_id) {
            $.ajax({
                url: "/PtGoodsManage/elemClassBind",
                data: {elem_restaurant_id: elem_restaurant_id,
                    stores_class_id:stores_class_id,
                    class_name:class_name,
                    listorder:listorder
                },
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
                        $.messager.show({
                            title:'提示信息',
                            msg:'绑定成功',
                            showType:'show'
                        });
                        //刷新饿了么列表
                        greatEGrid(elem_restaurant_id,name,stores_brand_id);
                    }
                    else {
                        $.messager.alert("提示信息", "绑定失败");
                    }
                }
            });
        }

        //饿了么绑定
        function bindUpdate(food_category_id,class_name,elem_restaurant_id,name,stores_brand_id) {
            $.ajax({
                url: "/PtGoodsManage/eleGoodClassBindUpdate",
                data: {food_category_id:food_category_id,
                    class_name:class_name
                },
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
                        $.messager.show({
                            title:'提示信息',
                            msg:'更新成功',
                            showType:'show'
                        });
                        //刷新饿了么列表
                        greatEGrid(elem_restaurant_id,name,stores_brand_id);
                    }
                    else {
                        $.messager.alert("提示信息", "更新失败");
                    }
                }
            });
        }

        //饿了么解除绑定
        function bindDelete(stores_class_id,food_category_id,elem_restaurant_id,name,stores_brand_id) {
            $.ajax({
                url: "/PtGoodsManage/eleGoodClassBindDelete",
                data: {food_category_id:food_category_id,
                    stores_class_id:stores_class_id
                },
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
                        $.messager.show({
                            title:'提示信息',
                            msg:'解绑成功',
                            showType:'show'
                        });
                        //刷新饿了么列表
                        greatEGrid(elem_restaurant_id,name,stores_brand_id);
                    }
                    else {
                        $.messager.alert("提示信息", "解绑失败");
                    }
                }
            });
        }

        function mbind(stores_class_id,listorder,class_name,stores_brand_id,name) {
            $.ajax({
                url: "/PtGoodsManage/mtClassBind",
                data: {stores_brand_id: stores_brand_id,
                    stores_class_id:stores_class_id,
                    class_name:class_name,
                    listorder:listorder
                },
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
                        $.messager.show({
                            title:'提示信息',
                            msg:'绑定成功',
                            showType:'show'
                        });
                        //刷新美团列表
                        greatMGrid(stores_brand_id,name);
                    }
                    else {
                        $.messager.alert("提示信息", "绑定失败");
                    }
                }
            });
        }
        
        function getMclassList(stores_brand_id) {
            $.ajax({
                url: "/PtGoodsManage/meituanClassQuery",
                data: {stores_brand_id: stores_brand_id},
                type: 'POST',
                async:false,
                success: function (result) {
                    if (result.status == "1") {
                        $("#list").datagrid('showColumn', 'meituan_cat_id');
                        $("#list").datagrid('hideColumn', 'mstatus');
                    } else {
                        $("#list").datagrid('showColumn', 'mstatus');
                        $("#list").datagrid('hideColumn', 'meituan_cat_id');
                    }
                }
            });
        }


        function mbindUpdate(stores_class_id,stores_brand_id,class_name,category_name_origin,sequence,name) {
//            $('#cc').combobox({
//                url:'/PtGoodsManage/meituanClassQuery?stores_brand_id='+stores_brand_id,
//                valueField:'text',
//                textField:'text'
//            });
////            alert(sequence);
//            $('#sbid').val(stores_brand_id);
//            $("#sequence").val(sequence);
//            $("#category_name").val(class_name);
//            $("#dlg2").dialog("open").dialog('setTitle', '更新');
            $.ajax({
                url: "/PtGoodsManage/mtClassBind",
                data: {stores_class_id:stores_class_id,stores_brand_id: stores_brand_id, listorder:sequence,class_name:class_name,category_name_origin:category_name_origin},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
                        $.messager.show({
                            title:'提示信息',
                            msg:'更新成功',
                            showType:'show'
                        });
                        //刷新美团列表
                        greatMGrid(stores_brand_id,name);
                    } else {
                        $.messager.alert("提示信息", "更新失败");
                    }
                }
            });
        }


        function mtClassRemoveBind(stores_class_id,stores_brand_id,class_name,name) {
            $.ajax({
                url: "/PtGoodsManage/mtClassRemoveBind",
                data: {stores_class_id:stores_class_id,
                    stores_brand_id:stores_brand_id,
                    class_name:class_name
                },
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#selectForm').serializeSelectJson('List');
                        $.messager.show({
                            title:'提示信息',
                            msg:'解绑成功',
                            showType:'show'
                        });
                        //刷新美团列表
                        greatMGrid(stores_brand_id,name);
                    }
                    else {
                        $.messager.alert("提示信息", "解绑失败");
                    }
                }
            });
        }

        function greatMGrid(id,stores_name) {
            $('#mList').datagrid({
                url:'/PtGoodsManage/meituanClassQuery?stores_brand_id='+id,
                width: '100%',
                height:'100%',
//                sortName: 'sequence',
                idField: 'name',
                singleSelect: true,//单选模式
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                //title: 'erp商品原料',
                nowrap: "true",
                pagination: false,
                columns: [[
                    { field: 'stores_brand_id', title: 'id', align: 'center',hidden:true },
                    { field: 'stores_name', title: '商铺', width: '30%', align: 'center',formatter:function(value,row){
                        return stores_name;
                    } },
                    { field: 'name', title: '类别名称', width: '30%', align: 'center'},
                    { field: 'mstatus', title: '美团绑定状态', width: '30%', align: 'center',formatter:function(value,row){
                        if(value=="1"){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=reverseBindMT('+row.stores_brand_id+',"'+row.name+'","'+stores_name+'","mt") style="text-decoration:none;color: red" >绑定</a>'+
                                    '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=deleteMT('+row.stores_brand_id+',"'+row.name+'","'+stores_name+'") style="text-decoration:none;color: red;padding-left: 40px" >删除</a>'
                        }else{
                            return '<span style="color: green">已绑定</span>';
                        }
                    } },
                    { field: 'sequence', title: '类别排序', width: '10%', align: 'center' }
                ]]
            });
        }


        function  deleteMT(stores_brand_id,name,stores_name) {
            $.ajax({
                url: "/PtGoodsManage/deleteMT",
                data: {stores_brand_id:stores_brand_id,
                    class_name:name
                },
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        greatMGrid(stores_brand_id,stores_name);
                        $.messager.show({
                            title:'提示信息',
                            msg:'删除成功',
                            showType:'show'
                        });
                    }
                    else {
                        $.messager.alert("提示信息", "删除失败");
                    }
                }
            });
        }


        function greatEGrid(id,stores_name,stores_brand_id) {
            $('#eList').datagrid({
                url:'/PtGoodsManage/getElemClassList?restaurant_id='+id,
                width: '100%',
                height:'100%',
//                sortName: 'sequence',
                idField: 'name',
                singleSelect: true,//单选模式
                striped: true, //奇偶行是否区分
//                rownumbers: true,//行号
                //title: 'erp商品原料',
                nowrap: "true",
                pagination: false,
                columns: [[
                    { field: 'food_category_id', title: 'id', align: 'center',hidden:true },
                    { field: 'stores_name', title: '商铺', width: '30%', align: 'center',formatter:function(value,row){
                        return stores_name;
                    } },
                    { field: 'name', title: '类别名称', width: '30%', align: 'center'},
                    { field: 'estatus', title: '饿了么绑定状态', width: '30%', align: 'center',formatter:function(value,row){
                        if(value=="1"){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=reverseBindElem('+stores_brand_id+',"'+row.name+'","'+stores_name+'","'+row.food_category_id+'",'+id+',"eleme") style="text-decoration:none;color: red" >绑定</a>'+
                                '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=deleteElem('+stores_brand_id+',"'+row.food_category_id+'","'+stores_name+'",'+id+') style="text-decoration:none;color: red;padding-left: 40px" >删除</a>'
                        }else{
                            return '<span style="color: green">已绑定</span>';
                        }
                    } },
                    { field: 'weight', title: '类别排序', width: '10%', align: 'center' }
                ]]
            });
        }


        function  deleteElem(stores_brand_id,food_category_id,stores_name,id) {
            $.ajax({
                url: "/PtGoodsManage/deleteElem",
                data: {food_category_id:food_category_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        greatEGrid(id,stores_name,stores_brand_id);
                        $.messager.show({
                            title:'提示信息',
                            msg:'删除成功',
                            showType:'show'
                        });
                    }
                    else {
                        $.messager.alert("提示信息", "删除失败");
                    }
                }
            });
        }


        function reverseBindElem(stores_brand_id,name,stores_name,food_category_id,elmid,r_type) {
            var brand_id = $("#brand_id").val();
            greatTree(brand_id,'elem');
            $("#r_class_name").val(name);
            $("#r_stores_brand_id").val(stores_brand_id);
            $("#r_food_category_id").val(food_category_id);
            $("#r_stores_name").val(stores_name);
            $("#r_elmid").val(elmid);
            $("#r_type").val(r_type);
            $("#dlg2").dialog("open").dialog('setTitle', '绑定');

        }

        function reverseBindMT(stores_brand_id,name,stores_name,r_type) {
            var brand_id = $("#brand_id").val();
            greatTree(brand_id,'mt');
            $("#r_class_name").val(name);
            $("#r_stores_brand_id").val(stores_brand_id);
            $("#r_stores_name").val(stores_name);
            $("#r_type").val(r_type);
            $("#dlg2").dialog("open").dialog('setTitle', '绑定');

        }


        function saveReverseBind() {
            var type = $("#r_type").val();
            var name =$("#r_class_name").val();
            var stores_brand_id = $("#r_stores_brand_id").val();
            var stores_name = $("#r_stores_name").val();
            var class_id = $('#xzcc').combotree('tree').tree('getSelected').id;
            var category_name = $('#xzcc').combotree('tree').tree('getSelected').text;
            if(class_id==null||class_id==""||class_id==undefined){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择类别',
                    showType:'show'
                });
                return false;
            }
            if(type=="eleme"){
                var food_category_id =$("#r_food_category_id").val();
                var id = $("#r_elmid").val();
                //饿了么反向绑定
                $.ajax({
                    url: "/PtGoodsManage/reverseBindElem",
                    data: {stores_brand_id:stores_brand_id,class_name:category_name,food_category_id:food_category_id,class_id:class_id},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            greatEGrid(id,stores_name,stores_brand_id);
                            $('#selectForm').serializeSelectJson('List');
                            $('#dlg2').dialog('close');
                            $.messager.show({
                                title:'提示信息',
                                msg:'绑定成功',
                                showType:'show'
                            });
                        }else if(result.status == "2") {
                            $.messager.alert("提示信息", "请先在平台上新建分类");
                        }else{
                            $.messager.alert("提示信息", "绑定失败");
                        }
                    }
                });

            }else{
                //美团反向绑定
                var sequence = $('#xzcc').combotree('tree').tree('getSelected').sequence;
                $.ajax({
                    url: "/PtGoodsManage/reverseBindMT",
                    data: {stores_brand_id:stores_brand_id,class_name:name,class_id:class_id,category_name:category_name,sequence:sequence},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $('#dlg2').dialog('close');
                            greatMGrid(stores_brand_id,stores_name);
                            $('#selectForm').serializeSelectJson('List');
                            $.messager.show({
                                title:'提示信息',
                                msg:'绑定成功',
                                showType:'show'
                            });
                        }else if(result.status == "2") {
                            $.messager.alert("提示信息", "请先在平台上新建分类");
                        }else{
                            $.messager.alert("提示信息", "绑定失败");
                        }
                    }
                });
            }
        }


        function greatTree(id,status) {
            $('#xzcc').combotree({
                url:'/PtGoodsManage/GetBrandTree?brand_id='+id,
                onClick:function (node) {
                    $.ajax({
                        url: "/PtGoodsManage/checkBind",
                        data: {class_id:node.id,status:status},
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {
                                $.messager.show({
                                    title:'提示信息',
                                    msg:'该分类已绑定',
                                    showType:'show'
                                });
                                $('#xzcc').combotree('setValue', '');
                            } else {
//                                $.messager.alert("提示信息", "系统错误");
                            }
                        }
                    });
                },
                required: true
            });
        }


        function deleteClass(stores_brand_id,elem_restaurant_id,name,stores_class_id) {
            $.ajax({
                url: "/PtGoodsManage/deleteClass",
                data: {stores_class_id:stores_class_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.show({
                            title:'提示信息',
                            msg:'成功删除',
                            showType:'show'
                        });
//                        greatEGrid(elem_restaurant_id,name,stores_brand_id);
//                        greatMGrid(stores_brand_id,name);
                        $('#selectForm').serializeSelectJson('List');
                    } else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });

        }



    </script>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;">

    <div data-options="region:'north'">
        <%--<h2 style="margin-left: 10px;margin-top: 5px;">--%>
            <%--</h2>--%>
    </div>
    <div data-options="region:'west',split:true" style="width:15%;">
        <div style="width:100%;margin-top: 10%" id="shop">
            <ul id="tt" class="easyui-tree" data-options="
				url: '/PtGoodsManage/GetStoresTree',
				method: 'post',
				animate: true,
				onClick: function(node){
					var rows = node.children;
					if( rows != undefined && rows.length>0 ){
					    <%--$(':input','#selectForm').not(':button,:submit,:reset') .val('');--%>
					    <%--$(':input','#fm').not(':button,:submit,:reset') .val('');--%>
					    $('#stores_id').val('');
		                $('#brand_id').val(node.id);
		                $('#selectForm').serializeSelectJson('List');
		                <%--$('#List2').datagrid('loadData',{total:0,rows:[]});//清空表格--%>
		                $('#btn').linkbutton('disable');
		                return false;
		            }else{
		                $('#btn').linkbutton('enable');
		                <%--$(':input','#selectForm').not(':button,:submit,:reset') .val('');--%>
		                <%--$(':input','#fm').not(':button,:submit,:reset') .val('');--%>
		                $('#List2').datagrid('loadData',{total:0,rows:[]});//清空表格
		                $('#lb_id').combogrid('clear');//清除下拉列表数据
		                var parent = $('#tt').tree('getParent', node.target);
		                var brand_id = $('#brand_id').val();
		                if(brand_id==parent.id){
		                    $('#stores_name').textbox('setValue','');
		                }else{
		                    $(':input','#selectForm').not(':button,:submit,:reset') .val('');
		                    $(':input','#fm').not(':button,:submit,:reset') .val('');
		                }
		                var elem_restaurant_id = node.elem_restaurant_id;
		                var meituan_restaurant_id = node.meituan_restaurant_id;
		                if(elem_restaurant_id==undefined){
		                    elem_restaurant_id='';
		                }
		                if(meituan_restaurant_id==undefined){
		                    meituan_restaurant_id='';
		                }
		                $('#brand_id').val(parent.id);
		                $('#stores_id').val(node.id);
		                $('#stores_brand_id').val(node.stores_brand_id);
		                $('#stores_name').textbox('setValue',node.text);
		                $('#brand_name').textbox('setValue',parent.text);
		                $('#meituan_restaurant_id').textbox('setValue',meituan_restaurant_id);
		                $('#elem_restaurant_id').textbox('setValue',elem_restaurant_id);
		                $('#selectForm').serializeSelectJson('List');
		                <%--storesBindQuery(node.stores_brand_id);--%>
		                greatGrid(parent.id,'','');
		                greatMGrid(node.stores_brand_id,node.text);
		                greatEGrid(elem_restaurant_id,node.text,node.stores_brand_id);
		            }
				}
			"></ul>
        </div>
    </div>
    <div data-options="region:'center',title:'',iconCls:'icon-ok'">
        <div class="easyui-tabs" style="width:100%;height:100%">
            <div title="平台" style="padding:10px">
                <table id="List">
                </table>
            </div>
            <div title="饿了么">
                <table id="eList">
                </table>
            </div>
            <div title="美团">
                <table id="mList">
                </table>
            </div>
        </div>
    </div>
    <div id="selectForm">
        <input type="hidden" id="class_id" name="class_id">
        <input name="stores_id" type="hidden" id="stores_id" value=""/>
        <input name="brand_id" type="hidden" id="brand_id" value=""/>
        <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">平台</a>--%>
        <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">饿了么</a>--%>
        <%--<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">美团</a>--%>

        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right" onclick="add()" id="btn">添加</a>

    </div>
    <%--<div id="selectForm2">--%>
        <%--<input class="easyui-textbox" name="class_name" id="class_name" style="width:150px"--%>
               <%--data-options="label:'类别名称：',prompt:'类别名称模糊查询'">--%>
        <%--<a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>--%>
        <%--<a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>--%>
    <%--</div>--%>

    <!--   ============================== 新增界面div、表单===============================     -->
    <div id="dlg" class="easyui-dialog" style="width: 50%; height: 80%; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem">
                <label class="mytitle">品牌</label>
                <%--<select class="easyui-combobox easyui-validatebox" id="brand_id" name="brand_id" style="width:160px;"--%>
                        <%--data-options="editable:false,panelHeight:'auto'" required="true">--%>
                    <%--<c:forEach var="item" items="${brandlist}">--%>
                        <%--<option value="${item.brand_id}">${item.brand_name}</option>--%>
                    <%--</c:forEach>--%>
                <%--</select>--%>
                <input name="brand_name" class="easyui-validatebox easyui-textbox" id="brand_name" readonly="readonly"/>
            </div>
            <div class="fitem">
                <label  class="mytitle">门店:</label>
                <input name="stores_name" class="easyui-validatebox easyui-textbox" id="stores_name" readonly="readonly"/>
                <%--<select id="stores_id" class="easyui-combogrid" name="stores_id" style="width:250px;"></select>--%>
            </div>
            <div class="fitem">
                <label class="mytitle">分类:</label>
                <select id="lb_id" class="easyui-combogrid" name="class_id" style="width:300px;"></select>
            </div>
            <input name="class_id" type="hidden"  value="1"/>
            <input name="opt" type="hidden" value=""  />
        </form>
        <div style="height:60%;width:100%">
            <%--<label class="mytitle">饿了么</label>--%>
            <input name="stores_brand_id" class="easyui-validatebox easyui-textbox" id="stores_brand_id" readonly="readonly" type="hidden"/>
            <input name="elem_restaurant_id" class="easyui-validatebox easyui-textbox" id="elem_restaurant_id" readonly="readonly" type="hidden"/>
            <input name="meituan_restaurant_id" class="easyui-validatebox easyui-textbox" id="meituan_restaurant_id" readonly="readonly" type="hidden"/>
            <table id="List2" class="easyui-datagrid " data-options="singleSelect:true,collapsible:true" style="width: 100%;height: 500px;">
            </table>
        </div>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>

    <div id="dlg2" class="easyui-dialog" style="width: 550px; height: 250px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons2">
        <form id="fm2" method="post">
            <div class="fitem" style="padding-top:10%;padding-left:10%;">
                <label class="mytitle">请选择食物分类</label>
                <select id="xzcc" name="class_id" panelWidth="300px" panelHeight="200px" class="easyui-combotree" style="width:200px;">
                </select>
            </div>
            <input name="r_elmid" type="hidden" value=""  id="r_elmid"/>
            <input name="r_class_name" type="hidden" value=""  id="r_class_name"/>
            <input name="r_stores_name" type="hidden" value=""  id="r_stores_name"/>
            <input name="r_food_category_id" type="hidden" value=""  id="r_food_category_id"/>
            <input name="r_category_name" type="hidden" value=""  id="r_category_name"/>
            <input name="r_stores_brand_id" type="hidden" value=""  id="r_stores_brand_id"/>
            <input name="r_sequence" type="hidden" value=""  id="r_sequence"/>
            <input name="r_type" type="hidden" value=""  id="r_type"/>
        </form>
    </div>
    <div id="dlg-buttons2">

        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveReverseBind()">绑定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
    </div>

</div>
</body>
</html>
