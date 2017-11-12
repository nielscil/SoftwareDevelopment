using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ControllerUpdate
{
	public List<LightUpdate> Lights { get; set; }
}

public class LightUpdate
{
	public int Id { get; set; }
	public int Status { get; set; }
}