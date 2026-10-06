//package com.ShopHubView;
//
//import com.ShopHubPages.LoginShopHubPage;
//
////package com.MasterCardSparkTest;
//
//import com.generics.ConfigManager;
//import com.generics.Pojo;
//
//public class LoginShopHubView extends ConfigManager {
//
//    private LoginShopHubPage objLoginShopHubPage;
//
//    public LoginShopHubView(Pojo pojo) {
//        super(pojo);
//        objLoginShopHubPage = new LoginShopHubPage(pojo);
//    }
//
//    public void shopHubLoginSteps() {
//
//        String username = dpString("id");
//        String password = dpString("Passkey");
//
//        System.out.println("================================");
//        System.out.println("Username = [" + username + "]");
//        System.out.println("Password = [" + password + "]");
//        System.out.println("================================");
//
//        objLoginShopHubPage.loginCredentials(username, password);
//    }
//
//
//    public void verifyLoginSuccessful() {
//        objLoginShopHubPage.verifyLoginSuccessful();
//    }
//
//    public void verifyLoginError() {
//
//        String expectedError = dpString("loginError");
//
//        objLoginShopHubPage.verifyLoginError(expectedError);
//    }
//
//    public void verifyEmailError() {
//        String expectedError = dpString("EmailError");
//        objLoginShopHubPage.verifyEmailError(expectedError);
//    }
//
//    public void verifyLoginError2() {
//
//        String expectedError = dpString("loginError");
//
//        objLoginShopHubPage.verifyLoginError(expectedError);
//    }
//
//    public void verifyEmailRequiredError() {
//
//        String expectedError = dpString("EmailRequiredError");
//
//        objLoginShopHubPage.verifyEmailRequiredError(expectedError);
//    }
//
//    public void shopHubLoginWithBlankEmail() {
//
//        String password = dpString("Passkey");
//
//        objLoginShopHubPage.loginWithBlankEmail(password);
//    }
//
//
//
//    public void shopHubLoginWithBlankPassword() {
//
//        String userName = dpString("id");
//
//        objLoginShopHubPage.loginWithBlankPassword(userName);
//    }
//
//    public void verifyPasswordRequiredError() {
//
//        String expectedError = dpString("PasswordRequiredError");
//
//        objLoginShopHubPage.verifyPasswordRequiredError(expectedError);
//    }
//
//    public void shopHubLoginWithBothFieldsBlank() {
//
//        objLoginShopHubPage.loginWithBothFieldsBlank();
//    }
//
//    public void verifyBothRequiredErrors() {
//
//        String expectedEmailError = dpString("EmailRequiredError");
//        String expectedPasswordError = dpString("PasswordRequiredError");
//
//        objLoginShopHubPage.verifyEmailRequiredError(expectedEmailError);
//
//        objLoginShopHubPage.verifyPasswordRequiredError(expectedPasswordError);
//    }
//}
//
//
////
////    // Contact Us step
////    public void contactUsSteps() {
////
////        objLoginShopHubPage.clickContactUs();
////    }
////}