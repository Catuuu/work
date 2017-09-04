//控件处理
;(function ($, window, document, undefined) {
    //定义UITools的构造函数
    var UITools = function (ele, opt) {
        this.$element = ele,
            this.defaults = {
                'iconCls': 'icon-cur',
                'selected': true,
                'name': '',//表单要提交的参数名字
                'status': 0,//默认选中
                'custom': {'p1': 0,'p2':''}//定制参数：1设置空字符
            },
            this.options = $.extend({}, this.defaults, opt)
    }
    //定义UITools的方法
    UITools.prototype = {
        tab_tool: function () {
            var name = this.options.name;
            var custom = this.options.custom;
            var element = this.$element;
            return $("[data-status=" + this.options.status + "]").linkbutton({
                iconCls: this.options.iconCls,
                selected: this.options.selected
            }),

                element.click(function () {
                    $(element).each(function () {
                        $(element).linkbutton({
                            iconCls: ''
                        });
                    });
                    $(this).linkbutton({
                        iconCls: 'icon-cur'
                    });

                    var status = $(this).data("status");
                    if (custom.p1 == 1 && status == 0) {
                        status = custom.p2;
                    }

                    $("#" + name).val(status);
                    loaddata();
                });

        }
    }
    //在插件中使用UITools对象
    $.fn.UI_tab = function (options) {
        //创建UITools的实体
        var UI = new UITools(this, options);
        //调用其方法
        return UI.tab_tool();
    };
})(jQuery, window, document);