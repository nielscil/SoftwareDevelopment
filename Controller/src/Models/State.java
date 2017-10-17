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
public enum State
{
    Red,
    Orange,
    Green,
    GreenLeft,
    GreenRight,
    GreenAll;
    
    public boolean isGreen()
    {
        return this == Green || this == GreenAll || this == GreenLeft || this == GreenRight;
    }
    
    public boolean isGreen(Direction direction)
    {
        switch(direction)
        {
            case Left:
                return this == GreenLeft || this == GreenAll;
            case Right:
                return this == GreenRight || this == GreenAll;
            case StraightAhead:
                return this == Green || this == GreenAll;
            default:
                return false;
        }
    }
    
    public boolean isRed()
    {
        return this == Red;
    }
    
    public boolean isOrange()
    {
        return this == Orange;
    }
}
