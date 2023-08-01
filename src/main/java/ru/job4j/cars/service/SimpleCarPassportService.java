package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarPassport;
import ru.job4j.cars.repository.CarPassportRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleCarPassportService implements CarPassportService {
    private final CarPassportRepository carPassportRepository;

    @Override
    public Optional<CarPassport> save(CarPassport carPassport) {
        return carPassportRepository.save(carPassport);
    }

    @Override
    public boolean update(CarPassport carPassport) {
        return carPassportRepository.update(carPassport);
    }

    @Override
    public boolean deleteById(int id) {
        return carPassportRepository.deleteById(id);
    }

    @Override
    public Optional<CarPassport> findById(int id) {
        return carPassportRepository.findById(id);
    }

    @Override
    public Collection<CarPassport> findAll() {
        return carPassportRepository.findAll();
    }
}
