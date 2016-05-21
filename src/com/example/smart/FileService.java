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
          //向文件写入注册信息  
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
      //由文件读取信息，并写入map  
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
		    * 1、根据上下文对象能快速得到一个文件输出流对象；
		    * 2、私有操作模式：创建出来的文件只能被本应用访问，其他应用无法访问该文件：Context.MODE_PRIVATE；
		    * 另外采用私有操作模式创建的文件，写入的内容会覆盖原文件的内容。
		    * 3、openFileOutput()方法的第一个参数用于指定文件名称，不能包含路径分隔符"/"，如果文件不存在，
		    * Android会自动创建它，创建的文件保存在/data/data/<package name>/files目录，如/data/data/org.example.files/files.
		    */
		   FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
		   //把字符串传化为二进制数据写入到文件中
		   outStream.write(content.getBytes());
		   //然后关掉这个流
		   outStream.close();
		  }
	 public String read(String filename) throws Exception
	  {
	   /*
	    * 1、从上下文对象中得到一个文件输入流对像，context.openFileInput(filename)得到文件输入流对象；
	    * 2、
	    */
	   FileInputStream inStream = context.openFileInput(filename);
	   //把每次读到的数据都存放在内存中
	   ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	   //定义数组大小
	   byte[] buffer = new byte[1024];
	   int len = 0;
	   //读取这个输入流数组,判断数据是否读完
	   while((len = inStream.read(buffer)) != -1)
	   {
	    outStream.write(buffer,0,len);
	   }
	   //从内存中获取得到的数据
	   byte[] data = outStream.toByteArray();
	   //转化为字符
	   return new String(data);
	  }
	 
	 
	 
	 
	 
	 

}
