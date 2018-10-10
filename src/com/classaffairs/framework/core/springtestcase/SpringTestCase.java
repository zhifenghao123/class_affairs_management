package com.classaffairs.framework.core.springtestcase;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.classaffairs.framework.core.init.SpringContextHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
public class SpringTestCase extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private ApplicationContext applicationContext;
	
	@Before
	public void init(){
	    SpringContextHelper.setApplicationContext(this.applicationContext);
	}
	  
	@After
	public void destory() {}
}
