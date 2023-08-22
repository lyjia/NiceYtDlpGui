package us.lyjia.windows;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class MainWindow {
  
  public MainWindow() {
    
    // build the GUI
    JFrame frame = new JFrame("NiceYtDlpGUI");
    JPanel panel = new JPanel(new MigLayout());
    frame.setSize(400, 400);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // 'Enter URL label'
    JLabel lblUrl = new JLabel();
    lblUrl.setText("Enter a URL:");
    
    JTextField txtUrl = new JTextField();
    
    // Folder destination
    JLabel lblDest = new JLabel();
    lblDest.setText("Save to:");
    
    JTextField txtDest = new JTextField();
    
    JButton btnDestFolderPicker = new JButton();
    btnDestFolderPicker.setText("Browse...");
    
    // Button bar
    JButton btnGo = new JButton();
    
    panel.add(lblUrl);
    panel.add(txtUrl, "wrap, span, grow");
    panel.add(lblDest);
    panel.add(txtDest);
    panel.add(btnDestFolderPicker, "wrap");
    panel.add(btnGo, "wrap");
    
    frame.add(panel);
    
    frame.setVisible(true);
    
  }
  
}
