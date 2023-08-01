package ru.job4j.cars.repository;

import ru.job4j.cars.model.Color;

import java.util.Collection;
import java.util.Optional;

public interface ColorRepository {
    Optional<Color> save(Color color);
    boolean update(Color color);
    boolean deleteById(int id);
    Optional<Color> findById(int id);
    Collection<Color> findAll();
    Optional<Color> findByName(String name);
}
