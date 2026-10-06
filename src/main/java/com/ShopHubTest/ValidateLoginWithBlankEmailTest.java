package com.ShopHubTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ShopHubView.ValidateLoginWithBlankEmailView;
import com.generics.BaseTest;

public class ValidateLoginWithBlankEmailTest extends BaseTest {

    private ValidateLoginWithBlankEmailView
        objValidateLoginWithBlankEmailView;

    public void initializeViewsAndPages() {

        objValidateLoginWithBlankEmailView =
            new ValidateLoginWithBlankEmailView(this);
    }

    @BeforeMethod
    public void initializeEnvironment() {

        initializeWebEnvironment(
            getProperty("tenantUrl")
        );

        initializeViewsAndPages();
    }

    @Test
    public void validateLoginWithBlankEmail_005() {

        objValidateLoginWithBlankEmailView
            .validateLoginWithBlankEmail();

        objValidateLoginWithBlankEmailView
            .verifyEmailRequiredError();
    }

    @AfterMethod
    public void tearDownEnv() {

        tearDownWebEnvironment();

        objValidateLoginWithBlankEmailView = null;
    }
}