package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.DataTransferObjects.AccessorieDTO;
import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import ca.ulaval.glo2004.Domain.ChaletController;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import ca.ulaval.glo2004.Domain.wall.Polygone_;
import ca.ulaval.glo2004.Domain.wall.Wall;
import java.awt.BasicStroke;
import java.awt.*;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;


public class DrawingPanel extends JPanel implements Serializable {

    private int lastX, lastY;
    private Image background, door, window;
    private ChaletController chaletController;
    int translateX = 0, translateY = 0;
    double zoom = 1;
    int width, height ;
    int mouseX,mouseY;
    private boolean grilleActive = true;
    private Imperial grille_line_gap = new Imperial(10, 6);
    private Imperial grille_column_gap = new Imperial(10, 6);
    private Accessorie selectedAccessorie;
    private double x_accessorie = 0 ,y_accessorie = 0;
    private double zoomIncrement = 0.1;
    private boolean showBackground = true;
    private MainWindow mainWindow;
    public DrawingPanel(ChaletController chaletController,MainWindow mainWindow) {
        this.chaletController = chaletController;
        this.background = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/background2.jpg"));
        this.door = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/door.png"));
        this.window = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/window.png"));
        this.setDoubleBuffered(true);
        this.chaletController.setCanvas(this);
        this.mainWindow = mainWindow;

        addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    adjustScale();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Enregistrez le point de départ du glissement
                lastX = e.getX();
                lastY = e.getY();

                //récupérer la liste des accessoires
                List<Accessorie> accessories = chaletController.getAccessories();
                //parcourir la liste des accessoiresq
                for (int i = 0; i < accessories.size(); i++) {
                    Accessorie accessory = accessories.get(i);
                    //oon récupère la vue de l'accessoire
                    Polygone_ view = accessory.draw();
                    //zoomer et translater la vue
                    view.zoomAndTranslate(zoom, translateX, translateY);
                    //vérifier si le point cliqué est dans la vue
                    if(view.contains(mouseX , mouseY )){
                        //si oui, sélectionner l'accessoire (sauvegarder l'accessoire sélectionné)
                        selectedAccessorie = accessory;
                        //sauvegarder la position de l'accessoire (initiale)
                        x_accessorie = accessory.getPosition().x ;
                        y_accessorie = accessory.getPosition().y ;
                        //notifier la fenêtre principale
                        chaletController.setSelectedAccessorie(i);
                        chaletController.updateSelectedAccessorie(new AccessorieDTO(selectedAccessorie));
                        //changer le curseur
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        //redessiner
                        repaint();
                        return;
                    }
                }
                chaletController.setSelectedAccessorie(-1);

                //si on est ici, l'utilisateur n'a pas cliqué sur un accessoire
                //récupérer la liste des murs
                List<Polygone_> walls = chaletController.drawWalls();
                //parcourir la liste des murs (en inverse, car on veut sélectionner le mur le plus en avant)
                for (int j = 3; j >= 0; j--) {
                    //récupérer le mur
                    Polygone_ wall = walls.get(j);
                    //zoomer et translater le mur
                    wall.zoomAndTranslate(zoom, translateX, translateY);
                    //vérifier si le point cliqué est dans le mur
                    if(wall.contains(mouseX, mouseY)){
                        //si oui, sélectionner le mur
                        chaletController.changeView(j);

                        //redessiner
                        repaint();
                        //quittez la méthode (pour ne pas sélectionner un accessoire)
                        return;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println(e.getPoint());
                selectedAccessorie = null;
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }



        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double oldZoom = zoom;
                if (e.getWheelRotation() < 0) {
                    zoom *= (1 + zoomIncrement);
                } else {
                    zoom /= (1 + zoomIncrement);
                }
                zoom = Math.max(1.0, zoom);

                adjustTranslation(e.getPoint(), oldZoom, zoom);
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {

                    int deltaX = e.getX() - lastX;
                    int deltaY = e.getY() -  lastY;
                    //vérifier si l'utilisateur a sélectionné un accessoire
                    if(selectedAccessorie != null){
                        //si oui, déplacer l'accessoire
                    x_accessorie = (x_accessorie * zoom + deltaX) / zoom;
                    y_accessorie = (y_accessorie * zoom + deltaY) / zoom;
                    selectedAccessorie.setPosition(new Point2d(x_accessorie, y_accessorie));
                    mainWindow.showAccessorieDetail(new AccessorieDTO(selectedAccessorie));
                } else {
                    translateX += deltaX;
                    translateY += deltaY;
                }

                    // Mettez à jour le point de départ du glissement
                lastX = e.getX();
                lastY = e.getY();

                    //redessiner
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                mouseX = e.getX();
                mouseY = e.getY();
                repaint();
            }


        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Now you can get the actual size
                width = getWidth();
                height = getHeight();
                centreChalet();
            }
        });

    }

    private void adjustTranslation(Point mousePt, double oldZoom, double newZoom) {
        double zoomDelta = newZoom / oldZoom;
        translateX = (int) ((translateX - mousePt.x) * zoomDelta + mousePt.x);
        translateY = (int) ((translateY - mousePt.y) * zoomDelta + mousePt.y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(showBackground)
            g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        else{
            g2d.setColor(new Color(1, 3, 24));
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        }


        List<Polygone_> walls = chaletController.drawWalls();
        Integer selectedWall_Index = -1;
        for (int j = 0; j < walls.size(); j++) {
            Polygone_ wall = walls.get(j);
            //Zoom et translation sur murs
            wall.zoomAndTranslate(zoom, translateX, translateY);

            //Dessin contour mur quand souris sur le mur 
            g2d.setColor(wall.getColor());
            g2d.fillPolygon(wall);
            if (wall.contains(mouseX, mouseY)) {
                selectedWall_Index = j;
            }
        }

        if (selectedWall_Index != -1) {
            Polygone_ selectedWall = walls.get(selectedWall_Index);
            g2d.setColor(Color.white);
            g2d.setStroke(new BasicStroke((float) (1/zoom)));
            g2d.drawPolygon(selectedWall);
        }

        List<Accessorie> accessories = chaletController.getAccessories();
        if(!accessories.isEmpty()){
            List<Polygone_> accessories_validated = new ArrayList<>();
            List<Polygone_> accessories_not_validated = new ArrayList<>();
            Polygone_ selected = null;

            for (Accessorie accessory : accessories) {
                Polygone_ view = accessory.draw();
                view.zoomAndTranslate(zoom, translateX, translateY);
//                g2d.setColor(view.getColor());
//                if (view.contains(mouseX, mouseY)) {
//                    g2d.setColor(view.getColor().darker());
//                }
//                g2d.fillPolygon(view);

                Point position = view.getPoints().get(0);
                int height = view.getPoints().get(3).y -view.getPoints().get(0).y  ;
                int length = view.getPoints().get(1).x - view.getPoints().get(0).x;
                //check if the accessory is a instance of door class
                if(accessory.getClass().getSimpleName().equals("Door"))
                    g2d.drawImage(door, position.x, position.y, length, height, this);
                else
                    g2d.drawImage(window, position.x, position.y, length, height, this);



                if (accessory.isSelectionStatus()) {
                    selected = view;
                } else if (accessory.isValidate()) {
                    accessories_validated.add(view);
                } else {
                    accessories_not_validated.add(view);
                }
            }
            g2d.setStroke(new BasicStroke(1.5f));
            g2d.setColor(new Color(46, 204, 113));
            //draw validate accessories
            for (Polygone_ view : accessories_validated) {
                g2d.drawPolygon(view);
            }

            g2d.setColor(new Color(231, 76, 60));
            //invalidate accessories
            for (Polygone_ view : accessories_not_validated) {
                g2d.drawPolygon(view);
            }

            if (selected != null) {
                float[] dashPattern = {5, 5};
                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10, dashPattern, 0));
                g2d.setColor(new Color(241, 196, 15));
                g2d.drawPolygon(selected);
            }
        }

        drawGrid(g2d);


        //afficher les dimensions du mur sélectionné
        int pos_x = 10, pos_y = 20, delta = 20;
        if (selectedWall_Index != -1 && selectedWall_Index < 4) {
            Wall selectedWall = chaletController.getWall(selectedWall_Index);
            g2d.setColor(Color.white);
            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            g2d.drawString("Longueur : " + selectedWall.getLength().toString(), pos_x, pos_y);
            g2d.drawString("Hauteur : " + selectedWall.getHeight().toString(), pos_x, pos_y + delta);
            g2d.drawString("Épaisseur : " + selectedWall.getThickness().toString() + "''", pos_x, pos_y + delta * 2);
        }

        //afficher la position de la souris en bas à gauche
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.LAYOUT_LEFT_TO_RIGHT, 12));
        g2d.drawString("X : " + mouseX + " Y : " + mouseY, pos_x, this.getHeight() - 10);
    }

    public void centreChalet() {
        translateX = (int) (this.width - chaletController.getReelLengthFacade() * zoom) / 2;
        translateY = (int) (this.height - chaletController.getReelLengthCote() * zoom) / 2;
        repaint();
    }

    private void drawGrid(Graphics2D g2d) {
        if (!grilleActive)
            return;
            g2d.setStroke(new BasicStroke(1.f));
            //g2d.scale(zoom, zoom);
            g2d.setPaint(Color.LIGHT_GRAY.darker());
            Dimension adjustingDimension = new Dimension(this.getWidth(), this.getHeight());
            //Dessin lignes horizontales
            double nbLignes = adjustingDimension.getHeight() / this.grille_line_gap.doubleValue();
            for (int row = 1; row <= nbLignes ; row++)
                g2d.drawLine(0, (int) (row * this.grille_line_gap.doubleValue()), (int) adjustingDimension.getWidth(), (int) (row * this.grille_line_gap.doubleValue()));

            //Dessin ligne verticale grille
            double nbColonnes = adjustingDimension.getWidth() / this.grille_column_gap.doubleValue();
            for (int col = 1; col <= (int) adjustingDimension.getWidth() / this.grille_column_gap.doubleValue(); col++)
                g2d.drawLine((int) (col *this.grille_column_gap.doubleValue()), 0, (int) (col * this.grille_column_gap.doubleValue()), (int) adjustingDimension.getHeight());
    }

    public void adjustScale() {
        translateX = 15;
        translateY = 15;
        double lengthFacade = chaletController.getReelLengthFacade();
        double lengthCote = chaletController.getReelLengthCote();
        double scaleX = (double) (this.width - (translateX * 2)) / lengthFacade;
        double scaleY = (double) (this.height - (translateY * 2)) / lengthCote;
        if (scaleX > scaleY) {
            zoom = scaleY;
            translateX = (int) ((this.width - (lengthFacade * zoom)) / 2);
        } else if (scaleX < scaleY) {
            zoom = scaleX;
            translateX = (int) ((this.height - (lengthCote * zoom)) / 2);

        }
        repaint();
    }

    public void setGrilleActive(boolean grilleActive) {
        this.grilleActive = grilleActive;
    }


    public void setGrille_line_gap(Imperial grille_line_gap) {
        this.grille_line_gap = grille_line_gap;
    }

    public void setGrille_column_gap(Imperial grille_column_gap) {
        this.grille_column_gap = grille_column_gap;
    }

    public Imperial getGrille_line_gap() {
        return grille_line_gap;
    }

    public Imperial getGrille_column_gap() {
        return grille_column_gap;
    }

    public void setShowBackground(boolean showBackground) {
        this.showBackground = showBackground;
    }


    public void addNewDor(double mouseX, double mouseY) {
        //check if the door is in the wall
        Wall w = chaletController.getWallInFront();
        Polygone_ wall = w.getView();
        wall.zoomAndTranslate(zoom, translateX, translateY);
        if (!wall.contains(mouseX, mouseY)) {
            return;
        }
        Point2d position = new Point2d(mouseX, mouseY);
        position.x = position.x - wall.getPoints().get(0).x;
        position.y = position.y - wall.getPoints().get(0).y;

        position.x = position.x / zoom;
        position.y = position.y / zoom;

        chaletController.addNewDoor(position.x, position.y);
        repaint();
    }


    public void addNewWindows(double mouseX, double mouseY) {
        //check if the door is in the wall
        Wall w = chaletController.getWallInFront();
        Polygone_ wall = w.getView();
        wall.zoomAndTranslate(zoom, translateX, translateY);
        if (!wall.contains(mouseX, mouseY)) {
            return;
        }
        Point2d position = new Point2d(mouseX, mouseY);
        position.x = position.x - wall.getPoints().get(0).x;
        position.y = position.y - wall.getPoints().get(0).y;

        position.x = position.x / zoom;
        position.y = position.y / zoom;

        chaletController.addNewWindow(position.x, position.y);
        repaint();
    }
}