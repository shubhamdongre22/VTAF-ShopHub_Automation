package com.ShopHubView;

import com.ShopHubPages.ValidateLoginWithBlankCredentialsPage;
import com.generics.ConfigManager;
import com.generics.Pojo;

public class ValLogWithBlankCredentialsView
        extends ConfigManager {

    private ValidateLoginWithBlankCredentialsPage
        objValidateLoginWithBlankCredentialsPage;

    public ValLogWithBlankCredentialsView(Pojo pojo) {

        super(pojo);

        objValidateLoginWithBlankCredentialsPage =
            new ValidateLoginWithBlankCredentialsPage(pojo);
    }

    public void validateLoginWithBlankCredentials() {

        objValidateLoginWithBlankCredentialsPage
            .loginWithBothFieldsBlank();
    }

    public void verifyEmailRequiredError() {

        String expectedError = dpString("EmailRequiredError");

        objValidateLoginWithBlankCredentialsPage
            .verifyEmailRequiredError(expectedError);
    }
}