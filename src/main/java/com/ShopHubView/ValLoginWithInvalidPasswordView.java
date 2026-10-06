package com.ShopHubView;

import com.ShopHubPages.ValidateLoginWithInvalidPasswordPage;
import com.generics.ConfigManager;
import com.generics.Pojo;

public class ValLoginWithInvalidPasswordView extends ConfigManager {

    private ValidateLoginWithInvalidPasswordPage objValidateLoginWithInvalidPasswordPage;

    public ValLoginWithInvalidPasswordView(Pojo pojo) {
        super(pojo);
        objValidateLoginWithInvalidPasswordPage =
                new ValidateLoginWithInvalidPasswordPage(pojo);
    }

    public void validateInvalidPasswordSteps() {

        String username = dpString("id");
        String password = dpString("Passkey");

        objValidateLoginWithInvalidPasswordPage.loginCredentials(
                username,
                password
        );
    }

    public void verifyLoginError() {

        String expectedError = dpString("loginError");

        objValidateLoginWithInvalidPasswordPage
                .verifyLoginError(expectedError);
    }
}