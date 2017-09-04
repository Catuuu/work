<%--
  订单列表
  User: chenbin
  Date: 17-02-22
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>订单查询</title>
    <style>
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

        .l-btn-plain-selected, .l-btn-plain-selected:hover {
            background: #d84f4b none repeat scroll 0 0;
            color: #fff;
        }

        .l-btn-focus {
            outline: none;
        }

        .print-btn {
            line-height: 30px;
            cursor: pointer;
            background-color: #5bc0de;
            border-color: #46b8da;
            color: #fff;
            padding: 5px;
            border-radius: 3px;
            text-decoration: none;
        }

        .send-btn {
            line-height: 30px;
            cursor: pointer;
            background-color: #337ab7;
            border-color: #2e6da4;
            color: #fff;
            padding: 5px;
            border-radius: 3px;
            text-decoration: none;
        }

        .cancelsend-btn {
            line-height: 30px;
            cursor: pointer;
            background-color: #d9534f;
            border-color: #d43f3a;
            color: #fff;
            padding: 5px;
            border-radius: 3px;
            text-decoration: none;
        }

        a:hover {
            color: #1f637b;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        var cur_status = ${action};//1.预订单，2.打包异常，3.配送异常
        var title = "<a style ='float:right' href='javascript:void(0)' onclick='parent.ifrmid.src=\"/Client/home\"'>返回首页》</a>";
        if (cur_status == 1) {
            title = "今日预订单列表"+title;
        } else if (cur_status == 2) {
            title = "今日打包异常列表"+title;
        } else if (cur_status == 3) {
            title = "今日配送异常列表"+title;
        }

        var order_field = "order_no"; //排序字段
        $(function () {
            loaddata();
        });

        function loaddata() {
            //$("#selectForm").serializeSelectJson("tb1");
//            var queryParams = $("#selectForm").serializeJson();
           var queryParams = {
                cur_status:cur_status,
//                sl_date_begin:"2017-07-13"
            };
            $('#tb1').datagrid({
                toolbar: '#selectForm',
                title: title,
                fit: true,
                nowrap: false,
                striped: false,
                url: '/homeApi/exceptionOrderList',
                queryParams: queryParams,
                sortName: order_field,
                sortOrder: 'desc',
                idField: 'order_id',
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
                        field: 'price', title: '订单金额', width: 220, formatter: function (val, row) {
                        var str = '<div style="margin: 5px;">';
                        str += '<div>';
                        if (row.box_price != undefined && row.box_price != 0) {
                            str += '餐盒费：￥' + row.box_price;
                        }
                        if (row.ship_fee != undefined && row.ship_fee != 0) {
                            str += '&nbsp;&nbsp;&nbsp;配送费：￥' + row.ship_fee;
                        }
                        str += '</div>';

                        str += '<div>';

                        if (row.deduction_price != undefined && row.deduction_price != 0) {
                            str += '优惠金额：￥' + row.deduction_price;
                        }
                        if (row.uc_price != undefined && row.uc_price != 0) {
                            str += '&nbsp;&nbsp;&nbsp;红包金额：￥' + row.uc_price;
                        }
                        str += '</div>';

                        if (row.total_price != undefined && row.total_price != 0) {
                            str += '<div style="color:#9F35FF">用户支付金额：￥' + row.total_price + '</div>';
                        }
                        if (row.pay_type_name != undefined && row.pay_type_name != 0 && row.pay_type_name != '货到付款') {
                            str += '<div>付款方式：' + row.pay_type_name + '</div>';
                        } else if (row.pay_type_name != undefined && row.pay_type_name != 0 && row.pay_type_name == '货到付款') {
                            str += '<div  style="background:#d94600;color: #fff;padding: 2px;border-radius: 4px;">付款方式：' + row.pay_type_name + '</div>';
                        }
                        if (row.serviceFee != undefined && row.serviceFee != 0) {
                            str += '<div>平台服务费：￥' + row.serviceFee + '</div>';
                        }

                        str += '<div>';

                        if (row.platform_part != undefined && row.platform_part != 0) {
                            str += '平台承担：￥' + row.platform_part;
                        }
                        if (row.shop_part != undefined && row.shop_part != 0) {
                            str += '&nbsp;&nbsp;&nbsp;店铺承担：￥' + row.shop_part;
                        }
                        str += '</div>';

                        if (row.income != undefined && row.income != 0) {
                            str += '<div style="color:#9F35FF">店铺收入：￥' + row.income + '</div>';
                        }

                        str += "</div>"
                        return str;
                    }
                    },
                    {
                        field: 'pack_name', title: '打包信息', width: 120, formatter: function (val, row) {
                        var str = '<div style="margin: 5px;">';
                        if ((row.order_status == 1 || row.order_status == 2) && row.pack_time == null) {
                            str += '<div style="color:#2e6da4">等待：' + row.pack_wait_time + '(分钟)</div>';
                        }

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
                        var str = '<div style="margin: 5px;">';
                        if ((row.order_status == 1 || row.order_status == 2) && row.takemea_time == null) {
                            str += '<div style="color:#2e6da4">取餐等待：' + row.takemea_wait_time + '(分钟)</div>';
                        }
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
                            str += '<div>送达时间：' + row.use_send_time + '分钟</div>';
                            str += '<div>' + row.task_time + '</div>';
                        }
                        str += "</div>"
                        return str;
                    }
                    },

                    {
                        field: 'state', title: '订单状态', width: 140, formatter: function (val, row) {
                        var str = '<div style="margin: 5px;"> <div><a id="orderstatusview' + row.rownumber + '"  onclick="orderstatusview(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">';
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
                    {
                        field: 'opt', title: '操作', width: 150, formatter: function (val, row) {
                        var str = '<div style="margin: 5px;">';

                        str += '<div><a class="print-btn" onclick="print_order(\'' + row.order_id + '\')">打印客户小票</a></div>';


                        //str += '<div><a class="send-btn" onclick="sendDwdOrder(\'' + row.order_id + '\')">点我达配送</a></div>';

                        if (row.order_status != 4 && row.order_status != 99) {

                            if (row.is_sync == 0 && row.order_type != 2) {
                                //str += '<div><a class="send-btn" onclick="sendShOrder(\'' + row.order_id + '\')">生活半径配送</a></div>';
                                str += '<div><a class="send-btn" onclick="sendDwdOrder(\'' + row.order_id + '\')">点我达配送</a></div>';
                            } else if (row.is_sync == 1 && row.send_name == '生活半径') {
                                str += '<div><a class="send-btn" onclick="sendShOrder(\'' + row.order_id + '\')">生活半径配送</a></div>';
                            } else if (row.is_sync == 2 && row.send_name == '生活半径') {
                                str += '<div><a class="cancelsend-btn" onclick="cancelShsend(\'' + row.order_id + '\')" style="line-height: 30px;cursor:pointer;">取消生活半径</a></div>';
                            } else if (row.is_sync == 1 && row.send_name == '点我达') {
                                str += '<div><a class="send-btn" onclick="sendDwdOrder(\'' + row.order_id + '\')">点我达配送</a></div>';
                            } else if (row.is_sync == 2 && row.send_name == '点我达') {
                                str += '<div><a class="cancelsend-btn" onclick="cancelDwdsend(\'' + row.order_id + '\')" style="line-height: 30px;cursor:pointer;">取消点我达</a></div>';
                            }
                        }

//                        if (row.order_status != 99) {
//                            str += '<div><a class="cancelorder-btn" onclick="cancelorder(\'' + row.order_id + '\',\'' + row.order_no + '\')" style="line-height: 30px;cursor:pointer;">订单取消</a></div>';
//                        }

                        if (row.order_status == 99) {
                            if (row.is_sync == 2 && row.send_name == '生活半径') {
                                str += '<div><a class="cancelsend-btn" onclick="cancelShsend(\'' + row.order_id + '\')" style="line-height: 30px;cursor:pointer;">取消生活半径</a></div>';
                            } else if (row.is_sync == 2 && row.send_name == '点我达') {
                                str += '<div><a class="cancelsend-btn" onclick="cancelDwdsend(\'' + row.order_id + '\')" style="line-height: 30px;cursor:pointer;">取消点我达</a></div>';
                            }
                        }

                        str += "</div>"
                        return str;
                    }
                    }
                ]],
                pagination: true,
                singleSelect: true,
                rownumbers: true
            });
        }
        //查看配送日志
        function viewsend(order_id, rownumber) {
            $.ajax({
                url: "/orderSelectAPI/viewsendlog",
                type: 'POST',
                data: {order_id: order_id},
                success: function (result) {
                    if (result.status == "1") {
                        var content = "";
                        if (result.obj != null) {
                            for (var i = 0; i < result.obj.length; i++) {
                                var log = result.obj[i];
                                content += log.send_type;
                                if (log.opt_type == 0) {
                                    content += "(取消订单)"
                                } else if (log.opt_type == 1) {
                                    content += "(创建订单)"
                                } else {
                                    content += "(操作异常)"
                                }
                                content += "  " + log.opt_name;
                                content += "  " + log.opt_time;
                                if (log.opt_note != null) {
                                    content += "  " + log.opt_note;
                                }
                                if (log.opt_type == 1) {
                                    content += "  配送订单编号：" + log.send_id;
                                }
                                content += "<br>";
                            }
                        } else {
                            content = "暂无配送日志";
                        }
                        $('#viewsend' + rownumber).tooltip({
                            position: 'bottom',
                            content: '<span style="color:#fff">' + content + '</span>',
                            onShow: function () {
                                $(this).tooltip('tip').css({
                                    backgroundColor: '#666',
                                    borderColor: '#666'
                                });
                            }
                        });
                        $('#viewsend' + rownumber).tooltip("show");

                    }
                    else {
                        $.messager.alert("提示信息", "数据请求失败");
                    }
                }
            });
        }

        function toolmove(rownumber) {
            $('#orderstatusview' + rownumber).tooltip("hide");
        }


        //查看订单状态
        function orderstatusview(order_id, rownumber) {
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
                                $('#orderstatusview' + rownumber).tooltip({
                                    showEvent: 'dblclick',
                                    hideEvent: 'dblclick',
                                    position: 'bottom',
                                    content: '<span onmouseleave="toolmove(\'' + rownumber + '\')" style="color:#fff">' + content + '</span>',
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
        /**
         * 打印小票
         * @param order_id
         */
        function print_order(order_id) {
            top.$.messager.confirm('打印小票', '您确定要打印客户小票吗?', function (r) {
                if (r) {
                    $.postJSON("/orderSelectAPI/printOrder", {order_id: order_id}, function (jsonMessage) {
                        if (jsonMessage.status == 1) {
                            $("#tb1").datagrid("reload");
                        } else {
                            $.messageError("打印失败!");
                        }
                    })
                }
            });
        }
        /**
         * 发送生活半径配送
         * @param order_id
         */
        function sendShOrder(order_id) {
            top.$.messager.confirm('发送生活半径配送', '您确定要发送生活半径配送吗?', function (r) {
                if (r) {
                    $.postJSON("/orderSelectAPI/sendShOrder", {order_id: order_id}, function (jsonMessage) {
                        if (jsonMessage.status == 1) {
                            $("#tb1").datagrid("reload");
                        } else {
                            $.messageError("发送失败!" + jsonMessage.message);
                        }
                    })
                }
            });
        }
        /**
         * 发送点我达配送
         * @param order_id
         */
        function sendDwdOrder(order_id) {
            top.$.messager.confirm('发送点我达配送', '您确定要发送点我达配送吗?', function (r) {
                if (r) {
                    $.postJSON("/orderSelectAPI/sendDwdOrder", {order_id: order_id}, function (jsonMessage) {
                        if (jsonMessage.status == 1) {
                            $("#tb1").datagrid("reload");
                        } else {
                            $.messageError("发送失败!" + jsonMessage.message);
                        }
                    })
                }
            });
        }
        /**
         * 取消生活半径配送
         * @param order_id
         */
        function cancelShsend(order_id) {
            top.$.messager.confirm('取消生活半径配送', '您确定要取消生活半径配送吗?', function (r) {
                if (r) {
                    $.postJSON("/orderSelectAPI/cancelShsend", {order_id: order_id}, function (jsonMessage) {
                        if (jsonMessage.status == 1) {
                            $("#tb1").datagrid("reload");
                        } else {
                            $.messageError("发送失败!" + jsonMessage.message);
                        }
                    })
                }
            });
        }
        /**
         * 取消点我达配送
         * @param order_id
         */
        function cancelDwdsend(order_id) {
            top.$.messager.confirm('取消点我达配送', '您确定要取消点我达配送吗?', function (r) {
                if (r) {
                    $.postJSON("/orderSelectAPI/cancelDwdsend", {order_id: order_id}, function (jsonMessage) {
                        if (jsonMessage.status == 1) {
                            $("#tb1").datagrid("reload");
                        } else {
                            $.messageError("发送失败!" + jsonMessage.message);
                        }
                    })
                }
            });
        }
        /**
         * 取消订单弹窗
         * @param order_id
         */
        function cancelorder(order_id, order_no) {
            $("#order_id").val(order_id);
            $("#order_id_div").html(order_id);
            $("#order_no_div").html(order_no);
            $("#cancel_remark").textbox("setValue", "");
            $("input[type='radio'][name='cancel_type'][value='店铺太忙']").attr("checked", "checked");
            $("#ordercancel").dialog("open").dialog('setTitle', '取消订单');
        }

        //保存扫描枪信息
        function saveCancelorder() {
            $.ajax({
                url: "/orderSelectAPI/cancelorder",
                data: $("#fm").serialize(),
                type: 'POST',
                beforeSend: function () {
                    return $("#fm").form("validate");
                },
                success: function (result) {
                    if (result.status == "1") {
                        $.messageShow("操作成功");
                        // $.messager.alert("提示信息", "操作成功");
                        $("#ordercancel").dialog("close");
                        $("#tb1").datagrid("reload");
                    }
                    else {
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }


    </script>
</head>
<body>
<div class="bodymain">

    <%--<div id="selectForm" name="selectForm">--%>
        <%--<input type="hidden" name="cur_status" id="cur_status" value="">--%>
        <%--<input type="hidden" name="brand_id" id="brand_id" value="">--%>
        <%--<div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">--%>
            <%--<b>品牌：</b>--%>
            <%--<a href="###" data-status="0" class="easyui-linkbutton  orderbrandid"--%>
            <%--data-options="toggle:true,group:'g4',plain:true">全部</a>--%>
            <%--<c:forEach items="${cdsbrands}" var="item">--%>
            <%--<a href="###" data-status="${item.brand_id}" class="easyui-linkbutton  orderbrandid"--%>
            <%--data-options="toggle:true,group:'g4',plain:true">${item.brand_name}</a>--%>
            <%--</c:forEach>--%>
            <%--&nbsp;&nbsp;&nbsp;--%>

            <%--<b>下单时间：</b><input class="Wdate" type="text" id="sl_date_begin" name="sl_date_begin"--%>
            <%--onClick="WdatePicker({maxDate:'#F{$dp.$D(\'sl_date_end\')}'})" value="${curday}">--%>
            <%--至 <input class="Wdate" type="text" id="sl_date_end" name="sl_date_end"--%>
            <%--onClick="WdatePicker({minDate:'#F{$dp.$D(\'sl_date_begin\')}',maxDate:'${curday}'})"--%>
            <%--value="${curday}">--%>
        <%--</div>--%>
    <%--</div>--%>

    <div style="padding:5px;background:#fff;position:absolute;top:0px;left:0px;right:0px;bottom:0px;">
        <table id="tb1" style="width:100%;height:100%"></table>
    </div>

    <div id="ordercancel" class="easyui-dialog" style="width: 500px; height: 400px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem">
                <label class="mytitle">订单流水号</label>
                <span id="order_no_div" style="font-weight: 800"></span>
            </div>

            <div class="fitem">
                <label class="mytitle">订单编号</label>
                <span id="order_id_div" style="font-weight: 800"></span>
            </div>

            <div class="fitem">

                <div>
                    <label><input type="radio" name="cancel_type" value="店铺太忙">&nbsp;店铺太忙</label>
                    <label><input type="radio" name="cancel_type" value="商品已售完">&nbsp;商品已售完</label>
                    <label><input type="radio" name="cancel_type" value="地址无法配送">&nbsp;地址无法配送</label>
                    <label><input type="radio" name="cancel_type" value="店铺已打烊">&nbsp;店铺已打烊</label>
                    <label><input type="radio" name="cancel_type" value="联系不上用户">&nbsp;联系不上用户</label>
                    <label><input type="radio" name="cancel_type" value="重复订单">&nbsp;重复订单</label>
                    <label><input type="radio" name="cancel_type" value="配送员取餐慢">&nbsp;配送员取餐慢</label>
                    <label><input type="radio" name="cancel_type" value="配送员送餐慢">&nbsp;配送员送餐慢</label>
                    <label style="width: 204px;"><input type="radio" name="cancel_type" value="配送员丢餐、少餐、餐洒">&nbsp;配送员丢餐、少餐、餐洒</label>
                    <label><input type="radio" name="cancel_type" value="其他原因">&nbsp;其他原因</label>
                </div>
            </div>

            <div class="fitem printcf">
                <label class="mytitle">取消说明</label>
                <input class="easyui-textbox" data-options="multiline:true" value="123" style="width:285px;height:100px"
                       id="cancel_remark" name="cancel_remark">
            </div>
            <input type="hidden" name="order_id" id="order_id" value="0"/>
        </form>
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveCancelorder()" iconcls="icon-save">确认取消</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#ordercancel').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>
</div>
</body>
</html>
