package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int id;
    private Integer carId;
    private Integer userId;
    private float price;
    private Integer brandId;
    private String carModelName;
    private Integer carBodyId;
    private Integer carYear;
    private int mileage;
    private String vin;
    private Integer colorId;
    private String engineName;
    private Integer transmissionId;
    private String carPassportNumber;
    private String ownerName;
    private String ownerPassportNumber;
    private String description;
    private Integer currentPhotoId;
    private MultipartFile photo;

    public PostDto(int carId, int userId, float price, int brandId, String carModelName, int carBodyId, int carYear, int mileage) {
        this.carId = carId;
        this.userId = userId;
        this.price = price;
        this.brandId = brandId;
        this.carModelName = carModelName;
        this.carBodyId = carBodyId;
        this.carYear = carYear;
        this.mileage = mileage;
    }
}
