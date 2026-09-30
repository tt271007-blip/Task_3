package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private static final By NAME_INPUT = By.xpath("//input[@name='name']");
    private static final By EMAIL_INPUT = By.xpath("//label[text()='Email']/following-sibling::input");
    private static final By PASSWORD_INPUT = By.xpath("//input[@name='Пароль']");
    private static final By REGISTER_BUTTON = By.xpath("//button[text()='Зарегистрироваться']");
    private static final By LOGIN_LINK = By.xpath("//a[text()='Войти']");
    private static final By PASSWORD_ERROR = By.xpath("//p[contains(@class, 'input__error')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввод имени: {name}")
    public RegisterPage enterName(String name) {
        type(NAME_INPUT, name);
        return this;
    }

    @Step("Ввод email: {email}")
    public RegisterPage enterEmail(String email) {
        type(EMAIL_INPUT, email);
        return this;
    }

    @Step("Ввод пароля")
    public RegisterPage enterPassword(String password) {
        type(PASSWORD_INPUT, password);
        return this;
    }

    @Step("Клик по кнопке 'Зарегистрироваться'")
    public LoginPage clickRegister() {
        click(REGISTER_BUTTON);
        return new LoginPage(driver);
    }

    @Step("Клик по ссылке 'Войти'")
    public LoginPage clickLoginLink() {
        click(LOGIN_LINK);
        return new LoginPage(driver);
    }

    @Step("Зарегистрировать пользователя: {name}, {email}")
    public LoginPage registerAs(String name, String email, String password) {
        return enterName(name)
                .enterEmail(email)
                .enterPassword(password)
                .clickRegister();
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