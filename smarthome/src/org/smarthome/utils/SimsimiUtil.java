package org.smarthome.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SimsimiUtil {
	private static String url = "http://www.simsimi.com/func/reqN?lc=ch&ft=1.0&fl=http%3A%2F%2Fwww.simsimi.com%2Ftalk.html";
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static String getSimsimiResult(String s){
		String value = sendGet(s);
		Map<String, String> map = null;
		try {
			map = objectMapper.readValue(value, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return map.get("sentence_resp");
	}
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		String value = getSimsimiResult("今天的天气怎么样");
		System.out.println(value);
//		Map map = objectMapper.readValue(value, Map.class);
//		System.out.println(map.get("sentence_resp"));
	}
	
	public static String sendGet(String p) {
		String result = "";
		BufferedReader in = null;
		try {
			p = URLEncoder.encode(p, "UTF-8");
			String urlNameString = SimsimiUtil.url + "&req=" + p;
			URL realUrl = new URL(urlNameString);
//			System.out.println(urlNameString);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setDoOutput(true);
	        conn.setDoInput(true);
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
			conn.setRequestProperty("Cache-Control", "max-age=0");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Host", "www.simsimi.com");
			conn.setRequestProperty("Cookie", "simsimi_uid=1");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
			// 建立实际的连接
			conn.connect();
			// 定义 BufferedReader输入流来读取URL的响应
//			System.out.println("content_type :" + conn.getContentType());
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

}
