package com.opensdk.baidu.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class CategorySerializer implements JsonSerializer<Category> {
	@Override
    public JsonElement serialize(Category category, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("name", context.serialize(category.getName()));
        object.add("shop_id", context.serialize(category.getShop_id()));
        object.add("nambai_shop_ide", context.serialize(category.getNambai_shop_ide()));
        object.add("rank", context.serialize(category.getRank()));
        object.add("must", context.serialize(category.getMust()));
        return object;
    }
}
