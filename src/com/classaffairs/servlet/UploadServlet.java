/**
 * 
 */
package com.classaffairs.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

import com.classaffairs.common.CommonPath;
import com.classaffairs.common.FilePath;
import com.classaffairs.framework.core.utils.Log;

/**
 * @author Haozhifeng
 *
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String basePath;//获取相对上传跟目录
	private String absolutePath;//获取绝对上传跟目录

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filename = "";
		try {
			req.setCharacterEncoding("utf-8");
			resp.setCharacterEncoding("utf-8");
			boolean isMultipart = ServletFileUpload.isMultipartContent(req);
			if (!isMultipart) {
				Log.log.debug("非文件上传请求！");
				return;
			}
			PrintWriter out = resp.getWriter();

			String type = req.getParameter("type").trim();
			String objectId = req.getParameter("objectId").trim();
			absolutePath = FilePath.getPath(type, objectId);
			// create factory and file cleanup tracker
			FileCleaningTracker tracker = FileCleanerCleanup.getFileCleaningTracker(getServletContext());
			File tmpDir = new File(basePath + absolutePath);
			if (!tmpDir.exists()) {
				tmpDir.mkdirs();
			}
			DiskFileItemFactory factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, tmpDir);
			factory.setFileCleaningTracker(tracker);
			//保存上传文件
			ServletFileUpload upload = new ServletFileUpload(factory);

			List<FileItem> items = upload.parseRequest(req);
			String fileName = null;
			String pathfull = null;
			File savefile = null;
			for (FileItem item : items) {
				if (!item.isFormField()) {
					// 确定是文件而不是一个普通的表单字段
					fileName = item.getName();
					/*System.out.println(fileName);
					fileName = FileUtil.getNumberName(fileName);
					System.out.println(fileName);*/
					savefile = new File(basePath + absolutePath + fileName);
					item.write(savefile);
					Log.log.debug("保存文件到--->" + savefile.getAbsolutePath());
					pathfull = CommonPath.getBasePath(req) + absolutePath + fileName;
					Log.log.debug("filePathName--->" + pathfull);
					//返回给客户端的数据(返回到客户端的serverdata变量中)
					//out.print(pathfull);
					
					String relativePath = "/" + pathfull.substring(pathfull.indexOf("upload"));
					String uploadInfo = "{msg:'" + true + "',ServerFileFullPath:'" + pathfull + "',ServerFileRelativePath:'"+relativePath+"',filename:'"+fileName+"'}";
					out.print(uploadInfo);
					out.flush();
					out.close();
					filename = pathfull;
				}
			}
			/*
			 * @update 2014-07-10
			 * @author mamm 带有缩略图的图片按指定大小缩放，其他按指定路径名复制图片（和前台获取图片时拼接路径一直）
			 */
/*			File newFile;
			switch (type) {
			case "1"://俱乐部相册
				newFile = new File(basePath + absolutePath + "thumb/" + fileName);
				if (!newFile.getParentFile().exists()) {
					newFile.getParentFile().mkdirs();
				}
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 95, 66);
				break;
			case "2"://俱乐部logo
				newFile = new File(basePath + absolutePath + "thumb100" + fileName);
				ImageUtil.CopyFile(savefile.getAbsolutePath(), newFile.getAbsolutePath());

				newFile = new File(basePath + absolutePath + "thumb190" + fileName);
				ImageUtil.CopyFile(savefile.getAbsolutePath(), newFile.getAbsolutePath());

				newFile = new File(basePath + absolutePath + "thumb80" + fileName);
				ImageUtil.CopyFile(savefile.getAbsolutePath(), newFile.getAbsolutePath());

				break;
			case "3"://圈子相册
				newFile = new File(basePath + absolutePath + "thumb/" + fileName);
				if (!newFile.getParentFile().exists()) {
					newFile.getParentFile().mkdirs();
				}
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 95, 66);
				break;
			case "4"://圈子logo
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.CopyFile(savefile.getAbsolutePath(), newFile.getAbsolutePath());
				break;
			case "6"://健友、教练相册
				newFile = new File(basePath + absolutePath + "thumb/" + fileName);
				if (!newFile.getParentFile().exists()) {
					newFile.getParentFile().mkdirs();
				}
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 95, 66);
				break;
			case "7"://健友、教练logo
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.CopyFile(savefile.getAbsolutePath(), newFile.getAbsolutePath());
				break;
			case "10"://活动logo
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 100, 75);
				break;
			case "12"://课程logo
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 100, 75);
				break;
			case "14"://场地logo
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 100, 75);
				break;
			case "20"://健身卡logo
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 156, 90);
				break;
			case "22"://商品logo
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 80, 60);
				break;
			case "24"://界面图片
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.CopyFile(savefile.getAbsolutePath(), newFile.getAbsolutePath());
				break;
			case "25"://运动图片
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.CopyFile(savefile.getAbsolutePath(), newFile.getAbsolutePath());
				break;
			case "27"://活动相册照片
				newFile = new File(basePath + absolutePath + "thumb" + fileName);
				ImageUtil.compressPic(savefile.getAbsolutePath(), newFile.getAbsolutePath(), 100, 75);
				break;
			default:
				break;
			}
*/
		} catch (Exception e) {
			Log.log.error(filename + "<----->" + e.getMessage());
			throw new IOException(e.getMessage());
		}
	}

	public void init() throws ServletException {

		basePath = this.getServletContext().getRealPath("/");
		Log.log.debug("basePath--->" + basePath);
	}
}
