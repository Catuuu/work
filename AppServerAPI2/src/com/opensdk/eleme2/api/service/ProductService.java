package com.opensdk.eleme2.api.service;


import com.opensdk.eleme2.api.annotation.Service;
import com.opensdk.eleme2.api.base.BaseNopService;
import com.opensdk.eleme2.api.entity.product.*;
import com.opensdk.eleme2.api.enumeration.product.OItemCreateProperty;
import com.opensdk.eleme2.api.enumeration.product.OItemUpdateProperty;
import com.opensdk.eleme2.api.exception.ServiceException;
import com.opensdk.eleme2.config.Config;
import com.opensdk.eleme2.oauth.response.Token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品服务
 */
@Service("eleme.product")
public class ProductService extends BaseNopService {
    public ProductService(Config config, Token token) {
        super(config, token, ProductService.class);
    }

    /**
     * 查询店铺商品分类
     *
     * @param shopId 店铺Id
     * @return 商品分类列表
     * @throws ServiceException 服务异常
     */
    public List<OCategory> getShopCategories(long shopId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        return call("eleme.product.category.getShopCategories", params);
    }

    /**
     * 查询店铺商品分类，包含二级分类
     *
     * @param shopId 店铺Id
     * @return 商品分类列表
     * @throws ServiceException 服务异常
     */
    public List<OCategory> getShopCategoriesWithChildren(long shopId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        return call("eleme.product.category.getShopCategoriesWithChildren", params);
    }

    /**
     * 查询商品分类详情
     *
     * @param categoryId 商品分类Id
     * @return 商品分类
     * @throws ServiceException 服务异常
     */
    public OCategory getCategory(long categoryId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        return call("eleme.product.category.getCategory", params);
    }

    /**
     * 查询商品分类详情，包含二级分类
     *
     * @param categoryId 商品分类Id
     * @return 商品分类
     * @throws ServiceException 服务异常
     */
    public OCategory getCategoryWithChildren(long categoryId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        return call("eleme.product.category.getCategoryWithChildren", params);
    }

