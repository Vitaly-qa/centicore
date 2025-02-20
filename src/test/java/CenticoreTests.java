import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Tag("smoke")
public class CenticoreTests extends TestBase {

    @Test
    @DisplayName("Проверка перехода на страницу о компании")
    void conditionsTest() {
        step("Открываем страницу работа ", () -> {
            open("/career");
        });
        step("Нажимаем на контакты", () -> {
            $("a[href*='/contacts']").click();
        });
        step("Проверяем, что на странице содержится заголовок 'Контакты'", () -> {
            $(".heading__title").shouldHave(text("Контакты"));
        });
    }

    @Test
    @DisplayName("Выбор вакансии QA Инженер (Java)")
    void jobSelectionTest() {
        step("Открываем страницу вакансии", () -> {
            open("/vacancies");
        });
        step("Кликаем на кнопку страницы номер 3", () -> {
            $("a[href*='/page/3']").click();
        });
        step("Выбираем вакансию QA Инженер (Java)", () -> {
            $(".vacancies__link").click();
        });
        step("Проверяем заголовок на страницы вакансии", () -> {
            $(".info__subtitle").shouldHave(text("ПРИСОЕДИНЯЙТЕСЬ К НАМ!"));
        });

    }


    @Test
    @DisplayName("Переходим на страницу Контакты")
    void contactPageTest() {
        step("Переходим на страничку Контактов", () -> {
            open("/contacts");
        });
        step("Проверяем наличиее Москвы на странице контактов", () -> {
            $(".locations__subtitle").shouldHave(text("Москва"));
        });
    }

    @Test
    @DisplayName("Переходим по кнопке Хабр на сайт Хабр")
    void goingToTheWebsiteTest() {
        step("Переход на страницу Контакты", () -> {
            open("/contacts");
            $(".nav-open").click();
        });
        step("Кликаем на кнопку Хабр", () -> {
            $(".social__link", 1).click();
        });
        step("Переходим на страницу Хабр", () -> {
            open("https://career.habr.com/companies/centicore");
        });
        step("Страница содержит текст", () -> {
            $(".title").shouldHave(text("О компании «Centicore Group»"));
        });
    }

    @Test
    @DisplayName("Переходим на страницу Высокие технологии")
    void goToTheHighTechnologyPageTest() {
        step("Переходим на страницу экспертиза", () -> {
            open("/expertise");
        });
        step("Выбираем категорию высокие технологии", () -> {
            $(".categories__subtitle", 2).click();
        });
        step("Страница содержит заголовок ", () -> {
            $(".heading__title").shouldHave(text("ВЫСОКИЕ ТЕХНОЛОГИИ"));

        });
    }
}






