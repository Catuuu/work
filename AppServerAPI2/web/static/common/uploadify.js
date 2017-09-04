(function ($) {
    $(function () {
        $(".rs_uploadify").each(function (i, item) {
            var data_options = $(item).attr("data-options");
            if (data_options == undefined || data_options == "") {
                $(item).upLoadFile();
            } else {
                data_options = "{" + data_options + "}";
                $(item).upLoadFile($.evalJSON(data_options));
            }
        });
    });

    /** 通用效果渲染 */
    $.fn.upLoadFile = function (opts) {
        /*操作对象集*/
        var defaults = {
            file_show: "",
            file_input: "",
            isImg: false,
            download: false,
            del: true,
            multi: true,
            buttonText: '选择文件',
            fileTypeDesc: '所有文件',
            fileTypeExts: '*.*',
            height: 20,
            width: 80
        };

        //设置参数合并
        var opts = $.extend({}, defaults, opts);

        if (opts.file_show == "")  opts.file_show = $(this).attr("id") + "_show";
        if (opts.file_input == "") opts.file_input = $(this).attr("id") + "_input";
        if (opts.isImg) opts.multi = false;

        /*文件参数重设置*/
        var _resetFiles = function () {
            var _sf_ids = "";
            $('#' + opts.file_show + ' div').each(function (i, item) {
                if (_sf_ids == "") {
                    _sf_ids = $(item).attr('id');
                } else {
                    _sf_ids += ";" + $(item).attr('id');
                }
            });
            $('#' + opts.file_input).val(_sf_ids);
        };

        /*设置文件列表DIV*/
        var _queueSet = function () {
            var left = (Math.max(document.documentElement.clientWidth, document.body.clientWidth) - $('#_fileQueue')._outerWidth()) / 2;
            var top = (Math.max(document.documentElement.clientHeight, document.body.clientHeight) - $('#_fileQueue')._outerHeight()) / 2;
            $('#_fileQueue').css({
                border: '0px solid',
                overflow: 'auto',
                position: 'absolute',
                zIndex: 200000,
                left: left,
                top: top
            });
        }

        /*文件行处理*/
        var _fileRow = function (files) {
            $('#' + files + ' div:odd').css("background", "#FDE5DD");
            $('#' + files + ' div:even').css("background", "");
        }

        /*文件删除*/
        var _fileRemove = function () {
            $(this).parent().remove();
            _fileRow(opts.file_show);
            _resetFiles();
        }

        /*文件添加*/
        var _fileAdd = function (file, data) {
            var fileDiv = "<div id='" + data + "'>";
            var downUrl = !opts.download ? "#" : "/file/fileDownload?exist=f&sf_id=" + data;
            fileDiv += "<a class='easyui-linkbutton' href='" + downUrl + "'>" + file.name + "</a>";
            if (opts.del)  fileDiv += "<a class='easyui-linkbutton' iconCls='icon-delete' href='#'></a>";
            fileDiv += "</div>";

            $('#' + opts.file_show).append(fileDiv);
            $.parser.parse($('#' + opts.file_show));
            $('#' + data + ' a[iconCls]').click(_fileRemove);
            _fileRow(opts.file_show);
        }

        /*图片加载显示*/
        var _loadImg = function (data) {
            $('#' + opts.file_show).attr("src", "/file/imageDownload?exist=f&sf_id=" + data);
        }

        /*判断是否存在 input id为 opts.file_input */
        if ($('#' + opts.file_input).size() == 0) {
            var sf_ids = $("<input type='hidden' id='" + opts.file_input + "' name='" + opts.file_input + "'/>");
            $(this).after(sf_ids);
        }

        /*判断是否存在 div id为fileQueue */
        if ($('#_fileQueue').size() == 0) {
            var fileQueue = $("<div id='_fileQueue' style='width:400px;'></div>");
            $('body').append(fileQueue);
            _queueSet();
        }

        /*判断是否存在 div id 为 opts.file_show */
        if ($('#' + opts.file_show).size() == 0) {
            if (opts.isImg)
                $(this).before("<img id='" + opts.file_show + "' style='width: 120px;height: 150px;' src='/file/imageDownload'/>");
            else
                $(this).before("<div id='" + opts.file_show + "'></div>");
        } else {
            /*如果为文件列表而且存在已有文件列时进行初始化*/
            if (!opts.isImg) {
                //将down，del参数重新设置
                var data_options = $('#' + opts.file_show).attr("data-options");
                if (data_options != undefined && data_options != "") {
                    data_options = "{" + data_options + "}";
                    opts = $.extend({}, opts, $.evalJSON(data_options));
                }
                _fileRow(opts.file_show);
                _resetFiles();
                $('#' + opts.file_show + " a[iconCls]").click(_fileRemove);
            }
        }

        $(this).uploadify({
            'swf': '/ui/uploadify/uploadify.swf',
            'uploader': '/file/fileUpload',
            'queueID': '_fileQueue',
            'buttonText': opts.buttonText,
            'multi': opts.multi,
            'removeTimeout': 0,
            'fileTypeDesc': opts.fileTypeDesc,
            'fileTypeExts': opts.fileTypeExts,
            'height': opts.height,
            'width': opts.width,
            'onUploadSuccess': function (file, data, response) {
                if (opts.isImg) {
                    _loadImg(data);
                    $('#' + opts.file_input).val(data);
                }
                else {
                    _fileAdd(file, data);
                    var fileV = $('#' + opts.file_input).val();
                    fileV = (fileV == "") ? data : fileV + ";" + data;
                    $('#' + opts.file_input).val(fileV);
                }
                _queueSet();
            },
            'onQueueComplete': function () {
                $.unLockScreen();
            },
            'onSelect': function () {
                _queueSet();
                $.lockScreen("文件上传中...");
            }
        });
    }
})(jQuery);