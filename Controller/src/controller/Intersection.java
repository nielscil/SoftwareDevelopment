/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Models.BusLight;
import Models.Direction;
import Models.Light;
import Models.LightNumber;
import Models.SerializeableIntersection;
import controller.Helpers.DependenciesHelper;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Niels
 */
public class Intersection extends Observable implements Observer
{
    private final List<Light> _lights;
    private final Map<LightNumber,Light> _lightsMap;
    
    public Intersection()
    {
        _lights = new ArrayList<>();
        _lightsMap = new HashMap<>();
        
        //normal traffic lights
        AddLights(100, 10);
        
        //bus traffic light
        AddLights(200, 1, BusLight.class);
        
        //bicyle traffic lights
        AddLights(300, 5);
        
        //walk traffic lights
        AddLights(400, 6);
        
        //train lights
        AddLights(500, 1);
        AddLights(600, 1);
        
        //populate dependencies
        DependenciesHelper.populate(this);
    }
    
    private <T extends Light> void AddLights (int IdOffset, int count, Class<T> classType)
    {
        try
        {
            Constructor<T> constructor = classType.getConstructor(new Class<?>[] { int.class });
            for(int i = 1; i <= count; i++)
            {
                T light = constructor.newInstance(IdOffset + i);
                _lights.add(light);
                _lightsMap.put(light.Id, light);
                light.addObserver(this);
            }
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }
    
    private <T extends Light> void AddLights (int IdOffset, int count)
    {
        AddLights(IdOffset,count, Light.class);
    }
            
    public Light getLight(LightNumber id)
    {
        return _lightsMap.get(id);
    }
    
    public SerializeableIntersection GetSerializable()
    {
        return new SerializeableIntersection(_lights);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        setChanged();
    }
    
    public void Send()
    {
        notifyObservers();
    }
    
}
