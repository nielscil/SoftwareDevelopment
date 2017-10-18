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
    private final List<Light> _alreadyChecked = new ArrayList<>();
    public BlockingDependenciesChecker(Light light)
    {
        _light = light;
        _priority = ControlRunner.getVehicleCount(_light.Id).getPriorty();
    }
    
    private State _state;
    public BlockingDependenciesChecker(BusLight light, State state)
    {
        this(light);
        _state = state;
    }
    
    public void run()
    {
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
        light.GetBlockingDependencies()
                .stream()
                .filter((dependency) -> !(_alreadyChecked.contains(dependency.Light)))
                .forEach((dependency) ->
        {
            if(dependency.Light.canSetStatus(State.Orange))
            {
                boolean lightIsNotOrange = !dependency.Light.Status.isOrange();
                if(ControlRunner.getVehicleCount(dependency.Light.Id).getPriorty() < _priority && lightIsNotOrange)
                {
                    dependency.Light.setStatus(State.Orange);
                }
            }
            else
            {
                internalRun(dependency.Light);
                
            }
        });
        _alreadyChecked.add(light);
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
                dependencies.addAll(light.GetBlockingDependencies(Direction.Left));
            }

            if(_state.isGreen(Direction.Right))
            {
                dependencies.addAll(light.GetBlockingDependencies(Direction.Right));
            }

            if(_state.isGreen(Direction.StraightAhead))
            {
                dependencies.addAll(light.GetBlockingDependencies(Direction.StraightAhead));
            }
        }
        
        dependencies
                .stream()
                .filter((dependency) -> !(_alreadyChecked.contains(dependency.Light)))
                .forEach((dependency) ->
        {
            if(dependency.Light.canSetStatus(State.Orange))
            {
                boolean lightIsNotOrange = !dependency.Light.Status.isOrange();
                if(ControlRunner.getVehicleCount(dependency.Light.Id).getPriorty() < _priority && lightIsNotOrange)
                {
                    dependency.Light.setStatus(State.Orange);
                }
            }
            else
            {
                internalRun(dependency.Light);
                
            }
        });
        _alreadyChecked.add(light);
    }   
    
}
