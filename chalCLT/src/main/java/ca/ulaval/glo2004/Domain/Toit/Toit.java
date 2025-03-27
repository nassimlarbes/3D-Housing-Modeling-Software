package ca.ulaval.glo2004.Domain.Toit;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.stl.STL;
import ca.ulaval.glo2004.Domain.stl.STLManager;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import ca.ulaval.glo2004.Domain.wall.Point3d;
import ca.ulaval.glo2004.Domain.wall.Wall;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Toit implements Serializable {

    Chalet chalet;
    double angle = 15;
    double longueur = 0; //lié au pignon
    double largeur = 0; // lié au laralenge
    double epaisseur = 0;
    double hauteurRallonge = 0;
    double epaisseurDesusToit = 0;
    double hauteur = 0;
    double grouve = 0;

    public Toit(Chalet chalet) {
        this.chalet = chalet;
        updateToit();
    }

    public void updateToit() {
        //calculer la hauteur du pignon
        this.grouve = this.chalet.getGroove().doubleValue();
        //calculer l'épaisseur
        this.angle = chalet.getAngle();
        this.epaisseur = chalet.getThickness().doubleValue() * Wall.half;
        //calculer la longueur
        this.longueur = (!chalet.isFrontIsPrincipal() ? chalet.getLengthFacade().doubleValue() : chalet.getLengthCote().doubleValue()) + epaisseur + grouve;
        //calculer la largeur de la rallonge
        this.largeur = !chalet.isFrontIsPrincipal() ? chalet.getLengthCote().doubleValue() : chalet.getLengthFacade().doubleValue();
        //calculer l'épaisseur de la rallonge
        this.hauteur = this.longueur * Math.tan(Math.toRadians(this.angle)) ;
        //calculer l'hauteur de la rallonge
        this.hauteurRallonge = this.hauteur + this.epaisseur * Math.tan(Math.toRadians(this.angle));
        //calculer l'épaisseur du dessus du toit
        this.epaisseurDesusToit = this.epaisseur * Math.cos(Math.toRadians(this.angle));
        //inverser le signe de la hauteur
        this.hauteur = -this.hauteur;
        this.hauteurRallonge = -this.hauteurRallonge;
        this.epaisseurDesusToit = -this.epaisseurDesusToit;
    }


    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getLongueur() {
        return longueur;
    }

    public void setLongueur(double longueur) {
        this.longueur = longueur;
    }

    public double getLargeur() {
        return largeur;
    }

    public void setLargeur(double largeur) {
        this.largeur = largeur;
    }

    public double getEpaisseur() {
        return epaisseur;
    }

    public void setEpaisseur(double epaisseur) {
        this.epaisseur = epaisseur;
    }


    List<Point2d> getPignons_de_gauche_a_droite() {
        List<Point2d> pignons = new ArrayList<Point2d>();
        Point2d point1 = new Point2d(epaisseur , 0);
        pignons.add(point1);
        pignons.add(new Point2d(point1.x + longueur + grouve, point1.y));
        pignons.add(new Point2d(point1.x, point1.y + hauteur));
        return pignons;
    }

    List<Point2d> getPignons_de_droite_a_gauche() {
        List<Point2d> pignons = new ArrayList<Point2d>();
        Point2d point1 = new Point2d(0, 0);
        pignons.add(point1);
        pignons.add(new Point2d(point1.x + longueur + grouve, point1.y));
        pignons.add(new Point2d(point1.x + longueur + grouve, point1.y + hauteur));
        return pignons;
    }

    List<Point2d> getRallonge_de_gauche_a_droite() {
        List<Point2d> rallonges = new ArrayList<Point2d>();
        Point2d depart = new Point2d(0, 0);
        rallonges.add(depart);
        double hauteurErr = - this.grouve * Math.tan(Math.toRadians(this.angle));
        rallonges.add(new Point2d(depart.x + epaisseur, depart.y));
        rallonges.add(new Point2d(depart.x + epaisseur, depart.y + hauteur + hauteurErr));
        rallonges.add(new Point2d(depart.x, depart.y + hauteurRallonge + hauteurErr));
        return rallonges;
    }

    List<Point2d> getRallonge_de_droite_a_gauche() {
        List<Point2d> rallonges = new ArrayList<Point2d>();
        Point2d depart = new Point2d(longueur + grouve, 0);
        double hauteurErr = - this.grouve * Math.tan(Math.toRadians(this.angle));
        rallonges.add(depart);
        rallonges.add(new Point2d(depart.x + epaisseur, depart.y));
        rallonges.add(new Point2d(depart.x + epaisseur, depart.y + hauteurRallonge + hauteurErr));
        rallonges.add(new Point2d(depart.x, depart.y + hauteur + hauteurErr));
        return rallonges;
    }

    List<Point2d> getRallonge_facade() {
        List<Point2d> rallonges = new ArrayList<Point2d>();
        Point2d depart = new Point2d(0, 0);
        rallonges.add(depart);
        rallonges.add(new Point2d(depart.x + largeur, depart.y));
        rallonges.add(new Point2d(depart.x + largeur, depart.y + hauteurRallonge));
        rallonges.add(new Point2d(depart.x, depart.y + hauteurRallonge));
        return rallonges;
    }

    List<Point2d> getDessus_toit_facade() {
        List<Point2d> rallonges = new ArrayList<Point2d>();
        Point2d depart = new Point2d(0, 0);
        rallonges.add(depart);
        rallonges.add(new Point2d(depart.x + largeur, depart.y));
        rallonges.add(new Point2d(depart.x + largeur, depart.y + epaisseurDesusToit + hauteurRallonge));
        rallonges.add(new Point2d(depart.x, depart.y + epaisseurDesusToit + hauteurRallonge));
        return rallonges;
    }

    List<Point2d> getDessus_toit_arriere() {
        List<Point2d> rallonges = new ArrayList<Point2d>();
        Point2d depart = new Point2d(0, hauteurRallonge);
        rallonges.add(depart);
        rallonges.add(new Point2d(depart.x + largeur, depart.y));
        rallonges.add(new Point2d(depart.x + largeur, depart.y + epaisseurDesusToit));
        rallonges.add(new Point2d(depart.x, depart.y + epaisseurDesusToit));
        return rallonges;
    }

    List<Point2d> getDessus_toit_cote_gauche() {
        List<Point2d> rallonges = new ArrayList<Point2d>();
        Point2d depart = new Point2d(longueur + epaisseur + grouve, 0);
        rallonges.add(depart);
        rallonges.add(new Point2d(depart.x, depart.y + epaisseurDesusToit));
        rallonges.add(new Point2d(depart.x - (longueur + epaisseur + grouve), depart.y + epaisseurDesusToit + hauteurRallonge));
        rallonges.add(new Point2d(depart.x - (longueur + epaisseur + grouve), depart.y + hauteurRallonge));
        return rallonges;
    }

    List<Point2d> getDessus_toit_cote_droit() {
        List<Point2d> rallonges = new ArrayList<Point2d>();
        Point2d depart = new Point2d(0, 0);
        rallonges.add(depart);
        rallonges.add(new Point2d(depart.x, depart.y + epaisseurDesusToit));
        rallonges.add(new Point2d(depart.x + (longueur + epaisseur + grouve), depart.y + epaisseurDesusToit + hauteurRallonge));
        rallonges.add(new Point2d(depart.x + (longueur + epaisseur + grouve), depart.y + hauteurRallonge));
        return rallonges;
    }


    String exportPignonArriereFront_gauche() {
        double hauteur_ = -this.hauteur;
        Point3d d = new Point3d(
                epaisseur + grouve,
                0,
                this.chalet.getHeight().doubleValue());

        Point3d d2 = new Point3d(
                epaisseur + grouve + epaisseur,
                epaisseur - grouve,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d,
                epaisseur - grouve,
                 longueur,
                 hauteur_ );

        STL stl2 = new STL(d2,
                epaisseur + grouve,
                longueur - (2 * epaisseur),
                hauteur_ + this.epaisseurDesusToit);

        return STLManager.exportPignon(stl) + STLManager.exportPignon(stl2);
    }

    String exportPignonArriereFront_gauche_brut() {
        double hauteur_ = -this.hauteur;
        Point3d d = new Point3d(
                0,
                0,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d,
                epaisseur * 2,
                longueur,
                hauteur_ );

        return STLManager.exportPignon(stl) ;
    }


    String exportPignonFrontArriere_gauche() {
        double hauteur_ = -this.hauteur;
        Point3d d = new Point3d(
                longueur,
                0,
                this.chalet.getHeight().doubleValue());

        Point3d d2 = new Point3d(
                longueur - epaisseur,
                epaisseur - grouve,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(
                d,
                epaisseur - grouve,
                - longueur,
                hauteur_ );

        STL stl2 = new STL(d2,
                epaisseur + grouve,
                -longueur + (2 * epaisseur),
                hauteur_ + this.epaisseurDesusToit);

        return STLManager.exportPignon(stl) + STLManager.exportPignon(stl2);
    }

    String exportPignonFrontArriere_gauche_brut() {
        double hauteur_ = -this.hauteur;
        Point3d d = new Point3d(
                longueur,
                0,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(
                d,
                epaisseur * 2,
                - longueur,
                hauteur_ );

        return STLManager.exportPignon(stl) ;
    }

    String exportPignonArriereFront_droit() {
        double hauteur_ = -this.hauteur;
        Point3d d = new Point3d(
                epaisseur + grouve,
                this.largeur - epaisseur + grouve,
                this.chalet.getHeight().doubleValue());

        Point3d d2 = new Point3d(
                epaisseur + grouve + epaisseur,
                this.largeur - (2 * epaisseur) ,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, epaisseur - grouve, longueur, hauteur_ );
        STL stl2 = new STL(d2, epaisseur + grouve, longueur - epaisseur, hauteur_ + this.epaisseurDesusToit);
        return STLManager.exportPignon(stl) + STLManager.exportPignon(stl2);
    }

    String exportPignonArriereFront_droit_brut() {
        double hauteur_ = -this.hauteur;
        Point3d d = new Point3d(
                0,
                this.largeur - (2 *epaisseur ),
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, epaisseur * 2, longueur, hauteur_ );
        return STLManager.exportPignon(stl) ;
    }



    String exportPignonFrontArriere_droit() {
        double hauteur_ = -this.hauteur;
        Point3d d = new Point3d(
                longueur,
                this.largeur - epaisseur + grouve,
                this.chalet.getHeight().doubleValue());

        Point3d d2 = new Point3d(
                longueur - epaisseur,
                this.largeur - (2 * epaisseur) ,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, epaisseur - grouve, - longueur, hauteur_ );
        STL stl2 = new STL(d2, epaisseur + grouve, - longueur + epaisseur, hauteur_ + this.epaisseurDesusToit);
        return STLManager.exportPignon(stl) + STLManager.exportPignon(stl2);
    }

    String exportPignonFrontArriere_droit_brut() {
        double hauteur_ = -this.hauteur;
        Point3d d = new Point3d(
                longueur,
                this.largeur - (2*epaisseur) ,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, epaisseur * 2, - longueur, hauteur_ );
        return STLManager.exportPignon(stl) ;
    }



    String exportRallonge_arriere() {
        double hauteur_ = -this.hauteur;
        double err = this.epaisseur * Math.tan(Math.toRadians(this.angle));

        Point3d d = new Point3d(
                0,
                0,
                this.chalet.getHeight().doubleValue());

        Point3d d2 = new Point3d(
                epaisseur,
                epaisseur,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, epaisseur, largeur, hauteur_ );
        STL stl2 = new STL(d2, epaisseur, largeur - (2 * epaisseur), hauteur_  + this.epaisseurDesusToit);
        return STLManager.exportRanlange(stl,0,err) + STLManager.exportRanlange(stl2,0,err);
    }

    String exportRallonge_arriere_brut() {
        double hauteur_ = -this.hauteur;
        double err = this.epaisseur * Math.tan(Math.toRadians(this.angle));

        Point3d d = new Point3d(
                0,
                0,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, epaisseur * 2, largeur, hauteur_ );
        return STLManager.exportRanlange(stl,0,err);
    }

    String exportRallonge_front() {
        double hauteur_ = -this.hauteur;
        double err = this.epaisseur * Math.tan(Math.toRadians(this.angle));
        Point3d d = new Point3d(
                longueur + grouve,
                0,
                this.chalet.getHeight().doubleValue());

        Point3d d2 = new Point3d(
                longueur - epaisseur + grouve,
                epaisseur,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, epaisseur, largeur, hauteur_ );
        STL stl2 = new STL(d2, epaisseur, largeur - (2 * epaisseur), hauteur_  + this.epaisseurDesusToit);
        return STLManager.exportRanlange(stl,err,0) + STLManager.exportRanlange(stl2,err,0);
    }

    String exportRallonge_front_brut() {
        double hauteur_ = -this.hauteur;
        double err = this.epaisseur * Math.tan(Math.toRadians(this.angle));
        Point3d d = new Point3d(
                longueur  - epaisseur,
                0,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, epaisseur * 2, largeur, hauteur_ );
        return STLManager.exportRanlange(stl,err,0);
    }


    String exportDessusToit_droit() {
        double hauteur_ = -this.hauteurRallonge;
        double err = this.epaisseur * Math.tan(Math.toRadians(this.angle));
        Point3d d = new Point3d(
                0,
                0,
                this.chalet.getHeight().doubleValue());

        Point3d d2 = new Point3d(
                0,
                epaisseur,
                this.chalet.getHeight().doubleValue() + this.epaisseurDesusToit);


        STL stl = new STL(d, largeur,  longueur + epaisseur, hauteur_ );
        STL stl2 = new STL(d2, largeur - (2 * epaisseur), longueur  , hauteur_  );
        return STLManager.exportTopRoof(stl2, this.epaisseurDesusToit) + STLManager.exportTopRoof(stl, this.epaisseurDesusToit);
    }

    String exportDessusToit_droit_brut() {
        double hauteur_ = -this.hauteurRallonge;
        double err = this.epaisseur * Math.tan(Math.toRadians(this.angle));
        Point3d d = new Point3d(
                0,
                0,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d, largeur,  longueur + epaisseur, hauteur_ );
        return STLManager.exportTopRoof(stl, this.epaisseurDesusToit * 2);
    }

    String exportDessusToit_gauche() {
        double hauteur_ = -this.hauteurRallonge;
        double err = this.epaisseur * Math.tan(Math.toRadians(this.angle));
        Point3d d = new Point3d(
                longueur + grouve,
                0,
                this.chalet.getHeight().doubleValue());

        Point3d d1= new Point3d(
                longueur + grouve,
                epaisseur,
                this.chalet.getHeight().doubleValue() + this.epaisseurDesusToit);

        STL stl = new STL(d,
                largeur,
                -longueur,

                hauteur_);
        STL stl2 = new STL(d1,
                largeur - (2 * epaisseur),
                -longueur + epaisseur,

                hauteur_ );
        return STLManager.exportTopRoof(stl, this.epaisseurDesusToit) + STLManager.exportTopRoof(stl2, this.epaisseurDesusToit);
    }

    String exportDessusToit_gauche_brut() {
        double hauteur_ = -this.hauteurRallonge;
        double err = this.epaisseur * Math.tan(Math.toRadians(this.angle));
        Point3d d = new Point3d(
                longueur,
                0,
                this.chalet.getHeight().doubleValue());

        STL stl = new STL(d,
                largeur,
                -longueur,
                hauteur_);
        return STLManager.exportTopRoof(stl, this.epaisseurDesusToit * 2);
    }










}
