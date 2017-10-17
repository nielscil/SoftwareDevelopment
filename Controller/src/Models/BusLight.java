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
    private final transient List<Dependency> _dependenciesRight = new ArrayList<>();
    private final transient List<Dependency> _dependenciesStraight = new ArrayList<>();
    private final transient List<Dependency> _dependenciesLeft = new ArrayList<>();
    
    
    public BusLight(int id)
    {
        super(id);
    }
    
    public void addDependency(Light light, Direction direction)
    {
        Dependency dependency = new Dependency(light, null);
        if(direction == Direction.Right && !_dependenciesRight.contains(dependency))
        {
            _dependenciesRight.add(dependency);
        }
        
        if(direction == Direction.StraightAhead && !_dependenciesStraight.contains(dependency))
        {
            _dependenciesStraight.add(dependency);
        }
        
        if(!_dependencies.contains(dependency))
        {
            _dependencies.add(dependency);
        }
    }
    
    @Override
    public void addDependency(Light light)
    {
        Dependency dependency = new Dependency(light, null);
        if(!_dependenciesRight.contains(dependency))
        {
             _dependenciesRight.add(dependency);
        }
        
        if(!_dependenciesStraight.contains(dependency))
        {
            _dependenciesStraight.add(dependency);
        }
        
        if(!_dependencies.contains(dependency))
        {
            _dependencies.add(dependency);
        }
    }    
}
