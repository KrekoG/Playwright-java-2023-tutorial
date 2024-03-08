package org.nixsolutions.day10;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LearnTraceViewer {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        BrowserType chromium = playwright.chromium();
//        BrowserType.LaunchOptions headless = new BrowserType.LaunchOptions().setHeadless(false);
        Browser browser = chromium.launch();
//        Browser browser = chromium.launch(headless);
        BrowserContext context = browser.newContext();

        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        Page page = context.newPage();
        page.navigate("https://bookcart.azurewebsites.net/login");
        page.locator("input[formcontrolname='username']").fill("ortoni");
        page.locator("input[formcontrolname='password']").fill("Pass1234$");
        page.locator("xpath=.//form//button[contains(.,\"Login\")]").click();
        System.out.println("Username: " + page.locator("xpath=.//a[@class=\"mat-mdc-menu-trigger mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-primary mat-mdc-button-base ng-star-inserted\"]//span[@class=\"mdc-button__label\"]/span").textContent());

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day10.zip")));

        // It is required to close the context otherwise the video won't be saved
        context.close();
        playwright.close();

        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day10.zip"`

        // or drop it to
        // `https://trace.playwright.dev/`

        // or load it with
        // `https://trace.playwright.dev/?trace=<yourUrlToYourTraceFile.zip>`
    }
}
