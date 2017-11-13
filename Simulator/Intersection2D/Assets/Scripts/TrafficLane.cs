using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum TrafficType{car=0, bus, pedestrian, cyclist, train}
public class TrafficLane : MonoBehaviour {
	public TrafficType type;
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
		controllingLight.addToQue ();
	}

	public void RemoveFromQue()
	{
		controllingLight.removeFromQue ();
	}
}
