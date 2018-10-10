/**
 * 
 */
package com.classaffairs.framework.core.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Update;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * @author Haozhifeng
 *
 */
public class ConvertObject {
	public static String BeanToJson(Object bean)
	  {
	    try
	    {
	      Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	      




	      return gson.toJson(bean);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      Log.log.error(e);
	    }
	    return null;
	  }
	  
	  public static Object JsonToBean(String json, Class type)
	  {
	    Gson gson = new Gson();
	    return gson.fromJson(json, type);
	  }
	  
	  public static Object getJsonValue(String json, String param)
	  {
	    JsonObject root = new JsonParser().parse(json).getAsJsonObject();
	    return root.get(param).getAsString();
	  }
	  
	  public static Object mapToBean(Class type, Map map)
	    throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException
	  {
	    BeanInfo beanInfo = Introspector.getBeanInfo(type);
	    Object obj = type.newInstance();
	    

	    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	    for (int i = 0; i < propertyDescriptors.length; i++)
	    {
	      PropertyDescriptor descriptor = propertyDescriptors[i];
	      String propertyName = descriptor.getName();
	      if (map.containsKey(propertyName))
	      {
	        Object value = map.get(propertyName);
	        
	        Object[] args = new Object[1];
	        args[0] = value;
	        
	        descriptor.getWriteMethod().invoke(obj, args);
	      }
	    }
	    return obj;
	  }
	  
	  public static Map beantoMap(Object entity)
	    throws IntrospectionException, IllegalAccessException, InvocationTargetException
	  {
	    Class type = entity.getClass();
	    Map returnMap = new HashMap();
	    BeanInfo beanInfo = Introspector.getBeanInfo(type);
	    
	    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	    for (int i = 0; i < propertyDescriptors.length; i++)
	    {
	      PropertyDescriptor descriptor = propertyDescriptors[i];
	      String propertyName = descriptor.getName();
	      if (!propertyName.equals("class"))
	      {
	        Method readMethod = descriptor.getReadMethod();
	        Object result = readMethod.invoke(entity, new Object[0]);
	        if (result != null) {
	          returnMap.put(propertyName, result);
	        } else {
	          returnMap.put(propertyName, "");
	        }
	      }
	    }
	    return returnMap;
	  }
	  
	  public static DBObject beanToDBObject(Object entity)
	    throws IntrospectionException, IllegalAccessException, InvocationTargetException
	  {
	    Class type = entity.getClass();
	    BasicDBObject bdbo = new BasicDBObject();
	    BeanInfo beanInfo = Introspector.getBeanInfo(type);
	    
	    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	    for (int i = 0; i < propertyDescriptors.length; i++)
	    {
	      PropertyDescriptor descriptor = propertyDescriptors[i];
	      String propertyName = descriptor.getName();
	      if (!propertyName.equals("class"))
	      {
	        Method readMethod = descriptor.getReadMethod();
	        Object result = readMethod.invoke(entity, new Object[0]);
	        if (result != null) {
	          bdbo.put(propertyName, result);
	        } else {
	          bdbo.put(propertyName, "");
	        }
	      }
	    }
	    return bdbo;
	  }
	  
	  public static Update beanToUpdate(Object entity)
	    throws IntrospectionException, IllegalAccessException, InvocationTargetException
	  {
	    Class type = entity.getClass();
	    Update update = new Update();
	    BeanInfo beanInfo = Introspector.getBeanInfo(type);
	    
	    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
	    for (int i = 0; i < propertyDescriptors.length; i++)
	    {
	      PropertyDescriptor descriptor = propertyDescriptors[i];
	      String propertyName = descriptor.getName();
	      if (!propertyName.equals("class"))
	      {
	        Method readMethod = descriptor.getReadMethod();
	        Object result = readMethod.invoke(entity, new Object[0]);
	        if (result != null) {
	          update.set(propertyName, result);
	        } else {
	          update.set(propertyName, "");
	        }
	      }
	    }
	    return update;
	  }
}
