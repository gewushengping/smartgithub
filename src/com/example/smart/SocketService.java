package com.example.smart;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.util.Xml;

public class SocketService extends  Service{

    //他的生命周期还是比较简单的再被绑定时的情况
	private static 	final String TAG = "MyService";
	Socket tcpClient=null;
	String ni="nihao";
	SharedPreferences userPreferences=null;
	InetAddress serverIPAddress=null;
	SocketFramework  socketFramework=null;
	//引用和对象要分开
	@Override
	public IBinder onBind(Intent arg0) {
		
		// TODO Auto-generated method stub
		  return new MyBinder();//通过他来返回一个binder对象
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//Log.v(TAG, "onCreate");
		//上下文对象是可以在service中使用的；
		
		userPreferences=getSharedPreferences("user_Preference", MODE_PRIVATE);
		socketFramework =new SocketFramework(userPreferences);
		Thread socketFrameworkt=new Thread(socketFramework);
		socketFrameworkt.start();//我可以定义一个匿名内部类
		
		
		super.onCreate();
	}

	@Override
	public void onDestroy()//服务停止时调用 程序退出时必须关闭服务，但是退出activity时可以不关闭
	{
		// TODO Auto-generated method stub
		try {
			Xml.newSerializer();
			socketFramework.socketClientTh.getTCPSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socketFramework.socketClientTh.tcpReceiveStopFlag=true;
		socketFramework.executorService.shutdownNow();
		
		
		
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	//onStart  会调用多次 onCreat只会调用一次。
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//Thread socketFrameworkt=new Thread(socketFramework);
		//socketFrameworkt.start();//我可以定义一个匿名内部类
		
		//super.onStart(intent, startId);
	}
	
   
	
class  MyBinder extends Binder {
	
	public String getname()
	{
	return "5";
		// TODO Auto-generated method stub
		
	}
	 public SocketService getService(){  
	        return SocketService.this;//这里是返回一个SocketService实例  	
	    }
	
}
}
