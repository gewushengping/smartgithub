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
//主工作线程应该是在一开机就开始的运行的完成的任务包括获取UDP IP 之后建立TCP连接并维护这个连接
public class MainWorkthread implements Runnable
{
	
	//对象只要还在运行就会存在。
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
		//等有空了再说先每次都获取 IP地址 等开会时再说
		//主工作线程应该是 线UDP开启 之后 得到 之后创建TCP 
		String address=null;
		address=workSharedPreferences.getString("IPaddress", null);
		
		
		
		Editor editor = workSharedPreferences.edit();//这里是获取编辑器
		ExecutorService executorService = Executors.newFixedThreadPool(5); 
		//主线程进行轮询这里是不能关闭的
		
		
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
				if (searchServerInf.getSuccessflag())//如果已经成功的话 
			    {
				editor.putString("IPaddress", searchServerInf.getSeverAddress().toString().substring(1));  
				// 提交修改, 
				editor.commit();
				socketClientTh=new SocketClientTh(searchServerInf.getSeverAddress(),8266,this.workhander);
				
				break;
				}
			}
		}
		
		
		//对象还是要创建的
		
		
		//executorService.shutdown();这里将会平缓的关闭所有的线程 
		
		
		//定时器任务还得有		
		//使用toast信息提示框显示信息 
		//Toast.makeText(this, "读取数据如下："+"\n"+"name：" + name1 + "\n", 
		
			//	Toast.LENGTH_LONG).show(); 
		
		executorService.execute(socketClientTh);//第二个线程
	
	
	
	
	}

}
