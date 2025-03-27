package ca.ulaval.glo2004.Domain.wall;
	
import java.io.Serializable;

public class Point2d implements Serializable {

    public double x;
    public double y;

    public Point2d(double x, double y){
        this.x = x;
        this.y = y;
    }

}