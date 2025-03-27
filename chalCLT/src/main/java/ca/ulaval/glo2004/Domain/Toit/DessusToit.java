/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain.Toit;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.wall.Polygone_;

import java.awt.*;

public class DessusToit extends WallOfRoof {
    public DessusToit(Chalet chalet, Toit toit) {
        super(chalet, toit);
    }

    @Override
    public Polygone_ draw() {
        Polygone_ p = new Polygone_();
        p.setColor(new Color(241, 196, 15));
        if(this.chalet.isFrontIsPrincipal()){
            if(!this.chalet.isDirection_toit()){
                switch (this.chalet.getView()){
                    case LeftView  :
                        p.addPoints(toit.getDessus_toit_cote_gauche());
                        break;
                    case RightView :
                        p.addPoints(toit.getDessus_toit_cote_droit());
                        break;
                    case FrontView:
                        p.addPoints(toit.getDessus_toit_facade());
                        break;
                    case BackView:
                        p.addPoints(toit.getDessus_toit_arriere());
                        break;
                }
            }else {
                switch (this.chalet.getView()){
                    case RightView:
                        p.addPoints(toit.getDessus_toit_cote_gauche());
                        break;
                    case LeftView :
                        p.addPoints(toit.getDessus_toit_cote_droit());
                        break;
                    case FrontView:
                        p.addPoints(toit.getDessus_toit_arriere());
                        break;
                    case BackView:
                        p.addPoints(toit.getDessus_toit_facade());
                        break;
                }
            }
        }else {
            if(!this.chalet.isDirection_toit()){
                switch (this.chalet.getView()){
                    case LeftView:
                        p.addPoints(toit.getDessus_toit_facade());
                        break;
                    case RightView:
                        p.addPoints(toit.getDessus_toit_arriere());
                        break;
                    case FrontView:
                        p.addPoints(toit.getDessus_toit_cote_gauche());
                        break;
                    case BackView:
                        p.addPoints(toit.getDessus_toit_cote_droit());
                        break;
                }
            }else {
                switch (this.chalet.getView()){
                    case LeftView:
                        p.addPoints(toit.getDessus_toit_arriere());
                        break;
                    case RightView:
                        p.addPoints(toit.getDessus_toit_facade());
                        break;
                    case FrontView:
                        p.addPoints(toit.getDessus_toit_cote_droit());
                        break;
                    case BackView:
                        p.addPoints(toit.getDessus_toit_cote_gauche());
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
                return toit.exportDessusToit_gauche_brut();
            }else{
                return toit.exportDessusToit_droit_brut();
            }
        }
        return "";
    }
    
    @Override
    public String exportToBrut() {
        if(this.chalet.isFrontIsPrincipal()){
            if(!this.chalet.isDirection_toit()){
                return toit.exportDessusToit_gauche();
            }else{
                return toit.exportDessusToit_droit();
            }
        }
        return "";
    }

    @Override
    public String getTypeFileExportName() {
        return "T";
    }
    



}