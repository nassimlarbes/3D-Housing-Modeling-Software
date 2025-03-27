package ca.ulaval.glo2004.Domain.Toit;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import ca.ulaval.glo2004.Domain.wall.Polygone_;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class WallOfRoof {
    Point2d point_debut;
    Toit toit;
    Chalet chalet;

    public WallOfRoof(Chalet chalet, Toit toit) {
        this.point_debut = new Point2d(0,0);
        this.toit = toit;
        this.chalet = chalet;
    }

    public abstract Polygone_ draw();

    public abstract String exportSTL();
    public abstract String exportToBrut();
    public abstract String getTypeFileExportName();

    public void exportToSTL_Fini(String path) {
        String fileExportName = path + "/" + chalet.getName() + "_Fini_" + getTypeFileExportName() + ".stl";
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(fileExportName)))) {
            // Écrire l'en-tête du fichier STL
            writer.println("solid Wall");
            //exporter le premier mur
            writer.println(exportSTL());
            // Écrire la fin du fichier STL
            writer.println("endsolid Wall");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void exportToSTL_Brut(String path) {
        String fileExportName = path + "/" + chalet.getName() + "_Brut_" + getTypeFileExportName() + ".stl";
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(fileExportName)))) {
            // Écrire l'en-tête du fichier STL
            writer.println("solid Wall");
            //exporter le premier mur
            writer.println(exportToBrut());
            // Écrire la fin du fichier STL
            writer.println("endsolid Wall");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}