    /**
     * 添加商品分类
     *
     * @param shopId 店铺Id
     * @param name 商品分类名称，长度需在50字以内
     * @param description 商品分类描述，长度需在50字以内
     * @return 商品分类
     * @throws ServiceException 服务异常
     */
    public OCategory createCategory(long shopId, String name, String description) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("name", name);
        params.put("description", description);
        return call("eleme.product.category.createCategory", params);
    }

    /**
     * 添加商品分类，支持二级分类
     *
     * @param shopId 店铺Id
     * @param name 商品分类名称，长度需在50字以内
     * @param parentId 父分类ID，如果没有可以填0
     * @param description 商品分类描述，长度需在50字以内
     * @return 商品分类
     * @throws ServiceException 服务异常
     */
    public OCategory createCategoryWithChildren(long shopId, String name, long parentId, String description) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("name", name);
        params.put("parentId", parentId);
        params.put("description", description);
        return call("eleme.product.category.createCategoryWithChildren", params);
    }

    /**
     * 更新商品分类
     *
     * @param categoryId 商品分类Id
     * @param name 商品分类名称，长度需在50字以内
     * @param description 商品分类描述，长度需在50字以内
     * @return 商品分类
     * @throws ServiceException 服务异常
     */
    public OCategory updateCategory(long categoryId, String name, String description) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        params.put("name", name);
        params.put("description", description);
        return call("eleme.product.category.updateCategory", params);
    }

    /**
     * 更新商品分类，包含二级分类
     *
     * @param categoryId 商品分类Id
     * @param name 商品分类名称，长度需在50字以内
     * @param parentId 父分类ID，如果没有可以填0
     * @param description 商品分类描述，长度需在50字以内
     * @return 商品分类
     * @throws ServiceException 服务异常
     */
    public OCategory updateCategoryWithChildren(long categoryId, String name, long parentId, String description) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        params.put("name", name);
        params.put("parentId", parentId);
        params.put("description", description);
        return call("eleme.product.category.updateCategoryWithChildren", params);
    }

    /**
     * 删除商品分类
     *
     * @param categoryId 商品分类Id
     * @return 商品分类
     * @throws ServiceException 服务异常
     */
    public OCategory removeCategory(long categoryId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        return call("eleme.product.category.removeCategory", params);
    }

    /**
     * 设置分类排序
     *
     * @param shopId 饿了么店铺Id
     * @param categoryIds 需要排序的分类Id
     * @throws ServiceException 服务异常
     */
    public void setCategoryPositions(Long shopId, List<Long> categoryIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("categoryIds", categoryIds);
        call("eleme.product.category.setCategoryPositions", params);
    }

    /**
     * 设置二级分类排序
     *
     * @param shopId 饿了么店铺Id
     * @param categoryWithChildrenIds 需要排序的父分类Id，及其下属的二级分类ID
     * @throws ServiceException 服务异常
     */
    public void setCategoryPositionsWithChildren(Long shopId, List<CategoryWithChildrenIds> categoryWithChildrenIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("categoryWithChildrenIds", categoryWithChildrenIds);
        call("eleme.product.category.setCategoryPositionsWithChildren", params);
    }

    /**
     * 查询商品后台类目
     *
     * @param shopId 店铺Id
     * @return 商品后台类目列表
     * @throws ServiceException 服务异常
     */
    public List<OBackCategory> getBackCategory(Long shopId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        return call("eleme.product.category.getBackCategory", params);
    }

    /**
     * 上传图片，返回图片的hash值
     *
     * @param image 文件内容base64编码值
     * @return 图片的 hash 值
     * @throws ServiceException 服务异常
     */
    public String uploadImage(String image) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("image", image);
        return call("eleme.file.uploadImage", params);
    }

    /**
     * 通过远程URL上传图片，返回图片的hash值
     *
     * @param url 远程Url地址
     * @return 图片的 hash 值
     * @throws ServiceException 服务异常
     */
    public String uploadImageWithRemoteUrl(String url) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("url", url);
        return call("eleme.file.uploadImageWithRemoteUrl", params);
    }

    /**
     * 获取上传文件的访问URL，返回文件的Url地址
     *
     * @param hash 图片hash值
     * @return 文件的Url地址
     * @throws ServiceException 服务异常
     */
    public String getUploadedUrl(String hash) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("hash", hash);
        return call("eleme.file.getUploadedUrl", params);
    }

    /**
     * 获取一个分类下的所有商品
     *
     * @param categoryId 商品分类Id
     * @return 商品列表
     * @throws ServiceException 服务异常
     */
    public Map<Long,OItem> getItemsByCategoryId(long categoryId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        return call("eleme.product.item.getItemsByCategoryId", params);
    }

    /**
     * 查询商品详情
     *
     * @param itemId 商品Id
     * @return 商品
     * @throws ServiceException 服务异常
     */
    public OItem getItem(long itemId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemId", itemId);
        return call("eleme.product.item.getItem", params);
    }

    /**
     * 批量查询商品详情
     *
     * @param itemIds 商品Id的列表
     * @return 商品列表
     * @throws ServiceException 服务异常
     */
    public Map<Long,OItem> batchGetItems(List<Long> itemIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemIds", itemIds);
        return call("eleme.product.item.batchGetItems", params);
    }

    /**
     * 添加商品
     *
     * @param categoryId 商品分类Id
     * @param properties 商品属性
     * @return 商品
     * @throws ServiceException 服务异常
     */
    public OItem createItem(long categoryId, Map<OItemCreateProperty,Object> properties) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        params.put("properties", properties);
        return call("eleme.product.item.createItem", params);
    }

    /**
     * 批量添加商品
     *
     * @param categoryId 商品分类Id
     * @param items 商品属性的列表
     * @return 商品列表
     * @throws ServiceException 服务异常
     */
    public Map<Long,OItem> batchCreateItems(long categoryId, List<Map<OItemCreateProperty,Object>> items) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        params.put("items", items);
        return call("eleme.product.item.batchCreateItems", params);
    }

    /**
     * 更新商品
     *
     * @param itemId 商品Id
     * @param categoryId 商品分类Id
     * @param properties 商品属性
     * @return 商品列表
     * @throws ServiceException 服务异常
     */
    public OItem updateItem(long itemId, long categoryId, Map<OItemUpdateProperty,Object> properties) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemId", itemId);
        params.put("categoryId", categoryId);
        params.put("properties", properties);
        return call("eleme.product.item.updateItem", params);
    }

    /**
     * 批量置满库存
     *
     * @param specIds 商品及商品规格的列表
     * @throws ServiceException 服务异常
     */
    public void batchFillStock(List<OItemIdWithSpecIds> specIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("specIds", specIds);
        call("eleme.product.item.batchFillStock", params);
    }

    /**
     * 批量沽清库存
     *
     * @param specIds 商品及商品规格的列表
     * @throws ServiceException 服务异常
     */
    public void batchClearStock(List<OItemIdWithSpecIds> specIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("specIds", specIds);
        call("eleme.product.item.batchClearStock", params);
    }

    /**
     * 批量上架商品
     *
     * @param specIds 商品及商品规格的列表
     * @throws ServiceException 服务异常
     */
    public void batchOnShelf(List<OItemIdWithSpecIds> specIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("specIds", specIds);
        call("eleme.product.item.batchOnShelf", params);
    }

    /**
     * 批量下架商品
     *
     * @param specIds 商品及商品规格的列表
     * @throws ServiceException 服务异常
     */
    public void batchOffShelf(List<OItemIdWithSpecIds> specIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("specIds", specIds);
        call("eleme.product.item.batchOffShelf", params);
    }

    /**
     * 删除商品
     *
     * @param itemId 商品Id
     * @return 商品
     * @throws ServiceException 服务异常
     */
    public OItem removeItem(long itemId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemId", itemId);
        return call("eleme.product.item.removeItem", params);
    }

    /**
     * 批量删除商品
     *
     * @param itemIds 商品Id的列表
     * @return 被删除的商品列表
     * @throws ServiceException 服务异常
     */
    public Map<Long,OItem> batchRemoveItems(List<Long> itemIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("itemIds", itemIds);
        return call("eleme.product.item.batchRemoveItems", params);
    }

    /**
     * 批量更新商品库存
     *
     * @param specStocks 商品以及规格库存列表
     * @throws ServiceException 服务异常
     */
    public void batchUpdateSpecStocks(List<OItemIdWithSpecStock> specStocks) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("specStocks", specStocks);
        call("eleme.product.item.batchUpdateSpecStocks", params);
    }

    /**
     * 设置商品排序
     *
     * @param categoryId 商品分类Id
     * @param itemIds 商品Id列表
     * @throws ServiceException 服务异常
     */
    public void setItemPositions(Long categoryId, List<Long> itemIds) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categoryId", categoryId);
        params.put("itemIds", itemIds);
        call("eleme.product.item.setItemPositions", params);
    }

    /**
     * 批量沽清库存并在次日2:00开始置满
     *
     * @param clearStocks 店铺Id及商品Id的列表
     * @throws ServiceException 服务异常
     */
    public void clearAndTimingMaxStock(List<ClearStock> clearStocks) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("clearStocks", clearStocks);
        call("eleme.product.item.clearAndTimingMaxStock", params);
    }

    /**
     * 根据商品扩展码获取商品
     *
     * @param shopId 店铺Id
     * @param extendCode 商品扩展码
     * @return 商品
     * @throws ServiceException 服务异常
     */
    public OItem getItemByShopIdAndExtendCode(Long shopId, String extendCode) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("extendCode", extendCode);
        return call("eleme.product.item.getItemByShopIdAndExtendCode", params);
    }

    /**
     * 根据商品条形码获取商品
     *
     * @param shopId 店铺Id
     * @param barCode 商品条形码
     * @return 商品
     * @throws ServiceException 服务异常
     */
    public OItem getItemsByShopIdAndBarCode(Long shopId, String barCode) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("barCode", barCode);
        return call("eleme.product.item.getItemsByShopIdAndBarCode", params);
    }

    /**
     * 批量修改商品价格
     *
     * @param shopId 店铺Id
     * @param specPrices 商品Id及其下SkuId和价格对应Map(限制最多50个)
     * @throws ServiceException 服务异常
     */
    public void batchUpdatePrices(Long shopId, List<OItemIdWithSpecPrice> specPrices) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        params.put("specPrices", specPrices);
        call("eleme.product.item.batchUpdatePrices", params);
    }

    /**
     * 查询活动商品
     *
     * @param shopId 店铺Id
     * @return 商品ID集合
     * @throws ServiceException 服务异常
     */
    public List<Long> getItemIdsHasActivityByShopId(Long shopId) throws ServiceException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("shopId", shopId);
        return call("eleme.product.item.getItemIdsHasActivityByShopId", params);
    }
}
