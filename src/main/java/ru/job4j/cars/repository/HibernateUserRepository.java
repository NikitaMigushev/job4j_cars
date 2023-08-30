package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;

import java.util.*;

@AllArgsConstructor
@Repository
public class HibernateUserRepository implements UserRepository {
    private final CrudRepository crudRepository;

    private static final Logger LOG = LoggerFactory.getLogger(HibernateBrandRepository.class);

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    @Override
    public Optional<User> create(User user) {
        try {
            crudRepository.run(session -> session.save(user));
            return Optional.of(user);
        } catch (Exception e) {
            logError("Failed to save user: " + user, e);
        }
        return Optional.empty();
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    @Override
    public boolean update(User user) {
        try {
            crudRepository.run(session -> session.merge(user));
            return true;
        } catch (Exception e) {
            logError("Failed to update user: " + user, e);
        }
        return false;
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    @Override
    public boolean delete(int userId) {
        try {
            crudRepository.run("DELETE FROM User WHERE id = :userId",
                    Map.of("userId", userId));
            return true;
        } catch (Exception e) {
            logError("Failed to deleteById user: " + userId, e);
        }
        return false;
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    @Override
    public List<User> findAllOrderById() {
        try {
            return crudRepository.query("from User order by id asc", User.class);
        } catch (Exception e) {
            logError("Failed to findAllOrderById: ", e);
        }
        return Collections.emptyList();
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    @Override
    public Optional<User> findById(int userId) {
        try {
            return crudRepository.optional(
                    "from User where id = :fId", User.class,
                    Map.of("fId", userId)
            );
        } catch (Exception e) {
            logError("Failed to findById: " + userId, e);
        }
        return Optional.empty();
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    @Override
    public List<User> findByLikeLogin(String key) {
        try {
            return crudRepository.query(
                    "from User u where u.fullName like :fKey", User.class,
                    Map.of("fKey", "%" + key + "%")
            );
        } catch (Exception e) {
            logError("Failed to findByLikeLogin: " + key, e);
        }
        return Collections.emptyList();
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    @Override
    public Optional<User> findByLogin(String login) {
        try {
            return crudRepository.optional(
                    "from User u where u.fullName = :fLogin", User.class,
                    Map.of("fLogin", login)
            );
        } catch (Exception e) {
            logError("Failed to findByLogin: " + login, e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return crudRepository.optional(
                    "from User u where u.email = :fEmail", User.class,
                    Map.of("fEmail", email)
            );
        } catch (Exception e) {
            logError("Failed to findByEmail: " + email, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> findAll() {
        try {
            return crudRepository.query("FROM User", User.class);
        } catch (Exception e) {
            logError("Failed to findAll user: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try {
            return crudRepository.optional(
                    "FROM User u WHERE u.email = :fEmail AND u.password = :fPassword",
                    User.class,
                    Map.of("fEmail", email, "fPassword", password)
            );
        } catch (Exception e) {
            logError("Failed to findByEmailAndPassword user: " + email + " " + password, e);
        }
        return Optional.empty();
    }

    private void logError(String message, Throwable e) {
        LOG.error(message, e);
    }

}
