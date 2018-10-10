/**
 * 
 */
package com.classaffairs.common;

/**
 * @author Haozhifeng
 *
 */
public class FilePath {

	/**
	 * 通过业务类型和对象ID获取该文件应该上传到服务器的相对地址
	 * @param type
	 * @param objectId
	 * @return
	 * 
	 * */
	
	public static String getPath(String type,String objectId){
		String filePath = "upload/";
		switch (type) {
		case "1":
			filePath += "studentBatchAdd/" + objectId + "/";
			break;
		case "2":
			filePath += "blog/" + objectId + "/";
			break;
		case "3":
			filePath += "score/" + objectId + "/";
			break;
		case "4":
			filePath += "graduateProjectGroup/" + objectId + "/";
			break;
		default:
			break;
		}
		return filePath;
	}
		
}
