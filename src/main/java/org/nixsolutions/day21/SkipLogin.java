package org.nixsolutions.day21;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;

import java.nio.file.Paths;

public class SkipLogin {
    public static void main(String[] args) {
        boolean runInHeadlessMode = true;
        Playwright playwright = Playwright.create();
        BrowserType chromium = playwright.chromium();
        BrowserType.LaunchOptions headless = new BrowserType.LaunchOptions()
                .setHeadless(runInHeadlessMode);
        Browser browser = chromium.launch(headless);
        BrowserContext context = browser.newContext();

        Page page = context.newPage();
        page.navigate("https://bookcart.azurewebsites.net/login");
        page.locator("input[formcontrolname='username']").fill("ortoni");
        page.locator("input[formcontrolname='password']").fill("Pass1234$");
        page.locator("xpath=.//form//button[contains(.,\"Login\")]").click();
        System.out.println("Username: " + page.locator("xpath=.//a[@class=\"mat-mdc-menu-trigger mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-primary mat-mdc-button-base ng-star-inserted\"]//span[@class=\"mdc-button__label\"]/span").textContent());

        context.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("./target/auth/bookcart-auth.json")));
        playwright.close();

        // Restart, while reusing the previous login data
        playwright = Playwright.create();
        chromium = playwright.chromium();
        headless = new BrowserType.LaunchOptions()
                .setHeadless(runInHeadlessMode);
        browser = chromium.launch(headless);
        context = browser.newContext(new Browser.NewContextOptions().setStorageStatePath(Paths.get("./target/auth/bookcart-auth.json")));

        page = context.newPage();
        page.navigate("https://bookcart.azurewebsites.net/login");
        System.out.println("Username: " + page.locator("xpath=.//a[@class=\"mat-mdc-menu-trigger mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-primary mat-mdc-button-base ng-star-inserted\"]//span[@class=\"mdc-button__label\"]/span").textContent());

        playwright.close();
    }
}
