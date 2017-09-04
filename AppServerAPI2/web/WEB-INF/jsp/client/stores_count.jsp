<%--
  用户分析
  User: Administrator
  Date: 12-12-26
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<script src="/static/Highcharts-4.2.5/js/highcharts.js"></script>
<script src="/static/Highcharts-4.2.5/js/modules/exporting.js"></script>
<script src="/static/Highcharts-4.2.5/js/themes/grid.js"></script>

<html>
<head>
    <title>店铺战报</title>
    <style>
        body {
            padding: 0px;
        }

        .htitle {
            background: #fff;
            padding: 0px;
            margin: 20px auto 10px auto;
            width: 98%;
            height: 45px;
        }

        .htitle .title {
            width: 200px;
            float: left;
            margin-left: 20px;
            margin-top: 5px;
        }

        .htitle .selecttime {
            float: right;
            left: 180px;
            margin-right: 20px;
            margin-top: 10px;
        }

        .htitle .dateclass {
            float: right;
            margin-right: 20px;
            margin-top: 10px;
        }

        .food_title {
            width: 96%;
            height: 38px;
            line-height: 38px;
            background: #fff;
            font-size: 20px;
            text-align: center;
            margin: 0 2%;
        }

        .food_title .inset {
            width: 60%;
            margin: 0% 20%;
        }

        .food_title .title_text {
            float: left;
        }

        .food_title .title_text p {
            display: inline-block;
            margin-right: 45px;
        }

        .food_title .title_text a {
            color: #000;
            margin-right: 20px;
        }

        .food_title .title_date {
            float: left;
            border: 1px solid #dddddd;
            margin-top: 4px;
            padding: 0px 10px;
        }

        .food_title .title_date input {
            width: 80px;
            height: 28px;
            position: relative;
            bottom: 3px;
            font-size: 16px;
            border: none;
        }

        .food_title .title_date {
            height: 28px;
        }

        .food_title .title_date .date_text {
            height: 38px;
            line-height: 28px;
        }

        .food_title .title_date .date_text span {
            font-size: 18px;
        }

        .inset_auto {
            width: 98%;
            margin: 0 auto;
        }

        .inset_auto .main .cation a {
            display: inline-block;
            height: 30px;
            line-height: 30px;
            color: #000;
            background: #fff;
            text-align: center;
            padding: 0px 14px;
        }

        .inset_auto .main .cation .nowcation {
            background: #c30d23;
            color: #fff;
            box-shadow: 3px 3px 10px rgba(0, 0, 0, 0.4);
        }

        .inset_auto .main > p {
            height: 27px;
            line-height: 27px;
        }

        .inset_auto .main .foodname {
            margin-top: 18px;
        }

        .inset_auto .main .foodname_div {
            width: 30%;
            height: 90px;
            float: left;
            background: #fff;
            position: relative;
        }

        .inset_auto .main .center {
            margin: 0% 5%;
        }

        .inset_auto .main .foodname_div img {
            position: absolute;
            top: -12px;
            left: -15px;
        }

        .inset_auto .main .foodname_div span {
            z-index: 10;
            color: #fff;
            font-size: 20px;
            font-weight: bolder;
            display: inline-block;
            width: 22px;
            height: 26px;
            text-align: center;
            line-height: 26px;
            position: absolute;
            top: 0px;
            left: 0px;
        }

        .inset_auto .main .foodname_div table {
            margin-left: 50px;
        }

        .inset_auto .main .foodname_div h4 {
            margin-left: 72px;
            line-height: 34px;
        }

        .inset_auto .main .foodname_div td {
            width: 100px;
            text-align: center;
            color: #b4b4b4;
        }

        .inset_auto .main #goodtable {
            padding: 10px 20px;
            background: #fff;
            margin-top: 20px;
        }

        .inset_auto .main .table_list {
            width: 100%;
            border-collapse: collapse;
        }

        .inset_auto .main .table_list .title {
            height: 40px;
            line-height: 40px;
            background: #d7d7d7;
        }

        .inset_auto .main .table_list td {
            text-align: center;
            height: 40px;
            line-height: 40px;
        }

        .inset_auto .main .table_list tr:nth-child(even) td {
            background-color: #efefef;
        }

        /*  .inset_auto .main .table_list td .strip {
              background: #c30d23;
              height: 16px;
              margin-top: 12px;
              max-width: 90%;
              float: left;
          }*/

        .inset_auto .main .table_list td span {
            float: left;
        }

        .inset_auto .main .table_list .bili {
            text-align: left;
        }

        .bodymain .title {
            line-height: 35px;
        }

        .table_list thead tr th {
            border: 1px solid #dddddd;
            height: 35px;
        }

        .table_list tbody tr td {
            border: 1px solid #dddddd;
        }
        .datagrid-row {
            height: 42px;
            text-align:center;
        }

        .datagrid-header-row {
            height: 42px;
            font-weight:700;
        }
        #fm
        {
            margin: 0;
        }
        .ftitle
        {
            font-size: 14px;
            font-weight: bold;
            padding: 5px 0;
            margin-bottom: 10px;
            border-bottom: 1px solid #ccc;
        }
        .fitem
        {

            border-bottom:1px dashed #9f9f9f;
            padding-bottom:15px;
            padding-top:15px;
        }
        .fitem label
        {
            display: inline-block;
            width: 100px;
        }

        .mytitle {
            font-weight:lighter;
        }
    </style>
    <script type="text/javascript">
        $(function () {


            $("#datatype").find("a").on("click", function () {
                $(this).addClass("nowcation").siblings().removeClass("nowcation");
                datatype = $(this).attr("datatype");
                getdata();
                return false;
            });

            $("#downLoad").click(function () {
                var startday = $("#startday").val();
                var endday = $("#endday").val();
                var paramData = {'startday': startday, 'endday': endday};
                $("#downLoad").exprotAjaxExcel("/orderSelectAPI/storesIncomeExcel",paramData);
            });

            $("#create_date").change(function() {
                $.postJSON("/orderSelectAPI/storesIncome", {create_date: $("#create_date").val()}, function (data) {
                    fromInit(data);
                });
            });

            var endday = $("#endday").val();
            var myDate = new Date(endday);
            var startday;
            if(myDate.getMonth()+1<10){
                startday = myDate.getFullYear()+"-0"+(myDate.getMonth()+1)+"-01";
            }else{
                startday = myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-01";
            }
            $("#startday").val(startday);
            getdata();
        });

        function getdata() {
            var d = new Date();
            var monthDay = new Date(d.getFullYear(), (d.getMonth()+1), 0).getDate();
            var toDay = d.getDate();
            var dateNumber = (toDay/monthDay).toFixed(5);
            var endday = $("#endday").val();
            var myDate = new Date(endday);
            var startday;
            if(myDate.getMonth()+1<10){
                startday = myDate.getFullYear()+"-0"+(myDate.getMonth()+1)+"-01";
            }else{
                startday = myDate.getFullYear()+"-"+(myDate.getMonth()+1)+"-01";
            }
            $("#startday").val(startday);
                $('#tableList').datagrid({
                    url: "/orderSelectAPI/stores_orderCount?startday="+startday+"&&endday="+endday,
                    sortName: 'create_date',
                    sortOrder: 'desc',
                    iconCls: "icon-add",
                    fit: true,
                    fitColumns: false,
                    loadMsg: "数据加载中......",
                    pagination: false,
                    rownumbers: true,
                    singleSelect: true,//单行选取
                    <c:if test="${sessionScope.loginUser.stores_id == 0 }">
                    toolbar: [
                        {
                            text: '目标营业额设置',
                            iconCls: 'icon-add',
                            handler: function () { addOrEdit(); }
                        }
                        ],
                    </c:if>
                    frozenColumns:[
                        [
                            {"field":"storesName","title":"门店","width":140,"align":"center"}
                        ]
                    ],
                    columns: [[
                        {"field":"defined1","title":"营业额完成率","width":120,"align":"center","rowspan":2,
                            formatter: function (val,row) {
                                return ((row.income - row.sendPrice)*100/row.monthIncome).toFixed(2)+"%";
                            }
                        },
                        {"field":"defined2","title":"时间进度","width":70,"align":"center","rowspan":2,
                            formatter: function () {
                                return (dateNumber*100).toFixed(3)+"%";
                            }
                        },
                        {"field":"defined3","title":"预计完成度","width":100,"align":"center","rowspan":2,
                            formatter: function (val,row) {
                                return (((row.income - row.sendPrice)*100/row.monthIncome)/dateNumber).toFixed(2)+"%";
                            }
                        },
                        {"field":"defined4","title":"累计营业额缺口","width":120,"align":"center","rowspan":2,
                            formatter: function (val,row) {
                                return ((dateNumber - ((row.goodsPrice - row.sendPrice)/row.monthIncome)) * row.monthIncome).toFixed(2);
                            }
                        },
                        {"title":"基础数据(仅饿了么,美团)","align":"center","colspan":12}],
                            [
                                {"field":"income","title":"累计营业额","width":100,"align":"center","rowspan":1},
                                {"field":"monthIncome","title":"目标营业额","width":100,"align":"center","rowspan":1},
                                {"field":"elmOrderCount","title":"饿了单量","width":80,"align":"center","rowspan":1},
                                {"field":"mtOrderCount","title":"美团单量","width":80,"align":"center","rowspan":1},
                                {"field":"orderCount","title":"总有效订单","width":100,"align":"center","rowspan":1},
                                {"field":"sendPrice","title":"配送费预估","width":100,"align":"center","rowspan":1},
                                {"field":"defined5","title":"客单价","width":100,"align":"center","rowspan":1,
                                    formatter: function (val,row) {
                                        return (row.income/row.orderCount).toFixed(2);
                                    }
                                },
                                {"field":"defined6","title":"饿了么平台抽佣","width":100,"align":"center","rowspan":1},
                                {"field":"defined7","title":"美团平台抽佣","width":100,"align":"center","rowspan":1},
                                {"field":"shopPart","title":"自营销金额","width":100,"align":"center","rowspan":1},
                                {"field":"price","title":"交易额","width":100,"align":"center","rowspan":1},
                                {"field":"defined8","title":"自营销力度","width":100,"align":"center","rowspan":1,
                                    formatter: function (val,row) {
                                        return (row.shopPart*100/row.price).toFixed(3)+"%";
                                    }
                                }
                            ]
                    ]
                })

        }


        function addOrEdit() {
            //$("#fm").form("clear");
            $("#dlg").dialog("open").dialog('setTitle', '目标营业额设置');
            $.postJSON("/orderSelectAPI/storesIncome", {create_date: $("#create_date").val()}, function (data) {
                fromInit(data);
            });
        }

        function fromInit(data){
            if(data){
                $("[id=month_income]").val("");
            }
            for(var i=0;i<data.length;i++){
                $("[id=month_income]:eq("+i+")").val(data[i].month_income);
            }
        }

        function save(){
            var flag = false;
            $("input[id=month_income]").each(function(a,b){
                if(flag)return;
                if(isNaN(parseInt(b.value))){
                    $.messager.alert("提示信息", "数据有误,请检查"+b.value);
                    flag = true;
                }
            });
            if(flag)return;
            $.post("/orderSelectAPI/storesIncomeSave",$("#fm").serializeArray(),function(data){
                if(data==1){
                    $.messager.alert("提示信息", "保存成功!");
                    $('#dlg').dialog('close');
                }
            })
        }
    </script>
