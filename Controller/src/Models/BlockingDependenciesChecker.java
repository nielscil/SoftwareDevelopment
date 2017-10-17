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
    
    public void run()
    {
        internalRun(_light);
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
                if(ControlRunner.getVehicleCount(dependency.Light.Id).getPriorty() < _priority)
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
