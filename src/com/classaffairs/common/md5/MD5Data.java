package com.classaffairs.common.md5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Data {

	public final static String encryption(String str) {
		try {
			byte[] btinput = str.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(btinput);
			byte[] btinputByMD = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < btinputByMD.length; i++) {
				byte b = btinputByMD[i];
				int val = ((int) b) & 0XFF;
				if (val < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));

			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return null;
		}
	}


}
