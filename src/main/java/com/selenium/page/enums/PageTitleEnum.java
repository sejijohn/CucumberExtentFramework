package com.selenium.page.enums;

import com.selenium.page.pojo.PageTitlePojo;

public enum PageTitleEnum implements BaseEnum<PageTitlePojo>{
	
	APPLE{

		@Override
		public PageTitlePojo getInfo() {
			// TODO Auto-generated method stub
			PageTitlePojo title = new PageTitlePojo();
			title.setTitle("Apple");
			return title;
		}
		
	},
	
	 INCH15_MAC{

		@Override
		public PageTitlePojo getInfo() {
			// TODO Auto-generated method stub
			PageTitlePojo title = new PageTitlePojo();
			title.setTitle("15-inch MacBook Pro");
			return title;
			
		}
	},
	 
	 DEMOTITLE{

		@Override
		public PageTitlePojo getInfo() {
			// TODO Auto-generated method stub
			PageTitlePojo title = new PageTitlePojo();
			title.setTitle("Welcome");
			return title;
		}
		};

}
