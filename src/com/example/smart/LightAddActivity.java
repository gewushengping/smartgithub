package com.example.smart;

import com.example.smart.MainActivity.Buttonlistener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class LightAddActivity extends Activity{
	private ImageTextButton light=null; 
	 SocketClientTh socketClientTh=null;
	protected void onCreate(Bundle savedInstanceState) {//这里相当于是一个方法但是这里是主线程的方法最先执行这里
	    //这里是创建acctivity 对象
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lightadd);//这里应该是开启一个线程去判断
	      light = (ImageTextButton) findViewById(R.id.light);  
	       light.setImageResource(R.drawable.light_open);  
	       
	      // socketClientTh = (SocketClientTh) getIntent().getSerializableExtra("book");
	      //while(true)
	      // socketClientTh.SecketSend("ttt\n");
	       
	       // light.setTextViewText("灯光1"); 
	       /* findViewById(R.id.light).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
						
						if(lightState)
						{
							//itb .setBackgroundDrawable((DeviceManagement.this.getResources().getDrawable(R.drawable.light_open)));	
							itb.setImageDrawable((DeviceManagement.this.getResources().getDrawable(R.drawable.light_open)));
							lightState=false;
						}
						else{
							//itb .setBackgroundDrawable((DeviceManagement.this.getResources().getDrawable(R.drawable.light_close)));	
							itb.setImageDrawable((DeviceManagement.this.getResources().getDrawable(R.drawable.light_close))); 
							lightState=true;	
						}
						
				
						// mainWorkthread.socketClientTh.SecketSend("litongqing");
	           	} 
	           	catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}
						
				}
			});*/
	       
		
		
	}
	

}
