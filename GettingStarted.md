# Introduction #

So you want to build Ugtris, eh? Well, that's not too hard.

If you're a wizard

If you're not a wizard we'll try to boil it down for you.

# Details #

First things first, this project is based on [PlayN](http://code.google.com/p/playn/wiki/GettingStarted), so what they say there is authoritative. To make it simple, here's how we roll:

  1. Get git
  1. [Follow the Eclipse instructions for PlayN](http://code.google.com/p/playn/wiki/GettingStarted#One_time_Eclipse_setup)
  1. Modify the settings.xml file for maven according to [these instructions](http://code.google.com/p/playn/wiki/MavenAndroidBuild#Configure_Android_SDK_Path)
    * Since you are going to be using Eclipse's installed version of the Android SDK, it's helpful to know that on Mac you would set android.sdk.path to ~/android-sdks and maven will add '/platforms' to your path to find what it needs
  1. Clone the git repo for Ugtris
    * `git clone http://code.google.com/p/ugtris`
  1. Go into the new ugtris directory
    * `cd ugtris`
  1. Run maven to download all of your dependencies. This can take a while
    * `mvn install`
  1. Open up Eclipse.
  1. Make sure you have the Android SDK 11 installed
  1. Follow something like [these instructions](http://code.google.com/p/playn/wiki/GettingStarted#Import_projects) to import ugtris into eclipse
    * Basically replace the playn-samples with the ugtris path and it'll work

At this point I couldn't get Eclipse working with my android device. Someone else would be more than welcome to help us out here and let me know how to do it.

So, being me, I decided to try another way. Maven deploy. Worked like a champ.

  1. Open up a terminal
  1. Go to your ugtris directory
    * `cd ~/src/ugtris`
  1. If you haven't run `mvn install` from this directory yet, do so now
    * `mvn install`
  1. Go into the android directory
    * `cd android`
  1. Tell it to deploy to your device
    * `mvn android:deploy`

It will then push Ugtris to your device! Woot! You can then find it in your start menu and get it started
