package us.lyjia.NiceYtDlpGui.windows;

import net.miginfocom.swing.MigLayout;
import us.lyjia.NiceYtDlpGui.Const;
import us.lyjia.NiceYtDlpGui.exceptions.ProcessFailureException;
import us.lyjia.NiceYtDlpGui.models.YtDlp;
import us.lyjia.NiceYtDlpGui.Util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.prefs.*;

import static java.lang.Integer.parseInt;

public class MainWindow {
  YtDlp ytdlp;
  
  Preferences prefs = Preferences.userNodeForPackage(this.getClass());
  
  JLabel lblStatus;
  JButton btnGo;
  JTextField txtDest;
  JTextField txtUrl;
  
  
  public MainWindow() {
    
    String[] windowPos = prefs.get(Const.Prefs.MAINWINDOW_POS, "100,100").split(",");
    String[] windowSize = prefs.get(Const.Prefs.MAINWINDOW_SIZE, "400x400").split("x");
    
    setUpWindow(parseInt(windowPos[0]), parseInt(windowPos[1]), parseInt(windowSize[0]), parseInt(windowSize[1]));
    initYtDlpInstance();
    
  }
  
  // Init the YT-DLP object
  // https://stackabuse.com/how-to-use-threads-in-java-swing/
  public void initYtDlpInstance() {
    var worker = new SwingWorker<YtDlp, Void>() {
      
      @Override
      protected YtDlp doInBackground() throws Exception {
        return YtDlp.createInstanceAndVerify(Preferences.userNodeForPackage(MainWindow.class).get(Const.Prefs.YTDLP_PATH, Const.Prefs.YTDLP_PATH_DEFAULT));
      }
      
      @Override
      public void done() {
        try {
          ytdlp = get();
        } catch (InterruptedException e) {
          
          lblStatus.setText("ERROR loading yt-dlp!");
        } catch (ExecutionException e) {
          lblStatus.setText("ERROR loading yt-dlp!");
        }
        lblStatus.setText("yt-dlp v" + ytdlp.binVersion);
        setEnabledOnYtDlpActions(true);
        
      }
    };
    
    worker.execute();
  }
  
  public void setUpWindow(int x, int y, int width, int height) {
    Dimension windowSize = new Dimension(width, height);
    
    // build the GUI
    JFrame frame = new JFrame("NiceYtDlpGUI");
    JPanel panel = new JPanel(new MigLayout("fillx", "[right][grow,fill][right][right]"));
    
    // 'Enter URL label'
    JLabel lblUrl = new JLabel();
    lblUrl.setText("Enter a URL:");
    txtUrl = new JTextField();
    
    panel.add(lblUrl);
    panel.add(txtUrl, "wrap, span 3, grow");
    
    // Folder destination
    JLabel lblDest = new JLabel();
    lblDest.setText("Save to:");
    txtDest = new JTextField();
    
    JButton btnBrowse = new JButton();
    btnBrowse.setText("Browse...");
    btnBrowse.addActionListener(e -> btnBrowse_click());
    
    panel.add(lblDest);
    panel.add(txtDest, "span 2, grow");
    panel.add(btnBrowse, "wrap");
    
    // Button bar
    lblStatus = new JLabel("Loading...");
    
    JButton btnSettings = new JButton();
    btnSettings.setText("Settings...");
    
    btnGo = new JButton();
    btnGo.setText("Go!");
    btnGo.setEnabled(false);
    
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
  
  public void setEnabledOnYtDlpActions(boolean enabled) {
    btnGo.setEnabled(enabled);
  }
  
  public void btnBrowse_click() {
    var ret = Util.popupFolderPicker(txtDest.getText(), null);
    if (ret != null) {
      txtDest.setText(ret.toString());
    }
  }
  
}
