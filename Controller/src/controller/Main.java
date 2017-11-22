/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.Helpers.JsonHelper;
import controller.Helpers.StringHelper;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Niels
 */
public class Main
{
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {      
        Intersection intersection = new Intersection();
        String test = JsonHelper.instance().Serialize(intersection.GetSerializable());
        
        String host = args.length > 0 ? args[0] : GetUserInput("What is the host address");
        String username = args.length > 2 ? args[2] : GetUserInput("What is the username");
        String password = args.length > 3 ? args[3] : GetUserInput("What is the password");
        boolean useGroup = !StringHelper.IsNullOrWhitespace(username) && !StringHelper.IsNullOrWhitespace(password);
        
        new Controller(host, username, password, useGroup);
    }
    
    private static String GetUserInput(String text)
    {
        try
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println(text);
            String input = scanner.nextLine();
            
            System.out.println("Are you sure You want to use '" + input + "' (Y/N)?");
            
            if(!scanner.nextLine().toUpperCase().equals("Y"))
            {
                return GetUserInput(text);
            }
            return input;
        }
        catch(Exception e)
        {
            System.err.println(e.toString());
        }
        return null;
    }
}
