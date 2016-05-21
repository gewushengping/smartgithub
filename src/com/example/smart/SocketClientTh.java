package com.example.smart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
//在这个场景下SOCKET线程必须等待上一个线程完毕才行

public class SocketClientTh implements Runnable{
	//Serializable的实现，只需要implements  Serializable 即可。这只是给对象打了一个标记，系统会自动将其序列化
	 BufferedWriter  socketClientwriter=null;  
	 BufferedReader  socketClientreader=null;
	 Socket socketClient=null; 
	 volatile boolean  tcpReceiveStopFlag=false;
	 InetAddress  severAddress=null;
	 InetAddress  severAddress1=null;
	 Handler sendSocketMessage=null;
	 int remotePort=8266;
	 Message msg=null;
	 //应该设计成可以传递进来Socket但是不能调用公用的，
	 SocketClientTh(InetAddress severAddress,int remotePort)
		{
		this.severAddress=severAddress;
		this.remotePort=remotePort;
		}
	 
	 SocketClientTh( Socket socketClient,int remotePort,Handler sendSocketMessage)
		{
		this.socketClient=socketClient;
		this.remotePort=remotePort;
		this.sendSocketMessage=sendSocketMessage;
		}
	 SocketClientTh(InetAddress severAddress,int remotePort,Handler sendSocketMessage)
		{
          this(severAddress,remotePort);
          this.sendSocketMessage=sendSocketMessage;
		}

	 SocketClientTh(Socket socketClient,int remotePort)
		{
			this.socketClient=socketClient;
			this.remotePort=remotePort;
		}
	 
	 Socket getTCPSocket ()
	 {		return socketClient;
	 }
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String line;
		String sendm=null;
		try {
			//这里相当于指针只有一个但是指向的值却发生了变化了
			//UDP是8267 TCP是8266
			//但是如果不对的话是不行的这方面的架构还需要重新设计    			    	  
			if(socketClient==null)     
			socketClient=new Socket(severAddress,8266);//创建的时候就在进行连接	这里会有各种异常
			       
			//从socket获得相应的输入输出流
			      
				  socketClientwriter=new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
			      //这里涉及了三个类socketClient.getOutputStream这里是返回针对这个socket的输出字节流对象，
			      //OutputStreamWriter这里是将字节输出流转换 为字符输出流 也就是将socketClient.getOutputStream转换为字符流
			      //这里感觉不应该调用公有的对象应该创建自己的对象
				  socketClientreader=new BufferedReader(new InputStreamReader(socketClient.getInputStream()));			      
			//Toast.makeText(MainActivity.this, "连接成功",Toast.LENGTH_SHORT).show();
		        //  publishProgress("@success");
				try{
					Message msg=this.sendSocketMessage.obtainMessage();
				    msg.obj= severAddress.toString();   
				    sendSocketMessage.sendMessage(msg);
				  }catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
		    //这里是只要连接成功就跳出去了但是这里不应该是阻塞方法应该使用定时循环简则
			     
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			//Toast.makeText(MainActivity.this, "无法建立连接",Toast.LENGTH_SHORT).show();//这里难道会跳入UI线程不可理解
		
			  Message msg=this.sendSocketMessage.obtainMessage();
			  msg.obj="faile";
			  sendSocketMessage.sendMessage(msg);
			
			
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		//	Toast.makeText(MainActivity.this, "无法建立连接",Toast.LENGTH_SHORT).show();
			e1.printStackTrace();
			Message msg=this.sendSocketMessage.obtainMessage();
			msg.obj="faile";
			sendSocketMessage.sendMessage(msg);
		}
		//这里是异步的因为socket是保证连接的所以 发送完了之后服务端肯定是可以接收到的这里读是阻塞线程 创建压根不需要线程
	
		try {
		
			while((line= socketClientreader.readLine())!=null&&tcpReceiveStopFlag!=false)//这里是一直循环读取的这里会一直进行读
			{  //这里是阻塞方法并不是读到没有数据了就是null 而是断开连接之后才是null；这样就可以一直读取了。
				//每一个换行符都会阶段一次 他妈的这可如何是好
				
				try{
					 Message msg=this.sendSocketMessage.obtainMessage();//这里只能用局部变量 全局变量反而不好
					  msg.obj=line;//
					  sendSocketMessage.sendMessage(msg);
		            }catch (Exception e) {
							// TODO Auto-generated catch block
			   e.printStackTrace();
			}	
				
				
				//publishProgress(line);//这里是将读到的数据更新的显示线程
				//line就是传入下面onProgressUpdate的参数
				//publishProgress是跨线程的    
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 	
	
	public void SecketSend( String cmd)
	{
		 try {
			 //Toast.makeText(MainActivity.this, "连接成功",Toast.LENGTH_SHORT).show();
			
	
			 socketClientwriter.write(cmd+"\n");
			socketClientwriter.flush();
			//记得发送过去的时候要调用flush()刷新否则那边接收不到
		} catch (Exception e)//这样是所有异常
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
		
	
	
	
	}
	
	
	
	
	
	


