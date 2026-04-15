package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage extends BasePage {
    private static final String BASE_URL = System.getProperty("baseUrl", "https://www.saucedemo.com/");

    public LoginPage(Page page) {
        super(page);
    }

    public void open() {
        navigateTo(BASE_URL);
    }

    public Locator usernameField() {
        return page.getByPlaceholder("Username");
    }

    public Locator passwordField() {
        return page.getByPlaceholder("Password");
    }

    public Locator loginButton() {
        return page.locator("[data-test='login-button']");
    }

    public Locator errorMessage() {
        return page.locator("[data-test='error']");
    }

    public void loginAs(String username, String password) {
        usernameField().fill(username);
        passwordField().fill(password);
        loginButton().click();
    }
}
