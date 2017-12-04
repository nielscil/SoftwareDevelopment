using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrainTrack : MonoBehaviour
{
    public Transform[] waypoints;
    public TrafficLight trafficlight;
    public float spawnTime;
    public GameObject trainPrefab;
    private bool trainActive = false;
    private TrafficPool _TrafficPool { get; set; }
    GameObject train;

    private void Start()
    {
        _TrafficPool = gameObject.GetComponentInParent<TrafficPool>();
    }

    public void SpawnTraffic()
    {
        train = Instantiate(trainPrefab, Vector3.zero, Quaternion.identity);
        train.SetActive(false);
        if(!trainActive)
        {
            trainActive = true;
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
        trainActive = false;
        trafficlight.RemoveFromQue();
        Destroy(train);
    }

    IEnumerator TrainSpawned(GameObject train, float spawntime)
    {
        Debug.Log("Spawning train after: " + spawntime);
        trafficlight.AddToQue();
        yield return new WaitForSeconds(spawntime);
        
        
        train.gameObject.GetComponent<TrainObject>().InitTrainObject(this);
    }

}
