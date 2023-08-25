# NiceYtDlpGui

A graphical frontend for yt_dlp by [Lyjia](http://www.lyjia.us)

Built with GraalVM 20

To build jars
 * run `mvn package`
 * Execute `java -jar NiceYtDlpGui-0.1-jar-with-dependencies.jar` with a java runtime version 20 or higher
 * Do not try to run the jarfile directly or doubleclick it unless you have the correct JDK configured to handle this (uncommon)

To build native executable:
 * run `mvn -P native package` (currently broken)
 * Execute `NiceYtDlpGui` or `NiceYtDlpGui.exe` in `target`

## Known issues:



### native-image throws exception in thread "main" java.lang.Error: java.home property not set

* Solution unknown.
* Relevant github issues:
* * https://github.com/lyjia/NiceYtDlpGui/issues/1
  * https://github.com/oracle/graal/issues/3659 [native-image] Swing application cannot be compiled on Windows
  * https://github.com/oracle/graal/issues/1812 [native-image] java.home property not set


### native-image throws exception in thread "main" java.lang.NoSuchMethodError: java.awt.Toolkit.getDefaultToolkit()Ljava/awt/Toolkit;

* Run the JAR with the native agent to generate agent-output: ` mvn -P native -D agent exec:exec@java-agent`
* Recompile the native-image: `mvn -P native package`