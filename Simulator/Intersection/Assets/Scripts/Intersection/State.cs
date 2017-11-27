using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class State : MonoBehaviour
{
    [Header("State Info:")]
    public int ID;
    public GameObject StatePrefab;

    [Header("Pool Info:")]
    public int InstantiateNumber;
    private GameObject[] States { get; set; }

    private void Start()
    {
        States = new GameObject[InstantiateNumber];

        for(int i = 0; i < InstantiateNumber; i++)
        {
            States[i] = Instantiate(StatePrefab, Vector3.zero, Quaternion.identity);
            States[i].SetActive(false);
        }
    }

    public GameObject GetState(Transform stateTransform)
    {
        for(int i = 0; i < InstantiateNumber; i++)
        {
            if (!States[i].activeSelf)
            {
                States[i].transform.position = stateTransform.position;
                States[i].transform.rotation = stateTransform.rotation;
                return States[i];
            }
        }

        return null;
    }
}
