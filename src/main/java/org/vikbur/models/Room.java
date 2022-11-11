package org.vikbur.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "rooms")
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="room_id_seq")
    @SequenceGenerator(name="room_id_seq", sequenceName="room_id_seq",allocationSize=1)
    private int id;

    private String name;
    private String description;
    private Type type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "base_id", referencedColumnName = "id")
    private Base base;

    public enum Type {
        REHEARSAL_ROOM(1),
        RECORDS_STUDIO(2),
        REHEARSAL_AND_RECORDS(3),
        MINI(4);

        private int value;

        Type(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}

