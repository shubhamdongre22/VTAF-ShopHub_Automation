package com.ShopHubTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ShopHubView.ValLoginWithUppercaseEmailView;
import com.generics.BaseTest;

public class ValLoginWithUppercaseEmailTest
        extends BaseTest {

    private ValLoginWithUppercaseEmailView
        objValLoginWithUppercaseEmailView;

    public void initializeViewsAndPages() {

        objValLoginWithUppercaseEmailView =
            new ValLoginWithUppercaseEmailView(this);
    }

    @BeforeMethod
    public void initializeEnvironment() {

        initializeWebEnvironment(
            getProperty("tenantUrl")
        );

        initializeViewsAndPages();
    }

    @Test
    public void validateLoginWithUppercaseEmail_007() {

        objValLoginWithUppercaseEmailView
            .validateLoginWithUppercaseEmail();

        objValLoginWithUppercaseEmailView
            .verifyEmailValidationError();
    }

    @AfterMethod
    public void tearDownEnv() {

        tearDownWebEnvironment();

        objValLoginWithUppercaseEmailView = null;
    }
}