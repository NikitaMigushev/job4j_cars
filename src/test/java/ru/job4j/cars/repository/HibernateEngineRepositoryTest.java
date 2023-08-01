package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateEngineRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static EngineRepository engineRepository;

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
        engineRepository = new HibernateEngineRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Engine").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        Engine engine = new Engine();
        Optional<Engine> savedEngine = engineRepository.save(engine);
        assertThat(savedEngine).isPresent();
        assertThat(savedEngine.get().getId()).isPositive();
    }

    @Test
    void update() {
        Engine engine = new Engine();
        engineRepository.save(engine);
        boolean isUpdated = engineRepository.update(engine);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Engine engine = new Engine();
        engineRepository.save(engine);
        boolean isDeleted = engineRepository.deleteById(engine.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        Engine engine = new Engine();
        var savedEngine = engineRepository.save(engine);
        Optional<Engine> foundEngine = engineRepository.findById(savedEngine.get().getId());
        assertThat(foundEngine).isPresent();
        assertThat(foundEngine.get()).isEqualTo(engine);
    }

    @Test
    void findAll() {
        Engine engine1 = new Engine();
        engineRepository.save(engine1);
        Engine engine2 = new Engine();
        engineRepository.save(engine2);
        Collection<Engine> engines = engineRepository.findAll();
        assertThat(engines).hasSize(2);
    }

    @Test
    void findByName() {
        Engine engine = new Engine();
        engine.setName("Test");
        var savedEngine = engineRepository.save(engine);
        Optional<Engine> foundEngine = engineRepository.findByName("Test");
        assertThat(foundEngine).isPresent();
        assertThat(foundEngine.get().getName()).isEqualTo("Test");
    }
}