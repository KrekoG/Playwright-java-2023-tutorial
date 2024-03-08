package org.nixsolutions.day8;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class WhatIsBrowserContext {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        BrowserType chromium = playwright.chromium();
//        BrowserType.LaunchOptions headeless = new BrowserType.LaunchOptions().setHeadless(false);
        Browser browser = chromium.launch();
//        Browser browser = chromium.launch(headeless);
        BrowserContext context = browser.newContext();
        Page page = context.newPage();

        System.out.println("first browser, first tab");
        page.navigate("https://bookcart.azurewebsites.net/login");
        page.locator("input[formcontrolname='username']").fill("ortoni");
        page.locator("input[formcontrolname='password']").fill("Pass1234$");
        page.locator("xpath=.//form//button[contains(.,\"Login\")]").click();
        System.out.println("Username: " + page.locator("xpath=.//a[@class=\"mat-mdc-menu-trigger mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-primary mat-mdc-button-base ng-star-inserted\"]//span[@class=\"mdc-button__label\"]/span").textContent());

        System.out.println("same browser, different tab");
        Page secondPage = context.newPage();
        secondPage.navigate("https://bookcart.azurewebsites.net/");
        System.out.println("Username: " + secondPage.locator("xpath=.//a[@class=\"mat-mdc-menu-trigger mdc-button mdc-button--unelevated mat-mdc-unelevated-button mat-primary mat-mdc-button-base ng-star-inserted\"]//span[@class=\"mdc-button__label\"]/span").textContent());

        System.out.println("Different browser");
        BrowserContext secondContext = browser.newContext();
        Page thirdPage = secondContext.newPage();
        thirdPage.navigate("https://bookcart.azurewebsites.net/");
        System.out.println("Login button available: " + thirdPage.locator("xpath=.//span[text()=\" Login \"]").isVisible());

        page.close();
        browser.close();
        playwright.close();
    }
}
