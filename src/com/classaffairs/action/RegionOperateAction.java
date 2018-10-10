/**
 * 
 */
package com.classaffairs.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.classaffairs.entity.Region;
import com.classaffairs.framework.core.init.SpringContextHelper;
import com.classaffairs.framework.sdp.orm.query.Page;
import com.classaffairs.service.RegionService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * @author Haozhifeng
 *
 */
@Controller
public class RegionOperateAction {
	private RegionService init() {
		ApplicationContext ctx = SpringContextHelper.getApplicationContext();

		RegionService itsRegionService = (RegionService) ctx.getBean("regionServiceImpl");

		return itsRegionService;
	}

	/**
	 * 分页获取所有区域信息
	 */
	@RequestMapping(value = "/admin/getRegion")
	@ResponseBody
	public String getRegion(HttpServletRequest request) {
		String page = request.getParameter("page");

		String rows = request.getParameter("rows");

		String parentid = request.getParameter("parentId");

		if (page == null || page.trim().equals("")) {

			page = "1";
		} else {
			page = page.trim();
		}
		if (rows == null || rows.trim().equals("")) {

			rows = "10";
		} else {
			rows = rows.trim();
		}

		Long parentId = 1L;//默认为中国，parentId=1L

		if (parentid != null && !parentid.trim().equals(""))

			parentId = Long.valueOf(parentid.trim());

		JsonObject result = new JsonObject();

		Page<Region> RegionPage = init().getRegionPageByParentId(parentId, page, rows);

		result.addProperty("total", RegionPage.getTotalCount());

		JsonArray array = new JsonArray();

		if (RegionPage != null) {

			List<Region> regionList = RegionPage.getResult();

			for (Region region : regionList) {

				JsonObject re = new JsonObject();

				re.addProperty("regionId", region.getRegionId());

				re.addProperty("code", region.getCode());

				re.addProperty("englishName", region.getEnglishName() == null ? "" : region.getEnglishName().trim());

				re.addProperty("name", region.getName());

				re.addProperty("state", region.getState());

				array.add(re);

			}

		}
		result.add("rows", array);

		return result.toString();
	}
}
