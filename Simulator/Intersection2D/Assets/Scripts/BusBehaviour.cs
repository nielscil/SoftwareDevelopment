using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BusBehaviour : MonoBehaviour, TrafficBehaviour
{
    public TrafficLane lane = null;
    private bool _inTrafficLightQue = false;
    private bool _atTrafficLight = false;
    private int _currentWaypoint = 0;
    private Transform _currentWaypointHolder = null;

    public float moveSpeed = 1f;
    private float timer;

    public void Start()
    {
        transform.position = lane.waypoints[_currentWaypoint].position;
        transform.rotation = lane.waypoints[_currentWaypoint].rotation;
        _currentWaypointHolder = lane.waypoints[_currentWaypoint];
        gameObject.SetActive(true);
    }

    void FixedUpdate()
    {
        Drive();
    }

    public void AtTrafficLight()
    {
        switch (lane.CheckTrafficLightState())
        {
            case 0:
                if (!_inTrafficLightQue)
                {
                    lane.AddToQue();
                    _inTrafficLightQue = true;
                }
                break;
            case 1:
                if (!_inTrafficLightQue)
                {
                    lane.AddToQue();
                    _inTrafficLightQue = true;
                }
                break;
            case 2:
                if (_inTrafficLightQue && lane.direction == LaneDirection.straight)
                {
                    lane.RemoveFromQue();
                    _inTrafficLightQue = false;
                }
                else
                {
                    if (!_inTrafficLightQue)
                    {
                        lane.AddToQue();
                        _inTrafficLightQue = true;
                    }
                }
                break;
            case 3:
                if (_inTrafficLightQue && lane.direction == LaneDirection.left)
                {
                    lane.RemoveFromQue();
                    _inTrafficLightQue = false;
                }
                else
                {
                    if (!_inTrafficLightQue)
                    {
                        lane.AddToQue();
                        _inTrafficLightQue = true;
                    }
                }
                break;
            case 4:
                if (_inTrafficLightQue && lane.direction == LaneDirection.right)
                {
                    lane.RemoveFromQue();
                    _inTrafficLightQue = false;
                }
                else
                {
                    if (!_inTrafficLightQue)
                    {
                        lane.AddToQue();
                        _inTrafficLightQue = true;
                    }
                }
                break;
            case 5:
                if (_inTrafficLightQue && (lane.direction == LaneDirection.left || lane.direction == LaneDirection.right))
                {
                    lane.RemoveFromQue();
                    _inTrafficLightQue = false;
                }
                else
                {
                    if (!_inTrafficLightQue)
                    {
                        lane.AddToQue();
                        _inTrafficLightQue = true;
                    }
                }
                break;
            case 6:
                if (_inTrafficLightQue && (lane.direction == LaneDirection.right || lane.direction == LaneDirection.straight))
                {
                    lane.RemoveFromQue();
                    _inTrafficLightQue = false;
                }
                else
                {
                    if (!_inTrafficLightQue)
                    {
                        lane.AddToQue();
                        _inTrafficLightQue = true;
                    }
                }
                break;
            case 7:
                if (_inTrafficLightQue && (lane.direction == LaneDirection.left || lane.direction == LaneDirection.straight))
                {
                    lane.RemoveFromQue();
                    _inTrafficLightQue = false;
                }
                else
                {
                    if (!_inTrafficLightQue)
                    {
                        lane.AddToQue();
                        _inTrafficLightQue = true;
                    }
                }
                break;
            case 8:
                if (_inTrafficLightQue)
                {
                    lane.AddToQue();
                    _inTrafficLightQue = true;
                }
                break;
        }
    }

    public void Drive()
    {
        timer += Time.fixedDeltaTime * moveSpeed;
        if (this.transform.position != _currentWaypointHolder.transform.position)
        {
            this.transform.position = Vector3.Lerp(this.transform.position, _currentWaypointHolder.position, timer);
            this.transform.rotation = Quaternion.Lerp(this.transform.rotation, _currentWaypointHolder.rotation, timer);
        }
        else
        {
            if (_atTrafficLight)
            {
                AtTrafficLight();
            }

            if (!_inTrafficLightQue)
            {
                _atTrafficLight = false;
                GetWaypoint();
            }
        }
    }

    public void GetWaypoint()
    {
        timer = 0;
        _currentWaypoint++;

        if (_currentWaypoint != lane.waypoints.Length)
        {
            _currentWaypointHolder = lane.waypoints[_currentWaypoint];
            if (_currentWaypointHolder.gameObject.name == "light")
            {
                _atTrafficLight = true;
            }
        }
        else
        {
            ReleaseMe();
        }
    }

    public void ReleaseMe()
    {
        gameObject.SetActive(false);
        lane = null;
        _inTrafficLightQue = false;
        _atTrafficLight = false;
        _currentWaypoint = 0;
        _currentWaypointHolder = null;
    }
}
