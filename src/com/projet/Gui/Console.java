package com.imageLibs.Gui;

import javax.swing.*;
import java.awt.*;

public class Console extends JTextArea {

    private String text;

    public void print (String text) {
        this.text = this.text + ">>>>> Console : "+ text + "\n";
        this.setText(this.text);
    }

    private void init () {
        this.setBackground(Color.black);
        this.setForeground(Color.GREEN);
        this.setEnabled(false);
        this.setLineWrap(true);

        this.text = "Choisissez une image ... \n";
        this.setText(this.text);

    }
    public Console () {
        this.init();
    }
}
