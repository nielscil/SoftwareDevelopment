using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Serialization;

public class TrafficUpdate 
{
	public int LightID;
	public int Count;
	public int[] DirectionRequests;

	public TrafficUpdate(int lightID, int count, int[] directionRequests = null)
	{
		LightID = lightID;
		Count = count;
		DirectionRequests = directionRequests;
	}
}
