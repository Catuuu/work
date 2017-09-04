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
<html>
<head>
    <title>菜品统计</title>
    <style>
        body {
            padding: 16px 10px;
        }

        .htitle {
            background: #fff;
            padding: 0px;
            margin: 10px auto;
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
            width: 70%;
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
    </style>
    <script type="text/javascript">
        var ordertype = 1;
        $(function () {
            //排序分类
            $(".cation").find("a").on("click", function () {
                $(this).addClass("nowcation").siblings().removeClass("nowcation");
                ordertype = $(this).attr("ordertype");

                getdata();
                return false;
            });

            /*//好评度   进度条
             $(".table_content").each(function () {
             var bili = $(this).find("td:last span").html();
             bili = bili.split("%");
             bili = bili[0];
             $(this).find(".strip").css("width", (bili) + "%")
             });*/

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
                    //var startday = addDate(myDate, -1);
                    $("#endday").val(startday);
                } else if (status == 2) {
                    var startday = addDate(myDate, -7);
                    $("#startday").val(startday);
                    var endday = addDate(myDate, -1);
                    $("#endday").val(endday);
                } else if (status == 3) {
                    var startday = addDate(myDate, -30);
                    $("#startday").val(startday);
                    var endday = addDate(myDate, -1);
                    $("#endday").val(endday);
                } else if (status == 4) {
                    var startday = addDate(myDate, -90);
                    $("#startday").val(startday);
                    var endday = addDate(myDate, -1);
                    $("#endday").val(endday);
                }
                getdata()
            });
            getdata();
        })

        function getdata() {
            var startday = $("#startday").val();
            var endday = $("#endday").val();
            var paramData = {'startday': startday, 'endday': endday, 'ordertype': ordertype};
            $.postJSON("/GoodAPI/goodReport", paramData, function (data) {
                $(".foodname").empty()
                $("#goodList").empty();
                for (let i in data.obj) {
                    var topindex = parseInt(i) + 1;
                    if (i < 3) {
                        var str_center = "";
                        if (i == 1) {
                            str_center = "center"
                        }
                        var toporder = '<div class="foodname_div ' + str_center + '">' +
                                '<img src="/static/images/medal.png" alt="">' +
                                '<span class="number">' + topindex + '</span>' +
                                '<h4>' + data.obj[i].class_good_name + '</h4>' +
                                '<table class="food_info">' +
                                '<tr><td>销售额</td><td>销量</td></tr>' +
                                '<tr><td>' + data.obj[i].good_price + '</td><td>' + data.obj[i].good_count + '</td></tr>' +
                                '</table>' +
                                '</div>';
                        $(".foodname").append(toporder);
                    }
                    var goodtd = '<tr class="table_content">' +
                            '<td>' + topindex + '</td>' +
                            '<td>' + data.obj[i].class_good_name + '</td>' +
                            '<td>' + data.obj[i].good_price + '</td>' +
                            '<td>' + data.obj[i].good_count + '</td>' +
                            '</tr>';
                    $("#goodList").append(goodtd);
                }
                $("#goodtable").show();
            })

        }

    </script>
</head>
<body>
<div class="bodymain">
    <div class="htitle">
        <div class="title"><h2>菜品统计</h2></div>

        <div class="dateclass">
            <input id="startday" class="Wdate" type="text"
                   onFocus="WdatePicker({ onpicked:getdata, maxDate: '#F{$dp.$D(\'endday\')}' })" value="${curday}">
            至 <input id="endday" class="Wdate" type="text"
                     onFocus="WdatePicker({ onpicked:getdata, maxDate: '%y-%M-%d', minDate: '#F{$dp.$D(\'startday\')}' })"
                     value="${curday}">
        </div>
        <div class="selecttime">
            <a href="###" data-status="0" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true,selected:true">今天</a>
            <a href="###" data-status="1" class="easyui-linkbutton  dayselect"
               data-options="toggle:true,group:'g3',plain:true">昨天</a>
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
            <p>本店排行</p>
            <div class="cation">
                <a href="" class="nowcation" ordertype="1">销量最好</a>
                <a href="" ordertype="2">销售额最高</a>
                <%--<a href="" ordertype="3">评价最好</a>--%>
            </div>
            <div class="foodname clear">
                <%--<div class="foodname_div">
                    <img src="/static/images/medal.png" alt="">
                    <span class="number">1</span>
                    <h4>红烧牛腩-份</h4>
                    <table class="food_info">
                        <tr><td>销售额</td><td>销量</td><td>好评率</td></tr>
                        <tr><td>1862.40</td><td>3000</td><td>100%</td></tr>
                    </table>
                </div>
                <div class="foodname_div center">
                    <img src="/static/images/medal.png" alt="">
                    <span class="number">2</span>
                    <h4>红烧牛腩-份</h4>
                    <table class="food_info">
                        <tr><td>销售额</td><td>销量</td></tr>
                        <tr><td>1862.40</td><td>3000</td></tr>
                    </table>
                </div>
                <div class="foodname_div">
                    <img src="/static/images/medal.png" alt="">
                    <span class="number">3</span>
                    <h4>红烧牛腩-份</h4>
                    <table class="food_info">
                        <tr><td>销售额</td><td>销量</td></tr>
                        <tr><td>1862.40</td><td>3000</td></tr>
                    </table>
                </div>--%>
            </div>

            <div id="goodtable" style="margin-bottom: 50px;display: none;">
                <table class="table_list">
                    <thead>
                    <tr class="title">
                        <th style="width:10%">排名</th>
                        <th style="width:10%">菜品</th>
                        <th style="width:15%">销量额(元)</th>
                        <th style="width:15%">销量(份)</th>
                        <%--<th style="width:30%">好评率</th>--%>
                    </tr>
                    </thead>
                    <tbody id="goodList">
                    <tr class="table_content">
                        <td>1</td>
                        <td>红烧牛腩-份</td>
                        <td>1,628.33</td>
                        <td>79</td>
                        <%--<td><div class="strip"></div><span>100%</span></td>--%>
                    </tr>


                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
