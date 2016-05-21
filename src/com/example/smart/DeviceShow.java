package com.example.smart;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64InputStream;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class DeviceShow extends Activity {
	 private ListView lv=null;
	 SimpleAdapter adapter=null;
	 private DataApplication application; 
	 private Map<String,String> passwords= new HashMap<String,String>();
	@Override
	//oncreat在整个程序中并不是只是运行一次只要在backstack中提出去了就会再次运行 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_show);//在这里就已经生成了对象了
		application=(DataApplication)getApplicationContext();
		
		
    
    
		
		
		/*LinearLayout ll = (LinearLayout)findViewById(R.id.viewObj);  
	      // 将TextView 加入到LinearLayout 中  
	      TextView tv = new TextView(this);  
	      tv.setText("Hello World");  
	      ll. addView ( tv );  
	      // 将Button 1 加入到LinearLayout 中  
	      Button b1 = new Button(this);  
	      b1.setText("取消");  
	      ll. addView ( b1 );  
	      // 将Button 2 加入到LinearLayout 中  
	      Button b2 = new Button(this);  
	      b2.setText("确定");  
	      ll. addView ( b2 );  
	      // 从LinearLayout 中移除Button 1  
	    //  ll. removeView ( b1 );  
*/		
	      lv = (ListView)findViewById(R.id.listViewshow);/*定义一个动态数组这并不是动态数组这是列表*/ 
	 //     ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();/*在数组中存放数据*/
	     //数组是由hashmap组成的。成分是hashmap 
	    
	      //listItem是要加载的数据
	      //
	      //第一个参数时所要关联的视图也就是activity
	      //第二个参数是一个泛型只要是一个list就可以这里的list是列表的意思是一种数据结构，这一般会是ArrayList，而他内部存储的则是Map或者继承自Map的对象，比如hashMap，这里呢 是作为数据
	      //源，而且每一个ArrayList中的一行就代表呈现出来的一行map的键就是这一行的列名，值也是有列名的。
	      //第三个是每一行需要的xml文件。
	      //第四个是string数组 主要是将map对象中的名称映射到列名，一一对应
	      //第五个是将第四个参数的值一一对象显示，下面这里是固定的不用管 上面确定的。
	       adapter = new SimpleAdapter(this, application.listItem,
	       R.layout.device_item, new String[] { "ItemImage","ItemTitle", "ItemText"},
	       new int[] { R.id.ItemImage,R.id.ItemTitle,R.id.ItemText });
	      
	      
	      lv.setAdapter(adapter);
	      lv.setOnItemClickListener(new 
	    		  OnItemClickListener() {

	    		              @Override
	    		              public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    		                      long arg3) {
	    		                  
	    		            	  setTitle("你点击了第"+arg2+"行");//设置标题栏显示点击的行                
	    		              }
	    		          });
	    		    
	      findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
					     Intent intent=new Intent();//这里是意图
					   //  intent.setClass(MainActivity.this,ButtonMActivity.class);
					    // intent.setClass(MainActivity.this,DeviceShow.class);
					     intent.setClass(DeviceShow.this,DeviceManagement.class); 
					     //mainWorkthread.socketClientTh
					 
					     
					  // 放入account对象  
					     
					        
					      startActivity(intent);	
						// mainWorkthread.socketClientTh.SecketSend("litongqing");
	          	} 
	          	catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
							
					}
						
				}
			});
		
		
		
		
		
		
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		SharedPreferences passwd = getPreferences(Activity.MODE_PRIVATE);  
		SharedPreferences.Editor editor = passwd.edit();  
		ByteArrayOutputStream toByte = new ByteArrayOutputStream();  
		ObjectOutputStream oos = null;  
		  try {
			oos=new ObjectOutputStream(toByte);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(oos!=null)
		{
		try {
			oos.writeObject(application.listItem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}
	
		
		//BASE64Encoder().encode();
  // String PasswordMapBase64=new String(Base64.encodeBase64(toByte.toByteArray(), Base64.DEFAULT));
  
   //editor.putString("KEY",PasswordMapBase64);
   // editor.commit();
    
    
    //SharedPreferences prefer = getPreferences(Activity.MODE_PRIVATE);  
   /* String passwordinbase64 = prefer.getString("KEY", null);  
    if(passwordinbase64 != null)  
    {  
        byte[] base64Bytes = Base64.decode(passwordinbase64,Base64.DEFAULT);    
        ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);    
        ObjectInputStream ois = null;  
        try {  
            ois = new ObjectInputStream(bais);  
        } catch (StreamCorruptedException e) {  
            // TODO Auto-generated catch block  
          
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
           
        }    
        if(ois != null)  
        {  
            try {  
            	application.listItem= ( ArrayList<HashMap<String, Object>>) ois.readObject();  
            } catch (OptionalDataException e) {  
                // TODO Auto-generated catch block  
               
            } catch (ClassNotFoundException e) {  
                // TODO Auto-generated catch block  
             
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                 
            }   
        }  
    }  
		*/
		
		
		lv.setAdapter(adapter);//只需要这样就可以了这样就可以刷新视图了只需要在这里写上这个就ok；
	
	
	
	
	
	}
	
	
	
	

}
