package ru.job4j.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private float price;
    private int brand;
    private String carModel;
    private int carBody;
    private int carYear;
    private int mileage;
    private String vin;
    private int color;
    private String engine;
    private int transmission;
    private String carPassport;
    private String ownerName;
    private String ownerPassport;
    private String description;
    private MultipartFile photo;
    private int user;
}
