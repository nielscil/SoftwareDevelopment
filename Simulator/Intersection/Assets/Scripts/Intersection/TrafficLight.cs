using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficLight : MonoBehaviour
{
    public int ID;
    public State[] States;
    public Transform[] locations;

    private int State;
    private int TrafficInQue;
    private List<int> DirectionRequests;
    private GameObject[] _currentStateObjects;
    private MessageBroker _messageBroker;

    private void Start()
    {
        State = 0;
        TrafficInQue = 0;
        DirectionRequests = new List<int>();
        _currentStateObjects = new GameObject[locations.Length];
        SetStateObjects(0);
        _messageBroker = gameObject.GetComponentInParent<MessageBroker>();
    }

    private void SetStateObjects(int state)
    {
        for (int i = 0; i < locations.Length; i++)
        {
            if (_currentStateObjects[i] != null)
            {
                _currentStateObjects[i].SetActive(false);
            }
            
            _currentStateObjects[i] = States[state].GetState(locations[i]);
            _currentStateObjects[i].SetActive(true);
        }
    }
    
    private void SendTrafficUpdate()
    {
        
        TrafficUpdate update;
        if (DirectionRequests.Count == 0)
        {
			update = new TrafficUpdate(ID, TrafficInQue);
        }
        else
        {
            int count = DirectionRequests.Count;
            int[] directions = new int[count];
            for (int i = 0; i < count; i++)
            {
                directions[i] = DirectionRequests[i];
            }

			update = new TrafficUpdate(ID, TrafficInQue, directions);
        }

        _messageBroker.SendTrafficUpdate(update);
    }

    public void UpdateState(int newState, int time)
    {
        if(!(time <= 0))
        {
            StartCoroutine(UpdateAfter(newState, time));
        }
        else
        {
            UpdateMe(newState);
        }
    }

    private IEnumerator UpdateAfter(int newstate, int seconds)
    {
        yield return new WaitForSeconds(seconds);
        UpdateMe(newstate);
    }

    private void UpdateMe(int newState)
    {
        State = newState;

        SetStateObjects(newState);
    }

    public void AddToQue()
    {
        TrafficInQue++;
        SendTrafficUpdate();
    }

    public void AddToQue(int direction)
    {
        TrafficInQue++;
        DirectionRequests.Add(direction);
        SendTrafficUpdate();
    }

    public void RemoveFromQue()
    {
        TrafficInQue--;
        SendTrafficUpdate();
    }

    public void RemoveFromQue(int direction)
    {
        TrafficInQue--;
        DirectionRequests.Remove(direction);
        SendTrafficUpdate();
    }

    public int GetState()
    {
        return State;
    }


}
