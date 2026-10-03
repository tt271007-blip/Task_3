package praktikum.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import praktikum.api.UserApiClient;
import praktikum.config.WebDriverConfig;
import praktikum.model.User;
import praktikum.pages.LoginPage;
import praktikum.pages.MainPage;
import praktikum.pages.RegisterPage;
import praktikum.util.UserGenerator;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Stellar Burgers UI")
@Feature("Вход")
@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты входа")
class LoginTest {

    private static final String URL =
            "https://qa-stellarburgers.education-services.ru/";

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

        Response response = apiClient.register(user);

        System.out.println("===== РЕГИСТРАЦИЯ =====");
        System.out.println("EMAIL: " + user.email);
        System.out.println("PASSWORD: " + user.password);
        System.out.println("STATUS: " + response.statusCode());
        System.out.println("BODY: " + response.asPrettyString());

        assertEquals(200, response.statusCode());

        accessToken = response.jsonPath().getString("accessToken");

        assertNotNull(accessToken);

        Response loginResponse = apiClient.login(user);

        System.out.println("===== API ЛОГИН =====");
        System.out.println("STATUS: " + loginResponse.statusCode());
        System.out.println("BODY: " + loginResponse.asPrettyString());

        assertEquals(
                200,
                loginResponse.statusCode(),
                "API логин не работает"
        );
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

        MainPage mainPage = new MainPage(driver)
                .open(URL)
                .clickLoginButton()
                .loginAs(user.email, user.password)
                .waitForMainPage();

        System.out.println("===== ПОСЛЕ ВХОДА =====");
        System.out.println("URL: " + driver.getCurrentUrl());

        assertTrue(
                mainPage.isAuthorized(),
                "Пользователь должен быть авторизован"
        );
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет'")
    void loginViaPersonalAccountButton() {

        MainPage mainPage = new MainPage(driver)
                .open(URL)
                .clickPersonalAccountAsGuest()
                .loginAs(user.email, user.password)
                .waitForMainPage();

        System.out.println("URL: " + driver.getCurrentUrl());

        assertTrue(
                mainPage.isAuthorized(),
                "Пользователь должен быть авторизован"
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    void loginViaRegisterFormLink() {

        MainPage mainPage = new MainPage(driver).open(URL);

        LoginPage loginPage = mainPage.clickLoginButton();

        RegisterPage registerPage = loginPage.clickRegisterLink();

        loginPage = registerPage.clickLoginLink();

        mainPage = loginPage.loginAs(user.email, user.password);

        mainPage.waitForMainPage();

        assertTrue(
                mainPage.isAuthorized(),
                "Пользователь должен быть авторизован"
        );
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    void loginViaForgotPasswordFormLink() {

        MainPage mainPage = new MainPage(driver).open(URL);

        LoginPage loginPage = mainPage.clickLoginButton();

        loginPage = loginPage.clickForgotPassword();

        loginPage = loginPage.clickLoginLinkFromForgotPassword();

        mainPage = loginPage.loginAs(user.email, user.password);

        mainPage.waitForMainPage();

        assertTrue(
                mainPage.isAuthorized(),
                "Пользователь должен быть авторизован"
        );
    }
}