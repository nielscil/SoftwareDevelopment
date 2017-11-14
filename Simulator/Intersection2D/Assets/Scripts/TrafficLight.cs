using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficLight : MonoBehaviour
{
	public int id;
	public int state = 0;
	public int trafficInQue = 0;
    private List<int> _directionRequests;

	private MessageBroker _messageBroker;

	private StatePool _statePool;
	private GameObject _stateObject;
	private Stack<string> _stateLog;

	void Start()
	{
		_statePool = this.GetComponentInParent<StatePool>();
		_stateObject = _statePool.getRed ();
		_stateObject.transform.position = this.transform.position;
		_stateObject.SetActive (true);

		_messageBroker = this.gameObject.GetComponentInParent<MessageBroker> ();
		_stateLog = new Stack<string> ();
        _directionRequests = new List<int>();
		_stateLog.Push (" > Trafficlight created.");
	}

	public void UpdateState(int newState)
	{
		if (state != newState)
		{
			_stateObject.SetActive (false);
			_stateObject.transform.position = Vector3.zero;

			if (newState == 0)
			{
				_stateObject = _statePool.getRed ();
				_stateObject.transform.position = this.transform.position;
				_stateObject.SetActive (true);
			}
			else if (newState == 1)
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
            else
            {
                _stateObject = _statePool.getGreen();
                _stateObject.transform.position = this.transform.position;
                _stateObject.SetActive(true);
            }
		}

		state = newState;
		_stateLog.Push (" > Trafficlight changed to: " + newState.ToString () + ".");
	}

	public void AddToQue()
	{
		trafficInQue += 1;
		SendUpdate ();
	}

    public void AddToQue(int direction)
    {
        trafficInQue += 1;
        _directionRequests.Add(direction);
        SendUpdate();
    }

    public void RemoveFromQue()
	{
		trafficInQue -= 1;
		SendUpdate ();
	}

    public void RemoveFromQue(int direcion)
    {
        trafficInQue -= 1;
        _directionRequests.Remove(direcion);
        SendUpdate();
    }

    private void SendUpdate()
	{
        TrafficUpdate update;
        if (_directionRequests.Count == 0)
        {
            update = new TrafficUpdate(id, trafficInQue);
        }
        else
        {
            int[] directions = new int[_directionRequests.Count];
            for(int i = 0; i < _directionRequests.Count; i++)
            {
                directions[i] = _directionRequests[i];
            }
            update = new TrafficUpdate(id, trafficInQue, directions);
        }

		_messageBroker.SendTrafficUpdate(update);
	}	
}
