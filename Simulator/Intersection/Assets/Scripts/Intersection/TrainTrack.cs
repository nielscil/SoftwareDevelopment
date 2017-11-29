using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrainTrack : MonoBehaviour
{
    public Transform[] waypoints;
    public TrafficLight trafficlight;
    public float spawnTime;
    
    private TrafficPool _TrafficPool { get; set; }

    private void Start()
    {
        _TrafficPool = gameObject.GetComponentInParent<TrafficPool>();
    }

    public void SpawnTraffic()
    {
        GameObject train = _TrafficPool.GetTrain();
        if(train != null)
        {
            StartCoroutine(TrainSpawned(train, spawnTime));
        }
    }

    public Transform GetWaypoint(ITrafficObject trafficObject)
    {
        if (trafficObject.GetWaypointCount() == waypoints.Length)
        {
            return null;
        }
        else
        {
            return waypoints[trafficObject.GetWaypointCount()];
        }
    }

    public void RemoveFromQue()
    {
        trafficlight.RemoveFromQue();
    }

    IEnumerator TrainSpawned(GameObject train, float spawntime)
    {
        yield return new WaitForSeconds(spawntime);
        trafficlight.AddToQue();
        
        train.gameObject.GetComponent<TrainObject>().InitTrainObject(this);
    }

}
