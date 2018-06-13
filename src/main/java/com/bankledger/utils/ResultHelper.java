package com.bankledger.utils;

import java.text.SimpleDateFormat;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2017年11月21日 下午8:17:29
 */
public class ResultHelper {

	private Integer code;
	
	private String message;
	
	private Object data;
	
	private String timestamp;
	
	private static final Integer SUCCESS_CODE = 0;
	
	private static final String SUCCESS_MESSAGE = "SUCCESS";
	
	private static final Integer FAIL_CODE = 1;
	
	private static final String FAIL_MESSAGE = "FAIL";
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	
	public static ResultHelper success() {
		return success(SUCCESS_MESSAGE);
	}
	
	public static ResultHelper success(String message) {
		return success(message,null);
	}
	
	public static ResultHelper success(Object data) {
		return success(null,data);
	}
	
	public static ResultHelper success(String message,Object data) {
		return success(SUCCESS_CODE,message,data);
	}
	
	public static ResultHelper fail() {
		return fail(FAIL_MESSAGE);
	}
	
	public static ResultHelper fail(String failMessage) {
		return fail(FAIL_CODE,failMessage);
	}
	
	public static ResultHelper fail(Integer failCode,String failMessage) {
		return success(failCode,failMessage,null);
	}
	
	private static ResultHelper success(Integer code,String message,Object data) {
		
		ResultHelper result = new ResultHelper();
		result.setCode(code);
		result.setMessage(message);
		result.setData(data);
		result.timestamp = format.format(System.currentTimeMillis());
		return result;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	
}
