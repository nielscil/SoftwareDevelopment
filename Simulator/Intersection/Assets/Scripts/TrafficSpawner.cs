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
        InvokeRepeating("SpawnTraffic", 5.0f, 2.0f);
	}

    private void Update()
    {
        if (Input.GetKeyDown(KeyCode.A))
        {
            traintrack[0].SpawnTraffic();
        }

        if (Input.GetKeyDown(KeyCode.B))
        {
            traintrack[1].SpawnTraffic();
        }
    }

    private void SpawnTraffic()
    {
        int lane = Random.Range(0, Lanes.Length + 2);
        int spawntrain = Random.Range(0, 2);
        if (lane == 25)
        {
            /*
            if(spawntrain == 1)
                traintrack[0].SpawnTraffic();*/
        }
        else if (lane == 26)
        {
            /*
            if (spawntrain == 1)
                traintrack[1].SpawnTraffic();*/
        }
        else
        {
            Lanes[lane].SpawnTraffic();
        }
    }
}
