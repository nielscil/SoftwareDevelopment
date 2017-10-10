/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serializers;

import Models.LightNumber;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author Niels
 */
public class LightNumberSerializer implements JsonDeserializer<LightNumber>, JsonSerializer<LightNumber>
{

    @Override
    public LightNumber deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        return LightNumber.get(je.getAsInt());
    }

    @Override
    public JsonElement serialize(LightNumber t, Type type, JsonSerializationContext jsc)
    {
        int test = t.getValue();
        return jsc.serialize(test);
    }
    
}
