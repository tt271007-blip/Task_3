package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import praktikum.api.UserApiClient;
import praktikum.config.WebDriverConfig;
import praktikum.model.User;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.ProfilePage;
import praktikum.util.UserGenerator;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Stellar Burgers UI")
@Feature("Вход")
@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты входа")
class LoginTest {

    private static final String URL = "https://qa-stellarburgers.education-services.ru/";
    private WebDriver driver;
    private UserApiClient apiClient;
    private User user;
    private String accessToken;

    @BeforeEach
    void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverConfig.createDriver(browser);
        apiClient = new UserApiClient();
        user = UserGenerator.randomUser();

        var regResponse = apiClient.register(user);
        accessToken = regResponse.jsonPath().getString("accessToken");
    }

    @AfterEach
    void tearDown() {
        if (accessToken != null) {
            apiClient.deleteUser(accessToken);
        }
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной")
    void loginViaMainPageButton() {
        new MainPage(driver).open(URL)
                .clickLoginButton()
                .loginAs(user.email, user.password)
                .waitForMainPage();

        assertTrue(new MainPage(driver).isAuthorized(),
                "После входа должна быть доступна кнопка 'Оформить заказ'");
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    void loginViaPersonalAccountButton() {
        new MainPage(driver).open(URL)
                .clickPersonalAccount()
                .loginAs(user.email, user.password)
                .waitForMainPage();

        assertTrue(new MainPage(driver).isAuthorized(),
                "После входа должна быть доступна кнопка 'Оформить заказ'");
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    void loginViaRegisterFormLink() {
        new MainPage(driver).open(URL)
                .clickLoginButton()
                .clickRegisterLink()
                .clickLoginLink()
                .loginAs(user.email, user.password);

        assertTrue(new MainPage(driver).isAuthorized(),
                "После входа должна быть доступна кнопка 'Оформить заказ'");
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    void loginViaForgotPasswordFormLink() {
        new MainPage(driver).open(URL)
                .clickLoginButton()
                .clickForgotPassword()
                .clickLoginLinkFromForgotPassword()
                .loginAs(user.email, user.password);

        assertTrue(new MainPage(driver).isAuthorized(),
                "После входа должна быть доступна кнопка 'Оформить заказ'");
    }
}