package pageobjects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutCompletePage {
    private com.codeborne.selenide.SelenideElement completeHeader = $(".complete-header");

    public void checkCompletionMessage(String message) {
        completeHeader.shouldBe(visible).shouldHave(text(message));
    }
}
