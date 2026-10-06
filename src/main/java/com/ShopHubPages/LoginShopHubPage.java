////package com.MasterCardSparkTest;
//
//package com.ShopHubPages;
//
//import org.openqa.selenium.By;
//
//import com.generics.Pojo;
//
//public class LoginShopHubPage {
//
//    private Pojo objPojo;
//
//    By id = By.xpath("//input[@id='input-email']");  //By email = By.xpath("//input[@id='input-email']");
//    By password = By.xpath("//input[@name='password']"); //input-password
//    By submit = By.xpath("//input[@id='btnSubmit']"); //Submit
//    By brandStore = By.xpath("//span[text()='Brand Store']");    ////span[text()='Brand Store']
//    By loginError = By.xpath(
//    	    "//div[contains(@class,'alert-danger') and contains(normalize-space(.),'Warning: No match for email address and/or password.')]"
//    	);
//
//    By emailError = By.xpath(
//    	    "//div[contains(@class,'email-error') and normalize-space()='Please Enter valid email']"
//    	);
//
//    By loginError2 = By.xpath(
//    	    "//div[contains(@class,'alert-danger') and contains(normalize-space(.),'Warning: No match for email address and/or password.')]"
//    	);
//
//
//    By emailRequiredError = By.xpath(
//    	    "//div[contains(@class,'email-error') and normalize-space()='Email is required']"
//    	);
//
//
//    By passwordRequiredError = By.xpath(
//    	    "//div[contains(@class,'password-error') and normalize-space()='Password is required']"
//    	);
//    // Contact Us locator
//  //  By contactUs = By.xpath("//a[text()='Contact Us']");
//
//    public LoginShopHubPage(Pojo pojo) {
//        objPojo = pojo;
//    }
//
//    public void loginCredentials(String userName, String PassKey) {
//
//        objPojo.getObjUtilities().logReporter(
//            "Element highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(id)
//        );
//
//        objPojo.getObjUtilities().logReporter(   "Username inserted", objPojo.getObjWrapperFunctions().setText(id, userName)        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Element highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(password)
//        );
//
//        objPojo.getObjWrapperFunctions().setText(password, PassKey);
//
//        objPojo.getObjWrapperFunctions().waitFor(3);
//
//        objPojo.getObjUtilities().logReporter(
//            "Submit button highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(submit)
//        );
//
//        objPojo.getObjWrapperFunctions().click(submit);
//    }
//    public void verifyLoginSuccessful() {
//
//        String actualUrl = objPojo.getObjWrapperFunctions().getCurrentUrl();
//
//        if (actualUrl.contains("route=common/home")) {
//
//            String actualText = objPojo.getObjWrapperFunctions()
//                    .getText(brandStore, "text");
//
//            if (actualText.trim().equals("Brand Store")) {
//
//                objPojo.getObjUtilities().logReporter(
//                        "Login successful - Brand Store displayed",
//                        true);
//
//            } else {
//
//                objPojo.getObjUtilities().logReporter(
//                        "Login verification failed - Brand Store not displayed",
//                        false);
//            }
//
//        } else {
//
//            objPojo.getObjUtilities().logReporter(
//                    "Login failed - Home page not reached",
//                    false);
//        }
//    }
//
//    public void verifyLoginError(String expectedError) {
//
//        String actualError = objPojo.getObjWrapperFunctions()
//                .getText(loginError, "text");
//
//        if (actualError.contains(expectedError.trim())) {
//
//            objPojo.getObjUtilities().logReporter(
//                "Negative login verified - Error message displayed",
//                true
//            );
//
//        } else {
//
//            objPojo.getObjUtilities().logReporter(
//                "Negative login verification failed. Actual message: " + actualError,
//                false
//            );
//        }
//    }
//
//    public void verifyEmailError(String expectedError) {
//
//        String actualError = objPojo.getObjWrapperFunctions()
//                .getText(emailError, "text");
//
//        if (actualError.contains(expectedError.trim())) {
//
//            objPojo.getObjUtilities().logReporter(
//                "Invalid email validation verified",
//                true
//            );
//
//        } else {
//
//            objPojo.getObjUtilities().logReporter(
//                "Invalid email validation failed. Actual message: " + actualError,
//                false
//            );
//        }
//    }
//
//    public void verifyLoginError2(String expectedError) {
//
//        String actualError = objPojo.getObjWrapperFunctions()
//                .getText(loginError, "text");
//
//        if (actualError.contains(expectedError.trim())) {
//
//            objPojo.getObjUtilities().logReporter(
//                "Negative login verified - Error message displayed",
//                true
//            );
//
//        } else {
//
//            objPojo.getObjUtilities().logReporter(
//                "Negative login verification failed. Actual message: " + actualError,
//                false
//            );
//        }
//    }
//
//
//    public void loginWithBlankEmail(String passKey) {
//
//        objPojo.getObjUtilities().logReporter(
//            "Email field highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(id)
//        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Email field left blank",
//            true
//        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Password field highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(password)
//        );
//
//        objPojo.getObjWrapperFunctions().setText(password, passKey);
//
//        objPojo.getObjUtilities().logReporter(
//            "Submit button highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(submit)
//        );
//
//        objPojo.getObjWrapperFunctions().click(submit);
//    }
//
//    public void verifyEmailRequiredError(String expectedError) {
//
//        String actualError = objPojo.getObjWrapperFunctions()
//                .getText(emailRequiredError, "text");
//
//        if (actualError.contains(expectedError.trim())) {
//
//            objPojo.getObjUtilities().logReporter(
//                "Email required validation verified",
//                true
//            );
//
//        } else {
//
//            objPojo.getObjUtilities().logReporter(
//                "Email required validation failed. Actual message: "
//                + actualError,
//                false
//            );
//        }
//    }
//
//
//    public void verifyPasswordRequiredError(String expectedError) {
//
//        String actualError = objPojo.getObjWrapperFunctions()
//                .getText(passwordRequiredError, "text");
//
//        if (actualError.contains(expectedError.trim())) {
//
//            objPojo.getObjUtilities().logReporter(
//                "Password required validation verified",
//                true
//            );
//
//        } else {
//
//            objPojo.getObjUtilities().logReporter(
//                "Password required validation failed. Actual message: " + actualError,
//                false
//            );
//        }
//    }
//
//
//    public void loginWithBlankPassword(String userName) {
//
//        objPojo.getObjUtilities().logReporter(
//            "Email field highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(id)
//        );
//
//        objPojo.getObjWrapperFunctions().setText(id, userName);
//
//        objPojo.getObjUtilities().logReporter(
//            "Password field highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(password)
//        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Password field left blank",
//            true
//        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Submit button highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(submit)
//        );
//
//        objPojo.getObjWrapperFunctions().click(submit);
//    }
//
//
//
//    public void loginWithBothFieldsBlank() {
//
//        objPojo.getObjUtilities().logReporter(
//            "Email field highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(id)
//        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Email field left blank",
//            true
//        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Password field highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(password)
//        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Password field left blank",
//            true
//        );
//
//        objPojo.getObjUtilities().logReporter(
//            "Submit button highlighted",
//            objPojo.getObjWrapperFunctions().elementHighlight(submit)
//        );
//
//        objPojo.getObjWrapperFunctions().click(submit);
//    }
//    // Click Contact Us link
////  public void clickContactUs() {
////
////        objPojo.getObjUtilities().logReporter(
////            "Contact Us link highlighted",
////            objPojo.getObjWrapperFunctions().elementHighlight(contactUs)
////        );
////
////        objPojo.getObjWrapperFunctions().click(contactUs);
////    }
//}