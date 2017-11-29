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
public class BlockingDependenciesChecker
{
    private final Light _light;
    private final long _priority;
    private State _state;
    private final List<Light> _alreadyChecked = new ArrayList<>();
    
    public BlockingDependenciesChecker(Light light)
    {
        _light = light;
        _priority = ControlRunner.getVehicleCount(_light.Id).getPriorty();
    }
    
    public BlockingDependenciesChecker(BusLight light, State state)
    {
        this(light);
        _state = state;
    }
    
    public void run()
    {
        //Cannot put a blocked light on green, so no need to check dependencies
        if(_light.isBlocked())
        {
            if(!_light.HasHigherPriority(_light))
            {
                return;
            }
            _light.unBlock();
        }
        
        if(_light instanceof BusLight)
        {
            internalRunBusLight((BusLight)_light);
        }
        else
        {
            internalRun(_light);
        }
    }
    
    private void internalRun(Light light)
    {
        light.BlockAllDependecies();
        CheckDependencies(light, light.GetBlockingDependencies());
    }
    
    private void CheckDependencies(Light light, List<Dependency> dependencies)
    {
        _alreadyChecked.add(light);
        dependencies.stream()
                .filter((dependency) -> !(_alreadyChecked.contains(dependency.Light)))
                .forEach((dependency) ->
        {
            boolean hasHigherPrio = ControlRunner.getVehicleCount(dependency.Light.Id).getPriorty() < _priority;
            if(hasHigherPrio && dependency.Light.canSetStatus(State.Orange) && !(dependency.Light instanceof CrosswayLight))
            {
                dependency.Light.setStatus(State.Orange);
            }
        });
    }
    
    private void internalRunBusLight(BusLight light)
    {
        List<Dependency> dependencies = new ArrayList<>();
        
        if(_state == State.GreenAll)
        {
            dependencies = light.GetBlockingDependencies();
        }
        else
        {
            if(_state.isGreen(Direction.Left))
            {
                light.BlockAllDependecies(Direction.Left);
                dependencies.addAll(light.GetBlockingDependencies(Direction.Left));
            }

            if(_state.isGreen(Direction.Right))
            {
                light.BlockAllDependecies(Direction.Right);
                dependencies.addAll(light.GetBlockingDependencies(Direction.Right));
            }

            if(_state.isGreen(Direction.StraightAhead))
            {
                light.BlockAllDependecies(Direction.StraightAhead);
                dependencies.addAll(light.GetBlockingDependencies(Direction.StraightAhead));
            }
        }
        CheckDependencies(_light, dependencies);
    }    
}
