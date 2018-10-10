/**
 * 
 */
package com.classaffairs.test;

import org.junit.Test;

import com.classaffairs.framework.core.springtestcase.SpringTestCase;

/**
 * @author Haozhifeng
 *
 */
public class Exercise extends SpringTestCase {
	@Test
	public void StringLowerUpperExchange(){
		String Mongo_db = "Mong_db";
		String result = Mongo_db.toLowerCase();
		System.out.println(result);
	}
}
