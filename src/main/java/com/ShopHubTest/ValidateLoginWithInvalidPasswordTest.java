package com.ShopHubTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ShopHubView.ValLoginWithInvalidPasswordView;
import com.generics.BaseTest;

public class ValidateLoginWithInvalidPasswordTest extends BaseTest {

    private ValLoginWithInvalidPasswordView objValLoginWithInvalidPasswordView;

    public void initializeViewsAndPages() {

        objValLoginWithInvalidPasswordView =
                new ValLoginWithInvalidPasswordView(this);
    }

    @BeforeMethod
    public void initializeEnvironment() {

        initializeWebEnvironment(
                getProperty("tenantUrl")
        );

        initializeViewsAndPages();
    }

    @Test
    public void validateLoginWithInvalidPassword_003() {

        objValLoginWithInvalidPasswordView
                .validateInvalidPasswordSteps();

        objValLoginWithInvalidPasswordView
                .verifyLoginError();
    }

    @AfterMethod
    public void tearDownEnv() {

        tearDownWebEnvironment();

        objValLoginWithInvalidPasswordView = null;
    }
}