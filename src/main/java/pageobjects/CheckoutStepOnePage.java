package pageobjects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class CheckoutStepOnePage {
    private com.codeborne.selenide.SelenideElement firstNameInput = $("#first-name");
    private com.codeborne.selenide.SelenideElement lastNameInput = $("#last-name");
    private com.codeborne.selenide.SelenideElement postalCodeInput = $("#postal-code");
    private com.codeborne.selenide.SelenideElement continueButton = $("#continue");

    public CheckoutStepOnePage fillForm(String firstName, String lastName, String postalCode) {
        firstNameInput.shouldBe(visible).setValue(firstName);
        lastNameInput.shouldBe(visible).setValue(lastName);
        postalCodeInput.shouldBe(visible).setValue(postalCode);
        return this;
    }

    public CheckoutStepTwoPage clickContinue() {
        continueButton.click();
        return new CheckoutStepTwoPage();
    }
}
