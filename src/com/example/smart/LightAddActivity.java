package com.example.smart;

import com.example.smart.MainActivity.Buttonlistener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class LightAddActivity extends Activity{
	private ImageTextButton light=null; 
	 SocketClientTh socketClientTh=null;
	protected void onCreate(Bundle savedInstanceState) {//�����൱����һ�������������������̵߳ķ�������ִ������
	    //�����Ǵ���acctivity ����
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lightadd);//����Ӧ���ǿ���һ���߳�ȥ�ж�
	      light = (ImageTextButton) findViewById(R.id.light);  
	       light.setImageResource(R.drawable.light_open);  
	       
	      // socketClientTh = (SocketClientTh) getIntent().getSerializableExtra("book");
	      //while(true)
	      // socketClientTh.SecketSend("ttt\n");
	       
	       // light.setTextViewText("�ƹ�1"); 
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
