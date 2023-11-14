package us.lyjia.NiceYtDlpGui;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Util {
  
  // reads the input stream of a ProcessBuilder process and converts it into a string
  // source chatgpt3.5
  public static String readStream(InputStream inputStream) throws IOException {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      StringBuilder result = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        result.append(line).append(System.lineSeparator());
      }
      return result.toString();
    }
  }
  
  public static File popupFolderPicker(String path, Component parent) {
    final JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int returnVal = fc.showOpenDialog(parent);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      return fc.getSelectedFile();
    } else {
      return null;
    }
  }
  
  public static String encloseWithQuotes(String str) {
    return "\"" + str + "\"";
  }
  
  public class Array {
    public static String[] crappyUnshift(String[] arr, String toAdd) {
      String[] toret = new String[arr.length + 1];
      toret[0] = toAdd;
      for (var i = 0; i < arr.length; i++) {
        toret[i + 1] = arr[i];
      }
      return toret;
    }
  }
  
  public static String getFriendlyByteSpeed(String byteSpeedAsString) throws NumberFormatException {
    return getFriendlyBytes(Double.parseDouble(byteSpeedAsString)) + "/sec";
  }
  public static String getFriendlyBytes(String bytesAsString) throws NumberFormatException {
    return getFriendlyBytes(Double.parseDouble(bytesAsString));
  }
  
  public static String getFriendlyBytes(double bytesAsDouble) {
    if (bytesAsDouble < 0) {
      throw new IllegalArgumentException("Speed must be a non-negative number.");
    }
    
    if (bytesAsDouble < 1024) {
      return String.format("%.1f B", bytesAsDouble);
    } else if (bytesAsDouble < 1024 * 1024) {
      return String.format("%.1f KB", bytesAsDouble / 1024);
    } else if (bytesAsDouble < 1024 * 1024 * 1024) {
      return String.format("%.1f MB", bytesAsDouble / (1024 * 1024));
    } else {
      return String.format("%.1f GB", bytesAsDouble / (1024 * 1024 * 1024));
    }
  }
  
}
