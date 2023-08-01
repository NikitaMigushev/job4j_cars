package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarPassport;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateCarPassportRepository implements CarPassportRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<CarPassport> save(CarPassport carPassport) {
        try {
            crudRepository.run(session -> session.save(carPassport));
            return Optional.of(carPassport);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(CarPassport carPassport) {
        try {
            crudRepository.run(session -> session.merge(carPassport));
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
                    "delete from CarPassport where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
