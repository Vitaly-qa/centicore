import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.step;

@Tag("smoke")
public class CenticoreTests extends TestBase {

    private MainPage mainPage = new MainPage();

    @Test
    @DisplayName("Проверка перехода на страницу о компании")
    void conditionsTest() {
        step("Открываем страницу работа", () -> {
            mainPage.openCareerPage();
        });
        step("Нажимаем на контакты", () -> {
            mainPage.clickContactsLink();
        });
        step("Проверяем, что на странице содержится заголовок 'Контакты'", () -> {
            mainPage.openContactsPage();
            $(".heading__title").shouldHave(text("Контакты"));
        });
    }

    @Test
    @DisplayName("Выбор вакансии QA Инженер (MOBILE)")
    void jobSelectionTest() {
        step("Открываем страницу вакансии", () -> {
            mainPage.openVacanciesPage();
        });
        step("Кликаем на кнопку страницы номер 3", () -> {
            mainPage.goToPage3(); //
        });
        step("Выбираем вакансию QA Инженер (MOBILE)", () -> {
            mainPage.selectQaMobileVacancy();
        });
        step("Проверяем заголовок на странице вакансии", () -> {
            $(".info__subtitle").shouldHave(text("ПРИСОЕДИНЯЙТЕСЬ К НАМ!"));
        });
    }

    @Test
    @DisplayName("Переходим на страницу Контакты")
    void contactPageTest() {
        step("Переходим на страничку Контактов", () -> {
            mainPage.openContactsPage();
        });
        step("Проверяем наличие Москвы на странице контактов", () -> {
            $(".locations__subtitle").shouldHave(text("Москва"));
        });
    }

    @Test
    @DisplayName("Переходим по кнопке Хабр на сайт Хабр")
    void goingToTheWebsiteTest() {
        step("Переход на страницу Контакты", () -> {
            mainPage.openContactsPage();
            $(".nav-open").click();
        });
        step("Кликаем на кнопку Хабр", () -> {
            mainPage.goToHabrPage();
        });
        step("Переходим на страницу Хабр", () -> {
            mainPage.openHabrWebsite();
        });
        step("Страница содержит текст", () -> {
            $(".title").shouldHave(text("О компании «Centicore Group»"));
        });
    }

    @Test
    @DisplayName("Переходим на страницу Высокие технологии")
    void goToTheHighTechnologyPageTest() {
        step("Переходим на страницу экспертиза", () -> {
            mainPage.openExpertisePage();
        });
        step("Выбираем категорию высокие технологии", () -> {
            mainPage.selectHighTechCategory();
        });
        step("Страница содержит заголовок", () -> {
            $(".heading__title").shouldHave(text("ВЫСОКИЕ ТЕХНОЛОГИИ"));
        });
    }
}





