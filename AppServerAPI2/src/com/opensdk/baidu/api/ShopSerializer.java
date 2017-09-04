package com.opensdk.baidu.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ShopSerializer implements JsonSerializer<Shop> {
	@Override
    public JsonElement serialize(Shop shop, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("name", context.serialize(shop.getName()));
        object.add("shop_id", context.serialize(shop.getShopId()));
        object.add("baidu_shop_id", context.serialize(shop.getBaidu_shop_id()));
        return object;
    }
}
