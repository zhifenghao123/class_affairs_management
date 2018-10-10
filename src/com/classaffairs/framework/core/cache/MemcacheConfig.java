/**
 * 
 */
package com.classaffairs.framework.core.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * @author Haozhifeng
 *
 */
@Repository
public class MemcacheConfig {
	private String servers;
	  private Integer[] weights;
	  private int initConn = 5;
	  private int minConn = 5;
	  private int maxConn = 50;
	  private int maxIdle = 21600000;
	  private int maintSleep = 30;
	  private boolean nagle = false;
	  private int socketTO = 3600;
	  private int socketConnectTO = 0;
	  
	  public String getServers()
	  {
	    return this.servers;
	  }
	  
	  public void setServers(String servers)
	  {
	    this.servers = StringUtils.strip(servers);
	  }
	  
	  public Integer[] getWeights()
	  {
	    return this.weights;
	  }
	  
	  public void setWeights(Integer[] weights)
	  {
	    this.weights = weights;
	  }
	  
	  public int getInitConn()
	  {
	    return this.initConn;
	  }
	  
	  public void setInitConn(int initConn)
	  {
	    this.initConn = initConn;
	  }
	  
	  public int getMinConn()
	  {
	    return this.minConn;
	  }
	  
	  public void setMinConn(int minConn)
	  {
	    this.minConn = minConn;
	  }
	  
	  public int getMaxConn()
	  {
	    return this.maxConn;
	  }
	  
	  public void setMaxConn(int maxConn)
	  {
	    this.maxConn = maxConn;
	  }
	  
	  public int getMaintSleep()
	  {
	    return this.maintSleep;
	  }
	  
	  public void setMaintSleep(int maintSleep)
	  {
	    this.maintSleep = maintSleep;
	  }
	  
	  public boolean isNagle()
	  {
	    return this.nagle;
	  }
	  
	  public void setNagle(boolean nagle)
	  {
	    this.nagle = nagle;
	  }
	  
	  public int getSocketTO()
	  {
	    return this.socketTO;
	  }
	  
	  public void setSocketTO(int socketTO)
	  {
	    this.socketTO = socketTO;
	  }
	  
	  public int getSocketConnectTO()
	  {
	    return this.socketConnectTO;
	  }
	  
	  public void setSocketConnectTO(int socketConnectTO)
	  {
	    this.socketConnectTO = socketConnectTO;
	  }
	  
	  public int getMaxIdle()
	  {
	    return this.maxIdle;
	  }
	  
	  public void setMaxIdle(int maxIdle)
	  {
	    this.maxIdle = maxIdle;
	  }
}
