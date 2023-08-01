package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Transmission;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateTransmissionRepository implements TransmissionRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Transmission> save(Transmission transmission) {
        try {
            crudRepository.run(session -> session.save(transmission));
            return Optional.of(transmission);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Transmission transmission) {
        try {
            crudRepository.run(session -> session.merge(transmission));
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
                    "delete from Transmission where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
