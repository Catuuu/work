(function ($) {
    $.checkEmpty = function (name, msg, focus) {
        if ($.isEmpty($('#' + name).val())) {
            alert(msg);
            if (focus) {
                $('#' + name).focus();
            }
            return true;
        }
        else {
            return false;
        }
    };
    /*将String类型解析为Date类型.
     #   parseDate('2006-1-1') return new Date(2006,0,1)
     #   parseDate(' 2006-1-1 ') return new Date(2006,0,1)
     #   parseDate('2006-1-1 15:14:16') return new Date(2006,0,1,15,14,16)
     #   parseDate(' 2006-1-1 15:14:16 ') return new Date(2006,0,1,15,14,16);
     #   parseDate('2006-1-1 15:14:16.254') return new Date(2006,0,1,15,14,16,254)
     #   parseDate(' 2006-1-1 15:14:16.254 ') return new Date(2006,0,1,15,14,16,254)
     #   parseDate('不正确的格式') retrun null
     # */
    $.parseDate = function (str) {
        var date = new Date(Date.parse(str.replace(/-/g, "/")));
        return isNaN(date) ? null : date;
    };

    /*
     #   将Date/String类型,解析为String类型.
     #   传入String类型,则先解析为Date类型
     #   不正确的Date,返回 ''
     #   如果时间部分为0,则忽略,只返回日期部分.
     # */
    $.formatDate = function (v, len) {
        var date_obj;
        if (typeof v == 'string') {
            date_obj = $.parseDate(v);
        }
        else {
            date_obj = v;
        }
        if (date_obj != null) {
            var y = date_obj.getFullYear();
            var m = date_obj.getMonth() + 1;
            var d = date_obj.getDate();
            var h = date_obj.getHours();
            var i = date_obj.getMinutes();
            var s = date_obj.getSeconds();
            var result = "" + y;
            result += "-" + ((m < 10) ? "0" + m : m);
            result += "-" + ((d < 10) ? "0" + d : d);
            result += " " + ((h < 10) ? "0" + h : h);
            result += ":" + ((i < 10) ? "0" + i : i);
            result += ":" + ((s < 10) ? "0" + s : s);

            if (len && len > 0)
                return result.substring(0, len);
            else
                return result;
        }
        else {
            return "";
        }
    };


    $.addzero = function (str, lenght) {
        if (str.length >= lenght)
            return str;
        else
            return addzero("0" + str, lenght);
    }
    /**
     * 判断字符串为空，
     * @param str
     */
    $.isEmpty = function (str) {
        return null == str || "" == $.trim(str);
    };


    /**
     * 判断是否都是数字
     * @param str
     */
    $.isDigit = function (str) {
        var patrn = /^\d+$/;
        return patrn.test(str);
    };

    /**
     * 判断是否整形
     * @param str
     */
    $.isInteger = function (str) {
        var patrn = /^([+-]?)(\d+)$/;
        return patrn.test(str);
    };

    /**
     * 判断是否是浮点型
     * @param str
     */
    $.isFloat = function (str) {
        var patrn = /^-?\d+\.?\d*$/;
        return patrn.test(str);
    };

    /**
     * 判断邮箱
     * @param str
     */
    $.isEmail = function (str) {
        var patrn = /^[\w-]+@[\w-]+(\.[\w-]+)+$/;
        return patrn.test(str);
    };


    $.isDate = function (str) {
        if (!/\d{4}(\.|\/|\-)\d{1,2}(\.|\/|\-)\d{1,2}/.test(str)) {
            return false;
        }
        var r = str.match(/\d{1,4}/g);
        if (r == null) {
            return false;
        }
        ;
        var d = new Date(r[0], r[1] - 1, r[2]);
        return (d.getFullYear() == r[0] && (d.getMonth() + 1) == r[1] && d.getDate() == r[2]);
    };

    /*    $.isUrl = function(str) {
     var patrn = /^http[s]?:\/\/[\w-]+(\.[\w-]+)+([\w-\.\/?%&=]*)?$/;
     return patrn.test(str);
     };*/


    $.primaryKey = function () {
        var now = new Date();
        var year = now.getFullYear();
        var month = now.getMonth() + 1;
        var date = now.getDate();
        var hour = now.getHours();
        var minute = now.getMinutes();
        var second = now.getSeconds();
        var millisecond = now.getMilliseconds();

        var key = "";
        key = key.concat(year);
        key = key.concat(month < 10 ? '0' + month : month);
        key = key.concat(date < 10 ? '0' + date : date);
        key = key.concat(hour < 10 ? '0' + hour : hour);
        key = key.concat(minute < 10 ? '0' + minute : minute);
        key = key.concat(second < 10 ? '0' + second : second);
        if (millisecond < 10) key = key.concat('00' + millisecond);
        else if (millisecond < 100) key = key.concat('0' + millisecond);
        else key = key.concat(millisecond);
        key = key.concat($.randomChar(13));
        return key;
    }

    $.randomChar = function (len) {
        if (len == null) len = 10;
        var charArray = new Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z");

        var randomString = "";
        for (var i = 0; i < len; i++) {
            randomString += charArray[Math.round((Math.random() * 35))];
        }
        charArray = null;
        return randomString;
    }

    $.randomNum = function (len) {
        if (len == null) {
            len = 10;
        }
        var randomNumber = "";
        for (var i = 0; i < len; i++) {
            randomNumber += Math.round((Math.random() * 9));
        }
        return randomNumber;
    }

})(jQuery);
