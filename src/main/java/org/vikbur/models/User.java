package org.vikbur.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private int id;
    private String name;
    private String login;
    private String password;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String salt;
    private String email;
    private String phone;
}
