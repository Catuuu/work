package com.service;


import com.framework.mapping.system.*;
import com.framework.service.BasicService;
import com.framework.util.DateUtil;
import com.opensdk.eleme.vo.OrderParam;
import com.opensdk.eleme2.api.entity.order.OOrder;
import com.opensdk.meituan.vo.OrderDetailParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * Created by c on 2017-01-30.
 */
@Service("MemberService")
public class MemberService extends BasicService {

    /**
     * 通过饿了么订单信息获取会员信息
     *
     * @param orderParam 饿了么订单信息
     * @return
     * @throws Exception
     */
    public CdsMember GetMember(OOrder orderParam, CdsStores stores) {
        CdsMember member = new CdsMember();
        member.setPhone(orderParam.getPhoneList().get(0));
        member.setBrand_id(stores.getBrand_id());
        member.addConditionFields("phone,brand_id");
        member = sqlDao.getRecord(member);

        if (member == null) {
            member = new CdsMember();
            member.setBrand_id(stores.getBrand_id());
            member.setPhone(orderParam.getPhoneList().get(0));
            member.setName(orderParam.getConsignee());
            member.setCreate_time(orderParam.getActiveAt());
            member.setLast_login_time(orderParam.getActiveAt());
            member.setLogin_count(1);
            member.setLevel_id(1);
            member.setUser_status(1);
            member.setBalance_money(0);
            member.setLocation(orderParam.getDeliveryGeo());
            member.setAll_order_count(0);
            member.setFirst_order_time(orderParam.getActiveAt());
            member.setLast_order_time(orderParam.getActiveAt());
            member.setAll_order_count1(0);
            member.setFirst_order_time1(orderParam.getActiveAt());
            member.setLast_order_time1(orderParam.getActiveAt());
            member.addParamFields("phone,name,create_time,last_login_time,login_count,level_id,user_status,balance_money,location,all_order_count,first_order_time,brand_id," +
                    "last_order_time,all_order_count1,first_order_time1,last_order_time1");
            sqlDao.insertRecord(member);

            member.addConditionFields("phone,brand_id");
            member.addParamField("member_id");
            member = sqlDao.getRecord(member);

            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), stores.getStores_id(), stores.getBrand_id(), orderParam.getActiveAt());
            cdsMemberStores.setFirst_order_time1(orderParam.getActiveAt());
            cdsMemberStores.addUnParamFields("id");
            sqlDao.insertRecord(cdsMemberStores);

        } else {
            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), stores.getStores_id(), stores.getBrand_id(), orderParam.getActiveAt());
            cdsMemberStores.setFirst_order_time1(orderParam.getActiveAt());
            cdsMemberStores.addConditionFields("stores_id,member_id,brand_id");
            CdsMemberStores mStores = sqlDao.getRecord(cdsMemberStores);
            if (mStores == null) {
                cdsMemberStores.addUnParamFields("id");
                sqlDao.insertRecord(cdsMemberStores);
            }
        }

        return member;
    }



    /**
     * 通过饿了么订单信息获取会员信息
     *
     * @param orderParam 饿了么订单信息
     * @return
     * @throws Exception
     */
    public CdsMember GetMember(OrderParam orderParam, CdsStores stores) {
        CdsMember member = new CdsMember();
        member.setPhone(orderParam.getPhone_list().get(0));
        member.setBrand_id(stores.getBrand_id());
        member.addConditionFields("phone,brand_id");
        member = sqlDao.getRecord(member);

        if (member == null) {
            member = new CdsMember();
            member.setBrand_id(stores.getBrand_id());
            member.setPhone(orderParam.getPhone_list().get(0));
            member.setName("菜大师用户");
            member.setCreate_time(orderParam.getActive_at());
            member.setLast_login_time(orderParam.getActive_at());
            member.setLogin_count(1);
            member.setLevel_id(1);
            member.setUser_status(1);
            member.setBalance_money(0);
            member.setLocation(orderParam.getDelivery_geo());
            member.setAll_order_count(0);
            member.setFirst_order_time(orderParam.getActive_at());
            member.setLast_order_time(orderParam.getActive_at());
            member.setAll_order_count1(0);
            member.setFirst_order_time1(orderParam.getActive_at());
            member.setLast_order_time1(orderParam.getActive_at());
            member.addParamFields("phone,name,create_time,last_login_time,login_count,level_id,user_status,balance_money,location,all_order_count,first_order_time,brand_id," +
                    "last_order_time,all_order_count1,first_order_time1,last_order_time1");
            sqlDao.insertRecord(member);

            member.addConditionFields("phone,brand_id");
            member.addParamField("member_id");
            member = sqlDao.getRecord(member);

            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), stores.getStores_id(), stores.getBrand_id(), orderParam.getActive_at());
            cdsMemberStores.setFirst_order_time1(orderParam.getActive_at());
            cdsMemberStores.addUnParamFields("id");
            sqlDao.insertRecord(cdsMemberStores);

        } else {
            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), stores.getStores_id(), stores.getBrand_id(), orderParam.getActive_at());
            cdsMemberStores.setFirst_order_time1(orderParam.getActive_at());
            cdsMemberStores.addConditionFields("stores_id,member_id,brand_id");
            CdsMemberStores mStores = sqlDao.getRecord(cdsMemberStores);
            if (mStores == null) {
                cdsMemberStores.addUnParamFields("id");
                sqlDao.insertRecord(cdsMemberStores);
            }
        }

        return member;
    }


    /**
     * 通过美团订单信息获取会员信息
     *
     * @param orderParam 美团订单信息
     * @return
     * @throws Exception
     */
    public CdsMember GetMember(OrderDetailParam orderParam, CdsStores stores) {
        CdsMember member = new CdsMember();
        member.setPhone(orderParam.getRecipient_phone());
        member.setBrand_id(stores.getBrand_id());
        member.addConditionFields("phone,brand_id");
        member = sqlDao.getRecord(member);
        Date d = new Date(orderParam.getCtime() * 1000);

        if (member == null) {
            member = new CdsMember();
            member.setBrand_id(stores.getBrand_id());
            member.setPhone(orderParam.getRecipient_phone());
            member.setName("菜大师用户");
            member.setCreate_time(d);
            member.setLast_login_time(d);
            member.setLogin_count(1);
            member.setLevel_id(1);
            member.setUser_status(1);
            member.setBalance_money(0);
            member.setLocation(orderParam.getLatitude() + "," + orderParam.getLongitude());
            member.setAll_order_count(0);
            member.setFirst_order_time(d);
            member.setLast_order_time(d);
            member.setAll_order_count1(0);
            member.setFirst_order_time1(d);
            member.setLast_order_time1(d);
            member.addParamFields("phone,name,create_time,last_login_time,login_count,level_id,user_status,balance_money,location,all_order_count,first_order_time,brand_id," +
                    "last_order_time,all_order_count1,first_order_time1,last_order_time1");
            sqlDao.insertRecord(member);

            member.addConditionFields("phone,brand_id");
            member.addParamField("member_id");
            member = sqlDao.getRecord(member);

            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), stores.getStores_id(), stores.getBrand_id(), d);
            cdsMemberStores.setFirst_order_time2(d);
            cdsMemberStores.addUnParamFields("id");
            sqlDao.insertRecord(cdsMemberStores);
        } else {
            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), stores.getStores_id(), stores.getBrand_id(), d);
            cdsMemberStores.setFirst_order_time2(d);
            cdsMemberStores.addConditionFields("stores_id,member_id,brand_id");
            CdsMemberStores mStores = sqlDao.getRecord(cdsMemberStores);
            if (mStores == null) {
                cdsMemberStores.addUnParamFields("id");
                sqlDao.insertRecord(cdsMemberStores);
            }
        }
        return member;
    }


    /**
     * 通过微信订单信息获取会员信息
     *
     * @param weixinInfo 微信订单信息
     * @return
     * @throws Exception
     */
    public CdsMember GetMember(CdsWeixinOrderInfo weixinInfo) {
        CdsMember member = new CdsMember();
        member.setMember_id(weixinInfo.getMember_id());
        member = sqlDao.getRecord(member);

        if (member != null) {
            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), weixinInfo.getStores_id(), member.getBrand_id(), weixinInfo.getCreate_date());
            cdsMemberStores.setFirst_order_time1(weixinInfo.getCreate_date());
            cdsMemberStores.addConditionFields("stores_id,member_id,brand_id");
            CdsMemberStores mStores = sqlDao.getRecord(cdsMemberStores);
            if (mStores == null) {
                cdsMemberStores.addUnParamFields("id");
                sqlDao.insertRecord(cdsMemberStores);
            }
        }
        return member;
    }


    /**
     * 通过平台订单信息获取会员信息
     *
     * @param orderParam 饿了么订单信息
     * @return
     * @throws Exception
     */
    public CdsMember GetMember(CdsOrderInfo orderParam, CdsStores stores) {
        CdsMember member = new CdsMember();
        member.setPhone(orderParam.getReceiver_phone());
        member.setBrand_id(stores.getBrand_id());
        member.addConditionFields("phone,brand_id");
        member = sqlDao.getRecord(member);

        if (member == null) {
            member = new CdsMember();
            member.setBrand_id(stores.getBrand_id());
            member.setPhone(orderParam.getReceiver_phone());
            member.setName("菜大师用户");
            member.setCreate_time(orderParam.getCreate_date());
            member.setLast_login_time(orderParam.getCreate_date());
            member.setLogin_count(1);
            member.setLevel_id(1);
            member.setUser_status(1);
            member.setBalance_money(0);
            //member.setLocation(orderParam.getDelivery_geo());
            member.setAll_order_count(0);
            member.setFirst_order_time(orderParam.getCreate_date());
            member.setLast_order_time(orderParam.getCreate_date());
            member.setAll_order_count1(0);
            member.setFirst_order_time1(orderParam.getCreate_date());
            member.setLast_order_time1(orderParam.getCreate_date());
            member.addParamFields("phone,name,create_time,last_login_time,login_count,level_id,user_status,balance_money,all_order_count,first_order_time,brand_id," +
                    "last_order_time,all_order_count1,first_order_time1,last_order_time1");
            sqlDao.insertRecord(member);

            member.addConditionFields("phone,brand_id");
            member.addParamField("member_id");
            member = sqlDao.getRecord(member);

            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), stores.getStores_id(), stores.getBrand_id(), orderParam.getCreate_date());
            cdsMemberStores.setFirst_order_time1(orderParam.getCreate_date());
            cdsMemberStores.addUnParamFields("id");
            sqlDao.insertRecord(cdsMemberStores);

        } else {
            CdsMemberStores cdsMemberStores = new CdsMemberStores(member.getMember_id(), stores.getStores_id(), stores.getBrand_id(), orderParam.getCreate_date());
            cdsMemberStores.setFirst_order_time1(orderParam.getCreate_date());
            cdsMemberStores.addConditionFields("stores_id,member_id,brand_id");
            CdsMemberStores mStores = sqlDao.getRecord(cdsMemberStores);
            if (mStores == null) {
                cdsMemberStores.addUnParamFields("id");
                sqlDao.insertRecord(cdsMemberStores);
            }
        }

        return member;
    }


    /**
     * 更新用户的订单数及最后下单时间
     *
     * @param cdsOrderInfo
     */
    @Transactional(rollbackForClassName = {"RuntimeException", "Exception"})
    public void updateMember(CdsOrderInfo cdsOrderInfo) {
        CdsMember cdsMember = new CdsMember();
        cdsMember.setMember_id(cdsOrderInfo.getMember_id());
        cdsMember.setLast_order_time(cdsOrderInfo.getCreate_date());
        cdsMember.setBrand_id(cdsOrderInfo.getBrand_id());


        CdsMemberStores cdsMemberStores = new CdsMemberStores();
        cdsMemberStores.setMember_id(cdsOrderInfo.getMember_id());
        cdsMemberStores.setStores_id(cdsOrderInfo.getStores_id());
        cdsMemberStores.setBrand_id(cdsOrderInfo.getBrand_id());
        cdsMemberStores.setLast_order_time(cdsOrderInfo.getCreate_date());

        if (cdsOrderInfo.getFromin().equals("饿了么")) {
            cdsMember.setLast_order_time1(cdsOrderInfo.getCreate_date());
            cdsMemberStores.setLast_order_time1(cdsOrderInfo.getCreate_date());
            sqlDao.updateRecord("cds_member.updateMemberRecord1", cdsMember);
            sqlDao.updateRecord("cds_member_stores.updateMemberRecord1", cdsMemberStores);
        } else if (cdsOrderInfo.getFromin().equals("美团")) {
            cdsMember.setLast_order_time2(cdsOrderInfo.getCreate_date());
            cdsMemberStores.setLast_order_time2(cdsOrderInfo.getCreate_date());
            sqlDao.updateRecord("cds_member.updateMemberRecord2", cdsMember);
            sqlDao.updateRecord("cds_member_stores.updateMemberRecord2", cdsMemberStores);
        } else if (cdsOrderInfo.getFromin().equals("百度外卖")) {
            cdsMember.setLast_order_time3(cdsOrderInfo.getCreate_date());
            cdsMemberStores.setLast_order_time3(cdsOrderInfo.getCreate_date());
            sqlDao.updateRecord("cds_member.updateMemberRecord3", cdsMember);
            sqlDao.updateRecord("cds_member_stores.updateMemberRecord3", cdsMemberStores);
        } else if (cdsOrderInfo.getFromin().equals("微信")) {
            cdsMember.setLast_order_time4(cdsOrderInfo.getCreate_date());
            cdsMemberStores.setLast_order_time4(cdsOrderInfo.getCreate_date());
            sqlDao.updateRecord("cds_member.updateMemberRecord4", cdsMember);
            sqlDao.updateRecord("cds_member_stores.updateMemberRecord4", cdsMemberStores);
        }
    }
}
