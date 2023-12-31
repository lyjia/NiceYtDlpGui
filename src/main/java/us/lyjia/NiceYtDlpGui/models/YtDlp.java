package us.lyjia.NiceYtDlpGui.models;

import us.lyjia.NiceYtDlpGui.Const;
import us.lyjia.NiceYtDlpGui.Tweakables;
import us.lyjia.NiceYtDlpGui.exceptions.ProcessFailureException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.prefs.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.lyjia.NiceYtDlpGui.Util;
import us.lyjia.NiceYtDlpGui.models.swing.DownloadsTableModel;

import javax.swing.*;


public class YtDlp {
  Logger log = Logger.getLogger(this.getClass().getName());
  
  public String binPath;
  public String binVersion;
  
  public Preferences prefs = Preferences.userNodeForPackage(this.getClass());
  static Map<String, String> downloadProgressTemplateMap = Tweakables.initDownloadProgressTemplateMap();
  
  private YtDlp(String path, String version) {
    //this(Preferences.userNodeForPackage(MainWindow.class).get(Const.PREF_YTDLP_PATH, Const.PREF_YTDLP_PATH_DEFAULT));
    binPath = path;
    binVersion = version;
    
    log.info("yt-dlp initialized. Version " + binVersion + " at " + binPath);
    
  }
  
  public static YtDlp createInstanceAndVerify(String path) throws IOException, InterruptedException, ProcessFailureException {
    ProcessBuilder builder = new ProcessBuilder(path, "--version");
    builder.redirectErrorStream(true);
    
    Process process = builder.start();
    int exitCode = process.waitFor();
    
    if (exitCode == 0) {
      String versionString = Util.readStream(process.getInputStream()).strip();
      
      Pattern versionPattern = Pattern.compile(Const.Regex.YTDLP_VERSION_FORMAT);
      Matcher versionMatcher = versionPattern.matcher(versionString);
      
      if (versionMatcher.find()) {
        return new YtDlp(path, versionString);
      } else {
        throw new ProcessFailureException("The process did not supply its version information in the correct format. Instead it supplied: " + versionString);
      }
    } else {
      throw new ProcessFailureException("The process returned a nonzero exit code: " + exitCode);
    }
    
  }
  
  public ProcessBuilder getProcessBuilder(String[] args) {
    var cmd = Util.Array.crappyUnshift(args, binPath);
    ProcessBuilder builder = new ProcessBuilder(cmd);
    return builder;
    
  }
  
  public static String[] getProgressTemplateKeys() {
   return downloadProgressTemplateMap.keySet().toArray(new String[0]);
  }
  

  
  public String getDownloadProgressTemplateAsArgString() {
    return Util.encloseWithQuotes("download:" +
        Const.Progress.TOKE_HEADER +
        Const.Progress.TOKE_SEPERATOR +
        String.join(Const.Progress.TOKE_SEPERATOR, downloadProgressTemplateMap.values()));
  }
  
  public Download getDownloadObj(URL url, File destFolder, DownloadsTableModel model) {
    return new Download(this, url, destFolder, model);
  }
  
  
}

