/*
package ru.job4j.cars.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import ru.job4j.cars.dto.PostDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.*;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleCarServiceTest {
    private static Session session;
    private static SessionFactory sf;
    private static CarRepository carRepository;
    private static BrandService brandService;
    private static BrandRepository brandRepository;
    private static CarModelService carModelService;
    private static CarModelRepository carModelRepository;
    private static CarBodyService carBodyService;
    private static CarBodyRepository carBodyRepository;
    private static EngineRepository engineRepository;
    private static EngineService engineService;
    private static TransmissionService transmissionService;
    private static TransmissionRepository transmissionRepository;
    private static CarPassportService carPassportService;
    private static CarPassportRepository carPassportRepository;
    private static CrudRepository crudRepository;
    private static CarService carService;

    private static ColorRepository colorRepository;

    private static ColorService colorService;

    private static byte[] content = "Test Photo Content".getBytes();
    private static MockMultipartFile photoFile = new MockMultipartFile(
            "photo",
            "test_photo.jpg",
            "image/jpeg",
            content
    );


    @BeforeAll
    public static void setup() throws Exception {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sf = configuration.buildSessionFactory(serviceRegistry);
        sf = configuration.buildSessionFactory();
        session = sf.openSession();
        crudRepository = new CrudRepository(sf);
        brandRepository = new HibernateBrandRepository(crudRepository);
        brandService = new SimpleBrandService(brandRepository);
        carModelRepository = new HibernateCarModelRepository(crudRepository);
        carModelService = new SimpleCarModelService(carModelRepository);
        carBodyRepository = new HibernateCarBodyRepository(crudRepository);
        carBodyService = new SimpleCarBodyService(carBodyRepository);
        engineRepository = new HibernateEngineRepository(crudRepository);
        engineService = new SimpleEngineService(engineRepository);
        transmissionRepository = new HibernateTransmissionRepository(crudRepository);
        transmissionService = new SimpleTransmissionService(transmissionRepository);
        carPassportRepository = new HibernateCarPassportRepository(crudRepository);
        carPassportService = new SimpleCarPassportService(carPassportRepository);
        colorRepository = new HibernateColorRepository(crudRepository);
        colorService = new SimpleColorService(colorRepository);

        carRepository = new HibernateCarRepository(crudRepository);
        carService = new SimpleCarService(carRepository,
                brandService,
                carModelService,
                carBodyService,
                engineService,
                transmissionService,
                carPassportService,
                colorService);
    }

    @Test
    public void testCreateCarFromPostDto() {
        User user = new User();
        Brand brand = new Brand(1, "brandA");
        CarBody carBody = new CarBody(1, "Sedan");
        Color color = new Color(1, "Black");
        Transmission transmission = new Transmission(1, "Auto");
        brandService.save(brand);
        carBodyService.save(carBody);
        colorService.save(color);
        transmissionService.save(transmission);
        PostDto postDto = new PostDto(
                100f,
                1,
                "Model X",
                1,
                2020,
                80,
                "ABC123",
                1,
                "2L 200M",
                1,
                "ABC123",
                "Ivan",
                "post descr",
                photoFile,
                user
        );

        var result = carService.createCarFromPostDto(postDto);
        assertThat(result).isPresent();
        assertThat(result.get().getCarModel().getName()).isEqualTo("Model X");
    }
}*/
