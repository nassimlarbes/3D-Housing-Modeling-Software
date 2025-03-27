/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain;

import ca.ulaval.glo2004.Domain.Toit.*;
import ca.ulaval.glo2004.Domain.wall.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import ca.ulaval.glo2004.Domain.accessorie.Door;
import ca.ulaval.glo2004.Domain.accessorie.Window;
import ca.ulaval.glo2004.DataTransferObjects.AccessorieDTO;
import ca.ulaval.glo2004.DataTransferObjects.ChaletDTO;
import java.io.Serializable;


public class Chalet implements Serializable {
    private String name = "NewChalet";;
    private Imperial height;
    private Imperial lengthFacade;
    private Imperial lengthCote;
    private Imperial thickness ;
    private Imperial groove = new Imperial();
    private Imperial minSeparationDistance = new Imperial(0,3);
    private boolean frontIsPrincipal = true;
    private boolean direction_toit = true;
    private double angle = 15;
    private Toit toit;
    private List<Wall> walls = new ArrayList<>();
    private List<WallOfRoof> wallsOfRool = new ArrayList<>();
    private Color accessoriesColor = new Color(44, 62, 80);
    private Color frontColor = new Color(41, 128, 185);
    private Color sidesColor = new Color(243, 156, 18);
    private ChaletController controller;
    private Imperial oldHeight , oldLengthFacade, oldLengthCote;

    public Chalet(Imperial height, Imperial lenghtFacade, Imperial lenghtCote, Imperial thickness, ChaletController controller) {
        this.height = height;
        this.lengthFacade = lenghtFacade;
        this.lengthCote = lenghtCote;
        this.thickness = thickness;
        this.controller = controller;
        this.toit = new Toit(this);
        buildInitialWalls();
        updateOldValues();
    }

    public Chalet(ChaletController controller) {
        this.height = new Imperial(8, 0);
        this.lengthFacade = new Imperial(10, 0);
        this.lengthCote = new Imperial(10, 0);
        this.thickness = new Imperial(0, 12);
        this.controller = controller;
        this.toit = new Toit(this);
        buildInitialWalls();
        updateOldValues();
    }
    
    public Chalet(ChaletDTO chaletDTO, ChaletController controller) {
        this.height = chaletDTO.Height;
        this.lengthFacade = chaletDTO.LengthFacade;
        this.lengthCote = chaletDTO.LengthCote;
        this.thickness = chaletDTO.Thickness;
        this.groove = chaletDTO.Groove;
        this.minSeparationDistance = chaletDTO.MinSeparationDistance;
        this.frontIsPrincipal = chaletDTO.FrontIsPrincipal;
        this.accessoriesColor = chaletDTO.AccessoriesColor;
        this.frontColor = chaletDTO.FrontColor;
        this.sidesColor = chaletDTO.SidesColor;
        this.name = chaletDTO.Name;
        this.angle = chaletDTO.Angle;
        this.direction_toit = chaletDTO.DirectionToit;
        this.controller = controller;
        this.toit = new Toit(this);
        buildInitialWalls();
        updateOldValues();
        for (int i = 0; i < chaletDTO.Walls.size(); i++) {
            this.walls.get(i).setVisible(chaletDTO.Walls.get(i).visible);
            for (AccessorieDTO accessorieDTO : chaletDTO.Walls.get(i).Accessories) {
                Accessorie accessorie ;
                if(accessorieDTO.Type)
                    accessorie = new Door(this.walls.get(i));
                else
                    accessorie = new Window(this.walls.get(i));
                accessorie.setHeight(accessorieDTO.Height);
                accessorie.setLength(accessorieDTO.Length);
                accessorie.setPosition(new Point2d(accessorieDTO.Position.x, accessorieDTO.Position.y));
                this.walls.get(i).getAccessories().add(accessorie);
            }
        }
    }

    private void buildInitialWalls() {
        walls.add(new FrontWall(this));
        walls.add(new BackWall(this));
        walls.add(new LeftWall(this));
        walls.add(new RightWall(this));

        wallsOfRool.add(new PignonG(this, this.toit));
        wallsOfRool.add(new PignonD(this, this.toit));
        wallsOfRool.add(new Rallonge(this, this.toit));
        wallsOfRool.add(new DessusToit(this, this.toit));
    }


    public void setGroove(Imperial groove) {
        this.groove = groove;
        updateWallsAndToit();
    }


    public Imperial getHeight() {
        return height;
    }

    public void setHeight(Imperial height) {
        updateOldValues();
        this.height = height;
        updateWallsAndToit();
        updateAccoriePosition();
    }

    public Imperial getLengthFacade() {
        return lengthFacade;
    }

    public void setLengthFacade(Imperial lengthFacade) {
        updateOldValues();
        this.lengthFacade = lengthFacade;
        updateWallsAndToit();
        updateAccoriePosition();

    }

    public Imperial getLengthCote() {
        return lengthCote;
    }

    public void setLengthCote(Imperial lengthCote) {
        updateOldValues();
        this.lengthCote = lengthCote;
        updateWallsAndToit();
        updateAccoriePosition();

    }

    public Imperial getThickness() {
        return thickness;
    }

    public void setThickness(Imperial thickness) {
        this.thickness = thickness;
        updateWallsAndToit();

    }

    public Imperial getGroove() {
        return groove;
    }

    public Imperial getMinSeparationDistance() {
        return minSeparationDistance;
    }

    public void setMinSeparationDistance(Imperial minSeparationDistance) {
        this.minSeparationDistance = minSeparationDistance;
        updateWallsAndToit();
        for (Wall wall : walls) {
            wall.updateAccessories();
        }
    }

    public void setVisibility(boolean visible, Integer index) {
        this.walls.get(index).setVisible(visible);
    }

    public Color getAccessoriesColor() {
        return accessoriesColor;
    }

    public void setAccessoriesColor(Color accessoriesColor) {
        this.accessoriesColor = accessoriesColor;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public boolean isFrontIsPrincipal() {
        return frontIsPrincipal;
    }

    public void setFrontIsPrincipal(boolean frontIsPrincipal) {
        this.frontIsPrincipal = frontIsPrincipal;
        updateWallsAndToit();
    }

    public Color getFrontColor() {
        return frontColor;
    }

    public Color getSidesColor() {
        return sidesColor;
    }

    public void setFrontColor(Color frontColor) {
        this.frontColor = frontColor;
    }

    public void setSidesColor(Color sidesColor) {
        this.sidesColor = sidesColor;
    }

    public View getView() {
        return this.controller.getView();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateWallsPosition() {
        for (Wall wall : walls) {
            wall.calculatePosition();
        }
    }

    public boolean isDirection_toit() {
        return direction_toit;
    }

    public void setDirection_toit(boolean direction_toit) {
        this.direction_toit = direction_toit;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
        this.toit.updateToit();
    }

     private void updateWallsAndToit() {
        for (Wall wall : walls) {
            wall.calculatePosition();
        }
        this.toit.updateToit();
    }
    
    private void updateAccoriePosition(){
        for (Wall wall : walls) {
            wall.calulateRelativeAccessoriesPosition();
        }
    }

    public List<WallOfRoof> getWallsOfRool() {
        return wallsOfRool;
    }
    
    public void updateOldValues(){
        oldHeight = new Imperial(height);
        oldLengthFacade = new Imperial(lengthFacade);
        oldLengthCote = new Imperial(lengthCote);
    }
    public Imperial getOldHeight() {
        return oldHeight;
    }
    public Imperial getOldLengthFacade() {
        return oldLengthFacade;
    }
    public Imperial getOldLengthCote() {
        return oldLengthCote;
    }
}