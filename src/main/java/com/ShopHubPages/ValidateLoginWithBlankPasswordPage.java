package com.ShopHubPages;

import org.openqa.selenium.By;

import com.generics.Pojo;

public class ValidateLoginWithBlankPasswordPage {

    private Pojo objPojo;

    By id = By.xpath("//input[@id='input-email']");
    By password = By.xpath("//input[@name='password']");
    By submit = By.xpath("//input[@id='btnSubmit']");

    By passwordRequiredError = By.xpath(
        "//div[contains(@class,'password-error') and " +
        "normalize-space()='Password is required']"
    );

    public ValidateLoginWithBlankPasswordPage(Pojo pojo) {
        objPojo = pojo;
    }

    public void loginWithBlankPassword(String username) {

        objPojo.getObjUtilities().logReporter(
            "Email field highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(id)
        );

        objPojo.getObjWrapperFunctions().setText(id, username);

        objPojo.getObjUtilities().logReporter(
            "Password field highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(password)
        );

        objPojo.getObjUtilities().logReporter(
            "Password field left blank",
            true
        );

        objPojo.getObjUtilities().logReporter(
            "Submit button highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(submit)
        );

        objPojo.getObjWrapperFunctions().click(submit);
    }

    public void verifyPasswordRequiredError(String expectedError) {

        String actualError = objPojo.getObjWrapperFunctions()
                .getText(passwordRequiredError, "text");

        if (actualError.contains(expectedError.trim())) {

            objPojo.getObjUtilities().logReporter(
                "Password required validation verified",
                true
            );

        } else {

            objPojo.getObjUtilities().logReporter(
                "Password required validation failed. Actual message: "
                + actualError,
                false
            );
        }
    }
}