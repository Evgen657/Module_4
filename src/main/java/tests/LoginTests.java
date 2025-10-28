package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.firefox.FirefoxOptions;
import pageobjects.LoginPage;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.qameta.allure.Allure.step;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTests {

    @BeforeAll
    static void setup() {
        Configuration.browser = "firefox";
        Configuration.browserSize = "1920x1080"; // можно вручную масштабировать окно, "1920x1080"
        Configuration.timeout = 5000;

        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:/Program Files/Mozilla Firefox/firefox.exe");
        Configuration.browserCapabilities = options;

    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }



    @Test
    @Order(1)
    @DisplayName("Успешный вход под standard_user")
    @Story("Авторизация")
    @Description("Проверка успешного входа с валидными данными standard_user")
    @Tags({@Tag("позитивный"), @Tag("авторизация")})
    public void successfulLogin() {
        step("Открыть страницу логина и войти", () ->
                new LoginPage().openPage().login("standard_user", "secret_sauce"));
    }

    @Test
    @Order(2)
    @DisplayName("Вход заблокированного пользователя locked_out_user")
    @Story("Авторизация")
    @Description("Проверка ошибки при входе заблокированного пользователя locked_out_user")
    @Tags({@Tag("негативный"), @Tag("авторизация")})
    public void lockedOutUserLogin() {
        LoginPage loginPage = new LoginPage().openPage();
        step("Попытка входа заблокированным пользователем", () ->
                loginPage.login("locked_out_user", "secret_sauce"));

        step("Проверка сообщения об ошибке", () ->
                loginPage.checkError("Epic sadface: Sorry, this user has been locked out."));
    }
}