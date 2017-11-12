using UnityEngine;
using System;
using System.Text;
using System.Collections.Generic;
using Newtonsoft.Json;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

public delegate void ObjectReceived(string data);

public class MessageBroker : MonoBehaviour
{
	[SerializeField] private string server = null;
	[SerializeField] private string path = null;
	[SerializeField] private string username = null;
	[SerializeField] private string password = null;

	private readonly ConnectionFactory _factory = new ConnectionFactory();

	private IModel _controllerChannel;
	private IModel _simulatorChannel;

	private IConnection _controllerQueue;
	private IConnection _simulatorQueue;

	private const string _controllerQueueName = "controller";
	private const string _simulatorQueueName = "simulator";

	private EventingBasicConsumer _eventReceiver;

	private event ObjectReceived _received;
	public event ObjectReceived Received
	{
		add
		{
			_received += value;
		}
		remove
		{
			_received -= value;
		}
	}

	private void EventReceiver_Received(object sender, BasicDeliverEventArgs e)
	{
		string data = Encoding.UTF8.GetString(e.Body);
		if(_received != null)
			_received.Invoke(data);
	}

	private void CreateControllerQueue()
	{
		_controllerQueue = _factory.CreateConnection();
		_controllerChannel = _controllerQueue.CreateModel();
		_controllerChannel.QueueDeclare(_controllerQueueName, false, false, true, GetQueueArguments());
	}

	private void CreateSimulatorQueue()
	{
		_simulatorQueue = _factory.CreateConnection();
		_simulatorChannel = _simulatorQueue.CreateModel();
		_simulatorChannel.QueueDeclare(_simulatorQueueName, false, false, true, GetQueueArguments());

		_eventReceiver = new EventingBasicConsumer(_simulatorChannel);
		_eventReceiver.Received += EventReceiver_Received;
		_simulatorChannel.BasicConsume(_simulatorQueueName, false, _eventReceiver);
	}

	private Dictionary<string, object> GetQueueArguments()
	{
		var arguments = new Dictionary<string, object>();
		arguments.Add("x-message-ttl", 10000);
		return arguments;
	}

	public void SendTrafficUpdate(TrafficUpdate update)
	{
		var wrapper = new TrafficUpdateWrapper (update);
		string serialized = wrapper.ToJson ();
		byte[] data = Encoding.UTF8.GetBytes (serialized);
		_controllerChannel.BasicPublish ("", _controllerQueueName, null, data);
		Debug.Log (" > " + serialized);
	}

	void Start()
	{
		_factory.HostName = server;

		if(!string.IsNullOrEmpty(path))
		{
			_factory.VirtualHost = path;
		}

		if(!string.IsNullOrEmpty(username) && !string.IsNullOrEmpty(password))
		{
			_factory.UserName = username;
			_factory.Password = password;
		}

		CreateControllerQueue();
		CreateSimulatorQueue();
	}

	void OnDestroy()
	{
		if(_controllerChannel != null)
			_controllerChannel.Dispose();
		if(_simulatorChannel != null)
			_simulatorChannel.Dispose();
		if(_controllerQueue != null)
			_controllerQueue.Dispose();
		if(_simulatorQueue != null)
			_simulatorQueue.Dispose();

		_eventReceiver.Received -= EventReceiver_Received;
	}
}
