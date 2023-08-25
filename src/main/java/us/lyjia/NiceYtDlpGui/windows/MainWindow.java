package us.lyjia.NiceYtDlpGui.windows;

import net.miginfocom.swing.MigLayout;
import us.lyjia.NiceYtDlpGui.Const;
import us.lyjia.NiceYtDlpGui.models.Download;
import us.lyjia.NiceYtDlpGui.models.YtDlp;
import us.lyjia.NiceYtDlpGui.Util;
import us.lyjia.NiceYtDlpGui.models.swing.DownloadsTableModel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.prefs.*;

import static java.lang.Integer.parseInt;

public class MainWindow {
  Logger log = Logger.getLogger(this.getClass().getName());
  YtDlp ytdlp;
  
  Preferences prefs = Preferences.userNodeForPackage(this.getClass());
  
  JLabel lblStatus;
  JButton btnGo;
  JTextField txtDest;
  JTextField txtUrl;
  JTable tblDownloads;
  
  
  public MainWindow() {
    
    String[] windowPos = prefs.get(Const.Prefs.MAINWINDOW_POS, "100,100").split(",");
    String[] windowSize = prefs.get(Const.Prefs.MAINWINDOW_SIZE, "400x400").split("x");
    
    setUpWindow(parseInt(windowPos[0]), parseInt(windowPos[1]), parseInt(windowSize[0]), parseInt(windowSize[1]));
    initYtDlpInstance();
    
  }
  
  // Init the YT-DLP object
  
  public void initYtDlpInstance() {
    // https://stackabuse.com/how-to-use-threads-in-java-swing/
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
    txtUrl.setText( prefs.get(Const.Prefs.LAST_URL, "") );
    
    panel.add(lblUrl);
    panel.add(txtUrl, "wrap, span 3, grow");
    
    // Folder destination
    JLabel lblDest = new JLabel();
    lblDest.setText("Save to:");
    txtDest = new JTextField();
    txtDest.setText( prefs.get(Const.Prefs.LAST_DEST, "") );
    
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
    btnGo.addActionListener(e -> btnGo_click());
    
    panel.add(lblStatus, "span 2, grow");
    panel.add(btnSettings);
    panel.add(btnGo, "wrap");
    
    // Downloads table
    // https://docs.oracle.com/javase/tutorial/uiswing/components/table.html
    tblDownloads = new JTable(new DownloadsTableModel(Download.getDownloadPile()));
    JScrollPane scrlDownloads = new JScrollPane(tblDownloads);
    tblDownloads.setFillsViewportHeight(true);
    
    panel.add(scrlDownloads, "span 4, grow");
    
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
  
  public void btnGo_click() {
    try {
      var destStr = txtDest.getText();
      var urlStr = txtUrl.getText();
      
      // create download object
      URL url = new URL( urlStr );
      File destFolder = new File( destStr );
      var download = ytdlp.getDownloadObj(url, destFolder);
      
      // set up progress monitoring
      
      // start the download
      Download.addDownloadToPileAndStart(download);
      
      // since everything was successful save the path so it is recalled next time
      prefs.put(Const.Prefs.LAST_DEST, destStr);
      prefs.put(Const.Prefs.LAST_URL, urlStr);
      
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
      
    }
  }
  
}
