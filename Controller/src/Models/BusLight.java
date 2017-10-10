/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Niels
 */
public class BusLight extends Light
{
    private final transient List<Light> _dependenciesRight = new ArrayList<>();
    private final transient List<Light> _dependenciesStraight = new ArrayList<>();
    
    public BusLight(int id)
    {
        super(id);
    }
    
    @Override
    public void addDependency(Light light, Direction direction)
    {
        if(direction == Direction.Right && !_dependenciesRight.contains(light))
        {
            _dependenciesRight.add(light);
        }
        
        if(direction == Direction.StraightAhead && !_dependenciesStraight.contains(light))
        {
            _dependenciesStraight.add(light);
        }
        
        Tuple<Light,Direction> tuple = new Tuple<>(light, null);
        if(!_dependencies.contains(tuple))
        {
            _dependencies.add(tuple);
        }
    }
    
    @Override
    public void addDependency(Light light)
    {
        if(!_dependenciesRight.contains(light))
        {
             _dependenciesRight.add(light);
        }
        
        if(!_dependenciesStraight.contains(light))
        {
            _dependenciesStraight.add(light);
        }
        
        Tuple<Light,Direction> tuple = new Tuple<>(light, null);
        if(!_dependencies.contains(tuple))
        {
            _dependencies.add(tuple);
        }
    }
    
}
