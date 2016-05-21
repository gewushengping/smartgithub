package com.example.smart;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class Search_theServerInformation implements Runnable {
	  ShareDate shareDat=new ShareDate();//传递进去的是实例，所以同步代码块 是得到对象锁
	  private InetAddress  severAddress=null;//我最终的目的就是要得到这个所以他必须是成员变量啊
	  private int remote_Port=8267;
	  private volatile boolean successFlag=false;//用来标识是否成功获得了IP地址和端口号 这里必须是一个线程更改另外的线程也得更改
	 //这里面理应定义一个hadler来接收UDP接收到的信息
	  UDP_Receive  uDP_Receive=null;
	  UDPSend uDPsend=null;
	  ExecutorService udpService = Executors.newFixedThreadPool(2); 
	  public  boolean getSuccessflag()
	   {
		return successFlag ;
	   }
	  public  InetAddress getSeverAddress()
	   {
		return severAddress ;
	   }
	  public  int getRemote_Port()
	   {
		return remote_Port;
	   }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		DatagramSocket UdpClient = null;
		try{
		//search_Information1=new SearchHandler(); 
		}
		catch(Exception e)
		{	
		}
		
		try {
			UdpClient = new DatagramSocket(8268);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			severAddress =InetAddress.getByName("255.255.255.255") ;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		uDP_Receive= new UDP_Receive(UdpClient, shareDat);
		udpService.execute(uDP_Receive);
		
		
	    //传进去的是实例不是类的名字，传递类的名字没有意义。
		uDPsend=new UDPSend(UdpClient,severAddress, 8267, shareDat);
		//Thread uDP_send=new Thread(uDPsend);
		udpService.execute(uDPsend);
		String receive=null;
		int first=0;
			
	 while(true)
	 {	
	    receive=uDP_Receive.getReceiveResult();//这里是7个数据最终是7-1
		//因为uDP_Receive.getReceiveResult()他肯能不会返回东西所以应该将其放在try里面
		try
		{   receive=receive.substring(0,7);
		
			if (receive.equals("AABBANS")&&(first==0))
			{	
				uDPsend.stopThread=true;//这里你不能中断一个已经死去的线程	
				uDP_Receive.stopFlag=true;
				//uDPSendTh.interrupt();//接收的线程也应该中断掉 因为已经没用了 等下次再完成的时候就完了
				first=1;
			    //之后接收到了所以应该
				//应该得到
				severAddress=uDP_Receive.getRemoteAddress();
				remote_Port= uDP_Receive.getRemote_Port();
				successFlag=true;
				udpService.shutdownNow();//不会中断一个正在执行的线程，该方法调用的是interrupt
				break;
			}	
			
		}catch (Exception e){
         e.printStackTrace();	
		}
	 }	
	}
}
