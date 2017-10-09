/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.EnumSet;

/**
 *
 * @author Niels
 */
public class TrafficUpdate
{
    public int LightId;
    public int Count;
    public EnumSet<Direction> DirectionRequests;
}
