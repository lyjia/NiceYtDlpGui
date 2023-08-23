package us.lyjia.NiceYtDlpGui.models;

import us.lyjia.NiceYtDlpGui.Const;
import us.lyjia.NiceYtDlpGui.exceptions.ProcessFailureException;
import us.lyjia.NiceYtDlpGui.windows.MainWindow;

import java.io.IOException;
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
  
  private YtDlp(String path, String version) {
    //this(Preferences.userNodeForPackage(MainWindow.class).get(Const.PREF_YTDLP_PATH, Const.PREF_YTDLP_PATH_DEFAULT));
    binPath = path;
    binVersion = version;
    log.info("yt-dlp initialized. Version "+binVersion+" at "+ binPath);
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
        throw new ProcessFailureException("The process did not supply its version information in the correct format. Instead it supplied: "+versionString);
      }
    } else {
      throw new ProcessFailureException("The process returned a nonzero exit code: "+exitCode);
    }
    
  }
  
}

