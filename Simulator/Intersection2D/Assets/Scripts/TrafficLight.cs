using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficLight : MonoBehaviour
{
	public int id;
	public int state = 0;
	private Stack<string> _stateLog;
	public int trafficInQue = 0;

	private MessageBroker _messageBroker;

	private StatePool _statePool;
	private GameObject _stateObject;

	void Start()
	{
		_statePool = this.GetComponentInParent<StatePool>();
		_stateObject = _statePool.getRed ();
		_stateObject.transform.position = this.transform.position;
		_stateObject.SetActive (true);

		_messageBroker = this.gameObject.GetComponentInParent<MessageBroker> ();
		_stateLog = new Stack<string> ();
		_stateLog.Push (" > Trafficlight created.");
	}

	public void UpdateState(int newState)
	{
		if (state != newState)
		{
			_stateObject.SetActive (false);
			_stateObject.transform.position = Vector3.zero;

			if (newState == 1)
			{
				_stateObject = _statePool.getYellow ();
				_stateObject.transform.position = this.transform.position;
				_stateObject.SetActive (true);
			}
			else if (newState == 2)
			{
				_stateObject = _statePool.getGreen ();
				_stateObject.transform.position = this.transform.position;
				_stateObject.SetActive (true);
			}
		}

		state = newState;
		_stateLog.Push (" > Trafficlight changed to: " + newState.ToString () + ".");
	}

	public void addToQue()
	{
		trafficInQue += 1;
		SendUpdate ();
	}

	public void removeFromQue()
	{
		trafficInQue -= 1;
		SendUpdate ();
	}

	private void SendUpdate()
	{
		TrafficUpdate update = new TrafficUpdate(id, trafficInQue);
		_messageBroker.SendTrafficUpdate(update);
	}	
}
