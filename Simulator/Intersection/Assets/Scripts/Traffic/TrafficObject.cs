using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficObject : MonoBehaviour, ITrafficbject
{
    public float movementSpeed;
    public float rotationSpeed;

    private bool InQue { get; set; }
    private Lane Lane { get; set; }
    private bool WaitingForTraffic { get; set; }
    private int waypointCount = 0;
    Transform CurrentWaypoint { get; set; }

    private void FixedUpdate()
    {
        FollowWaypoints();
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (!collision.isTrigger)
        {
            WaitingForTraffic = true;
        }
    }

    private void OnTriggerExit2D(Collider2D collision)
    {
        if (!collision.isTrigger)
        {
            WaitingForTraffic = false;
        }
    }

    private void DisposeMe()
    {
        InQue = false;
        Lane = null;
        waypointCount = 0;
        CurrentWaypoint = null;
        gameObject.SetActive(false);
    }

    private void FollowWaypoints()
    {

        if (CurrentWaypoint != null)
        {
            
            if(transform.position == CurrentWaypoint.position)
            {
                if(waypointCount == Lane.waypoints.Length)
                {
                    DisposeMe();
                }
                else
                {

                    CurrentWaypoint = Lane.GetWaypoint(this);
                    if(CurrentWaypoint != null)
                        waypointCount++;
                }
            }

            if (!WaitingForTraffic && CurrentWaypoint != null)
            {
                transform.position = Vector3.MoveTowards(transform.position, CurrentWaypoint.position, Time.deltaTime * movementSpeed);
                transform.rotation = Quaternion.Lerp(transform.rotation, CurrentWaypoint.rotation, Time.deltaTime * rotationSpeed);
            }
        }
        else if(waypointCount < Lane.waypoints.Length)
        {
            CurrentWaypoint = Lane.GetWaypoint(this);
            if (CurrentWaypoint != null)
                waypointCount++;
        }
    }

    public void InitTrafficObject(Lane lane)
    {
        Lane = lane;
        CurrentWaypoint = Lane.GetWaypoint(this);
        waypointCount++;

        transform.position = CurrentWaypoint.position;
        transform.rotation = CurrentWaypoint.rotation;

        gameObject.SetActive(true);
    }
    
    public void UpdateInQue(bool inQue)
    {
        InQue = inQue;
    }
    
    public int GetWaypointCount()
    {
        return waypointCount;
    }
    
    public bool IsInQue()
    {
        return InQue;
    }
}
