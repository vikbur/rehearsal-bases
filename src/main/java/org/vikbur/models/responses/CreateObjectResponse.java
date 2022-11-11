package org.vikbur.models.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateObjectResponse {
    private String message;
    private int id;
}
