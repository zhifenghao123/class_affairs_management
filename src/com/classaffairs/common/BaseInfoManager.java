package com.classaffairs.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;

import com.classaffairs.entity.BaseInfo;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.framework.sdp.orm.query.PageImpl;
import com.classaffairs.service.BaseInfoService;
import com.classaffairs.service.BaseTypeService;

public class BaseInfoManager {
	//private Map<Long, List<BaseInfo>> typeGroupInfo =  new HashMap<Long, List<BaseInfo>>();
	private Map<Long, List<BaseInfo>> typeGroupInfo =  new LinkedHashMap<Long, List<BaseInfo>>();
	/**
	 * LinkedHashMap实现有序key值的关键就是根据插入顺序另外维护了一个按照插入顺序作为标记的双向循环列表，这样在获取所有数据进行循环获取时获取到的数据就是有序的数据。
	 * */
	private Map<Long, BaseInfo> byIdInfo;
	//private List<Map<Long,Long>> baseInfoAndBaseTypeId = new ArrayList<Map<Long,Long>>();
		
	private static BaseInfoManager _instance = new BaseInfoManager();
	//private BaseInfoHelper baseInfoHelper = new BaseInfoHelper();
	private BaseInfoService baseInfoService;
	private BaseTypeService baseTypeService;

	public static BaseInfoManager getInstance() {
		if (_instance == null) {
			_instance = new BaseInfoManager();
		}
		//System.out.println("BaseInfoManager--->getInstance--->"+_instance);
		return _instance;
	}

	/**
	 * 加载BaseInfo数据到typeGroupInfo中
	 */
	public void init() {
		ApplicationContext applicationContext = SpringContextHelper.getApplicationContext();
		baseInfoService = (BaseInfoService)applicationContext.getBean("baseInfoServiceImpl");
		baseTypeService = (BaseTypeService)applicationContext.getBean("baseTypeServiceImpl");
		typeGroupInfo = loadBaseInfo();
		byIdInfo = loadBaseInfoById();
		//baseInfoAndBaseTypeId = this.loadBaseInfoAndBaseType();
	}

	/**
	 * 通过type(信息类型码)获取基本信息List
	 * @param type 信息类型码
	 * @return 基本信息对象List
	 */
	public List<BaseInfo> getBaseInfoByType(Long baseTypeId) {
		return typeGroupInfo.get(baseTypeId);
	}

	public BaseInfo getBaseInfoById(Long baseInfoId) {
		return byIdInfo.get(baseInfoId);
	}

	/**
	 * @param type 信息类型码
	 * @param code 顺序代码
	 * @return 基本信息对象
	 */
	public BaseInfo getBaseInfoByTypeAndCode(Long baseTypeId, int code) {
		List<BaseInfo> temp = typeGroupInfo.get(baseTypeId);

		if (null == temp) {
			return null;
		}

		for (BaseInfo p : temp) {
			if (p.getCode() == code) {
				return p;
			}
		}

		return null;
	}

	/**
	 * 通过信息类型码和顺序代码获取基本信息描述
	 * @param type 信息类型码
	 * @param code 顺序代码
	 * @return 基本信息描述
	 */
	public String getDisplayByTypeAndCode(Long type, Integer code) {
		String displayNameResult = "";
		List<BaseInfo> temp = typeGroupInfo.get(type);
		if (null == temp) {
			return displayNameResult;
		}
		for (BaseInfo itsBaseInfo : temp) {
			if (null != code && !code.equals("")) {
				if (itsBaseInfo.getCode() == code) {
					displayNameResult = itsBaseInfo.getDisplay();
					return displayNameResult;
				}
			}
		}
		return displayNameResult;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Map<Long, List<BaseInfo>> loadBaseInfo() {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) baseTypeService.findAllBaseType();
		for (Map<String, Object> map : resultList) {
			List<BaseInfo> list = baseInfoService.findBaseInfoByBaseTypeId((Long)map.get("base_type_id"));
			typeGroupInfo.put((Long) map.get("base_type_id"), list);
		}
		return typeGroupInfo;
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Map<Long,Long>> loadBaseInfoAndBaseType() {
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) baseTypeService.findAllBaseType();
		for (Map<String, Object> map : resultList) {
			Long baseTypeId = (Long)map.get("base_type_id");
			//List<BaseInfo> list = baseInfoService.findBaseInfoByBaseTypeId((Long)map.get("base_type_id"));
			//typeGroupInfo.put((Long) map.get("base_type_id"), list);
			Map<Long,Long> baseTypeIdMap = new HashMap<Long,Long>();
			baseTypeIdMap.put(baseTypeId, 0l);
			baseInfoAndBaseTypeId.add(baseTypeIdMap);
			List<BaseInfo> list = baseInfoService.findBaseInfoByBaseTypeId(baseTypeId);
			for(BaseInfo baseInfo:list){
				Map<Long,Long> baseInfoIdMap = new HashMap<Long,Long>();
				baseTypeIdMap.put(baseInfo.getBaseInfoId(), baseTypeId);
				baseInfoAndBaseTypeId.add(baseTypeIdMap);
			}
		}
		return baseInfoAndBaseTypeId;
	}*/

	/**
	 * 按id加载基本信息
	 * @return Map<Long, BaseInfo> - id对应基本信息
	 */
	@SuppressWarnings("unchecked")
	public Map<Long, BaseInfo> loadBaseInfoById() {
		List<BaseInfo> resultList = new ArrayList<BaseInfo>();
		resultList = baseInfoService.findAllBaseInfo();
		Map<Long, BaseInfo> map = new HashMap<Long, BaseInfo>();

		for (BaseInfo info : resultList) {
			map.put(info.getBaseInfoId(), info);
		}

		return map;
	}

	/**
	 * @since 2010-11-24
	 * 返回指定类型下的信息列表
	 * @param type
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<BaseInfo> getInfoByType(Long type) {
		List<BaseInfo> list = baseInfoService.findBaseInfoByBaseTypeId(type);
		return list;
	}
	
/*	public Page<Map<Long,Long>> getBaseInfoAndBaseTypeByPageQuery(int recordOffset,int pageSize){
		Page baseTypeAndBaseInfo = new PageImpl(recordOffset, pageSize);
		Integer totalCount = baseInfoAndBaseTypeId.size();
		baseTypeAndBaseInfo.setTotalCount(totalCount.intValue());
		baseTypeAndBaseInfo.setResult(baseInfoAndBaseTypeId);
		return baseTypeAndBaseInfo;
	}*/
	public Page<Map<Long,Long>> getBaseInfoAndBaseTypeByPageQuery(int page,int pageSize){
		int currentOffset = 0;
		int startOffset = (page - 1) * pageSize;
		int endOffset = page * pageSize;
		
		//int recordOffset = startOffset;
		Page baseInfoPage = new PageImpl(startOffset, pageSize);
		Integer totalCount = byIdInfo.size();
		if(startOffset == 0){
			Iterator<Entry<Long, List<BaseInfo>>> iterator= typeGroupInfo.entrySet().iterator();  
			while(iterator.hasNext())  
			{  
			    Map.Entry entry = iterator.next();  
			    //System.out.println(entry.getKey()+":"+entry.getValue()); 
			    for(BaseInfo baseInfo:(List<BaseInfo>)entry.getValue()){
			    	if((currentOffset > startOffset)&&(currentOffset < endOffset)){
			    		currentOffset ++;
			    		
			    	}else{
			    		
			    	}
			    }
			}  
		}else{
			
		}
		
	}

}

