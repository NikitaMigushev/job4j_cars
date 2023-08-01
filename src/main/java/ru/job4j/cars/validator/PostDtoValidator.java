package ru.job4j.cars.validator;

import ru.job4j.cars.dto.PostDto;

import java.util.ArrayList;
import java.util.List;

public class PostDtoValidator {
    public void validate(PostDto postDto) {
        List<String> errors = new ArrayList<>();
        if (postDto.getPrice() <= 0) {
            errors.add("Price must be greater than 0.");
        }
        if (postDto.getBrand() <= 0) {
            errors.add("Brand is required.");
        }
        if (isEmpty(postDto.getCarModel())) {
            errors.add("Car model is required.");
        }
        if (postDto.getCarBody() <= 0) {
            errors.add("Car body is required.");
        }
        if (postDto.getCarYear() <= 0) {
            errors.add("Car year is required.");
        }
        if (isEmpty(postDto.getVin())) {
            errors.add("VIN is required.");
        }
        if (postDto.getColor() <= 0) {
            errors.add("Color is required.");
        }
        if (isEmpty(postDto.getEngine())) {
            errors.add("Engine is required.");
        }
        if (postDto.getTransmission() <= 0) {
            errors.add("Transmission is required.");
        }
        if (isEmpty(postDto.getCarPassport())) {
            errors.add("Car passport is required.");
        }
        if (isEmpty(postDto.getOwnerName())) {
            errors.add("Owner is required.");
        }
        if (isEmpty(postDto.getOwnerPassport())) {
            errors.add("Owner passport is required.");
        }
        if (isEmpty(postDto.getDescription())) {
            errors.add("Description is required.");
        }
        if (postDto.getPhoto() == null || postDto.getPhoto().isEmpty()) {
            errors.add("Photo is required.");
        }
        if (postDto.getUser() <= 0) {
            errors.add("User is required");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
