package com.aaron.tvbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Welcome extends Activity{ 
 private ViewFlipper viewFlipper;
 private ImageButton btn_left=null;
 private ImageButton btn_rigth=null;
 private ImageView btn_product1=null;
 private ImageView btn_product2=null;
 
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//handler.sendEmptyMessageDelayed(0, 3*1000);
		
		viewFlipper = (ViewFlipper) findViewById(R.id.myViewFlipper);
		btn_left=(ImageButton)findViewById(R.id.btn_left);
		btn_rigth=(ImageButton) findViewById(R.id.btn_right);
		btn_product1=(ImageView) findViewById(R.id.btn_product1);
		btn_product2=(ImageView) findViewById(R.id.btn_product2);
		
		
		OnClickListener ocl = new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.btn_left:
					viewFlipper.showPrevious();
					break;
				case R.id.btn_right:
					viewFlipper.showNext();
					break;
					
				case R.id.btn_product1:
					Intent intent1=new Intent(Welcome.this, TVBox3DActivity.class);
					startActivity(intent1);
					break;
				case R.id.btn_product2:
					Intent intent2=new Intent(Welcome.this, TVBox3DActivity.class);
					startActivity(intent2);
					break;
					

				}			
	};
		};
		
	btn_left.setOnClickListener(ocl);
	btn_rigth.setOnClickListener(ocl);
	btn_product1.setOnClickListener(ocl);
	
	
	
	
	}
	
	
	/*private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Intent intent = new Intent(Welcome.this, TVBox3DActivity.class);
			startActivity(intent);
			finish();
		}
		
	};
			
		};*/
	
	
		
			
	}

		
		
	
