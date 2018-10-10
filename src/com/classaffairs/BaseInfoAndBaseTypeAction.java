/**
 * 
 */
package com.classaffairs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.common.BaseInfoManager;
import com.classaffairs.entity.BaseInfo;
import com.classaffairs.entity.BaseType;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.framework.sdp.orm.query.PageImpl;
import com.classaffairs.service.BaseInfoService;
import com.classaffairs.service.BaseTypeService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class BaseInfoAndBaseTypeAction {

	@Autowired
	private BaseTypeService itsBaseTypeService;
	@Autowired 
	private BaseInfoService itsBaseInfoService;
	
	@RequestMapping(value = "/admin/getBaseInfoAndBaseType.action")
	public @ResponseBody
	String getBaseInfoAndBaseType(HttpServletRequest request) {
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		
		JsonObject result = new JsonObject();
/*		Page<BaseInfo> baseInfoPage = itsBaseInfoService.getBaseInfosByPageQuery(page, rows);
		result.addProperty("total", baseInfoPage.getTotalCount());
		JsonArray ja = new JsonArray();
		if(baseInfoPage != null){
			List<BaseInfo> baseInfoList = baseInfoPage.getResult();
			for(BaseInfo baseInfo:baseInfoList){
				BaseType baseType = itsBaseTypeService.findBaseTypeByBaseTypeId(baseInfo.getBaseTypeId());
				JsonObject jo = new JsonObject();
				jo.addProperty("baseInfoId", baseInfo.getBaseInfoId());
				jo.addProperty("baseInfoCode", baseInfo.getCode());
				jo.addProperty("baseInfoDisplay", baseInfo.getDisplay());
				jo.addProperty("baseTypeId", baseType.getBaseTypeId());
				jo.addProperty("baseTypeName", baseType.getTypeName());
				jo.addProperty("baseTypeMemo", baseType.getMemo());
				ja.add(jo);
			}
		}*/
		Page<Map<Long,Long>>  baseInfoAndBaseType = BaseInfoManager.getInstance().getBaseInfoAndBaseTypeByPageQuery((Integer.valueOf(page) - 1)*(Integer.valueOf(rows)), Integer.valueOf(rows));
		result.addProperty("total", baseInfoAndBaseType.getTotalCount());
		JsonArray ja = new JsonArray();
		if(baseInfoAndBaseType != null){
			for(Map<Long,Long> base:baseInfoAndBaseType){
				Iterator iter = base.entrySet().iterator();
				Long key = -1l;
				Long val = -1l;
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					key = (Long)entry.getKey();
					val = (Long)entry.getValue();
				}
				JsonObject jo = new JsonObject();
				if(val != 0l){//baseInfo
					BaseInfo baseInfo = itsBaseInfoService.findBaseInfoById(Long.valueOf(key));
					BaseType baseType = itsBaseTypeService.findBaseTypeByBaseTypeId(Long.valueOf(val));
					jo.addProperty("baseInfoId", baseInfo.getBaseInfoId());
					jo.addProperty("baseInfoCode", baseInfo.getCode());
					jo.addProperty("baseInfoDisplay", baseInfo.getDisplay());
					jo.addProperty("baseTypeId", baseType.getBaseTypeId());
					jo.addProperty("baseTypeName", baseType.getTypeName());
					jo.addProperty("baseTypeMemo", baseType.getMemo());
				}else{//baseType
					BaseType baseType = itsBaseTypeService.findBaseTypeByBaseTypeId(Long.valueOf(key));
					jo.addProperty("baseInfoId", "—————");
					jo.addProperty("baseInfoCode", "—————");
					jo.addProperty("baseInfoDisplay", "—————");
					jo.addProperty("baseTypeId", baseType.getBaseTypeId());
					jo.addProperty("baseTypeName", baseType.getTypeName());
					jo.addProperty("baseTypeMemo", baseType.getMemo());
				}
				ja.add(jo);
			}
		}
		
		result.add("rows", ja);
		return result.toString();
	}
}
