/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.DataTransferObjects.AccessorieDTO;
import ca.ulaval.glo2004.DataTransferObjects.ChaletDTO;
import ca.ulaval.glo2004.Domain.ChaletController;
import ca.ulaval.glo2004.Domain.Imperial;
import ca.ulaval.glo2004.Domain.View;
import ca.ulaval.glo2004.Domain.wall.Point2d;
import java.awt.*;
import java.util.UUID;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends javax.swing.JFrame {
    ChaletController chaletController;
    DrawingPanel canv;

    private void repaint_() {
        canv.repaint();
    }

    private void waringDialog(String message){
        javax.swing.JOptionPane.showMessageDialog(this, message, "Attention", javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    private int getInteger(String text){
        try{
            return Integer.parseInt(text);
        }catch(NumberFormatException e){
            waringDialog("Veuillez entrer un nombre valide");
            return 0;
        }
    }

    private void newProjet(){
        int response = JOptionPane.showConfirmDialog(null, "Voulez vous créer un nouveau chalet?", "Nouveau", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            this.chaletController.newChalet();
            repaint_();
        }
    }

    private void saveProjet(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select a folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setApproveButtonText("Save");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().toString();
            chaletController.setPath(path);
            chaletController.saveChalet();
        }
    }

    private void openProjet(){
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select the chalet file");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Chalet file", "chalet");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().toString();
            chaletController.loadChalet(path);
            repaint_();
        }
    }

    private double getDouble(String text){
        try{
            return Double.parseDouble(text);
        }catch(NumberFormatException e){
            waringDialog("Veuillez entrer un nombre valide");
            return 0;
        }
    }


    private void updateAccessorie(){
        try {
            Point2d position = new Point2d(getDouble(AccesoireX.getText().replace(',','.')), getDouble(AccesoireY.getText().replace(',','.')));
            Imperial length = new Imperial("0",accesoiresLongueurInch.getText(), accesoiresLongueurFraction.getText());
            Imperial height = new Imperial("0",accesoiresHauteurInch.getText(), accesoiresHauteurFraction.getText());
            AccessorieDTO accessorieDTO = new AccessorieDTO(UUID.fromString(uuid.getText()), position, height, length);
            this.chaletController.updateSelectedAccessorie(accessorieDTO);
            repaint_();
            updateaccesoiresHauteurInch(height);
            updateaccesoiresLongueurInch(length);
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
        }
    }

    private void updateLengthFacade(Imperial imperial){
        LengthFacadeFoot.setText(imperial.getFeet().toString());
        LengthFacadeInch.setText(imperial.getInch().toString());
        LengthFacadefraction.setText(imperial.fractionToString());
    }

    private void updateHeight(Imperial imperial){
        HeightFoot.setText(imperial.getFeet().toString());
        HeightInch.setText(imperial.getInch().toString());
        HeightFraction.setText(imperial.fractionToString());
    }

    private void updateLengthCote(Imperial imperial){
        LengthCoteFoot.setText(imperial.getFeet().toString());
        LengthCoteInch.setText(imperial.getInch().toString());
        LengthCotefraction.setText(imperial.fractionToString());
    }

    private void updateThickness(Imperial imperial){
        thicknessInch.setText(imperial.getInch() + imperial.getFeet() * 12 + "");
        thicknessFraction.setText(imperial.fractionToString());
    }

    private void updateDistanceMinimun(Imperial imperial){
        distanceMinimunInch.setText(imperial.getInch() + imperial.getFeet() * 12 + "");
        distanceMinimunFraction.setText(imperial.fractionToString());
    }

    private void updateGroove(Imperial imperial){
        grooveInch.setText(imperial.getInch() + imperial.getFeet() * 12 + "");
        grooveFraction.setText(imperial.fractionToString());
    }

    private void updateDistanceBetweenLinesGrid(Imperial imperial){
        distanceBetweenLinesInch.setText(imperial.getInch() + imperial.getFeet() * 12 + "");
        distanceBetweenLinesIFraction.setText(imperial.fractionToString());
    }

    private void updateDistanceBetweenLinesComuns(Imperial imperial){
        distanceBetweenComunsInch.setText(imperial.getInch() + imperial.getFeet() * 12 + "");
        distanceBetweenComunsFraction.setText(imperial.fractionToString());
    }

    private void updateaccesoiresLongueurInch(Imperial imperial){
        accesoiresLongueurInch.setText(imperial.getInch() + imperial.getFeet() * 12 + "");
        accesoiresLongueurFraction.setText(imperial.fractionToString());
    }

    private void updateaccesoiresHauteurInch(Imperial imperial){
        accesoiresHauteurInch.setText(imperial.getInch() + imperial.getFeet() * 12 + "");
        accesoiresHauteurFraction.setText(imperial.fractionToString());
    }

    private void calculLengthFacade(){
        try {
            Imperial imperial = new Imperial(LengthFacadeFoot.getText(), LengthFacadeInch.getText(), LengthFacadefraction.getText());
            this.chaletController.setLengthFacade(imperial);
            updateLengthFacade(imperial);
            repaint_();
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
            this.updateLengthFacade(this.chaletController.getLengthFacade());
        }
    }

    private void calculHeight(){
        try {
            Imperial imperial = new Imperial(HeightFoot.getText(), HeightInch.getText(), HeightFraction.getText());
            this.chaletController.setHeight(imperial);
            updateHeight(imperial);
            repaint_();
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
            this.updateHeight(this.chaletController.getHeight());
        }
    }

    private void calculLengthCote(){
        try {
            Imperial imperial = new Imperial(LengthCoteFoot.getText(), LengthCoteInch.getText(), LengthCotefraction.getText());
            this.chaletController.setLengthCote(imperial);
            updateLengthCote(imperial);
            repaint_();
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
            this.updateLengthCote(this.chaletController.getLengthCote());
        }
    }

    private void calculThickness(){
        try {
            Imperial imperial = new Imperial("0",thicknessInch.getText(), thicknessFraction.getText());
            this.chaletController.setThickness(imperial);
            updateThickness(imperial);
            repaint_();
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
            this.updateThickness(this.chaletController.getThickness());
        }
    }

    private void calculDistanceMinimun(){
        try {
            Imperial imperial = new Imperial("0",distanceMinimunInch.getText(), distanceMinimunFraction.getText());
            this.chaletController.setMinSeparationDistance(imperial);
            updateDistanceMinimun(imperial);
            repaint_();
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
            this.updateDistanceMinimun(this.chaletController.getMinSeparationDistance());
        }
    }

    private void calculGroove(){
        try {
            Imperial imperial = new Imperial("0",grooveInch.getText(), grooveFraction.getText());
            this.chaletController.setGroove(imperial);
            this.updateGroove(imperial);
            repaint_();
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
            this.updateGroove(this.chaletController.getGroove());
        }
    }

    private void calculDistanceBetweenLinesGrid(){
        try {
            Imperial imperial = new Imperial("0",distanceBetweenLinesInch.getText(), distanceBetweenLinesIFraction.getText());
            this.chaletController.setGrille_line_gap(imperial);
            this.updateDistanceBetweenLinesGrid(imperial);
            repaint_();
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
            this.updateDistanceBetweenLinesGrid(this.canv.getGrille_line_gap());
        }
    }

    private void calculDistanceBetweenLinesComuns(){
        try {
            Imperial imperial = new Imperial("0",distanceBetweenComunsInch.getText(), distanceBetweenComunsFraction.getText());
            this.chaletController.setGrille_column_gap(imperial);
            this.updateDistanceBetweenLinesComuns(imperial);
            repaint_();
        }catch (IllegalArgumentException e) {
            waringDialog(e.getMessage());
            this.updateDistanceBetweenLinesComuns(this.canv.getGrille_column_gap());
        }
    }



    public MainWindow() {
        initComponents();
        this.chaletController = new ChaletController(this);
        canv = new DrawingPanel(this.chaletController,this);
        mainPanel.add(canv, BorderLayout.CENTER);
        canv.centreChalet();
        this.setEnableAccessorieDetail(false);
        this.updateDistanceBetweenLinesGrid(this.canv.getGrille_line_gap());
        this.updateDistanceBetweenLinesComuns(this.canv.getGrille_column_gap());
    }

    private void setEnableAccessorieDetail(boolean enable){
        uuid.setEnabled(enable);
        AccesoireX.setEnabled(enable);
        AccesoireY.setEnabled(enable);
        accesoiresLongueurInch.setEnabled(enable);
        accesoiresHauteurInch.setEnabled(enable);
        accesoiresHauteurFraction.setEnabled(enable);
        accesoiresLongueurFraction.setEnabled(enable);
        deleteBtn.setEnabled(enable);

    }


    public void showAccessorieDetail(AccessorieDTO accessorieDTO){
        setEnableAccessorieDetail(true);
        uuid.setText(accessorieDTO.Id.toString());
        AccesoireX.setText(String.format("%.2f", (float)accessorieDTO.Position.x)  );
        AccesoireY.setText(String.format("%.2f", (float)accessorieDTO.Position.y)  );
        updateaccesoiresHauteurInch(accessorieDTO.Height);
        updateaccesoiresLongueurInch(accessorieDTO.Length);
        repaint_();
    }

    public void hideAccessorieDetail(){
        uuid.setText("");
        AccesoireX.setText("");
        AccesoireY.setText("");
        accesoiresLongueurInch.setText("");
        accesoiresHauteurInch.setText("");
        accesoiresHauteurFraction.setText("");
        accesoiresLongueurFraction.setText("");
        setEnableAccessorieDetail(false);
    }

    public void activerDesactiverPorteFenetreBtn(boolean activer){
        addDoorBtn.setEnabled(activer);
        addWindowBtn.setEnabled(activer);

    }

    public void showChaletDetail(ChaletDTO chaletDTO){
        updateLengthFacade(chaletDTO.LengthFacade);
        updateHeight(chaletDTO.Height);
        updateLengthCote(chaletDTO.LengthCote);

        updateGroove(chaletDTO.Groove);
        updateThickness(chaletDTO.Thickness);
        updateDistanceMinimun(chaletDTO.MinSeparationDistance);

        btnCouleurSides.setBackground(chaletDTO.SidesColor);
        btnCouleurFront.setBackground(chaletDTO.FrontColor);
        //btnCouleurAccessoire.setBackground(chaletDTO.AccessoriesColor);

        //afficher que deux chiffre apres la virgule
        angleDegre.setText(String.format("%.2f", chaletDTO.Angle));

        nomChalet.setText(chaletDTO.Name);
        changerP.setSelected(chaletDTO.FrontIsPrincipal);
        DirectionToitCheckBox.setSelected(chaletDTO.DirectionToit);

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        nomChalet = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        largeurPanel = new javax.swing.JPanel();
        largeurLabel = new javax.swing.JLabel();
        LengthCoteFoot = new javax.swing.JTextField();
        largeurLabalFoot = new javax.swing.JLabel();
        LengthCoteInch = new javax.swing.JTextField();
        largeurLabelInch = new javax.swing.JLabel();
        LengthCotefraction = new javax.swing.JTextField();
        longeurPanel = new javax.swing.JPanel();
        longeurLabel2 = new javax.swing.JLabel();
        LengthFacadeFoot = new javax.swing.JTextField();
        longeurLabalFoot = new javax.swing.JLabel();
        LengthFacadeInch = new javax.swing.JTextField();
        longeurLabelInch = new javax.swing.JLabel();
        LengthFacadefraction = new javax.swing.JTextField();
        hauteurPanel = new javax.swing.JPanel();
        Height = new javax.swing.JLabel();
        HeightFoot = new javax.swing.JTextField();
        hauteurLabalFoot = new javax.swing.JLabel();
        HeightInch = new javax.swing.JTextField();
        hauteurLabelInch = new javax.swing.JLabel();
        HeightFraction = new javax.swing.JTextField();
        epaisseurPanel = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        epaisseurLabel = new javax.swing.JLabel();
        thicknessInch = new javax.swing.JTextField();
        epaisseurLabelInch = new javax.swing.JLabel();
        thicknessFraction = new javax.swing.JTextField();
        epaisseurPanel1 = new javax.swing.JPanel();
        epaisseurLabel1 = new javax.swing.JLabel();
        distanceMinimunInch = new javax.swing.JTextField();
        epaisseurLabelInch1 = new javax.swing.JLabel();
        distanceMinimunFraction = new javax.swing.JTextField();
        epaisseurPanel2 = new javax.swing.JPanel();
        epaisseurLabel2 = new javax.swing.JLabel();
        grooveInch = new javax.swing.JTextField();
        epaisseurLabelInch2 = new javax.swing.JLabel();
        grooveFraction = new javax.swing.JTextField();
        anglePanel = new javax.swing.JPanel();
        epaisseurLabel3 = new javax.swing.JLabel();
        angleDegre = new javax.swing.JTextField();
        epaisseurLabelInch3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        epaisseurPanel3 = new javax.swing.JPanel();
        epaisseurLabel4 = new javax.swing.JLabel();
        distanceBetweenLinesInch = new javax.swing.JTextField();
        epaisseurLabelInch4 = new javax.swing.JLabel();
        distanceBetweenLinesIFraction = new javax.swing.JTextField();
        epaisseurPanel4 = new javax.swing.JPanel();
        epaisseurLabel5 = new javax.swing.JLabel();
        distanceBetweenComunsInch = new javax.swing.JTextField();
        epaisseurLabelInch5 = new javax.swing.JLabel();
        distanceBetweenComunsFraction = new javax.swing.JTextField();
        epaisseurPanel7 = new javax.swing.JPanel();
        afficherGrille = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnCouleurFront = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        btnCouleurSides = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        changerP = new javax.swing.JCheckBox();
        DirectionToitCheckBox = new javax.swing.JCheckBox();
        displayeBackground = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        uuid = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        largeurPanel2 = new javax.swing.JPanel();
        largeurLabel2 = new javax.swing.JLabel();
        AccesoireX = new javax.swing.JTextField();
        largeurPanel4 = new javax.swing.JPanel();
        largeurLabel4 = new javax.swing.JLabel();
        AccesoireY = new javax.swing.JTextField();
        jSeparator14 = new javax.swing.JSeparator();
        epaisseurPanel5 = new javax.swing.JPanel();
        epaisseurLabel6 = new javax.swing.JLabel();
        accesoiresLongueurInch = new javax.swing.JTextField();
        epaisseurLabelInch6 = new javax.swing.JLabel();
        accesoiresLongueurFraction = new javax.swing.JTextField();
        epaisseurPanel6 = new javax.swing.JPanel();
        epaisseurLabel7 = new javax.swing.JLabel();
        accesoiresHauteurInch = new javax.swing.JTextField();
        epaisseurLabelInch7 = new javax.swing.JLabel();
        accesoiresHauteurFraction = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jSeparator16 = new javax.swing.JSeparator();
        longeurPanel2 = new javax.swing.JPanel();
        deleteBtn = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        newBtn = new javax.swing.JButton();
        openBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        jSeparator8 = new javax.swing.JSeparator();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        andoBtn = new javax.swing.JButton();
        rendoBtn = new javax.swing.JButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        jSeparator9 = new javax.swing.JSeparator();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        adjustBtn = new javax.swing.JButton();
        addDoorBtn = new javax.swing.JButton();
        addWindowBtn = new javax.swing.JButton();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        jSeparator17 = new javax.swing.JSeparator();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0));
        topView = new javax.swing.JButton();
        FrontView = new javax.swing.JButton();
        backView = new javax.swing.JButton();
        LeftView = new javax.swing.JButton();
        RightView = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newMenu = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        saveMenu = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        undo = new javax.swing.JMenuItem();
        redo = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        retrait = new javax.swing.JMenuItem();
        exportBrut = new javax.swing.JMenuItem();
        exportFini = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setMaximumSize(new java.awt.Dimension(250, 100));
        jPanel2.setMinimumSize(new java.awt.Dimension(250, 100));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jPanel26.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jPanel26.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel26.setLayout(new java.awt.CardLayout());

        jLabel14.setBackground(new java.awt.Color(44, 62, 80));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Nom projet");
        jLabel14.setMaximumSize(new java.awt.Dimension(250, 21));
        jLabel14.setMinimumSize(new java.awt.Dimension(250, 21));
        jLabel14.setOpaque(true);
        jLabel14.setPreferredSize(new java.awt.Dimension(250, 200));
        jPanel26.add(jLabel14, "card2");

        jPanel2.add(jPanel26);

        jPanel27.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jPanel27.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel27.setLayout(new java.awt.CardLayout());

        nomChalet.setForeground(new java.awt.Color(79, 105, 132));
        nomChalet.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        nomChalet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nomChaletActionPerformed(evt);
            }
        });
        jPanel27.add(nomChalet, "card2");

        jPanel2.add(jPanel27);

        jPanel10.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jPanel10.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel10.setLayout(new java.awt.CardLayout());

        jLabel6.setBackground(new java.awt.Color(44, 62, 80));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Dimensions");
        jLabel6.setMaximumSize(new java.awt.Dimension(250, 21));
        jLabel6.setMinimumSize(new java.awt.Dimension(250, 21));
        jLabel6.setOpaque(true);
        jLabel6.setPreferredSize(new java.awt.Dimension(250, 200));
        jPanel10.add(jLabel6, "card2");

        jPanel2.add(jPanel10);

        largeurPanel.setBackground(new java.awt.Color(79, 105, 132));
        largeurPanel.setForeground(new java.awt.Color(255, 255, 255));
        largeurPanel.setMaximumSize(new java.awt.Dimension(250, 25));
        largeurPanel.setMinimumSize(new java.awt.Dimension(250, 25));
        largeurPanel.setPreferredSize(new java.awt.Dimension(250, 25));
        largeurPanel.setLayout(new javax.swing.BoxLayout(largeurPanel, javax.swing.BoxLayout.LINE_AXIS));

        largeurLabel.setBackground(new java.awt.Color(79, 105, 132));
        largeurLabel.setForeground(new java.awt.Color(255, 255, 255));
        largeurLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        largeurLabel.setText("Cote Length");
        largeurLabel.setMinimumSize(new java.awt.Dimension(40, 0));
        largeurLabel.setOpaque(true);
        largeurLabel.setPreferredSize(new java.awt.Dimension(200, 16));
        largeurPanel.add(largeurLabel);

        LengthCoteFoot.setBackground(new java.awt.Color(79, 105, 132));
        LengthCoteFoot.setForeground(new java.awt.Color(255, 255, 255));
        LengthCoteFoot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LengthCoteFoot.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        LengthCoteFoot.setMaximumSize(new java.awt.Dimension(40, 22));
        LengthCoteFoot.setMinimumSize(new java.awt.Dimension(40, 22));
        LengthCoteFoot.setPreferredSize(new java.awt.Dimension(40, 22));
        LengthCoteFoot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LengthCoteFootActionPerformed(evt);
            }
        });
        largeurPanel.add(LengthCoteFoot);

        largeurLabalFoot.setBackground(new java.awt.Color(79, 105, 132));
        largeurLabalFoot.setForeground(new java.awt.Color(255, 255, 255));
        largeurLabalFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        largeurLabalFoot.setText("'");
        largeurLabalFoot.setOpaque(true);
        largeurLabalFoot.setPreferredSize(new java.awt.Dimension(20, 16));
        largeurPanel.add(largeurLabalFoot);

        LengthCoteInch.setBackground(new java.awt.Color(79, 105, 132));
        LengthCoteInch.setForeground(new java.awt.Color(255, 255, 255));
        LengthCoteInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LengthCoteInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        LengthCoteInch.setMaximumSize(new java.awt.Dimension(40, 22));
        LengthCoteInch.setMinimumSize(new java.awt.Dimension(40, 22));
        LengthCoteInch.setPreferredSize(new java.awt.Dimension(40, 22));
        LengthCoteInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LengthCoteInchActionPerformed(evt);
            }
        });
        largeurPanel.add(LengthCoteInch);

        largeurLabelInch.setBackground(new java.awt.Color(79, 105, 132));
        largeurLabelInch.setForeground(new java.awt.Color(255, 255, 255));
        largeurLabelInch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        largeurLabelInch.setText("''");
        largeurLabelInch.setOpaque(true);
        largeurLabelInch.setPreferredSize(new java.awt.Dimension(20, 16));
        largeurPanel.add(largeurLabelInch);

        LengthCotefraction.setBackground(new java.awt.Color(79, 105, 132));
        LengthCotefraction.setForeground(new java.awt.Color(255, 255, 255));
        LengthCotefraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LengthCotefraction.setToolTipText("d/d");
        LengthCotefraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        LengthCotefraction.setMaximumSize(new java.awt.Dimension(40, 22));
        LengthCotefraction.setMinimumSize(new java.awt.Dimension(40, 22));
        LengthCotefraction.setPreferredSize(new java.awt.Dimension(40, 22));
        LengthCotefraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LengthCotefractionActionPerformed(evt);
            }
        });
        largeurPanel.add(LengthCotefraction);

        jPanel2.add(largeurPanel);

        longeurPanel.setBackground(new java.awt.Color(79, 105, 132));
        longeurPanel.setMaximumSize(new java.awt.Dimension(250, 25));
        longeurPanel.setMinimumSize(new java.awt.Dimension(250, 25));
        longeurPanel.setPreferredSize(new java.awt.Dimension(250, 25));
        longeurPanel.setLayout(new javax.swing.BoxLayout(longeurPanel, javax.swing.BoxLayout.LINE_AXIS));

        longeurLabel2.setBackground(new java.awt.Color(79, 105, 132));
        longeurLabel2.setForeground(new java.awt.Color(255, 255, 255));
        longeurLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        longeurLabel2.setText("Facade Length ");
        longeurLabel2.setMinimumSize(new java.awt.Dimension(40, 0));
        longeurLabel2.setOpaque(true);
        longeurLabel2.setPreferredSize(new java.awt.Dimension(200, 16));
        longeurPanel.add(longeurLabel2);

        LengthFacadeFoot.setBackground(new java.awt.Color(79, 105, 132));
        LengthFacadeFoot.setForeground(new java.awt.Color(255, 255, 255));
        LengthFacadeFoot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LengthFacadeFoot.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        LengthFacadeFoot.setMaximumSize(new java.awt.Dimension(40, 22));
        LengthFacadeFoot.setMinimumSize(new java.awt.Dimension(40, 22));
        LengthFacadeFoot.setPreferredSize(new java.awt.Dimension(40, 22));
        LengthFacadeFoot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LengthFacadeFootActionPerformed(evt);
            }
        });
        longeurPanel.add(LengthFacadeFoot);

        longeurLabalFoot.setBackground(new java.awt.Color(79, 105, 132));
        longeurLabalFoot.setForeground(new java.awt.Color(255, 255, 255));
        longeurLabalFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        longeurLabalFoot.setText("'");
        longeurLabalFoot.setOpaque(true);
        longeurLabalFoot.setPreferredSize(new java.awt.Dimension(20, 16));
        longeurPanel.add(longeurLabalFoot);

        LengthFacadeInch.setBackground(new java.awt.Color(79, 105, 132));
        LengthFacadeInch.setForeground(new java.awt.Color(255, 255, 255));
        LengthFacadeInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LengthFacadeInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        LengthFacadeInch.setMaximumSize(new java.awt.Dimension(40, 22));
        LengthFacadeInch.setMinimumSize(new java.awt.Dimension(40, 22));
        LengthFacadeInch.setPreferredSize(new java.awt.Dimension(40, 22));
        LengthFacadeInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LengthFacadeInchActionPerformed(evt);
            }
        });
        longeurPanel.add(LengthFacadeInch);

        longeurLabelInch.setBackground(new java.awt.Color(79, 105, 132));
        longeurLabelInch.setForeground(new java.awt.Color(255, 255, 255));
        longeurLabelInch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        longeurLabelInch.setText("''");
        longeurLabelInch.setOpaque(true);
        longeurLabelInch.setPreferredSize(new java.awt.Dimension(20, 16));
        longeurPanel.add(longeurLabelInch);

        LengthFacadefraction.setBackground(new java.awt.Color(79, 105, 132));
        LengthFacadefraction.setForeground(new java.awt.Color(255, 255, 255));
        LengthFacadefraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        LengthFacadefraction.setToolTipText("d/d");
        LengthFacadefraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        LengthFacadefraction.setMaximumSize(new java.awt.Dimension(40, 22));
        LengthFacadefraction.setMinimumSize(new java.awt.Dimension(40, 22));
        LengthFacadefraction.setPreferredSize(new java.awt.Dimension(40, 22));
        LengthFacadefraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LengthFacadefractionActionPerformed(evt);
            }
        });
        longeurPanel.add(LengthFacadefraction);

        jPanel2.add(longeurPanel);

        hauteurPanel.setBackground(new java.awt.Color(79, 105, 132));
        hauteurPanel.setForeground(new java.awt.Color(255, 255, 255));
        hauteurPanel.setMaximumSize(new java.awt.Dimension(250, 25));
        hauteurPanel.setMinimumSize(new java.awt.Dimension(250, 25));
        hauteurPanel.setName(""); // NOI18N
        hauteurPanel.setPreferredSize(new java.awt.Dimension(250, 25));
        hauteurPanel.setLayout(new javax.swing.BoxLayout(hauteurPanel, javax.swing.BoxLayout.LINE_AXIS));

        Height.setBackground(new java.awt.Color(79, 105, 132));
        Height.setForeground(new java.awt.Color(255, 255, 255));
        Height.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Height.setText("Height");
        Height.setMinimumSize(new java.awt.Dimension(40, 0));
        Height.setOpaque(true);
        Height.setPreferredSize(new java.awt.Dimension(200, 16));
        hauteurPanel.add(Height);

        HeightFoot.setBackground(new java.awt.Color(79, 105, 132));
        HeightFoot.setForeground(new java.awt.Color(255, 255, 255));
        HeightFoot.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        HeightFoot.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        HeightFoot.setMaximumSize(new java.awt.Dimension(40, 22));
        HeightFoot.setMinimumSize(new java.awt.Dimension(40, 22));
        HeightFoot.setPreferredSize(new java.awt.Dimension(40, 22));
        HeightFoot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HeightFootActionPerformed(evt);
            }
        });
        hauteurPanel.add(HeightFoot);

        hauteurLabalFoot.setBackground(new java.awt.Color(79, 105, 132));
        hauteurLabalFoot.setForeground(new java.awt.Color(255, 255, 255));
        hauteurLabalFoot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hauteurLabalFoot.setText("'");
        hauteurLabalFoot.setOpaque(true);
        hauteurLabalFoot.setPreferredSize(new java.awt.Dimension(20, 16));
        hauteurPanel.add(hauteurLabalFoot);

        HeightInch.setBackground(new java.awt.Color(79, 105, 132));
        HeightInch.setForeground(new java.awt.Color(255, 255, 255));
        HeightInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        HeightInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        HeightInch.setMaximumSize(new java.awt.Dimension(40, 22));
        HeightInch.setMinimumSize(new java.awt.Dimension(40, 22));
        HeightInch.setPreferredSize(new java.awt.Dimension(40, 22));
        HeightInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HeightInchActionPerformed(evt);
            }
        });
        hauteurPanel.add(HeightInch);

        hauteurLabelInch.setBackground(new java.awt.Color(79, 105, 132));
        hauteurLabelInch.setForeground(new java.awt.Color(255, 255, 255));
        hauteurLabelInch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hauteurLabelInch.setText("''");
        hauteurLabelInch.setOpaque(true);
        hauteurLabelInch.setPreferredSize(new java.awt.Dimension(20, 16));
        hauteurPanel.add(hauteurLabelInch);

        HeightFraction.setBackground(new java.awt.Color(79, 105, 132));
        HeightFraction.setForeground(new java.awt.Color(255, 255, 255));
        HeightFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        HeightFraction.setToolTipText("d/d");
        HeightFraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        HeightFraction.setMaximumSize(new java.awt.Dimension(40, 22));
        HeightFraction.setMinimumSize(new java.awt.Dimension(40, 22));
        HeightFraction.setPreferredSize(new java.awt.Dimension(40, 22));
        HeightFraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HeightFractionActionPerformed(evt);
            }
        });
        hauteurPanel.add(HeightFraction);

        jPanel2.add(hauteurPanel);

        epaisseurPanel.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurPanel.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurPanel.setMaximumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel.setMinimumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel.setPreferredSize(new java.awt.Dimension(250, 25));
        epaisseurPanel.setLayout(new javax.swing.BoxLayout(epaisseurPanel, javax.swing.BoxLayout.LINE_AXIS));
        epaisseurPanel.add(jSeparator2);

        epaisseurLabel.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabel.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabel.setText("Thickness");
        epaisseurLabel.setMaximumSize(new java.awt.Dimension(90, 16));
        epaisseurLabel.setMinimumSize(new java.awt.Dimension(90, 16));
        epaisseurLabel.setOpaque(true);
        epaisseurLabel.setPreferredSize(new java.awt.Dimension(250, 16));
        epaisseurPanel.add(epaisseurLabel);

        thicknessInch.setBackground(new java.awt.Color(79, 105, 132));
        thicknessInch.setForeground(new java.awt.Color(255, 255, 255));
        thicknessInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        thicknessInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        thicknessInch.setMaximumSize(new java.awt.Dimension(40, 22));
        thicknessInch.setMinimumSize(new java.awt.Dimension(40, 22));
        thicknessInch.setPreferredSize(new java.awt.Dimension(40, 22));
        thicknessInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thicknessInchActionPerformed(evt);
            }
        });
        epaisseurPanel.add(thicknessInch);

        epaisseurLabelInch.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabelInch.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabelInch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabelInch.setText("''");
        epaisseurLabelInch.setOpaque(true);
        epaisseurLabelInch.setPreferredSize(new java.awt.Dimension(20, 16));
        epaisseurPanel.add(epaisseurLabelInch);

        thicknessFraction.setBackground(new java.awt.Color(79, 105, 132));
        thicknessFraction.setForeground(new java.awt.Color(255, 255, 255));
        thicknessFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        thicknessFraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        thicknessFraction.setMaximumSize(new java.awt.Dimension(40, 22));
        thicknessFraction.setMinimumSize(new java.awt.Dimension(40, 22));
        thicknessFraction.setPreferredSize(new java.awt.Dimension(40, 22));
        thicknessFraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thicknessFractionActionPerformed(evt);
            }
        });
        epaisseurPanel.add(thicknessFraction);

        jPanel2.add(epaisseurPanel);

        epaisseurPanel1.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurPanel1.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurPanel1.setMaximumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel1.setMinimumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel1.setPreferredSize(new java.awt.Dimension(250, 25));
        epaisseurPanel1.setLayout(new javax.swing.BoxLayout(epaisseurPanel1, javax.swing.BoxLayout.LINE_AXIS));

        epaisseurLabel1.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabel1.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabel1.setText("min distance");
        epaisseurLabel1.setToolTipText("min Separation Distance between accessory");
        epaisseurLabel1.setMaximumSize(new java.awt.Dimension(90, 22));
        epaisseurLabel1.setMinimumSize(new java.awt.Dimension(90, 16));
        epaisseurLabel1.setOpaque(true);
        epaisseurLabel1.setPreferredSize(new java.awt.Dimension(250, 16));
        epaisseurPanel1.add(epaisseurLabel1);

        distanceMinimunInch.setBackground(new java.awt.Color(79, 105, 132));
        distanceMinimunInch.setForeground(new java.awt.Color(255, 255, 255));
        distanceMinimunInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        distanceMinimunInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        distanceMinimunInch.setMaximumSize(new java.awt.Dimension(40, 22));
        distanceMinimunInch.setMinimumSize(new java.awt.Dimension(40, 22));
        distanceMinimunInch.setPreferredSize(new java.awt.Dimension(40, 22));
        distanceMinimunInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distanceMinimunInchActionPerformed(evt);
            }
        });
        epaisseurPanel1.add(distanceMinimunInch);

        epaisseurLabelInch1.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabelInch1.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabelInch1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabelInch1.setText("''");
        epaisseurLabelInch1.setOpaque(true);
        epaisseurLabelInch1.setPreferredSize(new java.awt.Dimension(20, 16));
        epaisseurPanel1.add(epaisseurLabelInch1);

        distanceMinimunFraction.setBackground(new java.awt.Color(79, 105, 132));
        distanceMinimunFraction.setForeground(new java.awt.Color(255, 255, 255));
        distanceMinimunFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        distanceMinimunFraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        distanceMinimunFraction.setMaximumSize(new java.awt.Dimension(40, 22));
        distanceMinimunFraction.setMinimumSize(new java.awt.Dimension(40, 22));
        distanceMinimunFraction.setPreferredSize(new java.awt.Dimension(40, 22));
        distanceMinimunFraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distanceMinimunFractionActionPerformed(evt);
            }
        });
        epaisseurPanel1.add(distanceMinimunFraction);

        jPanel2.add(epaisseurPanel1);

        epaisseurPanel2.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurPanel2.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurPanel2.setMaximumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel2.setMinimumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel2.setPreferredSize(new java.awt.Dimension(250, 25));
        epaisseurPanel2.setLayout(new javax.swing.BoxLayout(epaisseurPanel2, javax.swing.BoxLayout.LINE_AXIS));

        epaisseurLabel2.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabel2.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabel2.setText("groove");
        epaisseurLabel2.setMaximumSize(new java.awt.Dimension(90, 22));
        epaisseurLabel2.setMinimumSize(new java.awt.Dimension(90, 16));
        epaisseurLabel2.setOpaque(true);
        epaisseurLabel2.setPreferredSize(new java.awt.Dimension(250, 16));
        epaisseurPanel2.add(epaisseurLabel2);

        grooveInch.setBackground(new java.awt.Color(79, 105, 132));
        grooveInch.setForeground(new java.awt.Color(255, 255, 255));
        grooveInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        grooveInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        grooveInch.setMaximumSize(new java.awt.Dimension(40, 22));
        grooveInch.setMinimumSize(new java.awt.Dimension(40, 22));
        grooveInch.setPreferredSize(new java.awt.Dimension(40, 22));
        grooveInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grooveInchActionPerformed(evt);
            }
        });
        epaisseurPanel2.add(grooveInch);

        epaisseurLabelInch2.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabelInch2.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabelInch2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabelInch2.setText("''");
        epaisseurLabelInch2.setOpaque(true);
        epaisseurLabelInch2.setPreferredSize(new java.awt.Dimension(20, 16));
        epaisseurPanel2.add(epaisseurLabelInch2);

        grooveFraction.setBackground(new java.awt.Color(79, 105, 132));
        grooveFraction.setForeground(new java.awt.Color(255, 255, 255));
        grooveFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        grooveFraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        grooveFraction.setMaximumSize(new java.awt.Dimension(40, 22));
        grooveFraction.setMinimumSize(new java.awt.Dimension(40, 22));
        grooveFraction.setPreferredSize(new java.awt.Dimension(40, 22));
        grooveFraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                grooveFractionActionPerformed(evt);
            }
        });
        epaisseurPanel2.add(grooveFraction);

        jPanel2.add(epaisseurPanel2);

        anglePanel.setBackground(new java.awt.Color(79, 105, 132));
        anglePanel.setForeground(new java.awt.Color(255, 255, 255));
        anglePanel.setMaximumSize(new java.awt.Dimension(250, 25));
        anglePanel.setMinimumSize(new java.awt.Dimension(250, 25));
        anglePanel.setPreferredSize(new java.awt.Dimension(250, 25));
        anglePanel.setLayout(new javax.swing.BoxLayout(anglePanel, javax.swing.BoxLayout.LINE_AXIS));

        epaisseurLabel3.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabel3.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabel3.setText("Angle");
        epaisseurLabel3.setMaximumSize(new java.awt.Dimension(90, 16));
        epaisseurLabel3.setMinimumSize(new java.awt.Dimension(90, 16));
        epaisseurLabel3.setOpaque(true);
        epaisseurLabel3.setPreferredSize(new java.awt.Dimension(250, 16));
        anglePanel.add(epaisseurLabel3);

        angleDegre.setBackground(new java.awt.Color(79, 105, 132));
        angleDegre.setForeground(new java.awt.Color(255, 255, 255));
        angleDegre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        angleDegre.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        angleDegre.setMaximumSize(new java.awt.Dimension(85, 22));
        angleDegre.setMinimumSize(new java.awt.Dimension(80, 22));
        angleDegre.setPreferredSize(new java.awt.Dimension(90, 22));
        angleDegre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                angleDegreActionPerformed(evt);
            }
        });
        anglePanel.add(angleDegre);

        epaisseurLabelInch3.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabelInch3.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabelInch3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabelInch3.setText("°");
        epaisseurLabelInch3.setOpaque(true);
        epaisseurLabelInch3.setPreferredSize(new java.awt.Dimension(20, 16));
        anglePanel.add(epaisseurLabelInch3);

        jPanel2.add(anglePanel);

        jPanel11.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jPanel11.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel11.setLayout(new java.awt.CardLayout());

        jLabel7.setBackground(new java.awt.Color(44, 62, 80));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Sentings of Grid");
        jLabel7.setMaximumSize(new java.awt.Dimension(250, 21));
        jLabel7.setMinimumSize(new java.awt.Dimension(250, 21));
        jLabel7.setOpaque(true);
        jLabel7.setPreferredSize(new java.awt.Dimension(250, 200));
        jPanel11.add(jLabel7, "card2");

        jPanel2.add(jPanel11);

        epaisseurPanel3.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurPanel3.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurPanel3.setMaximumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel3.setMinimumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel3.setPreferredSize(new java.awt.Dimension(250, 25));
        epaisseurPanel3.setLayout(new javax.swing.BoxLayout(epaisseurPanel3, javax.swing.BoxLayout.LINE_AXIS));

        epaisseurLabel4.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabel4.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabel4.setText("distance between lines");
        epaisseurLabel4.setMaximumSize(new java.awt.Dimension(42, 16));
        epaisseurLabel4.setMinimumSize(new java.awt.Dimension(42, 16));
        epaisseurLabel4.setOpaque(true);
        epaisseurLabel4.setPreferredSize(new java.awt.Dimension(250, 16));
        epaisseurPanel3.add(epaisseurLabel4);

        distanceBetweenLinesInch.setBackground(new java.awt.Color(79, 105, 132));
        distanceBetweenLinesInch.setForeground(new java.awt.Color(255, 255, 255));
        distanceBetweenLinesInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        distanceBetweenLinesInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        distanceBetweenLinesInch.setMaximumSize(new java.awt.Dimension(40, 22));
        distanceBetweenLinesInch.setMinimumSize(new java.awt.Dimension(40, 22));
        distanceBetweenLinesInch.setPreferredSize(new java.awt.Dimension(40, 22));
        distanceBetweenLinesInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distanceBetweenLinesInchActionPerformed(evt);
            }
        });
        epaisseurPanel3.add(distanceBetweenLinesInch);

        epaisseurLabelInch4.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabelInch4.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabelInch4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabelInch4.setText("''");
        epaisseurLabelInch4.setOpaque(true);
        epaisseurLabelInch4.setPreferredSize(new java.awt.Dimension(20, 16));
        epaisseurPanel3.add(epaisseurLabelInch4);

        distanceBetweenLinesIFraction.setBackground(new java.awt.Color(79, 105, 132));
        distanceBetweenLinesIFraction.setForeground(new java.awt.Color(255, 255, 255));
        distanceBetweenLinesIFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        distanceBetweenLinesIFraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        distanceBetweenLinesIFraction.setMaximumSize(new java.awt.Dimension(40, 22));
        distanceBetweenLinesIFraction.setMinimumSize(new java.awt.Dimension(40, 22));
        distanceBetweenLinesIFraction.setPreferredSize(new java.awt.Dimension(40, 22));
        distanceBetweenLinesIFraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distanceBetweenLinesIFractionActionPerformed(evt);
            }
        });
        epaisseurPanel3.add(distanceBetweenLinesIFraction);

        jPanel2.add(epaisseurPanel3);

        epaisseurPanel4.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurPanel4.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurPanel4.setMaximumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel4.setMinimumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel4.setPreferredSize(new java.awt.Dimension(250, 25));
        epaisseurPanel4.setLayout(new javax.swing.BoxLayout(epaisseurPanel4, javax.swing.BoxLayout.LINE_AXIS));

        epaisseurLabel5.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabel5.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabel5.setText("distance between columns");
        epaisseurLabel5.setToolTipText("min Separation Distance between accessory");
        epaisseurLabel5.setMaximumSize(new java.awt.Dimension(42, 16));
        epaisseurLabel5.setMinimumSize(new java.awt.Dimension(42, 16));
        epaisseurLabel5.setOpaque(true);
        epaisseurLabel5.setPreferredSize(new java.awt.Dimension(250, 16));
        epaisseurPanel4.add(epaisseurLabel5);

        distanceBetweenComunsInch.setBackground(new java.awt.Color(79, 105, 132));
        distanceBetweenComunsInch.setForeground(new java.awt.Color(255, 255, 255));
        distanceBetweenComunsInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        distanceBetweenComunsInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        distanceBetweenComunsInch.setMaximumSize(new java.awt.Dimension(40, 22));
        distanceBetweenComunsInch.setMinimumSize(new java.awt.Dimension(40, 22));
        distanceBetweenComunsInch.setPreferredSize(new java.awt.Dimension(40, 22));
        distanceBetweenComunsInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distanceBetweenComunsInchActionPerformed(evt);
            }
        });
        epaisseurPanel4.add(distanceBetweenComunsInch);

        epaisseurLabelInch5.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabelInch5.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabelInch5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabelInch5.setText("''");
        epaisseurLabelInch5.setOpaque(true);
        epaisseurLabelInch5.setPreferredSize(new java.awt.Dimension(20, 16));
        epaisseurPanel4.add(epaisseurLabelInch5);

        distanceBetweenComunsFraction.setBackground(new java.awt.Color(79, 105, 132));
        distanceBetweenComunsFraction.setForeground(new java.awt.Color(255, 255, 255));
        distanceBetweenComunsFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        distanceBetweenComunsFraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        distanceBetweenComunsFraction.setMaximumSize(new java.awt.Dimension(40, 22));
        distanceBetweenComunsFraction.setMinimumSize(new java.awt.Dimension(40, 22));
        distanceBetweenComunsFraction.setPreferredSize(new java.awt.Dimension(40, 22));
        distanceBetweenComunsFraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                distanceBetweenComunsFractionActionPerformed(evt);
            }
        });
        epaisseurPanel4.add(distanceBetweenComunsFraction);

        jPanel2.add(epaisseurPanel4);

        epaisseurPanel7.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurPanel7.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurPanel7.setMaximumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel7.setMinimumSize(new java.awt.Dimension(250, 25));
        epaisseurPanel7.setPreferredSize(new java.awt.Dimension(250, 25));
        epaisseurPanel7.setLayout(new java.awt.GridBagLayout());

        afficherGrille.setBackground(new java.awt.Color(79, 105, 132));
        afficherGrille.setForeground(new java.awt.Color(255, 255, 255));
        afficherGrille.setSelected(true);
        afficherGrille.setText("Show grille");
        afficherGrille.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afficherGrilleActionPerformed(evt);
            }
        });
        epaisseurPanel7.add(afficherGrille, new java.awt.GridBagConstraints());

        jPanel2.add(epaisseurPanel7);

        jPanel15.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jPanel15.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel15.setLayout(new java.awt.CardLayout());

        jLabel8.setBackground(new java.awt.Color(44, 62, 80));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Colors");
        jLabel8.setMaximumSize(new java.awt.Dimension(250, 21));
        jLabel8.setMinimumSize(new java.awt.Dimension(250, 21));
        jLabel8.setOpaque(true);
        jLabel8.setPreferredSize(new java.awt.Dimension(250, 200));
        jPanel15.add(jLabel8, "card2");

        jPanel2.add(jPanel15);

        jPanel16.setBackground(new java.awt.Color(79, 105, 132));
        jPanel16.setForeground(new java.awt.Color(255, 255, 255));
        jPanel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel16.setMaximumSize(new java.awt.Dimension(250, 50));
        jPanel16.setMinimumSize(new java.awt.Dimension(250, 50));
        jPanel16.setPreferredSize(new java.awt.Dimension(250, 50));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setBackground(new java.awt.Color(79, 105, 132));
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Murs A/R :");
        jLabel9.setMaximumSize(new java.awt.Dimension(80, 25));
        jLabel9.setMinimumSize(new java.awt.Dimension(80, 25));
        jLabel9.setName(""); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(80, 25));
        jPanel16.add(jLabel9);

        btnCouleurFront.setBackground(new java.awt.Color(0, 153, 255));
        btnCouleurFront.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCouleurFront.setMaximumSize(new java.awt.Dimension(15, 20));
        btnCouleurFront.setMinimumSize(new java.awt.Dimension(15, 20));
        btnCouleurFront.setPreferredSize(new java.awt.Dimension(35, 25));
        btnCouleurFront.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCouleurFrontActionPerformed(evt);
            }
        });
        jPanel16.add(btnCouleurFront);

        jSeparator7.setBackground(new java.awt.Color(79, 105, 132));
        jSeparator7.setForeground(new java.awt.Color(79, 105, 132));
        jPanel16.add(jSeparator7);

        jLabel11.setBackground(new java.awt.Color(79, 105, 132));
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Murs G/D:");
        jLabel11.setMaximumSize(new java.awt.Dimension(80, 25));
        jLabel11.setMinimumSize(new java.awt.Dimension(80, 25));
        jLabel11.setName(""); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(80, 25));
        jPanel16.add(jLabel11);

        btnCouleurSides.setBackground(new java.awt.Color(255, 255, 51));
        btnCouleurSides.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCouleurSides.setMaximumSize(new java.awt.Dimension(35, 20));
        btnCouleurSides.setMinimumSize(new java.awt.Dimension(35, 20));
        btnCouleurSides.setPreferredSize(new java.awt.Dimension(35, 35));
        btnCouleurSides.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCouleurSidesActionPerformed(evt);
            }
        });
        jPanel16.add(btnCouleurSides);

        jPanel2.add(jPanel16);

        jPanel18.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jPanel18.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel18.setLayout(new java.awt.CardLayout());

        jLabel12.setBackground(new java.awt.Color(44, 62, 80));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Display");
        jLabel12.setMaximumSize(new java.awt.Dimension(250, 21));
        jLabel12.setMinimumSize(new java.awt.Dimension(250, 21));
        jLabel12.setOpaque(true);
        jLabel12.setPreferredSize(new java.awt.Dimension(250, 200));
        jPanel18.add(jLabel12, "card2");

        jPanel2.add(jPanel18);

        jPanel19.setBackground(new java.awt.Color(79, 105, 132));
        jPanel19.setForeground(new java.awt.Color(255, 255, 255));
        jPanel19.setMaximumSize(new java.awt.Dimension(250, 70));
        jPanel19.setMinimumSize(new java.awt.Dimension(200, 70));
        jPanel19.setPreferredSize(new java.awt.Dimension(200, 80));
        jPanel19.setLayout(new java.awt.GridLayout(3, 1, 5, 5));

        changerP.setBackground(new java.awt.Color(79, 105, 132));
        changerP.setForeground(new java.awt.Color(255, 255, 255));
        changerP.setSelected(true);
        changerP.setText("Change priority");
        changerP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changerPActionPerformed(evt);
            }
        });
        jPanel19.add(changerP);

        DirectionToitCheckBox.setBackground(new java.awt.Color(79, 105, 132));
        DirectionToitCheckBox.setForeground(new java.awt.Color(255, 255, 255));
        DirectionToitCheckBox.setSelected(true);
        DirectionToitCheckBox.setText("Change Roof Direction");
        DirectionToitCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DirectionToitCheckBoxActionPerformed(evt);
            }
        });
        jPanel19.add(DirectionToitCheckBox);

        displayeBackground.setBackground(new java.awt.Color(79, 105, 132));
        displayeBackground.setForeground(new java.awt.Color(255, 255, 255));
        displayeBackground.setSelected(true);
        displayeBackground.setText("Display Background");
        displayeBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayeBackgroundActionPerformed(evt);
            }
        });
        jPanel19.add(displayeBackground);

        jPanel2.add(jPanel19);

        jPanel7.setBackground(new java.awt.Color(79, 105, 132));
        jPanel7.setForeground(new java.awt.Color(79, 105, 132));
        jPanel7.setMaximumSize(new java.awt.Dimension(250, 1000));
        jPanel7.setMinimumSize(new java.awt.Dimension(250, 1000));
        jPanel7.setPreferredSize(new java.awt.Dimension(40, 22));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jPanel21.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jPanel21.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel21.setLayout(new java.awt.CardLayout());

        jLabel17.setBackground(new java.awt.Color(44, 62, 80));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Accessorie");
        jLabel17.setMaximumSize(new java.awt.Dimension(250, 21));
        jLabel17.setMinimumSize(new java.awt.Dimension(250, 21));
        jLabel17.setOpaque(true);
        jLabel17.setPreferredSize(new java.awt.Dimension(250, 200));
        jPanel21.add(jLabel17, "card2");

        jPanel7.add(jPanel21);

        jPanel25.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        jPanel25.setPreferredSize(new java.awt.Dimension(250, 20));
        jPanel25.setLayout(new java.awt.CardLayout());

        uuid.setBackground(new java.awt.Color(79, 105, 132));
        uuid.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        uuid.setMaximumSize(new java.awt.Dimension(250, 21));
        uuid.setMinimumSize(new java.awt.Dimension(250, 21));
        uuid.setOpaque(true);
        uuid.setPreferredSize(new java.awt.Dimension(250, 200));
        jPanel25.add(uuid, "card2");

        jPanel7.add(jPanel25);

        jSeparator12.setBackground(new java.awt.Color(79, 105, 132));
        jSeparator12.setForeground(new java.awt.Color(79, 105, 132));
        jSeparator12.setMaximumSize(new java.awt.Dimension(250, 5));
        jSeparator12.setMinimumSize(new java.awt.Dimension(5, 5));
        jSeparator12.setPreferredSize(new java.awt.Dimension(5, 5));
        jPanel7.add(jSeparator12);

        largeurPanel2.setBackground(new java.awt.Color(204, 204, 204));
        largeurPanel2.setMaximumSize(new java.awt.Dimension(150, 20));
        largeurPanel2.setMinimumSize(new java.awt.Dimension(250, 35));
        largeurPanel2.setPreferredSize(new java.awt.Dimension(150, 35));
        largeurPanel2.setLayout(new javax.swing.BoxLayout(largeurPanel2, javax.swing.BoxLayout.LINE_AXIS));

        largeurLabel2.setBackground(new java.awt.Color(79, 105, 132));
        largeurLabel2.setForeground(new java.awt.Color(255, 255, 255));
        largeurLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        largeurLabel2.setText("X");
        largeurLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        largeurLabel2.setMinimumSize(new java.awt.Dimension(40, 0));
        largeurLabel2.setOpaque(true);
        largeurLabel2.setPreferredSize(new java.awt.Dimension(60, 16));
        largeurPanel2.add(largeurLabel2);

        AccesoireX.setBackground(new java.awt.Color(79, 105, 132));
        AccesoireX.setForeground(new java.awt.Color(255, 255, 255));
        AccesoireX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AccesoireX.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        AccesoireX.setMinimumSize(new java.awt.Dimension(80, 22));
        AccesoireX.setPreferredSize(new java.awt.Dimension(80, 22));
        AccesoireX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccesoireXActionPerformed(evt);
            }
        });
        largeurPanel2.add(AccesoireX);

        jPanel7.add(largeurPanel2);

        largeurPanel4.setBackground(new java.awt.Color(79, 105, 132));
        largeurPanel4.setMaximumSize(new java.awt.Dimension(150, 20));
        largeurPanel4.setMinimumSize(new java.awt.Dimension(250, 35));
        largeurPanel4.setPreferredSize(new java.awt.Dimension(150, 35));
        largeurPanel4.setLayout(new javax.swing.BoxLayout(largeurPanel4, javax.swing.BoxLayout.LINE_AXIS));

        largeurLabel4.setBackground(new java.awt.Color(79, 105, 132));
        largeurLabel4.setForeground(new java.awt.Color(255, 255, 255));
        largeurLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        largeurLabel4.setText("Y");
        largeurLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        largeurLabel4.setMinimumSize(new java.awt.Dimension(40, 0));
        largeurLabel4.setOpaque(true);
        largeurLabel4.setPreferredSize(new java.awt.Dimension(60, 16));
        largeurPanel4.add(largeurLabel4);

        AccesoireY.setBackground(new java.awt.Color(79, 105, 132));
        AccesoireY.setForeground(new java.awt.Color(255, 255, 255));
        AccesoireY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AccesoireY.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        AccesoireY.setMinimumSize(new java.awt.Dimension(80, 22));
        AccesoireY.setPreferredSize(new java.awt.Dimension(80, 22));
        AccesoireY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AccesoireYActionPerformed(evt);
            }
        });
        largeurPanel4.add(AccesoireY);

        jPanel7.add(largeurPanel4);

        jSeparator14.setBackground(new java.awt.Color(79, 105, 132));
        jSeparator14.setForeground(new java.awt.Color(79, 105, 132));
        jSeparator14.setMaximumSize(new java.awt.Dimension(250, 5));
        jSeparator14.setMinimumSize(new java.awt.Dimension(5, 5));
        jSeparator14.setPreferredSize(new java.awt.Dimension(5, 5));
        jPanel7.add(jSeparator14);

        epaisseurPanel5.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurPanel5.setMaximumSize(new java.awt.Dimension(150, 35));
        epaisseurPanel5.setMinimumSize(new java.awt.Dimension(150, 35));
        epaisseurPanel5.setPreferredSize(new java.awt.Dimension(150, 35));
        epaisseurPanel5.setRequestFocusEnabled(false);
        epaisseurPanel5.setLayout(new javax.swing.BoxLayout(epaisseurPanel5, javax.swing.BoxLayout.LINE_AXIS));

        epaisseurLabel6.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabel6.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabel6.setText("Length");
        epaisseurLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        epaisseurLabel6.setMaximumSize(new java.awt.Dimension(50, 22));
        epaisseurLabel6.setMinimumSize(new java.awt.Dimension(90, 16));
        epaisseurLabel6.setOpaque(true);
        epaisseurLabel6.setPreferredSize(new java.awt.Dimension(50, 16));
        epaisseurPanel5.add(epaisseurLabel6);

        accesoiresLongueurInch.setBackground(new java.awt.Color(79, 105, 132));
        accesoiresLongueurInch.setForeground(new java.awt.Color(255, 255, 255));
        accesoiresLongueurInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        accesoiresLongueurInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        accesoiresLongueurInch.setMaximumSize(new java.awt.Dimension(40, 22));
        accesoiresLongueurInch.setMinimumSize(new java.awt.Dimension(40, 22));
        accesoiresLongueurInch.setPreferredSize(new java.awt.Dimension(40, 22));
        accesoiresLongueurInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accesoiresLongueurInchActionPerformed(evt);
            }
        });
        epaisseurPanel5.add(accesoiresLongueurInch);

        epaisseurLabelInch6.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabelInch6.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabelInch6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabelInch6.setText("''");
        epaisseurLabelInch6.setOpaque(true);
        epaisseurLabelInch6.setPreferredSize(new java.awt.Dimension(20, 16));
        epaisseurPanel5.add(epaisseurLabelInch6);

        accesoiresLongueurFraction.setBackground(new java.awt.Color(79, 105, 132));
        accesoiresLongueurFraction.setForeground(new java.awt.Color(255, 255, 255));
        accesoiresLongueurFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        accesoiresLongueurFraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        accesoiresLongueurFraction.setEnabled(false);
        accesoiresLongueurFraction.setMaximumSize(new java.awt.Dimension(40, 22));
        accesoiresLongueurFraction.setMinimumSize(new java.awt.Dimension(40, 22));
        accesoiresLongueurFraction.setPreferredSize(new java.awt.Dimension(40, 22));
        accesoiresLongueurFraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accesoiresLongueurFractionActionPerformed(evt);
            }
        });
        epaisseurPanel5.add(accesoiresLongueurFraction);

        jPanel7.add(epaisseurPanel5);

        epaisseurPanel6.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurPanel6.setMaximumSize(new java.awt.Dimension(150, 35));
        epaisseurPanel6.setMinimumSize(new java.awt.Dimension(150, 35));
        epaisseurPanel6.setPreferredSize(new java.awt.Dimension(250, 25));
        epaisseurPanel6.setLayout(new javax.swing.BoxLayout(epaisseurPanel6, javax.swing.BoxLayout.LINE_AXIS));

        epaisseurLabel7.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabel7.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabel7.setText("Height");
        epaisseurLabel7.setToolTipText("min Separation Distance between accessory");
        epaisseurLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        epaisseurLabel7.setMaximumSize(new java.awt.Dimension(50, 22));
        epaisseurLabel7.setMinimumSize(new java.awt.Dimension(50, 16));
        epaisseurLabel7.setOpaque(true);
        epaisseurLabel7.setPreferredSize(new java.awt.Dimension(50, 16));
        epaisseurPanel6.add(epaisseurLabel7);

        accesoiresHauteurInch.setBackground(new java.awt.Color(79, 105, 132));
        accesoiresHauteurInch.setForeground(new java.awt.Color(255, 255, 255));
        accesoiresHauteurInch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        accesoiresHauteurInch.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        accesoiresHauteurInch.setMaximumSize(new java.awt.Dimension(40, 22));
        accesoiresHauteurInch.setMinimumSize(new java.awt.Dimension(40, 22));
        accesoiresHauteurInch.setPreferredSize(new java.awt.Dimension(40, 22));
        accesoiresHauteurInch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accesoiresHauteurInchActionPerformed(evt);
            }
        });
        epaisseurPanel6.add(accesoiresHauteurInch);

        epaisseurLabelInch7.setBackground(new java.awt.Color(79, 105, 132));
        epaisseurLabelInch7.setForeground(new java.awt.Color(255, 255, 255));
        epaisseurLabelInch7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        epaisseurLabelInch7.setText("''");
        epaisseurLabelInch7.setOpaque(true);
        epaisseurLabelInch7.setPreferredSize(new java.awt.Dimension(20, 16));
        epaisseurPanel6.add(epaisseurLabelInch7);

        accesoiresHauteurFraction.setBackground(new java.awt.Color(79, 105, 132));
        accesoiresHauteurFraction.setForeground(new java.awt.Color(255, 255, 255));
        accesoiresHauteurFraction.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        accesoiresHauteurFraction.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        accesoiresHauteurFraction.setEnabled(false);
        accesoiresHauteurFraction.setMaximumSize(new java.awt.Dimension(40, 22));
        accesoiresHauteurFraction.setMinimumSize(new java.awt.Dimension(40, 22));
        accesoiresHauteurFraction.setPreferredSize(new java.awt.Dimension(40, 22));
        accesoiresHauteurFraction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accesoiresHauteurFractionActionPerformed(evt);
            }
        });
        epaisseurPanel6.add(accesoiresHauteurFraction);

        jPanel7.add(epaisseurPanel6);

        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.PAGE_AXIS));
        jPanel7.add(jPanel23);

        jSeparator16.setBackground(new java.awt.Color(79, 105, 132));
        jSeparator16.setForeground(new java.awt.Color(79, 105, 132));
        jSeparator16.setMaximumSize(new java.awt.Dimension(250, 15));
        jSeparator16.setMinimumSize(new java.awt.Dimension(5, 5));
        jSeparator16.setPreferredSize(new java.awt.Dimension(5, 5));
        jPanel7.add(jSeparator16);

        longeurPanel2.setBackground(new java.awt.Color(204, 204, 204));
        longeurPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        longeurPanel2.setMaximumSize(new java.awt.Dimension(100, 25));
        longeurPanel2.setMinimumSize(new java.awt.Dimension(80, 35));
        longeurPanel2.setPreferredSize(new java.awt.Dimension(80, 35));
        longeurPanel2.setLayout(new java.awt.CardLayout());

        deleteBtn.setBackground(new java.awt.Color(255, 51, 51));
        deleteBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        deleteBtn.setText("Delete");
        deleteBtn.setToolTipText("delete");
        deleteBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteBtn.setMaximumSize(new java.awt.Dimension(25, 25));
        deleteBtn.setMinimumSize(new java.awt.Dimension(25, 25));
        deleteBtn.setPreferredSize(new java.awt.Dimension(25, 25));
        deleteBtn.setRolloverEnabled(false);
        deleteBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });
        longeurPanel2.add(deleteBtn, "card2");

        jPanel7.add(longeurPanel2);

        jPanel2.add(jPanel7);

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        mainPanel.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(79, 105, 132));
        jPanel5.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel5.setMinimumSize(new java.awt.Dimension(0, 45));
        jPanel5.setPreferredSize(new java.awt.Dimension(490, 40));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        newBtn.setBackground(new java.awt.Color(44, 62, 80));
        newBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        newBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-add-new-32.png"))); // NOI18N
        newBtn.setToolTipText("open");
        newBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        newBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newBtn.setMaximumSize(new java.awt.Dimension(35, 35));
        newBtn.setMinimumSize(new java.awt.Dimension(35, 35));
        newBtn.setPreferredSize(new java.awt.Dimension(35, 35));
        newBtn.setRolloverEnabled(false);
        newBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });
        jPanel5.add(newBtn);

        openBtn.setBackground(new java.awt.Color(44, 62, 80));
        openBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        openBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-folder-32.png"))); // NOI18N
        openBtn.setToolTipText("open");
        openBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        openBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        openBtn.setMaximumSize(new java.awt.Dimension(35, 35));
        openBtn.setMinimumSize(new java.awt.Dimension(35, 35));
        openBtn.setPreferredSize(new java.awt.Dimension(35, 35));
        openBtn.setRolloverEnabled(false);
        openBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        openBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openBtnActionPerformed(evt);
            }
        });
        jPanel5.add(openBtn);

        saveBtn.setBackground(new java.awt.Color(44, 62, 80));
        saveBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-floppy-32.png"))); // NOI18N
        saveBtn.setToolTipText("save");
        saveBtn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        saveBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        saveBtn.setMaximumSize(new java.awt.Dimension(35, 35));
        saveBtn.setMinimumSize(new java.awt.Dimension(35, 35));
        saveBtn.setPreferredSize(new java.awt.Dimension(35, 35));
        saveBtn.setRolloverEnabled(false);
        saveBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        jPanel5.add(saveBtn);
        jPanel5.add(filler1);

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator8.setToolTipText("");
        jSeparator8.setAlignmentX(1.0F);
        jSeparator8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSeparator8.setMaximumSize(new java.awt.Dimension(2, 30));
        jSeparator8.setMinimumSize(new java.awt.Dimension(10, 30));
        jSeparator8.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanel5.add(jSeparator8);
        jPanel5.add(filler2);

        andoBtn.setBackground(new java.awt.Color(44, 62, 80));
        andoBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        andoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-arrow-32 (1).png"))); // NOI18N
        andoBtn.setToolTipText("back");
        andoBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        andoBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        andoBtn.setMaximumSize(new java.awt.Dimension(35, 35));
        andoBtn.setMinimumSize(new java.awt.Dimension(35, 35));
        andoBtn.setPreferredSize(new java.awt.Dimension(35, 35));
        andoBtn.setRolloverEnabled(false);
        andoBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        andoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                andoBtnActionPerformed(evt);
            }
        });
        jPanel5.add(andoBtn);

        rendoBtn.setBackground(new java.awt.Color(44, 62, 80));
        rendoBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        rendoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-arrow-32.png"))); // NOI18N
        rendoBtn.setToolTipText("forward");
        rendoBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        rendoBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rendoBtn.setMaximumSize(new java.awt.Dimension(35, 35));
        rendoBtn.setMinimumSize(new java.awt.Dimension(35, 35));
        rendoBtn.setPreferredSize(new java.awt.Dimension(35, 35));
        rendoBtn.setRolloverEnabled(false);
        rendoBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        rendoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rendoBtnActionPerformed(evt);
            }
        });
        jPanel5.add(rendoBtn);
        jPanel5.add(filler3);

        jSeparator9.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator9.setToolTipText("");
        jSeparator9.setAlignmentX(1.0F);
        jSeparator9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSeparator9.setMaximumSize(new java.awt.Dimension(2, 30));
        jSeparator9.setMinimumSize(new java.awt.Dimension(10, 30));
        jSeparator9.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanel5.add(jSeparator9);
        jPanel5.add(filler4);

        adjustBtn.setBackground(new java.awt.Color(44, 62, 80));
        adjustBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        adjustBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-move-32.png"))); // NOI18N
        adjustBtn.setToolTipText("forward");
        adjustBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        adjustBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        adjustBtn.setMaximumSize(new java.awt.Dimension(35, 35));
        adjustBtn.setMinimumSize(new java.awt.Dimension(35, 35));
        adjustBtn.setPreferredSize(new java.awt.Dimension(35, 35));
        adjustBtn.setRolloverEnabled(false);
        adjustBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        adjustBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adjustBtnActionPerformed(evt);
            }
        });
        jPanel5.add(adjustBtn);

        addDoorBtn.setBackground(new java.awt.Color(44, 62, 80));
        addDoorBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addDoorBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-door-32.png"))); // NOI18N
        addDoorBtn.setToolTipText("forward");
        addDoorBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addDoorBtn.setEnabled(false);
        addDoorBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addDoorBtn.setMaximumSize(new java.awt.Dimension(35, 35));
        addDoorBtn.setMinimumSize(new java.awt.Dimension(35, 35));
        addDoorBtn.setPreferredSize(new java.awt.Dimension(35, 35));
        addDoorBtn.setRolloverEnabled(false);
        addDoorBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addDoorBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addDoorBtnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addDoorBtnMouseReleased(evt);
            }
        });
        addDoorBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addDoorBtnActionPerformed(evt);
            }
        });
        jPanel5.add(addDoorBtn);

        addWindowBtn.setBackground(new java.awt.Color(44, 62, 80));
        addWindowBtn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        addWindowBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-window-32.png"))); // NOI18N
        addWindowBtn.setToolTipText("forward");
        addWindowBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        addWindowBtn.setEnabled(false);
        addWindowBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addWindowBtn.setMaximumSize(new java.awt.Dimension(35, 35));
        addWindowBtn.setMinimumSize(new java.awt.Dimension(35, 35));
        addWindowBtn.setPreferredSize(new java.awt.Dimension(35, 35));
        addWindowBtn.setRolloverEnabled(false);
        addWindowBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addWindowBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                addWindowBtnMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                addWindowBtnMouseReleased(evt);
            }
        });
        addWindowBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addWindowBtnActionPerformed(evt);
            }
        });
        jPanel5.add(addWindowBtn);
        jPanel5.add(filler5);

        jSeparator17.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator17.setToolTipText("");
        jSeparator17.setAlignmentX(1.0F);
        jSeparator17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSeparator17.setMaximumSize(new java.awt.Dimension(2, 30));
        jSeparator17.setMinimumSize(new java.awt.Dimension(10, 30));
        jSeparator17.setPreferredSize(new java.awt.Dimension(10, 20));
        jPanel5.add(jSeparator17);
        jPanel5.add(filler6);

        topView.setBackground(new java.awt.Color(44, 62, 80));
        topView.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        topView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/up.png"))); // NOI18N
        topView.setToolTipText("vue de dessus");
        topView.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        topView.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        topView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        topView.setMaximumSize(new java.awt.Dimension(35, 35));
        topView.setMinimumSize(new java.awt.Dimension(35, 35));
        topView.setPreferredSize(new java.awt.Dimension(35, 35));
        topView.setRolloverEnabled(false);
        topView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        topView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topViewActionPerformed(evt);
            }
        });
        jPanel5.add(topView);

        FrontView.setBackground(new java.awt.Color(44, 62, 80));
        FrontView.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        FrontView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/front.png"))); // NOI18N
        FrontView.setToolTipText("vue de dessus");
        FrontView.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FrontView.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        FrontView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        FrontView.setMaximumSize(new java.awt.Dimension(35, 35));
        FrontView.setMinimumSize(new java.awt.Dimension(35, 35));
        FrontView.setPreferredSize(new java.awt.Dimension(35, 35));
        FrontView.setRolloverEnabled(false);
        FrontView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        FrontView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FrontViewActionPerformed(evt);
            }
        });
        jPanel5.add(FrontView);

        backView.setBackground(new java.awt.Color(44, 62, 80));
        backView.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        backView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/back.png"))); // NOI18N
        backView.setToolTipText("vue de dessus");
        backView.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        backView.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        backView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        backView.setMaximumSize(new java.awt.Dimension(35, 35));
        backView.setMinimumSize(new java.awt.Dimension(35, 35));
        backView.setPreferredSize(new java.awt.Dimension(35, 35));
        backView.setRolloverEnabled(false);
        backView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        backView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backViewActionPerformed(evt);
            }
        });
        jPanel5.add(backView);

        LeftView.setBackground(new java.awt.Color(44, 62, 80));
        LeftView.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LeftView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/left.png"))); // NOI18N
        LeftView.setToolTipText("vue de dessus");
        LeftView.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        LeftView.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LeftView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        LeftView.setMaximumSize(new java.awt.Dimension(35, 35));
        LeftView.setMinimumSize(new java.awt.Dimension(35, 35));
        LeftView.setPreferredSize(new java.awt.Dimension(35, 35));
        LeftView.setRolloverEnabled(false);
        LeftView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        LeftView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeftViewActionPerformed(evt);
            }
        });
        jPanel5.add(LeftView);

        RightView.setBackground(new java.awt.Color(44, 62, 80));
        RightView.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        RightView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/right.png"))); // NOI18N
        RightView.setToolTipText("vue de dessus");
        RightView.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        RightView.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        RightView.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        RightView.setMaximumSize(new java.awt.Dimension(35, 35));
        RightView.setMinimumSize(new java.awt.Dimension(35, 35));
        RightView.setPreferredSize(new java.awt.Dimension(35, 35));
        RightView.setRolloverEnabled(false);
        RightView.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        RightView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RightViewActionPerformed(evt);
            }
        });
        jPanel5.add(RightView);

        mainPanel.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(mainPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        newMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        newMenu.setText("New");
        newMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuActionPerformed(evt);
            }
        });
        jMenu1.add(newMenu);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        saveMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        saveMenu.setText("save");
        saveMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenu);
        jMenu1.add(jSeparator1);

        undo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        undo.setText("Undo");
        undo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoActionPerformed(evt);
            }
        });
        jMenu1.add(undo);

        redo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        redo.setText("Redo");
        redo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoActionPerformed(evt);
            }
        });
        jMenu1.add(redo);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Export");

        retrait.setText("retrait");
        retrait.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retraitActionPerformed(evt);
            }
        });
        jMenu2.add(retrait);

        exportBrut.setText("brut");
        exportBrut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBrutActionPerformed(evt);
            }
        });
        jMenu2.add(exportBrut);

        exportFini.setText("fini");
        exportFini.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportFiniActionPerformed(evt);
            }
        });
        jMenu2.add(exportFini);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCouleurFrontActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCouleurFrontActionPerformed
        //color chooser
        Color c = JColorChooser.showDialog(this, "Choose a color", this.btnCouleurFront.getBackground());
        if (c != null) {
            this.chaletController.setFrontColor(c);
            btnCouleurFront.setBackground(c);
            repaint_();
        }
    }//GEN-LAST:event_btnCouleurFrontActionPerformed

    private void btnCouleurSidesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCouleurSidesActionPerformed
        //color chooser
        Color c = JColorChooser.showDialog(this, "Choose a color", this.btnCouleurSides.getBackground());
        if (c != null) {
            this.chaletController.setSidesColor(c);
            btnCouleurSides.setBackground(c);
            repaint_();
        }
    }//GEN-LAST:event_btnCouleurSidesActionPerformed

    private void changerPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changerPActionPerformed
        this.chaletController.setFrontIsPrincipal(changerP.isSelected());
        repaint_();
    }//GEN-LAST:event_changerPActionPerformed

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        newProjet();
    }//GEN-LAST:event_newBtnActionPerformed

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
//        //Dialoge avec deux choix personaliser
//        String[] options = {"Brut","Fini"};
//        int response = JOptionPane.showOptionDialog(null, "Voulez vous exporter le chalet en brut ou fini?", "Sauvegarder", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (response == 0) {
//            this.chaletController.exportSTLBrut();
//            waringDialog("Le fichier a été sauvegarder dans le dossier STL avec succès");
//        } else if (response == 1) {
//            this.chaletController.exportSTLFini();
//            waringDialog("Le fichier a été sauvegarder dans le dossier STL avec succès");
//        }
        //open select folder dialog
        saveProjet();
    }//GEN-LAST:event_saveBtnActionPerformed

    private void andoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_andoBtnActionPerformed
        this.chaletController.undo();
        this.repaint_();
    }//GEN-LAST:event_andoBtnActionPerformed

    private void rendoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rendoBtnActionPerformed
        this.chaletController.redo();
        this.repaint_();
    }//GEN-LAST:event_rendoBtnActionPerformed

    private void topViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topViewActionPerformed
        this.chaletController.setView(View.TopView);
        repaint_();
    }//GEN-LAST:event_topViewActionPerformed

    private void FrontViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FrontViewActionPerformed
        this.chaletController.setView(View.FrontView);
        repaint_();
    }//GEN-LAST:event_FrontViewActionPerformed

    private void LeftViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeftViewActionPerformed
        this.chaletController.setView(View.LeftView);
        repaint_();
    }//GEN-LAST:event_LeftViewActionPerformed

    private void RightViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RightViewActionPerformed
        this.chaletController.setView(View.RightView);
        repaint_();
    }//GEN-LAST:event_RightViewActionPerformed

    private void backViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backViewActionPerformed
        this.chaletController.setView(View.BackView);
        repaint_();
    }//GEN-LAST:event_backViewActionPerformed

    private void LengthCoteFootActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LengthCoteFootActionPerformed
        calculLengthCote();
    }//GEN-LAST:event_LengthCoteFootActionPerformed

    private void LengthFacadeFootActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LengthFacadeFootActionPerformed
        calculLengthFacade();
    }//GEN-LAST:event_LengthFacadeFootActionPerformed

    private void LengthFacadeInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LengthFacadeInchActionPerformed
        calculLengthFacade();
    }//GEN-LAST:event_LengthFacadeInchActionPerformed

    private void HeightFootActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HeightFootActionPerformed
        calculHeight();
    }//GEN-LAST:event_HeightFootActionPerformed

    private void HeightInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HeightInchActionPerformed
        calculHeight();
    }//GEN-LAST:event_HeightInchActionPerformed

    private void thicknessInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thicknessInchActionPerformed
        calculThickness();
    }//GEN-LAST:event_thicknessInchActionPerformed

    private void adjustBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adjustBtnActionPerformed
        canv.adjustScale();
    }//GEN-LAST:event_adjustBtnActionPerformed

    private void addPorteActionPerformed(java.awt.event.ActionEvent evt) {                                         
        this.chaletController.addNewDoor();
    }

    private void addFenetreActionPerformed(java.awt.event.ActionEvent evt) {                                           
        this.chaletController.addNewWindow();
    }

    private void AccesoireXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccesoireXActionPerformed
        updateAccessorie();
    }//GEN-LAST:event_AccesoireXActionPerformed

    private void AccesoireYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AccesoireYActionPerformed
        updateAccessorie();

    }//GEN-LAST:event_AccesoireYActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        //confirmation dialog
        int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this accessorie?", "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.chaletController.removeAccessorie();
            repaint_();
        }
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void accesoiresLongueurInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accesoiresLongueurInchActionPerformed
      updateAccessorie();
    }//GEN-LAST:event_accesoiresLongueurInchActionPerformed

    private void accesoiresHauteurInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accesoiresHauteurInchActionPerformed
        updateAccessorie();
    }//GEN-LAST:event_accesoiresHauteurInchActionPerformed

    private void distanceMinimunInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distanceMinimunInchActionPerformed
        calculDistanceMinimun();
    }//GEN-LAST:event_distanceMinimunInchActionPerformed

    private void openBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openBtnActionPerformed
           openProjet();
    }//GEN-LAST:event_openBtnActionPerformed

    private void addDoorBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addDoorBtnActionPerformed
        this.chaletController.addNewDoor();
    }//GEN-LAST:event_addDoorBtnActionPerformed

    private void addWindowBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addWindowBtnActionPerformed
        this.chaletController.addNewWindow();
    }//GEN-LAST:event_addWindowBtnActionPerformed

    private void nomChaletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nomChaletActionPerformed
        if(nomChalet.getText().length() > 0) {
            this.chaletController.setName(nomChalet.getText());
        }else{
            this.chaletController.setName("NewChalet");
            nomChalet.setText("NewChalet");
        }

    }//GEN-LAST:event_nomChaletActionPerformed

    private void grooveInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grooveInchActionPerformed
        calculGroove();
    }//GEN-LAST:event_grooveInchActionPerformed

    private void LengthFacadefractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LengthFacadefractionActionPerformed
        calculLengthFacade();
    }//GEN-LAST:event_LengthFacadefractionActionPerformed

    private void HeightFractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HeightFractionActionPerformed
        calculHeight();
    }//GEN-LAST:event_HeightFractionActionPerformed

    private void LengthCoteInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LengthCoteInchActionPerformed
        calculLengthCote();
    }//GEN-LAST:event_LengthCoteInchActionPerformed

    private void LengthCotefractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LengthCotefractionActionPerformed
        calculLengthCote();
    }//GEN-LAST:event_LengthCotefractionActionPerformed

    private void thicknessFractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thicknessFractionActionPerformed
       calculThickness();
    }//GEN-LAST:event_thicknessFractionActionPerformed

    private void distanceMinimunFractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distanceMinimunFractionActionPerformed
       calculDistanceMinimun();
    }//GEN-LAST:event_distanceMinimunFractionActionPerformed

    private void grooveFractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_grooveFractionActionPerformed
       calculGroove();
    }//GEN-LAST:event_grooveFractionActionPerformed

    private void DirectionToitCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DirectionToitCheckBoxActionPerformed
        this.chaletController.setDirection_toit(DirectionToitCheckBox.isSelected());
        repaint_();
    }//GEN-LAST:event_DirectionToitCheckBoxActionPerformed

    private void afficherGrilleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afficherGrilleActionPerformed
        canv.setGrilleActive(afficherGrille.isSelected());
        repaint_();
    }//GEN-LAST:event_afficherGrilleActionPerformed

    private void angleDegreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_angleDegreActionPerformed
        //check if angleDegre.getText() can be parsed to double
        try {
            String text = angleDegre.getText().replace(',', '.');
            this.chaletController.setAngle(Double.parseDouble(text));
            repaint_();
        } catch (NumberFormatException e) {
            angleDegre.setText(String.format("%.2f", this.chaletController.getAngle()));
        }
    }//GEN-LAST:event_angleDegreActionPerformed

    private void distanceBetweenLinesInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distanceBetweenLinesInchActionPerformed
        calculDistanceBetweenLinesGrid();
    }//GEN-LAST:event_distanceBetweenLinesInchActionPerformed

    private void distanceBetweenLinesIFractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distanceBetweenLinesIFractionActionPerformed
        calculDistanceBetweenLinesGrid();
    }//GEN-LAST:event_distanceBetweenLinesIFractionActionPerformed

    private void distanceBetweenComunsInchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distanceBetweenComunsInchActionPerformed
        calculDistanceBetweenLinesComuns();
    }//GEN-LAST:event_distanceBetweenComunsInchActionPerformed

    private void distanceBetweenComunsFractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_distanceBetweenComunsFractionActionPerformed
        calculDistanceBetweenLinesComuns();
    }//GEN-LAST:event_distanceBetweenComunsFractionActionPerformed

    private void accesoiresLongueurFractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accesoiresLongueurFractionActionPerformed
