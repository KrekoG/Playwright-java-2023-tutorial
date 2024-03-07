package org.nixsolutions.days6;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.options.SelectOption;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Assignment {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setChannel("chrome")
        );
        Page page = browser.newPage();

        page.navigate("https://lambdatest.com/selenium-playground/javascript-alert-box-demo");
        page.locator("#CybotCookiebotDialogBodyButtonDecline").click();

        Locator JavaScriptAlert = page.locator("xpath=.//p[text()=\"JavaScript Alerts\"]/button[text()=\"Click Me\"]");
        Locator confirmBoxTrigger = page.locator("xpath=.//p[text()=\"Confirm box:\"]/button[text()=\"Click Me\"]");
        Locator promptBox = page.locator("xpath=.//p[text()=\"Prompt box:\"]/button[text()=\"Click Me\"]");

        System.out.println("-------------------------------------");
        page.onceDialog(dialog -> {
            System.out.println("message: " + dialog.message());
            dialog.accept();
        });
        JavaScriptAlert.click();

        System.out.println("-------------------------------------");
        page.onceDialog(dialog -> {
            System.out.println("message: " + dialog.message());
            dialog.accept();
        });
        confirmBoxTrigger.click();
        System.out.println(page.locator("#confirm-demo").textContent());

        page.onceDialog(dialog -> {
            System.out.println("message: " + dialog.message());
            dialog.dismiss();
        });
        confirmBoxTrigger.click();
        System.out.println(page.locator("#confirm-demo").textContent());

        System.out.println("-------------------------------------");
        page.onceDialog(dialog -> {
            System.out.println("message: " + dialog.message());
            System.out.println("defaultValue: " + dialog.defaultValue());
            dialog.accept("Johnny B Good");
        });
        promptBox.click();
        System.out.println(page.locator("#prompt-demo").textContent());

        page.close();
        browser.close();
        playwright.close();
    }
}

