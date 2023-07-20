package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Photo;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernatePhotoRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static PhotoRepository photoRepository;


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
        photoRepository = new HibernatePhotoRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Photo").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void save() {
        Photo photo = new Photo();
        Optional<Photo> savedPhoto = photoRepository.save(photo);
        assertThat(savedPhoto).isPresent();
        assertThat(savedPhoto.get().getId()).isPositive();
    }

    @Test
    void findById() {
        Photo photo = new Photo();
        var savedPhoto = photoRepository.save(photo);
        Optional<Photo> foundPhoto = photoRepository.findById(savedPhoto.get().getId());
        assertThat(foundPhoto).isPresent();
        assertThat(foundPhoto.get()).isEqualTo(photo);
    }

    @Test
    void update() {
        Photo photo = new Photo();
        photoRepository.save(photo);
        boolean isUpdated = photoRepository.update(photo);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Photo photo = new Photo();
        photoRepository.save(photo);
        boolean isDeleted = photoRepository.deleteById(photo.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findAll() {
        Photo photo1 = new Photo();
        photoRepository.save(photo1);
        Photo photo2 = new Photo();
        photoRepository.save(photo2);
        Collection<Photo> photos = photoRepository.findAll();
        assertThat(photos).hasSize(2);
    }
}