package com.hhz.study.util;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
	private static final String CONFIG_PATH = "config.properties";
	
	public static String getProperty(String key) {
		Properties prop = new Properties();
		try {
			prop.load(Configuration.class.getClassLoader().getResourceAsStream(CONFIG_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
}
