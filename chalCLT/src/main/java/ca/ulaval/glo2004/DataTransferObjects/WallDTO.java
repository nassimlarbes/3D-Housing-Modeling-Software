/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.DataTransferObjects;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import ca.ulaval.glo2004.Domain.wall.Wall;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo2004.Domain.wall.Point2d;


public class WallDTO implements Serializable {

    public Chalet chalet;
    public List<AccessorieDTO> Accessories;
    public Point2d position;
    public boolean visible = true;

    public WallDTO(Wall wall) {
        this.chalet = wall.getChalet();
        this.position = wall.getPosition();
        this.visible = wall.isVisible();
        this.Accessories = new ArrayList<>();
        for (Accessorie accessorie : wall.getAccessories()){
            this.Accessories.add(new AccessorieDTO(accessorie));
        }
    }
}
