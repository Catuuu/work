/**
 * Created by IntelliJ IDEA.
 * User: chenbin
 * Date: 12-8-14
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
(function ($) {
    // 调用方法：$("#apDiv").setApDiv();
    $.fn.extend({
        rsMenus: null,
        rsLoadMenus: function (options) {
            return this.each(function () {
                var defaults = {
                    mSecond: null,
                    mData: null,
                    mTabs: 'tabs'
                };

                //设置参数合并
                var opts = $.extend({}, defaults, options);
                var menusObj = $(this);

                //主菜单加载
                var _mainMenus = function (obj) {
                    $(obj).each(function (i, n) {
                        var mainMenus = $("<a href='#' order='0'><span class='iconview icon-menu_1' style='width:19px;'></span>" + n.text + "</a>");
                        $(mainMenus).attr("class", "easyui-linkbutton").attr("order", i);
                        if (undefined != n.iconCls) {
                            $(mainMenus).find("span:first-child").removeClass().addClass('iconview ' + n.iconCls);
                        }
                        $(mainMenus).bind("click", function () {
                            var order = $(this).attr("order");
                            if (null == opts.mSecond) {
                                _navMenus(menusObj, $.rsMenus[order].children);
                            } else {
                                _navMenus(opts.mSecond, $.rsMenus[order].children);
                            }
                        });
                        menusObj.append(mainMenus);
                        $.parser.parse(menusObj);
                    });
                };

                //导航菜单加载
                var _navMenus = function (obj, data) {
                    _removeAccordion(obj);
                    $(obj).accordion({animate: false});
                    $(data).each(function (i, n) {
                        var treeMenu = $("<ul></ul>");
                        $(treeMenu).tree({
                            data: n.children,
                            onClick: function (node) {
                                var arr = $(treeMenu).tree('getChildren', node.target);
                                if (arr.length == 0) {
                                    _addTab(node.id, node.text, node.attributes.url, node.iconCls);
                                }
                            }
                        });
                        var iconCls = "icon-menu_2";
                        if (undefined != n.iconCls) {
                            iconCls = n.iconCls;
                        }
                        $(obj).accordion('add', {
                            title: n.text,
                            content: treeMenu,
                            iconCls: iconCls
                        });
                    });

                    //选中第一个
                    $(obj).accordion('select', 0);

                    //修改导航菜单题头信息
                    var west = $('#westDiv').prev();
                    west.find(".panel-title").html(data.text);
                    if (undefined != data.iconCls) {
                        west.find(".panel-icon").removeClass().addClass("panel-icon").addClass(data.iconCls);
                    } else {
                        west.find(".panel-icon").removeClass().addClass("panel-icon").addClass("icon icon-24");
                    }
                };

                //添加tab
                var _addTab = function (id, tabTitle, url, icon) {
                    var tabsObj = $('#' + opts.mTabs);
                    if (tabsObj.size() > 0) {
                        var tabObj = $(tabsObj).find('div[id=' + id + ']');
                        if (tabObj.size() == 0) {
                            $(tabsObj).tabs('add', {
                                id: id,
                                title: tabTitle,
                                content: '<iframe id="iframe' + id + '" name="iframe' + id + '" scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>',
                                closable: true,
                                iconCls: icon
                            });
                            $(tabsObj).rsTabEven();
                        } else {
                            $(tabsObj).tabs('select', $(tabsObj).tabs('getTabIndex', tabObj));
                            $('#tm-update').click();  //刷新内容
                        }
                    }
                };

                //移除左边菜单项
                var _removeAccordion = function (obj) {
                    var pan = $(obj).accordion("panels");
                    $(pan).each(function (i, n) {
                        var title = n.panel("options").title
                        $(obj).accordion("remove", title);
                    });
                }

                //菜单数据加载入口
                if (null != opts.mData) {
                    $.rsMenus = opts.mData;
                    if (null == opts.mSecond) {
                        _navMenus(menusObj, $.rsMenus);
                    } else {
                        _mainMenus($.rsMenus);
                        _navMenus(opts.mSecond, $.rsMenus[0].children);
                    }
                    $.rsLoadTabMenu();
                    var tabsObj = $('#' + opts.mTabs);
                    if (tabsObj.size() > 0) {
                        $(tabsObj).rsTabEven();
                    }
                }
            });
        },
        rsTabEven: function (options) {
            return this.each(function () {
                var defaults = {
                    tabMenu: 'tabMenu'
                };
                //设置参数合并
                var opts = $.extend({}, defaults, options);
                var tabObj = $(this);
                var tabMenu = $('#' + opts.tabMenu);
                $(".tabs-inner").unbind("dblclick").bind('dblclick',function () {
                    var indexN = $(".tabs-inner").index($(this));
                    if (indexN != 0)
                        tabObj.tabs('close', indexN);
                }).unbind("contextmenu").bind('contextmenu', function (e) {
                        e.preventDefault();
                        var indexN = $(".tabs-inner").index($(this));
                        tabObj.tabs('select', indexN);
                        if (indexN != 0) {
                            tabMenu.menu('show', {
                                left: e.pageX,
                                top: e.pageY
                            });
                            $(tabMenu).data("currtab", indexN);
                            return false;
                        }
                    });
            });
        },
        rsTabMenuEven: function (options) {
            return this.each(function () {
                var defaults = {
                    tabId: 'tabs'
                };
                //设置参数合并
                var opts = $.extend({}, defaults, options);
                var tabMenu = $(this);
                var tabObj = $('#' + opts.tabId);
                //刷新
                $('#tm-update').click(function () {
                    var currTab = $(tabObj).tabs('getSelected');
                    var url = $(currTab.panel('options').content).attr('src');
                    $(tabObj).tabs('update', {
                        tab: currTab,
                        options: {
                            content: '<iframe  id="iframe' + currTab.attr("id") + '" name="iframe' + currTab.attr("id") + '" scrolling="auto" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>'
                        }
                    })
                })
                //关闭当前
                $('#tm-close').click(function () {
                    var indexN = $(tabMenu).data("currtab");
                    $(tabObj).tabs('close', indexN);
                })
                //全部关闭
                $('#tm-closeall').click(function () {
                    $('.tabs-inner span').each(function (i, n) {
                        $(tabObj).tabs('close', 1);
                    });
                });
                //关闭除当前之外的TAB
                $('#tm-closeother').click(function () {
                    $('#tm-closeright').click();
                    $('#tm-closeleft').click();
                });
                //关闭当前右侧的TAB
                $('#tm-closeright').click(function () {
                    var tab = $(tabObj).tabs('getSelected');
                    var index = $(tabObj).tabs('getTabIndex', tab);
                    var nextall = $('.tabs-selected').nextAll();
                    if (nextall.length == 0) {
                        $.messager.alert("提示", "后边没有啦~~", "info");
                        return false;
                    }
                    nextall.each(function (i, n) {
                        $(tabObj).tabs('close', index + 1);
                    });
                    $(tabObj).tabs('select', index);
                    return false;
                });
                //关闭当前左侧的TAB
                $('#tm-closeleft').click(function () {
                    var tab = $(tabObj).tabs('getSelected');
                    var index = $(tabObj).tabs('getTabIndex', tab);
                    var prevall = $('.tabs-selected').prevAll();
                    if (prevall.length == 1) {
                        $.messager.alert("提示", "到头了，前边没有啦~~", "info");
                        return false;
                    }
                    prevall.each(function (i, n) {
                        if (i != 0)
                            $(tabObj).tabs('close', 1);
                    });
                    $(tabObj).tabs('select', 1);
                    return false;
                });
            });
        }
    });

    // 调用方法：$.function();
    $.extend({
        rsLoadTabMenu: function () {
            var tabMenuHtml = '<div id="_t_1_m">';
            tabMenuHtml += '<div id="tabMenu" class="easyui-menu" style="width:100px;display:none">';
            tabMenuHtml += '<div id="tm-update" iconCls="icon-reload">刷新</div>';
            tabMenuHtml += '<div class="menu-sep"></div>';
            tabMenuHtml += '<div id="tm-close" iconCls="icon-closeself">关闭</div>';
            tabMenuHtml += '<div id="tm-closeall" iconCls="icon-closeall">全部关闭</div>';
            tabMenuHtml += '<div id="tm-closeother" iconCls="icon-closeother">除此之外全部关闭</div>';
            tabMenuHtml += '<div class="menu-sep"></div>';
            tabMenuHtml += '<div id="tm-closeright" iconCls="icon-closeright">当前页右侧全部关闭</div>';
            tabMenuHtml += '<div id="tm-closeleft" iconCls="icon-closeleft">当前页左侧全部关闭</div>';
            tabMenuHtml += '</div></div>';
            if ($('#tabMenu').size() == 0) {
                $('body').append(tabMenuHtml);
                $.parser.parse($('#_t_1_m'));
            }
            $('#tabMenu').rsTabMenuEven();
        }
    });
})(jQuery);