</head>
<body>
<div class="bodymain">
    <div class="htitle">
        <div class="title"><h2>店铺战报</h2></div>
        <div class="dateclass">
            <button id="downLoad">下载</button>
            <input id="startday" class="Wdate" type="text" value="${curday}" readonly >
            至 <input id="endday" class="Wdate" type="text"
                     onFocus="WdatePicker({ onpicked:getdata, maxDate: '%y-%M-%d'})"
                     value="${curday}">
        </div>
    </div>

    <div style="padding:5px;background:#fff;position:absolute;top:65px;left:0px;right:0px;bottom:0px;">
        <table id="tableList" style="width:100%;height:100%"></table>
    </div>

    <div id="dlg" class="easyui-dialog" style="width: 550px; height: 470px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">

            <div class="fitem" >
                <label class="mytitle">选择年份</label>
                <input name="year" type="number" value="2017">
            </div>

            <div class="fitem" >
                <label class="mytitle">选择月份</label>
                <select id="create_date" name="create_date" style="width:160px;"
                        data-options="editable:false,panelHeight:'auto'">
                    <script>
                        for(var i=1;i<=12;i++){
                            if(i==new Date().getMonth()+1){
                                if(i<10){
                                    document.write("<option value='0"+i+"' selected>0"+i+"</option>");
                                }else{
                                    document.write("<option value='"+i+"' selected>"+i+"</option>");
                                }
                            }else{
                                if(i<10){
                                    document.write("<option value='0"+i+"'>0"+i+"</option>");
                                }else{
                                    document.write("<option value='"+i+"'>"+i+"</option>");
                                }
                            }
                        }
                    </script>
                </select>
            </div>


                <c:forEach var="items" items="${storesList}">
                    <div class="fitem" >
                    <label class="mytitle">${items.name}</label>
                    <input type="hidden" name="stores_id" value="${items.stores_id}" />
                    <input name="month_income" id="month_income" class="easyui-textbox easyui-numberbox" required="true" value="${items.month_income}"/>
                    </div>
                </c:forEach>

        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="save()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>
</div>
</body>
</html>
