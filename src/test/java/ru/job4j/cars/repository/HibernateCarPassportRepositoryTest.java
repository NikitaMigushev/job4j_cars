package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.CarPassport;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateCarPassportRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static CarPassportRepository carPassportRepository;

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
        carPassportRepository = new HibernateCarPassportRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM CarPassport").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        CarPassport carPassport = new CarPassport();
        Optional<CarPassport> savedCarPassport = carPassportRepository.save(carPassport);
        assertThat(savedCarPassport).isPresent();
        assertThat(savedCarPassport.get().getId()).isPositive();
    }

    @Test
    void update() {
        CarPassport carPassport = new CarPassport();
        carPassportRepository.save(carPassport);
        boolean isUpdated = carPassportRepository.update(carPassport);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        CarPassport carPassport = new CarPassport();
        carPassportRepository.save(carPassport);
        boolean isDeleted = carPassportRepository.deleteById(carPassport.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        CarPassport carPassport = new CarPassport();
        var savedCarPassport = carPassportRepository.save(carPassport);
        Optional<CarPassport> foundCarPassport = carPassportRepository.findById(savedCarPassport.get().getId());
        assertThat(foundCarPassport).isPresent();
        assertThat(foundCarPassport.get().getId()).isEqualTo(carPassport.getId());
    }

    @Test
    void findAll() {
        CarPassport carPassport1 = new CarPassport();
        carPassportRepository.save(carPassport1);
        CarPassport carPassport2 = new CarPassport();
        carPassportRepository.save(carPassport2);
        Collection<CarPassport> carPassports = carPassportRepository.findAll();
        assertThat(carPassports).hasSize(2);
    }
}