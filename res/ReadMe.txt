一、项目简介
	班级事务管理系统
二、开发环境
	jdk:jdk 1.7.0_25
	tomcat: apache-tomcat-7.0.47-windows-x86
	Eclipse Java EE IDE for Web Developers.   Version: Kepler Service Release 2   Build id: 20140224-0627
	Mysql Server 5.6
三、开发
	1、项目框架
		SSM整合。	Spring MVC是Web框架（jsp/action）。 Spring是容器框架，用于配置bean，并维护bean之间关系的框架。 	Mybatis是ORM，处于持久层。
	2、jar包解释
		（1）、spring-core.jar
			spring-core.jar 这个jar文件包含Spring框架基本的核心工具类。Spring其它的组件要都要使用到这个包里的类，是其它组件基本核心，当然你也可以在自己的系统中使用这些工具类。外部依赖(Commons Logging, Log4J)
		
		（2）、spring-beans.jar
			spring-beans.jar 这个jar文件是所有应用都要使用到的，它包含访问配置文件，创建和管理bean以及进行Inversion of Control / Dependency（IoC/DI）操作相关的所有类。
		
		（3）、spring-context.jar
			这个jar文件为spring核心提供了大量的扩展。可以找到使用spring ApplicationContext 特性中所需要的全部的类，JDNI所需的全部类，UI方面的用来与模板引擎如Velocity 、freeMarker集成的类，以及校验Validation 方面的相关类。外部依赖spring-beans, spring-aop. 。
		
		（4）、spring-aop.jar
			这个jar文件包含在应用中使用Spring的AOP特性时所需的类和源码级元数据的支持。使用基于AOP的Spring的特性，如声明型事务管理(Declaritive Transaction Management),也要在应用里包含这个jar包。外部依赖(spring-core, spring-bean , AOP Alliance, CGLIB, Commons Attributes).
		
		（5）、spring-expression.jar
			spring表达式语言
		
		（6）、spring-jdbc.jar
			这个jar文件包含Spring对JDBC数据访问进行封装的所有类。外部依赖spring-beans, spring-dao.
		
		（7）、spring-tx.jar
			spring提供对事物的支持，事物的相关处理以及实现类就在这个jar包中。
		
		（8）、spring-test.jar
		
			对Junit等测试框架的简单封装
		（9）、spring-web.jar
			这个jar文件包含Web应用开发时，用到Spring框架时所需的核心类，包括自动载入Web Application Context特性的类，Struts与JSF集成类，文件上传的支持类，Filter类和大量工具辅助类。外部依赖spring-context, Servlet API,(JSP API, JSTL, Commons FileUpload, COS).
		
		（10）、spring-webmvc.jar
			这个jar文件包含Spring MVC框架相关的所有类。包括框架的Servlets, Web MVC框架，控制器和视图支持。当然，如果你的应用使用了独立的框架，则无需这个JAR文件里的任何类。外部依赖spring-web, sping-support, Tiles, iText,POI.
		
		（11）、mybatis.jar
			MyBatis的核心包
		
		（12）、mybatis-spring.jar
			MyBatis和spring的整合包
		
		（13）、mysql-connector-java-5.1.26-bin.jar
			MySql驱动包
		
		（14）、commons-dbcp.jar
			dbcp数据库连接池的包
		
		（15）、commons-pool.jar
			dbcp数据库连接池的包
		
		（16）、log4j-1.2.17.jar
			目前应用最广泛的日志控件，可以配置文件log4j.properties结合在一起使用，通过配置文件配置日志的输出端，输出样式等。
		
		（17）、commons-logging-1.1.1.jar
			commons-logging能够选择使用Log4j还是JDK Logging，但不依赖于Log4j、JDK Logging的API。如果项目的classpath中包含log4j的类库，则使用Log4j，否则使用JDK Logging。使用commons-logging能够灵活地选择使用哪种日志，而不需要修改源代码。commons-logging的使用类似于Log4j，他们的级别及使用规则完全一样。
			
			Commons-logging+log4j : 经典的一个日志实现方案。直接使用log4j 即可满足我们的日志方案。但是一般为了避免直接依赖具体的日志实现，一般都是结合commons-logging 来实现。
			如果有Log4j，commons-logging会把输出原封不动的交给Log4j，如果没有则相应的输出转化为JDK Logging的输出。默认地，commons-logging会自动检查是否使用Log4j。也可以使用配置文件显式地启用log4j。配置问价为commons-logging.properties，放到classpath下即可。
		
		（18）、aopalliance.jar
			AOP Alliance(http://aopalliance.sourceforge.net/)是个联合的开源协作组织，在多个项目间进行协作以期提供一套标准的AOP Java接口（interface）。 Spring AOP就是基于AOP Alliance标准API实现的。如果你打算使用Spring的AOP或基于AOP的任何特性，只需这个JAR文件。
		
		（19）、aspectjrt.jar
			处理事务和AOP所需的包
		
		（20）、aspectjweaver.jar 
			处理事务和AOP所需的包
		
		（21）、jstl-1.2.jar
			JSP标准标签库（JSTL）是一个JSP标签集合，它封装了JSP应用的通用核心功能。JSTL支持通用的、结构化的任务，比如迭代，条件判断，XML文档操作，国际化标签，SQL标签。 除了这些，它还提供了一个框架来使用集成JSTL的自定义标签。
		
		（22）、gson-1.5.jar
			json
		
		（23）、spring-data-mongodb-1.5.0.RELEASE.jar
			
		（24）、guava-17.0.jar
			jar包中包含有com.google.common.collect.Lists;
		
		（25）、mongo-java-driver-2.13.0.jar
			 com.mongodb.MongoException
		
		（26）、commons-lang3-3.3.2.jar
		
			org.apache.commons.lang3.ArrayUtils; org.apache.commons.lang3.StringUtils;
		
		（27）、fastjson-1.2.4.jar
			com.alibaba.fastjson.JSONObject;
			
			
		
		(28)、spring-security-config-3.0.3.RELEASE.jar
		
		(29)、spring-security-core-3.0.3.RELEASE.jar
		
		(30)、spring-security-web-3.0.3.RELEASE.jar
		
		（31）commons-fileupload-1.3.1.jar
		
		（32）hibernate-core-4.3.4.Final.jar
		
		（33）cglib-2.2.2.jar
			缺少此包，会报错：
			Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.classaffairs.common.security.ClassAffairFilterInvocationSecurityMetadataSource]: Constructor threw exception; nested exception is org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.PersistenceException: 
			### Error querying database.  Cause: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
			### The error may exist in file [G:\Explorer_Haozhifeng\workspace\class_affairs_management\WebContent\WEB-INF\classes\com\classaffairs\entity\ApplicationMapper.xml]
			### The error may involve com.classaffairs.entity.Application.findAll
			### The error occurred while handling results
			### SQL: select * from application where type=1
			### Cause: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
				at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:163)
				at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:122)
				at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:267)
				... 48 more
			Caused by: org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.exceptions.PersistenceException: 
			### Error querying database.  Cause: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
			### The error may exist in file [G:\Explorer_Haozhifeng\workspace\class_affairs_management\WebContent\WEB-INF\classes\com\classaffairs\entity\ApplicationMapper.xml]
			### The error may involve com.classaffairs.entity.Application.findAll
			### The error occurred while handling results
			### SQL: select * from application where type=1
			### Cause: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
				at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:75)
				at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:371)
				at com.sun.proxy.$Proxy17.selectList(Unknown Source)
				at org.mybatis.spring.SqlSessionTemplate.selectList(SqlSessionTemplate.java:191)
				at com.classaffairs.dao.impl.BaseDaoImpl.mFindAll(BaseDaoImpl.java:125)
				at com.classaffairs.service.system.impl.ApplicationServiceImpl.finAllApplication(ApplicationServiceImpl.java:113)
				at com.classaffairs.common.security.ClassAffairFilterInvocationSecurityMetadataSource.loadResourceDefine(ClassAffairFilterInvocationSecurityMetadataSource.java:49)
				at com.classaffairs.common.security.ClassAffairFilterInvocationSecurityMetadataSource.<init>(ClassAffairFilterInvocationSecurityMetadataSource.java:44)
				at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
				at sun.reflect.NativeConstructorAccessorImpl.newInstance(Unknown Source)
				at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(Unknown Source)
				at java.lang.reflect.Constructor.newInstance(Unknown Source)
				at org.springframework.beans.BeanUtils.instantiateClass(BeanUtils.java:147)
				... 50 more
			Caused by: org.apache.ibatis.exceptions.PersistenceException: 
			### Error querying database.  Cause: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
			### The error may exist in file [G:\Explorer_Haozhifeng\workspace\class_affairs_management\WebContent\WEB-INF\classes\com\classaffairs\entity\ApplicationMapper.xml]
			### The error may involve com.classaffairs.entity.Application.findAll
			### The error occurred while handling results
			### SQL: select * from application where type=1
			### Cause: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
				at org.apache.ibatis.exceptions.ExceptionFactory.wrapException(ExceptionFactory.java:26)
				at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:111)
				at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:102)
				at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:98)
				at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
				at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
				at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
				at java.lang.reflect.Method.invoke(Unknown Source)
				at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:358)
				... 61 more
			Caused by: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
				at org.apache.ibatis.executor.loader.cglib.CglibProxyFactory.<init>(CglibProxyFactory.java:56)
				at org.apache.ibatis.session.Configuration.getProxyFactory(Configuration.java:300)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.createResultObject(DefaultResultSetHandler.java:523)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.getRowValue(DefaultResultSetHandler.java:334)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleRowValuesForSimpleResultMap(DefaultResultSetHandler.java:294)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleRowValues(DefaultResultSetHandler.java:269)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleResultSet(DefaultResultSetHandler.java:239)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleResultSets(DefaultResultSetHandler.java:153)
				at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:60)
				at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:73)
				at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:60)
				at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:267)
				at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:137)
				at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:96)
				at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:77)
				at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:108)
				... 68 more
			Caused by: java.lang.ClassNotFoundException: Cannot find class: net.sf.cglib.proxy.Enhancer
				at org.apache.ibatis.io.ClassLoaderWrapper.classForName(ClassLoaderWrapper.java:190)
				at org.apache.ibatis.io.ClassLoaderWrapper.classForName(ClassLoaderWrapper.java:89)
				at org.apache.ibatis.io.Resources.classForName(Resources.java:256)
				at org.apache.ibatis.executor.loader.cglib.CglibProxyFactory.<init>(CglibProxyFactory.java:54)
				... 83 more
				
		（33）asm-3.3.1.jar
			缺少此包，会报错：
			Caused by: org.apache.ibatis.exceptions.PersistenceException: 
			### Error querying database.  Cause: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
			### The error may exist in file [G:\Explorer_Haozhifeng\workspace\class_affairs_management\WebContent\WEB-INF\classes\com\classaffairs\entity\ApplicationMapper.xml]
			### The error may involve com.classaffairs.entity.Application.findAll
			### The error occurred while handling results
			### SQL: select * from application where type=1
			### Cause: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
				at org.apache.ibatis.exceptions.ExceptionFactory.wrapException(ExceptionFactory.java:26)
				at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:111)
				at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:102)
				at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:98)
				at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
				at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
				at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
				at java.lang.reflect.Method.invoke(Unknown Source)
				at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:358)
				... 61 more
			Caused by: java.lang.IllegalStateException: Cannot enable lazy loading because CGLIB is not available. Add CGLIB to your classpath.
				at org.apache.ibatis.executor.loader.cglib.CglibProxyFactory.<init>(CglibProxyFactory.java:56)
				at org.apache.ibatis.session.Configuration.getProxyFactory(Configuration.java:300)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.createResultObject(DefaultResultSetHandler.java:523)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.getRowValue(DefaultResultSetHandler.java:334)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleRowValuesForSimpleResultMap(DefaultResultSetHandler.java:294)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleRowValues(DefaultResultSetHandler.java:269)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleResultSet(DefaultResultSetHandler.java:239)
				at org.apache.ibatis.executor.resultset.DefaultResultSetHandler.handleResultSets(DefaultResultSetHandler.java:153)
				at org.apache.ibatis.executor.statement.PreparedStatementHandler.query(PreparedStatementHandler.java:60)
				at org.apache.ibatis.executor.statement.RoutingStatementHandler.query(RoutingStatementHandler.java:73)
				at org.apache.ibatis.executor.SimpleExecutor.doQuery(SimpleExecutor.java:60)
				at org.apache.ibatis.executor.BaseExecutor.queryFromDatabase(BaseExecutor.java:267)
				at org.apache.ibatis.executor.BaseExecutor.query(BaseExecutor.java:137)
				at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:96)
				at org.apache.ibatis.executor.CachingExecutor.query(CachingExecutor.java:77)
				at org.apache.ibatis.session.defaults.DefaultSqlSession.selectList(DefaultSqlSession.java:108)
				... 68 more
			Caused by: java.lang.NoClassDefFoundError: org/objectweb/asm/Type
				at net.sf.cglib.core.TypeUtils.parseType(TypeUtils.java:184)
				at net.sf.cglib.core.KeyFactory.<clinit>(KeyFactory.java:66)
				at net.sf.cglib.proxy.Enhancer.<clinit>(Enhancer.java:69)
				at java.lang.Class.forName0(Native Method)
				at java.lang.Class.forName(Unknown Source)
				at org.apache.ibatis.io.ClassLoaderWrapper.classForName(ClassLoaderWrapper.java:178)
				at org.apache.ibatis.io.ClassLoaderWrapper.classForName(ClassLoaderWrapper.java:89)
				at org.apache.ibatis.io.Resources.classForName(Resources.java:256)
				at org.apache.ibatis.executor.loader.cglib.CglibProxyFactory.<init>(CglibProxyFactory.java:54)
				... 83 more
			Caused by: java.lang.ClassNotFoundException: org.objectweb.asm.Type
				at java.net.URLClassLoader$1.run(Unknown Source)
				at java.net.URLClassLoader$1.run(Unknown Source)
				at java.security.AccessController.doPrivileged(Native Method)
				at java.net.URLClassLoader.findClass(Unknown Source)
				at java.lang.ClassLoader.loadClass(Unknown Source)
				at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
				at java.lang.ClassLoader.loadClass(Unknown Source)
				... 92 more
				
		(34)、commons-beanutils-1.8.3.jar
			BeanUtils.copyProperties(application, app);
			
		(35)、java_memcached-release_2.6.6.jar
			import com.danga.MemCached.MemCachedClient;
			import com.danga.MemCached.SockIOPool;
			
		（36）、poi-3.8-20120326.jar
				XSSFWorkbook:poi-ooxml-3.9-20121203.jar  org.apache.poi.xssf.usermodel.XSSFWorkbook   .xlsx
		（37）、poi-ooxml-3.8-20120326.jar
				HSSFWorkbook:poi-3.9-20121203.jar   org.apache.poi.hssf.usermodel.HSSFWorkbook   Excel2003-2007  .xls
		（38）、poi-ooxml-schemas-3.8-20120326.jar
				poi-ooxml.jar包的依赖包
		(39)、xmlbeans-2.3.0.jar
				poi-ooxml-schemas.jar包的依赖包
				
		(40)、commons-io.jar
		
		(41)、dom4j-1.6.1.jar
			java.lang.ClassNotFoundException: org.dom4j.DocumentException
			at java.net.URLClassLoader$1.run(Unknown Source)
			at java.net.URLClassLoader$1.run(Unknown Source)
			at java.security.AccessController.doPrivileged(Native Method)
			
		(42)、freemarker-2.3.8.jar
		
		(43)、spring-data-commons-1.9.2.RELEASE.jar
			Spring-MongoDB需要
		(44)、slf4j-api-1.7.7.jar
			Spring-MongoDB需要
			: java.lang.ClassNotFoundException: org.slf4j.LoggerFactory
				at java.net.URLClassLoader$1.run(Unknown Source)
				at java.net.URLClassLoader$1.run(Unknown Source)
				at java.security.AccessController.doPrivileged(Native Method)
				at java.net.URLClassLoader.findClass(Unknown Source)
				at java.lang.ClassLoader.loadClass(Unknown Source)
				at sun.misc.Launcher$AppClassLoader.loadClass(Unknown Source)
				at java.lang.ClassLoader.loadClass(Unknown Source)
				... 59 more
		
		
			
		
		
			

		