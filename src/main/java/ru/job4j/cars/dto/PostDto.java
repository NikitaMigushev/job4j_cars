package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int id;
    private Integer carId;
    @NotNull
    @Min(1000)
    private Integer userId;
    @Min(1000)
    private float price;
    @NotNull
    @Min(1)
    private Integer brandId;
    @NotNull
    @Min(1)
    private String carModelName;
    @NotNull
    @Min(1)
    private Integer carBodyId;
    @NotNull
    @Min(1)
    private Integer carYear;
    private int mileage;

    @NotNull
    @NotBlank
    private String vin;

    private Integer colorId;

    @NotNull
    @NotBlank
    private String engineName;
    private Integer transmissionId;

    @NotNull
    @NotBlank
    private String carPassportNumber;

    @NotNull
    @NotBlank
    private String ownerName;

    @NotNull
    @NotBlank
    private String ownerPassportNumber;

    @NotNull
    @NotBlank
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
