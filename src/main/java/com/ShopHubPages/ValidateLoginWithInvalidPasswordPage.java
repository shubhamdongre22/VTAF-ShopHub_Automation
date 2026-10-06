package com.ShopHubPages;

import org.openqa.selenium.By;

import com.generics.Pojo;

public class ValidateLoginWithInvalidPasswordPage {

    private Pojo objPojo;

    By id = By.xpath("//input[@id='input-email']");
    By password = By.xpath("//input[@name='password']");
    By submit = By.xpath("//input[@id='btnSubmit']");

    By loginError = By.xpath(
        "//div[contains(@class,'alert-danger') and " +
        "contains(normalize-space(.),'Warning: No match for email address and/or password.')]"
    );

    public ValidateLoginWithInvalidPasswordPage(Pojo pojo) {
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

    public void verifyLoginError(String expectedError) {

        String actualError =
            objPojo.getObjWrapperFunctions().getText(loginError, "text");

        if (actualError.contains(expectedError.trim())) {

            objPojo.getObjUtilities().logReporter(
                "Invalid password error verified",
                true
            );

        } else {

            objPojo.getObjUtilities().logReporter(
                "Invalid password verification failed. Actual message: "
                + actualError,
                false
            );
        }
    }
}