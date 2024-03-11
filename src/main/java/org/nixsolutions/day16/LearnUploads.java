package org.nixsolutions.day16;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LearnUploads {
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

        // With file type inputs:

        // page.navigate("https://sendgb.com");
        // page.locator("#cookiescript_reject").click();
        // page.locator("span:has-text('OK') >> visible=true'").click();

        // page.locator("input[type='file']").setInputFiles(Paths.get("./target/downloads/sample.xlsx"));

        // with event based file input:

        page.navigate("https://the-internet.herokuapp.com/upload");

        FileChooser fileChooser = page.waitForFileChooser(() -> page.locator("#drag-drop-upload").click());
        System.out.println("multiple file uploads are supported: " + fileChooser.isMultiple());
        fileChooser.setFiles(new Path[] {
                Paths.get("./target/downloads/sample.xlsx"),
                Paths.get("./target/downloads/sample.pdf"),
                Paths.get("./target/downloads/sample.txt")
        });
        System.out.println(page.locator("#drag-drop-upload").textContent());

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day16.zip")));

        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day16.zip"`
    }
}
