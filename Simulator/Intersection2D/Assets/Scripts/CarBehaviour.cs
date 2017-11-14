using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CarBehaviour : MonoBehaviour, TrafficBehaviour {

	public TrafficLane lane = null;
	private bool _inTrafficLightQue = false;
	private bool _atTrafficLight = false;
	private int _currentWaypoint = 0;
	private Transform _currentWaypointHolder = null;
	private bool _waitForOtherTraffic = false;

	public float moveSpeed = 1f;
	private float timer;

	public void Start()
	{
		transform.position = lane.waypoints [_currentWaypoint].position;
		transform.rotation = lane.waypoints [_currentWaypoint].rotation;
		_currentWaypointHolder = lane.waypoints [_currentWaypoint];
        gameObject.SetActive (true);
	}

	void FixedUpdate ()
	{
		Drive ();
	}

	public void Drive()
	{
		
		if (this.transform.position != _currentWaypointHolder.transform.position)
		{
			if (!_waitForOtherTraffic)
			{
				timer += Time.fixedDeltaTime * moveSpeed;
				this.transform.position = Vector3.Lerp (this.transform.position, _currentWaypointHolder.position, timer);
				this.transform.rotation = Quaternion.Lerp (this.transform.rotation, _currentWaypointHolder.rotation, timer);
			}
		}
		else
		{
			if (_atTrafficLight)
			{
				AtTrafficLight ();
			}

			if (!_inTrafficLightQue)
			{
				_atTrafficLight = false;
				GetWaypoint ();
			}
		}
	}

	public void GetWaypoint()
	{
		timer = 0;
		_currentWaypoint++;

		if (_currentWaypoint != lane.waypoints.Length) {
			_currentWaypointHolder = lane.waypoints [_currentWaypoint];
			if (_currentWaypointHolder.gameObject.name == "light")
			{
				_atTrafficLight = true;
			}
		}
		else
		{
			ReleaseMe ();
		}
	}

	public void AtTrafficLight()
	{
		switch (lane.CheckTrafficLightState())
		{
		case 0:
			if (!_inTrafficLightQue)
			{
				lane.AddToQue ();
				_inTrafficLightQue = true;
			}
			break;
		case 1:
			if (!_inTrafficLightQue)
			{
				lane.AddToQue ();
				_inTrafficLightQue = true;
			}
			break;
		case 2:
			if (_inTrafficLightQue) {
				lane.RemoveFromQue ();
				_inTrafficLightQue = false;
			}
			break;
		}
	}

	public void ReleaseMe()
	{
		this.gameObject.SetActive (false);
		lane = null;
		_inTrafficLightQue = false;
		_atTrafficLight = false;
		_currentWaypoint = 0;
		_currentWaypointHolder = null;
	}

	void OnTriggerEnter2D(Collider2D collider)
	{
		if (!collider.isTrigger)
			_waitForOtherTraffic = true;
	}

	void OnTriggerExit2D(Collider2D collider)
	{
        if (!collider.isTrigger)
			_waitForOtherTraffic = false;
	}
}
