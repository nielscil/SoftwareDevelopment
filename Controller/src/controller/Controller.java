/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Niels
 */
public class Controller implements Observer
{
    private ConnectionProvider _provider;
    public Controller()
    {
        try
        {
            _provider = new ConnectionProvider("localhost", null);
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
        new Controller();
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
            //Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
