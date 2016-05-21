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
	//oncreat�����������в�����ֻ������һ��ֻҪ��backstack�����ȥ�˾ͻ��ٴ����� 
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.device_show);//��������Ѿ������˶�����
		application=(DataApplication)getApplicationContext();
		
		
    
    
		
		
		/*LinearLayout ll = (LinearLayout)findViewById(R.id.viewObj);  
	      // ��TextView ���뵽LinearLayout ��  
	      TextView tv = new TextView(this);  
	      tv.setText("Hello World");  
	      ll. addView ( tv );  
	      // ��Button 1 ���뵽LinearLayout ��  
	      Button b1 = new Button(this);  
	      b1.setText("ȡ��");  
	      ll. addView ( b1 );  
	      // ��Button 2 ���뵽LinearLayout ��  
	      Button b2 = new Button(this);  
	      b2.setText("ȷ��");  
	      ll. addView ( b2 );  
	      // ��LinearLayout ���Ƴ�Button 1  
	    //  ll. removeView ( b1 );  
*/		
	      lv = (ListView)findViewById(R.id.listViewshow);/*����һ����̬�����Ⲣ���Ƕ�̬���������б�*/ 
	 //     ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();/*�������д������*/
	     //��������hashmap��ɵġ��ɷ���hashmap 
	    
	      //listItem��Ҫ���ص�����
	      //
	      //��һ������ʱ��Ҫ��������ͼҲ����activity
	      //�ڶ���������һ������ֻҪ��һ��list�Ϳ��������list���б����˼��һ�����ݽṹ����һ�����ArrayList�������ڲ��洢������Map���߼̳���Map�Ķ��󣬱���hashMap�������� ����Ϊ����
	      //Դ������ÿһ��ArrayList�е�һ�оʹ�����ֳ�����һ��map�ļ�������һ�е�������ֵҲ���������ġ�
	      //��������ÿһ����Ҫ��xml�ļ���
	      //���ĸ���string���� ��Ҫ�ǽ�map�����е�����ӳ�䵽������һһ��Ӧ
	      //������ǽ����ĸ�������ֵһһ������ʾ�����������ǹ̶��Ĳ��ù� ����ȷ���ġ�
	       adapter = new SimpleAdapter(this, application.listItem,
	       R.layout.device_item, new String[] { "ItemImage","ItemTitle", "ItemText"},
	       new int[] { R.id.ItemImage,R.id.ItemTitle,R.id.ItemText });
	      
	      
	      lv.setAdapter(adapter);
	      lv.setOnItemClickListener(new 
	    		  OnItemClickListener() {

	    		              @Override
	    		              public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	    		                      long arg3) {
	    		                  
	    		            	  setTitle("�����˵�"+arg2+"��");//���ñ�������ʾ�������                
	    		              }
	    		          });
	    		    
	      findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					try {
					     Intent intent=new Intent();//��������ͼ
					   //  intent.setClass(MainActivity.this,ButtonMActivity.class);
					    // intent.setClass(MainActivity.this,DeviceShow.class);
					     intent.setClass(DeviceShow.this,DeviceManagement.class); 
					     //mainWorkthread.socketClientTh
					 
					     
					  // ����account����  
					     
					        
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
		
		
		lv.setAdapter(adapter);//ֻ��Ҫ�����Ϳ����������Ϳ���ˢ����ͼ��ֻ��Ҫ������д�������ok��
	
	
	
	
	
	}
	
	
	
	

}
