package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBody;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateCarBodyRepository implements CarBodyRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<CarBody> save(CarBody carBody) {
        try {
            crudRepository.run(session -> session.save(carBody));
            return Optional.of(carBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(CarBody carBody) {
        try {
            crudRepository.run(session -> session.merge(carBody));
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
                    "delete from CarBody where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
