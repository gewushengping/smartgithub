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
        deviceList = (ListView)findViewById(R.id.listViewadd);/*定义一个动态数组这并不是动态数组这是列表*/ 
        application=(DataApplication)getApplicationContext();  
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();/*在数组中存放数据*/
	     //数组是由hashmap组成的。成分是hashmap 
	   
	            
	    	          //object是所有类的父类
	                 HashMap<String, Object> map = new HashMap<String, Object>();  
	                 map.put("ItemImage", R.drawable.light_open);//加入图片            map.put("ItemTitle", "第"+i+"行");  
	                 map.put("ItemText", "灯光类");  
	                 listItem.add(map);  
	                 HashMap<String, Object> map1 = new HashMap<String, Object>();  
	                 map1.put("ItemImage", R.drawable.light_open);//加入图片            map.put("ItemTitle", "第"+i+"行");  
	                 map1.put("ItemText", "空调");  
	                 listItem.add(map1); 
	                 map1.put("ItemImage", R.drawable.light_open);//加入图片            map.put("ItemTitle", "第"+i+"行");  
	                 map1.put("ItemText", "窗帘");  
	                 listItem.add(map1); 
	             
	      //listItem是要加载的数据
	      //
	      //第一个参数时所要关联的视图也就是activity
	      //第二个参数是一个泛型只要是一个list就可以这里的list是列表的意思是一种数据结构，这一般会是ArrayList，而他内部存储的则是Map或者继承自Map的对象，比如hashMap，这里呢 是作为数据
	      //源，而且每一个ArrayList中的一行就代表呈现出来的一行map的键就是这一行的列名，值也是有列名的。
	      //第三个是每一行需要的xml文件。
	      //第四个是string数组 主要是将map对象中的名称映射到列名，一一对应
	      //第五个是将第四个参数的值一一对象显示，下面这里是固定的不用管 上面确定的。
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
	    		 	                 map.put("ItemImage", R.drawable.light_open);//加入图片            map.put("ItemTitle", "第"+i+"行");  
	    		 	                 map.put("ItemText", "灯光类");  
	    		            		 application.listItem.add(map); //这里面和listview没有关系
	    		            		
	    		            		  break;
	    		            	  }
	    		            	  default: break;
	    		            	  
	    		            	  }        
	    		            	  
	    		            	  
	    		            	  
	    		            	  
	    		            	  
	    		            	  
	    		              }
	    		          });
	    		    
     
		
		
	
	
	
	
	
	} 
      
      
      
      
      
      
      
      
      
      
     
      //为ListView绑定适配器 lv.setOnItemClickListener(new 
	

      
      
      
      
	
      
   
    
 
        

         
 
         

        
        
      
       
       
        
       
       //这里先是得到资源再是得到 Drawable
        
      //  my_button2 = (Button)findViewById(R.id.my_button2);
    //    my_button2.setText("@string/close");    //setText里面不能采用资源引用
                                                //资源引用显示文本应该是在xml中的
       // my_button2.setText("Close");*/
   
	
	 class ItemClickEvent implements OnItemClickListener{
 		
 		//这里需要注意的是第三个参数arg2，这是代表单击第几个选项
 

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			
				String text = (String) list.getItemAtPosition(arg2);
				switch(arg2)
				{
				case 0: 
				 try{
					Intent intent=new Intent();//这里是意图
				 //  intent.setClass(MainActivity.this,ButtonMActivity.class);
			     intent.setClass(DeviceManagement.this,LightAddActivity.class);
			   
			     startActivity(intent);
				 }catch (Exception e)
				 {
					 
					  
				 }
					break;
				}
				
 			//通过吐丝对象显示出来。
 			Toast.makeText(getApplicationContext(), text,Toast.LENGTH_SHORT).show();
				
			}	
 	}
	 //也就是凡是想要使用服务的类都得写这个类来得到实例
	 public class MyServiceConnection implements ServiceConnection
	 {
	 //当绑定服务成功的时候会调用此方法
	 public void onServiceConnected(ComponentName name, IBinder service)
	 {
	 
     //得到MyService.MyBinder对象，我们通过这个对象来操作服务中的方法
	// myBinder = (MyService.MyBinder) service;
	 //调用服务中的getname()方法并把值设置到TextView上进行显示
	// tv_show.setText(myBinder.getname());
	 }
	 public void onServiceDisconnected(ComponentName name)
	 {
	 }
	 }
  

}
