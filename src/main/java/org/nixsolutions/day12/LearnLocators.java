package org.nixsolutions.day12;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.nio.file.Paths;

public class LearnLocators {
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

        page.navigate("https://letcode.in/test");
        // decline the consent cookie

        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();
        // short-form for anchor tag with "Click" text, page.locator("text=Click").click();
        page.locator("'Click'").click();
        System.out.println(page.url());
        page.locator("button:has-text('Goto Home')").click();
        System.out.println(page.url());
        page.locator("nav :text('product')").click();
        System.out.println(page.url());

        // other mentioned locators: `visible` and `nth`

        page.navigate("https://letcode.in/edit");
        // alternative locators: if the first one is not found, try the second one.
        // works only with css selectors - for xpath, use the union (`|`) char
        page.locator("#fullName, #name").fill("koushik");


        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day12.zip")));

        // It is required to close the context otherwise the video won't be saved
        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day12.zip"`
    }
}
