package us.lyjia.NiceYtDlpGui;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import us.lyjia.NiceYtDlpGui.windows.MainWindow;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    
    try {
        UIManager.setLookAndFeel( new FlatLightLaf() );
    } catch( Exception ex ) {
        System.err.println( "Failed to initialize LaF" );
    }
    
    MainWindow mainWindow = new MainWindow();
  }
}