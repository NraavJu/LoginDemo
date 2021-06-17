package com.kklldog.util;

import java.util.UUID;

/** 
 * @author mj.zhou 
 * @version create：2013-12-3 下午4:03:48 
 * UUID 工具类
 */
public class Uuid {
	
	/**
	 * 随即一个UUID
	 * @return String
	 */
	public static String GetUUID(){
		
		UUID uuid = UUID.randomUUID();   
		return uuid.toString();
	}
}
