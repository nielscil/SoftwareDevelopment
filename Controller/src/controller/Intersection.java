/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Models.Light;
import Models.LightNumber;
import Models.SerializeableIntersection;
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
        AddLights(200, 1);
        
        //bicyle traffic lights
        AddLights(300, 5);
        
        //walk traffic lights
        AddLights(400, 6);
        
        //train lights
        AddLights(500, 1);
        AddLights(600, 1);
    }
    
    private void AddLights(int IdOffset, int count)
    {
        for(int i = 1; i <= count; i++)
        {
            Light light = new Light(IdOffset + i);
            _lights.add(light);
            _lightsMap.put(light.Id, light);
            light.addObserver(this);
        }
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
