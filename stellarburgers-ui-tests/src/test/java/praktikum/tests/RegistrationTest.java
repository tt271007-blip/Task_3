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
import praktikum.pages.RegisterPage;
import praktikum.util.UserGenerator;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Stellar Burgers UI")
@Feature("Регистрация")
@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты регистрации")
class RegistrationTest {

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
    @DisplayName("Успешная регистрация")
    @Description("Заполняем форму регистрации валидными данными и проверяем переход на страницу логина")
    void successfulRegistration() {
        new MainPage(driver).open(URL)
                .clickLoginButton()
                .clickRegisterLink()
                .registerAs(user.name, user.email, user.password);

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isDisplayed(
                        org.openqa.selenium.By.xpath("//button[text()='Войти']")),
                "После регистрации должна открыться страница входа");
    }

    @Test
    @DisplayName("Ошибка при коротком пароле (меньше 6 символов)")
    @Description("Вводим пароль из 5 символов и проверяем появление ошибки")
    void registrationWithShortPassword() {
        User shortPassUser = UserGenerator.userWithShortPassword();

        RegisterPage registerPage = new MainPage(driver).open(URL)
                .clickLoginButton()
                .clickRegisterLink()
                .enterName(shortPassUser.name)
                .enterEmail(shortPassUser.email)
                .enterPassword(shortPassUser.password)
                .clickRegister().clickRegisterLink();

        assertTrue(registerPage.isPasswordErrorDisplayed(),
                "Должна отображаться ошибка о некорректном пароле");
        assertEquals("Некорректный пароль", registerPage.getPasswordError());
    }
}