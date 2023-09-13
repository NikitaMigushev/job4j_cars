package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class HibernateEngineRepository implements EngineRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Engine> save(Engine engine) {
        try {
            crudRepository.run(session -> session.save(engine));
            return Optional.of(engine);
        } catch (Exception e) {
            logError("Failed to save engine: " + engine, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Engine engine) {
        try {
            crudRepository.run(session -> session.merge(engine));
            return true;
        } catch (Exception e) {
            logError("Failed to update engine: " + engine, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from Engine where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            logError("Failed to delelteById engine: " + id, e);
        }
        return false;
    }

    @Override
    public Optional<Engine> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT e FROM Engine e WHERE e.id = :fId",
                    Engine.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            logError("Failed to findById engine: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Engine> findAll() {
        try {
            return crudRepository.query(
                    "SELECT e FROM Engine e",
                    Engine.class
            );
        } catch (Exception e) {
            logError("Failed to findAll: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Engine> findByName(String name) {
        try {
            return crudRepository.optional(
                    "SELECT e FROM Engine e WHERE e.name = :fName",
                    Engine.class,
                    Map.of("fName", name)
            );
        } catch (Exception e) {
            logError("Failed to findByName: " +  name, e);
        }
        return Optional.empty();
    }

    private void logError(String message, Throwable e) {
        log.error(message, e);
    }

}
