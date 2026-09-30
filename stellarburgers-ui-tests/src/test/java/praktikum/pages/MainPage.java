package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    private static final By LOGIN_BUTTON = By.xpath("//button[text()='Войти в аккаунт']");
    private static final By PERSONAL_ACCOUNT_BUTTON = By.xpath("//a[@href='/account']");
    private static final By CONSTRUCTOR_BUTTON = By.xpath("//p[text()='Конструктор']");
    private static final By LOGO = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']");
    private static final By BUNS_TAB = By.xpath("//span[text()='Булки']");
    private static final By SAUCES_TAB = By.xpath("//span[text()='Соусы']");
    private static final By FILLINGS_TAB = By.xpath("//span[text()='Начинки']");
    private static final By ORDER_BUTTON = By.xpath("//button[contains(., 'Оформить заказ')]");
    private static final By BUNS_SECTION = By.xpath("//h2[text()='Булки']");
    private static final By SAUCES_SECTION = By.xpath("//h2[text()='Соусы']");
    private static final By FILLINGS_SECTION = By.xpath("//h2[text()='Начинки']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Ожидание загрузки главной страницы")
    public MainPage waitForMainPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(ORDER_BUTTON));
        return this;
    }

    @Step("Открыть главную страницу")
    public MainPage open(String url) {
        driver.get(url);
        return this;
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public LoginPage clickLoginButton() {
        click(LOGIN_BUTTON);
        return new LoginPage(driver);
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public LoginPage clickPersonalAccount() {
        click(PERSONAL_ACCOUNT_BUTTON);
        return new LoginPage(driver);
    }

    @Step("Клик по кнопке 'Конструктор'")
    public MainPage clickConstructor() {
        click(CONSTRUCTOR_BUTTON);
        return this;
    }

    @Step("Клик по логотипу Stellar Burgers")
    public MainPage clickLogo() {
        click(LOGO);
        return this;
    }

    @Step("Клик по табу 'Булки'")
    public MainPage clickBunsTab() {
        click(BUNS_TAB);
        return this;
    }

    @Step("Клик по табу 'Соусы'")
    public MainPage clickSaucesTab() {
        click(SAUCES_TAB);
        return this;
    }

    @Step("Клик по табу 'Начинки'")
    public MainPage clickFillingsTab() {
        click(FILLINGS_TAB);
        return this;
    }

    @Step("Проверка отображения раздела 'Булки'")
    public boolean isBunsSectionDisplayed() {
        return isDisplayed(BUNS_SECTION);
    }

    @Step("Проверка отображения раздела 'Соусы'")
    public boolean isSaucesSectionDisplayed() {
        return isDisplayed(SAUCES_SECTION);
    }

    @Step("Проверка отображения раздела 'Начинки'")
    public boolean isFillingsSectionDisplayed() {
        return isDisplayed(FILLINGS_SECTION);
    }

    @Step("Проверка, что пользователь авторизован (видна кнопка 'Оформить заказ')")
    public boolean isAuthorized() {
        return isDisplayed(ORDER_BUTTON);
    }
}