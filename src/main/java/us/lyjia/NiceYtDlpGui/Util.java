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
  
}
