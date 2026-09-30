package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {

    private static final By LOGOUT_BUTTON = By.xpath("//button[text()='Выйти']");
    private static final By CONSTRUCTOR_BUTTON = By.xpath("//p[text()='Конструктор']");
    private static final By LOGO = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Клик по кнопке 'Выйти'")
    public LoginPage clickLogout() {
        click(LOGOUT_BUTTON);
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

    @Step("Проверка, что мы в личном кабинете (видна кнопка 'Выйти')")
    public boolean isLogoutButtonDisplayed() {
        return isDisplayed(LOGOUT_BUTTON);
    }
}