package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateEngineRepository implements EngineRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Engine> save(Engine engine) {
        try {
            crudRepository.run(session -> session.save(engine));
            return Optional.of(engine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Engine engine) {
        try {
            crudRepository.run(session -> session.merge(engine));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
