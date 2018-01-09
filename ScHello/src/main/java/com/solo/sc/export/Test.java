package com.solo.sc.export;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
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
		}
		
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
}
