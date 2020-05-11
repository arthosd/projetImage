package com.imageLibs.Gui;

import javax.swing.*;
import java.awt.*;

public class MainGui extends JFrame {

    private void init () {

        this.setTitle("Stair Counter");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setSize(new Dimension(600 , 700));
        this.setMinimumSize(new Dimension( 400 ,400));
        this.setContentPane(new MainPanel());
        this.setVisible(true);
    }
    public MainGui () {
        this.init();
    }
}
