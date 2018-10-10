package com.classaffairs.framework.sdp.orm.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class Sort implements Iterable<Sort.Order>, Serializable {

	private static final long serialVersionUID = 5737186511678863905L;
	  public static final Direction DEFAULT_DIRECTION = Direction.ASC;
	  private final List<Order> orders;
	  
	  public Sort(Order... orders)
	  {
	    this(Arrays.asList(orders));
	  }
	  
	  public Sort(List<Order> orders)
	  {
	    if ((orders == null) || (orders.isEmpty())) {
	      throw new IllegalArgumentException(
	        "You have to provide at least one sort property to sort by!");
	    }
	    this.orders = orders;
	  }
	  
	  public Sort(String... properties)
	  {
	    this(DEFAULT_DIRECTION, properties);
	  }
	  
	  public Sort(Direction direction, String... properties)
	  {
	    this(direction, properties == null ? new ArrayList() : Arrays.asList(properties));
	  }
	  
	  public Sort(Direction direction, List<String> properties)
	  {
	    if ((properties == null) || (properties.isEmpty())) {
	      throw new IllegalArgumentException(
	        "You have to provide at least one property to sort by!");
	    }
	    this.orders = new ArrayList(properties.size());
	    for (String property : properties) {
	      this.orders.add(new Order(direction, property));
	    }
	  }
	  
	  public Sort and(Sort sort)
	  {
	    if (sort == null) {
	      return this;
	    }
	    ArrayList<Order> these = new ArrayList(this.orders);
	    for (Order order : sort) {
	      these.add(order);
	    }
	    return new Sort(these);
	  }
	  
	  public Order getOrderFor(String property)
	  {
	    for (Order order : this) {
	      if (order.getProperty().equals(property)) {
	        return order;
	      }
	    }
	    return null;
	  }
	  
	  public Iterator<Order> iterator()
	  {
	    return this.orders.iterator();
	  }
	  
	  public boolean equals(Object obj)
	  {
	    if (this == obj) {
	      return true;
	    }
	    if (!(obj instanceof Sort)) {
	      return false;
	    }
	    Sort that = (Sort)obj;
	    
	    return this.orders.equals(that.orders);
	  }
	  
	  public int hashCode()
	  {
	    int result = 17;
	    result = 31 * result + this.orders.hashCode();
	    return result;
	  }
	  
	  public String toString()
	  {
	    return StringUtils.collectionToCommaDelimitedString(this.orders);
	  }
	  
	  public static enum Direction
	  {
	    ASC,  DESC;
	    
	    public static Direction fromString(String value)
	    {
	      try
	      {
	        return valueOf(value.toUpperCase(Locale.US));
	      }
	      catch (Exception e)
	      {
	        throw new IllegalArgumentException(
	          String.format(
	          "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", new Object[] {
	          value }), e);
	      }
	    }
	  }
	  
	  public static class Order
	    implements Serializable
	  {
	    private static final long serialVersionUID = 1522511010900108987L;
	    private final Sort.Direction direction;
	    private final String property;
	    
	    public Order(Sort.Direction direction, String property)
	    {
	      if (!StringUtils.hasText(property)) {
	        throw new IllegalArgumentException(
	          "Property must not null or empty!");
	      }
	      this.direction = (direction == null ? Sort.DEFAULT_DIRECTION : direction);
	      this.property = property;
	    }
	    
	    public Order(String property)
	    {
	      this(Sort.DEFAULT_DIRECTION, property);
	    }
	    
	    @Deprecated
	    public static List<Order> create(Sort.Direction direction, Iterable<String> properties)
	    {
	      List<Order> orders = new ArrayList();
	      for (String property : properties) {
	        orders.add(new Order(direction, property));
	      }
	      return orders;
	    }
	    
	    public Sort.Direction getDirection()
	    {
	      return this.direction;
	    }
	    
	    public String getProperty()
	    {
	      return this.property;
	    }
	    
	    public boolean isAscending()
	    {
	      return this.direction.equals(Sort.Direction.ASC);
	    }
	    
	    public Order with(Sort.Direction order)
	    {
	      return new Order(order, this.property);
	    }
	    
	    public Sort withProperties(String... properties)
	    {
	      return new Sort(this.direction, properties);
	    }
	    
	    public int hashCode()
	    {
	      int result = 17;
	      
	      result = 31 * result + this.direction.hashCode();
	      result = 31 * result + this.property.hashCode();
	      
	      return result;
	    }
	    
	    public boolean equals(Object obj)
	    {
	      if (this == obj) {
	        return true;
	      }
	      if (!(obj instanceof Order)) {
	        return false;
	      }
	      Order that = (Order)obj;
	      
	      return (this.direction.equals(that.direction)) && 
	        (this.property.equals(that.property));
	    }
	    
	    public String toString()
	    {
	      return String.format("%s: %s", new Object[] { this.property, this.direction });
	    }
	  }
}
