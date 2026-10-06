package praktikum.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import praktikum.config.WebDriverConfig;
import praktikum.pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Stellar Burgers UI")
@Feature("Конструктор")
@DisplayName("Тесты раздела 'Конструктор'")
class ConstructorTest {

    private static final String URL =
            "https://qa-stellarburgers.education-services.ru/";

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        String browser = System.getProperty("browser", "chrome");
        driver = WebDriverConfig.createDriver(browser);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    void goToBuns() {
        MainPage mainPage = new MainPage(driver)
                .open(URL)
                .clickBunsTab();

        assertTrue(mainPage.isBunsTabActive(),
                "Должен быть выбран раздел 'Булки'");
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    void goToSauces() {
        MainPage mainPage = new MainPage(driver)
                .open(URL)
                .clickSaucesTab();

        assertTrue(mainPage.isSaucesTabActive(),
                "Должен быть выбран раздел 'Соусы'");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    void goToFillings() {
        MainPage mainPage = new MainPage(driver)
                .open(URL)
                .clickFillingsTab();

        assertTrue(mainPage.isFillingsTabActive(),
                "Должен быть выбран раздел 'Начинки'");
    }
}