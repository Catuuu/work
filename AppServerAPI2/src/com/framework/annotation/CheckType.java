package com.framework.annotation;

/**
 * 权限验证枚举值
 * User: Administrator
 * Date: 13-1-26
 * Time: 下午12:04
 * To change this template use File | Settings | File Templates.
 */
public enum CheckType {
    //验证权限的类型 ( NO_CHECK: 不需要  ,CHECK_LOGIN: 验证登录 ,CHECK_LOGIN_POWERS: 验证登录和权限组)
    //验证厨房 CHECK_CHUFAN
    NO_CHECK, CHECK_LOGIN, CHECK_LOGIN_POWERS,CHECK_MUITUAN,CHECK_ELEME,CHECK_UDESK,CHECK_CHUFAN
}
