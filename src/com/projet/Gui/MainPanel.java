package com.imageLibs.Gui;

import com.imageLibs.Image.ImageProc;
import com.imageLibs.Logique.Compter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainPanel extends JPanel {

    private ImagePanel imagePanel;
    private Console console;

    private JButton count;
    private JButton showImage;
    private JPanel flow;
    private JPanel border;


    private void initComponent() {

        this.setBackground(Color.white);

        this.count = new JButton("Count");
        this.count.setBackground(Color.white);
        this.count.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                count.setEnabled(false);

                 Thread thread = new Thread(new Runnable() {
                     @Override
                     public void run() {
                         console.print("Conuting stairs ....");
                         try {
                             new Compter(imagePanel.getBf() , console, count);
                         }catch (IOException e) {
                             e.printStackTrace();
                         }
                     }
                 });

                 thread.start(); // On démare le thread
            }
        });
        this.showImage = new JButton("Show Image");
        this.showImage.setBackground(Color.white);
        this.console = new Console();
        this.imagePanel = new ImagePanel(this.console); // Calling default Constructor.

        this.flow = new JPanel(); // FLow panel
        this.flow.setLayout(new FlowLayout(FlowLayout.TRAILING));
        this.flow.add(this.showImage);
        this.flow.add(this.count);

        this.border = new JPanel(); // Border layout
        this.border.setLayout(new BorderLayout());
        this.border.add(this.console, BorderLayout.CENTER);
        this.border.add(this.flow , BorderLayout.SOUTH);
    }
    private void init () {

        GridLayout gl = new GridLayout(2, 0);
        this.initComponent();

        this.setLayout(gl);

        this.add(this.imagePanel); // Adding it to the window
        this.add(this.border); // Adding the console to the Windows
    }
    public MainPanel () {
        this.init();
    }
}
