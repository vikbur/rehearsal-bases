package org.vikbur.services;

import com.google.gson.Gson;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vikbur.models.Role;
import org.vikbur.models.User;
import org.vikbur.models.requests.CreateUserRequest;
import org.vikbur.repositories.UserCrudRepository;
import org.vikbur.utils.HashUtil;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserCrudRepository userCrudRepository;
    private final Gson gson = new Gson();

    public String createUser(@NonNull CreateUserRequest userRequest) throws Exception {
        log.info(String.format("Create user attempting, login %s", userRequest.getLogin()));

        validateUser(userRequest);

        User user = userRequest.toUser();
        user.setSalt(UUID.randomUUID().toString());
        user.setPassword(HashUtil.getHashString(user.getPassword()+user.getSalt()));
        user.setRoles(Arrays.asList(Role.USER));

        userCrudRepository.save(user);

        log.info(String.format("User '%s' created", user.getLogin()));

        return gson.toJson("OK");
    }

    public String getUserById(int id) throws SQLException, IllegalArgumentException {
        User user = userCrudRepository.findById(id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException(String.format("User with id '%s' not found", id));
        }
        return gson.toJson(prepareUser(user));
    }

    public String getUserByLogin(String login) throws SQLException, IllegalArgumentException {
        User user = userCrudRepository.findByLogin(login).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException(String.format("User with login '%s' not found", login));
        }
        return gson.toJson(prepareUser(user));
    }
    private User prepareUser(User user) {
        user.setPassword("");
        user.setSalt("");
        user.setBands(user.getBands().stream().peek(b -> b.setMembers(new HashSet<>())).collect(Collectors.toSet()));
        return user;
    }
    private void validateUser(CreateUserRequest user) throws Exception {
        if (user.getLogin() == null || user.getLogin().isEmpty()){
            throw new Exception("Login is empty");
        }
        if (userCrudRepository.findByLogin(user.getLogin()).orElse(null) != null){
            throw new Exception(String.format("Login '%s' already busy", user.getLogin()));
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            throw new Exception("Password is empty");
        }
        if (user.getName() == null || user.getName().isEmpty()){
            throw new Exception("Name is empty");
        }
        //TODO validate email and phone via regExp
    }
}
