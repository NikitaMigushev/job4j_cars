package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.EngineRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleEngineService implements EngineService {
    private final EngineRepository engineRepository;

    @Override
    public Optional<Engine> save(Engine engine) {
        Optional<Engine> result;
        var foundEngine = engineRepository.findByName(engine.getName());
        if (foundEngine.isPresent()) {
            result = foundEngine;
        } else {
            result = engineRepository.save(engine);
        }
        return result;
    }

    @Override
    public boolean update(Engine engine) {
        return engineRepository.update(engine);
    }

    @Override
    public boolean deleteById(int id) {
        return engineRepository.deleteById(id);
    }

    @Override
    public Optional<Engine> findById(int id) {
        return engineRepository.findById(id);
    }

    @Override
    public Collection<Engine> findAll() {
        return engineRepository.findAll();
    }
}
