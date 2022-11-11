package org.vikbur.repositories;

import org.springframework.data.repository.CrudRepository;
import org.vikbur.models.Base;

import java.util.Optional;

public interface BaseCrudRepository extends CrudRepository<Base, Integer> {
    Optional<Base> findByName(String name);
}
