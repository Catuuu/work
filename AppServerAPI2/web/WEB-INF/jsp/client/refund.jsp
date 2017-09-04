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
<script type="text/javascript" src="/static/common/ui_control.js"></script>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>退款处理</title>
    <style>
        /* 选定的链接 */
        a.a_order_id:link {
            color: #da2e27
        }

        /* 未访问的链接 */
        a.a_order_id:visited {
            color: #da2e27
        }

        /* 已访问的链接 */
        a.a_order_id:hover {
            color: #36a854
        }

        /* 鼠标移动到链接上 */
        a.a_order_id:active {
            color: #0000FF
        }

        /* 选定的链接 */
        a.a_send_order_id:link {
            color: #36a854
        }

        /* 未访问的链接 */
        a.a_send_order_id:visited {
            color: #36a854
        }

        /* 已访问的链接 */
        a.a_send_order_id:hover {
            color: #da2e27
        }

        /* 鼠标移动到链接上 */
        a.a_send_order_id:active {
            color: #0000FF
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
            font-weight: 800;
            /*font-weight: lighter;*/
        }

        .l-btn-plain-selected, .l-btn-plain-selected:hover {
            background: #d84f4b none repeat scroll 0 0;
            color: #fff;
        }

        .l-btn-focus {
            outline: none;
        }

        .gift-upbtn {
            line-height: 30px;
            cursor: pointer;
            background-color: #cc4715;
            border-color: #cc4715;
            color: #fff;
            padding: 5px;
            border-radius: 3px;
            text-decoration: none;
        }

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
        $(function () {
            //1.选择.品牌
//            $(".orderbrandid").UI_tab({'name': 'brand_id'});

            //2.选择.退款状态
            $(".backStatus").UI_tab({'name': 'back_status'});
            //3.选择.平台
            $(".orderfromin").UI_tab({'name': 'cur_fromin'});
            //4.选择.门店

            $("#shops").combobox({
                onChange: function (n, o) {
                    loaddata();
                }
            });
            //5.选择.是否处理
            $(".dostatus").UI_tab({'name': 'do_status'});

            //查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("tb1");
            });
            //刷新
            $("#refreshBtn").click(function () {
                window.location.reload();
            });

            loaddata();

        });
        //加载页面数据
        function loaddata() {
            var queryParams = $("#selectForm").serializeJson();

            $('#tb1').datagrid({
                toolbar: '#selectForm',
                title: '退单处理',
                fit: true,
                nowrap: false,
                striped: false,
                url: '/BackMoneyApi/backlist',//TODO 测试用******************************
                method: 'POST',
                queryParams: queryParams,
                sortName: 'bp_time',
                sortOrder: 'desc',
                idField: 'bp_id',
                columns: [[
                    {field: 'stores_name', title: '店铺', width: '5%', align: 'center'},
                    {
                        field: 'bp_time', title: '操作时间', width: '8%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="font-weight: bold">';
                            str += '<br>' + row.bp_time + '</div>';
                            return str;
                        }
                    },
                    {
                        field: 'fromin', title: '平台', width: '5%', align: 'center',
                        formatter: function (val, row) {
                            var str = row.fromin + "#" + row.fromin_no;
                            return str;
                        }
                    },
                    {
                        field: 'order_id', title: '退款订单', width: '16%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: left">';
                            str += "流水号：" + row.order_no + "<br>";
                            str += "平台订单号:" + row.order_desc + "<br>";
                            str += "订单号：" + row.order_id + '<a href="javascript:void(0)" id="a_orderInfo' + row.rownumber + '" class="a_send_order_id" onclick = "showOrderInfo(\'' + row.order_id + '\',\'' + row.rownumber + '\')">（点击查看）<a/><br>';
                            str += "</div>";
                            return str;
                        }
                    },
                    {field: 'cancel_type', title: '退款原因', width: '8%', align: 'center'},
                    {
                        field: 'send_type', title: '订单物流状态', width: '8%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: left">';
                            str += '订单：';
                            if (row.order_status == 0) {
                                str += '商家待接单';
                            } else if (row.order_status == 1 || row.order_status == 2) {
                                if (row.pack_user_time == undefined) {
                                    str += '待打包';
                                } else {
                                    str += '已打包';
                                }
                            } else if (row.order_status == 3) {
                                str += '配送中';
                            } else if (row.order_status == 4) {
                                str += '已完成';
                            } else if (row.order_status == 99) {
                                str += '已取消';
                            }

                            if (row.send_type == undefined) {
                                row.send_type = "";
                            }
                            if (row.task_user_name != null) {
                                str += '<br>' + '配送员：' + row.task_user_name;
                            }
                            if (row.task_user_phone != null) {
                                str += '<br>' + '配送电话：' + row.task_user_phone;
                            }
//                            str += '<br>' + row.send_type;
                            str += '</div>';
                            return str;
                        }
                    },
                    {field: 'bp_remark', title: '申请备注', width: '8%', align: 'center'},
//                    {field: 'bp_check_remark', title: '审核备注', width: '8%', align: 'center'},
                    {
                        field: 'takemea_time', title: '出餐时长(分钟)', width: '7%', align: 'center',
                        formatter: function (val, row) {
                            var str = row.takemea_time;
                            if (row.takemea_time == null) {
                                str = '未知';
                            }
                            return str;
                        }
                    },
                    {
                        field: 'pack_time', title: '打包时长(分钟)', width: '7%', align: 'center',
                        formatter: function (val, row) {
                            var str = row.pack_time;
                            if (row.pack_time == null) {
                                str = '未知';
                            }
                            return str;
                        }
                    },
                    {
                        field: 'use_send_time', title: '配送时长(分钟)', width: '7%', align: 'center',
                        formatter: function (val, row) {
                            var str = row.use_send_time;
                            if (row.use_send_time == null) {
                                str = '未知';
                            }
                            return str;
                        }
                    },
                    {
                        field: 'back_status', title: '退款状态', width: '5%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;">';
                            var action = "";
                            if (row.do_status == 1) {
                                if (row.back_status == 2 || row.back_status == 1 || row.back_status == 6 || row.back_status == 7) {
                                    str += '退单成功';
                                } else if (row.back_status == 3) {
                                    str += '退单驳回';
                                } else if (row.back_status == 4) {
                                    str += '退单仲裁中';
                                } else if (row.back_status == 5) {
                                    str += '退单失败';
                                }
                            } else if (row.do_status == 0 || row.do_status == null) {
                                if (row.back_status == 1 && row.fromin == '美团') {
                                    if (row.order_status == 99) {
                                        str += '退单成功';
                                    } else {
                                        str += '退单申请';
                                    }
                                } else if ((row.back_status == 1 && row.fromin == '饿了么') || row.back_status == 2) {
                                    str += '退单成功';
                                } else if (row.back_status == 7) {
                                    str += '平台取消';
                                } else if (row.back_status == 6) {
                                    str += '员工取消';
                                }
                            }

                            return str;
                        }
                    },
                    {
                        field: 'opt', title: '操作', width: '13%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: left">';
                            if (row.do_status == 1) {

                                str += "审核状态：" + "<span  style='color:#519838;font-weight: bold'>已处理</span>" + "<br>";
                                str += "审核备注：" + row.bp_check_remark + "<br>";
                                str += "审核时间：" + row.bp_check_time + "<br>";

                            } else if (row.do_status == 0 || row.do_status == null) {
                                str += "审核状态：";
                                if (row.back_status == 1 && row.fromin == '美团') {
                                    if (row.order_status == 99) {
                                        str += '已处理';
                                    } else {
                                        str += '<span><a class="gift-upbtn" onclick="refund_do(\'' + row.order_desc + '\',\'' + row.fromin + '\',\'' + row.back_status + '\',\'' + row.order_id + '\',\'' + row.order_no + '\',\'' + row.bp_id + '\')">待处理</a></span>';
                                    }
                                } else if ((row.back_status == 1 && row.fromin == '饿了么') || row.back_status == 2) {
                                    str += '<span><a class="gift-upbtn" onclick="refund_do(\'' + row.order_desc + '\',\'' + row.fromin + '\',\'' + row.back_status + '\',\'' + row.order_id + '\',\'' + row.order_no + '\',\'' + row.bp_id + '\')">待处理</a></span>';
                                } else if (row.back_status == 6 || row.back_status == 7) {
                                    str += '<span><a class="gift-upbtn" onclick="refund_do(\'' + row.order_desc + '\',\'' + row.fromin + '\',\'' + row.back_status + '\',\'' + row.order_id + '\',\'' + row.order_no + '\',\'' + row.bp_id + '\')">待处理</a></span>';
                                }
                            }

                            return str;
                        }

                    }
                ]],
                pagination: true,
                singleSelect: true,
                rownumbers: true
            });
        }

        //订单穿透
        function showOrderInfo(order_id, rownumber) {
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
                            if (content != "") {
                                $.messager.alert('订单详情', content);
                            }


                        }

                    }
                    else {
                        $.messager.alert("提示信息", "数据请求失败");
                    }
                }
            });
        }


        //提交审核信息
        function saveRefund(type) {
            top.$.messager.confirm('退单审核', '确定提交审核吗?', function (r) {
                var data = $("#fm").serialize();
                if (type == 2) {
                    data = $("#eleme_fm").serialize();
                }

                if (r) {
                    $.ajax({
                        url: "/BackMoneyApi/refund",
                        data: data,
                        type: 'POST',
                        beforeSend: function () {
                            return $("#fm").form("validate");
                        },
                        success: function (result) {
                            if (result.status == "1") {
                                $.messageShow("操作成功");
                                // $.messager.alert("提示信息", "操作成功");
                                $("#refund").dialog("close");
                                $("#tb1").datagrid("reload");
                            }
                            else {
                                $.messager.alert("提示信息", result.message);
                            }
                        }
                    });

                }
            });

        }

        //退款处理
        function refund_do(order_desc, fromin, back_status, order_id, order_no, bp_id) {
            $('#fm').form('reset');
            var action = "";
            if (fromin == '美团') {
                if (back_status == 1) {
                    action = "美团退款申请";
                    $("input[type='radio'][name='agree_type'][value='1']").attr("checked", "checked");
                    $("#meituan_refund").dialog("open").dialog('setTitle', '退单处理' + '---' + action);
                    $("#order_desc").val(order_desc);
                    $("#bp_id").val(bp_id);
                } else if (back_status != 1) {
                    action = "美团";
                    $("#refund").dialog("open").dialog('setTitle', '退单处理' + '---' + action);
                    $("#eleme_order_desc").val(order_desc);
                    $("#eleme_bp_id").val(bp_id);
                }

            } else if (fromin == '饿了么') {
                action = "饿了么";
                $("#refund").dialog("open").dialog('setTitle', '退单处理' + '---' + action);
                $("#eleme_order_desc").val(order_desc);
                $("#eleme_bp_id").val(bp_id);
            }

        }

    </script>
