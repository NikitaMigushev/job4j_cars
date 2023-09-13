package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class HibernateTransmissionRepository implements TransmissionRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Transmission> save(Transmission transmission) {
        try {
            crudRepository.run(session -> session.save(transmission));
            return Optional.of(transmission);
        } catch (Exception e) {
            logError("Failed to save transmission: " + transmission, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Transmission transmission) {
        try {
            crudRepository.run(session -> session.merge(transmission));
            return true;
        } catch (Exception e) {
            logError("Failed to update transmission: " + transmission, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from Transmission where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            logError("Failed to deleteById: " + id, e);
        }
        return false;
    }

    @Override
    public Optional<Transmission> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT t FROM Transmission t WHERE t.id = :fId",
                    Transmission.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            logError("Failed to findById: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Transmission> findAll() {
        try {
            return crudRepository.query(
                    "SELECT t FROM Transmission t",
                    Transmission.class
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
