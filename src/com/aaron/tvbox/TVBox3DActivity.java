package com.aaron.tvbox;

import min3d.Min3d;
import min3d.Shared;
import min3d.core.Object3dContainer;
import min3d.core.RendererActivity;
import min3d.parser.IParser;
import min3d.parser.Parser;
import min3d.vos.Light;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html.TagHandler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;

public class TVBox3DActivity extends RendererActivity {
	
	public final static String TAG = "TVBox3DActivity";
	
	public static int sScreenWidth;
	public static int sScreenHeight;
	/** diagonal pix*/
	public static double sScreenSize;
	
	/**
	 * How to load a model from a .obj file
	 * 
	 * @author dennis.ippel
	 * 
	 */
	private Object3dContainer objModel;
	
	private Controler mControler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initScreenSize();
			
		
		   /* Resources res = getResources();
	        Drawable drawable = res.getDrawable(R.drawable.bg);
	        this.getWindow().setBackgroundDrawable(drawable);*/
		//scene.set_backgroundColor((short)0, (short)0, (short)0, (short)0);
		//Shared.renderer().setmBackgoundRes(R.drawable.sence_bg);
	}
	
	 @Override 
	protected void glSurfaceViewConfig()
    {
		// !important
        _glSurfaceView.setEGLConfigChooser(8,8,8,8, 16, 0);
        _glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        _glSurfaceView.setZOrderOnTop(true);
    }


//	private Object setImageResource(int senceBg) {
//		// TODO Auto-generated method stub
//		return senceBg;
//	}


	@Override
	public void initScene() {
		// !important
		scene.backgroundColor().setAll(0x00000000);
		
		scene.lights().add(new Light());
		 
		IParser parser = Parser.createParser(Parser.Type.OBJ,
				getResources(), "com.aaron.tvbox:raw/jidinghe_obj", true);
		parser.parse();

		objModel = parser.getParsedObject();
		objModel.scale().x = objModel.scale().y = objModel.scale().z = 1.0f;
		scene.addChild(objModel);
		mControler = new Controler();
	}
	
	private void initScreenSize(){
		Display display = getWindowManager().getDefaultDisplay();
		sScreenWidth = display.getWidth();
		sScreenHeight = display.getHeight();
		sScreenSize = Math.sqrt(sScreenWidth * sScreenWidth +  sScreenHeight * sScreenHeight);
	}

	@Override
	public void updateScene() {
		objModel.rotation().x = mControler.mRotationX;
		objModel.rotation().y = mControler.mRotationY;
		objModel.rotation().z = mControler.mRotationZ;
		objModel.scale().x = mControler.mScale;
		objModel.scale().y = mControler.mScale;
		objModel.scale().z = mControler.mScale;
		objModel.position().x = mControler.mPositionX;
		objModel.position().y = mControler.mPositionY;
		objModel.position().z = mControler.mPositionZ;
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*if (BuildConfig.DEBUG) {
			Log.d(TAG, "ontouch " + event.getX() + " " + event.getY());
			Log.d(TAG, "ontouch0 " + event.getX(0) + " " + event.getY(0));
			Log.d(TAG, "ontouch1 " + event.getX(1) + " " + event.getY(1));
		}*/
		//Log.d(Min3d.TAG, "scan "+objModel.scale().x);
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mControler.eventDown(event);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			mControler.eventMoving(event);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			mControler.eventUp();
		}
		return true;

	}


	
}