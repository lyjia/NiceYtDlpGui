package us.lyjia.NiceYtDlpGui.models;

import us.lyjia.NiceYtDlpGui.Const;
import us.lyjia.NiceYtDlpGui.exceptions.ProcessFailureException;
import us.lyjia.NiceYtDlpGui.windows.MainWindow;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.prefs.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.lyjia.NiceYtDlpGui.Util;


public class YtDlp {
  Logger log = Logger.getLogger(this.getClass().getName());
  
  public String binPath;
  public String binVersion;
  
  public Preferences prefs = Preferences.userNodeForPackage(this.getClass());
  Map<String, String> downloadProgressTemplateMap;
  
  private YtDlp(String path, String version) {
    //this(Preferences.userNodeForPackage(MainWindow.class).get(Const.PREF_YTDLP_PATH, Const.PREF_YTDLP_PATH_DEFAULT));
    binPath = path;
    binVersion = version;
    initDownloadProgressTemplateMap();
    
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
    String argsStr = String.join(" ", args);
    ProcessBuilder builder = new ProcessBuilder(binPath, argsStr);
    return builder;
  }
  
  private void initDownloadProgressTemplateMap() {
    downloadProgressTemplateMap = new HashMap<>();
    downloadProgressTemplateMap.put(Const.Progress.TOKE_INFO_ID, "%(info.id)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_INFO_TITLE, "%(info.title)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_INFO_EXTRACTOR, "%(info.extractor)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_INFO_DOMAIN, "%(info.webpage_url_domain)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_STATUS, "%(progress.status)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_BYTES_DOWNLOADED, "%(progress.downloaded_bytes)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_BYTES_TOTAL, "%(progress.total_bytes)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_BYTES_ETA, "%(progress.eta)s");
    downloadProgressTemplateMap.put(Const.Progress.TOKE_PRG_BYTES_SPEED, "%(progress.speed)s");
    
  }
  
  public String getDownloadProgressTemplateAsArgString() {
    return "--progress-template \"download:" +
        Const.Progress.TOKE_SEPERATOR +
        Const.Progress.TOKE_HEADER +
        Const.Progress.TOKE_SEPERATOR +
        String.join(Const.Progress.TOKE_SEPERATOR, downloadProgressTemplateMap.values());
  }
  
  
  
}

