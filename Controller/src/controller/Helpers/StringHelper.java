/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.Helpers;

/**
 *
 * @author Niels
 */
public class StringHelper
{
    public static boolean IsNullOrEmpty(String str)
    {
        return str == null || str.equals("");
    }
    
    public static boolean IsNullOrWhitespace(String str)
    {
        return IsNullOrEmpty(str) || str.equals(" ");
    }
}
