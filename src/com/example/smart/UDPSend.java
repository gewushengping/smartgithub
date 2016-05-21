package com.example.smart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSend implements Runnable{
     //毕竟也是一个类啊也可以有成员变量和参数
	
	
	DatagramSocket UdpClient = null;//定义一个UDPsocket
	InetAddress severAddress=null;//地址
	int remotePort=8267;
	ShareDate sharedat=null;//这里面应该有一个数组和shake_hand_num
	volatile boolean stopThread= false;
	
	//暂时只写四个参数的构造函数
	UDPSend(DatagramSocket UdpClient ,InetAddress severAddress,int remotePort,ShareDate sharedat)
	{
	this.UdpClient=UdpClient;
	this.severAddress=severAddress;
	this.remotePort=remotePort;
	this.sharedat =sharedat;
	}
	UDPSend(DatagramSocket UdpClient ,InetAddress severAddress,int remotePort,String sharedat)
	{
	this.UdpClient=UdpClient;
	this.severAddress=severAddress;
	this.remotePort=remotePort;
	}
	UDPSend(DatagramSocket UdpClient ,InetAddress severAddress,int remotePort)
	{
	this.UdpClient=UdpClient;
	this.severAddress=severAddress;
	this.remotePort=remotePort;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
         int count=0;
		 DatagramPacket sendpacket=null;
		 byte buf[]=this.sharedat.sendstring.getBytes();
		 sendpacket=new DatagramPacket(buf,buf.length,severAddress,remotePort);
		 String send=null;
		 send=sharedat.sendstring;
		 while(!stopThread)//这里是循环发送
			{
			 try {
				//sharedat.sendstring =send +String.valueOf(count); 
				UdpClient.send(sendpacket);
				count++;
				buf= sharedat.sendstring.getBytes();
			    sendpacket=new DatagramPacket(buf,buf.length,severAddress,remotePort);
				try {
					Thread.sleep(150);//即使是100ms只是1秒钟收到3到6次所以这里应该发送周期在100ms还算可以 甚至更少因为轮到他的时间片并不多。	
                    //频率不可以太高，太高的话8266受不了				
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 
			 
		 }
		 
	} 
}
		 


