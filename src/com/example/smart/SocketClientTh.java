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
//�����������SOCKET�̱߳���ȴ���һ���߳���ϲ���

public class SocketClientTh implements Runnable{
	//Serializable��ʵ�֣�ֻ��Ҫimplements  Serializable ���ɡ���ֻ�Ǹ��������һ����ǣ�ϵͳ���Զ��������л�
	 BufferedWriter  socketClientwriter=null;  
	 BufferedReader  socketClientreader=null;
	 Socket socketClient=null; 
	 volatile boolean  tcpReceiveStopFlag=false;
	 InetAddress  severAddress=null;
	 InetAddress  severAddress1=null;
	 Handler sendSocketMessage=null;
	 int remotePort=8266;
	 Message msg=null;
	 //Ӧ����Ƴɿ��Դ��ݽ���Socket���ǲ��ܵ��ù��õģ�
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
			//�����൱��ָ��ֻ��һ������ָ���ֵȴ�����˱仯��
			//UDP��8267 TCP��8266
			//����������ԵĻ��ǲ��е��ⷽ��ļܹ�����Ҫ�������    			    	  
			if(socketClient==null)     
			socketClient=new Socket(severAddress,8266);//������ʱ����ڽ�������	������и����쳣
			       
			//��socket�����Ӧ�����������
			      
				  socketClientwriter=new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream()));
			      //�����漰��������socketClient.getOutputStream�����Ƿ���������socket������ֽ�������
			      //OutputStreamWriter�����ǽ��ֽ������ת�� Ϊ�ַ������ Ҳ���ǽ�socketClient.getOutputStreamת��Ϊ�ַ���
			      //����о���Ӧ�õ��ù��еĶ���Ӧ�ô����Լ��Ķ���
				  socketClientreader=new BufferedReader(new InputStreamReader(socketClient.getInputStream()));			      
			//Toast.makeText(MainActivity.this, "���ӳɹ�",Toast.LENGTH_SHORT).show();
		        //  publishProgress("@success");
				try{
					Message msg=this.sendSocketMessage.obtainMessage();
				    msg.obj= severAddress.toString();   
				    sendSocketMessage.sendMessage(msg);
				  }catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
		    //������ֻҪ���ӳɹ�������ȥ�˵������ﲻӦ������������Ӧ��ʹ�ö�ʱѭ������
			     
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			//Toast.makeText(MainActivity.this, "�޷���������",Toast.LENGTH_SHORT).show();//�����ѵ�������UI�̲߳������
		
			  Message msg=this.sendSocketMessage.obtainMessage();
			  msg.obj="faile";
			  sendSocketMessage.sendMessage(msg);
			
			
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
		//	Toast.makeText(MainActivity.this, "�޷���������",Toast.LENGTH_SHORT).show();
			e1.printStackTrace();
			Message msg=this.sendSocketMessage.obtainMessage();
			msg.obj="faile";
			sendSocketMessage.sendMessage(msg);
		}
		//�������첽����Ϊsocket�Ǳ�֤���ӵ����� ��������֮�����˿϶��ǿ��Խ��յ���������������߳� ����ѹ������Ҫ�߳�
	
		try {
		
			while((line= socketClientreader.readLine())!=null&&tcpReceiveStopFlag!=false)//������һֱѭ����ȡ�������һֱ���ж�
			{  //�������������������Ƕ���û�������˾���null ���ǶϿ�����֮�����null�������Ϳ���һֱ��ȡ�ˡ�
				//ÿһ�����з�����׶�һ�� ������������Ǻ�
				
				try{
					 Message msg=this.sendSocketMessage.obtainMessage();//����ֻ���þֲ����� ȫ�ֱ�����������
					  msg.obj=line;//
					  sendSocketMessage.sendMessage(msg);
		            }catch (Exception e) {
							// TODO Auto-generated catch block
			   e.printStackTrace();
			}	
				
				
				//publishProgress(line);//�����ǽ����������ݸ��µ���ʾ�߳�
				//line���Ǵ�������onProgressUpdate�Ĳ���
				//publishProgress�ǿ��̵߳�    
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 	
	
	public void SecketSend( String cmd)
	{
		 try {
			 //Toast.makeText(MainActivity.this, "���ӳɹ�",Toast.LENGTH_SHORT).show();
			
	
			 socketClientwriter.write(cmd+"\n");
			socketClientwriter.flush();
			//�ǵ÷��͹�ȥ��ʱ��Ҫ����flush()ˢ�·����Ǳ߽��ղ���
		} catch (Exception e)//�����������쳣
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
		
	
	
	
	}
	
	
	
	
	
	


