package org.vikbur.dao.user;

import org.vikbur.models.User;

public interface UserDAO {
    /**
     * Создание нового пользователя в БД
     * @param user - объект пользователя для создания
     */
    void createUser(User user);

    /**
     * Обновление пользователя в БД
     * @param user - объект пользователя для обновления
     */
    void updateUser(User user);

    /**
     * Получение пользователя по ID
     * @param id - идентификтор пользователя
     * @return пользователь из БД
     */
    User getUserById(String id);

    /**
     * Получение пользователя по логину
     * @param login - идентификтор пользователя
     * @return пользователь из БД
     */
    User getUserByLogin(String login);
}
