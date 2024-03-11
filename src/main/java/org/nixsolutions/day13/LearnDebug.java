package org.nixsolutions.day13;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;

import java.nio.file.Paths;

public class LearnDebug {
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

        page.navigate("https://bookcart.azurewebsites.net/");

        page.pause(); // basically a debug break point

        page.getByText("Login").locator("visible=true").click();
        page.getByLabel("Username").fill("ortoni");
        page.getByLabel("Password").fill("Pass1234$");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("Login")).last().click();

        // to ensure we wait for the page reload after the login action
        page.getByText("ortoni").waitFor();

        page.getByPlaceholder("Search books or authors")
                .pressSequentially("The Hookup");
        page.getByRole(AriaRole.OPTION).first().click();
        page.getByAltText("Book cover image").click();
        System.out.println(page.url());

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day13.zip")));

        // It is required to close the context otherwise the video won't be saved
        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day13.zip"`
    }
}
