/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Helpers;

import Models.BusLight;
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
        
        setNorthBicylceDependencies(l);
        setNorthWalkLeftDependencies(l);
        setWestBicylceDependencies(l);
        setWestWalkUpDependecies(l);
        setBusDepenency(l, Direction.StraightAhead);
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        l.addDependency(getLight(LightNumber.EastStraight_105));
    }
    private void SetNorthStraight102()
    {
        Light l = getLight(LightNumber.NorthStraight_102);
        
        setNorthBicylceDependencies(l);
        setNorthWalkLeftDependencies(l);
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
        
        setNorthBicylceDependencies(l);
        setNorthWalkLeftDependencies(l);
        setEastBicylceDependencies(l);
        setEastWalkDownDependencies(l);
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
        
        setEastBicylceDependencies(l);
        setEastWalkUpDependencies(l);
        setNorthBicylceDependencies(l);
        setNorthWalkRightDependencies(l);
        setBusDepenency(l, Direction.StraightAhead);
        setBusDepenency(l, Direction.Right);
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        l.addDependency(getLight(LightNumber.WestLeft_110));
    }
    
    private void setEastStraight105()
    {
        Light l = getLight(LightNumber.EastStraight_105);
        
        setEastBicylceDependencies(l);
        setEastWalkUpDependencies(l);
        setWestBicylceDependencies(l);
        setWestWalkUpDependecies(l);
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
        
        setEastBicylceDependencies(l);
        setEastWalkDownDependencies(l);
        setNorthBicylceDependencies(l);
        setNorthWalkRightDependencies(l);
        
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
        
        setWestBicylceDependencies(l);
        setWestWalkUpDependecies(l);
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
        
        setWestBicylceDependencies(l);
        setWestWalkDownDependecies(l);
        setTrainDependency(l);
        
        l.addDependency(getLight(LightNumber.NorthStraight_102));
    }
    
    private void setWestStraight109()
    {
        Light l = getLight(LightNumber.WestStraight_109);
        
        setWestBicylceDependencies(l);
        setWestWalkDownDependecies(l);
        setEastBicylceDependencies(l);
        setEastWalkDownDependencies(l);
        
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        l.addDependency(getLight(LightNumber.NorthLeft_103));
        
        l.addDependency(getLight(LightNumber.SouthLeft_107));
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));     
    }
    
    private void setWestLeft110()
    {
        Light l = getLight(LightNumber.WestLeft_110);
        
        setWestBicylceDependencies(l);
        setWestWalkDownDependecies(l);
        setNorthBicylceDependencies(l);
        setNorthWalkRightDependencies(l);
        
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
        SetSouthTrainSignal_502();
        SetSouthRailRoadCrossing_601();
    }
    
    private void setEastBus201()
    {
        BusLight l = (BusLight)getLight(LightNumber.EastBus_201);
        
        //set for all dependencies
        setEastBicylceDependencies(l);
        setEastWalkUpDependencies(l);
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        l.addDependency(getLight(LightNumber.WestLeft_110));
        l.addDependency(getLight(LightNumber.EastRight_104));
        
        //set right dependencies
        l.addDependency(getLight(LightNumber.WalkNorthCenterRight_408), Direction.Right);
        l.addDependency(getLight(LightNumber.WalkEastWest_403), Direction.Right);
        l.addDependency(getLight(LightNumber.BicycleWestEast_302), Direction.Right);
        l.addDependency(getLight(LightNumber.BicycleEastWest_303), Direction.Right);
        
        //set straight dependencies
        
        l.addDependency(getLight(LightNumber.WalkNorthSouthWestSide_401), Direction.StraightAhead);
        l.addDependency(getLight(LightNumber.WalkWestCenterUp_412), Direction.StraightAhead);
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
        setForWalkerNorthLeft(l);
        
        l = getLight(LightNumber.WalkNorthCenterLeft_407);
        setForWalkerNorthLeft(l);
        
        l = getLight(LightNumber.WalkEastWest_403);
        setForWalkerNorthRight(l);
        
        l = getLight(LightNumber.WalkNorthCenterRight_408);
        setForWalkerNorthRight(l);
    }
    
    private void setForWalkerNorthRight(Light l)
    {
        l.addDependency(getLight(LightNumber.SouthStraightRight_106)); 
        l.addDependency(getLight(LightNumber.EastRight_104));        
        l.addDependency(getLight(LightNumber.WestLeft_110));
    }
    
    private void setForWalkerNorthLeft(Light l)
    {
        l.addDependency(getLight(LightNumber.NorthRight_101));
        l.addDependency(getLight(LightNumber.NorthStraight_102));
        l.addDependency(getLight(LightNumber.NorthLeft_103));
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
        setForWalkerUpEast(l);
        
        l = getLight(LightNumber.WalkEastCenterUp_409);
        setForWalkerUpEast(l);
        
        l = getLight(LightNumber.WalkSouthNorthEastSide_405);
        setForWalkerDownEast(l);
        
        l = getLight(LightNumber.WalkEastCenterDown_410);
        setForWalkerDownEast(l);
    }
    
    private void setForWalkerUpEast(Light l)
    {
        setBusDepenency(l, Direction.Right);
        setBusDepenency(l, Direction.StraightAhead);
        
        l.addDependency(getLight(LightNumber.EastRight_104));
        l.addDependency(getLight(LightNumber.EastStraight_105));
    }
    
    private void setForWalkerDownEast(Light l)
    {
        l.addDependency(getLight(LightNumber.WestStraight_109));
        
        l.addDependency(getLight(LightNumber.SouthStraightRight_106));
        
        l.addDependency(getLight(LightNumber.NorthLeft_103));  
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
        Light l = getLight(LightNumber.TrainSignalWest_501);
        
        l.addDependency(getLight(LightNumber.SouthRailRoadCrossing_601));    
        l.addDependency(getLight(LightNumber.TrainSignalEast_502));
    }
    
    private void SetSouthTrainSignal_502()
    {
        Light l = getLight(LightNumber.TrainSignalEast_502);
        
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
    
    private void setNorthWalkLeftDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkWestEast_402));
        l.addDependency(getLight(LightNumber.WalkNorthCenterLeft_407));
    }
    
    private void setNorthWalkRightDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkEastWest_403));
        l.addDependency(getLight(LightNumber.WalkNorthCenterRight_408));
    }
    
    private void setNorthBicylceDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.BicycleWestEast_302));
        l.addDependency(getLight(LightNumber.BicycleEastWest_303));
    }
    
    private void setEastWalkUpDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkNorthSouthEastSide_404));
        l.addDependency(getLight(LightNumber.WalkEastCenterUp_409));
    }
    
    private void setEastWalkDownDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkSouthNorthEastSide_405));
        l.addDependency(getLight(LightNumber.WalkEastCenterDown_410));
    }
    
    private void setEastBicylceDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.BicycleSouthNorthEastSide_304));
    }
    
    private void setWestWalkUpDependecies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkNorthSouthWestSide_401));
        l.addDependency(getLight(LightNumber.WalkWestCenterUp_412));
    }
    
    private void setWestWalkDownDependecies(Light l)
    {
        l.addDependency(getLight(LightNumber.WalkSouthNorthWestSide_406));
        l.addDependency(getLight(LightNumber.WalkWestCenterDown_411));
    }
    
    private void setWestBicylceDependencies(Light l)
    {
        l.addDependency(getLight(LightNumber.BicycleNorthSouth_301));
        l.addDependency(getLight(LightNumber.BicycleSouthNorthWestSide_305));
    }
    
    private void setBusDepenency(Light l, Direction direction)
    {
        l.addDependency(getLight(LightNumber.EastBus_201), direction);
    }
    
    private void setTrainDependency(Light l)
    {
        l.addDependency(getLight(LightNumber.TrainSignalWest_501));
        //l.addDependency(getLight(LightNumber.SouthRailRoadCrossing_601));
    }
        
    private Light getLight(LightNumber lightNumber)
    {
        return _intersection.getLight(lightNumber);
    }
}
