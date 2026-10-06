package com.ShopHubPages;

import org.openqa.selenium.By;

import com.generics.Pojo;

public class ValidateLoginWithUppercaseEmailPage {

    private Pojo objPojo;

    By id = By.xpath("//input[@id='input-email']");
    By password = By.xpath("//input[@name='password']");
    By submit = By.xpath("//input[@id='btnSubmit']");

    By emailValidationError = By.xpath(
        "//div[contains(@class,'email-error') and " +
        "normalize-space()='Please Enter valid email']"
    );

    public ValidateLoginWithUppercaseEmailPage(Pojo pojo) {
        objPojo = pojo;
    }

    public void loginWithUppercaseEmail(
            String userName,
            String passKey) {

        objPojo.getObjUtilities().logReporter(
            "Email field highlighted",
            objPojo.getObjWrapperFunctions().elementHighlight(id)
        );

        objPojo.getObjUtilities().logReporter(
            "Uppercase email entered",
            objPojo.getObjWrapperFunctions().setText(
                id,
                userName
            )
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

    public void verifyEmailValidationError(String expectedError) {

        String actualError =
            objPojo.getObjWrapperFunctions()
                   .getText(emailValidationError, "text");

        if (actualError.contains(expectedError.trim())) {

            objPojo.getObjUtilities().logReporter(
                "Uppercase email validation verified",
                true
            );

        } else {

            objPojo.getObjUtilities().logReporter(
                "Uppercase email validation failed. Actual message: "
                + actualError,
                false
            );
        }
    }
}