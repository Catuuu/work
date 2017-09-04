<%--
  门店主界面
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
    <link rel="stylesheet" type="text/css" href="/static/client/home.css"/>
    <%--<link rel="stylesheet" type="text/css" href="/static/client/home_data.css"/>--%>

    <style>
        body, ul, p, h1, h2, h3, h4, h5, h6, dl, dd, form, input, textarea, select {
            padding: 0;
            margin: 0;
            font-family: arial;
        }

        li {
            list-style: none;
        }

        img {
            border: none;
        }

        a {
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        .clear {
            zoom: 1;
        }

        .clear:after {
            content: '';
            display: block;
            clear: both;
        }

        #warp {
            background: #E5E5E5;
            padding: 28px
        }

        /*title样式#c30d23*/
        .title {
            width: 90%;
            margin: 0 auto;
        }

        .title h3 {
            font-size: 27px;
            display: inline-block;
            font-weight: 700;
        }

        .title span {
            font-size: 18px;
            background: #c30d23;
            color: #fff;
            float: right;
            padding: 3px 10px;
            border-radius: 6px;
        }

        /*content样式*/
        .content {
            display: flex
        }

        .seller {
            flex: 1;
            position: relative;
            background: #fff;
            padding: 80px 20px;
            width: 420px;
            margin-top: -17px;
            border-radius: 6px;
            z-index: 100;
            margin-left: 20px;
        }

        .seller:first-of-type {
            margin-left: -25px;
        }

        .seller h3, .seller p {
            text-align: center;
        }

        .seller h3 {
            font-size: 22px;
        }

        .seller h3 span {
            color: #ffcc00;
            font-size: 22px
        }

        .seller p {
            font-size: 14px;
            line-height: 20px;
            margin-bottom: 30px;
            font-size: 18px
        }

        .seller ul {
            font-size: 14px;
        }

        .seller li {
            width: 75%;
            margin: 10px auto;
            height: 24px;
            display: flex;
            position: relative;
        }

        .seller li .line {
            font-size: 30px;
            height: 12px;
            border-bottom: dashed 1px #ccc;
            flex: 1;
        }

        .seller li span {
            display: inline-block
        }

        .seller li .name {
            line-height: 24px;
            font-size: 16px
        }

        .seller li .money {
            display: block;
            width: 280px;
            float: left;
            line-height: 24px;
            font-size: 16px
        }

        .seller h6 {
            /*font-family: '楷体';*/
            color: #da2e27;
            text-align: center;
            margin: 20px;
            font-size: 20px
        }

        .seller h5 {
            text-align: left;
            margin: -20px 0px 50px 0px;
            font-size: 16px
        }

        .list {
            display: flex;
            font-size: 14px;
            height: 22px;
            line-height: 22px;
        }

        /*.list span {*/
        /*font-weight: 900;*/
        /*flex: 1;*/
        /*text-align: center;*/
        /*font-size: 14px*/
        /*}*/

        .list .brand_name {
            font-weight: 900;
            flex: 1;
            text-align: center;
            font-size: 14px
        }

        .list .brand_info {
            flex: 1;
            text-align: center;
            font-size: 14px
        }

        .top-arrow {
            position: absolute;
            left: 50%;
            top: 93%
        }

    </style>

    <script type="text/javascript">
        $(function () {
            $(".top-arrow").click(function () {
                if ($(".show").css('display') === 'none') {
                    $(".show").show();
                    $(".top-arrow").attr('src', '/static/images/top-arrow.jpg');
                } else {
                    $(".show").hide();
                    $(".top-arrow").attr('src', '/static/images/bottom-arrow.jpg');
                }
            });



            $('#sl_date_begin').datebox().datebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
//                    var d2 = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 10);
                    return date && date <= d1;
                }
            });


            $('#sl_date_begin').datebox({
                onSelect: function (date) {
                    var cur_date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                    $.ajax({
                        url: '/homeApi/shopSelect',
                        type: 'POST',
                        data: {
                            cur_date: cur_date
                        },
                        dataType: 'json',
                        success: function (res) {
                            if (res.status == 1) {
                                window.location.reload();
                            }
                        }
                    });
                }
            });

        });


    </script>
