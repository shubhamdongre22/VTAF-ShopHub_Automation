package com.ShopHubView;

import com.ShopHubPages.ValidateLoginWithBlankEmailPage;
import com.generics.ConfigManager;
import com.generics.Pojo;

public class ValidateLoginWithBlankEmailView extends ConfigManager {

    private ValidateLoginWithBlankEmailPage
        objValidateLoginWithBlankEmailPage;

    public ValidateLoginWithBlankEmailView(Pojo pojo) {

        super(pojo);

        objValidateLoginWithBlankEmailPage =
            new ValidateLoginWithBlankEmailPage(pojo);
    }

    public void validateLoginWithBlankEmail() {

        String password = dpString("Passkey");

        objValidateLoginWithBlankEmailPage
            .loginWithBlankEmail(password);
    }

    public void verifyEmailRequiredError() {

        String expectedError = dpString("EmailRequiredError");

        objValidateLoginWithBlankEmailPage
            .verifyEmailRequiredError(expectedError);
    }
}