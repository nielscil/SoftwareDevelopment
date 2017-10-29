using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Text;
using UnityEngine;

public delegate void ObjectReceived(ControllerUpdate update);

public class ConnectionProvider: IDisposable
{
    private readonly ConnectionFactory _factory;
	private const string ControllerQueueName = "controller";
    private IConnection _controllerQueue;
    private IModel _controllerChannel;
	private const string SimulatorQueueName = "simulator";
    private IConnection _simulatorQueue;
    private IModel _simulatorChannel;

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

    public ConnectionProvider(string server, string path = null, string username = null, string password = null)
    {
        _factory = new ConnectionFactory();
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

    public void Dispose()
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

    private void CreateControllerQueue()
    {
        _controllerQueue = _factory.CreateConnection();
        _controllerChannel = _controllerQueue.CreateModel();
        _controllerChannel.QueueDeclare(ControllerQueueName, false, false, true, GetQueueArguments());
    }

    private void CreateSimulatorQueue()
    {
        _simulatorQueue = _factory.CreateConnection();
        _simulatorChannel = _simulatorQueue.CreateModel();
        _simulatorChannel.QueueDeclare(SimulatorQueueName, false, false, true, GetQueueArguments());

        _eventReceiver = new EventingBasicConsumer(_simulatorChannel);
        _eventReceiver.Received += EventReceiver_Received;
        _simulatorChannel.BasicConsume(SimulatorQueueName, false, _eventReceiver);
    }

    private Dictionary<string, object> GetQueueArguments()
    {
        var arguments = new Dictionary<string, object>();
        arguments.Add("x-message-ttl", 10000);
        return arguments;
    }

    private void EventReceiver_Received(object sender, BasicDeliverEventArgs e)
    {
		string data = Encoding.UTF8.GetString(e.Body);
		ControllerUpdate update = JsonUtility.FromJson<ControllerUpdate>(data);
		if(_received != null)
          	_received.Invoke(update);
		Debug.Log (" > received");
    }

	public void Send(TrafficUpdate update)
    {
		string serialized = JsonUtility.ToJson (update);
        byte[] data = Encoding.UTF8.GetBytes(serialized);
        _controllerChannel.BasicPublish("", ControllerQueueName, null, data);
		Debug.Log (" > " + serialized);
    }
}