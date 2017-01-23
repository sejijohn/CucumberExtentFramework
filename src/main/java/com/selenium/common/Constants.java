package com.selenium.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constants {
	
	static Date date = new Date();
	static SimpleDateFormat reportDate = new SimpleDateFormat("mm-dd-yyyy hh-mm-ss");
	public static final String filePath = System.getProperty("user.dir");
	public static final String reportPath = filePath + "/RESULT_LOG";
	public static final String imagePath = filePath + "/IMAGES";
	//public static final String imagePath = "/Users/sejijohn/Desktop";
	public static final String reportFileName = reportPath +"/" + "TESTREPORT_" + reportDate.format(date) + "TC.html";
	public static final String screenshotFileName = reportPath + "/SCREENSHOTS" + "TESTREPORT_" + reportDate.format(date) + "TC.html";
	public static final String screenshotFilePath = screenshotFileName + "/SCREENSHOTS"+"TEST" + reportDate.format(date) + "/";
    public static String sScreenshotFilepath=filePath+"/Screenshots/"+"IOLS_Screenshot_"+reportDate.format(date)+"/";
    public static String sReportFileName = reportPath+"/"+"IOLSTestReport_"+reportDate.format(date)+".html";


}