</head>
<body>
<div class="bodymain">
    <div id="selectForm" name="selectForm">
        <input type="hidden" name="cur_fromin" id="cur_fromin" value="0">
        <input type="hidden" name="do_status" id="do_status" value="0">
        <input type="hidden" name="back_status" id="back_status" value="">
        <input type="hidden" name="brand_id" id="brand_id" value="">

        <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">
            <%--<b>品牌：</b>--%>
            <%--<a href="###" data-status="0" class="easyui-linkbutton  orderbrandid"--%>
            <%--data-options="toggle:true,group:'g5',plain:true">全部</a>--%>
            <%--<c:forEach items="${cdsbrands}" var="item">--%>
            <%--<a href="###" data-status="${item.brand_id}" class="easyui-linkbutton  orderbrandid"--%>
            <%--data-options="toggle:true,group:'g5',plain:true">${item.brand_name}</a>--%>
            <%--</c:forEach>--%>
            <%--&nbsp;&nbsp;&nbsp;--%>
            <b>退款状态：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  backStatus"
               data-options="toggle:true,group:'g3',plain:true">全部</a>
            <a href="###" data-status="1" class="easyui-linkbutton  backStatus"
               data-options="toggle:true,group:'g3',plain:true">退款申请</a>
            <a href="###" data-status="2" class="easyui-linkbutton  backStatus"
               data-options="toggle:true,group:'g3',plain:true">退款成功</a>
            <a href="###" data-status="3" class="easyui-linkbutton  backStatus"
               data-options="toggle:true,group:'g3',plain:true">退款驳回</a>
            <a href="###" data-status="4" class="easyui-linkbutton  backStatus"
               data-options="toggle:true,group:'g3',plain:true">退款仲裁中</a>
            <a href="###" data-status="5" class="easyui-linkbutton  backStatus"
               data-options="toggle:true,group:'g3',plain:true">退款失败</a>
            &nbsp;&nbsp;&nbsp;
            <b>平台：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g4',plain:true">全部</a>
            <a href="###" data-status="1" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g4',plain:true">饿了么</a>
            <a href="###" data-status="2" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g4',plain:true">美团</a>
            &nbsp;&nbsp;&nbsp;
            <b>处理状态：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  dostatus"
               data-options="toggle:true,group:'g5',plain:true">待处理</a>
            <a href="###" data-status="1" class="easyui-linkbutton  dostatus"
               data-options="toggle:true,group:'g5',plain:true">已处理</a>
            &nbsp;&nbsp;&nbsp;
            <b>时间：</b><input class="Wdate" type="text" id="sl_date_begin" name="sl_date_begin"
                             onClick="WdatePicker({maxDate:'#F{$dp.$D(\'sl_date_end\')}'})" value="${curday}">
            至 <input class="Wdate" type="text" id="sl_date_end" name="sl_date_end"
                     onClick="WdatePicker({minDate:'#F{$dp.$D(\'sl_date_begin\')}',maxDate:'${curday}'})"
                     value="${curday}">

        </div>
        <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">

            <select class="easyui-combobox" id="shops" name="stores_id" label="门店：" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'">
                <option value="">全部</option>
                <c:forEach items="${cdsshops}" var="item">
                    <option value="${item.stores_id}">${item.name}</option>
                </c:forEach>
            </select>

            <input class="easyui-textbox" name="keywords" style="width:280px"
                   data-options="label:'关键词：',prompt:'订单号、流水号...'">

            <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
            <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
        </div>
    </div>
    <%--主列表--%>
    <div style="padding:5px;background:#fff;position:absolute;top:0px;left:0px;right:0px;bottom:0px;">
        <table id="tb1" style="width:100%;height:100%"></table>
    </div>

    <%--美团--退单处理对话框--%>
    <div id="meituan_refund" class="easyui-dialog" style="width: 500px; height: 510px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem" id="meituan_agree">
                <label class="mytitle" style="height:30px">是否同意退款:</label>
                <div>
                    <label><input type="radio" name="agree_type" value="1" checked="true" onclick="getValue(this)">&nbsp;1.同意退款</label>
                    <label><input type="radio" name="agree_type" value="2" onclick="getValue(this)">&nbsp;2.驳回申请</label>
                </div>
            </div>

            <div class="fitem" id="refund_reason">
                <label class="mytitle" style="height:30px;">审核备注:</label>
                <div>
                    <label style="width: 150px;"><input type="radio" name="reason" value="专送运力不足">&nbsp;1.专送运力不足</label>
                    <label><input type="radio" name="reason" value="商户接单超时">&nbsp;2.商户接单超时</label>
                    <label><input type="radio" name="reason" value="商家取消">&nbsp;3.商家取消</label>
                    <label style="width: 150px;"><input type="radio" name="reason" value="用户申请取消" checked="true">&nbsp;4.用户申请取消</label>
                    <label><input type="radio" name="reason" value="客服强制无效">&nbsp;5.客服强制无效</label>
                    <label style="width: 150px;"><input type="radio" name="reason" value="售后退款">&nbsp;6.售后退款</label>
                </div>
            </div>

            <div class="fitem">
                <label class="mytitle" style="height:30px">订单物流状态:</label>
                <div>
                    <label><input type="radio" name="send_type" value="骑手已送达">&nbsp;1.骑手已送达</label>
                    <label><input type="radio" name="send_type" value="用户拒收">&nbsp;2.用户拒收</label>
                    <label><input type="radio" name="send_type" value="联系不上用户">&nbsp;3.联系不上用户</label>
                    <label><input type="radio" name="send_type" value="其他原因" checked="true">&nbsp;4.其他原因</label>
                </div>
            </div>

            <input type="hidden" name="order_desc" id="order_desc" value="0"/>
            <input type="hidden" name="bp_id" id="bp_id"/>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveRefund(1)" iconcls="icon-save">确认取消</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#meituan_refund').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>


    <%--饿了么--退单处理对话框--%>
    <div id="refund" class="easyui-dialog" style="width: 500px; height: 510px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="eleme_fm" method="post">
            <div class="fitem">
                <label class="mytitle" style="height:30px;">审核备注:</label>
                <div>
                    <label style="width: 150px;"><input type="radio" name="reason" value="专送运力不足">&nbsp;1.专送运力不足</label>
                    <label><input type="radio" name="reason" value="商户接单超时">&nbsp;2.商户接单超时</label>
                    <label><input type="radio" name="reason" value="商家取消">&nbsp;3.商家取消</label>
                    <label style="width: 150px;"><input type="radio" name="reason" value="用户申请取消" checked="true">&nbsp;4.用户申请取消</label>
                    <label><input type="radio" name="reason" value="客服强制无效">&nbsp;5.客服强制无效</label>
                    <label style="width: 150px;"><input type="radio" name="reason" value="售后退款">&nbsp;6.售后退款</label>
                </div>
            </div>

            <div class="fitem">
                <label class="mytitle" style="height:30px">订单物流状态:</label>
                <div>
                    <label><input type="radio" name="send_type" value="骑手已送达">&nbsp;1.骑手已送达</label>
                    <label><input type="radio" name="send_type" value="用户拒收">&nbsp;2.用户拒收</label>
                    <label><input type="radio" name="send_type" value="联系不上用户">&nbsp;3.联系不上用户</label>
                    <label><input type="radio" name="send_type" value="其他原因" checked="true">&nbsp;4.其他原因</label>
                </div>
            </div>

            <input type="hidden" name="order_desc" id="eleme_order_desc" value="0"/>
            <input type="hidden" name="bp_id" id="eleme_bp_id"/>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveRefund(2)" iconcls="icon-save">确认取消</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#refund').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
