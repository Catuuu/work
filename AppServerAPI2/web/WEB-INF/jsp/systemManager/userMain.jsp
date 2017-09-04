<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<script type="text/javascript" src="/static/common/print.js"></script>
<%--*菜单初始化--%>
<link rel="stylesheet" type="text/css" href="/static/client/main.css"/>

<html>

<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${systemName}</title>
    <style>
        /*角标 */
        .bm_css {
            display: none;
            background: #f00;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            top: 14px;
            right: 20px;
            position: absolute;
            text-align: center;
            color: #FFF;
            z-index: 99999;
            line-height: 20px;
        }

        /*select {*/
        /*!*Chrome和Firefox里面的边框是不一样的，所以复写了一下*!*/
        /*border: solid 1px #000;*/

        /*!*很关键：将默认的select选择框样式清除*!*/
        /*appearance: none;*/
        /*-moz-appearance: none;*/
        /*-webkit-appearance: none;*/

        /*!*在选择框的最右侧中间显示小箭头图片*!*/
        /*background: url("http://ourjs.github.io/static/2015/arrow.png") no-repeat scroll right center transparent;*/

        /*!*为下拉小箭头留出一点位置，避免被文字覆盖*!*/
        /*padding-right: 14px;*/
        /*}*/

        /*!*清除ie的默认选择框样式清除，隐藏下拉箭头*!*/
        /*select::-ms-expand {*/
        /*display: none;*/
        /*}*/
    </style>

    <script type="text/javascript">

        $(function () {
            $("#shops option[value='${sessionScope.stores_id}']").attr("selected",true)
            $.ajax({
                url: '/BackMoneyApi/bmCount',
                type: 'POST',
                dataType: 'json',
                success: function (res) {
                    if (res.obj.bm_count != null) {
                        var data = res.obj.bm_count;
                        if (parseInt(data) > 0) {
                            $("#bm").show();
                            $("#bm").text(parseInt(data));
                        } else {
                            $("#bm").hide();
                        }
                    }
                }
            });

            $.ajax({
                url: '/Reminder/cdCount',
                type: 'POST',
                dataType: 'json',
                success: function (res) {
                    if (res.status != null) {
                        var data = res.status;
                        if (parseInt(data) > 0) {
                            $("#bmCd").show();
                            $("#bmCd").text(parseInt(data));
                        } else {
                            $("#bmCd").hide();
                        }
                    }
                }
            });


            $('#shops').change(function () {
//                alert($(this).children('option:selected').val());
                var stores_id = $(this).children('option:selected').val();//这就是selected的值
//                var p2=$('#param2').val();//获取本页面其他标签的值
                $.ajax({
                    url: '/homeApi/shopSelect',
                    type: 'POST',
                    data: {
                        stores_id: stores_id
                    },
                    dataType: 'json',
                    success: function (res) {
                        <%--<c:if test="${action}">--%>
                        <%--$("#ifrmid").attr("src","/Client/exception_orderlist/${action}");--%>
                        <%--</c:if>--%>
                        if (res.status == 1) {
                            $('#ifrmid').attr('src', $('#ifrmid').attr('src'));
                        }
                    }
                });

            })


            $(".li_has").on("click", function () {
                var flag = $(this).attr("flag");
                $(".li_has").each(function () {
                    var next = $(this).next(".hasul");
                    next.hide();
                    $(this).find(".has").removeClass("fa-caret-down").addClass("fa-caret-right");
                    $(this).attr("flag", "false");
                });

                if (flag == "true") {
                    var next = $(this).next(".hasul");
                    next.hide();
                    $(this).find(".has").removeClass("fa-caret-right").addClass("fa-caret-right");
                    $(this).attr("flag", "false");
                } else {
                    var next = $(this).next(".hasul");
                    next.show();
                    $(this).find(".has").removeClass("fa-caret-right").addClass("fa-caret-down");
                    $(this).attr("flag", "true");
                }
            });

            $(".main_left li[class!='hasul']").on("click", function () {
                var _this = $(this).attr("data");
                if (_this != undefined) {
                    $(".main_right iframe").attr("src", _this)
                }
            })

            /*重新登录*/
            $('#loginOut').click(function () {
                $.messager.confirm('系统提示', '您确定要重新登录吗?', function (r) {
                    if (r) {
                        location.href = '/login/userLoginOut';
                    }
                });
            });

            /*退出登录*/
            $('#exitOut').click(function () {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function (r) {
                    if (r) {
                        $.postJSON("/login/userExitOut", null, function (jsonMessage) {
                            location.href = '/login/userLoginOut';
                        })
                    }
                });
            })

            $("#findOrder").keydown(function(e) {
                if (e.keyCode == 13) {
                    findOrder();
                }
            });

            $(".fa-search").click(function(){
                findOrder();
            })

        });
        function findOrder(){
            var keyword = $("#findOrder").val();
            $("#ifrmid").attr("src","/Client/all_orderlist?keyword="+keyword);
        }


        //轮询退款申请
        var getting = {
            url: '/BackMoneyApi/bmCount',
            type: 'POST',
            dataType: 'json',
            success: function (res) {

                if (res.obj.bm_count != null) {
                    var data = res.obj.bm_count;
                    if (parseInt(data) > 0) {
                        $("#bm").show();
                        $("#bm").text(parseInt(data));
                    } else {
                        $("#bm").hide();
                    }
                }
            }
        };
        window.setInterval(function () {
            $.ajax(getting)
        }, 1000 * 60 * 5);


        //催单数量
        var getting = {
            url: '/Reminder/cdCount',
            type: 'POST',
            dataType: 'json',
            success: function (res) {
                if (res.status != null) {
                    var data = res.status;
                    if (parseInt(data) > 0) {
                        $("#bmCd").show();
                        $("#bmCd").text(parseInt(data));
                    } else {
                        $("#bmCd").hide();
                    }
                }
            }
        };
        window.setInterval(function () {
            $.ajax(getting)
        }, 1000 * 60 * 5);


        //验证饿了么登陆是否失效
        var loginCheckMl = {
            url: "/Reminder/validateLogin",
            data: null,
            type: 'POST',
            success: function (result) {
                if (result.status != "0") {
                    //登陆失效
                    $("yzm").src = result.status;
                    $("#dlg").dialog("open").dialog('setTitle', '登陆');
                }
            }
        }
        window.setInterval(function () {
            $.ajax(loginCheckMl)
        }, 1000 * 60 * 5);

        //测试
        function test() {
            var data = Math.random() * 10;
            if (parseInt(data) > 0) {
                $("#bm").show();
                $("#bm").text(parseInt(data));
            } else {
                $("#bm").hide();
            }
        }
        //        window.setInterval(function () {
        //            test()
        //        }, 1000 * 1);

        //手动模拟登陆
        function saveprint() {
            var image = $("#image").val();
            $.ajax({
                url: "/Reminder/reminderLogin",
                data: {image:image},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        //登陆失效
                        $.messager.show({
                            title:'提示信息',
                            msg:'登陆成功',
                            showType:'show'
                        });
                    }else{
                        $.messager.alert("提示信息", "登陆失败");
                    }
                }
            });
        }


    </script>

