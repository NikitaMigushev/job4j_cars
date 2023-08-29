package ru.job4j.cars.converter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import ru.job4j.cars.configuration.ModelMapperConfig;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class ModelMapperPostDtoConverterTest {

    private static UserRepository userRepositoryMock;

    private static BrandRepository brandRepositoryMock;

    private static CarBodyRepository carBodyRepositoryMock;

    private static ColorRepository colorRepositoryMock;

    private static TransmissionRepository transmissionRepositoryMock;

    private static CarRepository carRepositoryMock;

    private static PostRepository postRepositoryMock;

    @BeforeAll
    public static void setup() {
        userRepositoryMock = Mockito.mock(UserRepository.class);
        User mockUser = new User(1, "John Doe", "email@email.ru", "123");
        Mockito.when(userRepositoryMock.findById(1)).thenReturn(Optional.of(mockUser));

        brandRepositoryMock = Mockito.mock(BrandRepository.class);
        Brand brand = new Brand(1, "Totyota");
        Mockito.when(brandRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(brand));

        carBodyRepositoryMock = Mockito.mock(CarBodyRepository.class);
        CarBody carBody = new CarBody(1, "Sedan");
        Mockito.when(carBodyRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(carBody));

        colorRepositoryMock = Mockito.mock(ColorRepository.class);
        Color color = new Color(1, "Black");
        Mockito.when(colorRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(color));

        transmissionRepositoryMock = Mockito.mock(TransmissionRepository.class);
        Transmission transmission = new Transmission(1, "auto");
        Mockito.when(transmissionRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(transmission));

        carRepositoryMock = Mockito.mock(CarRepository.class);
        Brand brandMock = new Brand(1, "Honda");
        CarModel carModelMock = new CarModel(1, "Toyota");
        CarBody carBodyMock = new CarBody(1, "Car body");
        Color colorMock = new Color(1, "Color");
        Engine engineMock = new Engine(1, "Engine");
        Transmission transmissionMock = new Transmission(1, "Transmission");
        CarPassport carPassportMock = new CarPassport(1, "ABC", new Owner(1, "Owner name", "Abc"));
        Car carMock = new Car();
        carMock.setId(1);
        carMock.setBrand(brandMock);
        carMock.setCarModel(carModelMock);
        carMock.setCarBody(carBodyMock);
        carMock.setColor(colorMock);
        carMock.setCarYear(2020);
        carMock.setMileage(100);
        carMock.setVin("ABC");
        carMock.setEngine(engineMock);
        carMock.setTransmission(transmissionMock);
        carMock.setCarPassport(carPassportMock);
        Mockito.when(carRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(carMock));

        postRepositoryMock = Mockito.mock(PostRepository.class);
        Photo photo = new Photo(1, "Test photo", "path");
        Post postWithPhoto = new Post(new Photo(1, "Test name", "Test path"));
        Mockito.when(postRepositoryMock.findById(Mockito.anyInt())).thenReturn(Optional.of(postWithPhoto));
    }

    @Test
    public void testConvertPostToPostDto() {
        /*Test Post to PostDto*/
        ModelMapper modelMapper = new ModelMapperConfig(
                userRepositoryMock,
                brandRepositoryMock,
                carBodyRepositoryMock,
                colorRepositoryMock,
                transmissionRepositoryMock,
                carRepositoryMock,
                postRepositoryMock
        ).modelMapper();
        ModelMapperPostDtoConverter converter = new ModelMapperPostDtoConverter(modelMapper);
        Post post = new Post();
        post.setId(1);
        post.setPrice(100);
        post.setDescription("description");
        User user = new User(1, "John Doe", "email@email.ru", "123");
        post.setUser(user);
        Brand brand = new Brand(1, "Toyota");
        CarModel carModel = new CarModel(1, "Toyota");
        CarBody carBody = new CarBody(1, "Car body");
        Color color = new Color(1, "Color");
        Engine engine = new Engine(1, "Engine");
        Transmission transmission = new Transmission(1, "Transmission");
        CarPassport carPassport = new CarPassport(1, "ABC", new Owner(1, "Owner name", "Abc"));
        Car car = new Car();
        car.setId(1);
        car.setBrand(brand);
        car.setCarModel(carModel);
        car.setCarBody(carBody);
        car.setColor(color);
        car.setCarYear(2020);
        car.setMileage(100);
        car.setVin("ABC");
        car.setEngine(engine);
        car.setTransmission(transmission);
        car.setCarPassport(carPassport);
        post.setCar(car);
        PostDto postDto = converter.convertPostToPostDto(post);
        assertThat(postDto).isNotNull();
        assertThat(postDto.getId()).isEqualTo(1);
        assertThat(postDto.getOwnerPassportNumber()).isEqualTo("Abc");
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "photo", "test.jpg", "image/jpeg", new byte[0]
        );
        postDto.setPhoto(mockMultipartFile);


        /*Test PostDto to Post*/
        Post postTest = converter.convertPostDtoToPost(postDto);
        assertThat(postTest).isNotNull();
        assertThat(postTest.getCar().getBrand().getName()).isEqualTo("Honda");
        assertThat(postTest.getCar().getCarPassport().getPassportNumber()).isEqualTo("ABC");
        assertThat(postTest.getCar().getCarPassport().getCurrentOwner().getPassport()).isEqualTo("Abc");
    }
}