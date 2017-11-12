using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficLane : MonoBehaviour {

	public TrafficLight controllingLight;
	public Transform[] waypoints;

	void Start () {
		//TODO:  init waypoints
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
