using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum TrafficType { cars = 0, busses, pedestrians, cyclists}
public enum Direction { straight = 2, left, right}

public class Lane : MonoBehaviour
{
    public int id;
    public Direction laneDirection;
    public TrafficType trafficType;
    public Transform[] waypoints;

    private TrafficLight _TrafficLight { get; set; }
    private TrafficPool _TrafficPool { get; set; }

    private void Start()
    {
        _TrafficLight = gameObject.GetComponentInParent<TrafficLight>();
        _TrafficPool = gameObject.GetComponentInParent<TrafficPool>();
    }

    private bool Continue()
    {
        int state = _TrafficLight.GetState();

        if (trafficType == TrafficType.busses)
        {
            switch (laneDirection)
            {
                case Direction.straight:
                    return (state == 2 || (state >= 5 && state <= 8));
                case Direction.left:
                    return (state == 3 || state == 5 || (state >= 7 && state <= 8));
                case Direction.right:
                    return (state == 4 || (state >= 6 && state <= 8));
            }
        }
        else
        {
            return state == 2;
        }

        return false;
    }

    private void AddToQue()
    {
        if (trafficType != TrafficType.busses)
        {
            _TrafficLight.AddToQue();
        }
        else
        {
            _TrafficLight.AddToQue((int)laneDirection);
        }
    }

    private void RemoveFromQue()
    {
        if (trafficType != TrafficType.busses)
        {
            _TrafficLight.RemoveFromQue();
        }
        else
        {
            _TrafficLight.RemoveFromQue((int)laneDirection);
        }
    }    

    public void SpawnTraffic()
    {
        GameObject trafficObject = null;

        switch (trafficType)
        {
            case TrafficType.cars:
                trafficObject = _TrafficPool.GetCar();
                break;
            case TrafficType.busses:
                trafficObject = _TrafficPool.GetBus();
                break;
            case TrafficType.pedestrians:
                trafficObject = _TrafficPool.GetPedestrian();
                break;
            case TrafficType.cyclists:
                trafficObject = _TrafficPool.GetCyclist();
                break;
        }
        if(trafficObject != null)
        {
            trafficObject.GetComponent<TrafficObject>().InitTrafficObject(this);
        }
    }
    
    public Transform GetWaypoint(ITrafficbject trafficObject)
    {
        Transform waypoint = waypoints[trafficObject.GetWaypointCount()];

        if (trafficObject.GetWaypointCount() == 2)
        {
            if (Continue())
            {
                if(trafficObject.IsInQue())
                {
                    RemoveFromQue();
                    trafficObject.UpdateInQue(false);
                }

                return waypoint;
            }
            else
            {
                if(!trafficObject.IsInQue())
                {
                    AddToQue();
                    trafficObject.UpdateInQue(true);
                }

                return null;
            }
        }
        else if (trafficObject.GetWaypointCount() == 1)
        {
            AddToQue();
            trafficObject.UpdateInQue(true);
            return waypoint;
        }
        else
        {
            return waypoint;
        }
    }
}
