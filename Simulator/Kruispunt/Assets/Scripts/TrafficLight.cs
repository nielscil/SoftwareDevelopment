using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public delegate void StateChanged(int state);

public class TrafficLight
{
	public GameObject obj;

	private int _id;
	private int _state = 0;
	[SerializeField]
	private Stack<string> _stateLog;
	private event StateChanged _changed;

	public TrafficLight(int id)
	{
		_id = id;
		_stateLog = new Stack<string> ();
		_stateLog.Push (" > Trafficlight created.");
	}

	public void UpdateState(int newState)
	{
		_state = newState;
		_stateLog.Push (" > Trafficlight changed to: " + newState.ToString () + ".");
		if (_changed != null)
		{
			_changed.Invoke (newState);
		}
	}

	public event StateChanged Changed
	{
		add
		{
			_changed += value;
		}
		remove
		{
			_changed -= value;
		}
	}
}
