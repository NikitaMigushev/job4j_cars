package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Transmission;
import ru.job4j.cars.repository.TransmissionRepository;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleTransmissionService implements TransmissionService {
    private final TransmissionRepository transmissionRepository;

    @Override
    public Optional<Transmission> save(Transmission transmission) {
        return transmissionRepository.save(transmission);
    }

    @Override
    public boolean update(Transmission transmission) {
        return transmissionRepository.update(transmission);
    }

    @Override
    public boolean deleteById(int id) {
        return transmissionRepository.deleteById(id);
    }

    @Override
    public Optional<Transmission> findById(int id) {
        return transmissionRepository.findById(id);
    }

    @Override
    public Collection<Transmission> findAll() {
        return transmissionRepository.findAll();
    }
}
