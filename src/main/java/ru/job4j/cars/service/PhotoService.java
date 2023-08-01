package ru.job4j.cars.service;

import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.Photo;

import java.util.Optional;

public interface PhotoService {
    Optional<Photo> save(PhotoDto photoDto);
    Optional<PhotoDto> findById(int id);
    boolean deleteById(int id);
    PhotoDto convertMultipartFileToPhotoDto(MultipartFile file);
}
