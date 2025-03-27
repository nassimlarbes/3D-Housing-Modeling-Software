package ca.ulaval.glo2004.Domain;

import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import ca.ulaval.glo2004.DataTransferObjects.AccessorieDTO;
import ca.ulaval.glo2004.DataTransferObjects.ChaletDTO;
import ca.ulaval.glo2004.Domain.accessorie.Accessorie;
import ca.ulaval.glo2004.Domain.accessorie.Door;
import ca.ulaval.glo2004.Domain.accessorie.Window;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import ca.ulaval.glo2004.Domain.wall.Polygone_;
import ca.ulaval.glo2004.Domain.wall.Wall;
import ca.ulaval.glo2004.Domain.Toit.WallOfRoof;
import ca.ulaval.glo2004.gui.DrawingPanel;
import ca.ulaval.glo2004.gui.MainWindow;


public class ChaletController implements Serializable {

    private Chalet chalet;
    private View view = View.TopView;
    private MainWindow mainWindow;
    private int accessorieSelectedIndex = -1;
    private DrawingPanel canvas;
    private String path = "";
    Stack<HashMap<UserAction, Object>> undoStack = new Stack<>();
    Stack<HashMap<UserAction, Object>> redoStack = new Stack<>();

    /**
     * cette liste contient les murs dans l'ordre necessaire pour chaque vue
     * il faut faire attention a l'ordre des murs durant l'affichage, car le mur qui est affiche en premier sera cache par les autres
     * pour chaque vue, on a un ordre different des murs selon comment on les voit
     */
    private final HashMap<View, Integer[]> viewToWallIndex = new HashMap<>() {{
        put(View.TopView, new Integer[]{0, 1, 2, 3});
        put(View.BackView, new Integer[]{0, 2, 3, 1});
        put(View.FrontView, new Integer[]{1, 2, 3, 0});
        put(View.RightView, new Integer[]{2, 0, 1, 3});
        put(View.LeftView, new Integer[]{3, 0, 1, 2});
    }};

    /**
     * cette liste contient les vues. elle est utilile lorsqu'on veut changer la vue
     */
    private final View[] views = new View[]{View.FrontView, View.BackView, View.LeftView, View.RightView};


    public ChaletController() {
        this.chalet = new Chalet(this);
    }

    public ChaletController(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.chalet = new Chalet(this);
        this.mainWindow.showChaletDetail(new ChaletDTO(this.chalet));
    }

    /**
     * cette fontion retourne le mur qui est en face de la vue
     *
     * @return le mur qui est en face de la vue
     */
    public Wall getWallInFront() {
        Integer[] indexes = viewToWallIndex.get(view);
        int wallIndex = indexes[3];
        return this.chalet.getWalls().get(wallIndex);
    }

    public List<Polygone_> drawWalls() {
        //créer une liste de polygones qui contient les murs
        List<Polygone_> polygones = new ArrayList<>();
        //récupérer les murs de chalet
        List<Wall> walls = chalet.getWalls();
        //avoir l'order des murs selon la vue
        Integer[] indexes = viewToWallIndex.get(view);
        //parcourir les murs et les ajouter à la liste des polygones
        for (Integer index : indexes) {
            //recalculer la position du mur (c'est necessaire si l'utilisateur a changé la vue)
            walls.get(index).calculatePosition();
            //vérfier si le mur est visible
            if (walls.get(index).isVisible()) {
                //récupérer la vue du mur et l'ajouter à la liste des polygones
                polygones.add(walls.get(index).getView());
            }
        }

        //add roof
        List<WallOfRoof> roof = chalet.getWallsOfRool();
        for (WallOfRoof wallOfRoof : roof) {
            polygones.add(wallOfRoof.draw());
        }
        return polygones;
    }

