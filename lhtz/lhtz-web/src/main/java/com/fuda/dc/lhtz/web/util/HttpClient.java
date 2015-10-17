package com.fuda.dc.lhtz.web.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http request client
 * 
 * @author kevin
 * @date 2015.09.03
 */
public class HttpClient {
	private static final Logger LOG = LoggerFactory.getLogger(HttpClient.class);
	
	public static final String ENCODING = "GB2312";

	public static String doGet(String url) {
		return doGet(url, ENCODING);
	}

	public static String doGet(String url, String encoding) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			LOG.error(e1.getMessage());
		}

		StringBuilder pageHTML = new StringBuilder();
		try {
			URL uri = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),
					Charset.forName(encoding)));
			String line = null;
			while ((line = br.readLine()) != null) {
				pageHTML.append(line);
				pageHTML.append("\r\n");
			}
			connection.disconnect();
		} catch (Exception e) {
			return "";
		}

		return pageHTML.toString();
	}
}
