package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBody;
import ru.job4j.cars.repository.CarBodyRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleCarBodyService implements CarBodyService {
    private final CarBodyRepository carBodyRepository;

    @Override
    public Optional<CarBody> save(CarBody carBody) {
        return carBodyRepository.save(carBody);
    }

    @Override
    public boolean update(CarBody carBody) {
        return carBodyRepository.update(carBody);
    }

    @Override
    public boolean deleteById(int id) {
        return carBodyRepository.deleteById(id);
    }

    @Override
    public Optional<CarBody> findById(int id) {
        return carBodyRepository.findById(id);
    }

    @Override
    public Collection<CarBody> findAll() {
        return carBodyRepository.findAll();
    }
}
