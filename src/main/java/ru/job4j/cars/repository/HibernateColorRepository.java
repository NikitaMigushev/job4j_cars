package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Color;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateColorRepository implements ColorRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Color> save(Color color) {
        try {
            crudRepository.run(session -> session.save(color));
            return Optional.of(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Color color) {
        try {
            crudRepository.run(session -> session.merge(color));
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
                    "delete from Color where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
