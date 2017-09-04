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

<script type="text/javascript" src="/static/common/ui_control.js"></script>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>赠品查询</title>
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

        .fitem1 {
            border-bottom: 1px dashed #9f9f9f;
            padding-bottom: 15px;
            padding-top: 15px;
            padding-left: 50px;
        }

        .fitem1 label {
            display: inline-block;
            width: 200px;
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

        .gift-upbtn {
            line-height: 30px;
            cursor: pointer;
            background-color: #5bc0de;
            border-color: #46b8da;
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

        .gift-upbtn1 {
            line-height: 30px;
            cursor: pointer;
            background-color: #9ca39c;
            border-color: #9ca39c;
            color: #fff;
            padding: 5px;
            border-radius: 3px;
            text-decoration: none;
        }

        .gift-delbtn1 {
            line-height: 30px;
            cursor: pointer;
            background-color: #9ca39c;
            border-color: #9ca39c;
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
            color: #5075af;
            text-decoration: none;
        }
    </style>
    <script type="text/javascript">
        var lastIndex;
        var pic_address = "";
        var pic_address_list = new Array();//图片地址数组

        $(function () {
            if (${stores_id==0}) {
                $("#shops_show").show();
            }
            //查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("tb1");
            });
            //清空查询
            $("#refreshBtn").click(function () {
                window.location.reload();
            });
            //手动登记
            $("#giftBtn").click(function () {
                registerGift1();
            });

            //品牌
            $(".orderbrandid").UI_tab({'name': 'brand_id'});
            //审核状态
            $(".is_check").UI_tab({'name': 'is_check'});
            //登记状态
            $(".gifttype").UI_tab({'name': 'opt_type', 'custom': {'p1': 1}});
            //登记原因
            $(".orderfromin").UI_tab({'name': 'cur_fromin'});


            $("#shops").combobox({
                onChange: function (n, o) {
                    loaddata();
                }
            });
            $("#isget").combobox({
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
        //加载页面数据
        function loaddata() {
            var queryParams = $("#selectForm").serializeJson();
            $('#tb1').datagrid({
                toolbar: '#selectForm',
                title: '赠品查询',
                fit: true,
                nowrap: false,
                striped: false,
                url: '/GiftApi/giftlist',
                method: 'POST',
                queryParams: queryParams,
                sortName: 'create_time',
                sortOrder: 'desc',
                idField: 'id',
                pagination: true,
                singleSelect: true,
                rownumbers: true,
                columns: [[
                    {
                        field: 'name', title: '顾客信息', width: '6%', align: 'center',
                        formatter: function (val, row) {
                            return "<strong>" + row.name + "</strong>" + "<br>" + row.phone;
                        }
                    },
                    {
                        field: 'goods', title: '赠品明细', width: '10%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: left">';
                            <c:forEach var='item' items='${cdsbrands}'>
                            if (row.brand_id == '${item.brand_id}') {
                                str += "品牌：" + '${item.brand_name}' + "<br>";
                            }
                            </c:forEach>
                            var goods = JSON.parse(val);
                            for (var i = 0; i < goods.length; i++) {
                                var good = goods[i];
                                var good_name = good.good_name.toString();
                                str += "赠品" + (i + 1) + "：" + good_name + " x" + good.quantity + "<br>";
                            }
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'opt_type', title: '登记明细', width: '17%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: left">';
                            str += "登记方式：" + row.opt_type + "<br>";
                            if (row.order_id != null) {
                                str += "登记订单号：" + row.order_id + '<a href="javascript:void(0)" id="a_orderInfo' + row.rownumber + '" class="a_order_id" onclick = "showOrderInfo(1,\'' + row.order_id + '\',\'' + row.rownumber + '\')">(点击查看)<a/><br>';
                            }
                            str += "登记时间：" + row.create_time + "<br>" + "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'send_type', title: '领取明细', width: '17%', align: 'center',
                        formatter: function (val, row) {
                            var send_type = '';
                            var str = '<div style="margin: 5px;text-align: left">';
                            if (row.send_type == 1) {
                                send_type = "<div style='color:#519838'>是否领取：已领取</div>";
                            } else if (row.send_type == 0) {
                                send_type = "<div style='color:#981e1b'>是否领取：未领取</div>";
                            }
                            str += send_type;
                            if (row.send_order_id != null) {
                                str += "领取时间：" + row.get_time + "<br>";
                                str += "领取订单号：" + row.send_order_id + '<a href="javascript:void(0)" id="a_send_orderInfo' + row.rownumber + '" class="a_send_order_id" onclick = "showOrderInfo(2,\'' + row.send_order_id + '\',\'' + row.rownumber + '\')">（点击查看）<a/><br>';
                            }
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'send_reason', title: '赠送原因', width: '11%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: center">';
                            var reason_id = row.send_reason_sub;
                            if (reason_id == 10) {
                                str += "打包错漏" + "<br>";
                            } else if (reason_id == 11) {
                                str += "[打包错漏]:漏送" + "<br>";
                            } else if (reason_id == 12) {
                                str += "[打包错漏]:错送" + "<br>";
                            } else if (reason_id == 20) {
                                str += "餐品质量问题" + "<br>";
                            } else if (reason_id == 21) {
                                str += "[餐品质量问题]:餐品口味问题" + "<br>";
                            } else if (reason_id == 22) {
                                str += "[餐品质量问题]:食材变质" + "<br>";
                            } else if (reason_id == 23) {
                                str += "[餐品质量问题]:有异物" + "<br>";
                            } else if (reason_id == 24) {
                                str += "[餐品质量问题]:份量问题" + "<br>";
                            } else if (reason_id == 25) {
                                str += "[餐品质量问题]:漏洒" + "<br>";
                            } else if (reason_id == 26) {
                                str += "[餐品质量问题]:保温问题" + "<br>";
                            } else if (reason_id == 27) {
                                str += "[餐品质量问题]:用餐后出现身体不适" + "<br>";
                            } else if (reason_id == 30) {
                                str += "配送问题" + "<br>";
                            } else if (reason_id == 31) {
                                str += "[配送问题]:60-90分钟" + "<br>";
                            } else if (reason_id == 32) {
                                str += "[配送问题]:90-120分钟" + "<br>";
                            } else if (reason_id == 40) {
                                str += "其他原因" + "<br>";
                            } else if (reason_id == 41) {
                                str += "[其他原因]:配送人员服务投诉" + "<br>";
                            } else if (reason_id == 42) {
                                str += "[其他原因]:客服人员服务投诉" + "<br>";
                            } else if (reason_id == 43) {
                                str += "[其他原因]:客服人员电话回访" + "<br>";
                            }
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'opt_name', title: '登记员信息', width: '6%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: center">';
                            var stores_name = "总部";
                            <c:forEach var='item' items='${cdsshops}'>
                            if (row.stores_id == '${item.stores_id}') {
                                stores_name = '${item.name}';
                            }
                            </c:forEach>
                            str += stores_name + ":" + "<br>" + row.opt_name + "<br>";
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'img', title: '图证', width: '8%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align:left">';
                            var imgs = JSON.parse(row.img);
                            for (var i = 0; i < imgs.length; i++) {
                                var reason_img = imgs[i];
                                var img = "http://caidashi.oss-cn-hangzhou.aliyuncs.com/" + reason_img;
                                if (i == 1) {
                                    str += '<img src=' + img + ' height="55" width="55" alt="暂无图片" onclick="getBigImg(\'' + img + '\')"' + '/>';
                                } else {
                                    str += '<img src=' + img + ' height="55" width="55" alt="暂无图片" onclick="getBigImg(\'' + img + '\')"' + '/>&nbsp;';
                                }
                            }
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'is_check', title: '审核信息', width: '15%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: left">';
                            str += "审核状态：";
                            if (row.check_info != null) {
                                if (row.check_info.length > 8) {
                                    row.check_info = '<a id="check_info' + row.rownumber + '" href="javascript:void(0)" onclick = "showCheckInfo(\'' + row.check_info + '\',\'' + row.rownumber + '\')">' + row.check_info.substring(0, 8) + "..." + "</a>";
                                }
                            }
                            if (row.is_check == 0 && ${stores_id==0}) {//只有总部有权限审核
                                str += '<a class="gift-delbtn" onclick="check(\'' + row.id + '\')">未审核</a>';
                            } else if (row.is_check == 0 && ${stores_id!=0}) {
                                str += "未审核";
                            } else if (row.is_check == 1) {
                                str += "<span  style='color:#519838;font-weight: bold'>审核通过</span>" + "<br>";
                                str += "审核人员：" + row.check_name + "<br>";
                                str += "审核备注：" + row.check_info + "<br>";
                                str += "审核时间：" + row.check_time + "<br>";
                            } else if (row.is_check == 2) {
                                str += "<span style='color:#981e1b;font-weight: bold'>审核驳回</span>" + "<br>";
                                str += "审核人员：" + row.check_name + "<br>";
                                str += "审核备注：" + row.check_info + "<br>";
                                str += "审核时间：" + row.check_time + "<br>";
                            }
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'opt', title: '操作', width: '6%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;">';
                            if (row.send_order_id == null) {
                                if ((row.is_check == 1 || row.is_check == 2) && ${stores_id!=0}) {//审核过 门店用户不可以操作
                                    str += '<div><a class="gift-upbtn1" >修改</a></div>';
                                    str += '<div><a class="gift-delbtn1">删除</a></div>';
                                } else {
                                    var goodsStr = "";
                                    var goods = JSON.parse(row.goods);
                                    for (var i in goods) {
                                        goodsStr += goods[i].good_id + "&" + goods[i].good_name + ",";
                                    }
                                    str += '<div><a class="gift-upbtn" onclick="upGift(\'' + row.id + '\',\'' + row.name + '\',\'' + row.phone + '\',\'' + row.send_reason_sub + '\',\'' + row.brand_id + '\',\'' + goodsStr + '\')">修改</a></div>';
                                    str += '<div><a class="gift-delbtn" onclick="delGift(\'' + row.id + '\')">删除</a></div>';
                                }
                                str += "</div>"
                            } else {
                                str += '<div><a class="gift-upbtn1" >修改</a></div>';
                                str += '<div><a class="gift-delbtn1">删除</a></div>';
                            }
                            return str;
                        }
                    }
                ]],
            });
        }
        //订单穿透
        function showOrderInfo(type, order_id, rownumber) {
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
        //手动登记
        function registerGift1(brand_id) {
            //清空上传图片
            pic_address_list = new Array();
            $("#imgs").empty();
            //清空赠品联动
            var data2 = [];
            $('#giftInfo').datagrid({
                idField: 'good_id',
                textField: 'good_name',
                striped: true, //奇偶行是否区分
                data: data2,
                pagination: true,
                singleSelect: false,
                pagination: false,
                columns: [[]]
            });

            $.extend($.fn.validatebox.defaults.rules, {
                checkPhone: {
                    validator: function (value) {
                        if (!(/^1[34578]\d{9}$/.test(value))) {
                            return false;
                        } else {
                            return true;
                        }
                    },
                    message: '请输入正确的手机号码.'
                }
            });
            var data1 = new Array();
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
                                    return row.quantity;
                                },
                                editor: {
                                    type: 'numberbox',
                                    options: {
                                        min: 0,
                                    }
                                }
                            },
                            {
                                field: 'opt', title: '操作', width: '30%', align: 'center', sortable: true,
                                formatter: function (value, row, index) {
                                    return '<div><a class="gift-delbtn" onclick="delGiftInfo(\'' + index + '\',\'' + row.rownumber1 + '\')">删除</a></div>';
                                }
                            },
                        ]]
                    });

                }
            });


            //重置表单和赠品选择表
            $('#hand_gift_fm').form('reset');
            $('#giftInfoSelect').datagrid('unselectAll');

            //创建赠品选择表格
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
                queryParams: {
                    status: 1
                },
                toolbar: '#selectForm2',
                pagination: true,
                singleSelect: true,
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
            $("#findBtn2").click(function () {
                $("#selectForm2").serializeSelectJson("giftInfoSelect");
            });
            //清空
            $("#refreshBtn2").click(function () {
                $(':input', '#selectForm2').not(':button,:submit,:reset').val('');
                $('#status1').combobox('setValue', '');
                $('#class_id1').combobox('setValue', "");
                $("#selectForm2").serializeSelectJson("giftInfoSelect");
            });

            $("#status1").combobox({
                onChange: function (n, o) {
                    $("#selectForm2").serializeSelectJson("giftInfoSelect");
                }
            });

            $("#class_id").combobox({
                onChange: function (n, o) {
                    $("#selectForm2").serializeSelectJson("giftInfoSelect");
                }
            });

            $('#hand_gift_order_name_div').textbox({
                required: true,

                icons: [{
                    iconCls: 'icon-man',
                    handler: function (e) {
                        var sex = $(e.data.target).textbox('getValue').toString();
                        sex = sex.replace("（先生）", "").replace("（女士）", "");
                        $(e.data.target).textbox('setValue', sex + '（先生）');
                    }
                }, {
                    iconCls: 'icon-woman',
                    handler: function (e) {
                        var sex = $(e.data.target).textbox('getValue').toString();
                        sex = sex.replace("（先生）", "").replace("（女士）", "");
                        $(e.data.target).textbox('setValue', sex + '（女士）');
                    }
                }],
                value: ""
            });

            $('#hand_gift_order_phone_div').textbox({
                required: true,
                validType: 'checkPhone',
                value: ""
            });

            //赠送原因二级分类
            $('#cc').combotree({

                onChange: function (n, o) {
                    $("#price").val("");
                    if (n == 31 || n == 21 || n == 26 || n == 41 || n == 42 || n == 43) {
                        $("#price").val(5);
                    } else if (n == 32) {
                        $("#price").val(20);
                    }


                    $("#selectForm2").serializeSelectJson("giftInfoSelect");
                    if (n == "12" || n == "23" || n == "24" || n == "25") {
                        getPic();
                    }
                },


                lines: true,
                panelWidth: 250,
                panelHeight: 320,
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
                    }, {
                        id: 43,
                        text: '客服人员电话回访（5元以下）'
                    }]
                }],
                required: true
            });
            $("#hand_gift_id").val(0);
            $("#hand_giftRegister").dialog("open").dialog('setTitle', '登记赠品');

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
                    var action = '';
                    var data = $("#giftInfo").datagrid("getData").rows;
                    var goodsList = new Array();

                    for (var i in data) {
                        var goods = {
                            good_name: data[i].good_name,
                            good_id: data[i].good_id,
                            good_type: 2,
                            class_id: data[i].class_id,
                            price: data[i].market_price,
                            quantity: data[i].quantity
                        }
                        goodsList.push(goods);
                    }
                    var goods = JSON.stringify(goodsList).toString();

                    var dataParam = $("#hand_gift_fm").serialize();
                    dataParam = dataParam + "&" + "goods=" + goods + "&" + "send_reason_sub=" + n;
                    dataParam = dataParam.replace("send_reason=" + n, "send_reason=" + (n + "").substring(0, 1));

                    var pic_json = JSON.stringify(pic_address_list).toString();
                    dataParam = dataParam + "&" + "img=" + pic_json;

                    if (dataParam.toString().indexOf("id=0") >= 0) {
                        action = "save";
                    } else {
                        action = "update";
                    }


                    $.ajax({
                        url: "/GiftApi/" + action,
                        data: dataParam,
                        type: 'POST',
                        beforeSend: function () {
                            return $("#hand_gift_fm").form("validate");
                        },
                        success: function (result) {
                            if (result.status == "1") {
                                $.messageShow("操作成功");
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
        //删除登记信息
        function delGift(id) {
            top.$.messager.confirm('删除赠品信息', '您确定要删除赠品信息吗?', function (r) {
                if (r) {
                    $.ajax({
                        url: "/GiftApi/del",
                        data: {id: id},
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {
                                $.messageShow("操作成功");
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
        //修改登记信息
        function upGift(id, name, phone, reason, brand_id, goods) {
            //待修改赠品联动
            var data2 = new Array();
            var goods1 = goods.split(",");
            for (var i in goods1) {
                var row = {
                    good_name: goods1[i].split("&")[1],
                    good_id: goods1[i].split("&")[0],
                    rownumber: i,
//                    rownumber1: goods1[i].rownumber
                }
                if (row.good_name != null && row.good_name != "" && row.good_name != undefined) {
                    data2.push(row);
                }
            }

//            var data2 = [];
            $('#giftInfo').datagrid({
                idField: 'good_id',
                textField: 'good_name',
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                data: data2,
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

            $.extend($.fn.validatebox.defaults.rules, {
                checkPhone: {
                    validator: function (value) {
                        if (!(/^1[34578]\d{9}$/.test(value))) {
                            return false;
                        } else {
                            return true;
                        }


                    },
                    message: '请输入正确的手机号码.'
                }
            });

            //联动显示赠品信息
            $("#giftInfoSelect").datagrid({
                onSelect: function (n, o) {

                    var data = $('#giftInfoSelect').datagrid('getSelections');
                    var data1 = new Array();
                    for (var i in data) {
                        var row = {
                            good_name: data[i].good_name,
                            good_id: data[i].good_id,
                            rownumber: i,
                            rownumber1: data[i].rownumber
                        }

                        data1.push(row);

                    }

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

            $('#hand_gift_fm').form('reset');
            $('#giftInfoSelect').datagrid('unselectAll');
            //创建赠品选择表格
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
                queryParams: {
                    status: 1
                },
                toolbar: '#selectForm2',
                pagination: true,
                singleSelect: false,
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
            $("#findBtn2").click(function () {
                var queryParams = $("#selectForm2").serializeJson();
                $('#cc1').combogrid("grid").datagrid("load", queryParams);
            });

            $("#refreshBtn2").click(function () {

                $(':input', '#selectForm2').not(':button,:submit,:reset').val('');
                $('#status1').combobox('setValue', '');
                $('#class_id1').combobox('setValue', "");
                var queryParams = $("#selectForm2").serializeJson();
                $('#cc1').combogrid("grid").datagrid("load", queryParams);
            });

            $("#status1").combobox({
                onChange: function (n, o) {
                    var queryParams = $("#selectForm2").serializeJson();
                    $('#cc1').combogrid("grid").datagrid("load", queryParams);
                }
            });

            $('#hand_gift_order_name_div').textbox({
                required: true,

                icons: [{
                    iconCls: 'icon-man',
                    handler: function (e) {
                        var sex = $(e.data.target).textbox('getValue').toString();
                        sex = sex.replace("（先生）", "").replace("（女士）", "");
                        $(e.data.target).textbox('setValue', sex + '（先生）');
                    }
                }, {
                    iconCls: 'icon-woman',
                    handler: function (e) {
                        var sex = $(e.data.target).textbox('getValue').toString();
                        sex = sex.replace("（先生）", "").replace("（女士）", "");
                        $(e.data.target).textbox('setValue', sex + '（女士）');
                    }
                }],
                value: name
            });

            $('#hand_gift_order_phone_div').textbox({
                required: true,
                validType: 'checkPhone',
                value: phone
            });

            $('#cc').combotree({
                onChange: function (n, o) {
                    $("#price").val("");
                    if (n == 31 || n == 21 || n == 26 || n == 41 || n == 42 || n == 43) {
                        $("#price").val(5);
                    } else if (n == 32) {
                        $("#price").val(20);
                    }
                    $("#selectForm2").serializeSelectJson("giftInfoSelect");
                    if (n == "12" || n == "23" || n == "24" || n == "25") {
                        getPic();
                    }
                },


                lines: true,
                panelWidth: 220,
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
                    }, {
                        id: 43,
                        text: '客服人员电话回访（5元以下）'
                    }]
                }],
                required: true
            });
            //原来待修改信息
            $('#cc').combotree('setValue', reason);

            $("#hand_gift_order_brand_div").combobox('select', brand_id);
            $("#hand_gift_id").val(id);
            var index = reason.substring(0, 1) - 1;
            $("input[type='radio'][name='send_reason']").eq(index).attr("checked", "checked");
            $("#hand_giftRegister").dialog("open").dialog('setTitle', '修改赠品');

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
            $("#imgs").prepend('<img src=' + img + ' height="50" width="50" alt="暂无图片"/>&nbsp;');

        }
        function getBigImg(img) {

            $("#big_img").attr("src", img);
            if ($("#img_div").css('display') === 'none') {
                $("#img_div").show();
            }
        }
        //赠品审核
        function check(id) {
            $("#check_fm")[0].reset();
            $("#cancel_remark").textbox('reset');
            $("#giftId").val(id);
            $('#gift_check').dialog({
                title: '赠品审核',
                width: 400,
                height: 400,
                closed: false,
                cache: false,
                modal: true
            });
        }
        function saveCheck() {
            var dataParam = $("#check_fm").serialize();
            $.ajax({
                url: "/GiftApi/check",
                data: dataParam,
                type: 'POST',
                beforeSend: function () {
                    return $("#gift_check").form("validate");
                },
                success: function (result) {
                    if (result.status == "1") {
                        $.messageShow("操作成功");
                        $("#gift_check").dialog("close");
                        $("#tb1").datagrid("reload");
                    }
                    else {
                        $.messager.alert("消息提示", result.message);
                    }
                }
            });
        }
        //显示审核信息
        function showCheckInfo(checkInfo, rownumber) {
            $('#check_info' + rownumber).tooltip({
                position: 'bottom',
                content: '<div style="color:#fff; width: 200px" >' + checkInfo + '</div>',
                onShow: function () {
                    $(this).tooltip('tip').css({
                        backgroundColor: '#666',
                        borderColor: '#666'
                    });
                }
            });
            $('#check_info' + rownumber).tooltip("show");
        }

    </script>
</head>
<body>
<div class="bodymain">


    <div id="selectForm" name="selectForm">
        <input type="hidden" name="cur_status" id="cur_status" value="">
        <input type="hidden" name="send_reason" id="cur_fromin" value="">
        <input type="hidden" name="brand_id" id="brand_id" value="">
        <input type="hidden" name="opt_type" id="opt_type" value="">
        <input type="hidden" name="is_check" id="is_check" value="0">
        <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">
            <b>品牌：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  orderbrandid"
               data-options="toggle:true,group:'g4',plain:true">全部</a>
            <c:forEach items="${cdsbrands}" var="item">
                <a href="###" data-status="${item.brand_id}" class="easyui-linkbutton  orderbrandid"
                   data-options="toggle:true,group:'g4',plain:true">${item.brand_name}</a>
            </c:forEach>
            &nbsp;&nbsp;&nbsp;
            <b>登记方式：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  gifttype"
               data-options="toggle:true,group:'g5',plain:true">全部</a>
            <a href="###" data-status="手动登记" class="easyui-linkbutton  gifttype"
               data-options="toggle:true,group:'g5',plain:true">手动登记</a>
            <a href="###" data-status="订单登记" class="easyui-linkbutton  gifttype"
               data-options="toggle:true,group:'g5',plain:true">订单登记</a>
            &nbsp;&nbsp;&nbsp;
            <b>登记原因：</b>
            <a href="###" data-status="0" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">全部</a>
            <a href="###" data-status="1" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">打包错漏</a>
            <a href="###" data-status="2" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">餐品质量问题</a>
            <a href="###" data-status="3" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">配送问题</a>
            <a href="###" data-status="4" class="easyui-linkbutton  orderfromin"
               data-options="toggle:true,group:'g3',plain:true">其他原因</a>
            &nbsp;&nbsp;&nbsp;
            <b>审核状态：</b>
            <a href="###" data-status="" class="easyui-linkbutton  is_check"
               data-options="toggle:true,group:'g6',plain:true">全部</a>
            <a href="###" data-status="1" class="easyui-linkbutton  is_check"
               data-options="toggle:true,group:'g6',plain:true">审核通过</a>
            <a href="###" data-status="2" class="easyui-linkbutton  is_check"
               data-options="toggle:true,group:'g6',plain:true">审核驳回</a>
            <a href="###" data-status="0" class="easyui-linkbutton  is_check"
               data-options="toggle:true,group:'g6',plain:true">未审核</a>
            &nbsp;&nbsp;&nbsp;
            <b>登记时间：</b><input class="Wdate" type="text" id="sl_date_begin" name="sl_date_begin"
                               onClick="WdatePicker({maxDate:'#F{$dp.$D(\'sl_date_end\')}'})" value="${curday}">
            至 <input class="Wdate" type="text" id="sl_date_end" name="sl_date_end"
                     onClick="WdatePicker({minDate:'#F{$dp.$D(\'sl_date_begin\')}',maxDate:'${curday}'})"
                     value="${curday}">

        </div>
        <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;">
            <select class="easyui-combobox" id="isget" name="send_type" label="是否领取：" style="width:160px;"
                    data-options="editable:false,panelHeight:'auto'">
                <option value="">全部</option>
                <option value="1">已领取</option>
                <option value="0">未领取</option>
            </select>

            <input class="easyui-textbox" name="keywords" style="width:280px"
                   data-options="label:'关键词：',prompt:'姓名、电话、订单号...'">

            <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search" enterkey="13">查询</a>
            <a href="###" id="refreshBtn" class="easyui-linkbutton" iconCls="icon-reload">刷新</a>
            <a href="###" id="giftBtn" class="easyui-linkbutton" iconCls="icon-add">赠品登记</a>

        </div>
    </div>
    <div style="padding:5px;background:#fff;position:absolute;top:0px;left:0px;right:0px;bottom:0px;">
        <table id="tb1" style="width:100%;height:100%"></table>
    </div>

    <%--手动登记对话框--%>
    <div id="hand_giftRegister" class="easyui-dialog" style="width:730px; height:800px; padding: 0px 0px;"
         closed="true" buttons="#dlg-buttons">
        <div id="gift-layout" class="easyui-layout" style="width:700px;height:720px;">
            <%--赠品信息展示面板--%>
            <div data-options="region:'east',title:'赠品信息',split:true,collapsible:false" style="width:280px;">
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
                <form id="hand_gift_fm" method="post">
                    <div class="fitem">
                        <label class="mytitle">品牌</label>
                        <select class="easyui-combobox easyui-validatebox"
                                id="hand_gift_order_brand_div" name="brand_id" style="width:80px;" required="true"
                                data-options="editable:false,panelHeight:'auto'">
                            <c:forEach items="${cdsbrands}" var="item">
                                <option value="${item.brand_id}" selected="true">${item.brand_name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">顾客姓名</label>
                        <input id="hand_gift_order_name_div" name="name"
                               style="font-weight: 800;width:250px;height:30px;"/>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">顾客手机号</label>
                        <input id="hand_gift_order_phone_div" name="phone"
                               style="font-weight: 800;width:250px;height:30px;"/>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">赠送原因：</label>
                        <%--赠送原因二级分类树形下拉框--%>
                        <input id="cc" style="width: 250px;height: 30px" name="send_reason"/>
                        <a id="upload" href="javascript:void(0)" onclick="getPic(0)" class="easyui-linkbutton "
                           iconCls="icon-upload"></a>
                    </div>

                    <input type="hidden" name="id" id="hand_gift_id" value="0"/>
                </form>


            </div>

            <%--赠品搜索框--%>
            <div id="selectForm2" name="selectForm1" style="height: 40px">
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
                        <select class="easyui-combobox easyui-validatebox" id="status1" name="status"
                                style="width:60px;"
                                data-options="editable:false,panelHeight:'auto'" required="true">
                            <option value="">全部</option>
                            <option value="1" selected="true">是</option>
                            <option value="0">否</option>
                        </select>
                        <span style="padding-left: 15px">商品名称:</span>
                        <input class="easyui-textbox" name="good_name" id="good_name1" style="width:160px"
                               data-options="prompt:'商品名称模糊查询'">
                        <a href="###" id="findBtn2" class="easyui-linkbutton " iconCls="icon-search"
                           enterkey="13">查询</a>
                        <a href="###" id="refreshBtn2" class="easyui-linkbutton"
                           iconCls="icon-reload">清空</a>
                    </div>
                </div>
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
           onclick="javascript:$('#hand_giftRegister').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>

    <div id="img_div" style="display:none;position:relative;height: 100%; width: 100%;">
        <div>
            <img src="/static/images/uploadbg.png" height="600" width="800" alt="暂无图片" id="big_img"
                 style="position: absolute;left: 50%;top:50%;margin:-300px 0 0 -400px;z-index: 1000;"/>
            <img src="/static/images/cancel_img.jpg" height="40" width="40"
                 onclick="this.parentNode.parentNode.style.display='none'" ;
                 style="position: absolute;left: 50%;top:50%;margin:-300px 0 0 360px;z-index: 1500;"/>
        </div>
    </div>

    <%--赠品审核框--%>
    <div id="gift_check" class="easyui-dialog" style="width:400px; height:400px; padding: 0px 20px;" closed="true"
         buttons="#check-buttons">
        <form id="check_fm" method="post">
            <div class="fitem">
                <label class="mytitle"></label>
                <span id="order_no_div" style="font-weight: 800"></span>
                <div style="text-align: center">
                    <label><input type="radio" name="is_check" value="1" checked="checked">&nbsp;审核通过</label>
                    <label><input type="radio" name="is_check" value="2">&nbsp;审核驳回</label>
                </div>
            </div>

            <div class="fitem printcf">
                <label class="mytitle"></label>
                <input class="easyui-textbox" data-options="multiline:true" prompt="备注信息"
                       style="width:350px;height:150px"
                       id="cancel_remark" name="check_info">
            </div>
            <input type="hidden" name="id" id="giftId" value="0"/>
        </form>

    </div>
    <div id="check-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveCheck()" iconcls="icon-save">确认审核</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#gift_check').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
