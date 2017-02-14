package br.com.procempa.modus.utils;

import java.security.NoSuchAlgorithmException;

public class CryptoUtils {

	public static String encripty(String nameUser, char[] password) {
		String sign = nameUser + String.valueOf(password);
		return encripty(sign);
	}
	
	public static String encripty(char[] password) {
		String sign = String.valueOf(password);
		return encripty(sign);
	}
	
	public static String encripty(String key) {
		if (key == null) {
			key = "";
		}
		String sign = key;

		java.security.MessageDigest md;
		try {
			md = java.security.MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

		md.update(sign.getBytes());

		byte[] hash = md.digest();

		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}

		sign = hexString.toString();
		
		return sign;
	}
}
