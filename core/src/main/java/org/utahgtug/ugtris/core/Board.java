package org.utahgtug.ugtris.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.assets;

import org.utahgtug.ugtris.core.Shape.Tetrominoes;
import org.utahgtug.ugtris.core.ScoreTracker;

import playn.core.CanvasLayer;
import playn.core.Color;
import playn.core.Graphics;
import playn.core.SurfaceLayer;
import playn.core.PlayN;
import playn.core.Sound;


public class Board implements BoardControl {
    final int BoardWidth = 15;
    final int BoardHeight = 30;
    
    int boardX;
    int boardY;
    int width;
    int height;
    
    SurfaceLayer layer = null;

    boolean isFallingFinished = false;
    boolean isStarted = false;
    boolean isPaused = false;
    int numLinesRemoved = 0;
    int curX = 0;
    int curY = 0;
    Shape nextPiece = null;
    Shape curPiece = null;
    Tetrominoes[] board;

    Console console = null;
    ScoreTracker scoreTracker = null;

    // The various bang sounds for a piece hitting the bottom of the board
    private Sound[] bangSounds = null;
    private static final String[] bangSoundSources = {"audio/thump1", "audio/thump2", "audio/thump3"};
    
    public Board(Ugtris parent) {
        nextPiece = new Shape();
        nextPiece.setRandomShape();
        curPiece = new Shape();
        boardX = 0;
        boardY = 0;
        width = graphics().screenWidth();
        height = graphics().screenHeight();

        board = new Tetrominoes[BoardWidth * BoardHeight];
        clearBoard();  

        scoreTracker = new ScoreTracker();

        // Set up the sound we'll need for various events
        bangSounds = new Sound[bangSoundSources.length];
        for( int i = 0; i < bangSoundSources.length; ++i ) {
            bangSounds[i] = assets().getSound(bangSoundSources[i]);
        }
    }
    
    public Board(Ugtris parent, int boardX, int boardY, int width, int height) {
    	this(parent);
    	
    	this.boardX = boardX;
    	this.boardY = boardY;
    	this.width = width;
    	this.height = height;
    }

    public void update() {
        if (isFallingFinished) {
            isFallingFinished = false;
            newPiece();
        } else {
            oneLineDown();
        }
    }

    int squareWidth() { return (int) width / BoardWidth; }
    int squareHeight() { return (int) height / BoardHeight; }
    Tetrominoes shapeAt(int x, int y) { return board[(y * BoardWidth) + x]; }


    public void start()
    {
        if (isPaused)
            return;

        isStarted = true;
        isFallingFinished = false;
        numLinesRemoved = 0;
        clearBoard();

        newPiece();
        //timer.start();
    }

    private void pause()
    {
        if (!isStarted)
            return;

        isPaused = !isPaused;
        if (isPaused) {
            //timer.stop();
            //statusbar.setText("paused");
        } else {
            //timer.start();
            //statusbar.setText(String.valueOf(numLinesRemoved));
        }
        //repaint();
    }

    public void paint()
    { 
    	Graphics g = graphics();
    	if(layer == null) {
    		layer = g.createSurfaceLayer(graphics().screenWidth(), graphics().screenHeight());
    		g.rootLayer().add(layer);
    	} 
    	layer.surface().clear();

        //Dimension size = getSize();
        int boardTop = (height - BoardHeight * squareHeight()) + boardY;
        int boardWidth = BoardWidth * squareWidth() + 1;
        int boardHeight = BoardHeight * squareHeight() + boardTop;
        
        layer.surface().setFillColor(Color.rgb(0, 0, 0));
        layer.surface().drawLine(boardX, boardTop, boardX, boardHeight, 1);
        layer.surface().drawLine(boardX, boardHeight, boardX + boardWidth, boardHeight, 1);
        layer.surface().drawLine(boardX + boardWidth, boardTop, boardX + boardWidth, boardHeight, 1);


        for (int i = 0; i < BoardHeight; ++i) {
            for (int j = 0; j < BoardWidth; ++j) {
                Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
                if (shape != Tetrominoes.NoShape)
                    Board.drawSquare(layer, boardX + 1 + j * squareWidth(),
                               boardTop + i * squareHeight(), squareWidth(), squareHeight(), shape);
            }
        }

        if (curPiece.getShape() != Tetrominoes.NoShape) {
            for (int i = 0; i < 4; ++i) {
                int x = curX + curPiece.x(i);
                int y = curY - curPiece.y(i);
                drawSquare(layer, boardX + 1 + x * squareWidth(),
                           boardTop + (BoardHeight - y - 1) * squareHeight(),
                           squareWidth(), squareHeight(),
                           curPiece.getShape());
            }
        }
    }

