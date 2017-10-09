using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System;
using System.Text;

namespace Simulator
{
    class Program
    {
        static void Main(string[] args)
        {
            using (var connectionProvider = new ConnectionProvider("localhost"))
            {
                connectionProvider.Received += ConnectionProvider_Received;
                connectionProvider.Send("hallo");
                Console.ReadLine();
                connectionProvider.Received -= ConnectionProvider_Received;
            }
        }

        private static void ConnectionProvider_Received(object obj)
        {
            Console.WriteLine(obj);
        }
    }
}
