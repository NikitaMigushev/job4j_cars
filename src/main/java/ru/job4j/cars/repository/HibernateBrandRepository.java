package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Brand;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class HibernateBrandRepository implements BrandRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<Brand> save(Brand brand) {
        try {
            crudRepository.run(session -> session.save(brand));
            return Optional.of(brand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Brand brand) {
        try {
            crudRepository.run(session -> session.merge(brand));
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
                    "delete from Brand where id = :fId",
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Optional<Brand> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT b FROM Brand b WHERE b.id = :fId",
                    Brand.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Collection<Brand> findAll() {
        try {
            return crudRepository.query(
                    "SELECT b FROM Brand b",
                    Brand.class
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
