package org.vikbur.models.requests;

import lombok.Getter;
import lombok.Setter;
import org.vikbur.models.Base;
import org.vikbur.models.Room;

@Getter
@Setter
public class CreateRoomRequest {
    private String name;
    private String description;
    private Room.Type type;
    private int base_id;

    public Room toRoom(Base base){
        Room room = new Room();
        room.setName(name);
        room.setDescription(description == null ? "" : description);
        room.setType(type);
        room.setBase(base);
        return room;
    }
}
