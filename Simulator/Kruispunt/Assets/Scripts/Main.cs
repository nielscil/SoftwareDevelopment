using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Main : MonoBehaviour
{
	public TrafficLight[] _trafficLights = new TrafficLight[24];

	[Header("BlackBox information")]
	[SerializeField] private string _server;
	[SerializeField] private string _path;
	[SerializeField] private string _username;
	[SerializeField] private string _password;

	private BlackBox _blackBox;

	void Start ()
	{
		_blackBox = new BlackBox (_server, _path, _username, _password);
		InitTrafficLights ();
		TrafficUpdate test = new TrafficUpdate (101, 1);
		_blackBox.SendUpdate (test);
	}

	void FixedUpdate()
	{
		ControllerUpdate update = _blackBox != null ? _blackBox.GetUpdate() : null;

		if(update != null)
		{
			UpdateTrafficLights (update.Lights);
		}
	}

	private void InitTrafficLights()
	{
		_trafficLights [0] = new TrafficLight (101);
		_trafficLights [1] = new TrafficLight (102);
		_trafficLights [2] = new TrafficLight (103);
		_trafficLights [3] = new TrafficLight (104);
		_trafficLights [4] = new TrafficLight (105);
		_trafficLights [5] = new TrafficLight (106);
		_trafficLights [6] = new TrafficLight (107);
		_trafficLights [7] = new TrafficLight (108);
		_trafficLights [8] = new TrafficLight (109);
		_trafficLights [9] = new TrafficLight (110);
		_trafficLights [10] = new TrafficLight (201);
		_trafficLights [11] = new TrafficLight (301);
		_trafficLights [12] = new TrafficLight (302);
		_trafficLights [13] = new TrafficLight (303);
		_trafficLights [14] = new TrafficLight (304);
		_trafficLights [15] = new TrafficLight (305);
		_trafficLights [16] = new TrafficLight (401);
		_trafficLights [17] = new TrafficLight (402);
		_trafficLights [18] = new TrafficLight (403);
		_trafficLights [19] = new TrafficLight (404);
		_trafficLights [20] = new TrafficLight (405);
		_trafficLights [21] = new TrafficLight (406);
		_trafficLights [22] = new TrafficLight (501);
		_trafficLights [23] = new TrafficLight (601);
	}

	private void UpdateTrafficLights(List<LightUpdate> lights)
	{
        if(lights != null)
        {
            foreach (LightUpdate l in lights)
            {
                switch (l.Id)
                {
                    case 101:                        
                        if(l.Status == 2 && _trafficLights[0].State != 2)
                        {
                            TrafficUpdate test = new TrafficUpdate(101, 0);
                            _blackBox.SendUpdate(test);
                        }
                        _trafficLights[0].UpdateState(l.Status); //TODO: place test code here
                        break;
                    case 102:
                        _trafficLights[1].UpdateState(l.Status);
                        break;
                    case 103:
                        _trafficLights[2].UpdateState(l.Status);
                        break;
                    case 104:
                        _trafficLights[3].UpdateState(l.Status);
                        break;
                    case 105:
                        _trafficLights[4].UpdateState(l.Status);
                        break;
                    case 106:
                        _trafficLights[5].UpdateState(l.Status);
                        break;
                    case 107:
                        _trafficLights[6].UpdateState(l.Status);
                        break;
                    case 108:
                        _trafficLights[7].UpdateState(l.Status);
                        break;
                    case 109:
                        _trafficLights[8].UpdateState(l.Status);
                        break;
                    case 110:
                        _trafficLights[9].UpdateState(l.Status);
                        break;
                    case 201:
                        _trafficLights[10].UpdateState(l.Status);
                        break;
                    case 301:
                        _trafficLights[11].UpdateState(l.Status);
                        break;
                    case 302:
                        _trafficLights[12].UpdateState(l.Status);
                        break;
                    case 303:
                        _trafficLights[13].UpdateState(l.Status);
                        break;
                    case 304:
                        _trafficLights[14].UpdateState(l.Status);
                        break;
                    case 305:
                        _trafficLights[15].UpdateState(l.Status);
                        break;
                    case 401:
                        _trafficLights[16].UpdateState(l.Status);
                        break;
                    case 402:
                        _trafficLights[17].UpdateState(l.Status);
                        break;
                    case 403:
                        _trafficLights[18].UpdateState(l.Status);
                        break;
                    case 404:
                        _trafficLights[19].UpdateState(l.Status);
                        break;
                    case 405:
                        _trafficLights[20].UpdateState(l.Status);
                        break;
                    case 406:
                        _trafficLights[21].UpdateState(l.Status);
                        break;
                    case 501:
                        _trafficLights[22].UpdateState(l.Status);
                        break;
                    case 601:
                        _trafficLights[23].UpdateState(l.Status);
                        break;
                }
            }
        }
	}
}
