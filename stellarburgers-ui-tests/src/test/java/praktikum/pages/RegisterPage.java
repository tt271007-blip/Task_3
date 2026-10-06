package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterPage extends BasePage {

    private static final By NAME_INPUT = By.xpath("//input[@name='name']");
    private static final By EMAIL_INPUT = By.xpath("//label[normalize-space()='Email']/following-sibling::input");
    private static final By PASSWORD_INPUT = By.xpath("//input[@name='Пароль']");
    private static final By REGISTER_BUTTON = By.xpath("//button[normalize-space()='Зарегистрироваться']");
    private static final By LOGIN_LINK = By.xpath("//a[normalize-space()='Войти']");
    private static final By PASSWORD_ERROR = By.xpath("//p[contains(@class, 'input__error')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввод имени: {name}")
    public RegisterPage enterName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(NAME_INPUT))
                .sendKeys(name);
        return this;
    }

    @Step("Ввод email: {email}")
    public RegisterPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT))
                .sendKeys(email);
        return this;
    }

    @Step("Ввод пароля")
    public RegisterPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT))
                .sendKeys(password);
        return this;
    }

    @Step("Клик по кнопке 'Зарегистрироваться'")
    public RegisterPage clickRegister() {
        click(REGISTER_BUTTON);
        return this;
    }

    @Step("Клик по ссылке 'Войти'")
    public LoginPage clickLoginLink() {
        click(LOGIN_LINK);
        return new LoginPage(driver);
    }

    @Step("Зарегистрировать пользователя: {name}, {email}")
    public LoginPage registerAs(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegister();
        return new LoginPage(driver);
    }

    @Step("Получить текст ошибки пароля")
    public String getPasswordError() {
        return getText(PASSWORD_ERROR);
    }

    @Step("Проверить отображение ошибки пароля")
    public boolean isPasswordErrorDisplayed() {
        return isDisplayed(PASSWORD_ERROR);
    }
}