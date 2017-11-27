/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Niels
 */
public class SerializeableIntersection
{
    public List<SerializeableLight> Lights;
    
    public SerializeableIntersection(List<Light> lights)
    {
        Lights = lights.stream()
                .filter(l -> l.Id != LightNumber.TrainSignalEast_502)
                .map(obj -> new SerializeableLight(obj.Id, obj.Status))
                .collect(Collectors.toList());
    }
}
