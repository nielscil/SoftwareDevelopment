using Newtonsoft.Json;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Serialization;

public class TrafficUpdate 
{
	public int LightId;
	public int Count;
	public int[] DirectionRequests;

	public TrafficUpdate(int lightID, int count, int[] directionRequests = null)
	{
		LightId = lightID;
		Count = count;
		DirectionRequests = directionRequests;
	}
}

public class TrafficUpdateWrapper
{
    public TrafficUpdate TrafficUpdate;

    public TrafficUpdateWrapper(TrafficUpdate traficUpdate)
    {
        TrafficUpdate = traficUpdate;
    }

    public string ToJson()
    {
        return JsonConvert.SerializeObject(this);
    }
}
