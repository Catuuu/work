<%--
  菜品分析
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
    <title>菜品分析</title>
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

        .inset_auto .main #tableList {
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

        .inset_auto .main .table_list tr:nth-child(even) td { background-color: #efefef; }

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
        .datagrid-header,
        .datagrid-td-rownumber {
            background-color: #efefef;
            background: -webkit-linear-gradient(top,#F9F9F9 0,#efefef 100%);
            background: -moz-linear-gradient(top,#F9F9F9 0,#efefef 100%);
            background: -o-linear-gradient(top,#F9F9F9 0,#efefef 100%);
            background: linear-gradient(to bottom,#F9F9F9 0,#efefef 100%);
            background-repeat: repeat-x;
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#F9F9F9,endColorstr=#efefef,GradientType=0);
            font-weight: 700;
        }
    </style>
    <script type="text/javascript">
        var date = new Date();
        var ordertype = 1;
        var hours = date.getHours();
        var minutes = date.getMinutes();
        $(function () {
            //排序分类
            $(".cation").find("a").on("click", function () {
                $(this).addClass("nowcation").siblings().removeClass("nowcation");
                ordertype = $(this).attr("ordertype");
                if(ordertype==1){
                    $("#tableDiv").show();
                    $("#reportline").hide();
                    $("#reportcolumn").hide();
                }else if(ordertype==2){
                    $("#reportline").show();
                    $("#tableDiv").hide();
                    $("#reportcolumn").hide();
                }else if(ordertype==3){
                    $("#reportcolumn").show();
                    $("#tableDiv").hide();
                    $("#reportline").hide();
                }

                return false;
            });

            $("#findBtn").click(function(){
              getdata();
            });



            $(".dayselect").click(function () {
                var mydate = new Date();
                var today  = mydate.getFullYear()+"-"+(mydate.getMonth()+1)+"-"+mydate.getDate();
                today = addDate(today,0);
                var status = $(this).data("status");
                var endtime = today;
                $("#endday").val(endtime);
                if (status == 0) {
                    $("#startday").val(today);
                    $("#endday").val(today);
                } else if (status == 1) {
                    var startday = addDate(today, -1);
                    $("#startday").val(startday);
                    $("#endday").val(startday);
                } else if (status == 2) {
                    var startday = addDate(today, -7);
                    $("#startday").val(startday);
                    var endday = addDate(today, -1);
                    $("#endday").val(endday);
                } else if (status == 3) {
                    var startday = addDate(today, -30);
                    $("#startday").val(startday);
                    var endday = addDate(today, -1);
                    $("#endday").val(endday);
                } else if (status == 4) {
                    var startday = addDate(today, -90);
                    $("#startday").val(startday);
                    var endday = addDate(today, -1);
                    $("#endday").val(endday);
                }
                getdata()
            });
            getdata();
        })

        function getdata() {
            if(ordertype==1){
                $("#tableDiv").show();
                $("#reportline").hide();
                $("#reportcolumn").hide();
            }else if(ordertype==2){
                $("#reportline").show();
                $("#tableDiv").hide();
                $("#reportcolumn").hide();
            }else if(ordertype==3){
                $("#reportcolumn").show();
                $("#tableDiv").hide();
                $("#reportline").hide();
            }

            var startTime = parseInt($("#sHour").val()*60) + parseInt($("#sMinute").val());
            var endTime = parseInt($("#eHour").val()*60) + parseInt($("#eMinute").val());
            if(startTime>=endTime){
                $.messager.alert("提示信息", "截止时间不得大于或等于开始时间");
                return;
            }
            var splitTime = $("#splitTime").val();
            var startday = $("#startday").val();
            var endday = $("#endday").val();
            var days = getDay(startday,endday);
            var paramData = {'startday': startday, 'endday': endday, 'ordertype': ordertype,'startTime':startTime,'endTime':endTime,'splitTime':splitTime,'days':days};


            $.postJSON("/GoodAPI/getReportAnalyse", paramData, function (data) {
                $('#tableList').datagrid({
                    iconCls: 'icon-save',
                    fitColumns:false,
                    rownumbers: true,//行号
                    singleSelect: true,//单行选取
                    pagination:false,
                    frozenColumns:[
                        [{"field":"good_name","title":"菜品名","width":180,"align":"center"}]
                    ],
                    columns: JSON.parse(data.obj.time_names)
                }).datagrid("loadData",data.obj.list);

                $('#container_line').highcharts({
                    credits:false,
                    lang: {
                        downloadPNG:"生成PNG文件",
                        downloadJPEG:"生成JPEG文件",
                        downloadPDF:"生成PDF文件",
                        downloadSVG:"生成SVG文件",
                        printButtonTitle:"打印报表"
                    },
                    title: {
                        text: '<b>菜品时间段销售统计</b>',
                        x: -20 //center
                    },
                    subtitle: {
                        text: '日期：'+$("#startday").val()+'  至   '+$("#endday").val(),
                        x: -20
                    },
                    xAxis: {
                        categories: data.obj.titleList
                    },
                    yAxis: {
                        title: {
                            text: '销售份数 (份)'
                        },
                        plotLines: [{
                            value: 0,
                            width: 1,
                            color: '#808080'
                        }]
                    },
                    tooltip: {
                        valueSuffix: '份'
                    },
                    plotOptions: {
                        line: {
                            dataLabels: {
                                enabled: true
                            },
                            enableMouseTracking: true
                        }
                    },
                    legend: {
                        layout: 'horizontal',
                        align: 'center',
                        verticalAlign: 'bottom',
                        borderWidth: 0
                    },
                    series: data.obj.goods
                });


                $('#container_column').highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: '<b>菜品时间段销售统计</b>'
                    },
                    subtitle: {
                        text: '日期：'+$("#startday").val()+'  至   '+$("#endday").val(),
                    },
                    xAxis: {
                        categories: data.obj.titleList
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '销售份数 (份)'
                        }
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:12px;">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y} 份</b></td></tr>',
                        footerFormat: '</table>',
                        shared: true,
                        useHTML: true
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0
                        }
                    },
                    series: data.obj.goods
                });
            })
        }

        function getDay(sDate,eDate){
            var sArr = sDate.split("-");
            var eArr = eDate.split("-");
            var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
            var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
            var result = (eRDate-sRDate)/(24*60*60*1000);
            result++;
            return result;
        }

    </script>
