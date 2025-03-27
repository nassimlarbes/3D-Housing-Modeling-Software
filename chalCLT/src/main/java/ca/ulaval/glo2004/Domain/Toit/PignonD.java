package ca.ulaval.glo2004.Domain.Toit;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.View;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import ca.ulaval.glo2004.Domain.wall.Polygone_;
import ca.ulaval.glo2004.Domain.wall.Wall;

import java.awt.*;

public class PignonD extends WallOfRoof {
    public PignonD(Chalet chalet, Toit toit) {
        super(chalet, toit);
        this.point_debut = new Point2d(this.chalet.getThickness().doubleValue() * Wall.half,0);
    }

    @Override
    public Polygone_ draw() {
        View view = this.chalet.getView();
        Polygone_ p = new Polygone_();
        p.setColor(new Color(46, 204, 113));
        if(this.chalet.isFrontIsPrincipal()){
            if(!this.chalet.isDirection_toit()){
                if(view == View.LeftView)
                    p.addPoints(toit.getPignons_de_gauche_a_droite());
                else if(view == View.RightView)
                    p.addPoints(toit.getPignons_de_droite_a_gauche());
            }else{
                if(view == View.LeftView)
                    p.addPoints(toit.getPignons_de_droite_a_gauche());
                else if(view == View.RightView)
                    p.addPoints(toit.getPignons_de_gauche_a_droite());
            }
        }else {
            if(!this.chalet.isDirection_toit()){
                if(view == View.FrontView)
                    p.addPoints(toit.getPignons_de_gauche_a_droite());
                else if(view == View.BackView)
                    p.addPoints(toit.getPignons_de_droite_a_gauche());
            }else{
                if(view == View.FrontView)
                    p.addPoints(toit.getPignons_de_droite_a_gauche());
                else if(view == View.BackView)
                    p.addPoints(toit.getPignons_de_gauche_a_droite());
            }
        }
        return p;
    }

    @Override
    public String exportSTL() {
        if(this.chalet.isFrontIsPrincipal()){
            if(!this.chalet.isDirection_toit()){
                return toit.exportPignonArriereFront_droit();
            }else{
                return toit.exportPignonFrontArriere_droit();
            }
        }
        return "";
    }
    
    @Override
    public String exportToBrut() {
        if(this.chalet.isFrontIsPrincipal()){
            if(!this.chalet.isDirection_toit()){
                return toit.exportPignonArriereFront_droit_brut();
            }else{
                return toit.exportPignonFrontArriere_droit_brut();
            }
        }
        return "";
    }
    
    @Override
    public String getTypeFileExportName() {
        return "PD";
    }
}