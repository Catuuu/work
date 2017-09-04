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
    <meta name="viewport" content="width=device-width" />
    <title>营业数据</title>

    <style>
        .htitle{
            background:#fff;padding:0px;margin:10px auto;width:98%; height:45px;
        }
        .htitle .title{
            width:200px;
            float:left;
            margin-left: 20px;
            margin-top: 5px;
        }
        .htitle .selecttime{
            float:right;
            left:180px;
            margin-right:20px;
            margin-top: 10px;
        }
        .htitle .dateclass{
            float:right;
            margin-right:20px;
            margin-top: 10px;
        }

        .hcontent{
            padding:0px;margin:10px auto;width:98%; height:200px;
        }

        .hcontent .item {
            width: 24%;
            margin-right:1%;
            float:left;
            background: #fff none repeat scroll 0 0;
            height: 200px;
        }

        .hcontent .left{
            float: left;
            height: 200px;
            width: 50%;
        }
        .hcontent .right{
            float: left;
            height: 200px;
            width: 50%;
        }
        .hcontent .right .item {
            background: #fff none repeat scroll 0 0;
            width:98%;
        }

        .hcontent .left .item {
            background: #fff none repeat scroll 0 0;
            width:98%;
        }

        .hcontent h4{
            margin:20px;
            color:#a6a6a6;
        }
        .hcontent h1,.hcontent h3{
            text-align:center;
        }
        .hcontent h5{
            text-align:center;
            color:#808080;
        }

    </style>
    <script type="text/javascript">
        $(function () {
            $(".dayselect").click(function () {
                var status = $(this).data("status");
                var myDate = new Date('@ViewData["curday"]');
                var endtime = myDate.format("yyyy-MM-dd");
                $("#endday").val(endtime);
                if (status == 0) {
                    $("#startday").val(endtime);
                }else if(status == 1){
                    var startday = addDate(myDate, -1);
                    $("#startday").val(startday);
                } else if (status == 2) {
                    var startday = addDate(myDate, -7);
                    $("#startday").val(startday);
                } else if (status == 3) {
                    var startday = addDate(myDate, -30);
                    $("#startday").val(startday);
                } else if (status == 4) {
                    var startday = addDate(myDate, -90);
                    $("#startday").val(startday);
                }
                getdata();
            });


            //订单时间分布
            $('#container').highcharts({
                credits: false,
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: '订单时间分布'
                },
                tooltip: {
                    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
                },
                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: 'Browser share',
                    data: [
                        ['夜宵', 6.2],
                        ['下午茶', 8.5],
                        ['晚餐', 26.8],
                        ['中餐', 45.0],
                        ['早餐', 0.7]
                    ]
                }]
            });

            //订单趋势图
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
                    text: '<b>订单趋势图</b>',
                    x: -20 //center
                },
                subtitle: {
                    text: '日期：2016-12-21 至  2017-01-20 ',
                    x: -20
                },
                xAxis: {
                    categories: ["2016-12-21", "2016-12-22", "2016-12-23", "2016-12-24", "2016-12-25", "2016-12-26", "2016-12-27", "2016-12-28", "2016-12-29", "2016-12-30", "2016-12-31", "2017-01-01", "2017-01-02", "2017-01-03", "2017-01-04", "2017-01-05", "2017-01-06", "2017-01-07", "2017-01-08", "2017-01-09", "2017-01-10", "2017-01-11", "2017-01-12", "2017-01-13", "2017-01-14", "2017-01-15", "2017-01-16", "2017-01-17", "2017-01-18", "2017-01-19", "2017-01-20", "\u5408\u8ba1"],
                    showEmpty: 'true'
                },
                yAxis: {
                    title: {
                        text: '订单数'
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    headerFormat: '<span style="font-size:12px;">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                    '<td style="padding:0"><b>{point.y} 单</b></td></tr>',
                    footerFormat: '</table>',
                    //shared: true,
                    useHTML: true
                },

                plotOptions: {
                    line: {
                        dataLabels: {
                            enabled: false
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
                series: [{ "name": "\u5408\u8ba1", "data": [3824, 4003, 3924, 4530, 5089, 3791, 3322, 3130, 3215, 3165, 3141, 3719, 3337, 3913, 3857, 3947, 3862, 3513, 3242, 3307, 3793, 3461, 3944, 3699, 1385, 3743, 3754, 3346, 3242, 2753, 1569] }, { "name": "\u997f\u4e86\u4e48", "data": [2446, 2586, 2436, 3076, 3282, 2082, 1841, 1763, 1726, 1639, 1510, 1630, 1690, 2145, 2140, 2210, 2180, 1969, 1894, 1915, 2076, 1933, 2371, 2263, 867, 2242, 2437, 2163, 2094, 1628, 1000] }, { "name": "\u7f8e\u56e2", "data": [1309, 1355, 1426, 1391, 1730, 1612, 1421, 1295, 1414, 1462, 1611, 1969, 1482, 1621, 1507, 1503, 1504, 1487, 1309, 1363, 1682, 1492, 1541, 1413, 487, 1356, 1296, 1159, 1114, 1104, 561] }, { "name": "\u767e\u5ea6\u5916\u5356", "data": [62, 56, 55, 54, 71, 87, 51, 66, 66, 60, 19, 114, 151, 138, 202, 231, 173, 49, 29, 28, 33, 34, 30, 19, 29, 136, 18, 16, 26, 17, 5] }, { "name": "\u5fae\u4fe1", "data": [7, 6, 7, 9, 6, 10, 9, 6, 9, 4, 1, 6, 14, 9, 8, 3, 5, 8, 10, 1, 2, 2, 2, 4, 2, 9, 3, 8, 8, 4, 3] }]
            });
        });


        function getdata() {
            var startday = $("#startday").val();
            var endday = $("#endday").val();
            console.log(startday);
            console.log(endday);
        }
    </script>


