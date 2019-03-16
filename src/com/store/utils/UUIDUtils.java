package com.store.utils;

import java.util.UUID;

public class UUIDUtils {
	/**
	 * 随机生成id
	 * @return
	 */
	public static String getId(){
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	
	/**
	 * 生成64位随机码
	 * @return 64位随机码
	 */
	public static String getUUID64(){
		return getId()+getId();
	}
	
	/**
	 * 生成随机码:激活码
	 * @return
	 */
	public static String getCode(){
		return getId();
	}
	
}
