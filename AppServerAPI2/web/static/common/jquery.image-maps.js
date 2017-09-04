(function ($) {
    $.fn.map_opts = {
        closed:function (obj) {
            return true;
        },
        onClick:function (obj) {
            return true;
        },
        onSelect:function (obj) {
            return true;
        },
        width:70,
        height:30,
        operateDiv:12, //操作块的大小
        isEdit:false   //是否可编辑
    };
    jQuery.fn.imageMaps = function (options) {
        $.fn.map_opts = $.extend($.fn.map_opts, options);
        var $container = this;
        $(this).addClass("position-conrainer").css({
            position:'absolute'
        });
        var _img_offset;//图片的偏移位置
        if ($container.length == 0) return false;
        $container.each(function () {
            var container = $(this);
            var $images = container.find('img[ref=imageMaps]');
            $images.each(function () {
                _img_offset = $(this).offset();//获取匹配元素在当前视口的相对偏移。
                var map_name = $(this).attr('usemap').replace('#', '');
                if (map_name != '') {
                    container.find('map[name=' + map_name + ']').find('area[postion=true]').each(function () {
                        var coords = $(this).attr('coords');
                        coords = coords.split(',');
                        coords[0] = parseFloat(coords[0]) + _img_offset.left;
                        coords[1] = parseFloat(coords[1]) + _img_offset.top;
                        coords[2] = parseFloat(coords[2]) + _img_offset.left;
                        coords[3] = parseFloat(coords[3]) + _img_offset.top;
                        var ref = $(this).attr('ref');
                        iconCls
                        var title = $(this).attr('subtitle');
                        var iconCls = $(this).attr('iconCls');
                        var toolTip = $(this).attr('toolTip');
                        if ($.fn.map_opts.isEdit) {//是否可以编辑
                            //添加移动层
                            container.append('<div ref="' + ref + '" subtitle="' + title + '" iconCls="' + iconCls + '" toolTip="' + toolTip + '" class="map-position" style="left:' + coords[0] + 'px;top:' + coords[1] + 'px;width:' + ($.fn.map_opts.width + $.fn.map_opts.operateDiv) + 'px;height:' + $.fn.map_opts.height + 'px;"><div class="map-position-bg"></div><div class="map-position-move">ж</div><span class="delete">X</span><div class="map-content"><img src="' + iconCls + '"><br>' + title + '</div></div>');
                        } else {
                            container.append('<div ref="' + ref + '" subtitle="' + title + '" iconCls="' + iconCls + '" toolTip="' + toolTip + '" class="map-position" style="left:' + coords[0] + 'px;top:' + coords[1] + 'px;width:' + $.fn.map_opts.width + 'px;height:' + $.fn.map_opts.height + 'px;"><div class="map-content"><img src="' + iconCls + '"><br>' + title + '</div><div class="toolTipDiv"><div class="toolTip">' + toolTip + '</div></div></div>');
                        }
                    });
                }
            });

        });
        $container.find('.map-position').each(function () {
            bind_map_event_p($(this));//绑定事件
            define_css_p($(this));//添加样式
        });
    };
    //添加一个节点
    jQuery.fn.imageAdd = function (options) {
        var add_options = {
            ref:'new_1',
            subtitle:'新节点',
            iconCls:'img/11.png', //图标
            toolTip:''   //提示信息
        };
        add_options = $.extend(add_options, options);
        $container = $(this);
        if ($.fn.map_opts.isEdit) {//是否可以移动
            var map_position = $container.find('.map-position[ref=' + add_options.ref + ']');
            if (map_position.length == 0) {
                //添加移动层
                $container.append('<div ref="' + add_options.ref + '" subtitle="' + add_options.title + '" iconCls="' + add_options.iconCls + '" toolTip="' + add_options.toolTip + '" class="map-position" style="left:0px;top:0px;width:' + ($.fn.map_opts.width + $.fn.map_opts.operateDiv) + 'px;height:' + $.fn.map_opts.height + 'px;"><div class="map-position-bg"></div><div class="map-position-move">ж</div><span class="delete">X</span><div class="map-content" ><img src="' + add_options.iconCls + '"><br>' + add_options.title + '</div></div>');
            } else {
                return "1";//已经存在
            }
        } else {
            return "2";//不能进行编辑
        }
        var map_position = $container.find('.map-position[ref=' + add_options.ref + ']');

        bind_map_event_p(map_position);//绑定事件
        define_css_p(map_position);//添加样式
    };

    //查询图片上的所有节点
    jQuery.fn.getPositions = function () {
        var resultJson = {};
        var mapPositions = $(this).find('.map-position');
        var conrainer_offset = $(this).offset();//得到DIV相对于窗口偏移。
        resultJson['length'] = mapPositions.length;
        var jsonArray = [];
        mapPositions.each(function () {
            position_offset = $(this).offset();//获取节点相对于窗口偏移。
            var json = {};
            json['ref'] = $(this).attr("ref");
            json['left'] = position_offset.left - conrainer_offset.left;
            json['top'] = position_offset.top - conrainer_offset.top;
            json['width'] = $(this).width() - $.fn.map_opts.operateDiv;
            json['height'] = $(this).height();
            jsonArray.push(json);
        });
        resultJson['postionList'] = jsonArray;
        return resultJson;
    };
    //删除一个节点
    jQuery.fn.imageDel = function (ref) {
        var map_position = $(this).find('.map-position[ref=' + ref + ']');
        map_position.remove();
    }
    //选中一个节点
    jQuery.fn.imageSelect = function (ref, isSelect) {
        $(this).find('.map-position').css({
            border:'1px solid #000'
        })
        var map_position = $(this).find('.map-position[ref=' + ref + ']');
        if (isSelect == true) {
            map_position.css({
                border:'2px solid #F00'
            })
        } else {
            map_position.css({
                border:'1px solid #000'
            })
        }
    }

})(jQuery);

