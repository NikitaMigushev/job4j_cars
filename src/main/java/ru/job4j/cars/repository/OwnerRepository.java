package ru.job4j.cars.repository;

import ru.job4j.cars.model.Owner;

import java.util.Collection;
import java.util.Optional;

public interface OwnerRepository {
    Optional<Owner> save(Owner owner);
    boolean update(Owner owner);
    boolean deleteById(int ownerId);
    Optional<Owner> findById(int ownerId);
    Collection<Owner> findAll();
}
