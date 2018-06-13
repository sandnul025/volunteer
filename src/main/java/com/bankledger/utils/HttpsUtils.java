package com.bankledger.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtils {
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private static final Integer DEFAULT_CONNECT_TIMEOUT = 50000;
	
	private static final Integer DEFAULT_READ_TIMEOUT = 50000;
	
	public static String upload( String url , Map<String, String> headers , String fileName , byte[] file ) throws Exception{
		
        String newLine = "\r\n";  
        String boundaryPrefix = "--";  
        String BOUNDARY = "========" + UUID.randomUUID().toString().replaceAll("-", "");  
		
		HttpURLConnection conn = getHttpURLConnection(url, headers);
		
		conn.setRequestProperty("connection", "Keep-Alive");  
        conn.setRequestProperty("Charsert", "UTF-8");  
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        
        conn.setRequestMethod("POST");  
        conn.setDoOutput(true);  
        conn.setDoInput(true);  
        conn.setUseCaches(false);  
        
        OutputStream out = new DataOutputStream(conn.getOutputStream());  
        InputStream  is  = null;
        try {
            String response = null;
        	// 上传文件  
            StringBuilder sb = new StringBuilder();  
            sb.append(boundaryPrefix);  
            sb.append(BOUNDARY);  
            sb.append(newLine);  
            // 文件参数,photo参数名可以随意修改  
            sb.append("Content-Disposition: form-data;name="+ fileName + ";filename=\"" + fileName  +".jpg"
                    + "\"" + newLine);  
            sb.append("Content-Type:application/octet-stream");  
            // 参数头设置完以后需要两个换行，然后才是参数内容  
            sb.append(newLine);  
            sb.append(newLine);  
            // 写入前部分说明信息
            out.write(sb.toString().getBytes()); 
            // 写入文件字节
            out.write(file);
            // 写入后部分说明信息
            out.write(newLine.getBytes());  
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)  
                    .getBytes();  
            // 写上结尾标识  
            out.write(end_data);  
            out.flush();
            is = conn.getInputStream();
            response = getInputStreamString(is);
            return response;
		} finally{
			if( out != null ){
				out.close();
			}
			if( is != null ){
				is.close();
			}
			conn.disconnect();
		}
	}
	
	public static String get(String url) throws Exception{
		return get(url, null, null);
	}
	
	public static String get(String url , Map<String, String> params) throws Exception{
		return get(url, null, params);
	}
	
	public static String get(String url , Map<String, String> headers , Map<String, String> params) throws Exception{
		String mapURLEncodingString = getMapURLEncodingString(params);
		url = mapURLEncodingString.length() > 0 ? url + "?" + mapURLEncodingString : url;
		HttpURLConnection conn = null;
		InputStream is = null;
		try {
			conn = getHttpURLConnection(url, headers);
			is   = conn.getInputStream();
			return getInputStreamString(is);
		}catch(Exception e){
			throw e;
		} finally {
			is.close();
			conn.disconnect();
		}
	}
	
	public static String post(String url , Map<String, String> params) throws Exception{
		return post(url, null, params);
	}
	
	public static String post(String url , Map<String, String> headers , Map<String, String> params) throws Exception{
		String mapURLEncodingString = getMapURLEncodingString(params);
		return post(url, headers, mapURLEncodingString);
	}
	
	public static String post(String url , String body) throws Exception{
		return post(url, null, body);
	}
	
	public static String post(String url , Map<String, String> headers , String body) throws Exception{
		HttpURLConnection conn = null;
		InputStream  is = null;
		OutputStream os = null;
		InputStream  error = null;
		try {
			conn = getHttpURLConnection(url, headers);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			os = conn.getOutputStream();
			os.write(body.getBytes(DEFAULT_CHARSET));
			is = conn.getInputStream();
			return getInputStreamString(is);
		}catch(Exception e){
			error = conn.getErrorStream();
			if(error != null){
				return getInputStreamString(error);
			}else throw e;
		}finally{
			if(is != null){
				is.close();
				os.close();
			}
			conn.disconnect();
		}
	}
	
	private static HttpURLConnection getHttpURLConnection( String url , Map<String, String> headers ) throws Exception{
		
		HttpURLConnection conn = null;
		if( url.startsWith("https") ){
			javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
	        javax.net.ssl.TrustManager tm = new MyTrustManager();
	        trustAllCerts[0] = tm;
	        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, null);
	        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        
	        HostnameVerifier hv = new HostnameVerifier() {
                public boolean verify(String urlHostName, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hv);
		}
		conn = (HttpURLConnection)new URL(url).openConnection();
		
		if(headers != null && !headers.isEmpty()){
			for( Entry<String, String> entry : headers.entrySet() ){
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}
		conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
		conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
		
		return conn;
	}
	
	private static String getInputStreamString(InputStream is) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while( (line = reader.readLine()) != null ){
			sb.append(line);
		}
		return sb.toString();
	}
	
	private static String getMapURLEncodingString( Map<String, String> params )throws Exception{
		StringBuilder sb = new StringBuilder();
		if( params != null && !params.isEmpty() ){
			String key = null;
			String val = null;
			for( Entry<String, String> param : params.entrySet() ){
				key = URLEncoder.encode(param.getKey(), DEFAULT_CHARSET);
				val = URLEncoder.encode(param.getValue(), DEFAULT_CHARSET);
				sb.append( key + "=" + val );
			}
		}
		return sb.toString();
	}
	
	/**
	 * 通过代替默认的证书管理器,忽略证书来访问HTTPS请求.
	 */
	static class MyTrustManager implements TrustManager,X509TrustManager{

		public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
 
        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }
 
        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }
 
        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
 
        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
		
	}
	
}
