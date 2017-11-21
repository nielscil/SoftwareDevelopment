using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrainObject : MonoBehaviour
{

    public float movementSpeed;
    public float rotationSpeed;

    private TrainTrack Track { get; set; }
    private int _direction;
    
    private int waypointCount = 0;
    Transform CurrentWaypoint { get; set; }
    
    public void InitTrain(TrainTrack track, int direction)
    {

        Track = track;
        _direction = direction;
        CurrentWaypoint = Track.GetWaypoint(this);
        transform.position = CurrentWaypoint.position;
        transform.rotation = CurrentWaypoint.rotation;
        waypointCount++;
        gameObject.SetActive(true);
    }

	void FixedUpdate () {
        FollowWaypoints();
	}

    private void FollowWaypoints()
    {

        if (CurrentWaypoint != null)
        {

            if (transform.position == CurrentWaypoint.position)
            {
                if (waypointCount == Track.waypoints[_direction].Length)
                {
                    DisposeMe();
                }
                else
                {

                    CurrentWaypoint = Track.GetWaypoint(this);
                    if (CurrentWaypoint != null)
                        waypointCount++;
                }
            }

            if (CurrentWaypoint != null)
            {
                transform.position = Vector3.MoveTowards(transform.position, CurrentWaypoint.position, Time.deltaTime * movementSpeed);
                transform.rotation = Quaternion.Lerp(transform.rotation, CurrentWaypoint.rotation, Time.deltaTime * rotationSpeed);
            }
        }
        else if (waypointCount < Track.waypoints[_direction].Length)
        {
            CurrentWaypoint = Track.GetWaypoint(this);
            if (CurrentWaypoint != null)
                waypointCount++;
        }
    }

    private void DisposeMe()
    {
        Track = null;
        waypointCount = 0;
        CurrentWaypoint = null;
        gameObject.SetActive(false);
    }

    public int GetDirection()
    {
        return _direction;
    }

    public int GetWaypointCount()
    {
        return waypointCount;
    }
}
