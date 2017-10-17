/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Niels
 */
public class BusVehicleCount extends LightVehicleCount implements Cloneable
{
    public BusVehicleCount(BusLight l)
    {
        super(l);
        
        for(Direction direction : Direction.values())
        {
            _priorities.put(direction, 0);
        }
    }
    
    private final Map<Direction,Integer> _priorities = new HashMap<>();
    
    public Map<Direction,Integer> getPriorities()
    {
        return _priorities;
    }
    
    @Override
    public void calculatePriorty(long currentTime)
    {
        _priority = (long)0;
        
        for(Direction direction : Direction.values())
        {
            int value = CountDirections(direction);
            _priorities.put(direction, value);
            _priority += value;
        }
        
    }
    
    private int CountDirections(Direction direction)
    {
        if(_directionRequests != null)
        {
            return (int)_directionRequests.stream().filter((d) -> (direction.equals(d))).count() * 150;
        }
        return 0;
    }
    
    @Override
    public LightVehicleCount clone()
    {
        BusVehicleCount count = new BusVehicleCount((BusLight)_light);
        count._count = _count;
        count._directionRequests = _directionRequests;
        return  count;
    }
}
