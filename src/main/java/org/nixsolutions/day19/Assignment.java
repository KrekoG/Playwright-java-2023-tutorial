package org.nixsolutions.day19;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;

public class Assignment {
    public static void main(String[] args) {
        boolean runInHeadlessMode = false;
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

        page.navigate("https://google.com");
        page.locator("button:has-text('Az összes elutasítása')").click();
        Locator searchBar = page.locator("xpath=.//textarea[@name='q']");
        searchBar.fill("Krekog");
        page.keyboard().press("Enter");
        page.locator("#result-stats").waitFor();
        Locator elements = page.locator("text=Krekog");
        System.out.println("Amount of hits: " + elements.count());

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day19-assignment.zip")));

        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day19-assignment.zip"`
    }
}

