package com.ShopHubTest;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ShopHubView.ValidateSuccessfulLoginView;
import com.generics.BaseTest;

public class ValidateSuccessfulLoginTest extends BaseTest {

    private ValidateSuccessfulLoginView objValidateSuccessfulLoginView;

    private static Properties objProperties = null;

    public void initializeViewsAndPages() {

        objValidateSuccessfulLoginView =
                new ValidateSuccessfulLoginView(this);
    }

    @BeforeMethod
    public void initializeEnvironment() {

        initializeWebEnvironment(getProperty("tenantUrl"));
        initializeViewsAndPages();
    }

    @Test
    public void validateSuccessfulLogin_001() {

        objValidateSuccessfulLoginView.validateSuccessfulLoginSteps();

        objValidateSuccessfulLoginView.verifyLoginSuccessful();
    }

    @AfterMethod
    public void tearDownEnv() {

        tearDownWebEnvironment();
        objValidateSuccessfulLoginView = null;
    }

    @Override
	public String getProperty(String testSuiteName) {

        if (objProperties == null) {

            try {

                objProperties = new Properties();

                objProperties.load(
                    new FileInputStream(
                        System.getProperty("user.dir")
                        + "/src/test/resources/config.properties"
                    )
                );

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return objProperties.getProperty(testSuiteName);
    }
}