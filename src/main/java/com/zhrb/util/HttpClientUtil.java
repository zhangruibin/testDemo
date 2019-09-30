package com.zhrb.util;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;

/**
 * HttpClient 工具类 以get/post的方式发送数据到指定的http接口
 * 
 * @author zhrb
 * 
 */
public class HttpClientUtil {


	/**
	 * get方式
	 * 
	 * @param url
	 * @return
	 */
	public static String getHttp(String url) {
		String responseMsg = "";
		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		// 用于测试的http接口的url
		// 2.创建GetMethod的实例
		GetMethod getMethod = new GetMethod(url);
		// 使用系统系统的默认的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		try {
			// 3.执行getMethod,调用http接口
			httpClient.executeMethod(getMethod);
			// 4.读取内容
			byte[] responseBody = getMethod.getResponseBody();
			// 5.处理返回的内容
			responseMsg = new String(responseBody,"GB2312");

		} catch (HttpException e) {
			//e.printStackTrace();
			//zhrb-20190528-修复漏洞
		} catch (IOException e) {
			//e.printStackTrace();
			//zhrb-20190528-修复漏洞
		} finally {
			// 6.释放连接
			getMethod.releaseConnection();
		}
		return responseMsg;
	}

	/**
	 * post方式<br/>
	 * 
	 * 构造PostMethod的实例<br/>
	 * PostMethod postMethod = new PostMethod(url);<br/>
	 * 把参数值放入到PostMethod对象中<br/>
	 * 方式1：<br/>
	 * NameValuePair[] data = { new NameValuePair("param1", param1), new<br/>
	 * NameValuePair("param2", param2) }; postMethod.setRequestBody(data);<br/>
	 * 方式2：<br/>
	 * postMethod.addParameter("param1", param1);<br/>
	 * postMethod.addParameter("param2", param2);<br/>
	 * 
	 * @return
	 */
	public static String postHttp(PostMethod postMethod) {
		String responseMsg = "";

		// 1.构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("UTF-8");
		try {
			// 4.执行postMethod,调用http接口
			httpClient.executeMethod(postMethod);// 200
			// 5.读取内容
			responseMsg = postMethod.getResponseBodyAsString();
		} catch (HttpException e) {
			//e.printStackTrace();
			//zhrb-20190528-修复漏洞
		} catch (IOException e) {
			//e.printStackTrace();
			//zhrb-20190528-修复漏洞
		} finally {
			// 7.释放连接
			postMethod.releaseConnection();
		}
		return responseMsg;
	}
}