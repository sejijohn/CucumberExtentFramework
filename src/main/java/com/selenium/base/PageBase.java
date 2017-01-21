package com.selenium.base;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.Annotations;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Predicate;
import com.relevantcodes.extentreports.LogStatus;
import com.selenium.common.Configuration;
import com.selenium.common.Constants;
import com.selenium.common.Reports;
import com.selenium.maps.DataMaps;
import com.selenium.page.enums.PageTitleEnum;

public class PageBase extends WebDriverBase {
	private static final int WAIT_IN_SECONDS = 20;
	public static final int PAGE_LOAD_TIMEOUT_SECONDS = 180;
	public static String firstName;
	public static final String LOADING_IMAGE = "ajaxLoadingImageDiv";
	public static Reports report = new Reports();
	public static HashMap<WebElement, String> elementDetails = new HashMap<WebElement, String>();

	public PageBase() {
		PageFactory.initElements(getDriverInstance(), this);
		waitForPagetoLoad(PAGE_LOAD_TIMEOUT_SECONDS);
		//report = new Reports();
		//report.startTest("TC1");

		/**
		 * The PageBase constructor is primarily used to check if AngularJS is
		 * on the page and if it is, wait until it has finished it's last
		 * request up to 5 seconds(or as defined in the code below).
		 */
		JavascriptExecutor executor = (JavascriptExecutor) getDriverInstance();
		getDriverInstance().manage().timeouts().setScriptTimeout(WAIT_IN_SECONDS, TimeUnit.SECONDS);
		String angularResult = "";

		try {
			angularResult = executor.executeScript("return angular.version;").toString();
		} catch (Exception e) {
			// AngularJS is not defined on the page.
		}
		if (!angularResult.contains("angular is not defined")) {
			long start = System.currentTimeMillis();
			try {
				executor.executeScript("var callback = arguments[arguments.length - 1];"
						+ "angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);");
			} catch (Exception e) {
				// Reporter.log("AngularJS timed out.<br>");
			}
		}

		// handledSleep(500);
	}

	/**
	 * This method uses WebDriver's getCurrentUrl method to return the URL
	 * currently in focus by the WebDriver.
	 *
	 * @return a string of the current URL.
	 */
	public String getUrl() {
		return getDriverInstance().getCurrentUrl();
	}

	/**
	 * This method returns the title of the current browser.
	 *
	 * @return the title string.
	 */
	public static String getTitle() {
		return getDriverInstance().getTitle().trim();
	}

	/**
	 * This will verify that an element does not exist, use this if you expect
	 * no element to be on the screen. This works for elements that don't exist,
	 * and elements that are hidden.
	 *
	 * @param by
	 * @return
	 */
	public boolean hasNoElementAsExpected(By by) {
		WebElement element;
		try {
			element = (new WebDriverWait(getDriverInstance(), 5))
					.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception ex) {
			return true;
		}

		return element == null || !element.isDisplayed();
	}

	public boolean hasNoElementAsExpected(WebElement element) {
		return element != null;
	}

	/**
	 * This method will return the page source as you would see it in a web
	 * browser.
	 * <p>
	 * This can be used to assert specific text exists in the page if the
	 * location is not important.
	 *
	 * @return the page source
	 */
	public String getPageSource() {
		return getDriverInstance().getPageSource();
	}

	public static WebElement waitForElement(WebElement element) {
		return waitForElement(element, WAIT_IN_SECONDS);
	}

