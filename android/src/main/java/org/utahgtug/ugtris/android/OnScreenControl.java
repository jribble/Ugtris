package org.utahgtug.ugtris.android;

import org.utahgtug.ugtris.core.HasBoardControl;
import org.utahgtug.ugtris.core.BoardControl;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class OnScreenControl implements HasBoardControl {
	private BoardControl board;
	private Button leftButton;
	private Button rightButton;
	private Button rotateLeftButton;
	private Button rotateRightButton;
	private Button dropButton;
	
	public OnScreenControl(Activity activity) {        
        Context context = activity.getApplicationContext();
        LinearLayout lll = new LinearLayout(context);
        leftButton = new Button(context);
        leftButton.setWidth(100);
        leftButton.setHeight(100);
        leftButton.setText("Left");
        lll.addView(leftButton);
        leftButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					board.moveLeft();
					break;
				}
				return true;
			}
        	
        });
        rotateLeftButton = new Button(context);
        rotateLeftButton.setText("Rotate");
        rotateLeftButton.setWidth(100);
        rotateLeftButton.setHeight(100);
        lll.addView(rotateLeftButton);
        rotateLeftButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					board.rotateLeft();
					break;
				}
				return true;
			}
        	
        });
        lll.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        activity.addContentView(lll, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        

        LinearLayout rll = new LinearLayout(context);
        rotateRightButton = new Button(context);
        rotateRightButton.setText("Rotate");
        rotateRightButton.setWidth(100);
        rotateRightButton.setHeight(100);
        rll.addView(rotateRightButton);
        rotateRightButton.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        board.rotateRight();
                        break;
                }
                return true;
            }

        });
        rightButton = new Button(context);
        rightButton.setText("Right");
        rightButton.setWidth(100);
        rightButton.setHeight(100);
        rll.addView(rightButton);
        rightButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					board.moveRight();
					break;
				}
				return true;
			}
        	
        });
        rll.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
        activity.addContentView(rll, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
	}

	@Override
	public void setBoardControl(BoardControl control) {
		this.board = control;
	}

}
