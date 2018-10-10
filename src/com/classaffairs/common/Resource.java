/**
 * 
 */
package com.classaffairs.common;

import java.io.File;

/**
 * @author Haozhifeng
 *
 */
public class Resource {
	String filepath;

	public Resource(String filepath) {
		String path = CommonPath.getWebappPath("");
		this.filepath = path + filepath;
	}

	public boolean delete() {
		boolean flag = false;
		File file = new File(this.filepath);
		if (file.isFile() && file.exists()) {
			flag = file.delete();

		}
		if (file.isDirectory()) {
			flag = file.delete();
		}
		return flag;

	}
}
