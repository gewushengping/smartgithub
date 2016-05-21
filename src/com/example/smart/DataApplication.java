package com.example.smart;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

public class DataApplication extends Application {
	
	 private String lightState; 
	  ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,Object>>();
	 
   
	    public String getLightState(){  
	        return this.lightState;  
	    }  
	    public void setB(String state){  
	    	this.lightState= state;  
	    }  
	    @Override  
	    public void onCreate(){  
	        lightState = "close";  
	        super.onCreate();  
	    }  

}
