/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Helpers;

import Models.Direction;
import Models.Light;
import Models.LightNumber;
import controller.Intersection;

/**
 *
 * @author Niels
 */
public class DependenciesHelper
{
    private final Intersection _intersection;
    
    private DependenciesHelper(Intersection intersection)
    {
        _intersection = intersection;
    }
    
    public static void populate(Intersection intersection)
    {
        new DependenciesHelper(intersection).populate();
    }
    
    public void populate()
    {
        populateNorth();
        populateEast();
        populateSouth();
        populateWest();
        
        populateOthers();
    }
    
    private void populateNorth()
    {
        setNorthRight101();
        SetNorthStraight102();
        SetNorthLeft103();
    }
    
    private void setNorthRight101()
    {
        Light l = getLight(LightNumber.NorthRight_101);
        
        setNorthWalkAndBicylceDependencies(l);
        setWestWalkAndBicylceDependencies(l);
        setBusDepenency(l, Direction.StraightAhead);
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        l.addDependency(getLight(LightNumber.EastStraight_105));
    }
    private void SetNorthStraight102()
    {
        Light l = getLight(LightNumber.NorthStraight_102);
        
        setNorthWalkAndBicylceDependencies(l);
        setTrainDependency(l);
        setBusDepenency(l, Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.WestRight_108));
        l.addDependency(getLight(LightNumber.WestStraight_109));
        l.addDependency(getLight(LightNumber.WestLeft_110));
        
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        l.addDependency(getLight(LightNumber.EastStraight_105));
    }
    
    private void SetNorthLeft103()
    {
        Light l = getLight(LightNumber.NorthLeft_103);
        
        setNorthWalkAndBicylceDependencies(l);
        setEastWalkAndBicylceDependencies(l);
        setBusDepenency(l, Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.WestStraight_109));
        l.addDependency(getLight(LightNumber.WestLeft_110));
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        l.addDependency(getLight(LightNumber.EastStraight_105));
    }
    
    private void populateEast()
    {
        setEastleft104();
        setEastStraight105();
    }
    
    private void setEastleft104()
    {
        Light l = getLight(LightNumber.EastRight_104);
        
        setEastWalkAndBicylceDependencies(l);
        setNorthWalkAndBicylceDependencies(l);
        setBusDepenency(l, Direction.StraightAhead);
        setBusDepenency(l, Direction.Right);
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        l.addDependency(getLight(LightNumber.WestLeft_110));
    }
    
    private void setEastStraight105()
    {
        Light l = getLight(LightNumber.EastStraight_105);
        
        setEastWalkAndBicylceDependencies(l);
        setWestWalkAndBicylceDependencies(l);
        setBusDepenency(l, Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.NorthRight_101));
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        l.addDependency(getLight(LightNumber.NorthLeft_103));
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        
        l.addDependency(getLight(LightNumber.WestLeft_110));
    }
    
    private void populateSouth()
    {
        setSouthStraightRight106();
        setSouthLeft107();
    }
    
    private void setSouthStraightRight106()
    {
        Light l = getLight(LightNumber.SouthStraightRight_106);
        
        setEastWalkAndBicylceDependencies(l);
        setNorthWalkAndBicylceDependencies(l);
        
        setTrainDependency(l);
        
        setBusDepenency(l, Direction.StraightAhead);
        setBusDepenency(l, Direction.Right);
        
        l.addDependency(getLight(LightNumber.WestLeft_110));
        l.addDependency(getLight(LightNumber.WestStraight_109));
        
        l.addDependency(getLight(LightNumber.NorthLeft_103));
        
        l.addDependency(getLight(LightNumber.EastRight_104));
        l.addDependency(getLight(LightNumber.EastStraight_105));
    }
    
    private void setSouthLeft107()
    {
        Light l = getLight(LightNumber.SouthLeft_107);
        
        setWestWalkAndBicylceDependencies(l);
        setTrainDependency(l);
        setBusDepenency(l, Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.WestLeft_110));
        l.addDependency(getLight(LightNumber.WestStraight_109));
        
        l.addDependency(getLight(LightNumber.NorthRight_101));
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        
        l.addDependency(getLight(LightNumber.EastStraight_105));
    }

    private void populateWest()
    {
        setWestRight108();
        setWestStraight109();
        setWestLeft110();
    }
    
    private void setWestRight108()
    {
        Light l = getLight(LightNumber.WestRight_108);
        
        setWestWalkAndBicylceDependencies(l);
        setTrainDependency(l);
        
        l.addDependency(getLight(LightNumber.NorthStraight_102));
    }
    
    private void setWestStraight109()
    {
        Light l = getLight(LightNumber.WestStraight_109);
        
        setWestWalkAndBicylceDependencies(l);
        setEastWalkAndBicylceDependencies(l);
        
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        l.addDependency(getLight(LightNumber.NorthLeft_103));
        
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));     
    }
    
    private void setWestLeft110()
    {
        Light l = getLight(LightNumber.WestLeft_110);
        
        setWestWalkAndBicylceDependencies(l);
        setNorthWalkAndBicylceDependencies(l);
        setBusDepenency(l, Direction.Right);
        setBusDepenency(l, Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        l.addDependency(getLight(LightNumber.NorthLeft_103));
        
        l.addDependency(getLight(LightNumber.EastRight_104));
        l.addDependency(getLight(LightNumber.EastStraight_105));
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        l.addDependency(getLight(LightNumber.SouthLeft_107)); 
    }
    
    private void populateOthers()
    {
        setEastBus201();
        
        setWestDependencies();
        setNorthDependencies();
        setEastDependencies();
        
        SetSouthTrainSignal_501();
        SetSouthRailRoadCrossing_601();
    }
    
    private void setEastBus201()
    {
        Light l = getLight(LightNumber.EastBus_201);
        
        //set for all dependencies
        setEastWalkAndBicylceDependencies(l);
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        l.addDependency(getLight(LightNumber.WestLeft_110));
        l.addDependency(getLight(LightNumber.EastRight_104));
        
        //set right dependencies
        l.addDependency(getLight(LightNumber.WalkWestEast_402), Direction.Right);
        l.addDependency(getLight(LightNumber.WalkEastWest_403), Direction.Right);
        l.addDependency(getLight(LightNumber.BicycleWestEast_302), Direction.Right);
        l.addDependency(getLight(LightNumber.BicycleEastWest_303), Direction.Right);
        
        //set straight dependencies
        
        l.addDependency(getLight(LightNumber.WalkNorthSouthWestSide_401), Direction.StraightAhead);
        l.addDependency(getLight(LightNumber.WalkSouthNorthWestSide_406), Direction.StraightAhead);
        l.addDependency(getLight(LightNumber.BicycleNorthSouth_301), Direction.StraightAhead);
        l.addDependency(getLight(LightNumber.BicycleSouthNorthWestSide_305), Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.SouthLeft_107), Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.NorthRight_101), Direction.StraightAhead);
        l.addDependency(getLight(LightNumber.NorthStraight_102), Direction.StraightAhead);
        l.addDependency(getLight(LightNumber.NorthLeft_103), Direction.StraightAhead);

        l.addDependency(getLight(LightNumber.EastStraight_105), Direction.StraightAhead);
    }
    
    private void setWestDependencies()
    {
        Light l = getLight(LightNumber.BicycleNorthSouth_301);
        setForBicycleWalkerWest(l);
        
        l = getLight(LightNumber.BicycleSouthNorthWestSide_305);
        setForBicycleWalkerWest(l);
        
        l = getLight(LightNumber.WalkNorthSouthWestSide_401);
        setForBicycleWalkerWest(l);
        
        l = getLight(LightNumber.WalkSouthNorthWestSide_406);
        setForBicycleWalkerWest(l);
    }
    
    private void setForBicycleWalkerWest(Light l)
    {
        setBusDepenency(l, Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.NorthRight_101));
        
        l.addDependency(getLight(LightNumber.EastStraight_105));
        
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        
        l.addDependency(getLight(LightNumber.WestLeft_110));
        l.addDependency(getLight(LightNumber.WestStraight_109));
        l.addDependency(getLight(LightNumber.WestRight_108));
    }
    
    private void setNorthDependencies()
    {
        Light l = getLight(LightNumber.BicycleWestEast_302);
        setForBicycleWalkerNorth(l);
        
        l = getLight(LightNumber.BicycleEastWest_303);
        setForBicycleWalkerNorth(l);
        
        l = getLight(LightNumber.WalkWestEast_402);
        setForBicycleWalkerNorth(l);
        
        l = getLight(LightNumber.WalkEastWest_403);
        setForBicycleWalkerNorth(l);
    }
    
    private void setForBicycleWalkerNorth(Light l)
    {
        setBusDepenency(l, Direction.Right);
        
        l.addDependency(getLight(LightNumber.NorthRight_101));
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        l.addDependency(getLight(LightNumber.NorthLeft_103));
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        
        l.addDependency(getLight(LightNumber.EastRight_104));
        
        l.addDependency(getLight(LightNumber.WestLeft_110));
    }
    
    private void setEastDependencies()
    {
        Light l = getLight(LightNumber.BicycleSouthNorthEastSide_304);
        setForBicycleWalkerEast(l);
        
        l = getLight(LightNumber.WalkNorthSouthEastSide_404);
        setForBicycleWalkerEast(l);
        
        l = getLight(LightNumber.WalkSouthNorthEastSide_405);
        setForBicycleWalkerEast(l);
    }
    
    private void setForBicycleWalkerEast(Light l)
    {
        setBusDepenency(l, Direction.Right);
        setBusDepenency(l, Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.EastRight_104));
        l.addDependency(getLight(LightNumber.EastStraight_105));
        
        l.addDependency(getLight(LightNumber.WestStraight_109));
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        
        l.addDependency(getLight(LightNumber.NorthLeft_103));       
    }
    
    private void SetSouthTrainSignal_501()
    {
        Light l = getLight(LightNumber.SouthTrainSignal_501);
        
        l.addDependency(getLight(LightNumber.WestRight_108));
        
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        l.addDependency(getLight(LightNumber.SouthRailRoadCrossing_601));     
    }
    
    private void SetSouthRailRoadCrossing_601()
    {
        Light l = getLight(LightNumber.SouthRailRoadCrossing_601);
        l.addDependency(getLight(LightNumber.WestRight_108));
        
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));        
    }
    
    
    private void setNorthWalkAndBicylceDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkWestEast_402));
        l.addDependency(getLight(LightNumber.WalkEastWest_403));
        l.addDependency(getLight(LightNumber.BicycleWestEast_302));
        l.addDependency(getLight(LightNumber.BicycleEastWest_303));
    }
    
    private void setEastWalkAndBicylceDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkNorthSouthEastSide_404));
        l.addDependency(getLight(LightNumber.WalkSouthNorthEastSide_405));
        l.addDependency(getLight(LightNumber.BicycleSouthNorthEastSide_304));
    }
    
    private void setWestWalkAndBicylceDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkNorthSouthWestSide_401));
        l.addDependency(getLight(LightNumber.WalkSouthNorthWestSide_406));
        l.addDependency(getLight(LightNumber.BicycleNorthSouth_301));
        l.addDependency(getLight(LightNumber.BicycleSouthNorthWestSide_305));
    }
    
    private void setBusDepenency(Light l, Direction direction)
    {
        l.addDependency(getLight(LightNumber.EastBus_201), direction);
    }
    
    private void setTrainDependency(Light l)
    {
        l.addDependency(getLight(LightNumber.SouthTrainSignal_501));
        l.addDependency(getLight(LightNumber.SouthRailRoadCrossing_601));
    }
        
    private Light getLight(LightNumber lightNumber)
    {
        return _intersection.getLight(lightNumber);
    }
}
