# NiceYtDlpGui

A graphical frontend for [yt-dlp](https://github.com/yt-dlp/yt-dlp) by [Lyjia](http://www.lyjia.us)

Built with JDK 21 (currently Temurin). This project was originally built to be compiled to a native binary with GraalVM, however native-image support for Swing applications is currently lacking and I have not been able to get such a build to work. (See below)

Note that this project is a *WORK IN PROGRESS*.

To build jars
 * Make sure JDK 21 (or greater) and Maven are installed and in %PATH%
 * run `mvn package`
 * Execute `java -jar NiceYtDlpGui-0.1-jar-with-dependencies.jar`
 * Do not try to run the jarfile directly or doubleclick it unless you have the correct JDK configured to handle this (uncommon)

To build native-image executable (currently broken):
 * Make sure GraalVM 21 (or greater) and Maven are installed
 * run `mvn -P native package`
 * Execute `NiceYtDlpGui` or `NiceYtDlpGui.exe` in `target`

## Known issues:

### native-image throws exception java.lang.Error: no ComponentUI class for: javax.swing.[...]

* Solution unknown.
* see https://github.com/lyjia/NiceYtDlpGui/issues/2

### native-image throws exception in thread "main" java.lang.Error: java.home property not set

* Certain dependencies need to be manually copied to the build folder since many parts of Swing require %JAVA_HOME% to be defined when it isn't (because of the standalone native-image executable)
* Relevant github issues:
  * https://github.com/lyjia/NiceYtDlpGui/issues/1
  * https://github.com/oracle/graal/issues/3659 [native-image] Swing application cannot be compiled on Windows
  * https://github.com/oracle/graal/issues/1812 [native-image] java.home property not set


### native-image throws exception in thread "main" java.lang.NoSuchMethodError: java.awt.Toolkit.getDefaultToolkit()Ljava/awt/Toolkit;

* This occurs because the native-image was built without graalVM's agent-output. This can happen if `target/` is deleted, `mvn clean`, or on a fresh checkout
* Run the JAR with the native agent to generate agent-output, do stuff in it so the agent sees it: ` mvn -P native -D agent exec:exec@java-agent`
* Recompile the native-image: `mvn -P native package`
