package org.vikbur.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "bases")
@Getter
@Setter
public class Base {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="base_id_seq")
    @SequenceGenerator(name="base_id_seq", sequenceName="base_id_seq",allocationSize=1)
    private int id;

    private String name;
    private String description;
    private String email;
    private String phone;
    private String additional_info;
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "base")
    private Set<Room> rooms;

}
