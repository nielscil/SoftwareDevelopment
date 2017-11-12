using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StatePool : MonoBehaviour {

	int numState = 25;
	public GameObject greenPrefab;
	public GameObject yellowPrefab;
	public GameObject redPrefab;
	GameObject[] green;
	GameObject[] yellow;
	GameObject[] red;

	void Start () {
		green = new GameObject[numState];
		yellow = new GameObject[numState];
		red = new GameObject[numState];

		for (int i = 0; i < numState; i++)
		{
			green [i] = (GameObject)Instantiate (greenPrefab, Vector3.zero, Quaternion.identity);
			green [i].SetActive (false);

			yellow [i] = (GameObject)Instantiate (yellowPrefab, Vector3.zero, Quaternion.identity);
			yellow [i].SetActive (false);

			red [i] = (GameObject)Instantiate (redPrefab, Vector3.zero, Quaternion.identity);
			red [i].SetActive (false);
		}
	}

	public GameObject getGreen()
	{
		for (int i = 0; i < numState; i++)
		{
			if (!green [i].activeSelf)
			{
				return green [i];
			}
		}
		return null;
	}

	public GameObject getYellow()
	{
		for (int i = 0; i < numState; i++)
		{
			if (!yellow [i].activeSelf)
			{
				return yellow [i];
			}
		}
		return null;
	}

	public GameObject getRed()
	{
		for (int i = 0; i < numState; i++)
		{
			if (!red [i].activeSelf)
			{
				return red [i];
			}
		}
		return null;
	}
}
