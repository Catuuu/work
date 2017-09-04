
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>平台商品管理</title>
    <style>
        li {
            line-height: 26px;
            font-size: 14px;
            color: #48576a;
            padding: 0 10px;
            cursor: pointer;
            position: relative;
            transition: border-color .3s, background-color .3s, color .3s;
            box-sizing: border-box;
            white-space: nowrap;
        }

        #fm {
            margin: 0;
        }

        .ftitle {
            font-size: 14px;
            font-weight: bold;
            padding: 5px 0;
            margin-bottom: 10px;
            border-bottom: 1px solid #ccc;
        }

        .fitem {

            border-bottom: 1px dashed #9f9f9f;
            padding-bottom: 15px;
            padding-top: 15px;
        }

        .fitem label {
            display: inline-block;
            width: 100px;
        }

        .mytitle {
            font-weight: lighter;
        }

        #bd2 {
            padding-top: 10%;
            padding-left: 30%;
        }

        #bd1 {
            padding-top: 10%;
            padding-left: 30%;
        }

        .datagrid-row {
            height: 42px;
            text-align: center;
        }

        .datagrid-header-row {
            height: 42px;
            font-weight: 700;
        }

        .panel-title {
            font-size: 12px;
            font-weight: bold;
            color: #777;
            height: 16px;
            line-height: 16px;
        }
        a{text-decoration:none}
    </style>
    <script type="text/javascript">

        $(function () {


            $('#tabs').tabs({
                onSelect: function(title){
                    if(title=="饿了么未处理"){
                        greatGrid("List","1","饿了么");
                    }else if(title=="饿了么已处理"){
                        greatGrid("List2","2","饿了么");
                    }else if(title=="美团未处理"){
                        greatGrid("List3","1","美团");
                    }else if(title=="美团已处理"){
                        greatGrid("List4","2","美团");
                    }
                }
            });

            //判断登陆是否失效
            $.ajax({
                url: "/Reminder/validateLogin",
                data: null,
                type: 'POST',
                timeout:2000,
                success: function (result) {
                    if (result.status != "0") {
                        //登陆失效
                        $("yzm").src = result.status;
                        $("#dlg").dialog("open").dialog('setTitle', '登陆');
                    }
                }
            });

            greatGrid("List","1");

//            var cookie1 = document.cookie;
//            console.log(cookie1);
//            var acookie=document.cookie.split(";");
//            alert(acookie);

//            var dt = new Date();
//            dt.setSeconds(dt.getSeconds() + 60);
//            document.cookie = "cookietest=1; expires=" + dt.toGMTString();
//            var cookiesEnabled = document.cookie.indexOf("cookietest=") != -1;
//            if(!cookiesEnabled) {
//                //没有启用cookie
//                alert("没有启用cookie ");
//            } else{
//                //已经启用cookie
//                alert("已经启用cookie ");
//            }
//
//            var acookie = $.cookie();
//            console.log(acookie);
//            var cookie = document.cookie;
//            alert(cookie);
        });


        function greatGrid(id,do_status,fromin) {
            $('#'+id).datagrid({
                fit: true,
                nowrap: false,
                striped: false,
                url: '/Reminder/geReminderList',
                method: 'POST',
                sortName: 'cr_time',
                queryParams:{
                    do_status:do_status,
                    fromin:fromin
                },
                sortOrder: 'desc',
                idField: 'id',
                frozenColumns: [[
                    {
                        field: 'order_no', title: '订单编号', width: 200, sortable: true, formatter: function (val, row) {
                        var str = '<div style="margin: 5px;">';
                        str += '<div style="color:#4c93fd"><b>流水号：' + row.order_no + '</b></div>';

                        str += '编号:' + row.order_id + '<br>';
                        str += '<div style="color:#345645">' + row.stores_name + '</div>';
                        str += '<div style="color:#008200">下单时间:' + row.create_date + '</div>';
                        str += "</div>"
                        return str;
                    }
                    }
                ]],
                rowStyler: function (rowIndex, rowData) {
                    if (rowData && rowData.order_status == 99) {
                        return 'background-color:#F4F4F4;color:#CDC5BF';
                    }
                },
                columns: [[
                    { field: 'reminder_id', title: 'ID', width: '10%', align: 'center',hidden:true},
                    {
                        field: 'fromin_no', title: '订单来源', width: 180, sortable: true, sorter: function (a, b) {
                        return "CAST(fromin_no as int)";
                    }, formatter: function (val, row) {
                            // var str = '<div style="margin: 5px;">';
                            var str = '<div style="margin: 5px;">';
                            str += '<div style="color:#4c93fd"><b>品牌：#' + row.brand_name + '</b></div>';
                            str += '<div style="color:#4c93fd"><b>来源流水号：#' + row.fromin_no + '</b></div>';
                            var fromin = row.fromin;
                            if (row.fromin_name != null && row.fromin_name != "") {
                                fromin += ":" + row.fromin_name;
                            }
                            str += '<div>' + fromin + '</div>';
                            str += '<div >SN：' + row.order_desc + '</div>';
                            str += "</div>"
                            return str;
                    }
                    },
                    {
                        field: 'receiver_name', title: '收货人', width: 200, formatter: function (val, row) {
                            var str = '<div style="margin: 5px;">';
                            str += '<div><b>' + row.receiver_name + ' ' + row.receiver_phone + '</b></div>';
                            if (row.receiver_adress != undefined) {
                                str += '<div>' + row.receiver_adress + '</div>';
                            }

                            if (row.order_type == 1) {
                                str += '<div  style="color:#008200">就餐方式：送货到门</div>';
                            } else {
                                str += '<div  style="color:#d94600">就餐方式：店堂就餐</div>';
                            }
                            if (row.service_time != undefined && row.service_time != '') {
                                if (row.service_time_str == undefined) {
                                    str += '<div  style="background:#d94600;color: #fff;padding: 2px;border-radius: 4px;">预订单</div>';
                                } else {
                                    str += '<div  style="background:#d94600;color: #fff;padding: 2px;border-radius: 4px;">预订单：' + row.service_time_str + '</div>';
                                }

                            }
                            if (row.member_desc != undefined && row.member_desc != '') {
                                str += '<div  style="background:#718BB7;color: #fff;padding: 2px;border-radius: 4px;">客户备注：' + row.member_desc + '</div>';
                            }

                            str += "</div>"
                            return str;
                        }
                    },
                    {
                        field: 'goods', title: '订单商品', width: 180, formatter: function (val, row) {
                            if (val == undefined || val == "") {
                                return "";
                            }
                            var str = '<div style="margin: 5px;">';
                            var goods = JSON.parse(val);
                            var good_count = 0;
                            for (var i = 0; i < goods.length; i++) {
                                var good = goods[i];
                                if (good.good_type == 2) {
                                    str += "<span style='color:#da2e27'>[赠品]</span>"
                                }

                                var price = good.price;
                                if (price == undefined || price == "") {
                                    price = 0;
                                }
                                str += "[" + good.good_name + ":" + good.quantity + "份 ￥" + price + "]<br>";

                                good_count += good.quantity;
                            }
                            str += '<div style="color:#9F35FF">共' + good_count + '份,合计：￥' + row.goods_prcie + '</div>';
                            str += "</div>"
                            return str;
                        }
                    },
                    {
                        field: 'pack_name', title: '打包信息', width: 120, formatter: function (val, row) {
                            var str = '<div style="margin: 5px;">';
//                            if ((row.order_status == 1 || row.order_status == 2) && row.pack_time == null) {
//                                str += '<div style="color:#2e6da4">等待：' + row.pack_wait_time + '(分钟)</div>';
//                            }

                            if (row.pack_user_name != null) {
                                str += '<div>打包员：' + row.pack_user_name + '</div>';
                                str += '<div>' + row.pack_user_time + '</div>';
                            }
                            if (row.pack_time != null) {
                                str += '<div>打包用时：' + row.pack_time + '分钟</div>';
                            }
                            str += "</div>"
                            return str;
                        }
                    },

                    {
                        field: 'send_name', title: '配送信息', width: 200, formatter: function (val, row) {
                            var send_time = '';
                            var time1=row.task_time;
                            var time2=row.task_user_time;
                            if(time2!=null&&time2!=""&&time2!=undefined){
                                if(time1!=null&&time1!=""&&time1!=undefined){
                                    var min1=parseInt(time1.substr(11,2))*60+parseInt(time1.substr(14,2));
                                    var min2=parseInt(time2.substr(11,2))*60+parseInt(time2.substr(14,2));
                                    send_time=min1-min2;
                                }
                            }
                            var str = '<div style="margin: 5px;">';
//                            if ((row.order_status == 1 || row.order_status == 2) && row.takemea_time == null) {
//                                str += '<div style="color:#2e6da4">取餐等待：' + row.takemea_wait_time + '(分钟)</div>';
//                            }
                            if (row.takemea_time != null) {
                                str += '<div>取餐用时：' + row.takemea_time + '分钟</div>';
                            }

                            if (row.is_sync == 1) {
                                str += '<div><a id="viewsend' + row.rownumber + '"  onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '<span style="color: red;padding: 5px;">（未发送）</span>(' + row.distance + '米)</a></div>';

                            } else if (row.is_sync == 2) {
                                str += '<div><a id="viewsend' + row.rownumber + '" onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '</a>(' + row.distance + '米)</div>';
                                str += '<div>已发送：' + row.send_time + '</div>';
                                str += '<div>配送sn：' + row.send_id + '</div>';
                            } else if (row.send_name != undefined && row.send_name != "") {
                                str += '<div><a id="viewsend' + row.rownumber + '" onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '</a>(' + row.distance + '米)</div>';
                            } else {
                                str += '<div>配送方式：未指定</div>';
                            }

                            if (row.send_exception != undefined && row.send_exception != "") {
                                str += '<div style="color:red">配送异常：' + row.send_exception + '</div>';
                                str += '<div>配送sn：' + row.send_id + '</div>';
                            }

                            str += '<div>';
                            if (row.task_order_name != null) {
                                str += '<div>接单员：' + row.task_order_name + '</div>';
                                str += '<div>tel：' + row.task_order_phone + '</div>';
                                str += '<div>' + row.task_order_time + '</div>';
                            }
                            if (row.task_user_name != null) {
                                str += '<div>配送员：' + row.task_user_name + '</div>';
                                str += '<div>tel：' + row.task_user_phone + '</div>';
                                str += '<div>' + row.task_user_time + '</div>';
                            }
                            if (row.order_status == 3) {
                                str += '<div style="color:#2e6da4">配送已用时间：' + row.use_send_wait_time + '(分钟)</div>';
                            }
                            if (row.task_time != null) {
                                str += '<div>送达时间：' + send_time + '分钟</div>';
                                str += '<div>' + row.task_time + '</div>';
                            }
                            str += "</div>"
                            return str;
                        }
                    },
                    {
                        field: 'send_reason', title: '催单信息', width: 150, align: 'center',
                        formatter: function (val, row) {
                            var send_type = '';
                            var str = '<div style="margin: 10px;text-align: left">';
                            var cr_remark ="";
                            var cr_time = "";
                            if(row.cr_remark!=undefined&&row.cr_remark!=null){
                                cr_remark = row.cr_remark;
                            }
                            if(row.cr_time!=undefined&&row.cr_time!=null){
                                cr_time =row.cr_time;
                            }
                            str +="催单内容："+ cr_remark + "<br>";
                            str +="催单时间："+ cr_time + "<br>";
//                            str +="催单次数："+ row.times + "<br>";
                            str +='催单次数：'+'<font style="color:red">' + row.times + '</font>'
                            str +="</div>";
                            return str;
                        }
                    },
                    {
                        field: 'state', title: '订单状态', width: 120, formatter: function (val, row) {
                            var str = '<div style="margin: 5px;"> <div><a id="orderstatusview' + row.rownumber + '" style="position: relative" onclick="orderstatusview(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">';
                            if (row.order_status == 0) {
                                str += '订单状态：商家待接单';
                            } else if (row.order_status == 1 || row.order_status == 2) {
                                if (row.pack_user_time == undefined) {
                                    str += '订单状态：待打包';
                                } else {
                                    str += '订单状态：已打包';
                                }
                            } else if (row.order_status == 3) {
                                str += '订单状态：配送中';
                            } else if (row.order_status == 4) {
                                str += '订单状态：已完成';
                            } else if (row.order_status == 99) {
                                str += '订单状态：订单已取消';
                            }
                            str += '</a></div>';

                            if (row.isstoresprint == 0) {
                                str += '<div style="color: #ab1e1e;">打印状态：未打印</div>';
                            } else if (row.isstoresprint == 1) {
                                str += '<div>打印状态：已打印</div>';
                            } else if (row.isstoresprint == 2) {
                                str += '<div>打印状态：已取消</div>';
                            }


                            str += "</div>"
                            return str;
                        }
                    },
                    {field: 'cr_check_remark', title: '回复内容', width: 120, align: 'center'},
                    {
                        field: 'type', title: '操作类型', width: '8%', align: 'center',
                        formatter: function (val, row) {
                            if(val=="1"){
                                return '系统回复'
                            }else if(val=="2"){
                                return '人工回复'
                            }else{
                                return'未回复';
                            }
                        }
                    },
                    {
                        field: 'opt', title: '操作', width: '5%', align: 'center', formatter: function (val, row) {
                        if (row.do_status == "1") {
                            var str = '<div style="margin: 5px;">';
                            str += '<div><a class="gift-delbtn" style="color: green" onclick="elemhf(\'' + row.reminder_id + '\')">回复</a></div>';
                            str += "</div>"
                        }
//                        else{
//                            var str='<a class="gift-delbtn" onclick="elemLogin(\'' + row.reminder_id + '\')">登陆</a>';
//                        }
                        return str;
                    }

                    }
                ]],
                pagination: true,
                singleSelect: true,
                rownumbers: true
            });
        }



        function toolmove(rownumber) {
            $('#orderstatusview' + rownumber).tooltip("hide");
        }

        //查看订单状态
        function orderstatusview(order_id, rownumber) {
//            alert($(".bodymain").scrollTop())
            $.ajax({
                url: "/orderSelectAPI/vieworderlog",
                type: 'POST',
                data: {order_id: order_id},
                success: function (result) {
                    if (result.status == "1") {
                        if (result.obj != null) {
                            var content = "";
                            for (var i = 0; i < result.obj.length; i++) {
                                var log = result.obj[i];
                                content += log.opt_time + " " + log.opt_note;
                                content += "<br>";
                            }
                            if (content != "") {
//                                var a='<div  class="tip"><div class="tip-content"><span style="color:#fff">'+content+'</span></div><div class="tip-arrow-outer"></div><div class="tip-arrow"></div></div>'
//                                $('#orderstatusview' + rownumber).html(a);
                                $('#orderstatusview' + rownumber).tooltip({
                                    showEvent: 'dblclick',
                                    hideEvent: 'dblclick',
                                    position: 'bottom',
                                    content: '<span onmouseleave="toolmove(\'' + rownumber + '\')"  style="color:#fff">' + content + '</span>',
                                    onShow: function () {
                                        $(this).tooltip('tip').css({
                                            backgroundColor: '#666',
                                            borderColor: '#666'
                                        });
                                    }
                                });
                                $('#orderstatusview' + rownumber).tooltip("show");
                            }


                        }

                    }
                    else {
                        $.messager.alert("提示信息", "数据请求失败");
                    }
                }
            });
        }


        //订单穿透
        function showOrderInfo(type, order_id,rownumber) {
            $.ajax({
                url: "/GiftApi/orderInfo",
                type: 'POST',
                data: {order_id: order_id},
                success: function (result) {
                    if (result.status == "1") {
                        if (result.obj != null) {
                            var content = "";
                            var log = result.obj;
                            var goods_str = '';
                            var goods = JSON.parse(log.goods);
                            var good_count = 0;
                            for (var i = 0; i < goods.length; i++) {
                                var good = goods[i];
                                goods_str += "[" + good.good_name + ":" + good.quantity + "份 ￥" + good.price + "]<br>";
                                good_count += good.quantity;
                            }
                            goods_str += '共' + good_count + '份,合计：￥' + log.goods_prcie;
                            content += "订单号：" + log.order_id + "<br>";
                            content += "订单流水号：" + log.order_no + "<br>";
                            content += "来源平台：" + log.fromin + "<br>";
                            content += "来源流水号：" + "#" + log.fromin_no + "<br>";
                            content += "来源店铺：" + log.fromin_name + "<br>";
                            content += "支付方式：" + log.pay_type_name + "<br>";
                            content += "收货人：" + log.receiver_name + "<br>";
                            content += "收货人电话：" + log.receiver_phone + "<br>";
                            content += "收货人地址：" + log.receiver_adress + "<br>";
                            content += "支付时间：" + log.pay_time + "<br>";
                            content += "支付方式：" + log.pay_type_name + "<br>";
                            content += "商品信息：" + goods_str + "<br>";

                            content += "<br>";
                            if (type == 1) {
                                if (content != "") {
                                    $('#a_orderInfo' + rownumber).tooltip({
                                        position: 'bottom',
                                        content: '<span style="color:#fff">' + content + '</span>',
                                        onShow: function () {
                                            $(this).tooltip('tip').css({
                                                backgroundColor: '#666',
                                                borderColor: '#666'
                                            });
                                        }
                                    });
                                    $('#a_orderInfo' + rownumber).tooltip("show");
                                }
                            } else if (type == 2) {
                                if (content != "") {
                                    $('#a_send_orderInfo' + rownumber).tooltip({
                                        position: 'bottom',
                                        content: '<span style="color:#fff">' + content + '</span>',
                                        onShow: function () {
                                            $(this).tooltip('tip').css({
                                                backgroundColor: '#666',
                                                borderColor: '#666'
                                            });
                                        }
                                    });
                                    $('#a_send_orderInfo' + rownumber).tooltip("show");
                                }
                            }

                        }

                    }
                    else {
                        $.messager.alert("提示信息", "数据请求失败");
                    }
                }
            });
        }
        

        function login() {
            $("#dlg").dialog("open").dialog('setTitle', '登陆');
        }

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



        function elemhf(reminder_id) {
            $("#reminder_id").val(reminder_id);
            $("#dlg2").dialog("open").dialog('setTitle', '回复');
        }

        function reminder() {
            var text = $("#text").val();
            var reminder_id = $("#reminder_id").val();
            $.ajax({
                url: "/Reminder/reminderHf",
                data: {reminder_id:reminder_id,text:text},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $('#dlg2').dialog('close');
                        $('#List').datagrid('reload');
                        $.messager.show({
                            title:'提示信息',
                            msg:'操作成功',
                            showType:'show'
                        });

                    }else{
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }


        function elemLogin() {
//            $.ajax({
//                url: "/Reminder/validateLoginText",
//                data: null,
//                type: 'POST',
//                success: function (result) {
//                    if (result.status != "1") {
//                        //登陆失效
////                        $("#yzm").src(result.status);
//                        alert(result.status);
//                        $("#dlg").dialog("open").dialog('setTitle', '登陆');
//                    }
//                }
//            });

//            $('body').openWin({
//                url:'http://e.waimai.meituan.com',
//                width:'60%',
//                height:'60%',
//                title:'美团登陆'
//            });

            $("#dlg3").dialog("open").dialog('setTitle', '美团登陆');
        }


        function getCookie() {
            var cookie2 = document.getElementById('mt').contentWindow.document.cookie;
            console.log(cookie2);
            alert(cookie2);
        }




//        function loginMt() {
//            var image = $("#image3").val();
//            var login = $("#login").val();
//            var password = $("#password").val();
//            var captcha_v_token = $("#captcha_v_token").val();
//            $.ajax({
//                url: "/Reminder/MtText",
//                data: null,
//                type: 'POST',
//                success: function (result) {
//                    if (result.status == "1") {
//                        //登陆失效
//                        $.messager.show({
//                            title:'提示信息',
//                            msg:'登陆成功',
//                            showType:'show'
//                        });
//                    }else{
//                        $("#captcha_v_token").val(result.status);
//                        $.messager.alert("提示信息", "登陆失败");
//                    }
//                }
//            });
//        }

        function sxtp() {
             var captcha_v_token = $("#captcha_v_token").val();
             var timestamp = Date.parse(new Date());
            document.getElementById('yzm3').src="https://epassport.meituan.com/bizverify/captcha?verify_event=1&captcha_v_token="+captcha_v_token+"&"+timestamp;
        }

        




    </script>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;">

    <div data-options="region:'north'" style="height:30px">
        <h2 style="margin-left: 10px;margin-top: 5px;">
            催单管理</h2>
    </div>
    <div data-options="region:'center',title:'',iconCls:'icon-ok'">
        <div class="easyui-tabs" style="height: 100%" id="tabs">
            <div title="饿了么未处理">
                <table id="List"></table>
            </div>
            <div title="饿了么已处理">
                <table id="List2"></table>
            </div>
            <%--<div title="美团未处理" >--%>
                <%--<table id="List3"></table>--%>
            <%--</div>--%>
            <%--<div title="美团已处理">--%>
                <%--<table id="List4"></table>--%>
            <%--</div>--%>
        </div>

    </div>

    <div id="dlg" class="easyui-dialog" style="width: 500px; height: 150px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">
        <form id="fm" method="post">
            <div class="fitem">
                <label  class="mytitle">验证码:</label>
                <input name="image" id="image" class="easyui-validatebox" required="true" /><img id="yzm" src="">
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">登陆</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>


    <div id="dlg3" class="easyui-dialog" style="width: 70%;height: 75%"
         closed="true" buttons="#dlg-buttons3">
        <form id="fm3" method="post">
            <div class="fitem">
                <label  class="mytitle">cookie:</label>
                <textarea cols=60 rows=3 name=cookie id="cookie" class="textbox" class=" easyui-validatebox" required="true"></textarea>
            </div>
        </form>

    </div>
    <div id="dlg-buttons3">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="loginMt()" iconcls="icon-save">登陆</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg3').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>


    <div id="dlg2" class="easyui-dialog" style="width: 600px; height: 200px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons2">
        <form id="fm2" method="post">
            <div class="fitem">
                <label  class="mytitle">回复内容:</label>
                <textarea cols=60 rows=3 name=text id="text" class="textbox" class=" easyui-validatebox" required="true"></textarea>
                <input type="hidden" name="reminder_id" id="reminder_id">
            </div>
        </form>
    </div>
    <div id="dlg-buttons2">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="reminder()" iconcls="icon-save">回复</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg2').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
