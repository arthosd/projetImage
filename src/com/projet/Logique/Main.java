package com.projet.Logique;

import com.projet.imageProc.*;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {


    public static void main (String [] args) {

        try {
            //BufferedImage bf = ImageProc.chargerImage("/home/elie/Images/escalier/e2.jpg");
            //BufferedImage bf = ImageProc.chargerImage("/home/elie/Images/escalier/sarah.jpeg");
            //BufferedImage bf = ImageProc.chargerImage("/home/elie/Images/escalier/comment-eclaircir-un-escalier-en-bois-4058-720-0.jpg");
            //BufferedImage bf = ImageProc.chargerImage("/home/elie/Images/escalier/escalier2.jpeg");
            //BufferedImage bf = ImageProc.chargerImage("/home/elie/Images/escalier/escalier-bois.jpg");
            //BufferedImage bf = ImageProc.chargerImage("/home/elie/Images/escalier/etage.jpg");
            BufferedImage bf = ImageProc.chargerImage("/home/elie/Images/escalier/fauteuil-monte-escaliers.jpg");
            ImageProc.greyScale(bf); // On transforme l'image en noir et blanc
            ImageProc.afficherImage(bf , "avant");

            ImageProc.otsuSeuillage(bf);
            ImageProc.afficherImage(bf , "Seuillage d'otsu");

            BufferedImage bc = ImageProc.filtreMadian(bf);

            ImageProc.afficherImage(bc , "Apres filtre median");

            //ImageProc.afficherImage(ImageProc.filtreMadian(bc) , "Deuxieme filtre");
            ImageProc.afficherImage(ImageProc.filtreMadian(ImageProc.histogrammeProjeter(bc)) , "Projeter");

            ImageProc.compterMarche(bc);

            //ImageProc.afficherImage(ImageProc.histogrammeProjeter(bf) , "Histogramme Projeté");

        }catch (IOException e) {
            System.out.println("ERREUR CHATGEMENT IMAGE.");
        }
    }
}
