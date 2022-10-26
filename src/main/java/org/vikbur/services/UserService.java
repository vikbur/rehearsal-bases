package org.vikbur.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.vikbur.dao.user.UserDAO;
import org.vikbur.models.User;
import org.vikbur.utils.HashUtil;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserDAO userDAO;

    public void createUser(@NonNull User user) throws Exception {
        log.info(String.format("Create user attempting, login %s", user.getLogin()));
        validateUser(user);
        user.setSalt(UUID.randomUUID().toString());
        user.setPassword(HashUtil.getHashString(user.getPassword()+user.getSalt()));
        userDAO.createUser(user);
        log.info(String.format("User '%s' created", user.getLogin()));
    }

    private void validateUser(User user) throws Exception {
        if (user.getLogin() == null || user.getLogin().isEmpty()){
            throw new Exception("Login is empty");
        }
        if (userDAO.getUserByLogin(user.getLogin()) != null){
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
