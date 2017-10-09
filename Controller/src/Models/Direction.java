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
public enum Direction
{
    StraightAhead(2),
    Left(3),
    Right(4);
    
    private final int _value;

    private Direction(int value)
    {
        _value = value;
    }
    
    public int getValue()
    {
        return _value;
    }
    
    public static Direction get(int i)
    {
        for(Direction var : Direction.values())
        {
            if(var._value == i)
            {
                return var;
            }
        }
        return null;
    }
}