</head>
<body>
<div class="bodymain">
    <div class="htitle">
        <div class="title"><h2>菜品分析</h2></div>


        <div class="dateclass">
            <input id="startday" class="Wdate" type="text"
                   onFocus="WdatePicker({ onpicked:getdata, maxDate: '#F{$dp.$D(\'endday\')}' })" value="${curday}">
            至 <input id="endday" class="Wdate" type="text"
                     onFocus="WdatePicker({ onpicked:getdata, maxDate: '%y-%M-%d', minDate: '#F{$dp.$D(\'startday\')}' })"
                     value="${curday}">
        </div>

        <div class="selecttime">
            <a href="###" data-status="0" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">今天</a>
            <a href="###" data-status="1" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true,selected:true">昨天</a>
            <a href="###" data-status="2" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近7天</a>
            <a href="###" data-status="3" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近30天</a>
            <a href="###" data-status="4" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近90天</a>
        </div>

    </div>

    <div class="inset_auto">
        <div class="main">
            <div class="cation">
                <a href="" class="nowcation" ordertype="1" onclick="getdata()">表格</a>
                <a href="" ordertype="2">曲线图</a>
                <a href="" ordertype="3">柱状图</a>
                <strong style="margin-left: 50%;"></strong>起始时间:<select  name="sHour" id="sHour"  style="width:38px;">
                <script>
                    for(var i=0;i<=23;i++){
                        if(i==hours){
                            document.write("<option value='"+i+"' selected>"+i+"</option>");
                        }else{
                            document.write("<option value='"+i+"'>"+i+"</option>");
                        }
                    }
                </script>
            </select>
                时
                <select  name="sMinute" id="sMinute"  style="width:38px;">
                    <script>
                        for(var i=0;i<=59;i++){
                            if(i==minutes){
                                document.write("<option value='"+i+"' selected>"+i+"</option>");
                            }else{
                                document.write("<option value='"+i+"'>"+i+"</option>");
                            }
                        }
                    </script>
                </select>
                分&nbsp;&nbsp;&nbsp;&nbsp;
                截止时间:<select  name="eHour" id="eHour"  style="width:38px;">
                <script>
                    for(var i=0;i<=23;i++){
                        if(hours<=19){
                            if(i==hours+4){
                                document.write("<option value='" + i + "' selected>" + i + "</option>");
                            }else{
                                document.write("<option value='" + i + "' >" + i + "</option>");
                            }
                        } else if(i==hours){
                            document.write("<option value='" + i + "' selected>" + i + "</option>");
                        }else{
                            document.write("<option value='" + i + "' >" + i + "</option>");
                        }
                    }
                </script>
            </select>
                时
                <select  name="eMinute" id="eMinute"  style="width:38px;">
                    <script>
                        for(var i=0;i<=59;i++){
                            if(i==minutes){
                                document.write("<option value='"+i+"' selected>"+i+"</option>");
                            }else{
                                document.write("<option value='"+i+"'>"+i+"</option>");
                            }
                        }
                    </script>
                </select>
                分&nbsp;&nbsp;&nbsp;
                间隔时间:
                <select  name="splitTime" id="splitTime" style="width:38px;" data-options="panelHeight:'auto'">
                    <option value='20'>20</option>
                    <option value='30' selected>30</option>
                    <option value='40'>40</option>
                    <option value='50'>50</option>
                    <option value='60'>60</option>
                </select>
                分
                <button id="findBtn" class="easyui-linkbutton" iconCls="icon-search" enterkey="13">查询</button>
            </div>


            <div id="tableDiv" style="padding:10px;background:#fff;position:absolute;top:114px;left:14px;right:0px;bottom:0px;">
                <table id="tableList" style="width:100%;height:100%"></table>
            </div>
            <div id="reportline" class="tab-pane fade" style="display: none;margin-top: 20px;">
                <p>
                <div id="container_line" style="width:98%;height:600px"></div>
                </p>
            </div>
            <div id="reportcolumn" class="tab-pane fade" style="display: none;margin-top: 20px;">
                <p>
                <div id="container_column" style="width:98%;height:600px"></div>
                </p>
            </div>
        </div>
    </div>
</div>
</body>
</html>
