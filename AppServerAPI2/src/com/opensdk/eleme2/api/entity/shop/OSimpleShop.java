package com.opensdk.eleme2.api.entity.shop;

public class OSimpleShop{

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
     * 是否开店
     */
    private int isOpen;
    public int getIsOpen() {
        return isOpen;
    }
    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }
    
    /**
     * 是否有效
     */
    private int isValid;
    public int getIsValid() {
        return isValid;
    }
    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
    
}