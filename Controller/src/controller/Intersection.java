/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Models.BusLight;
import Models.CrosswayLight;
import Models.Light;
import Models.LightNumber;
import Models.SerializeableIntersection;
import Models.TrainLight;
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
        AddLights(100, 10, 5);
        
        //bus traffic light
        AddLights(200, 1, 7, BusLight.class);
        
        //bicyle traffic lights
        AddLights(300, 5, 7);
        
        //walk traffic lights
        AddLights(400, 12, 12);
        
        //train lights
        AddLights(500, 2, 0, TrainLight.class);
        AddLights(600, 1, 0, CrosswayLight.class);
        
        //populate dependencies
        DependenciesHelper.populate(this);
    }
    
    private <T extends Light> void AddLights (int IdOffset, int count,int clearanceTime, Class<T> classType)
    {
        try
        {
            Constructor<T> constructor = classType.getConstructor(new Class<?>[] { int.class, int.class });
            for(int i = 1; i <= count; i++)
            {
                T light = constructor.newInstance(IdOffset + i, clearanceTime);
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
    
    private <T extends Light> void AddLights (int IdOffset, int count, int clearanceTime)
    {
        AddLights(IdOffset,count, clearanceTime, Light.class);
    }
            
    public Light getLight(LightNumber id)
    {
        return _lightsMap.get(id);
    }
    
    public List<Light> getLights()
    {
        return _lights;
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
    
    public void saveChanges()
    {
        notifyObservers();
    }
    
}
