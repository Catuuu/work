<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../inc/tld.jsp" %>
<%@ include file="../inc/ui.jsp" %>
<html>
<head>
    <title>欢迎界面</title>
    <link rel="stylesheet" type="text/css" href="ui/easyui-1.3/portal.css">
    <script type="text/javascript" src="ui/easyui-1.3/jquery.portal.js"></script>
    <script type="text/javascript">
        $(function(){
            $('#pp').portal({
                border:false,
                fit:true
            });
        });

        //通报批评
        function opt3(){
            $('body').openWin({
                url:'eventNoticeMorePage.desk?notice_reason=2',
                title:'通报批评',
                width:950,
                height:530
            });
        }
        function formatOpt3(value, rowData){
            var editStr = "<a href=\"javascript:opt3()\">详情></a>";
            return editStr
        }

        //通报表扬
        function opt4(){
            $('body').openWin({
                url:'eventNoticeMorePage.desk?notice_reason=1',
                title:'通报表扬',
                width:950,
                height:530
            });
        }
        function formatOpt4(value, rowData){
            var editStr = "<a href=\"javascript:opt4()\">详情></a>";
            return editStr
        }
       //狱情处理
       function opt1(){
            $('body').openWin({
                url:'commitIndex.evfunc?access=dispose',
                title:'狱情处理',
                width:950,
                height:530
            });
        }
        function formatOpt1(value, rowData){
            var editStr = "<a href=\"javascript:opt1()\">详情></a>";
            return editStr
        }

           //狱情阅览
       function opt2(){
            $('body').openWin({
                url:'commitIndex.evfunc?access=read',
                title:'狱情阅览',
                width:950,
                height:530
            });
        }
        function formatOpt2(value, rowData){
            var editStr = "<a href=\"javascript:opt2()\">详情></a>";
            return editStr
        }
            //未处理流程
       function opt5(){
            $('body').openWin({
                url:'flowIndexPage.flow',
                title:'流程管理',
                width:950,
                height:530
            });
        }
        function formatOpt5(value, rowData){
            var editStr = "<a href=\"javascript:opt5()\">详情></a>";
            return editStr
        }

         function opt6(){
            $('body').openWin({
                url:'eventExamMorePage.desk',
                title:'考核通知',
                width:950,
                height:530
            });
        }
        function formatOpt6(value, rowData){
            var editStr = "<a href=\"javascript:opt6()\">详情></a>";
            return editStr
        }
    </script>
</head>
<body class="easyui-layout">
    <div id="pp" region="center"  style="position:relative">
        <div style="width:49%;">
            <div title="狱情处理" style="height:150px;padding:2px;">
                <table id="tb1" class="easyui-datagrid" url="commitDisposePage.desk"
                       rownumbers="false" pagination="false" showHeader="false">
                    <thead>
                    <tr>
                        <th field="msg" width="430">描述</th>
                        <th field="opt1" formatter="formatOpt1" width="50"></th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div title="通报批评" style="height:150px;padding:2px;">
                <table id="tb3" class="easyui-datagrid" url="eventNoticePage.desk?notice_reason=2"
                       rownumbers="false" pagination="false" showHeader="false">
                    <thead>
                    <tr>
                        <th field="msg" width="430">描述</th>
                        <th field="opt3" formatter="formatOpt3" width="50"></th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div title="流程审核" style="height:150px;padding:2px;">
                  <table id="tb5" class="easyui-datagrid" url="before_flowIndexPage.desk"
                       rownumbers="false" pagination="false" showHeader="false">
                    <thead>
                    <tr>
                        <th field="msg" width="430">描述</th>
                        <th field="opt5" formatter="formatOpt5" width="50"></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
        <div style="width:49%;">
            <div title="狱情阅览" style="height:150px;padding:2px;">
                <table id="tb2" class="easyui-datagrid" url="commitReadPage.desk"
                       rownumbers="false" pagination="false" showHeader="false">
                    <thead>
                    <tr>
                        <th field="msg" width="430">描述</th>
                        <th field="opt2" formatter="formatOpt2" width="50"></th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div title="通报表扬" style="height:150px;padding:2px;">
                <table id="tb4" class="easyui-datagrid" url="eventNoticePage.desk?notice_reason=1"
                       rownumbers="false" pagination="false" showHeader="false">
                    <thead>
                    <tr>
                        <th field="msg" width="430">描述</th>
                        <th field="opt4" formatter="formatOpt4" width="50"></th>
                    </tr>
                    </thead>
                </table>
            </div>
            <div title="考核通知" style="height:150px;padding:2px;">
                <table id="tb6" class="easyui-datagrid" url="eventExamPage.desk"
                       rownumbers="false" pagination="false" showHeader="false">
                    <thead>
                    <tr>
                        <th field="msg" width="430">描述</th>
                        <th field="opt6" formatter="formatOpt6" width="50"></th>
                    </tr>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</body>
</html>