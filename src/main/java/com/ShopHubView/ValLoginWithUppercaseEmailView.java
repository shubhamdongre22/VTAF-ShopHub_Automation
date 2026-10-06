package com.ShopHubView;

import com.ShopHubPages.ValidateLoginWithUppercaseEmailPage;
import com.generics.ConfigManager;
import com.generics.Pojo;

public class ValLoginWithUppercaseEmailView extends ConfigManager {

    private ValidateLoginWithUppercaseEmailPage
        objValidateLoginWithUppercaseEmailPage;

    public ValLoginWithUppercaseEmailView(Pojo pojo) {

        super(pojo);

        objValidateLoginWithUppercaseEmailPage =
            new ValidateLoginWithUppercaseEmailPage(pojo);
    }

    public void validateLoginWithUppercaseEmail() {

        String username = dpString("id");
        String password = dpString("Passkey");

        objValidateLoginWithUppercaseEmailPage
            .loginWithUppercaseEmail(
                username,
                password
            );
    }

    public void verifyEmailValidationError() {

        String expectedError = dpString("EmailError");

        objValidateLoginWithUppercaseEmailPage
            .verifyEmailValidationError(
                expectedError
            );
    }
}