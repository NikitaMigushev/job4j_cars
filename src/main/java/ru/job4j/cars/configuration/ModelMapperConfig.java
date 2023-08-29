package ru.job4j.cars.configuration;

import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.AbstractProvider;
import org.modelmapper.ModelMapper;
import org.modelmapper.Provider;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.*;

@AllArgsConstructor
@Configuration
public class ModelMapperConfig {

    private final UserRepository userRepository;

    private final BrandRepository brandRepository;

    private final CarBodyRepository carBodyRepository;

    private final ColorRepository colorRepository;

    private final TransmissionRepository transmissionRepository;

    private final CarRepository carRepository;

    private final PostRepository postRepository;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addConverter(new AbstractConverter<Integer, Car>() {
            @Override
            protected Car convert(Integer carId) {
                Car car = new Car();
                car.setId(carId);
                return car;
            }
        });
        modelMapper.addConverter(new AbstractConverter<Integer, User>() {
            @Override
            protected User convert(Integer userId) {
                User user = new User();
                user.setId(userId);
                return user;
            }
        });

        Provider<Car> carProvider = new AbstractProvider<Car>() {
            @Override
            protected Car get() {
                return new Car();
            }
        };

        Provider<User> userProvider = new AbstractProvider<User>() {
            @Override
            protected User get() {
                return new User();
            }
        };

        modelMapper.createTypeMap(Post.class, PostDto.class)
                .addMapping(src -> src.getCar().getId(), PostDto::setCarId)
                .addMapping(src -> src.getCar().getBrand().getId(), PostDto::setBrandId)
                .addMapping(src -> src.getCar().getCarModel().getName(), PostDto::setCarModelName)
                .addMapping(src -> src.getCar().getCarBody().getId(), PostDto::setCarBodyId)
                .addMapping(src -> src.getCar().getCarYear(), PostDto::setCarYear)
                .addMapping(src -> src.getCar().getMileage(), PostDto::setMileage)
                .addMapping(src -> src.getCar().getVin(), PostDto::setVin)
                .addMapping(src -> src.getCar().getColor().getId(), PostDto::setColorId)
                .addMapping(src -> src.getCar().getEngine().getName(), PostDto::setEngineName)
                .addMapping(src -> src.getCar().getTransmission().getId(), PostDto::setTransmissionId)
                .addMapping(src -> src.getCar().getCarPassport().getPassportNumber(), PostDto::setCarPassportNumber)
                .addMapping(src -> src.getCar().getCarPassport().getCurrentOwner().getName(), PostDto::setOwnerName)
                .addMapping(src -> src.getCar().getCarPassport().getCurrentOwner().getPassport(), PostDto::setOwnerPassportNumber)
                .addMapping(src -> src.getPhoto().getId(), PostDto::setCurrentPhotoId)
                .addMapping(src -> src.getUser().getId(), PostDto::setUserId);

        modelMapper.createTypeMap(PostDto.class, Post.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> {
                        PostDto source = (PostDto) ctx.getSource();
                        if (source.getCarId() != null && source.getCarId() != 0) {
                            Car car = carRepository.findById(source.getId()).get();
                            return car;
                        }
                        Car car = new Car();
                        car.setBrand(brandRepository.findById(source.getBrandId()).get());
                        car.setCarModel(new CarModel(source.getCarModelName()));
                        car.setCarBody(carBodyRepository.findById(source.getCarBodyId()).get());
                        car.setColor(colorRepository.findById(source.getColorId()).get());
                        car.setCarYear(source.getCarYear());
                        car.setMileage(source.getMileage());
                        car.setVin(source.getVin());
                        car.setEngine(new Engine(source.getEngineName()));
                        car.setTransmission(transmissionRepository.findById(source.getTransmissionId()).get());
                        car.setCarPassport(new CarPassport(source.getCarPassportNumber(),
                                new Owner(source.getOwnerName(), source.getOwnerPassportNumber())));
                        return car;
                    }).map(src -> src, Post::setCar);
                })
                .addMappings(mapper -> {
                    mapper.using(ctx -> {
                        PostDto source = (PostDto) ctx.getSource();
                        User user = userRepository.findById(source.getUserId()).get();
                        return user;
                    }).map(src -> src, Post::setUser);
                })
                .addMappings(mapper -> {
                    mapper.using(ctx -> {
                        PostDto source = (PostDto) ctx.getSource();
                        if (source.getId() != 0) {
                            Photo photo = postRepository.findById(source.getId()).get().getPhoto();
                            return photo;
                        }
                        return new Photo();
                    }).map(src -> src, Post::setPhoto);
                });
        return modelMapper;
    }
}
