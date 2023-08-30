package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Color;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateColorRepository implements ColorRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateColorRepository.class);
    private final CrudRepository crudRepository;

    @Override
    public Optional<Color> save(Color color) {
        try {
            crudRepository.run(session -> session.save(color));
            return Optional.of(color);
        } catch (Exception e) {
            logError("Failed to save color: " + color, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Color color) {
        try {
            crudRepository.run(session -> session.merge(color));
            return true;
        } catch (Exception e) {
            logError("Failed to update color: " + color, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from Color where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            logError("Failed to deleteById color: " + id, e);
        }
        return false;
    }

    @Override
    public Optional<Color> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT c FROM Color c WHERE c.id = :fId",
                    Color.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            logError("Failed to findById color: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Color> findAll() {
        try {
            return crudRepository.query(
                    "SELECT c FROM Color c",
                    Color.class
            );
        } catch (Exception e) {
            logError("Failed to findAll color: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Color> findByName(String name) {
        try {
            return crudRepository.optional(
                    "SELECT c FROM Color c WHERE c.name = :fName",
                    Color.class,
                    Map.of("fName", name)
            );
        } catch (Exception e) {
            logError("Failed to findByName color: " + name, e);
        }
        return Optional.empty();
    }

    private void logError(String message, Throwable e) {
        LOG.error(message, e);
    }
}
