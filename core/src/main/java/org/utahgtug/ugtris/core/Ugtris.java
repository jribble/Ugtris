package org.utahgtug.ugtris.core;

import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;

import java.util.ArrayList;
import java.util.List;

public class Ugtris implements Game {
	
	private Board board = null;
	private Console console;
    private List<HasBoardControl> controllers = new ArrayList<HasBoardControl>();
	
  @Override
  public void init() {
	  graphics().setSize(400, 400);
	  
    // create and add background image layer
    Image bgImage = assetManager().getImage("images/bg.png");
    ImageLayer bgLayer = graphics().createImageLayer(bgImage);
    graphics().rootLayer().add(bgLayer);
    
    //graphics().rootLayer().add(sLayer);
    
    board = new Board(this, 100, 10, 200, 380);
    console = new SideConsole ( 300, 0, 100, 400, board.squareWidth(), board.squareHeight() );
    board.setConsole(console);
    connectControllers();
    KeyboardController controller = new KeyboardController();
    addController(controller);
    keyboard().setListener(controller);
  }

  @Override
  public void paint(float alpha) {
    // the background automatically paints itself, so no need to do anything here!
	board.paint();
	console.paint();
  }

  @Override
  public void update(float delta) {
	  board.update();
  }

  @Override
  public int updateRate() {
    return 125;
  }

  public void addController ( HasBoardControl controller ) {
      controllers.add(controller);
      if ( board != null ) {
          controller.setBoardControl(board);
      }
  }

  private void connectControllers ( ) {
      for ( HasBoardControl controller : controllers ) {
          controller.setBoardControl ( board );
      }
  }
}
