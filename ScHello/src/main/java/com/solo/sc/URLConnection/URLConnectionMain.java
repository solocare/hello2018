package com.solo.sc.URLConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


public class URLConnectionMain {
	public static String buildRequestParams(Map<String, String> params,String charset) throws UnsupportedEncodingException {
        if (params == null || params.isEmpty()) {
            return null;
        }
        
        // 对参数进行排序
        List<Map.Entry<String, String>> newParams = new ArrayList<Map.Entry<String, String>>(
                params.entrySet());
        Collections.sort(newParams,
                new Comparator<Map.Entry<String, String>>() {
                    public int compare(Map.Entry<String, String> o1,
                            Map.Entry<String, String> o2) {
                        return (o1.getKey()).toString().compareTo(o2.getKey());
                    }
                });
 
        StringBuilder query = new StringBuilder();
        for (Map.Entry<String, String> entry : newParams) {
            String name = entry.getKey();
            String value = entry.getValue();
            query.append("&");
            query.append(name).append("=").append(URLEncoder.encode(value, charset));
        }
        if(!"".equalsIgnoreCase(query.toString()))
        	return query.toString().substring(1, query.toString().length());
        return query.toString();
    }

	public static String requestHTTPS(String targetUrl, String method, Map<String,String> params) {
		try {
			System.out.println(targetUrl);
			URL url = new URL(targetUrl);
			HttpsURLConnection connection = (javax.net.ssl.HttpsURLConnection) url
					.openConnection();
			/* Load the keyStore that includes self-signed cert as a "trusted" entry. */
			//http://stackoverflow.com/questions/859111/how-do-i-accept-a-self-signed-certificate-with-a-java-httpsurlconnection
			//javax.net.ssl.SSLSocketFactory
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			FileInputStream instream = new FileInputStream(new File("/Users/yol/Documents/id.keystore"));
			try {
				// 加载keyStore   
				trustStore.load(instream, "D#s@a1".toCharArray());
			} catch (CertificateException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
				}
			}
			TrustManagerFactory tmf = 
					  TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(trustStore);
			
			//
			X509TrustManager defaultTrustManager = (X509TrustManager)tmf.getTrustManagers()[0];
 
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, new TrustManager[] {defaultTrustManager}, null);
			
			SSLSocketFactory sslFactory = ctx.getSocketFactory();
			
			connection.setSSLSocketFactory(sslFactory);
 
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod(method);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			
			connection.connect();
			if(params!=null){
				//POST请求
				DataOutputStream out = new DataOutputStream(
						connection.getOutputStream());
				out.writeBytes(buildRequestParams(params,"UTF-8"));
				out.flush();
				out.close();
			}
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			reader.close();
			connection.disconnect();
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getCookieVal(String path) throws UnsupportedEncodingException, IOException{
		// 连接地址（通过阅读html源代码获得，即为登陆表单提交的URL）
		 /**
		  * 首先要和URL下的URLConnection对话。 URLConnection可以很容易的从URL得到。比如： // Using
		  * java.net.URL and //java.net.URLConnection
		  */
		 URL url = new URL(path);
		 HttpsURLConnection connection = (javax.net.ssl.HttpsURLConnection) url.openConnection();
		 
		 /**
		  * 然后把连接设为输出模式。URLConnection通常作为输入来使用，比如下载一个Web页。
		  * 通过把URLConnection设为输出，你可以把数据向你个Web页传送。下面是如何做：
		  */
		 connection.setDoOutput(true);
		 /**
		  * 最后，为了得到OutputStream，简单起见，把它约束在Writer并且放入POST信息中，例如： ...
		  */
		 OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GBK");
		 //其中的memberName和password也是阅读html代码得知的，即为表单中对应的参数名称
		 out.write("username=张玥&password=100895"); // post的关键所在！
		 // remember to clean up
		 out.flush();
		 out.close();
		 
		 // 取得cookie，相当于记录了身份，供下次访问时使用
		 String cookieVal = connection.getHeaderField("Set-Cookie");
		 return cookieVal;
	}
	
	public static void excuSubmit(String cookieVal, String path) throws IOException{
		//重新打开一个连接
		URL url = new URL(path);
		HttpURLConnection resumeConnection = (HttpURLConnection) url.openConnection();
		if (cookieVal != null) {
			//发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
			resumeConnection.setRequestProperty("Cookie", cookieVal);
		}
		resumeConnection.connect();
		InputStream urlStream = resumeConnection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));
		String ss = null;
		String total = "";
		while ((ss = bufferedReader.readLine()) != null) {
		 total += ss;
		}
		System.out.println("//------------------------------------------------------//");
		System.out.println(total);
	//	IOUtils.write(total, new FileOutputStream("d:/index.html"));
		bufferedReader.close(); 
	}
	
	public static void main(String[] args) {
		String cookieVal;
		try {
			cookieVal = getCookieVal("https://spas.ncs-cyber.com.cn:9897");
			System.out.println(cookieVal);
			excuSubmit(cookieVal, "https://spas.ncs-cyber.com.cn:9897/empActivity/toEmpActivityInfoManage?_t=576977&_winid=w7732");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