</head>
<body>
<div id="main">
    <div class="main_top">
        <img class="logo" src="/static/images/logo_top.png" alt="">

        <c:choose>
            <c:when test="${systemOrg.name==null||systemOrg.name==''}">
                <span>菜大师门店管理系统-</span>

                <select id="shops" name="stores_id"
                        style="height:40px;width:100px;float:left;color:#fff;background: #C30D22"
                        data-options="editable:false,panelHeight:'auto'">
                    <option value="-1">全部</option>
                    <c:forEach items="${cdsshops}" var="item">
                        <option value="${item.stores_id}">${item.name}</option>
                    </c:forEach>
                </select>
            </c:when>
            <c:otherwise>
                <span>菜大师门店管理系统-${systemOrg.name}</span>
            </c:otherwise>
        </c:choose>


        <%--<span>菜大师门店管理系统-${systemOrg.name}</span>--%>

        <ul class="simplewind-nav pull-right">
            <li class="light-blue open">
                <span class="user-info">欢迎, ${loginUser.user_nicename}</span>
                <a href="###" id="exitOut"><i class="fa fa-sign-out"></i> 退出</a>
            </li>
        </ul>

        <div class="search">
            <i class="fa fa-search"></i>
            <input id="findOrder" type="text" placeholder="搜索订单详情">
        </div>

    </div>
    <div class="main_left" style="height:100%">
        <ul class="wrap">
            <c:forEach items="${userMenus}" var="item">
                <c:choose>
                    <c:when test="${item.childs!=null && fn:length(item.childs) > 0}">
                        <li class="li_has"><i class="fa fa-${item.icon}"></i><a href="javascript:;">${item.name}</a><i
                                class="has fa fa-caret-right"></i></li>
                        <li class="hasul">
                            <ul class="inset">
                                <c:forEach items="${item.childs}" var="childitem">
                                    <li data="/${childitem.model}/${childitem.action}"><a
                                            href="javascript:;">${childitem.name}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <c:choose>

                            <c:when test="${item.action=='refund'}">

                                <li data="/${item.model}/${item.action}"><i class="fa fa-${item.icon}"></i><a
                                        href="javascript:;">${item.name}<span class='bm_css' id='bm'>4</span></a></li>
                            </c:when>
                            <c:when test="${item.action=='indexCd'}">
                                <li data="/${item.model}/${item.action}"><i class="fa fa-${item.icon}"></i><a
                                        href="javascript:;">${item.name}<span class='bm_css' id='bmCd'>4</span></a></li>
                            </c:when>
                            <c:otherwise>
                                <li data="/${item.model}/${item.action}"><i class="fa fa-${item.icon}"></i><a
                                        href="javascript:;">${item.name}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </ul>
    </div>
    <div class="main_right">
        <iframe name="ifrmname" id="ifrmid" src="/Client/home" frameborder="0" scrolling="no"></iframe>
    </div>
</div>
<div id="_window_div" style="display: none">
    <%--打开Window放置的位置--%>
</div>

<div id="dlg" class="easyui-dialog" style="width: 500px; height: 150px; padding: 10px 20px;"
     closed="true" buttons="#dlg-buttons">
    <form id="fm" method="post">
        <div class="fitem">
            <label  class="mytitle">验证码:</label>
            <input name="image" id="image" class="easyui-validatebox" required="true" /><img id="yzm" src="" alt="验证码">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">登陆</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
</div>
</body>
</html>