    private void dropDown()
    {
        int newY = curY;
        while (newY > 0) {
            if (!tryMove(curPiece, curX, newY - 1))
                break;
            --newY;
        }
        pieceDropped();
    }

    private void oneLineDown()
    {
        if (!tryMove(curPiece, curX, curY - 1))
            pieceDropped();
    }


    private void clearBoard()
    {
        for (int i = 0; i < BoardHeight * BoardWidth; ++i)
            board[i] = Tetrominoes.NoShape;
    }

    /*
     * Handle the logic for when the piece hits the bottom of the board
     */
    private void pieceDropped()
    {
        for (int i = 0; i < 4; ++i) {
            int x = curX + curPiece.x(i);
            int y = curY - curPiece.y(i);
            board[(y * BoardWidth) + x] = curPiece.getShape();
        }

        // Play the sound for the piece hitting the bottom of the board
        playThump();
        
        removeFullLines();

        if (!isFallingFinished)
            newPiece();
    }

    private void newPiece()
    {
    	curPiece.setShape(nextPiece.getShape());
        nextPiece.setRandomShape();
        if (console != null) {
        	console.setNextShape(nextPiece.getShape());            
        	console.setScore(scoreTracker.getScore());
        }
        
        curX = BoardWidth / 2 + 1;
        curY = BoardHeight - 1 + curPiece.minY();

        if (!tryMove(curPiece, curX, curY)) {
            curPiece.setShape(Tetrominoes.NoShape);
            //timer.stop();
            isStarted = false;
            //statusbar.setText("game over");
        }
    }

    private boolean tryMove(Shape newPiece, int newX, int newY)
    {
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
                return false;
            if (shapeAt(x, y) != Tetrominoes.NoShape)
                return false;
        }

