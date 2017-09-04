
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


        $(function () {

        });




    </script>
</head>
<body>

<table id="tt" title="TreeGrid" class="easyui-treegrid" style="width:700px;height:300px"
       url="treegrid_data3.json" idField="id" treeField="code"
       pagination="true" fitColumns="true">
    <thead>
    <tr>
        <th field="code" rowspan="2" width="150" editor="text">Code</th>
        <th colspan="2">Group Fields</th>
    </tr>
    <tr>
        <th field="name" width="200" editor="text">Name</th>
        <th field="addr" width="200" editor="text">Addr</th>
    </tr>
    </thead>
</table>

</body>
</html>
