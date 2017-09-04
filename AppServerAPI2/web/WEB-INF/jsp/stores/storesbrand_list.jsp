<%--
  门店品牌明细管理
  User: zhuzhenghua
  Date: 17-05-28
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/inc/tld.jsp" %>
<%@ include file="/WEB-INF/jsp/inc/ui.jsp" %>
<html>
<head>
    <meta name="viewport" content="width=device-width"/>
    <title>门店品牌明细管理</title>
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
    </style>
    <script type="text/javascript">

        $(function () {


            $('#List').datagrid({
                url: '/Stores/getstoresBrandList',
                width: $(window).width(),
                methord: 'post',
                height: $(window).height(),

                sortName: 'stores_brand_id',
                //sortOrder: 'desc',
                idField: 'stores_brand_id',
                striped: true, //奇偶行是否区分
                singleSelect: true,//单选模式
                rownumbers: true,//行号
                //title: '门店品牌明细管理',
                nowrap: "true",
                toolbar: [
                    {
                        text: '添加',
                        iconCls: 'icon-add',
                        handler: function () {
                            add();
                        }
                    },
                    {
                        text: '修改',
                        iconCls: 'icon-edit',
                        handler: function () {
                            editprint();
                        }
                    }
                ],
                columns: [[
                    {field: 'stores_brand_id', title: 'ID', rowspan: 2, width: '5%', align: 'center', sortable: true},
                    {field: 'stores_name', title: '门店名称', rowspan: 2, width: '7%', align: 'center', sortable: true},
                    {
                        field: 'brand_id',
                        title: '品牌',
                        rowspan: 2,
                        width: '7%',
                        sortable: true,
                        align: 'center',
                        formatter: function (value, row) {
                            <c:forEach var="item" items="${brandlist}">
                            if (row.brand_id == "${item.brand_id}") {
                                return "${item.brand_name}";
                            }
                            </c:forEach>
                        }
                    },
                    {field: 'service_phone', title: '客户电话', rowspan: 2, width: '7%', align: 'center', sortable: true},
                    {title: '饿了么', colspan: 4},
                    {title: '美团', colspan: 3},
                    {title: '百度', colspan: 2},
                    {title: '微信'}
                ],
                    [
                        {
                            field: 'elem_restaurant_id',
                            title: '绑定',
                            width: '10%',
                            align: 'center',
                            formatter: function (value, row) {
                                var id = row.stores_brand_id;
                                var stores_id = row.stores_id;
                                var elemid = row.elem_restaurant_id;
                                elestoresBindQuery(id, elemid);
                                return '<a href="javascript:void(0)" onclick="openDlg()" style="text-decoration:none;color: green" name="' + id + '">加载中...</a>';
                            }
                        },
                        {
                            field: 'elem_mode',
                            title: '接单模式',
                            width: '10%',
                            align: 'center',
                            formatter: function (value, row) {
                                var elemid = row.elem_restaurant_id;
                                 queryMode(elemid);
                                //console.log(row);
                                return '<a href="javascript:void(0)"  onclick="bindMode(' + elemid + ','+row.stores_brand_id+')" style="text-decoration:none;color: red" name="' + elemid + '">未绑定</a>';
                            }
                        },
                        {
                            field: 'dispatch_extinfo',
                            title: '配送方式',
                            width: '7%',
                            align: 'center',
                            formatter: function (value, row) {
                                var id = row.stores_brand_id;
                                var statu = '';
                                var value2 = 0;
                                if (value != null && value != undefined && value != "") {
                                    value = eval('(' + value + ')');
                                    value2 = value.send_type1;
                                }
                                if (value2 == undefined || value2 == "" || value2 == null) {
                                    statu = '请选择';
                                } else if (value2 == 0) {
                                    statu = '平台专送';
                                } else if (value2 == 2) {
                                    statu = '生活半径';
                                } else if (value2 == 3) {
                                    statu = '点我达';
                                }else{
                                    statu = '请选择';
                                }
                                return '<a href="javascript:void(0)"  onclick="psfs(' + value2 + ',' + id + ',1)" style="text-decoration:none;color: green">' + statu + '</a>';
                            }
//                            editor:{
//                                type:'combobox',
//                                options: {
//                                    data: [
//                                        {value: '0', text: '平台专送', iconCls: 'icon-add', selected: true},
//                                        {value: '1', text: '点我达', iconCls: 'icon-remove'},
//                                        {value: '2', text: '生活半径', iconCls: 'icon-save'}
//                                    ]
//                                }
//                            }
                        },
                        {field: 'elem_url', title: '订餐地址', width: '7%', sortable: true, align: 'center'},
                        {
                            field: 'meituan_restaurant_id',
                            title: '绑定',
                            width: '7%',
                            align: 'center',
                            formatter: function (value, row) {
                                var id2 = row.stores_brand_id;
                                var stores_id = row.stores_id;
                                var meitid = row.meituan_restaurant_id;
                                meituanstoresBindQuery(id2, meitid);
                                return '<a href="javascript:void(0)"  style="text-decoration:none;color: green" name="' + meitid + '">加载中...</a>';
                            }
                        },
                        {
                            field: 'dispatch_extinfo2',
                            title: '配送方式',
                            width: '7%',
                            align: 'center',
                            formatter: function (value, row) {
                                var id = row.stores_brand_id;
                                value = row.dispatch_extinfo;
                                var statu = '';
                                var value2 = 0;
                                if (value != null && value != undefined && value != "") {
                                    value = eval('(' + value + ')');
                                    value2 = value.send_type2;
                                }
                                if (value2 == undefined || value2 == "" || value2 == null) {
                                    statu = '请选择';
                                } else if (value2 == 0) {
                                    statu = '平台专送';
                                } else if (value2 == 2) {
                                    statu = '生活半径';
                                } else if (value2 == 3) {
                                    statu = '点我达';
                                }else {
                                    statu = '请选择';
                                }
                                return '<a href="javascript:void(0)"  onclick="psfs(' + value2 + ',' + id + ',2)" style="text-decoration:none;color: green">' + statu + '</a>';
                            }
                        },
                        {field: 'meituan_url', title: '订餐地址', width: '7%', align: 'center'},
                        {field: 'baidu_restaurant_id', title: '餐厅ID', width: '7%', align: 'center'},
                        {
                            field: 'dispatch_extinfo3',
                            title: '配送方式',
                            width: '7%',
                            align: 'center',
                            formatter: function (value, row) {
                                var id = row.stores_brand_id;
                                value = row.dispatch_extinfo;
                                var statu = '';
                                var value2 = 0;
                                if (value != null && value != undefined && value != "") {
                                    value = eval('(' + value + ')');
                                    value2 = value.send_type3;
                                }
                                if (value2 == undefined || value2 == "" || value2 == null) {
                                    statu = '请选择';
                                } else if (value2 == 0) {
                                    statu = '平台专送';
                                } else if (value2 == 2) {
                                    statu = '生活半径';
                                } else if (value2 == 3) {
                                    statu = '点我达';
                                }else{
                                    statu = '请选择';
                                }
                                return '<a href="javascript:void(0)"  onclick="psfs(' + value2 + ',' + id + ',3)" style="text-decoration:none;color: green">' + statu + '</a>';
                            }
                        },
                        {
                            field: 'dispatch_extinfo4',
                            title: '配送方式',
                            width: '5%',
                            align: 'center',
                            formatter: function (value, row) {
                                var id = row.stores_brand_id;
                                value = row.dispatch_extinfo;
                                var statu = '';
                                var value2 = 0;
                                if (value != null && value != undefined && value != "") {
                                    value = eval('(' + value + ')');
                                    value2 = value.send_type4;
                                }
                                if (value2 == undefined || value2 == "" || value2 == null) {
                                    statu = '请选择';
                                } else if (value2 == 0) {
                                    statu = '平台专送';
                                } else if (value2 == 2) {
                                    statu = '生活半径';
                                } else if (value2 == 3) {
                                    statu = '点我达';
                                }else{
                                    statu = '请选择';
                                }
                                return '<a href="javascript:void(0)"  onclick="psfs(' + value2 + ',' + id + ',4)" style="text-decoration:none;color: green">' + statu + '</a>';
                            }
                        }
                    ],
                ]
            });

            //添加品牌明细
            function add() {
                //$("#fm").form("clear");
                $("#dlg").dialog("open").dialog('setTitle', '添加门店品牌明细');
                $("select").val("");
                $("[name='brand_id']").val("");
                $("[name='stores_id']").val("");
                $("[name='service_phone']").val("");
                $("[name='elem_restaurant_id']").val("");
                $("[name='elem_url']").val("");
                $("[name='meituan_restaurant_id']").val("");
                $("[name='meituan_url']").val("");
                $("[name='baidu_restaurant_id']").val("");
                $("[name='baidu_url']").val("");
                $("#opt").val("add");

                $("#stores_id").combobox({
                    onChange: function (n, o) {
                        var brand_id = $("#brand_id").val();
                        if(brand_id==null||brand_id==""){
                            $.messager.show({
                                title:'提示信息',
                                msg:'请先选择品牌',
                                showType:'show'
                            });
                            $("#stores_id").combobox().clear();
                        }
                    $.ajax({
                        url: "/Stores/validateStores",
                        data: {brand_id: brand_id,stores_id:n},
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {

                            } else {
                                $.messager.show({
                                    title:'提示信息',
                                    msg:'已存在该门店，请重新选择！',
                                    showType:'show'
                                });
                                $("#stores_id").combobox().clear();
                            }
                        }
                    });
                    }
                });
            }

//            function add2(){
//                $('body').openWin({
//
//                    width:580,//对象宽度
//
//                    height:450,//对象高度
//
//                    url:'/Stores/savestoresbrand',
//
//                    title:'添加'
//
//                });
//            }

            //区域、门店联动
            $("#area").combobox({
                onChange: function (n, o) {
//                    if (n == "qx") {
//                        $("li").css({"display": "inline"});
//                    } else {
//                        $("li").css({"display": "none"});
//                        $("li[name='" + n + "']").css({"display": "inline"});
//                    }
                    $.ajax({
                        url: "/Stores/getCityStores",
                        data: {city: n},
                        type: 'POST',
                        success: function (result) {
                            if (result.status == "1") {
                                var objs = result.obj;
                                $("#shop").html("");
                                for (var i = 0; i < objs.length; i++){
                                    var name = objs[i].name;
                                    var id = objs[i].stores_id;
                                    var country = objs[i].country;
                                    $("#shop").append(' <a href="###" onclick=tzbg('+id+','+country+') > <li class="mdss" name='+id+'>'+name+'</li></a>');
                                }
                            } else {

                            }
                        }
                    });

                }
            });
            //修改门店品牌明细
            function editprint() {
                $("#stores_id").combobox({
                    onChange: null
                });
                var row = $("#List").datagrid("getSelected");
                if (row == null) {
                    $.messager.alert("提示信息", "请选择一条记录");
                }
                // alert(row.elem_restaurant_id);
                if (row) {
                    $("#dlg").dialog("open").dialog('setTitle', '修改门店品牌明细');
                    $("[name='brand_id']").val("");
                    $("[name='stores_id']").val("");
                    $("[name='service_phone']").val("");
                    $("[name='elem_restaurant_id']").val("");
                    $("[name='elem_url']").val("");
                    $("[name='meituan_restaurant_id']").val("");
                    $("[name='meituan_url']").val("");
                    $("[name='baidu_restaurant_id']").val("");
                    $("[name='baidu_url']").val("");
                    $("[name='stores_brand_id']").val("");
                    $("#fm").form("load", row);
                    $("#opt").val("mod");
                }

            }



        });

        function openDlg() {
            var row = $("#List").datagrid("getSelected");
//            var stores_brand_id = row.stores_brand_id;
//            var elem_restaurant_id = row.elem_restaurant_id;
            if(row!=null&&row!=""&&row!=undefined){
                $("#shopId").textbox('setValue',row.elem_restaurant_id);
                $("#stores_brand_ide").val(row.stores_brand_id);
                $("#dlgElm").dialog("open").dialog('setTitle', '绑定');
            }
        }

        function elemeBind() {
            var shopId = $("#shopId").val();
            var stores_brand_id = $("#stores_brand_ide").val();
            $.ajax({
                url: "/Stores/elemeBind",
                data: {stores_brand_id: stores_brand_id,shopId:shopId},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.show({
                            title:'提示信息',
                            msg:'操作成功',
                            showType:'show'
                        });
                        $('#dlgElm').dialog('close');
                    }
                    else {
                        $('#dlgElm').dialog('close');
                        $.messager.alert("提示信息", "操作失败");
                    }
                }
            });
        }

        //绑定饿了么、美团
        function bind(id, status) {
            //$("input").val("");
            $("[name='brand_id']").val("");
            $("[name='stores_id']").val("");
            $("[name='service_phone']").val("");
            $("[name='elem_restaurant_id']").val("");
            $("[name='elem_url']").val("");
            $("[name='meituan_restaurant_id']").val("");
            $("[name='meituan_url']").val("");
            $("[name='baidu_restaurant_id']").val("");
            $("[name='baidu_url']").val("");
            $("[name='stores_brand_id']").val("");
            var rows = $("#List").datagrid('getData').rows;
            var row;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].stores_brand_id == id) {
                    row = rows[i];
                    break;
                }
            }
            //判断是否绑定，动态改变跳转状态
            $.ajax({
                url: "/Stores/elestoresBindQuery",
                data: {stores_brand_id: id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $("#bdopt").val("bindaresh");
                        $("#buttn").text("重新绑定");
                    }
                    else {
                        $("#buttn").text("绑定");
                        $("#bdopt").val("bind");
                    }
                }
            });
            if (status == 1) {
                $("#bd1").show();
                $("#bd2").hide();
            } else if (i == 2) {
                $("#bd1").hide();
                $("#bd2").show();
                $("#bdopt").val("meituanbind");
            }

            $("#dlg2").dialog("open").dialog('setTitle', '绑定');
            $("[name='elem_restaurant_id']").val(row.elem_restaurant_id);
            $("[name='stores_id']").val(row.stores_id);
            $("[name='service_phone']").val(row.service_phone);
            $("[name='elem_url']").val(row.elem_url);
            $("[name='meituan_restaurant_id']").val(row.meituan_restaurant_id);
            $("[name='meituan_url']").val(row.meituan_url);
            $("[name='baidu_restaurant_id']").val(row.baidu_restaurant_id);
            $("[name='baidu_url']").val(row.baidu_url);
            $("[name='stores_brand_id']").val(row.stores_brand_id);
            $("#fm2").form("load", row);
