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
    <title>顾客评价</title>
    <link rel="stylesheet" href="/static/client/comment.css">
    <script>
        $(function(){
            $(".content .tell_user").click(function(){
                $(this).siblings("form").toggle();
                return false;
            });
            $(".content .submit .submitno").click(function(){
                $(this).parents("form").find("textarea").val("");
                $(this).parents("form").toggle();
                return false;
            });
            $(".content .submit .submitgo").click(function(){
                if( $(this).parents("form").find("textarea").val()==""){
                    alert("请输入正确的内容")
                }
                else{
                    $(this).parents("form").toggle();
                }
                return false;
            })

            $(".inp").click(function(){
                $(this).parent("label").addClass("check_color");
                $(this).parent("label").siblings("label").removeClass("check_color");
            })


        })
    </script>

</head>
<body>
<div class="bodymain" >
    <div id="main">
        <div class="main_title clear">
            <div class="title_left">
                <label class="check_color" for=""><input name="ha" checked type="radio" class="nobad inp">未完成差评<span>(11)</span></label>
                <label for=""><input name="ha" type="radio" class="no inp">未完成<span>(4)</span></label>
                <label for=""><input name="ha" type="radio" class="all inp">全部</label>
            </div>
            <div class="title_right">
                <input type="checkbox" class="onlylook" checked><span>只查看有内容的评价</span>
            </div>
        </div>
        <div class="main_content">
            <div class="main_star clear">
                <div class="stars">
                    <i class="fa fa-star good"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                </div>
                <div class="order_info">
                    订单编号
                    <span class="order_code">101584423541344657</span>
                    <span class="order_time">2016-11-26 11:45</span>
                </div>
            </div>
            <div class="content">
                <p>订的藕汤没有送 电话打不通</p>
                <a href="javascript:;" class="tell_user">回复</a>
                <form action="">
                    <textarea name="" placeholder="请输入内容"></textarea>
                    <div class="submit">
                        <a href="" class="submitno abtn">取消</a><a href="" class="submitgo  abtn">发表回复</a>
                    </div>
                </form>
            </div>

        </div>

        <div class="main_content">
            <div class="main_star clear">
                <div class="stars">
                    <i class="fa fa-star good"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                </div>
                <div class="order_info">
                    订单编号
                    <span class="order_code">101584423541344657</span>
                    <span class="order_time">2016-11-26 11:45</span>
                </div>
            </div>
            <div class="content">
                <p class="user_content">订的藕汤没有送 电话打不通</p>
                <a href="javascript:;" class="tell_user">回复</a>
                <form action="">
                    <textarea name="" placeholder="请输入内容"></textarea>
                    <div class="submit">
                        <a href="" class="submitno abtn">取消</a><a href="" class="submitgo  abtn">发表回复</a>
                    </div>
                </form>
            </div>
        </div>
        <div class="main_content">
            <div class="main_star clear">
                <div class="stars">
                    <i class="fa fa-star good"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                    <i class="fa fa-star"></i>
                </div>
                <div class="order_info">
                    订单编号
                    <span class="order_code">101584423541344657</span>
                    <span class="order_time">2016-11-26 11:45</span>
                </div>
            </div>
            <div class="content">
                <p>订的藕汤没有送 电话打不通</p>
                <a href="javascript:;" class="tell_user">回复</a>
                <form action="">
                    <textarea name="" id="" placeholder="请输入内容"></textarea>
                    <div class="submit">
                        <a href="" class="submitno abtn">取消</a><a href="" class="submitgo  abtn">发表回复</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
