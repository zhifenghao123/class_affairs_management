/**
 * 
 */
package com.classaffairs.common.FreeMarketTool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import com.classaffairs.framework.core.utils.Log;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Haozhifeng
 *
 */
public class FreeMarkertUtil {
	public static void analysisTemplate(String templatePath, String templateName, String fileName, Map<?, ?> root, String dir) {
		try {
			//初使化FreeMarker配置
			Configuration config = new Configuration();
			// 设置要解析的模板所在的目录，并加载模板文件
			config.setDirectoryForTemplateLoading(new File(templatePath));
			// 设置包装器，并将对象包装为数据模型
			config.setObjectWrapper(new DefaultObjectWrapper());
			// 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
			// 否则会出现乱码
			Template template = config.getTemplate(templateName, "UTF-8");
			//创建目标文件目录
			File file = new File(dir);
			if (!file.exists())
				file.mkdirs();
			// 合并数据模型与模板
			FileOutputStream fos = new FileOutputStream(fileName);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			template.process(root, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.log.error("analysisTemplate异常common.FreeMarkertTool", e);
		} catch (TemplateException e) {
			e.printStackTrace();
			Log.log.error("analysisTemplate异常common.FreeMarkertTool", e);
		}
	}

}
