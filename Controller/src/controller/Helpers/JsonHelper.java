/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Helpers;

import Models.Direction;
import Models.Light;
import Models.Speed;
import Models.Status;
import Models.TrafficUpdate;
import Serializers.DirectionSerializer;
import Serializers.StatusSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;

/**
 *
 * @author Niels
 */
public class JsonHelper
{  
    private static JsonHelper _instance;
    
    private static final String LightTypeIdentifier = "LightId";
    private static final String SpeedTypeIdentifier = "Speed";
    
    private final Gson _gson;
    private JsonHelper()
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Direction.class, new DirectionSerializer());
        builder.registerTypeAdapter(Status.class, new StatusSerializer());
        builder.serializeNulls();
        _gson = builder.create();
    }
      
    public Object Parse(String input)
    {
        return _gson.fromJson(input, GetType(input));
    }
    
    public String Serialize(Object obj)
    {
        return _gson.toJson(obj);
    }
    
    private Type GetType(String input)
    {
        try
        {
            JsonObject obj = _gson.fromJson(input, JsonObject.class);
            
            if(obj.has(LightTypeIdentifier))
            {
                return TrafficUpdate.class;
            }
            
            if(obj.has(SpeedTypeIdentifier))
            {
                return Speed.class;
            }

        }
        catch(Exception e)
        {
            return null;
        }
        return null;
    }
    
    public boolean isValidJSON(String toTestStr) 
    {
        try
        {
            _gson.toJsonTree(toTestStr);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
    
    public static JsonHelper instance()
    {
        if (_instance == null)
        {
            _instance = new JsonHelper();
        }
        
        return _instance;
    }
}
