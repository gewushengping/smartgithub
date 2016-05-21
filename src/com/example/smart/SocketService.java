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

    //�����������ڻ��ǱȽϼ򵥵��ٱ���ʱ�����
	private static 	final String TAG = "MyService";
	Socket tcpClient=null;
	String ni="nihao";
	SharedPreferences userPreferences=null;
	InetAddress serverIPAddress=null;
	SocketFramework  socketFramework=null;
	//���úͶ���Ҫ�ֿ�
	@Override
	public IBinder onBind(Intent arg0) {
		
		// TODO Auto-generated method stub
		  return new MyBinder();//ͨ����������һ��binder����
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//Log.v(TAG, "onCreate");
		//�����Ķ����ǿ�����service��ʹ�õģ�
		
		userPreferences=getSharedPreferences("user_Preference", MODE_PRIVATE);
		socketFramework =new SocketFramework(userPreferences);
		Thread socketFrameworkt=new Thread(socketFramework);
		socketFrameworkt.start();//�ҿ��Զ���һ�������ڲ���
		
		
		super.onCreate();
	}

	@Override
	public void onDestroy()//����ֹͣʱ���� �����˳�ʱ����رշ��񣬵����˳�activityʱ���Բ��ر�
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
	//onStart  ����ö�� onCreatֻ�����һ�Ρ�
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//Thread socketFrameworkt=new Thread(socketFramework);
		//socketFrameworkt.start();//�ҿ��Զ���һ�������ڲ���
		
		//super.onStart(intent, startId);
	}
	
   
	
class  MyBinder extends Binder {
	
	public String getname()
	{
	return "5";
		// TODO Auto-generated method stub
		
	}
	 public SocketService getService(){  
	        return SocketService.this;//�����Ƿ���һ��SocketServiceʵ��  	
	    }
	
}
}
