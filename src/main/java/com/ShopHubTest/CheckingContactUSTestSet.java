//package com.ShopHubTest;
//
//
//import java.io.FileInputStream;
//import java.util.Properties;
//
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import com.ShopHubTest.*;
//import com.ShopHubView.CheckingContactUSView;
//import com.generics.BaseTest;
//
//public class CheckingContactUSTestSet extends BaseTest {
//
//    private CheckingContactUSView objCheckingContactUSView;
//
//    private static Properties objProperties = null;
//
//    public void initializeViewsAndPages() {
//        objCheckingContactUSView = new CheckingContactUSView(this);
//    }
//
//    @BeforeClass
//    public void initializeEnvironment() {
//        initializeWebEnvironment(getProperty("tenantUrl"));
//        initializeViewsAndPages();
//    }
//
//    @Test
////    public void shopHubLogin_001() {
////        objLoginShopHubView.shopHubLoginSteps();
////        objLoginShopHubView.verifyAdminDashbOard();
////    }
//
//    public void ContactUS_002() {
//    	objCheckingContactUSView.VerifyFooter();
//    	objCheckingContactUSView.VerifyFooter();
//    }
//
//
//
//    @AfterClass
//    public void tearDownEnv() {
//        tearDownWebEnvironment();
//        objCheckingContactUSView = null;
//    }
//
//    public String getProperty(String testSuiteName) {
//
//        if (objProperties == null) {
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
//
//}
