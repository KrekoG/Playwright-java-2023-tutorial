package org.nixsolutions.day2;

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

        page.navigate("https://google.com/");
        System.out.println(page.title());
        System.out.println(page.url());

        page.close();
        browser.close();
        playwright.close();
    }
}

