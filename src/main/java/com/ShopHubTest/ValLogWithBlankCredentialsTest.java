package com.ShopHubTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ShopHubView.ValLogWithBlankCredentialsView;
import com.generics.BaseTest;

public class ValLogWithBlankCredentialsTest
        extends BaseTest {

    private ValLogWithBlankCredentialsView
        objValLogWithBlankCredentialsView;

    public void initializeViewsAndPages() {

        objValLogWithBlankCredentialsView =
            new ValLogWithBlankCredentialsView(this);
    }

    @BeforeMethod
    public void initializeEnvironment() {

        initializeWebEnvironment(
            getProperty("tenantUrl")
        );

        initializeViewsAndPages();
    }

    @Test
    public void validateLoginWithBlankCredentials_008() {

        objValLogWithBlankCredentialsView
            .validateLoginWithBlankCredentials();

        objValLogWithBlankCredentialsView
            .verifyEmailRequiredError();
    }

    @AfterMethod
    public void tearDownEnv() {

        tearDownWebEnvironment();

        objValLogWithBlankCredentialsView = null;
    }
}