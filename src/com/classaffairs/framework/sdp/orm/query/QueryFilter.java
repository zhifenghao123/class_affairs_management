package com.classaffairs.framework.sdp.orm.query;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class QueryFilter implements Serializable {
	private static final long serialVersionUID = 1L;
	  public static final String AND_STRING = " AND ";
	  public static final String OR_STRING = " OR ";
	  public static final String EQ = "=";
	  public static final String LIKE = "like";
	  public static final String LT = "<";
	  public static final String GT = ">";
	  public static final String LE = "<=";
	  public static final String GE = ">=";
	  public static final String S = "S";
	  public static final String I = "I";
	  public static final String L = "L";
	  public static final String N = "N";
	  public static final String D = "D";
	  public static final String B = "B";
	  public static final String K = "K";
	  public static final String T = "T";
	  private String name;
	  private Object value;
	  private String operators;
	  
	  public String getName(){
	    return this.name;
	  }
	  
	  public void setName(String name){
	    this.name = name;
	  }
	  
	  public Object getValue(){
	    return this.value;
	  }
	  
	  public void setValue(Object value){
	    this.value = value;
	  }
	  
	  public String getOperators(){
	    return this.operators;
	  }
	  
	  public void setOperators(String operators){
	    this.operators = operators;
	  }
	  
	  public String toString(){
	    return 
	      "QueryFilter [name=" + this.name + ", value=" + this.value + ", operators=" + this.operators + "]";
	  }
	  
	  public static String conditionAnd(List<QueryFilter> paramFilters){
	    if ((paramFilters == null) || (paramFilters.isEmpty())) {
	      return null;
	    }
	    StringBuilder builder = new StringBuilder();
	    for (QueryFilter queryFilter : paramFilters) {
	      if ((!StringUtils.isBlank(queryFilter.getOperators())) && 
	        (queryFilter.getValue() != null) && (
	        (queryFilter.getOperators().contains("EQ")) || 
	        (queryFilter.getOperators().contains("LIKE")) || 
	        (queryFilter.getOperators().contains("LT")) || 
	        (queryFilter.getOperators().contains("GT")) || 
	        (queryFilter.getOperators().contains("LE")) || 
	        (queryFilter.getOperators().contains("GE")))) {
	        builder.append(" ").append(queryFilter.getName()).append(" ").append(processOperator(queryFilter)).append("'").append(queryFilter.getValue()).append("'").append(" AND ");
	      }
	    }
	    String reusltSql = builder.toString();
	    return (reusltSql != null) && (!"".equals(reusltSql)) ? 
	      reusltSql.substring(0, reusltSql.lastIndexOf(" AND ")) : "";
	  }
	  
	  public static String conditionAnd(String paramFilters){
	    if ((paramFilters == null) || (paramFilters.isEmpty())) {
	      return null;
	    }
	    List<QueryFilter> queryFilter = JSONObject.parseArray(paramFilters, QueryFilter.class);
	    return conditionAnd(queryFilter);
	  }
	  
	  public static String conditionOr(List<QueryFilter> paramFilters){
	    if ((paramFilters == null) || (paramFilters.isEmpty())) {
	      return null;
	    }
	    StringBuilder builder = new StringBuilder();
	    for (QueryFilter queryFilter : paramFilters) {
	      if ((!StringUtils.isBlank(queryFilter.getOperators())) && 
	        (queryFilter.getValue() != null) && (
	        (queryFilter.getOperators().contains("EQ")) || 
	        (queryFilter.getOperators().contains("LIKE")) || 
	        (queryFilter.getOperators().contains("LT")) || 
	        (queryFilter.getOperators().contains("GT")) || 
	        (queryFilter.getOperators().contains("LE")) || 
	        (queryFilter.getOperators().contains("GE")))) {
	        builder.append(" ").append(queryFilter.getName()).append(" ").append(processOperator(queryFilter)).append("'").append(queryFilter.getValue()).append("'").append(" OR ");
	      }
	    }
	    String reusltSql = builder.toString();
	    return (reusltSql != null) && (!"".equals(reusltSql)) ? 
	      reusltSql.substring(0, reusltSql.lastIndexOf(" OR ")) : "";
	  }
	  
	  public static String conditionOr(String paramFilters) {
	    if ((paramFilters == null) || (paramFilters.isEmpty())) {
	      return null;
	    }
	    List<QueryFilter> queryFilter = JSONObject.parseArray(paramFilters, QueryFilter.class);
	    return conditionOr(queryFilter);
	  }
	  
	  private static String processOperator(QueryFilter queryFilter){
	    String operator = queryFilter.getOperators();
	    StringBuilder sql = new StringBuilder();
	    if (operator.contains("EQ")) {
	      sql.append("=").append(" ");
	    } else if (operator.contains("LIKE")) {
	      sql.append("like").append(" ");
	    } else if (operator.contains("LT")) {
	      sql.append("<").append(" ");
	    } else if (operator.contains("GT")) {
	      sql.append(">").append(" ");
	    } else if (operator.contains("LE")) {
	      sql.append("<=").append(" ");
	    } else if (operator.contains("GE")) {
	      sql.append(">=").append(" ");
	    }
	    return sql.toString();
	  }
	  
	  public static String unifyTypeCondition(List<QueryFilter> paramFilters, String type){
	    String resultSql = null;
	    if (" OR ".trim().equalsIgnoreCase(type)) {
	      resultSql = conditionOr(paramFilters);
	    } else {
	      resultSql = conditionAnd(paramFilters);
	    }
	    return resultSql;
	  }
}
