package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitCard {
    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldPositiveTest() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.tagName("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иванов Никита");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79886592356");
        form.findElement(By.className("checkbox__box")).click();
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldNegativeTest() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.tagName("form"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Noname");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79886592356");
        form.findElement(By.className("checkbox__box")).click();
        form.findElement(By.className("button")).click();
        WebElement invalidField = driver.findElement(By.className("input_invalid"));
        String errorText = invalidField.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", errorText.trim());
    }
}
