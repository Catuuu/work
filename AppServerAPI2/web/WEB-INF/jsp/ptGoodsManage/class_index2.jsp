
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
            $('#List').datagrid({
                url: '/PtGoodsManage/getGoodsClassList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'listorder',
                sortOrder: 'desc',
                idField: 'stores_class_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: '门店品牌明细管理',
                nowrap: "true",
                pageSize: 15,
                toolbar: '#selectForm3',
//                pagination:true,
                onDblClickRow:function (rowIndex,rowData) {
                    update();
                },
                columns: [[
                    { field: 'stores_brand_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'listorder', title: 'ID',  align: 'center',hidden:true},
                    { field: 'class_desc', title: 'ID',  align: 'center',hidden:true},
                    { field: 'type', title: 'ID',  align: 'center',hidden:true},
                    { field: 'stores_id', title: 'ID',  align: 'center',hidden:true},
                    { field: 'stores_class_id', title: 'ID', width: '10%', align: 'center'},
                    { field: 'class_name', title: '类别名称', width: '15%', align: 'center'},
                    { field: 'stores_name', title: '店铺', width: '20%', align: 'center'},
                    { field: 'bd_id', title: '所属品牌', width: '10%', align: 'center',formatter:function(value,row){
                        <c:forEach var="item" items="${brandlist}">
                        if(row.bd_id=="${item.brand_id}"){
                            return "${item.brand_name}";
                        }
                        </c:forEach>
                    }  },
                    { field: 'food_category_id', title: '饿了么', width: '10%', align: 'center',formatter:function(value,row){
//                        storesBindQuery(row.stores_brand_id,row.stores_class_id);
                        if(!validateStr(value)){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" name="' + row.stores_class_id + '" onclick=elemeAll('+row.stores_class_id+',"",1,'+row.elem_restaurant_id+') style="text-decoration:none;color: red">绑定</a>'
                        }else if(row.type==2){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=elemeAll('+row.stores_class_id+','+row.food_category_id+',2,"") style="text-decoration:none;color: green" >更新</a>';
                        }else{
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=elemeAll('+row.stores_class_id+','+row.food_category_id+',3,"") style="text-decoration:none;color: green" >解绑</a>'
                        }
                    }},
                    { field: 'meituan_cat_id', title: '美团', width: '10%', align: 'center',formatter:function(value,row){
                        var id = row.stores_brand_id;
//                        storesBindQuery(row.stores_brand_id,row.stores_class_id);
                        if(value==""||value==undefined){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" class="' + row.stores_class_id + '" onclick=mtAll('+row.stores_class_id+','+row.stores_brand_id+',1) style="text-decoration:none;color: red" >绑定</a>';
                        }else if(row.class_name!=value){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" onclick=mtAll('+row.stores_class_id+','+row.stores_brand_id+',1) style="text-decoration:none;color: green" >更新</a>';
                        }else{
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=mtAll('+row.stores_class_id+','+row.stores_brand_id+',2) style="text-decoration:none;color: green" >解绑</a>';
                        }
                    }},
                    { field: 'baidu_cat_id', title: '百度', width: '10%', align: 'center',formatter:function(value,row){
                        var id = row.stores_brand_id;
                        if(value==""||value==undefined){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" class="' + row.stores_class_id + '" onclick=baiduAll('+row.stores_class_id+','+row.stores_brand_id+',1) style="text-decoration:none;color: red" >绑定</a>';
                        }else if(row.class_name!=value){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton" onclick=baiduAll('+row.stores_class_id+','+row.stores_brand_id+',1) style="text-decoration:none;color: green" >更新</a>';
                        }else{
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=baiduAll('+row.stores_class_id+','+row.stores_brand_id+',2) style="text-decoration:none;color: green" >解绑</a>';
                        }
                    }},
                    { field: 'cz', title: '操作', width: '10%', align: 'center',formatter:function(value,row){
                        return '<span onclick="deleteClass('+row.stores_class_id+')" STYLE="color: red" >删除</sapn>'
                    } }
                ]]

            });


            $('#tabs2').tabs({
                onSelect: function(title){
                    if(title=="平台"){

                    }else if(title=="饿了么"){
                        var stores_id = $('#stores_idc').val();
                        var brand_id = $('#brand_idc').val();
                        var stores_name = $('#stores_namec').val();
                        if(!validateStr(brand_id)){
                            $.messager.show({
                                title:'提示信息',
                                msg:'请先选择品牌',
                                showType:'show'
                            });
                            return false;
                        }
                        greatEGrid(stores_name,brand_id,stores_id);
                    }else{
                        var stores_id = $('#stores_idc').val();
                        var brand_id = $('#brand_idc').val();
                        var stores_name = $('#stores_namec').val();
                        if(!validateStr(brand_id)){
                            $.messager.show({
                                title:'提示信息',
                                msg:'请先选择品牌',
                                showType:'show'
                            });
                            return false;
                        }
                        greatMtGrid(stores_name,brand_id,stores_id)
                    }
                }
            })

            $('#stores_brand_idc').combotree({
                url:'/PtGoodsManage/getStoresCombobox',
                panelWidth:'200px',
                panelHeight:'300px',
                valueField:'id',
                textField:'text'
            });

            $('#stores_brand_id2').combotree({
                url:'/PtGoodsManage/getStoresCombobox',
                panelWidth:'200px',
                panelHeight:'300px',
                valueField:'id',
                textField:'text'
            });

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

            $("#stores_brand_idc").combotree({

                onChange: function (n, o) {
                    $("#selectForm2").serializeSelectJson("List");

                }
            });

            $("#stores_brand_id").combotree({

                onChange: function (n, o) {
                    $("#selectForm").serializeSelectJson("List_class");

                }
            });
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
                        greatGread();
                        $("#bc").unbind('click');
                        $("#bc").on("click", function() {
                            saveprint2();
                        });
                    }
                }
            })


            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("List_class");
            });

            $("#refreshBtn").click(function () {
                $("#class_name").textbox('setValue', '');
                $("#stores_brand_id").textbox('setValue', '');
                $("#selectForm").serializeSelectJson("List_class");
            });

            $("#findBtn2").click(function () {
                $("#selectForm2").serializeSelectJson("List");
            });

            $("#refreshBtn2").click(function () {
                $("#class_namec").textbox('setValue', '');
                $("#stores_brand_idc").textbox('setValue', '');
                $("#selectForm2").serializeSelectJson("List");
            });

        })

        function greatGread() {
            $('#List_class').datagrid({
                url: '/PtGoodsManage/getGoodsClassList',
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
                pageSize: 50,
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


        function add() {
            $("#dlg").dialog("open").dialog('setTitle', '添加');
            $("#opt").val("add");
            $("#stores_brand_id2").combotree("setValue",'');
            $("#class_name").textbox("setValue",'');
            $("#listorder").textbox("setValue",'');
            $("#class_desc").val('');
            $("#brand_id").combobox("setValue",'');
            greatGread();
        }

        function update() {
            var row = $('#List').datagrid('getSelected');
            if(!validateStr(row)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择一条记录',
                    showType:'show'
                });
                return false;
            }
            $("#dlg").dialog("open").dialog('setTitle', '修改');
            $("#opt").val("update");
            $("#stores_brand_id2").combotree("setValue",row.stores_brand_id);
            $("#class_name").textbox("setValue",row.class_name);
            $("#listorder").textbox("setValue",row.listorder);
            $("#class_desc").val(row.class_desc);
            $("#stores_class_id").val(row.stores_class_id);
            $("#brand_id").combobox("setValue",row.bd_id);
        }


        function saveprint2() {
            var rows = $('#List_class').datagrid('getChecked');
            if(!validateStr(rows)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择一条记录',
                    showType:'show'
                });
                return false;
            }
            var stores_brand_id = $("#stores_brand_id3").val();
            if(!validateStr(stores_brand_id)){
                $.messager.show({
                    title:'提示信息',
                    msg:'请先选择店铺',
                    showType:'show'
                });
                return false;
            }
            var datas = JSON.stringify(rows);
            $.ajax({
                url: "/PtGoodsManage/saveClassList",
                data: {jsondata:datas,stores_brand_id:stores_brand_id},
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

        function saveprint(){
            var stores_brand_id = $("#stores_brand_id2").val();
            var class_name = $("#class_name").val();
            var listorder = $("#listorder").val();
            var class_desc = $("#class_desc").val();
            var stores_class_id = $("#stores_class_id").val();
            var opt =$("#opt").val();
            if(!validateStr(stores_brand_id)||!validateStr(class_name)||!validateStr(listorder)){
                $.messager.show({
                    title:'提示信息',
                    msg:'相关信息不能为空',
                    showType:'show'
                });
                return false;
            }
            if(class_name.length>10){
                $.messager.show({
                    title:'提示信息',
                    msg:'分类名称不能超过10个字！',
                    showType:'show'
                });
                return false;
            }
            $.ajax({
                url: "/PtGoodsManage/saveClass",
                data: {stores_brand_id:stores_brand_id,class_name:class_name,listorder:listorder,class_desc:class_desc,opt:opt,stores_class_id:stores_class_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.show({
                            title:'提示信息',
                            msg:'操作成功',
                            showType:'show'
                        });
                        $("#dlg").dialog("close");
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


        function greatEGrid(stores_name,brand_id,stores_id) {
            $('#eList').datagrid({
                url:'/PtGoodsManage/getElemeClassList2?brand_id='+brand_id+'&stores_id='+stores_id,
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
                    { field: 'id', title: 'Id', align: 'center',width: '10%' },
                    { field: 'stores_name', title: '商铺', width: '20%', align: 'center',formatter:function(value,row){
                        return stores_name;
                    } },
                    { field: 'name', title: '商品名称', width: '20%', align: 'center'},
                    { field: 'description', title: '类别描述', width: '30%', align: 'center'},
                    { field: 'estatus', title: '饿了么绑定状态', width: '20%', align: 'center',formatter:function(value,row){
                        elemeClassQuery(row.id,brand_id,stores_id,stores_name);
                        return '<span id="'+row.id+'" name="dept" ></sapn>'
                    } }
                ]]
            });
        }

        function greatMtGrid(stores_name,brand_id,stores_id) {
            $('#mtList').datagrid({
                url:'/PtGoodsManage/getMtClassList?brand_id='+brand_id+'&stores_id='+stores_id,
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
                    { field: 'id', title: 'Id', align: 'center',width: '10%',hidden:true },
                    { field: 'stores_brand_id', title: 'Id', align: 'center',width: '10%',hidden:true },
                    { field: 'stores_name', title: '商铺', width: '20%', align: 'center',formatter:function(value,row){
                        return stores_name;
                    } },
                    { field: 'name', title: '类别名称', width: '20%', align: 'center'},
//                    { field: 'description', title: '类别描述', width: '30%', align: 'center'},
                    { field: 'cz', title: '美团绑定状态', width: '20%', align: 'center',formatter:function(value,row){
                        mtClassQuery(row.stores_brand_id,row.name,row.id,stores_name,brand_id,stores_id);
                        return '<span id="'+row.id+'" name="dept" ></sapn>'
                    } }
                ]]
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

        function refreshList() {
            $('#selectForm3').serializeSelectJson('List');
        }


        function elemeAll(stores_class_id,food_category_id,type,elem_restaurant_id) {
            if(type==1){
                //绑定
                if(!validateStr(stores_class_id)&&validateStr(elem_restaurant_id)){
                    $.messager.show({
                        title:'提示信息',
                        msg:'请先绑定门店',
                        showType:'show'
                    });
                    return false;
                }
            }else if(type==2){
                //更新
                if(!validateStr(stores_class_id)&&validateStr(food_category_id)){
                    $.messager.show({
                        title:'提示信息',
                        msg:'请先绑定类别',
                        showType:'show'
                    });
                    return false;
                }
            }else if(type==3){
                //解绑
            }
            var datas={stores_class_id:stores_class_id,food_category_id:food_category_id,type:type,elem_restaurant_id:elem_restaurant_id};
            $.ajax({
                url: "/PtGoodsManage/elemeAll",
                data: datas,
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

        function mtAll(stores_class_id,stores_brand_id,type) {
            var datas={stores_class_id:stores_class_id,stores_brand_id:stores_brand_id,type:type};
            $.ajax({
                url: "/PtGoodsManage/mtAll",
                data: datas,
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

        function elemeClassQuery(id,brand_id,stores_id,stores_name) {
            if (validateStr(id)){
                $.ajax({
                    url: "/PtGoodsManage/elemeClassQuery",
                    data: {food_category_id:id,brand_id:brand_id,stores_id:stores_id},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $('#'+id).combobox({
                                url:'/PtGoodsManage/getElemeClass?brand_id='+brand_id+'&stores_id='+stores_id,
                                valueField:'id',
                                textField:'text',
                                onChange:function (newValue,oldValue) {
                                    elemeAll(newValue,id,2,"");
                                    greatEGrid(stores_name,brand_id,stores_id);
                                }
                            });
                        } else {
                            $('#'+id).text("已绑定");
//                            $('#'+id).attr("readonly",true);
//                            $('#'+id).attr('disabled','disabled');
                        }
                    }
                });
            }

        }

        function deleteClass(stores_class_id) {
            $.messager.confirm('系统提示', '确定删除吗?', function(r){
                if (r){
                    $.ajax({
                        url: "/PtGoodsManage/deleteClass",
                        data: {stores_class_id:stores_class_id},
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {
                                $("#List").datagrid("reload")
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
            });

        }

        function mtClassQuery(stores_brand_id,name,id,stores_name,brand_id,stores_id) {
            if (validateStr(stores_brand_id)){
                $.ajax({
                    url: "/PtGoodsManage/mtClassQuery",
                    data: {stores_brand_id:stores_brand_id,name:name},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $('#'+id).combobox({
                                url:'/PtGoodsManage/getMtClass?stores_brand_id='+stores_brand_id+'&name='+name,
                                valueField:'id',
                                textField:'text',
                                onChange:function (newValue,oldValue) {
                                    var datas={stores_class_id:newValue,stores_brand_id:stores_brand_id,type:'3',name:name};
                                    $.ajax({
                                        url: "/PtGoodsManage/mtAll",
                                        data: datas,
                                        type: 'POST',
                                        success: function (result) {
                                            if (result.status == "1") {
                                                greatMtGrid(stores_name,brand_id,stores_id);
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
                            });
                        } else {
                            $('#'+id).text("已绑定");
                        }
                    }
                });
            }

        }


        function baiduAll(stores_class_id,stores_brand_id,type) {
            var datas={stores_class_id:stores_class_id,stores_brand_id:stores_brand_id,type:type};
            $.ajax({
                url: "/PtGoodsManage/baiduClasssAll",
                data: datas,
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




    </script>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;">
    <!--  =========================所有商铺=====================   -->
    <div data-options="region:'west',title:'',split:true" style="width:15%;height: 100%" id="chuFang">
        <ul id="tt2" class="easyui-tree" data-options="
                        url: '/PtGoodsManage/getClassTree?type=2',
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
                                 $('#stores_namec').val(node.text);
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
                            }else {
                                $('#brand_idc').val('');
                                $('#stores_brand_idc').val('');
                                $('#stores_class_idc').val(node.id);
                                $('#stores_namec').val(node.name);
                                 refreshList();
                            }


                        }

                    "></ul>
    </div>
    <%--<div data-options="region:'center',title:'',iconCls:'icon-ok'" style="width: 90%;height: 100%;">--%>
    <%----%>
    <%--</div>--%>
    <div class="easyui-layout" data-options="region:'center',title:'',iconCls:'icon-ok'" style="overflow: hidden;border:0;height: 95%">
        <div data-options="region:'center'" >
            <div class="easyui-tabs" style="width:100%;height: 100%;border:0" id="tabs2">
                <div title="平台" >
                    <table id="List"></table>
                </div>
                <div title="饿了么" >
                    <table id="eList"></table>
                </div>
                <div title="美团 ">
                    <table id="mtList"></table>
                </div>
            </div>
        </div>

        <div id="selectForm3">
            <div class="easyui-panel" style="background:#fff;height:30px;border: 0;width: 100%">
                <div style="float: right;padding-left: 5px">
                    <input id="stores_idc" name="stores_id"   type="hidden" value="">
                    <input id="stores_class_idc" name="stores_class_id" type="hidden"  value="">
                    <input id="brand_idc" name="brand_id" type="hidden"  value="">
                    <input id="stores_namec" name="stores_namec" type="hidden"  value="">
                    <a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right;margin-right: 20px;" onclick="add()" id="btn">添加</a>

                </div>
            </div>
        </div>

    </div>

</div>


<%--<div class="easyui-layout" style="width:100%;height:100%;">--%>
    <%--<div class="easyui-tabs" style="width:100%;height: 100%;border:0" id="tabs2">--%>
        <%--<div title="平台" style="display:none;">--%>
            <%--<table id="List"></table>--%>
        <%--</div>--%>
        <%--<div title="饿了么" >--%>
            <%--<table id="eList"></table>--%>
        <%--</div>--%>
        <%--<div title="美团 ">--%>
            <%--<table id="mtList"></table>--%>
        <%--</div>--%>
    <%--</div>--%>
        <%--&lt;%&ndash;<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" style="float: right;margin-right: 20px;" onclick="update()" id="btn2">修改</a>&ndash;%&gt;--%>
    <%--<div id="selectForm2">--%>
        <%--<div class="easyui-panel" style="background:#fff;height:100%;border: 0">--%>
            <%--<div style="float: left;padding-left: 5px">--%>
                <%--<input class="easyui-textbox" name="class_name" id="class_namesc" style="width:250px"--%>
                       <%--data-options="label:'分类名称：',prompt:'分类名称模糊查询'">--%>
                <%--<label  class="mytitle">门店:</label>--%>
                <%--<input id="stores_brand_idc" name="stores_brand_id"  class="easyui-textbox easyui-validatebox" style="width:160px;" value="">--%>
                <%--<a href="###" id="findBtn2" class="easyui-linkbutton " iconCls="icon-search">查询</a>--%>
                <%--<a href="###" id="refreshBtn2" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>--%>
                <%--<a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" style="float: right;margin-right: 20px;" onclick="add()" id="btn">添加</a>--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>



<div id="dlg" class="easyui-dialog" style="width: 50%; height: 80%; padding: 10px 20px;"
     closed="true" buttons="#dlg-buttons">
    <div data-options="region:'center',title:''" style="width: 100%;height: 100%;border:0">
        <div class="easyui-tabs" style="width:100%;height: 100%;border:0" id="tabs">
            <div title="单个添加">
                <form id="fm" method="post">
                    <%--<div class="fitem">--%>
                        <%--<label class="mytitle">品牌</label>--%>
                        <%--<select class="easyui-combobox easyui-validatebox" id="brand_id" name="brand_id" style="width:160px;"--%>
                        <%--data-options="editable:false,panelHeight:'auto'" required="true">--%>
                        <%--<c:forEach var="item" items="${brandlist}">--%>
                        <%--<option value="${item.brand_id}">${item.brand_name}</option>--%>
                        <%--</c:forEach>--%>
                        <%--</select>--%>
                    <%--</div>--%>
                    <div class="fitem">
                        <label  class="mytitle">门店:</label>
                        <input id="stores_brand_id2" name="stores_brand_id"  class="easyui-textbox easyui-validatebox" style="width:160px;" >
                    </div>
                    <div class="fitem">
                        <label  class="mytitle">商品种类名称:</label>
                        <input name="class_name" class="easyui-textbox easyui-validatebox" required="true" id="class_name"/>
                        <span id="check1" style="color: red"></span>
                    </div>
                    <div class="fitem">
                        <label class="mytitle">类别排序:</label>
                        <input name="listorder" class="easyui-textbox easyui-numberbox" id="listorder" required="true" />
                    </div>
                    <div class="fitem">
                        <label class="mytitle">类别描述:</label>
                        <textarea cols=40 rows=5 name=class_desc class="textbox" id="class_desc"></textarea>
                    </div>
                        <input name="stores_class_id" id="stores_class_id" type="hidden" value="" />
                    <input name="opt" id="opt" type="hidden" value="" />
                </form>
            </div>
            <div title="批量添加" style="display:none;">
                <table id="List_class"></table>
            </div>
        </div>
    </div>
    <div id="selectForm">
        <div class="easyui-panel" style="background:#fff;height:100%;">
            <div style="float: left;padding-left: 5px">
                <input class="easyui-textbox" name="class_name" id="class_names" style="width:150px"
                       data-options="label:'分类名称：',prompt:'分类名称模糊查询'">
                <label  class="mytitle">门店:</label>
                <input id="stores_brand_id" name="stores_brand_id"  class="easyui-textbox easyui-validatebox" style="width:100px;" value="">
                <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search">查询</a>
                <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
                <label  class="mytitle">添加到:</label>
                <input id="stores_brand_id3" name="stores_brand_id3"  class="easyui-textbox easyui-validatebox" style="width:100px;" >
            </div>
        </div>
    </div>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" id="bc" iconcls="icon-save">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')"
       iconcls="icon-cancel">取消</a>
</div>
</div>


</body>
</html>
