package ca.ulaval.glo2004.Domain.wall;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Polygone_ extends Polygon {

    private Color color;
    public double[] xpoints_double = new double[20];
    public double[] yoints_double = new double[20];

    public Polygone_(Color color) {
        super();
        this.color = color;
    }

    public Polygone_() {
        super();
        this.color = Color.black;
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }


    public void zoomAndTranslate(double zoomFactor, int x, int y) {
        for (int i = 0; i < npoints; i++) {
            xpoints_double[i] *= zoomFactor;
            yoints_double[i] *= zoomFactor;
            xpoints_double[i] += x;
            yoints_double[i] += y;
            xpoints[i] = (int) xpoints_double[i];
            ypoints[i] = (int) yoints_double[i];
        }
    }

    public void addPoint(double x, double y) {
        super.addPoint((int) x, (int) y);
        xpoints_double[npoints - 1] = x;
        yoints_double[npoints - 1] = y;
    }

    public void addPoints(List<Point2d> points){
        for (Point2d point : points) {
            addPoint(point.x, point.y);
        }
    }


    public Point2D.Double getCenter(){
        Rectangle bounds = getBounds();
        return new Point2D.Double(bounds.getCenterX(), bounds.getCenterY());
    }

    public List<Point> getPoints() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < npoints; i++) {
            points.add(new Point(xpoints[i], ypoints[i]));
        }
        return points;
    }
}