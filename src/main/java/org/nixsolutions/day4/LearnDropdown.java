package org.nixsolutions.day4;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.SelectOption;

public class LearnDropdown {
    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
        Page page = browser.newPage();

        page.navigate("https://letcode.in/dropdowns");
        page.locator(".fc-button.fc-cta-do-not-consent.fc-secondary-button").click();

//        page.selectOption("#fruits", "3");
        Locator fruitsDropDown = page.locator("#fruits");

        // variations of the same action:
        fruitsDropDown.selectOption("3");
//        fruitsDropDown.selectOption(new SelectOption().setLabel("Banana"));
//        fruitsDropDown.selectOption(new SelectOption().setIndex(4));
//        fruitsDropDown.selectOption(new SelectOption().setValue("3"));
//        fruitsDropDown.selectOption(new SelectOption().setValue("3"), new Locator.SelectOptionOptions());
        System.out.println(page.locator("p.subtitle").textContent());

        Locator heroes = page.locator("#superheros");
        heroes.selectOption(new String[]{"ta", "bp", "am"});

        Locator languages = page.locator("#lang");
        Locator options = languages.locator("option");
        System.out.println(options.allInnerTexts());
        languages.selectOption(new SelectOption().setIndex(options.count() - 1));

        page.close();
        browser.close();
        playwright.close();
    }
}
