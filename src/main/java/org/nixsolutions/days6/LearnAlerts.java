package org.nixsolutions.days6;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LearnAlerts {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();

        page.navigate("https://letcode.in/alert");
        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();

        page.onceDialog(dialog -> {
            System.out.println(dialog.message());
            dialog.dismiss();
        });
        page.locator("#accept").click();

        page.onceDialog(dialog -> {
             System.out.println(dialog.message());
            dialog.accept("Test Name");
        });
        page.locator("#prompt").click();
        System.out.println(page.locator("#myName").textContent());

        page.close();
        browser.close();
        playwright.close();
    }
}
