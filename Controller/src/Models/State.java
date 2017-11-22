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
    GreenLeftStraight,
    GreenRightStraight,
    GreenLeftRight,
    GreenAll;
    
    public boolean isGreen()
    {
        return this == Green || this == GreenAll || this == GreenLeft || this == GreenRight || this == GreenLeftRight || this == GreenLeftStraight || this == GreenRightStraight;
    }
    
    public boolean isGreen(Direction direction)
    {
        switch(direction)
        {
            case Left:
                return this == GreenLeft || this == GreenAll || this == GreenLeftRight || this == GreenLeftStraight;
            case Right:
                return this == GreenRight || this == GreenAll || this == GreenLeftRight || this == GreenRightStraight;
            case StraightAhead:
                return this == Green || this == GreenAll || this == GreenLeftStraight || this == GreenRightStraight;
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
    
    public static State getGreenStateByDirection(Direction direction)
    {
        switch(direction)
        {
            case Left:
                return GreenLeft;
            case Right:
                return GreenRight;
            case StraightAhead:
                return Green;
        }
        return null;
    }
}
