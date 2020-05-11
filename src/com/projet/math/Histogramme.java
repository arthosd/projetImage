package com.imageLibs.math;

import java.awt.image.BufferedImage;

public class Histogramme {

    /**
     * Fonction qui étire l'histogramme passer en paramètre
     *
     * @param histogramme histogramme à étirer
     * @return un nouveau tableau
     */
    public static int [] etirement (int histogramme []) {

        int [] hist = new int [256]; // Le tableau qui va  contenir les nouvelle valeur

        int min = 0;
        int max = 256;

        int lmin = Histogramme.getMinIntensity(histogramme);
        System.out.println ("Voici 'intensité minimal"+lmin );
        int lmax = Histogramme.getMaxIntensity(histogramme);
        System.out.println ("Voici 'intensité maximal "+lmax );
        // On doit tout d'abord trouver le minimum

        for (int i = 0 ; i <hist.length ; i ++ ) {

            hist[i] = (max/(lmax-lmin))*(i- lmin);
            System.out.println("Voila l'intensité --->" + hist[i]);
        }

        return null;
    }

    /**
     * Permet de calculer l'histogramme cumulé croissant d'un histogramme.
     *
     * @param histogramme L'histogramme dont il faut calculer l'hcc
     * @return La matrice de l'histogramme cumulé croissant
     */
    public static int [] calculHcc (int [] histogramme) {

        int matricePoubelle [] = new int  [256];

        for (int i = 0 ; i <histogramme.length ; i ++) {

            if (i == 0)
                matricePoubelle [i] = histogramme [i];
            else
                matricePoubelle [i] = matricePoubelle [i-1] + histogramme[i];
        }

        System.out.println("HCC ---------->");

        for (int i = 0 ; i < matricePoubelle.length ; i ++) {

            System.out.println(matricePoubelle [i]);
        }

        return matricePoubelle;
    }
    /**
     * Retourne la valeur maximum dans un tableau
     *
     * @param matrice Tableau d'entiers
     *
     * @return le nombre maximal dans le tableau
     */
    public static int getMax (int [] matrice) {

        int max = 0;

        for (int i = 0 ; i<matrice.length ; i ++)
            if (max < matrice [i])
                max = matrice[i];

        return max;
    }

    /**
     * Retourne l'intensité maximal dans un histogramme
     *
     * @param hist histogramme dans lequel il faut toruver l'intensité maximal
     * @return retourne l'indice contenant l'intensité maximal
     */
    public static int getMaxIntensity (int hist []) {

        int indice = -1;

        for (int i = 0 ; i <hist.length ; i ++) {

            if (hist[i]!= 0)
                indice = i;
        }

        return indice;
    }

    /**
     * Retourne l'intensité minimal dans un histogramme
     * Renvoie -1 s'il n'a rien trouvé
     * @param hist l'histogramme dans lequel on doit chercher l'intensité
     * @return
     */
    public static int getMinIntensity (int hist []) {

        for (int i = 0 ; i < hist.length ; i ++) {

            if (hist[i] != 0)
                return i;
        }

        return -1;
    }

    /**
     *
     * Retourne la valeur minimal dans un tableau
     *
     * @param matrice
     * @return le min
     */
    private static int getMin (int [] matrice) {

        int min = 0;

        for (int i = 0 ; i <256 ; i ++) {

            if(min > matrice[i])
                min = matrice [i];
        }

        return min;
    }

    /**
     * Normalise toutes les valeurs du tableau entre O et 100
     *
     * @param matrice la matrice qui va être normalisé
     * @param max La valeur maximale qui va être utilisé pour normaliser la matrice
     *
     * @return Une matrice normalisé
     */
    public static int [] normalise (int [] matrice , int max) {

        int [] inter = new int [256];

        for (int i = 0 ; i <256 ; i++) {

            inter [i] = (int) (((double) matrice[i]/max)*100);
            //System.out.println(i + " "+inter [i]);
        }

        return inter;
    }

    /**
     * Normalise un histogramme entre 0 et 100
     *
     * @param matrice Histogramme non normalisé.
     *
     * @return L'histogramme normalisé
     */
    public static int [] normalise (int [] matrice) {

        int [] inter = new int [256];
        int max = Histogramme.getMax(matrice); // On récupère la valeur maximale de la matrice

        for (int i = 0 ; i <256 ; i++) {

            inter [i] = (int) (((double) matrice[i]/max)*100);
            System.out.println(i + " "+inter [i]);
        }

        return inter;
    }

    /**
     * Calcul l'histogramme à partir de d'
     *
     * @param matrice la matrice qui va etre utilisé pour le calcul de l'histogramme
     * @param tailleX La taille du buffer en longeur
     * @param tailleY La taille du buffer en largeur
     * @param i Le niveau de gris
     * @return L'histogramme
     */
    public static int [] calculHistogramme (BufferedImage bi ,int matrice [], int tailleX , int tailleY , int i) {

        int indice;

        for (int x = 0 ; x < tailleX ; x ++) {

            for (int y = 0 ; y <tailleY ; y ++) {

                indice = i;
                matrice [indice] += 1;
            }
        }

        return matrice;
    }


    /**
     * Calcul l'histogramme des niveaux de gris dans une images DEJA GRISE
     *
     * @param bi Le buffer comptenant l'image en niveau de gris
     * @return retourne l'histogramme des niveaux de gris.
     */
    public static int [] calculHistogramme (BufferedImage bi) {

        int [] histo = new int [256];// Un tableau de 256 valeurs. Chaque case du tableau représente un niveau de gris.

        int x = bi.getWidth();// Longueur de l'image
        int y = bi.getHeight();//Largeur de l'image

        // On calcul tout l'histogramme

        for (int i = 0 ; i < y ; i ++) {// On compte les Y

            for (int j = 0 ; j < x ; j ++) {// On compte les x

                int couleur = bi.getRGB(j,i);
                int gris = (couleur>>8)&0xff;

                histo [gris] += 1;
            }
        }

        for (int i = 0 ; i < histo.length ; i ++) {

            System.out.println(histo [i]);
        }

        return histo;
    }
}