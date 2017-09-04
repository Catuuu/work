(function ($) {
    /**
     * 导出分页对象序列化
     */
    $.fn.extend({
        /**
         * 序列化当前页的分页对象
         */
        serializeExpPageJson: function () {
            var datagridObj = $(this)
            var options = datagridObj.datagrid('options');
            var sj = {"columns":options.columns,"frozenColumns":options.frozenColumns};
            var forminfo = {};
            forminfo["exportJsonParams"] = $.toJSON(sj);
            forminfo["expfileName"] = options.expfileName;
            forminfo["rows"] = $.toJSON($(this).datagrid("getRows"));
            datagridObj.exprotAjaxExcel('/emp/expCurrentPage', forminfo);
        },
        /**
         * 序列化分页对象
         */
        serializeExpJson: function () {
            var datagridObj = $(this)
            var options = datagridObj.datagrid('options');
            var sj = {"columns":options.columns,"frozenColumns":options.frozenColumns};
            var forminfo = {};
            forminfo["exportJsonParams"] = $.toJSON(sj);
            forminfo["sort"] = options.sortName;
            forminfo["order"] = options.sortOrder;
            forminfo["xh_return_type"] = "excel";
            forminfo["expfileName"] = options.expfileName;;
            $.each(options.queryParams, function (key, value) {
                //其中key相当于是JAVA中MAP中的KEY，VALUE就是KEY相对应的值
                forminfo[key] = value;
            });
            datagridObj.exprotAjaxExcel(options.url, forminfo);
        },
        exprotAjaxExcel: function (url, forminfo) {
            datagridObj = $(this);
            $.ajax({
                type: "POST",
                url: url,
                dataType: "json",
                data: forminfo,
                async: false,
                success: function (result, textStatus) {
                    var expfileName = result.fileName;
                    $.messager.progress({
                        title: '请等待',
                        msg: '数据处理中...',
                        interval: 3600000
                    });
                    var p = $.messager.progress('bar');
                    loadInterval = window.setInterval(function () {
                        //得到当前的进度
                        var forMap = {expfileName: expfileName};
                        $.ajax({
                            type: "POST",
                            url: "/emp/getCreateProgress",
                            data: forMap,
                            success: function (result, textStatus) {
                                if (result == 'end') {//文件生成成功，开始下载文件
                                    window.clearInterval(loadInterval);
                                    $.messager.progress('close');
                                    var expForm = $('<form action="/emp/downFile" method="post" />');
                                    $("<input type='hidden' id='expfileName' name='expfileName' value='" + expfileName + "'/>").appendTo(expForm);
                                    datagridObj.append(expForm)
                                    expForm.submit();
                                    expForm.remove();
                                } else if (result == 'error') {//文件生成过程中出现异常
                                    window.clearInterval(loadInterval);
                                    $.messager.progress('close');
                                    $.messager.alert('导出信息失败', "导出信息失败", 'error');
                                } else if (result == 'empty') {//导出文件为空
                                    window.clearInterval(loadInterval);
                                    $.messager.progress('close');
                                    $.messager.show({title: '导出数据', msg: '没有可导出的数据!'});
                                } else {//更新进度条
                                    p.progressbar('setValue', result);
                                }
                            }, error: function (request, textStatus, errorThrown) {
                                window.clearInterval(loadInterval);
                                $.messager.progress('close');
                                $.messager.alert('导出信息失败', request.responseText, 'error');
                            }
                        });
                    }, 1000);
                },
                error: function (request, textStatus, errorThrown) {
                    $.messager.alert('导出信息失败', request.responseText, 'error');
                }
            });
        },
        //报表导出excel
        ExpReport: function (expfileName) {
            var JsonArray = [];
            $(this).find("thead,tbody").each(function () {
                $(this).find("tr").each(function () {
                    var trJson = [];
                    $(this).find("th,td").each(function (i) {//遍历每行所有的th,td
                        var type = $(this).is("th") ? 'th' : 'td';
                        var tdJson = {type: type,
                            text: $(this).text()};
                        if (undefined != $(this).attr("colspan")) {
                            tdJson["colspan"] = $(this).attr("colspan");
                        }
                        if (undefined != $(this).attr("rowspan")) {
                            tdJson["rowspan"] = $(this).attr("rowspan");
                        }
                        if (undefined != $(this).attr("width")) {
                            tdJson["width"] = $(this).attr("width");
                        }
                        if (undefined != $(this).attr("align")) {
                            tdJson["align"] = $(this).attr("align");
                        }
                        if (undefined != $(this).attr("valign")) {
                            tdJson["valign"] = $(this).attr("valign");
                        }
                        if (undefined != $(this).attr("class")) {
                            tdJson["class"] = $(this).attr("class");
                        }
                        if (undefined != $(this).attr("style")) {
                            tdJson["style"] = $(this).attr("style");
                        }
                        tdJson["rowIndex"] = this.parentNode.rowIndex;
                        tdJson["cellIndex"] = this.cellIndex;
                        trJson.push(tdJson);
                    })
                    JsonArray.push(trJson);
                });
            });
            var forminfo = {"tableJson": $.toJSON(JsonArray)};
            if (undefined != expfileName) {
                forminfo["expfileName"] = expfileName;
            }
            //把生成json传入后台
            $.ajax({
                type: "POST",
                url: "createrReports.exp",
                dataType: "text",
                data: forminfo,
                async: false,
                success: function (result, textStatus) {
                    var expForm = $('<form action="downFile.exp" method="post" />');
                    $("<input type='hidden' id='expfileName' name='expfileName' value='" + result + "'/>").appendTo(expForm);
                    expForm.appendTo("body");
                    expForm.submit();
                    expForm.remove();
                },
                error: function (request, textStatus, errorThrown) {
                    $.messager.alert('导出信息失败', request.responseText, 'error');
                }
            })
        }
    });
})(jQuery);

$(function () {
    $(".easyui-datagrid").each(function () {
        var datagridObj = $(this);
        options = datagridObj.datagrid("options");
        var exp = options.exp;
        var expPage = options.expPage;
        var buttons;
        if (exp && expPage) {
            buttons = [
                {
                    iconCls: 'icon-expPage',
                    text: '导出当前页',
                    handler: function () {
                        $(datagridObj).serializeExpPageJson();
                    }},
                '-',
                {
                    iconCls: 'icon-exp',
                    text: '导出全部',
                    handler: function () {
                        $(datagridObj).serializeExpJson();
                    }},
                '-'
            ]
        } else if (exp) {
            buttons = [
                {
                    iconCls: 'icon-exp',
                    text: '导出全部',
                    handler: function () {
                        $(datagridObj).serializeExpJson();
                    }},
                '-'
            ]
        } else if (expPage) {
            buttons = [
                {
                    iconCls: 'icon-exp',
                    text: '导出当前页',
                    handler: function () {
                        $(datagridObj).serializeExpPageJson();
                    }},
                '-'
            ]
        }
        var pager = datagridObj.datagrid('getPager');	// get the pager of datagrid
        pager.pagination({buttons: buttons});
    });
});
