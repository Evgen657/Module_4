package pageobjects;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.*;

public class CartPage {
    private ElementsCollection cartItems = $$(".cart_item");
    private com.codeborne.selenide.SelenideElement checkoutButton = $("#checkout");

    public int getCartItemsCount() {
        return cartItems.size();
    }

    public CartPage waitCartItemsCount(int expectedCount) {
        cartItems.shouldHave(CollectionCondition.size(expectedCount));
        return this;
    }

    public CheckoutStepOnePage clickCheckout() {
        checkoutButton.click();
        return new CheckoutStepOnePage();
    }
}
