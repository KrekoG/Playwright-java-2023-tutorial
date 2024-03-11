package org.nixsolutions.day19;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.nio.file.Paths;
import java.util.List;

public class LearnElements {
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

        page.navigate("https://letcode.in/elements");
        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();

        page.getByPlaceholder("Enter your").fill("ortonikc");
        page.locator("#search").click();
        Locator links = page.locator("ol li a");
        links.first().waitFor(
                new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED)
                        .setTimeout(20000)
        );
        int count = links.count();
        System.out.println("Count: " + count);
        for (int i = 0; i < count; i++) {
            System.out.println(links.nth(i).textContent());
        }

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day19.zip")));

        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day19.zip"`
    }
}
