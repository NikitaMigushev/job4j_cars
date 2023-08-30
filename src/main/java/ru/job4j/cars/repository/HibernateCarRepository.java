package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateCarRepository implements CarRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HibernateCarRepository.class);
    private final CrudRepository crudRepository;

    @Override
    public Optional<Car> save(Car car) {
        try {
            crudRepository.run(session -> session.save(car));
            return Optional.of(car);
        } catch (Exception e) {
            logError("Failed to save car: " + car, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Car car) {
        try {
            crudRepository.run(session -> session.merge(car));
            return true;
        } catch (Exception e) {
            logError("Failed to save update: " + car, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from Car where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            logError("Failed to save deleteById: " + id, e);
        }
        return false;
    }

    @Override
    public Optional<Car> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT c FROM Car c WHERE c.id = :fId",
                    Car.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            logError("Failed to findById: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Car> findAll() {
        try {
            return crudRepository.query(
                    "SELECT c FROM Car c",
                    Car.class
            );
        } catch (Exception e) {
            logError("Failed to findAll: ", e);
        }
        return Collections.emptyList();
    }

    private void logError(String message, Throwable e) {
        LOG.error(message, e);
    }

}
