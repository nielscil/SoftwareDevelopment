/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.Helpers.StringHelper;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 *
 * @author Niels
 */
public class Controller implements Observer
{
    private ConnectionProvider _provider;
    public Controller(String host, String groupId, String username, String password)
    {
        try
        {
            _provider = new ConnectionProvider("localhost", groupId, username, password);
            _provider.addObserver(this);
        }
        catch(Exception e)
        {
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        String host = args.length > 0 ? args[0] : GetUserInput("What is the host address");
        String groupId = args.length > 1 ? args[1] : GetUserInput("What is the groupId");
        String username = args.length > 2 ? args[2] : GetUserInput("What is the username");
        String password = args.length > 3 ? args[3] : GetUserInput("What is the password");
        
        new Controller(host, groupId, username, password);
    }
    
    private static String GetUserInput(String text)
    {
        try
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println(text);
            String input = scanner.nextLine();
            
            System.out.println("Are you sure You want to use '" + input + "' (Y/N)?");
            
            if(scanner.nextLine().toUpperCase().equals("N"))
            {
                return GetUserInput(text);
            }
            return input;
        }
        catch(Exception e)
        {
            
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        System.out.print(arg);
        try
        {
            _provider.Send(arg);
        } catch (IOException ex)
        {
             
        }
    }
    
}
