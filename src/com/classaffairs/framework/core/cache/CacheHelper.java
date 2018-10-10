package com.classaffairs.framework.core.cache;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.core.utils.Log;
import com.danga.MemCached.MemCachedClient;

@Repository
public class CacheHelper {
	private static String cacheName = "COMMON_CACHED";
	  private static long expiry = 1296000000L;
	  
	  private static MemCachedClient getCache(String cacheName)
	  {
	    Log.log.debug("get cacheName '" + cacheName + "' cachedClient.");
	    
	    ApplicationContext ac = SpringContextHelper.getApplicationContext();
	    MemcachedManager memcachedManager = (MemcachedManager)ac.getBean(MemcachedManager.class);
	    return memcachedManager.getCache(cacheName);
	  }
	  
	  public static boolean put(Object key, Object value)
	  {
	    return put(cacheName, key, value);
	  }
	  
	  public static boolean delete(Object key)
	  {
	    return delete(cacheName, key);
	  }
	  
	  public static boolean clean()
	  {
	    return clean(cacheName);
	  }
	  
	  public static Object get(Object key)
	  {
	    return get(cacheName, key);
	  }
	  
	  public static boolean put(String cacheName, Object key, Object value)
	  {
	    return put(cacheName, key, value, expiry);
	  }
	  
	  public static boolean delete(String cacheName, Object key)
	  {
	    Log.log.debug("delete data in cache：cacheName=" + cacheName + ">>key=" + key);
	    return getCache(cacheName).delete(getKey(cacheName, key));
	  }
	  
	  public static boolean clean(String cacheName)
	  {
	    Log.log.debug("clean cache：cacheName=" + cacheName);
	    return getCache(cacheName).flushAll();
	  }
	  
	  public static Object get(String cacheName, Object key)
	  {
	    Log.log.debug("get data in cache：cacheName=" + cacheName + ">>key=" + key);
	    return getCache(cacheName).get(getKey(cacheName, key));
	  }
	  
	  public static boolean put(String cacheName, Object key, Object value, long expiry)
	  {
	    Log.log.debug("put data in cache：cacheName=" + cacheName + ">>key=" + key + ">>value=" + value);
	    String keyStr = getKey(cacheName, key);
	    if ((keyStr == null) || ("".equals(keyStr.trim())))
	    {
	      Log.log.info("cache key is null, data not cached!");
	      return false;
	    }
	    return getCache(cacheName).set(keyStr, value, new Date(expiry));
	  }
	  
	  public static void testSet(String key, Object object)
	  {
	    MemCachedClient mcc = getCache(cacheName);
	    mcc.add(key, object);
	  }
	  
	  public static long incr(Object key)
	  {
	    return incr(key, 1L);
	  }
	  
	  public static long incr(Object key, long inc)
	  {
	    return incr(cacheName, key, inc);
	  }
	  
	  public static long incr(String cacheName, Object key)
	  {
	    return incr(cacheName, key, 1L);
	  }
	  
	  public static long incr(String cacheName, Object key, long inc)
	  {
	    Log.log.debug("incr value in cache：cacheName=" + cacheName + ">>key=" + key + ">>inc=" + inc);
	    return getCache(cacheName).addOrIncr(getKey(cacheName, key), inc);
	  }
	  
	  public static long decr(Object key)
	  {
	    return decr(key, 1L);
	  }
	  
	  public static long decr(Object key, long inc)
	  {
	    return decr(cacheName, key, inc);
	  }
	  
	  public static long decr(String cacheName, Object key)
	  {
	    return decr(cacheName, key, 1L);
	  }
	  
	  public static long decr(String cacheName, Object key, long inc)
	  {
	    Log.log.debug("decr value in cache：cacheName=" + cacheName + ">>key=" + key + ">>inc=" + inc);
	    return getCache(cacheName).addOrDecr(getKey(cacheName, key), inc);
	  }
	  
	  private static String getKey(String cacheName, Object key)
	  {
	    Log.log.debug("real cacheKey：cacheName=" + cacheName + ">>key=" + key);
	    if (key == null) {
	      return null;
	    }
	    String keyStr = String.valueOf(key);
	    if ((keyStr == null) || ("".equals(keyStr.trim()))) {
	      return null;
	    }
	    return cacheName + "@" + keyStr;
	  }
}
