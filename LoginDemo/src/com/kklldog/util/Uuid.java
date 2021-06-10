package com.kklldog.util;

import java.util.UUID;

public class Uuid {
	
	public static String GetUUID(){
		
		UUID uuid = UUID.randomUUID();   
		return uuid.toString();
	}
}
