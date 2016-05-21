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
	  ShareDate shareDat=new ShareDate();//���ݽ�ȥ����ʵ��������ͬ������� �ǵõ�������
	  private InetAddress  severAddress=null;//�����յ�Ŀ�ľ���Ҫ�õ���������������ǳ�Ա������
	  private int remote_Port=8267;
	  private volatile boolean successFlag=false;//������ʶ�Ƿ�ɹ������IP��ַ�Ͷ˿ں� ���������һ���̸߳���������߳�Ҳ�ø���
	 //��������Ӧ����һ��hadler������UDP���յ�����Ϣ
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
		
		
	    //����ȥ����ʵ������������֣������������û�����塣
		uDPsend=new UDPSend(UdpClient,severAddress, 8267, shareDat);
		//Thread uDP_send=new Thread(uDPsend);
		udpService.execute(uDPsend);
		String receive=null;
		int first=0;
			
	 while(true)
	 {	
	    receive=uDP_Receive.getReceiveResult();//������7������������7-1
		//��ΪuDP_Receive.getReceiveResult()�����ܲ��᷵�ض�������Ӧ�ý������try����
		try
		{   receive=receive.substring(0,7);
		
			if (receive.equals("AABBANS")&&(first==0))
			{	
				uDPsend.stopThread=true;//�����㲻���ж�һ���Ѿ���ȥ���߳�	
				uDP_Receive.stopFlag=true;
				//uDPSendTh.interrupt();//���յ��߳�ҲӦ���жϵ� ��Ϊ�Ѿ�û���� ���´�����ɵ�ʱ�������
				first=1;
			    //֮����յ�������Ӧ��
				//Ӧ�õõ�
				severAddress=uDP_Receive.getRemoteAddress();
				remote_Port= uDP_Receive.getRemote_Port();
				successFlag=true;
				udpService.shutdownNow();//�����ж�һ������ִ�е��̣߳��÷������õ���interrupt
				break;
			}	
			
		}catch (Exception e){
         e.printStackTrace();	
		}
	 }	
	}
}
