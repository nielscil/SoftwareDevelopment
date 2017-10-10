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
public class ObserverArgs
{
    private final TrafficUpdate _trafficUpdate;
    private final Speed _speed;
    
    public ObserverArgs(TrafficUpdate trafficUpdate, Speed speed)
    {
        _trafficUpdate = trafficUpdate;
        _speed = speed;
    }
    
    public boolean hasTrafficUpdate()
    {
        return _trafficUpdate != null;
    }
    
    public TrafficUpdate getTrafficUpdate()
    {
        return _trafficUpdate;
    }
    
    public boolean hasSpeed()
    {
        return _speed != null;
    }
    
    public Speed getSpeed()
    {
        return _speed;
    }
}