    public void setHeight(Imperial height) {
        //save old height
        saveUndoAction(UserAction.CHANGE_HEIGHT, chalet.getHeight());
        //mise à jour de la hauteur du chalet
        chalet.setHeight(height);
        //parcourir les murs et mettre à jour les accessoires
        for (Wall wall : chalet.getWalls()) {
            wall.updateAccessories();
        }
    }
    public Wall getWall(int index){
        return  this.chalet.getWalls().get(index);
    }

    public void addNewDoor() {
        Wall w = getWallInFront();
        Accessorie accessoriedoor = new Door(w);
        w.getAccessories().add(accessoriedoor);
        this.setSelectedAccessorie(w.getAccessories().size() - 1);
        //save action
        saveUndoAction(UserAction.ADD_ACCESSORIE, accessoriedoor);
    }
    
    public void addNewDoor(double x,double y){
        Wall w = getWallInFront();
        Accessorie accessoriedoor = new Door(w);
        accessoriedoor.setPosition(new Point2d(x,y));
        w.getAccessories().add(accessoriedoor);
        this.setSelectedAccessorie(w.getAccessories().size() - 1);
        //save action
        saveUndoAction(UserAction.ADD_ACCESSORIE, accessoriedoor);
    }

    public void addNewWindow() {
        Wall w = getWallInFront();
        Accessorie accessorieWindow = new Window(w);
        w.getAccessories().add(accessorieWindow);
        this.setSelectedAccessorie(w.getAccessories().size() - 1);
        //save action
        saveUndoAction(UserAction.ADD_ACCESSORIE, accessorieWindow);
    }
    
     public void addNewWindow(double x,double y){
        Wall w = getWallInFront();
        Accessorie accessorieWindow = new Window(w);
        accessorieWindow.setPosition(new Point2d(x,y));
        w.getAccessories().add(accessorieWindow);
        this.setSelectedAccessorie(w.getAccessories().size() - 1);
        //save action
        saveUndoAction(UserAction.ADD_ACCESSORIE, accessorieWindow);
    }

    public List<Accessorie> getAccessories() {
        if (view == View.TopView)
            return new ArrayList<>();
        Wall w = getWallInFront();
        return w.getAccessories();
    }

    public void removeAccessorie() {
        if (accessorieSelectedIndex == -1)
            return;
        Wall w = getWallInFront();
        Accessorie accessorie = w.getAccessories().get(accessorieSelectedIndex);
        w.getAccessories().remove(accessorie);
        this.setSelectedAccessorie(-1);
        //save action
        saveUndoAction(UserAction.REMOVE_ACCESSORIE, accessorie);
    }

    public void changeView(int index) {
        //save old view
        Integer[] indexes = viewToWallIndex.get(view);
        int wallIndex = indexes[index];
        if (views[wallIndex] != view)
            setView(views[wallIndex]);
    }

    public View getView() {
        return view;
    }

    public void setView(View new_view) {
        //save action
        saveUndoActionWithout(UserAction.CHANGE_VIEW, view);
        this.view = new_view;
        this.chalet.updateWallsPosition();
        this.mainWindow.activerDesactiverPorteFenetreBtn(view != View.TopView);
    }

