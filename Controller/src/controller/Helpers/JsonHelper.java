/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Helpers;

import Models.*;
import Serializers.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 *
 * @author Niels
 */
public class JsonHelper
{  
    private static JsonHelper _instance;
    
    private static final String LightTypeIdentifier = "TrafficUpdate";
    private static final String SpeedTypeIdentifier = "Speed";
    
    private final Gson _gson;
    private JsonHelper()
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Direction.class, new DirectionSerializer());
        builder.registerTypeAdapter(State.class, new StatusSerializer());
        builder.registerTypeAdapter(LightNumber.class, new LightNumberSerializer());
        builder.serializeNulls();
        _gson = builder.create();
    }
      
    public ObserverArgs Parse(String input)
    {
        TrafficUpdate trafficUpdate = null;
        Speed speed = null;
        try
        {
            JsonObject jsonObj = _gson.fromJson(input, JsonObject.class);

            if(jsonObj.has(LightTypeIdentifier))
            {
                trafficUpdate = _gson.fromJson(jsonObj.get(LightTypeIdentifier), TrafficUpdate.class);
            }
            
            if(jsonObj.has(SpeedTypeIdentifier))
            {
                //If we want to use speed, uncomment this below. Needs Lots of testing!!
                //speed = _gson.fromJson(jsonObj, Speed.class); 
            }

        }
        catch(Exception e)
        {
        }        
        return new ObserverArgs(trafficUpdate, speed);
    }
    
    public String Serialize(Object obj)
    {
        return _gson.toJson(obj);
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
