import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;
import java.util.Map;


public class TestBase {

    @BeforeAll
    static void setUpBrowserConfiguration() {
        String browser = System.getProperty("browser", "chrome");
        String browserVersion = System.getProperty("browserVersion", browser.equals("firefox") ? "125.0" : "125.0");

        Configuration.browser = browser;
        Configuration.browserVersion = browserVersion;
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = System.getProperty("loadStrategy", "eager");
        Configuration.baseUrl = System.getProperty("baseUrl", "https://centicore.ru");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("chrome")) {
            Map<String, Object> chromeOptions = Map.of(
                    "args", List.of(
                            "--remote-allow-origins=*",
                            "--proxy-bypass-list=<-loopback>",
                            "--disable-dev-shm-usage",
                            "--window-size=1920,1080"
                    ),
                    "excludeSwitches", List.of("enable-automation", "load-extension"),
                    "prefs", Map.of(
                            "credentials_enable_service", false,
                            "profile.default_content_setting_values.automatic_downloads", 1,
                            "safebrowsing.enabled", true,
                            "plugins.always_open_pdf_externally", true
                    )
            );
            capabilities.setCapability("goog:chromeOptions", chromeOptions);
        }

        if (browser.equalsIgnoreCase("firefox")) {
            if (!browserVersion.equals("122.0") && !browserVersion.equals("125.0")) {
                throw new IllegalArgumentException("Поддерживаются только версии Firefox: 122.0 и 125.0");
            }

            // Настройки Firefox через FirefoxOptions
            org.openqa.selenium.firefox.FirefoxOptions firefoxOptions = new org.openqa.selenium.firefox.FirefoxOptions();
            firefoxOptions.addArguments("--headless");
            firefoxOptions.addArguments("--width=1920");
            firefoxOptions.addArguments("--height=1080");

            capabilities.setCapability(org.openqa.selenium.firefox.FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        }

        // Общие настройки Selenoid
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;

        String remoteUrl = getRemoteWebDriverUrl();
        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;
        } else {
            System.out.println("Запуск локального браузера");
        }
    }

    private static String getRemoteWebDriverUrl() {
        String remote = System.getProperty("remote", "");
        if (remote.isEmpty()) {
            return null;
        }

        String user = System.getenv("SELENOID_USER");
        String password = System.getenv("SELENOID_PASSWORD");
        String wdHost = System.getProperty("wd", "selenoid.autotests.cloud");

        if (user == null || password == null) {
            throw new IllegalStateException("Selenoid user or password not defined in environment variables");
        }

        return String.format("https://%s:%s@%s/wd/hub", user, password, wdHost);
    }

    @BeforeEach
    void addSelenideLogger() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        Selenide.closeWebDriver();
    }
}


