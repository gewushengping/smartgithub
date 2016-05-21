package com.example.smart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.Handler;
import android.os.Message;

public class UDP_Receive implements Runnable {
	DatagramSocket UdpClient=null;
	ShareDate sharedat=null;
	private InetAddress remoteAddress;
	private int remote_Port;
	String receiveResult=null;
	Handler sendReceiveResultH;
	boolean stopFlag=false;
	
	UDP_Receive(DatagramSocket UdpClient ,ShareDate sharedat)
	{
	this.UdpClient=UdpClient;
	this.sharedat =sharedat;
	}
	UDP_Receive(DatagramSocket UdpClient ,ShareDate sharedat,Handler handler)
	{
	this(UdpClient,sharedat);
	this.sendReceiveResultH=handler;
	}
	public  String getReceiveResult()
	{
		return receiveResult;
	}
	public  InetAddress getRemoteAddress()
	{
		return remoteAddress;
	}
	public int getRemote_Port()
	{
		return remote_Port;	
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte recbuf[]=new byte[1024];//最多接收一个线程
		//接收已经是阻塞线程了再也无法容纳发送线程了
        DatagramPacket receivepacket=new DatagramPacket(recbuf,recbuf.length);	
        while(!stopFlag)
		{
		try {
			UdpClient.receive(receivepacket);//这里确实是阻塞了
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		receiveResult=new String(receivepacket.getData(),receivepacket.getOffset(),receivepacket.getLength());
		remoteAddress=receivepacket.getAddress(); //得到与之通信的IP地址而不是使用UDPsocket的getAddress()
		remote_Port=receivepacket.getPort();//得到服务器端的端口号	
		try {
			Message msg=sendReceiveResultH.obtainMessage();//这里是获取一个消息对象当用户点击按钮时
			msg.obj=receiveResult;	
			//MainActivity.UiHandler.sendMessage(msg);//sendMessage
			sendReceiveResultH.sendMessage(msg);//sendMessage 
			//因为可能不传入Handler所以这里要放入try catch结构
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		
		
		
		}
	}

	
	
	
	

}
