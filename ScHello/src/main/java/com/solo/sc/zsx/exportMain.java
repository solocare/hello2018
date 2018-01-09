package com.solo.sc.zsx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

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

	public static void main(String[] args) {
		try {
			List<content> cList = readTXT("C://Users/hxl/Desktop/a.txt");
			ExportExcel<content> ex = new ExportExcel<content>();  
			String[] headers =  { "A1", "B2", "C3"};  
            OutputStream out = new FileOutputStream("C://Users/hxl/Desktop/zsx" + new Date().getTime() + ".xls");  
            ex.exportExcel(headers, cList, out);  
            out.close();  
            JOptionPane.showMessageDialog(null, "导出成功!");  
            System.out.println("excel导出成功！");  
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}