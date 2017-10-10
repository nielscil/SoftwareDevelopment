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
    
    public boolean isRed()
    {
        return this == Red;
    }
    
    public boolean isOrange()
    {
        return this == Orange;
    }
}
