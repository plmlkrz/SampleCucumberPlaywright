package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class InventoryPage extends BasePage {

    public InventoryPage(Page page) {
        super(page);
    }

    public Locator appLogo() {
        return page.locator(".app_logo");
    }

    public Locator sortDropdown() {
        return page.locator("[data-test='product-sort-container']");
    }

    public Locator firstItemName() {
        return page.locator(".inventory_item_name").first();
    }

    public void sortBy(String value) {
        sortDropdown().selectOption(value);
    }
}
