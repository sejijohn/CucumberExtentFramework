package com.selenium.base;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import com.selenium.common.BrowserTypes;
import com.selenium.common.Configuration;
import com.selenium.enums.Environment;

public abstract class WebDriverBase {
	
	private static HashMap<Long, WebDriver> map = new HashMap<Long, WebDriver>();
    private static HashMap<Long, BrowserTypes> browserMap = new HashMap<Long, BrowserTypes>();
    private static final long waitMillis = 180;
    protected static final long implicitWaitMillis = 180;
    public static WebDriver webDriver;
    
    public static WebDriver getDriverInstance() {
        WebDriver toReturn = map.get(Thread.currentThread().getId());
        if (toReturn == null) {
            loadWebDriver();
        }
        return map.get(Thread.currentThread().getId());
    }
    
    public void abort() {
        getDriverInstance().close();
        getDriverInstance().quit();
        map.clear();
        webDriver = null;
    }

    public static BrowserTypes getBrowserType() {
        return browserMap.get(Thread.currentThread().getId());

    }

    /**
     * This method is used to sleep the current thread for any duration of time.
     *
     *
     * @param milliseconds This is the amount of milliseconds the thread will sleep.
     */
    public void handledSleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickButton(WebElement webElement) {
        try {
            webElement.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <<<<<<< HEAD
     * This method will wait for the page load to complete or until the timeOut time has elapsed. A
     * negative timeout can cause indefinite page loads.
     * =======
     * This method will wait for the page load to complete or until the timeOut
     * time has elapsed. A negative timeout can cause indefinite page loads.
     * >>>>>>> Shoppers_Deva
     *
     * @param timeOut The timeout value in milliseconds.
     */
    public void waitForPagetoLoad(int timeOut) {
        if (timeOut > 0) {
            getDriverInstance().manage().timeouts()
                    .pageLoadTimeout(timeOut, TimeUnit.SECONDS);
        }
    }

    /**
     * This method returns the text of an element
     *
     * @param elem the element that you wish to retrieve the text from.
     * @return the string of text from the element.
     */
    public String getTextFromElement(WebElement elem) {
        return elem.getText();
    }

    /**
     * This method is used to select a value in the radio group by its value This method needs to be
     * refactored to use web elements.
     *
     * @param radioGroup the radio group to be selected from.
     * @param ValueToSelect the value in the radio group that will be selected.
     */
    public void selectRadioButtonByValue(By radioGroup, String ValueToSelect) {
        // Find the radio group element
        List<WebElement> radioLabels = getDriverInstance().findElements(
                radioGroup);
        for (int i = 0; i < radioLabels.size(); i++) {
            if (radioLabels.get(i).getText().trim()
                    .equalsIgnoreCase(ValueToSelect.trim())) {
                radioLabels.get(i).click();
                break;
            }
        }
    }

    /**
     *
     * This method clicks (Selects) an option passed in the parameter ('ItemToSelect'), in the
     * dropdown ('dropDownList') This method needs to be refactored to use web elements.
     *
     * @param dropDownList the dropdown list to be expanded.
     * @param ItemToSelect the item in the dropdown list to select.
     */
    public void selectItemInDropDown(By dropDownList, String ItemToSelect) {
        WebElement dropDownElement = getDriverInstance().findElement(
                dropDownList);
        List<WebElement> options = dropDownElement.findElements(By
                .tagName("li"));
        for (WebElement option : options) {
            if (option.getText().toUpperCase()
                    .contains(ItemToSelect.toUpperCase())) {
                option.click();
                break;
            }
        }

    }

    /**
     * This method can take a string formatted as a CSS selector and return a web element that the
     * selector finds.
     *
     * @param cssSelector the css string that will be searched for on the page.
     * @return the web element that is found.
     */
    public WebElement findElementByCssSelector(String cssSelector) {
        return getDriverInstance().findElement(By.cssSelector(cssSelector));
    }

    /**
     * This method builds profiles for the specific browser that is sent in. It builds the correct
     * profile and launches that browser from the WebDriverBase WebDriver.
     *
     * @return The WebDriver with the appropriate browser is returned.
     */
   

    public static void loadWebDriver() {
        BrowserTypes browserType = Configuration.BROWSER;
        //BrowserTypes browserType = BrowserTypes.FIREFOX;
        System.out.println(browserType);
        webDriver = null;

        try {
            DesiredCapabilities capabilities;
            switch (browserType) {
                case IE:
                    System.setProperty("webdriver.ie.driver",
                            Configuration.IE_WEBDRIVER);
                    capabilities = DesiredCapabilities.internetExplorer();
                    capabilities.setPlatform(Platform.WINDOWS);
                    capabilities
                            .setCapability(
                                    InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
                                    true);
                    capabilities.setCapability(
                            InternetExplorerDriver.INITIAL_BROWSER_URL,
                            "about:blank");
                    capabilities.setCapability(
                            InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                    capabilities.setCapability(
                            InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
                    capabilities.setCapability(
                            InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,
                            false);
                    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
                            true);
                    if (Configuration.REMOTE) {
                        webDriver = new RemoteWebDriver(new URL(
                                Configuration.SELENIUM_GRID_URL), capabilities);
                    } else {
                        InternetExplorerDriverService service = InternetExplorerDriverService
                                .createDefaultService();
                        webDriver = new InternetExplorerDriver(service,
                                capabilities);
                    }
                    break;

                case FIREFOX:
                	System.setProperty("webdriver.gecko.driver", "/users/sejijohn/documents/geckodriver");
                    ProfilesIni profilesIni = new ProfilesIni();
                    FirefoxProfile firefoxProfile = profilesIni
                            .getProfile("default");
                    capabilities = new DesiredCapabilities();
                    capabilities.setBrowserName("firefox");
                    capabilities.setPlatform(Platform.ANY);
                    if (Configuration.REMOTE) {
                        webDriver = new RemoteWebDriver(new URL(
                                Configuration.SELENIUM_GRID_URL), capabilities);
                    } else {
                        webDriver = new FirefoxDriver(firefoxProfile);
                   }
                	//webDriver = new FirefoxDriver();
                    break;
                case CHROME:
                    System.getenv();
                    System.setProperty("webdriver.chrome.driver",
                            Configuration.CHROME_WEBDRIVER);
                    capabilities = DesiredCapabilities.chrome();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("test-type", "start-maximized",
                            "no-default-browser-check", "disable-popup-blocking");
                    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
                    capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,
                            true);
                    capabilities.setPlatform(Platform.ANY);
                    if (Configuration.REMOTE) {
                        webDriver = new RemoteWebDriver(new URL(
                                Configuration.SELENIUM_GRID_URL), capabilities);
                    } else {
                        webDriver = new ChromeDriver(capabilities);
                    }
                    break;

                case SAFARI:
                    //capabilities = new DesiredCapabilities();
                	capabilities = DesiredCapabilities.safari();
                    capabilities.setPlatform(Platform.MAC);
                    if (Configuration.REMOTE) {
                        webDriver = new RemoteWebDriver(new URL(
                                Configuration.SELENIUM_GRID_URL), capabilities);
                    } else {
                        webDriver = new SafariDriver(capabilities);
                    }
                    break;

                case MOBILE:
                    System.setProperty("webdriver.chrome.driver",
                            Configuration.CHROME_WEBDRIVER);

                    Map<String, String> mobileEmulation = new HashMap<String, String>();
                    mobileEmulation.put("deviceName", Configuration.MOBILE_DEVICE);

                    Map<String, Object> chromeOptions = new HashMap<String, Object>();
                    chromeOptions.put("mobileEmulation", mobileEmulation);

                    capabilities = DesiredCapabilities.chrome();
                    capabilities.setCapability(ChromeOptions.CAPABILITY,
                            chromeOptions);

                    webDriver = new ChromeDriver(capabilities);
                    break;

                default:
                    throw new RuntimeException("Browser type not supported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts()
                .implicitlyWait(implicitWaitMillis, TimeUnit.MILLISECONDS
                );
        if (browserType.equals(BrowserTypes.MOBILE)) {
            webDriver
                    .manage()
                    .window()
                    .setSize(
                            new Dimension(Configuration.MOBILE_WIDTH,
                                    Configuration.MOBILE_HEIGHT));
        }

        webDriver.manage().deleteAllCookies();

        map.put(Thread.currentThread().getId(), webDriver);
        browserMap.put(Thread.currentThread().getId(), browserType);

    }

    public String getFullURL(String relativeURL) {

        String protocol = "http://";

        if (Configuration.USE_SSL) {
            protocol = "https://";
        }

        return protocol
                + Environment.valueOf(
                        Configuration.EXECUTION_ENVIRONMENT.toUpperCase())
                .getUrl() + relativeURL;
    }

    public boolean hasElement(By by) {
        return countElements(by) != 0;
    }

    public boolean hasElement(WebElement element) {
        return element != null;
    }

    public boolean hasElement(SearchContext searchContext, By by) {

        return countElements(searchContext, by) != 0;

    }

    /**
     *
     *
     *
     * {@inheritDoc}
     *
     *
     *
     */
    public int countElements(By by) {

        return countElements(getDriverInstance(), by);

    }

    public int countElements(SearchContext searchContext, By by) {
        int result = 0;
        long currentWaitMillis = implicitWaitMillis;
        try {
            if (currentWaitMillis > 0) {
                getDriverInstance().manage().timeouts()
                        .implicitlyWait(0, TimeUnit.MILLISECONDS);
            }
            result = searchContext.findElements(by).size();
        } finally {
            getDriverInstance().manage().timeouts()
                    .implicitlyWait(currentWaitMillis, TimeUnit.MILLISECONDS);
        }
        return result;

    }

    public void resizeViewport(String deviceType) {
        Dimension dimension = getDriverInstance().manage().window().getSize();
        switch (deviceType.toLowerCase()) {
            case "iphone6":
                dimension = new Dimension(375, 559);
                break;
        }
        getDriverInstance().manage().window().setSize(dimension);
    }

    public void waitTillMultipleWindowHandles() {
        Set<String> allWindows = getDriverInstance().getWindowHandles();
        while (allWindows.size() == 1) {
            allWindows = getDriverInstance().getWindowHandles();
        }
    }

    public void waitTillFindDoctorPageAppear() {
        Set<String> allWindows = getDriverInstance().getWindowHandles();
        while (allWindows.size() <= 1) {
            allWindows = getDriverInstance().getWindowHandles();
        }
    }

    public void switchToLastTab() {

        List<String> browserTabs = new ArrayList<String>(getDriverInstance()
                .getWindowHandles());
        getDriverInstance().switchTo().window(
                browserTabs.get(browserTabs.size() - 1));
    }

    public void switchToFirstWindow() {
        Set<String> handles = getDriverInstance().getWindowHandles();
        for (int i = handles.size(); i > 1; i--) {
            getDriverInstance().switchTo().window(handles.toArray(new String[handles.size()])[i - 1]);
            getDriverInstance().close();
        }
        getDriverInstance().switchTo().window(
                handles.toArray(new String[handles.size()])[0]);
    }

    public void closeTab() {
        getDriverInstance().close();
        List<String> browserTabs = new ArrayList<>(getDriverInstance()
                .getWindowHandles());
        getDriverInstance().switchTo().window(
                browserTabs.get(browserTabs.size() - 1));
    }

}
