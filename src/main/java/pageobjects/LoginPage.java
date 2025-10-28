package pageobjects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class LoginPage {

    private com.codeborne.selenide.SelenideElement usernameInput = $("#user-name");
    private com.codeborne.selenide.SelenideElement passwordInput = $("#password");
    private com.codeborne.selenide.SelenideElement loginButton = $("#login-button");
    private com.codeborne.selenide.SelenideElement errorMessage = $("h3[data-test='error']");

    public LoginPage openPage() {
        open("https://www.saucedemo.com/");
        return this;
    }

    public ProductsPage login(String username, String password) {
        usernameInput.shouldBe(visible).setValue(username);
        passwordInput.shouldBe(visible).setValue(password);
        loginButton.click();
        return new ProductsPage();
    }

    public void checkError(String expectedText) {
        errorMessage.shouldBe(visible).shouldHave(text(expectedText));
    }
}
