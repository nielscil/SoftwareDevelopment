using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum TrafficType{car=0, bus, pedestrian, cyclist, train}
public enum LaneDirection { straight=2,left=3,right=4}
public class TrafficLane : MonoBehaviour {
	public TrafficType type;
    public LaneDirection direction;
	public TrafficLight controllingLight;
	public Transform[] waypoints;

	void Start ()
	{
	}

	public int CheckTrafficLightState()
	{
		return controllingLight.state;
	}

	public void AddToQue()
	{
        if (type == TrafficType.bus)
        {
            controllingLight.AddToQue((int)direction);
        }
        else
        {
            controllingLight.AddToQue();
        }
	}

	public void RemoveFromQue()
	{
        if (type == TrafficType.bus)
        {
            controllingLight.RemoveFromQue((int)direction);
        }
        else
        {
            controllingLight.RemoveFromQue();
        }
        
	}
}
