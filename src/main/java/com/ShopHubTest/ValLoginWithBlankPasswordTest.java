package com.ShopHubTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ShopHubView.ValLoginWithBlankPasswordView;
import com.generics.BaseTest;

public class ValLoginWithBlankPasswordTest
        extends BaseTest {

    private ValLoginWithBlankPasswordView
        objValLoginWithBlankPasswordView;

    public void initializeViewsAndPages() {

        objValLoginWithBlankPasswordView =
            new ValLoginWithBlankPasswordView(this);
    }

    @BeforeMethod
    public void initializeEnvironment() {

        initializeWebEnvironment(
            getProperty("tenantUrl")
        );

        initializeViewsAndPages();
    }

    @Test
    public void validateLoginWithBlankPassword_006() {

        objValLoginWithBlankPasswordView
            .validateLoginWithBlankPassword();

        objValLoginWithBlankPasswordView
            .verifyPasswordRequiredError();
    }

    @AfterMethod
    public void tearDownEnv() {

        tearDownWebEnvironment();

        objValLoginWithBlankPasswordView = null;
    }
}