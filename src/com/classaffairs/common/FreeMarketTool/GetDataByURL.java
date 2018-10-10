/**
 * 
 */
package com.classaffairs.common.FreeMarketTool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.classaffairs.common.GetIp;
import com.classaffairs.framework.core.utils.Log;

/**
 * @author Haozhifeng
 *
 */
public class GetDataByURL {
	public static String getHtml(String urlString) {
		String webappPath = GetIp.class.getResource("").getPath().replace("/WEB-INF/classes/com/classaffairs/common", "");
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			StringBuffer html = new StringBuffer();
			FileInputStream file = new FileInputStream(webappPath + urlString);
			isr = new InputStreamReader(file, "UTF-8");
			br = new BufferedReader(isr);
			String temp;
			while ((temp = br.readLine()) != null) {
				html.append(temp);
			}
			int start = html.toString().indexOf("<div id=\"static_introduce\">");
			int end = html.toString().lastIndexOf("</div>");
			return html.toString().substring(start, end);
					 
		} catch (Exception e) {
			Log.log.error("通过html文件抓取内容异常com.classaffairs.common.FreeMarkertTool.GetDataByURL");
			e.printStackTrace();
			return null;
		} finally {
			try {
				br.close();
				isr.close();
			} catch (Exception ee) {
				Log.log.info("关闭文件异常com.classaffairs.common.FreeMarkertTool.GetDataByURL");
				ee.printStackTrace();
				return null;
			}
		}
	}
	public static String Html2Text(String inputString){
	     String htmlStr = inputString; //含html标签的字符串
	     String textStr ="";
	     java.util.regex.Pattern p_script;
	     java.util.regex.Matcher m_script;
	     java.util.regex.Pattern p_style;
	     java.util.regex.Matcher m_style;
	     java.util.regex.Pattern p_html;
	     java.util.regex.Matcher m_html;
	    try{
	          String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }
	          String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }
	          String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
	          p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
	          m_script = p_script.matcher(htmlStr);
	          htmlStr = m_script.replaceAll(""); //过滤script标签

	          p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
	          m_style = p_style.matcher(htmlStr);
	          htmlStr = m_style.replaceAll(""); //过滤style标签

	          p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
	          m_html = p_html.matcher(htmlStr);
	          htmlStr = m_html.replaceAll(""); //过滤html标签
	          htmlStr=htmlStr.replace("&nbsp;","");//过滤前面空格
			  // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
			  // 清除掉所有特殊字符
//			       String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
	          String regEx="[/ ]"; 
	          Pattern p = Pattern.compile(regEx);
			       Matcher m = p.matcher(htmlStr);
			       textStr = m.replaceAll("").trim();
	          //textStr = htmlStr.trim();
	     }catch(Exception e){
	     }
	     return textStr;//返回文本字符串
	 }   
}
