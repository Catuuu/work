$(function () {
    $.extend($.fn.validatebox.defaults.rules, {
        date:{
            validator:function (value, param) {
                return $.isDate(value);
            },
            message:'请输入正确的日期格式'
        },
        time:{
            validator:function (value, param) {
                if ("HH:mm:ss" == param[0]) {
                    return /^([01][0-9]|2[0-3])\:[0-5][0-9]\:[0-5][0-9]$/.test(value);
                } else if ("hh:mm:ss" == param[0]) {
                    return /^([0][1-9]|1[0-2])\:[0-5][0-9]\:[0-5][0-9]$/.test(value);
                } else if ("HH:mm" == param[0]) {
                    return /^([01][0-9]|2[0-3])\:[0-5][0-9]$/.test(value);
                } else if ("HH" == param[0]) {
                    return /^([01][0-9]|2[0-3])$/.test(value);
                } else if ("hh" == param[0]) {
                    return /^([0][1-9]|1[0-2])$/.test(value);
                } else if ("mm" == param[0] | "ss" == param[0]) {
                    return /^[0-5][0-9]$/.test(value);
                }
            },
            message:'请输入正确的时间格式'
        },
        minLength:{
            validator:function (value, param) {
                return value.length >= param[0];
            },
            message:'此输入域的最小字符长度为 {0}。'
        },
        maxLength:{
            validator:function (value, param) {
                return value.length <= param[0];
            },
            message:'此输入域的最大字符长度为 {0}。'
        },
        min:{
            validator:function (value, param) {
                return value >= param[0];
            },
            message:'此输入域的最小值为 {0}。'
        },
        max:{
            validator:function (value, param) {
                return value <= param[0];
            },
            message:'此输入域的最大值为 {0}。'
        },
        value:{
            validator:function (value, param) {
                return value == param[0];
            },
            message:'此输入域的值不为 {0}。'
        },
        justLength:{
            validator:function (value, param) {
                return value.length == param[0];
            },
            message:'此输入域的字符长度必须为 {0}。'
        },
        CHS:{
            validator:function (value, param) {
                return /^[\u0391-\uFFE5]+$/.test(value);
            },
            message:'请输入汉字'
        },
        chrnum:{
            validator:function (value, param) {
                return /^([a-zA-Z0-9]+)$/.test(value);
            },
            message:'只能输入数字和字母(字符A-Z, a-z, 0-9)'
        },
        ZIP:{
            validator:function (value, param) {
                return /^[1-9]\d{5}$/.test(value);
            },
            message:'邮政编码不存在'
        },
        QQ:{
            validator:function (value, param) {
                return /^[1-9]\d{4,10}$/.test(value);
            },
            message:'QQ号码不正确'
        },
        mobile:{
            validator:function (value, param) {
                return /^(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/.test(value);
            },
            message:'手机号码不正确'
        },
        phone:{
            validator:function (value, param) {
                return /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/.test(value);
            },
            message:'电话号码不正确'
        },
        loginName:{
            validator:function (value, param) {
                return /^[\u0391-\uFFE5\w]+$/.test(value);
            },
            message:'登录名称只允许汉字、英文字母、数字及下划线。'
        },
        safepass:{
            validator:function (value, param) {
                return (/^([a-zA-Z0-9]+)$/.test(value)) && (value.length == param[0]);
            },
            message:'密码由字母和数字组成，至少6位'
        },
        equalTo:{
            validator:function (value, param) {
                return value == $("#" + param[0]).val();
            },
            message:'两次输入的字符不一致'
        },
        number:{
            validator:function (value, param) {
                return /^\d+$/.test(value);
            },
            message:'请输入数字'
        },
        idcardno:{
            validator:function (value, param) {
                return isIdCardNo(value);
            },
            message:'请输入正确的身份证号码'
        },
        IP:{
            validator:function (value, param) {
                var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
                return (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
            },
            message:'Ip地址格式不正确'
        },
        minCheckbox:{
            validator:function (value, param) {
                var inputs = $("input[name='" + param[1] + "'][checked=true]");
                if (inputs.length < param[0])   return false;
                return true;
            },
            message:'至少选择{0}项'
        },
        maxCheckbox:{
            validator:function (value, param) {
                var inputs = $("input[name='" + param[1] + "'][checked=true]");
                if (inputs.length > param[0])   return false;
                return true;
            },
            message:'最多选择{0}项'
        },
        justCheckbox:{
            validator:function (value, param) {
                var inputs = $("input[name='" + param[1] + "'][checked=true]");
                if (inputs.length != param[0])   return false;
                return true;
            },
            message:'必须选择{0}项'
        },
        radio:{
            validator:function (value, param) {
                var inputs = $("input[name='" + param[0] + "'][checked=true]");
                if (inputs.length == 0)   return false;
                return true;
            },
            message:'请选择一个单选框'
        }
    });
})
/**
 * 身份证号码验证
 *
 */
function isIdCardNo(num) {

    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;

    if ((intStrLen != 15) && (intStrLen != 18)) {
        return false;
    }
    // check and set value
    for (i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i] * factorArr[i];
        }
    }

    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6, 14);
        if (isDate8(date8) == false) {
            return false;
        }
        // calculate the sum of the products
        for (i = 0; i < 17; i++) {
            lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProduct % 11];
        // check last digit
        if (varArray[17] != intCheckDigit) {
            return false;
        }
    }
    else {        //length is 15

        //check date
        var date6 = idNumber.substring(6, 12);
        if (isDate6(date6) == false) {

            return false;
        }
    }
    return true;
}
/**
 * 判断是否为“YYYYMM”式的时期
 *
 */
function isDate6(sDate) {
    if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = "19" + sDate.substring(0, 2);
    month = sDate.substring(2, 4);
    day = sDate.substring(4, 6);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if (year < 1700 || year > 2500) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false
    if (day < 1 || day > iaMonthDays[month - 1]) return false
    return true
}

/**
 * 判断是否为“YYYYMMDD”式的时期
 *
 */
function isDate8(sDate) {
    if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    day = sDate.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    if (year < 1700 || year > 2500) return false
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false
    if (day < 1 || day > iaMonthDays[month - 1]) return false
    return true
}
