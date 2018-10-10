/**
 * 
 */
package com.classaffairs.framework.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * @author Haozhifeng
 *
 */
public class DateUtil {

	public static final long DAY_SECONDS = 86400000L;
	
	public static java.util.Date strToDate(String str){
		java.util.Date result = new java.util.Date();
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    try{
	      result = format.parse(str);
	    }catch (ParseException e){
	      Logger log = Logger.getLogger(DateUtil.class);
	      log.debug("字符串转换成java.util.Date时，出错:字符串：" + str);
	      throw new NumberFormatException("日期字符串(" + str + 
	        ")的格式不正确，应以中杠分隔（2011-01-06）");
	    }
	    return result;
	  }
	  
	  public static java.util.Date strToDetailDate(String str)
	  {
	    java.util.Date result = new java.util.Date();
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    try
	    {
	      result = format.parse(str);
	    }
	    catch (ParseException e)
	    {
	      Logger log = Logger.getLogger(DateUtil.class);
	      log.debug("字符串转换成java.util.Date时，出错:字符串：" + str);
	      throw new NumberFormatException("日期字符串(" + str + 
	        ")的格式不正确，应以中杠分隔（2006-11-06）");
	    }
	    return result;
	  }
	  
	  public static String formatDate(java.util.Date date, String sdfStr)
	  {
	    if (date == null) {
	      return null;
	    }
	    if ((sdfStr == null) || ("".equals(sdfStr)))
	    {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	      String nowDate = sdf.format(date);
	      return nowDate;
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat(sdfStr);
	    String nowDate = sdf.format(date);
	    return nowDate;
	  }
	  
	  public static String formatDate(String datestr, String sdfStr)
	    throws ParseException
	  {
	    if (datestr == null) {
	      return null;
	    }
	    if ((sdfStr == null) || ("".equals(sdfStr)))
	    {
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	      java.util.Date date = sdf.parse(datestr);
	      String nowDate = sdf.format(date);
	      return nowDate;
	    }
	    SimpleDateFormat sdf = new SimpleDateFormat(sdfStr);
	    java.util.Date date = sdf.parse(datestr);
	    String nowDate = sdf.format(date);
	    return nowDate;
	  }
	  
	  public static java.util.Date formatDateToDate(java.util.Date date, String sdfStr)
	  {
	    if (date == null) {
	      return null;
	    }
	    try
	    {
	      if ((sdfStr == null) || ("".equals(sdfStr)))
	      {
	        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        String nowDate = sdf.format(date);
	        return sdf.parse(nowDate);
	      }
	      SimpleDateFormat sdf = new SimpleDateFormat(sdfStr);
	      String nowDate = sdf.format(date);
	      return sdf.parse(nowDate);
	    }
	    catch (ParseException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }
	  
	  public static java.util.Date formatDateToDate(String datestr, String sdfStr)
	  {
	    if (datestr == null) {
	      return null;
	    }
	    try
	    {
	      if ((sdfStr == null) || ("".equals(sdfStr)))
	      {
	        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        java.util.Date date = sdf.parse(datestr);
	        String nowDate = sdf.format(date);
	        return sdf.parse(nowDate);
	      }
	      SimpleDateFormat sdf = new SimpleDateFormat(sdfStr);
	      java.util.Date date = sdf.parse(datestr);
	      String nowDate = sdf.format(date);
	      return sdf.parse(nowDate);
	    }
	    catch (ParseException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }
	  
	  public static java.sql.Date getNowSqlDate()
	  {
	    java.util.Date date = new java.util.Date();
	    java.sql.Date result = new java.sql.Date(date.getTime());
	    return result;
	  }
	  
	  public static java.sql.Date utilDateToSqlDate(java.util.Date date)
	  {
	    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String str = sdf.format(date);
	    java.util.Date timer = strToDate(str);
	    java.sql.Date result = new java.sql.Date(timer.getTime());
	    return result;
	  }
	  
	  public static java.sql.Date strToSQLDate(String str)
	  {
	    java.util.Date temp = strToDate(str);
	    java.sql.Date result = new java.sql.Date(temp.getTime());
	    return result;
	  }
	  
	  public static String dateToStr(java.util.Date date)
	  {
	    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    return format.format(date);
	  }
	  
	  public static java.util.Date addDay(java.util.Date orig, int addDay)
	  {
	    java.util.Date result = new java.util.Date(orig.getTime() + addDay * 86400000L);
	    return result;
	  }
	  
	  public static java.util.Date subDay(java.util.Date orig, int subDay)
	  {
	    java.util.Date result = new java.util.Date(orig.getTime() - subDay * 86400000L);
	    return result;
	  }
	  
	  public static int getSubDay(java.util.Date end, java.util.Date start)
	  {
	    int result = (int)((end.getTime() - start.getTime()) / 86400000L);
	    return result;
	  }
	  
	  public static String getDateString(String dateStr, int count, String type)
	    throws Exception
	  {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	    java.util.Date date = null;
	    String dateString = "";
	    try
	    {
	      date = df.parse(dateStr);
	    }
	    catch (Exception ex)
	    {
	      throw new Exception("传入的时间格式有误！" + ex.getMessage());
	    }
	    long nowTime = count * 24 * 60 * 60 * 1000;
	    try
	    {
	      if ("AFFTER".equals(type)) {
	        nowTime = date.getTime() + nowTime;
	      } else if ("FORWARD".equals(type)) {
	        nowTime = date.getTime() - nowTime;
	      }
	      dateString = df.format(Long.valueOf(nowTime));
	    }
	    catch (Exception ex)
	    {
	      throw new Exception("时间转化时出错！", ex);
	    }
	    return dateString;
	  }
	  
	  public static java.util.Date getDateString(java.util.Date date, int count, String type)
	    throws Exception
	  {
	    long nowTime = count * 24 * 60 * 60 * 1000;
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	    if ("AFFTER".equals(type)) {
	      nowTime = date.getTime() + nowTime;
	    } else if ("FORWARD".equals(type)) {
	      nowTime = date.getTime() - nowTime;
	    }
	    try
	    {
	      date = df.parse(df.format(Long.valueOf(nowTime)));
	    }
	    catch (Exception ex)
	    {
	      throw new Exception("时间转化时出错！", ex);
	    }
	    return date;
	  }
	  
	  public static int getTimeDifferenceBetweenGivenTimeAndCurrentTime(java.util.Date givenTime)
	  {
	    Calendar cal = Calendar.getInstance();
	    if (cal.before(givenTime)) {
	      throw new IllegalArgumentException(
	        "错误:给定时间早于现在时间!");
	    }
	    int yearNow = cal.get(1);
	    int monthNow = cal.get(2);
	    int dayOfMonthNow = cal.get(5);
	    
	    cal.setTime(givenTime);
	    
	    int yearGiven = cal.get(1);
	    int monthGiven = cal.get(2);
	    int dayOfMonthGiven = cal.get(5);
	    
	    int diff = yearNow - yearGiven;
	    if (monthNow <= monthGiven) {
	      if (monthNow == monthGiven)
	      {
	        if (dayOfMonthNow < dayOfMonthGiven) {
	          diff--;
	        }
	      }
	      else {
	        diff--;
	      }
	    }
	    return diff;
	  }
	  
	  public static int getTimeDifferenceBetweenGivenTimeAndCurrentTime(String givenTime)
	    throws Exception
	  {
	    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date givenDate = null;
	    try
	    {
	      givenDate = df.parse(givenTime);
	    }
	    catch (Exception ex)
	    {
	      throw new Exception("传入的时间格式有误！" + ex.getMessage());
	    }
	    Calendar cal = Calendar.getInstance();
	    if (cal.before(givenDate)) {
	      throw new IllegalArgumentException(
	        "错误:给定时间早于现在时间!");
	    }
	    int yearNow = cal.get(1);
	    int monthNow = cal.get(2);
	    int dayOfMonthNow = cal.get(5);
	    
	    cal.setTime(givenDate);
	    
	    int yearGiven = cal.get(1);
	    int monthGiven = cal.get(2);
	    int dayOfMonthGiven = cal.get(5);
	    
	    int diff = yearNow - yearGiven;
	    if (monthNow <= monthGiven) {
	      if (monthNow == monthGiven)
	      {
	        if (dayOfMonthNow < dayOfMonthGiven) {
	          diff--;
	        }
	      }
	      else {
	        diff--;
	      }
	    }
	    return diff;
	  }
	  
	  public static String getNowDate()
	  {
	    java.util.Date date = new java.util.Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    String nowDate = sdf.format(date);
	    return nowDate;
	  }
	  
	  public static int getYear(java.util.Date date)
	  {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    return c.get(1);
	  }
	  
	  public static int getMonth(java.util.Date date)
	  {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    return c.get(2) + 1;
	  }
	  
	  public static int getDay(java.util.Date date)
	  {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    return c.get(5);
	  }
	  
	  public static int getHour(java.util.Date date)
	  {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    return c.get(11);
	  }
	  
	  public static int getMinute(java.util.Date date)
	  {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    return c.get(12);
	  }
	  
	  public static int getSecond(java.util.Date date)
	  {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    return c.get(13);
	  }
	  
	  public static long getMillis(java.util.Date date)
	  {
	    Calendar c = Calendar.getInstance();
	    c.setTime(date);
	    return c.getTimeInMillis();
	  }
	
}
