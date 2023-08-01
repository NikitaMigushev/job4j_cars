package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Color;
import ru.job4j.cars.repository.ColorRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleColorService implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public Optional<Color> save(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public boolean update(Color color) {
        return colorRepository.update(color);
    }

    @Override
    public boolean deleteById(int id) {
        return colorRepository.deleteById(id);
    }

    @Override
    public Optional<Color> findById(int id) {
        return colorRepository.findById(id);
    }

    @Override
    public Collection<Color> findAll() {
        return colorRepository.findAll();
    }

    @Override
    public Optional<Color> findByName(String name) {
        return colorRepository.findByName(name);
    }
}
