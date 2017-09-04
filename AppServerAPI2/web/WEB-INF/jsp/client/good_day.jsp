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
    <title>菜品统计</title>
    <style>
        body {
            padding: 0px;
        }

        .htitle {
            background: #fff;
            padding: 0px;
            margin: 20px auto 10px auto;
            width: 100%;
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

        .htitle .selectbrand {
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
    </style>

    <script type="text/javascript">
        $(function () {
            $(".dayselect").click(function () {
                var status = $(this).data("status");
                var myDate = new Date('${curday}');
                var endtime = myDate.format("yyyy-MM-dd");
                $("#endday").val(endtime);
                if (status == 0) {
                    $("#startday").val(endtime);
                } else if (status == 1) {
                    var startday = addDate(myDate, 0);
                    $("#startday").val(startday);
                } else if (status == 2) {
                    var startday = addDate(myDate, -6);
                    $("#startday").val(startday);
                } else if (status == 3) {
                    var startday = addDate(myDate, -29);
                    $("#startday").val(startday);
                } else if (status == 4) {
                    var startday = addDate(myDate, -89);
                    $("#startday").val(startday);
                }
                getdata();
            });

            $("#shops").combobox({
                onChange: function (n, o) {
                    getdata();
                }
            });


            $("#downLoad").click(function () {
                var startday = $("#startday").val();
                var endday = $("#endday").val();
                var paramData = {
                    'startday': startday,
                    'endday': endday
                };
                $("#downLoad").exprotAjaxExcel("/GoodAPI/goodReportDaysExcel",paramData);
//                window.open("/GoodAPI/goodReportDaysExcel?startday="+startday+"&&endday="+endday);
            });
            var myDate = new Date('${curday}');
            var startday = addDate(myDate, -6);
            $("#startday").val(startday);
            getdata();
            });
        function getdata() {
            var startday = $("#startday").val();
            var endday = $("#endday").val();
            var paramData = {
                'startday': startday,
                'endday': endday
            };


            $.postJSON("/GoodAPI/goodReportDays", paramData, function (data) {
                $('#tableList').datagrid({
                    iconCls: 'icon-save',
                    fitColumns:false,
                    rownumbers: true,//行号
                    singleSelect: true,//单行选取
                    pagination:false,
                    frozenColumns:[
                        [{"field":"good_name","title":"菜品名","width":180}]
                    ],
                    columns: JSON.parse(data.obj.colums)
                    }).datagrid("loadData",data.obj.list);
            })
        }
    </script>

</head>
<body>
<div class="bodymain">
    <div class="htitle">
        <div class="title"><h2>菜品统计</h2></div>
        <div class="dateclass">
            <button id="downLoad" onclick="">下载</button>
            <input id="startday" class="Wdate" type="text"
                   onFocus="WdatePicker({ onpicked:getdata, maxDate: '#F{$dp.$D(\'endday\')}' })" value="${curday}">
            至 <input id="endday" class="Wdate" type="text"
                     onFocus="WdatePicker({ onpicked:getdata, maxDate: '%y-%M-%d', minDate: '#F{$dp.$D(\'startday\')}' })"
                     value="${curday}">
        </div>
        <div class="selecttime">
            <b>日期：</b>
            <a href="###" data-status="1" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">今天</a>
            <a href="###" data-status="2" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true,selected:true">近7天</a>
            <a href="###" data-status="3" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近30天</a>
            <a href="###" data-status="4" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近90天</a>
        </div>



    </div>
    <div style="padding:5px;background:#fff;position:absolute;top:65px;left:0px;right:0px;bottom:0px;">
        <table id="tableList" style="width:100%;height:100%"></table>
    </div>
</div>
</div>
</body>
</html>
