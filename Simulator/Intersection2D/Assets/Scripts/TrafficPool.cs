using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficPool : MonoBehaviour {

	int numCars = 100;
	int numBus = 50;
	public GameObject carPrefab;
	public GameObject busPrefab;
	GameObject[] cars;
	GameObject[] busses;

	void Start () {
		cars = new GameObject[numCars];
		busses = new GameObject[numBus];
		for (int i = 0; i < numCars; i++)
		{
			if (i < numBus)
			{
				busses [i] = Instantiate (busPrefab, Vector3.zero, Quaternion.identity);
				busses [i].SetActive (false);
			}

			cars [i] = Instantiate (carPrefab, Vector3.zero, Quaternion.identity);
			cars [i].SetActive (false);
		}
	}

	public GameObject getCar()
	{
		for (int i = 0; i < numCars; i++)
		{
			if (!cars [i].activeSelf)
			{
				return cars [i];
			}
		}
		return null;
	}

	public GameObject getBus()
	{
		for (int i = 0; i < numBus; i++)
		{
			if (!busses [i].activeSelf)
			{
				return busses [i];
			}
		}
		return null;
	}
}
