# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Test Commands

```bash
mvn test                                          # Run all tests
mvn test -Dcucumber.filter.tags=@login-success    # Run a single scenario by tag
mvn test -Dcucumber.filter.tags=@inventory        # Run all scenarios in a feature
mvn test -Dheadless=false                         # Run with visible browser
mvn test -Dbrowser=firefox                        # Run with Firefox (must install: see below)
```

## Playwright Browser Installation

Browsers are installed automatically on first run, or manually:

```bash
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install firefox"
```

## Architecture Overview

This is a Cucumber BDD test automation project for the [SauceLabs demo app](https://www.saucedemo.com/), using **Playwright for Java** as the browser automation library.

### Key Design Patterns

**Java Page Object Model** — Page objects live in `src/test/java/pages/` as Java classes. Each extends `BasePage` and exposes Playwright `Locator` instances for UI elements and action methods for common workflows.

**PicoContainer Dependency Injection** — `ScenarioContext` is a simple POJO that holds the Playwright `Page` and `BrowserContext` for the current scenario. PicoContainer automatically creates one instance per scenario and injects it into all step definition and hook constructors.

**Playwright Lifecycle** — Managed in `hooks/Hooks.java`:
- `@BeforeAll`: Creates `Playwright` instance and `Browser` (once per suite)
- `@Before`: Creates a new `BrowserContext` and `Page` per scenario (full isolation)
- `@After`: Takes screenshot on failure, then closes `BrowserContext`
- `@AfterAll`: Closes `Browser` and `Playwright`

**Configuration via System Properties:**
- `browser` (default: `chromium`) — chromium, firefox, or webkit
- `headless` (default: `true`) — set to `false` for visible browser
- `baseUrl` (default: `https://www.saucedemo.com/`) — base URL for the app under test

### Project Layout

```
src/test/java/
  context/ScenarioContext.java       # PicoContainer-managed shared state (Page, BrowserContext)
  hooks/Hooks.java                   # Playwright lifecycle (@BeforeAll/@AfterAll + @Before/@After)
  pages/BasePage.java                # Abstract base with navigateTo()
  pages/LoginPage.java               # Login page locators and actions
  pages/InventoryPage.java           # Product/inventory page locators and actions
  steps/LoginSteps.java              # Login feature step definitions
  steps/InventorySteps.java          # Inventory feature step definitions
  runner/TestRunner.java             # JUnit 5 @Suite runner (cucumber-junit-platform-engine)
src/test/resources/
  features/01_login.feature          # Login scenarios (success + locked-out)
  features/02_inventory.feature      # Product sorting (Scenario Outline with Examples)
  junit-platform.properties          # Cucumber config
```

### Runner

`TestRunner.java` uses the JUnit 5 Platform Suite engine (`@Suite` + `@IncludeEngines("cucumber")`). Tag filtering is passed as a system property: `-Dcucumber.filter.tags=@tagName`.

### Scenario Tagging Convention

Feature-level tags: `@login`, `@inventory`
Scenario-level tags: `@login-success`, `@login-locked`, `@sort`, `@product-name`

### Java & Dependencies

- **JDK**: 26 (installed); compiler targets Java 21 (LTS) via `<release>21</release>`
- **Playwright**: 1.49.0
- **Cucumber**: 7.20.1
- **JUnit Platform**: 1.11.4 (`junit-platform-suite`)
- **PicoContainer**: via `cucumber-picocontainer` 7.20.1

### Test Reports

- HTML: `target/cucumber-reports/report.html`
- JSON: `target/cucumber.json`
