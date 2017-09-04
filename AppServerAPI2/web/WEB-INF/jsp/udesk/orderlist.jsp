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


        var order_field = "create_date"; //排序字段
        $(function () {
            //查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("tb1");
            });
            loaddata();
        });


        function loaddata() {
            var queryParams = $("#selectForm").serializeJson();
            var window_width = $(window).width();
            if (window_width < 400) {

                $('#tb1').datagrid({
                    toolbar: '#selectForm',
                    title: '订单查询',
                    nowrap: false,
                    striped: false,
                    url: '/udeskAPI/orderlist',
                    queryParams: queryParams,
                    sortName: order_field,
                    rownumbers: false,
                    sortOrder: 'desc',
                    idField: 'order_id',
                    columns: [[{
                        field: 'fromin_no',
                        title: '订单信息',
                        sortable: true,
                        width: window_width - 25,
                        formatter: function (val, row) {
                            var str ="<div style='border: 2px solid #2e6da4;'>"
                            //订单流水号及来源
                             str+= "<div style='height: 30px;background: #2e6da4;width: 100%;color: #fff;'>";

                            str += '<div style="float: left;height: 30px;line-height: 30px;margin-left:3px;"><b>' + row.order_no + '</b>';
                            var fromin = row.fromin;
                            if (row.fromin_name != null && row.fromin_name != "") {
                                fromin += ":" + row.fromin_name;
                            }


                            var orderstatus_msg= '<a style="color: #FFF;" id="orderstatusview' + row.rownumber + '"  onclick="orderstatusview(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">';
                            if (row.order_status == 0) {
                                orderstatus_msg += '商家待接单';
                            } else if (row.order_status == 1 || row.order_status == 2) {
                                if (row.pack_user_time == undefined) {
                                    orderstatus_msg += '待打包';
                                } else {
                                    orderstatus_msg += '已打包';
                                }
                            } else if (row.order_status == 3) {
                                orderstatus_msg += '配送中';
                            } else if (row.order_status == 4) {
                                orderstatus_msg += '已完成';
                            } else if (row.order_status == 99) {
                                orderstatus_msg += '订单已取消';
                            }
                            orderstatus_msg += '</a>';

                            str += '('+orderstatus_msg+")</div>";

                            str += '<div style="float: right;height: 30px;line-height: 30px;margin-right:3px;">'+ fromin +'#' + row.fromin_no + '</div>';
                            str += "</div>";



                             str += '<div style="margin: 5px">';
                            //订单商铺及下单时间及就餐方式
                            var order_type_msg = '<div  style="color:#d94600;float: right">店堂就餐</div>';
                            if (row.order_type == 1) {
                                order_type_msg = '<div  style="color:#008200;float: right">送货到门</div>';
                            }
                            str += '<div style="color:#345645;font-weight: 900">' + row.stores_name + '(' + row.create_date + ')' + order_type_msg + '</div>';

                            //收货信息及是否预订单
                            var service_time_msg = '';
                            if (row.service_time != undefined && row.service_time != '') {
                                if (row.service_time_str == undefined) {
                                    service_time_msg = '<div  style="background:#d94600;color: #fff;padding: 2px;border-radius: 4px;float: right">预订单</div>';
                                } else {
                                    service_time_msg = '<div  style="background:#d94600;color: #fff;padding: 2px;border-radius: 4px;float: right">预订单：' + row.service_time_str + '</div>';
                                }
                            }
                            str += '<div style="margin-top: 5px;"><b>' + row.receiver_name + ' ' + row.receiver_phone + service_time_msg+'</b></div>';
                            if (row.receiver_adress != undefined) {
                                str += '<div>' + row.receiver_adress + '</div>';
                            }
                            if (row.member_desc != undefined && row.member_desc != '') {
                                str += '<div  style="background:#718BB7;color: #fff;padding: 2px;border-radius: 4px;">客户备注：' + row.member_desc + '</div>';
                            }
                            str += "</div>"

                            //订单商品信息
                            if (row.goods != undefined && row.goods != "") {
                                str +='<div style="border-radius: 2px;border:1px solid #ffc606;padding: 5px;margin: 5px;">';
                                var goods = JSON.parse(row.goods);
                                var good_count = 0;
                                for (var i = 0; i < goods.length; i++) {
                                    var good = goods[i];
                                    str += "[" + good.good_name + ":" + good.quantity + "份 ￥" + good.price + "]<br>";
                                    good_count += good.quantity;
                                }
                                str += '<div style="color:#9F35FF">共' + good_count + '份,合计：￥' + row.goods_prcie + '<div style="color:#9F35FF;float: right">支付金额：￥' + row.total_price + '</div></div>';
                                str += "</div>"
                            }

                            //其它金额信息
                            str += '<div style="margin: 5px">';
                            if (row.box_price != undefined && row.box_price != 0) {
                                str += '餐盒费：￥' + row.box_price;
                            }
                            if (row.ship_fee != undefined && row.ship_fee != 0) {
                                str += '&nbsp;&nbsp;&nbsp;配送费：￥' + row.ship_fee;
                            }


                            if (row.deduction_price != undefined && row.deduction_price != 0) {
                                str += '&nbsp;&nbsp;&nbsp;优惠金额：￥' + row.deduction_price;
                            }
                            if (row.uc_price != undefined && row.uc_price != 0) {
                                str += '&nbsp;&nbsp;&nbsp;红包金额：￥' + row.uc_price;
                            }
                            str += '</div>';


                            if (row.pay_type_name != undefined && row.pay_type_name != 0 && row.pay_type_name != '货到付款') {
                                str += '<div style="margin: 5px;">付款方式：' + row.pay_type_name;
                            } else if (row.pay_type_name != undefined && row.pay_type_name != 0 && row.pay_type_name == '货到付款') {
                                str += '<div  style="background:#d94600;color: #fff;padding: 2px;border-radius: 4px;">付款方式：' + row.pay_type_name;
                            }
                            if (row.serviceFee != undefined && row.serviceFee != 0) {
                                str += '<div style="float: right">平台服务费：￥' + row.serviceFee + '</div>';
                            }
                            str += '<div style="margin: 5px;">&nbsp;';
                            if (row.platform_part != undefined && row.platform_part != 0) {
                                str += '平台承担：￥' + row.platform_part;
                            }
                            if (row.shop_part != undefined && row.shop_part != 0) {
                                str += '&nbsp;&nbsp;&nbsp;店铺承担：￥' + row.shop_part;
                            }
                            if (row.income != undefined && row.income != 0) {
                                str += '<div style="float: right">店铺收入：￥' + row.income + '</div>';
                            }

                            //打包信息
                            str += '<hr>';
                            str += '<div style="margin: 5px;">';
                            if ((row.order_status == 1 || row.order_status == 2) && row.pack_time == null) {
                                str += '<div style="color:#2e6da4">打包等待：' + row.pack_wait_time + '(分钟)</div>';
                            }

                            if (row.pack_user_name != null) {
                                str += '<div>打包员：' + row.pack_user_name + '&nbsp;&nbsp;'+row.pack_user_time +'&nbsp;&nbsp;用时：' + row.pack_time + '分钟</div>';
                            }
                            str += "</div>"
                            //配送信息
                            str += '<hr>';
                            str += '<div style="margin: 5px;">';
                            if ((row.order_status == 1 || row.order_status == 2) && row.takemea_time == null) {
                                str += '<div style="color:#2e6da4">取餐等待：' + row.takemea_wait_time + '(分钟)</div>';
                            }
                            if (row.takemea_time != null) {
                                str += '<div>取餐用时：' + row.takemea_time + '分钟</div>';
                            }

                            if (row.is_sync == 1) {
                                str += '<div><a id="viewsend' + row.rownumber + '"  onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '<span style="color: red;padding: 5px;">（未发送）</span></a></div>';

                            } else if (row.is_sync == 2) {
                                str += '<div><a id="viewsend' + row.rownumber + '" onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '</a></div>';
                                str += '<div>已发送：' + row.send_time + '</div>';
                                str += '<div>配送sn：' + row.send_id + '</div>';
                            } else if (row.send_name != undefined && row.send_name != "") {
                                str += '<div><a id="viewsend' + row.rownumber + '" onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '</a></div>';
                            } else {
                                str += '<div>配送方式：未指定</div>';
                            }

                            if (row.send_exception != undefined && row.send_exception != "") {
                                str += '<div style="color:red">配送异常：' + row.send_exception + '</div>';
                                str += '<div>配送sn：' + row.send_id + '</div>';
                            }

                            str += '<div style="height: 60px;">';

                            if (row.task_order_name != null) {
                                str += '<div style="float: left;width: 48%;">';
                                str += '<div><b>接单员：' + row.task_order_name + '</b></div>';
                                str += '<div><b>tel：' + row.task_order_phone + '</b></div>';
                                str += '<div><b>' + row.task_order_time + '</b></div>';
                                str += "</div>"
                            }
                            if (row.task_user_name != null) {
                                str += '<div style="float: right;width: 48%;">';
                                str += '<div><b>配送员：' + row.task_user_name + '</b></div>';
                                str += '<div><b>tel：' + row.task_user_phone + '</b></div>';
                                str += '<div><b>' + row.task_user_time + '</b></div>';
                                str += "</div>"
                            }

                            str += "</div>"
                            if (row.order_status == 3) {
                                str += '<div style="color:#2e6da4">配送已用时间：' + row.use_send_wait_time + '(分钟)</div>';
                            }
                            if (row.task_time != null) {
                                str += '<div>送达时间：' + row.use_send_time + '分钟&nbsp;&nbsp;'+ row.task_time +'</div>';
                            }



                            //结束div
                            str += '</div>';
                            return str;
                        }
                    }]],
                    rowStyler: function (rowIndex, rowData) {
                        if (rowData && rowData.order_status == 99) {
                            return 'background-color:#F4F4F4;color:#CDC5BF';
                        }
                    },
                    pagination: true,
                    singleSelect: true
                });
                return;
            }
            $('#tb1').datagrid({
                toolbar: '#selectForm',
                title: '订单查询',
                fit: true,
                nowrap: false,
                striped: false,
                url: '/udeskAPI/orderlist',
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
                            str += "[" + good.good_name + ":" + good.quantity + "份 ￥" + good.price + "]<br>";
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
                            str += '<div><a id="viewsend' + row.rownumber + '"  onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '<span style="color: red;padding: 5px;">（未发送）</span></a></div>';

                        } else if (row.is_sync == 2) {
                            str += '<div><a id="viewsend' + row.rownumber + '" onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '</a></div>';
                            str += '<div>已发送：' + row.send_time + '</div>';
                            str += '<div>配送sn：' + row.send_id + '</div>';
                        } else if (row.send_name != undefined && row.send_name != "") {
                            str += '<div><a id="viewsend' + row.rownumber + '" onclick="viewsend(\'' + row.order_id + '\',' + row.rownumber + ')" href="javascript:void(0)">配送方式：' + row.send_name + '</a></div>';
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
                                    position: 'bottom',
                                    content: '<span style="color:#fff">' + content + '</span>',
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
    <div id="selectForm" name="selectForm">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">
            &nbsp;&nbsp;&nbsp;
            <b>下单时间：</b><input class="Wdate" type="text" id="sl_date_begin" name="sl_date_begin"
                               onClick="WdatePicker({onpicked:loaddata,maxDate:'#F{$dp.$D(\'sl_date_end\')}'})"
                               value="${startday}">
            至 <input class="Wdate" type="text" id="sl_date_end" name="sl_date_end"
                     onClick="WdatePicker({onpicked:loaddata,minDate:'#F{$dp.$D(\'sl_date_begin\')}',maxDate:'${curday}'})"
                     value="${curday}">

        </div>
        <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">
            <input class="easyui-textbox" name="keywords" style="width:280px"
                   data-options="label:'关键词：',prompt:'输入流水号、姓名、电话、地址...'" value="${mobile}">

            <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13"
               style="display: none;">查询</a>
            <%--<a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>--%>

        </div>
    </div>
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
