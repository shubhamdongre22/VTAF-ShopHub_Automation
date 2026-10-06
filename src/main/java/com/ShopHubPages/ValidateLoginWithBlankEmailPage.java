package com.ShopHubPages;

import org.openqa.selenium.By;

import com.generics.Pojo;

public class ValidateLoginWithBlankEmailPage {

    private Pojo objPojo;

    By id = By.xpath("//input[@id='input-email']");
    By password = By.xpath("//input[@name='password']");
    By submit = By.xpath("//input[@id='btnSubmit']");

    By emailRequiredError = By.xpath(
        "//div[contains(@class,'email-error') and " +
        "normalize-space()='Email is required']"
    );

    public ValidateLoginWithBlankEmailPage(Pojo pojo) {
        objPojo = pojo;
    }

    public void loginWithBlankEmail(String passKey) {

        objPojo.getObjUtilities().logReporter(
            "Email field highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(id)
        );

        objPojo.getObjUtilities().logReporter(
            "Email field left blank",
            true
        );

        objPojo.getObjUtilities().logReporter(
            "Password field highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(password)
        );

        objPojo.getObjWrapperFunctions().setText(
            password,
            passKey
        );

        objPojo.getObjUtilities().logReporter(
            "Submit button highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(submit)
        );

        objPojo.getObjWrapperFunctions().click(submit);
    }

    public void verifyEmailRequiredError(String expectedError) {

        String actualError =
            objPojo.getObjWrapperFunctions()
                   .getText(emailRequiredError, "text");

        if (actualError.contains(expectedError.trim())) {

            objPojo.getObjUtilities().logReporter(
                "Email required validation verified",
                true
            );

        } else {

            objPojo.getObjUtilities().logReporter(
                "Email required validation failed. Actual message: "
                + actualError,
                false
            );
        }
    }
}