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
public class SerializeableLight
{
    public LightNumber Id;
    public State Status = State.Red;
    public int Time = -1;
    
    public SerializeableLight(LightNumber id, State state)
    {
        Id = id;
        Status = state;
    }
}
