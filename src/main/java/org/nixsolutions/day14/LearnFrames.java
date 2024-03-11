package org.nixsolutions.day14;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Frame;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class LearnFrames {
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

        page.navigate("https://letcode.in/frame");
        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();

        List<Frame> frames = page.frames();
        System.out.println(frames.size());
//        FrameLocator frameLocator = page.frameLocator("#firstFr");
//        frameLocator.getByPlaceholder("Enter name").fill("Koushik");
        Frame frame = page.frame("firstFr");
        frame.getByPlaceholder("Enter name").fill("Koushik");
        frame.getByPlaceholder("Enter email").fill("Chatterjee");

        List<Frame> childFrames = frame.childFrames();
        System.out.println(childFrames.size());
        childFrames.forEach(f -> System.out.println(f.url()));

//        FrameLocator childFrame = frameLocator.frameLocator("iframe.has-background-white");
        // Frame innerFrame = page.frameByUrl("https://letcode.in/innerFrame");
        Frame innerFrame = page.frameByUrl(Pattern.compile("innerFrame"));
        frame.getByPlaceholder("Enter email").fill("koushik@mail.com");

        context.tracing().stop(new Tracing.StopOptions().setPath(Paths.get("./target/tracing/day14.zip")));

        // It is required to close the context otherwise the video won't be saved
        context.close();
        playwright.close();
        // open trace file with
        // `mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="show-trace ./target/tracing/day13.zip"`
    }
}
