using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficSpawner : MonoBehaviour {

    public Lane[] Lanes;
    public TrainTrack traintrack;

	// Use this for initialization
	void Start () {
        Lanes = gameObject.GetComponentsInChildren<Lane>();
        traintrack = gameObject.GetComponentInChildren<TrainTrack>();
        InvokeRepeating("SpawnTraffic", 5.0f, 1.0f);
	}

    private void SpawnTraffic()
    {
        int lane = Random.Range(0, Lanes.Length);

        if(lane == 25)
        {
            traintrack.SpawnTraffic();
        }
        else
        {
            Lanes[lane].SpawnTraffic();
        }
    }
}
