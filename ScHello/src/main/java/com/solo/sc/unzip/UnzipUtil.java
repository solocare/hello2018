package com.solo.sc.unzip;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class UnzipUtil {

	public static boolean unzip(String zipfile, String savepath) {
		boolean flag = false;

		int count = -1;

		File unzipfile = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		savepath = savepath.replace("\\", "/");

		File savefile = new File(savepath);

		savefile.mkdir(); // 创建保存目录

		ZipFile zipFile = null;
		try {
			Charset utf8 = Charset.forName("utf-8");
			zipFile = new ZipFile(zipfile, utf8);
			Enumeration<?> entries = zipFile.entries();
			System.out.println(zipFile.getName()+"解压中......");
			while (entries.hasMoreElements()) {
				
				ZipEntry entry = (ZipEntry) entries.nextElement();

				long size = entry.getSize();

				String filename = entry.getName();
				
				//System.out.println(zipFile.getName()+"---"+filename+"解压中......");

				filename = savepath + filename;

				unzipfile = new File(filename);

				unzipfile.createNewFile(); // 创建文件

				int buffer = ((int) size) * 2;
				if(buffer==0){
					buffer=1024;
				}
				byte buf[] = new byte[buffer];

				is = zipFile.getInputStream(entry);
				fos = new FileOutputStream(unzipfile);
				bos = new BufferedOutputStream(fos, buffer);

				while ((count = is.read(buf)) > -1) {
					bos.write(buf, 0, count);
				}
				//System.out.println(zipFile.getName()+"---"+filename+"解压完成......");

				bos.flush();
				bos.close();
				fos.close();

				is.close();
			}
			zipFile.close();
			flag = true;
			System.out.println(zipFile.getName()+"解压解压完成......");
		} catch (IOException ioe) {
			//ioe.printStackTrace();
			System.out.println(zipFile.getName()+"解压失败！");
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
				if (zipFile != null) {
					zipFile.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return flag;
	}

	public static void main(String[] args) {
		UnzipUtil.unzip("E:/a.zip", "E:/");
	}
}
