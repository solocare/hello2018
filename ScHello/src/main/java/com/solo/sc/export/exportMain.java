package com.solo.sc.export;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class exportMain {
	
	public static List<content> readTXT(String path)throws IOException {
		List<content> contentList = new ArrayList<content>();
		FileInputStream fis = new FileInputStream(path);   
		InputStreamReader isr = new InputStreamReader(fis, "GBK");   
		BufferedReader bufread;
		bufread = new BufferedReader(isr);
		String line;
		String contentAll = "";
		int num = 0;
		String temp_line = "";
		while ((line = bufread.readLine()) != null) {
		//	contentAll += line;
			num++;
			if(num < 14){
				temp_line += line;
			}
			if(num == 14){
				temp_line = temp_line + "}";
				temp_line = temp_line.trim();
				temp_line = temp_line.replace(" ", "");
				temp_line = temp_line.replaceAll("\\s*", ""); 

				JSONObject j = JSONObject.fromObject(temp_line);
				JSONObject json_c = j.optJSONObject("_source");
				if(json_c == null){
					num = 0;
					temp_line = "";
					continue;
				}
			//	System.out.println(json_c.toString());
				content c = new content(json_c.optString("IR_VERIFIED"), json_c.optString("IR_UID"), json_c.optString("IR_SCREEN_NAME"));
				contentList.add(c);
				num = 0;
				temp_line = "";
			}
		}
		bufread.close();
		
//		JSONArray array = JSONArray.fromObject("[" + contentAll + "]");
//		List<sourceTXT> sourceList = JSONArray.toList(array);
//		for(sourceTXT s : sourceList){
//			JSONObject j = JSONObject.fromObject(s.get_source());
//			content c = new content(j.optString("IR_VERIFIED"), j.optString("IR_UID"), j.optString("IR_SCREEN_NAME"));
//			contentList.add(c);
//		}
		System.out.println("准备完毕");
		
		return contentList;
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
	
	public static void writeJson(String content, String path){
		
		File file = new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(file.exists()){
			PrintWriter pw = null;
			try {
				pw = new PrintWriter(new FileWriter(file, true));
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				pw.close();
				e.printStackTrace();
			}  
		}else{
			System.out.println("文件不存在");
		}
	}
	public static List<String> selectJsonOject(JSONObject obj){
		  /*JTextArea textA;JTextField textF;JButton b1,b2;
		  setSize(250,150);
		  textA=new JTextArea("",5,10);
		  textA.setBackground(Color.cyan);
		  textF=new JTextField("",10);
		  textF.setBackground(Color.pink);
		  b1=new JButton("求 和"); b2=new JButton("重新开始");
		  textF.setEditable(false);
		  b1.addActionListener(this); b2.addActionListener(this);
		  add(textA); add(textF); add(b1);add(b2);*/
		
		
		
		List<String> finaljsonList = new ArrayList<String>();
		List<String> temp_jsonList = new ArrayList<String>();
		Iterator keys = obj.keys();
		while(keys.hasNext()){
			String k = keys.next().toString();
			temp_jsonList.add(k);
	//		JSONObject v = obj.getJSONObject(v);
			System.out.print("第一层" + k + " , ");
		}
		return finaljsonList;
	}
	public static void main(String[] args) {
		try {
			JFileChooser jfc = new JFileChooser();
			String readpath = "";
			String s = new SimpleDateFormat("YYYYMMddhhmmss").format(new Date());
			String writeFileName = "/newjson" + s + ".csv";
//			List<List<String>> cList = null;
			if(jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
//				cList = new ArrayList<List<String>>();
				File excelFile = jfc.getSelectedFile();
				readpath = excelFile.getAbsolutePath();
				System.out.println(excelFile.getAbsolutePath());
				String jsonstr = readFile(readpath);//读文件
				
				JSONObject obj = JSONObject.fromObject(jsonstr);
				List<String> finaljsonList = selectJsonOject(obj);//最终选出的jsonlist
				
				obj = obj.getJSONObject("aggregations");
				obj = obj.getJSONObject("terms_classNo");
				JSONArray arr = obj.getJSONArray("buckets");
				
				for (int i = 0; i < arr.size(); i++) {
					JSONObject _obj = (JSONObject)arr.get(i);
					String key = _obj.getString("key");
					_obj = _obj.getJSONObject("test");
					
					JSONArray _arr = _obj.getJSONArray("buckets");
					JSONObject obj2 = (JSONObject)_arr.get(0);
//					List<String> list = new ArrayList<String>();
//					list.add(key);
//					list.add(obj2.getString("key"));
//					cList.add(list);
				//  System.out.println(key+","+obj2.getString("key")+"\n");
					writeJson(key+","+obj2.getString("key"), excelFile.getParent() + writeFileName);
				}
				JOptionPane.showMessageDialog(null, "导出成功!"); 
			}else{
				JOptionPane.showMessageDialog(null, "选择文件错误!"); 
			}
			/*List<content> cList = readTXT("C://Users/hxl/Desktop/微博认证.txt");
			ExportExcel<content> ex = new ExportExcel<content>();  
			String[] headers =  { "A1", "B2", "C3"};  
            OutputStream out = new FileOutputStream("C://Users/hxl/Desktop/zsx" + new Date().getTime() + ".xls");  
            ex.exportExcel(headers, cList, out);  
            out.close();  
            JOptionPane.showMessageDialog(null, "导出成功!");  
            System.out.println("excel导出成功！");*/  
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "导出失败!"); 
		}

	}

}