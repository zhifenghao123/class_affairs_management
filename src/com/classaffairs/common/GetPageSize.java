package com.classaffairs.common;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.classaffairs.framework.core.utils.Log;


/**
 * 通过状态值获取状态名
 * @author mamm
 *
 */
public class GetPageSize {
	//前台列表页面大小
	public static final int PAGESIZE_LIST = PAGESIZE_LIST();

	private final static String pageSizePath = "pageSize.xml";

	public static NodeList getPageSizeXml(String tagname) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {
			builder = dbf.newDocumentBuilder();
			Document doc;

			String file = pageSizePath;
			file = CommonPath.getWebappPath("/WEB-INF/classes/") + file;

			doc = builder.parse(new File(file));

			Element root = doc.getDocumentElement();
			NodeList list = root.getElementsByTagName(tagname);

			return list;

		} catch (IOException e) {
			e.printStackTrace();
			Log.log.error(e);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			Log.log.error(e);
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Integer PAGESIZE_LIST() {
		return Integer.valueOf(getPageSizeXml("PAGESIZELIST").item(0).getFirstChild().getNodeValue());
	}
}
