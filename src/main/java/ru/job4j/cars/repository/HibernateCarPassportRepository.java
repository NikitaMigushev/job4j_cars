package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarPassport;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class HibernateCarPassportRepository implements CarPassportRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<CarPassport> save(CarPassport carPassport) {
        try {
            crudRepository.run(session -> session.save(carPassport));
            return Optional.of(carPassport);
        } catch (Exception e) {
            logError("Failed to save carPassport: " + carPassport, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(CarPassport carPassport) {
        try {
            crudRepository.run(session -> session.merge(carPassport));
            return true;
        } catch (Exception e) {
            logError("Failed to update carPassport: " + carPassport, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from CarPassport where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            logError("Failed to update deleteById: " + id, e);
        }
        return false;
    }

    @Override
    public Optional<CarPassport> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT c FROM CarPassport c WHERE c.id = :fId",
                    CarPassport.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            logError("Failed to findById: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<CarPassport> findAll() {
        try {
            return crudRepository.query(
                    "SELECT c FROM CarPassport c",
                    CarPassport.class
            );
        } catch (Exception e) {
            logError("Failed to findAll: ", e);
        }
        return Collections.emptyList();
    }
    private void logError(String message, Throwable e) {
        log.error(message, e);
    }
}
