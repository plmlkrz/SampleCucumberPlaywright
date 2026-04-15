# SampleCucumberPlaywright

A Cucumber BDD test automation project targeting the [Swag Labs demo app](https://www.saucedemo.com/), using **Playwright for Java** as the browser automation library.

## Prerequisites

- JDK 21+
- Maven 3.6+

Playwright browsers are downloaded automatically on the first run. To install them manually:

```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install firefox"
```

## Running Tests

```bash
# Run all tests
mvn test

# Run a single scenario by tag
mvn test -Dcucumber.filter.tags=@login-success

# Run all scenarios in a feature
mvn test -Dcucumber.filter.tags=@inventory

# Run with a visible browser window
mvn test -Dheadless=false

# Run with a different browser (chromium | firefox | webkit)
mvn test -Dbrowser=firefox
```

## Test Reports

After a run, open the HTML report for results and any failure screenshots:

```
target/cucumber-reports/report.html
```

## Test Coverage

| Feature | Tag | Scenarios |
|---|---|---|
| Login | `@login` | Successful login, locked-out user error |
| Inventory / Product Page | `@inventory` | Product sorting (A-Z, Z-A, price high-low, price low-high) |

## Project Structure

```
src/test/
  java/
    context/      # ScenarioContext — PicoContainer-managed Page + BrowserContext per scenario
    hooks/        # Playwright browser lifecycle (suite-level Browser, scenario-level Page)
    pages/        # Page Object Model (BasePage + feature-specific page classes)
    steps/        # Cucumber step definitions
    runner/       # JUnit 5 Suite runner
  resources/
    features/     # Gherkin feature files
```

## Architecture Notes

- **PicoContainer** handles dependency injection — `ScenarioContext` is auto-constructed and injected into all step definitions and hooks, providing a fresh `Page` and `BrowserContext` per scenario.
- **Parallel execution is disabled** by default. Enabling it requires making the static `Browser` instance in `Hooks.java` thread-safe.
- Failure screenshots are captured automatically and embedded in the HTML report.
