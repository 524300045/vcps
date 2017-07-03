package com.wologic.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * @project:					TIIS 
 * @Title: 						SimpleClient.java 		
 * @Package 					com.rainet.tiis.network		
 * @Description: 				HTTP����factory
 * @author 						
 * @date 						2014-3-18 ����12:39:23 
 * @version 					V1.0
 */
public class SimpleClient {
	private static HttpParams httpParams;
	private static DefaultHttpClient httpClient;
	private static String JSESSIONID; //����һ����̬���ֶΣ�����sessionID

	/**
	 * @Title: 				getHttpClient 
	 * @author 				
	 * @date 				2014-3-18 ����1:11:18
	 * @Description: 		��ȡHttpClient
	 * @return
	 * @throws Exception 
	 * HttpClient 				����
	 */
	public static HttpClient getHttpClient() throws Exception {
		// ���� HttpParams ���������� HTTP ��������һ���ֲ��Ǳ���ģ�
		httpParams = new BasicHttpParams();
		// �������ӳ�ʱ�� Socket ��ʱ���Լ� Socket �����С
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
		// �����ض���ȱʡΪ true
		HttpClientParams.setRedirecting(httpParams, true);
		// ���� user agent
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		// ����һ�� HttpClient ʵ��
		// ע�� HttpClient httpClient = new HttpClient(); ��Commons HttpClient
		// �е��÷����� Android 1.5 ��������Ҫʹ�� Apache ��ȱʡʵ�� DefaultHttpClient
		httpClient = new DefaultHttpClient(httpParams);
		return httpClient;
	}

	/**
	 * @Title: 				doGet 
	 * @author 				�����
	 * @date 				2014-3-18 ����12:39:58
	 * @Description: 		doGet����
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 * String 				����
	 */
	@SuppressWarnings("rawtypes")
	public static String doGet(String url, Map params){
		/* ����HTTPGet���� */
		String strResult;
		try {
			String paramStr = "";
			if (params != null) {
				Iterator iter = params.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					Object key = entry.getKey();
					Object val = entry.getValue();
					paramStr += paramStr = "&" + key + "=" + val;
				}
			}
			if (!paramStr.equals("")) {
				paramStr = paramStr.replaceFirst("&", "?");
				url += paramStr;
			}
			HttpGet httpRequest = new HttpGet(url);
			strResult = "doGetError";
			/* �������󲢵ȴ���Ӧ */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* ��״̬��Ϊ200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* ���������� */
				httpResponse.getStatusLine().getReasonPhrase();
				strResult = EntityUtils.toString(httpResponse.getEntity());
			} 
			else {
				strResult = "Error Response: " + httpResponse.getStatusLine().toString();
			}
			Log.v("strResult", strResult);
			return strResult;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * @Title: 				doPost 
	 * @author 				�����
	 * @date 				2014-3-18 ����12:39:38
	 * @Description: 		doPost����
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 * String 				����
	 */
	@SuppressWarnings("rawtypes")
	public static String doPost(String url, Map params) throws Exception {
		/* ����HTTPGet���� */
		String paramStr = "";
		if (params != null) {
			Iterator iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				paramStr += paramStr = "&" + key + "=" + val;
			}
		}
		if (!paramStr.equals("")) {
			paramStr = paramStr.replaceFirst("&", "?");
			url += paramStr;
		}
		HttpPost httpRequest = new HttpPost(url);
		String strResult = "doPostError";
		/* �������󲢵ȴ���Ӧ */
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		/* ��״̬��Ϊ200 ok */
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			/* ���������� */
			httpResponse.getStatusLine().getReasonPhrase();
			strResult = EntityUtils.toString(httpResponse.getEntity());
		} 
		else {
			strResult = "Error Response: " + httpResponse.getStatusLine().toString();
		}
		Log.v("strResult", strResult);
		return strResult;
	}
//	public static String doPost(String url, List<NameValuePair> params) throws Exception {
//		/* ����HTTPPost���� */
//		HttpPost httpRequest = new HttpPost(url);
//		String strResult = "doPostError";
//		/* ������������������� */
//		if (params != null && params.size() > 0) {
//			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
//		}
//		if(null != JSESSIONID){
//			httpRequest.setHeader("Cookie", "JSESSIONID="+JSESSIONID);
//		}
//		/* �������󲢵ȴ���Ӧ */
//		HttpResponse httpResponse = httpClient.execute(httpRequest);
//		/* ��״̬��Ϊ200 ok */
//		if (httpResponse.getStatusLine().getStatusCode() == 200) {
//			/* ���������� */
//			strResult = EntityUtils.toString(httpResponse.getEntity());
//			/* ��ȡcookieStore */
//			CookieStore cookieStore = httpClient.getCookieStore();
//			List<Cookie> cookies = cookieStore.getCookies();
//			for(int i=0;i<cookies.size();i++){
//				if("JSESSIONID".equals(cookies.get(i).getName())){
//					JSESSIONID = cookies.get(i).getValue();
//					break;
//				}
//			}
//		}
//		Log.v("strResult", strResult);
//		return strResult;
//	}

}
