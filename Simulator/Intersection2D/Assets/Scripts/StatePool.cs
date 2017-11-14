using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StatePool : MonoBehaviour {

	int numState = 25;
	public GameObject greenPrefab;
	public GameObject yellowPrefab;
	public GameObject redPrefab;
    public GameObject straightPrefab;
    public GameObject leftPrefab;
    public GameObject rightPrefab;
    public GameObject straightRightPrefab;

	GameObject[] green;
	GameObject[] yellow;
	GameObject[] red;
    GameObject straight;
    GameObject right;
    GameObject left;
    GameObject straightRight;

	void Start () {
		green = new GameObject[numState];
		yellow = new GameObject[numState];
		red = new GameObject[numState];

        straight = Instantiate(straightPrefab, Vector3.zero, Quaternion.identity);
        straight.SetActive(false);
        right = Instantiate(rightPrefab, Vector3.zero, Quaternion.identity);
        right.SetActive(false);
        left = Instantiate(leftPrefab, Vector3.zero, Quaternion.identity);
        left.SetActive(false);
        straightRight = Instantiate(straightRightPrefab, Vector3.zero, Quaternion.identity);
        straightRight.SetActive(false);

        for (int i = 0; i < numState; i++)
		{
			green [i] = Instantiate (greenPrefab, Vector3.zero, Quaternion.identity);
			green [i].SetActive (false);

			yellow [i] = Instantiate (yellowPrefab, Vector3.zero, Quaternion.identity);
			yellow [i].SetActive (false);

			red [i] = Instantiate (redPrefab, Vector3.zero, Quaternion.identity);
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

    public GameObject GetStraight()
    {
        return straight;
    }

    public GameObject GetRight()
    {
        return right;
    }

    public GameObject GetLeft()
    {
        return left; 
    }

    public GameObject GetStraightRight()
    {
        return straightRight;
    }
}
