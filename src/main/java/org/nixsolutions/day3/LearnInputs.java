package org.nixsolutions.day3;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LearnInputs {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
        );
        Page page = browser.newPage();

        page.navigate("https://letcode.in/edit");

        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();

        page.locator("#fullName").fill("Full Name");

        Locator locator = page.locator("#join");
        locator.press("End");
        locator.pressSequentially(" man");
        locator.press("Tab");

        System.out.println(page.locator("#getMe").getAttribute("value"));

        page.locator("#clearMe").clear();

        page.close();
        browser.close();
        playwright.close();
    }
}
