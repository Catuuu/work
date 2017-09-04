
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
        $(function () {
            stores_id= ${stores_id}
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
                    brand_id:"1",
                    stores_id:stores_id,
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
                    //根据总数分配美团、饿了么、微信数量
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
                                wx_count: wxcount
                            }
                        });
                    }
                    $.ajax({
                        url: "/PtGoodsManage/updateStoresGoods",
                        data: {sgl_id: rowData.sgl_id,
                            elem_count:ecount,
                            mt_count:mcount,
                            wx_count:wxcount,
                            all_count:changes.all_count,
                            market_price:rowData.market_price,
                            box_price:rowData.box_price,
                            box_count:rowData.box_count,
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
                    { field: 'good_name', title: '商品名称', width: '20%', align: 'center' },
                    { field: 'class_name', title: '类别', width: '10%', align: 'center' },
                    { field: 'all_count', title: '总数', width: '10%', align: 'center',
                        styler: function(value,row,index){return 'color:green;cursor:pointer'},
                        editor: {
                            type: 'numberbox',
                            options: {
                                min: 0,
                            }
                        }
                    },
                    { field: 'elem_count', title: '饿了么', width: '10%', align: 'center'},
                    { field: 'mt_count', title: '美团', width: '5%', align: 'center'},
                    { field: 'wx_count', title: '微信', width: '5%', align: 'center' },
                    { field: 'market_price', title: '价格', width: '5%', align: 'center'},
                    { field: 'unit', title: '单位', width: '5%', align: 'center'},
                    { field: 'box_price', title: '餐盒价格', width: '7%', align: 'center'},
                    { field: 'box_count', title: '餐盒数量', width: '10%', align: 'center'}
//                    { field: 'status', title: '售卖状态', width: '10%', align: 'center',formatter:function(value,row){
//                        if(value=="1"){
//                            return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=sellStatus('+row.sgl_id+',"2","'+row.good_name+'","'+row.class_name+'",0) style="text-decoration:none;color: green" >售卖中</a>'
//                        }else{
//                            return'<a href="javascript:void(0)" class="easyui-linkbutton"  onclick=sellStatus('+row.sgl_id+',"1","'+row.good_name+'","'+row.class_name+'",'+row.elem_count+') style="text-decoration:none;color: red" >暂停售卖</a>'
//                        }
//                    }}
                ]]

            });

            $('#tt').tabs({
                onSelect: function(title){
                    $("#stores_id").val(stores_id);
                    if(title=="菜大师"){
                        $("#brand_id").val("1");
                        $('#selectForm').serializeSelectJson('List');
                    }else if(title=="帅小锅"){
                        $("#brand_id").val("2");
                        $('#selectForm').serializeSelectJson('List');
                    }
                }
            });
        });

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

        function goodClass(id) {
            $("#cdsGoodClass li").each(function () {
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

        function goodClass2(id) {
            $("#sxgGoodClass li").each(function () {
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




    </script>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;">

    <div data-options="region:'north'" style="height:30px">
        <h2 style="margin-left: 10px;margin-top: 5px;">
            门店品牌明细管理</h2>
    </div>
    <div data-options="region:'west',split:true" style="width:15%;">
        <div class="easyui-tabs" style="height: 100%" id="tt">
            <div title="菜大师" style="height: 100%" id="cdsGoodClass">
                <a href="###" onclick=goodClass("")> <li class="mdss" name="all" style="background-color:#C0C0C0 ">全部</li></a>
                <c:forEach var="item" items="${cds_list}">
                    <a href="javascript:void(0)" onclick="tzbg(${item.class_id},${item.class_name})">
                        <a href="###" onclick=goodClass(${item.class_id}) ><li class="mdss" name="${item.class_id}">${item.class_name}</li></a>
                    </a>
                </c:forEach>
            </div>
            <div title="帅小锅" id="sxgGoodClass">
                <a href="###" onclick=goodClass2("")> <li class="mdss" name="all" style="background-color:#C0C0C0 ">全部</li></a>
                <c:forEach var="item" items="${sxg_list}">
                    <a href="javascript:void(0)" onclick="tzbg(${item.class_id},${item.class_name})">
                        <a href="###" onclick=goodClass2(${item.class_id}) ><li class="mdss" name="${item.class_id}">${item.class_name}</li></a>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
    <div data-options="region:'center',title:'',iconCls:'icon-ok'">
        <table id="List">
        </table>
        <input type="hidden" id="meituan_restaurant_id" name="meituan_restaurant_id">
        <input type="hidden" id="elem_restaurant_id" name="elem_restaurant_id">
    </div>

    <div id="selectForm">
            <input type="hidden"  name="brand_id" id="brand_id">
            <input type="hidden"  name="stores_id" id="stores_id">
            <input type="hidden" id="class_id" name="class_id">
            <%--<input type="hidden" id="good_id" name="good_id">--%>

    </div>



</div>
</body>
</html>
