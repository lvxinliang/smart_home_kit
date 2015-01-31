package org.smarthome.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SignUtil {
	public static String TOKEN = "smart_home_token";

	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {
		String[] data = new String[]{TOKEN, timestamp, nonce};
		if(null == timestamp || null == nonce){
			return false;
		}
		Arrays.sort(data);
//		System.out.println(Arrays.toString(data));
		String dataString = data[0] + data[1] + data[2];
		MessageDigest digest;
		String shaHex = null;
		StringBuffer hexString = new StringBuffer();
		try {
			digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(dataString.getBytes());
			byte messageDigest[] = digest.digest();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString().equals(signature);
	}
}
