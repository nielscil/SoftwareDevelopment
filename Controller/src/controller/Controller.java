/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Models.LightNumber;
import Models.LightVehicleCount;
import Models.ObserverArgs;
import Models.Speed;
import Models.TrafficUpdate;
import Models.VehicleCount;
import controller.Helpers.ClassHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;


/**
 *
 * @author Niels
 */
public class Controller implements Observer
{
    private ConnectionProvider _provider;
    private ControlRunner _runner;
    
    
    public Controller(String host, String username, String password, boolean useGroup)
    {
        try
        {
            _provider = new ConnectionProvider(host, username, password, useGroup);
            _provider.addObserver(this);
            
            _runner = ControlRunner.create(this);
            _vehicleUpdateMap = _runner.GetTrafficUpdateMap();
            _runner.run();
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
    }

    public ConnectionProvider getConnectionProvider()
    {
        return _provider;
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
        ObserverArgs observerArgs = ClassHelper.safeCast(arg, ObserverArgs.class);
        if(observerArgs != null)
        {
            if(observerArgs.hasTrafficUpdate())
            {
                setLight(observerArgs.getTrafficUpdate());
            }
            
            if(observerArgs.hasSpeed())
            {
                setSpeed(observerArgs.getSpeed());
            }
        }
        else
        {
            System.err.println("Observerargs are empty. Something is wrong while receiving.");
        }
    }
    
    private Map<LightNumber,VehicleCount> _vehicleUpdateMap;
    
    private final Object _syncLock = new Object();
    public void setLight(TrafficUpdate update)
    {
        synchronized (_syncLock)
        {
            _vehicleUpdateMap.get(update.LightId).setUpdate(update);
        }
    }
    
    private float _speed = 1.0f;
    
    public void setSpeed(Speed speed)
    {
        synchronized (_syncLock)
        {
            _speed = speed.Speed;
        }
    }
    
    public float checkSpeed(float currentSpeed)
    {
        boolean speedChanged;
        Float speed = currentSpeed;
        synchronized (_syncLock)
        {
            speedChanged = !speed.equals(_speed);
            speed = _speed;
        }
        
        if(speedChanged)
        {
            _provider.Send(new Speed(speed));
        }
        
        return speed;
    }
    
    public Map<LightNumber,LightVehicleCount> getLights()
    {
        synchronized (_syncLock)
        {
            Map<LightNumber, LightVehicleCount> map = new HashMap<>();
            _vehicleUpdateMap.entrySet().stream().forEach((set) ->
            {
                map.put(set.getKey(), ((LightVehicleCount)set.getValue()).clone());
            });
            return map;
        }       
    }
    
}
