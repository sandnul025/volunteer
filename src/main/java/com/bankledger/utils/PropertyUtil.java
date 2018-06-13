package com.bankledger.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @AUTHOR: sandnul025
 * @MOTTO: Rainbow comes after a storm.
 * @DATE: 2017年12月4日 下午8:13:31
 */
public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    private static Map<String,String> pro = new HashMap<>();

    private static String[] files = new String[]{"config/config.properties","config/config1.properties","config/config2.properties"};

    private PropertyUtil(){}

    static {
        load();
    }

    private static void load(){

        for ( int i = 0; i < files.length; i ++) {
            Properties p = new Properties();
            Reader is = null;
            try {
                is = new InputStreamReader(PropertyUtil.class.getClassLoader().getResourceAsStream(files[i]), "UTF-8");
                p.load(is);

                @SuppressWarnings("rawtypes")
				Enumeration en = p.propertyNames();
                while (en.hasMoreElements()){
                    String key = (String)en.nextElement();
                    String value = p.getProperty(key);
                    pro.put(key, value);
                }
            } catch (Exception e) {
                logger.error("properties load exception --->{}",e.getMessage());
            }finally {
                try {
                    if(null != is){
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static String getProperty(String key){
        if(0 == pro.size())
            load();
        return pro.get(key);
    }
}
