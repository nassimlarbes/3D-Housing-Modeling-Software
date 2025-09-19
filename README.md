# ChalCLT – 3D Housing Modeling Software 
**(English version follows below)**

> Ce projet a été développé à l’automne 2023 sous la supervision du professeur **Marc-Philippe Parent**, dans le cadre du cours **GLO-2004 – Génie logiciel orienté objet** à l’Université Laval.

## Description

ChalCLT est une application interactive conçue pour la modélisation et l’exportation de panneaux de chalets en bois lamellé-croisé (CLT). Développé dans le cadre d’un projet académique en collaboration avec un partenaire industriel, ce logiciel vise à simplifier le processus de conception jusqu’à la fabrication en combinant la planification et l’exportation STL dans un environnement unique.

## Technologies

- Java 17  
- Swing (interface graphique)  
- Git (gestion de version)  
- Aucune bibliothèque externe

## Fonctionnalités

- **Création de chalets** avec dimensions personnalisables (longueur, largeur, hauteur, épaisseur des murs)
- **Édition des murs** : chaque mur (Façade, Arrière, Gauche, Droit) est éditable indépendamment avec mise à jour visuelle automatique
- **Ajout d’accessoires** : portes et fenêtres ajoutées dynamiquement avec validation de position
- **Modélisation du toit** : toit à pente configurable avec génération automatique des panneaux de support
- **Navigation entre vues** : vue en plan, vues en élévation, aperçu des panneaux
- **Contrôles avancés** : zoom infini, annulation/rétablissement (jusqu’à 99 999 actions), affichage des dimensions en survol
- **Unités impériales** : toutes les mesures supportent les fractions de pouces (ex : 96 11/64")
- **Sauvegarde/chargement** de projets pour une reprise ultérieure
- **Exportation STL** :
  - Panneaux bruts (`Projet_Brut_X.stl`)
  - Panneaux finis avec ouvertures (`Projet_Fini_X.stl`)
  - Volumes de matière à retirer (`Projet_Retrait_X_Y.stl`)

## Utilisation

1. Démarrer un nouveau projet pour générer un chalet par défaut.
2. Modifier les dimensions ou ajouter des accessoires via le panneau latéral.
3. Naviguer entre les vues pour affiner le design et valider les contraintes.
4. Exporter les fichiers STL une fois la conception finalisée.


## Remarques

- Les interactions utilisateur (glisser, redimensionnement, édition) se font directement dans la vue.
- Une grille d’aide au positionnement est disponible (non magnétique).
- Les seules fenêtres contextuelles permises sont celles liées aux fichiers et messages d’erreur.

---

# ChalCLT – 3D Housing Modeling Software  
**(Version française ci-dessus)**

## Description

ChalCLT is an interactive software designed for the modeling and export of chalet panels built with Cross-Laminated Timber (CLT). Developed as part of an academic project in collaboration with an industry partner, this tool simplifies the entire design-to-manufacturing process by combining layout planning and STL export in a single platform.

## Features

- **Chalet creation** with customizable dimensions (length, width, height, wall thickness)
- **Wall editing**: individually editable walls (Front, Back, Left, Right), with automatic visual updates
- **Accessory management**: dynamically add and edit doors and windows with placement validation
- **Roof modeling**: configurable sloped roof with automatic generation of ridge and support panels
- **Multi-view navigation**: top view, elevation views, and panel previews
- **Advanced controls**: infinite zoom, undo/redo (up to 99,999 actions), live dimension display on hover
- **Imperial units**: all measurements support inch fractions (e.g., 96 11/64")
- **Project saving/loading** for future editing
- **STL export**:
  - Raw panels (`Project_Brut_X.stl`)
  - Finished panels with openings (`Project_Fini_X.stl`)
  - Material removal volumes (`Project_Retrait_X_Y.stl`)

## Usage

1. Start a new project to initialize a default chalet layout.
2. Use the side panel to edit wall dimensions or add accessories.
3. Switch between views to refine the design and validate constraints.
4. Export the STL files once the model is complete.

## Technologies

- Java 17  
- Swing (GUI)  
- Git (version control)  
- No external libraries

## Notes

- All interactions (drag, resize, edit) are done directly within the view.
- A non-magnetic grid can be toggled for alignment assistance.
- Only file dialogs and error messages are allowed as pop-up windows.
