package us.lyjia.NiceYtDlpGui.windows;

import net.miginfocom.swing.MigLayout;
import us.lyjia.NiceYtDlpGui.Const;
import us.lyjia.NiceYtDlpGui.exceptions.ProcessFailureException;
import us.lyjia.NiceYtDlpGui.models.YtDlp;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.io.IOException;
import java.util.prefs.*;

import static java.lang.Integer.parseInt;

public class MainWindow {
  YtDlp ytdlp;
  
  Preferences prefs = Preferences.userNodeForPackage(this.getClass());
  
  JLabel lblStatus;
  JButton btnGo;
  
  public MainWindow() {
    
    String[] windowPos = prefs.get(Const.Prefs.MAINWINDOW_POS, "100,100").split(",");
    String[] windowSize = prefs.get(Const.Prefs.MAINWINDOW_SIZE, "400x400").split("x");
    
    SetUpWindow(parseInt(windowPos[0]), parseInt(windowPos[1]), parseInt(windowSize[0]), parseInt(windowSize[1]));
    
    try {
      ytdlp = YtDlp.createInstanceAndVerify( Preferences.userNodeForPackage(MainWindow.class).get(Const.Prefs.YTDLP_PATH, Const.Prefs.YTDLP_PATH_DEFAULT) );
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ProcessFailureException e) {
      throw new RuntimeException(e);
    }
    
  }
  
  public void SetUpWindow(int x, int y, int width, int height) {
    Dimension windowSize = new Dimension(width, height);
    
    // build the GUI
    JFrame frame = new JFrame("NiceYtDlpGUI");
    JPanel panel = new JPanel(new MigLayout("fillx", "[right][grow,fill][right][right]"));
    
    
    // 'Enter URL label'
    JLabel lblUrl = new JLabel();
    lblUrl.setText("Enter a URL:");
    JTextField txtUrl = new JTextField();
    
    panel.add(lblUrl);
    panel.add(txtUrl, "wrap, span 3, grow");
    
    // Folder destination
    JLabel lblDest = new JLabel();
    lblDest.setText("Save to:");
    
    JTextField txtDest = new JTextField();
    
    JButton btnDestFolderPicker = new JButton();
    btnDestFolderPicker.setText("Browse...");
    
    panel.add(lblDest);
    panel.add(txtDest, "span 2, grow");
    panel.add(btnDestFolderPicker, "wrap");
    
    // Button bar
    lblStatus = new JLabel("[VERSION][STATUS]");
    
    JButton btnSettings = new JButton();
    btnSettings.setText("Settings...");
    
    btnGo = new JButton();
    btnGo.setText("Go!");
    
    panel.add(lblStatus, "span 2, grow");
    panel.add(btnSettings);
    panel.add(btnGo);
    
    // build the window
    frame.add(panel);
    frame.setSize(windowSize);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // All systems go!
    frame.setVisible(true);
  }
  
}
