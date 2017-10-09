/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serializers;

import Models.Status;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 *
 * @author Niels
 */
public class StatusSerializer implements JsonSerializer<Status>
{

    @Override
    public JsonElement serialize(Status t, Type type, JsonSerializationContext jsc)
    {
        return jsc.serialize(t.ordinal());
    }
    
}
