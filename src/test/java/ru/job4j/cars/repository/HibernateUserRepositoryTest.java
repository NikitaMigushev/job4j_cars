package ru.job4j.cars.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HibernateUserRepositoryTest {
    private static Session session;
    private static SessionFactory sf;

    private static UserRepository userRepository;

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
        userRepository = new HibernateUserRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void tearDown() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM User").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void whenCreateThenGetSame() {
        User user = new User();
        user.setFullName("John Doe");
        user.setPassword("password");
        Optional<User> savedUser = userRepository.create(user);
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getId()).isNotNull();
    }

    @Test
    public void whenFindByIdThenSuccess() {
        User user = new User();
        user.setFullName("John Doe");
        user.setPassword("password");
        userRepository.create(user);
        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getFullName()).isEqualTo(user.getFullName());
    }

    @Test
    public void whenFindByLoginSuccess() {
        User user = new User();
        user.setFullName("John Doe");
        user.setPassword("password");
        userRepository.create(user);
        Optional<User> foundUser = userRepository.findByLogin(user.getFullName());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getId()).isEqualTo(user.getId());
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setFullName("John Doe");
        user.setPassword("password");
        userRepository.create(user);
        boolean isDeleted = userRepository.delete(user.getId());
        assertThat(isDeleted).isTrue();
        Optional<User> deletedUser = userRepository.findById(user.getId());
        assertThat(deletedUser).isEmpty();
    }

    @Test
    public void testFindAll() {
        User user1 = new User();
        user1.setFullName("John Doe");
        user1.setPassword("password");
        userRepository.create(user1);
        User user2 = new User();
        user2.setFullName("John Doe");
        user2.setPassword("password");
        userRepository.create(user2);
        Collection<User> allUsers = userRepository.findAll();
        assertThat(allUsers).hasSize(2);
    }

    @Test
    public void whenUpdateThenSuccess() {
        User user = new User();
        user.setFullName("John Doe");
        user.setPassword("password");
        userRepository.create(user);
        user.setFullName("Jane Smith");
        user.setPassword("newPassword");
        userRepository.update(user);
        Optional<User> updatedUser = userRepository.findById(user.getId());
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getFullName()).isEqualTo("Jane Smith");
        assertThat(updatedUser.get().getPassword()).isEqualTo("newPassword");
    }

    @Test
    public void whenFindAllOrderByIdThenSuccess() {
        User user1 = new User();
        user1.setFullName("John Doe");
        user1.setPassword("password");
        userRepository.create(user1);
        User user2 = new User();
        user2.setFullName("Jane Smith");
        user2.setPassword("password123");
        userRepository.create(user2);
        User user3 = new User();
        user3.setFullName("Alice Johnson");
        user3.setPassword("qwerty");
        userRepository.create(user3);
        List<User> users = userRepository.findAllOrderById();
        assertThat(users).hasSize(3);
        assertThat(users.get(0).getId()).isEqualTo(user1.getId());
        assertThat(users.get(1).getId()).isEqualTo(user2.getId());
        assertThat(users.get(2).getId()).isEqualTo(user3.getId());
    }

    @Test
    public void whenFindByLikeLoginThenSuccess() {
        User user1 = new User();
        user1.setFullName("John Doe");
        user1.setPassword("password");
        userRepository.create(user1);
        User user2 = new User();
        user2.setFullName("Jane Smith");
        user2.setPassword("password123");
        userRepository.create(user2);
        User user3 = new User();
        user3.setFullName("Alice Johnson");
        user3.setPassword("qwerty");
        userRepository.create(user3);
        List<User> foundUsers = userRepository.findByLikeLogin("Jo");
        assertThat(foundUsers).hasSize(2);
        assertThat(foundUsers.get(0).getFullName()).isEqualTo("John Doe");
        assertThat(foundUsers.get(1).getFullName()).isEqualTo("Alice Johnson");
    }
}