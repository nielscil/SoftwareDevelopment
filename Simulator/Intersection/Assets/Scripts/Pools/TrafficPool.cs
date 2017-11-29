using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficPool : MonoBehaviour
{
    public GameObject CarPrefab;
    private int _numCars = 100;
    private GameObject[] _cars;

    public GameObject BusPrefab;
    private int _numbusses = 50;
    private GameObject[] _busses;

    public GameObject PedestrianPrefab;
    private int _numPedestrians = 100;
    private GameObject[] _pedestrians;

    public GameObject CyclistPrefab;
    private int _numCyclists = 100;
    private GameObject[] _cyclists;

    public GameObject TrainPrefab;
    private int _numTrains = 5;
    private GameObject[] _trains;

    private void Start()
    {
        _cars = new GameObject[_numCars];
        _busses = new GameObject[_numbusses];
        _pedestrians = new GameObject[_numPedestrians];
        _cyclists = new GameObject[_numCyclists];
        _trains = new GameObject[_numTrains];

        for(int i = 0; i < _numCars; i++)
        {
            _cars[i] = Instantiate(CarPrefab, Vector3.zero, Quaternion.identity);
            _cars[i].SetActive(false);

            if (i < _numbusses)
            {
                _busses[i] = Instantiate(BusPrefab, Vector3.zero, Quaternion.identity);
                _busses[i].SetActive(false);
            }

            _pedestrians[i] = Instantiate(PedestrianPrefab, Vector3.zero, Quaternion.identity);
            _pedestrians[i].SetActive(false);

            _cyclists[i] = Instantiate(CyclistPrefab, Vector3.zero, Quaternion.identity);
            _cyclists[i].SetActive(false);
        }
    }


    public GameObject GetCar()
    {
        for(int i = 0; i < _numCars; i++)
        {
            if (!_cars[i].activeSelf)
            {
                return _cars[i];
            }
        }

        return null;
    }

    public GameObject GetBus()
    {
        for (int i = 0; i < _numbusses; i++)
        {
            if (!_busses[i].activeSelf)
            {
                return _busses[i];
            }
        }

        return null;
    }

    public GameObject GetPedestrian()
    {
        for (int i = 0; i < _numPedestrians; i++)
        {
            if (!_pedestrians[i].activeSelf)
            {
                return _pedestrians[i];
            }
        }

        return null;
    }

    public GameObject GetCyclist()
    {
        for (int i = 0; i < _numCyclists; i++)
        {
            if (!_cyclists[i].activeSelf)
            {
                return _cyclists[i];
            }
        }

        return null;
    }
    
}
