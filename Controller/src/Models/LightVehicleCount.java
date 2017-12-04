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
        long timeAmount = 0;
        
        setFirstVehicleTime(currentTime);
        
        if(_light.Status.isRed() && _count > 0)
        {
            timeAmount = currentTime - _light.getFirstVehicleTime();
            
            if(timeAmount > 60)
            {
                timeAmount += 3000; //increase prio when longer than 60 sec on red
            }
        }
        
        _priority = _count * 40 + timeAmount;
    }
    
    private void setFirstVehicleTime(long currentTime)
    {
        if(_light.Status.isRed())
        {
            if(_count > 0 && _light.getFirstVehicleTime() == null)
            {
                _light.setFirstVehicleTime(currentTime);
            }
            else if(_count == 0 && _light.getFirstVehicleTime() != null)
            {
                _light.setFirstVehicleTime(null);
            }
        }
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
