package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int postId;
    private int carId;
    private int userId;
    private float price;
    private int brandId;
    private String carModelName;
    private int carBodyId;
    private int carYear;
    private int mileage;
    private String vin;
    private int colorId;
    private String engineName;
    private int transmissionId;
    private String carPassportNumber;
    private String ownerName;
    private String ownerPassportNumber;
    private String description;
    private int currentPhotoId;
    private MultipartFile photo;
}
