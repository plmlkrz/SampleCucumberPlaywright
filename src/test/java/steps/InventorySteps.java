package steps;

import context.ScenarioContext;
import pages.LoginPage;
import pages.InventoryPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class InventorySteps {
    private final InventoryPage inventoryPage;
    private final ScenarioContext ctx;

    public InventorySteps(ScenarioContext ctx) {
        this.ctx = ctx;
        this.inventoryPage = new InventoryPage(ctx.getPage());
    }

    @Given("I am logged in as a standard user")
    public void iAmLoggedInAsStandardUser() {
        LoginPage loginPage = new LoginPage(ctx.getPage());
        loginPage.open();
        loginPage.loginAs("standard_user", "secret_sauce");
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String sortOption) {
        inventoryPage.sortBy(sortOption);
    }

    @Then("the first product name should be {string}")
    public void theFirstProductNameShouldBe(String expectedName) {
        assertThat(inventoryPage.firstItemName()).hasText(expectedName);
    }
}
