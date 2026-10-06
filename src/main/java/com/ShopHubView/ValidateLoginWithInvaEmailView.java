package com.ShopHubView;

import com.ShopHubPages.ValidateLoginWithInvalidEmailPage;
import com.generics.ConfigManager;
import com.generics.Pojo;

public class ValidateLoginWithInvaEmailView extends ConfigManager {

    private ValidateLoginWithInvalidEmailPage objValidateLoginWithInvalidEmailPage;

    public  ValidateLoginWithInvaEmailView(Pojo pojo) {

        super(pojo);

        objValidateLoginWithInvalidEmailPage =
            new ValidateLoginWithInvalidEmailPage(pojo);
    }

    public void validateLoginWithInvalidEmail() {

        String userName = dpString("id");
        String password = dpString("Passkey");

        objValidateLoginWithInvalidEmailPage
            .loginWithInvalidEmail(userName, password);
    }

    public void verifyInvalidEmailError() {

        String expectedError = dpString("EmailError");

        objValidateLoginWithInvalidEmailPage
            .verifyInvalidEmailError(expectedError);
    }
}