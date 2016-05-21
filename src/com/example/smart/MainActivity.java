package com.example.smart;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.google.gson.Gson;
import com.sun.corba.se.impl.orbutil.graph.Node;
import com.sun.xml.internal.txw2.Document;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.renderscript.Element;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {//���Ȼ�ִ���������

	//@Override
	//��Щ����ʹ����ϵͳ���õ�����ֻ��Ҫд�þͿ�����
	int count=0;
	private TextView receiveTextView;//������ʾ����
	private  Button connectButton;//���Ӱ�ť
	private Button sendButton;//���Ͱ�ť
	private Button deviceManagementButton;//���Ͱ�ť
	private   EditText sendeditText;	
	private   SocketService  socketservice =null;
	Intent socketService=null;
	DMOXmlService domXmlService=new DMOXmlService();
	List<Person> persons=null;
	MyServiceConnection	conn=null;
	Point prev=new Point();
	Gson gson = new Gson();
	 String temp = null; 
	//new Socket("192.168.1.110",5555);
	//���еĳ����ҿ�ʼ����ʹ��ѭ��ȥʵ�֣���֮���java������֮����ʹ���̳߳أ���Ϣ���еȷ�ʽȥʵ�֡�
	public  Handler UiHandler=new MyHandler();//������ʵ���˶�̬����� �������ڶ����ʱ��ʹ�����
	MainWorkthread  mainWorkthread =null;
	Thread mainWorkthreadt=null;
	InputStream is=null;
	//Search_theServerInformation  searchServerInf= new Search_theServerInformation();
	//Thread searchServerInfTh=new Thread(searchServerInf); 
	//
    //����һ���̱߳���ȴ�����һ���̵߳�������Ҳ������
    //��ô�ͱ���õȴ�����һ���߳�����		
	protected void onCreate(Bundle savedInstanceState) {//�����൱����һ�������������������̵߳ķ�������ִ������
    //�����Ǵ���acctivity ����
		Buttonlistener	buttonlistener=new Buttonlistener();
		super.onCreate(savedInstanceState);//supper �������ʾ���ø���ĺ��� �����ǵ����˸���ķ���oncreate
		//���ø���ķ���
		setContentView(R.layout.activity_main);//��������Ѿ������˶�����
	
		//setContentView(R.layout.activity_lightadd);
		receiveTextView=(TextView)findViewById(R.id.receiveTextView);//textView�������ľ��ǲ����ļ�����Ķ���
		sendButton=(Button) findViewById(R.id.sendbutton);
		sendeditText=(EditText)findViewById(R.id.sendeditText);
		connectButton=(Button)findViewById(R.id.connect);
		
		connectButton.setOnClickListener(buttonlistener);
		deviceManagementButton=(Button)findViewById(R.id.devicemanagement);
		
		//������Ҫ��ϵ�����ĵ����Ա����ڷ�������ʱ��Ž����˶���
		//�����ǻ�ȡ����ʹ�õĶ�����Բ���mainactivity ���Ǳ������activity��
		//mainWorkthread=new MainWorkthread(UiHandler,userPreferences);
		//mainWorkthreadt=new Thread(mainWorkthread);
		//mainWorkthreadt.start();//���ÿ����Ҫ���¿�ʼ�Ļ��͵�ÿ�ζ����¸���һ������
		//ʹ��Activity���getSharedPreferences�������SharedPreferences����
		//Ҳ����˵getSharedPreferences
		 socketService = new Intent(MainActivity.this, SocketService.class);
		 conn = new MyServiceConnection();
				// startService(service)ֻ�ܵ����Ŀ���һ������Ҫ����÷�������еķ�����������bindService��unbindService
		//startService(socketService);
	   startService(socketService);
	   this.bindService(socketService, conn, Context.BIND_AUTO_CREATE);
	   String packageName = getPackageName( );
	         
	   
	   
	  
		   //receiveTextView.setText("ni"); 
		   String packetName =this.getPackageName();
		   //��fileName ת����id 
	       /* ��ȡ���Ķ��� InputStream *//* ��⣺ͨ������ */  
         //  InputStream in = getResources().openRawResource(R.layout.li);  

           /* ��ȡ�ļ��Ĵ�С(�ֽ���) */  
           /*int length;
		try {
			length = in.available();
			
			  ����һ��byte���飬 ����װ���ֽ���Ϣ   
	           byte[] buffer = new byte[length];  

	            ��ʼ��ȡ�ļ�read();  ���ͣ�����ȡ�����ֽڷ��뵽buffer���������   
	           in.read(buffer); 
	           temp = EncodingUtils.getString(buffer, "UTF-8");  
	           in.close();  
	          
	          // Toast.makeText(MainActivity.this, temp.toString(), 1000).show(); 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		 try {
			 XmlPullParserFactory	a= XmlPullParserFactory.newInstance();
			 
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} */
           

           /* �����ת���������ͣ�UTF-8 UNICODE BIG5 *//**/  
         
           //temp = EncodingUtils.getString(buffer, "UNICODE");  
           //temp = EncodingUtils.getString(buffer, "BIG5");  
           //temp = EncodingUtils.getString(buffer, "ANST");  


           /* ͨ��String �Ĺ��췽�������ֽ����鹹�����ַ��� */  
           //String temp = new String(buffer);  

           /* ���Ĺر�close(); */ 
		 //��̬���ز����ļ�
		// LayoutInflater inflater = getLayoutInflater();  //����Activity��getLayoutInflater()
	     InputStream inStream=this.getClass().getClassLoader().getResourceAsStream("li.xml");//���ܷ��ڰ�����
	     try {
	    	 int count = inStream.available();
	    	 byte[] buffer = new byte[count];
	    	 
	    	 inStream.read(buffer);
	    	 temp = EncodingUtils.getString(buffer, "UTF-8");//�����ȫ������
	    	 receiveTextView.setText("count"+count);
	    	//  receiveTextView.setText(  temp); 
	    	 
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	     
	     // while (readCount < 10) {
	     //  try {
	    	   
			//readCount += inStream.read(b, readCount, 1000 - readCount);
	//	} //catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
	     
	   
	       
	       
	    //  }
		
		
		
		
	      
	        
		
		
		
		
	
		
		//�����ǵõ�һ������
		  
			    // �����������{}���Ǵ���һ������  
		 
		//String json1= {"name":sam,"age":18,"weight":60};
		//String json2= gson.toJson(json1); 
	//	receiveTextView.setText(json2);
		
		//InputStream json2 = [12,13,15]  ;                           //json2 һ��json����
		// InputStream json3 = [{"name":"sam","age":18},{"name":"leo","age":19},{"name":"sky", "age":20}];

	//
			    JSONObject person = new JSONObject();  
			    JSONArray phone = new JSONArray();  
			    phone.put("12345678").put("87654321");  
			    try {
					person.put("phone", phone);
					person.put("name", "yuanzhifei89");  
					person.put("age", 100); 
					  JSONObject address = new JSONObject();  
					   address.put("country", "china");  
					   address.put("province", "jiangsu");  
					   person.put("address", address);    
					   person.put("married", false);  
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			    
			   
			
		
		
		
		findViewById(R.id.sendbutton).setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {			
				  //   Intent intent=new Intent();//��������ͼ
				   //  intent.setClass(MainActivity.this,ButtonMActivity.class);
				    // intent.setClass(MainActivity.this,DeviceManagement.class);
				    // startActivity(intent);	
					// mainWorkthread.socketClientTh.SecketSend("litongqing");
					socketservice.socketFramework.socketClientTh.SecketSend(temp);
            	} 
            	catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();	
				}	
			}
		});
		findViewById(R.id.devicemanagement).setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
				     Intent intent=new Intent();//��������ͼ
				   //  intent.setClass(MainActivity.this,ButtonMActivity.class);
				    // intent.setClass(MainActivity.this,DeviceShow.class);
				     intent.setClass(MainActivity.this,DeviceShow.class); 
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
		
		
		
		
		
		//setContentView ���������ʾR.layout.activity_main��������ļ��Ķ���
	}
	class MyServiceConnection implements ServiceConnection//����Ҳ�õõ�ʵ��
	 {
	 //���󶨷���ɹ���ʱ�����ô˷���
	 public void onServiceConnected(ComponentName name, IBinder service)
	 {
	 
     //�õ�MyService.MyBinder��������ͨ��������������������еķ���
	  socketservice =((SocketService.MyBinder)service).getService();
	 //���÷����е�getname()��������ֵ���õ�TextView�Ͻ�����ʾ
	
	 //receiveTextView.setText("niyu");
	 }
	 public void onServiceDisconnected(ComponentName name)
	 {
	 }
	 }
	
	
	
	
	class Buttonlistener implements OnClickListener  //���Ӱ�ť��listener 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub		
			try {		 	
				
				//�������try catch��Ȼ�����ֹ������Ϊ������쳣���� ����Բ��������쳣
				
				try{
				//��ȡ��ȡ�ļ�������������
				is=getResources().getAssets().open("configure.xml");
				
				//����dom����
				 persons=domXmlService.parseXML(is);
				//�򵥲���
				//Toast.makeText(XmlActivity.this, ""+persons.get(0).getName(), Toast.LENGTH_LONG).show();
				 receiveTextView.setText(persons.get(0).getName());
				//Toast.makeText(MainActivity.this,"����DOM����"+persons.get(0).getName(), Toast.LENGTH_LONG).show();
				}
				catch(Exception e){
					 receiveTextView.setText(persons.get(0).getName());
				e.printStackTrace();
				}
				
				
				
				
				
				
				
				
				
			}catch (Exception e)//�����������쳣
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				// receiveTextView.setText(socketservice.ni);
			}
			//���ý���һ��UDP�ķ������߳�
			//UDP�����������黹�кܶ� 
			//�������յ�UDP�㲥���Ѿ���һ��ΰ��Ľ�����
			//�������˵ĳ�����Ҫ��ϸ����һ��
		}
		
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)//����activity�Դ��ĺ��� �����������������¼�
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  //����Ƿ��ؼ����˳�
        {  
            // �����˳��Ի���  
            AlertDialog isExit = new AlertDialog.Builder(this).create(); //����һ������ 
            // ���öԻ������  
            isExit.setTitle("ϵͳ��ʾ");  
            // ���öԻ�����Ϣ  
            isExit.setMessage("ȷ��Ҫ�˳���");  //��������
            // ���ѡ��ť��ע�����  
            isExit.setButton("ȷ��", listener);  
            isExit.setButton2("ȡ��", listener);  
            // ��ʾ�Ի���  
            isExit.show();  
  
        }  
          
        return false;  
          
    }  
    /**�����Ի��������button����¼�*/  
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
    {  
        public void onClick(DialogInterface dialog, int which)  
        {  
            switch (which)  
            {  
            case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����  
            	try {
					//mainWorkthread.socketClientTh.getTCPSocket().close();//ֻ��������ʽ�ضϵĲŻᴥ���ضϣ����û�е�����Ϊ�쳣�Ͽ�
					
            		socketservice.socketFramework.socketClientTh.getTCPSocket().close();
            		
					
            	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                finish();  
                
                break;  
            case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���  
                break;  
            default:  
                break;  
            }  
        }

		private void unbindService(SocketService socketservice) {
			// TODO Auto-generated method stub
			
		}  
    };    
	class person1 {
		private String  hello=null; 	
	}
	class MyHandler extends Handler //�ڲ����ʹ����ĳ�̶ֳ���ʵ���˶��ؼ̳� ���������֤
	{
		public void handleMessage(Message msg){
			String s=(String)msg.obj;
			try {
			 //JSONObject person = new JSONObject(s);  
			//String name = person.optString("hello");
				person1 per= gson.fromJson(s, person1.class);
				
				   receiveTextView.setText(per.hello);//���ڽ����ɹ��� ���ùȸ�Ŀ�
			 //  receiveTextView.setText(s);
			  if(s.equals("success"))//֤���Ѿ������Ϸ�������
			  {
				Toast.makeText(getApplicationContext(), "���ӳɹ�",Toast.LENGTH_SHORT).show();
			   }
	     
			  
			  
			} catch(Exception e)//�����������쳣
			{
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//�˷������ڳ�ʼ���˵�������menu�������Ǽ���Ҫ��ʾ��menuʵ������true
		//����true����ʾ��menu��false����ʾֻ���ڵ�һ�γ�ʼ���˵�ʱ����
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		this.unbindService(conn);
		super.onDestroy();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		
			this.stopService(socketService);
			
		super.onStop();
	}
	public boolean onTouchEvent(MotionEvent event) {

	    switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
	      prev.set(event.getX(), event.getY());
	      break;
	    case MotionEvent.ACTION_UP:
	      break;
	    case MotionEvent.ACTION_MOVE:
	      float moveX = prev.x - event.getX();
	   
	      // ��
	      if (moveX > 50 && moveX < 5000) {
	        // mDesignClothesBackground
	        // .setBackgroundResource(idClothesBackground[0]);
	    	  receiveTextView.setText("��");
	    	  
	    	  Intent intent = new Intent();
              intent.setClass(MainActivity.this,DeviceShow.class);
              startActivity(intent);
              //�����л����������ұ߽��룬����˳�
              overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
	      }
	      // �һ�
	      else if (moveX < -150 && moveX > -5000) {
	    	  
	    	  Intent intent = new Intent();
              intent.setClass(MainActivity.this,DeviceShow.class);
              startActivity(intent);
              //�����л����������ұ߽��룬����˳�
             // overridePendingTransition(R.layout.activity_main, R.layout.device_show);
             
              // mDesignClothesBackground
	        // .setBackgroundResource(idClothesBackground[1]);
	      }

	    }

	    return false;
	  }
	class Point 
	{
	float x=0;
	float y=0;
	void set(float x ,float y)
	{
	this.x=x;
	this.y=y;	
	}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
