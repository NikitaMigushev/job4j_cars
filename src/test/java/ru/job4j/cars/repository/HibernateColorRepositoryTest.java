package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Color;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateColorRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static ColorRepository colorRepository;

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
        colorRepository = new HibernateColorRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Color").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        Color color = new Color();
        Optional<Color> savedColor = colorRepository.save(color);
        assertThat(savedColor).isPresent();
        assertThat(savedColor.get().getId()).isPositive();
    }

    @Test
    void update() {
        Color color = new Color();
        colorRepository.save(color);
        boolean isUpdated = colorRepository.update(color);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Color color = new Color();
        colorRepository.save(color);
        boolean isDeleted = colorRepository.deleteById(color.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        Color color = new Color();
        var savedColor = colorRepository.save(color);
        Optional<Color> foundColor = colorRepository.findById(savedColor.get().getId());
        assertThat(foundColor).isPresent();
        assertThat(foundColor.get()).isEqualTo(color);
    }

    @Test
    void findAll() {
        Color color1 = new Color();
        colorRepository.save(color1);
        Color color2 = new Color();
        colorRepository.save(color2);
        Collection<Color> colors = colorRepository.findAll();
        assertThat(colors).hasSize(2);
    }

    @Test
    void findByName() {
        Color color = new Color();
        color.setName("Test");
        var savedColor = colorRepository.save(color);
        Optional<Color> foundColor = colorRepository.findByName("Test");
        assertThat(foundColor).isPresent();
        assertThat(foundColor.get().getName()).isEqualTo("Test");
    }

}