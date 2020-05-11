package com.imageLibs.Image;

import com.imageLibs.math.Histogramme;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.Arrays;



/**
 * Classe permettant le traitement d'image sur le buffer Directement.
 *
 * @author elie
 *
 */
public class ImageProc {

    public static BufferedImage filtreMadian(BufferedImage bf  ) {

        BufferedImage untouched = bf;
        BufferedImage modifier = new BufferedImage(bf.getWidth() , bf.getHeight() , BufferedImage.TYPE_INT_RGB);

        int [] buff = new int [25]; // Tableau qui va contenir les tableau de la matrice

        for (int y = 3 ; y < bf.getHeight() -3; y ++) {

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

                ImageProc.setGreyIntensity(modifier,x,y , valeurMedian);
            }
        }

        return modifier;
    }
    public static int getValeurMax (BufferedImage histProj) {

        int valeurMax = 0;
        int compteur = 0 ;

        int listes [] = new int [histProj.getHeight()];


        for (int y = 0 ; y < histProj.getHeight() ; y ++) {

            for (int x = 0 ; x < histProj.getWidth() ; x ++) {

                if (ImageProc.getGris(histProj , x , y) != 255) {

                    listes[x] = compteur;
                    compteur = 0;
                    //x = histProj.getWidth()+1;
                }else{
                    compteur ++;
                }
            }
        }

        for (int i = 0 ; i < listes.length ; i ++) {

            System.out.println("VOici cjih ------> "+listes [i]);
        }

        System.out.println("Voici la valeur max -->  " );

        valeurMax = Histogramme.getMax(listes)/2;

        return valeurMax;
    }
    /**
     * Le principe est qu'on compte le nombre de blanc , et qu'on fasse une moyenne de largeur d'une marche pour povoir retirer les pic qui servent a rien
     *
     * @param histProj
     * @return
     */
    public static int compterMarche (BufferedImage histProj , int quart) {
        //(histProj.getWidth() /2);
        int couleur = ImageProc.getGris(histProj , quart ,0);
        int compteur = 0;

        boolean cont = true;

        if (quart == histProj.getWidth()/2) {
            cont = false;
        }

        for (int y = 0 ; y <histProj.getHeight() ; y ++) {

            if (ImageProc.getGris(histProj , quart , y) != couleur) {

                compteur ++;
                couleur = ImageProc.getGris(histProj , quart ,y);
            }

        }

        compteur /=2;

        if (compteur == 0  && cont) {
            return ImageProc.compterMarche(histProj , histProj.getWidth()/2);
        }

        System.out.println(compteur -1);

        return compteur-1;
    }

    public static BufferedImage histogrammeProjeter (BufferedImage bf) {

        int xMax = bf.getWidth();
        int yMax = bf.getHeight();

        int [] tab = new int [yMax]; // Tableau qui va contenir les dénivrances

        BufferedImage newBuff = new BufferedImage(xMax , yMax ,  BufferedImage.TYPE_INT_RGB);

        int newX , newY = -1;

        for (int y = 0 ; y < yMax ; y ++) {

            newX = 0;
            newY ++;

            for (int x = 0 ; x < xMax ; x ++) {
                if (ImageProc.getGris(bf , x , y) == 255) {
                    ImageProc.setGreyIntensity(newBuff,newX,newY,255);
                    newX ++;
                }
            }
            tab[newY] = newX;

        }

        return newBuff;
    }
    public static BufferedImage chargerImage( String path) throws IOException {

        return ImageIO.read(new File (path));
    }
    public static void setGreyIntensity (BufferedImage bf , int x , int y , int intensity) {

        int [] intens = { intensity , intensity , intensity , 255 };
        bf.getRaster().setPixel(x,y,intens);
    }
    public static BufferedImage egalise (BufferedImage bf) {

        BufferedImage bn = bf;
        float tailleImage = (float) bf.getHeight()*bf.getWidth(); // On calcul la taille de l'image en pixel.
        int intensity;

        int hc [] = Histogramme.calculHistogramme(bf);
        int hcc[] = Histogramme.calculHcc(hc);
        for (int y = 0 ; y< bf.getHeight() ; y ++) {
            for (int x = 0 ; x<bf.getWidth() ; x ++) {

                intensity = (int) ((256/tailleImage)*(hcc[ImageProc.getGris(bf,x,y)] -1));
                System.out.println("Intens =" + intensity + "; ==== " + (256/tailleImage));
                setGreyIntensity(bf,x,y,intensity);

            }
        }

        return bf;
    }
    public static BufferedImage etirer (BufferedImage bf) {

        int [] hist = Histogramme.calculHistogramme(bf); // on calcule l'histogramme et on l'étire
        int lmax = Histogramme.getMaxIntensity(hist); // On récupère 'intensité maximal
        int lmin = Histogramme.getMinIntensity(hist); // ON récupère l'intensité minimal
        int intensity;

        for (int y = 0 ; y < bf.getHeight() ;  y ++) {
            for (int x = 0 ; x < bf.getWidth() ; x ++) {

                intensity = (int) (256/(lmax-lmin))*(ImageProc.getGris(bf , x , y) - lmin);
                System.out.println("voici la nouvelle intensité : "+intensity);
                setGreyIntensity(bf,x,y,intensity);
            }
        }

        return bf;
    }
    public static BufferedImage otsuSeuillage (BufferedImage bf) {

        Otsu otsu = new Otsu ();

        int hist [] = Histogramme.calculHistogramme(bf);
        int normalise [] = Histogramme.normalise(hist);
        ImageProc.etirer(bf);

        try {
            ImageProc.afficherImage(bf , "Apres etiremment");
        } catch (IOException e) {
            e.printStackTrace();
        }

        int seuil = (int) otsu.getOtsuThreshold(normalise);

        seuil +=100;
        System.out.println("Voici le treshold trouvé " + seuil);

        try {
            ImageProc.seuillage(bf ,seuil);
            return bf;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bf;
    }
    /**
     * Permet de transformer une image en niveau de gris en  image binaire
     *
     * @param bi Le buffer contenant l'image
     * @param seuil Le seuil à partir duquel le pixel sera blanc
     *
     * @return le bufferedImage avec la nouvelle image en noir et blanc
     */
    public static BufferedImage seuillage (BufferedImage bi,int seuil ) throws IOException {

        int couleur,red;

        for (int y = 0 ; y<bi.getHeight() ; y ++) {
            for (int x = 0 ; x <bi.getWidth() ; x ++) {
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
     * Met en négatif l'image
     *
     * @param bi
     * @return
     */
    public static BufferedImage toNegatif (BufferedImage bi) {

        BufferedImage img = bi;

        for (int i = 0 ; i < bi.getWidth() ; i ++) {

            for (int y = 0 ; y <bi.getHeight() ; y ++) {

                int couleur = bi.getRGB(i, y);

                //int a = (couleur>>24)&0xff;
                int b = couleur&0xff;
                int [] couleurBis = {255-b ,255-b ,255-b , 255 };

                bi.getRaster().setPixel(i, y, couleurBis);
            }
        }

        return img;
    }
    /**
     * Affiche l'histogramme d'un image en noir et blanc
     *
     * @param matrice
     * @throws IOException
     */
    public static void afficherHistogramme (int matrice []) throws IOException {

        BufferedImage bf = new BufferedImage (256 ,100, BufferedImage.TYPE_INT_RGB);


        for (int i =0 ; i < 256 ; i++ ) {

            int y = 100 - matrice [i];
            int compteur = y;

            for ( compteur = y; y <100; y++) {

                bf.setRGB(i, y, Color.WHITE.getRGB());
            }
        }

        ImageProc.afficherImage(bf, "Image");
        // On affiche le resultat
    }
    /**
     * Affiche une image dans une fenetre à part
     *
     * @param image Le buffureredImage
     *
     * @return
     * @throws IOException
     */
    public static void afficherImage(BufferedImage image , String title) throws IOException {
        //Encoding the image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( image, "jpg", baos );
        //Storing the encoded Mat in a byte array
        byte[] byteArray = baos.toByteArray();
        //Preparing the Buffered Image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);
        //Instantiate JFrame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(title);
        //Set Content to the JFrame
        frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
        frame.pack();
        frame.setVisible(true);

    }
    /**
     * Transforme une image en couleur en noir niveau de gris
     *
     * @param bi Le bufferedImage qui contient l'image
     * @return retourne le BufferedImage rentré en paramètre avec des valeurs en niveau de gris
     */
    public static BufferedImage greyScale (BufferedImage bi) {

        int p , rouge , bleu, vert , gris;

        BufferedImage ba = bi;

        for (int i = 0 ;i <bi.getWidth() ; i ++ ) {

            for (int y = 0 ; y < bi.getHeight() ; y ++) {

                p = bi.getRGB(i, y);

                rouge = (p>>16)&0xff;
                bleu = p&0xff;
                vert = (p>>8)&0xff;
                gris = (rouge+vert+bleu)/3;

                int [] couleur = { gris , gris , gris , 255 };

                bi.getRaster().setPixel(i, y, couleur);
            }
        }

        return ba;
    }
    /**
     * Récupère le niveau de gris d'un pixel
     *
     * @param bi
     * @param posX
     * @param posY
     * @return
     */
    public static int getGris (BufferedImage bi , int posX , int posY) {

        int inter = bi.getRGB(posX, posY);
        int couleur = (inter>>8)&0xff;

        System.out.println ("Intensité gris =" + couleur);

        return couleur;
    }

}