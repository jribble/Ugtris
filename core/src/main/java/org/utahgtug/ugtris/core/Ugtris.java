package org.utahgtug.ugtris.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;

public class Ugtris implements Game {
	
	private Board board;
	
  @Override
  public void init() {
	  graphics().setSize(400, 400);
	  
    // create and add background image layer
    Image bgImage = assetManager().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);
    
    //graphics().rootLayer().add(sLayer);
    
    board = new Board(this, 100, 10, 200, 380);
    KeyboardController controller = new KeyboardController(board);
    keyboard().setListener(controller);
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
	board.paint();
  }

  @Override
  public void update(float delta) {
	  board.update();
  }

  @Override
  public int updateRate() {
    return 125;
  }
}
