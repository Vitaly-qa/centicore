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
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));

        Configuration.browserCapabilities = capabilities;

        // Установка remote, если нужно
        String remoteUrl = getRemoteWebDriverUrl();
        if (remoteUrl != null) {
            Configuration.remote = remoteUrl;
            System.out.println("Запуск на Selenoid: " + remoteUrl);
        } else {
            System.out.println("Запуск локального браузера");
        }
    }

    private static String getRemoteWebDriverUrl() {
        // Если передан полный URL через -Dremote — используем его
        String fullRemoteUrl = System.getProperty("remote", "");
        if (!fullRemoteUrl.isEmpty()) {
            return fullRemoteUrl;
        }

        // Если переменная remote не задана — пробуем собрать URL из частей
        String user = System.getenv("SELENOID_USER");
        String password = System.getenv("SELENOID_PASSWORD");
        String wdHost = System.getProperty("wd", "selenoid.autotests.cloud");

        if (user != null && password != null) {
            return String.format("https://%s:%s@%s/wd/hub", user, password, wdHost);
        }

        // Ни URL, ни логин с паролем не заданы — значит локальный запуск
        return null;
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


