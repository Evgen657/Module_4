package pageobjects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutStepTwoPage {
    private com.codeborne.selenide.SelenideElement finishButton = $("#finish");
    private com.codeborne.selenide.SelenideElement totalLabel = $(".summary_total_label");

    public String getTotal() {
        return totalLabel.shouldBe(visible).getText(); // пример: "Total: $58.29"
    }

    public CheckoutCompletePage clickFinish() {
        finishButton.shouldBe(visible).click();
        return new CheckoutCompletePage();
    }
}
