package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBody;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class HibernateCarBodyRepository implements CarBodyRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<CarBody> save(CarBody carBody) {
        try {
            crudRepository.run(session -> session.save(carBody));
            return Optional.of(carBody);
        } catch (Exception e) {
            logError("Failed to save carBody: " + carBody, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(CarBody carBody) {
        try {
            crudRepository.run(session -> session.merge(carBody));
            return true;
        } catch (Exception e) {
            logError("Failed to update carBody: " + carBody, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from CarBody where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            logError("Failed to deleteById carBody: " + id, e);
        }
        return false;
    }

    @Override
    public Optional<CarBody> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT c FROM CarBody c WHERE c.id = :fId",
                    CarBody.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            logError("Failed to findById carBody: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<CarBody> findAll() {
        try {
            return crudRepository.query(
                    "SELECT c FROM CarBody c",
                    CarBody.class
            );
        } catch (Exception e) {
            logError("Failed to findAll carBody: ", e);
        }
        return Collections.emptyList();
    }

    private void logError(String message, Throwable e) {
        log.error(message, e);
    }
}
