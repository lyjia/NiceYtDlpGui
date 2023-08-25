package us.lyjia.NiceYtDlpGui.models;

import us.lyjia.NiceYtDlpGui.Util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

public class Download {
  public enum Status {Init, Loading, Running, Finished, Error}
  Logger log = Logger.getLogger(this.getClass().getName());
  
  public Status status = Status.Init;
  
  private URL url;
  private File destFolder;
  private YtDlp ytDlp;
  
  // https://www.baeldung.com/java-observer-pattern
  private PropertyChangeSupport support;
  
  
  public Download(YtDlp ytdlp_instance, URL url, File destFolder) {
    this.url = url;
    this.destFolder = destFolder;
    this.ytDlp = ytdlp_instance;
  }
  
  public void start() {
    status = Status.Loading;
    
    String[] args = {
        "--paths", Util.encloseWithQuotes(this.destFolder.toString()),
        "--newline",
        "--quiet",
        "--progress",
        "--force-overwrite",
        "--progress-template",
        ytDlp.getDownloadProgressTemplateAsArgString(),
        Util.encloseWithQuotes(this.url.toString())
    };
    
    log.info("About to spawn ytdlp with: "+String.join(" ", args));
    
    // spawn a new thread and run+monitor the ytdlp process from within it
    new Thread(() -> {
      
      ProcessBuilder builder = ytDlp.getProcessBuilder(args);
      Process process = null;

      try {
        builder.redirectErrorStream(true);
        process = builder.start();
        
        var in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        
        while ((line = in.readLine()) != null) {
          System.out.println(line);
        }
        
        int exitCode = process.waitFor();
        in.close();
        
        log.info("Process exited with code "+exitCode);
        
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }).start();
    
    
  }
  
  public void addChangeListener(PropertyChangeListener pcl) {
    support.addPropertyChangeListener(pcl);
  }
}
