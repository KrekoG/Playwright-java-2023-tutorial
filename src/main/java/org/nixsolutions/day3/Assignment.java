package org.nixsolutions.day3;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Assignment {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setChannel("chrome")
        );
        Page page = browser.newPage();

        page.navigate("https://www.lambdatest.com/selenium-playground/simple-form-demo");

        page.locator("#CybotCookiebotDialogBodyButtonDecline").click();

        page.locator("xpath=.//input[@id=\"user-message\"]").fill("Test message from the assignment");
        page.locator("#showInput").click();
        System.out.println("The message was: " + page.locator("#message").textContent());

        page.locator("#sum1").fill("18");
        page.locator("#sum2").fill("12");
        page.locator("xpath=.//button[text()=\"Get Sum\"]").click();
        System.out.println("Sum of 18 and 12: " + page.locator("#addmessage").textContent());

        page.close();
        browser.close();
        playwright.close();
    }
}

