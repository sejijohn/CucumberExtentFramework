package com.selenium.common;

//import org.apache.log4j.xml.DOMConfigurator;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reports {

	public static ExtentReports extent;
	public static ExtentTest test;

	// Starts a Test with the given Testcase Name
	public void startTest(String sTestCaseName) {
		try {
			// DOMConfigurator.configure("Log4j.xml");
			extent = new ExtentReports(Constants.sReportFileName, false);
			// extent.config()
			// .documentTitle("EA Test Report")
			// .reportName("EA Regression")
			// .reportHeadline("");
			// Log.startTestCase(sTestCaseName);
			// optional
			extent.addSystemInfo("Selenium Version", Configuration.SELENIUM_VERSION).addSystemInfo("Environment", "QA");
			test = extent.startTest(sTestCaseName, "TEST CASE");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void logStatus(LogStatus oStatus, String sStepName, String Details) {
		test.log(oStatus, sStepName, Details);
		// Log.info(oStatus+""+""+sStepName+""+Details);

	}

	public void endTest() {
		extent.endTest(test);
		// Log.endTestCase("asdasd");
		extent.flush();
		extent.close();
	}

	public void screenshotLog(LogStatus oStatus, String sStepName, String sFilepath) {
		test.log(oStatus, sStepName + test.addScreenCapture(sFilepath));
	}

}
