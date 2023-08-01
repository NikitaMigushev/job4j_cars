package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarPassport;

import java.util.Collection;
import java.util.Optional;

public interface CarPassportRepository {
    Optional<CarPassport> save(CarPassport carPassport);
    boolean update(CarPassport carPassport);
    boolean deleteById(int id);
    Optional<CarPassport> findById(int id);
    Collection<CarPassport> findAll();
}
