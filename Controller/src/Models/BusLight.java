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
    
    
    public BusLight(int id, int clearanceTime)
    {
        super(id, clearanceTime);
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
        
//        if(Status.isGreen() && ControlRunner.getTime() - _statusChangedTime <= 5) //minimum 5 secs of green time
//        {
//            return false;
//        }
        
        if(/*(Status.isOrange() || Status.isRed()) &&*/ ControlRunner.getTime() - _statusChangedTime <= 2)
        {
            return false;
        }
        
        if(newState.isGreen(Direction.Right) && !GetBlockingDependencies(Direction.Right).isEmpty())
        {
            return false;
        }
        
        if(newState.isGreen(Direction.StraightAhead) && !GetBlockingDependencies(Direction.StraightAhead).isEmpty())
        {
            return false;
        }
        
        if(newState.isGreen(Direction.Left) && !GetBlockingDependencies(Direction.Left).isEmpty())
        {
            return false;
        }
        
        if(newState.isGreen() && isBlocked())
        {
            return false;
        }
        
        return !hasChanged() || Status.isRed();
    }
    
    public void BlockAllDependecies(Direction direction)
    {
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
        
        dependencies.stream().forEach((d) ->
        {
            d.Light.block(this);
        });
    }
    
    public List<Dependency> GetBlockingDependencies(Direction direction)
    {
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
        return InternalGetBlockingDependencies(dependencies);
    }
}
