package pages;

import com.microsoft.playwright.Page;

public abstract class BasePage {
    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected void navigateTo(String url) {
        page.navigate(url);
    }
}
