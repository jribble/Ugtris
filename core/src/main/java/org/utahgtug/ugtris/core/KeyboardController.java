package org.utahgtug.ugtris.core;

import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Keyboard.Event;

public class KeyboardController extends Keyboard.Adapter implements HasBoardControl {

	private BoardControl board;
	
	public KeyboardController ( BoardControl board )
	{
		setBoardControl( board );
	}
	
	@Override
	public void onKeyDown(Event event) {
		// TODO Auto-generated method stub
		super.onKeyDown(event);

		Key keycode = event.key();
        switch (keycode) {
        case LEFT:
            board.moveLeft();
            break;
        case RIGHT:
            board.moveRight();
            break;
        case DOWN:
            board.rotateLeft();
            break;
        case UP:
            board.rotateRight();
            break;
        case SPACE:
            board.drop();
            break;
        }
	}

	@Override
	public void setBoardControl(BoardControl control) {
		this.board = control;		
	}
	

}
