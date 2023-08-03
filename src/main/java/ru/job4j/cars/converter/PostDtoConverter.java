package ru.job4j.cars.converter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.*;

import java.util.Optional;

@AllArgsConstructor
@Component
public class PostDtoConverter {
    private final BrandService brandService;
    private final CarBodyService carBodyService;
    private final ColorService colorService;
    private final TransmissionService transmissionService;
    private final CarService carService;
    private final UserService userService;
    private final PostService postService;

    public Post convertPostDtoToPost(PostDto postDto) {
        /*Creating Car*/
        Car car = new Car();
        Optional<Brand> brand = brandService.findById(postDto.getBrandId());
        car.setBrand(brand.get());
        CarModel carModel = new CarModel();
        carModel.setName(postDto.getCarModelName());
        car.setCarModel(carModel);
        Optional<CarBody> carBody = carBodyService.findById(postDto.getCarBodyId());
        car.setCarBody(carBody.get());
        Optional<Color> color = colorService.findById(postDto.getColorId());
        car.setColor(color.get());
        car.setCarYear(postDto.getCarYear());
        car.setMileage(postDto.getMileage());
        car.setVin(postDto.getVin());
        Engine engine = new Engine();
        engine.setName(postDto.getEngineName());
        car.setEngine(engine);
        Optional<Transmission> transmission = transmissionService.findById(postDto.getTransmissionId());
        car.setTransmission(transmission.get());
        Owner owner = new Owner();
        owner.setName(postDto.getOwnerName());
        owner.setPassport(postDto.getOwnerPassportNumber());
        CarPassport carPassport = new CarPassport();
        carPassport.setPassportNumber(postDto.getCarPassportNumber());
        carPassport.setCurrentOwner(owner);
        car.setCarPassport(carPassport);
        if (postDto.getCarId() != 0) {
            Car foundCar = carService.findById(postDto.getCarId()).get();
            car.setId(postDto.getCarId());
            car.getCarModel().setId(foundCar.getCarModel().getId());
            car.getEngine().setId(foundCar.getEngine().getId());
            car.getCarPassport().setId(foundCar.getCarPassport().getId());
            car.getCarPassport().getCurrentOwner().setId(foundCar.getCarPassport().getCurrentOwner().getId());
        }

        /*Creating Post*/
        Post post = new Post();
        post.setDescription(postDto.getDescription());
        post.setUser(userService.findById(postDto.getUserId()).get());
        post.setCar(car);
        post.setPrice(postDto.getPrice());
        if (postDto.getPostId() != 0) {
            Post foundPost = postService.findById(postDto.getPostId()).get();
            post.setCreated(foundPost.getCreated());
            post.setId(postDto.getPostId());
            post.setPhoto(foundPost.getPhoto());
        }
        return post;
    }

    public PostDto convertPostToPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setCarId(post.getCar().getId());
        postDto.setPostId(post.getId());
        postDto.setUserId(post.getUser().getId());
        postDto.setPrice(post.getPrice());
        postDto.setBrandId(post.getCar().getBrand().getId());
        postDto.setCarModelName(post.getCar().getCarModel().getName());
        postDto.setCarBodyId(post.getCar().getCarBody().getId());
        postDto.setCarYear(post.getCar().getCarYear());
        postDto.setMileage(post.getCar().getMileage());
        postDto.setVin(post.getCar().getVin());
        postDto.setColorId(post.getCar().getColor().getId());
        postDto.setEngineName(post.getCar().getEngine().getName());
        postDto.setTransmissionId(post.getCar().getTransmission().getId());
        postDto.setCarPassportNumber(post.getCar().getCarPassport().getPassportNumber());
        postDto.setOwnerName(post.getCar().getCarPassport().getCurrentOwner().getName());
        postDto.setOwnerPassportNumber(post.getCar().getCarPassport().getCurrentOwner().getPassport());
        postDto.setDescription(post.getDescription());
        postDto.setCurrentPhotoId(post.getPhoto().getId());
        return postDto;
    }

}
