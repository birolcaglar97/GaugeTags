package com.medium.driver;

import com.thoughtworks.gauge.ExecutionContext;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DriverManager {
    public static WebDriver webDriver;
    public static WebDriverWait webWait;
    private final boolean IS_YOUTUBE = false;
    private final Logger logger;
    private static final String OS = System.getProperty("os.name").toLowerCase();
    public boolean IS_WINDOWS = (OS.contains("win"));
    public boolean IS_MAC = (OS.contains("mac"));
    public boolean IS_UNIX = (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    private List<String> tags;

    public DriverManager() {
        String log4jConfigFile = System.getProperty("user.dir") + File.separator + "src/test/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfigFile);
        logger = Logger.getLogger(DriverManager.class);
    }

    public void initializeDriver(ExecutionContext context) {
        tags = getTags(context);
        if (tags.contains("chrome")) {
            setChromeDriver();
        } else if (tags.contains("firefox")) {
            setFirefoxDriver();
        } else if (tags.contains("edge")) {
            setEdgeDriver();
        } else {
            String msg = "Driver için desteklenen tag girilmedi. Desteklenen tagler => chrome,firefox,edge.";
            logger.error(msg);
            Assertions.fail(msg);
        }
        setWebWaitAndDriver();
    }

    public void closeDriver() {
        if (webDriver != null) {
            webDriver.quit();
            logger.info("Webdriver kapatıldı.");
        }
    }

    public List<String> getTags(ExecutionContext context) {
        return new ArrayList<>(new ArrayList<>(
                context.getCurrentScenario().getTags()));
    }

    private void setWebWaitAndDriver() {
        webDriver.manage().timeouts().scriptTimeout(Duration.ofMinutes(1));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofMinutes(1));
        webWait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
        webWait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        if (tags.contains("youtube")) webDriver.get("https://www.youtube.com");
        else if (tags.contains("medium")) webDriver.get("https://www.medium.com");
        else if (tags.contains("stack")) webDriver.get("https://stackoverflow.com");
        else if (tags.contains("twitter")) webDriver.get("https://www.twitter.com");
        else {
            String msg = "Lütfen desteklenen bir url tagi giriniz. Desteklenen tagler : youtube,medium,stackoverflow,twitter.";
            logger.error(msg);
            Assertions.fail(msg);
        }
        logger.info("WebDriver ayağa kaldırıldı.");
    }

    private void setChromeDriver() {
        if (IS_WINDOWS) WebDriverManager.chromedriver().win().setup();
        else if (IS_MAC) WebDriverManager.chromedriver().mac().setup();
        else if (IS_UNIX) WebDriverManager.chromedriver().linux().setup();
        webDriver = new ChromeDriver(getChromeOptions());
        logger.info("Webdriver chrome seçildi.");
    }

    public ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--ignore-ssl-errors=yes");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-translate");
        options.addArguments("--disable-gpu");
        return options;
    }

    private void setFirefoxDriver() {
        if (IS_WINDOWS) WebDriverManager.firefoxdriver().win().setup();
        else if (IS_MAC) WebDriverManager.firefoxdriver().mac().setup();
        else if (IS_UNIX) WebDriverManager.firefoxdriver().linux().setup();
        webDriver = new FirefoxDriver(getFirefoxOptions());
        logger.info("Webdriver firefox seçildi.");
    }

    public FirefoxOptions getFirefoxOptions() {
        FirefoxProfile profile = new FirefoxProfile();
        FirefoxOptions options = new FirefoxOptions();
        profile.setPreference("browser.download.folderList", 1);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.download.manager.focusWhenStarting", false);
        profile.setPreference("browser.download.useDownloadDir", true);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
        profile.setPreference("browser.download.manager.closeWhenDone", true);
        profile.setPreference("browser.download.manager.showAlertOnComplete", false);
        profile.setPreference("browser.download.manager.useWindow", false);
        profile.setPreference("browser.helperApps.alwaysAsk.force", false);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/octet-stream");
        options.setProfile(profile);
        return options;
    }

    private void setEdgeDriver() {
        if (IS_WINDOWS) WebDriverManager.edgedriver().win().setup();
        else if (IS_MAC) WebDriverManager.edgedriver().mac().setup();
        else if (IS_UNIX) WebDriverManager.edgedriver().linux().setup();
        webDriver = new EdgeDriver(getEdgeOptions());
        logger.info("Webdriver edge seçildi.");
    }

    public EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.setCapability("acceptSslCerts", true);
        options.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, "eager");
        return options;
    }
}