//绑定map事件
function bind_map_event_p($position) {
    if ($.fn.map_opts.isEdit) {
        $position.find('.map-position-move').each(function () {
            var map_position_bg = $(this);
            var conrainer = $(this).parent().parent();
            map_position_bg.unbind('mousedown').mousedown(
                function (event) {//鼠标按下
                    map_position_bg.data('mousedown', true);
                    map_position_bg.data('pageX', event.pageX);
                    map_position_bg.data('pageY', event.pageY);
                    map_position_bg.css('cursor', 'move');
                    return false;
                }).unbind('mouseup').mouseup(
                function (event) {//鼠标弹起
                    map_position_bg.data('mousedown', false);
                    map_position_bg.css('cursor', 'default');
                    return false;
                }).unbind('mouseover').mouseover(function (event) {//鼠标指针位于元素上方
                    map_position_bg.css('cursor', 'pointer');
                    return false;
                });

            conrainer.mousemove(
                function (event) {
                    if (!map_position_bg.data('mousedown')) return false;
                    var dx = event.pageX - map_position_bg.data('pageX');
                    var dy = event.pageY - map_position_bg.data('pageY');
                    if ((dx == 0) && (dy == 0)) {
                        return false;
                    }
                    var map_position = map_position_bg.parent();
                    var p = map_position.position();
                    var left = p.left + dx;
                    if (left < 0) left = 0;
                    var top = p.top + dy;
                    if (top < 0) top = 0;
                    var bottom = top + map_position.height();
                    if (bottom > conrainer.height()) {
                        top = top - (bottom - conrainer.height());
                    }
                    var right = left + map_position.width();
                    if (right > conrainer.width()) {
                        left = left - (right - conrainer.width());
                    }
                    map_position.css({
                        left:left,
                        top:top
                    });
                    map_position_bg.data('pageX', event.pageX);
                    map_position_bg.data('pageY', event.pageY);
                    return false;
                }).mouseup(function (event) {
                    map_position_bg.data('mousedown', false);
                    map_position_bg.css('cursor', 'default');
                    return false;
                });
        });

        //删除图标节点事件
        $position.find('.delete').unbind('click').click(function () {
            var map_position = $(this).parent();
            if ($.fn.map_opts.closed(map_position.attr('ref'))) {
                map_position.remove();
            }

        });
        //选中事件
        $position.find('.map-content').unbind('click').click(function () {
            var container = $(this).parent().parent();
            container.find('.map-position').css({
                border:'1px solid #000'
            })
            var map_position = $(this).parent();
            map_position.css({
                border:'2px solid #F00'
            })
            $.fn.map_opts.onSelect(map_position.attr('ref'));
        });

    } else {
        //单击事件
        $position.find('.map-content img').unbind('click').click(function () {
            var map_position = $(this).parent().parent();
            var json = {
                ref:map_position.attr("ref"),
                subtitle:map_position.attr("subtitle"),
                iconCls:map_position.attr("iconCls"), //图标
                toolTip:map_position.attr("toolTip")   //提示信息
            }
            $.fn.map_opts.onClick(json);

        });
        $position.find('.map-content img').unbind('mouseover').mouseover(function (event) {//鼠标指针位于元素上方
            var _toolTip = $(this).parent().parent().find('.toolTipDiv');
            $(_toolTip).show();
            return false;
        })
        $position.find('.map-content img').unbind('mouseout').mouseout(function (event) {//鼠标指针离开元素上方
            var _toolTip = $(this).parent().parent().find('.toolTipDiv');
            $(_toolTip).hide();
            return false;
        })
    }
}


