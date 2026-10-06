package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {

    private static final By LOGOUT_BUTTON =
            By.xpath("//button[contains(@class, 'Account_button__14Yp3') " +
                    "and normalize-space()='Выход']");

    private static final By CONSTRUCTOR_BUTTON =
            By.xpath("//p[normalize-space()='Конструктор']");

    private static final By LOGO =
            By.cssSelector("div[class*='AppHeader_header__logo']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Клик по кнопке 'Выход'")
    public LoginPage clickLogout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(LOGOUT_BUTTON)
        ).click();

        return new LoginPage(driver);
    }

    @Step("Клик по кнопке 'Конструктор'")
    public MainPage clickConstructor() {
        click(CONSTRUCTOR_BUTTON);
        return new MainPage(driver);
    }

    @Step("Клик по логотипу Stellar Burgers")
    public MainPage clickLogo() {
        click(LOGO);
        return new MainPage(driver);
    }

    @Step("Проверка, что мы в личном кабинете")
    public boolean isLogoutButtonDisplayed() {
        return isDisplayed(LOGOUT_BUTTON);
    }
}