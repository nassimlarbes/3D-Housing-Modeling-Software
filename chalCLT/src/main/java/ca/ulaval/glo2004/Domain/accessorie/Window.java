/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain.accessorie;

import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import java.awt.*;
import ca.ulaval.glo2004.Domain.wall.Wall;

public class Window extends Accessorie {
    
    public Window(Wall wall){
        super(new Imperial(0,30), new Imperial(0,30), new Point2d(10, 10), wall);
    }
}


