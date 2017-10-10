/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Observable;

/**
 *
 * @author Niels
 */
public class Light extends Observable
{
    public LightNumber Id;
    public State Status = State.Red;
    //private final transient List<Light> _dependencies = new ArrayList<>();
    
    public Light(int id)
    {
        Id = LightNumber.get(id);
    }
    
    public void setStatus(State state) throws Exception
    {
        CheckIfNewStateIsPossible(state);
        
        Status = state;
        setChanged();
        notifyObservers();
    }
    
    public boolean canSetStatus(State state)
    {
        try
        {
            CheckIfNewStateIsPossible(state);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
    
    private void CheckIfNewStateIsPossible(State state) throws Exception
    {
        if(Id != LightNumber.SouthRailRoadCrossing_601)
        {
            if(state.isGreen() && Status.isOrange())
            {
                throw new Exception("Can't set orange to green without going to red");
            }
            
            if(state.isRed() && Status.isGreen())
            {
                throw new Exception("Can't set green to red without going to orange");
            }
        }
    }    
}
