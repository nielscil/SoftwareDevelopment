using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public interface ITrafficbject
{
    void UpdateInQue(bool inQue);
    bool IsInQue();
    int GetWaypointCount();
}
