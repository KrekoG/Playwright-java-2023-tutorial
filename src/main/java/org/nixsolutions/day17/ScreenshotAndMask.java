package org.nixsolutions.day17;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.ScreenshotCaret;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ScreenshotAndMask {
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

        // viewport screenshot
        page.navigate("https://github.com/ortoniKC/playwright-java-2023");
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("./target/screenshots/viewport-img.png"))
        );

        // full page screenshot
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("./target/screenshots/full-page-img.png"))
                .setFullPage(true)
        );

        // particular locator screenshot
        Locator search = page.getByPlaceholder("Search");
        search.screenshot(new Locator.ScreenshotOptions()
                .setPath(Paths.get("./target/screenshots/locator-img.png"))
                .setCaret(ScreenshotCaret.INITIAL)
        );

        // masking
        page.screenshot(new Page.ScreenshotOptions()
                .setMask(Arrays.asList(new Locator[]{
                        page.locator("#repository-container-header").locator(".author"),
                        // select the second sibling of the located element:
                        page.locator("#repository-container-header").locator(".author +*+*"),
                }))
                .setPath(Paths.get("./target/screenshots/full-page-img-with-mask.png"))
                .setFullPage(true)
        );

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day17.zip")));

        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day17.zip"`
    }
}
