package com.opensdk.eleme2.api.entity.shop;

import com.opensdk.eleme2.api.enumeration.shop.OBusyLevel;

import java.util.*;

public class OShop{

    /**
     * 店铺Id
     */
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * 店铺地址
     */
    private String addressText;
    public String getAddressText() {
        return addressText;
    }
    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }
    
    /**
     * 店铺默认配送费
     */
    private double agentFee;
    public double getAgentFee() {
        return agentFee;
    }
    public void setAgentFee(double agentFee) {
        this.agentFee = agentFee;
    }
    
    /**
     * 店铺营业状态
     */
    private OBusyLevel busyLevel;
    public OBusyLevel getBusyLevel() {
        return busyLevel;
    }
    public void setBusyLevel(OBusyLevel busyLevel) {
        this.busyLevel = busyLevel;
    }
    
    /**
     * 城市Id
     */
    private int cityId;
    public int getCityId() {
        return cityId;
    }
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
    
    /**
     * 城市区号
     */
    private String cityCode;
    public String getCityCode() {
        return cityCode;
    }
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
    
    /**
     * 店铺关闭的原因
     */
    private String closeDescription;
    public String getCloseDescription() {
        return closeDescription;
    }
    public void setCloseDescription(String closeDescription) {
        this.closeDescription = closeDescription;
    }
    
    /**
     * 起送价
     */
    private double deliverAmount;
    public double getDeliverAmount() {
        return deliverAmount;
    }
    public void setDeliverAmount(double deliverAmount) {
        this.deliverAmount = deliverAmount;
    }
    
    /**
     * 配送区域详情
     */
    private String deliverDescription;
    public String getDeliverDescription() {
        return deliverDescription;
    }
    public void setDeliverDescription(String deliverDescription) {
        this.deliverDescription = deliverDescription;
    }
    
    /**
     * 配送范围
     */
    private String deliverGeoJson;
    public String getDeliverGeoJson() {
        return deliverGeoJson;
    }
    public void setDeliverGeoJson(String deliverGeoJson) {
        this.deliverGeoJson = deliverGeoJson;
    }
    
    /**
     * 2周内的平均送餐时间
     */
    private int deliverSpent;
    public int getDeliverSpent() {
        return deliverSpent;
    }
    public void setDeliverSpent(int deliverSpent) {
        this.deliverSpent = deliverSpent;
    }
    
    /**
     * 店铺描述
     */
    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * 店铺口味
     */
    private String flavors;
    public String getFlavors() {
        return flavors;
    }
    public void setFlavors(String flavors) {
        this.flavors = flavors;
    }
    
    /**
     * 店铺Logo地址
     */
    private String imageUrl;
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    /**
     * 是否支持开发票
     */
    private int invoice;
    public int getInvoice() {
        return invoice;
    }
    public void setInvoice(int invoice) {
        this.invoice = invoice;
    }
    
    /**
     * 支持的最小发票金额
     */
    private double invoiceMinAmount;
    public double getInvoiceMinAmount() {
        return invoiceMinAmount;
    }
    public void setInvoiceMinAmount(double invoiceMinAmount) {
        this.invoiceMinAmount = invoiceMinAmount;
    }
    
    /**
     * 是否支持预定(n=0表示不支持预定,7>n>=1表示支持n天内的预定）
     */
    private int isBookable;
    public int getIsBookable() {
        return isBookable;
    }
    public void setIsBookable(int isBookable) {
        this.isBookable = isBookable;
    }
    
    /**
     * 营业时间bitmap
     */
    private String openTimeBitmap;
    public String getOpenTimeBitmap() {
        return openTimeBitmap;
    }
    public void setOpenTimeBitmap(String openTimeBitmap) {
        this.openTimeBitmap = openTimeBitmap;
    }
    
    /**
     * 预定时间bitmap
     */
    private String bookTimeBitmap;
    public String getBookTimeBitmap() {
        return bookTimeBitmap;
    }
    public void setBookTimeBitmap(String bookTimeBitmap) {
        this.bookTimeBitmap = bookTimeBitmap;
    }
    
    /**
     * 预定时间选项
     */
    private List<String> deliverTimes;
    public List<String> getDeliverTimes() {
        return deliverTimes;
    }
    public void setDeliverTimes(List<String> deliverTimes) {
        this.deliverTimes = deliverTimes;
    }
    
    /**
     * 是否正在营业
     */
    private int isOpen;
    public int getIsOpen() {
        return isOpen;
    }
    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }
    
    /**
     * 是否隐藏电话号码
     */
    private int isPhoneHidden;
    public int getIsPhoneHidden() {
        return isPhoneHidden;
    }
    public void setIsPhoneHidden(int isPhoneHidden) {
        this.isPhoneHidden = isPhoneHidden;
    }
    
    /**
     * 是否品牌馆餐厅
     */
    private int isPremium;
    public int getIsPremium() {
        return isPremium;
    }
    public void setIsPremium(int isPremium) {
        this.isPremium = isPremium;
    }
    
    /**
     * 是否支持超时赔付
     */
    private int isTimeEnsure;
    public int getIsTimeEnsure() {
        return isTimeEnsure;
    }
    public void setIsTimeEnsure(int isTimeEnsure) {
        this.isTimeEnsure = isTimeEnsure;
    }
    
    /**
     * 超时赔付详细信息
     */
    private String timeEnsureFullDescription;
    public String getTimeEnsureFullDescription() {
        return timeEnsureFullDescription;
    }
    public void setTimeEnsureFullDescription(String timeEnsureFullDescription) {
        this.timeEnsureFullDescription = timeEnsureFullDescription;
    }
    
    /**
     * 店铺地址坐标-纬度
     */
    private double latitude;
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    
    /**
     * 店铺地址坐标-经度
     */
    private double longitude;
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    /**
     * 店铺接收饿了么短信的手机号码
     */
    private String mobile;
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    /**
     * 免配送费的最低消费额度
     */
    private double noAgentFeeTotal;
    public double getNoAgentFeeTotal() {
        return noAgentFeeTotal;
    }
    public void setNoAgentFeeTotal(double noAgentFeeTotal) {
        this.noAgentFeeTotal = noAgentFeeTotal;
    }
    
    /**
     * 店铺评价的列表
     */
    private List<Short> numRatings;
    public List<Short> getNumRatings() {
        return numRatings;
    }
    public void setNumRatings(List<Short> numRatings) {
        this.numRatings = numRatings;
    }
    
    /**
     * 是否支持在线支付，0:不支持，1:支持
     */
    private int onlinePayment;
    public int getOnlinePayment() {
        return onlinePayment;
    }
    public void setOnlinePayment(int onlinePayment) {
        this.onlinePayment = onlinePayment;
    }
    
    /**
     * 店铺的联系电话的列表
     */
    private List<String> phones;
    public List<String> getPhones() {
        return phones;
    }
    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
    
    /**
     * 店铺促销文案信息
     */
    private String promotionInfo;
    public String getPromotionInfo() {
        return promotionInfo;
    }
    public void setPromotionInfo(String promotionInfo) {
        this.promotionInfo = promotionInfo;
    }
    
    /**
     * 最近一个月美食销量
     */
    private int recentFoodPopularity;
    public int getRecentFoodPopularity() {
        return recentFoodPopularity;
    }
    public void setRecentFoodPopularity(int recentFoodPopularity) {
        this.recentFoodPopularity = recentFoodPopularity;
    }
    
    /**
     * 店铺名称
     */
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 店铺手机站url
     */
    private String mobileUrl;
    public String getMobileUrl() {
        return mobileUrl;
    }
    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }
    
    /**
     * 营业时间的列表
     */
    private List<String> servingTime;
    public List<String> getServingTime() {
        return servingTime;
    }
    public void setServingTime(List<String> servingTime) {
        this.servingTime = servingTime;
    }
    
    /**
     * 是否支持在线订餐
     */
    private boolean supportOnline;
    public boolean getSupportOnline() {
        return supportOnline;
    }
    public void setSupportOnline(boolean supportOnline) {
        this.supportOnline = supportOnline;
    }
    
    /**
     * 废弃字段，请忽略
     */
    private int serviceCategory;
    public int getServiceCategory() {
        return serviceCategory;
    }
    public void setServiceCategory(int serviceCategory) {
        this.serviceCategory = serviceCategory;
    }
    
    /**
     * 订单打包费
     */
    private double packingFee;
    public double getPackingFee() {
        return packingFee;
    }
    public void setPackingFee(double packingFee) {
        this.packingFee = packingFee;
    }
    
    /**
     * 店铺绑定的外部ID
     */
    private String openId;
    public String getOpenId() {
        return openId;
    }
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
}