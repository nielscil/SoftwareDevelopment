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
public class LightVehicleCount extends VehicleCount implements Comparable<LightVehicleCount>, Cloneable
{
    protected final Light _light; 
    
    public LightVehicleCount(Light l)
    {
        _light = l;
    }
    
    public Light getLight()
    {
        return _light;
    }
    
    protected Long _priority = null;
    public void calculatePriorty(long currentTime)
    {
        long timeAmount = currentTime - _light.getStatusChangedTime();
        
        if(_light.Status.isGreen())
        {
            if(timeAmount > 5)
            {
                timeAmount = timeAmount * -1;
            }
            else
            {
                timeAmount = 0;
            }
        }
        
        _priority = _count * 40 + timeAmount;
    }
    
    public long getPriorty()
    {
        return  _priority;
    }

    @Override
    public int compareTo(LightVehicleCount o)
    {
        return o._priority.compareTo(_priority);
    }
    
    @Override
    public LightVehicleCount clone()
    {
        LightVehicleCount count = new LightVehicleCount(_light);
        count._count = _count;
        count._directionRequests = _directionRequests;
        return  count;
    }
}
