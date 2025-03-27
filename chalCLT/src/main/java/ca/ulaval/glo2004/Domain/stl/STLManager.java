/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.Domain.stl;

import ca.ulaval.glo2004.Domain.wall.Point3d;


public class STLManager {
    
    public static String generateSTL(Point3d startPoint, double width, double length, double height) {
        StringBuilder builder = new StringBuilder();
        //calculate the 8 points of the parallelepiped
        Point3d p1 = new Point3d(startPoint.x, startPoint.y, startPoint.z);
        Point3d p2= new Point3d(startPoint.x + width, startPoint.y, startPoint.z);
        Point3d p3 = new Point3d(startPoint.x, startPoint.y + length, startPoint.z);
        Point3d p4 = new Point3d(startPoint.x + width, startPoint.y + length, startPoint.z);
        Point3d p5 = new Point3d(startPoint.x , startPoint.y, startPoint.z + height);
        Point3d p6 = new Point3d(startPoint.x + width, startPoint.y, startPoint.z+ height);
        Point3d p7 = new Point3d(startPoint.x , startPoint.y + length, startPoint.z + height);
        Point3d p8 = new Point3d(startPoint.x + width, startPoint.y + length, startPoint.z + height);
        
        builder.append(appendFacet(p1, p2, p3));
        builder.append(appendFacet(p4, p3, p2));
        
        builder.append(appendFacet(p5, p6, p7));
        builder.append(appendFacet(p8, p7, p6));
        
        builder.append(appendFacet(p1, p5, p7));
        builder.append(appendFacet(p3, p7, p1));
        
        builder.append(appendFacet(p2, p6, p8));
        builder.append(appendFacet(p4, p8, p2));
        
        builder.append(appendFacet(p1, p2, p5));
        builder.append(appendFacet(p6, p5, p2));
        
        builder.append(appendFacet(p3, p4, p7));
        builder.append(appendFacet(p8, p7, p4));
        
        return builder.toString();
    }
    
