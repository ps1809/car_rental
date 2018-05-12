package com.car_rent.util;

import java.text.SimpleDateFormat;
import java.util.Map;

import com.google.gson.Gson;

public class AppUtil {

	public static String getDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("---------------------->"+format.format(new java.util.Date()));
		return format.format(new java.util.Date());
	}

	public static String getJsonString(Map<String, Object> map) {
		return new Gson().toJson(map);
	}
}
