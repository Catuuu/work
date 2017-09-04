<%--
  订单列表
  User: chenbin
  Date: 12-12-26
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>骑手设置</title>
    <style>
        .datagrid-row {
            height: 42px;
            text-align: center;
        }

        .datagrid-header-row {
            height: 42px;
            font-weight: 700;
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
    </style>
    <script type="text/javascript">
        $(function () {

            $.postJSON("/Stores/storesCharge", null,function (data) {
                var lastIndex;
                $('#tableList').datagrid({
                    data:data.obj.list,
                    iconCls: 'icon-save',
                    fitColumns: false,
                    rownumbers: true,//行号
                    singleSelect: true,//单行选取
                    pagination: false,
                    columns:
//                        JSON.parse(data.obj.titleStr),
                        [[
                {field:"name",title:"店铺名",width:80,height:50,align:"center"},
                {field:"t1",title:"短信",width:80,height:50,align:"center",editor:{type: 'numberbox',options: {min: 0,precision: 2}}},
                {field:"t2",title:"微信",width:80,height:50,align:"center",editor:{type: 'numberbox',options: {min: 0,precision: 2}}},
                {field:"t3",title:"订单抓取",width:80,height:50,align:"center",editor:{type: 'numberbox',options: {min: 0,precision: 2}}},
                {field:"t4",title:"打包出餐",width:80,height:50,align:"center",editor:{type: 'numberbox',options: {min: 0,precision: 2}}},
                {field:"t5",title:"厨房出单",width:80,height:50,align:"center",editor:{type: 'numberbox',options: {min: 0,precision: 2}}}
                            ]],



//                    onBeforeEdit:function(index,row){
//                        row.editing = true;
//                        updateActions(index);
//                    },
//                    onAfterEdit:function(index,row){
//                        row.editing = false;
//                        updateActions(index);
//                    },
//                    onCancelEdit:function(index,row){
//                        row.editing = false;
//                        updateActions(index);
//                    },
                    onClickCell: function(rowIndex, field, value){
                        if (lastIndex != rowIndex){
                            $('#tableList').datagrid('endEdit', lastIndex);
                            var data =  $("#tableList").datagrid("getData").rows[lastIndex];
                            if(data){
                                $.postJSON("/Stores/storesChargeSave",data);
                            }
                            $('#tableList').datagrid('beginEdit', rowIndex);
//                        if((value.indexOf(".00"))>0){
//                            value=value.substr(0,value.length-3);
//                        }
                        }
                        lastIndex = rowIndex;
                    }
                })
//                    .datagrid("loadData",data.obj.list);

            });
        });
    </script>

</head>
<body>
<div style="padding:5px;background:#fff;position:absolute;top:0px;left:0px;right:0px;bottom:0px;">
    <table id="tableList" style="width:100%;height:100%"></table>
</div>



</div>
</body>
</html>
