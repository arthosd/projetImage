package com.imageLibs.Logique;

import com.imageLibs.Image.ImageProc;
import com.imageLibs.math.Histogramme;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ThreadManager {

    private int nombreThread;
    private BufferedImage bi;
    private BufferedImage [] bu;
    private Thread tTable [];

    private BufferedImage [] cutImage (BufferedImage bi , int nombreThread) {

        BufferedImage [] bu = new BufferedImage[nombreThread]; // Contiendra tout les BuffuredImage à traiter

        int tailleBuffX =bi.getWidth();
        int tailleBuffY = bi.getHeight();
        int biais = (bi.getHeight()/nombreThread)-1; // On trouve le biais pour couper l'image

        int de = 0; // L'endroit ou onn av découper l'image
        int vers = de+biais;;


        // On rempli les tableaux des bufferedImages
        for(int c = 0 ; c < nombreThread ; c ++) {
            if (c == nombreThread-1) {// Quand on est dans la dernière boucles

                int newTailleY = ((nombreThread-1)*(biais+1));
                int nTailleY = bi.getHeight()-newTailleY;
                bu[c] = new BufferedImage(bi.getWidth(),nTailleY, BufferedImage.TYPE_INT_RGB);

            }else {// Quand on est dans les premieres boucles
                bu[c] = new BufferedImage(bi.getWidth(), (biais+1) , BufferedImage.TYPE_INT_RGB);// On crée le  bufferedImage
            }
        }

        int handler = 0;
        int compteurHandleur = 0;
        // La boucke qui va découper l'image
        for (int y = 0 ; y < bi.getHeight() ; y ++) {
            for (int x = 0 ; x <bi.getWidth() ; x ++) {
                ImageProc.setGreyIntensity(bu[compteurHandleur] , x,handler,ImageProc.getGris(bi,x,y));
            }

            handler ++;

            if (handler == bu[compteurHandleur].getHeight()){
                handler = 0;
                compteurHandleur ++;
            }
        }

        return bu;
    }
    public void start() {
        // On cut l'image
        this.bu = this.cutImage(this.bi,nombreThread);
        this.count();
        //this.allThreadAreGood(this.nombreThread);

    }
    private void count () {
        //On va compter les images
        for (int i = 0 ; i < this.nombreThread ; i ++) {

            int finalI = i;
            this.tTable[i] = new Thread(new Runnable() {
                @Override
                public void run() {


                    // on let la fonction qui va compter les marches

                    /*try {
                        Compter c = new Compter(bu[finalI] , null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            });
            this.tTable[i].start();

            System.out.println(Thread.currentThread().getState());
        }
    }
    private void init (BufferedImage bi , int nombreThread) {

        this.nombreThread = nombreThread;
        this.bi = bi;
        this.tTable = new Thread[this.nombreThread];
    }
    public ThreadManager(BufferedImage bi ,int nombreThread) {
        this.init(bi,nombreThread);
    }
}
