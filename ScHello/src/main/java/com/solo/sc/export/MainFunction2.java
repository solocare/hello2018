package com.solo.sc.export;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import com.solo.sc.unzip.UnzipUtil;


public class MainFunction2 {

	public static void main(String[] args) {
		startUnzip();

	}
	
	public static void startUnzip(){

    	String zippath = "E:/mirrorzip/";
    	String path = "E:/mirror/";
    	boolean flag = false;
    	File zipfile = new File(zippath);
    	File mirrorfile = new File(path);
    	boolean isNext = true;
    	while(isNext){
    		if (zipfile.exists()) {
    			File[] zipfiles = zipfile.listFiles();
    			if (zipfiles.length == 0) {
    				System.out.println("当前没有mirror zip包！");
    				isNext = false;
    			}else{
//				System.out.println("----------排序前第一个文件路径----" + zipfiles[0].getAbsolutePath().toString());
    				Arrays.sort(zipfiles, new Comparator<Object>(){  
    					public int compare(Object o1, Object o2) {
    						File file1 = (File)o1;
    						File file2 = (File)o2;
    						long diff = file1.lastModified() - file2.lastModified();
    						if (diff > 0)
    							return 1;
    						else if (diff == 0)
    							return 0;
    						else
    							return -1;
    					}  
    				});
//				System.out.println("----------排序后第一个文件路径----" + zipfiles[0].getAbsolutePath().toString());
    				File currentFile = zipfiles[0];
    				
    				try {
    					flag = UnzipUtil.unzip(zippath + currentFile.getName(), path);
    					
    					if(!flag){
    						System.out.println(" zip包解析失败！！！------------------" + currentFile.getName() + "----------------");
    						isNext = false;
//						// 删除镜像
//						File[] filestemp = mirrorfile.listFiles();
//						for (int i = 0; i < filestemp.length; i++) {
//							File temp = filestemp[i];
//							temp.delete();
//						}
    					}else{
    						if (currentFile.exists()) {
        						boolean bool = currentFile.delete();
        						if (bool) {
        							System.out.println("删除zip包成功！");
        						}else{
        							System.out.println("删除zip包失败！");
        						}
        					}
    					}
    					
    					
    				} catch (Exception e) {
    					System.out.println("解压mirror zip包失败！------------------" + currentFile.getName() + "----------------");
    					isNext = false;
    				}
    			}
    		}else{
    			isNext = false;
    			System.out.println("mirror zip路径不存在！");
    		}
    	}

    
	}

}
