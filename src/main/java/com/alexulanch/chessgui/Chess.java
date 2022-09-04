package com.alexulanch.chessgui;

import javax.swing.*;
import java.awt.*;
//import java.awt.*;

public class Chess {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Chess GUI");
        JPanel redPanel = new JPanel();
        redPanel.setBackground(Color.RED);
        JPanel bluePanel = new JPanel();
        JPanel greenPanel = new JPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(750, 750);
        frame.setVisible(true);


        ImageIcon icon = new ImageIcon("pawnIcon.png");
        frame.setIconImage(icon.getImage());
//        frame.getContentPane().setBackground(Color.DARK_GRAY);
    }
}