	public static WebElement waitForElement(WebElement element, long waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), waitInSeconds);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.pollingEvery(2, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.visibilityOf(element));
		/*
		 * if (Configuration.DEMO) { highlightElement(element, 2000); }
		 */
		return element;
	}

	public WebElement waitForElementClickable(WebElement element) {
		return waitForElementClickable(element, WAIT_IN_SECONDS);
	}

	public WebElement waitForElementClickable(WebElement element, long waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), waitInSeconds);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.pollingEvery(2, TimeUnit.SECONDS);
		wait.until(ExpectedConditions.elementToBeClickable(element));
		if (Configuration.DEMO) {
			highlightElement(element, 2000);
		}
		return element;
	}

	public WebElement waitForElementBy(final By by) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), WAIT_IN_SECONDS);
		wait.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver t) {
				return hasElement(by);
			}
		});
		return getDriverInstance().findElement(by);
	}

	public List<WebElement> waitForElementsBy(final By by) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), WAIT_IN_SECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.until(new Predicate<WebDriver>() {
			@Override
			public boolean apply(WebDriver t) {
				return t.findElements(by).size() > 0;
			}
		});
		return getDriverInstance().findElements(by);
	}

	public WebElement waitForElement(By by) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), WAIT_IN_SECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		WebElement element = getDriverInstance().findElement(by);
		if (Configuration.DEMO) {
			highlightElement(element, 2000);
		}
		return element;
	}

	public WebElement waitForHiddenElement(By by) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), WAIT_IN_SECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.until(ExpectedConditions.presenceOfElementLocated(by));
		WebElement element = getDriverInstance().findElement(by);
		if (Configuration.DEMO) {
			highlightElement(element, 2000);
		}
		return element;
	}

	public WebElement waitForElementClickable(By by) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), WAIT_IN_SECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.until(ExpectedConditions.elementToBeClickable(by));
		WebElement element = getDriverInstance().findElement(by);
		if (Configuration.DEMO) {
			highlightElement(element, 2000);
		}
		return element;
	}

	public WebElement waitForElementClickableNoError(By by, int timeoutMilliseconds) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), timeoutMilliseconds);
		WebElement element = null;
		try {
			wait.ignoring(NoSuchElementException.class);
			wait.ignoring(StaleElementReferenceException.class);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			element = getDriverInstance().findElement(by);
			if (Configuration.DEMO) {
				highlightElement(element, 2000);
			}
		} catch (Exception e) {
			// do nothing when not found
		}
		return element;
	}

	public void waitForElementNotAnimated(final By by) {
		new FluentWait<WebDriver>(getDriverInstance()).withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver d) {
						// String js = "return $(\":animated\").length";
						// String js = "return $(arguments[0]).is(':animated')";
						// String returnValue = ((JavascriptExecutor)
						// getDriverInstance()).
						// executeScript(js).toString();
						// return returnValue.equalsIgnoreCase("0") ?
						// true:false;
						WebElement e = getDriverInstance().findElement(by);
						String holder = e.getAttribute("ng-class");
						String animateHolder = e.getAttribute("animate");
						String animate3Holder = e.getAttribute("ng-style");
						String animate4Holder = e.getAttribute("style");
						return false;
					}

				});
	}

	public WebElement waitForElementGone(WebElement element) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), 15);
		wait.until(ExpectedConditions.stalenessOf(element));
		return element;
	}

	public By waitForElementGone(By element) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), 15);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
		return element;
	}

	public void waitForElementGone(By element, int timeout) {
		WebDriverWait wait = new WebDriverWait(getDriverInstance(), timeout);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
	}

	public void waitForElementNotStale(final By by) {
		new FluentWait<WebDriver>(getDriverInstance()).withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).until(new ExpectedCondition<Boolean>() {
					@Override
					public Boolean apply(WebDriver d) {
						try {
							d.findElement(by);
						} catch (StaleElementReferenceException e) {
							return false;
						}
						return true;
					}

				});
	}

	public boolean waitForFindToFail(final By by, int timeoutSeconds) {
		try {
			new FluentWait<WebDriver>(getDriverInstance()).withTimeout(timeoutSeconds, TimeUnit.SECONDS)
					.pollingEvery(200, TimeUnit.MILLISECONDS).until(new ExpectedCondition<Boolean>() {
						@Override
						public Boolean apply(WebDriver d) {
							try {
								d.findElement(by);
							} catch (Exception e) {
								return true;
							}
							return false;
						}

					});
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void setImplicitWait(int waitInSseconds) {
		getDriverInstance().manage().timeouts().implicitlyWait(waitInSseconds, TimeUnit.SECONDS);
	}

	public void highlightElement(WebElement element, int millisecond) {
		if (getDriverInstance() instanceof JavascriptExecutor) {
			((JavascriptExecutor) getDriverInstance()).executeScript("arguments[0].style.border='3px solid red'",
					element);
		}
		handledSleep(millisecond);
	}

	public void clickElementWithJavascript(WebElement element) {
		if (getDriverInstance() instanceof JavascriptExecutor) {
			((JavascriptExecutor) getDriverInstance()).executeScript("arguments[0].click()", element);
		}
	}

	public void selectComboItem(WebElement parent, String optionToSelect) {
		WebElement clickableStateDropDown = parent.findElement(By.xpath("//*[@class ='psButton btn btn-primary']"));

		clickableStateDropDown.click();

		List<WebElement> options = parent.findElements(By.tagName("label"));
		for (WebElement option : options) {
			if (option.getText().equals(optionToSelect)) {
				option.click();
				break;
			}
		}
	}

	public void selectItemByText(WebElement parent, String optionToSelect) {
		waitForElement(parent);
		Select select = new Select(parent);
		select.selectByVisibleText(optionToSelect);
	}

	public void selectItemByIndex(WebElement parent, int index) {
		waitForElement(parent);
		Select select = new Select(parent);
		select.selectByIndex(index);
	}

	public void executeScript(String script) {
		JavascriptExecutor executor = (JavascriptExecutor) getDriverInstance();
		executor.executeScript(script);

	}

	/**
	 * This is an efficient version of wait that allows the code to perform
	 * waits in tiers. It attempts the action after every provided wait time
	 * until action returns true, or all of the waits are run. It will only run
	 * as many waits as are required for action to return true.
	 *
	 * Example Usage: public boolean isExampleElementDisplayed() throws
	 * Exception { return waitTiered(new Callable<Boolean>() {
	 *
	 * @Override public Boolean call() throws Exception { return
	 *           hasElement(exampleElementBy); } }, 100, 500, 1000, 2800); }
	 *
	 *           This looks ugly at first glance, but intellij will autogenerate
	 *           most of it when you start typing new Callable. I'm not sure if
	 *           eclipse autogenerates, it didn't seem to from my initial look.
	 *           +1 for IntelliJ This will wait 100 milliseconds, then execute
	 *           the method named call(). If that returns true, waitTiered will
	 *           return true. If not, waitTiered will repeat for 500, then 1000,
	 *           then 2800 milliseconds.
	 *
	 *           Primary Uses/BenefitsPage: There are some situations (such as
	 *           waiting for animations) where we don't know how long it will
	 *           take and we don't have any events to work with that tell us
	 *           when the action is completed. One solution is to add a wait for
	 *           a specific number of seconds. Let's say it will normally take
	 *           between 100 and 5000 milliseconds for the action we are waiting
	 *           for to finish. With one wait, we would need to wait 5000
	 *           milliseconds every time to ensure that the action has
	 *           completed.
	 *
	 *           With waitTiered, we can break that 5 seconds into smaller waits
	 *           to return faster when the website is running faster and still
	 *           wait longer if the website requires. This is far more efficient
	 *           then just waiting. Using the example above, let's say that it
	 *           takes 200 milliseconds to run action. Best Case (Success): 100
	 *           ms wait + 200 ms action = 300 ms
	 *
	 *           Worst Case (Success or Failure): 100 ms wait + 200 ms action +
	 *           500 ms wait + 200 ms action + 1000 ms wait + 200 ms action +
	 *           2800 ms wait + 200 ms action = 5200 ms
	 *
	 *           Single Wait Best and Worst Case 5000 ms wait + 200 ms action =
	 *           5200 ms
	 *
	 *           If you set up the wait times correctly it can run in the same
	 *           amount of time as a normal wait in the worst case, and better
	 *           in every other case.
	 *
	 * @param action
	 *            - An implementation of the Callable interface that must return
	 *            a Boolean. Anonymous functions work well for this.
	 * @param waitTimesInMilliseconds
	 *            - This is 1 to many wait times. If you don't provide a value
	 *            this method will throw an exception. The waits will be
	 *            performed in the order they are passed in here. For efficiency
	 *            I recommend you provide the waits in ascending order.
	 * @return - True if action successfully runs after any of the waits, False
	 *         otherwise.
	 * @throws Exception
	 *             - If no wait times are provided, the wait fails, or any
	 *             exception raised by action.
	 */
	public boolean waitTiered(Callable<Boolean> action, long... waitTimesInMilliseconds) throws Exception {
		Boolean results = false;

		if (waitTimesInMilliseconds.length == 0) {
			throw new IllegalArgumentException("You must provide at least one wait time");
		}

		for (long waitTime : waitTimesInMilliseconds) {
			Thread.sleep(waitTime);

			if (results = action.call()) {
				break;
			}
		}

		return results;
	}

	public Dimension getWindowDimensions() {
		return getDriverInstance().manage().window().getSize();
	}

	/**
	 * Retrieves the By associated with a WebElement that uses the FindBy
	 * annotation.
	 *
	 * @return
	 */
	public By getByFromFindBy(String fieldName) throws NoSuchFieldException {
		return new Annotations(this.getClass().getDeclaredField(fieldName)).buildBy();
	}

	/**
	 * Normally getText will return the text for an element, including all
	 * children. This removes the children.
	 *
	 * @param element
	 * @return
	 */
	public String getTextIgnoreChildren(WebElement element) {
		String rtn = element.getText();

		for (WebElement child : element.findElements(By.xpath("./*"))) {
			rtn = rtn.replace(child.getText(), "");
		}

		return rtn;
	}

	public void navigateToURL(String mapURL) {
		getDriverInstance().navigate().to(mapURL);
		// report.logStatus(LogStatus.PASS, Configuration.BROWSER + " URL: "+
		// mapURL, "Passed");
		report.logStatus(LogStatus.PASS, "OpenBrowser: " + Configuration.BROWSER + "," + mapURL.substring(0, 20),
				"Passed");

	}

	public boolean waitForFeedbackPopUpGone() {
		boolean isFeedbackPopUpHandled = false;
		try {
			waitForElement(
					getDriverInstance().findElement(By.cssSelector("a[title*='Click to close'], .fsrDeclineButton")))
							.click();
			isFeedbackPopUpHandled = true;
		} catch (Exception e) {
			System.out.println("popUp not found");
		}
		return isFeedbackPopUpHandled;
	}

	public void navigateToSiteURL(/* String member, */ String portal) {
		switch (portal.toUpperCase()) {
		case "APPLE":

			navigateToURL(DataMaps.dataMap.get("QA SIT1"));

			/*
			 * if
			 * (hasElement(By.cssSelector("#button_right input[type='submit']"))
			 * ) { getDriverInstance().findElement(
			 * By.cssSelector("#button_right input[type='submit']")) .click(); }
			 * if (hasElement(By.linkText("Producer Toolbox Login Page"))) {
			 * getDriverInstance().findElement(
			 * By.linkText("Producer Toolbox Login Page")).click(); }
			 */
			break;
		case "ust-global":
			navigateToURL(DataMaps.dataMap.get("QA SIT2"));
			break;
		case "google":
			navigateToURL(DataMaps.dataMap.get("QA SIT3"));
		default:
			break;
		}
	}

	public String getRandomEmail() {
		String email = "testuser" + RandomStringUtils.randomAlphanumeric(6).toLowerCase() + "@gmail.com";
		return email;
	}

	public String getRandomFirstName() {
		String fname = "test" + RandomStringUtils.randomAlphabetic(3);
		return fname;
	}

	public String getRandomLastName() {
		String lname = "user" + RandomStringUtils.randomAlphabetic(3);
		return lname;
	}

	public String getRandomDOB() {
		int date = (int) (Math.random() * (29 - 1));
		int month = (int) (Math.random() * (12 - 1));
		int year = (int) (Math.random() * (94 - 90));

		if (date == 0) {
			date = 1;
		}
		if (month == 0) {
			month = 1;
		}

		String dob = String.valueOf(month) + "/" + String.valueOf(date) + "/199" + String.valueOf(year);
		return dob;
	}

	public void waitForElementEnable(final By element, final String attribute) {

		WebDriverWait wait = new WebDriverWait(getDriverInstance(), WAIT_IN_SECONDS);
		wait.until(new ExpectedCondition<Boolean>() {

			@Override
			public Boolean apply(WebDriver d) {
				try {
					return d.findElement(element).getAttribute(attribute).equalsIgnoreCase("false");
				} catch (Exception e) {
					return false;
				}
			}
		});
	}

	public void WaitForLoadImageGone() {
		waitForElementGone(By.id(LOADING_IMAGE));
	}

	/* Methods based on user actions */

	public static boolean verifyTitle(String title) {

		String actualTitle = getTitle();
		String expectedTitle = PageTitleEnum.valueOf(title.trim().toUpperCase()).getInfo().getTitle();
		if (actualTitle.equals(expectedTitle)) {
			System.out.println("TITLE VERIFIED");
			takeScreenShot(actualTitle);
			report.screenshotLog(LogStatus.PASS,
					"<table style='border:solid black 1px;'><col width='400'><col width='400'><tr style='border:solid black 1px;'><td colspan='2'><b>Verify Text Passed</b></td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'> Actual</td><td style='border:solid black 1px;'>Expected</td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'>"
							+ actualTitle + "</td><td style='border:solid black 1px;'>" + expectedTitle
							+ "</td></tr></table>",
					Constants.sScreenshotFilepath + expectedTitle + ".jpeg");
			report.logStatus(LogStatus.PASS, "Title verified",
					actualTitle + " <span class='label success'>success</span>");
			return true;

		} else {
			takeScreenShot(actualTitle);
			report.screenshotLog(LogStatus.FAIL,
					"<table style='border:solid black 1px;'><col width='400'><col width='400'><tr style='border:solid black 1px;'><td colspan='2'><b>Verify Text Passed</b></td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'> Actual</td><td style='border:solid black 1px;'>Expected</td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'>"
							+ actualTitle + "</td><td style='border:solid black 1px;'>" + expectedTitle
							+ "</td></tr></table>",
					Constants.sScreenshotFilepath + expectedTitle + ".jpeg");
			return false;
		}
	}

	public static void takeScreenShot(String fileName) {
		try {
			File screenshot = ((TakesScreenshot) getDriverInstance()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshot, new File(Constants.sScreenshotFilepath + fileName + ".jpeg"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static Reports endReport() {
		return report;
	}

	public static boolean clickElement(WebElement element) {
		try {
			element = waitForElement(element);
			if (element != null) {
				takeScreenShot(PageBase.elementDetails.get(element));
				element.click();
				report.logStatus(LogStatus.PASS, "Click", "Clicked " + PageBase.elementDetails.get(element)
						+ " <span class='label success'>success</span>");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
			takeScreenShot(PageBase.elementDetails.get(element));
			report.screenshotLog(LogStatus.FAIL, "Click  Object",
					Constants.sScreenshotFilepath + PageBase.elementDetails.get(element) + ".jpeg");
			report.endTest();
			return false;
		}
		return false;
	}

	public static boolean verifyTextPresent(WebElement element, String inputText) {
		String expectedText = null;
		String actualText = null;
		Boolean bFound = false;
		try {
			element = waitForElement(element);
			expectedText = inputText.trim().toUpperCase();
			actualText = waitForElement(element).getAttribute("textContent").trim().toUpperCase();
			if (actualText.equalsIgnoreCase("")) {
				try {
					actualText = element.getAttribute("value");
				} catch (Exception e) {
				}
				try {
					actualText = element.getAttribute("innerHTML");
				} catch (Exception e) {
				}
				try {
					actualText = element.getAttribute("text");
				} catch (Exception e) {
				}
			}
			if (actualText.equals(expectedText)) {
				bFound = true;
				System.out.println("Verify Text of " + expectedText + " passed");
				takeScreenShot(actualText);
				report.screenshotLog(LogStatus.PASS,
						"<table style='border:solid black 1px;'><col width='400'><col width='400'><tr style='border:solid black 1px;'><td colspan='2'><b>Verify Text Passed</b></td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'> Actual</td><td style='border:solid black 1px;'>Expected</td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'>"
								+ actualText + "</td><td style='border:solid black 1px;'>" + expectedText
								+ "</td></tr></table>",
						Constants.sScreenshotFilepath + expectedText + ".jpeg");

			} else {
				System.out.println("Verify Text of " + expectedText + " failed");
				takeScreenShot(actualText);
				report.screenshotLog(LogStatus.FAIL,
						"<table style='border:solid black 1px;'><tr style='border:solid black 1px;'><td colspan='2'><b>Verify Text Failed</b></td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'> Actual</td><td style='border:solid black 1px;'>Expected</td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'>"
								+ actualText + "</td><td style='border:solid black 1px;'>" + expectedText
								+ "</td></tr></table>",
						Constants.sScreenshotFilepath + expectedText + ".jpeg");
			}
		} catch (Exception e) {
			System.out.println(e);
			bFound = false;
		}
		return bFound;
	}

	public static boolean unCheckBox(WebElement element) {
		Boolean bFound = false;
		try {
			element = waitForElement(element);
			String checked = element.getAttribute("checked");
			if (!checked.equals(false)) {
				element.click();
				bFound = true;
			}
		} catch (Exception e) {
			bFound = false;
		}
		return bFound;
	}

	public static boolean verifyElementExists(WebElement element) {
		try {
			element = waitForElement(element);
			if (element.isDisplayed()) {
				System.out.println("Object is identified" + elementDetails.get(element));
				takeScreenShot(elementDetails.get(element));
				report.screenshotLog(LogStatus.PASS,
						"<table style='border:solid black 1px;'><tr style='border:solid black 1px;'><td colspan='2'><b>Object Verified</b></td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'> Actual</td><td style='border:solid black 1px;'>Expected</td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'>"
								+ elementDetails.get(element) + "</td><td style='border:solid black 1px;'>"
								+ elementDetails.get(element) + "</td></tr></table>",
						Constants.sScreenshotFilepath + elementDetails.get(element) + ".jpeg");
			} else {
				System.out.println("Not able to find" + elementDetails.get(element));
				takeScreenShot(elementDetails.get(element));
				report.screenshotLog(LogStatus.PASS,
						"<table style='border:solid black 1px;'><tr style='border:solid black 1px;'><td colspan='2'><b>Verify Ttile Passed</b></td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'> Actual</td><td style='border:solid black 1px;'>Expected</td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'>"
								+ "NULL" + "</td><td style='border:solid black 1px;'>" + elementDetails.get(element)
								+ "</td></tr></table>",
						Constants.sScreenshotFilepath + elementDetails.get(element) + ".jpeg");
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean verifyRadioSelected(WebElement element) {
		boolean bFound = false;
		try {
			element = waitForElement(element);
			if (element != null) {
				String sSelected = element.getAttribute("checked");
				if (sSelected.equals("true")) {
					takeScreenShot(elementDetails.get(element));
					report.screenshotLog(LogStatus.PASS,
							"<table style='border:solid black 1px;'><tr style='border:solid black 1px;'><td colspan='2'><b>Verify Radio Button</b></td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'> Table Name </td><td style='border:solid black 1px;'>State</td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'>"
									+ elementDetails.get(element) + "</td><td style='border:solid black 1px;'>"
									+ elementDetails.get(element) + "</td></tr></table>",
							Constants.sScreenshotFilepath + elementDetails.get(element) + ".jpeg");
				} else
					report.logStatus(LogStatus.PASS, "Check Radio Selection Passed ",
							"Check Radio Selection Passed <span class='label success'>Pass</span>");
				bFound = true;

			}
		} catch (Exception e) {
			takeScreenShot(elementDetails.get(element));
			report.screenshotLog(LogStatus.FAIL,
					"<table style='border:solid black 1px;'><tr style='border:solid black 1px;'><td colspan='2'><b>Verify Radio Button failed</b></td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'> Table Name </td><td style='border:solid black 1px;'>State</td></tr><tr style='border:solid black 1px;'><td style='border:solid black 1px;'>"
							+ elementDetails.get(element) + "</td><td style='border:solid black 1px;'>"
							+ elementDetails.get(element) + "</td></tr></table>",
					Constants.sScreenshotFilepath + elementDetails.get(element) + ".jpeg");
			return bFound;
		}
		return bFound;
	}
	
	public static boolean verifyRadioNotSelected(WebElement element) {
		try {
			element = waitForElement(element);
			if (element != null) {
				String sSelected = element.getAttribute("checked");
				if (sSelected != "checked") {

					takeScreenShot(elementDetails.get(element));
					report.logStatus(LogStatus.PASS, "Check Not Radio Selection", "Selected Radio "
							+ elementDetails.get(element) + " <span class='label success'>success</span>");

					return true;
				}
			}

		} catch (Exception e) {
			report.logStatus(LogStatus.FAIL, "Check Not Radio Selection Failed ", "Check Radio Selection Failed "
					+ elementDetails.get(element) + " <span class='label failure'>Fail</span>");
			return false;
		}
		return false;
	}
	
	public static boolean ClearText(WebElement element) {
		try {
			element = waitForElement(element);
			if (element != null) {
				element.clear();
			}
		} catch (Exception e) {
			System.out.println("Cleartext failed on " + elementDetails.get(element));
			return false;
		}
		return true;
	}
	
	public static boolean EnterText(WebElement element, String input) {
		try {
			element = waitForElement(element);
			if (element != null) {
				element.clear();
				element.sendKeys(input);
			}
		} catch (Exception e) {
			takeScreenShot(elementDetails.get(element));
			report.screenshotLog(LogStatus.FAIL, "Enter Text",
					Constants.sScreenshotFilepath + elementDetails.get(element) + ".jpeg");
			return false;
		}
		report.logStatus(LogStatus.PASS, "Enter Text",
				"Enter Text into " + elementDetails.get(element) + " <span class='label success'>Success</span>");
		return true;
	}
	
	public static boolean SelectElement(WebElement element, String input) {
		try {
			element = waitForElement(element);
			if (element != null) {
				Select dropdown = new Select(element);
				String content = element.getText();
				if (!content.contentEquals(input))
					dropdown.selectByVisibleText(input);
			}
		} catch (Exception e) {
			takeScreenShot(elementDetails.get(element));
			report.screenshotLog(LogStatus.FAIL, "Select Element",
					Constants.sScreenshotFilepath + elementDetails.get(element) + ".jpeg");
			System.out.println("Drop down selection issue");

			return false;
		}

		report.logStatus(LogStatus.PASS, "Select Element",
				"Select Element For " + elementDetails.get(element) + " <span class='label success'>Success</span>");
		return true;
	}
	
	public static boolean checkCheckBox(WebElement element) {
		Boolean bFound = false;
		try {
			element = waitForElement(element);
			String checked = element.getAttribute("checked");
			if (!checked.equals(true)) {
				element.click();
				bFound = true;
			}
		} catch (Exception e) {
			bFound = false;
		}
		return bFound;
	}

}
