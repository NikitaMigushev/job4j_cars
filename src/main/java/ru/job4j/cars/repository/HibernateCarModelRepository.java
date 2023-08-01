package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateCarModelRepository implements CarModelRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<CarModel> save(CarModel carModel) {
        try {
            crudRepository.run(session -> session.save(carModel));
            return Optional.of(carModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(CarModel carModel) {
        try {
            crudRepository.run(session -> session.merge(carModel));
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
                    "delete from CarModel where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<CarModel> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT c FROM CarModel c WHERE c.id = :fId",
                    CarModel.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<CarModel> findAll() {
        try {
            return crudRepository.query(
                    "SELECT c FROM CarModel c",
                    CarModel.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<CarModel> findByName(String name) {
        try {
            return crudRepository.optional(
                    "SELECT c FROM CarModel c WHERE c.name = :fName",
                    CarModel.class,
                    Map.of("fName", name)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
