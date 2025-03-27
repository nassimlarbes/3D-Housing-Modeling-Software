/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain.wall;

import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import ca.ulaval.glo2004.Domain.Chalet;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Wall implements Serializable {
    public static final double half = 0.5;
    protected Chalet chalet;
    protected Point2d position;
    protected boolean visible = true;
    private List<Accessorie> accessories;
    //comment

    public Wall(Chalet chalet) {
        this.chalet = chalet;
        this.accessories = new ArrayList<>();
        this.position = new Point2d(0, 0);
    }

    /**
     * Elle retourne la liste des points qui representent la vue exterieure du mur
     * cette vue en 2D est composee de 4 points qui sont les 4 coins du mur
     *
     * @return liste des points qui representent la vue exterieure du mur
     */
    public Polygone_ getExteriorView() {
        Polygone_ exteriorView = new Polygone_();
        Point2d post_start = this.getExterieurStart();
        exteriorView.setColor(this.getColor());
        exteriorView.addPoint(post_start.x, post_start.y);
        exteriorView.addPoint(post_start.x + this.getLength().doubleValue(), post_start.y);
        exteriorView.addPoint(post_start.x + this.getLength().doubleValue(), post_start.y + this.chalet.getHeight().doubleValue());
        exteriorView.addPoint(post_start.x, post_start.y + this.chalet.getHeight().doubleValue());
        return exteriorView;
    }


    /**
     * Elle retourne la liste des points qui representent la vue de côté du mur
     * cette vue en 2D est composee de 4 points qui sont les 4 coins du mur
     *
     * @return liste des points qui representent la vue de côté du mur
     */
    public Polygone_ getSideView(){
        Point2d post_start = this.getSideStart();
        Polygone_ sideView = new Polygone_();
        double moitié = this.chalet.getThickness().doubleValue()/2;
        sideView.setColor(this.getColor());
        sideView.addPoint(post_start.x, post_start.y);
        sideView.addPoint(post_start.x + moitié, post_start.y);
        sideView.addPoint(post_start.x + moitié, post_start.y + this.chalet.getHeight().doubleValue());
        sideView.addPoint(post_start.x, post_start.y + this.chalet.getHeight().doubleValue());
        return sideView;
    }

    /**
     * cette methode retourne les points de la vue de dessus
     * cette vue de dessus est une liste de points qui represente les points de la vue de dessus
     * elle contient 8 points
     * @return
     */
    public Polygone_ getTopView() {
        Polygone_ topView = new Polygone_(this.getColor());
        for (Point2d Point2d : this.getLimitPoints()) {
            topView.addPoint(this.getPosition().x + Point2d.x, this.getPosition().y + Point2d.y);
        }
        return topView;
    }

    public Imperial getHeight() {
        return this.chalet.getHeight();
    }
    public Imperial getThickness() {
        return this.chalet.getThickness();
    }
    public Chalet getChalet() {
        return chalet;
    }
    public void setChalet(Chalet chalet) {
        this.chalet = chalet;
    }
    public Point2d getPosition() {
        return position;
    }
    public void setPosition(Point2d position) {
        this.position = position;
    }
    public boolean isVisible() {
        return visible;
    }
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    public boolean getFrontIsPrincipal(){
        return this.chalet.isFrontIsPrincipal();
    }
    public List<Accessorie> getAccessories() {
        return accessories;
    }
    public void setAccessories(List<Accessorie> accessories) {
        this.accessories = accessories;
    }
    public Imperial getMinSeparationDistance() {
        return chalet.getMinSeparationDistance();
    }
    public Color getAccessoriesColor() {
        return chalet.getAccessoriesColor();
    }

    public abstract Imperial getLength();
    public abstract Point2d getExterieurStart();
    public abstract Point2d getSideStart();
    public abstract Polygone_ getView();
    public abstract void calculatePosition();
    public abstract Color getColor();
    public abstract Point2d[] getLimitPoints();
    public abstract String getTypeFileExportName();
    public abstract String getSTLFini();
    public abstract String getSTLBrut();
    public abstract String getSTLRetrait();
    public abstract void calulateRelativeAccessoriesPosition();


    public void updateAccessories(){
        for (Accessorie accessory : getAccessories()){
            accessory.setPosition(new Point2d(accessory.getPosition().x, accessory.getPosition().y));
        }
    }
    public List<Polygone_> drawAccessories() {
        List<Polygone_> accessoriesPolygone = new ArrayList<>();
        for (Accessorie accessory : getAccessories()) {
            accessoriesPolygone.add(accessory.draw());
        }
        return accessoriesPolygone;
    }

    public void exportToSTL_Fini(String path) {
        String fileExportName = path + "/" + chalet.getName() + "_Fini_" + getTypeFileExportName() + ".stl";
        calculatePosition();
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(fileExportName)))) {
            // Écrire l'en-tête du fichier STL
            writer.println("solid Wall");
            //exporter le premier mur
            writer.println(getSTLFini());
            // Écrire la fin du fichier STL
            writer.println("endsolid Wall");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToSTL_Brut(String path) {

        String fileExportName =path + "/" +chalet.getName() + "_Brut_" + getTypeFileExportName() + ".stl";
        calculatePosition();
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(fileExportName)))) {
            writer.println("solid Wall");
            writer.println( getSTLBrut());
            writer.println("endsolid Wall");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToSTL_Retrait(String path) {
        String fileExportName = path + "/" + chalet.getName() + "_Retrait_" + getTypeFileExportName() + ".stl";
        calculatePosition();
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(fileExportName)))) {
            writer.println("solid Wall");
            String stl = getSTLRetrait();
            writer.println(stl);
            writer.println("endsolid Wall");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}