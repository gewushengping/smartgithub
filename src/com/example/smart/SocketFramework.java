package com.example.smart;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;

public class SocketFramework implements Runnable{
	SharedPreferences socketFwSP=null;
	Editor editor=null;
	InetAddress serverIPAddress=null;
	SocketClientTh socketClientTh=null;
	ExecutorService executorService=null;
	Socket tcpClient=null;//�߳̽����˵��Ƕ�������û����� �߳����̶߳���������Ƕ��������
	SocketFramework (SharedPreferences sharePreferences)	
	{
	 	
	    this.socketFwSP=sharePreferences;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		String address=null;
		address=socketFwSP.getString("IPaddress", null);//�����Ƿ���keyΪIPaddress���ַ���
		editor = socketFwSP.edit();//�����ǻ�ȡ�༭��
		executorService = Executors.newFixedThreadPool(2);
		//���߳̽�����ѯ�����ǲ��ܹرյ�
		 try {
			serverIPAddress=InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 try {
			tcpClient=new Socket(serverIPAddress,8266);
			socketClientTh=new SocketClientTh(tcpClient,8266);
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
				socketClientTh=new SocketClientTh(searchServerInf.getSeverAddress(),8266);
				//���ﲢû�д��� socket ���Ի��ǲ��ܷ���
				break;
				}
			}
		}
		
		 executorService.execute(socketClientTh);	
		
	}
	

}
