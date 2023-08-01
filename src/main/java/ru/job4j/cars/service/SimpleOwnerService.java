package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.repository.OwnerRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleOwnerService implements OwnerService {
    private final OwnerRepository ownerRepository;

    @Override
    public Optional<Owner> save(Owner owner) {
        Optional<Owner> result;
        var foundOwner = ownerRepository.findByPassport(owner.getPassport());
        if (foundOwner.isPresent()) {
            result = foundOwner;
        } else {
            result = ownerRepository.save(owner);
        }
        return result;
    }

    @Override
    public boolean update(Owner owner) {
        return ownerRepository.update(owner);
    }

    @Override
    public boolean deleteById(int ownerId) {
        return ownerRepository.deleteById(ownerId);
    }

    @Override
    public Optional<Owner> findById(int ownerId) {
        return ownerRepository.findById(ownerId);
    }

    @Override
    public Collection<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    public Optional<Owner> findByPassport(String passport) {
        return ownerRepository.findByPassport(passport);
    }
}
