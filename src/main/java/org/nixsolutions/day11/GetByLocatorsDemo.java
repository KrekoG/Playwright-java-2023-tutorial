package org.nixsolutions.day11;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public class GetByLocatorsDemo {
    public static void main(String[] args) {
        boolean runInHeadlessMode = true;
        Playwright playwright = Playwright.create();
        BrowserType chromium = playwright.chromium();
        BrowserType.LaunchOptions headless = new BrowserType.LaunchOptions()
                .setHeadless(runInHeadlessMode);
        Browser browser = chromium.launch(headless);
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        page.navigate("https://bookcart.azurewebsites.net/");

        page.getByText("Login").locator("visible=true").click();
        page.getByLabel("Username").fill("ortoni");
        page.getByLabel("Password").fill("Pass1234$");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions()
                .setName("Login")).last().click();

        // to ensure we wait for the page reload after the login action
        page.getByText("ortoni").waitFor();

        page.getByPlaceholder("Search books or authors")
                .pressSequentially("The Hate U Give");
        page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions()
                .setName("The Hate U Give")).click();
        page.getByAltText("Book cover image").click();

        System.out.println(page.url());

        playwright.close();
    }
}
