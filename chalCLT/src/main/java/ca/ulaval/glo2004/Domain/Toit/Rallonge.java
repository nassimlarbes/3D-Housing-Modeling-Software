package ca.ulaval.glo2004.Domain.Toit;


import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.View;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import ca.ulaval.glo2004.Domain.wall.Polygone_;

import java.awt.*;

public class Rallonge extends WallOfRoof {

    public Rallonge(Chalet chalet, Toit toit) {
        super(chalet, toit);
        this.point_debut = new Point2d(0, 0);
    }

    @Override
    public Polygone_ draw() {
        Polygone_ p = new Polygone_();
        p.setColor(new Color(231, 76, 60));
        View view = this.chalet.getView();
        if (this.chalet.isFrontIsPrincipal()) {
            if (!this.chalet.isDirection_toit()) {
                switch (view) {
                    case BackView:
                        p.addPoints(toit.getRallonge_facade());
                        break;
                    case LeftView:
                        p.addPoints(toit.getRallonge_de_gauche_a_droite());
                        break;
                    case RightView:
                        p.addPoints(toit.getRallonge_de_droite_a_gauche());
                        break;
                }
            } else {
                switch (view) {
                    case FrontView:
                        p.addPoints(toit.getRallonge_facade());
                        break;
                    case LeftView:
                        p.addPoints(toit.getRallonge_de_droite_a_gauche());
                        break;
                    case RightView:
                        p.addPoints(toit.getRallonge_de_gauche_a_droite());
                        break;
                }
            }
        } else {
            if (!this.chalet.isDirection_toit()) {
                switch (view) {
                    case BackView:
                        p.addPoints(toit.getRallonge_de_droite_a_gauche());
                        break;
                    case FrontView:
                        p.addPoints(toit.getRallonge_de_gauche_a_droite());
                        break;
                    case RightView:
                        p.addPoints(toit.getRallonge_facade());
                        break;
                }
            } else {
                switch (view) {
                    case BackView:
                        p.addPoints(toit.getRallonge_de_gauche_a_droite());
                        break;
                    case FrontView:
                        p.addPoints(toit.getRallonge_de_droite_a_gauche());
                        break;
                    case LeftView:
                        p.addPoints(toit.getRallonge_facade());
                        break;
                }
            }
        }
        return p;
    }

    @Override
    public String exportSTL() {
       if(this.chalet.isFrontIsPrincipal()){
           if(!this.chalet.isDirection_toit()){
               return toit.exportRallonge_arriere_brut();
           }else{
               return toit.exportRallonge_front_brut();
           }
       }
         return "";
    }
    
    @Override
    public String exportToBrut() {
        if(this.chalet.isFrontIsPrincipal()){
            if(!this.chalet.isDirection_toit()){
                return toit.exportRallonge_arriere();
            }else{
                return toit.exportRallonge_front();
            }
        }
        return "";
    }
    
    @Override
    public String getTypeFileExportName() {
        return "R";
    }
}