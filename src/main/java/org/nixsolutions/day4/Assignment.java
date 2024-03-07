package org.nixsolutions.day4;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.SelectOption;

public class Assignment {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setChannel("chrome")
        );
        Page page = browser.newPage();

        page.navigate("https://testsheepnz.github.io/BasicCalculator.html");

        Locator num1Field = page.locator("#number1Field");
        Locator num2Field = page.locator("#number2Field");
        Locator operationDropDown = page.locator("#selectOperationDropdown");
        Locator calculateButton = page.locator("#calculateButton");
        Locator result = page.locator("#numberAnswerField");

        num1Field.fill("123");
        num2Field.fill("321");
        operationDropDown.selectOption(new SelectOption().setLabel("Add"));
        calculateButton.click();
        System.out.println("123 + 321 = " + result.inputValue());

        num1Field.fill("2");
        num2Field.fill("5");
        operationDropDown.selectOption(new SelectOption().setLabel("Multiply"));
        calculateButton.click();
        System.out.println("2 x 5 = " + result.inputValue());

        page.close();
        browser.close();
        playwright.close();
    }
}

