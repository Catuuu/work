
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
        var food_category_id;
        $(function () {
            $('#btn').linkbutton('disable');
             stores_id=${stores_id};
             brand_id =${brand_id};
        })
        function greatGrid(id) {
            $('#List').datagrid({
                url: '/PtGoodsManage/eleClassGoodsQuery?food_category_id='+id,
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),
                sortName: 'g.good_id',
                idField: 'good_id',
                singleSelect: true,//单选模式
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                //title: 'erp商品原料',
                nowrap: "true",
                pagination: false,
                columns: [[
                    {field:'food_id',title:'商品id',width:'35%',align: 'center',hidden:true},
                    {field:'isband',title:'是否绑定(0未绑定，1已绑定)',width:'35%',align: 'center',hidden:true},
                    {field:'name',title:'商品名称',width:'35%',align: 'center',sortable:true},
                    {field:'stores_name',title:'店铺名称',width:'35%',align: 'center',sortable:true},
                    {field:'cz',title:'操作',width:'30%',align: 'center',sortable:true,formatter:function(value,row){
                        if(row.isband=="0"){
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=elemBandGoods('+row.food_id+') style="text-decoration:none;color: red" >绑定</a>'
                        }else {
                            return '<a href="javascript:void(0)" class="easyui-linkbutton"  style="text-decoration:none;color: green" >已绑定</a>'
                        }
                    }}
                ]]
            });
        }

        function greatTree() {
            $('#xzcc').combotree({
                url:'/PtGoodsManage/getGoodTree?food_category_id='+food_category_id+'&stores_id='+stores_id+'&brand_id='+brand_id,
                onClick:function (node) {
                    $("#name").val(node.text);
                    $("#market_price").val(node.market_price);
                    $("#good_info").val(node.good_info);
                    $("#elem_count").val(node.elem_count);
                    $("#sgl_id").val(node.id);
                    $("#box_price").val(node.box_price);
                    if (node.food_id != null&&node.food_id !="") {
                        $.messager.show({
                            title: '提示信息',
                            msg: '该商品已绑定',
                            showType: 'show'
                        });
                        $('#xzcc').combotree('setValue', '');
                    }
                },
                required: true
            });
        }

        function goodClass(id) {
            food_category_id = id;
            $("#storesGoodClass li").each(function () {
                $(this).css("backgroundColor", "");
            });
            $("a [name='"+id+"']").css("backgroundColor", "#C0C0C0");
            if(id==null||id==""){
                $("a [name='all']").css("backgroundColor", "#C0C0C0");
            }
            greatGrid(id);
        }
        
        function elemBandGoods(food_id) {
            greatTree();
            $("#food_id").val(food_id);
            $("#dlg2").dialog("open").dialog('setTitle', '绑定');
        }

        function saveReverseBind() {
            var name = $("#name").val();
            var price = $("#market_price").val();
            var description = $("#good_info").val();
            var stock = $("#elem_count").val();
            var packing_fee = $("#box_price").val();
            var food_id = $("#food_id").val();
            var sgl_id = $("#sgl_id").val();
//            alert("food_id"+"="+food_id+"+++++++"+"food_category_id"+"="+food_category_id);
//            return false;
            $.ajax({
                url: "/PtGoodsManage/eleUpdateFood",
                data: {food_id:food_id,stock:stock,
                    description:description,price:price,
                        food_category_id:food_category_id,
                        name:name,sgl_id:sgl_id,packing_fee:packing_fee},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        greatGrid(food_category_id);
                        $('#dlg2').dialog('close');
                        $.messager.show({
                            title:'提示信息',
                            msg:'绑定成功',
                            showType:'show'
                        });
                    }else{
                        $.messager.alert("提示信息", "绑定失败");
                    }
                }
            });
        }

        function eleDeleteFood(food_id) {
            $.ajax({
                url: "/PtGoodsManage/eleDeleteFood",
                data: {food_id:food_id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        greatGrid(food_category_id);
                        $('#dlg2').dialog('close');
                        $.messager.show({
                            title:'提示信息',
                            msg:'操作成功',
                            showType:'show'
                        });
                    }else{
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }



    </script>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;">

    <div data-options="region:'north'" style="height: 0px">
    </div>
    <div data-options="region:'west',split:true" style="width:10%;padding-left: 0px;padding-right: 0px">
        <div data-options="region:'center',title:'',iconCls:'icon-ok'" style="height: 100%;width: 100%" id="storesGoodClass">
            <c:forEach var="item" items="${listjo}">
                <a href="javascript:void(0)" onclick=goodClass(${item.food_category_id})>
                    <li class="mdss" name="${item.food_category_id}">${item.name}</li>
                </a>
            </c:forEach>
        </div>
    </div>
    <div data-options="region:'center',title:'',iconCls:'icon-ok'">
        <table id="List">
        </table>
        <input type="hidden" id="meituan_restaurant_id" name="meituan_restaurant_id">
        <input type="hidden" id="elem_restaurant_id" name="elem_restaurant_id">
    </div>

    <div id="dlg2" class="easyui-dialog" style="width: 550px; height: 250px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons2">
        <form id="fm2" method="post">
            <div class="fitem" style="padding-top:10%;padding-left:10%;">
                <label class="mytitle">请选择商品</label>
                <select id="xzcc" name="sgl_id" panelWidth="300px" panelHeight="200px" class="easyui-combotree" style="width:200px;">
                </select>
            </div>
            <input name="sgl_id" type="hidden" value=""  id="sgl_id"/>
            <input name="food_id" type="hidden" value=""  id="food_id"/>
            <input name="name" type="hidden" value=""  id="name"/>
            <input name="market_price" type="hidden" value=""  id="market_price"/>
            <input name="good_info" type="hidden" value=""  id="good_info"/>
            <input name="elem_count" type="hidden" value=""  id="elem_count"/>
            <input name="box_price" type="hidden" value=""  id="box_price"/>
        </form>
    </div>
    <div id="dlg-buttons2">

        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveReverseBind()">绑定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
    </div>




</div>
</body>
</html>
