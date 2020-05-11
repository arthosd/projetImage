package com.imageLibs.Logique;

import com.imageLibs.Gui.Console;
import com.imageLibs.Image.ImageProc;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Compter {

    private BufferedImage bf;
    private Console console;
    private JButton count;

    private void compterMarche () throws IOException {

        int nombreMarche = 0;


        double lStartTime = System.nanoTime();

        ImageProc.afficherImage(this.bf , "Avant tout Traitement");
        this.console.print("Taille de l'image : "+this.bf.getWidth()+" "+ this.bf.getHeight());
        this.console.print("Transformation de l'iùage en niveau de gris ...");
        ImageProc.greyScale(this.bf);// On transforme l'image en niveau de gris
        ImageProc.afficherImage(this.bf , "Niveaux de gris");
        ImageProc.otsuSeuillage(this.bf); // On trouve le seuil automatiquement avec otsu
        this.console.print("Recherche du seuil ...");
        ImageProc.afficherImage(this.bf , "Après seuillage");
        this.console.print("Filtrage de l'image par un filtre médian ...");
        BufferedImage filtreBuff = ImageProc.filtreMadian(this.bf);
        ImageProc.afficherImage(filtreBuff,"Apres filtre");
        this.console.print("Calcul de l'histogramme projete");
        BufferedImage img = ImageProc.histogrammeProjeter(filtreBuff);
        ImageProc.afficherImage(img , "Histogramme");
        nombreMarche = ImageProc.compterMarche(img , (img.getWidth()*3)/4);
        double lEndTime = System.nanoTime();
        double output = (lEndTime - lStartTime)/ 1000000000.0; // on calcul le temps d'éxcécution


        ImageProc.afficherImage(img , "COUCOU");

        this.console.print("Nombre de marche : " + String.valueOf(nombreMarche));
        this.console.print("Temps d'éxcécution : "+String.valueOf(output));
        this.count.setEnabled(true);
    }

    private void init (BufferedImage image , Console console , JButton count) throws IOException {
        this.bf = image;
        this.console = console;
        this.count = count;

        this.compterMarche();
    }
    public Compter (BufferedImage image , Console console, JButton count) throws IOException {
        this.init(image , console , count);
    }
}
