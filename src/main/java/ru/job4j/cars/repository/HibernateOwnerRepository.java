package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class HibernateOwnerRepository implements OwnerRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<Owner> save(Owner owner) {
        try {
            crudRepository.run(session -> session.save(owner));
            return Optional.of(owner);
        } catch (Exception e) {
            logError("Failed to save owner: " + owner, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Owner owner) {
        try {
            crudRepository.run(session -> session.merge(owner));
            return true;
        } catch (Exception e) {
            logError("Failed to update owner: " + owner, e);
        }
        return false;
    }

    @Override
    public boolean deleteById(int ownerId) {
        try {
            crudRepository.run(
                    "delete from Owner where id = :fId",
                    Map.of("fId", ownerId)
            );
            return true;
        } catch (Exception e) {
            logError("Failed to deleteById owner: " + ownerId, e);
        }
        return false;
    }

    @Override
    public Optional<Owner> findById(int id) {
        try {
            return crudRepository.optional(
                    "SELECT o FROM Owner o WHERE o.id = :fId",
                    Owner.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            logError("Failed to findById owner: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Owner> findAll() {
        try {
            return crudRepository.query(
                    "SELECT o FROM Owner o",
                    Owner.class
            );
        } catch (Exception e) {
            logError("Failed to findAll owner: ", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Owner> findByPassport(String passport) {
        try {
            return crudRepository.optional(
                    "SELECT o FROM Owner o WHERE o.passport = :fPassport",
                    Owner.class,
                    Map.of("fPassport", passport)
            );
        } catch (Exception e) {
            logError("Failed to findByPassport owner: " + passport, e);
        }
        return Optional.empty();
    }

    private void logError(String message, Throwable e) {
        log.error(message, e);
    }

}
