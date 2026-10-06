package praktikum.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class WebDriverConfig {

    public static WebDriver createDriver(String browser) {
        if ("yandex".equalsIgnoreCase(browser)) {
            return createYandexDriver();
        }
        return createChromeDriver();
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    private static WebDriver createYandexDriver() {
        WebDriverManager.chromedriver().setup();

        String yandexPath = findYandexBrowserPath();
        System.setProperty("webdriver.chrome.driver",
                new File("src/test/resources/yandexdriver.exe").getAbsolutePath());

        ChromeOptions options = new ChromeOptions();
        options.setBinary(yandexPath);
        options.addArguments("--start-maximized");

        return new ChromeDriver(options);
    }

    private static String findYandexBrowserPath() {
        String[] paths = {
                System.getenv("LOCALAPPDATA") + "\\Yandex\\YandexBrowser\\Application\\browser.exe",
                "C:\\Program Files (x86)\\Yandex\\YandexBrowser\\Application\\browser.exe",
                "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe"
        };
        for (String p : paths) {
            if (new File(p).exists()) {
                return p;
            }
        }
        throw new RuntimeException("Yandex Browser not found. Install it or specify path manually.");
    }
}