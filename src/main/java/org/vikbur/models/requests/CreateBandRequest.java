package org.vikbur.models.requests;

import lombok.Getter;
import lombok.Setter;
import org.vikbur.models.Band;

import java.util.HashSet;

@Setter
@Getter
public class CreateBandRequest {
    private String name;
    private String description;

    public Band toBand(){
        Band band = new Band();
        band.setName(name);
        band.setDescription(description == null ? "" : description);
        band.setMembers(new HashSet<>());
        return band;
    }
}
