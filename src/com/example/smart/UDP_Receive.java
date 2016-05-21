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
		byte recbuf[]=new byte[1024];//������һ���߳�
		//�����Ѿ��������߳�����Ҳ�޷����ɷ����߳���
        DatagramPacket receivepacket=new DatagramPacket(recbuf,recbuf.length);	
        while(!stopFlag)
		{
		try {
			UdpClient.receive(receivepacket);//����ȷʵ��������
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		receiveResult=new String(receivepacket.getData(),receivepacket.getOffset(),receivepacket.getLength());
		remoteAddress=receivepacket.getAddress(); //�õ���֮ͨ�ŵ�IP��ַ������ʹ��UDPsocket��getAddress()
		remote_Port=receivepacket.getPort();//�õ��������˵Ķ˿ں�	
		try {
			Message msg=sendReceiveResultH.obtainMessage();//�����ǻ�ȡһ����Ϣ�����û������ťʱ
			msg.obj=receiveResult;	
			//MainActivity.UiHandler.sendMessage(msg);//sendMessage
			sendReceiveResultH.sendMessage(msg);//sendMessage 
			//��Ϊ���ܲ�����Handler��������Ҫ����try catch�ṹ
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//
		
		
		
		}
	}

	
	
	
	

}
