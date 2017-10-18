/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import controller.ControlRunner;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niels
 */
public class BusLight extends Light
{
    private final transient List<Dependency> _dependenciesRight = new ArrayList<>();
    private final transient List<Dependency> _dependenciesStraight = new ArrayList<>();
    private final transient List<Dependency> _dependenciesLeft = new ArrayList<>();
    
    
    public BusLight(int id)
    {
        super(id);
    }
    
    @Override
    public void addDependency(Light light, Direction direction)
    {
        Dependency dependency = new Dependency(light, null);
        if(direction == Direction.Right && !_dependenciesRight.contains(dependency))
        {
            _dependenciesRight.add(dependency);
        }
        
        if(direction == Direction.StraightAhead && !_dependenciesStraight.contains(dependency))
        {
            _dependenciesStraight.add(dependency);
        }
        
        if(direction == Direction.Left && !_dependenciesLeft.contains(dependency))
        {
            _dependenciesLeft.add(dependency);
        }
        
        if(!_dependencies.contains(dependency))
        {
            _dependencies.add(dependency);
        }
    }
    
    @Override
    public void addDependency(Light light)
    {
        Dependency dependency = new Dependency(light, null);
        if(!_dependenciesRight.contains(dependency))
        {
             _dependenciesRight.add(dependency);
        }
        
        if(!_dependenciesStraight.contains(dependency))
        {
            _dependenciesStraight.add(dependency);
        }
        
        if(!_dependencies.contains(dependency))
        {
            _dependencies.add(dependency);
        }
    }   
    
    @Override
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
        
        if(state.isGreen(Direction.Right) && !GetBlockingDependencies(Direction.Right).isEmpty())
        {
            return false;
        }
        
        if(state.isGreen(Direction.StraightAhead) && !GetBlockingDependencies(Direction.StraightAhead).isEmpty())
        {
            return false;
        }
        
        if(state.isGreen(Direction.Left) && !GetBlockingDependencies(Direction.Left).isEmpty())
        {
            return false;
        }
        
        return !hasChanged() || Status.isRed();
    }
    
    public List<Dependency> GetBlockingDependencies(Direction direction)
    {
        List<Dependency> list = new ArrayList<>();
        
        List<Dependency> dependencies;
        if(direction == Direction.Right)
        {
            dependencies = _dependenciesRight;
        }
        else if(direction == Direction.Left)
        {
            dependencies = _dependenciesLeft;
        }
        else
        {
            dependencies = _dependenciesStraight;
        }
        
        dependencies.stream().forEach((dependency) ->
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
