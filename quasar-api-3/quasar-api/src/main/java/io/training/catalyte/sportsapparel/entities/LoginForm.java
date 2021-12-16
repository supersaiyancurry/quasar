package io.training.catalyte.sportsapparel.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static io.training.catalyte.sportsapparel.constants.StringConstants.INVALID_EMAIL;
import static io.training.catalyte.sportsapparel.constants.StringConstants.REQUIRED_FIELD;

public class LoginForm {

    @Email(message = INVALID_EMAIL)
    @NotBlank(message = "Email" + REQUIRED_FIELD)
    private String email;

    @NotBlank(message = "Password" + REQUIRED_FIELD)
    private String password;

    public LoginForm() {
    }

    public LoginForm(
            @Email(message = INVALID_EMAIL) @NotBlank(message = "Email" + REQUIRED_FIELD) String email,
            @NotBlank(message = "Password" + REQUIRED_FIELD) String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
