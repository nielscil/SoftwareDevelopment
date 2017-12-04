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
    protected Light _blockedBy = null;
    protected Long _firstVehicleTime = null;
    
    protected final transient List<Dependency> _dependencies = new ArrayList<>();
    protected final int _minClearanceTime;

    public Light(int id, int clearanceTime)
    {
        Id = LightNumber.get(id);
        _minClearanceTime = clearanceTime;
    }
       
    public boolean isInClearanceTime()
    {
        if(Status.isRed())
        {
            return _statusChangedTime + _minClearanceTime > ControlRunner.getTime();
        }
        return false;
    }
    
    public boolean isInTrainClearanceTime()
    {
        if(Status.isRed())
        {
            return _statusChangedTime + 5 > ControlRunner.getTime();
        }
        return false;
    }
    
    public Long getFirstVehicleTime()
    {
        return _firstVehicleTime;
    }
    
    public void setFirstVehicleTime(long time)
    {
        _firstVehicleTime = time;
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
        setStatus(state, false);
    }
    
    public void setStatus(State state, boolean override)
    {
        if(canSetStatus(state) || override)
        {
            Status = state;
            _statusChangedTime = ControlRunner.getTime();
            
            if(Status.isGreen())
            {
                _firstVehicleTime = null;
            }
            unBlockDependencies();
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
        
        if(newState.isOrange() && !Status.isGreen())
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
    
    public void block(Light l)
    {
        _blocked = true;
        _blockedBy = l;
    }
    
    public void unBlock()
    {
        _blocked = false;
        _blockedBy = null;
    }
    
    public Light blockedBy()
    {
        return _blockedBy;
    }
    
    protected void unBlockDependencies()
    {
        _dependencies.stream().filter((d) -> d.Light.isBlocked()).forEach((d) ->
        {
            d.Light.unBlock();
        });
    }
    
    public void BlockAllDependecies()
    {
        _dependencies.stream().forEach((d)->
        {
            if(!d.Light.isBlocked() || HasHigherPriority(d.Light))
            {
                d.Light.block(this);
            } 
        });
    }
    
    public boolean HasHigherPriority(Light l)
    {
        return ControlRunner.getVehicleCount(l.blockedBy().Id).getPriorty() < ControlRunner.getVehicleCount(this.Id).getPriorty();
    }
    
    public List<Dependency> GetBlockingDependencies()
    {
        return InternalGetBlockingDependencies(_dependencies);
    }
    
    protected List<Dependency> InternalGetBlockingDependencies(List<Dependency> allDependencies)
    {
        List<Dependency> list = new ArrayList<>();
        
        allDependencies.stream().forEach((dependency) ->
        {
            Light light = dependency.Light;
            
            boolean shouldAdd;
            if(this instanceof CrosswayLight)
            {
                shouldAdd = shouldAddTrain(light);
            }
            else
            {
                shouldAdd = shouldAddNormal(light);
            }
            
            if (shouldAdd)
            {
                if(dependency.Direction != null && light instanceof BusLight && !light.Status.isOrange())
                {
                    if(light.Status.isGreen(dependency.Direction) || ((BusLight)light).isInClearanceTime(dependency.Direction))
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
    
    private boolean shouldAddNormal(Light light)
    {
        boolean shouldAdd;
        if(light instanceof CrosswayLight)
        {
            shouldAdd = light.Status.isRed() || light.isInClearanceTime();
        }
        else
        {
            shouldAdd = light.Status.isGreen() || light.Status.isOrange() || light.isInClearanceTime();
        }
        return shouldAdd;
    }
    
    private boolean shouldAddTrain(Light light)
    {
        boolean shouldAdd;
        if(light instanceof CrosswayLight)
        {
            shouldAdd = light.Status.isRed() || light.isInTrainClearanceTime();
        }
        else
        {
            shouldAdd = light.Status.isGreen() || light.Status.isOrange() || light.isInTrainClearanceTime();
        }
        return shouldAdd;
    }
}
