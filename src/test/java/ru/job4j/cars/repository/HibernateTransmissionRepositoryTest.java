package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Transmission;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateTransmissionRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static TransmissionRepository transmissionRepository;

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
        transmissionRepository = new HibernateTransmissionRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Transmission").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        Transmission transmission = new Transmission();
        Optional<Transmission> savedTransmission = transmissionRepository.save(transmission);
        assertThat(savedTransmission).isPresent();
        assertThat(savedTransmission.get().getId()).isPositive();
    }

    @Test
    void update() {
        Transmission transmission = new Transmission();
        transmissionRepository.save(transmission);
        boolean isUpdated = transmissionRepository.update(transmission);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Transmission transmission = new Transmission();
        transmissionRepository.save(transmission);
        boolean isDeleted = transmissionRepository.deleteById(transmission.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        Transmission transmission = new Transmission();
        var savedTransmission = transmissionRepository.save(transmission);
        Optional<Transmission> foundTransmission = transmissionRepository.findById(savedTransmission.get().getId());
        assertThat(foundTransmission).isPresent();
        assertThat(foundTransmission.get()).isEqualTo(transmission);
    }

    @Test
    void findAll() {
        Transmission transmission1 = new Transmission();
        transmissionRepository.save(transmission1);
        Transmission transmission2 = new Transmission();
        transmissionRepository.save(transmission2);
        Collection<Transmission> transmissions = transmissionRepository.findAll();
        assertThat(transmissions).hasSize(2);
    }

}