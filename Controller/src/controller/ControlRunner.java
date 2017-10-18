/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Models.BlockingDependenciesChecker;
import Models.BusLight;
import Models.BusVehicleCount;
import Models.CrosswayLight;
import Models.CrosswayLightCount;
import Models.Dependency;
import Models.Light;
import Models.LightNumber;
import Models.LightVehicleCount;
import Models.State;
import Models.TrainLight;
import Models.TrainVehicleCount;
import Models.VehicleCount;
import controller.Helpers.ClassHelper;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Niels
 */
public class ControlRunner implements Runnable
{     
    private final int OrangeLightTime = 2;
    
    private static ControlRunner _instance;
    
    private final Intersection _intersection;
    private final Controller _controller;
    private float _speed = 1.0f;
    private int _beginTimePreviousRun;
    private volatile boolean _needsStop;
    
    private ControlRunner(Controller controller)
    {
        _controller = controller;
        _beginTimePreviousRun = LocalTime.now().toSecondOfDay();
        _intersection = new Intersection();
        _intersection.addObserver(controller.getConnectionProvider());
    }

    @Override
    public void run()
    {
        while(!_needsStop)
        {
            try
            {
                checkSpeed();
                calculateNewTime();
                long currentTime = getTime();
                
                setMappedVehicleCount(_controller.getLights());
            
                List<LightVehicleCount> sortedList = new ArrayList<>(_mappedVehicleCount.values());
                sortedList.stream().forEach((LightVehicleCount l) -> 
                { 
                    l.calculatePriorty(currentTime);
                });
                sortedList.sort((a,b)-> a.compareTo(b));
        
                //set orange lights to red when orange period is past.
                for(Light l : _intersection.getLights().stream().filter((l) -> l.Status.isOrange()).collect(Collectors.toList()))
                {
                    if(currentTime - l.getStatusChangedTime() >= OrangeLightTime) //<- this is the orange period time must be 2 or 3 seconds
                    {
                        l.setStatus(State.Red);
                    }
                }       

                CheckTrain();

                for(LightVehicleCount vehicleCount : sortedList)
                {
                    
                    if(vehicleCount instanceof BusVehicleCount)
                    {
                        if(vehicleCount.getPriorty() > 0)
                        {
                            trySetBusLightToGreen((BusVehicleCount)vehicleCount);
                        }
                    }
                    else if(vehicleCount instanceof TrainVehicleCount)
                    {
                        if(vehicleCount.getPriorty() > 0)
                        {
                            trySetLightToGreen(vehicleCount.getLight());
                        }
                    }
                    else if(vehicleCount instanceof CrosswayLightCount)
                    {
                        if(vehicleCount.getPriorty() > 0)
                        {
                            trySetLightToGreen(vehicleCount.getLight());
                        }
                    }
                    else
                    {
                        trySetLightToGreen(vehicleCount.getLight());
                    }
                }
                _intersection.saveChanges();
            }
            catch(Exception e)
            {
                System.err.println(e.toString());
            }    
        }       
    }
    
    private void trySetLightToGreen(Light light)
    {
        if(!light.Status.isGreen())
        {
            if(light.canSetStatus(State.Green))
            {
                light.setStatus(State.Green);
            }
            else
            {
                new BlockingDependenciesChecker(light).run();
            }
            
        }
    }
    
    private void trySetBusLightToGreen(BusVehicleCount busVehicleCount)
    {
        long totalPriority = busVehicleCount.getPriorty();

        if(!busVehicleCount.getLight().Status.isGreen())
        {
            State state = State.GreenRightStraight;
            
            if(busVehicleCount.getPriorities().values().stream().filter((l) -> l == totalPriority).count() == 1)
            {
                state = State.getGreenStateByDirection(busVehicleCount.getHighestDirection());
            }
            
            if(busVehicleCount.getLight().canSetStatus(state))
            {
                busVehicleCount.getLight().setStatus(state);
            }
            else
            {
                new BlockingDependenciesChecker((BusLight)busVehicleCount.getLight(), state).run();
            }
          
        }
    }
    
    private void CheckTrain()
    {
        LightVehicleCount vehicleCount = _mappedVehicleCount.get(LightNumber.SouthTrainSignal_501);
        if(vehicleCount.getPriorty() > 0 && !vehicleCount.getLight().Status.isGreen())
        {
            if(vehicleCount.getLight().canSetStatus(State.Green))
            {
                vehicleCount.getLight().setStatus(State.Green);
            }
        }
        else if(!vehicleCount.getLight().Status.isRed())
        {
            if(vehicleCount.getLight().canSetStatus(State.Red))
            {
                vehicleCount.getLight().setStatus(State.Red);
            }
        }
    }
    
    public void stop()
    {
        _needsStop = true;
    }
    
    public Map<LightNumber,VehicleCount> GetTrafficUpdateMap()
    {
        Map<LightNumber,VehicleCount> map = new HashMap<>();
        _intersection.getLights().stream().forEach((l) ->
        {
            TrainLight trainLight = ClassHelper.safeCast(l, TrainLight.class);
            BusLight busLight = ClassHelper.safeCast(l, BusLight.class);
            CrosswayLight crosswayLight = ClassHelper.safeCast(l, CrosswayLight.class);
            
            if (trainLight != null)
            {
                map.put(l.Id, new TrainVehicleCount(trainLight));
            }
            else if(busLight != null)
            {
                map.put(l.Id, new BusVehicleCount(busLight));
            }
            else if(crosswayLight != null)
            {
                map.put(l.Id, new CrosswayLightCount(crosswayLight));
            }
            else
            {
                map.put(l.Id, new LightVehicleCount(l));
            }
        });
        return map;
    }
    
    private void checkSpeed()
    {
        _speed = _controller.checkSpeed(_speed);
    }
    
    private static Map<LightNumber,LightVehicleCount> _mappedVehicleCount;
    private void setMappedVehicleCount(Map<LightNumber,LightVehicleCount> mappedVehicleCount)
    {
        synchronized(_syncLock)
        {
            _mappedVehicleCount = mappedVehicleCount;
        }
    }
    
    public static LightVehicleCount getVehicleCount(LightNumber number)
    {
        synchronized(_syncLock)
        {
            return _mappedVehicleCount.get(number);
        }
    }
    
    private void calculateNewTime()
    {
        synchronized(_syncLock)
        {
            int newTime = LocalTime.now().toSecondOfDay();
            int diff = newTime - _beginTimePreviousRun;
            _beginTimePreviousRun = newTime;
            _currentTime += (int)((float)diff * _speed);
        }
    }
    
    private int _currentTime = 0;
    private static final Object _syncLock = new Object();
    public static int getTime()
    {
        synchronized(_syncLock)
        {
            return  _instance._currentTime;
        }
    }    
    
    public static ControlRunner create(Controller controller)
    {
        if(_instance == null)
        {
            _instance = new ControlRunner(controller); 
        }
        return _instance;
    }
    
    public static void close()
    {
        if(_instance != null)
        {
            if(!_instance._needsStop)
            {
                _instance.stop();
            }
            _instance = null;
        }
    }
    
}
