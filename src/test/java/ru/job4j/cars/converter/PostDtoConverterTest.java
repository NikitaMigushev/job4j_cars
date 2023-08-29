package ru.job4j.cars.converter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.BeforeAll;
import ru.job4j.cars.repository.*;
import ru.job4j.cars.service.*;

class PostDtoConverterTest {
    private static Session session;
    private static SessionFactory sf;
    private static PostRepository postRepository;

    private static BrandService brandService;
    private static CarBodyService carBodyService;
    private static ColorService colorService;
    private static TransmissionService transmissionService;
    private static CarService carService;
    private static UserService userService;

    private static PostDtoConverter postDtoConverter;

    @BeforeAll
    public static void setup() throws Exception {
        Configuration configuration = new Configuration();
        configuration.configure("hibernatePostgres.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();
        sf = configuration.buildSessionFactory(serviceRegistry);
        sf = configuration.buildSessionFactory();
        session = sf.openSession();
        postRepository = new HibernatePostRepository(new CrudRepository(sf));
        brandService = new SimpleBrandService(new HibernateBrandRepository(new CrudRepository(sf)));
        carBodyService = new SimpleCarBodyService(new HibernateCarBodyRepository(new CrudRepository(sf)));
        colorService = new SimpleColorService(new HibernateColorRepository(new CrudRepository(sf)));
        transmissionService = new SimpleTransmissionService(new HibernateTransmissionRepository(new CrudRepository(sf)));
        carService = new SimpleCarService(new HibernateCarRepository(new CrudRepository(sf)));
        userService = new SimpleUserService(new HibernateUserRepository(new CrudRepository(sf)));
        postDtoConverter = new PostDtoConverter(brandService, carBodyService, colorService, transmissionService, carService, userService, postRepository);

    }
}