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


public class MainActivity extends Activity {//首先会执行这个方法

	//@Override
	//这些方法使操作系统调用的我们只需要写好就可以了
	int count=0;
	private TextView receiveTextView;//接收显示窗口
	private  Button connectButton;//连接按钮
	private Button sendButton;//发送按钮
	private Button deviceManagementButton;//发送按钮
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
	//所有的程序我开始尽量使用循环去实现，等之后对java更熟练之后再使用线程池，消息队列等方式去实现。
	public  Handler UiHandler=new MyHandler();//这里是实现了多态处理的 这里是在定义的时候就创建了
	MainWorkthread  mainWorkthread =null;
	Thread mainWorkthreadt=null;
	InputStream is=null;
	//Search_theServerInformation  searchServerInf= new Search_theServerInformation();
	//Thread searchServerInfTh=new Thread(searchServerInf); 
	//
    //其中一个线程必须等待另外一个线程的数据这也是醉了
    //那么就必须得等待上有一个线程完事		
	protected void onCreate(Bundle savedInstanceState) {//这里相当于是一个方法但是这里是主线程的方法最先执行这里
    //这里是创建acctivity 对象
		Buttonlistener	buttonlistener=new Buttonlistener();
		super.onCreate(savedInstanceState);//supper 在这里表示调用父类的函数 这里是调用了父类的方法oncreate
		//调用父类的方法
		setContentView(R.layout.activity_main);//在这里就已经生成了对象了
	
		//setContentView(R.layout.activity_lightadd);
		receiveTextView=(TextView)findViewById(R.id.receiveTextView);//textView这里代表的就是布局文件里面的对象
		sendButton=(Button) findViewById(R.id.sendbutton);
		sendeditText=(EditText)findViewById(R.id.sendeditText);
		connectButton=(Button)findViewById(R.id.connect);
		
		connectButton.setOnClickListener(buttonlistener);
		deviceManagementButton=(Button)findViewById(R.id.devicemanagement);
		
		//这里是要联系上下文的所以必须在方法中这时候才建立了对象
		//这里是获取保存使用的对象可以不再mainactivity 但是必须得在activity中
		//mainWorkthread=new MainWorkthread(UiHandler,userPreferences);
		//mainWorkthreadt=new Thread(mainWorkthread);
		//mainWorkthreadt.start();//如果每次想要重新开始的话就得每次都重新赋予一个对象
		//使用Activity类的getSharedPreferences方法获得SharedPreferences对象
		//也就是说getSharedPreferences
		 socketService = new Intent(MainActivity.this, SocketService.class);
		 conn = new MyServiceConnection();
				// startService(service)只能单纯的开启一个服务，要想调用服务服务中的方法，必须用bindService和unbindService
		//startService(socketService);
	   startService(socketService);
	   this.bindService(socketService, conn, Context.BIND_AUTO_CREATE);
	   String packageName = getPackageName( );
	         
	   
	   
	  
		   //receiveTextView.setText("ni"); 
		   String packetName =this.getPackageName();
		   //将fileName 转换成id 
	       /* 获取流的对象 InputStream *//* 理解：通道建立 */  
         //  InputStream in = getResources().openRawResource(R.layout.li);  

           /* 获取文件的大小(字节数) */  
           /*int length;
		try {
			length = in.available();
			
			  创建一个byte数组， 用于装载字节信息   
	           byte[] buffer = new byte[length];  

	            开始读取文件read();  解释：将读取到的字节放入到buffer这个数组中   
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
           

           /* 编码的转换三种类型：UTF-8 UNICODE BIG5 *//**/  
         
           //temp = EncodingUtils.getString(buffer, "UNICODE");  
           //temp = EncodingUtils.getString(buffer, "BIG5");  
           //temp = EncodingUtils.getString(buffer, "ANST");  


           /* 通过String 的构造方法，将字节数组构建成字符串 */  
           //String temp = new String(buffer);  

           /* 流的关闭close(); */ 
		 //动态加载布局文件
		// LayoutInflater inflater = getLayoutInflater();  //调用Activity的getLayoutInflater()
	     InputStream inStream=this.getClass().getClassLoader().getResourceAsStream("li.xml");//不能放在包里面
	     try {
	    	 int count = inStream.available();
	    	 byte[] buffer = new byte[count];
	    	 
	    	 inStream.read(buffer);
	    	 temp = EncodingUtils.getString(buffer, "UTF-8");//这次完全读对了
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
		
		
		
		
	      
	        
		
		
		
		
	
		
		//这里是得到一个对象
		  
			    // 首先最外层是{}，是创建一个对象  
		 
		//String json1= {"name":sam,"age":18,"weight":60};
		//String json2= gson.toJson(json1); 
	//	receiveTextView.setText(json2);
		
		//InputStream json2 = [12,13,15]  ;                           //json2 一个json数组
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
				  //   Intent intent=new Intent();//这里是意图
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
				     Intent intent=new Intent();//这里是意图
				   //  intent.setClass(MainActivity.this,ButtonMActivity.class);
				    // intent.setClass(MainActivity.this,DeviceShow.class);
				     intent.setClass(MainActivity.this,DeviceShow.class); 
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
		
		
		
		
		
		//setContentView 这个方法显示R.layout.activity_main这个布局文件的东西
	}
	class MyServiceConnection implements ServiceConnection//这里也得得到实例
	 {
	 //当绑定服务成功的时候会调用此方法
	 public void onServiceConnected(ComponentName name, IBinder service)
	 {
	 
     //得到MyService.MyBinder对象，我们通过这个对象来操作服务中的方法
	  socketservice =((SocketService.MyBinder)service).getService();
	 //调用服务中的getname()方法并把值设置到TextView上进行显示
	
	 //receiveTextView.setText("niyu");
	 }
	 public void onServiceDisconnected(ComponentName name)
	 {
	 }
	 }
	
	
	
	
	class Buttonlistener implements OnClickListener  //连接按钮的listener 
	{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub		
			try {		 	
				
				//如果加了try catch仍然软件终止那是因为捕获的异常不对 你可以捕获所有异常
				
				try{
				//获取读取文件的输入流对象
				is=getResources().getAssets().open("configure.xml");
				
				//采用dom解析
				 persons=domXmlService.parseXML(is);
				//简单测试
				//Toast.makeText(XmlActivity.this, ""+persons.get(0).getName(), Toast.LENGTH_LONG).show();
				 receiveTextView.setText(persons.get(0).getName());
				//Toast.makeText(MainActivity.this,"采用DOM解析"+persons.get(0).getName(), Toast.LENGTH_LONG).show();
				}
				catch(Exception e){
					 receiveTextView.setText(persons.get(0).getName());
				e.printStackTrace();
				}
				
				
				
				
				
				
				
				
				
			}catch (Exception e)//这样是所有异常
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
				// receiveTextView.setText(socketservice.ni);
			}
			//还得建立一个UDP的服务器线程
			//UDP可以做的事情还有很多 
			//但是能收到UDP广播包已经是一次伟大的进步了
			//服务器端的程序还需要仔细打量一下
		}
		
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)//这是activity自带的函数 可以永凯监听键盘事件
    {  
        if (keyCode == KeyEvent.KEYCODE_BACK )  //如果是返回键则退出
        {  
            // 创建退出对话框  
            AlertDialog isExit = new AlertDialog.Builder(this).create(); //生成一个对象 
            // 设置对话框标题  
            isExit.setTitle("系统提示");  
            // 设置对话框消息  
            isExit.setMessage("确定要退出吗");  //先这样吧
            // 添加选择按钮并注册监听  
            isExit.setButton("确定", listener);  
            isExit.setButton2("取消", listener);  
            // 显示对话框  
            isExit.show();  
  
        }  
          
        return false;  
          
    }  
    /**监听对话框里面的button点击事件*/  
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
    {  
        public void onClick(DialogInterface dialog, int which)  
        {  
            switch (which)  
            {  
            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
            	try {
					//mainWorkthread.socketClientTh.getTCPSocket().close();//只有这样正式关断的才会触发关断，如果没有的则视为异常断开
					
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
            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
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
	class MyHandler extends Handler //内部类的使用在某种程度上实现了多重继承 这里就是明证
	{
		public void handleMessage(Message msg){
			String s=(String)msg.obj;
			try {
			 //JSONObject person = new JSONObject(s);  
			//String name = person.optString("hello");
				person1 per= gson.fromJson(s, person1.class);
				
				   receiveTextView.setText(per.hello);//终于解析成功了 就用谷歌的库
			 //  receiveTextView.setText(s);
			  if(s.equals("success"))//证明已经连接上服务器了
			  {
				Toast.makeText(getApplicationContext(), "连接成功",Toast.LENGTH_SHORT).show();
			   }
	     
			  
			  
			} catch(Exception e)//这样是所有异常
			{
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//此方法用于初始化菜单，其中menu参数就是即将要显示的menu实例返回true
		//返回true则显示该menu，false则不显示只会在第一次初始化菜单时调用
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
	   
	      // 左滑
	      if (moveX > 50 && moveX < 5000) {
	        // mDesignClothesBackground
	        // .setBackgroundResource(idClothesBackground[0]);
	    	  receiveTextView.setText("左滑");
	    	  
	    	  Intent intent = new Intent();
              intent.setClass(MainActivity.this,DeviceShow.class);
              startActivity(intent);
              //设置切换动画，从右边进入，左边退出
              overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left); 
	      }
	      // 右滑
	      else if (moveX < -150 && moveX > -5000) {
	    	  
	    	  Intent intent = new Intent();
              intent.setClass(MainActivity.this,DeviceShow.class);
              startActivity(intent);
              //设置切换动画，从右边进入，左边退出
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
