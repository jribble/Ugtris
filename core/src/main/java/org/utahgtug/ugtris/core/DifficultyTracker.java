//Written by Nathan Harward

package org.utahgtug.ugtris.core;


public class DifficultyTracker {
    // fields
    private float mDifficulty = 0;
    private int mLinesRemoved = 0;
    private int mPiecesFallen = 0;
    // consts
    private final float LINE_WEIGHT = 6f;
    private final float PIECE_WEIGHT = 1.5f;
    private final float TIME_MULITIPLIER = 0.2f;
    public final static int TIME_START = 160;
    public final static int MAX_LEVELS = 10;
    private final int DIFFICULTY_PER_LEVEL = 100;
    public final static int MIN_RATE = 40; //40 ms per frame

    private void updateDifficulty() {
        mDifficulty = (mLinesRemoved * LINE_WEIGHT) + (mPiecesFallen * PIECE_WEIGHT);
    }

    /*
   Method: addLinesRemove(int)
   For increasing game difficulty when lines are removed from the game
   Parameters: lines - a integer between 0 and 4. Enforced by an Exception.
    */
    public void addLinesRemoved(int lines) throws Exception {
        if ((lines > 4) || (lines < 0))
            throw new Exception("Invalid number of lines");
        mLinesRemoved += lines;
        updateDifficulty();
    }

    public void addPieceFallen() {
        mPiecesFallen++;
        updateDifficulty();
    }

    public int getRateInMilliseconds() {
        int rate = (TIME_START - (int) (mDifficulty * TIME_MULITIPLIER));
        if (rate < MIN_RATE) rate = MIN_RATE;
        return rate;
    }

    public int getLevel() {
        int level = (int) (mDifficulty);
        level /= DIFFICULTY_PER_LEVEL; //integer division
        level += 1; //starts from 1 instead of 0
        if (level > MAX_LEVELS) level = MAX_LEVELS;

        return level;
    }

    public void clearTracker() {
        mLinesRemoved = 0;
        mPiecesFallen = 0;
        updateDifficulty();
    }
}
