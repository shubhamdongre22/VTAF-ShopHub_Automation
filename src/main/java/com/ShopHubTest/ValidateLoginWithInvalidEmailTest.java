package com.ShopHubTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ShopHubView. ValidateLoginWithInvaEmailView;
import com.generics.BaseTest;

public class ValidateLoginWithInvalidEmailTest extends BaseTest {

    private  ValidateLoginWithInvaEmailView objValidateLoginWithInvalidEmailView;

    public void initializeViewsAndPages() {

        objValidateLoginWithInvalidEmailView =
            new  ValidateLoginWithInvaEmailView(this);
    }

    @BeforeMethod
    public void initializeEnvironment() {

        initializeWebEnvironment(
            getProperty("tenantUrl")
        );

        initializeViewsAndPages();
    }

    @Test
    public void validateLoginWithInvalidEmail_002() {

        objValidateLoginWithInvalidEmailView
            .validateLoginWithInvalidEmail();

        objValidateLoginWithInvalidEmailView
            .verifyInvalidEmailError();
    }

    @AfterMethod
    public void tearDownEnv() {

        tearDownWebEnvironment();

        objValidateLoginWithInvalidEmailView = null;
    }
}