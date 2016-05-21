package com.example.smart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSend implements Runnable{
     //�Ͼ�Ҳ��һ���డҲ�����г�Ա�����Ͳ���
	
	
	DatagramSocket UdpClient = null;//����һ��UDPsocket
	InetAddress severAddress=null;//��ַ
	int remotePort=8267;
	ShareDate sharedat=null;//������Ӧ����һ�������shake_hand_num
	volatile boolean stopThread= false;
	
	//��ʱֻд�ĸ������Ĺ��캯��
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
		 while(!stopThread)//������ѭ������
			{
			 try {
				//sharedat.sendstring =send +String.valueOf(count); 
				UdpClient.send(sendpacket);
				count++;
				buf= sharedat.sendstring.getBytes();
			    sendpacket=new DatagramPacket(buf,buf.length,severAddress,remotePort);
				try {
					Thread.sleep(150);//��ʹ��100msֻ��1�����յ�3��6����������Ӧ�÷���������100ms������� ����������Ϊ�ֵ�����ʱ��Ƭ�����ࡣ	
                    //Ƶ�ʲ�����̫�ߣ�̫�ߵĻ�8266�ܲ���				
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
		 


