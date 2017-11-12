/*
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BlackBox
{
	private readonly ConnectionProvider _connectionProvider;
	private readonly Stack<ControllerUpdate> _received;
	public static BlackBox Instance { get; private set; }

	private BlackBox (string server, string path = null, string username = null, string password = null)
	{
		_connectionProvider = new ConnectionProvider (server, path, username, password);
		_connectionProvider.Received += ConnectionProvider_Received;
		_received = new Stack<ControllerUpdate>();
	}

	public static void Init(string server, string path = null, string username = null, string password = null)
	{
		if (Instance == null)
		{
			Instance = new BlackBox (server, path, username, password);
		}
	}

	private void ConnectionProvider_Received(ControllerUpdate update)
	{
		_received.Push (update); //TODO: make this threadsafe
	}

	public ControllerUpdate GetUpdate()
	{
		if (_received != null && _received.Count > 0)
			return _received.Pop (); //TODO: make this threadsafe
		else
			return null;
	}

	public void SendUpdate(TrafficUpdate update)
	{
		_connectionProvider.Send (update);
	}

	public void Dispose()
	{
		_connectionProvider.Received -= ConnectionProvider_Received;
		_connectionProvider.Dispose ();
		Instance = null;
	}
		
}
*/