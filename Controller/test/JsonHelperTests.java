/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Models.Direction;
import Models.LightNumber;
import Models.ObserverArgs;
import Models.Speed;
import Models.State;
import Models.TrafficUpdate;
import controller.Helpers.JsonHelper;
import controller.Helpers.StringHelper;
import controller.Intersection;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niels
 */
public class JsonHelperTests
{
    
    
    public JsonHelperTests()
    {
    }
    
    //Region TrafficUpdate

    @Test
    public void DeserializeThreeCarsByTrafficLight104()
    {
        ObserverArgs args = JsonHelper.instance().Parse(CommunicationData.ThreeCarsByLight104);
        //Check if observerarguments are not null
        assertNotNull(args);
        
        //Check if the TrafficUpdate object is not null
        assertTrue(args.hasTrafficUpdate());
        assertNotNull(args.getTrafficUpdate());
        
        //Check if the Speed object is null
        
        assertFalse(args.hasSpeed());
        assertNull(args.getSpeed());
        
        //get TrafficUpdate object
        TrafficUpdate trafficUpdate = args.getTrafficUpdate();
        
        //check if data of TrafficUpdate object is correct
        assertEquals(104, trafficUpdate.LightId);
        assertEquals(3, trafficUpdate.Count);
        assertNull(trafficUpdate.DirectionRequests);
    }
    
    @Test
    public void DeserializeOneBusByBusLightGoingRight()
    {
        ObserverArgs args = JsonHelper.instance().Parse(CommunicationData.OneBusByBusLightGoingRight);
        //Check if observerarguments are not null
        assertNotNull(args);
        
        //Check if the TrafficUpdate object is not null
        assertTrue(args.hasTrafficUpdate());
        assertNotNull(args.getTrafficUpdate());
        
        //Check if the Speed object is null
        
        assertFalse(args.hasSpeed());
        assertNull(args.getSpeed());
        
        //get TrafficUpdate object
        TrafficUpdate trafficUpdate = args.getTrafficUpdate();
        
        //check if data of TrafficUpdate object is correct
        assertEquals(201, trafficUpdate.LightId);
        assertEquals(1, trafficUpdate.Count);
        
        //Check if directions is not null and check if only the direction 'Right' is in the enum set
        assertNotNull(trafficUpdate.DirectionRequests);
        assertTrue(trafficUpdate.DirectionRequests.contains(Direction.Right));
        assertFalse(trafficUpdate.DirectionRequests.contains(Direction.Left));
        assertFalse(trafficUpdate.DirectionRequests.contains(Direction.StraightAhead));
    }
    
    @Test
    public void ThreeBussesByBusLightGoingRightRightStraight()
    {
        ObserverArgs args = JsonHelper.instance().Parse(CommunicationData.ThreeBussesByBusLightGoingRightRightStraight);
        //Check if observerarguments are not null
        assertNotNull(args);
        
        //Check if the TrafficUpdate object is not null
        assertTrue(args.hasTrafficUpdate());
        assertNotNull(args.getTrafficUpdate());
        
        //Check if the Speed object is null
        
        assertFalse(args.hasSpeed());
        assertNull(args.getSpeed());
        
        //get TrafficUpdate object
        TrafficUpdate trafficUpdate = args.getTrafficUpdate();
        
        //check if data of TrafficUpdate object is correct
        assertEquals(201, trafficUpdate.LightId);
        assertEquals(3, trafficUpdate.Count);
        
        //Check if directions is not null and check if only the direction 'Right' and 'StraightAhead' are in the enum set
        assertNotNull(trafficUpdate.DirectionRequests);
        assertTrue(trafficUpdate.DirectionRequests.contains(Direction.Right));
        assertTrue(trafficUpdate.DirectionRequests.contains(Direction.StraightAhead));
        assertFalse(trafficUpdate.DirectionRequests.contains(Direction.Left));
    }
    
