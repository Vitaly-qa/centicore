package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private final SelenideElement contactsLink = $("a[href*='/contacts']");
    private final SelenideElement page3Button = $("a[href*='/page/3']");
    private final SelenideElement mobileQaVacancyLink = $(".vacancies__link");
    private final SelenideElement highTechCategoryLink = $(".categories__subtitle", 2);
    private final SelenideElement habrLink = $(".social__link", 1);
    private final SelenideElement languageSwitchLink = $(".lang__drop a[href*='/en/']");
    private final SelenideElement salesPageLink = $("a[href='/sales/']");
    private final SelenideElement checkboxAgreement = $("label[for='check1']");
    private final SelenideElement submitButton = $(".feedback__btn");
    private final SelenideElement successModalButton = $("#succes_button");
    private final SelenideElement requestCallButton = $(".req-call");
    private final SelenideElement nameInput = $("[name='name1']");
    private final SelenideElement phoneInput = $("[name='phone1']");
    private final SelenideElement organInput = $("[name='organ']");
    private final SelenideElement policyLink = $(byText("Политикой"));
    private final SelenideElement callSubmitButton = $("button .resume__btn-text");

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

    public void openHabrWebsite() {
        open("https://career.habr.com/companies/centicore");
    }

    public void clickContactsLink() {
        contactsLink.click();
    }

    public void selectQaMobileVacancy() {
        mobileQaVacancyLink.click();
    }

    public void selectHighTechCategory() {
        highTechCategoryLink.click();
    }

    public void goToHabrPage() {
        Selenide.executeJavaScript("arguments[0].scrollIntoView(true);", habrLink);
        Selenide.executeJavaScript("arguments[0].click();", habrLink);
    }

    public void clickSalesPage() {
        Selenide.executeJavaScript("arguments[0].click();", salesPageLink);
    }

    public void fillContactForm(String name, String email, String lastName, String phone) {
        $("input[name='name']").setValue(name);
        $("input[name='email']").setValue(email);
        $("input[name='lastName']").setValue(lastName);
        $("input[name='phone']").setValue(phone);
        checkboxAgreement.click();
    }

    public void submitContactForm() {
        submitButton.shouldBe(visible).click();
    }

    public void confirmSuccessModal() {
        successModalButton.click();
    }

    public void selectTestingCategory() {
        executeJavaScript("arguments[0].click();", $(".list"));
        $$("li.option").findBy(Condition.text("Тестирование")).click();
    }

    public void selectQaJavaVacancy() {
        executeJavaScript("arguments[0].click();",
                $$("a.vacancies__link").findBy(Condition.text("QA Инженер (Java)")));
    }

    public void openHomePage() {
        open("/");  // Открывает главную страницу сайта
    }

    public void switchToEnglish() {
        SelenideElement languageSwitchLink = $(".lang__drop a[href*='/en/']");
        executeJavaScript("arguments[0].scrollIntoView(true);", languageSwitchLink);  // Скроллим к элементу
        executeJavaScript("arguments[0].click();", languageSwitchLink);  // Кликаем на элемент
    }


    public void clickRequestCallButton() {
        requestCallButton.shouldBe(Condition.visible).click();
    }

    public void fillCallForm(String name, String phone, String organization) {
        nameInput.shouldBe(Condition.visible).setValue(name);
        phoneInput.setValue(phone);
        organInput.setValue(organization);
    }

    public void acceptPrivacyPolicy() {
        policyLink.click();
    }

    public void submitCallForm() {
        callSubmitButton.click();
    }
}





