using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficObject : MonoBehaviour, ITrafficObject
{
    public float movementSpeed;
    public float rotationSpeed;

    public bool Stop { get; set; }

    private bool inQue = false;
    private bool _atTrafficLight = false;
    private Direction _direction { get; set; }
    private TrafficType _trafficType { get; set; }
    private TrafficLight _trafficlightInQue { get; set; }
    private Lane _lane { get; set; }
    private int _waypointCount = 0;
    private Transform _currentWaypoint = null;

    private void FixedUpdate()
    {
        FollowWaypoints();
    }

    private void FollowWaypoints()
    {
        if(_currentWaypoint != null)
        {
            if (_atTrafficLight && _trafficlightInQue != null)
            {
                _atTrafficLight = !Continue(_trafficlightInQue.GetState());
            }

            if (!Stop && !_atTrafficLight)
            {
                if (transform.position != _currentWaypoint.position)
                {
                    transform.position = Vector3.MoveTowards(transform.position, _currentWaypoint.position, movementSpeed * Time.deltaTime);
                    transform.rotation = Quaternion.Lerp(transform.rotation, _currentWaypoint.rotation, rotationSpeed * Time.deltaTime);
                }

                if (transform.position == _currentWaypoint.position)
                {
                    _currentWaypoint = GetNextWaypoint();
                    _waypointCount++;
                }
            }
        }
        else
        {
            DisposeMe();
        }
    }

    private bool Continue(int state)
    {
        if(_trafficType == TrafficType.busses)
        {
            if(state == 6 || state == 8)
            {
                return true;
            }
            else
            {
                if((int)_direction == state)
                {
                    return true;
                }
            }
        }
        else
        {
            if(state == 2)
            {
                return true;
            }
        }

        return false;
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        // trafficlight
        if (collision.isTrigger)
        {
            TrafficLight t = collision.gameObject.GetComponent<TrafficLight>();
            if (t != null && transform.rotation == t.transform.rotation)
            {
                _atTrafficLight = true;
                _trafficlightInQue = t;
                if (!inQue && !Continue(t.GetState()))
                {
                    if (_trafficType == TrafficType.busses)
                    {
                        inQue = true;
                        t.AddToQue((int)_direction);
                    }
                    else
                    {
                        inQue = true;
                        t.AddToQue();
                    }
                }
            }
        }
        // traffic object
        else
        {
            TrafficObject t = collision.gameObject.GetComponent<TrafficObject>();
            if(t != null)
            {
                Stop = true;

                if (t.GetInQue())
                {
                    _trafficlightInQue = t._trafficlightInQue;

                    if (_trafficType == TrafficType.busses)
                    {
                        inQue = true;
                        if (_trafficlightInQue != null)
                            _trafficlightInQue.AddToQue((int)_direction);
                    }
                    else
                    {
                        inQue = true;
                        if(_trafficlightInQue != null)
                            _trafficlightInQue.AddToQue();
                    }
                }
            }
        }
    }

    private void OnTriggerExit2D(Collider2D collision)
    {
        // trafficlight
        if (collision.isTrigger)
        {
            if (inQue)
            {
                TrafficLight t = collision.gameObject.GetComponent<TrafficLight>();
                if (t != null)
                {
                    _atTrafficLight = false;
                    if (_trafficType == TrafficType.busses)
                    {
                        inQue = false;
                        t.RemoveFromQue((int)_direction);
                    }
                    else
                    {
                        inQue = false;
                        t.RemoveFromQue();
                    }
                }
                _trafficlightInQue = null;
            }
        }
        // traffic object
        else
        {
            TrafficObject t = collision.gameObject.GetComponent<TrafficObject>();
            if (t != null)
            {
                Stop = false;
            }
        }
    }

    private void DisposeMe()
    {
        Stop = false;
        _lane = null;
        _waypointCount = 0;
        _currentWaypoint = null;
        inQue = false;
        Stop = false;
        gameObject.SetActive(false);
    }

    private Transform GetNextWaypoint()
    {
        return _lane.GetWaypoint(this);
    }

    public void InitTrafficObject(Lane lane)
    {
        _lane = lane;
        _direction = lane.direction;
        _trafficType = lane.trafficType;
        _currentWaypoint = _lane.GetWaypoint(this);
        _waypointCount++;
        transform.position = _currentWaypoint.position;
        transform.rotation = _currentWaypoint.rotation;
        _currentWaypoint = _lane.GetWaypoint(this);
        _waypointCount++;
        gameObject.SetActive(true);
    }

    public int GetWaypointCount()
    {
        return _waypointCount;
    }

    public bool GetInQue()
    {
        return inQue;
    }
}
