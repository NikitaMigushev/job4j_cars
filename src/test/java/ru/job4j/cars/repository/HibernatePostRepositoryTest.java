package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * When test Hibernate make sure to add all necessary records to fetch data correctly.
 * For example if Car object need to be fetched in Post object, make sure to add Car record in CarRepository first.
 */
class HibernatePostRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static PostRepository postRepository;

    private static CarRepository carRepository;

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
        postRepository = new HibernatePostRepository(new CrudRepository(sf));
        carRepository = new HibernateCarRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM Post").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }



    @Test
    void save() {
        Post post = new Post();
        Optional<Post> savedPost = postRepository.save(post);
        assertThat(savedPost).isPresent();
        assertThat(savedPost.get().getId()).isPositive();
    }

    @Test
    void update() {
        Post post = new Post();
        postRepository.save(post);
        boolean isUpdated = postRepository.update(post);
        assertThat(isUpdated).isTrue();
    }

    @Test
    void deleteById() {
        Post post = new Post();
        postRepository.save(post);
        boolean isDeleted = postRepository.deleteById(post.getId());
        assertThat(isDeleted).isTrue();
    }

    @Test
    void findById() {
        Post post = new Post();
        var savedPost = postRepository.save(post);
        Optional<Post> foundPost = postRepository.findById(savedPost.get().getId());
        assertThat(foundPost).isPresent();
        assertThat(foundPost.get()).isEqualTo(post);
    }

    @Test
    void findAll() {
        Post post1 = new Post();
        postRepository.save(post1);
        Post post2 = new Post();
        postRepository.save(post2);
        Collection<Post> posts = postRepository.findAll();
        assertThat(posts).hasSize(2);
    }

    @Test
    void getLastDayPosts() {
        Post post1 = new Post();
        post1.setCreated(LocalDateTime.now().minusDays(1));
        post1.setDescription("yesterday");
        var savedPost = postRepository.save(post1);
        Post post2 = new Post();
        post2.setCreated(LocalDateTime.now());
        post2.setDescription("today");
        postRepository.save(post2);
        Collection<Post> result = postRepository.getLastDayPosts();
        assertThat(result.iterator().next().getDescription()).isEqualTo(savedPost.get().getDescription());
    }

    @Test
    void getPostsWithPhoto() {
        Post post1 = new Post();
        post1.setHasPhoto(true);
        post1.setDescription("has photo");
        var savedPost = postRepository.save(post1);
        Post post2 = new Post();
        post2.setHasPhoto(false);
        post2.setDescription("no photo");
        postRepository.save(post2);
        Collection<Post> result = postRepository.getPostsWithPhoto();
        assertThat(result.iterator().next().getDescription()).isEqualTo(savedPost.get().getDescription());
    }

    @Test
    void getPostsByBrand() {
        Car car1 = new Car();
        car1.setBrand("Toyota");
        Car car2 = new Car();
        car2.setBrand("Mercedes");
        var savedCar1 = carRepository.save(car1);
        var savedCar2 = carRepository.save(car2);
        Post post1 = new Post();
        post1.setCar(savedCar1.get());
        post1.setDescription("Toyota");
        var savedPost1 = postRepository.save(post1);
        Post post2 = new Post();
        post2.setDescription("Mercedes");
        post2.setCar(savedCar2.get());
        var savedPost2 = postRepository.save(post2);
        Collection<Post> result = postRepository.getPostsByBrand("Toyota");
        assertThat(result.iterator().next().getDescription()).isEqualTo(savedPost1.get().getDescription());
    }
}