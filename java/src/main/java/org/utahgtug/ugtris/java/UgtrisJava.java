package org.utahgtug.ugtris.java;

import playn.core.PlayN;
import playn.java.JavaPlatform;

import org.utahgtug.ugtris.core.Ugtris;

public class UgtrisJava {

  public static void main(String[] args) {
    JavaPlatform platform = JavaPlatform.register();
    platform.assetManager().setPathPrefix("org/utahgtug/ugtris/resources");
    PlayN.run(new Ugtris());
  }
}
