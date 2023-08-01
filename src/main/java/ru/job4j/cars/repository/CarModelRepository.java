package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarModel;

import java.util.Collection;
import java.util.Optional;

public interface CarModelRepository {
    Optional<CarModel> save(CarModel carModel);
    boolean update(CarModel carModel);
    boolean deleteById(int id);
    Optional<CarModel> findById(int id);
    Collection<CarModel> findAll();

    Optional<CarModel> findByName(String name);
}