        curPiece = newPiece;
        curX = newX;
        curY = newY;
        return true;
    }

    private void removeFullLines()
    {
        int numFullLines = 0;

        for (int i = BoardHeight - 1; i >= 0; --i) {
            boolean lineIsFull = true;

            for (int j = 0; j < BoardWidth; ++j) {
                if (shapeAt(j, i) == Tetrominoes.NoShape) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                ++numFullLines;
                for (int k = i; k < BoardHeight - 1; ++k) {
                    for (int j = 0; j < BoardWidth; ++j)
                         board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
                }
            }
        }

        if (numFullLines > 0) {
            numLinesRemoved += numFullLines;
            //statusbar.setText(String.valueOf(numLinesRemoved));
            //Add to the score
            scoreTracker.addScoreEvent(ScoreTracker.ScoreEvent.fromInt(numLinesRemoved));
            isFallingFinished = true;
            curPiece.setShape(Tetrominoes.NoShape);
            //repaint();
        }
        else
        {
            scoreTracker.addScoreEvent(ScoreTracker.ScoreEvent.None); //So we can keep track of back to back events
        }
     }

    public static void drawSquare(SurfaceLayer layer, int x, int y, int squareWidth, int squareHeight, Tetrominoes shape)
    {  	
    	
        int colors[] = { Color.rgb(0, 0, 0), Color.rgb(204, 102, 102), 
        		Color.rgb(102, 204, 102), Color.rgb(102, 102, 204), 
        		Color.rgb(204, 204, 102), Color.rgb(204, 102, 204), 
        		Color.rgb(102, 204, 204), Color.rgb(218, 170, 0)
        };

        int brighter_colors[] = { Color.rgb(0, 0, 0), Color.rgb(210, 121, 121), 
        		Color.rgb(121, 210, 121), Color.rgb(121, 121, 210), 
        		Color.rgb(210, 210, 121), Color.rgb(210, 121, 210), 
        		Color.rgb(121, 210, 210), Color.rgb(245, 192, 0)
        };

        int darker_colors[] = { Color.rgb(0, 0, 0), Color.rgb(198, 83, 83), 
        		Color.rgb(83, 198, 83), Color.rgb(83, 83, 198), 
        		Color.rgb(198, 198, 83), Color.rgb(198, 83, 198), 
        		Color.rgb(83, 198, 198), Color.rgb(194, 152, 0)
        };


        int color = colors[shape.ordinal()];
        int brighter_color = brighter_colors[shape.ordinal()];
        int darker_color = darker_colors[shape.ordinal()];

        layer.surface().setFillColor(color);
        layer.surface().fillRect(x + 1, y + 1, squareWidth - 2, squareHeight - 2);

        layer.surface().setFillColor(brighter_color);
        layer.surface().drawLine(x, y + squareHeight - 1, x, y, 1);
        layer.surface().drawLine(x, y, x + squareWidth - 1, y, 1);

        layer.surface().setFillColor(darker_color);
        layer.surface().drawLine(x + 1, y + squareHeight - 1,
                         x + squareWidth - 1, y + squareHeight - 1, 1);
        layer.surface().drawLine(x + squareWidth - 1, y + squareHeight - 1,
                         x + squareWidth - 1, y + 1, 1);
    }

    public static void drawSquare(CanvasLayer layer, int x, int y, int squareWidth, int squareHeight, Tetrominoes shape)
    {  	
    	
        int colors[] = { Color.rgb(0, 0, 0), Color.rgb(204, 102, 102), 
        		Color.rgb(102, 204, 102), Color.rgb(102, 102, 204), 
        		Color.rgb(204, 204, 102), Color.rgb(204, 102, 204), 
        		Color.rgb(102, 204, 204), Color.rgb(218, 170, 0)
        };

        int brighter_colors[] = { Color.rgb(0, 0, 0), Color.rgb(210, 121, 121), 
        		Color.rgb(121, 210, 121), Color.rgb(121, 121, 210), 
        		Color.rgb(210, 210, 121), Color.rgb(210, 121, 210), 
        		Color.rgb(121, 210, 210), Color.rgb(245, 192, 0)
        };

        int darker_colors[] = { Color.rgb(0, 0, 0), Color.rgb(198, 83, 83), 
        		Color.rgb(83, 198, 83), Color.rgb(83, 83, 198), 
        		Color.rgb(198, 198, 83), Color.rgb(198, 83, 198), 
        		Color.rgb(83, 198, 198), Color.rgb(194, 152, 0)
        };


        int color = colors[shape.ordinal()];
        int brighter_color = brighter_colors[shape.ordinal()];
        int darker_color = darker_colors[shape.ordinal()];

        layer.canvas().setFillColor(color);
        layer.canvas().setStrokeColor(color);
        layer.canvas().fillRect(x + 1, y + 1, squareWidth - 2, squareHeight - 2);

        layer.canvas().setFillColor(brighter_color);
        layer.canvas().setStrokeColor(brighter_color);
        layer.canvas().drawLine(x, y + squareHeight - 1, x, y);
        layer.canvas().drawLine(x, y, x + squareWidth - 1, y);

        layer.canvas().setFillColor(darker_color);
        layer.canvas().setStrokeColor(darker_color);
        layer.canvas().drawLine(x + 1, y + squareHeight - 1,
                         x + squareWidth - 1, y + squareHeight - 1);
        layer.canvas().drawLine(x + squareWidth - 1, y + squareHeight - 1,
                         x + squareWidth - 1, y + 1);
    }
    
    public void moveLeft()
    {
        tryMove(curPiece, curX - 1, curY);    	
    }
    
    public void moveRight()
    {
        tryMove(curPiece, curX + 1, curY);	
    }

        
    public void rotateLeft()
    {
        tryMove(curPiece.rotateLeft(), curX, curY);  	
    }
    
    public void rotateRight()
    {
        tryMove(curPiece.rotateRight(), curX, curY);
    }
    
    public void drop()
    {
        dropDown();    	
    }

    /*
    class TAdapter extends KeyAdapter {
         public void keyPressed(KeyEvent e) {

             if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {  
                 return;
             }

             int keycode = e.getKeyCode();

             if (keycode == 'p' || keycode == 'P') {
                 pause();
                 return;
             }

             if (isPaused)
                 return;

             switch (keycode) {
             case KeyEvent.VK_LEFT:
                 tryMove(curPiece, curX - 1, curY);
                 break;
             case KeyEvent.VK_RIGHT:
                 tryMove(curPiece, curX + 1, curY);
                 break;
             case KeyEvent.VK_DOWN:
                 tryMove(curPiece.rotateRight(), curX, curY);
                 break;
             case KeyEvent.VK_UP:
                 tryMove(curPiece.rotateLeft(), curX, curY);
                 break;
             case KeyEvent.VK_SPACE:
                 dropDown();
                 break;
             case 'd':
                 oneLineDown();
                 break;
             case 'D':
                 oneLineDown();
                 break;
             }

         }
     }
     */
    
    public void setConsole ( Console console ) {
    	this.console = console;
    }
    
    public Console getConsole ( ) {
    	return console;
    }
    
    private void playThump() {
    	java.util.Random random = new java.util.Random();
    	int i = random.nextInt(3);
    	bangSounds[i].play();
    }
}
