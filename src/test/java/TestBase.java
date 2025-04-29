import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.util.Map;


public class TestBase {

    @BeforeAll
    static void setUpBrowserConfiguration() {
        String browser = System.getProperty("browser", "chrome");
        String browserVersion = System.getProperty("browserVersion", "128.0");

        Configuration.browser = browser;
        Configuration.browserVersion = browserVersion;
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = System.getProperty("loadStrategy", "eager");
        Configuration.baseUrl = System.getProperty("baseUrl", "https://centicore.ru");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;

        // Указание Selenoid URL
        String remoteUrl = getRemoteWebDriverUrl();
        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;
        } else {
            System.out.println("Запуск локального браузера");
        }
    }

    private static String getRemoteWebDriverUrl() {
        String remote = System.getProperty("remote", "selenoid.autotests.cloud");
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


