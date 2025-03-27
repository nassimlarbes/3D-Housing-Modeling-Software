/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain.accessorie;

import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import ca.ulaval.glo2004.Domain.wall.Polygone_;
import ca.ulaval.glo2004.Domain.wall.Wall;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.UUID;


public abstract class Accessorie {
    private final UUID id;
    private Point2d position;
    private Imperial height;
    private Imperial length;
    protected boolean selectionStatus;
    protected Wall wall;


    public Accessorie(Imperial height, Imperial length, Point2d position, Wall wall) {
        this.id = UUID.randomUUID();
        this.height = height;
        this.length = length;
        this.position = position;
        this.selectionStatus = false;
        this.wall = wall;
    }

    public Point2d getPosition() {
        return position;
    }

    public void setPosition(Point2d position) {
        int DISTANCE_BETWEEN_WALL_AND_ACCESSORIE =  this.wall.getChalet().getMinSeparationDistance().intValue();

        //vérfier si la porte ne déborde pas du mur
        if (position.x < wall.getExterieurStart().x + DISTANCE_BETWEEN_WALL_AND_ACCESSORIE) {
            position.x = wall.getExterieurStart().x + DISTANCE_BETWEEN_WALL_AND_ACCESSORIE;
        } else if (position.x > wall.getExterieurStart().x + wall.getLength().doubleValue() - this.getLength().doubleValue() -  DISTANCE_BETWEEN_WALL_AND_ACCESSORIE) {
            position.x = (int) (wall.getExterieurStart().x + wall.getLength().doubleValue() - this.getLength().doubleValue() -  DISTANCE_BETWEEN_WALL_AND_ACCESSORIE);
        }

        if (position.y < wall.getExterieurStart().y + DISTANCE_BETWEEN_WALL_AND_ACCESSORIE)
            position.y = wall.getExterieurStart().y + DISTANCE_BETWEEN_WALL_AND_ACCESSORIE ;
        else if (position.y > wall.getExterieurStart().y + wall.getChalet().getHeight().doubleValue() - this.getHeight().doubleValue() -  DISTANCE_BETWEEN_WALL_AND_ACCESSORIE )
            position.y = (int) (wall.getExterieurStart().y + wall.getChalet().getHeight().doubleValue() - this.getHeight().doubleValue() -  DISTANCE_BETWEEN_WALL_AND_ACCESSORIE);

        this.position = position;
    }

    public Imperial getHeight() {
        return height;
    }

    public void setHeight(Imperial height) {
        int DISTANCE_BETWEEN_WALL_AND_ACCESSORIE =  this.wall.getChalet().getMinSeparationDistance().intValue();

        if(height.doubleValue() < 5)
            this.height = new Imperial(0 , 5);
        else {
            double h = height.doubleValue();
            double h2 = wall.getChalet().getHeight().doubleValue() - 2 * DISTANCE_BETWEEN_WALL_AND_ACCESSORIE;
            if (h > h2) {
                this.height = new Imperial(wall.getChalet().getHeight());
                this.height.setInch(this.height.getInch() - 2 * DISTANCE_BETWEEN_WALL_AND_ACCESSORIE);
            } else {
                this.height = new Imperial(height);
            }
        }
        this.setPosition(new Point2d(this.position.x, this.position.y));
    }

    public Imperial getLength() {
        return length;
    }

    public void setLength(Imperial length) {
        int DISTANCE_BETWEEN_WALL_AND_ACCESSORIE =  this.wall.getChalet().getMinSeparationDistance().intValue();
        if(length.doubleValue() < 5)
            this.length = new Imperial(0 , 5);
        else {
            double l = length.doubleValue();
            double l2 = wall.getLength().doubleValue() - 2 * DISTANCE_BETWEEN_WALL_AND_ACCESSORIE;
            if (l > l2) {
                this.length = new Imperial(wall.getLength());
                this.length.setInch(this.length.getInch() - 2 * DISTANCE_BETWEEN_WALL_AND_ACCESSORIE);
            } else {
                this.length = new Imperial(length);
            }
        }

        //recalculer la position de la porte
        this.setPosition(new Point2d(this.position.x, this.position.y));
    }


    public UUID getId() {
        return id;
    }

    public boolean isSelectionStatus() {
        return selectionStatus;
    }

    public void setSelectionStatus(boolean selectionStatus) {
        this.selectionStatus = selectionStatus;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
    }

    public Polygone_ draw() {
        Polygone_ view = new Polygone_(this.wall.getAccessoriesColor());
        view.addPoint(this.position.x, this.position.y);
        view.addPoint(this.position.x + this.length.doubleValue(), this.position.y);
        view.addPoint(this.position.x + this.length.doubleValue(), this.position.y + this.height.doubleValue());
        view.addPoint(this.position.x, this.position.y + this.height.doubleValue());
        return view;
    }

    public boolean isValidate() {
        //parcourir tous les accessoires du mur, si un accessoire est en collision avec l'accessoire courant, alors l'accessoire courant n'est pas valide
        List<Accessorie> accessories = wall.getAccessories();
        Polygone_ view_ = this.draw();
        Area view = new Area(view_);
        Point2D.Double viewCenter = view_.getCenter();

        //avoir l'index de l'accessoire courant (car le vériication de collision ne doit pas se faire que sur les accessoires qui sont aprés l'accessoire)

        int index = accessories.indexOf(this);
        for (int i = index + 1; i < accessories.size(); i++) {
            Accessorie accessory = accessories.get(i);
            Polygone_ accessoryView_ = accessory.draw();
            Area accessoryView = new Area(accessoryView_);
            if (view.intersects(accessoryView.getBounds2D()))
                return false;

            double delta_x = Math.abs(Math.abs(accessoryView_.getCenter().x - viewCenter.x) - (double) (accessoryView_.getBounds().width + view_.getBounds().width) / 2);
            double delta_y = Math.abs(Math.abs(accessoryView_.getCenter().y - viewCenter.y) - (double) (accessoryView_.getBounds().height + view_.getBounds().height) / 2);
            if(delta_x < wall.getChalet().getMinSeparationDistance().doubleValue() || delta_y < wall.getChalet().getMinSeparationDistance().doubleValue())
                return false;
        }
        return true;
    }
    
    public void calculateRelativePosition(Imperial oldHeight, Imperial oldLength) {
        int DISTANCE_BETWEEN_WALL_AND_ACCESSORIE =  this.wall.getChalet().getMinSeparationDistance().intValue();
        
        //calculer la position de l'accessoire par rapport au mur
        double x = this.position.x * (this.wall.getLength().doubleValue() - 2 * DISTANCE_BETWEEN_WALL_AND_ACCESSORIE) / oldLength.doubleValue();
        double y = this.position.y * (this.wall.getChalet().getHeight().doubleValue() - 2 * DISTANCE_BETWEEN_WALL_AND_ACCESSORIE) / oldHeight.doubleValue();
        this.setPosition(new Point2d(x, y));
    }
}