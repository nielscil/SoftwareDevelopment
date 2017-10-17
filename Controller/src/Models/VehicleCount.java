/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;

/**
 *
 * @author Niels
 */
public class VehicleCount
{
    protected int _count = 0;
    protected List<Direction> _directionRequests = null;
    
    public void setUpdate(TrafficUpdate update)
    {
        _count = update.Count;
        _directionRequests = update.DirectionRequests;
    }
}
