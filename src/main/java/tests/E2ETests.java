package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.firefox.FirefoxOptions;
import pageobjects.*;

import java.util.concurrent.atomic.AtomicReference;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.qameta.allure.Allure.step;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class E2ETests {

    @BeforeAll
    static void setup() {
        Configuration.browser = "firefox";
        Configuration.browserSize = "1920x1080";
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
    @DisplayName("Покупка для standard_user")
    @Story("Сценарий End-to-End")
    @Description("Тест полного цикла покупки для обычного пользователя.")
    @Tags({@Tag("позитив"), @Tag("e2e")})
    public void e2eTestStandardUser() {
        e2eTestFlow("standard_user");
    }

    @Test
    @Order(2)
    @DisplayName("Покупка для performance_glitch_user")
    @Story("Сценарий End-to-End")
    @Description("Тест полного цикла покупки для пользователя с задержками.")
    @Tags({@Tag("позитив"), @Tag("e2e")})
    public void e2eTestPerformanceGlitchUser() {
        e2eTestFlow("performance_glitch_user");
    }

    @Step("Выполнение сценария покупки для пользователя {username}")
    private void e2eTestFlow(String username) {
        LoginPage loginPage = new LoginPage();
        AtomicReference<ProductsPage> productsPage = new AtomicReference<>();

        step("Логин под пользователем " + username, () -> {
            productsPage.set(loginPage.openPage().login(username, "secret_sauce"));
        });

        step("Добавить товары в корзину", () -> {
            productsPage.get().addProductToCart("Sauce Labs Backpack");
            productsPage.get().addProductToCart("Sauce Labs Bolt T-Shirt");
            productsPage.get().addProductToCart("Sauce Labs Onesie");

            productsPage.get().waitCartBadgeCount(3);

            int countInBadge = productsPage.get().getCartItemsCount();
            assertEquals(3, countInBadge, "Ожидаемое количество товаров в корзине: 3");
        });

        CartPage cartPage = productsPage.get().goToCart();

        cartPage.waitCartItemsCount(3);
        assertEquals(3, cartPage.getCartItemsCount(), "Ожидаемое количество товаров в корзине на странице корзины: 3");

        CheckoutStepOnePage checkoutStepOne = cartPage.clickCheckout();

        step("Заполнение формы Checkout", () -> {
            checkoutStepOne.fillForm("Ivan", "Ivanov", "12345");
        });

        CheckoutStepTwoPage checkoutStepTwo = checkoutStepOne.clickContinue();

        String totalText = checkoutStepTwo.getTotal();
        assertTrue(totalText.contains("$58.29"), "Ожидаемая сумма заказа: $58.29");

        CheckoutCompletePage checkoutComplete = checkoutStepTwo.clickFinish();
        checkoutComplete.checkCompletionMessage("THANK YOU FOR YOUR ORDER");
    }
}
