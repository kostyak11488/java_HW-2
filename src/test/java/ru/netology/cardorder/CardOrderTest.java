package ru.netology.cardorder;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardOrderTest {

    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Успешная отправка формы с валидными данными")
    void shouldSubmitFormSuccessfully() {
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        assertTrue(
                driver.getPageSource().contains("Ваша заявка успешно отправлена"),
                "Должно появиться сообщение об успешной отправке"
        );
    }

    @Test
    @DisplayName("Ошибка при вводе телефона без плюса")
    void shouldShowErrorForInvalidPhone() {
        driver.findElement(By.cssSelector("input[name='name']"))
                .sendKeys("Иван Иванов");

        driver.findElement(By.cssSelector("input[name='phone']"))
                .sendKeys("89991234567");

        driver.findElement(By.cssSelector(".checkbox__box")).click();
        driver.findElement(By.cssSelector("button")).click();

        String actualText = driver.findElement(
                By.cssSelector("[data-test-id='phone'] .input__sub")
        ).getText();

        assertEquals(
                "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                actualText
        );
    }



    @Test
    @DisplayName("Ошибка при неотмеченном чекбоксе")
    void shouldShowErrorIfCheckboxNotChecked() {
        driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("input[name='phone']")).sendKeys("+79991234567");
        driver.findElement(By.cssSelector("button")).click();

        assertTrue(
                driver.getPageSource().contains("Я соглашаюсь"),
                "Должно быть сообщение о необходимости согласия"
        );
    }
}
