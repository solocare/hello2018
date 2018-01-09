package com.solo.sc.zsx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		String readpath= "C://Users/hxl/Desktop/a.txt";
		String writepath= "C://Users/hxl/Desktop/zsxa.txt";
		exitFile(readpath);
		exitFile(writepath);
		try {
			writerFile(readpath,writepath);
		//	writerFileGBK(readpath,writepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		String jsonstr = readFile("E://a.txt");
		JSONObject obj = JSONObject.fromObject(jsonstr);
		obj = obj.getJSONObject("aggregations");
		obj = obj.getJSONObject("terms_classNo");
		JSONArray arr = obj.getJSONArray("buckets");
		
		for (int i = 0; i < arr.size(); i++) {
			JSONObject _obj = (JSONObject)arr.get(i);
			String key = _obj.getString("key");
			_obj = _obj.getJSONObject("test");
			
			JSONArray _arr = _obj.getJSONArray("buckets");
			JSONObject obj2 = (JSONObject)_arr.get(0);
			
			System.out.println(key+"	"+obj2.getString("key"));
		}*/
		
	}
	
	public static void writerFile(String readpath, String writepath) throws IOException {
		StringBuffer sb= new StringBuffer("");
		   
	    FileInputStream fis = new FileInputStream(readpath);
	    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
	    BufferedReader br;
		br = new BufferedReader(isr);
		File filewriter = new File(writepath);
		BufferedWriter bufwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filewriter), "UTF-8"));
		
	    String str = null;
	   
	    while((str = br.readLine()) != null) {
	    	str = str.substring("{\"comment\": \"".length(), str.length()-2);
	    	 while(str.indexOf("<span class=\"url-icon\">") >= 0) {
	    		 str = str.substring(0, str.indexOf("<span class=\"url-icon\">")) + str.substring(str.indexOf("</span>") + "</span>".length(), str.length());
	    	 }
	    	if(str.indexOf("回复") == 0){
	    		str = str.replace("</a>", "");
	    		str = "回复" + str.substring(str.indexOf("@"), str.length());
	    	}else if(str.indexOf("@") >= 0){
	    		str = str.replace("</a>", "");
	    		str = str.substring(str.indexOf("@"), str.length());
	    	}
	        sb.append(str + "\t\r");
	    }
	    
		bufwriter.write(sb.toString());
		bufwriter.close();
		br.close();
	    fis.close();
	}
	
	public static void writerFileGBK(String readpath, String writepath) throws IOException {
		StringBuffer sb= new StringBuffer("");
		   
	    FileInputStream fis = new FileInputStream(readpath);
	    InputStreamReader isr = new InputStreamReader(fis, "GBK");
	    BufferedReader br;
		br = new BufferedReader(isr);
		File filewriter = new File(writepath);
		BufferedWriter bufwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filewriter), "UTF-8"));
		
	    String str = null;
	   
	    while((str = br.readLine()) != null) {
	    	str = str.substring("{\"comment\": \"".length()-1, str.length()-2);
	    	 while(str.indexOf("<span class=\\\"url-icon\\\">") >= 0) {
	    		 str = str.substring(0, str.indexOf("<span class=\\\"url-icon\\\">")) + str.substring(str.indexOf("</span>") + "</span>".length(), str.length());
	    	 }
	    	if(str.indexOf("回复") == 0){
	    		str = str.replace("</a>", "");
	    		str = "回复" + str.substring(str.indexOf("@"), str.length());
	    	}else if(str.indexOf("@") >= 0){
	    		str = str.replace("</a>", "");
	    		str = str.substring(str.indexOf("@"), str.length());
	    	}
	        sb.append(str + "\t\r");
	    }
	    
		bufwriter.write(sb.toString());
		bufwriter.close();
		br.close();
	    fis.close();
	}
	
	public static String readFile(String path) {
	    try {
		    StringBuffer sb= new StringBuffer("");
		   
//		    FileReader reader = new FileReader(path);
		    FileInputStream fis = new FileInputStream(path);
		    InputStreamReader isr = new InputStreamReader(fis, "GBK");
		    BufferedReader br;
			br = new BufferedReader(isr);
		   
		    String str = null;
		   
		    while((str = br.readLine()) != null) {
		          sb.append(str);
		    }
		   
		    br.close();
		    fis.close();
		    
		    return sb.toString();
	    }
	    catch(Exception e) {
	       e.printStackTrace();
	    }
	    
	    return null;
	}
	public static void exitFile(String path){
		File f = new File(path);   
		if(!f.getParentFile().exists()){ 
			System.out.print(path + "文件不存在，创建文件");
			f.getParentFile().mkdirs();  
		}else{  
			System.out.print("文件存在");
		}
	}
}
