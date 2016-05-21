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
	Socket tcpClient=null;//线程结束了但是对象和引用还存在 线程是线程对象和引用是对象和引用
	SocketFramework (SharedPreferences sharePreferences)	
	{
	 	
	    this.socketFwSP=sharePreferences;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	
		String address=null;
		address=socketFwSP.getString("IPaddress", null);//这里是返回key为IPaddress的字符串
		editor = socketFwSP.edit();//这里是获取编辑器
		executorService = Executors.newFixedThreadPool(2);
		//主线程进行轮询这里是不能关闭的
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
				if (searchServerInf.getSuccessflag())//如果已经成功的话 
			    {
				editor.putString("IPaddress", searchServerInf.getSeverAddress().toString().substring(1));  
				// 提交修改, 
				editor.commit();
				socketClientTh=new SocketClientTh(searchServerInf.getSeverAddress(),8266);
				//这里并没有创建 socket 所以还是不能返回
				break;
				}
			}
		}
		
		 executorService.execute(socketClientTh);	
		
	}
	

}
