/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.DataTransferObjects;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.wall.Wall;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo2004.Domain.Toit.Toit;
import ca.ulaval.glo2004.Domain.Toit.WallOfRoof;
import ca.ulaval.glo2004.Domain.ChaletController;
import java.io.Serializable;


public class ChaletDTO implements Serializable {

    public Imperial Height;
    public Imperial LengthFacade;
    public Imperial LengthCote;
    public Imperial Thickness ;
    public Imperial Groove ;
    public Imperial MinSeparationDistance ;
    public boolean FrontIsPrincipal ;
    public double Angle ;
    public boolean DirectionToit ;
    public Color AccessoriesColor;
    public Color FrontColor ;
    public Color SidesColor ;
    public String Name;
    public List<WallDTO> Walls = new ArrayList<>();

    public ChaletDTO(Chalet chalet) {
        this.Height = chalet.getHeight();
        this.LengthFacade = chalet.getLengthFacade();
        this.LengthCote = chalet.getLengthCote();
        this.Thickness = chalet.getThickness();
        this.Groove = chalet.getGroove();
        this.MinSeparationDistance = chalet.getMinSeparationDistance();
        this.FrontIsPrincipal = chalet.isFrontIsPrincipal();
        this.AccessoriesColor = chalet.getAccessoriesColor();
        this.FrontColor = chalet.getFrontColor();
        this.SidesColor = chalet.getSidesColor();
        this.Name = chalet.getName();
        this.Angle = chalet.getAngle();
        this.DirectionToit = chalet.isDirection_toit();
        for (Wall wall : chalet.getWalls()) {
            this.Walls.add(new WallDTO(wall));
        }
    }

}
