package ru.job4j.cars.service;

import ru.job4j.cars.model.CarBody;

import java.util.Collection;
import java.util.Optional;

public interface CarBodyService {
    Optional<CarBody> save(CarBody carBody);
    boolean update(CarBody carBody);
    boolean deleteById(int id);
    Optional<CarBody> findById(int id);
    Collection<CarBody> findAll();
}
