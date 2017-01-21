package com.selenium.common;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Res {
	
	private static final String[] DEFAULT_RESOURCE_PATH = {null,"src/main/resources","src/test/resources"};
	
	public static String getResources(String propFile){
		//URL resource = Thread.currentThread().getContextClassLoader().getResource(propFile);
		URL resource = null;
		if(resource == null){
			
			for (String resourcePath : DEFAULT_RESOURCE_PATH){
				File resFile = new File(resourcePath, propFile);
				
				if(resFile.isFile()||resFile.isDirectory()){
					try{
						resource = resFile.toURI().toURL();
						break;
					}catch(MalformedURLException e){
						throw new RuntimeException("Unable to locate property file");
					}
				}
			}
		}
		return resource.toString().replaceAll("file:", "");
	}

}
