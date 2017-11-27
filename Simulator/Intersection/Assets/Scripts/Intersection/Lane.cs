using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum TrafficType { cars = 0, busses, pedestrians, cyclists}
public enum Direction { straight = 2, left, right}

public class Lane : MonoBehaviour
{
    public Direction direction;
    public TrafficType trafficType;
    private TrafficPool _TrafficPool { get; set; }
    public Transform[] waypoints;

    private void Start()
    {
        _TrafficPool = gameObject.GetComponentInParent<TrafficPool>();
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
    
    public Transform GetWaypoint(ITrafficObject trafficObject)
    {
        if(trafficObject.GetWaypointCount() == waypoints.Length)
        {
            return null;
        }
        else
        {
            return waypoints[trafficObject.GetWaypointCount()];
        }
    }
}
