package org.nixsolutions.page15;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.nio.file.Paths;

public class LearnDownload {
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

        page.navigate("https://letcode.in/file");
        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();

        Download download = page.waitForDownload(() -> page.locator("'Download Excel'").click());
        System.out.println(download.path());
        System.out.println(download.url());
        System.out.println(download.failure());
        System.out.println(download.suggestedFilename());
        download.saveAs(Paths.get("./target/downloads/" + download.suggestedFilename()));

        // Assignment:
        download = page.waitForDownload(() -> page.locator("'Download Pdf'").click());
        download.saveAs(Paths.get("./target/downloads/" + download.suggestedFilename()));

        download = page.waitForDownload(() -> page.locator("'Download Text'").click());
        download.saveAs(Paths.get("./target/downloads/" + download.suggestedFilename()));
        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day14.zip")));

        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day13.zip"`
    }
}
