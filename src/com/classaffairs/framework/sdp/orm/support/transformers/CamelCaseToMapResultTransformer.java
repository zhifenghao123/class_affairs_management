package com.classaffairs.framework.sdp.orm.support.transformers;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

public class CamelCaseToMapResultTransformer extends
		AliasedTupleSubsetResultTransformer {
	
	private static final long serialVersionUID = 1L;
	  public static final CamelCaseToMapResultTransformer INSTANCE = new CamelCaseToMapResultTransformer();

	public Object transformTuple(Object[] tuple, String[] aliases){
		Map result = new HashMap(tuple.length);
	    for (int i = 0; i < tuple.length; i++){
	    	String alias = aliases[i];
	    	if (alias != null){
	    		alias = CamelCaseUtil.toCamelCase(alias);
	    		result.put(alias, tuple[i]);
	    	}
	    }
	    return result;
	}
	
	private Object readResolve(){
	    return INSTANCE;
	  }
	
	@Override
	public boolean isTransformedValueATupleElement(String[] arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