//            $("#elem_id2").val("");
        }
        //保存门店品牌明细
        function saveprint() {
            var num = $("input[name='service_phone']").val();
            var sele = $("#brand_id").val();
            var sele2 = $("#stores_id").val();
            if (isNaN(num)) {
                $.messager.alert("提示信息", "品牌起始流水号只能输入数字");
            } else if (sele == null || sele == "" || sele == undefined) {
                $.messager.alert("提示信息", "请选择品牌");
            } else if (sele2 == null || sele2 == "" || sele2 == undefined) {
                $.messager.alert("提示信息", "请选择门店");
            } else {

                $.ajax({
                    url: "/Stores/saveBrand",
                    data: $("#fm").serialize(),
                    type: 'POST',
                    beforeSend: function () {
                        return $("#fm").form("validate");
                    },
                    success: function (result) {
                        if (result.status == "1") {
                            $.messager.alert("提示信息", "操作成功");
                            $("#dlg").dialog("close");
                            $("#List").datagrid("reload");
                        } else {
                            $.messager.alert("提示信息", "操作失败");
                        }
                    }
                });
            }

        }
        //根据选择的门店重新加载grid
        function tzbg(id,id2) {
            $('#List').datagrid({url: '/Stores/getstoresBrandList2?stores_id=' + id});
//            $("#shop li").each(function () {
//                $(this).css("backgroundColor", "");
//            });
//            $("a [name='"+id2+"']").css("backgroundColor", "#C0C0C0");
        }


        function saveprint2() {
            var fildid = $("#stores_brand_id").val();
            $.ajax({
                url: "/Stores/saveBrand",
                data: $("#fm2").serialize(),
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        $.messager.alert("提示信息", result.message);
                        $("#dlg2").dialog("close");
                        $("#List").datagrid("reload");
                        $('#List').datagrid('selectRecord',fildid);

                    }
                    else if (result.status == "2") {
                        $.messager.alert("提示信息", result.message);
                    } else if (result.status == "3") {
                        $.messager.confirm('提示信息', '该id已经被绑定，是否解绑到该商户？', function (r) {
                            if (r) {
                                $("#bdopt").val("bindaresh");
                                saveprint2();
                            }
                        });
                    } else {
                        $.messager.alert("提示信息", result.message);
                    }
                }
            });

        }

        //查询饿了么绑定信息
        function elestoresBindQuery(id, elem_restaurant_id) {
            $.ajax({
                url: "/Stores/elemeQuery",
                data: {elem_restaurant_id: elem_restaurant_id,stores_brand_id:id},
                type: 'POST',
                //async:false,
                success: function (result) {
                    if (result.status == "1") {
                        $("a[name='" + id + "']").text('已绑定' + "(" + elem_restaurant_id + ")");
                    } else {
                        $("a[name='" + id + "']").text('未绑定');
                        $("a[name='" + id + "']").css('color', 'red');
                    }
                }
            });
        }
        //查询美团绑定信息
        function meituanstoresBindQuery(id, bdid) {
            $.ajax({
                url: "/Stores/meituanstoresBindQuery",
                data: {stores_brand_id: id},
                type: 'POST',
                success: function (result) {
                    if (result.status == "1") {
                        if (bdid != undefined) {
                            $("a[name='" + bdid + "']").text('已绑定' + "(" + bdid + ")");
                        } else {
                            $("a[name='" + bdid + "']").text('已绑定');
                        }
                    } else {
                        $("a[name='" + bdid + "']").text('未绑定');
                        $("a[name='" + bdid + "']").css('color', 'red');
                    }
                }
            });
        }
        //查询饿了接单模式
        function queryMode(id) {
            var stasu;
            if (id == undefined || id == "" || id == null) {
                $("a[name='" + id + "']").text('未绑定');
                $("a[name='" + id + "']").css('color', 'red');
            } else {
                $.ajax({
                    url: "/Stores/elestoresModeQuery",
                    data: {elem_restaurant_id: id},
                    type: 'POST',
                    //async:false,
                    success: function (result) {
                        stasu= result.status;
                        if (stasu == "1"||stasu == "2"||stasu == "3"||stasu == "4") {
                            $("a[name='" + id + "']").text(result.message);
                            $("a[name='" + id + "']").css('color', 'green');
                        } else {
                            //$("#"+id).text('未绑定');
                            //$("#"+id).css('color','red');
                        }
                    }
                });
            }
        }
        //绑定饿了接单模式
        function bindMode(id,rowsid) {
            $.ajax({
                url: "/Stores/elestoresModeQuery",
                data: {elem_restaurant_id: id},
                type: 'POST',
                success: function (result) {
                    var stasu= result.status;
                    $("#order_mode").combobox('setValue',stasu);
                }
            });
            $("#dlg3").dialog("open").dialog('setTitle', '绑定');
            $("#restaurant_id").val(id);

            $("#rowsid").val(rowsid);
        }

        //绑定饿了接单模式
        function saveprint3() {
            var order_mode = $("#order_mode").val();
            var restaurant_id = $("#restaurant_id").val();
            var rowsid = $("#rowsid").val();
            if (restaurant_id == "" || restaurant_id == undefined) {
                $.messager.alert("提示信息", "请先绑定饿了么餐厅");
            } else if (order_mode == "" || order_mode == null || order_mode == undefined) {
                $.messager.alert("提示信息", "请先选择接单模式");
            } else {
                $.ajax({
                    url: "/Stores/elestoresBindMode",
                    data: {restaurant_id: restaurant_id, order_mode: order_mode},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $.messager.show({
                                title:'提示信息',
                                msg:result.message,
                                showType:'show'
                            });
                            $("#dlg3").dialog("close");
                            $("#List").datagrid("reload");
                            $('#List').datagrid('selectRecord',rowsid);
                        } else {
                            $.messager.alert("提示信息", "操作失败");
                        }
                    }
                });
            }
        }

        //选择配送方式
        function psfs(statu, stores_brand_id, sendtype) {
            $("#dlg4").dialog("open").dialog('setTitle', '配送方式');
            $("#dispatch_extinfo").combobox('setValue',statu);
            if (sendtype == 1) {
                $("#sendtype").val("send_type1");
            } else if (sendtype == 2) {
                $("#sendtype").val("send_type2");
            } else if (sendtype == 3) {
                $("#sendtype").val("send_type3");
            } else if (sendtype == 4) {
                $("#sendtype").val("send_type4");
            }

            $("#stores_brand_id2").val(stores_brand_id);
        }

        //选择配送方式
        function saveprint4() {
            var sendtype = $("#sendtype").val();
            var stores_brand_id = $("#stores_brand_id2").val();
            var dispatch_extinfo = $("#dispatch_extinfo").val();
            if (dispatch_extinfo == "" || dispatch_extinfo == undefined) {
                $.messager.alert("提示信息", "请选择配送方式");
            } else {
                $.ajax({
                    url: "/Stores/updateDispatchExtinfo",
                    data: {stores_brand_id: stores_brand_id, dispatch_extinfo: dispatch_extinfo, sendtype: sendtype},
                    type: 'POST',
                    success: function (result) {
                        if (result.status == "1") {
                            $.messager.show({title:'提示信息', msg:result.message,showType:'show'});
                            $("#dlg4").dialog("close");
                            $("#List").datagrid("reload");
                            $('#List').datagrid('selectRecord',stores_brand_id);
                        } else {
                            $.messager.alert("提示信息", "操作失败");
                        }
                    }
                });
            }
        }

    </script>
