package hooks;

import com.microsoft.playwright.*;
import context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;

public class Hooks {
    private static Playwright playwright;
    private static Browser browser;
    private final ScenarioContext scenarioContext;

    public Hooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @BeforeAll
    public static void setUpSuite() {
        playwright = Playwright.create();
        String browserName = System.getProperty("browser", "chromium");
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
        browser = switch (browserName) {
            case "firefox" -> playwright.firefox().launch(options);
            case "webkit" -> playwright.webkit().launch(options);
            default -> playwright.chromium().launch(options);
        };
    }

    @Before
    public void setUp() {
        BrowserContext context = browser.newContext();
        Page page = context.newPage();
        scenarioContext.setBrowserContext(context);
        scenarioContext.setPage(page);
    }

    @After
    public void tearDown(Scenario scenario) {
        Page page = scenarioContext.getPage();
        if (scenario.isFailed() && page != null) {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        BrowserContext context = scenarioContext.getBrowserContext();
        if (context != null) {
            context.close();
        }
    }

    @AfterAll
    public static void tearDownSuite() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
