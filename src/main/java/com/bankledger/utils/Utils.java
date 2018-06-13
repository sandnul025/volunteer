package com.bankledger.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Utils {


    /**
     * p + yyyyMMddHHmmss + 一位 字母和数字 随机数
     * @return
     */
    public static String getRandomStr() {

        StringBuffer sb =new StringBuffer("p");
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        sb.append(time);
        sb.append(getRandomNumber(1, 3));
        return sb.toString();
    }


    /**
     *
     * @param len
     * @param type 1 数字  2字母 3数字加字母
     * @return
     */
    public static String getRandomNumber(Integer len, Integer type) {

        if(len <= 0) {
            return null;
        }

        // 数字48-57=10   ||  字母大写 65-90=26  || 小写97-122=26

        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        switch (type) {
            case 1:
                for(int i=0 ;i < len; i++) {
                    int n = random.nextInt(57-48+1)  +48;
                    sb.append((char)n);
                }
                break;
            case 2:
                for(int i=0 ;i < len; i++) {
                    int n = random.nextInt(51);
                    if(n >=26 && n<=51) {
                        n += 71;
                    }else if(n>=0 && n<=25){
                        n += 65;
                    }
                    sb.append((char)n);
                }
                break;
            case 3:
                for(int i=0 ;i < len; i++) {
                    int n = random.nextInt(61);
                    if( n >=10 && n<=35) {
                        n += 55;
                    }else if( n >=36 && n<= 61) {
                        n += 61;
                    }else if(n>=0 && n<=9) {
                        n += 48;
                    }
                    sb.append((char)n);
                }
                break;
            default:
                break;
        }
        return sb.toString();
    }


    /**
     * 去掉-
     * @return
     */
    public static String UUID32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 没去掉-
     * @return
     */
    public static String UUID36() {
        return UUID.randomUUID().toString();
    }

    /**
     *
     * @return  格式为 “2017-11-13 10:36:28” 字符串
     */
    public static String getCreatetime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * md5 加密
     * @param source
     * @return
     */
    public static String MD5encode(String source) {
        if (StringUtils.isEmpty(source)) {
            return null;
        }
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
        }
        byte[] encode = messageDigest.digest(source.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte anEncode : encode) {
            String hex = Integer.toHexString(0xff & anEncode);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


    /**
	 * 去掉数组中相同的元素
	 * @param array 数组
	 * @param isRemoveEmpty 是否去掉""和null
	 * @return
	 */
	public static String[] removeSame(String[] array,boolean isRemoveEmpty){

		List<String> list = new ArrayList<>();
		for(int i=0; i <array.length; i++){
			String item = array[i];
			if(!list.contains(item)){
				if(!StringUtils.isBlank(item)){
					list.add(item);
				}else{
					if(!isRemoveEmpty){
						list.add(item);
					}
				}
			}

		}
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * 中文字母数字下划线组合
	 * @param username
	 * @return
	 */
	public static boolean isName(String username) {
    	String regEx = "^[a-zA-Z0-9_\u4e00-\u9fa5]{3,15}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
    
    public static boolean isEmail(String email) {
    	
    	String regEx = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    /**
	 * 6-16 含有字母、数字、特殊符号的两种及以上
	 * @param pwd
	 * @return
	 */
	public static boolean isPwd(String pwd) {
    	String regEx = "^(?![a-zA-Z]+$)(?!\\d+$)(?![\\W_]+$)\\S{6,16}$";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }
	
	
	/**
	 * 转 16 进制
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
	    char[] chars = "0123456789ABCDEF".toCharArray();
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();
	    int bit;
	    for (int i = 0; i < bs.length; i++) {
	        bit = (bs[i] & 0x0f0) >> 4;
	        sb.append(chars[bit]);
	        bit = bs[i] & 0x0f;
	        sb.append(chars[bit]);
	        // sb.append(' ');
	    }
	    return sb.toString().trim();
	}

    public static void main(String[] args) {
        System.out.println(MD5encode("t123456"));
        System.out.println(getCreatetime());
        System.out.println(Arrays.toString(removeSame(new String[]{"1","1","3","2","1","",null},true)));
    }

}
