using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficLight : MonoBehaviour
{
    public int ID;
	public TrafficType trafficType;
    private int State { get; set; }
    private int TrafficInQue { get; set; }
    private List<int> DirectionRequests { get; set; }

    private StatePool _StatePool { get; set; }
    private GameObject StateObject { get; set; }
    
    private MessageBroker _MessageBroker { get; set; }

    private void Start()
    {
        State = 0;
        TrafficInQue = 0;
        DirectionRequests = new List<int>();
        _StatePool = gameObject.GetComponentInParent<StatePool>();
        StateObject = _StatePool.GetColorStateObject(0);
        StateObject.transform.position = transform.position;
        StateObject.SetActive(true);
        _MessageBroker = gameObject.GetComponentInParent<MessageBroker>();
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

        _MessageBroker.SendTrafficUpdate(update);
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

    private void UpdateMe(int newState)
    {
        StateObject.SetActive(false);
        State = newState;
        if (trafficType != TrafficType.busses)
        {
            StateObject = _StatePool.GetColorStateObject(newState);
        }
        else
        {
            StateObject = _StatePool.GetDirectionStateObject(newState);
        }

        StateObject.transform.position = transform.position;
        StateObject.SetActive(true);
    }

    IEnumerator UpdateAfter(int newstate ,int seconds)
    {
        yield return new WaitForSeconds(seconds);
        UpdateMe(newstate);
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
