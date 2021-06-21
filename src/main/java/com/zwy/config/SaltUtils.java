package com.zwy.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Random;

public class SaltUtils {
	private static Log log = LogFactory.getLog(SaltUtils.class);

	/**
	 * 生成随机码
	 *
	 * @param pwd_len
	 *            生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止 生成负数，

			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	public static String getPwdBySalt(String pwd, String salt){
		//加密方式
		String hashAlgorithmName = "MD5";
		//盐：为了即使相同的密码不同的盐加密后的结果也不同
		ByteSource byteSalt = ByteSource.Util.bytes(salt);
		//密码
//		Object source = "0000";
		//加密次数
		int hashIterations = 1024;
		SimpleHash result = new SimpleHash(hashAlgorithmName, pwd, byteSalt, hashIterations);
		return result.toString();
	}

	public static void main(String[] args) {
		System.out.println(genRandomNum(8));
	}

}
