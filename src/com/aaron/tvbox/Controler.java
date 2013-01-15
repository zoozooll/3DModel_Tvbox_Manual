package com.aaron.tvbox;

import min3d.Min3d;
import android.text.Html.TagHandler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class Controler {
	
	private final static String TAG = "Controler";
	
	/**静止状态*/
	private final static int CONTROL_STATE_ACTIONLESS = 0;
	/**转动状态*/
	private final static int CONTROL_STATE_ROTATION = 1;
	/**移动状态*/
	private final static int CONTROL_STATE_POSITION = 2;
	
	/*
	 * show the 3d mode site
	 * 
	 *  @author Aaron Lee
	 * */	

	float mRotationY;

	float mRotationX;

	float mRotationZ;

	float mScale= 1.0f;

	float mPositionX;

	float mPositionY;

	float mPositionZ;
	
	// for onTouchEvent value;
	private float baseValue;
	private float baseMoveX;
	private float baseMoveY;
	private float singleX = -1;
	private float singleY = -1;
	private long firstDownTime;
	
	/**动作状态，值取决于*/
	private int mState;
	private boolean mDoubleEnable;
	private boolean mTouchEnable;
	
	
	public void eventMoving(MotionEvent event) {
		mDoubleEnable = false;
		if (event.getPointerCount() >= 2) {
			//Log.d(Min3d.TAG, "first down doubletap");
			mState = CONTROL_STATE_POSITION;
			if (mState ==  CONTROL_STATE_POSITION) {
				float x = event.getX(0) - event.getX(1);
				float y = event.getY(0) - event.getY(1);
				float value = (float) Math.sqrt(x * x + y * y);// 计算两点的距离
				if (baseValue == 0 || baseMoveX == 0 || baseMoveY == 0) {
					baseValue = value;
					baseMoveX = (event.getX(0) + event.getX(1))/2.0f;
					baseMoveY = (event.getY(0) + event.getY(1))/2.0f;
				} else {
					float scale = (float) ((value - baseValue) / (TVBox3DActivity.sScreenSize * 45));
					//Log.d(TAG, "scale "+scale );
					mTouchEnable = true;
					mScale += scale; 	// 响应转动事件
					float currentX = (event.getX(0) + event.getX(1))/2.0f;
					float currentY = (event.getY(0) + event.getY(1))/2.0f;
					float disX = currentX - baseMoveX;
					float disY = currentY - baseMoveY;
					//Log.d(TAG, "posistion x "+currentX + " - "+ baseMoveX);
					//Log.d(TAG, "posistion y "+currentY + " - "+ baseMoveY);
					if (currentX >= 10 || currentY >= 10 || currentX <= -10 || currentY  <= -10) {
						mTouchEnable = true;
						// 响应平移事件
						mPositionX += disX/(TVBox3DActivity.sScreenWidth) ;
						mPositionY -= disY/(TVBox3DActivity.sScreenHeight);
						baseMoveX = currentX;
						baseMoveY = currentY;
					}
				}
			}
			
		} else if (event.getPointerCount() == 1 && singleX != -1 && singleY != -1 /*&& mState == CONTROL_STATE_ROTATION*/) {
			//Log.d(Min3d.TAG, "first down singletap");
			if(mState == CONTROL_STATE_ACTIONLESS) {
				mState = CONTROL_STATE_ROTATION;
			}
			if (mState == CONTROL_STATE_ROTATION) {
				float x = event.getRawX();
				float y = event.getRawY();
				float disX = x - singleX;
				float disY = y -singleY;
				if(disX > 1|| disY >1) {
					mTouchEnable = true;
					// 响应转动事件
					mRotationY += 100 * disX/TVBox3DActivity.sScreenWidth;
					mRotationX += 100 * disY/TVBox3DActivity.sScreenHeight;
				}
				singleX = x;
				singleY = y;
			}
		}
	}


	public void eventUp() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - firstDownTime < 500 
				&& currentTime - firstDownTime > 350
				&& !mTouchEnable
				&& !mDoubleEnable) {
			resetShowing();
		}
		mState = CONTROL_STATE_ACTIONLESS;
	}

	public void eventDown(MotionEvent event) {
		baseValue = 0;
		mTouchEnable = false;
		if (event.getPointerCount() == 1) {
			singleX = event.getRawX();
			singleY = event.getRawY();
		} 
		baseMoveX = 0; 
		baseMoveY = 0;
		long currentDown = System.currentTimeMillis();
		if (currentDown - firstDownTime <200) {
			largestOrLest() ;
			mDoubleEnable = true;
		} else {
			firstDownTime = currentDown;
			mDoubleEnable = false;
		}
	}
	
	/**最大最小化*/
	private void largestOrLest() {
		if(mScale >= 2.0f) {
			mScale = 1.0f;
		} else {
			mScale = 2.2f;
		}
	}
	
	public void resetShowing() {
		mRotationY = 0;
		mRotationX = 0;
		mRotationZ = 0;
		mScale= 1.0f;
		mPositionX = 0;
		mPositionY = 0;
		mPositionZ = 0;
	}

}
