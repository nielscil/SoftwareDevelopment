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
public class TrainLight extends Light
{
    public TrainLight(int id, int clearanceTime)
    {
        super(id, clearanceTime);
    }
    
    @Override
    public void setStatus(State state)
    {
        if(canSetStatus(state))
        {
            Status = state;
            _statusChangedTime = ControlRunner.getTime();
            
            if(state == State.Green)
            {
                ((CrosswayLightCount)ControlRunner.getVehicleCount(LightNumber.SouthRailRoadCrossing_601)).setTrainSignalTime(true);
            }
            
            if(state == State.Red)
            {
                ((CrosswayLightCount)ControlRunner.getVehicleCount(LightNumber.SouthRailRoadCrossing_601)).setTrainSignalTime(false);
                ControlRunner.getVehicleCount(LightNumber.SouthRailRoadCrossing_601)._light.Status = State.Red;
            }
            
            setChanged();
            notifyObservers();
        }
    }
    
    @Override
    public boolean canSetStatus(State state)
    {
        return ControlRunner.getTime() != _statusChangedTime;
    }
}