    @Test
    public void NoBussesByBusLight()
    {
        ObserverArgs args = JsonHelper.instance().Parse(CommunicationData.NoBussesByBusLight);
        //Check if observerarguments are not null
        assertNotNull(args);
        
        //Check if the TrafficUpdate object is not null
        assertTrue(args.hasTrafficUpdate());
        assertNotNull(args.getTrafficUpdate());
        
        //Check if the Speed object is null
        
        assertFalse(args.hasSpeed());
        assertNull(args.getSpeed());
        
        //get TrafficUpdate object
        TrafficUpdate trafficUpdate = args.getTrafficUpdate();
        
        //check if data of TrafficUpdate object is correct
        assertEquals(201, trafficUpdate.LightId);
        assertEquals(0, trafficUpdate.Count);
        
        //Check if directions is null
        assertNull(trafficUpdate.DirectionRequests);
    }
    
    @Test
    public void TrainIncoming()
    {
        ObserverArgs args = JsonHelper.instance().Parse(CommunicationData.TrainIncoming);
        //Check if observerarguments are not null
        assertNotNull(args);
        
        //Check if the TrafficUpdate object is not null
        assertTrue(args.hasTrafficUpdate());
        assertNotNull(args.getTrafficUpdate());
        
        //Check if the Speed object is null
        
        assertFalse(args.hasSpeed());
        assertNull(args.getSpeed());
        
        //get TrafficUpdate object
        TrafficUpdate trafficUpdate = args.getTrafficUpdate();
        
        //check if data of TrafficUpdate object is correct
        assertEquals(501, trafficUpdate.LightId);
        assertEquals(1, trafficUpdate.Count);
        
        //Check if directions is null
        assertNull(trafficUpdate.DirectionRequests);
    }
    
    //EndRegion
    //Region Speed
    @Test
    public void SpeedRealTime()
    {
        ObserverArgs args = JsonHelper.instance().Parse(CommunicationData.SpeedRealTime);
        //Check if observerarguments are not null
        assertNotNull(args);
        
        //Check if the TrafficUpdate object is null
        assertFalse(args.hasTrafficUpdate());
        assertNull(args.getTrafficUpdate());
        
        //Check if the Speed object is not null
        
        assertTrue(args.hasSpeed());
        assertNotNull(args.getSpeed());
        
        //get TrafficUpdate object
        Speed speed = args.getSpeed();
        
        //check if data of TrafficUpdate object is correct
        assertEquals(1.0f, speed.Speed, 0.005f);
    }
    
    @Test
    public void SpeedPause()
    {
        ObserverArgs args = JsonHelper.instance().Parse(CommunicationData.SpeedPause);
        //Check if observerarguments are not null
        assertNotNull(args);
        
        //Check if the TrafficUpdate object is null
        assertFalse(args.hasTrafficUpdate());
        assertNull(args.getTrafficUpdate());
        
        //Check if the Speed object is not null
        
        assertTrue(args.hasSpeed());
        assertNotNull(args.getSpeed());
        
        //get TrafficUpdate object
        Speed speed = args.getSpeed();
        
        //check if data of TrafficUpdate object is correct
        assertEquals(0.0f, speed.Speed, 0.005f);
    }
    
    @Test
    public void SpeedMax()
    {
        ObserverArgs args = JsonHelper.instance().Parse(CommunicationData.SpeedMax);
        //Check if observerarguments are not null
        assertNotNull(args);
        
        //Check if the TrafficUpdate object is null
        assertFalse(args.hasTrafficUpdate());
        assertNull(args.getTrafficUpdate());
        
        //Check if the Speed object is not null
        
        assertTrue(args.hasSpeed());
        assertNotNull(args.getSpeed());
        
        //get TrafficUpdate object
        Speed speed = args.getSpeed();
        
        //check if data of TrafficUpdate object is correct
        assertEquals(100.0f, speed.Speed, 0.005f);
    }
    
    //EndRegion
    
    @Test
    public void Lights() throws Exception
    {
        //Create intersection
        Intersection intersection = new Intersection();
        
        //set de right lights
        intersection.getLight(LightNumber.NorthRight_101).setStatus(State.Green);
        intersection.getLight(LightNumber.NorthStraight_102).setStatus(State.Orange);
        intersection.getLight(LightNumber.EastBus_201).setStatus(State.GreenRight);
        intersection.getLight(LightNumber.SouthRailRoadCrossing_601).setStatus(State.Green);
        
        String json = JsonHelper.instance().Serialize(intersection.GetSerializable());
        
        //check if string is not empty or null
        assertFalse(StringHelper.IsNullOrWhitespace(json));
        
        assertEquals(CommunicationData.Lights, json);
    }
    
}
