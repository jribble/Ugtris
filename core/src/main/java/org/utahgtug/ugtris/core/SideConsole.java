package org.utahgtug.ugtris.core;

import static playn.core.PlayN.graphics;

import org.utahgtug.ugtris.core.Shape.Tetrominoes;

import playn.core.CanvasLayer;
import playn.core.Color;
import playn.core.Graphics;

public class SideConsole implements Console {
	
	public static int textColor = Color.rgb(0, 0, 0);
	
	int x;
	int y;
	int width;
	int height;
	int squareWidth;
	int squareHeight;
	
	int score;
    int level;
    String status = null;

	Shape.Tetrominoes nextShape;
	
	CanvasLayer layer = null;

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
    public void setStatus(String status){
        this.status = status;
    }
    @Override
    public void setLevel(int level) {
        this.level = level;
    }

	@Override
	public void setNextShape(Shape.Tetrominoes next) {
		this.nextShape = next;
	}

	@Override
	public void paint() {
    	Graphics g = graphics();
    	if(layer == null) {
    		layer = g.createCanvasLayer(graphics().screenWidth(), graphics().screenHeight());
    		g.rootLayer().add(layer);
    	} 
    	layer.canvas().clear();

    	int curX = x+5;
    	int curY = y+50;

    	layer.canvas().setStrokeColor(textColor);
    	layer.canvas().setFillColor(textColor);

        if(status != null)  {
            layer.canvas().setFillColor(Color.rgb(255, 0, 0));
            layer.canvas().drawText(status, curX, curY);
            curY = curY + 20;
            layer.canvas().setFillColor(textColor);
        }

    	layer.canvas().drawText("Score: " + score, curX, curY);

        curY = curY + 20;
        layer.canvas().drawText("Level: " + level, curX, curY);

    	curY = curY + 20;
    	layer.canvas().drawText("Coming Up:", curX, curY);
    	curX = x+30;
    	curY = curY + 20;
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
