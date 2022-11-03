package org.vikbur.repositories;

import org.springframework.data.repository.CrudRepository;
import org.vikbur.models.Band;

import java.util.Optional;

public interface BandCrudRepository extends CrudRepository<Band, Integer> {
    Optional<Band> findByName(String name);
}
