package com.example.smart;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import android.content.Context;

public class FileService {
	
	 private Context context;
	  public FileService(Context context) {
		   this.context = context;
		  }
	
		  
		// TODO Auto-generated constructor stub
	
	public static boolean saveInfo(Context context, String name,String content ) {  
	  File file = new File(context.getFilesDir(), "login.txt");  
      try {  
          //���ļ�д��ע����Ϣ  
          FileOutputStream fos = new FileOutputStream(file);  
          String str = name + "##" + content;  
          fos.write(str.getBytes());  
      } catch (Exception e) {  
          // TODO Auto-generated catch block  
    	  
      
          return false;
          }
      return true;
      
      }  
	  public String getip( String filename) {
	  File file = new File(context.getFilesDir(), "login.txt");  
      //���ļ���ȡ��Ϣ����д��map  
      try {  
          BufferedReader br = new BufferedReader(new InputStreamReader(  
                  new FileInputStream(file)));  
          String text = br.readLine();  
         

        
    return text;

      } catch (Exception e) {  
          // TODO Auto-generated catch block  
          return null;  
      }  
  }  
	  
	 public void save(String filename, String content) throws Exception {
		  
		   /*
		    * 1�����������Ķ����ܿ��ٵõ�һ���ļ����������
		    * 2��˽�в���ģʽ�������������ļ�ֻ�ܱ���Ӧ�÷��ʣ�����Ӧ���޷����ʸ��ļ���Context.MODE_PRIVATE��
		    * �������˽�в���ģʽ�������ļ���д������ݻḲ��ԭ�ļ������ݡ�
		    * 3��openFileOutput()�����ĵ�һ����������ָ���ļ����ƣ����ܰ���·���ָ���"/"������ļ������ڣ�
		    * Android���Զ����������������ļ�������/data/data/<package name>/filesĿ¼����/data/data/org.example.files/files.
		    */
		   FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
		   //���ַ�������Ϊ����������д�뵽�ļ���
		   outStream.write(content.getBytes());
		   //Ȼ��ص������
		   outStream.close();
		  }
	 public String read(String filename) throws Exception
	  {
	   /*
	    * 1���������Ķ����еõ�һ���ļ�����������context.openFileInput(filename)�õ��ļ�����������
	    * 2��
	    */
	   FileInputStream inStream = context.openFileInput(filename);
	   //��ÿ�ζ��������ݶ�������ڴ���
	   ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	   //���������С
	   byte[] buffer = new byte[1024];
	   int len = 0;
	   //��ȡ�������������,�ж������Ƿ����
	   while((len = inStream.read(buffer)) != -1)
	   {
	    outStream.write(buffer,0,len);
	   }
	   //���ڴ��л�ȡ�õ�������
	   byte[] data = outStream.toByteArray();
	   //ת��Ϊ�ַ�
	   return new String(data);
	  }
	 
	 
	 
	 
	 
	 

}
