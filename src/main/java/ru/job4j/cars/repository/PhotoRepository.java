package ru.job4j.cars.repository;

import ru.job4j.cars.model.Photo;

import java.util.Collection;
import java.util.Optional;

public interface PhotoRepository {
    Optional<Photo>  save(Photo photo);
    Optional<Photo> findById(int id);
    boolean update(Photo photo);
    boolean deleteById(int id);
    Collection<Photo> findAll();
}
