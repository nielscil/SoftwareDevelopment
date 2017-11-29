using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficSpawner : MonoBehaviour {

    public Lane[] Lanes;
    public TrainTrack[] traintrack;

	// Use this for initialization
	void Start () {
        Lanes = gameObject.GetComponentsInChildren<Lane>();
        traintrack = gameObject.GetComponentsInChildren<TrainTrack>();
        InvokeRepeating("SpawnTraffic", 5.0f, 1.0f);
	}

    private void SpawnTraffic()
    {
        int lane = Random.Range(0, Lanes.Length + 2);

        if (lane == 25)
        {
            traintrack[0].SpawnTraffic();
        }
        else if (lane == 26)
        {
            traintrack[1].SpawnTraffic();
        }
        else
        {
            Lanes[lane].SpawnTraffic();
        }
    }
}
