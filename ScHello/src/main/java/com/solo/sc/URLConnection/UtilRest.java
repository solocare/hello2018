package com.solo.sc.URLConnection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
 

import net.sf.json.JSONObject;
 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
public class UtilRest {
	private static Log log=LogFactory.getLog(UtilRest.class);
	static {
	    //for localhost testing only 
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){
 
	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost")) {
	                return true;
	            }else  if (hostname.equals("118.85.194.45")) {
	                return true;
				}
	            return false;
	        }
	    });
	}
	
	public static void main(String[] args) {
    	JSONObject response=UtilRest.post("https://118.85.194.45:8080/");
	}
	public static JSONObject get(String targetUrl) {
//		HttpClient httpClient = new HttpClient();
// 
//		HttpMethodBase method = new GetMethod();
//		JSONObject response = null;
//		try {
//			method.setURI(new URI(targetUrl, false));
//			int statusCode = httpClient.executeMethod(method);
//			if (statusCode != HttpStatus.SC_OK) {
//				System.out.println("Method failed: "
//						+ method.getStatusLine() + " for url " + targetUrl);
//			}
//			String strResponse = method.getResponseBodyAsString();
//			response = JSONObject.fromObject(strResponse);
//			return response;
//		} catch (Exception e) {
//			log.error("Please check your provided http address!");
//		} finally {
//			if (method != null)
//				method.releaseConnection();
//		}
//		if (response == null)
//			return null;
		return null;
	}
	public static JSONObject post(String targetUrl) {
		return post(targetUrl,null);
	}
	public static JSONObject post(String targetUrl,Map<String,String> params) {
		String result = null;
		if (targetUrl.startsWith("https")) {
			result = requestHTTPS(targetUrl, "POST",params);
			
		}else if (targetUrl.startsWith("http")) {
			result = request(targetUrl, "POST",params);
 
		}
		if (result != null)
			try {
				return JSONObject.fromObject(result);
			} catch (Exception e) {
			}
		return null;
	}
	public static JSONObject put(String targetUrl) {
		String result = request(targetUrl, "PUT",null);
		if (result != null)
			try {
				return JSONObject.fromObject(result);
			} catch (Exception e) {
			}
		return null;
	}
	public static JSONObject delete(String targetUrl) {
		String result = request(targetUrl, "DELETE",null);
		if (result != null)
			try {
				return JSONObject.fromObject(result);
			} catch (Exception e) {
			}
		return null;
	}
 
	public static String request(String targetUrl, String method, Map<String,String> params) {
		try {
			log.debug("request targetUrl:"+targetUrl+",method:"+method);
			URL url = new URL(targetUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
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
			log.debug("response:"+sb.toString());
			return sb.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String requestHTTPS(String targetUrl, String method, Map<String,String> params) {
		try {
			log.debug("requestHTTPS targetUrl:"+targetUrl+",method:"+method);
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
			log.debug("response:"+sb.toString());
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
	
	public static String buildRequestParams(Map<String, String> params,
            String charset) throws UnsupportedEncodingException {
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
}
