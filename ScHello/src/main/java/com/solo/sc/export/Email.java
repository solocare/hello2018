package com.solo.sc.export;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
	private final static String pathread = "C:/Users/hxl/Desktop/过程明细.csv";
	private final static String pathwriter = "C:/Users/hxl/Desktop/邮箱final.xlsx";
	private final static String pathwriterno = "C:/Users/hxl/Desktop/no邮箱final.xlsx";
	
	public static void exitFile(String path){
		File f = new File(path);   
		if(!f.getParentFile().exists()){ 
			System.out.print(path + "文件不存在，创建文件");
			f.getParentFile().mkdirs();  
		}else{  
			System.out.print("文件存在");
		}
	}
	
    /**
     * 验证邮箱
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
                String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
                Pattern regex = Pattern.compile(check);
                Matcher matcher = regex.matcher(email);
                flag = matcher.matches();
            }catch(Exception e){
                flag = false;
            }
        return flag;
    }
    
	public static void main(String[] args) throws IOException {
		
		exitFile(pathread);
		exitFile(pathwriter);
		exitFile(pathwriterno);
		
		File fileread = new File(pathread);
		File filewriter = new File(pathwriter);
		File filewriterno = new File(pathwriterno);
		BufferedReader bufread = new BufferedReader(new InputStreamReader(new FileInputStream(fileread), "GBK"));
		BufferedWriter bufwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filewriter), "UTF-8"));
		BufferedWriter bufwriterno = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filewriterno), "UTF-8"));
		StringBuffer stringbufwriterok=new StringBuffer();
		StringBuffer stringbufwriterno=new StringBuffer();
		
		String line;
		int count = 0;
		
	//	while ((line = new String(bufread.readLine().getBytes("8859_1"), "utf8")) != null) {
		while ((line = bufread.readLine()) != null) {
			if(checkEmail(line)){
				stringbufwriterok.append(line + "\r\n");
				System.out.println("已验证邮箱数" + (++count));
			}else{
				stringbufwriterno.append(line + "\r\n");
			}

		}
		bufwriter.write(stringbufwriterok.toString());
		bufwriterno.write(stringbufwriterno.toString());
		System.out.println("验证邮箱完毕,正确邮箱共" + count);
		bufwriter.close();
		bufwriterno.close();
		bufread.close();

	}

}
