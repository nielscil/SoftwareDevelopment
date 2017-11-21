using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrainTrack : MonoBehaviour
{
    public Transform[] WaypointsStationOut;
    public Transform[] WaypointsStationIn;
    public Dictionary<int, Transform[]> waypoints;

    private GameObject trainIn;
    private GameObject trainOut;

    private TrafficLight _TrafficLight { get; set; }
    private TrafficPool _TrafficPool { get; set; }

    private void Start()
    {
        _TrafficLight = gameObject.GetComponentInParent<TrafficLight>();
        _TrafficPool = gameObject.GetComponentInParent<TrafficPool>();
        waypoints = new Dictionary<int, Transform[]>(2)
        {
            { 0, WaypointsStationIn },
            { 1, WaypointsStationOut }
        };
    }

    public void SpawnTraffic()
    {
        if (trainIn != null && !trainIn.activeSelf)
        {
            trainIn = null;
        }
        if (trainOut != null && !trainOut.activeSelf)
        {
            trainOut = null;
        }

        if(trainIn == null)
        {
            if(trainOut == null)
            {
                _TrafficLight.UpdateState(0, -1);
                trainIn = _TrafficPool.GetTrain();
                StartCoroutine(TrainSpawned(trainIn, 0));
            }
        }
        else
        {
            if (trainOut == null)
            {
                trainOut = _TrafficPool.GetTrain();
                StartCoroutine(TrainSpawned(trainOut, 1));
            }
        }
    }

    private void AddToQue()
    {
        _TrafficLight.AddToQue();
    }

    private void RemoveFromQue()
    {
        _TrafficLight.RemoveFromQue();
    }

    private bool Continue()
    {
        int state = _TrafficLight.GetState();

        return state == 2;
    }

    public Transform GetWaypoint(TrainObject trainObject)
    {
        Transform waypoint = waypoints[trainObject.GetDirection()][trainObject.GetWaypointCount()];

        if (trainObject.GetWaypointCount() == 2 && trainObject.GetDirection() == 1)
        {
            if (Continue())
            {
                return waypoint;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return waypoint;
        }
    }

    IEnumerator TrainSpawned(GameObject train, int direction)
    {
        AddToQue();
        train.GetComponent<TrainObject>().InitTrain(this, direction);
        yield return new WaitForSeconds(7);
        // spoorbomen omlaag
        // trein is gepasseerd
        yield return new WaitForSeconds(7);
        RemoveFromQue();
        // spoorbomen omhoog
        // lichten uit
        _TrafficLight.UpdateState(2, -1);
    }

}
