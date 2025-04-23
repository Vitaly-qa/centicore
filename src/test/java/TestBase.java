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
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "125.0");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = System.getProperty("loadStrategy", "eager");
        Configuration.baseUrl = System.getProperty("baseUrl", "https://centicore.ru");

        String remoteUrl = getRemoteWebDriverUrl();
        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;
        } else {
            System.out.println("Запуск локального браузера");
            Configuration.remote = null;
        }

        // Chrome options для запуска в контейнере (например, в Selenoid)
        Map<String, Object> chromeOptions = Map.of(
                "args", List.of(
                        "--headless=new",                        // Новый headless режим
                        "--disable-gpu",
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--window-size=1920,1080",
                        "--remote-allow-origins=*"
                )
        );

        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("acceptInsecureCerts", true);
        capabilities.setCapability("goog:chromeOptions", chromeOptions);
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;
    }

    private static String getRemoteWebDriverUrl() {
        String remote = System.getProperty("remote", "");
        if (remote.isEmpty()) {
            return null;
        }

        String user = System.getenv("SELENOID_USER");  // Чтение из переменной окружения
        String password = System.getenv("SELENOID_PASSWORD");  // Чтение из переменной окружения
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


