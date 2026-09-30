package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT = By.xpath("//input[@name='name']");
    private static final By PASSWORD_INPUT = By.xpath("//input[@name='Пароль']");
    private static final By REGISTER_LINK = By.xpath("//a[text()='Зарегистрироваться']");
    private static final By LOGIN_BUTTON = By.xpath("//button[text()='Войти']");
    private static final By LOGIN_LINK_FROM_FORGOT = By.xpath("//a[text()='Войти']");
    private static final By FORGOT_PASSWORD_LINK = By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввод email: {email}")
    public LoginPage enterEmail(String email) {
        type(EMAIL_INPUT, email);
        return this;
    }

    @Step("Ввод пароля")
    public LoginPage enterPassword(String password) {
        type(PASSWORD_INPUT, password);
        return this;
    }

    @Step("Клик по кнопке 'Войти'")
    public MainPage clickLogin() {
        click(LOGIN_BUTTON);
        return new MainPage(driver);
    }

    @Step("Клик по ссылке 'Зарегистрироваться'")
    public RegisterPage clickRegisterLink() {
        click(REGISTER_LINK);
        return new RegisterPage(driver);
    }

    @Step("Клик по ссылке 'Восстановить пароль'")
    public LoginPage clickForgotPassword() {
        click(FORGOT_PASSWORD_LINK);
        return this;
    }

    @Step("Выполнить вход: {email}")
    public MainPage loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        return new MainPage(driver);
    }


    @Step("Клик по ссылке 'Войти' со страницы восстановления пароля")
    public LoginPage clickLoginLinkFromForgotPassword() {
        click(LOGIN_LINK_FROM_FORGOT);
        return this;
    }
}