package com.kiran.productapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomGsonBuilder {

    public static GsonBuilder getInstance() {

        JsonSerializer<Date> ser = new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return src == null ? null : new JsonPrimitive(src.getTime());
            }
        };

        JsonDeserializer<Date> desr = new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                if (json.isJsonObject()) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(json.getAsJsonObject().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return json == null ? null : new Date(jsonObject.optLong("millis"));
                }else{
                    if(((JsonPrimitive)json).isString()){
                        return json == null ? null : getDate(json.getAsString());
                    }else{
                        return json == null ? null : new Date(json.getAsLong());
                    }
                }
            }
        };

        return new GsonBuilder()
                .registerTypeAdapter(Date.class,ser)
                .registerTypeAdapter(Date.class,desr);
    }

    public static <T> T fromJson(String jsonData, Class<T> type){

        Gson gson = getInstance().create();
        return gson.fromJson(jsonData, type);
    }

    public static <T> T fromJsonList(String jsonData, Type type){
        Gson gson = getInstance().create();
        T target = gson.fromJson(jsonData,type);

        return target;
    }

    private static Date getDate(String dtStart)
    {

        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
        try {
            return format.parse(dtStart);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
