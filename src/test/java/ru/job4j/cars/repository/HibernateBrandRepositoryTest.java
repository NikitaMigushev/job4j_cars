package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Brand;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateBrandRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static BrandRepository brandRepository;

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
        brandRepository = new HibernateBrandRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Brand").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        Brand brand = new Brand();
        Optional<Brand> savedBrand = brandRepository.save(brand);
        assertThat(savedBrand).isPresent();
        assertThat(savedBrand.get().getId()).isPositive();
    }

    @Test
    void update() {
        Brand brand = new Brand();
        brandRepository.save(brand);
        boolean isUpdated = brandRepository.update(brand);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Brand brand = new Brand();
        brandRepository.save(brand);
        boolean isDeleted = brandRepository.deleteById(brand.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        Brand brand = new Brand();
        var savedBrand = brandRepository.save(brand);
        Optional<Brand> foundBrand = brandRepository.findById(savedBrand.get().getId());
        assertThat(foundBrand).isPresent();
        assertThat(foundBrand.get()).isEqualTo(brand);
    }

    @Test
    void findAll() {
        Brand brand1 = new Brand();
        brandRepository.save(brand1);
        Brand brand2 = new Brand();
        brandRepository.save(brand2);
        Collection<Brand> brands = brandRepository.findAll();
        assertThat(brands).hasSize(2);
    }
}