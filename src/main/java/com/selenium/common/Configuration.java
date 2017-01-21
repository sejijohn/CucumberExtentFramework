package com.selenium.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class Configuration {
	
	private static Properties prop;
	private static HashMap<String, String> urlMap;
	
	private static Properties getProp(){
		//System.out.println("INSIDE GET PROPERTY");
		if(prop == null){
			prop = new Properties();
			InputStream input = null;
			try{
				//input = new FileInputStream(new File(Res.getResources("system.properties").toURI()));
				//System.out.println("PROP PATH: "+Res.getResources("system.properties"));
				input = new FileInputStream(new File(Res.getResources("system.properties")));
				prop.load(input);
				//System.out.println("PROPERTY LOAD COMPLETE: "+prop.getProperty("selenium.browser"));
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		return prop;
	}
	
	public static HashMap<String, String> getURLMAP(){
		if(urlMap == null){
			urlMap = new HashMap<String, String>();
		}
		return urlMap;
	}
	
	public static final Boolean REMOTE = new Boolean(getProp().getProperty("selenium.remote"));
	public static final BrowserTypes BROWSER = BrowserTypes.valueOf(getProp().getProperty("selenium.browser"));
	public static final String SELENIUM_GRID_URL = getProp().getProperty("selenium.gridurl");
	public static final Boolean DEMO = new Boolean(getProp().getProperty("project.demo"));
	public static final String HOST = getProp().getProperty("aut.server");
	public static final Boolean USE_SSL = new Boolean(getProp().getProperty("aut.usessl"));
	public static final String SELENIUM_VERSION = getProp().getProperty("selenium.version");
	
	public static final String MOBILE_DEVICE = "Galaxy";
	public static final int MOBILE_WIDTH = 200;
	public static final int MOBILE_HEIGHT = 600;
	
	public static final String CHROME_WEBDRIVER = getProp().getProperty("webdriver.chrome.driver");
	public static final String IE_WEBDRIVER = getProp().getProperty("webdriver.ie.driver");
	public static final String SAFARI_WEBDRIVER = getProp().getProperty("webdriver.safari.driver");
	public static final String FIREFOX_WEBDRIVER = getProp().getProperty("webdriver.firefox.driver");
	
	public static final String EXECUTION_ENVIRONMENT = getProp().getProperty("execution.environment");
	
	
}
