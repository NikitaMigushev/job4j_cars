package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.CarBody;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCarBodyRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static CarBodyRepository carBodyRepository;

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
        carBodyRepository = new HibernateCarBodyRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM CarBody").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        CarBody carBody = new CarBody();
        Optional<CarBody> savedCarBody = carBodyRepository.save(carBody);
        assertThat(savedCarBody).isPresent();
        assertThat(savedCarBody.get().getId()).isPositive();
    }

    @Test
    void update() {
        CarBody carBody = new CarBody();
        carBodyRepository.save(carBody);
        boolean isUpdated = carBodyRepository.update(carBody);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        CarBody carBody = new CarBody();
        carBodyRepository.save(carBody);
        boolean isDeleted = carBodyRepository.deleteById(carBody.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        CarBody carBody = new CarBody();
        var savedCarBody = carBodyRepository.save(carBody);
        Optional<CarBody> foundCarBody = carBodyRepository.findById(savedCarBody.get().getId());
        assertThat(foundCarBody).isPresent();
        assertThat(foundCarBody.get()).isEqualTo(carBody);
    }

    @Test
    void findAll() {
        CarBody carBody1 = new CarBody();
        carBodyRepository.save(carBody1);
        CarBody carBody2 = new CarBody();
        carBodyRepository.save(carBody2);
        Collection<CarBody> carBodies = carBodyRepository.findAll();
        assertThat(carBodies).hasSize(2);
    }
}