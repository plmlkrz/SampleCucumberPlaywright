package steps;

import com.microsoft.playwright.Page;
import context.ScenarioContext;
import pages.LoginPage;
import pages.InventoryPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginSteps {
    private final Page page;
    private final LoginPage loginPage;
    private final InventoryPage inventoryPage;

    public LoginSteps(ScenarioContext ctx) {
        this.page = ctx.getPage();
        this.loginPage = new LoginPage(page);
        this.inventoryPage = new InventoryPage(page);
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.open();
    }

    @When("I login as {string} with password {string}")
    public void iLoginAs(String username, String password) {
        loginPage.loginAs(username, password);
    }

    @Then("I should see the inventory page")
    public void iShouldSeeTheInventoryPage() {
        assertThat(page).hasURL("https://www.saucedemo.com/inventory.html");
    }

    @Then("I should see the app logo")
    public void iShouldSeeTheAppLogo() {
        assertThat(inventoryPage.appLogo()).isVisible();
    }

    @Then("I should see an error message containing {string}")
    public void iShouldSeeErrorMessageContaining(String expectedText) {
        assertThat(loginPage.errorMessage()).containsText(expectedText);
    }
}
