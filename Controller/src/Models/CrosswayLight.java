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
        else if (state == State.Green)
        {
            state = State.Red;
        }
        
        super.setStatus(state);
    }
    
    @Override
    public boolean canSetStatus(State state)
    {
        return ControlRunner.getTime() != _statusChangedTime;
    }   
    
}
