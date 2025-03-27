/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ca.ulaval.glo2004.Domain.accessorie;

import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import ca.ulaval.glo2004.Domain.wall.Wall;

public class Door extends Accessorie {
    
    final static int HEIGHT = 88;
    final static int LENGTH = 38;
   

   public Door(Wall wall) {
       super(new Imperial(0,HEIGHT), new Imperial(0,LENGTH), new Point2d(0, 0), wall);
        //calculer la position de la porte (la porte est toujours au milieu du mur et le bas de la porte est toujours au sol)
        double pox = wall.getPosition().x + (wall.getLength().doubleValue() - (double) LENGTH) / 2;
        this.setPosition(new Point2d(pox,0));
   }
   
    @Override
    public void setPosition(Point2d position) {
        int DISTANCE_BETWEEN_WALL_AND_ACCESSORIE =  this.wall.getChalet().getMinSeparationDistance().intValue();
        position.y = wall.getExterieurStart().y + wall.getChalet().getHeight().doubleValue() - this.getHeight().doubleValue() - DISTANCE_BETWEEN_WALL_AND_ACCESSORIE;
        super.setPosition(position);
    }
   
   
}
   