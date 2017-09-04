package com.opensdk.baidu.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class DishSerializer implements JsonSerializer<Dish> {
	@Override
    public JsonElement serialize(Dish dish, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("name", context.serialize(dish.getName()));
        object.add("shop_id", context.serialize(dish.getShop_id()));

        object.add("dish_id", context.serialize(dish.getDish_id()));
        object.add("baidu_shop_id", context.serialize(dish.getBaidu_shop_id()));
        object.add("price", context.serialize(dish.getPrice()));
        object.add("pic", context.serialize(dish.getPic()));
        object.add("min_order_num", context.serialize(dish.getMin_order_num()));
        object.add("package_box_num", context.serialize(dish.getPackage_box_num()));
        object.add("description", context.serialize(dish.getDescription()));
        object.add("stock", context.serialize(dish.getStock()));
        object.add("category", context.serialize(dish.getCategorys()));
        object.add("norms[]", context.serialize(dish.getNorms()));
        return object;
    }
}