//单击对象样式
function define_css_p($position) {
    //样式定义
    $position.css({
        position:'absolute',
        border:$.fn.map_opts.isEdit ? '1px solid #000' : '0px solid #000'
    });
    //背景颜色
    $position.find('.map-position-bg').css({
        position:'absolute',
        background:'#0F0',
        opacity:0.5,
        top:0,
        left:0,
        right:0,
        bottom:0
    });
    //移动图标
    $position.find('.map-position-move').css({
        display:'block',
        position:'absolute',
        right:0,
        bottom:0,
        width:$.fn.map_opts.operateDiv,
        height:$.fn.map_opts.operateDiv,
        'line-height':'11px',
        'font-size':12,
        'font-weight':'bold',
        background:'#000',
        color:'#fff',
        'font-family':'Arial',
        'padding-left':'2px'
    });
    //关闭按钮
    $position.find('.delete').css({
        display:'block',
        position:'absolute',
        right:0,
        top:0,
        width:$.fn.map_opts.operateDiv,
        height:$.fn.map_opts.operateDiv,
        'line-height':'11px',
        'font-size':12,
        'font-weight':'bold',
        background:'#000',
        color:'#fff',
        'font-family':'Arial',
        'padding-left':'2px',
        cursor:'pointer',
        opactiey:1
    });

    //内容区域
    $position.find('.map-content').css({
        position:'absolute',
        'font-size':10,
        'text-align':'center',
        'padding-top':2,
        color:'#F00',
        width:$.fn.map_opts.width,
        height:$.fn.map_opts.height
    });

    //图标按钮
    $position.find('.map-content img').css({
        cursor:'pointer'
    });
    //提示tooltip容器
    $position.find('.toolTipDiv').css({
        display:'none',
        left:$.fn.map_opts.width - 20,
        top:-10,
        position:'absolute',
        'font-weight':'normal',
        width:400,
        height:40
    });
    //提示tooltip
    $position.find('.toolTipDiv .toolTip').css({
        background:'#EEEEEE',
        display:'block',
        position:'absolute',
        'font-size':12,
        padding:4,
        border:'2px solid #000'
    });
}