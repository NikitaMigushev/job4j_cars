package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.repository.CarModelRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleCarModelService implements CarModelService {
    private final CarModelRepository carModelRepository;

    @Override
    public Optional<CarModel> save(CarModel carModel) {
        Optional<CarModel> result;
        var foundCarModel = carModelRepository.findByName(carModel.getName());
        if (foundCarModel.isPresent()) {
            result = foundCarModel;
        } else {
            result = carModelRepository.save(carModel);
        }
        return result;
    }

    @Override
    public boolean update(CarModel carModel) {
        return carModelRepository.update(carModel);
    }

    @Override
    public boolean deleteById(int id) {
        return carModelRepository.deleteById(id);
    }

    @Override
    public Optional<CarModel> findById(int id) {
        return carModelRepository.findById(id);
    }

    @Override
    public Collection<CarModel> findAll() {
        return carModelRepository.findAll();
    }

    @Override
    public Optional<CarModel> findByName(String name) {
        return carModelRepository.findByName(name);
    }
}
