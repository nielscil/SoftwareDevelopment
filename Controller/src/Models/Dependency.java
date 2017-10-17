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
public class Dependency
{
    public final Light Light;
    public final Direction Direction;
    
    public Dependency(Light light, Direction direction)
    {
        Light = light;
        Direction = direction;
    }
}
