/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Niels
 */
public class TrainVehicleCount extends LightVehicleCount implements Cloneable
{
    public TrainVehicleCount(TrainLight l)
    {
        super(l);
    }
    
    @Override
    public void calculatePriorty(long currentTime)
    {
        if(_count > 0)
        {
            _priority = (long)999999999;
        }
        else
        {
            _priority = (long)0;
        }
    }
    
    @Override
    public LightVehicleCount clone()
    {
        TrainVehicleCount count = new TrainVehicleCount((TrainLight)_light);
        count._count = _count;
        count._directionRequests = _directionRequests;
        count._priority = _priority;
        return  count;
    }
}
