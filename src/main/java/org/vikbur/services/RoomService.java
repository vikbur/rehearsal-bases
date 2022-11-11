package org.vikbur.services;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vikbur.models.Base;
import org.vikbur.models.Room;
import org.vikbur.models.requests.CreateRoomRequest;
import org.vikbur.models.responses.CreateObjectResponse;
import org.vikbur.repositories.BaseCrudRepository;
import org.vikbur.repositories.RoomCrudRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomService {
    private final RoomCrudRepository roomCrudRepository;
    private final BaseCrudRepository baseCrudRepository;
    private final Gson gson = new Gson();

    public String createRoom(CreateRoomRequest request, String userLogin) throws Exception {
        log.info(String.format("Create room '%s' attempting by '%s'", request.getName(), userLogin));
        Base base = validateRoom(request);
        Room room = request.toRoom(base);
        Room savedRoom = roomCrudRepository.save(room);
        return gson.toJson(new CreateObjectResponse(
                String.format("Room '%s' in base '%s' created", savedRoom.getName(), base.getName()),
                savedRoom.getId())
        );
    }

    private Base validateRoom(CreateRoomRequest request) throws IllegalArgumentException {
        if (request.getName() == null || request.getName().isEmpty()){
            throw new IllegalArgumentException("Name is empty");
        }
        if (request.getBase_id() == 0){
            throw new IllegalArgumentException("Base ID is empty");
        }

        Base base = baseCrudRepository.findById(request.getBase_id()).orElse(null);
        if (base == null){
            throw new IllegalArgumentException(String.format("Base with id '%d' not found", request.getBase_id()));
        }
        return base;
    }
}
