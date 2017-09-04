/**
 * Created by IntelliJ IDEA.
 * User: chenbin
 * Date: 12-8-14
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */

//日期格式化函数
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}


/**
 日期添加函数
 **/
function addDate(date, days) {
    var d = new Date(date);
    d.setDate(d.getDate() + days);
    var month = d.getMonth() + 1;
    var day = d.getDate();
    if (month < 10) {
        month = "0" + month;
    }
    if (day < 10) {
        day = "0" + day;
    }
    var val = d.getFullYear() + "-" + month + "-" + day;
    return val;
}



(function ($) {
    //如下为easyui中文语言包-----开始
    if ($.fn.pagination) {
        $.fn.pagination.defaults.beforePageText = '第';
        $.fn.pagination.defaults.afterPageText = '共{pages}页';
        $.fn.pagination.defaults.displayMsg = '显示{from}到{to},共{total}记录';
    }
    if ($.fn.datagrid) {
        $.fn.datagrid.defaults.loadMsg = '正在处理，请稍待。。。';
    }
    if ($.fn.treegrid && $.fn.datagrid) {
        $.fn.treegrid.defaults.loadMsg = $.fn.datagrid.defaults.loadMsg;
    }
    if ($.messager) {
        $.messager.defaults.ok = '确定';
        $.messager.defaults.cancel = '取消';
    }
    if ($.fn.validatebox) {
        $.fn.validatebox.defaults.missingMessage = '该输入项为必输项';
        $.fn.validatebox.defaults.rules.email.message = '请输入有效的电子邮件地址';
        $.fn.validatebox.defaults.rules.url.message = '请输入有效的URL地址';
        $.fn.validatebox.defaults.rules.length.message = '输入内容长度必须介于{0}和{1}之间';
        $.fn.validatebox.defaults.rules.remote.message = '请修正该字段';
    }
    if ($.fn.numberbox) {
        $.fn.numberbox.defaults.missingMessage = '该输入项为必输项';
    }
    if ($.fn.combobox) {
        $.fn.combobox.defaults.missingMessage = '该输入项为必输项';
    }
    if ($.fn.combotree) {
        $.fn.combotree.defaults.missingMessage = '该输入项为必输项';
    }
    if ($.fn.combogrid) {
        $.fn.combogrid.defaults.missingMessage = '该输入项为必输项';
    }
    if ($.fn.calendar) {
        $.fn.calendar.defaults.weeks = ['日', '一', '二', '三', '四', '五', '六'];
        $.fn.calendar.defaults.months = ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'];
    }
    if ($.fn.datebox) {
        $.fn.datebox.defaults.currentText = '今天';
        $.fn.datebox.defaults.closeText = '关闭';
        $.fn.datebox.defaults.okText = '确定';
        $.fn.datebox.defaults.missingMessage = '该输入项为必输项';
        $.fn.datebox.defaults.formatter = function (date) {
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            var d = date.getDate();
            return y + '-' + (m < 10 ? ('0' + m) : m) + '-' + (d < 10 ? ('0' + d) : d);
        };
        $.fn.datebox.defaults.parser = function (s) {
            if (!s) return new Date();
            var ss = s.split('-');
            var y = parseInt(ss[0], 10);
            var m = parseInt(ss[1], 10);
            var d = parseInt(ss[2], 10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
                return new Date(y, m - 1, d);
            } else {
                return new Date();
            }
        };
    }
    if ($.fn.datetimebox && $.fn.datebox) {
        $.extend($.fn.datetimebox.defaults, {
            currentText: $.fn.datebox.defaults.currentText,
            closeText: $.fn.datebox.defaults.closeText,
            okText: $.fn.datebox.defaults.okText,
            missingMessage: $.fn.datebox.defaults.missingMessage
        });
    }
    //如上为easyui中文语言包-----结束

    //自定义的默认值
    if ($.fn.linkbutton) {
        $.fn.linkbutton.defaults.plain = true;
    }
    if ($.fn.datagrid) {
        $.extend($.fn.datagrid.defaults, {
            striped: true,
            rownumbers: true,
            checkOnSelect: false,
            selectOnCheck: false,
            singleSelect: true,
            remoteSort: true,
            pagination: true,
            fit: true,
            pageList: [10, 15, 20, 30, 50],
            pageSize: 15,
            border: false,
            exp: false,
            expPage: false,
            onDblClickRow: function (rowIndex, rowData) {
                if (typeof(onDblClickRow) == "function") {
                    onDblClickRow(rowIndex, rowData);
                }
            },
            onLoadSuccess: function (data) {
                if (typeof(onLoadSuccess) == "function") {
                    onLoadSuccess(data);
                }
            },
            onClickRow: function (rowIndex, rowData) {
                if (typeof(onClickRow) == "function") {
                    onClickRow(rowIndex, rowData);
                }
            },
            onLoadError: function () {
                $.postJSON("/login/isLogon", {}, function (jsonMessage) {
                    if (jsonMessage.status == 1) {
                        $.messageError("数据加载失败！可能原因如下：<br>1、服务器资源不存在。<br>2、系统出现异常。");
                    }
                });
            }
        });
    }

    if ($.fn.treegrid) {
        $.extend($.fn.treegrid.defaults, {
            onDblClickRow: function (row) {
                if (typeof(onDblClickRow) == "function") {
                    onDblClickRow(row);
                }
            },
            onLoadSuccess: function (row, data) {
                if (typeof(onLoadSuccess) == "function") {
                    onLoadSuccess(row, data);
                }
            },
            onClickRow: function (row) {
                if (typeof(onClickRow) == "function") {
                    onClickRow(row);
                }
            },
            onLoadError: function () {
                $.postJSON("/login/isLogon", {}, function (jsonMessage) {
                    if (jsonMessage.status == 1) {
                        $.messageError("数据加载失败！可能原因如下：<br>1、服务器资源不存在。<br>2、系统出现异常。");
                    }
                });
            }
        });
    }
    ;

    //页面初始化内容
    $(function () {
        //表格样式
        $(".editTable tr:odd").addClass('editTableOdd');
        $(".editTable tr:even").addClass('editTableEven');
        $(".editTable tr td[nowrap]").addClass('nowrap');
        $(".editTable tr td:not([nowrap])").addClass('content');

        //默认的关闭窗口按钮
        $("#colseWin").click(function () {
            $.closeWin();
        });
        $("#editSave").click(function () {
            var obj = $(this);
            $(obj).linkbutton('disable');
            setTimeout(function () {
                $(obj).linkbutton('enable');
            }, 5000);
        });
        $("#addSave").click(function () {
            var obj = $(this);
            $(obj).linkbutton('disable');
            setTimeout(function () {
                $(obj).linkbutton('enable');
            }, 5000);
        });

        //键盘操作
        $(document).keydown(function (event) {
            var btnObjs = $(document).find("[enterkey]");
            $.each(btnObjs, function () {
                if ($(this).attr("enterkey") == event.keyCode) {
                    $(this).click();
                }
                return;
            });

            btnObjs = $("#selectForm").find("[enterkey]");
            $.each(btnObjs, function () {
                if ($(this).attr("enterkey") == event.keyCode) {
                    $(this).click();
                }
            });
        });
    });


    $.extend($.fn.datagrid.methods, {
        autoMergeCells: function (jq, fields) {
            return jq.each(function () {
                var target = $(this);
                if (!fields) {
                    fields = target.datagrid("getColumnFields");
                }
                var rows = target.datagrid("getRows");
                var i = 0,
                    j = 0,
                    temp = {};
                for (i; i < rows.length; i++) {
                    var row = rows[i];
                    j = 0;
                    for (j; j < fields.length; j++) {
                        var field = fields[j];
                        var tf = temp[field];
                        if (!tf) {
                            tf = temp[field] = {};
                            tf[row[field]] = [i];
                        } else {
                            var tfv = tf[row[field]];
                            if (tfv) {
                                tfv.push(i);
                            } else {
                                tfv = tf[row[field]] = [i];
                            }
                        }
                    }
                }
                $.each(temp, function (field, colunm) {
                    $.each(colunm, function () {
                        var group = this;

                        if (group.length > 1) {
                            var before,
                                after,
                                megerIndex = group[0];
                            for (var i = 0; i < group.length; i++) {
                                before = group[i];
                                after = group[i + 1];
                                if (after && (after - before) == 1) {
                                    continue;
                                }
                                var rowspan = before - megerIndex + 1;
                                if (rowspan > 1) {
                                    target.datagrid('mergeCells', {
                                        index: megerIndex,
                                        field: field,
                                        rowspan: rowspan
                                    });
                                }
                                if (after && (after - before) != 1) {
                                    megerIndex = after;
                                }
                            }
                        }
                    });
                });
            });
        }
    });

    //Ajax异常错误处理
    function ajaxPostError(request, textStatus, errorThrown) {
        try {
            var json = $.parseJSON(request.responseText);
            if (json.exceptionName == 'com.framework.exception.NotLoginException') {
                top.$.messager.alert('登录超时', json.exceptionMessage, 'warning', function () {
                    window.top.location.href = "/login/userLogin";
                });
            } else if (json.exceptionName == 'com.framework.exception.NotAccessException') {
                top.$.messager.alert('信息提示', json.exceptionMessage, 'warning');
            } else if (json.exceptionName == 'ErrorCode404') {
                $.openWin({
                    name: 'Error404Win',
                    title: "Error404",
                    iconCls: "icon-exception",
                    url: "/WEB-INF/jsp/error/error404.jsp"});
            } else {
                $.openWin({
                    name: 'Error500Win',
                    title: "Error500",
                    iconCls: "icon-exception",
                    url: "/system/exceptionError?exception=" + request.responseText});
            }
        } catch (e) {
            $.messageError("操作出现异常，请联系管理员！")
        }
    }

    $.fn.extend({
        /**
         * 提交一个Form表单
         * @param url
         * @param callback
         * @return {*}
         */
        postForm: function (url, callback) {
            if (!$(this).form('validate')) {
                return;
            }
            return jQuery.ajax({
                'type': 'POST',
                'url': url,
                'data': $(this).serializeJson(),
                'dataType': 'json',
                'success': callback,
                'error': ajaxPostError
            });
        },
        /**
         * 序列化表单对象
         */
        serializeJson: function () {
            var sj = {};
            //给对象赋值
            var callByValue = function (obj, key, value) {
                if (obj.hasOwnProperty(key)) {
                    if ($.type(obj[key]) != 'array') {
                        obj[key] = [obj[key]];
                    }
                    obj[key].push(value);
                }
                else if (key != undefined) {
                    obj[key] = value;
                }
            };
            //元素提取
            var elementHandling = function (tag, data) {
                var tagName = $(tag)[0].tagName;
                var key = $(tag).attr('name');
                if ("INPUT" == tagName) {
                    //查找所有可用的input元素,且不为submit，reset，button
                    if ($(tag).is(':text,:password,:hidden')) {
                        callByValue(data, key, $(tag).val());
                    } else if ($(tag).is(':radio:checked,:checkbox:checked')) {
                        callByValue(data, key, $(tag).val());
                    }
                } else if ("TEXTAREA" == tagName) {
                    callByValue(data, key, $(tag).val());
                } else if ("SELECT" == tagName) {
                    callByValue(data, key, $(tag).val());
                }
            };
            //递归处理数据
            var loopHandling = function (tagObj, dataObj) {
                //判断当前对象是否为表单元素 input, textarea, select 和 button
                if ($(tagObj).is(':input')) {
                    elementHandling(tagObj, dataObj);
                } else {
                    var child = $(tagObj).children();
                    //判断此元素下是否还有子元素，有则继续遍历
                    if (child.size() > 0) {
                        var tempData = dataObj;
                        //判断此对象是否存在groupkey标识
                        if ($(tagObj).is('[groupkey]')) {
                            tempData = {};
                            callByValue(dataObj, $(tagObj).attr('groupkey'), tempData);
                        }

                        $(child).each(function (i, item) {
                            loopHandling(item, tempData);
                        });
                    }
                }
            };
            loopHandling($(this), sj);
            return sj;
        },
        serializeSelectJson: function (tablePageID, params) {   //将查询域的值赋给 分页table 的 queryParams
            if (tablePageID != undefined) {
                var queryParams = $(this).serializeJson();
                $.extend(queryParams, params);
                $('#' + tablePageID).datagrid("load", queryParams);
            } else {
                return;
            }
        },
        serializeSelectClear: function (tablePageID) {   //将查询域的值赋给 分页table 的 queryParams
            $(this).form('clear');
            $(this).serializeSelectJson(tablePageID);
            return null;
        },
        openWin:function (options) {
            var winObject = $(this);
            winObject.mask();
            var defaults = {
                name: '_pop_win',
                url: '###',
                width: 500, //对象宽度
                height: 400, //对象高度
                title: '编辑窗口', //标题
                inline: false,
                closable: true,
                modal: true,
                left: null,
                top: null,
                iconCls: 'icon-edit'
            };
            //设置参数合并
            var opts = $.extend({}, defaults, options);

            if (winObject.find('#' + opts.name).size() == 0) {
                winObject.append('<div id="' + opts.name + '" style="overflow:hidden"><iframe id="' + opts.name + '_iframe" name="' + opts.name + '_iframe" src="###" frameborder="0" style="width:100%;height:100%"></iframe></div>')
            }

            var iframe = winObject.find("#" + opts.name + "_iframe")[0];

            if (iframe.attachEvent) {//IE执行方式
                iframe.attachEvent("onload", function () {
                    if (iframe.src != '###') {
                        winObject.mask("hide");
                    }
                });
            } else {//其他非IE的浏览器上 Firefox,Opera,chrome等
                iframe.addEventListener("load", function () {
                    winObject.mask("hide");
                }, false);
            }
            $(iframe).attr('src', opts.url);
            top.openWindow = opts.name;//当前打开窗口的名称,在Error.jsp中用到来关闭当前的窗体
            winObject.find("#" + opts.name).hide().window({
                minimizable: false,
                resizable: false,
                maximizable: false,
                collapsible: false,
                inline: opts.inline,
                closable: opts.closable,
                width: opts.width,
                height: opts.height,
                title: opts.title,
                modal: opts.modal,
                left: opts.left,
                top: opts.top,
                iconCls: opts.iconCls,
                onClose: function () {
                    $(this).find("iframe").attr('src', "###");
                }
            }).show();
        }
    });


    // 调用方法：$.function();
    $.extend({
        //打开一个窗体
        openWin: function (options) {
            var winObject = top.$('body');
            winObject.mask();
            var defaults = {
                name: '_pop_win',
                url: '###',
                width: 500, //对象宽度
                height: 400, //对象高度
                title: '编辑窗口', //标题
                inline: false,
                closable: true,
                modal: true,
                left: null,
                top: null,
                iconCls: 'icon-edit'
            };
            //设置参数合并
            var opts = $.extend({}, defaults, options);

            if (winObject.find('#' + opts.name).size() == 0) {
                winObject.find("#_window_div").html('<div id="' + opts.name + '"><iframe id="' + opts.name + '_iframe" name="' + opts.name + '_iframe" src="###" frameborder="0" style="width:100%;height:100%"></iframe></div>')
            }

            var iframe = winObject.find("#" + opts.name + "_iframe")[0];

            if (iframe.attachEvent) {//IE执行方式
                iframe.attachEvent("onload", function () {
                    if (iframe.src != '###') {
                        winObject.mask("hide");
                    }
                });
            } else {//其他非IE的浏览器上 Firefox,Opera,chrome等
                iframe.addEventListener("load", function () {
                    winObject.mask("hide");
                }, false);
            }
            $(iframe).attr('src', opts.url);
            top.openWindow = opts.name;//当前打开窗口的名称,在Error.jsp中用到来关闭当前的窗体
            winObject.find("#" + opts.name).hide().window({
                minimizable: false,
                resizable: false,
                maximizable: false,
                collapsible: false,
                inline: opts.inline,
                closable: opts.closable,
                width: opts.width,
                height: opts.height,
                title: opts.title,
                modal: opts.modal,
                left: opts.left,
                top: opts.top,
                iconCls: opts.iconCls,
                onClose: function () {
                    $(this).find("iframe").attr('src', "###");
                }
            }).show();

        },//关闭一个窗体
        closeWin: function (winName) {
            var n = '_pop_win';
            if (winName != null) n = winName;
            if(window.parent.$('#' + n) != undefined){
                window.parent.$('#' + n).window('close');
                window.parent.$('body').mask("hide");
            }
            if(top.$('#' + n)!=undefined){
                top.$('#' + n).window('close');
                top.$('body').mask("hide");
            }
        },
        closeWinAndMessager: function (message, winName) {
            $.closeWin(winName);
            $.messageShow(message);
        },
        /**
         * @param message
         * @param refresh 参数值('load'、'reload') load:载入并显示第一页的记录  reload重载记录，跟'load'方法一样但是重载的是当前页的记录而非第一页。
         * @param winName
         */
        closeWinAndRefresh: function (message, refresh, winName) {
            if (refresh != null) {
                $.getTabFrmContent().$(".easyui-datagrid").datagrid(refresh);
            }
            $.closeWinAndMessager(message, winName);
        },
        getTabFrmContent: function () {
            var tab = window.top.$('#tabs').tabs('getSelected');
            var tbId = tab.attr("id");
            var name = 'iframe' + tbId;
            var frmContent;
            $.each(top.frames, function (index) {
                if (this.name == name) {
                    frmContent = this;
                }
            });
            return frmContent;
        },
        getWindowContent: function (winName) {
            if (winName == null) {
                winName = '_pop_win_iframe';
            } else {
                winName = winName + '_iframe';
            }
            var winContent;
            $.each(top.frames, function (index) {
                if (this.name == winName) {
                    winContent = this;
                }
            });
            return winContent;
        },
        messageInfo: function (message) {
            if (message == null) {
                message = '请确认操作！'
            }
            top.$.messager.alert('信息提示', message, 'info');
        },
        messageError: function (message) {
            if (message == null) {
                message = '操作失败！'
            }
            top.$.messager.alert('信息提示', message, 'error');
        },
        messageShow: function (message) {
            if (message == null) {
                message = '操作成功！'
            }
            top.$.messager.show({title: '提示信息', msg: message});
        },
        lockScreen: function (maskMasg) {
            if (null == maskMasg)
                top.$('body').mask();
            else
                top.$('body').mask({maskMsg: maskMasg});
        },
        unLockScreen: function () {
            top.$('body').mask("hide");
        },
        postForm: function (url, data, callback) {
            return jQuery.ajax({
                'type': 'POST',
                'url': url,
                'data': data,
                'dataType': 'json',
                'success': callback,
                'error': ajaxPostError
            });
        },
        postJSON: function (url, data, callback) {
            return jQuery.ajax({
                'type': 'POST',
                'contentType': 'application/json',
                'url': url,
                'data': $.toJSON(data),
                'dataType': 'json',
                'success': callback,
                'error': ajaxPostError
            });
        }
    });
})(jQuery);
