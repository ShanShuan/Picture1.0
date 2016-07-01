package cn.shanshuan.html.demo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HttpUrlUtil {
	public static InputStream get(String path) throws IOException{
		URL url=new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		return is;
	}
	public static InputStream post(String path,Map<String, String> paramsMap) throws IOException{
		URL url=new URL(path);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setDoOutput(true);
		if(paramsMap!=null){
			StringBuilder sb=new StringBuilder();
			Set<String> keys = paramsMap.keySet();
			Iterator<String> it = keys.iterator();
			while(it.hasNext()){
				String key = it.next();
				String v=paramsMap.get(key);
				sb.append(key+"="+v+"&");
			}
			sb.deleteCharAt(sb.length()-1);
			OutputStream os=conn.getOutputStream();
			os.write(sb.toString().getBytes("utf-8"));
			os.flush();
		}
		InputStream is=conn.getInputStream();
		
		return is;
	}
	public static String toSting(InputStream is) throws IOException{
		BufferedReader reader=new BufferedReader(new InputStreamReader(is));
		StringBuilder sb=new StringBuilder();
		String line="";
		while((line=reader.readLine())!=null){
			sb.append(line);
		}
		
		return sb.toString();
	}
}
