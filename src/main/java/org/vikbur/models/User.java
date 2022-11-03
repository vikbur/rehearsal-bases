package org.vikbur.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="usr_id_seq")
    @SequenceGenerator(name="usr_id_seq", sequenceName="usr_id_seq",allocationSize=1)
    private int id;

    private String name;
    private String login;
    private String password;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String salt;
    private String email;
    private String phone;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Collection<Role> roles;

    @ManyToMany
    @JoinTable(
            name = "user_in_bands",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "band_id"))
    private Set<Band> bands;
}
