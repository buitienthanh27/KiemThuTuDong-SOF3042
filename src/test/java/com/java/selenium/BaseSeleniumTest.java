package com.java.selenium;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseSeleniumTest {


    protected static WebDriver driver;
    // App đang chạy ở http://localhost:8080/
    protected static final String BASE_URL = "http://localhost:8080/";

    @BeforeAll
    static void setUpClass() {
        WebDriverManager.chromedriver().setup();

        // 2. Cấu hình Chrome Options cho CI/CD
        ChromeOptions options = new ChromeOptions();

        // --- CÁC CỜ BẮT BUỘC CHO CI/CD (LINUX) ---
        options.addArguments("--headless"); // Chạy không giao diện (Quan trọng nhất)
        options.addArguments("--disable-gpu"); // Tắt GPU (tránh lỗi trên server linux)
        options.addArguments("--window-size=1920,1080"); // Giả lập màn hình to để element không bị ẩn
        options.addArguments("--no-sandbox"); // Bắt buộc cho môi trường Docker/Linux
        options.addArguments("--disable-dev-shm-usage"); // Khắc phục lỗi bộ nhớ chia sẻ trên Linux

        // 3. Khởi tạo driver với options vừa tạo
        driver = new ChromeDriver(options);

        // Dù headless cũng nên maximize ảo để layout chuẩn
        driver.manage().window().maximize();
    }

    @AfterAll
    static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void openHomePage() {
        driver.get(BASE_URL);      // không cộng thêm gì nữa
    }
}