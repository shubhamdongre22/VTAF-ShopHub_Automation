package com.ShopHubView;

import com.ShopHubPages.ValidateLoginWithBlankPasswordPage;
import com.generics.ConfigManager;
import com.generics.Pojo;

public class ValLoginWithBlankPasswordView
        extends ConfigManager {

    private ValidateLoginWithBlankPasswordPage
        objValidateLoginWithBlankPasswordPage;

    public ValLoginWithBlankPasswordView(Pojo pojo) {

        super(pojo);

        objValidateLoginWithBlankPasswordPage =
            new ValidateLoginWithBlankPasswordPage(pojo);
    }

    public void validateLoginWithBlankPassword() {

        String username = dpString("id");

        objValidateLoginWithBlankPasswordPage
            .loginWithBlankPassword(username);
    }

    public void verifyPasswordRequiredError() {

        String expectedError = dpString("PasswordRequiredError");

        objValidateLoginWithBlankPasswordPage
            .verifyPasswordRequiredError(expectedError);
    }
}