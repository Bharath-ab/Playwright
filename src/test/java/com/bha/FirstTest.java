package com.bha;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FirstTest {
    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Map<String, String> env = new HashMap<>();
        env.put("PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD", "1");
        String binariesDir = System.getProperty("user.dir") + "/ms-playwright";
        env.put("PLAYWRIGHT_BROWSERS_PATH", binariesDir);
        Playwright.CreateOptions options = new Playwright.CreateOptions();

        try (Playwright playwright = Playwright.create(options.setEnv(env))) {

            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setChannel("chrome")
                            .setHeadless(false)
                            .setSlowMo(50)
            );

            BrowserContext context = browser.newContext(
                    new Browser.NewContextOptions()
                            .setViewportSize((int) screenSize.getWidth(), (int) screenSize.getHeight())
            );

            Page page = context.newPage();
            page.navigate("http://playwright.dev");

            // Expect a title "to contain" a substring.
            assertThat(page).hasTitle(Pattern.compile("Playwright"));

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

    }
}
