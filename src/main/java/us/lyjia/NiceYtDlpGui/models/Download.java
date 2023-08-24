package us.lyjia.NiceYtDlpGui.models;

import java.io.File;
import java.net.URL;

public class Download {
  
  private URL url;
  private File destFolder;
  private YtDlp ytDlp;
  
  public Download(YtDlp ytdlp_instance, URL url, File destFolder) {
    this.url = url;
    this.destFolder = destFolder;
    this.ytDlp = ytdlp_instance;
  }
}
