package com.classaffairs.service.impl.oplog;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.classaffairs.common.CommonPath;
import com.classaffairs.framework.core.utils.Log;
/**
 * 操作日志实体管理类
 * @author YK
 * @since 2013年12月20日 22:40:02
 */
@Service
public class EntityForOplogManager {
	
	public final static String  entityForOplogCacheString = "EntityForOplog";
	
	private static EntityForOplogManager _instance;
	
	private static HashMap<String, List<String>> entityMap;//用于存储实体中实体路径和关注字段之间的映射关系
	
	public static EntityForOplogManager getInstance(){
		if(null==_instance){
			_instance = new EntityForOplogManager();
		}
		return _instance;
	}
	/**
	 * 操作日志实体管理器的初始化
	 */
	public void init(){
		//创建DocumentBuilder工厂
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();    
        //创建DocumentBuilder
		DocumentBuilder builder;
		//Xml解析后的Document对象
        Document document;
		try {
			builder = factory.newDocumentBuilder();
			
			String xmlPath = CommonPath.getWebappPath("")+"/WEB-INF/classes/spring/entity_config.xml";
			document = builder.parse(new File(xmlPath));
			document.normalize();
			//从document将元素载入缓存中
			entityMap = load(document);
		} catch (ParserConfigurationException pce) {
			Log.log.error("操作日志实体xml解析配置异常com.classaffairs.service.impl.oplog.EntityForOplogManager"+pce);
			pce.printStackTrace();
		} catch (SAXException saxe) {
			Log.log.error("操作日志实体xml解析配置异常com.classaffairs.service.impl.oplog.EntityForOplogManager"+saxe);
			saxe.printStackTrace();
		} catch (IOException ioe) {
			Log.log.error("操作日志实体xml文件io异常com.classaffairs.service.impl.oplog.EntityForOplogManager"+ioe);
			ioe.printStackTrace();
		} catch(Exception e){
			Log.log.error("操作日志实体manager异常com.classaffairs.service.impl.oplog.EntityForOplogManager"+e);
			e.printStackTrace();
		}
	}
	/**
	 * 加载Dom树中的元素到HashMap中
	 * @param document 从xml文件中解析出的Dom树
	 * @return hashMap 关联了实体路径和关注字段的HashMap
	 */
	private HashMap<String, List<String>> load(Document document){
		
			HashMap<String, List<String>> hashMap = new LinkedHashMap<String, List<String>>();
			//获取xml文件中的所有entity实体列表
			NodeList entityList = document.getElementsByTagName("entity");
			if(null!=entityList&&entityList.getLength()>0){
				for(int i=0; i<entityList.getLength();i++){
					Node entity = entityList.item(i);
					if(entity instanceof Element){
						//获取实体的路径
						String classpath = ((Element) entity).getAttribute("name");
						//获取实体中关心的属性
						List<String> propertyList = new LinkedList<String>();
						if(entity.hasChildNodes()){
							NodeList propertyNodeList= entity.getChildNodes();
							for(int j=0;j<propertyNodeList.getLength();j++){
								Node propertyNode = propertyNodeList.item(j);
								if(propertyNode instanceof Element){
									String property = ((Element) propertyNode).getAttribute("name");
									propertyList.add(property);
								}
							}
						}
						//以实体路径为key，实体中关心属性为value存储hashMap
						hashMap.put(classpath, propertyList);
					}
				}
			}
			return hashMap;
	}
	/**
	 * 根据实体路径获取关注字段的列表
	 * @param classpath 实体的绝对路径
	 * @return 关注字段列表
	 */
	public List<String> getPropertyList(String classpath){
		return (List<String>)entityMap.get(classpath);
	}
}
