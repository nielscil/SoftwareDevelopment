using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TrafficBehaviour : MonoBehaviour {

	public TrafficLane lane = null;
	bool inQue = false;
	// Use this for initialization
	void Start () {
	}

	void FixedUpdate () {
		if (lane != null) {
			switch(lane.CheckTrafficLightState ()) {
			case 0:
				if (!inQue) {
					inQue = true;
					lane.AddToQue ();
				}
				break;
			case 1:
				break;
			case 2:
				if (inQue) {
					inQue = false;
					lane.RemoveFromQue ();
					lane = null;
					this.gameObject.SetActive (false);
				}
				break;
			}
		}
	}
}
