package ru.job4j.cars.repository;

import ru.job4j.cars.model.Engine;

import java.util.Collection;
import java.util.Optional;

public interface EngineRepository {
    Optional<Engine> save(Engine engine);
    boolean update(Engine engine);
    boolean deleteById(int id);
    Optional<Engine> findById(int id);
    Collection<Engine> findAll();
}
