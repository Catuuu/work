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
<script type="text/javascript" src="/static/lib/crypto1/crypto/crypto.js"></script>
<script type="text/javascript" src="/static/lib/crypto1/hmac/hmac.js"></script>
<script type="text/javascript" src="/static/lib/crypto1/sha1/sha1.js"></script>
<script type="text/javascript" src="/static/lib/base64.js"></script>
<script type="text/javascript" src="/static/lib/plupload-2.1.2/js/plupload.full.min.js"></script>
<script type="text/javascript" src="/static/lib/gift_upload.js"></script>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>订单查询</title>
    <style>
        .gift-delbtn {
            line-height: 30px;
            cursor: pointer;
            background-color: #ed3931;
            border-color: #da2e27;
            color: #fff;
            padding: 5px;
            border-radius: 3px;
            text-decoration: none;
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
        //var cur_status = 2;//当前的订单状态
        var cur_status = 0;//当前的订单状态
        var cur_fromin = 0;//当前的订单来源
        var brand_id = 0; //品牌id
        var remark = 0;//是否备注
        var order_field = "order_no"; //排序字段
        var lastIndex;
        var pic_address = "";
        var pic_address_list = new Array();//图片地址数组
        $(function () {
            //头部关键词搜索
            var keyword = '${keyword}';
            if(keyword) {
                $("#keywords").textbox("setValue", keyword);
                loaddata();
            }
            //查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("tb1");
            });
            //清空查询
            $("#refreshBtn").click(function () {
                /* $("#cur_status").val(cur_status);
                 $("#cur_fromin").val(cur_fromin);
                 $("#selectForm").serializeSelectClear('tb1');*/
                window.location.reload();
            });

            $(".orderstatus[data-status=" + cur_status + "]").linkbutton({
                iconCls: 'icon-cur',
                selected: true
            });
            $(".orderbrandid[data-status=" + brand_id + "]").linkbutton({
                iconCls: 'icon-cur',
                selected: true
            });

            $(".orderfromin[data-status=" + cur_fromin + "]").linkbutton({
                iconCls: 'icon-cur',
                selected: true
            });

            $(".remark[data-status=" + remark + "]").linkbutton({
                iconCls: 'icon-cur',
                selected: true
            });

            $(".orderstatus").click(function () {
                $(".orderstatus").each(function () {
                    $(this).linkbutton({
                        iconCls: ''
                    });
                });
                $(this).linkbutton({
                    iconCls: 'icon-cur'
                });
                cur_status = $(this).data("status");
                loaddata();
            });

            $(".orderbrandid").click(function () {
                $(".orderbrandid").each(function () {
                    $(this).linkbutton({
                        iconCls: ''
                    });
                });
                $(this).linkbutton({
                    iconCls: 'icon-cur'
                });
                brand_id = $(this).data("status");
                loaddata();
            });

            $(".orderfromin").click(function () {
                $(".orderfromin").each(function () {
                    $(this).linkbutton({
                        iconCls: ''
                    });
                });
                $(this).linkbutton({
                    iconCls: 'icon-cur'
                });
                cur_fromin = $(this).data("status");
                if (cur_fromin == 0) {
                    order_field = "order_no";
                } else {
                    order_field = "fromin_no";
                }
                loaddata();
            });

            $(".remark").click(function () {
                $(".remark").each(function () {
                    $(this).linkbutton({
                        iconCls: ''
                    });
                });
                $(this).linkbutton({
                    iconCls: 'icon-cur'
                });
                remark = $(this).data("status");
                loaddata();
            });

//            $("#shops").combobox({
//                onChange: function (n, o) {
//                    loaddata();
//                }
//            });

            $("#pay_type_id").combobox({
                onChange: function (n, o) {
                    loaddata();
                }
            });
            $("#isprint").combobox({
                onChange: function (n, o) {
                    loaddata();
                }
            });
            $("#order_type").combobox({
                onChange: function (n, o) {
                    loaddata();
                }
            });
            $("#send_name").combobox({
                onChange: function (n, o) {
                    loaddata();
                }
            });
            loaddata();


        });

        function loaddata() {


            $("#cur_status").val(cur_status);
            $("#cur_fromin").val(cur_fromin);
            $("#remark").val(remark);
            $("#brand_id").val(brand_id);
            //$("#selectForm").serializeSelectJson("tb1");
            var queryParams = $("#selectForm").serializeJson();

            $('#tb1').datagrid({
                toolbar: '#selectForm',
                title: '订单查询',
                fit: true,
                nowrap: false,
                striped: false,
                url: '/GiftApi/orderlist',
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


//                        str += '<div><a id="gift_status' + row.rownumber + '"  onclick="giftstatusview(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">';
//                        if (row.send_type == 99) {
//                            str += '赠品状态：未登记';
//                        } else if (row.send_type == 1) {
//                            str += '赠品状态：已赠送';
//                        } else if (row.send_type == 0) {
//                            str += '赠品状态：未赠送';
//                        }
//                        str += '</a></div>';


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
                        str += '<div><a class="gift-btn" onclick="registerGift(\'' + row.order_id + '\',\'' + row.receiver_name + '\',\'' + row.receiver_phone + '\',\'' + row.brand_name + '\',\'' + row.brand_id + '\')">赠品登记</a></div>';


//                        str += '<div><a class="send-btn" onclick="sendDwdOrder(\'' + row.order_id + '\')">点我达配送</a></div>';

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

                        if (row.order_status != 99) {
                            str += '<div><a class="cancelorder-btn" onclick="cancelorder(\'' + row.order_id + '\',\'' + row.order_no + '\')" style="line-height: 30px;cursor:pointer;">订单取消</a></div>';
                        }

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

        //订单赠品登记

        function registerGift(order_id, order_name, order_phone, order_brand, brand_id) {
            var data1 = new Array();
            $("#imgs").empty();
            pic_address_list = new Array();
            //清空赠品联动
            var data2 = [];
            $('#giftInfo').datagrid({
                idField: 'good_id',
                textField: 'good_name',
                striped: true, //奇偶行是否区分
                data:data2,
                pagination: true,
                singleSelect: false,
                pagination: false,
                columns: [[

                ]]
            });

            //联动显示赠品信息
            $("#giftInfoSelect").datagrid({
                onClickRow: function (index, row) {
                    data1.push(row);

                    $('#giftInfo').datagrid({
                        idField: 'good_id',
                        textField: 'good_name',
                        striped: true, //奇偶行是否区分
                        rownumbers: true,//行号
                        data: data1,
                        pagination: true,
                        singleSelect: false,
                        pagination: false,
                        onDblClickCell: function (rowIndex, field, value) {
                            $('#giftInfo').datagrid('beginEdit', rowIndex);
                            if (lastIndex != rowIndex) {
                                $('#giftInfo').datagrid('endEdit', lastIndex);
                                $('#giftInfo').datagrid('beginEdit', rowIndex);
                            }
                            lastIndex = rowIndex;
                        },
                        onClickCell: function (rowIndex, field, value) {
                            $('#giftInfo').datagrid('endEdit', rowIndex);
                        },

                        columns: [[
                            {field: 'good_name', title: '商品名称', width: '40%', align: 'center', sortable: true},
                            {
                                field: 'quantity', title: '数量', width: '30%', align: 'center', sortable: true,


                                formatter: function (value, row) {
                                    if (row.quantity == null) {
                                        row.quantity = 1;
                                    }
                                    var str = row.quantity;
                                    return str;
                                },
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        min: 0,
//                                precision: 2
                                    }
                                }

                            },
                            {
                                field: 'opt', title: '操作', width: '30%', align: 'center', sortable: true,
                                formatter: function (value, row, index) {
                                    var str = '';

                                    str += '<div><a class="gift-delbtn" onclick="delGiftInfo(\'' + index + '\',\'' + row.rownumber1 + '\')">删除</a></div>';

                                    return str;
                                }
                            },
                        ]]
                    });

                }
            });
            $('#giftInfoSelect').datagrid('unselectAll');

            //创建赠品选择列表
            $('#giftInfoSelect').datagrid({
                panelWidth: '40%',
                panelHeight: '40%',
                idField: 'good_id',
                textField: 'good_name',
                sortName: 'good_id',
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                url: '/GiftApi/GetErpGoodsLists',
                method: 'post',
                toolbar: '#selectForm1',
                pagination: true,
                multiple: true,
                singleSelect: true,
                queryParams: {
                    status: 1
                },
                columns: [[
                    {field: 'good_id', title: 'ID', width: '5%', align: 'center'},
                    {field: 'good_name', title: '商品名称', width: '47%', align: 'center', sortable: true},
                    {
                        field: 'class_id',
                        title: '品牌',
                        width: '15%',
                        sortable: true,
                        align: 'center',
                        formatter: function (value, row) {
                            <c:forEach var='item' items='${cdsbrands}'>
                            if (row.class_id == '${item.brand_id}') {
                                return '${item.brand_name}';
                            }
                            </c:forEach>
                        }
                    },
                    {field: 'good_price', title: '商品价格', width: '15%', align: 'center', sortable: true},
                    {
                        field: 'status',
                        title: '状态',
                        width: '15%',
                        align: 'center',
                        sortable: true,
                        formatter: function (value, row) {
                            if (value == 1) {
                                return '<span  style="color:green">上架</span>';
                            } else {
                                return '<span  style="color:red">下架</span>';
                            }
                        }

                    }

                ]]
            });


            //查询
            $("#findBtn1").click(function () {
                $("#selectForm1").serializeSelectJson("giftInfoSelect");
            });

            $("#refreshBtn1").click(function () {

                $(':input', '#selectForm1').not(':button,:submit,:reset').val('');
                //$(':input','#selectForm').not(':button,:submit,:reset') .removeAttr('checked');
                $('#status').combobox('setValue', '');
//                $("#class_id").get(0).options[0].selected = true;
                $('#class_id').combobox('setValue', "");
                $("#selectForm1").serializeSelectJson("giftInfoSelect");
            });

            $("#status").combobox({
                onChange: function (n, o) {
                    $("#selectForm1").serializeSelectJson("giftInfoSelect");
                }
            });

            $("#class_id").combobox({
                onChange: function (n, o) {
                    $("#selectForm1").serializeSelectJson("giftInfoSelect");
                }
            });

            $('#cc').combotree({

                onChange: function (n, o) {
                    $("#price").val("");
                    if (n == 31 || n == 21 || n == 26 || n == 41 || n == 42 || n == 43) {
                        $("#price").val(5);
                    } else if (n == 32) {
                        $("#price").val(20);
                    }


                    $("#selectForm1").serializeSelectJson("giftInfoSelect");
                    if (n == "12" || n == "23" || n == "24" || n == "25") {
                        getPic();
                    }
                },


                lines: true,
                panelWidth: 250,
                panelHeight: 330,
                data: [{
                    id: 10,
                    text: '打包错漏',
                    children: [{
                        id: 11,
                        text: '漏送',
                    }, {
                        id: 12,
                        text: '错送（图证）'
                    }]
                }, {
                    id: 20,
                    text: '餐品质量问题',
                    children: [{
                        id: 21,
                        text: '餐品口味问题（5元以下）'
                    }, {
                        id: 22,
                        text: '食材变质'
                    }, {
                        id: 23,
                        text: '有异物（图证）'
                    }, {
                        id: 24,
                        text: '份量问题（图证）'
                    }, {
                        id: 25,
                        text: '漏洒（图证）'
                    }, {
                        id: 26,
                        text: '保温问题（5元以下）'
                    }, {
                        id: 27,
                        text: '用餐后出现身体不适'
                    }
                    ]
                }, {
                    id: 30,
                    text: '配送问题',
                    children: [{
                        id: 31,
                        text: '60-90分钟（5元以下）'
                    }, {
                        id: 32,
                        text: '90-120分钟（20元以下）'
                    }]
                }, {
                    id: 40,
                    text: '其他原因',
                    children: [{
                        id: 41,
                        text: '配送人员服务投诉（5元以下）'
                    }, {
                        id: 42,
                        text: '客服人员服务投诉（5元以下）'
                    },{
                        id: 43,
                        text: '客服人员电话回访（5元以下）'
                    }]
                }],
                required: true
            });

            $("#gift_order_id_div").html(order_id);
            $("#gift_order_name_div").html(order_name);
            $("#gift_order_phone_div").html(order_phone);
            $("#gift_order_brand_div").html(order_brand);

            $("#gift_order_brand_id").val(brand_id);
            $("#gift_order_name").val(order_name);
            $("#gift_order_phone").val(order_phone);
            $("#gift_order_brand").val(order_brand);
            $("#gift_order_id").val(order_id);

            $("input[type='radio'][name='send_reason'][value='1打包错漏']").attr("checked", "checked");
            $("#giftRegister").dialog("open").dialog('setTitle', '登记赠品');

        }

        //提交赠品登记信息
        function saveGift() {
            var t = $('#cc').combotree('tree');	// 获取树对象
            var b = t.tree('getSelected');		// 获取选择的节点
            var n = b.id;

            if (n == "12" || n == "23" || n == "24" || n == "25") {
                if (pic_address == null || pic_address == "") {
                    $.messager.alert("消息提示", "该赠送需要上传图证");
                    return;
                }
            }

            top.$.messager.confirm('登记赠品', '核对<span style="color:#993222">赠品数量</span>后，确定登记吗?', function (r) {
                if (r) {
                    var data = $("#giftInfo").datagrid("getData").rows;
                    var goodsList = new Array();
                    for (var i in data) {
                        var goods = {
                            good_name: data[i].good_name,
                            good_id: data[i].good_id,
                            good_type: 2,
                            class_id: data[i].market_price,
                            price: data[i].market_price,
                            quantity: data[i].quantity

                        }
                        goodsList.push(goods);
                    }
                    var goods = JSON.stringify(goodsList).toString();

                    var dataParam = $("#gift_fm").serialize();

                    dataParam = dataParam + "&" + "goods=" + goods + "&" + "send_reason_sub=" + n;
                    dataParam = dataParam.replace("send_reason=" + n, "send_reason=" + (n+"").substring(0,1));

                    var pic_json = JSON.stringify(pic_address_list).toString();
                    dataParam = dataParam + "&" + "img=" + pic_json;

                    $.ajax({
                        url: "/GiftApi/save",
                        data: dataParam,
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {
                                $.messageShow("操作成功");
                                // $.messager.alert("提示信息", "操作成功");
                                $("#giftRegister").dialog("close");
                                $("#hand_giftRegister").dialog("close");
                                $("#tb1").datagrid("reload");
                            }
                            else {
                                $.messager.alert("消息提示", result.message);
                            }
                        }
                    });
                }
            });


        }
        //删除联动行
        function delGiftInfo(index, n) {
            $('#giftInfo').datagrid('deleteRow', index);
            var rows = $('#giftInfo').datagrid("getRows");
            $('#giftInfo').datagrid("loadData", rows);


        }
        //上传图片
        function getPic(status) {
            $('body').openWin({
                url: '/GiftApi/getPicAddress?status=' + status,
                width: '20%',
                height: '22%',
                title: '图片上传'
            });
        }
        function getAddress(address, status) {
            pic_address = address;
            pic_address_list.push(address);

            var img = "http://caidashi.oss-cn-hangzhou.aliyuncs.com/" + address;
            $("#imgs").prepend('<img src='+img+' height="50" width="50" alt="暂无图片"/>&nbsp;');

        }

    </script>
