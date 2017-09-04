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
        var opt_type = 0;//登记方式
        var order_field = "order_no"; //排序字段
        $(function () {
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
            //手动登记
            $("#giftBtn").click(function () {
                registerGift1();
            });


            $(".orderstatus[data-status=" + cur_status + "]").linkbutton({
                iconCls: 'icon-cur',
                selected: true
            });
            $(".orderbrandid[data-status=" + brand_id + "]").linkbutton({
                iconCls: 'icon-cur',
                selected: true
            });
            $(".gifttype[data-status=" + opt_type + "]").linkbutton({
                iconCls: 'icon-cur',
                selected: true
            });


            $(".orderfromin[data-status=" + cur_fromin + "]").linkbutton({
                iconCls: 'icon-cur',
                selected: true
            });


            $(".gifttype").click(function () {
                $(".gifttype").each(function () {
                    $(this).linkbutton({
                        iconCls: ''
                    });
                });
                $(this).linkbutton({
                    iconCls: 'icon-cur'
                });
                opt_type = $(this).data("status");
                loaddata();
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
            if (opt_type == 0) {
                opt_type = "";
            }
            $("#opt_type").val(opt_type);
            $("#cur_status").val(cur_status);
            $("#cur_fromin").val(cur_fromin);
            $("#brand_id").val(brand_id);
            //$("#selectForm").serializeSelectJson("tb1");
            var queryParams = $("#selectForm").serializeJson();

            $('#tb1').datagrid({
                toolbar: '#selectForm',
                title: '订单查询',
                fit: true,
                nowrap: false,
                striped: false,
                url: '/GiftApi/giftlist',
                method: 'POST',
                queryParams: queryParams,
                sortName: 'create_time',
                sortOrder: 'desc',
                idField: 'id',
                columns: [[
                    {
                        field: 'name', title: '顾客姓名', width: '8%', align: 'center',
                        formatter: function (val, row) {
                            return row.name;
                        }
                    },
                    {
                        field: 'phone', title: '手机号码', width: '8%', align: 'center',
                        formatter: function (val, row) {
                            return row.phone;
                        }
                    },
                    {
                        field: 'goods', title: '赠品明细', width: '15%', align: 'center',
                        formatter: function (val, row) {
                            var str = '<div style="margin: 5px;text-align: left">';
                            var goods = JSON.parse(val);
//                            var good_count = 0;
                            if (row.brand_id == 1) {
                                str += "品牌：菜大师" + "<br>";
                            } else if (row.brand_id == 2) {
                                str += "品牌：帅小锅" + "<br>";
                            }
                            for (var i = 0; i < goods.length; i++) {
                                var good = goods[i];
                                var good_name = good.good_name.toString();
                                if (good_name.indexOf(" ") >= 0) {
                                    good_name = "套餐(" + good_name.replace(' ', '+') + ")";
                                }

                                str += "赠品" + (i + 1) + "：" + good_name + " x" + good.quantity + "<br>";
//                                good_count += good.quantity;
                            }
//
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'opt_type', title: '登记明细', width: '17%', align: 'center',
                        formatter: function (val, row) {
                            var send_type = '';
                            var str = '<div style="margin: 5px;text-align: left">';
                            str += "登记方式：" + row.opt_type + "<br>";
                            if (row.order_id != null) {
                                str += "登记订单号：" + row.order_id + '<a href="javascript:void(0)" id="a_orderInfo' + row.rownumber + '" class="a_order_id" onclick = "showOrderInfo(1,\'' + row.order_id + '\',\'' + row.rownumber + '\')">(点击查看)<a/><br>';
                            }
                            str += "登记时间：" + row.create_time + "<br>";
                            str += "</div>";
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
                        field: 'send_reason', title: '赠送原因', width: '15%', align: 'center',
                        formatter: function (val, row) {
                            var send_type = '';
                            var str = '<div style="margin: 5px;text-align: center">';
                            str += row.send_reason.toString().substring("1") + "<br>";
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'opt_name', title: '登记员', width: '8%', align: 'center',
                        formatter: function (val, row) {
                            var send_type = '';
                            var str = '<div style="margin: 5px;text-align: center">';
                            str += row.opt_name + "<br>";
                            str += "</div>";
                            return str;
                        }
                    },
                    {
                        field: 'opt', title: '操作', width: 150, align: 'center', formatter: function (val, row) {
                        if (row.send_order_id == null) {
                            var str = '<div style="margin: 5px;">';
                            str += '<div><a class="gift-upbtn" onclick="upGift(\'' + row.id + '\',\'' + row.name + '\',\'' + row.phone + '\',\'' + row.send_reason + '\',\'' + row.brand_id + '\')">修改</a></div>';
                            str += '<div><a class="gift-delbtn" onclick="delGift(\'' + row.id + '\')">删除</a></div>';
                            str += "</div>"
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

        function addCount() {
            alert("123");
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

            //扩展可编辑下拉框


            //联动显示赠品信息
            $("#cc1").combogrid({
                onChange: function (n, o) {
                    var data = $('#cc1').combogrid('grid').datagrid('getSelections');
                    var goodsList = new Array();
                    var gift = "<div>";
                    for (var i in data) {
                        var goods = {
                            good_name: data[i].good_name,
                            good_id: data[i].good_id,
                            good_type: 2,
                            class_id: data[i].class_id,
                            price: data[i].market_price,
                            quantity: data[i].quantity
                        }

                        gift += "赠品" + (parseInt(i) + 1) + "：" + goods.good_name +  "<span style='color: #CC2222'>" +" x" +goods.quantity+"</span>" + "<br/>";

                        goodsList.push(goods);
                    }
                    gift += "<div/>";
//                    var goods = JSON.stringify(goodsList).toString();
                    $("#giftInfo").empty();
                    $("#giftInfo").append(gift);

                }
            })
            ;


            $('#hand_gift_fm').form('reset');
            //创建下拉框表格
            var lastIndex;
            $('#cc1').combogrid({
                panelWidth: '40%',
                panelHeight: '40%',
                idField: 'good_id',
                textField: 'good_name',
                sortName: 'good_id',
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                url: '/GoodsInfo/GetGoodsInfoLists',
                method: 'post',
                queryParams:{
                    status:1
                },
                toolbar: '#selectForm2',
                pagination: true,
                multiple: true,


                onClickCell: function (rowIndex, field, value) {
                    if (lastIndex != rowIndex) {
                        $('#cc1').combogrid('grid').datagrid('endEdit', lastIndex);
                        $('#cc1').combogrid('grid').datagrid('beginEdit', rowIndex);
                    }
                    lastIndex = rowIndex;
                },
                onDblClickCell:function(index, field, value){
                    $('#cc1').combogrid('grid').datagrid('endEdit', index);
                },

                columns: [[
                    {field: 'good_id', title: 'ID', width: '5%', align: 'center'},
                    {field: 'good_name', title: '商品名称', width: '37%', align: 'center', sortable: true},
                    {
                        field: 'class_id',
                        title: '商品类别',
                        width: '15%',
                        sortable: true,
                        align: 'center',
                        formatter: function (value, row) {
                            <c:forEach var='item' items='${classlist}'>
                            if (row.class_id == '${item.class_id}') {
                                return '${item.class_name}';
                            }
                            </c:forEach>
                        }
                    },
                    {
                        field: 'brand_id',
                        title: '品牌',
                        width: '8%',
                        sortable: true,
                        align: 'center',
                        formatter: function (value, row) {
                            <c:forEach var='item' items='${cdsbrands}'>
                            if (row.brand_id == '${item.brand_id}') {
                                return '${item.brand_name}';
                            }
                            </c:forEach>
                        }
                    },
                    {field: 'market_price', title: '商品价格', width: '10%', align: 'center', sortable: true},
                    {field: 'box_price', title: '餐盒费', width: '8%', align: 'center', sortable: true},
                    {
                        field: 'status',
                        title: '状态',
                        width: '7%',
                        align: 'center',
                        sortable: true,
                        formatter: function (value, row) {
                            if (value == 1) {
                                return '<span  style="color:green">上架</span>';
                            } else {
                                return '<span  style="color:red">下架</span>';
                            }
                        }

                    },
                    {
                        field: 'quantity', title: '商品数量', width: '10%', align: 'center', sortable: true,



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

                    }
                ]]
            });


            //查询
            $("#findBtn2").click(function () {
                var queryParams = $("#selectForm2").serializeJson();
                $('#cc1').combogrid("grid").datagrid("load", queryParams);
            });
            //清空
            $("#refreshBtn2").click(function () {

                $(':input', '#selectForm2').not(':button,:submit,:reset').val('');
                //$(':input','#selectForm').not(':button,:submit,:reset') .removeAttr('checked');
                $('#status1').combobox('setValue', '');
//                $("#class_id").get(0).options[0].selected = true;
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
                value: ""
            });

            $('#hand_gift_order_phone_div').textbox({
                required: true,
                validType: 'checkPhone',
                value: ""
            });

            $("#hand_gift_id").val(0);
            $("input[type='radio'][name='send_reason'][value='1打包错漏']").attr("checked", "checked");
            $("#hand_giftRegister").dialog("open").dialog('setTitle', '登记赠品');

        }
        //提交赠品登记信息
        function saveGift() {
            top.$.messager.confirm('登记赠品', '核对<span style="color:#993222">赠品数量</span>后，确定登记吗?', function (r) {
                if(r){
                    var action = '';
                    var data = $('#cc1').combogrid('grid').datagrid('getSelections');
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
                    dataParam = dataParam + "&" + "goods=" + goods;
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
        function upGift(id, name, phone, reason, brand_id) {
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
            $("#cc1").combogrid({
                onChange: function (n, o) {
                    var data = $('#cc1').combogrid('grid').datagrid('getSelections');
                    var goodsList = new Array();
                    var gift = "<div>";
                    for (var i in data) {
                        var goods = {
                            good_name: data[i].good_name,
                            good_id: data[i].good_id,
                            good_type: 2,
                            class_id: data[i].class_id,
                            price: data[i].market_price,
                            quantity: data[i].quantity
                        }
                        gift += "<a herf='###' id ='gift_add_'+i onclick='addCount()'>" + "赠品" + (parseInt(i) + 1) + "：" + goods.good_name + "<span style='color: #CC2222'>" +" x" +goods.quantity+"</span>" + "<a/><br/>";

                        goodsList.push(goods);
                    }
                    gift += "<div/>";
//                    var goods = JSON.stringify(goodsList).toString();
                    $("#giftInfo").empty();
                    $("#giftInfo").append(gift);

                }
            })
            ;

            $('#hand_gift_fm').form('reset');
            //创建下拉框表格
            var lastIndex;
            $('#cc1').combogrid({
                panelWidth: '40%',
                panelHeight: '40%',
                idField: 'good_id',
                textField: 'good_name',
                sortName: 'good_id',
                striped: true, //奇偶行是否区分
                rownumbers: true,//行号
                url: '/GoodsInfo/GetGoodsInfoLists',
                method: 'post',
                toolbar: '#selectForm2',
                pagination: true,
                multiple: true,
                queryParams:{
                    status:1
                },
                onClickCell: function (rowIndex, field, value) {
                    if (lastIndex != rowIndex) {
                        $('#cc1').combogrid('grid').datagrid('endEdit', lastIndex);
                        $('#cc1').combogrid('grid').datagrid('beginEdit', rowIndex);
                    }
                    lastIndex = rowIndex;
                },
                onDblClickCell:function(index, field, value){
                    $('#cc1').combogrid('grid').datagrid('endEdit', index);
                },

                columns: [[
                    {field: 'good_id', title: 'ID', width: '5%', align: 'center'},
                    {field: 'good_name', title: '商品名称', width: '37%', align: 'center', sortable: true},
                    {
                        field: 'class_id',
                        title: '商品类别',
                        width: '15%',
                        sortable: true,
                        align: 'center',
                        formatter: function (value, row) {
                            <c:forEach var='item' items='${classlist}'>
                            if (row.class_id == '${item.class_id}') {
                                return '${item.class_name}';
                            }
                            </c:forEach>
                        }
                    },
                    {
                        field: 'brand_id',
                        title: '品牌',
                        width: '8%',
                        sortable: true,
                        align: 'center',
                        formatter: function (value, row) {
                            <c:forEach var='item' items='${cdsbrands}'>
                            if (row.brand_id == '${item.brand_id}') {
                                return '${item.brand_name}';
                            }
                            </c:forEach>
                        }
                    },
                    {field: 'market_price', title: '商品价格', width: '10%', align: 'center', sortable: true},
                    {field: 'box_price', title: '餐盒费', width: '8%', align: 'center', sortable: true},
                    {
                        field: 'status',
                        title: '状态',
                        width: '7%',
                        align: 'center',
                        sortable: true,
                        formatter: function (value, row) {
                            if (value == 1) {
                                return '<span  style="color:green">上架</span>';
                            } else {
                                return '<span  style="color:red">下架</span>';
                            }
                        }

                    },
                    {
                        field: 'quantity', title: '商品数量', width: '10%', align: 'center', sortable: true,
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
                //$(':input','#selectForm').not(':button,:submit,:reset') .removeAttr('checked');
                $('#status1').combobox('setValue', '');
//                $("#class_id").get(0).options[0].selected = true;
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

            $("#hand_gift_order_brand_div").combobox('select', brand_id);
//            $("#hand_gift_order_name_div").val(name);
//            $("#hand_gift_order_phone_div").val(phone);
            $("#hand_gift_id").val(id);

//            $("#gift_order_brand").val(old_data.order_brand);
//            $("#gift_order_id").val(order_id);

            var index = reason.substring(0, 1) - 1;
            $("input[type='radio'][name='send_reason']").eq(index).attr("checked", "checked");
            $("#hand_giftRegister").dialog("open").dialog('setTitle', '修改赠品');

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
    <div id="hand_giftRegister" class="easyui-dialog" style="width:730px; height:600px; padding: 0px 0px;"
         closed="true" buttons="#dlg-buttons">
        <div id="gift-layout" class="easyui-layout" style="width:700px;height:500px;">
            <%--赠品信息面板--%>
            <div data-options="region:'east',title:'赠品信息',split:true" style="width:230px;">
                <div id="giftInfo"></div>
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
                               style="font-weight: 800;width:250px;height:30px;"></input>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">顾客手机号</label>
                        <input id="hand_gift_order_phone_div" name="phone"
                               style="font-weight: 800;width:250px;height:30px;"></input>
                    </div>

                    <div class="fitem">
                        <label class="mytitle">赠送原因：</label>
                        <div>
                            <label><input type="radio" name="send_reason" value="1打包错漏">&nbsp;打包错漏</label>
                            <label><input type="radio" name="send_reason" value="2餐品质量问题">&nbsp;餐品质量问题</label>
                            <label><input type="radio" name="send_reason" value="3配送问题">&nbsp;配送问题</label>
                            <label><input type="radio" name="send_reason" value="4其他原因">&nbsp;其他原因</label>
                        </div>
                    </div>
                    <%--//下拉框列表--%>
                    <div class="fitem">
                        <label class="mytitle">选择赠品：</label>

                        <div id="selectForm2" name="selectForm1" style="height: 50px">
                            <div class="easyui-panel" style="padding:5px;background:#fff;height:100%;">
                                <div style="float: left;padding-left: 5px">
                                    <span>商品类别:</span>

                                    <select name="class_id" panelWidth="300px" panelHeight="300px"
                                            class="easyui-combotree"
                                            style="width:100px;"
                                            data-options="
                                        url:'/GoodsInfo/GetBrandTree',
                                        onClick: function(node){
                                            var queryParams = $('#selectForm2').serializeJson();
                                            $('#cc1').combogrid('grid').datagrid('load', queryParams);} ">
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

                        <div data-options="region:'center',title:'',iconCls:'icon-ok'">
                            <select class="easyui-combogrid" id="cc1" name="" style="width:450px" prompt="请选择赠品">
                            </select>
                        </div>

                    </div>

                    <%--<input type="hidden" name="brand_id" id="hand_gift_order_brand_id" value="0"/>--%>
                    <input type="hidden" name="id" id="hand_gift_id" value="0"/>

                </form>


            </div>
        </div>


    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveGift()" iconcls="icon-save">确认登记</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           onclick="javascript:$('#hand_giftRegister').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>

</div>
</body>
</html>
