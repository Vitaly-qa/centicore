import com.codeborne.selenide.Condition;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import pages.MainPage;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CenticoreTests extends TestBase {

    private final MainPage mainPage = new MainPage();

    @Test
    @Tag("smoke")
    @Tag("positive")
    @Tag("contacts")
    @DisplayName("Проверка перехода на страницу о компании и отображения адреса")
    void conditionsTest() {
        step("Открываем страницу 'Карьера'", mainPage::openCareerPage);
        step("Нажимаем на 'Контакты'", mainPage::clickContactsLink);
        step("Открываем страницу контактов", mainPage::openContactsPage);

        step("Проверяем заголовок 'Контакты'", () ->
                $(".heading__title").shouldHave(text("Контакты")));

        step("Проверяем отображение адреса", () ->
                $$(".locations__item").findBy(text("Адрес:"))
                        .shouldHave(text("Краснопресненская набережная, 12")));

        step("Проверяем отображение телефона", () ->
                $$(".locations__item").findBy(text("Телефон:"))
                        .shouldHave(text("+7 (495) 161-61-11")));

        step("Проверяем отображение email", () ->
                $$(".locations__item").findBy(text("Email:"))
                        .shouldHave(text("hq@centicore.ru")));
    }

    @Test
    @Tag("smoke")
    @Tag("positive")
    @Tag("vacancy")
    @DisplayName("Выбор вакансии QA Инженер (Java)")
    void jobSelectionTest() {
        step("Открываем страницу вакансий", mainPage::openVacanciesPage);
        step("Выбираем категорию 'Тестирование'", mainPage::selectTestingCategory);
        step("Выбираем вакансию 'QA Инженер (Java)'", mainPage::selectQaJavaVacancy);

        step("Проверяем наличие заголовка вакансии", () ->
                $(".article__top").shouldHave(text("QA Инженер (Java)")));
    }

    @Test
    @Tag("smoke")
    @Tag("positive")
    @Tag("contacts")
    @DisplayName("Переход на страницу Контакты и отправка формы")
    void contactPageTest() {
        step("Открываем страницу 'Контакты'", mainPage::openContactsPage);

        step("Проверяем наличие Москвы", () ->
                $(".locations__subtitle").shouldHave(text("Москва")));

        step("Переход на страницу 'Продажи'", mainPage::clickSalesPage);

        step("Заполняем форму обратной связи", () ->
                mainPage.fillContactForm("Виталий", "vitaliy@example.com", "Куз", "123"));

        step("Отправляем форму", mainPage::submitContactForm);
        step("Проверяем отправку сообщения", mainPage::confirmSuccessModal);
    }

    @Test
    @Tag("smoke")
    @Tag("positive")
    @Tag("integration")
    @DisplayName("Переход на Хабр через кнопку на сайте")
    void goingToTheWebsiteTest() {
        step("Открываем страницу 'Контакты'", mainPage::openContactsPage);
        step("Открываем меню", () -> $(".nav-open").click());
        step("Кликаем по кнопке 'Хабр'", mainPage::goToHabrPage);
        step("Переход на Хабр", mainPage::openHabrWebsite);

        step("Проверка текста на странице Хабра", () ->
                $(".title").shouldHave(text("О компании «Centicore Group»")));
    }

    @Test
    @Tag("smoke")
    @Tag("positive")
    @Tag("technologies")
    @DisplayName("Переход на страницу 'Высокие технологии'")
    void goToTheHighTechnologyPageTest() {
        step("Открываем страницу 'Экспертиза'", mainPage::openExpertisePage);
        step("Выбираем категорию 'Высокие технологии'", mainPage::selectHighTechCategory);

        step("Проверяем заголовок", () ->
                $(".heading__title").shouldHave(text("ВЫСОКИЕ ТЕХНОЛОГИИ")));
    }

    @Test
    @Tag("smoke")
    @Tag("positive")
    @Tag("language")
    @DisplayName("Тапаем на ссылку переключения языка")
    void tapLanguageSwitchTest() {
        step("Открываем главную страницу", mainPage::openHomePage);
        step("Переключаем язык на английский", mainPage::switchToEnglish);
        step("Проверяем, что перешли на английскую версию страницы", () -> {
            String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
            assertTrue(currentUrl.contains("/en/"), "URL не содержит '/en/'");
        });
    }

    @Test
    @Tag("smoke")
    @Tag("positive")
    @Tag("call")
    @DisplayName("Проверка заказа звонка")
    void checkingTheCallOrder() {
        step("Открываем главную страницу", mainPage::openHomePage);
        step("Кликаем по кнопке 'Заказать звонок'", mainPage::clickRequestCallButton);
        step("Заполняем форму заказа звонка", () ->
                mainPage.fillCallForm("Вит", "123456789", "ООО Тест"));
        step("Принимаем Политику конфиденциальности", mainPage::acceptPrivacyPolicy);
        step("Отправляем форму", mainPage::submitCallForm);
    }
}




