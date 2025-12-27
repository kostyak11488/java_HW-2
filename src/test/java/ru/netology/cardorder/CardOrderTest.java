package ru.netology.cardorder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardOrderTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void shouldSuccessfullySubmitFormWithValidData() {
        driver.findElement(By.cssSelector("[data-test-id=name] input"))
                .sendKeys("Иван Иванов");

        driver.findElement(By.cssSelector("[data-test-id=phone] input"))
                .sendKeys("+79012345678");

        driver.findElement(By.cssSelector("[data-test-id=agreement]"))
                .click();

        driver.findElement(By.tagName("button"))
                .click();

        WebElement successMessage =
                driver.findElement(By.cssSelector("[data-test-id=order-success]"));

        assertTrue(successMessage.isDisplayed());
        assertEquals(
                "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                successMessage.getText().trim()
        );
    }
}


