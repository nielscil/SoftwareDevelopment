using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrainObject : MonoBehaviour, ITrafficObject
{
    public float movementSpeed;
    public float rotationSpeed;

    public bool Stop { get; set; }

    private bool inQue = false;
    public bool _atTrafficLight = false;
    private TrafficLight _trafficlightInQue { get; set; }
    private TrainTrack _track { get; set; }
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
            if (_atTrafficLight)
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
        if(state == 2)
        {
            return true;
        }
    
        return false;
    }

    private void CheckTrafficLight()
    {
        if (_trafficlightInQue != null)
        {
            if (!Continue(_trafficlightInQue.GetState()))
            {
                _atTrafficLight = true;
                inQue = true;
            }
        }
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        // trafficlight
        if (collision.isTrigger)
        {
            TrafficLight t = collision.gameObject.GetComponent<TrafficLight>();
            if (t != null && transform.rotation == t.transform.rotation)
            {
                if (!inQue && !Continue(t.GetState()))
                {
                    _atTrafficLight = true;
                    inQue = true;
                    _trafficlightInQue = t;
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
                    inQue = false;
                }
                _trafficlightInQue = null;
            }
        }
    }

    private void DisposeMe()
    {
        _track.RemoveFromQue();
    }

    private Transform GetNextWaypoint()
    {
        return _track.GetWaypoint(this);
    }

    public void InitTrainObject(TrainTrack track)
    {
        Debug.Log("train initialized");
        _track = track;
        _currentWaypoint = _track.GetWaypoint(this);
        _waypointCount++;
        transform.position = _currentWaypoint.position;
        transform.rotation = _currentWaypoint.rotation;
        _currentWaypoint = _track.GetWaypoint(this);
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
