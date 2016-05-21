package com.example.smart;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.button.CustomImageButton;
import com.sun.corba.se.spi.activation.Server;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class DeviceManagement extends Activity  {
	boolean lightState=true;
	ImageButton lightbutton=null;
	 SocketClientTh socketClientTh=null;
	 ListView list=null;
	 private ListView deviceList=null;
	// private CustomImageButton login_btn_login;
	 ArrayAdapter<String>  arr_adapter=null;
	 private DataApplication application; 
	protected void onCreate(Bundle savedInstanceState) {
		
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_management);
       // final DataApplication app = (DataApplication)getApplication(); 
     //   login_btn_login = (CustomImageButton)findViewById(R.id.login_btnLogin); 
        deviceList = (ListView)findViewById(R.id.listViewadd);/*����һ����̬�����Ⲣ���Ƕ�̬���������б�*/ 
        application=(DataApplication)getApplicationContext();  
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();/*�������д������*/
	     //��������hashmap��ɵġ��ɷ���hashmap 
	   
	            
	    	          //object��������ĸ���
	                 HashMap<String, Object> map = new HashMap<String, Object>();  
	                 map.put("ItemImage", R.drawable.light_open);//����ͼƬ            map.put("ItemTitle", "��"+i+"��");  
	                 map.put("ItemText", "�ƹ���");  
	                 listItem.add(map);  
	                 HashMap<String, Object> map1 = new HashMap<String, Object>();  
	                 map1.put("ItemImage", R.drawable.light_open);//����ͼƬ            map.put("ItemTitle", "��"+i+"��");  
	                 map1.put("ItemText", "�յ�");  
	                 listItem.add(map1); 
	                 map1.put("ItemImage", R.drawable.light_open);//����ͼƬ            map.put("ItemTitle", "��"+i+"��");  
	                 map1.put("ItemText", "����");  
	                 listItem.add(map1); 
	             
	      //listItem��Ҫ���ص�����
	      //
	      //��һ������ʱ��Ҫ��������ͼҲ����activity
	      //�ڶ���������һ������ֻҪ��һ��list�Ϳ��������list���б����˼��һ�����ݽṹ����һ�����ArrayList�������ڲ��洢������Map���߼̳���Map�Ķ��󣬱���hashMap�������� ����Ϊ����
	      //Դ������ÿһ��ArrayList�е�һ�оʹ�����ֳ�����һ��map�ļ�������һ�е�������ֵҲ���������ġ�
	      //��������ÿһ����Ҫ��xml�ļ���
	      //���ĸ���string���� ��Ҫ�ǽ�map�����е�����ӳ�䵽������һһ��Ӧ
	      //������ǽ����ĸ�������ֵһһ������ʾ�����������ǹ̶��Ĳ��ù� ����ȷ���ġ�
	      SimpleAdapter adapter = new SimpleAdapter(this, listItem,
	    		  R.layout.device_item, new String[] { "ItemImage","ItemTitle", "ItemText"},
	              new int[] { R.id.ItemImage,R.id.ItemTitle,R.id.ItemText });
	      
	      
	      deviceList.setAdapter(adapter);
	      deviceList.setOnItemClickListener(new 
	    		  OnItemClickListener() {

	    		              @Override
	    		              public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    		                      long arg3) {
	    		                  
	    		            	  switch(arg2)
	    		            	  {
	    		            	  case 0:
	    		            	  { 
	    		            		 HashMap<String, Object> map = new HashMap<String, Object>();  
	    		 	                 map.put("ItemImage", R.drawable.light_open);//����ͼƬ            map.put("ItemTitle", "��"+i+"��");  
	    		 	                 map.put("ItemText", "�ƹ���");  
	    		            		 application.listItem.add(map); //�������listviewû�й�ϵ
	    		            		
	    		            		  break;
	    		            	  }
	    		            	  default: break;
	    		            	  
	    		            	  }        
	    		            	  
	    		            	  
	    		            	  
	    		            	  
	    		            	  
	    		            	  
	    		              }
	    		          });
	    		    
     
		
		
	
	
	
	
	
	} 
      
      
      
      
      
      
      
      
      
      
     
      //ΪListView�������� lv.setOnItemClickListener(new 
	

      
      
      
      
	
      
   
    
 
        

         
 
         

        
        
      
       
       
        
       
       //�������ǵõ���Դ���ǵõ� Drawable
        
      //  my_button2 = (Button)findViewById(R.id.my_button2);
    //    my_button2.setText("@string/close");    //setText���治�ܲ�����Դ����
                                                //��Դ������ʾ�ı�Ӧ������xml�е�
       // my_button2.setText("Close");*/
   
	
	 class ItemClickEvent implements OnItemClickListener{
 		
 		//������Ҫע����ǵ���������arg2�����Ǵ������ڼ���ѡ��
 

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			
				String text = (String) list.getItemAtPosition(arg2);
				switch(arg2)
				{
				case 0: 
				 try{
					Intent intent=new Intent();//��������ͼ
				 //  intent.setClass(MainActivity.this,ButtonMActivity.class);
			     intent.setClass(DeviceManagement.this,LightAddActivity.class);
			   
			     startActivity(intent);
				 }catch (Exception e)
				 {
					 
					  
				 }
					break;
				}
				
 			//ͨ����˿������ʾ������
 			Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();
				
			}	
 	}
	 //Ҳ���Ƿ�����Ҫʹ�÷�����඼��д��������õ�ʵ��
	 public class MyServiceConnection implements ServiceConnection
	 {
	 //���󶨷���ɹ���ʱ�����ô˷���
	 public void onServiceConnected(ComponentName name, IBinder service)
	 {
	 
     //�õ�MyService.MyBinder��������ͨ��������������������еķ���
	// myBinder = (MyService.MyBinder) service;
	 //���÷����е�getname()��������ֵ���õ�TextView�Ͻ�����ʾ
	// tv_show.setText(myBinder.getname());
	 }
	 public void onServiceDisconnected(ComponentName name)
	 {
	 }
	 }
  

}
