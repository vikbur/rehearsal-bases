package org.vikbur.models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.vikbur.models.User;

@Setter
@Getter
public class CreateUserRequest {
    private String name;
    private String login;
    private String password;
    private String email;
    private String phone;

    public User toUser() {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }
}
