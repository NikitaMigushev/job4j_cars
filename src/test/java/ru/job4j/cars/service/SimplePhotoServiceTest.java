package ru.job4j.cars.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.dto.PhotoDto;
import ru.job4j.cars.model.Photo;
import ru.job4j.cars.repository.CrudRepository;
import ru.job4j.cars.repository.HibernatePhotoRepository;
import ru.job4j.cars.repository.PhotoRepository;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SimplePhotoServiceTest {

    private static String photoDirectory;
    private static PhotoService photoService;
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
        loadProperties();
        photoService = new SimplePhotoService(photoRepository, photoDirectory);
    }

    private static void loadProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = SimplePhotoServiceTest.class.getClassLoader().getResourceAsStream("application-test.properties")) {
            properties.load(inputStream);
        }
        photoDirectory = properties.getProperty("photo.directory.root")
                + java.io.File.separator
                + properties.getProperty("photo.directory.test")
                + java.io.File.separator
                + properties.getProperty("photo.directory.folder");
    }

    @AfterEach
    void afterEach() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(photoDirectory))) {
            for (Path filePath : stream) {
                Files.delete(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void save() {
        String photoName = "test.jpg";
        byte[] photoContent = "Test content".getBytes();
        PhotoDto photoDto = new PhotoDto(photoName, photoContent);
        Optional<Photo> savedPhoto = photoService.save(photoDto);
        assertTrue(savedPhoto.isPresent());
        assertNotNull(savedPhoto.get().getId());
        assertEquals(photoName, savedPhoto.get().getName());
    }

    @Test
    void findById() {
        String photoName = "test.jpg";
        byte[] photoContent = "Test content".getBytes();
        PhotoDto photoDto = new PhotoDto(photoName, photoContent);
        Optional<Photo> savedPhoto = photoService.save(photoDto);
        var foundPhoto = photoService.findById(savedPhoto.get().getId());
        assertTrue(foundPhoto.isPresent());
        assertThat(foundPhoto.get().getContent()).isEqualTo(photoContent);
    }

    @Test
    void deleteById() {
        String photoName = "test.jpg";
        byte[] photoContent = "Test content".getBytes();
        PhotoDto photoDto = new PhotoDto(photoName, photoContent);
        Optional<Photo> savedPhoto = photoService.save(photoDto);
        var foundPhotoBeforeDelete = photoService.findById(savedPhoto.get().getId());
        assertTrue(foundPhotoBeforeDelete.isPresent());
        photoService.deleteById(savedPhoto.get().getId());
        var foundPhotoAfterDelete = photoService.findById(savedPhoto.get().getId());
        assertTrue(foundPhotoAfterDelete.isEmpty());
    }
}