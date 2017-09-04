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
    <title>用户分析</title>
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
        var ordertype = 1;
        var datatype = 1;
        var brand_id = 1; //默认品牌id

        var result_data = '';
        $(function () {
            $("#brand_id").val(brand_id);
//
//            $(".brandselect").click(function () {
//                $(".brandselect").each(function () {
//                    $(this).linkbutton({
//                        iconCls: ''
//                    });
//                });
//                $(this).linkbutton({
//                    iconCls: 'icon-cur'
//                });
//                brand_id = $(this).data("status");
//                getdata();
//            });

            //品牌选择
            $(".brandselect[data-status=" + brand_id + "]").linkbutton({
                selected: true
            });


            $(".brandselect").click(function () {
//                $(this).addClass("brandselect").siblings().removeClass("brandselect");
                brand_id = $(this).data("status");
                $("#brand_id").val(brand_id);
                getdata();
            });


            //报表类型
            $("#ordertype").find("a").on("click", function () {
                $(this).addClass("nowcation").siblings().removeClass("nowcation");
                ordertype = $(this).attr("ordertype");
                if (ordertype == 1) {
                    $("#goodtable").show();
                    $("#reportline").hide();
                    $("#reportcolumn").hide();
                    $("#datatype").hide();
                } else if (ordertype == 2) {
                    $("#reportline").show();
                    $("#goodtable").hide();
                    $("#reportcolumn").hide();
                    $("#datatype").show();
                } else if (ordertype == 3) {
                    $("#reportcolumn").show();
                    $("#goodtable").hide();
                    $("#reportline").hide();
                    $("#datatype").show();
                }

                return false;
            });

            $("#datatype").find("a").on("click", function () {
                $(this).addClass("nowcation").siblings().removeClass("nowcation");
                datatype = $(this).attr("datatype");
                getdata();
                return false;
            });


            $(".dayselect").click(function () {
                var status = $(this).data("status");
                var myDate = new Date('${curday}');
                var endtime = myDate.format("yyyy-MM-dd");
                $("#endday").val(endtime);
                if (status == 0) {
                    $("#startday").val(endtime);
                } else if (status == 1) {
                    var startday = addDate(myDate, -0);
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
                getdata()
            });
            var myDate = new Date('${curday}');
            var startday = addDate(myDate, -6);
            $("#startday").val(startday);
            getdata();
        })

        function getdata() {
            if (ordertype == 1) {
                $("#goodtable").show();
                $("#reportline").hide();
                $("#reportcolumn").hide();
            } else if (ordertype == 2) {
                $("#reportline").show();
                $("#goodtable").hide();
                $("#reportcolumn").hide();
            } else if (ordertype == 3) {
                $("#reportcolumn").show();
                $("#goodtable").hide();
                $("#reportline").hide();
            }

            var startday = $("#startday").val();
            var endday = $("#endday").val();
            var brand_id = $("#brand_id").val();
            var paramData = {
                'startday': startday,
                'endday': endday,
                'ordertype': ordertype,
                'datatype': datatype,
                'brand_id': brand_id
            };
            $.postJSON("/ReportAPI/report1", paramData, function (data) {
                result_data = data;
                $("#goodList").empty();
                for (let i in data.obj.list) {
                    var topindex = parseInt(i) + 1;

                    var goodtd = '<tr class="table_content">' +
                        '<td>' + topindex + '</td>' +
                        '<td>' + data.obj.list[i].report_time + '</td>' +
                        '<td>' + data.obj.list[i].member_count5 + '</td>' +
                        '<td>' + data.obj.list[i].member_count1 + '</td>' +
                        '<td>' + data.obj.list[i].member_count2 + '</td>' +
                        '<td>' + data.obj.list[i].member_count3 + '</td>' +
                        '<td>' + data.obj.list[i].member_count4 + '</td>' +

                        '<td>' + data.obj.list[i].eleme_member_count5 + '</td>' +
                        '<td>' + data.obj.list[i].eleme_member_count1 + '</td>' +
                        '<td>' + data.obj.list[i].eleme_member_count2 + '</td>' +
                        '<td>' + data.obj.list[i].eleme_member_count3 + '</td>' +
                        '<td>' + data.obj.list[i].eleme_member_count4 + '</td>' +

                        '<td>' + data.obj.list[i].meituan_member_count5 + '</td>' +
                        '<td>' + data.obj.list[i].meituan_member_count1 + '</td>' +
                        '<td>' + data.obj.list[i].meituan_member_count2 + '</td>' +
                        '<td>' + data.obj.list[i].meituan_member_count3 + '</td>' +
                        '<td>' + data.obj.list[i].meituan_member_count4 + '</td>' +

                        '<td>' + data.obj.list[i].baidu_member_count5 + '</td>' +
                        '<td>' + data.obj.list[i].baidu_member_count1 + '</td>' +
                        '<td>' + data.obj.list[i].baidu_member_count2 + '</td>' +
                        '<td>' + data.obj.list[i].baidu_member_count3 + '</td>' +
                        '<td>' + data.obj.list[i].baidu_member_count4 + '</td>' +

                        '<td>' + data.obj.list[i].weixin_member_count5 + '</td>' +
                        '<td>' + data.obj.list[i].weixin_member_count1 + '</td>' +
                        '<td>' + data.obj.list[i].weixin_member_count2 + '</td>' +
                        '<td>' + data.obj.list[i].weixin_member_count3 + '</td>' +
                        '<td>' + data.obj.list[i].weixin_member_count4 + '</td>' +
                        '</tr>';
                    $("#goodList").append(goodtd);
                }


                $('#container_line').highcharts({
                    credits: false,
                    lang: {
                        downloadPNG: "生成PNG文件",
                        downloadJPEG: "生成JPEG文件",
                        downloadPDF: "生成PDF文件",
                        downloadSVG: "生成SVG文件",
                        printButtonTitle: "打印报表"
                    },
                    title: {
                        text: '<b>用户分析-' + data.obj.reporttitle + '</b>',
                        x: -20 //center
                    },
                    subtitle: {
                        text: '日期：' + $("#startday").val() + '  至   ' + $("#endday").val(),
                        x: -20
                    },
                    xAxis: {
                        categories: data.obj.time_names
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
                        text: '<b>用户分析-' + data.obj.reporttitle + '</b>'
                    },
                    subtitle: {
                        text: '日期：' + $("#startday").val() + '  至   ' + $("#endday").val(),
                    },
                    xAxis: {
                        categories: data.obj.time_names
                    },
                    yAxis: {
                        min: 0,
                        title: {
                            text: '用户数'
                        }
                    },
                    tooltip: {
                        headerFormat: '<span style="font-size:12px;">{point.key}</span><table>',
                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                        '<td style="padding:0"><b>{point.y} </b></td></tr>',
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
            return result_data;

        }

    </script>
</head>
<body>
<div class="bodymain">
    <div class="htitle">
        <div class="title"><h2>用户分析</h2></div>
        <input type="hidden" id="brand_id" value="">
        <div class="dateclass">
            <input id="startday" class="Wdate" type="text"
                   onFocus="WdatePicker({ onpicked:getdata, maxDate: '#F{$dp.$D(\'endday\')}' })" value="${curday}">
            至 <input id="endday" class="Wdate" type="text"
                     onFocus="WdatePicker({ onpicked:getdata, maxDate: '%y-%M-%d', minDate: '#F{$dp.$D(\'startday\')}' })"
                     value="${curday}">
        </div>
        <div class="selecttime">
            <b>日期：</b>
            <a href="###" data-status="1" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">昨天</a>
            <a href="###" data-status="2" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true,selected:true">近7天</a>
            <a href="###" data-status="3" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近30天</a>
            <a href="###" data-status="4" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近90天</a>
        </div>

        <div class="selectbrand">
            <b>品牌：</b>
            <c:forEach items="${cdsbrands}" var="item">
                <a href="###" data-status="${item.brand_id}" class="easyui-linkbutton  brandselect"
                   data-options="toggle:true,group:'g4',plain:true">${item.brand_name}</a>
            </c:forEach>
        </div>


    </div>

    <div class="inset_auto">
        <div class="main">
            <div class="cation" id="ordertype">
                <a href="" class="nowcation" ordertype="1">表格</a>
                <a href="" ordertype="2">曲线图</a>
                <a href="" ordertype="3">柱状图</a>
            </div>
            <div class="cation" style="float: right;display: none;" id="datatype">
                <a href="" class="nowcation" datatype="1">新用户</a>
                <a href="" datatype="2">活跃用户</a>
                <a href="" datatype="3">沉默用户</a>
                <a href="" datatype="4">流失用户</a>
                <a href="" datatype="5">新增用户</a>
            </div>

            <div id="goodtable" style="margin-bottom: 50px;display: none;">
                <table class="table_list">
                    <thead>
                    <tr>
                        <th rowspan="2">序号</th>
                        <th rowspan="2" valign="center">日期</th>
                        <th colspan="5">合计</th>
                        <th colspan="5">饿了么</th>
                        <th colspan="5">美团</th>
                        <th colspan="5">百度外卖</th>
                        <th colspan="5">微信</th>

                    </tr>
                    <tr>
                        <th>当天新增</th>
                        <th>新用户</th>
                        <th>活跃用户</th>
                        <th>沉默用户</th>
                        <th>流失用户</th>
                        <th>当天新增</th>
                        <th>新用户</th>
                        <th>活跃用户</th>
                        <th>沉默用户</th>
                        <th>流失用户</th>
                        <th>当天新增</th>
                        <th>新用户</th>
                        <th>活跃用户</th>
                        <th>沉默用户</th>
                        <th>流失用户</th>
                        <th>当天新增</th>
                        <th>新用户</th>
                        <th>活跃用户</th>
                        <th>沉默用户</th>
                        <th>流失用户</th>
                        <th>当天新增</th>
                        <th>新用户</th>
                        <th>活跃用户</th>
                        <th>沉默用户</th>
                        <th>流失用户</th>
                    </tr>
                    </thead>
                    <tbody id="goodList">
                    </tbody>
                </table>
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
