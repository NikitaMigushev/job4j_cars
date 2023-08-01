package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateOwnerRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static OwnerRepository ownerRepository;

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
        ownerRepository = new HibernateOwnerRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Owner").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        Owner owner = new Owner();
        Optional<Owner> savedOwner = ownerRepository.save(owner);
        assertThat(savedOwner).isPresent();
        assertThat(savedOwner.get().getId()).isPositive();
    }

    @Test
    void update() {
        Owner owner = new Owner();
        ownerRepository.save(owner);
        boolean isUpdated = ownerRepository.update(owner);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Owner owner = new Owner();
        ownerRepository.save(owner);
        boolean isDeleted = ownerRepository.deleteById(owner.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        Owner owner = new Owner();
        var savedOwner = ownerRepository.save(owner);
        Optional<Owner> foundOwner = ownerRepository.findById(savedOwner.get().getId());
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get()).isEqualTo(owner);
    }

    @Test
    void findAll() {
        Owner owner1 = new Owner();
        ownerRepository.save(owner1);
        Owner owner2 = new Owner();
        ownerRepository.save(owner2);
        Collection<Owner> owners = ownerRepository.findAll();
        assertThat(owners).hasSize(2);
    }

    @Test
    void findByPassport() {
        Owner owner = new Owner();
        owner.setPassport("ABC");
        var savedOwner = ownerRepository.save(owner);
        var foundOwner = ownerRepository.findByPassport(owner.getPassport());
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get()).isEqualTo(owner);
    }
}