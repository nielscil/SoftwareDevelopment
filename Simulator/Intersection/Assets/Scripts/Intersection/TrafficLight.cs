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
    private object _syncLock = new object();
    private List<int> DirectionRequests;
    private GameObject[] _currentStateObjects;
    private MessageBroker _messageBroker;

    private void Start()
    {
        
        
        TrafficInQue = 0;
        DirectionRequests = new List<int>();
        _currentStateObjects = new GameObject[locations.Length];
        if (ID == 601)
        {
            State = 2;
            SetStateObjects(2);
        }
        else
        {
            State = 0;
            SetStateObjects(0);
        }
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

        lock (_syncLock)
        {
            var array = DirectionRequests.ToArray();
            update = new TrafficUpdate(ID, TrafficInQue, array.Length == 0 ? null : array);
        }

        _messageBroker.SendTrafficUpdate(update);
    }

    public void UpdateState(int newState, int time)
    {
        if(State == newState)
        {
            return;
        }
        if(ID == 601)
        {
            SetStateObjects(1);
            time = 12;
        }
            
        StartCoroutine(UpdateAfter(newState, time));
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
        lock(_syncLock)
        {
            TrafficInQue++;
        }
        
        SendTrafficUpdate();
    }

    public void AddToQue(int direction)
    {
        lock(_syncLock)
        {
            TrafficInQue++;
            DirectionRequests.Add(direction);
        }
        
        SendTrafficUpdate();
    }

    public void RemoveFromQue()
    {
        int queue = 0;

        lock(_syncLock)
        {
            queue = TrafficInQue;
        }


        if(queue > 0)
        {
            lock(_syncLock)
            {
                TrafficInQue--;
            }
            
            SendTrafficUpdate();
        }
    }

    public void RemoveFromQue(int direction)
    {
        int queue = 0;

        lock (_syncLock)
        {
            queue = TrafficInQue;
        }

        if (queue > 0) //temp fix for negative count problem
        {
            lock(_syncLock)
            {
                TrafficInQue--;
                DirectionRequests.Remove(direction);
            }

            SendTrafficUpdate();
        }

        bool shouldUpdateEmpty = false;

        lock(_syncLock)
        {
            shouldUpdateEmpty = TrafficInQue == 0 && DirectionRequests.Count > 0;
        }

        if(shouldUpdateEmpty) //temp fix for negative count problem
        {
            lock(_syncLock)
            {
                DirectionRequests.Clear();
            }
            
            SendTrafficUpdate();
        }
    }

    public int GetState()
    {
        return State;
    }


}
