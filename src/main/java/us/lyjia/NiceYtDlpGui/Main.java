package us.lyjia.NiceYtDlpGui;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import us.lyjia.NiceYtDlpGui.windows.MainWindow;

import javax.swing.*;
import java.io.File;

public class Main {
  public static void main(String[] args) {
    
    // Possible fix for
    // https://github.com/oracle/graal/issues/3659
    if (System.getProperty("java.home") == null) {
        System.out.println("No java.home is set, seems this is a native-image from GraalVM. Fixing...");
        System.setProperty("java.home", new File(".").getAbsolutePath());
    }
    
    try {
        UIManager.setLookAndFeel( new FlatLightLaf() );
    } catch( Exception ex ) {
        System.err.println( "Failed to initialize LaF" );
    }
    
    MainWindow mainWindow = new MainWindow();
  }
}