</head>
<body>
<div class="bodymain">
    <div class="htitle" >
        <div class="title"><h2>营业数据 </h2></div>

        <div class="dateclass">
            <input id="startday" class="Wdate" type="text" onClick="WdatePicker({ changing: getdata, maxDate: '#F{$dp.$D(\'endday\')}' })" value="@ViewData["curday"]">
            至 <input id="endday" class="Wdate" type="text" onClick="WdatePicker({ changing: getdata, maxDate: '%y-%M-%d', minDate: '#F{$dp.$D(\'startday\')}' })"  value="@ViewData["curday"]">
        </div>
        <div class="selecttime">
            <a href="###" data-status="0" class="easyui-linkbutton  dayselect" data-options="toggle:true,group:'g3',plain:true,selected:true">今天</a>
            <a href="###" data-status="1" class="easyui-linkbutton  dayselect" data-options="toggle:true,group:'g3',plain:true">昨天</a>
            <a href="###" data-status="2" class="easyui-linkbutton  dayselect" data-options="toggle:true,group:'g3',plain:true">近7天</a>
            <a href="###" data-status="3" class="easyui-linkbutton  dayselect" data-options="toggle:true,group:'g3',plain:true">近30天</a>
            <a href="###" data-status="4" class="easyui-linkbutton  dayselect" data-options="toggle:true,group:'g3',plain:true">近90天</a>
        </div>
    </div>

    <div class="hcontent">
        <div class="item">
            <h4>订单数</h4>
            <h1>800单</h1>
            <h5>饿了么：245</h5>
            <h5>美团：1</h5>
            <h5>百度：2</h5>
            <h5>微信：2</h5>
        </div>
        <div class="item">
            <h4>营业额</h4>
            <h1>¥58.00</h1>
            <h5>饿了么：34.00</h5>
            <h5>美团：34.00</h5>
            <h5>百度：34.00</h5>
            <h5>微信：2.00</h5>
        </div>

        <div class="item">
            <h4>客单价</h4>
            <h1>¥48.00</h1>
            <h5>饿了么：34.00</h5>
            <h5>美团：34.00</h5>
            <h5>百度：34.00</h5>
            <h5>微信：56.00</h5>
        </div>

        <div class="item">
            <h4>净收入</h4>
            <h1>¥58.00</h1>
            <h5>饿了么：34.00</h5>
            <h5>美团：34.00</h5>
            <h5>百度：34.00</h5>
            <h5>微信：2.00</h5>
        </div>

    </div>



    <div class="hcontent">
        <div class="left">
            <div class="item">
                <div style="width:100%;">
                    <div style="width:32%;float:left;">
                        <h4>无效订单数</h4>
                        <div style="border-right:2px solid #eeeeee;height:130px;">
                            <h3>800单</h3>
                            <h5>饿了么：245</h5>
                            <h5>美团：1</h5>
                            <h5>百度：2</h5>
                            <h5>微信：2</h5>
                        </div>
                    </div>
                    <div style="width:32%;float:left;">
                        <h4>预计损失</h4>
                        <div style="border-right:2px solid #eeeeee;height:130px;">
                            <h3>¥58.00</h3>
                            <h5>饿了么：34.00</h5>
                            <h5>美团：34.00</h5>
                            <h5>百度：34.00</h5>
                            <h5>微信：2.00</h5>
                        </div>
                    </div>
                    <div style="width:32%;float:left;">
                        <h4>无效订单理由</h4>
                        <div style="height:130px;">
                            <div style="background:#dfdfdf;border-radius:2px;margin:2px;padding:2px 5px;color:#999;float:left;font-size:12px;">用户退单(16)</div>
                            <div style="background:#dfdfdf;border-radius:2px;margin:2px;padding:2px 5px;color:#999;float:left;font-size:12px;">用户无理由退单(1)</div>
                            <div style="background:#dfdfdf;border-radius:2px;margin:2px;padding:2px 5px;color:#999;float:left;font-size:12px;">由于配送过程问题,用户退单(1)</div>
                            <div style="background:#dfdfdf;border-radius:2px;margin:2px;padding:2px 5px;color:#999;float:left;font-size:12px;">其它(1)</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="right">
            <div class="item">
                <div id="container" style="min-width:200px;height:200px"></div>
            </div>
        </div>
    </div>

    <div id="container_line" style="width:98%;height:500px;margin:20px auto;"></div>
</div>
</body>
</html>
