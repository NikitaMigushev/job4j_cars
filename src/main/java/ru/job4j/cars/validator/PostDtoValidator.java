package ru.job4j.cars.validator;

import ru.job4j.cars.dto.PostDto;

import java.util.ArrayList;
import java.util.List;

public class PostDtoValidator {
    public List<String> validate(PostDto postDto) {
        List<String> errors = new ArrayList<>();
        if (postDto.getPrice() <= 0) {
            errors.add("Price must be greater than 0.");
        }
        if (postDto.getBrandId() <= 0) {
            errors.add("Brand is required.");
        }
        if (isEmpty(postDto.getCarModelName())) {
            errors.add("Car model is required.");
        }
        if (postDto.getCarBodyId() <= 0) {
            errors.add("Car body is required.");
        }
        if (postDto.getCarYear() <= 0) {
            errors.add("Car year is required.");
        }
        if (isEmpty(postDto.getVin())) {
            errors.add("VIN is required.");
        }
        if (postDto.getColorId() <= 0) {
            errors.add("Color is required.");
        }
        if (isEmpty(postDto.getEngineName())) {
            errors.add("Engine is required.");
        }
        if (postDto.getTransmissionId() <= 0) {
            errors.add("Transmission is required.");
        }
        if (isEmpty(postDto.getCarPassportNumber())) {
            errors.add("Car passport is required.");
        }
        if (isEmpty(postDto.getOwnerName())) {
            errors.add("Owner is required.");
        }
        if (isEmpty(postDto.getOwnerPassportNumber())) {
            errors.add("Owner passport is required.");
        }
        if (isEmpty(postDto.getDescription())) {
            errors.add("Description is required.");
        }
        if (postDto.getUserId() <= 0) {
            errors.add("User is required");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors.toString());
        }
        return errors;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
