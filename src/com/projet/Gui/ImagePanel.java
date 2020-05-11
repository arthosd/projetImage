package com.imageLibs.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel implements ActionListener {

    private BufferedImage bf;
    private ImageIcon icon;
    private JButton button;
    private JScrollPane jsp;
    private Console console;

    public BufferedImage getBf () {

        return this.bf;
    }
    /**
     * Loads another image into the ImagePanel
     *
     * @param imagePath
     * @return
     */
    private BufferedImage setNewImage (String imagePath) {

        try {
            this.bf = ImageIO.read(new File(imagePath));
            this.icon  = new ImageIcon(this.bf);
            this.button.setIcon(this.icon);

        }catch (IOException e) {
            JOptionPane erreurPane = new JOptionPane();
            erreurPane.showMessageDialog(null , "ERREUR durant le loading de l'image" , "ERREUR" , JOptionPane.ERROR_MESSAGE);
            return null; // On retourne null s'il y a une erreur
        }

        return this.bf;
    }
    /**
     * Initialise the imagePanel with an image
     *
     * @param imagePath
     */
    private void init (String imagePath , Console console) {

            //this.bf = ImageIO.read(new File(imagePath));
            //this.icon  = new ImageIcon(this.bf);
            this.button = new JButton("Pas d'image");

            this.console = console;

            this.jsp = new JScrollPane(this.button);

            this.button.setIcon(this.icon);
            this.button.setBackground(Color.white);
            this.button.setBorderPainted(false);
            this.button.addActionListener(this);

            this.add(this.jsp);
    }
    public ImagePanel (String imagePath , Console console) {
        this.init(imagePath , console);
    }
    public ImagePanel (Console console) {
        this ("src/com/imageLibs/Assets/no-image.png" , console);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JFileChooser choix = new JFileChooser();
        int retour=choix.showOpenDialog(this);
        if(retour==JFileChooser.APPROVE_OPTION){

            this.setNewImage(choix.getSelectedFile().getAbsolutePath());
            this.console.print(choix.getSelectedFile().getAbsolutePath());
        }else {
            // Pas de fichier chosie
        }
    }
}
