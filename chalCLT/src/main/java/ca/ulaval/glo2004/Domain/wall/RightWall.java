/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain.wall;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.View;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import ca.ulaval.glo2004.Domain.stl.STLManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RightWall extends Wall {
    public RightWall(Chalet chalet) {
        super(chalet);
    }


    @Override
    public Point2d[] getLimitPoints() {
        Polygone_ topView = new Polygone_(this.chalet.getSidesColor());
        Point2d[] points = new Point2d[8];
        double half_thickness = this.getThickness().doubleValue() * Wall.half;
        double groove = this.chalet.isFrontIsPrincipal() ? this.chalet.getGroove().doubleValue() : 0;
        points[0] = new Point2d(0, half_thickness);
        points[1] = new Point2d( (half_thickness + groove), half_thickness);
        points[2] = new Point2d((half_thickness + groove), 0);
        points[3] = new Point2d(this.getThickness().doubleValue(), 0);
        points[4] = new Point2d(this.getThickness().doubleValue(), this.getLength().doubleValue());
        points[5] = new Point2d((half_thickness + groove), this.getLength().doubleValue());
        points[6] = new Point2d((half_thickness + groove), (this.getLength().doubleValue() - half_thickness));
        points[7] = new Point2d(0, (this.getLength().doubleValue() - half_thickness));
        return points;
    }

    @Override
    public String getSTLFini() {
        Point2d[] points = this.getLimitPoints();
        String stl1 = STLManager.generateSTL(
                new Point3d( this.getPosition().y  + points[0].y , this.getPosition().x + points[0].x, 0.0),
                points[7].y - points[0].y,
                points[1].x - points[0].x,
                this.getHeight().doubleValue());

        String stl2 = STLManager.generateSTL(
                new Point3d( this.getPosition().y  + points[2].y , this.getPosition().x + points[2].x, 0.0),
                points[5].y - points[2].y,
                points[3].x - points[2].x,
                this.getHeight().doubleValue() );
        return stl1 + stl2;
    }

    @Override
    public String getSTLBrut() {
        return "";
    }

    @Override
    public String getSTLRetrait() {
        return "";
    }

    @Override
    public void calulateRelativeAccessoriesPosition() {
        for (Accessorie accessorie : this.getAccessories()) {
            accessorie.calculateRelativePosition(this.chalet.getOldHeight(), this.chalet.getOldLengthCote());
        }
    }

    @Override
    public Imperial getLength() {
        return chalet.getLengthCote();
    }

    @Override
    public Point2d getExterieurStart() {
        return new Point2d(this.position.y, 0);
    }

    @Override
    public Point2d getSideStart() {
        Point2d p = null;
        if (this.chalet.getView() == View.FrontView) {
            p = new Point2d(this.position.x + this.chalet.getThickness().doubleValue() * Wall.half, 0);
        } else if (this.chalet.getView() == View.BackView) {
            p = new Point2d(this.chalet.getWalls().get(2).getPosition().x, 0);
        }
        return p;
    }


    @Override
    public Polygone_ getView() {
        switch (this.chalet.getView()) {
            case FrontView:
            case BackView:
                return this.getSideView();
            case LeftView:
            case RightView:
                return this.getExteriorView();
            default:
                return this.getTopView();
        }
    }

    @Override
    public void calculatePosition() {
        double pos_x = this.chalet.getLengthFacade().doubleValue() + 2 * this.chalet.getGroove().doubleValue();
        double pos_y = 0;
        if (this.getFrontIsPrincipal()) {
            pos_x = this.chalet.getLengthFacade().doubleValue() - this.getThickness().doubleValue();
            pos_y = this.getThickness().doubleValue() * Wall.half + this.chalet.getGroove().doubleValue();
        }
        this.position = new Point2d( pos_x, pos_y);
    }

    @Override
    public Color getColor() {
        return this.chalet.getSidesColor();
    }

    @Override
    public List<Polygone_> drawAccessories() {
        if(this.chalet.getView() == View.RightView)
            return super.drawAccessories();
        return new ArrayList<>();
    }

    @Override
    public String getTypeFileExportName() {
        return "D";
    }
}