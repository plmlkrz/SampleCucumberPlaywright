package context;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;

public class ScenarioContext {
    private Page page;
    private BrowserContext browserContext;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public BrowserContext getBrowserContext() {
        return browserContext;
    }

    public void setBrowserContext(BrowserContext browserContext) {
        this.browserContext = browserContext;
    }
}
