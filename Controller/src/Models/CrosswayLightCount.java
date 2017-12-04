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
public class CrosswayLightCount extends LightVehicleCount implements Cloneable
{
    public CrosswayLightCount(CrosswayLight l)
    {
        super(l);
        _priority = (long)0;
    }
    
    private boolean _incoming;
    public void setTrainSignalTime(boolean incoming)
    {
        _incoming = incoming;
    }

    public CrosswayLightCount()
    {
        super(null);
    }
    
    @Override
     public void calculatePriorty(long currentTime)
    {
        _priority = (long)(_incoming ? Long.MAX_VALUE : 0);
    }
     
    @Override
    public LightVehicleCount clone()
    {
        CrosswayLightCount count = new CrosswayLightCount((CrosswayLight)_light);
        count._count = _count;
        count._directionRequests = _directionRequests;
        count._priority = _priority;
        return  count;
    }
    
}
