package org.utahgtug.ugtris.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import org.utahgtug.ugtris.core.Ugtris;

public class UgtrisHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform platform = HtmlPlatform.register();
    platform.assetManager().setPathPrefix("ugtris/");
    PlayN.run(new Ugtris());
  }
}
