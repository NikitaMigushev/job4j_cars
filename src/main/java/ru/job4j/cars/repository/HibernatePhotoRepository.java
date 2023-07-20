package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Photo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HibernatePhotoRepository implements PhotoRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Photo> save(Photo photo) {
        try {
            crudRepository.run(session -> session.save(photo));
            return Optional.of(photo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Photo> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT p FROM Photo p WHERE p.id = :fId",
                    Photo.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Photo photo) {
        try {
            crudRepository.run(session -> session.merge(photo));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            crudRepository.run(
                    "delete from Photo where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<Photo> findAll() {
        try {
            return crudRepository.query(
                    "SELECT p FROM Photo p",
                    Photo.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
