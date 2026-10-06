package com.ShopHubTest;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ShopHubView.ValLoginInvalidEmailAndPassView;
import com.generics.BaseTest;

public class ValLoginInvalidEmailAndPassTest
        extends BaseTest {

    private ValLoginInvalidEmailAndPassView
        objValLoginInvalidEmailAndPassView;

    public void initializeViewsAndPages() {

        objValLoginInvalidEmailAndPassView =
            new ValLoginInvalidEmailAndPassView(this);
    }

    @BeforeMethod
    public void initializeEnvironment() {

        initializeWebEnvironment(
            getProperty("tenantUrl")
        );

        initializeViewsAndPages();
    }

    @Test
    public void validateLoginWithInvalidEmailAndPassword_004() {

        objValLoginInvalidEmailAndPassView
            .validateLoginWithInvalidEmailAndPassword();

        objValLoginInvalidEmailAndPassView
            .verifyLoginError();
    }

    @AfterMethod
    public void tearDownEnv() {

        tearDownWebEnvironment();

        objValLoginInvalidEmailAndPassView = null;
    }
}