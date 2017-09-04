
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
        var ptsrc="/PtGoodsManage/indexPt?stores_id=''&brand_id=''&class_id=''&good_id=''&status=''";
        var elemsrc="";
        var mtsrc="";
        $(function () {
            $('#btn').linkbutton('disable');

            $('#tabs').tabs({
                onSelect: function(title){
                    if(title=="平台"){
                        document.getElementById('pt').src=ptsrc;
                    }else if(title=="饿了么"){
                        document.getElementById('elem').src=elemsrc;
                    }else if(title=="美团"){
                        document.getElementById('mt').src=mtsrc;
                    }
                }
            });

        });

        
        function refreshList(brand_id,stores_id,brand_name,stores_name,restaurant_id,stores_brand_id) {
            if(stores_id==""||stores_id=="undefined"){
                stores_id=null;
            }
            if(restaurant_id==""||restaurant_id=="undefined"){
                restaurant_id=null;
            }
             ptsrc = "/PtGoodsManage/indexPt?brand_id="+brand_id+"&stores_id="+stores_id+"&brand_name='"+brand_name+"'&stores_name='"+stores_name+"'&class_id=''&good_id=''&status=''";
            if(restaurant_id==""||restaurant_id==undefined||restaurant_id==null){
                elemsrc="";
            }else{
                elemsrc = "/PtGoodsManage/indexElem?brand_id="+brand_id+"&stores_id="+stores_id+"&restaurant_id="+restaurant_id;
            }
             mtsrc = "/PtGoodsManage/indexMt?brand_id="+brand_id+"&stores_id="+stores_id+"&stores_brand_id="+stores_brand_id;
            document.getElementById('pt').src=ptsrc;
//            if(restaurant_id!=null&&restaurant_id!="undefined"&&restaurant_id!=""){
//                document.getElementById('elem').src=elemsrc;
//            }
//            if(stores_brand_id!=null&&stores_brand_id!="undefined"&&stores_brand_id!=""){
//                document.getElementById('mt').src=mtsrc;
//            }
        }

        function refreshList2(class_id,good_id,status) {
             ptsrc = "/PtGoodsManage/indexPt?stores_id=''&brand_id=''&class_id="+class_id+"&good_id=''&status="+status;
            document.getElementById('pt').src=ptsrc;
        }

        function refreshList3(class_id,good_id,status) {
             ptsrc = "/PtGoodsManage/indexPt?stores_id=''&brand_id=''&class_id="+class_id+"&good_id="+good_id+"&status="+status;
            document.getElementById('pt').src=ptsrc;
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
        <div data-options="region:'center',title:'',iconCls:'icon-ok'" style="height: 100%;width: 100%">
            <div class="easyui-tabs" style="width: 100%;height: 100%">
                <div title="品牌" style="width: 100%;height: 100%">
                    <ul id="tt" class="easyui-tree" data-options="
                        url: '/PtGoodsManage/GetStoresTree',
                        method: 'post',
                        animate: true,
                        onClick: function(node){
                            var rows = node.children;
                            if( rows != undefined && rows.length>0 ){
                                <%--$('#btn').linkbutton('disable');--%>
                                <%--$(':input','#selectForm').not(':button,:submit,:reset') .val('');--%>
                                <%--$('#brand_id2').val(node.id);--%>
                                refreshList(node.id,'','','','','');
                            }else{
                                <%--$('#btn').linkbutton('enable');--%>
                                <%--$(':input','#selectForm').not(':button,:submit,:reset') .val('');--%>
                                <%--var parent = $('#tt').tree('getParent', node.target);--%>
                                <%--$('#brand_id2').val(parent.id);--%>
                                <%--$('#stores_id2').val(node.id);--%>
                                <%--$('#s_brand_id').val(parent.id);--%>
                                <%--$('#stores_id').val(node.id);--%>
                                <%--$('#s_stores_name').textbox('setValue',node.text);--%>
		                        <%--$('#s_brand_name').textbox('setValue',parent.text);--%>
                                <%--$('#meituan_restaurant_id').val(node.meituan_restaurant_id);--%>
                                <%--$('#elem_restaurant_id').val(node.elem_restaurant_id);--%>
                                 <%--$('#stores_brand_id').val(node.stores_brand_id);--%>
                                <%--getGood_ids(node.id,parent.id);--%>
                                <%--getCombobox(parent.id);--%>
                                var parent = $('#tt').tree('getParent', node.target);
                                refreshList(parent.id,node.id,parent.text,node.text,node.elem_restaurant_id,node.stores_brand_id);
                            }
                            <%--$('#selectForm').serializeSelectJson('List');--%>
                        }
                    "></ul>
                </div>
                <div title="菜品" style="width: 100%;height: 100%">
                    <ul id="tt2" class="easyui-tree" data-options="
                        url: '/PtGoodsManage/GetGoodsTree',
                        method: 'post',
                        animate: true,
                        onClick: function(node){
                            var rows = node.children;
                            $('#btn').linkbutton('disable');
                            if( rows != undefined && rows.length>0 ){
                                $(':input','#selectForm').not(':button,:submit,:reset') .val('');
                                $('#class_id').val(node.id);
                                refreshList2(node.id,'','1');
                            }else{
                                $(':input','#selectForm').not(':button,:submit,:reset') .val('');
                                   $('#good_id').val(node.id);
                                   var parent = $('#tt2').tree('getParent', node.target);
                                   refreshList3(parent.id,node.id,'1');
                            }
                        }
                    "></ul>
                </div>
            </div>
        </div>
    </div>
    <div data-options="region:'center',title:'',iconCls:'icon-ok'">
        <div class="easyui-tabs" style="height: 100%" id="tabs">
            <div title="平台" style="height: 100%;overflow: hidden;">
                <Iframe src="/PtGoodsManage/indexPt?stores_id=''&brand_id=''&class_id=''&good_id=''&status=''" id="pt" width=100% height=100% scrolling="no" frameborder="0"></iframe>
                <%--<table id="List">--%>
                <%--</table>--%>
            </div>
            <div title="饿了么">
                <Iframe src="" id="elem" width=100% height=100% scrolling="no" frameborder="0"></iframe>
            </div>
            <div title="美团">
                <Iframe src="" id="mt" width=100% height=100% scrolling="no" frameborder="0"></iframe>
            </div>
        </div>
        <input type="hidden" id="meituan_restaurant_id" name="meituan_restaurant_id">
        <input type="hidden" id="elem_restaurant_id" name="elem_restaurant_id">
    </div>

    <div id="selectForm">
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



</div>
</body>
</html>
