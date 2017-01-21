package com.selenium.enums;

public enum Environment {
	
	SIT("QA SIT","https://apple.com");
	
	private String key;
	private String url;
	
	Environment(String key, String url){
		this.key = key;
		this.url = url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public String getKey(){
		return key;
	}

}