</head>
<body>
<div class="bodymain">
    <div id="selectForm" name="selectForm">
        <input type="hidden" name="cur_status" id="cur_status" value="">
        <input type="hidden" name="cur_fromin" id="cur_fromin" value="">
        <input type="hidden" name="remark" id="remark" value="">
        <input type="hidden" name="brand_id" id="brand_id" value="">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">
            <b>订单状态：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  orderstatus"
               data-options="toggle:true,group:'g2',plain:true">全部</a>
            <a href="###" data-status="1" class="easyui-linkbutton  orderstatus"
               data-options="toggle:true,group:'g2',plain:true">待接订单</a>
            <a href="###" data-status="2" class="easyui-linkbutton  orderstatus"
               data-options="toggle:true,group:'g2',plain:true">待打包</a>
            <a href="###" data-status="3" class="easyui-linkbutton  orderstatus"
               data-options="toggle:true,group:'g2',plain:true">已打包(待取餐)</a>
            <a href="###" data-status="4" class="easyui-linkbutton  orderstatus"
               data-options="toggle:true,group:'g2',plain:true">配送中</a>
            <a href="###" data-status="5" class="easyui-linkbutton  orderstatus"
               data-options="toggle:true,group:'g2',plain:true">已完成</a>
            <a href="###" data-status="6" class="easyui-linkbutton  orderstatus"
               data-options="toggle:true,group:'g2',plain:true">已取消</a>
            &nbsp;&nbsp;&nbsp;
            <b>品牌：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  orderbrandid"
               data-options="toggle:true,group:'g4',plain:true">全部</a>
            <c:forEach items="${cdsbrands}" var="item">
                <a href="###" data-status="${item.brand_id}" class="easyui-linkbutton  orderbrandid"
                   data-options="toggle:true,group:'g4',plain:true">${item.brand_name}</a>
            </c:forEach>
            &nbsp;&nbsp;&nbsp;
            <b>订单来源：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">全部</a>
            <a href="###" data-status="1" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">饿了么</a>
            <a href="###" data-status="2" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">美团</a>
            <a href="###" data-status="3" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">百度</a>
            <a href="###" data-status="4" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">微信</a>
            &nbsp;&nbsp;&nbsp;

            <b>备注：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  remark"
               data-options="toggle:true,group:'g5',plain:true">全部</a>
            <a href="###" data-status="1" class="easyui-linkbutton  remark"
               data-options="toggle:true,group:'g5',plain:true">有</a>
            <a href="###" data-status="2" class="easyui-linkbutton  remark"
               data-options="toggle:true,group:'g5',plain:true">无</a>
            &nbsp;&nbsp;&nbsp;

            <b>下单时间：</b><input class="Wdate" type="text" id="sl_date_begin" name="sl_date_begin"
                               onClick="WdatePicker({maxDate:'#F{$dp.$D(\'sl_date_end\')}'})" value="${curday}">
            至 <input class="Wdate" type="text" id="sl_date_end" name="sl_date_end"
                     onClick="WdatePicker({minDate:'#F{$dp.$D(\'sl_date_begin\')}',maxDate:'${curday}'})"
                     value="${curday}">

        </div>
        <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">
            <%--<select class="easyui-combobox" id="shops" name="stores_id" label="门店：" style="width:160px;"--%>
                    <%--data-options="editable:false,panelHeight:'auto'">--%>
                <%--<option value="">全部</option>--%>
                <%--<c:forEach items="${cdsshops}" var="item">--%>
                    <%--<option value="${item.stores_id}">${item.name}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>

            <select class="easyui-combobox" id="pay_type_id" name="pay_type_id" label="付款方式：" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'">
                <option value="">全部</option>
                <option value="1">微信支付</option>
                <option value="2">余额支付</option>
                <option value="3">货到付款</option>
                <option value="4">网银支付</option>
                <option value="5">现金支付</option>
                <option value="6">三方平台</option>
            </select>

            <select class="easyui-combobox" id="isprint" name="isprint" label="是否打印：" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'">
                <option value="">全部</option>
                <option value="1">已打印</option>
                <option value="0">未打印</option>
                <option value="2">取消打印</option>
            </select>

            <select class="easyui-combobox" id="order_type" name="order_type" label="就餐方式：" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'">
                <option value="">全部</option>
                <option value="1">送货到门</option>
                <option value="2">店堂就餐</option>
            </select>

            <select class="easyui-combobox" id="send_name" name="send_name" label="配送方式：" style="width:180px;"
                    data-options="editable:false,panelHeight:'auto'">
                <option value="">全部</option>
                <option value="饿了么">饿了么专送</option>
                <option value="美团">美团专送</option>
                <option value="百度外卖">百度专送</option>
                <option value="生活半径">生活半径</option>
                <option value="点我达">点我达</option>
                <option value="运速达">运速达</option>
            </select>

            <input class="easyui-textbox" id="keywords" name="keywords" style="width:280px"
                   data-options="label:'关键词：',prompt:'输入流水号、姓名、电话、地址...'">

            <input class="easyui-textbox" name="fromin_no" style="width:180px"
                   data-options="label:'来源流水号：',prompt:'输入来源流水号'">

            <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
            <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>

        </div>
    </div>
    <div style="padding:5px;background:#fff;position:absolute;top:0px;left:0px;right:0px;bottom:0px;">
        <table id="tb1" style="width:100%;height:100%"></table>
    </div>
    <%--取消订单对话框--%>
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

    <%--改.订单登记对话框--%>
    <div id="giftRegister" class="easyui-dialog" style="width:730px; height:800px; padding: 0px 0px;"
         closed="true" buttons="#dlg-buttons">
        <div id="gift-layout" class="easyui-layout" style="width:700px;height:720px;">
            <%--赠品信息面板--%>
            <div data-options="region:'east',title:'赠品信息',split:true" style="width:280px;">
                <div style="height: 200px">
                    <div id="giftInfo"></div>
                </div>
                <div style="padding-top:30px;height:0px;">
                    <div id="imgs">
                    </div>
                </div>
            </div>
            <%--基本信息面板--%>
            <div data-options="region:'center',title:'基本信息'" style="padding:5px;background:#eee;">
                <form id="gift_fm" method="post">
                    <div class="fitem">
                        <label class="mytitle">品牌</label>
                        <span id="gift_order_brand_div" name="brand_name" style="font-weight: 800"></span>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">订单编号</label>
                        <span id="gift_order_id_div" name="order_id" style="font-weight: 800"></span>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">顾客姓名</label>
                        <span id="gift_order_name_div" name="name" style="font-weight: 800"></span>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">顾客手机号</label>
                        <span id="gift_order_phone_div" name="phone" style="font-weight: 800"></span>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">赠送原因：</label>
                        <%--赠送原因二级分类树形下拉框--%>
                        <input id="cc" style="width: 250px;height: 30px" name="send_reason"></input>
                        <a id="upload"  href="javascript:void(0)" onclick="getPic(0)" class="easyui-linkbutton " iconCls="icon-upload"></a>
                    </div>


                    <div id="selectForm1" name="selectForm1" style="height: 50px">
                        <input type="hidden" name="market_price" id="price" value="">
                        <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
                            <div style="float: left;padding-left: 5px">
                                <span>品牌:</span>
                                <select class="easyui-combobox easyui-validatebox" id="class_id" name="class_id"
                                        style="width:100px;"
                                        data-options="editable:false,panelHeight:'auto'" required="true">
                                    <option value="" selected="true">全部</option>
                                    <c:forEach var="item" items="${cdsbrands}">
                                        <option value="${item.brand_id}">${item.brand_name}</option>
                                    </c:forEach>
                                </select>
                                <span style="padding-left: 15px">是否上架:</span>
                                <select class="easyui-combobox easyui-validatebox" id="status" name="status"
                                        style="width:60px;"
                                        data-options="editable:false,panelHeight:'auto'" required="true">
                                    <option value="">全部</option>
                                    <option value="1" selected="true">是</option>
                                    <option value="0">否</option>
                                </select>
                                <span style="padding-left: 15px">商品名称:</span>
                                <input class="easyui-textbox" name="good_name" id="good_name" style="width:160px"
                                       data-options="prompt:'商品名称模糊查询'">
                                <a href="###" id="findBtn1" class="easyui-linkbutton " iconCls="icon-search"
                                   enterkey="13">查询</a>
                                <a href="###" id="refreshBtn1" class="easyui-linkbutton"
                                   iconCls="icon-reload">清空</a>
                            </div>
                        </div>
                    </div>


                    <input type="hidden" name="order_id" id="gift_order_id" value="0"/>
                    <input type="hidden" name="brand_id" id="gift_order_brand_id" value="0"/>

                    <input type="hidden" name="name" id="gift_order_name" value="0"/>
                    <input type="hidden" name="phone" id="gift_order_phone" value="0"/>
                    <%--<input type="hidden" name="brand_name" id="gift_order_brand_id" value="0"/>--%>
                </form>
            </div>

            <%--赠品选择面板--%>
            <div data-options="region:'south',title:'选择赠品',split:true" style="height:400px;">
                <div id="giftInfoSelect"></div>
            </div>
        </div>


    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveGift()" iconcls="icon-save">确认登记</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#giftRegister').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
