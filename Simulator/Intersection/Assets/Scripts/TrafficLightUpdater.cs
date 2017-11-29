using Newtonsoft.Json;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficLightUpdater : MonoBehaviour
{
    private MessageBroker _messageBroker;
    private readonly Stack<ControllerUpdate> _received = new Stack<ControllerUpdate>();

    private TrafficLight[] trafficLights;
    private Dictionary<int, int> trafficLightsIDs = new Dictionary<int, int>(23);

    private void Start () {
        trafficLights = gameObject.GetComponentsInChildren<TrafficLight>();
        InitLightIDReferences();
        _messageBroker = gameObject.GetComponent<MessageBroker>();
        _messageBroker.Received += MessageBroker_Received;
    }

    private void MessageBroker_Received(string data)
    {
        if (data != null)
        {
            var update = JsonConvert.DeserializeObject<ControllerUpdate>(data);
            _received.Push(update);
        }
    }

    private void FixedUpdate()
    {
        UpdateTrafficLights();
    }

    private ControllerUpdate GetUpdate()
    {
        if (_received != null && _received.Count > 0)
            return _received.Pop(); //TODO: make this threadsafe
        else
            return null;
    }

    void InitLightIDReferences()
    {
		int count = 0;
		foreach (TrafficLight light in trafficLights)
		{
			trafficLightsIDs.Add (light.ID, count);
			count++;
		}
    }

    void UpdateTrafficLights()
    {
        ControllerUpdate update = GetUpdate();

        if (update != null && update.Lights != null)
        {
            foreach (LightUpdate l in update.Lights)
            {
                if(l.Id == 412)
                {
                   
                }
                trafficLights[trafficLightsIDs[l.Id]].UpdateState(l.Status, l.Time);
                //Debug.Log(l.Id + ": " + l.Status);
            }
        }
    }
}
