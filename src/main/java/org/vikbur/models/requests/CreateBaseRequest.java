package org.vikbur.models.requests;

import lombok.Getter;
import lombok.Setter;
import org.vikbur.models.Base;

import java.util.HashSet;

@Setter
@Getter
public class CreateBaseRequest {
    private String name;
    private String description;
    private String email;
    private String phone;
    private String additional_info;
    private String address;

    public Base toBase(){
        Base base = new Base();
        base.setName(name);
        base.setDescription(description == null ? "" : description);
        base.setAddress(address);
        base.setEmail(email);
        base.setPhone(phone);
        base.setAdditional_info(additional_info == null ? "" : additional_info);
        base.setRooms(new HashSet<>());
        return base;
    }
}
