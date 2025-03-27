package ca.ulaval.glo2004.Domain.wall;

import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.View;
import ca.ulaval.glo2004.Domain.stl.STL;
import ca.ulaval.glo2004.Domain.stl.STLManager;
import java.awt.*;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import java.util.ArrayList;
import java.util.List;

public class LeftWall extends Wall {
    public LeftWall(Chalet chalet) {
        super(chalet);
    }

    @Override
    public Point2d[] getLimitPoints() {
        Point2d[] points = new Point2d[8];
        double half_thickness = this.getThickness().doubleValue() * Wall.half;
        double groove = this.chalet.isFrontIsPrincipal() ? this.chalet.getGroove().doubleValue() : 0;
        points[0] = new Point2d(0, 0);
        points[1] = new Point2d((half_thickness - groove), 0);
        points[2] = new Point2d((half_thickness - groove), (half_thickness));
        points[3] = new Point2d(this.getThickness().doubleValue(), (half_thickness));
        points[4] = new Point2d(this.getThickness().doubleValue(), (this.getLength().doubleValue() - half_thickness));
        points[5] = new Point2d((half_thickness - groove), (this.getLength().doubleValue() - half_thickness));
        points[6] = new Point2d((half_thickness - groove), this.getLength().doubleValue());
        points[7] = new Point2d(0, this.getLength().doubleValue());
        return points;
    }

    @Override
    public Color getColor() {
        return this.chalet.getSidesColor();
    }
    @Override
    public Imperial getLength() {
        return chalet.getLengthCote();
    }

    @Override
    public String getSTLFini() {
        Point2d[] Point2ds = this.getLimitPoints();
        List<Polygone_> accessories = this.drawAccessories();
        accessories.sort((o1, o2) -> {
            if(o1.getPoints().get(0).x < o2.getPoints().get(0).x)
                return -1;
            else if(o1.getPoints().get(0).x > o2.getPoints().get(0).x)
                return 1;
            else
                return 0;
        });

        StringBuilder sb = new StringBuilder();
        double y_intial = this.getPosition().y  + Point2ds[0].y ;
        double y_intial2 = this.getPosition().y  + Point2ds[2].y ;
        double width = Point2ds[7].y - Point2ds[0].y;
        double width2 = Point2ds[5].y - Point2ds[2].y;
        for (Polygone_ polygone_ : accessories) {
            Point3d point3d_1  = new Point3d(y_intial, this.getPosition().y  + Point2ds[0].y  , 0.0);
            double length = polygone_.getPoints().get(1).x - y_intial;
            double height = this.getHeight().doubleValue();
            STL stl = new STL(point3d_1, width, length, height);

            Point3d point3d_vide = new Point3d(this.getPosition().y  + Point2ds[0].y, polygone_.getPoints().get(0).x, this.getHeight().doubleValue() - polygone_.getPoints().get(2).y);
            double length_vide = polygone_.getPoints().get(1).x - polygone_.getPoints().get(0).x;
            double height_vide = polygone_.getPoints().get(2).y - polygone_.getPoints().get(1).y;
            STL vide = new STL(point3d_vide, width, length_vide, height_vide);

            sb.append(STLManager.generateSTL(stl, vide));
            y_intial = polygone_.getPoints().get(1).x ;



            Point3d point3d_2  = new Point3d(this.getPosition().y  + Point2ds[2].y , y_intial2, 0.0);
            length = polygone_.getPoints().get(1).x - y_intial2;
            height = this.getHeight().doubleValue();
            stl = new STL(point3d_2, width2, length, height);

            point3d_vide = new Point3d(this.getPosition().y  + Point2ds[2].y , polygone_.getPoints().get(0).x, this.getHeight().doubleValue() - polygone_.getPoints().get(2).y);
            length_vide = polygone_.getPoints().get(1).x - polygone_.getPoints().get(0).x;
            height_vide = polygone_.getPoints().get(2).y - polygone_.getPoints().get(1).y;
            vide = new STL(point3d_vide, width2, length_vide, height_vide);

            y_intial2 = polygone_.getPoints().get(1).x;
        }
        Point2ds = this.getLimitPoints();
        Point3d point3d  = new Point3d(this.getPosition().y  + Point2ds[0].y , y_intial, 0.0);
        double length = Point2ds[7].x - Point2ds[0].x - y_intial;
        double height = this.getHeight().doubleValue();
        sb.append(STLManager.generateSTL(point3d, width, length, height));

        Point3d point3d2  = new Point3d(this.getPosition().y  + Point2ds[2].y , y_intial2, 0.0);
        double length2 = Point2ds[5].x - Point2ds[2].x - y_intial2 +Point2ds[2].x;
        double height2 = this.getHeight().doubleValue();


        return sb.toString();

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
    public Point2d getExterieurStart() {
        return new Point2d(this.position.y, 0);
    }

    @Override
    public Point2d getSideStart() {
        Point2d p = null;
        if (this.chalet.getView() == View.FrontView) {
            p = new Point2d(this.position.x , 0);
        } else if (this.chalet.getView() == View.BackView) {
            p = new Point2d(this.chalet.getWalls().get(3).getPosition().x+ this.chalet.getThickness().doubleValue() * Wall.half, 0);
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
        double pox_y = 0;
        if(this.getFrontIsPrincipal())
            pox_y = this.getThickness().doubleValue() * Wall.half + this.chalet.getGroove().doubleValue();
        this.position = new Point2d(0, pox_y);
    }

    @Override
    public List<Polygone_> drawAccessories() {
        if(this.chalet.getView() == View.LeftView)
            return super.drawAccessories();
        return new ArrayList<>();
    }

    @Override
    public String getTypeFileExportName() {
        return "G";
    }
    
    @Override
    public void calulateRelativeAccessoriesPosition() {
        for (Accessorie accessorie : this.getAccessories()) {
            accessorie.calculateRelativePosition(this.chalet.getOldHeight(), this.chalet.getOldLengthCote());
        }
    }
}