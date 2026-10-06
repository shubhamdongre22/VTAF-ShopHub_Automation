package com.ShopHubPages;

import org.openqa.selenium.By;

import com.generics.Pojo;

public class ValidateSuccessfulLoginPage {

    private Pojo objPojo;

    By id = By.xpath("//input[@id='input-email']");
    By password = By.xpath("//input[@name='password']");
    By submit = By.xpath("//input[@id='btnSubmit']");
    By brandStore = By.xpath(
        "//div[@class='brand-name']//span[normalize-space()='Brand Store']"
    );

    public ValidateSuccessfulLoginPage(Pojo pojo) {
        objPojo = pojo;
    }

    public void loginCredentials(String userName, String passKey) {

        objPojo.getObjUtilities().logReporter(
            "Email field highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(id)
        );

        objPojo.getObjWrapperFunctions().setText(id, userName);

        objPojo.getObjUtilities().logReporter(
            "Password field highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(password)
        );

        objPojo.getObjWrapperFunctions().setText(password, passKey);

        objPojo.getObjUtilities().logReporter(
            "Submit button highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(submit)
        );

        objPojo.getObjWrapperFunctions().click(submit);
    }

    public void verifySuccessfulLogin() {

        String actualUrl =
            objPojo.getObjWrapperFunctions().getCurrentUrl();

        if (actualUrl.contains("route=common/home")) {

            String actualText =
                objPojo.getObjWrapperFunctions().getText(brandStore, "text");

            if (actualText.trim().equals("Brand Store")) {

                objPojo.getObjUtilities().logReporter(
                    "Login successful - Brand Store displayed",
                    true
                );

            } else {

                objPojo.getObjUtilities().logReporter(
                    "Login verification failed - Brand Store not displayed",
                    false
                );
            }

        } else {

            objPojo.getObjUtilities().logReporter(
                "Login failed - Home page not reached",
                false
            );
        }
    }
}