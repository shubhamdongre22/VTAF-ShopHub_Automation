//package com.ShopHubTest;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//
////package com.MasterCardSparkTest;
//
//import java.io.FileInputStream;
//import java.util.Properties;
//
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import com.ShopHubView.LoginShopHubView;
//import com.generics.BaseTest;
//
//public class LoginShopHubTestSet extends BaseTest {
//
//    private LoginShopHubView objLoginShopHubView;
//
//    private static Properties objProperties = null;
//
//    public void initializeViewsAndPages() {
//
//        objLoginShopHubView = new LoginShopHubView(this);
//    }
//
////    @BeforeClass
////    public void initializeEnvironment() {
////
////        initializeWebEnvironment(getProperty("tenantUrl"));
////
////        initializeViewsAndPages();
////    }
//    @BeforeMethod
//    public void initializeEnvironment() {
//        initializeWebEnvironment(getProperty("tenantUrl"));
//        initializeViewsAndPages();
//    }
//
//    @Test
//    public void shopHubLogin_001() {
//
//        objLoginShopHubView.shopHubLoginSteps();
//
//        objLoginShopHubView.verifyLoginSuccessful();
//
//    }
//
//    @Test
//    public void shopHubLogin_002() {
//        objLoginShopHubView.shopHubLoginSteps();
//        objLoginShopHubView.verifyLoginError();
//
//    }
//
//    @Test
//    public void shopHubLogin_003() {
//        objLoginShopHubView.shopHubLoginSteps();
//        objLoginShopHubView.verifyEmailError();
//    }
//
//    @Test
//    public void shopHubLogin_004() {
//        objLoginShopHubView.shopHubLoginSteps();
//        objLoginShopHubView.verifyLoginError2();
//    }
//
//    @Test
//    public void shopHubLogin_005() {
//
//        objLoginShopHubView.shopHubLoginWithBlankEmail();
//
//        objLoginShopHubView.verifyEmailRequiredError();
//    }
//
//    @Test
//    public void shopHubLogin_006() {
//
//        objLoginShopHubView.shopHubLoginWithBlankPassword();
//        objLoginShopHubView.verifyPasswordRequiredError();
//    }
//
//    @Test
//    public void shopHubLogin_007() {
//
//        objLoginShopHubView.shopHubLoginWithBothFieldsBlank();
//
//        objLoginShopHubView.verifyBothRequiredErrors();
//    }
//
//
//    @Test
//    public void shopHubLogin_008() {
//
//        objLoginShopHubView.shopHubLoginSteps();
//    }
//
//    // Contact Us test
////    @Test
////    public void contactUs_001() {
////
////        objLoginShopHubView.contactUsSteps();
////    }
////
////    @AfterClass
////    public void tearDownEnv() {
////
////        tearDownWebEnvironment();
////
////        objLoginShopHubView = null;
////    }
//
//    @AfterMethod
//    public void tearDownEnv() {
//        tearDownWebEnvironment();
//        objLoginShopHubView = null;
//    }
//
//    public String getProperty(String testSuiteName) {
//
//        if (objProperties == null) {
//
//            try {
//                objProperties = new Properties();
//
//                objProperties.load(
//                    new FileInputStream(
//                        System.getProperty("user.dir")
//                        + "/src/test/resources/config.properties"
//                    )
//                );
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return objProperties.getProperty(testSuiteName);
//    }
//}