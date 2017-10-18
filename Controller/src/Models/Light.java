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
    
    protected final transient List<Dependency> _dependencies = new ArrayList<>();
    
    public Light(int id)
    {
        Id = LightNumber.get(id);
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
        if(canSetStatus(state) && state != Status)
        {
            Status = state;
            _statusChangedTime = ControlRunner.getTime();
            setChanged();
            notifyObservers();
        }
    }
    
    public boolean canSetStatus(State state)
    {
        if(state.isGreen() && Status.isOrange())
        {
            return false;
        }

        if(state.isRed() && Status.isGreen())
        {
            return false;
        }
        
        if(Status.isGreen() && ControlRunner.getTime() - _statusChangedTime <= 2)
        {
            return false;
        }
        
        return (!hasChanged() || Status.isRed()) && (!state.isGreen() || GetBlockingDependencies().isEmpty());
    }    
    
    public List<Dependency> GetBlockingDependencies()
    {
        List<Dependency> list = new ArrayList<>();
        
        _dependencies.stream().forEach((dependency) ->
        {
            Light light = dependency.Light;
            if (light.Status.isGreen() || light.Status.isOrange())
            {
                if(dependency.Direction != null && light instanceof BusLight && !light.Status.isOrange())
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
