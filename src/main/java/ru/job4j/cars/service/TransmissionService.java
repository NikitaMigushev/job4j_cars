package ru.job4j.cars.service;

import ru.job4j.cars.model.Transmission;

import java.util.Collection;
import java.util.Optional;

public interface TransmissionService {
    Optional<Transmission> save(Transmission transmission);
    boolean update(Transmission transmission);
    boolean deleteById(int id);
    Optional<Transmission> findById(int id);
    Collection<Transmission> findAll();
}
