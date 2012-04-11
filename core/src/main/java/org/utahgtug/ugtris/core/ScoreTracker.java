//Written by Nathan Harward

package org.utahgtug.ugtris.core;


// Staful Score Tracker/Counter that implements 
public class ScoreTracker {
	
	//Constants Event Score Values
	final int SINGLE = 100;
	final int DOUBLE = 200;
	final int TRIPLE = 400;
	final int UGTRIS = 800;

	//Constants Back to Back Score Multiplier
	final float SINGLE_BACK_2 = 1f;
	final float DOUBLE_BACK_2 = 1f;
	final float TRIPLE_BACK_2 = 1.25f;
	final float UGTRIS_BACK_2 = 1.5f;
	final float SINGLE_BACK_3 = 1f;
	final float DOUBLE_BACK_3 = 1f;
	final float TRIPLE_BACK_3 = 1.5f;
	final float UGTRIS_BACK_3 = 2f;

	// Enums
    enum ScoreEvent { 
    	None,   // Default Value
    	Single, // 1 Line Cleared
    	Double, // 2 Lines Cleared
    	Triple, // 3 Lines Cleared
    	Ugtris; // 4 Lines Cleared
    	//Would like to add other cool events unique to ugtris

    	//Enums in java are wierd so this is for "casting"
    	public static ScoreEvent fromInt(int x) {
	        switch(x) {
		        case 0:
		            return None;
		        case 1:
		            return Single;
		        case 2:
		            return Double;
		        case 3:
		            return Triple;   
		        case 4:
		            return Ugtris;            
	        }
	        return null;
    	}
    };
	
	// Properties
	int score;
	ScoreEvent lastEvent; //1 score event ago
	ScoreEvent secondToLastEvent; // 2 score events ago
	
	// Methods
	public ScoreTracker () {
		this.score = 0;
		this.lastEvent = ScoreEvent.None;
		this.secondToLastEvent  = ScoreEvent.None;
	}
	
	public int getScore() {
		return this.score;	
	}

	public void clearScore() {
		this.score = 0;	
		this.lastEvent = ScoreEvent.None;
		this.secondToLastEvent  = ScoreEvent.None;	
	}

	public void addScoreEvent(ScoreEvent event) {
		
		//Is this a back to back event?
		boolean doubleEvent = (event == this.lastEvent);
		boolean tripleEvent = (doubleEvent && (this.lastEvent == this.secondToLastEvent));

		// Parameters
		int base = 0;
		float de = 0f;
		float te = 0f;
		float multiplier = 0f;

		switch (event){
			case None:
				base = 0;
				de = 1;
				te = 1;
				break;
			case Single:
				base = SINGLE;
				de = SINGLE_BACK_2;
				te = SINGLE_BACK_3;
				break;
			case Double:
				base = DOUBLE;
				de = DOUBLE_BACK_2;
				te = DOUBLE_BACK_3;
				break;
			case Triple:
				base = TRIPLE;
				de = TRIPLE_BACK_2;
				te = TRIPLE_BACK_3;
				break;
			case Ugtris:
				base = UGTRIS;
				de = UGTRIS_BACK_2;
				te = UGTRIS_BACK_3;
				break;
		//	default:  
        //		throw new ArgumentException("Unknown enum value found.");  
		}

		if(tripleEvent) multiplier = te;
		else if(doubleEvent) multiplier = de;
		else multiplier = 1;

		int eventScore = (int)((float)base*multiplier);
		this.score += eventScore;

		lastEvent = event;
		secondToLastEvent = lastEvent;
	}
	
}
