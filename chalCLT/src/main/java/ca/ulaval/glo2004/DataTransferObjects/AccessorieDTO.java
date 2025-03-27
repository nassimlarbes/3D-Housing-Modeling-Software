/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.DataTransferObjects;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import java.awt.*;
import java.io.Serializable;
import java.util.UUID;
import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.wall.Point2d;


public class AccessorieDTO  implements Serializable {
    
    public final UUID Id;
    public Point2d Position;
    public Imperial Height;
    public Imperial Length;
    public boolean Type = false;
    
    public AccessorieDTO(Accessorie accessorie) {
        
        this.Id = accessorie.getId();
        this.Position = accessorie.getPosition();
        this.Height = accessorie.getHeight();
        this.Length = accessorie.getLength();
        if(accessorie.getClass().getSimpleName().equals("Door"))
            this.Type = true;
        
    }
    
    public AccessorieDTO(UUID Id, Point2d position, Imperial height, Imperial length) {
        
        this.Id = Id;
        this.Position = position;
        this.Height = height;
        this.Length = length;
    }
    
    
}
