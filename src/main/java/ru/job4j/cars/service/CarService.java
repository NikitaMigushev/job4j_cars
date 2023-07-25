package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.Collection;
import java.util.Optional;

public interface CarService {
    Optional<Car> save(Car car);
    boolean update(Car car);
    boolean deleteById(int id);
    Optional<Car> findById(int id);
    Collection<Car> findAll();
}
