package us.lyjia.NiceYtDlpGui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
}
