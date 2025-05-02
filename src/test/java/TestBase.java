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
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("browserVersion", "128.0");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.pageLoadStrategy = System.getProperty("loadStrategy", "eager");
        Configuration.baseUrl = System.getProperty("baseUrl", "https://centicore.ru");

        String remoteUrl = getRemoteWebDriverUrl();
        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;

        } else {
            Configuration.remote = null;

        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;
    }

    private static String getRemoteWebDriverUrl() {
        String useRemote = System.getProperty("remote", ""); // Если -Dremote передан
        if (useRemote.isEmpty()) {
            return null; // Локальный запуск
        }

        String user = System.getenv("SELENOID_USER");
        String password = System.getenv("SELENOID_PASSWORD");
        String host = System.getProperty("wd", "selenoid.autotests.cloud");



        return String.format("https://%s:%s@%s/wd/hub", user, password, host);
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


