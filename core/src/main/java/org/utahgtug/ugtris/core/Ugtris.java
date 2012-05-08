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
    private final int GAME_UPDATE_RATE = 5;
    private int updateCounter = 0;
    private int ugtrisRate;
	
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
    ugtrisRate = DifficultyTracker.TIME_START;
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
      updateCounter += delta;
      if(updateCounter >= ugtrisRate)
      {
          board.update();
          updateCounter = 0;
          ugtrisRate = board.getUpdateRate();
      }
  }

  @Override
  public int updateRate() {
    return GAME_UPDATE_RATE;
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
