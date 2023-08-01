package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.CarModel;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCarModelRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static CarModelRepository carModelRepository;

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
        carModelRepository = new HibernateCarModelRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM CarModel").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        CarModel carModel = new CarModel();
        Optional<CarModel> savedCarModel = carModelRepository.save(carModel);
        assertThat(savedCarModel).isPresent();
        assertThat(savedCarModel.get().getId()).isPositive();
    }

    @Test
    void update() {
        CarModel carModel = new CarModel();
        carModelRepository.save(carModel);
        boolean isUpdated = carModelRepository.update(carModel);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        CarModel carModel = new CarModel();
        carModelRepository.save(carModel);
        boolean isDeleted = carModelRepository.deleteById(carModel.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        CarModel carModel = new CarModel();
        var savedCarModel = carModelRepository.save(carModel);
        Optional<CarModel> foundCarModel = carModelRepository.findById(savedCarModel.get().getId());
        assertThat(foundCarModel).isPresent();
        assertThat(foundCarModel.get()).isEqualTo(carModel);
    }

    @Test
    void findAll() {
        CarModel carModel1 = new CarModel();
        carModelRepository.save(carModel1);
        CarModel carModel2 = new CarModel();
        carModelRepository.save(carModel2);
        Collection<CarModel> carModels = carModelRepository.findAll();
        assertThat(carModels).hasSize(2);
    }

    @Test
    void findByName() {
        CarModel carModel = new CarModel();
        carModel.setName("Test");
        var savedCarModel = carModelRepository.save(carModel);
        Optional<CarModel> foundCarModel = carModelRepository.findByName("Test");
        assertThat(foundCarModel).isPresent();
        assertThat(foundCarModel.get().getName()).isEqualTo("Test");
    }
}