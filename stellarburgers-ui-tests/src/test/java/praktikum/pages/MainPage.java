package praktikum.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

    private static final By LOGIN_BUTTON =
            By.xpath("//button[normalize-space()='Войти в аккаунт']");

    private static final By PERSONAL_ACCOUNT_BUTTON =
            By.xpath("//p[normalize-space()='Личный Кабинет']/ancestor::a[1]");

    private static final By LOGO =
            By.cssSelector("div[class*='AppHeader_header__logo']");

    private static final By BUNS_TAB =
            By.xpath("//div[contains(@class,'tab_tab')][.//span[normalize-space()='Булки']]");

    private static final By SAUCES_TAB =
            By.xpath("//div[contains(@class,'tab_tab')][.//span[normalize-space()='Соусы']]");

    private static final By FILLINGS_TAB =
            By.xpath("//div[contains(@class,'tab_tab')][.//span[normalize-space()='Начинки']]");

    private static final By ACTIVE_TAB =
            By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public MainPage open(String url) {
        driver.get(url);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON)
        );

        return this;
    }

    @Step("Ожидание загрузки главной страницы")
    public MainPage waitForMainPage() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(LOGO)
        );

        return this;
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public LoginPage clickLoginButton() {
        click(LOGIN_BUTTON);
        return new LoginPage(driver);
    }

    @Step("Клик по 'Личный Кабинет' (неавторизованный)")
    public LoginPage clickPersonalAccountAsGuest() {
        click(PERSONAL_ACCOUNT_BUTTON);
        return new LoginPage(driver);
    }

    @Step("Клик по 'Личный Кабинет' (авторизованный)")
    public ProfilePage clickPersonalAccountAsUser() {
        click(PERSONAL_ACCOUNT_BUTTON);

        wait.until(ExpectedConditions.urlContains("/account"));

        return new ProfilePage(driver);
    }

    @Step("Клик по табу 'Булки'")
    public MainPage clickBunsTab() {
        jsClick(BUNS_TAB);
        return this;
    }

    @Step("Клик по табу 'Соусы'")
    public MainPage clickSaucesTab() {
        jsClick(SAUCES_TAB);
        return this;
    }

    @Step("Клик по табу 'Начинки'")
    public MainPage clickFillingsTab() {
        jsClick(FILLINGS_TAB);
        return this;
    }

    @Step("Проверка активности таба: {tabName}")
    public boolean isTabActive(String tabName) {
        return getText(ACTIVE_TAB).equals(tabName);
    }

    public boolean isBunsTabActive() {
        return isTabActive("Булки");
    }

    public boolean isSaucesTabActive() {
        return isTabActive("Соусы");
    }

    public boolean isFillingsTabActive() {
        return isTabActive("Начинки");
    }

    @Step("Проверка, что пользователь авторизован")
    public boolean isAuthorized() {
        return !driver.getCurrentUrl().contains("/login")
                && isDisplayed(PERSONAL_ACCOUNT_BUTTON);
    }
}