package zsx;

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
import java.io.PrintWriter;

public class test {
	public static void option(String rpath, String wpath) {
		try {

			// read file content from file

			StringBuffer sb = new StringBuffer("");

//			FileReader reader = new FileReader(rpath);
			File reader = new File(rpath);

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(reader),"GBK"));

			String str = null;
			int num = 0;
			while ((str = br.readLine()) != null) {
				if(num++ == 0){
					sb.append("参数,内容\r\n");
					System.out.println("参数,内容\r\n");
					continue;
				}
				String finalreault = "content,";
				String tempstr = str.replace("</a>", "");
				tempstr = tempstr.substring(2, tempstr.length());
				if(tempstr.length() > 1){
					tempstr = tempstr.substring(0, tempstr.length() - 1);
				}
				while(true){
					if(tempstr.indexOf("<span class=\"url-icon\">")>=0){
						String a = tempstr.substring(tempstr.indexOf("<span class=\"url-icon\">"), tempstr.indexOf("</span>")+7);
						tempstr = tempstr.replace(a, "");
					}else{
						break;
					}
				}
				
				if(tempstr.indexOf("回复") == 0){
					finalreault += "回复";
				}
				if(tempstr.indexOf("@")>=0){
					finalreault += tempstr.substring(tempstr.indexOf("@"), tempstr.length());
				}
				if(finalreault.equals("content,")){
					finalreault += tempstr;
				}
				sb.append(finalreault + "\r\n");
				System.out.println(finalreault);

			}

			br.close();

		//	reader.close();

			// write string to file

			/*FileWriter writer = new FileWriter(wpath);

			BufferedWriter bw = new BufferedWriter(writer);

			bw.write(sb.toString());

			bw.close();

			writer.close();*/
			
			
			File filename = new File(wpath);
            filename.createNewFile();
//            FileWriter fw = new FileWriter(filename); 
            
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename),"UTF-8")));
            out.write(sb.toString());
            out.flush();
            out.close();
		}

		catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		catch (IOException e) {

			e.printStackTrace();

		}

	}
	public static void workoption(String rpath, String wpath){
		
	//	WorkbookSettings workbookSettings = new WorkbookSettings();
    //    workbookSettings.setEncoding("ISO-8859-1");
     //   Workbook book = Workbook.getWorkbook(new File("D:\\excel\\Shirley.xls"),workbookSettings); 
	}
	public static void main(String[] args) {
		String strpath = "C://Users/solo/Desktop/zsxfinal/";
		option(strpath + "1.txt", strpath + "zsx1.csv");
		option(strpath + "2.txt", strpath + "zsx2.csv");
		option(strpath + "3.txt", strpath + "zsx3.csv");
		option(strpath + "4.txt", strpath + "zsx4.csv");
		option(strpath + "5.txt", strpath + "zsx5.csv");
	}

}
