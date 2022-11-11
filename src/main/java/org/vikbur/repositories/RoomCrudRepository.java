package org.vikbur.repositories;

import org.springframework.data.repository.CrudRepository;
import org.vikbur.models.Base;
import org.vikbur.models.Room;

import java.util.Optional;

public interface RoomCrudRepository extends CrudRepository<Room, Integer> {
    Optional<Room> findByNameAndBase(String name, Base base);
}
