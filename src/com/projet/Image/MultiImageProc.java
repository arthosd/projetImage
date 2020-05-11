package com.imageLibs.Image;

import com.imageLibs.math.Histogramme;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class MultiImageProc  {

    public static int [] calculHistogramme (BufferedImage bf) {

        int [] hist = Histogramme.calculHistogramme(bf); // on calcule l'histogramme et on l'étire

        return hist ;
    }
    public static BufferedImage etirer (BufferedImage bf , int from , int where , int lmax , int lmin ) throws IOException {

        int intensity;

       BufferedImage nbf = new BufferedImage(1200, 1600 , BufferedImage.TYPE_INT_RGB);

        for (int y = from ; y < where ;  y ++) {
            for (int x = 0 ; x < bf.getWidth() ; x ++) {

                //intensity = (int) (256/(lmax-lmin))*(ImageProc.getGris(bf , x , y) - lmin);
                //System.out.println("voici la nouvelle intensité : "+intensity);
                //setGreyIntensity(nbf,x,y,intensity);
                System.out.println("Juste pour voir si c'est bocler");
            }


            //ImageProc.afficherImage(nbf , "COUCOU");
        }

        return bf;
    }
    public static BufferedImage otsuSeuillage (BufferedImage bf , int [] normalise, int from , int to) {

        Otsu otsu = new Otsu ();

        int seuil = (int) otsu.getOtsuThreshold(normalise);
        seuil +=100;
        System.out.println("Voici le treshold trouvé " + seuil);

        try {
            MultiImageProc.seuillage(bf ,seuil, from , to);
            return bf;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bf;
    }
    private static void setGreyIntensity (BufferedImage bf , int x , int y , int intensity) {

        int [] intens = { intensity , intensity , intensity , 255 };
        bf.getRaster().setPixel(x,y,intens);
    }
    /**
     * Utilise le multi threading pour augmenter les performances du comptage
     *
     * @param bi
     * @return
     */
    public static BufferedImage greyScaleThread (BufferedImage bi , int from , int to) {

        int p , rouge , bleu, vert , gris;

        // Le traitement sur une localisation précise
        for (int i = 0 ;i <bi.getWidth() ; i ++ ) {

            for (int y = from ; y < to ; y ++) {

                p = bi.getRGB(i, y);

                rouge = (p>>16)&0xff;
                bleu = p&0xff;
                vert = (p>>8)&0xff;
                gris = (rouge+vert+bleu)/3;

                int [] couleur = { gris , gris , gris , 255 };

                bi.getRaster().setPixel(i, y, couleur);
            }
        }

        return bi;
    }
    public static BufferedImage seuillage (BufferedImage bi,int seuil , int from , int to ) throws IOException {

        int couleur,red;

        for (int y = 0 ; y<bi.getHeight() ; y ++) {
            for (int x = from ; x <to ; x ++) {
                couleur = bi.getRGB(x, y);
                red  = (couleur>>16)&0xff;

                if (red < seuil)
                    bi.setRGB(x, y, Color.black.getRGB());
                else
                    bi.setRGB(x, y, Color.white.getRGB());
            }
        }

        return bi;
    }

    /**
     *
     * Applique un filtre médian
     *
     * @param bf le filtre qui possède l'image
     * @param from de où
     * @param to à où
     *
     * @return
     */
    public static BufferedImage filtreMadian(BufferedImage bf, int from, int to) {

        //new BufferedImage(bf.getWidth() , bf.getHeight() , BufferedImage.TYPE_INT_RGB);

        int de = from;
        int vers = to;

        int [] buff = new int [25]; // Tableau qui va contenir les tableau de la matrice

        BufferedImage nBf = new BufferedImage(bf.getWidth() , bf.getHeight() , BufferedImage.TYPE_INT_RGB);

        if (de == 0)
            de +=3;
        if (vers == bf.getHeight())
            vers -=3;

        for (int y = de ; y <  vers; y ++) {
            for(int x = 3 ; x <bf.getWidth() -3 ; x++) {

                // On charge la matrice buffered

                int compteur = 0;

                for (int i = -1 ;i <4 ; i ++) {
                    for (int j = -1; j < 4 ; j ++) {

                        buff[compteur] = ImageProc.getGris(bf, x+j,y+i);
                        System.out.println("Charge la matrice ----> "+ buff[compteur]);

                        compteur++;
                    }
                }

                // Fin du chargement

                Arrays.sort(buff);

                int valeurMedian = buff[12]; // ON récupère la valeur médian

                setGreyIntensity(nBf,x,y , valeurMedian);
            }

            try {
                ImageProc.afficherImage(nBf , "threads");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return nBf;
    }
}
