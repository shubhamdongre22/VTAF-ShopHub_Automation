package com.ShopHubView;

import com.ShopHubPages.ValidateLoginWithInvalidEmailAndPasswordPage;
import com.generics.ConfigManager;
import com.generics.Pojo;

public class ValLoginInvalidEmailAndPassView
        extends ConfigManager {

    private ValidateLoginWithInvalidEmailAndPasswordPage
        objValidateLoginWithInvalidEmailAndPasswordPage;

    public ValLoginInvalidEmailAndPassView(Pojo pojo) {

        super(pojo);

        objValidateLoginWithInvalidEmailAndPasswordPage =
            new ValidateLoginWithInvalidEmailAndPasswordPage(pojo);
    }

    public void validateLoginWithInvalidEmailAndPassword() {

        String username = dpString("id");
        String password = dpString("Passkey");

        objValidateLoginWithInvalidEmailAndPasswordPage
            .loginWithInvalidEmailAndPassword(
                username,
                password
            );
    }

    public void verifyLoginError() {

        String expectedError = dpString("loginError");

        objValidateLoginWithInvalidEmailAndPasswordPage
            .verifyLoginError(expectedError);
    }
}