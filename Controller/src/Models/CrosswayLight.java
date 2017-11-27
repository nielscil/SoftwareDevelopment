/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import controller.ControlRunner;

/**
 *
 * @author Niels
 */
public class CrosswayLight extends Light
{
    public CrosswayLight(int id, int clearanceTime)
    {
        super(id, clearanceTime);
    }
    
    @Override
    public void setStatus(State state)
    {
        if(state == State.Orange)
        {
            state = State.Green;
        }
        
        super.setStatus(state);
    }
    
    @Override
    public boolean isInClearanceTime()
    {
        if(Status.isGreen())
        {
            return _statusChangedTime + _minClearanceTime > ControlRunner.getTime();
        }
        return false;
    }
    
    @Override
    public boolean canSetStatus(State state)
    {
        if(state.isGreen() && _statusChangedTime + 20 < ControlRunner.getTime())
        {
            return false;
        }
        
        
        
        return ControlRunner.getTime() != _statusChangedTime && GetBlockingDependencies().isEmpty();
    }   
    
    public void setDependenciesToOrange()
    {
        _dependencies.stream().forEach((d) ->
        {
            d.Light.setStatus(State.Orange);
        });
    }
}
