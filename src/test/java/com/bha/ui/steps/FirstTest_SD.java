package com.bha.ui.steps;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class FirstTest_SD {

    Dimension screenSize;
    Map<String, String> envProperties;
    Playwright.CreateOptions options;
    Playwright playwright;
    Page page;
    Browser browser;

    @Given("User set the screen dimensions")
    public void userSetTheScreenDimensions() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    @And("user sets below environment properties")
    public void userSetsBelowEnvironmentProperties(List<Map<String, String>> envProp) {
        envProperties = new HashMap<>();
        options = new Playwright.CreateOptions();
        for (Map<String, String> prop : envProp) {
            String property = prop.get("Property");
            String value = prop.get("Value");
            value = prop.get("Value").contains("ms-") ? System.getProperty("user.dir") + File.separator + value + File.separator : value;
            envProperties.put(property, value);
        }

    }


    @Then("user launches website as {string}")
    public void userLaunchesWebsiteAs(String url) {

       playwright = Playwright.create(options.setEnv(envProperties));
       browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setChannel("chrome")
                        .setHeadless(false)
                        .setSlowMo(50)
        );

        BrowserContext context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize((int) screenSize.getWidth(), (int) screenSize.getHeight())
        );

        page = context.newPage();
        page.navigate(url);
        assertThat(page).hasTitle(Pattern.compile("Playwright"));

    }

    @Then("user checks the title of page as {string}")
    public void userChecksTheTitleOfPageAs(String title) {

        // Expect a title "to contain" a substring.
        assertThat(page).hasTitle(Pattern.compile(title));

        // create a locator
        Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));

        // Expect an attribute "to be strictly equal" to the value.
        assertThat(getStarted).hasAttribute("href", "/docs/intro");

        // Click the get started link.
        getStarted.click();

        // Expects page to have a heading with the name of Installation.
        assertThat(page.getByRole(AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Installation"))).isVisible();
    }


    @Then("user close browser")
    public void userCloseBrowser() {

        browser.close();
    }
}
