package praktikum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import praktikum.config.WebDriverConfig;
import praktikum.pages.MainPage;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Stellar Burgers UI")
@Feature("Конструктор")
@ExtendWith(AllureJunit5.class)
@DisplayName("Тесты раздела 'Конструктор'")
class ConstructorTest {

    private static final String URL = "https://qa-stellarburgers.education-services.ru/";
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
        new MainPage(driver).open(URL)
                .clickSaucesTab()
                .clickBunsTab();

        assertTrue(new MainPage(driver).isBunsSectionDisplayed(),
                "Раздел 'Булки' должен отображаться");
    }

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    void goToSauces() {
        new MainPage(driver).open(URL)
                .clickSaucesTab();

        assertTrue(new MainPage(driver).isSaucesSectionDisplayed(),
                "Раздел 'Соусы' должен отображаться");
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    void goToFillings() {
        new MainPage(driver).open(URL)
                .clickFillingsTab();

        assertTrue(new MainPage(driver).isFillingsSectionDisplayed(),
                "Раздел 'Начинки' должен отображаться");
    }
}