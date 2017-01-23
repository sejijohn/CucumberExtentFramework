package com.selenium.page.enums;

import com.selenium.common.Constants;
import com.selenium.page.pojo.ImagePathPojo;

public enum ImagePathEnum implements BaseEnum<ImagePathPojo>{
	
	DEMOLOGO{

		@Override
		public ImagePathPojo getInfo() {
			ImagePathPojo imagePath = new ImagePathPojo();
			imagePath.setPath(Constants.imagePath + "/AL.png");
			return imagePath;
		}};

}
 