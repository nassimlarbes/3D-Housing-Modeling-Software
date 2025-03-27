package ca.ulaval.glo2004.Domain.wall;
import ca.ulaval.glo2004.Domain.Chalet;
import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.View;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import ca.ulaval.glo2004.Domain.stl.STL;
import ca.ulaval.glo2004.Domain.stl.STLManager;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FrontWall extends Wall {

    public FrontWall(Chalet chalet) {
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
    public Point2d[] getLimitPoints() {
        Point2d[] Point2ds = new Point2d[8];
        double half_thickness = this.getThickness().doubleValue() * Wall.half;
        double groove = this.chalet.isFrontIsPrincipal() ? 0 : this.chalet.getGroove().doubleValue() ;
        Point2ds[0] = new Point2d( 0, 0);
        Point2ds[1] = new Point2d( 0, (half_thickness - groove));
        Point2ds[2] = new Point2d(half_thickness, (half_thickness - groove));
        Point2ds[3] = new Point2d(half_thickness, this.getThickness().doubleValue());
        Point2ds[4] = new Point2d((this.getLength().doubleValue() - half_thickness), this.getThickness().doubleValue());
        Point2ds[5] = new Point2d((this.getLength().doubleValue() - half_thickness), (half_thickness - groove));
        Point2ds[6] = new Point2d(this.getLength().doubleValue(), (half_thickness - groove));
        Point2ds[7] = new Point2d(this.getLength().doubleValue(), 0);
        return Point2ds;
    }

    @Override
    public Color getColor() {
        return this.chalet.getFrontColor();
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
        double y_intial = this.getPosition().x + Point2ds[0].x;
        double y_intial2 = this.getPosition().x + Point2ds[2].x;
        double width = Point2ds[1].y - Point2ds[0].y;
        double width2 = Point2ds[3].y - Point2ds[2].y;
        for (Polygone_ polygone_ : accessories) {
            Point3d point3d_1  = new Point3d(this.getPosition().y  + Point2ds[0].y , y_intial, 0.0);
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

            sb.append(STLManager.generateSTL(stl, vide));
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
        sb.append(STLManager.generateSTL(point3d2, width2, length2, height2));


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
            p = new Point2d(0, 0);
        } else if (this.chalet.getView() == View.LeftView) {
            p = new Point2d(this.chalet.getWalls().get(1).getPosition().y + this.chalet.getThickness().doubleValue() * Wall.half, 0);
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
        double pox_x = 0;
        if(!this.getFrontIsPrincipal())
            pox_x = this.getThickness().doubleValue() * Wall.half + this.chalet.getGroove().doubleValue();
        this.position = new Point2d( pox_x, 0);
    }


    @Override
    public List<Polygone_> drawAccessories() {
        if(this.chalet.getView() == View.FrontView)
            return super.drawAccessories();
        return new ArrayList<>();
    }
    
    @Override
    public void calulateRelativeAccessoriesPosition() {
        for (Accessorie accessorie : this.getAccessories()) {
            accessorie.calculateRelativePosition(this.chalet.getOldHeight(), this.chalet.getOldLengthFacade());
        }
    }

    @Override
    public String getTypeFileExportName() {
        return "F";
    }



}