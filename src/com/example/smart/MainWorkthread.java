package com.example.smart;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.widget.Toast;
//�������߳�Ӧ������һ�����Ϳ�ʼ�����е���ɵ����������ȡUDP IP ֮����TCP���Ӳ�ά���������
public class MainWorkthread implements Runnable
{
	
	//����ֻҪ�������оͻ���ڡ�
	SocketClientTh socketClientTh=null;
	Handler workhander=null;
	SharedPreferences workSharedPreferences=null;
	InetAddress serverIPAddress=null;
	Socket tcpClient=null;
	MainWorkthread ()	
	{
	}
	MainWorkthread (Handler handler)
	{
		this.workhander=handler;
	}
	MainWorkthread (Handler handler, SharedPreferences sharePreferences)	
	{
	    this.workhander=handler;	
	    this.workSharedPreferences=sharePreferences;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		//���п�����˵��ÿ�ζ���ȡ IP��ַ �ȿ���ʱ��˵
		//�������߳�Ӧ���� ��UDP���� ֮�� �õ� ֮�󴴽�TCP 
		String address=null;
		address=workSharedPreferences.getString("IPaddress", null);
		
		
		
		Editor editor = workSharedPreferences.edit();//�����ǻ�ȡ�༭��
		ExecutorService executorService = Executors.newFixedThreadPool(5); 
		//���߳̽�����ѯ�����ǲ��ܹرյ�
		
		
		 try {
			serverIPAddress=InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			tcpClient=new Socket(serverIPAddress,8266);
			socketClientTh=new SocketClientTh(tcpClient,8266,this.workhander);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Search_theServerInformation  searchServerInf= new Search_theServerInformation();
			executorService.execute(searchServerInf);
			while(true)
			{
				if (searchServerInf.getSuccessflag())//����Ѿ��ɹ��Ļ� 
			    {
				editor.putString("IPaddress", searchServerInf.getSeverAddress().toString().substring(1));  
				// �ύ�޸�, 
				editor.commit();
				socketClientTh=new SocketClientTh(searchServerInf.getSeverAddress(),8266,this.workhander);
				
				break;
				}
			}
		}
		
		
		//������Ҫ������
		
		
		//executorService.shutdown();���ｫ��ƽ���Ĺر����е��߳� 
		
		
		//��ʱ�����񻹵���		
		//ʹ��toast��Ϣ��ʾ����ʾ��Ϣ 
		//Toast.makeText(this, "��ȡ�������£�"+"\n"+"name��" + name1 + "\n", 
		
			//	Toast.LENGTH_LONG).show(); 
		
		executorService.execute(socketClientTh);//�ڶ����߳�
	
	
	
	
	}

}
