/**
 * 
 */
package com.classaffairs.framework.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.classaffairs.framework.core.utils.Log;
import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * @author Haozhifeng
 *
 */
@Repository
public class MemcachedManager {
	  private Map<String, MemcacheConfig> configs;
	  private Map<String, SockIOPool> pools;
	  private Map<String, MemCachedClient> caches;
	  
	  public void setConfigs(Map<String, MemcacheConfig> configs)
	  {
	    this.configs = configs;
	  }
	  
	  private void getPoolInstance(String cacheName)
	  {
	    if ((cacheName == null) || ("".equals(cacheName.trim()))) {
	      cacheName = "default";
	    }
	    if (this.pools == null) {
	      this.pools = new HashMap();
	    }
	    if (!this.configs.containsKey(cacheName)) {
	      return;
	    }
	    if ((this.pools.isEmpty()) && (!this.pools.containsKey(cacheName))) {
	      try
	      {
	        MemcacheConfig config = (MemcacheConfig)this.configs.get(cacheName);
	        SockIOPool pool = SockIOPool.getInstance(cacheName);
	        pool.setServers(StringUtils.split(config.getServers(), ","));
	        if (!pool.isInitialized())
	        {
	          pool.setInitConn(config.getInitConn());
	          pool.setMinConn(config.getMinConn());
	          pool.setMaxConn(config.getMaxConn());
	          pool.setMaintSleep(config.getMaintSleep());
	          pool.setMaxIdle(config.getMaxIdle());
	          pool.setNagle(config.isNagle());
	          pool.setSocketTO(config.getSocketTO());
	          pool.setSocketConnectTO(config.getSocketConnectTO());
	          pool.setHashingAlg(3);
	          pool.initialize();
	        }
	        this.pools.put(cacheName, pool);
	      }
	      catch (Exception ex)
	      {
	        Log.log.error(ex.getMessage());
	      }
	    }
	  }
	  
	  public MemCachedClient getCache(String cacheName)
	  {
	    if (this.caches == null) {
	      this.caches = new HashMap();
	    }
	    if (!this.caches.containsKey(cacheName)) {
	      getPoolInstance(cacheName);
	    }
	    MemCachedClient mcc;
	   // MemCachedClient mcc;
	    if ((!this.caches.isEmpty()) && (this.caches.containsKey(cacheName)))
	    {
	      mcc = (MemCachedClient)this.caches.get(cacheName);
	    }
	    else
	    {
	      mcc = new MemCachedClient(cacheName);
	      if (this.configs.containsKey(cacheName)) {
	        this.caches.put(cacheName, mcc);
	      } else {
	        Log.log.warn("名称为：" + cacheName + "的客户端配置不存在！");
	      }
	    }
	    return mcc;
	  }
	  
	  public void destroy()
	  {
	    if (this.caches != null)
	    {
	      Set<String> cachess = this.caches.keySet();
	      for (String key : cachess)
	      {
	        MemCachedClient mcc = (MemCachedClient)this.caches.get(key);
	        mcc.flushAll();
	        mcc = null;
	      }
	    }
	    if (this.pools != null)
	    {
	      Set<String> poolss = this.pools.keySet();
	      for (String key : poolss)
	      {
	        SockIOPool pool = (SockIOPool)this.pools.get(key);
	        pool.shutDown();
	        pool = null;
	      }
	    }
	    if (this.configs != null)
	    {
	      this.configs.clear();
	      this.configs = null;
	    }
	    Log.log.debug("memcached manager closed");
	  }
}
