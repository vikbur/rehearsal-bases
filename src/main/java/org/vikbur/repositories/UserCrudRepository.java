package org.vikbur.repositories;

import org.springframework.data.repository.CrudRepository;
import org.vikbur.models.User;

import java.util.Optional;

public interface UserCrudRepository extends CrudRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}
