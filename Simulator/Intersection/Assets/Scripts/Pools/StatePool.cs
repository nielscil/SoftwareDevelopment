using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class StatePool : MonoBehaviour
{

    private int numColors = 32;

    public GameObject greenPrefab;
    private GameObject[] green;

    public GameObject yellowPrefab;
    private GameObject[] yellow;

    public GameObject redPrefab;
    private GameObject[] red;

    public GameObject straightPrefab;
    private GameObject straight;

    public GameObject leftPrefab;
    private GameObject left;

    public GameObject rightPrefab;
    private GameObject right;

    public GameObject straightRightPrefab;
    private GameObject straightRight;


    private void Start()
    {
        green = new GameObject[numColors];
        yellow = new GameObject[numColors];
        red = new GameObject[numColors];

        for (int i = 0; i < numColors; i++)
        {
            green[i] = Instantiate(greenPrefab, Vector3.zero, Quaternion.identity);
            green[i].SetActive(false);

            yellow[i] = Instantiate(yellowPrefab, Vector3.zero, Quaternion.identity);
            yellow[i].SetActive(false);

            red[i] = Instantiate(redPrefab, Vector3.zero, Quaternion.identity);
            red[i].SetActive(false);
        }

        straight = Instantiate(straightPrefab, Vector3.zero, Quaternion.identity);
        straight.SetActive(false);

        right = Instantiate(rightPrefab, Vector3.zero, Quaternion.identity);
        right.SetActive(false);

        straightRight = Instantiate(straightRightPrefab, Vector3.zero, Quaternion.identity);
        straightRight.SetActive(false);
    }

    public GameObject GetColorStateObject(int state)
    {
        switch (state)
        {
            case 0:
                for (int i = 0; i < numColors; i++)
                {
                    if (!red[i].activeSelf)
                    {
                        return red[i];
                    }
                }
                return null;
            case 1:
                for (int i = 0; i < numColors; i++)
                {
                    if (!yellow[i].activeSelf)
                    {
                        return yellow[i];
                    }
                }
                return null;
            case 2:
                for (int i = 0; i < numColors; i++)
                {
                    if (!green[i].activeSelf)
                    {
                        return green[i];
                    }
                }
                return null;
        }

        return null;
    }

    public GameObject GetDirectionStateObject(int state)
    {
        switch (state)
        {
            case 0:
                for (int i = 0; i < numColors; i++)
                {
                    if (!red[i].activeSelf)
                    {
                        return red[i];
                    }
                }
                return null;
            case 1:
                for (int i = 0; i < numColors; i++)
                {
                    if (!yellow[i].activeSelf)
                    {
                        return yellow[i];
                    }
                }
                return null;
            case 2:
                return straight;
            case 3:
                return left;
            case 4:
                return right;
        }

		return null;
    }
}