updateAccessorie();
    }//GEN-LAST:event_accesoiresLongueurFractionActionPerformed

    private void accesoiresHauteurFractionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accesoiresHauteurFractionActionPerformed
updateAccessorie();         
    }//GEN-LAST:event_accesoiresHauteurFractionActionPerformed

    private void displayeBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayeBackgroundActionPerformed
        this.canv.setShowBackground(displayeBackground.isSelected());
        repaint_();
    }//GEN-LAST:event_displayeBackgroundActionPerformed

    private void addDoorBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addDoorBtnMousePressed
        if(!this.addDoorBtn.isEnabled()) return;

        ImageIcon cursorImage = new ImageIcon(getClass().getResource("/icons8-door-32.png"));
        // Set custom cursor
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImage.getImage(),
                new Point(cursorImage.getIconWidth() / 2, cursorImage.getIconHeight() / 2),
                "add New Door"
        ));
    }//GEN-LAST:event_addDoorBtnMousePressed

    private void addDoorBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addDoorBtnMouseReleased
        if (!this.addDoorBtn.isEnabled()) return;

        //calculer la position relative au canvas
        Point p = this.canv.getLocationOnScreen();
        p.x = MouseInfo.getPointerInfo().getLocation().x - p.x;
        p.y = MouseInfo.getPointerInfo().getLocation().y - p.y;
        //add new door
        this.canv.addNewDor( p.x, p.y);
        setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_addDoorBtnMouseReleased

    private void addWindowBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addWindowBtnMousePressed
        if (!this.addWindowBtn.isEnabled()) return;

        ImageIcon cursorImage = new ImageIcon(getClass().getResource("/icons8-window-32.png"));
        // Set custom cursor
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImage.getImage(),
                new Point(cursorImage.getIconWidth() / 2, cursorImage.getIconHeight() / 2),
                "add New Windows"
        ));
    }//GEN-LAST:event_addWindowBtnMousePressed

    private void addWindowBtnMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addWindowBtnMouseReleased
        if(!this.addWindowBtn.isEnabled()) return;

        //calculer la position relative au canvas
        Point p = this.canv.getLocationOnScreen();
        p.x = MouseInfo.getPointerInfo().getLocation().x - p.x;
        p.y = MouseInfo.getPointerInfo().getLocation().y - p.y;
        //add new door
        this.canv.addNewWindows( p.x, p.y);
        setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_addWindowBtnMouseReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        openProjet();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void exportBrutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBrutActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select a folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        //change btn to save
        chooser.setApproveButtonText("Save");
        //if the user select a folder
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            //get the path of the folder
            String path = chooser.getSelectedFile().toString();
            this.chaletController.exportSTLBrut(path);
        }
    }//GEN-LAST:event_exportBrutActionPerformed

    private void exportFiniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportFiniActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select a folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        //change btn to save
        chooser.setApproveButtonText("Save");
        //if the user select a folder
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            //get the path of the folder
            String path = chooser.getSelectedFile().toString();
            this.chaletController.exportSTLFini(path);
        }
    }//GEN-LAST:event_exportFiniActionPerformed

    private void retraitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retraitActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select a folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        //change btn to save
        chooser.setApproveButtonText("Save");
        //if the user select a folder
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            //get the path of the folder
            String path = chooser.getSelectedFile().toString();
            this.chaletController.exportSTLRetrait(path);
        }
    }//GEN-LAST:event_retraitActionPerformed

    private void redoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoActionPerformed
        this.chaletController.redo();
        this.repaint_();
    }//GEN-LAST:event_redoActionPerformed

    private void undoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoActionPerformed
       this.chaletController.undo();
        this.repaint_();
    }//GEN-LAST:event_undoActionPerformed

    private void saveMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuActionPerformed
       saveProjet();
    }//GEN-LAST:event_saveMenuActionPerformed

    private void newMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuActionPerformed
        newProjet();
    }//GEN-LAST:event_newMenuActionPerformed


    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField AccesoireX;
    private javax.swing.JTextField AccesoireY;
    private javax.swing.JCheckBox DirectionToitCheckBox;
    private javax.swing.JButton FrontView;
    private javax.swing.JLabel Height;
    private javax.swing.JTextField HeightFoot;
    private javax.swing.JTextField HeightFraction;
    private javax.swing.JTextField HeightInch;
    private javax.swing.JButton LeftView;
    private javax.swing.JTextField LengthCoteFoot;
    private javax.swing.JTextField LengthCoteInch;
    private javax.swing.JTextField LengthCotefraction;
    private javax.swing.JTextField LengthFacadeFoot;
    private javax.swing.JTextField LengthFacadeInch;
    private javax.swing.JTextField LengthFacadefraction;
    private javax.swing.JButton RightView;
    private javax.swing.JTextField accesoiresHauteurFraction;
    private javax.swing.JTextField accesoiresHauteurInch;
    private javax.swing.JTextField accesoiresLongueurFraction;
    private javax.swing.JTextField accesoiresLongueurInch;
    private javax.swing.JButton addDoorBtn;
    private javax.swing.JButton addWindowBtn;
    private javax.swing.JButton adjustBtn;
    private javax.swing.JCheckBox afficherGrille;
    private javax.swing.JButton andoBtn;
    private javax.swing.JTextField angleDegre;
    private javax.swing.JPanel anglePanel;
    private javax.swing.JButton backView;
    private javax.swing.JButton btnCouleurFront;
    private javax.swing.JButton btnCouleurSides;
    private javax.swing.JCheckBox changerP;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JCheckBox displayeBackground;
    private javax.swing.JTextField distanceBetweenComunsFraction;
    private javax.swing.JTextField distanceBetweenComunsInch;
    private javax.swing.JTextField distanceBetweenLinesIFraction;
    private javax.swing.JTextField distanceBetweenLinesInch;
    private javax.swing.JTextField distanceMinimunFraction;
    private javax.swing.JTextField distanceMinimunInch;
    private javax.swing.JLabel epaisseurLabel;
    private javax.swing.JLabel epaisseurLabel1;
    private javax.swing.JLabel epaisseurLabel2;
    private javax.swing.JLabel epaisseurLabel3;
    private javax.swing.JLabel epaisseurLabel4;
    private javax.swing.JLabel epaisseurLabel5;
    private javax.swing.JLabel epaisseurLabel6;
    private javax.swing.JLabel epaisseurLabel7;
    private javax.swing.JLabel epaisseurLabelInch;
    private javax.swing.JLabel epaisseurLabelInch1;
    private javax.swing.JLabel epaisseurLabelInch2;
    private javax.swing.JLabel epaisseurLabelInch3;
    private javax.swing.JLabel epaisseurLabelInch4;
    private javax.swing.JLabel epaisseurLabelInch5;
    private javax.swing.JLabel epaisseurLabelInch6;
    private javax.swing.JLabel epaisseurLabelInch7;
    private javax.swing.JPanel epaisseurPanel;
    private javax.swing.JPanel epaisseurPanel1;
    private javax.swing.JPanel epaisseurPanel2;
    private javax.swing.JPanel epaisseurPanel3;
    private javax.swing.JPanel epaisseurPanel4;
    private javax.swing.JPanel epaisseurPanel5;
    private javax.swing.JPanel epaisseurPanel6;
    private javax.swing.JPanel epaisseurPanel7;
    private javax.swing.JMenuItem exportBrut;
    private javax.swing.JMenuItem exportFini;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.JTextField grooveFraction;
    private javax.swing.JTextField grooveInch;
    private javax.swing.JLabel hauteurLabalFoot;
    private javax.swing.JLabel hauteurLabelInch;
    private javax.swing.JPanel hauteurPanel;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel largeurLabalFoot;
    private javax.swing.JLabel largeurLabel;
    private javax.swing.JLabel largeurLabel2;
    private javax.swing.JLabel largeurLabel4;
    private javax.swing.JLabel largeurLabelInch;
    private javax.swing.JPanel largeurPanel;
    private javax.swing.JPanel largeurPanel2;
    private javax.swing.JPanel largeurPanel4;
    private javax.swing.JLabel longeurLabalFoot;
    private javax.swing.JLabel longeurLabel2;
    private javax.swing.JLabel longeurLabelInch;
    private javax.swing.JPanel longeurPanel;
    private javax.swing.JPanel longeurPanel2;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton newBtn;
    private javax.swing.JMenuItem newMenu;
    private javax.swing.JTextField nomChalet;
    private javax.swing.JButton openBtn;
    private javax.swing.JMenuItem redo;
    private javax.swing.JButton rendoBtn;
    private javax.swing.JMenuItem retrait;
    private javax.swing.JButton saveBtn;
    private javax.swing.JMenuItem saveMenu;
    private javax.swing.JTextField thicknessFraction;
    private javax.swing.JTextField thicknessInch;
    private javax.swing.JButton topView;
    private javax.swing.JMenuItem undo;
    private javax.swing.JLabel uuid;
    // End of variables declaration//GEN-END:variables
}
