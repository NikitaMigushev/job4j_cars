package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCarRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static CarRepository carRepository;

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
        carRepository = new HibernateCarRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Car").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


    @Test
    void save() {
        Car car = new Car();
        Optional<Car> savedCar = carRepository.save(car);
        assertThat(savedCar).isPresent();
        assertThat(savedCar.get().getId()).isPositive();
    }

    @Test
    void update() {
        Car car = new Car();
        carRepository.save(car);
        boolean isUpdated = carRepository.update(car);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Car car = new Car();
        carRepository.save(car);
        boolean isDeleted = carRepository.deleteById(car.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        Car car = new Car();
        var savedCar = carRepository.save(car);
        Optional<Car> foundCar = carRepository.findById(savedCar.get().getId());
        assertThat(foundCar).isPresent();
        assertThat(foundCar.get()).isEqualTo(car);
    }

    @Test
    void findAll() {
        Car car1 = new Car();
        carRepository.save(car1);
        Car car2 = new Car();
        carRepository.save(car2);
        Collection<Car> cars = carRepository.findAll();
        assertThat(cars).hasSize(2);
    }
}