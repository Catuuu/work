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
    <title>骑手统计</title>
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
        .table_list thead tr th{
            border: 1px solid #dddddd;
            height: 35px;
        }
        .table_list tbody tr td{
            border: 1px solid #dddddd;
        }
    </style>
    <script type="text/javascript">
        var sendType = 1;
        var orderStatus = 1;
        var taskOrderName;
        $(function () {
            $('#dd').window('close');


            $("#downLoad").click(function () {
                var startday = $("#startday").val();
                var endday = $("#endday").val();
                var paramData = {'startday': startday, 'endday': endday,'orderStatus': orderStatus};
                $("#downLoad").exprotAjaxExcel("/orderSelectAPI/orderRiderCountExcel",paramData);
            });


            $(".dayselect").click(function () {
                var status = $(this).data("status");
                var myDate = new Date('${curday}');
                var endtime = myDate.format("yyyy-MM-dd");
                $("#endday").val(endtime);
                if (status == 0) {
                    $("#startday").val(endtime);
                } else if (status == 1) {
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
            var myDate = new Date('${curday}');
            var startday = addDate(myDate, -6);
            $("#startday").val(startday);
            getdata();
        })

        function setOrderType(status){
            orderStatus = status;
            getdata();
        }


        function getSelectDate(){
            var startday;
            var endday;
            if($("#startday1").val()){
                startday = $("#startday1").val();
                endday = $("#endday1").val();
            }else{
                startday = $("#startday").val();
                endday = $("#endday").val();
            }
            $("#selectTable").datagrid({
                url: "/orderSelectAPI/orderRiderSelect?taskOrderName="+taskOrderName+"&&sendType="+sendType+"&&orderStatus="+orderStatus+"&&startday="+startday+"&&endday="+endday,
                sortName: 'create_date',
                sortOrder: 'desc',
                iconCls: "icon-add",
                fit: true,
                fitColumns: false,
                loadMsg: "数据加载中......",
                pagination: true,
                rownumbers: true,
                singleSelect: true,//单行选取
                columns: [[
                    {field:'orderSn',title:'订单编号',width: '16%', align: 'center',
                        formatter: function (val,row) {
                            return "流水号:"+row.orderNo+"</br>编号:"+row.orderId+"</br>下单日期:"+(row.createDate).split(" ")[0];
                        }
                    },
                    {field:'sendMsg4',title:'用时信息',width: '20%', align: 'center',
                        formatter: function (val,row) {
                            if(!row.taskTime)return;
                            if(orderStatus==1){
                                if(row.sendTime>=60)
                                    return "下单时间:"+(row.createDate).split(" ")[1]+"</br>送达时间:"+(row.taskTime).split(" ")[1]+"</br><strong style='color: red'>本次用时:"+row.sendTime+"</strong>";
                                return "下单时间:"+(row.createDate).split(" ")[1]+"</br>送达时间:"+(row.taskTime).split(" ")[1]+"</br><strong style='color: #00ee00'>本次用时:"+row.sendTime+"</strong>";
                            }else{
                                if(row.sendTime>10||row.sendTime<-10)
                                    return "下单时间:"+(row.createDate).split(" ")[1]+"</br>要求送达时间:"+(row.serviceTime).split(" ")[1]+"</br>实际送达时间:"+(row.taskTime).split(" ")[1]+"</br><strong style='color: red'>时差:"+row.sendTime+"</strong>";
                                return "下单时间:"+(row.createDate).split(" ")[1]+"</br>要求送达时间:"+(row.serviceTime).split(" ")[1]+"</br>实际送达时间:"+(row.taskTime).split(" ")[1]+"</br><strong style='color: #00ee00'>时差:"+row.sendTime+"</strong>";
                            }
                        }
                    },
                    {field:'pack',title:'打包信息',width: '8%', align: 'center',
                        formatter: function (val,row) {
                            if(!row.packUserName)return;
                            return "打包员:"+row.packUserName+"</br>打包时间:"+row.packUserTime;
                        }
                    },
                    {field:'sendMsg1',title:'配送用时',width: '8%', align: 'center',
                        formatter: function (val,row) {
                            if(!row.takeSendTime)return;
                            return "配送用时:"+row.takeSendTime;
                        }
                    },
                    {field:'orderIn',title:'订单来源',width: '8%', align: 'center',
                        formatter: function (val,row) {
                            var brandName = row.brandId==1?"菜大师":"帅小锅";
                            return "品牌:"+brandName+"</br>来源:"+row.formin;
                        }
                    },
                    {field:'name',title:'收货人',width: '14%', align: 'center',
                        formatter: function (val,row) {
                            if(!row.receiverName)return;
                            return "收货人姓名:"+row.receiverName+"</br>收货人地址:"+row.receiverAdress;
                        }
                    },
                    {field:'sendMsg2',title:'接单信息',width: '12%', align: 'center',
                        formatter: function (val,row) {
                            if(!row.taskOrderName)return;
                            return "接单员:"+row.taskOrderName+"</br>tel:"+row.taskOrderPhone+"</br>接单时间:"+row.taskOrderTime;
                        }
                    },
                    {field:'sendMsg3',title:'配送信息',width: '12%', align: 'center',
                        formatter: function (val,row) {
                            if(!row.taskUserName)return;
                            return "配送员:"+row.taskUserName+"</br>tel:"+row.taskUserPhone+"</br>取餐时间:"+row.taskUserTime;
                        }
                    }
                ]]
            })
            sendType=1;
        }


        function getdata() {
            var startday = $("#startday").val();
            var endday = $("#endday").val();
            var paramData = {'startday': startday, 'endday': endday, 'orderStatus':orderStatus};
            $.postJSON("/orderSelectAPI/orderRiderCount", paramData, function (data) {
                $('#tableList').datagrid({
                    iconCls: 'icon-save',
                    fitColumns:false,
                    rownumbers: true,//行号
                    singleSelect: true,//单行选取
                    pagination:false,
                    frozenColumns:[
                        [
                            {"field":"storesName","title":"店铺名","width":100,align:"center"},
                            {"field":"taskOrderName","title":"骑手姓名","width":60,align:"center"},
                            {"field":"taskOrderPhone","title":"手机号","width":80,align:"center"}
                        ]
                    ],
                    columns: JSON.parse(data.obj.titleStr),
                    onDblClickCell :function(index){
                        var rows =  $("#tableList").datagrid("getData").rows;
                        taskOrderName = rows[index].taskOrderName;
                        if(rows[index].createDate=="合计")return;
                        var startday = $("#startday").val();
                        $('#dd').window('open');
                        $('#dd').dialog({
                            iconCls:'icon-save',
                            title:taskOrderName,
                            resizable:true,
                            maximizable:true,
                            collapsible:true,
                            minimizable:true,
                            modal:true,
                            toolbar:[{
                                text:'筛选订单:<select id="cc" class="easyui-combobox" name="typeId" style="width:100px;" data-options="required:true"><option value="1">全部</option><option  value="2">超时</option><option  value="3">准时</option></select>',
                            }
                                ,{text:"<div class=\"dateclass\"><input id=\"startday1\" class=\"Wdate\" type=\"text\" onFocus=\"WdatePicker({ onpicked:getSelectDate, maxDate: '#F{$dp.$D(\\'endday\\')}' })\" value=\""+startday+"\"> 至 <input id=\"endday1\" class=\"Wdate\" type=\"text\" onFocus=\"WdatePicker({ onpicked:getSelectDate, maxDate: '%y-%M-%d', minDate: '#F{$dp.$D(\\'startday\\')}' }) \" value=\"${curday}\"></div>"}]
                        });
                        $("#cc").change(function(){
                            sendType = $("#cc").val();
                            getSelectDate();
                        });
                        getSelectDate();
                    }
                }).datagrid("loadData",data.obj.list);



//                $('#container_line').highcharts({
//                    credits:false,
//                    lang: {
//                        downloadPNG:"生成PNG文件",
//                        downloadJPEG:"生成JPEG文件",
//                        downloadPDF:"生成PDF文件",
//                        downloadSVG:"生成SVG文件",
//                        printButtonTitle:"打印报表"
//                    },
//                    title: {
//                        text: '<b>准时率</b>',
//                        x: -20 //center
//                    },
//                    subtitle: {
//                        text: '日期：'+$("#startday").val()+'  至   '+$("#endday").val(),
//                        x: -20
//                    },
//                    xAxis: {
//                        categories: data.obj.time_names
//                    },
//                    yAxis: {
//                        title: {
//                            text: '出餐率%'
//                        },
//                        plotLines: [{
//                            value: 0,
//                            width: 1,
//                            color: '#808080'
//                        }]
//                    },
//                    plotOptions: {
//                        line: {
//                            dataLabels: {
//                                enabled: true
//                            },
//                            enableMouseTracking: true
//                        }
//                    },
//                    legend: {
//                        layout: 'horizontal',
//                        align: 'center',
//                        verticalAlign: 'bottom',
//                        borderWidth: 0
//                    },
//                    series: data.obj.goods
//                });
//
//
//                $('#container_column').highcharts({
//                    chart: {
//                        type: 'column'
//                    },
//                    title: {
//                        text: '<b>准时率</b>'
//                    },
//                    subtitle: {
//                        text: '日期：'+$("#startday").val()+'  至   '+$("#endday").val(),
//                    },
//                    xAxis: {
//                        categories: data.obj.time_names
//                    },
//                    yAxis: {
//                        min: 0,
//                        title: {
//                            text: '出餐率'
//                        }
//                    },
//                    tooltip: {
//                        headerFormat: '<span style="font-size:12px;">{point.key}</span><table>',
//                        pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
//                        '<td style="padding:0"><b>{point.y}% </b></td></tr>',
//                        footerFormat: '</table>',
//                        shared: true,
//                        useHTML: true
//                    },
//                    plotOptions: {
//                        column: {
//                            pointPadding: 0.2,
//                            borderWidth: 0
//                        }
//                    },
//                    series: data.obj.goods
//                });
//

            })

        }

    </script>
</head>
<body>
<div class="bodymain">
    <div class="htitle">
        <div class="title"><h2>配送准时率统计</h2></div>

        <div class="dateclass">
            <button id="downLoad">下载</button>
            <input id="startday" class="Wdate" type="text"
                   onFocus="WdatePicker({ onpicked:getdata, maxDate: '#F{$dp.$D(\'endday\')}' })" value="${curday}">
            至 <input id="endday" class="Wdate" type="text"
                     onFocus="WdatePicker({ onpicked:getdata, maxDate: '%y-%M-%d', minDate: '#F{$dp.$D(\'startday\')}' })"
                     value="${curday}">
        </div>
        <div class="selecttime">

            <b>订单分类：</b>
            <a class="easyui-linkbutton"  data-options="toggle:true,group:'g2',plain:true,selected:true" onclick="setOrderType(1)">常规订单</a>
            <a class="easyui-linkbutton"  data-options="toggle:true,group:'g2',plain:true" onclick="setOrderType(2)">预送订单</a>

            <a href="###" data-status="0" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">今天</a>
            <a href="###" data-status="2" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true,selected:true">近7天</a>
            <a href="###" data-status="3" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近30天</a>
            <a href="###" data-status="4" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">近90天</a>
        </div>
    </div>


    <div class="inset_auto">
        <div class="main">

            <div id="tableDiv" style="padding:10px;background:#fff;position:absolute;top:114px;left:14px;right:0px;bottom:0px;">
                <table id="tableList" style="width:100%;height:100%">
                </table>
            </div>
            <div id="dd" class="easyui-dialog" style="width:80%;height:60%;">
                <div id="selectTable"></div>
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
