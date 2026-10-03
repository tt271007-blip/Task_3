 package praktikum.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import praktikum.api.UserApiClient;
import praktikum.config.WebDriverConfig;
import praktikum.model.User;
import praktikum.pages.MainPage;
import praktikum.pages.ProfilePage;
import praktikum.util.UserGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers UI")
@Feature("Переход в личный кабинет")
@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты перехода в личный кабинет")
class ProfileTransitionTest {

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

        accessToken = apiClient.register(user)
                .jsonPath()
                .getString("accessToken");
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
    @DisplayName("Переход в личный кабинет по клику на 'Личный кабинет'")
    void goToProfile() {
        MainPage mainPage = new MainPage(driver)
                .open(URL)
                .clickLoginButton()
                .loginAs(user.email, user.password)
                .waitForMainPage();

        assertTrue(mainPage.isAuthorized(),
                "Пользователь должен быть авторизован");

        ProfilePage profilePage = mainPage.clickPersonalAccountAsUser();

        assertTrue(profilePage.isLogoutButtonDisplayed(),
                "В личном кабинете должна быть кнопка 'Выход'");
    }
}