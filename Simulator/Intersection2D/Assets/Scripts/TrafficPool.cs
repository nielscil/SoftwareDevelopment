using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficPool : MonoBehaviour {

	int numCars = 100;
	public GameObject carPrefab;
	GameObject[] cars;

	void Start () {
		cars = new GameObject[numCars];
		for (int i = 0; i < numCars; i++)
		{
			cars [i] = (GameObject)Instantiate (carPrefab, Vector3.zero, Quaternion.identity);
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
}
