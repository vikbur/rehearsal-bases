package org.vikbur.dao.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.vikbur.db.PostgresUtil;
import org.vikbur.models.User;

import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Component
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO{
    private final PostgresUtil postgresUtil;
    private final static String insertQuery = "insert into users (id, name, login, password, salt, email, phone) values (?, ?, ?, ?, ?, ?, ?)";
    @Override
    public void createUser(User user) {
        int newId = postgresUtil.getJdbcTemplate().queryForObject("select nextval('usr_id_seq')", Integer.class);
        postgresUtil.getJdbcTemplate().update(insertQuery,
                                            newId,
                                            user.getName(),
                                            user.getLogin(),
                                            user.getPassword().getBytes(),
                                            user.getSalt(),
                                            user.getEmail(),
                                            user.getPhone());

        user.setId(newId);
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }
}
