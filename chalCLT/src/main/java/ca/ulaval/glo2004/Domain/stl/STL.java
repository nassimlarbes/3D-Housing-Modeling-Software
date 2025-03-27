/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain.stl;

import ca.ulaval.glo2004.Domain.wall.Point3d;

public class STL {
    Point3d startPoint;
    double width;
    double length;
    double height;

    public STL(Point3d startPoint, double width, double length, double height) {
        this.startPoint = startPoint;
        this.width = width;
        this.length = length;
        this.height = height;
    }

    public Point3d getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point3d startPoint) {
        this.startPoint = startPoint;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}