using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public interface TrafficBehaviour{

    void Start();
	void Drive ();
	void GetWaypoint();
	void AtTrafficLight ();
	void ReleaseMe();
}
