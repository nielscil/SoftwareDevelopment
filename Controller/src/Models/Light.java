/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import controller.ControlRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Niels
 */
public class Light extends Observable
{
    public final LightNumber Id;
    public State Status = State.Red;
    protected int _statusChangedTime = 0;
    protected boolean _blocked = false;
    
    protected final transient List<Dependency> _dependencies = new ArrayList<>();
    private final int _minClearanceTime;

    public Light(int id, int clearanceTime)
    {
        Id = LightNumber.get(id);
        _minClearanceTime = clearanceTime;
    }
    
    public int minClearanceTime()
    {
        return _minClearanceTime;
    }
    
    private int _currentClearanceTime = 0;
    public void setCurrentClearanceTime(int clearanceTime)
    {
        _currentClearanceTime = clearanceTime;
    }
    
    public boolean isInClearanceTime()
    {
        return _currentClearanceTime > ControlRunner.getTime();
    }
    
    
    public void addDependency(Light light)
    {
        addDependency(light, null);
    }
    
    public void addDependency(Light light, Direction direction)
    {
        Dependency dependency = new Dependency(light, direction);
        if(!_dependencies.contains(dependency))
        {
            _dependencies.add(dependency);
        }
    }
    
    public long getStatusChangedTime()
    {
        return _statusChangedTime;
    }
    
    public void setStatus(State state)
    {
        if(canSetStatus(state))
        {
            Status = state;
            _statusChangedTime = ControlRunner.getTime();
            unBlockDependecies();
            setChanged();
            notifyObservers();
        }
    }
    
    public boolean canSetStatus(State newState)
    {
        if(newState == Status)
        {
            return false;
        }
        
        if(newState.isGreen() && Status.isOrange())
        {
            return false;
        }

        if(newState.isRed() && Status.isGreen())
        {
            return false;
        }
        
        if(Status.isGreen() && ControlRunner.getTime() - _statusChangedTime <= 5) //minimum 5 secs of green time
        {
            return false;
        }
        
        if((Status.isOrange() || Status.isRed()) && ControlRunner.getTime() - _statusChangedTime <= 2) //minimum 2 secs of orange time
        {
            return false;
        }
        
        if(newState.isGreen() && isBlocked())
        {
            return false;
        }
        
        return (!hasChanged() || Status.isRed()) && (!newState.isGreen() || GetBlockingDependencies().isEmpty());
    }
    
    public boolean isBlocked()
    {
        return _blocked;
    }
    
    public void block()
    {
        _blocked = true;
    }
    
    public void unBlock()
    {
        _blocked = false;
    }
    
    private void unBlockDependecies()
    {
        _dependencies.stream().filter((d) -> d.Light.isBlocked()).forEach((d) ->
        {
            d.Light.unBlock();
        });
    }
    
    public List<Dependency> GetBlockingDependencies()
    {
        List<Dependency> list = new ArrayList<>();
        
        _dependencies.stream().forEach((dependency) ->
        {
            Light light = dependency.Light;
            if (light.Status.isGreen() || light.Status.isOrange() || light.isInClearanceTime())
            {
                if(dependency.Direction != null && light instanceof BusLight && (!light.Status.isOrange()))
                {
                    if(light.Status.isGreen(dependency.Direction))
                    {
                        list.add(dependency);
                    }
                }
                else
                {
                    list.add(dependency);
                }
            }
        });
        
        return list;
    }
}
