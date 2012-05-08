package org.utahgtug.ugtris.core;

public interface Console {
	
	public void setScore (int score);
	
	public void setNextShape ( Shape.Tetrominoes next );
	
	public void paint ( );

    public void setLevel(int level);
}
