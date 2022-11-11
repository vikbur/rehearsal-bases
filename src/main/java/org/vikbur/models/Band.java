package org.vikbur.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "bands")
@Getter
@Setter
public class Band {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="band_id_seq")
    @SequenceGenerator(name="band_id_seq", sequenceName="band_id_seq",allocationSize=1)
    private int id;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "user_in_bands",
            joinColumns = @JoinColumn(name = "band_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members;

    public void addMember(User user) {
        members.add(user);
    }
}
