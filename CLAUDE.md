# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build and Test Commands

```bash
mvn test                                          # Run all tests
mvn test -Dcucumber.filter.tags=@login-success    # Run a single scenario by tag
mvn test -Dcucumber.filter.tags=@inventory        # Run all scenarios in a feature
mvn test -Dheadless=false                         # Run with visible browser
mvn test -Dbrowser=firefox                        # Run with Firefox
mvn test -Dbrowser=webkit                         # Run with WebKit
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

**Java Page Object Model** — Page objects in `src/test/java/pages/` extend `BasePage` and expose Playwright `Locator` fields and action methods.

**PicoContainer Dependency Injection** — `ScenarioContext` is a POJO holding the Playwright `Page` and `BrowserContext` for the current scenario. PicoContainer automatically creates one instance per scenario and injects it into all step definition and hook constructors — no manual wiring needed.

**Playwright Lifecycle** — Managed in `hooks/Hooks.java`:
- `@BeforeAll`: Creates a single `Playwright` instance and `Browser` for the entire suite (static fields)
- `@Before`: Creates a fresh `BrowserContext` and `Page` per scenario (full isolation)
- `@After`: On failure, captures a full-page screenshot embedded into the Cucumber HTML report; then closes `BrowserContext`
- `@AfterAll`: Closes `Browser` and `Playwright`

> **Note:** Parallel execution is disabled (`cucumber.execution.parallel.enabled=false` in `junit-platform.properties`). Enabling it requires making the static `Browser` in `Hooks.java` thread-safe (e.g., a `ThreadLocal` browser per worker).

**Configuration via System Properties:**
- `browser` (default: `chromium`) — `chromium`, `firefox`, or `webkit`
- `headless` (default: `true`) — set to `false` for visible browser
- `baseUrl` (default: `https://www.saucedemo.com/`) — base URL for the app under test

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

- HTML (with embedded failure screenshots): `target/cucumber-reports/report.html`
- JSON: `target/cucumber.json`
