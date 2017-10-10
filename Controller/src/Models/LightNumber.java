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
public enum LightNumber
{
    //North side intersection

    NorthRight_101(101),
    NorthStraight_102(102),
    NorthLeft_103(103),
    
    BicycleNorthSouth_301(301),
    WalkNorthSouthWestSide_401(401),
    WalkNorthSouthEastSide_404(404),
    
    //East side intersection
    EastRight_104(104),
    EastStraight_105(105),
    
    EastBus_201(201),
    
    BicycleEastWest_303(303),
    
    WalkEastWest_403(403),
    
    //South side intersection
    SouthStraightRight_106(106),
    SouthLeft_107(107),
    
    SouthTrainSignal_501(501),
    SouthRailRoadCrossing_601(601),
    
    BicycleSouthNorthEastSide_304(304),
    BicycleSouthNorthWestSide_305(305),
    
    WalkSouthWestSide_406(406),
    WalkSouthNorthEastSide_405(405),
    
    //West side intersection
    WestRight_108(108),
    WestStraight_109(109),
    WestLeft_110(110),
    
    BicycleWestEast_302(302),
    WalkWestEast_402(402);
    
    private final int _value;

    private LightNumber(int value)
    {
        _value = value;
    }
    
    public int getValue()
    {
        return _value;
    }
    
    public static LightNumber get(int i)
    {
        for(LightNumber var : LightNumber.values())
        {
            if(var._value == i)
            {
                return var;
            }
        }
        return null;
    }
}
