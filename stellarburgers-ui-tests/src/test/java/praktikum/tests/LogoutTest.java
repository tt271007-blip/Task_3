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

import static org.junit.jupiter.api.Assertions.*;

@Epic("Stellar Burgers UI")
@Feature("Выход из аккаунта")
@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты выхода из аккаунта")
class LogoutTest {

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
    @DisplayName("Выход по кнопке 'Выйти' в личном кабинете")
    void logoutFromProfile() {
        new MainPage(driver).open(URL)
                .clickLoginButton()
                .loginAs(user.email, user.password);

        new MainPage(driver).clickPersonalAccount();

        LoginPage loginPage = new ProfilePage(driver).clickLogout();

        assertTrue(loginPage.isDisplayed(
                        org.openqa.selenium.By.xpath("//button[text()='Войти']")),
                "После выхода должна открыться страница входа");
    }
}