/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Niels
 */
public class CommunicationData
{
    //TrafficUpdate
    public static final String ThreeCarsByLight104 = "{ \"TrafficUpdate\": { \"LightId\": 104, \"Count\": 3, \"DirectionRequests\": null } }";
    public static final String OneBusByBusLightGoingRight = "{ \"TrafficUpdate\": { \"LightId\": 201, \"Count\": 1, \"DirectionRequests\": [ 4 ] } }";
    public static final String ThreeBussesByBusLightGoingRightRightStraight = "{ \"TrafficUpdate\": { \"LightId\": 201, \"Count\": 3, \"DirectionRequests\": [ 4, 4, 2 ] } }";
    public static final String NoBussesByBusLight = "{ \"TrafficUpdate\": { \"LightId\": 201, \"Count\": 0, \"DirectionRequests\": null } }";
    public static final String TrainIncoming = "{ \"TrafficUpdate\": { \"LightId\": 501, \"Count\": 1, \"DirectionRequests\": null } }";
    
    //Speed
    public static final String SpeedRealTime = "{ \"Speed\": 1.0 }";
    public static final String SpeedPause = "{ \"Speed\": 0.0 }";    
    public static final String SpeedMax = "{ \"Speed\": 100.0 }";
    
    //Lights
    public static final String Lights = "{\"Lights\":[{\"Id\":101,\"Status\":2},{\"Id\":102,\"Status\":1},{\"Id\":103,\"Status\":0},{\"Id\":104,\"Status\":0},{\"Id\":105,\"Status\":0},{\"Id\":106,\"Status\":0},{\"Id\":107,\"Status\":0},{\"Id\":108,\"Status\":0},{\"Id\":109,\"Status\":0},{\"Id\":110,\"Status\":0},{\"Id\":201,\"Status\":4},{\"Id\":301,\"Status\":0},{\"Id\":302,\"Status\":0},{\"Id\":303,\"Status\":0},{\"Id\":304,\"Status\":0},{\"Id\":305,\"Status\":0},{\"Id\":401,\"Status\":0},{\"Id\":402,\"Status\":0},{\"Id\":403,\"Status\":0},{\"Id\":404,\"Status\":0},{\"Id\":405,\"Status\":0},{\"Id\":406,\"Status\":0},{\"Id\":601,\"Status\":2}]}";
}

