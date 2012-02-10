package org.utahgtug.ugtris.android;

import playn.android.GameActivity;
import playn.core.PlayN;

import org.utahgtug.ugtris.core.Ugtris;

public class UgtrisActivity extends GameActivity {

  @Override
  public void main(){
    platform().assetManager().setPathPrefix("org/utahgtug/ugtris/resources");
    PlayN.run(new Ugtris());
  }
}