</head>
<body>
<div class="easyui-layout" style="width:100%;height:100%;">

    <div data-options="region:'north'" style="height:30px">
        <h2 style="margin-left: 10px;margin-top: 5px;">
            门店品牌明细管理</h2>
    </div>
    <div data-options="region:'west',split:true" style="width:10%;">
        <div style="width:100%;height:20px;">
            <select class="easyui-combobox" id="area" name="area" style="width:100%;"
                    data-options="editable:false,panelHeight:'auto'">
                <option value="">所有区域</option>
                <c:forEach var="item" items="${citylist}">
                    <option value="${item.id}">${item.dataname}</option>
                </c:forEach>
            </select>
        </div>
        <div style="width:100%;margin-top: 10%" id="shop">
            <c:forEach var="item" items="${storeslist}">
                <a href="javascript:void(0)" onclick="tzbg(${item.stores_id},${item.country})">
                    <li class="mdss" name="${item.city}">${item.name}</li>
                </a>
            </c:forEach>
        </div>
    </div>
    <div data-options="region:'center',title:'门店品牌详细管理',iconCls:'icon-ok'">
        <table id="List">
        </table>
    </div>

    <!--   ============================== 新增界面div、表单===============================     -->
    <div id="dlg" class="easyui-dialog" style="width: 550px; height: 580px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons">

        <form id="fm" method="post">
            <div class="fitem">
                <label class="mytitle">品牌</label>
                <select class="easyui-combobox" id="brand_id" name="brand_id" style="width:160px;"
                        data-options="editable:false,panelHeight:'auto'">
                    <option value="" selected="true">请选择</option>
                    <c:forEach var="item" items="${brandlist}">
                        <option value="${item.brand_id}">${item.brand_name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="fitem">
                <label class="mytitle">门店</label>
                <select class="easyui-combobox easyui-validatebox" id="stores_id" name="stores_id" style="width:160px;"
                        data-options="editable:false,panelHeight:'auto'" required="true">
                    <option value="" selected="true">请选择</option>
                    <c:forEach var="item" items="${storeslist}">
                        <option value="${item.stores_id}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="fitem">
                <label class="mytitle">门店名称</label>
                <input name="stores_name" class="easyui-validatebox" required="true"/>
            </div>
            <div class="fitem">
                <label class="mytitle">客服电话</label>
                <input name="service_phone" class="easyui-validatebox" required="true"/>
            </div>
            <div class="fitem">
                <label class="mytitle">饿了么餐厅ID</label>
                <input name="elem_restaurant_id" class="easyui-validatebox"/>
            </div>
            <div class="fitem">
                <label class="mytitle">饿了么订餐地址</label>
                <input name="elem_url" class="easyui-validatebox"/>
            </div>
            <div class="fitem">
                <label class="mytitle">美团餐厅ID</label>
                <input name="meituan_restaurant_id" class="easyui-validatebox"/>
            </div>
            <div class="fitem">
                <label class="mytitle">美团订餐地址</label>
                <input name="meituan_url" class="easyui-validatebox"/>
            </div>
            <div class="fitem">
                <label class="mytitle">百度餐厅ID</label>
                <input name="baidu_restaurant_id" class="easyui-validatebox"/>
            </div>
            <div class="fitem">
                <label class="mytitle">百度订餐地址</label>
                <input name="baidu_url" class="easyui-validatebox"/>
            </div>
            <input type="hidden" name="stores_brand_id" id="stores_brand_id" value="1"/>
            <input type="hidden" name="opt" id="opt" value=""/>
        </form>

    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg').dialog('close')"
           iconcls="icon-cancel">取消</a>
    </div>


    <!--   ============================== 绑定界面div、表单===============================     -->
    <div id="dlg2" class="easyui-dialog" style="width: 550px; height: 250px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons2">
        <form id="fm2" method="post">
            <div class="fitem" id="bd1">
                <label class="mytitle">饿了么餐厅ID</label>
                <input name="elem_restaurant_id"  class="easyui-textbox"/>
            </div>
            <div class="fitem" id="bd2">
                <label class="mytitle">美团餐厅ID</label>
                <input name="meituan_restaurant_id" class="easyui-textbox" id="mtid"/>
            </div>
            <input type="hidden" name="stores_brand_id" value="1"/>
            <input type="hidden" name="opt" value="bd" id="bdopt"/>
            <input type="hidden" name="stores_name" value="0"/>
            <input type="hidden" name="brand_id" value="1"/>
            <input type="hidden" name="stores_id" value="" id="storesid"/>
            <input type="hidden" name="service_phone" value="0"/>
            <input type="hidden" name="elem_url" value="1"/>
            <input type="hidden" name="meituan_url" value=""/>
            <input type="hidden" name="baidu_restaurant_id" value=""/>
            <input type="hidden" name="baidu_url" value="0"/>
        </form>
    </div>
    <div id="dlg-buttons2">

        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint2()" id="buttn">绑定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg2').dialog('close')">取消</a>
    </div>


    <!--   ============================== 饿了么绑定界面div、表单===============================     -->
    <div id="dlgElm" class="easyui-dialog" style="width: 350px; height: 150px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttonsElm">
        <form id="fmElm" method="post">
            <div class="fitem" >
                <label class="mytitle">饿了么餐厅ID</label>
                <input name="elem_restaurant_id" id="shopId" class="easyui-textbox"/>
            </div>
            <input type="hidden" name="stores_brand_id" id="stores_brand_ide" value="1"/>
        </form>
    </div>
    <div id="dlg-buttonsElm">

        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="elemeBind()" id="buttnElm">绑定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlgElm').dialog('close')">取消</a>
    </div>

    <!--   ============================== 绑定饿了么接单模式表单===============================     -->
    <div id="dlg3" class="easyui-dialog" style="width: 550px; height: 250px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons3">
        <form id="fm3" method="post">
            <div class="fitem" style="padding-top:10%;padding-left:10%;">
                <label class="mytitle">请选择接单模式</label>
                <select class="easyui-combobox" id="order_mode" name="order_mode" style="width:240px;"
                        data-options="editable:false,panelHeight:'auto'">
                    <option value="">请选择</option>
                    <option value="1">开放平台接单</option>
                    <option value="2">饿了么商家版后台接单</option>
                    <option value="3">饿了么商家版的android客户端接单</option>
                    <option value="4">饿了么商家版的ios客户端接单</option>
                </select>
            </div>

            <input type="hidden" id="restaurant_id" value=""/>
            <input type="hidden" id="rowsid" value=""/>
        </form>
    </div>
    <div id="dlg-buttons3">

        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint3()">绑定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg3').dialog('close')">取消</a>
    </div>

    <!--   ============================== 选择配送方式===============================     -->
    <div id="dlg4" class="easyui-dialog" style="width: 550px; height: 250px; padding: 10px 20px;"
         closed="true" buttons="#dlg-buttons4">
        <form id="fm4" method="post">
            <div class="fitem" style="padding-top:10%;padding-left:10%;">
                <label class="mytitle">请配送方式</label>
                <select class="easyui-combobox" id="dispatch_extinfo" name="dispatch_extinfo" style="width:240px;"
                        data-options="editable:false,panelHeight:'auto'">
                    <option value="8">请选择</option>
                    <option value="0">平台专送</option>
                    <option value="3">点我达</option>
                    <option value="2">生活半径</option>
                </select>
            </div>
            <input type="hidden" id="sendtype" value=""/>
            <input type="hidden" id="stores_brand_id2" value=""/>
        </form>
    </div>
    <div id="dlg-buttons4">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveprint4()">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#dlg4').dialog('close')">取消</a>
    </div>
</div>
</body>
</html>