</head>
<body>
<div class="bodymain">
    <div id="order" class="clear">
        <div class="order_left order_item">
            <div class="title clear">
                <span>订单实时数据</span>&nbsp;&nbsp;&nbsp;&nbsp;
                <%--<input class="Wdate" type="text" id="sl_date_begin" name="sl_date_begin"--%>
                <%--onClick="WdatePicker({maxDate:'${curday}'})" value="${curday}">--%>


                <a href="/Client/orderlist">订单查询></a>
            </div>
            <div class="project clear">
                <div class="project_item">
                    <span class="new"><c:out value="${doCount1}"/></span>
                    <p>待接单</p>
                </div>
                <div class="project_item">
                    <span class="new"><c:out value="${dopackCount}"/></span>
                    <p>待打包</p>
                </div>
                <div class="project_item">
                    <span class="new"><c:out value="${dosendCount}"/></span>
                    <p>待取餐</p>
                </div>

                <div class="project_item">
                    <span class="new"><c:out value="${doCount3}"/></span>
                    <p>配送中</p>
                </div>
                <div class="project_item">
                    <span class="old"><c:out value="${doCount4}"/></span>
                    <p>已完成</p>
                </div>
                <div class="project_item">
                    <span class="cancel"><c:out value="${doCount99}"/></span>
                    <p>已取消</p>
                </div>

            </div>
        </div>
        <div class="order_right  order_item">
            <div class="title clear">
                <span>特殊提醒</span>
            </div>
            <div class="project clear">
                <div class="project_item">
                    <span class="feedback">${advanceCount}</span>
                    <p><a href="javascript:void(0)" onclick="parent.ifrmid.src='/Client/exception_orderlist/1'">预订单</a></p>
                </div>
                <div class="project_item last">
                    <span class="feedback">${packExceptionCount}</span>
                    <p><a href="javascript:void(0)" onclick="parent.ifrmid.src='/Client/exception_orderlist/2'">打包异常</a></p>
                </div>
                <div class="project_item last">
                    <span class="feedback">${sendExceptionCount}</span>
                    <p><a href="/Client/exception_orderlist/3" onclick="parent.ifrmid.src='/Client/exception_orderlist/3'">配送异常</a></p>
                </div>
            </div>
        </div>
    </div>

    <%--<div class="order_bottom">--%>

    <%--数据.改--%>
    <div id="warp">
        <div class="content">
            <div class="seller">
                <%--←↑→↓↖↙↗↘↕--%>
                <h5>商户 <input id="sl_date_begin" style="height: 20px; width: 100px" class="easyui-datebox"
                              value="${curday}"/></h5>
                <fmt:parseNumber value="${map.curser.income}" type="number" var="a"></fmt:parseNumber>
                <fmt:parseNumber value="${map.yesterday.income}" type="number" var="b"></fmt:parseNumber>
                <c:choose>
                    <c:when test="${a>=b}">
                        <h3>应收&nbsp;<span style="color:#3aba60">↑${map.curser.income}</span>元</h3>
                    </c:when>

                    <c:otherwise>
                        <h3>应收&nbsp;<span style="color:#da2e27">↓${map.curser.income}</span>元</h3>
                    </c:otherwise>
                </c:choose>
                <p style="font-size: 14px;color:#858585;">
                    [昨日${map.yesterday.income}]&nbsp;&nbsp;[上周${map.last_week.income}]</p>

                <p>原营业额 ${map.curser.sum1}</p>
                <p style="font-size: 14px;margin-top: -30px;color:#858585;">
                    [昨日${map.yesterday.income}]&nbsp;&nbsp;[上周${map.last_week.income}]</p>

                <ul>
                    <li>
                        <span class="name">商户补贴</span>&nbsp;<span class="line"></span>&nbsp;<span
                            class="money">${map.curser.shop_part}&nbsp;&nbsp;<span class="his"
                                                                                   style="font-size: 14px;color:#858585;">[昨日${map.yesterday.shop_part}]&nbsp;&nbsp;[上周${map.last_week.shop_part}]</span></span>

                    </li>

                    <li>
                        <span class="name">平台补贴</span>&nbsp;<span class="line"></span>&nbsp;<span
                            class="money">${map.curser.platform_part}&nbsp;&nbsp;<span class="his"
                                                                                       style="font-size: 14px;color:#858585;">[昨日${map.yesterday.platform_part}]&nbsp;&nbsp;[上周${map.last_week.platform_part}]</span></span>
                    </li>

                    <li>
                        <span class="name">红包返还</span>&nbsp;<span class="line"></span>&nbsp;<span
                            class="money">${map.curser.uc_price}&nbsp;&nbsp;<span class="his"
                                                                                  style="font-size: 14px;color:#858585;">[昨日${map.yesterday.uc_price}]&nbsp;&nbsp;[上周${map.last_week.uc_price}]</span></span>
                    </li>

                    <li>
                        <span class="name">平台服务费</span>&nbsp;<span class="line"></span>&nbsp;<span
                            class="money">${map.curser.serviceFee}&nbsp;&nbsp;<span class="his"
                                                                                    style="font-size: 14px;color:#858585;">[昨日${map.yesterday.serviceFee}]&nbsp;&nbsp;[上周${map.last_week.serviceFee}]</span></span>
                    </li>

                </ul>
                <div class="show" style="display: none">
                    <h6>菜大师</h6>
                    <div class="list">
                        <span class="brand_name">名称</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_name">${item.fromin}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">应收</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.income}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">原营业额</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.sum1}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">商户补贴</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.shop_part}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">平台补贴</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.platform_part}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">红包返还</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.uc_price}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">平台服务费</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.serviceFee}</span>
                        </c:forEach>
                    </div>

                    <%--<c:out value="${map.curser.sxg.size()==0}"></c:out>--%>

                    <c:choose>
                        <c:when test="${map.curser.sxg.size()==0}">
                        </c:when>
                        <c:otherwise>
                            <h6>帅小锅</h6>
                            <div class="list">
                                <span class="brand_name">名称</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_name">${item.fromin}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">应收</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.income}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">原营业额</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.sum1}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">商户补贴</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.shop_part}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">平台补贴</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.platform_part}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">红包返还</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.uc_price}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">平台服务费</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.serviceFee}</span>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>


                </div>
                <img class="top-arrow" src="/static/images/bottom-arrow.jpg" height="24" width="24"/>
            </div>
            <div class="seller">
                <h5>顾客</h5>
                <fmt:parseNumber value="${map.curser.total_price}" type="number" var="a1"></fmt:parseNumber>
                <fmt:parseNumber value="${map.yesterday.total_price}" type="number" var="b1"></fmt:parseNumber>
                <c:choose>
                    <c:when test="${a1>=b1}">
                        <h3>实付&nbsp;<span style="color:#3aba60">↑${map.curser.total_price}</span>元</h3>
                    </c:when>

                    <c:otherwise>
                        <h3>实付&nbsp;<span style="color:#da2e27">↓${map.curser.total_price}</span>元</h3>
                    </c:otherwise>
                </c:choose>


                <p style="font-size: 14px;color:#858585;">
                    [昨日${map.yesterday.total_price}]&nbsp;&nbsp;[上周${map.last_week.total_price}]</p>
                <p>商品支付 ${map.curser.goods_prcie}</p>
                <p style="font-size: 14px;margin-top: -30px;color:#858585;">[昨日${map.yesterday.goods_prcie}]&nbsp;&nbsp;[上周${map.last_week.goods_prcie}]</p>
                <ul>
                    <li>
                        <span class="name">餐盒费</span>&nbsp;<span class="line"></span>&nbsp;<span
                            class="money">${map.curser.box_price}&nbsp;&nbsp;<span
                            style="font-size: 14px;color:#858585;">[昨日${map.yesterday.box_price}]&nbsp;&nbsp;[上周${map.last_week.box_price}]</span></span>
                    </li>

                    <li>
                        <span class="name">配送费</span>&nbsp;<span class="line"></span>&nbsp;<span
                            class="money">${map.curser.ship_fee}&nbsp;&nbsp;<span
                            style="font-size: 14px;color:#858585;">[昨日${map.yesterday.ship_fee}]&nbsp;&nbsp;[上周${map.last_week.ship_fee}]</span></span>
                    </li>
                    <%--<li>--%>
                    <%--<span class="name">商户补贴</span>&nbsp;<span class="line"></span>&nbsp;<span--%>
                    <%--class="money">${map.curser.shop_part}&nbsp;&nbsp;<span--%>
                    <%--style="font-size: 14px;color:#858585;">[昨日${map.yesterday.shop_part}]&nbsp;&nbsp;[上周${map.last_week.shop_part}]</span></span>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                    <%--<span class="name">平台补贴</span>&nbsp;<span class="line"></span>&nbsp;<span--%>
                    <%--class="money">${map.curser.platform_part}&nbsp;&nbsp;<span--%>
                    <%--style="font-size: 14px;color:#858585;">[昨日${map.yesterday.platform_part}]&nbsp;&nbsp;[上周${map.last_week.platform_part}]</span></span>--%>
                    <%--</li>--%>
                    <li>
                        <span class="name">红包抵</span>&nbsp;<span class="line"></span>&nbsp;<span
                            class="money">${map.curser.uc_price}&nbsp;&nbsp;<span
                            style="font-size: 14px;color:#858585;"> [昨日${map.yesterday.uc_price}]&nbsp;&nbsp;[上周${map.last_week.uc_price}]</span></span>
                    </li>
                </ul>
                <div class="show" style="display: none">
                    <h6>菜大师</h6>
                    <div class="list">
                        <span class="brand_name">名称</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_name">${item.fromin}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">实付</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.total_price}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">商品支付</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.goods_prcie}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">餐盒费</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.box_price}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">配送费</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.ship_fee}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">红包抵</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.uc_price}</span>
                        </c:forEach>
                    </div>
                    <c:choose>
                        <c:when test="${map.curser.sxg.size()==0}">
                        </c:when>
                        <c:otherwise>
                            <h6>帅小锅</h6>
                            <div class="list">
                                <span class="brand_name">名称</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_name">${item.fromin}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">实付</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.box_price}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">商品支付</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.goods_prcie}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">餐盒费</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.box_price}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">配送费</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.ship_fee}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">红包抵</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.uc_price}</span>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
                <img class="top-arrow" src="/static/images/bottom-arrow.jpg" height="24" width="24"/>
            </div>
            <div class="seller">
                <h5>订单</h5>
                <fmt:parseNumber value="${map.curser.sum2}" type="number" var="a2"></fmt:parseNumber>
                <fmt:parseNumber value="${map.yesterday.sum2}" type="number" var="b2"></fmt:parseNumber>
                <c:choose>
                    <c:when test="${a2>=b2}">
                        <h3>有效单&nbsp;<span style="color:#3aba60">↑${map.curser.sum2}</span></h3>
                    </c:when>

                    <c:otherwise>
                        <h3>有效单&nbsp;<span style="color:#da2e27">↓${map.curser.sum2}</span></h3>
                    </c:otherwise>
                </c:choose>


                <p style="font-size: 14px;color:#858585;">
                    [昨日${map.yesterday.sum2}]&nbsp;&nbsp;[上周${map.last_week.sum2}]</p>
                <p>无效单${map.curser.sum4}</p>
                <p style="font-size: 14px;margin-top: -30px;color:#858585;">
                    [昨日${map.yesterday.sum4}]&nbsp;&nbsp;[上周${map.last_week.sum4}]</p>
                <ul>
                    <li>
                        <span class="name">客单价</span>&nbsp;<span class="line"></span>&nbsp;<span
                            class="money">${map.curser.sum3}&nbsp;&nbsp;<span
                            style="font-size: 14px;color:#858585;">[昨日${map.yesterday.sum3}]&nbsp;&nbsp;[上周${map.last_week.sum3}]</span></span>
                    </li>

                </ul>
                <div class="show" style="display: none">
                    <h6>菜大师</h6>
                    <div class="list">
                        <span class="brand_name">名称</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_name">${item.fromin}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">有效单数</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.sum2}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">无效单数</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.sum4}</span>
                        </c:forEach>
                    </div>
                    <div class="list">
                        <span class="brand_name">有效客单价</span>
                        <c:forEach items="${map.curser.cds}" var="item">
                            <span class="brand_info">${item.sum3}</span>
                        </c:forEach>
                    </div>

                    <c:choose>
                        <c:when test="${map.curser.sxg.size()==0}">
                        </c:when>
                        <c:otherwise>
                            <h6>帅小锅</h6>
                            <div class="list">
                                <span class="brand_name">名称</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_name">${item.fromin}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">有效单数</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.sum2}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">无效单数</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.sum4}</span>
                                </c:forEach>
                            </div>
                            <div class="list">
                                <span class="brand_name">有效客单价</span>
                                <c:forEach items="${map.curser.sxg}" var="item">
                                    <span class="brand_info">${item.sum3}</span>
                                </c:forEach>
                            </div>
                        </c:otherwise>
                    </c:choose>

                </div>
                <img class="top-arrow" src="/static/images/bottom-arrow.jpg" height="24" width="24"/>
            </div>
        </div>
    </div>

    <%--</div>--%>

    <br/>
</div>

</body>
</html>
