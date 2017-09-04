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
    <title>评价查询</title>
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

        .none:first-of-type {
            border: none !important;
        }

        .reply:hover {
            cursor: pointer;
        }

        .replyCancel {
            opacity: 0.8;
            border-radius: 3px;
            padding: 3px 6px;
            background-color: #36a854;
            position: absolute;
            bottom: 20px;
            right: 60px
        }

        .replySave {
            opacity: 0.8;
            border-radius: 3px;
            padding: 3px 6px;
            background-color: #36a854;
            position: absolute;
            bottom: 20px;
            right: 20px
        }
    </style>
    <script type="text/javascript">
        $(function () {
            //饿了么查询
            $("#findBtn").click(function () {
                $("#selectForm").serializeSelectJson("tb");
            });
            //清空查询
            $("#refreshBtn").click(function () {
                window.location.reload();
            });

            //品牌
            $(".brand").UI_tab({'name': 'brand_id', 'custom': {'p1': 1}});
            //星级
            $(".ratingStar").UI_tab({'name': 'rating'});
            //平台
            $(".fromin").UI_tab({'name': 'fromin', 'custom': {'p1': 1}});


            loaddata();
        });

        //加载饿了么页面数据
        function loaddata() {
            var queryParams = $("#selectForm").serializeJson();

            $('#tb').datagrid({
                toolbar: '#selectForm',
                fit: true,
                nowrap: false,
                striped: false,
                url: '/CommentApi/comment/list',
                method: 'POST',
                sortName: 'create_time',
                queryParams: queryParams,
                sortOrder: 'desc',
                idField: 'fc_id',
//                onBeforeLoad: function (param) {
//                    if (param.stores_id == 0) {
//                        return false;
//                    }
//                    if (param.storesId == "") {
//                        return false;
//                    }
//                },
//                onLoadError: function () {
//                    return false;
//                },

                columns: [[
                    {field: 'fromin', title: '平台', width: '10%', align: 'center'},
                    {field: 'fromin_stores_name', title: '平台门店', width: '10%', align: 'center'},
                    {
                        field: 'fromin_order_id', title: '平台订单号', width: '10%', align: 'center',
                        formatter: function (val, row) {
                            var str = "<div>";
                            str += '<a href="javascript:void(0)" onclick="showOrderInfo(\'' + row.fromin_order_id + '\')">'+row.fromin_order_id+'</a>';;
                            str += "</div>";
                            return str;
                        }
                    },
                    {field: 'create_time', title: '评论时间', width: '10%', align: 'center'},
                    {
                        field: 'content', title: '评论内容', width: '60%', align: 'left', halign: 'center',
                        formatter: function (val, row) {
                            var foodList = JSON.parse(row.goodsInfo);
                            var food = "<div style='border:1px solid #ccc;margin: 10px;padding: 10px 20px'>";
                            var count = 0;
                            for (var i in foodList) {
                                if (foodList[i].foodRatingContent != null && foodList[i].foodRatingContent != "") {
                                    count++;
                                    food += "<p class='none' style='border-top:1px solid #ccc;position: relative;padding: 10px 0'>";
                                    //菜品好评图标
                                    food += "<span style='display: inline-block;width: 90%'>";
                                    var rating_img = "";
                                    if (foodList[i].quality == "BAD") {
                                        rating_img += "<img src='data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+Cjxzdmcgd2lkdGg9IjE2cHgiIGhlaWdodD0iMTVweCIgdmlld0JveD0iMCAwIDE2IDE1IiB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiPgogICAgPCEtLSBHZW5lcmF0b3I6IHNrZXRjaHRvb2wgMzkuMSAoMzE3MjApIC0gaHR0cDovL3d3dy5ib2hlbWlhbmNvZGluZy5jb20vc2tldGNoIC0tPgogICAgPHRpdGxlPjQ4MjMxQTlCLTc3QzMtNEE2Qy04QjFGLUEzNzk5OTZFQTgxRTwvdGl0bGU+CiAgICA8ZGVzYz5DcmVhdGVkIHdpdGggc2tldGNodG9vbC48L2Rlc2M+CiAgICA8ZGVmcz4KICAgICAgICA8cG9seWdvbiBpZD0icGF0aC0xIiBwb2ludHM9IjcuOTY1MDExNDkgMTQuOTE0NjcwNyAxNS45MzAwMjMgMTQuOTE0NjcwNyAxNS45MzAwMjMgMCAwIDAgMCA3LjQ1NzMzNTMzIDAgMTQuOTE0NjcwNyA3Ljk2NTAxMTQ5IDE0LjkxNDY3MDciPjwvcG9seWdvbj4KICAgIDwvZGVmcz4KICAgIDxnIGlkPSJwYy3nlKjmiLfor4Tku7fop4bop4norr7orqEiIHN0cm9rZT0ibm9uZSIgc3Ryb2tlLXdpZHRoPSIxIiBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPgogICAgICAgIDxnIGlkPSLnlKjmiLfor4Tku7ct5pyq5Zue5aSNIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgtMjcyLjAwMDAwMCwgLTY1MC4wMDAwMDApIj4KICAgICAgICAgICAgPGcgaWQ9Ikdyb3VwLTQiIHRyYW5zZm9ybT0idHJhbnNsYXRlKDYyLjAwMDAwMCwgNjcuMDAwMDAwKSI+CiAgICAgICAgICAgICAgICA8ZyBpZD0i6K+E5Lu3LTIiIHRyYW5zZm9ybT0idHJhbnNsYXRlKDE4MC4wMDAwMDAsIDI4Ny4wMDAwMDApIj4KICAgICAgICAgICAgICAgICAgICA8ZyBpZD0i5ZCQ5qe9IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgzMC4wMDAwMDAsIDI5My4wMDAwMDApIj4KICAgICAgICAgICAgICAgICAgICAgICAgPGcgaWQ9IlBhZ2UtMSIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMC4wMDAwMDAsIDMuMDAwMDAwKSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8bWFzayBpZD0ibWFzay0yIiBmaWxsPSJ3aGl0ZSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHVzZSB4bGluazpocmVmPSIjcGF0aC0xIj48L3VzZT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvbWFzaz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxnIGlkPSJDbGlwLTIiPjwvZz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxwYXRoIGQ9Ik0xMy40MjA2ODk3LDEzLjIzOTUyMSBMMTMuMzQ4MzIxOCwxMy4zNTE3OTY0IEMxMy4yODI3NTg2LDEzLjQ4MDk1ODEgMTMuMTgyODA0NiwxMy41ODk4MjA0IDEzLjA2MDk2NTUsMTMuNjY2MTY3NyBDMTIuOTQ5NDI1MywxMy43MzU3Nzg0IDEyLjgxNzI4NzQsMTMuNzc3MzY1MyAxMi43MTcyNDE0LDEzLjc4NDAxMiBMMy44MzkwODA0NiwxMy43ODA2ODg2IEwzLjgzOTA4MDQ2LDYuODkyNjM0NzMgQzUuMzU1MjE4MzksNi42MDQwNDE5MiA3LjcxODQzNjc4LDQuMzQwNTY4ODYgNy43ODI4MDQ2LDIuMDA3NDg1MDMgTDcuNzc1OTA4MDUsMS44OTI5NjQwNyBDNy43NzU5MDgwNSwxLjQ2ODU2Mjg3IDguMTI4NzM1NjMsMS4xMjI3NTQ0OSA4LjU2MzIxODM5LDEuMTIyNzU0NDkgQzguOTMyMjI5ODksMS4xMjI3NTQ0OSA5LjI0NzE3MjQxLDEuMzY3NTE0OTcgOS4zMjk5MzEwMywxLjcxODk4MjA0IEw5LjM5NDI5ODg1LDEuOTg2MTk3NiBDOS40NTA1NzQ3MSwyLjMwODM4MzIzIDkuNDc4MTYwOTIsMi42MzA1Njg4NiA5LjQ3ODE2MDkyLDIuOTQ1MDI5OTQgQzkuNDc4MTYwOTIsMy41ODI3NTQ0OSA5LjM2ODkxOTU0LDQuMjEyNTc0ODUgOS4xNTI5MTk1NCw0LjgxNTQ0OTEgTDguNjI0MTgzOTEsNi4yOTY0MDcxOSBMMTMuODU4NTc0Nyw2LjI5NzU3NDg1IEwxMy45ODI4MDQ2LDYuMzA5ODgwMjQgQzE0LjI2MDg3MzYsNi4zMjAwMjk5NCAxNC41MTM3NDcxLDYuNDYyNTc0ODUgMTQuNjU5NzcwMSw2LjY5MTYxNjc3IEMxNC43NTA2MjA3LDYuODM0MTYxNjggMTQuNzkxOTA4LDcuMDAwNDE5MTYgMTQuNzc4MTE0OSw3LjI1OTczMDU0IEMxNC43NzAxMTQ5LDcuMzA0NjQwNzIgMTMuOTc5MzEwMywxMS45MjQ4MjA0IDEzLjQyMDY4OTcsMTMuMjM5NTIxIEwxMy40MjA2ODk3LDEzLjIzOTUyMSBaIE0xLjE1NjMyMTg0LDYuOTU0MzQxMzIgTDIuNjg5NjU1MTcsNi45NTIwOTU4MSBMMi42ODk2NTUxNywxMy43ODE4NTYzIEwxLjE1MDYyMDY5LDEzLjc4NjM0NzMgTDEuMTU2MzIxODQsNi45NTQzNDEzMiBaIE0xNS42MzU2NzgyLDYuMDk3NzI0NTUgQzE1LjI4ODQ1OTgsNS41NTIwNjU4NyAxNC42ODczNTYzLDUuMjExODI2MzUgMTQuMDcwMDY5LDUuMTkzODYyMjggTDEwLjI0MzY3ODIsNS4xNzU4OTgyIEMxMC40OTg4NTA2LDQuNDU2MjU3NDkgMTAuNjI3NTg2MiwzLjcwNjI1NzQ5IDEwLjYyNzU4NjIsMi45NDcyNzU0NSBDMTAuNjI3NTg2MiwyLjU3MDAyOTk0IDEwLjU5NDIwNjksMi4xODM4MDI0IDEwLjUyMDczNTYsMS43NjcyMTU1NyBMMTAuNDQ5Mzc5MywxLjQ2NjMxNzM3IEMxMC4yNDU5NzcsMC42MDQwNDE5MTYgOS40NzEyNjQzNywwIDguNTYzMjE4MzksMCBDNy40OTU0NDgyOCwwIDYuNjI2NDgyNzYsMC44NDg4MDIzOTUgNi42MjY0ODI3NiwxLjg5Mjk2NDA3IEM2LjYyNjQ4Mjc2LDEuOTUzNTkyODEgNi42Mjg3ODE2MSwyLjAxMzE0MzcxIDYuNjM0NDgyNzYsMi4wNjI1NDQ5MSBDNi41ODE2MDkyLDMuOTEzOTIyMTYgNC4yOTk5NTQwMiw1LjgyNDg1MDMgMy41MjQxMzc5Myw1LjgyNDg1MDMgTDMuNTIzMDM0NDgsNS44MjQ4NTAzIEwwLjY4MDQ1OTc3LDUuODI2MDE3OTYgQzAuMzA5MjQxMzc5LDUuODI2MDE3OTYgMC4wMDY4OTY1NTE3Miw2LjEyMjMzNTMzIDAuMDA2ODk2NTUxNzIsNi40ODUwMjk5NCBMMCwxNC4yNTQ0OTEgQzAsMTQuNjIyNzU0NSAwLjMyNjQzNjc4MiwxNC44OTExMzc3IDAuNzM1NjMyMTg0LDE0LjkwMjI3NTQgTDMuMiwxNC44OTc4NzQzIEwzLjI5NDI1Mjg3LDE0LjkxNDY3MDcgTDMuMzc3MDExNDksMTQuOTAzNDQzMSBMMTIuNzU2MzIxOCwxNC45MDY4NTYzIEMxMy4wODYxNjA5LDE0Ljg4NDMxMTQgMTMuNDA0NTk3NywxNC43ODQ0MzExIDEzLjY4MDQ1OTgsMTQuNjEyNjk0NiBDMTMuOTc5MzEwMywxNC40MjUxNDk3IDE0LjIyMDY4OTcsMTQuMTYxMjU3NSAxNC4zMjk4MzkxLDEzLjkyODg5MjIgTDE0LjQ0NDc4MTYsMTMuNzQyNTE1IEMxNS4wOTA3NTg2LDEyLjI0MDI2OTUgMTUuODc5MjY0NCw3LjYzODA1Mzg5IDE1LjkxMTQ0ODMsNy40NDM4NjIyOCBMMTUuOTI0MTM3OSw3LjI1NzQ4NTAzIEMxNS45NTYzMjE4LDYuODQ0MzExMzggMTUuODU2MzY3OCw2LjQ0MzUzMjkzIDE1LjYzNTY3ODIsNi4wOTc3MjQ1NSBMMTUuNjM1Njc4Miw2LjA5NzcyNDU1IFoiIGlkPSJGaWxsLTEiIGZpbGwtb3BhY2l0eT0iMC4zIiBmaWxsPSIjNEM1MTY1IiBtYXNrPSJ1cmwoI21hc2stMikiIHRyYW5zZm9ybT0idHJhbnNsYXRlKDcuOTY1MDE5LCA3LjQ1NzMzNSkgc2NhbGUoMSwgLTEpIHRyYW5zbGF0ZSgtNy45NjUwMTksIC03LjQ1NzMzNSkgIj48L3BhdGg+CiAgICAgICAgICAgICAgICAgICAgICAgIDwvZz4KICAgICAgICAgICAgICAgICAgICA8L2c+CiAgICAgICAgICAgICAgICA8L2c+CiAgICAgICAgICAgIDwvZz4KICAgICAgICA8L2c+CiAgICA8L2c+Cjwvc3ZnPg=='>";
                                    } else if (foodList[i].quality == "GOOD") {
                                        rating_img += "<img src='data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+Cjxzdmcgd2lkdGg9IjE2cHgiIGhlaWdodD0iMTVweCIgdmlld0JveD0iMCAwIDE2IDE1IiB2ZXJzaW9uPSIxLjEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiPgogICAgPCEtLSBHZW5lcmF0b3I6IHNrZXRjaHRvb2wgMzkuMSAoMzE3MjApIC0gaHR0cDovL3d3dy5ib2hlbWlhbmNvZGluZy5jb20vc2tldGNoIC0tPgogICAgPHRpdGxlPjFBRjIwNTMzLUNCRUItNEQyRC1BNkEzLTIyQkNCOTMxMkQyMjwvdGl0bGU+CiAgICA8ZGVzYz5DcmVhdGVkIHdpdGggc2tldGNodG9vbC48L2Rlc2M+CiAgICA8ZGVmcz4KICAgICAgICA8cG9seWdvbiBpZD0icGF0aC0xIiBwb2ludHM9IjcuOTY1MDExNDkgMTQuOTE0NjcwNyAxNS45MzAwMjMgMTQuOTE0NjcwNyAxNS45MzAwMjMgMCAwIDAgMCA3LjQ1NzMzNTMzIDAgMTQuOTE0NjcwNyA3Ljk2NTAxMTQ5IDE0LjkxNDY3MDciPjwvcG9seWdvbj4KICAgIDwvZGVmcz4KICAgIDxnIGlkPSJwYy3nlKjmiLfor4Tku7fop4bop4norr7orqEiIHN0cm9rZT0ibm9uZSIgc3Ryb2tlLXdpZHRoPSIxIiBmaWxsPSJub25lIiBmaWxsLXJ1bGU9ImV2ZW5vZGQiPgogICAgICAgIDxnIGlkPSLnlKjmiLfor4Tku7ct5pyq5Zue5aSNIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgtMjcyLjAwMDAwMCwgLTUxNC4wMDAwMDApIj4KICAgICAgICAgICAgPGcgaWQ9Ikdyb3VwLTQiIHRyYW5zZm9ybT0idHJhbnNsYXRlKDYyLjAwMDAwMCwgNjcuMDAwMDAwKSI+CiAgICAgICAgICAgICAgICA8ZyBpZD0i6K+E5Lu3LTIiIHRyYW5zZm9ybT0idHJhbnNsYXRlKDE4MC4wMDAwMDAsIDI4Ny4wMDAwMDApIj4KICAgICAgICAgICAgICAgICAgICA8ZyBpZD0i5o6o6I2QIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgzMC4wMDAwMDAsIDE2MC4wMDAwMDApIj4KICAgICAgICAgICAgICAgICAgICAgICAgPGcgaWQ9IlBhZ2UtMSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICA8bWFzayBpZD0ibWFzay0yIiBmaWxsPSJ3aGl0ZSI+CiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgPHVzZSB4bGluazpocmVmPSIjcGF0aC0xIj48L3VzZT4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDwvbWFzaz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxnIGlkPSJDbGlwLTIiPjwvZz4KICAgICAgICAgICAgICAgICAgICAgICAgICAgIDxwYXRoIGQ9Ik0xMy40MjA2ODk3LDEzLjIzOTUyMSBMMTMuMzQ4MzIxOCwxMy4zNTE3OTY0IEMxMy4yODI3NTg2LDEzLjQ4MDk1ODEgMTMuMTgyODA0NiwxMy41ODk4MjA0IDEzLjA2MDk2NTUsMTMuNjY2MTY3NyBDMTIuOTQ5NDI1MywxMy43MzU3Nzg0IDEyLjgxNzI4NzQsMTMuNzc3MzY1MyAxMi43MTcyNDE0LDEzLjc4NDAxMiBMMy44MzkwODA0NiwxMy43ODA2ODg2IEwzLjgzOTA4MDQ2LDYuODkyNjM0NzMgQzUuMzU1MjE4MzksNi42MDQwNDE5MiA3LjcxODQzNjc4LDQuMzQwNTY4ODYgNy43ODI4MDQ2LDIuMDA3NDg1MDMgTDcuNzc1OTA4MDUsMS44OTI5NjQwNyBDNy43NzU5MDgwNSwxLjQ2ODU2Mjg3IDguMTI4NzM1NjMsMS4xMjI3NTQ0OSA4LjU2MzIxODM5LDEuMTIyNzU0NDkgQzguOTMyMjI5ODksMS4xMjI3NTQ0OSA5LjI0NzE3MjQxLDEuMzY3NTE0OTcgOS4zMjk5MzEwMywxLjcxODk4MjA0IEw5LjM5NDI5ODg1LDEuOTg2MTk3NiBDOS40NTA1NzQ3MSwyLjMwODM4MzIzIDkuNDc4MTYwOTIsMi42MzA1Njg4NiA5LjQ3ODE2MDkyLDIuOTQ1MDI5OTQgQzkuNDc4MTYwOTIsMy41ODI3NTQ0OSA5LjM2ODkxOTU0LDQuMjEyNTc0ODUgOS4xNTI5MTk1NCw0LjgxNTQ0OTEgTDguNjI0MTgzOTEsNi4yOTY0MDcxOSBMMTMuODU4NTc0Nyw2LjI5NzU3NDg1IEwxMy45ODI4MDQ2LDYuMzA5ODgwMjQgQzE0LjI2MDg3MzYsNi4zMjAwMjk5NCAxNC41MTM3NDcxLDYuNDYyNTc0ODUgMTQuNjU5NzcwMSw2LjY5MTYxNjc3IEMxNC43NTA2MjA3LDYuODM0MTYxNjggMTQuNzkxOTA4LDcuMDAwNDE5MTYgMTQuNzc4MTE0OSw3LjI1OTczMDU0IEMxNC43NzAxMTQ5LDcuMzA0NjQwNzIgMTMuOTc5MzEwMywxMS45MjQ4MjA0IDEzLjQyMDY4OTcsMTMuMjM5NTIxIEwxMy40MjA2ODk3LDEzLjIzOTUyMSBaIE0xLjE1NjMyMTg0LDYuOTU0MzQxMzIgTDIuNjg5NjU1MTcsNi45NTIwOTU4MSBMMi42ODk2NTUxNywxMy43ODE4NTYzIEwxLjE1MDYyMDY5LDEzLjc4NjM0NzMgTDEuMTU2MzIxODQsNi45NTQzNDEzMiBaIE0xNS42MzU2NzgyLDYuMDk3NzI0NTUgQzE1LjI4ODQ1OTgsNS41NTIwNjU4NyAxNC42ODczNTYzLDUuMjExODI2MzUgMTQuMDcwMDY5LDUuMTkzODYyMjggTDEwLjI0MzY3ODIsNS4xNzU4OTgyIEMxMC40OTg4NTA2LDQuNDU2MjU3NDkgMTAuNjI3NTg2MiwzLjcwNjI1NzQ5IDEwLjYyNzU4NjIsMi45NDcyNzU0NSBDMTAuNjI3NTg2MiwyLjU3MDAyOTk0IDEwLjU5NDIwNjksMi4xODM4MDI0IDEwLjUyMDczNTYsMS43NjcyMTU1NyBMMTAuNDQ5Mzc5MywxLjQ2NjMxNzM3IEMxMC4yNDU5NzcsMC42MDQwNDE5MTYgOS40NzEyNjQzNywwIDguNTYzMjE4MzksMCBDNy40OTU0NDgyOCwwIDYuNjI2NDgyNzYsMC44NDg4MDIzOTUgNi42MjY0ODI3NiwxLjg5Mjk2NDA3IEM2LjYyNjQ4Mjc2LDEuOTUzNTkyODEgNi42Mjg3ODE2MSwyLjAxMzE0MzcxIDYuNjM0NDgyNzYsMi4wNjI1NDQ5MSBDNi41ODE2MDkyLDMuOTEzOTIyMTYgNC4yOTk5NTQwMiw1LjgyNDg1MDMgMy41MjQxMzc5Myw1LjgyNDg1MDMgTDMuNTIzMDM0NDgsNS44MjQ4NTAzIEwwLjY4MDQ1OTc3LDUuODI2MDE3OTYgQzAuMzA5MjQxMzc5LDUuODI2MDE3OTYgMC4wMDY4OTY1NTE3Miw2LjEyMjMzNTMzIDAuMDA2ODk2NTUxNzIsNi40ODUwMjk5NCBMMCwxNC4yNTQ0OTEgQzAsMTQuNjIyNzU0NSAwLjMyNjQzNjc4MiwxNC44OTExMzc3IDAuNzM1NjMyMTg0LDE0LjkwMjI3NTQgTDMuMiwxNC44OTc4NzQzIEwzLjI5NDI1Mjg3LDE0LjkxNDY3MDcgTDMuMzc3MDExNDksMTQuOTAzNDQzMSBMMTIuNzU2MzIxOCwxNC45MDY4NTYzIEMxMy4wODYxNjA5LDE0Ljg4NDMxMTQgMTMuNDA0NTk3NywxNC43ODQ0MzExIDEzLjY4MDQ1OTgsMTQuNjEyNjk0NiBDMTMuOTc5MzEwMywxNC40MjUxNDk3IDE0LjIyMDY4OTcsMTQuMTYxMjU3NSAxNC4zMjk4MzkxLDEzLjkyODg5MjIgTDE0LjQ0NDc4MTYsMTMuNzQyNTE1IEMxNS4wOTA3NTg2LDEyLjI0MDI2OTUgMTUuODc5MjY0NCw3LjYzODA1Mzg5IDE1LjkxMTQ0ODMsNy40NDM4NjIyOCBMMTUuOTI0MTM3OSw3LjI1NzQ4NTAzIEMxNS45NTYzMjE4LDYuODQ0MzExMzggMTUuODU2MzY3OCw2LjQ0MzUzMjkzIDE1LjYzNTY3ODIsNi4wOTc3MjQ1NSBMMTUuNjM1Njc4Miw2LjA5NzcyNDU1IFoiIGlkPSJGaWxsLTEiIGZpbGw9IiNGRkMzNEQiIG1hc2s9InVybCgjbWFzay0yKSI+PC9wYXRoPgogICAgICAgICAgICAgICAgICAgICAgICA8L2c+CiAgICAgICAgICAgICAgICAgICAgPC9nPgogICAgICAgICAgICAgICAgPC9nPgogICAgICAgICAgICA8L2c+CiAgICAgICAgPC9nPgogICAgPC9nPgo8L3N2Zz4=' >";
                                    }
                                    //菜品名称
                                    if (foodList[i].foodName != null) {
                                        food += rating_img;
                                        food += "<em style='color: #2aabd2'>" + foodList[i].foodName + "：</em>";
                                    }
                                    //菜品回复
                                    var food_reply = '<strong style="padding: 2px 5px;border:1px solid #ccc;font-size:15px;color: #0a8;display: inline-block;position: absolute;right: 0;top: 5px" class="reply"' +
                                        ' onclick="foodReply(\'' + foodList[i].ratingId + '\')">回复</strong>';

                                    //菜品回复框
                                    var foodReplyBox = '<div id="foodReplyBox' + foodList[i].ratingId + '" style="position:relative;padding:10px;border:1px solid #ccc;display: none;">';
                                    foodReplyBox += '<textarea  id="foodReplyContent' + foodList[i].ratingId + '" style="resize:none;width:100%;height:100px"/>';
                                    foodReplyBox += '<span class="replyCancel" onclick="foodReplyCancel(\'' + foodList[i].ratingId + '\')">取消</span>';
                                    foodReplyBox += '<span class="replySave" onclick="foodReplySave(\'' + foodList[i].ratingId + '\',\'' + row.fromin_stores_id + '\',\'' + row.rating_id + '\')">确认</span>';
                                    foodReplyBox += '</div>';

                                    //菜品回复内容
                                    var foodReplyContent = "";
                                    var foodReplyContent1 = foodList[i].foodReplyContent;
                                    if (foodReplyContent1 != null) {
                                        foodReplyContent = "<br><div style='padding: 5px 10px;border:1px solid #36a854;'>";
                                        foodReplyContent += "<span style='color: #36a854'>" + row.replyer + "：</span>" + foodReplyContent1;
                                        foodReplyContent += "</div>";
                                        food_reply = "";
                                    }

                                    //菜品评论
                                    food += foodList[i].foodRatingContent + "</span>" + foodReplyContent + food_reply + foodReplyBox;
                                    food += "</p>";
                                }

                            }
                            food += "</div>";
                            if (count == 0) {
                                food = "";
                            }

                            //标签
                            var tags = "";
                            if (row.tags != null) {
                                var tagsList = JSON.parse(row.tags);
                                for (var i in tagsList) {
                                    tags += "<span style='border-radius: 6px;border:1px solid #ccc;margin: 6px;padding: 5px 10px;display: inline-block;'>" + tagsList[i] + "</span>";
                                }
                            }

                            var content = row.content;
                            var str = "<div>";

                            //星级评分
                            var rating = "<div style='margin: 10px 0 0 10px;position: relative'>";
                            if (row.rating > 0) {
                                for (var i = 0; i < row.rating; i++) {
                                    rating += "<img src='/static/images/star24.png' height='16' width='16'>";
                                }
                                for (var i = 0; i < (5 - row.rating); i++) {
                                    rating += "<img src='/static/images/star24_off.png' height='16' width='16'>";
                                }
                            }

                            //总回复
                            var reply = '<strong style="padding: 5px 10px;border:1px solid #ccc;font-size:15px;color: #0a8;display: inline-block;position: absolute;right: 10px;top: -5px;" class="reply"' +
                                ' onclick="reply(\'' + row.rating_id + '\')">回复</strong>';

                            //总回复框
                            var replyBox = '<div id="replyBox' + row.rating_id + '" style="position:relative;padding:10px;border:1px solid #ccc;display: none;">';
                            replyBox += '<textarea id="replyContent' + row.rating_id + '" style="resize:none;width:100%;height:100px"/>';
                            replyBox += '<span class="replyCancel" onclick="replyCancel(\'' + row.rating_id + '\')">取消</span>';
                            replyBox += '<span class="replySave" onclick="replySave(\'' + row.rating_id + '\',\'' + row.fromin_stores_id + '\')">确认</span>';
                            replyBox += '</div>';

                            //总回复内容
                            var reply_content = "";
                            if (row.replyContent != null) {
                                reply_content = "<br><div style='padding: 5px 10px;border:1px solid #36a854;'>";
                                reply_content += "<span style='color: #36a854'>" + row.replyer + "：</span>" + row.replyContent;
                                reply_content += "</div>";
                                reply = "";
                            }

                            rating += reply + "</div>";
                            str += rating;

                            //总评论内容
                            if (count == 0) {
                                str += "<div style='margin: 5px 0 0 5px;padding: 10px 15px'>" + "<div>" + content + "</div>";
                                str += reply_content;
                                str += replyBox;
                                str += "</div>";
                            } else {
                                str += "<div style='border-bottom: solid 1px #ccc;margin: 5px 0 0 5px;padding: 10px 15px'>" + "<div>" + content + "</div>";
                                str += reply_content;
                                str += replyBox;
                                str += "</div>";
                            }
                            str += tags;
                            str += food;
                            str += "</div>";
                            return str;
                        }
                    },
                ]],
                pagination: true,
                singleSelect: true,
                rownumbers: true
            });
        }


        //订单穿透
        function showOrderInfo(order_desc) {
            $.ajax({
                url: "/CommentApi/comment/order",
                type: 'GET',
                data: {order_desc: order_desc},
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
        //饿了么评论回复(订单)
        function reply(ratingId) {
            $("#replyBox" + ratingId).show();
        }

        function replyCancel(ratingId) {
            $("#replyBox" + ratingId).hide();
        }

        function replySave(ratingId, fromin_stores_id) {
            var content = $("#replyContent" + ratingId).val();
            $.ajax({
                url: "/CommentApi/comment/replyEleme",
                data: {
                    method: "replyRating",
                    ratingId: ratingId,
                    ratingType: "ORDER",
                    content: content,
                    shopId: fromin_stores_id
                },
                type: 'POST',
                success: function (result) {
                    if (result.status == 1) {
                        $.messageShow(result.message);
                        $("#tb").datagrid("reload");
                    }
                    else {
                        $.messager.alert("消息提示", result.message);
                    }
                }
            });
            $("#replyBox" + ratingId).hide();
        }
        //饿了么评论回复(菜品)
        function foodReply(ratingId) {
            $("#foodReplyBox" + ratingId).show();
        }
        function foodReplyCancel(ratingId) {
            $("#foodReplyBox" + ratingId).hide();
        }
        function foodReplySave(foodRatingId, fromin_stores_id, rating_id) {
            var content = $("#foodReplyContent" + foodRatingId).val();
            $.ajax({
                url: "/CommentApi/comment/replyEleme",
                data: {
                    method: "replyRating",
                    ratingId: foodRatingId,
                    rating_id: rating_id,
                    ratingType: "FOOD",
                    content: content,
                    shopId: fromin_stores_id
                },
                type: 'POST',
                success: function (result) {
                    if (result.status == 1) {
                        $.messageShow(result.message);
                        $("#tb").datagrid("reload");
                    }
                    else {
                        $.messager.alert("消息提示", result.message);
                    }
                }
            });
            $("#foodReplyBox" + foodRatingId).hide();
        }

    </script>
</head>
<body>
<div class="bodymain">
    <div id="p" class="easyui-panel" title="评价查询"
         style="width:100%;height:100%;padding:10px;background:#fafafa;">
        <div id="selectForm" name="selectForm">
            <input type="hidden" id="fromin" name="fromin" value="">
            <input type="hidden" id="rating" name="rating" value="0">
            <input type="hidden" id="brand_id" name="brand_id" value="">

            <div class="easyui-panel" style="padding:5px;background:#fff;height:40px;width:100%;">
                <b>星级：</b>
                <a href="###" data-status="0" class="easyui-linkbutton  ratingStar"
                   data-options="toggle:true,group:'g1',plain:true">全部</a>
                <a href="###" data-status="1" class="easyui-linkbutton  ratingStar"
                   data-options="toggle:true,group:'g1',plain:true">差评(1-3星)</a>
                <a href="###" data-status="2" class="easyui-linkbutton  ratingStar"
                   data-options="toggle:true,group:'g1',plain:true">好评(4-5星)</a>
                &nbsp;&nbsp;&nbsp;
                <b>平台：</b>
                <a href="###" data-status="0" class="easyui-linkbutton  fromin"
                   data-options="toggle:true,group:'g3',plain:true">全部</a>
                <a href="###" data-status="饿了么" class="easyui-linkbutton  fromin"
                   data-options="toggle:true,group:'g3',plain:true">饿了么</a>
                <a href="###" data-status="美团" class="easyui-linkbutton  fromin"
                   data-options="toggle:true,group:'g3',plain:true">美团</a>
                &nbsp;&nbsp;&nbsp;
                <b>品牌：</b>
                <a href="###" data-status="0" class="easyui-linkbutton  brand"
                   data-options="toggle:true,group:'g2',plain:true">全部</a>
                <c:forEach items="${cdsbrands}" var="item">
                    <a href="###" data-status="${item.brand_id}" class="easyui-linkbutton  brand"
                       data-options="toggle:true,group:'g2',plain:true">${item.brand_name}</a>
                </c:forEach>
                &nbsp;&nbsp;&nbsp;
                <b>评价时间：</b>
                <input class="Wdate" type="text" id="sl_date_begin" name="sl_date_begin"
                       onClick="WdatePicker({maxDate:'#F{$dp.$D(\'sl_date_end\')}'})"
                       value="${curday}"> 至
                <input class="Wdate" type="text" id="sl_date_end" name="sl_date_end"
                       onClick="WdatePicker({minDate:'#F{$dp.$D(\'sl_date_begin\')}',maxDate:'${curday}'})"
                       value="${curday}">
                &nbsp;&nbsp;&nbsp;
                <a href="###" id="findBtn" class="easyui-linkbutton " iconCls="icon-search"
                   enterkey="13">查询</a>
                <a href="###" id="refreshBtn" class="easyui-linkbutton"
                   iconCls="icon-reload">清空</a>
            </div>
        </div>

        <div style="padding:5px;background:#fff;position:absolute;top:30px;left:0px;right:0px;bottom:0px;">
            <table id="tb"></table>
        </div>

    </div>

</div>
</body>
</html>
