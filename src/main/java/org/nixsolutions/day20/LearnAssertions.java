package org.nixsolutions.day20;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.assertions.PageAssertions;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LearnAssertions {
    public static void main(String[] args) {
        boolean runInHeadlessMode = true;
        Playwright playwright = Playwright.create();
        BrowserType chromium = playwright.chromium();
        BrowserType.LaunchOptions headless = new BrowserType.LaunchOptions()
                .setHeadless(runInHeadlessMode);
        Browser browser = chromium.launch(headless);
        BrowserContext context = browser.newContext();
        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
        Page page = context.newPage();

        page.navigate("https://letcode.in/edit");
        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();

        Locator header = page.locator("h1");
        assertThat(header).hasText("Input");
        assertThat(page).hasTitle(
                "Interact with Input fields",
                new PageAssertions.HasTitleOptions().setTimeout(0)
        );
        assertThat(page).hasURL("https://letcode.in/edit");
        assertThat(page.locator("#join")).hasAttribute("value", "I am good");

        page.navigate("https://letcode.in/radio");
        assertThat(page.locator("#notfoo")).isChecked();

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day20.zip")));

        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day20.zip"`
    }
}
