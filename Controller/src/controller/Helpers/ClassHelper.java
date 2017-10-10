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
public class ClassHelper
{
    public static <T> T safeCast(Object o, Class<T> classType) 
    {
        return classType != null && classType.isInstance(o) ? classType.cast(o) : null;
    }
}
