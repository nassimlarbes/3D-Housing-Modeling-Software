package ca.ulaval.glo2004.Domain.wall;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.View;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import ca.ulaval.glo2004.Domain.stl.STLManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BackWall extends Wall {
    public BackWall(Chalet chalet) {
        super(chalet);
    }

    @Override
    public Polygone_ getExteriorView() {
        Polygone_ p = super.getExteriorView();
        p.setColor(this.chalet.getFrontColor());
        return p;
    }

    @Override
    public Polygone_ getSideView() {
        Polygone_ p = super.getSideView();
        p.setColor(this.chalet.getFrontColor());
        return p;
    }
    
    @Override
    public void calulateRelativeAccessoriesPosition() {
        for (Accessorie accessorie : this.getAccessories()) {
            accessorie.calculateRelativePosition(this.chalet.getOldHeight(), this.chalet.getOldLengthFacade());
        }
    }

    @Override
    public Point2d[] getLimitPoints() {
        Point2d[] points = new Point2d[8];
        double half_thickness = this.getThickness().doubleValue() * Wall.half;
        double groove = this.chalet.isFrontIsPrincipal() ? 0 : this.chalet.getGroove().doubleValue();
        points[0] = new Point2d(half_thickness, 0);
        points[1] = new Point2d(half_thickness, half_thickness + groove);
        points[2] = new Point2d(0, half_thickness + groove);
        points[3] = new Point2d(0, this.getThickness().doubleValue());
        points[4] = new Point2d(this.getLength().doubleValue(), this.getThickness().doubleValue());
        points[5] = new Point2d(this.getLength().doubleValue(), half_thickness + groove);
        points[6] = new Point2d(this.getLength().doubleValue() - half_thickness, half_thickness + groove);
        points[7] = new Point2d(this.getLength().doubleValue() - half_thickness, 0);
        return points;
    }

    @Override
    public String getSTLFini() {
        Point2d[] points = this.getLimitPoints();

        String stl1 = STLManager.generateSTL(
                new Point3d( this.getPosition().y  + points[2].y , this.getPosition().x + points[2].x, 0.0),
                points[3].y - points[2].y,
                points[5].x - points[2].x,
                this.getHeight().doubleValue());

        String stl2 = STLManager.generateSTL(
                new Point3d( this.getPosition().y  + points[0].y , this.getPosition().x + points[0].x, 0.0),
                points[1].y - points[0].y,
                points[7].x - points[0].x,
                this.getHeight().doubleValue());




        return stl1 + stl2 ;
    }
    @Override
    public String getSTLBrut() {
        String stl1 = STLManager.generateSTL(
                new Point3d( this.getPosition().y , this.getPosition().x , 0.0),
                this.getLength().doubleValue(),
                this.getThickness().doubleValue(),
                this.getHeight().doubleValue());
        return stl1;
    }
    @Override
    public String getSTLRetrait() {
        String stl1 = ""   ;
        for (Accessorie accessorie : this.getAccessories()) {
            stl1 += STLManager.generateSTL(
                    new Point3d( this.getPosition().y  , this.getPosition().x + accessorie.getPosition().x , this.getHeight().doubleValue() - accessorie.getPosition().y),
                    accessorie.getLength().doubleValue(),
                    this.getThickness().doubleValue(),
                    -accessorie.getHeight().doubleValue());
        }
        return stl1;
    }

    @Override
    public Imperial getLength() {
        return chalet.getLengthFacade();
    }

    @Override
    public Point2d getExterieurStart() {
        return new Point2d(this.position.x, 0);
    }

    @Override
    public Point2d getSideStart() {
        Point2d p = null;
        if (this.chalet.getView() == View.RightView) {
            p= new Point2d(this.position.y + this.chalet.getThickness().doubleValue() * Wall.half, 0);
        } else if (this.chalet.getView() == View.LeftView) {
            p = new Point2d(0, 0);
        }
        return p;
    }


    @Override
    public Polygone_ getView() {
        switch (this.chalet.getView()) {
            case FrontView:
            case BackView:
                return this.getExteriorView();
            case LeftView:
            case RightView:
                return this.getSideView();
            default:
                return this.getTopView();
        }
    }

    @Override
    public void calculatePosition() {
        double pos_x = 0;
        double pos_y = this.chalet.getLengthCote().doubleValue() + 2 * this.chalet.getGroove().doubleValue();
        if (!this.getFrontIsPrincipal()) {
            pos_x = this.getThickness().doubleValue() * Wall.half + this.chalet.getGroove().doubleValue();
            pos_y = this.chalet.getLengthCote().doubleValue()  - this.getThickness().doubleValue();
        }
        this.position = new Point2d( pos_x,  pos_y);
    }

    @Override
    public Color getColor() {
        return this.chalet.getFrontColor();
    }


    @Override
    public List<Polygone_> drawAccessories() {
        if(this.chalet.getView() == View.BackView)
            return super.drawAccessories();
        return new ArrayList<>();
    }

    @Override
    public String getTypeFileExportName() {
        return "A";
    }
}