package us.lyjia;
import com.formdev.flatlaf.FlatLightLaf;

import java.io.*;
import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    
    try {
        UIManager.setLookAndFeel( new FlatLightLaf() );
    } catch( Exception ex ) {
        System.err.println( "Failed to initialize LaF" );
    }
    
    JFrame frame = new JFrame();
    
    JButton button = new JButton("Hello world!");
    
    button.setBounds(150, 200, 220, 50);
    
    frame.add(button);
    
    frame.setSize(500, 600);
    frame.setLayout(null);
    frame.setVisible(true);
  }
}