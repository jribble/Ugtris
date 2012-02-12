package org.utahgtug.ugtris.core;

import static playn.core.PlayN.graphics;

import org.utahgtug.ugtris.core.Shape.Tetrominoes;

import playn.core.Graphics;
import playn.core.SurfaceLayer;

public class SideConsole implements Console {
	
	int x;
	int y;
	int width;
	int height;
	int squareWidth;
	int squareHeight;
	
	int score;
	Shape.Tetrominoes nextShape;
	
	SurfaceLayer layer = null;

	public SideConsole ( int x, int y, int width, int height, int squareWidth, int squareHeight ) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.squareWidth = squareWidth;
		this.squareHeight = squareHeight;
	}
	
	@Override
	public void setScore(int score) {
		this.score = score;		
	}

	@Override
	public void setNextShape(Shape.Tetrominoes next) {
		this.nextShape = next;
	}

	@Override
	public void paint() {
    	Graphics g = graphics();
    	if(layer == null) {
    		layer = g.createSurfaceLayer(graphics().screenWidth(), graphics().screenHeight());
    		g.rootLayer().add(layer);
    	} 
    	layer.surface().clear();

    	int curX = x+50;
    	int curY = y+100;
        if (nextShape != null && nextShape != Tetrominoes.NoShape) {
        	Shape nextPiece = new Shape();
        	nextPiece.setShape(nextShape);
            for (int i = 0; i < 4; ++i) {
                int x = nextPiece.x(i);
                int y = nextPiece.y(i);
                Board.drawSquare(layer, curX + (x*squareWidth), curY + (y*squareHeight), squareWidth, squareHeight, nextPiece.getShape());
            }
        }
		
	}
	
}
