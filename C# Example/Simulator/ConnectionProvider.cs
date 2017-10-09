using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Collections.Generic;
using System.Text;

namespace Simulator
{
    public delegate void ObjectReceived(object obj);

    public class ConnectionProvider: IDisposable
    {
        private const string ControllerQueueName = "Controller";
        private const string SimulatorQueueName = "Simulator";
        private readonly ConnectionFactory _factory;
        private IConnection _controllerQueue;
        private IModel _controllerChannel;
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

        public ConnectionProvider(string host, string groupId = null, string username = "softdev", string password = "softdev")
        {
            _factory = new ConnectionFactory();
            _factory.HostName = host;

            if(!string.IsNullOrWhiteSpace(groupId))
            {
                _factory.VirtualHost = groupId;
            }

            if(!string.IsNullOrWhiteSpace(username) && !string.IsNullOrWhiteSpace(password))
            {
                _factory.UserName = username;
                _factory.Password = password;
            }

            CreateControllerQueue();
            CreateSimulatorQueue();
        }

        public void Dispose()
        {
            _controllerChannel?.Dispose();
            _simulatorChannel?.Dispose();
            _controllerQueue?.Dispose();
            _simulatorQueue?.Dispose();
            _eventReceiver.Received -= EventReceiver_Received;
        }

        private void CreateControllerQueue()
        {
            _controllerQueue = _factory.CreateConnection();
            _controllerChannel = _controllerQueue.CreateModel();
            _controllerChannel.QueueDeclare(ControllerQueueName, false, false, true, null);
        }

        private void CreateSimulatorQueue()
        {
            _simulatorQueue = _factory.CreateConnection();
            _simulatorChannel = _simulatorQueue.CreateModel();
            _simulatorChannel.QueueDeclare(SimulatorQueueName, false, false, true, null);
            _eventReceiver = new EventingBasicConsumer(_simulatorChannel);
            _eventReceiver.Received += EventReceiver_Received;
            _simulatorChannel.BasicConsume(SimulatorQueueName, false, _eventReceiver);
        }

        private void EventReceiver_Received(object sender, BasicDeliverEventArgs e)
        {
            string data = Encoding.UTF8.GetString(e.Body);
            object obj = data;//deserialize
            _received?.Invoke(obj);
        }

        public void Send(object obj)
        {
            string serialized = "Simulator groepje"; // serialize with json parser
            byte[] data = Encoding.UTF8.GetBytes(serialized);
            _controllerChannel.BasicPublish("", ControllerQueueName, null, data);
        }
    }
}
