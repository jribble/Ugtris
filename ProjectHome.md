# Ugtris! #
A tetris game built as a group learning effort at the Utah Google Technology User Group.

This game is built using the [PlayN library](http://code.google.com/p/playn/), a library that allows you to build one game that runs as a desktop application, and web application, and an Android application.

## Building Ugtris ##
### Prerequisites ###
  * You need a [JDK (Java Development Kit)](http://www.oracle.com/technetwork/java/javase/downloads/index.html).  The latest is a good choice.
  * You need [Apache Maven](http://maven.apache.org/).  The latest is a good choice.
  * If you want to build for Android you need [the Android SDK](http://developer.android.com/sdk/installing.html).  Make sure you download and install version 11 of the SDK.
  * Also if you want to build for Android you need to modify your maven settings.xml as described here: http://code.google.com/p/playn/wiki/MavenAndroidBuild

### Get the code ###
Click the Source link up top.  You'll need [Git](http://git-scm.com/).

### Build the project ###
  1. Go to the project directory in a command line.
  1. Run 'mvn install'.  Maven will automatically pull down dependencies and build stuff.

### Running the project ###
**Java**
  * Run 'mvn test -Ptest-java' from the project directory
**HTML**
  * Run 'mvn test -Ptest-html' from the project directory
  * Open your browser and point it at http://localhost:8080
**Android**
  * You have to have an Android device connected and available via the debug bridge.  PlayN claims that emulated devices will not work, it has to be real.
  * Run 'mvn -f android/pom.xml android:deploy' and it should install to the attached device.

### More Help ###
Try the GettingStarted page for more help