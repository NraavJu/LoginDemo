package com.kklldog.core;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpHelper {
	private static HttpClient mHttpClient;
	
	public static HttpClient getHttpClient(){
		if( mHttpClient==null )
		{
			SchemeRegistry supportedSchemes = new SchemeRegistry();
			PlainSocketFactory sf = PlainSocketFactory.getSocketFactory();
			supportedSchemes.register(new Scheme("http", sf, 80));
			// self added ---设置HttpClient支持HTTP和HTTPS两种模式
			supportedSchemes.register(new Scheme("https", sf, 443));
			HttpParams params =createHttpParams();
		    HttpClientParams.setRedirecting(params, false);
	        // 使用线程安全的连接管理来创建HttpClient
	        ClientConnectionManager conMgr =new ThreadSafeClientConnManager(params, supportedSchemes);
	        mHttpClient =new DefaultHttpClient(conMgr, params);
		}
		
		return mHttpClient;
	}
	
	private static HttpParams createHttpParams() {
			final HttpParams params = new BasicHttpParams();

			// Turn off stale checking. Our connections break all the time anyway,
			// and it's not worth it to pay the penalty of checking every time.
			HttpConnectionParams.setStaleCheckingEnabled(params, false);

			// self added --- 设置一些基本参数 HTTP请求执行参数
			org.apache.http.params.HttpProtocolParamBean paramsBean = new org.apache.http.params.HttpProtocolParamBean(params);
			paramsBean.setVersion(HttpVersion.HTTP_1_1);// 定义使用HTTP协议版本,
														// 如果不设置此参数将使用HTTP/1.1
			paramsBean.setContentCharset(HTTP.UTF_8);// 定义字符集用于每个内容体的默认编码
														// ,如果不设置此参数将使用ISO-8859-1
			ConnManagerParams.setTimeout(params, 1000); /* 从连接池中取连接的超时时间 */
			// self added ---

			// 连接管理
			HttpConnectionParams.setConnectionTimeout(params,   1000);// 建立连接的超时时间（以毫秒为单位）当值为0被解释成一个无限超时,如果此参数不设置，连接操作不会超时（无限超时）。
			HttpConnectionParams.setSoTimeout(params,   5000); // 以毫秒为单位定义套接字超时（SO_TIMEOUT）。当值为0被解释成一个无限的暂停,如果此参数不设置，读操作不会超时（无限暂停）。
			HttpConnectionParams.setSocketBufferSize(params, 8192);// 接收/传输HTTP消息时，确定socket内部缓冲区缓存数据的大小,如果此参数不设置，HttpClient将分配8192字节scoket缓冲区

			return params;
	}
	
	protected static String execRequest(HttpRequestBase httpRequest) throws  Exception {
		HttpResponse response = getHttpClient().execute(httpRequest);

		switch (response.getStatusLine().getStatusCode()) {
		case 200:
			try {
				return EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				throw e;
			}
		case 204:
			return "";
		case 401:
			response.getEntity().consumeContent();
			throw new Exception(response.getStatusLine().toString());

		case 404:
			response.getEntity().consumeContent();
			throw new Exception(response.getStatusLine().toString());

		default:
			response.getEntity().consumeContent();
			throw new Exception(response.getStatusLine().toString());
		}
	}
	public static String doHttpGet(String url,List<NameValuePair> nameValuePairs) throws Exception{
		String query = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
		HttpGet httpGet = new HttpGet(url + "?" + query);
		return execRequest(httpGet);
	}
	
	public static String doHttpGet(String url) throws Exception{
		HttpGet httpGet = new HttpGet(url);
		return execRequest(httpGet);
	}
    
	public static String doHttpDelete(String url) throws Exception{
		HttpDelete httpDelete = new HttpDelete(url);
		return execRequest(httpDelete);
	}
	
	public static String doHttpDelete(String url,List<NameValuePair> nameValuePairs) throws Exception{
		String query = URLEncodedUtils.format(nameValuePairs, HTTP.UTF_8);
		HttpDelete httpDelete = new HttpDelete(url + "?" + query);
		return execRequest(httpDelete);
	}
	
	public static String doHttpPost(String url,List<NameValuePair> nameValuePairs) throws Exception{
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(new org.apache.http.client.entity.UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
		return execRequest(httpPost);
	}
	
	public static String  doHttpPost(String url,String josn) throws Exception{
		
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(new BasicHeader("Content-Type", "application/json"));
		httpPost.setEntity(new StringEntity(josn, HTTP.UTF_8));
		return execRequest(httpPost);
	}
	
	public static String  doHttpPut(String url,String josn) throws Exception{
		
		HttpPut httpPut = new HttpPut(url);
		httpPut.addHeader(new BasicHeader("Content-Type", "application/json"));
		httpPut.setEntity(new StringEntity(josn, HTTP.UTF_8));
		return execRequest(httpPut);
	}
}
