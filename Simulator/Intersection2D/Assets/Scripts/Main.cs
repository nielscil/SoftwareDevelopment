using System.Collections;
using System.Collections.Generic;
using Newtonsoft.Json;
using UnityEngine;

public class Main : MonoBehaviour
{
	//private BlackBox _blackBox;

	[Header("BlackBox information")]
	[SerializeField] private MessageBroker _messageBroker;
	private readonly Stack<ControllerUpdate> _received = new Stack<ControllerUpdate>();

	[Header("Traffic Pool")]
	public TrafficPool trafficPool;

	[Header("Traffic Lights")]
	public TrafficLight[] trafficLights = new TrafficLight[24];
	public Dictionary<int, int> trafficLightsIDs = new Dictionary<int, int>(24);

	[Header("Traffic Lanes")]
	public TrafficLane[] trafficLanes = new TrafficLane[27];

	void Start ()
	{
		InitLightIDReferences ();
		_messageBroker.Received += MessageBroker_Received;
		InvokeRepeating ("SpawnTraffic", 5.0F, 4.0F);
	}

	void FixedUpdate()
	{
		UpdateTrafficLights ();
	}

	void OnDestroy()
	{
		
	}

	void InitLightIDReferences()
	{
		KeyValuePair<int, int> p;
		trafficLightsIDs.Add (101,0);
		trafficLightsIDs.Add (102,1);
		trafficLightsIDs.Add (103,2);
		trafficLightsIDs.Add (104,3);
		trafficLightsIDs.Add (105,4);
		trafficLightsIDs.Add (106,5);
		trafficLightsIDs.Add (107,6);
		trafficLightsIDs.Add (108,7);
		trafficLightsIDs.Add (109,8);
		trafficLightsIDs.Add (110,9);
		trafficLightsIDs.Add (201,10);
		trafficLightsIDs.Add (301,11);
		trafficLightsIDs.Add (302,12);
		trafficLightsIDs.Add (303,13);
		trafficLightsIDs.Add (304,14);
		trafficLightsIDs.Add (305,15);
		trafficLightsIDs.Add (401,16);
		trafficLightsIDs.Add (402,17);
		trafficLightsIDs.Add (403,18);
		trafficLightsIDs.Add (404,19);
		trafficLightsIDs.Add (405,20);
		trafficLightsIDs.Add (406,21);
		trafficLightsIDs.Add (501,22);
		trafficLightsIDs.Add (601,23);
	}

	void SpawnTraffic()
	{
		int lane = Random.Range (0, 26);
		GameObject car;


	}

	void MessageBroker_Received (string data)
	{
		if (data != null)
		{
			var update = JsonConvert.DeserializeObject<ControllerUpdate> (data);
			_received.Push (update);
		}
	}

	ControllerUpdate GetUpdate()
	{
		if (_received != null && _received.Count > 0)
			return _received.Pop (); //TODO: make this threadsafe
		else
			return null;
	}


	void UpdateTrafficLights()
	{
		ControllerUpdate update = GetUpdate();

		if (update != null && update.Lights != null)
		{
			foreach (LightUpdate l in update.Lights)
			{
				trafficLights [trafficLightsIDs [l.Id]].UpdateState (l.Status);
			}
		}
	}
}
