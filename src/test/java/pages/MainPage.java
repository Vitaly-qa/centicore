package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class MainPage {

    // Элементы страницы
    private SelenideElement contactsLink = $("a[href*='/contacts']");
    private SelenideElement page3Button = $("a[href*='/page/3']");
    private SelenideElement mobileQaVacancyLink = $(".vacancies__link");
    private SelenideElement vacancyTitle = $(".info__subtitle");
    private SelenideElement moscowLocation = $(".locations__subtitle");
    private SelenideElement highTechCategoryLink = $(".categories__subtitle", 2);
    private SelenideElement headingTitle = $(".heading__title");
    private SelenideElement habrLink = $(".social__link", 1);

    // Методы для действий
    public void openCareerPage() {
        open("/career");
    }

    public void openVacanciesPage() {
        open("/vacancies");
    }

    public void openContactsPage() {
        open("/contacts");
    }

    public void openExpertisePage() {
        open("/expertise");
    }

    public void clickContactsLink() {
        contactsLink.click();
    }

    public void goToPage3() {
        page3Button.click();
    }

    public void selectQaMobileVacancy() {
        mobileQaVacancyLink.click();
    }


    public void selectHighTechCategory() {
        highTechCategoryLink.click();
    }


    public void goToHabrPage() {
        habrLink.click();
    }

    public void openHabrWebsite() {
        open("https://career.habr.com/companies/centicore");
    }

}




