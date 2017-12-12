using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficObject : MonoBehaviour, ITrafficObject
{
    public float movementSpeed;
    public float rotationSpeed;

    public bool Stop = false;

    public bool inQue = false;
    public bool _atTrafficLight = false;
    public Direction _direction { get; set; }
    public TrafficType _trafficType { get; set; }
    public TrafficLight _trafficlightInQue { get; set; }
    private Lane _lane { get; set; }
    private int _waypointCount = 0;
    private Transform _currentWaypoint = null;

    private void FixedUpdate()
    {
        FollowWaypoints();
    }

    private void FollowWaypoints()
    {
        if(_currentWaypoint != null )
        {
            
            if (_atTrafficLight && Continue(_trafficlightInQue.GetState()) )
            {
                if (_trafficType == TrafficType.busses)
                {
                    _trafficlightInQue.RemoveFromQue((int)_direction);
                }
                else
                {
                    _trafficlightInQue.RemoveFromQue();
                }

                inQue = false;
                _atTrafficLight = false;
                _trafficlightInQue = null;
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
        if (collision.GetComponent<TrafficLight>() != null && collision.gameObject.transform.rotation == transform.rotation && collision.GetComponent<TrafficLight>().ID != 601)
        {
            _trafficlightInQue = collision.GetComponent<TrafficLight>();
            _atTrafficLight = true;
            if (!inQue)
            {
                if (!Continue(_trafficlightInQue.GetState()))
                {
                    if (_trafficType == TrafficType.busses)
                    {
                        _trafficlightInQue.AddToQue((int)_direction);
                    }
                    else
                    {
                        _trafficlightInQue.AddToQue();
                    }
                    inQue = true;
                }                
            }
            else
            {
                if (!Continue(_trafficlightInQue.GetState()))
                {
                    _atTrafficLight = true;
                }
            }
        }
        else if (collision.GetComponent<TrafficObject>() != null && !collision.isTrigger)
        {
            Stop = true;
            if (collision.GetComponent<TrafficObject>().inQue && collision.GetComponent<TrafficObject>()._trafficlightInQue.ID != 601)
            {
                _trafficlightInQue = collision.GetComponent<TrafficObject>()._trafficlightInQue;
                if (!inQue)
                {
                    if (_trafficType == TrafficType.busses)
                    {
                        _trafficlightInQue.AddToQue((int)_direction);
                    }
                    else
                    {
                        _trafficlightInQue.AddToQue();
                    }

                    inQue = true;
                }
            }
        }
        else if(collision.GetComponent<TrafficLight>() != null && collision.GetComponent<TrafficLight>().ID == 601)
        {
            _trafficlightInQue = collision.GetComponent<TrafficLight>();
            _atTrafficLight = true;
            if (!inQue)
            {
                if (!Continue(_trafficlightInQue.GetState()))
                {
                    inQue = true;
                }
            }
            else
            {
                if (!Continue(_trafficlightInQue.GetState()))
                {
                    _atTrafficLight = true;
                }
            }
        }
    }

    private void OnTriggerExit2D(Collider2D collision)
    {
        if (collision.GetComponent<TrafficObject>() != null)
        {
            Stop = false;
        }
    }

    private void DisposeMe()
    {
        _lane = null;
        _waypointCount = 0;
        _currentWaypoint = null;
        _atTrafficLight = false;
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
