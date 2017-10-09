/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serializers;

import Models.Direction;
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
public class DirectionSerializer implements JsonDeserializer<Direction>, JsonSerializer<Direction>
{
    @Override
    public Direction deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException
    {
        int direction = je.getAsInt();
        return Direction.get(direction);
    }

    @Override
    public JsonElement serialize(Direction t, Type type, JsonSerializationContext jsc)
    {
        return jsc.serialize(t.getValue());
    }
}