    public static String generateSTL(STL stt, STL vide) {
        Point3d startPoint = stt.getStartPoint();
        double width = stt.getWidth();
        double length = stt.getLength();
        double height = stt.getHeight();
        Point3d videStartPoint = vide.getStartPoint();
        double videWidth = vide.getWidth();
        double videLength = vide.getLength();
        double videHeight = vide.getHeight();
        StringBuilder builder = new StringBuilder();

        builder.append(generateSTL(new Point3d(startPoint.x, startPoint.y, startPoint.z), width, videStartPoint.y - startPoint.y,height));
        builder.append(generateSTL(new Point3d(startPoint.x, startPoint.y, startPoint.z), width, length, videStartPoint.z - startPoint.z));
        if(height - ((videStartPoint.z - startPoint.z) + videHeight) != 0)
            builder.append(generateSTL(new Point3d(startPoint.x, startPoint.y, videStartPoint.z + videHeight), width, length, height - ((videStartPoint.z - startPoint.z) + videHeight)));
        if(length - ((videStartPoint.y - startPoint.y) + videLength) != 0)
            builder.append(generateSTL(new Point3d(startPoint.x, videStartPoint.y + videLength , startPoint.z), width, length - ((videStartPoint.y - startPoint.y) + videLength), height));
        return builder.toString();
    }
    public static String exportPignon(STL stl){
        StringBuilder builder = new StringBuilder();

        Point3d startPoint = stl.getStartPoint();
        Point3d p1 = new Point3d(startPoint.x, startPoint.y, startPoint.z);
        Point3d p2 = new Point3d(startPoint.x + stl.getLength(), startPoint.y, startPoint.z);
        Point3d p3 = new Point3d(startPoint.x, startPoint.y , startPoint.z + stl.getHeight());

        Point3d p4 = new Point3d(startPoint.x , startPoint.y + stl.getWidth(), startPoint.z);
        Point3d p5 = new Point3d(startPoint.x + stl.getLength(), startPoint.y + stl.getWidth(), startPoint.z);
        Point3d p6 = new Point3d(startPoint.x, startPoint.y + stl.getWidth(), startPoint.z + stl.getHeight());

        builder.append(appendFacet(p1, p2, p3));
        builder.append(appendFacet(p4, p5, p6));

        builder.append(appendFacet(p1, p2, p4));
        builder.append(appendFacet(p2, p4, p5));

        builder.append(appendFacet(p2, p3, p5));
        builder.append(appendFacet(p3, p5, p6));

        builder.append(appendFacet(p3, p1, p6));
        builder.append(appendFacet(p1, p6, p4));

        return builder.toString();
    }
    public static String exportPignonTourne(STL stl){
        StringBuilder builder = new StringBuilder();

        Point3d startPoint = stl.getStartPoint();
        Point3d p1 = new Point3d(startPoint.x, startPoint.y, startPoint.z);
        Point3d p2 = new Point3d(startPoint.x , startPoint.y + stl.getLength(), startPoint.z);
        Point3d p3 = new Point3d(startPoint.x, startPoint.y , startPoint.z + stl.getHeight());

        Point3d p4 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y , startPoint.z);
        Point3d p5 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y + stl.getLength(), startPoint.z);
        Point3d p6 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y , startPoint.z + stl.getHeight());

        builder.append(appendFacet(p1, p2, p3));
        builder.append(appendFacet(p4, p5, p6));

        builder.append(appendFacet(p1, p2, p4));
        builder.append(appendFacet(p2, p4, p5));

        builder.append(appendFacet(p2, p3, p5));
        builder.append(appendFacet(p3, p5, p6));

        builder.append(appendFacet(p3, p1, p6));
        builder.append(appendFacet(p1, p6, p4));

        return builder.toString();
    }
    public static String exportTopRoof(STL stl, double thickness){
        StringBuilder builder = new StringBuilder();

        Point3d startPoint = stl.getStartPoint();
        Point3d p1 = new Point3d(startPoint.x, startPoint.y, startPoint.z);
        Point3d p2 = new Point3d(startPoint.x , startPoint.y, startPoint.z + thickness);
        Point3d p3 = new Point3d(startPoint.x + stl.getLength(), startPoint.y, startPoint.z + thickness + stl.getHeight());
        Point3d p4 = new Point3d(startPoint.x + stl.getLength(), startPoint.y, startPoint.z + stl.getHeight());

        Point3d p5 = new Point3d(startPoint.x , startPoint.y + stl.getWidth(), startPoint.z);
        Point3d p6 = new Point3d(startPoint.x , startPoint.y + stl.getWidth(), startPoint.z + thickness);
        Point3d p7 = new Point3d(startPoint.x + stl.getLength(), startPoint.y + stl.getWidth(), startPoint.z + thickness + stl.getHeight());
        Point3d p8 = new Point3d(startPoint.x + stl.getLength(), startPoint.y + stl.getWidth(), startPoint.z + stl.getHeight());


        builder.append(appendFacet(p1, p2, p3));
        builder.append(appendFacet(p4, p3, p1));

        builder.append(appendFacet(p5, p6, p7));
        builder.append(appendFacet(p8, p7, p5));

        builder.append(appendFacet(p1, p5, p8));
        builder.append(appendFacet(p4, p8, p1));

        builder.append(appendFacet(p2, p6, p7));
        builder.append(appendFacet(p3, p7, p2));

        builder.append(appendFacet(p1, p2, p5));
        builder.append(appendFacet(p6, p5, p2));

        builder.append(appendFacet(p3, p4, p7));
        builder.append(appendFacet(p8, p7, p4));


        return builder.toString();
    }
    
    public static String exportTopRoof(STL stl, double thickness, double extra){
        StringBuilder builder = new StringBuilder();
        Point3d startPoint = stl.getStartPoint();
        Point3d p1 = new Point3d(startPoint.x + extra, startPoint.y, startPoint.z + thickness);
        Point3d p2 = new Point3d(startPoint.x , startPoint.y, startPoint.z + thickness);
        Point3d p3 = new Point3d(startPoint.x + stl.getLength(), startPoint.y, startPoint.z + thickness + stl.getHeight());
        Point3d p4 = new Point3d(startPoint.x + stl.getLength(), startPoint.y, startPoint.z + stl.getHeight());
        
        Point3d p5 = new Point3d(startPoint.x + extra, startPoint.y + stl.getWidth(), startPoint.z + thickness);
        Point3d p6 = new Point3d(startPoint.x , startPoint.y + stl.getWidth(), startPoint.z + thickness);
        Point3d p7 = new Point3d(startPoint.x + stl.getLength(), startPoint.y + stl.getWidth(), startPoint.z + thickness + stl.getHeight());
        Point3d p8 = new Point3d(startPoint.x + stl.getLength(), startPoint.y + stl.getWidth(), startPoint.z + stl.getHeight());
        
        builder.append(appendFacet(p1, p2, p3));
        builder.append(appendFacet(p4, p3, p1));
        
        builder.append(appendFacet(p5, p6, p7));
        builder.append(appendFacet(p8, p7, p5));
        
        builder.append(appendFacet(p1, p5, p8));
        builder.append(appendFacet(p4, p8, p1));
        
        builder.append(appendFacet(p2, p6, p7));
        builder.append(appendFacet(p3, p7, p2));
        
        builder.append(appendFacet(p1, p2, p5));
        builder.append(appendFacet(p6, p5, p2));
        
        builder.append(appendFacet(p3, p4, p7));
        builder.append(appendFacet(p8, p7, p4));
        
        
        return builder.toString();
    }
    
    public static String exportTopRoofTourne(STL stl, double thickness) {
        StringBuilder builder = new StringBuilder();

        Point3d startPoint = stl.getStartPoint();
        Point3d p1 = new Point3d(startPoint.x, startPoint.y, startPoint.z);
        Point3d p2 = new Point3d(startPoint.x, startPoint.y, startPoint.z + thickness);
        Point3d p3 = new Point3d(startPoint.x , startPoint.y + stl.getWidth(), startPoint.z + thickness + stl.getHeight());
        Point3d p4 = new Point3d(startPoint.x , startPoint.y + stl.getWidth(), startPoint.z + stl.getHeight());

        Point3d p5 = new Point3d(startPoint.x + stl.getLength(), startPoint.y , startPoint.z);
        Point3d p6 = new Point3d(startPoint.x + stl.getLength(), startPoint.y , startPoint.z + thickness);
        Point3d p7 = new Point3d(startPoint.x + stl.getLength(), startPoint.y + stl.getWidth(), startPoint.z + thickness + stl.getHeight());
        Point3d p8 = new Point3d(startPoint.x + stl.getLength(), startPoint.y + stl.getWidth(), startPoint.z + stl.getHeight());

        builder.append(appendFacet(p1, p2, p3));
        builder.append(appendFacet(p4, p3, p1));

        builder.append(appendFacet(p5, p6, p7));
        builder.append(appendFacet(p8, p7, p5));

        builder.append(appendFacet(p1, p5, p8));
        builder.append(appendFacet(p4, p8, p1));

        builder.append(appendFacet(p2, p6, p7));
        builder.append(appendFacet(p3, p7, p2));

        builder.append(appendFacet(p1, p2, p5));
        builder.append(appendFacet(p6, p5, p2));

        builder.append(appendFacet(p3, p4, p7));
        builder.append(appendFacet(p8, p7, p4));

        return builder.toString();
    }
    
    public static String exportRanlange(STL  stl, double heightSupplementaire, double heightSupplementaire2) {
        StringBuilder builder = new StringBuilder();
        Point3d startPoint = stl.getStartPoint();
        Point3d p1 = new Point3d(startPoint.x, startPoint.y, startPoint.z);
        Point3d p2 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y, startPoint.z);
        Point3d p3 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y, startPoint.z +  stl.getHeight() + heightSupplementaire);
        Point3d p4 = new Point3d( startPoint.x, startPoint.y, startPoint.z + stl.getHeight() +heightSupplementaire2 );

        Point3d p5 = new Point3d(startPoint.x, startPoint.y + stl.getLength(), startPoint.z);
        Point3d p6 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y + stl.getLength(), startPoint.z);
        Point3d p7 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y + stl.getLength(), startPoint.z +  stl.getHeight() + heightSupplementaire);
        Point3d p8 = new Point3d( startPoint.x, startPoint.y + stl.getLength(), startPoint.z + stl.getHeight()+heightSupplementaire2 );


        builder.append(appendFacet(p1, p2, p3));
        builder.append(appendFacet(p4, p3, p1));

        builder.append(appendFacet(p5, p6, p7));
        builder.append(appendFacet(p8, p7, p5));

        builder.append(appendFacet(p1, p5, p8));
        builder.append(appendFacet(p4, p8, p1));

        builder.append(appendFacet(p2, p6, p7));
        builder.append(appendFacet(p3, p7, p2));

        builder.append(appendFacet(p1, p2, p5));
        builder.append(appendFacet(p6, p5, p2));

        builder.append(appendFacet(p3, p4, p7));
        builder.append(appendFacet(p8, p7, p4));

        return builder.toString();
    }

    public static String generateRanlangeTourne(STL  stl, double heightSupplementaire) {
        StringBuilder builder = new StringBuilder();

        Point3d startPoint = stl.getStartPoint();
        Point3d p1 = new Point3d(startPoint.x, startPoint.y, startPoint.z);
        Point3d p2 = new Point3d(startPoint.x, startPoint.y + stl.getLength(), startPoint.z);
        Point3d p3 = new Point3d(startPoint.x, startPoint.y + stl.getLength(), startPoint.z + stl.getHeight());
        Point3d p4 = new Point3d(startPoint.x, startPoint.y, startPoint.z + stl.getHeight() + heightSupplementaire);

        Point3d p5 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y, startPoint.z);
        Point3d p6 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y + stl.getLength(), startPoint.z);
        Point3d p7 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y + stl.getLength(), startPoint.z + stl.getHeight());
        Point3d p8 = new Point3d(startPoint.x + stl.getWidth(), startPoint.y, startPoint.z + stl.getHeight() + heightSupplementaire);

        builder.append(appendFacet(p1, p2, p3));
        builder.append(appendFacet(p4, p3, p1));

        builder.append(appendFacet(p5, p6, p7));
        builder.append(appendFacet(p8, p7, p5));

        builder.append(appendFacet(p1, p5, p8));
        builder.append(appendFacet(p4, p8, p1));

        builder.append(appendFacet(p2, p6, p7));
        builder.append(appendFacet(p3, p7, p2));

        builder.append(appendFacet(p1, p2, p5));
        builder.append(appendFacet(p6, p5, p2));

        builder.append(appendFacet(p3, p4, p7));
        builder.append(appendFacet(p8, p7, p4));

        return builder.toString();
    }
    
        private static String appendFacet(Point3d p1, Point3d p2, Point3d p3) {
        StringBuilder builder = new StringBuilder();
        builder.append("facet normal 0 0 0\n");
        builder.append("outer loop\n");
        builder.append("vertex " + p1.x + " " + p1.y + " " + p1.z + "\n");
        builder.append("vertex " + p2.x + " " + p2.y + " " + p2.z + "\n");
        builder.append("vertex " + p3.x + " " + p3.y + " " + p3.z + "\n");
        builder.append("endloop\n");
        builder.append("endfacet\n");
        return builder.toString();
    }
    
    
}
