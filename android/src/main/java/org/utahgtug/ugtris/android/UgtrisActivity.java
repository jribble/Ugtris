package org.utahgtug.ugtris.android;

import android.os.Bundle;
import org.utahgtug.ugtris.core.HasBoardControl;
import playn.android.GameActivity;
import playn.core.PlayN;

import org.utahgtug.ugtris.core.Ugtris;

public class UgtrisActivity extends GameActivity {

  private Ugtris ugtris = null;
  private HasBoardControl controller = null;

  @Override
  public void main(){
    platform().assetManager().setPathPrefix("org/utahgtug/ugtris/resources");
    
    ugtris = new Ugtris();
    ugtris.addController(controller);
    PlayN.run(ugtris);
  }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        controller = new OnScreenControl(this);
    }
}
