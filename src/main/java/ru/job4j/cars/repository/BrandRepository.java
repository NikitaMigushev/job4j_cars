package ru.job4j.cars.repository;

import ru.job4j.cars.model.Brand;

import java.util.Collection;
import java.util.Optional;

public interface BrandRepository {
    Optional<Brand> save(Brand brand);
    boolean update(Brand car);
    boolean deleteById(int id);
    Optional<Brand> findById(int id);
    Collection<Brand> findAll();
}
