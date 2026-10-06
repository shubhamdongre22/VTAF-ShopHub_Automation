package com.ShopHubView;

import com.ShopHubPages.ValidateSuccessfulLoginPage;
import com.generics.ConfigManager;
import com.generics.Pojo;

public class ValidateSuccessfulLoginView extends ConfigManager {

    private ValidateSuccessfulLoginPage objValidateSuccessfulLoginPage;

    public ValidateSuccessfulLoginView(Pojo pojo) {
        super(pojo);
        objValidateSuccessfulLoginPage =
                new ValidateSuccessfulLoginPage(pojo);
    }

    public void validateSuccessfulLoginSteps() {

        String username = dpString("id");
        String password = dpString("Passkey");

        objValidateSuccessfulLoginPage.loginCredentials(
                username,
                password
        );
    }

    public void verifyLoginSuccessful() {

        objValidateSuccessfulLoginPage.verifySuccessfulLogin();
    }
}