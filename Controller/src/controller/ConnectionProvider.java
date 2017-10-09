/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import controller.Helpers.StringHelper;
import java.io.Closeable;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Observable;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Niels
 */
public class ConnectionProvider extends Observable implements Closeable
{
    private final String ControllerQueueName = "Controller";
    private final String SimulatorQueueName = "Simulator";
    
    private final ConnectionFactory _factory;
    private Connection _controllerQueue;
    private Channel _controllerChannel;
    private Connection _simulatorQueue;
    private Channel _simulatorChannel;
    
    public ConnectionProvider(String host, String groupId, String username, String password) throws IOException, TimeoutException
    {
        _factory = new ConnectionFactory();
        _factory.setHost(host);
        if(!StringHelper.IsNullOrWhitespace(groupId))
        {
            _factory.setVirtualHost(groupId);
        }
        
        if(!StringHelper.IsNullOrWhitespace(username) && !StringHelper.IsNullOrWhitespace(password))
        {
            _factory.setUsername(username);
            _factory.setPassword(password);
        }
        
        CreateControllerQueue();
        CreateSimulatorQueue();
    }
    
    public void Send(Object obj) throws UnsupportedEncodingException, IOException
    {
        String serialzed = "Controller: 5";
        byte[] bytes = serialzed.getBytes("UTF-8");
        _simulatorChannel.basicPublish("", SimulatorQueueName, null, bytes);
    }
    
    private void CreateControllerQueue() throws IOException, TimeoutException
    {
        _controllerQueue = _factory.newConnection();
        _controllerChannel = _controllerQueue.createChannel();
        _controllerChannel.queueDeclare(ControllerQueueName, false, false, true, null);
        Consumer consumer = new DefaultConsumer(_controllerChannel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException
            {
                String data = new String(body,"UTF-8");
                Object obj  = data; //deserialize
                setChanged();
                notifyObservers(obj);
            }
        };
        _controllerChannel.basicConsume(ControllerQueueName, false, consumer);
    }
    
    private void CreateSimulatorQueue() throws IOException, TimeoutException
    {
        _simulatorQueue = _factory.newConnection();
        _simulatorChannel = _simulatorQueue.createChannel();
        _simulatorChannel.queueDeclare(SimulatorQueueName, false, false, true, null);
    }

    @Override
    public void close() throws IOException
    {
        try
        {
            if(_simulatorChannel != null)
            {
                _simulatorChannel.close();
                _simulatorChannel = null;
            }
            
            if(_simulatorQueue != null)
            {
                _simulatorQueue.close();
                _simulatorQueue = null;
            }
            
            if(_controllerChannel != null)
            {
                _controllerChannel.close();
                _controllerChannel = null;
            }
            
            if(_controllerQueue != null)
            {
                _controllerQueue.close();
                _controllerQueue = null;
            }
            
            deleteObservers();
        }
        catch(Exception e)
        {
            
        }
    }
}
