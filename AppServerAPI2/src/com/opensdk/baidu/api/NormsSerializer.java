package com.opensdk.baidu.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class NormsSerializer implements JsonSerializer<Norms> {
	@Override
    public JsonElement serialize(Norms norms, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("selfid", context.serialize(norms.getSelfid()));
        object.add("is_select", context.serialize(norms.getIs_select()));
        object.add("stock", context.serialize(norms.getStock()));
        object.add("value", context.serialize(norms.getValue()));
        object.add("price", context.serialize(norms.getPrice()));
        return object;
    }
}