    public void setFrontIsPrincipal(boolean frontIsPrincipal) {
        //save action
        saveUndoAction(UserAction.CHANGE_FRONT_IS_PRINCIPAL, chalet.isFrontIsPrincipal());
        this.chalet.setFrontIsPrincipal(frontIsPrincipal);
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setLengthFacade(Imperial lengthFacade) {
        saveUndoAction(UserAction.CHANGE_LENGTH_FACADE, chalet.getLengthFacade());
        this.chalet.setLengthFacade(lengthFacade);
    }

    public void setLengthCote(Imperial lengthCote) {
        saveUndoAction(UserAction.CHANGE_LENGTH_COTE, chalet.getLengthCote());
        this.chalet.setLengthCote(lengthCote);
    }

    public void setThickness(Imperial thickness) {
        saveUndoAction(UserAction.CHANGE_THICKNESS, chalet.getThickness());
        this.chalet.setThickness(thickness);
    }

    public void setSidesColor(Color color) {
        saveUndoAction(UserAction.CHANGE_SIDES_COLOR, chalet.getSidesColor());
        this.chalet.setSidesColor(color);
    }

    public void setFrontColor(Color color) {
        saveUndoAction(UserAction.CHANGE_FRONT_COLOR, chalet.getFrontColor());
        this.chalet.setFrontColor(color);
    }

    public void setMinSeparationDistance(Imperial minSeparationDistance) {
        saveUndoAction(UserAction.CHANGE_MIN_SEPARATION_DISTANCE, chalet.getMinSeparationDistance());
        this.chalet.setMinSeparationDistance(minSeparationDistance);
    }

    public void setGroove(Imperial groove) {
        saveUndoAction(UserAction.CHANGE_GROOVE, chalet.getGroove());
        this.chalet.setGroove(groove);
    }

    public void setAccessoriesColor(Color accessoriesColor) {
        saveUndoAction(UserAction.CHANGE_ACCESSORIES_COLOR, chalet.getAccessoriesColor());
        this.chalet.setAccessoriesColor(accessoriesColor);
    }

    public void setName(String name) {
        saveUndoAction(UserAction.CHANGE_NAME, this.chalet.getName());
        this.chalet.setName(name);
    }
    public void setDirection_toit(boolean direction_toit) {
        saveUndoAction(UserAction.CHANGE_ROOF_DIRECTION, this.chalet.isDirection_toit());
        this.chalet.setDirection_toit(direction_toit);
    }

    public void setVisibility(boolean visible, Integer index) {
        this.chalet.getWalls().get(index).setVisible(visible);
    }

    public void setAngle(double angle) {
        saveUndoAction(UserAction.CHANGE_ANGLE, this.chalet.getAngle());
        this.chalet.setAngle(angle);
    }

    public double getAngle() {
        return this.chalet.getAngle();
    }

    public Imperial getLengthFacade() {
        return this.chalet.getLengthFacade();
    }

    public Imperial getLengthCote() {
        return this.chalet.getLengthCote();
    }

    public Imperial getThickness() {
        return this.chalet.getThickness();
    }

    public Imperial getHeight() {
        return this.chalet.getHeight();
    }

    public Imperial getGroove() {
        return this.chalet.getGroove();
    }

    public Imperial getMinSeparationDistance() {
        return this.chalet.getMinSeparationDistance();
    }

    /**
     * cette fonction calcule la longueur de la facade du chalet en prenant en compte la longueur de la facade +  l'epaisseur du mur (si elle existe)
     *
     * @return
     */
    public double getReelLengthFacade() {
        if (this.view == View.RightView || this.view == View.LeftView)
            return this.chalet.getLengthCote().doubleValue() + (this.chalet.isFrontIsPrincipal() ? this.chalet.getThickness().doubleValue() : 0);
        else
            return this.chalet.getLengthFacade().doubleValue() + (this.chalet.isFrontIsPrincipal() ? 0 : this.chalet.getThickness().doubleValue());
    }

    public double getReelLengthCote() {
        if (this.view == View.TopView)
            return this.chalet.getLengthCote().doubleValue() + (this.chalet.isFrontIsPrincipal() ? this.chalet.getThickness().doubleValue() : 0);
        else
            return this.chalet.getHeight().doubleValue();
    }

    public void setSelectedAccessorie(int accessorieIndex) {
        if (accessorieIndex == -1) {
            this.accessorieSelectedIndex = -1;
            for (Accessorie accessorie : this.getAccessories()) {
                accessorie.setSelectionStatus(false);
            }
            mainWindow.hideAccessorieDetail();
        } else {
            this.accessorieSelectedIndex = accessorieIndex;
            for (Accessorie accessorie : this.getAccessories()) {
                accessorie.setSelectionStatus(false);
            }
            this.getAccessories().get(this.accessorieSelectedIndex).setSelectionStatus(true);
            mainWindow.showAccessorieDetail(new AccessorieDTO(getAccessories().get(this.accessorieSelectedIndex)));
        }
    }

    public int getSelectedAccessorie() {
        return this.accessorieSelectedIndex;
    }

    public void updateSelectedAccessorie(AccessorieDTO accessorieDTO) {
        Accessorie accessorie = this.getAccessories().get(this.accessorieSelectedIndex);
        //save accessorie
        saveUndoAction(UserAction.CHANGE_ACCESSORIE, new AccessorieDTO(accessorie));
        accessorie.setPosition(new Point2d(accessorieDTO.Position.x, accessorieDTO.Position.y));
        accessorie.setHeight(accessorieDTO.Height);
        accessorie.setLength(accessorieDTO.Length);
        this.setSelectedAccessorie(this.accessorieSelectedIndex);
    }

    public void updateSelectedAccessorieWithOutUndo(AccessorieDTO accessorieDTO) {
        Accessorie accessorie = this.getAccessories().get(this.accessorieSelectedIndex);
        accessorie.setPosition(new Point2d(accessorieDTO.Position.x, accessorieDTO.Position.y));
        accessorie.setHeight(accessorieDTO.Height);
        accessorie.setLength(accessorieDTO.Length);
        this.setSelectedAccessorie(this.accessorieSelectedIndex);
    }

    public void exportSTLFini(String path) {
        List<Wall> walls = this.chalet.getWalls();
        List<WallOfRoof> roof = this.chalet.getWallsOfRool();
        for (Wall wall : walls) {
            wall.exportToSTL_Fini(path);
        }
        for (WallOfRoof wallOfRoof : roof) {
            wallOfRoof.exportToSTL_Fini(path);
        }
    }

    public void exportSTLBrut(String path) {
        List<Wall> walls = this.chalet.getWalls();
        for (Wall wall : walls) {
            wall.exportToSTL_Brut(path);
        }

        for (WallOfRoof wallOfRoof : this.chalet.getWallsOfRool()) {
            wallOfRoof.exportToSTL_Brut(path);
        }
    }
    
    public void exportSTLRetrait(String path) {
        List<Wall> walls = this.chalet.getWalls();
        for (Wall wall : walls) {
            wall.exportToSTL_Retrait(path);
        }
    }

    public void newChalet() {
        this.chalet = new Chalet(this);
        this.mainWindow.showChaletDetail(new ChaletDTO(this.chalet));
        //clear undo redo stack
        this.undoStack.clear();
        this.redoStack.clear();
    }


    private void saveUndoAction(UserAction userAction, Object o) {
        HashMap<UserAction, Object> action = new HashMap<>();
        action.put(userAction, o);
        undoStack.push(action);
        redoStack.clear();
    }

    private void saveUndoActionWithout(UserAction userAction, Object o) {
        HashMap<UserAction, Object> action = new HashMap<>();
        action.put(userAction, o);
        undoStack.push(action);
    }

    private void makeAction(UserAction userAction, Object object) {
        //do action
        switch (userAction) {
            case CHANGE_HEIGHT:
                this.setHeight((Imperial) object);
                break;
            case CHANGE_LENGTH_FACADE:
                this.setLengthFacade((Imperial) object);
                break;
            case CHANGE_LENGTH_COTE:
                this.setLengthCote((Imperial) object);
                break;
            case CHANGE_THICKNESS:
                this.setThickness((Imperial) object);
                break;
            case CHANGE_GROOVE:
                this.setGroove((Imperial) object);
                break;
            case CHANGE_MIN_SEPARATION_DISTANCE:
                this.setMinSeparationDistance((Imperial) object);
                break;
            case CHANGE_FRONT_IS_PRINCIPAL:
                this.setFrontIsPrincipal((Boolean) object);
                break;
            case CHANGE_ACCESSORIES_COLOR:
                this.setAccessoriesColor((Color) object);
                break;
            case CHANGE_FRONT_COLOR:
                this.setFrontColor((Color) object);
                break;
            case CHANGE_SIDES_COLOR:
                this.setSidesColor((Color) object);
                break;
            case CHANGE_NAME:
                this.setName((String) object);
                break;
            case CHANGE_VIEW:
                this.setView((View) object);
                break;
            case CHANGE_ROOF_DIRECTION:
                this.setDirection_toit((Boolean) object);
                break;
            case CHANGE_ANGLE:
                this.setAngle((Double) object);
                break;
            case CHANGE_GRIDE_DISTANCE_ENTER_LIGN:
                this.setGrille_line_gap((Imperial) object);
                break;
            case CHANGE_GRIDE_DISTANCE_ENTER_COLUMN:
                this.setGrille_column_gap((Imperial) object);
                break;
            case CHANGE_ACCESSORIE:
                AccessorieDTO accessorieDTO = (AccessorieDTO) object;
                for (Accessorie accessorie:this.getAccessories()){
                    if(accessorie.getId().equals(accessorieDTO.Id)){
                        saveUndoActionWithout(UserAction.CHANGE_ACCESSORIE, new AccessorieDTO(accessorie));
                        accessorie.setPosition(new Point2d(accessorieDTO.Position.x, accessorieDTO.Position.y));
                        accessorie.setHeight(accessorieDTO.Height);
                        accessorie.setLength(accessorieDTO.Length);
                    }
                }
                break;
            case ADD_ACCESSORIE:
                this.getAccessories().remove((Accessorie) object);
                saveUndoActionWithout(UserAction.REMOVE_ACCESSORIE, object);
                break;
            case REMOVE_ACCESSORIE:
                this.getAccessories().add((Accessorie) object);
                saveUndoActionWithout(UserAction.ADD_ACCESSORIE, object);
                break;
        }
    }

    public void undo(){
        if (undoStack.isEmpty())
            return;
        HashMap<UserAction, Object> action = undoStack.pop();
        UserAction userAction = action.keySet().iterator().next();
        Object object = action.get(userAction);
        makeAction(userAction, object);
        action = undoStack.pop();
        redoStack.push(action);
        this.mainWindow.showChaletDetail(new ChaletDTO(this.chalet));

    }

    public void redo(){
        if (redoStack.isEmpty())
            return;
        HashMap<UserAction, Object> action = redoStack.pop();
        UserAction userAction = action.keySet().iterator().next();
        Object object = action.get(userAction);
        makeAction(userAction, object);
        this.mainWindow.showChaletDetail(new ChaletDTO(this.chalet));
    }


    public void setCanvas(DrawingPanel canvas) {
        this.canvas = canvas;
    }

    public void setGrille_line_gap(Imperial grille_line_gap) {
        saveUndoAction(UserAction.CHANGE_GRIDE_DISTANCE_ENTER_LIGN, grille_line_gap);
        this.canvas.setGrille_line_gap(grille_line_gap);
    }

    public void setGrille_column_gap(Imperial grille_column_gap) {
        saveUndoAction(UserAction.CHANGE_GRIDE_DISTANCE_ENTER_COLUMN, grille_column_gap);
        this.canvas.setGrille_column_gap(grille_column_gap);
    }
    
    public void setPath(String path){
        this.path = path;
    }
    public String getPath(){
        return this.path;
    }
    public boolean saveChalet(){
        String filePath = this.path + "/" + this.chalet.getName() + ".chalet";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(new ChaletDTO(this.chalet));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean loadChalet(String path){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            ChaletDTO chaletDTO = (ChaletDTO) ois.readObject();
            this.chalet = new Chalet(chaletDTO, this);
            this.mainWindow.showChaletDetail(new ChaletDTO(this.chalet));
            this.undoStack.clear();
            this.redoStack.clear();
            this.path = path.substring(0, path.lastIndexOf("\\"));
            System.out.println(this.path);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
    





