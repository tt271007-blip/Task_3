package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LoginPage extends BasePage {

    private static final By EMAIL_INPUT =
            By.xpath("//input[@name='name']");

    private static final By PASSWORD_INPUT =
            By.xpath("//input[@name='Пароль']");

    private static final By REGISTER_LINK =
            By.xpath("//a[text()='Зарегистрироваться']");

    private static final By LOGIN_BUTTON =
            By.xpath("//button[text()='Войти']");

    private static final By LOGIN_LINK_FROM_FORGOT =
            By.xpath("//a[text()='Войти']");

    private static final By FORGOT_PASSWORD_LINK =
            By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ввод email: {email}")
    public LoginPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL_INPUT))
                .sendKeys(email);
        return this;
    }

    @Step("Ввод пароля")
    public LoginPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT))
                .sendKeys(password);
        return this;
    }

    @Step("Клик по кнопке 'Войти'")
    public MainPage clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON))
                .click();

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

    @Step("Клик по ссылке 'Войти' со страницы восстановления пароля")
    public LoginPage clickLoginLinkFromForgotPassword() {
        click(LOGIN_LINK_FROM_FORGOT);
        return this;
    }

    private void enableNetworkLogger() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "window.__networkLogs = [];" +

                        "const originalFetch = window.fetch;" +
                        "window.fetch = function() {" +
                        "   const args = arguments;" +
                        "   let url = typeof args[0] === 'string' ? args[0] : args[0].url;" +
                        "   return originalFetch.apply(this, args)" +
                         "       .then(function(response) {" +
                        "           window.__networkLogs.push('FETCH ' + url + ' -> ' + response.status);" +
                        "           return response;" +
                        "       })" +
                        "       .catch(function(error) {" +
                        "           window.__networkLogs.push('FETCH ' + url + ' -> ERROR: ' + error);" +
                        "           throw error;" +
                        "       });" +
                        "};" +

                        "const originalOpen = XMLHttpRequest.prototype.open;" +
                        "const originalSend = XMLHttpRequest.prototype.send;" +

                        "XMLHttpRequest.prototype.open = function(method, url) {" +
                        "   this.__method = method;" +
                        "   this.__url = url;" +
                        "   return originalOpen.apply(this, arguments);" +
                        "};" +

                        "XMLHttpRequest.prototype.send = function() {" +
                        "   this.addEventListener('loadend', function() {" +
                        "       window.__networkLogs.push(" +
                        "           'XHR ' + this.__method + ' ' + this.__url + ' -> ' + this.status" +
                        "       );" +
                        "   });" +
                        "   return originalSend.apply(this, arguments);" +
                        "};"
        );
    }

    @SuppressWarnings("unchecked")
    private List<String> getNetworkLogs() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        return (List<String>) js.executeScript(
                "return window.__networkLogs || [];"
        );
    }

    @Step("Выполнить вход: {email}")
    public MainPage loginAs(String email, String password) {

        enableNetworkLogger();

        enterEmail(email);
        enterPassword(password);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        String enteredEmail = (String) js.executeScript(
                "return document.querySelector(\"input[name='name']\").value;"
        );

        String enteredPassword = (String) js.executeScript(
                "return document.querySelector(\"input[name='Пароль']\").value;"
        );

        System.out.println("===== ДАННЫЕ ФОРМЫ =====");
        System.out.println("EMAIL В ФОРМЕ: " + enteredEmail);
        System.out.println("ПАРОЛЬ ЗАПОЛНЕН: "
                + (enteredPassword != null && !enteredPassword.isEmpty()));

        clickLogin();

        try {
            wait.until(driver -> !getNetworkLogs().isEmpty());
        } catch (Exception ignored) {
        }

        System.out.println("===== СЕТЕВЫЕ ЗАПРОСЫ =====");

        for (String log : getNetworkLogs()) {
            System.out.println(log);
        }

        System.out.println("===== ПОСЛЕ ВХОДА =====");
        System.out.println("URL: " + driver.getCurrentUrl());
        System.out.println(
                "BODY: " + driver.findElement(By.tagName("body")).getText()
        );

        return new MainPage(driver);
    }

    @Step("Проверка отображения кнопки 'Войти'")
    public boolean isLoginButtonDisplayed() {
        return isDisplayed(LOGIN_BUTTON);
    }
}