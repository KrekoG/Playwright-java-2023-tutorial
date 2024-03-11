package org.nixsolutions.day18;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.ScreenshotCaret;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class LearnWindowHandling {
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

        page.navigate("https://letcode.in/windows");
        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();

        // expecting single popup
        Page popup = page.waitForPopup(() -> {
            page.locator("button:has-text('Open Home Page')").click();
        });
        popup.waitForLoadState();
        System.out.println(popup.title());
        System.out.println(popup.url());
        popup.close();

        // expecting multiple popups
        page.waitForPopup(
                new Page.WaitForPopupOptions().setPredicate(tabs -> tabs.context().pages().size() == 3),
                () -> page.locator("#multi").click()
        );
        System.out.println("list of pages open: ");
        List<Page> pages = page.context().pages();
        pages.forEach(info -> System.out.println(info.url()));
        Page alertPage = pages.get(1);
        Page dropdownPage = pages.get(2);
        System.out.println("alert page url: " + alertPage.url());
        System.out.println("h1: " + alertPage.textContent("h1"));
        System.out.println("dropdown page url: " + dropdownPage.url());
        System.out.println("h1: " + dropdownPage.textContent("h1"));

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day18.zip")));

        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day18.zip"`
    }
}
